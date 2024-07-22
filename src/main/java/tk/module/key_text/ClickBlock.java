package tk.module.key_text;

import net.minecraft.util.BlockPos;
import org.json.JSONObject;
import tk.tools.Tools;

import java.io.*;
import java.util.*;

@SuppressWarnings("unused")
public final class ClickBlock {
    static private class JsonClass {
        final String address;
        final String position;

        JsonClass(String address, int[] position) {
            this.address = address;
            this.position = Arrays.toString(position);
        }
    }

    static final private JsonClass[] defaultJSONMessage = {
            new JsonClass("Bumper Cars - Perk Corner", new int[]{17, 70, -9}),
            new JsonClass("Roller Coaster", new int[]{-24, 70, 22}),
            new JsonClass("Roller Coaster - Chest Corner", new int[]{-24, 70, 31}),
            new JsonClass("ParkEntrance - gate x > 0", new int[]{5, 70, -14}),
            new JsonClass("ParkEntrance - gate x < 0", new int[]{-5, 70, -14}),
            new JsonClass("Ferris Wheel - alt", new int[]{16, 70, 39}),
            new JsonClass("Ferris Wheel - Ultimate Machine", new int[]{22, 70, 32}),
            new JsonClass("Ferris Wheel - Door", new int[]{19, 70, 16}),
    };

    static final private String url = "scriptsMods/KeyText.json";

    static {
        // 异步执行解析JSON
        final Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                try {
                    parseJSON();
                } catch (Exception ignored) {
                }
            }
        }, 0);
    }

    static private void parseJSON() throws Exception {
        final File file = new File(url);

        /// 如果没有json文件 就先写入json文件
        if (!file.exists()) {
            final JSONObject jsonObject = new JSONObject();
            for (JsonClass jsonClass : defaultJSONMessage) {
                jsonObject.put(jsonClass.address, jsonClass.position);
            }
            final PrintWriter printWriter = new PrintWriter(file);
            printWriter.println(jsonObject.toString(4));
            printWriter.close();
        }

        /// 读取json文件
        final StringBuilder stringBuilder = new StringBuilder();
        final BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        String line = bufferedReader.readLine();
        while (line != null) {
            stringBuilder.append(line);
            line = bufferedReader.readLine();
        }
        bufferedReader.close();

        // 解析json
        final String jsonString = stringBuilder.toString();
        final JSONObject jsonObject = new JSONObject(jsonString);
        final Set<String> keys = jsonObject.keySet();

        final LinkedList<List<Integer>> positions = new LinkedList<List<Integer>>();

        for (String key : keys) {
            final String value = jsonObject.getString(key);
            final String[] values = value.replace("[", "").replace("]", "")
                    .split(",");
            final int x = Integer.parseInt(values[0].trim());
            final int y = Integer.parseInt(values[1].trim());
            final int z = Integer.parseInt(values[2].trim());

            final List<Integer> position = new ArrayList<Integer>(Arrays.asList(x, y, z));
            positions.addLast(position);
        }
        initCampMap(keys.toArray(new String[0]), positions);
    }

    static private final HashMap<List<Integer>, String> campMap = new HashMap<List<Integer>, String>();

    /**
     * @param campName 据点名称
     * @param campPosition 据点坐标
     */
    static private void initCampMap(final String[] campName, final LinkedList<List<Integer>> campPosition) {
        campMap.clear();
        for (int i = 0; i < campName.length; i++) {
            campMap.put(campPosition.get(i), campName[i]);
        }
    }

    static private String getCampName(final List<Integer> campPosition) {
        return campMap.get(campPosition);
    }

    static private final Timer timer = new Timer();
    static private boolean lookOnClickBlock = false;

    static public void run(boolean clickSend, BlockPos blockPos, boolean spammer, boolean random) {

        // 如果没有开这个功能或者方法上锁了就结束
        if (!clickSend || lookOnClickBlock) return;

        // 读取方块坐标
        final int x = blockPos.getX();
        final int y = blockPos.getY();
        final int z = blockPos.getZ();
        final List<Integer> list = new ArrayList<Integer>(Arrays.asList(x, y, z));

        // 读取这个方块对应的据点位置
        final String campName = getCampName(list);
        if (campName == null) {
            return;
        }

        // 上锁方法
        lookOnClickBlock = true;

        // 上锁100毫秒后解锁
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                lookOnClickBlock = false;
            }
        }, 500);

        // 生成聊天文本
        String chatText = Tool.getChatText(campName, random);

        // 发送聊天信息
        Tools.mc.thePlayer.sendChatMessage(chatText);

        // 如果开了刷屏功能 就多生成与发送2次
        if (spammer) {
            for (int i = 0; i < 2; i++) {
                chatText =  Tool.getChatText(campName, random);
                Tools.mc.thePlayer.sendChatMessage(chatText);
            }
        }
    }
}
