package com.beckytidus.chatbubbles.mixin;

import com.beckytidus.chatbubbles.ChatBubbleManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.ChatComponent;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.multiplayer.chat.GuiMessageTag;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MessageSignature;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Mixin(ChatComponent.class)
public class ChatComponentMixin {
    @Shadow @Final private Minecraft minecraft;

    private static final Pattern CHAT_PATTERN = Pattern.compile("<([^>]+)>");

    @Inject(method = "addPlayerMessage", at = @At("HEAD"))
    private void onChatMessage(Component message, @Nullable MessageSignature signature, @Nullable GuiMessageTag tag, CallbackInfo ci) {
        if (minecraft.level == null) {
            return;
        }

        String messageString = message.getString();
        Matcher matcher = CHAT_PATTERN.matcher(messageString);

        if (matcher.find()) {
            String playerName = matcher.group(1);
            Player player = findPlayerByName(minecraft.level, playerName);

            if (player != null) {
                double height = com.beckytidus.chatbubbles.config.ChatBubblesConfig.INSTANCE.bubbleHeight;
                Vec3 bubblePos = player.position().add(0, player.getBbHeight() + height, 0);

                String chatText = messageString.substring(matcher.end()).trim();
                Component bubbleMessage = com.beckytidus.chatbubbles.config.ChatBubblesConfig.INSTANCE.showPlayerName
                    ? message
                    : Component.literal(chatText);

                ChatBubbleManager.getInstance().addChatBubble(player.getUUID(), bubbleMessage, bubblePos);
            }
        }
    }

    private Player findPlayerByName(ClientLevel world, String name) {
        for (AbstractClientPlayer player : world.players()) {
            if (player.getName().getString().equals(name)) {
                return player;
            }
        }
        return null;
    }
}
