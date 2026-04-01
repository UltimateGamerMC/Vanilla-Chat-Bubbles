package com.beckytidus.chatbubbles;

import com.beckytidus.chatbubbles.config.ChatBubblesConfig;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.level.LevelRenderEvents;
import net.minecraft.client.renderer.state.level.CameraRenderState;

public class ChatBubblesClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ChatBubbles.LOGGER.info("Chat Bubbles client initializing");

        ChatBubblesConfig.load();

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            ChatBubbleManager.getInstance().tick();
        });

        LevelRenderEvents.AFTER_SOLID_FEATURES.register(context -> {
            CameraRenderState cameraState = context.levelState().cameraRenderState;
            ChatBubbleRenderer.render(
                context.poseStack(),
                cameraState,
                context.bufferSource(),
                cameraState.pos.x(),
                cameraState.pos.y(),
                cameraState.pos.z()
            );
        });
    }
}
