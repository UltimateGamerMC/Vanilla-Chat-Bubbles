package com.beckytidus.chatbubbles;

import net.minecraft.network.chat.Component;
import net.minecraft.world.phys.Vec3;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class ChatBubbleManager {
    private static final ChatBubbleManager INSTANCE = new ChatBubbleManager();
    private final ConcurrentHashMap<UUID, List<ChatBubble>> playerBubbles = new ConcurrentHashMap<>();

    private ChatBubbleManager() {}

    public static ChatBubbleManager getInstance() {
        return INSTANCE;
    }

    public void addChatBubble(UUID playerId, Component message, Vec3 position) {
        ChatBubble bubble = new ChatBubble(message, playerId, position, System.currentTimeMillis());
        playerBubbles.computeIfAbsent(playerId, k -> new ArrayList<>()).add(bubble);
    }

    public List<ChatBubble> getAllBubbles() {
        List<ChatBubble> allBubbles = new ArrayList<>();
        for (List<ChatBubble> bubbles : playerBubbles.values()) {
            allBubbles.addAll(bubbles);
        }
        return allBubbles;
    }

    public void tick() {
        long currentTime = System.currentTimeMillis();
        playerBubbles.values().forEach(bubbles ->
            bubbles.removeIf(bubble -> bubble.shouldRemove(currentTime))
        );
        playerBubbles.entrySet().removeIf(entry -> entry.getValue().isEmpty());
    }
}
