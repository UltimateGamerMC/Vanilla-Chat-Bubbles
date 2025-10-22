package com.beckytidus.chatbubbles;

import com.beckytidus.chatbubbles.config.ChatBubblesConfig;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;

public class ChatBubblesClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ChatBubbles.LOGGER.info("Chat Bubbles client initializing");

        ChatBubblesConfig.load();

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            ChatBubbleManager.getInstance().tick();
        });
    }
}
