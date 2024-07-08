package si.f5.yagi.caviaengineering.mixin.coremods.client;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ScreenEffectRenderer;
import net.minecraft.world.entity.Entity;
import si.f5.yagi.caviaengineering.transporter.BeamingData;
import si.f5.yagi.caviaengineering.transporter.Transporter;

@Mixin(ScreenEffectRenderer.class)
public class ScreenEffectRendererMixin {

	@Inject(method="renderScreenEffect", at=@At("HEAD"))
	private static void renderTransportEffect(Minecraft pMinecraft, PoseStack pPoseStack, CallbackInfo ci) {
		
		Entity e = pMinecraft.cameraEntity;
		
		BeamingData beaming = BeamingData.get(e);
		
		if (beaming.isBeaming()) {
			Transporter.renderStream(beaming, pPoseStack);
		}
		
	}
}
