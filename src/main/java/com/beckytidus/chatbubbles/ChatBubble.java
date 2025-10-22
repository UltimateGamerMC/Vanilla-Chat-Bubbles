package com.beckytidus.chatbubbles;

import net.minecraft.text.Text;
import net.minecraft.util.math.Vec3d;

public class ChatBubble {
    private final Text message;
    private final Vec3d initialPosition;
    private final long creationTime;
    private final int displayDuration;
    private final int fadeDuration;
    private final int fadeInDuration;
    private final double upwardSpeed;

    public ChatBubble(Text message, Vec3d position, long creationTime) {
        this.message = message;
        this.initialPosition = position;
        this.creationTime = creationTime;
        this.displayDuration = com.beckytidus.chatbubbles.config.ChatBubblesConfig.INSTANCE.displayDuration;
        this.fadeDuration = com.beckytidus.chatbubbles.config.ChatBubblesConfig.INSTANCE.fadeDuration;
        this.fadeInDuration = com.beckytidus.chatbubbles.config.ChatBubblesConfig.INSTANCE.fadeInDuration;
        this.upwardSpeed = com.beckytidus.chatbubbles.config.ChatBubblesConfig.INSTANCE.upwardSpeed;
    }

    public Text getMessage() {
        return message;
    }

    public Vec3d getPosition(long currentTime) {
        long elapsed = currentTime - creationTime;
        double secondsElapsed = elapsed / 1000.0;
        double yOffset = secondsElapsed * upwardSpeed;
        return initialPosition.add(0, yOffset, 0);
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
