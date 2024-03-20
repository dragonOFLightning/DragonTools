package tk.tools;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import org.lwjgl.opengl.GL11;

import java.awt.Color;

@SuppressWarnings("unused")
public class RenderBox {
    private final static Minecraft mc = Minecraft.getMinecraft();

    // ��ԭGL״̬
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

    public String renderBox(final double maxX, final double maxY, final double maxZ, final double minX, final double minY, final double minZ, final Color color) {
        try {
            // ����GL״̬
            GL11.glPushMatrix();
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glEnable(GL11.GL_LINE_SMOOTH);
            GL11.glDisable(GL11.GL_DEPTH_TEST);
            GL11.glDisable(GL11.GL_TEXTURE_2D);
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glBegin(GL11.GL_LINES);

            // ������ɫ�Ϳ��
            final float red = color.getRed() / 255f;
            final float green = color.getGreen() / 255f;
            final float blue = color.getBlue() / 255f;
            final float alpha = color.getAlpha() / 255f;
            GlStateManager.color(red, green, blue, alpha);
            GL11.glLineWidth(2);

            // ��Ⱦ�Ϸ�����
            renderLine(minX, minY, minZ, maxX, minY, minZ);
            renderLine(maxX, minY, minZ, maxX, minY, maxZ);
            renderLine(maxX, minY, maxZ, minX, minY, maxZ);
            renderLine(minX, minY, maxZ, minX, minY, minZ);

            // ��Ⱦ�·�����
            renderLine(minX, maxY, minZ, maxX, maxY, minZ);
            renderLine(maxX, maxY, minZ, maxX, maxY, maxZ);
            renderLine(maxX, maxY, maxZ, minX, maxY, maxZ);
            renderLine(minX, maxY, maxZ, minX, maxY, minZ);

            // ��Ⱦ���Ӿ���
            renderLine(minX, minY, minZ, minX, maxY, minZ);
            renderLine(maxX, minY, minZ, maxX, maxY, minZ);
            renderLine(maxX, minY, maxZ, maxX, maxY, maxZ);
            renderLine(minX, minY, maxZ, minX, maxY, maxZ);
            GL11.glEnd();
        } catch (Error error) {
            resetGL();
            return error.getMessage();
        }
        resetGL();
        return "Success";
    }

    public String renderBox(double maxX, double maxY, double maxZ, double minX, double minY, double minZ) {
        return renderBox(maxX, maxY, maxZ, minX, minY, minZ, Color.GREEN);
    }

    // ���� ͨ��AxisAlignedBB���ݲ���
    public String renderBox(AxisAlignedBB box, Color color) {
        return renderBox(box.maxX, box.maxY, box.maxZ, box.minX, box.minY, box.minZ, color);
    }

    public String renderBox(AxisAlignedBB box) {
        return renderBox(box.maxX, box.maxY, box.maxZ, box.minX, box.minY, box.minZ, Color.GREEN);
    }

    // ��Ⱦʵ��
    public String renderEntity(final Entity entity, Color color) {
        // ��ȡʵ������
        final AxisAlignedBB box = entity.getEntityBoundingBox();
        double maxX = box.maxX;
        double maxY = box.maxY;
        double maxZ = box.maxZ;
        double minX = box.minX;
        double minY = box.minY;
        double minZ = box.minZ;

        // �������λ��
        final RenderManager renderManager = RenderBox.mc.getRenderManager();
        final double viewerPosX = renderManager.viewerPosX;
        final double viewerPosY = renderManager.viewerPosY;
        final double viewerPosZ = renderManager.viewerPosZ;
        maxX = maxX - viewerPosX;
        maxY = maxY - viewerPosY;
        maxZ = maxZ - viewerPosZ;
        minX = minX - viewerPosX;
        minY = minY - viewerPosY;
        minZ = minZ - viewerPosZ;
        
        return renderBox(maxX, maxY, maxZ, minX, minY, minZ, color);
    }

    public String renderEntity(Entity entity) {
        return renderEntity(entity, Color.GREEN);
    }
}
