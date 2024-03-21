package tk.tools;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.OpenGLException;

import java.awt.Color;

@SuppressWarnings("unused")
public class RenderLine3D {

    final private static Minecraft mc = Minecraft.getMinecraft();

    private static double[] getRelativePosition(double x, double y, double z) {
        // 读取渲染位置
        final RenderManager renderManager = mc.getRenderManager();
        final double viewerPosX = renderManager.viewerPosX;
        final double viewerPosY = renderManager.viewerPosY;
        final double viewerPosZ = renderManager.viewerPosZ;

        // 计算相对位置
        final double relativeX = x - viewerPosX;
        final double relativeY = y - viewerPosY;
        final double relativeZ = z - viewerPosZ;

        return new double[]{relativeX, relativeY, relativeZ};
    }

    static public String renderLine3D(double x1, double y1, double z1, double x2, double y2, double z2, final Color color, final boolean global) {
        try {
            // 保存GL状态
            GL11.glPushMatrix();
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glEnable(GL11.GL_LINE_SMOOTH);
            GL11.glDisable(GL11.GL_DEPTH_TEST);
            GL11.glDisable(GL11.GL_TEXTURE_2D);
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glBegin(GL11.GL_LINES);

            // 设置颜色和宽度
            final float red = color.getRed() / 255f;
            final float green = color.getGreen() / 255f;
            final float blue = color.getBlue() / 255f;
            final float alpha = color.getAlpha() / 255f;
            GlStateManager.color(red, green, blue, alpha);
            GL11.glLineWidth(2);

            // 根据条件来是否计算相对位置
            final double[] pos1 = global ? getRelativePosition(x1, y1, z1) : new double[]{x1, y1, z1};
            x1 = pos1[0];
            y1 = pos1[1];
            z1 = pos1[2];
            final double[] pos2 = global ? getRelativePosition(x2, y2, z2) : new double[]{x2, y2, z2};
            x2 = pos2[0];
            y2 = pos2[1];
            z2 = pos2[2];

            // 渲染
            GL11.glVertex3d(x1, y1, z1);
            GL11.glVertex3d(x2, y2, z2);
            GL11.glEnd();

            // 还原GL状态
            GL11.glDisable(GL11.GL_BLEND);
            GL11.glEnable(GL11.GL_TEXTURE_2D);
            GL11.glEnable(GL11.GL_DEPTH_TEST);
            GL11.glDisable(GL11.GL_LINE_SMOOTH);
            GL11.glDisable(GL11.GL_BLEND);
            GL11.glPopMatrix();
        } catch (OpenGLException error) {
            return error.getMessage();
        } catch (Exception error) {
            return error.getMessage();
        }
        return "Success";
    }

    // 重载 仅需传递坐标
    static public String renderLine3D(double x1, double y1, double z1, double x2, double y2, double z2) {
        return renderLine3D(x1, y1, z1, x2, y2, z2, Color.GREEN, true);
    }

    // 重载 需要传递颜色
    static public String renderLine3D(double x1, double y1, double z1, double x2, double y2, double z2, Color color) {
        return renderLine3D(x1, y1, z1, x2, y2, z2, color, true);
    }

    // 重载 需要传递是否全局
    static public String renderLine3D(double x1, double y1, double z1, double x2, double y2, double z2, boolean global) {
        return renderLine3D(x1, y1, z1, x2, y2, z2, Color.GREEN, global);
    }
}