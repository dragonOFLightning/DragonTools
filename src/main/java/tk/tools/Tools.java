package tk.tools;

import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.fml.common.Mod;

import java.util.Random;

@SuppressWarnings("unused")
@Mod(modid = Tools.name, version = Tools.version, dependencies = Tools.author)
public class Tools {
    final static String name = "Tools";
    final static String version = "0.0.1";
    final static String author = "ColdDragon";

    // 欧式距离计算
    static public int getEuclDistanceSQ(final int x1, final int y1, final int z1, final int x2, final int y2, final int z2) {
        final int x = x2 - x1;
        final int y = y2 - y1;
        final int z = z2 - z1;
        return x * x + y * y + z * z;
    }

    static public double getEuclDistance(final int x1, final int y1, final int z1, final int x2, final int y2, final int z2) {
        return Math.sqrt(getEuclDistanceSQ(x1, y1, z1, x2, y2, z2));
    }

    static public double getEuclDistanceSQ(final double x1, final double y1, final double z1, final double x2, final double y2, final double z2) {
        final double x = x2 - x1;
        final double y = y2 - y1;
        final double z = z2 - z1;
        return x * x + y * y + z * z;
    }

    static public double getEuclDistance(final double x1, final double y1, final double z1, final double x2, final double y2, final double z2) {
        return Math.sqrt(getEuclDistanceSQ(x1, y1, z1, x2, y2, z2));
    }

    // 欧式距离计算 : 原点


    // 体积计算
    static public int getVolume(final int maxX, final int maxY, final int maxZ, final int minX, final int minY, final int minZ) {
        final int lengthX = maxX - minX;
        final int lengthY = maxY - minY;
        final int lengthZ = maxZ - minZ;
        return lengthX * lengthY * lengthZ;
    }

    static public double getVolume(final double maxX, final double maxY, final double maxZ, final double minX, final double minY, final double minZ) {
        final double lengthX = maxX - minX;
        final double lengthY = maxY - minY;
        final double lengthZ = maxZ - minZ;
        return lengthX * lengthY * lengthZ;
    }

    static public double getVolume(final AxisAlignedBB box) {
        return getVolume(box.maxX, box.maxY, box.maxZ, box.minX, box.minY, box.minZ);
    }

    // 获取随机字符串
    static public String getRandomString(int length){
        StringBuilder stringBuffer = new StringBuilder();
        for(int i = 0;i<length;i++){
            stringBuffer.append(getRandomChar());
        }
        return stringBuffer.toString();
    }

    // 获取随机字符
    static final private String randomList = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    static final private Random random = new Random();
    static public char getRandomChar(){
        final int randomIndex = random.nextInt(randomList.length());
        return randomList.charAt(randomIndex);
    }
}
