package tk.module.key_text;

import tk.tools.Keys;
import tk.tools.Tools;

import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;

import static tk.module.key_text.Tool.getChatText;

@SuppressWarnings("unused")
final public class Key {

    static private class Config {
        final public String[] values;
        final public String[] keys;

        public Config(String[] values, String[] keys) {
            this.values = values;
            this.keys = keys;
        }
    }

    /**
     *
     * @param configs 用户的配置
     */
    static private Config parseConfig(String configs) {

        // 拆解元素
        String[] configsArray = configs.split(",");

        // 建立字典
        LinkedList<String> keys = new LinkedList<String>();
        LinkedList<String> values = new LinkedList<String>();

        for (String config : configsArray) {
            // 解析元素
            String[] configSet = config.split("-");

            // 存入字典
            keys.add(configSet[0]);
            values.add(configSet[1]);
        }
        // 把写好的字典吐出来
        return new Config(keys.toArray(new String[0]), values.toArray(new String[0]));
    }

    static final private String[] textListClaws7 = {
            "███爪爪爪爪爪爪爪█777777777777",
            "██爪███爪█爪█████████7",
            "█爪████爪██爪████████7",
            "██爪███爪██爪████████7",
            "██爪███爪███爪███████7",
            "██爪███爪████爪██████7",
            "█爪████爪█████爪█████7",
            "爪█████爪█████爪爪████7"
    };

    // 2019 10 2 author[little fish] TODO
    // 2024 4 14 do what author[3clas_dragon]?

    static private boolean lookOnKey = false;

    // 注 : 爪7 整活的功能 彩蛋
    static public void run(String configs, int keyID, boolean random, boolean spammer, boolean claws7) {

        // 如果门口被锁了 进不去只好不执行这个代码
        if (lookOnKey) return;

        // 如果按下Z键 并且开启了彩蛋功能
        if (keyID == Keys.getKeyValue("Z") && claws7) {

            // 买个新的锁把门锁了 这样其他线程就进不来了
            lookOnKey = true;

            // 有间隔地发送彩蛋文本
            final Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                int count = 0;

                @Override
                public void run() {
                    // 如果彩蛋派发完了
                    if (count == textListClaws7.length - 1) {

                        // 用计时器把锁砸开
                        lookOnKey = false;

                        // 计时器也被砸坏了
                        timer.cancel();
                    }
                    Tools.mc.thePlayer.sendChatMessage(textListClaws7[count]);
                    count++;
                }
            }, 0, 60);

            // 结束运行
            return;
        }

        // 解析配置
        final Config config = parseConfig(configs);

        // 获取配置中配置了哪些快捷键
        final String[] configKeys = config.keys;

        for (int index = 0; index < configKeys.length; index++) {

            // 获取单个快捷键
            final String configKey = configKeys[index];

            // 如果按下的键与这个快捷键相符
            if (keyID != Keys.getKeyValue((configKey))) continue;

            // 获取快捷键对应的内容
            final String configValue = config.values[index];

            // 根据内容生成聊天文本
            String chatText = getChatText(configValue, random);

            // 发送聊天
            Tools.mc.thePlayer.sendChatMessage(chatText);

            if (spammer) {
                for (int i = 0; i < 2; i++) {
                    chatText = getChatText(configValue, random);
                    Tools.mc.thePlayer.sendChatMessage(chatText);
                }
            }
        }
    }
}
