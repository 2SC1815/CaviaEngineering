package si.f5.yagi.caviaengineering.mixin.coremods.client;

import org.joml.Quaternionf;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import si.f5.yagi.caviaengineering.transporter.BeamingData;
import si.f5.yagi.caviaengineering.transporter.Transporter;

@Mixin(EntityRenderDispatcher.class)
public class EntityRenderDispatcherMixin {

	@Inject(
			method = "render", 
			at = @At(
				shift = Shift.AFTER,
				value = "INVOKE", 
				target = "Lnet/minecraft/client/renderer/entity/EntityRenderer;render(Lnet/minecraft/world/entity/Entity;FFLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V",
				args = {"log=false"}
				) 
			)
	private void render(
	        Entity pEntity,
	        double pX,
	        double pY,
	        double pZ,
	        float pRotationYaw,
	        float pPartialTicks,
	        PoseStack pPoseStack,
	        MultiBufferSource pBuffer,
	        int pPackedLight,
	        CallbackInfo ci
	        ) {
		
		BeamingData data = BeamingData.get(pEntity);
		
		if (data.isBeaming()) {
		
			Transporter.renderBeam(
					pPoseStack, 
					pBuffer, 
					pEntity, 
					Mth.rotationAroundAxis(
							Mth.Y_AXIS, 
							((EntityRenderDispatcher)(Object)this).cameraOrientation(), 
							new Quaternionf()), 
					data.getBeamProgress() 
					);
		
		}
		
	}
	
}
