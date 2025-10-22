package com.beckytidus.chatbubbles;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Vec3d;
import org.joml.Matrix4f;

public class ChatBubbleRenderer {
    public static void render(MatrixStack matrices, Camera camera, VertexConsumerProvider.Immediate vertexConsumers, double cameraX, double cameraY, double cameraZ) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null || client.world == null) {
            return;
        }

        long currentTime = System.currentTimeMillis();
        java.util.List<ChatBubble> bubbles = ChatBubbleManager.getInstance().getAllBubbles();

        for (ChatBubble bubble : bubbles) {
            float alpha = bubble.getAlpha(currentTime);
            if (alpha <= 0) {
                continue;
            }

            Vec3d bubblePos = bubble.getPosition(currentTime);

            matrices.push();
            matrices.translate((float)(bubblePos.x - cameraX), (float)(bubblePos.y - cameraY) + 0.07f, (float)(bubblePos.z - cameraZ));
            matrices.multiply(camera.getRotation());

            float scale = (float) com.beckytidus.chatbubbles.config.ChatBubblesConfig.INSTANCE.textScale;
            matrices.scale(scale, -scale, scale);

            String text = bubble.getMessage().getString();
            float offset = (float)(-client.textRenderer.getWidth(text)) / 2.0f;

            int configColor = com.beckytidus.chatbubbles.config.ChatBubblesConfig.INSTANCE.textColor;
            int textColor = (configColor & 0xFFFFFF) | ((int)(alpha * 255) << 24);

            int bgColor = com.beckytidus.chatbubbles.config.ChatBubblesConfig.INSTANCE.backgroundColor;
            int bgTransparency = com.beckytidus.chatbubbles.config.ChatBubblesConfig.INSTANCE.backgroundTransparency;
            float bgAlphaPercent = 1.0f - (bgTransparency / 100.0f);
            int bgAlpha = (int)(alpha * bgAlphaPercent * 255);
            int backgroundColor = (bgColor & 0xFFFFFF) | (bgAlpha << 24);

            Matrix4f matrix = matrices.peek().getPositionMatrix();

            client.textRenderer.draw(
                text,
                offset,
                0.0f,
                textColor,
                false,
                matrix,
                vertexConsumers,
                TextRenderer.TextLayerType.NORMAL,
                backgroundColor,
                LightmapTextureManager.MAX_LIGHT_COORDINATE
            );

            matrices.pop();
        }

        vertexConsumers.drawCurrentLayer();
    }
}
