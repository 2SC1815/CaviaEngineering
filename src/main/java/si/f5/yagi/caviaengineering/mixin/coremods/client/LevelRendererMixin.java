package si.f5.yagi.caviaengineering.mixin.coremods.client;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.entity.Entity;
import si.f5.yagi.caviaengineering.client.renderer.TransparencyBufferSource;
import si.f5.yagi.caviaengineering.transporter.BeamingData;

@Mixin(LevelRenderer.class)
public class LevelRendererMixin {
	
	//public static final TransporterBufferSource tbs = new TransporterBufferSource();

	
	@WrapOperation(
			method = "renderLevel", 
			at = @At(value="INVOKE", 
					target="Lnet/minecraft/client/renderer/LevelRenderer;renderEntity(Lnet/minecraft/world/entity/Entity;DDDFLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;)V",
					args= {"log=false"})
			)
	private void render(
			LevelRenderer renderer,
			Entity pEntity, 
			double pCamX, 
			double pCamY, 
			double pCamZ, 
			float pPartialTick, 
			PoseStack pPoseStack, 
			MultiBufferSource pBufferSource, 
			Operation<Void> o
	        ) {
		
		BeamingData data = BeamingData.get(pEntity);
		
		if ( data.isBeaming() &&  pBufferSource instanceof TransparencyBufferSource ) {
		
			((TransparencyBufferSource)pBufferSource).setTransparency(data.getTransparency());
		}
		
		o.call(
				renderer,
				pEntity, 
				pCamX, 
				pCamY, 
				pCamZ, 
				pPartialTick, 
				pPoseStack, 
				pBufferSource);

		if (pBufferSource instanceof TransparencyBufferSource)
			((TransparencyBufferSource)pBufferSource).setTransparency(-1);
        
	}
}
