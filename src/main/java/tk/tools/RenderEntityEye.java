package tk.tools;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.AxisAlignedBB;
import org.lwjgl.opengl.GL11;

import java.awt.Color;

@SuppressWarnings("unused")
public class RenderEntityEye {
    // 还原GL状态
    private static void resetGL() {
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glDisable(GL11.GL_LINE_SMOOTH);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glPopMatrix();
    }

    private static void renderLine(final double x1, final double y1, final double z1, final double x2, final double y2, final double z2) {
        GL11.glVertex3d(x1, y1, z1);
        GL11.glVertex3d(x2, y2, z2);
    }

    static public String renderEyeLine(final EntityLivingBase entity, final Color color) {
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

            // 读取实体X与Z的坐标的相对位置
            final Minecraft mc = Minecraft.getMinecraft();
            final RenderManager renderManager = mc.getRenderManager();
            final AxisAlignedBB boxEntity = entity.getEntityBoundingBox();
            final double maxX = boxEntity.maxX - renderManager.viewerPosX;
            final double minX = boxEntity.minX - renderManager.viewerPosX;
            final double maxZ = boxEntity.maxZ - renderManager.viewerPosZ;
            final double minZ = boxEntity.minZ - renderManager.viewerPosZ;

            // 读取实体眼睛高度的相对位置
            final double y = entity.posY + entity.getEyeHeight() - renderManager.viewerPosY;

            renderLine(minX, y, minZ, maxX, y, minZ);
            renderLine(maxX, y, minZ, maxX, y, maxZ);
            renderLine(maxX, y, maxZ, minX, y, maxZ);
            renderLine(minX, y, maxZ, minX, y, minZ);
            GL11.glEnd();
        } catch (Exception error) {
            resetGL();
            return error.getMessage();
        }
        resetGL();
        return "Success";
    }

    static public String renderEyeLine(EntityLivingBase entity) {
        return renderEyeLine(entity, Color.RED);
    }
}
