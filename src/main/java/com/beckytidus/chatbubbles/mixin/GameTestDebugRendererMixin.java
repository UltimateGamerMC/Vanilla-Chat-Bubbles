package com.beckytidus.chatbubbles.mixin;

import com.beckytidus.chatbubbles.ChatBubbleRenderer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.debug.GameTestDebugRenderer;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameTestDebugRenderer.class)
public class GameTestDebugRendererMixin {
    @Inject(method = "renderMarkers", at = @At("RETURN"))
    private void onRenderMarkers(MatrixStack matrices, VertexConsumerProvider vertexConsumers, CallbackInfo ci) {
        if (vertexConsumers instanceof VertexConsumerProvider.Immediate immediate) {
            net.minecraft.client.MinecraftClient client = net.minecraft.client.MinecraftClient.getInstance();
            if (client.world != null && client.gameRenderer != null && client.gameRenderer.getCamera() != null) {
                net.minecraft.client.render.Camera camera = client.gameRenderer.getCamera();
                net.minecraft.util.math.Vec3d cameraPos = camera.getPos();
                ChatBubbleRenderer.render(matrices, camera, immediate, cameraPos.x, cameraPos.y, cameraPos.z);
            }
        }
    }
}
