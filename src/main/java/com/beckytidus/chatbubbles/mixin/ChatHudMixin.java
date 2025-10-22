package com.beckytidus.chatbubbles.mixin;

import com.beckytidus.chatbubbles.ChatBubbleManager;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.ChatHud;
import net.minecraft.client.gui.hud.MessageIndicator;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.message.MessageSignatureData;
import net.minecraft.text.Text;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Mixin(ChatHud.class)
public class ChatHudMixin {
    @Shadow @Final private MinecraftClient client;

    private static final Pattern CHAT_PATTERN = Pattern.compile("<([^>]+)>");

    @Inject(method = "addMessage(Lnet/minecraft/text/Text;Lnet/minecraft/network/message/MessageSignatureData;Lnet/minecraft/client/gui/hud/MessageIndicator;)V", at = @At("HEAD"))
    private void onChatMessage(Text message, @Nullable MessageSignatureData signature, @Nullable MessageIndicator indicator, CallbackInfo ci) {
        if (client.world == null) {
            return;
        }

        String messageString = message.getString();
        Matcher matcher = CHAT_PATTERN.matcher(messageString);

        if (matcher.find()) {
            String playerName = matcher.group(1);
            PlayerEntity player = findPlayerByName(client.world, playerName);

            if (player != null) {
                double height = com.beckytidus.chatbubbles.config.ChatBubblesConfig.INSTANCE.bubbleHeight;
                Vec3d bubblePos = player.getEntityPos().add(0, player.getHeight() + height, 0);

                String chatText = messageString.substring(matcher.end()).trim();
                Text bubbleMessage = com.beckytidus.chatbubbles.config.ChatBubblesConfig.INSTANCE.showPlayerName
                    ? message
                    : Text.literal(chatText);

                ChatBubbleManager.getInstance().addChatBubble(player.getUuid(), bubbleMessage, bubblePos);
            }
        }
    }

    private PlayerEntity findPlayerByName(ClientWorld world, String name) {
        for (AbstractClientPlayerEntity player : world.getPlayers()) {
            if (player.getName().getString().equals(name)) {
                return player;
            }
        }
        return null;
    }
}
