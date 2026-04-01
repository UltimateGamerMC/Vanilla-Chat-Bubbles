package com.beckytidus.chatbubbles;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class ChatBubble {
    private final Component message;
    private final UUID ownerId;
    private final Vec3 initialPosition;
    private final long creationTime;
    private final int displayDuration;
    private final int fadeDuration;
    private final int fadeInDuration;
    private final double upwardSpeed;

    public ChatBubble(Component message, UUID ownerId, Vec3 position, long creationTime) {
        this.message = message;
        this.ownerId = ownerId;
        this.initialPosition = position;
        this.creationTime = creationTime;
        this.displayDuration = com.beckytidus.chatbubbles.config.ChatBubblesConfig.INSTANCE.displayDuration;
        this.fadeDuration = com.beckytidus.chatbubbles.config.ChatBubblesConfig.INSTANCE.fadeDuration;
        this.fadeInDuration = com.beckytidus.chatbubbles.config.ChatBubblesConfig.INSTANCE.fadeInDuration;
        this.upwardSpeed = com.beckytidus.chatbubbles.config.ChatBubblesConfig.INSTANCE.upwardSpeed;
    }

    public Component getMessage() {
        return message;
    }

    public Vec3 getPosition(long currentTime, @Nullable ClientLevel level) {
        Vec3 base = initialPosition;
        if (com.beckytidus.chatbubbles.config.ChatBubblesConfig.INSTANCE.bubbleFollowPlayer && level != null) {
            for (AbstractClientPlayer player : level.players()) {
                if (player.getUUID().equals(ownerId)) {
                    double h = com.beckytidus.chatbubbles.config.ChatBubblesConfig.INSTANCE.bubbleHeight;
                    base = player.position().add(0, player.getBbHeight() + h, 0);
                    break;
                }
            }
        }
        long elapsed = currentTime - creationTime;
        double secondsElapsed = elapsed / 1000.0;
        double yOffset = secondsElapsed * upwardSpeed;
        return base.add(0, yOffset, 0);
    }

    public long getCreationTime() {
        return creationTime;
    }

    public float getAlpha(long currentTime) {
        long elapsed = currentTime - creationTime;

        if (elapsed > displayDuration + fadeDuration) {
            return 0.0f;
        }

        if (elapsed < fadeInDuration) {
            float fadeInProgress = (float) elapsed / fadeInDuration;
            return fadeInProgress;
        }

        if (elapsed > displayDuration) {
            float fadeOutProgress = (float)(elapsed - displayDuration) / fadeDuration;
            return 1.0f - fadeOutProgress;
        }

        return 1.0f;
    }

    public boolean shouldRemove(long currentTime) {
        return (currentTime - creationTime) > (displayDuration + fadeDuration);
    }
}
