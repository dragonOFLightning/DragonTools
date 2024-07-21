package tk.tools;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import org.lwjgl.opengl.GL11;

import java.awt.Color;

@SuppressWarnings("unused")
public class RenderTracerLine {
    private static final Minecraft mc = Minecraft.getMinecraft();

    private static void resetGL() {
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glDisable(GL11.GL_LINE_SMOOTH);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glPopMatrix();
    }

    static public String renderTracerLine(final double x, final double y, final double z, Color color) {
        final EntityPlayer player = RenderTracerLine.mc.thePlayer;

        // 强制关闭视角摇晃
        final GameSettings gameSettings = mc.gameSettings;
        if (gameSettings.viewBobbing) {
            gameSettings.viewBobbing = false;
        }

        // 计算渲染位置
        final RenderManager renderManager = mc.getRenderManager();
        final double renderX = x - renderManager.viewerPosX;
        final double renderY = y - renderManager.viewerPosY;
        final double renderZ = z - renderManager.viewerPosZ;

        try {
            // 初始化GL
            GL11.glPushMatrix();
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glEnable(GL11.GL_LINE_SMOOTH);
            GL11.glDisable(GL11.GL_DEPTH_TEST);
            GL11.glDisable(GL11.GL_TEXTURE_2D);
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glBegin(GL11.GL_LINES);

            // 上色
            final float red = color.getRed() / 255f;
            final float green = color.getGreen() / 255f;
            final float blue = color.getBlue() / 255f;
            final float alpha = color.getAlpha() / 255f;
            GL11.glColor4f(red, green, blue, alpha);

            // 渲染
            GL11.glVertex3d(0, player.getEyeHeight(), 0);
            GL11.glVertex3d(renderX, renderY, renderZ);
            GL11.glEnd();
        } catch (Error error) {
            resetGL();
            return error.getMessage();
        }
        resetGL();
        return "Success";
    }

    static public String renderTracerLine(double x, double y, double z) {
        return renderTracerLine(x, y, z, Color.GREEN);
    }

    // 重载 - 通过实体传递参数
    static public String renderTracerLine(final Entity entity, Color color) {
        final AxisAlignedBB box = entity.getEntityBoundingBox();

        // 计算中点
        final double maxX = box.maxX;
        final double minX = box.minX;
        final double centerX = (maxX + minX) / 2;
        final double maxZ = box.maxZ;
        final double minZ = box.minZ;
        final double centerZ = (maxZ + minZ) / 2;

        return renderTracerLine(centerX, box.minY, centerZ, color);
    }

    static public String renderTracerLine(Entity entity) {
        return renderTracerLine(entity, Color.RED);
    }
}
