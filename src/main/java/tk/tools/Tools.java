package tk.tools;

import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.fml.common.Mod;

@SuppressWarnings("unused")
@Mod(modid = Tools.name, version = Tools.version, dependencies = Tools.author)
public class Tools {
    final static String name = "Tools";
    final static String version = "0.0.1";
    final static String author = "ColdDragon";

    // 欧式距离计算
    final public int getEulDistanceDef(int x1, int y1, int z1, int x2, int y2, int z2) {
        final int x = x2 - x1;
        final int y = y2 - y1;
        final int z = z2 - z1;
        return x * x + y * y + z * z;
    }

    final public double getEulDistance(int x1, int y1, int z1, int x2, int y2, int z2) {
        return Math.sqrt(getEulDistanceDef(x1, y1, z1, x2, y2, z2));
    }

    final public double getEulDistanceDef(double x1, double y1, double z1, double x2, double y2, double z2) {
        final double x = x2 - x1;
        final double y = y2 - y1;
        final double z = z2 - z1;
        return x * x + y * y + z * z;
    }

    final public double getEulDistance(double x1, double y1, double z1, double x2, double y2, double z2) {
        return Math.sqrt(getEulDistanceDef(x1, y1, z1, x2, y2, z2));
    }

    // 体积计算
    final public int getVolume(final int maxX, final int maxY, final int maxZ, final int minX, final int minY, final int minZ) {
        final int lengthX = maxX - minX;
        final int lengthY = maxY - minY;
        final int lengthZ = maxZ - minZ;
        return lengthX * lengthY * lengthZ;
    }

    final public double getVolume(final double maxX, final double maxY, final double maxZ, final double minX, final double minY, final double minZ) {
        final double lengthX = maxX - minX;
        final double lengthY = maxY - minY;
        final double lengthZ = maxZ - minZ;
        return lengthX * lengthY * lengthZ;
    }

    final public double getVolume(AxisAlignedBB box) {
        return getVolume(box.maxX, box.maxY, box.maxZ, box.minX, box.minY, box.minZ);
    }
}
