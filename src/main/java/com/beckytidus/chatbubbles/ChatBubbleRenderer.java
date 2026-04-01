package com.beckytidus.chatbubbles;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.network.chat.Component;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.util.LightCoordsUtil;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix4f;

public class ChatBubbleRenderer {
    public static void render(
        PoseStack poseStack,
        CameraRenderState cameraState,
        MultiBufferSource.BufferSource bufferSource,
        double cameraX,
        double cameraY,
        double cameraZ
    ) {
        Minecraft client = Minecraft.getInstance();
        if (client.player == null || client.level == null) {
            return;
        }

        long currentTime = System.currentTimeMillis();
        java.util.List<ChatBubble> bubbles = ChatBubbleManager.getInstance().getAllBubbles();
        double maxDistSq = com.beckytidus.chatbubbles.config.ChatBubblesConfig.INSTANCE.maxDistance;
        maxDistSq = maxDistSq * maxDistSq;

        for (ChatBubble bubble : bubbles) {
            float alpha = bubble.getAlpha(currentTime);
            if (alpha <= 0) {
                continue;
            }

            Vec3 bubblePos = bubble.getPosition(currentTime, client.level);
            double dx = bubblePos.x - cameraX;
            double dy = bubblePos.y - cameraY;
            double dz = bubblePos.z - cameraZ;
            if (dx * dx + dy * dy + dz * dz > maxDistSq) {
                continue;
            }

            poseStack.pushPose();
            poseStack.translate((float)(bubblePos.x - cameraX), (float)(bubblePos.y - cameraY) + 0.07f, (float)(bubblePos.z - cameraZ));
            if (cameraState.initialized) {
                poseStack.mulPose(cameraState.orientation);
            }

            float scale = (float) com.beckytidus.chatbubbles.config.ChatBubblesConfig.INSTANCE.textScale;
            poseStack.scale(scale, -scale, scale);

            Component msg = bubble.getMessage();
            float offset = (float)(-client.font.width(msg)) / 2.0f;

            int configColor = com.beckytidus.chatbubbles.config.ChatBubblesConfig.INSTANCE.textColor;
            int textColor = (configColor & 0xFFFFFF) | ((int)(alpha * 255) << 24);

            int bgColor = com.beckytidus.chatbubbles.config.ChatBubblesConfig.INSTANCE.backgroundColor;
            int bgTransparency = com.beckytidus.chatbubbles.config.ChatBubblesConfig.INSTANCE.backgroundTransparency;
            float bgAlphaPercent = 1.0f - (bgTransparency / 100.0f);
            int bgAlpha = (int)(alpha * bgAlphaPercent * 255);
            int backgroundColor = (bgColor & 0xFFFFFF) | (bgAlpha << 24);

            Matrix4f matrix = poseStack.last().pose();

            client.font.drawInBatch(
                msg,
                offset,
                0.0f,
                textColor,
                false,
                matrix,
                bufferSource,
                Font.DisplayMode.NORMAL,
                backgroundColor,
                LightCoordsUtil.FULL_BRIGHT
            );

            poseStack.popPose();
        }

        bufferSource.endBatch();
    }
}
