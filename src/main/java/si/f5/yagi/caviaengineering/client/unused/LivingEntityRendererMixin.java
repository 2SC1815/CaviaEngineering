package si.f5.yagi.caviaengineering.client.unused;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

@Mixin(LivingEntityRenderer.class)
public class LivingEntityRendererMixin {
/*
	@ModifyReturnValue(
			at = @At("RETURN"), 
			method = "getRenderType"
			)
	private RenderType getHurtSound(RenderType type) {
		//System.out.println("YEEES" + type);
		return type;
	}*/
/*
	@WrapOperation(
			method = "render", 
			at = @At(value="INVOKE", 
					target="Lnet/minecraft/client/renderer/entity/LivingEntityRenderer;getRenderType(Lnet/minecraft/world/entity/LivingEntity;ZZZ)Lnet/minecraft/client/renderer/RenderType;")
			)
	private RenderType getRenderType(LivingEntityRenderer<LivingEntity, EntityModel<LivingEntity>> ler, LivingEntity pEntity, boolean a, boolean b, boolean c, Operation<RenderType> o) {
		//System.out.println("Yass " + pEntity.toString());
        ResourceLocation resourcelocation = ler.getTextureLocation(pEntity);
		return RenderType.itemEntityTranslucentCull(resourcelocation);
	}*/
	/*
	@WrapOperation(
			method = "render", 
			at = @At(value="INVOKE", 
					target="Lnet/minecraft/client/model/EntityModel;renderToBuffer(Lcom/mojang/blaze3d/vertex/PoseStack;Lcom/mojang/blaze3d/vertex/VertexConsumer;III)V"
					)
			)
	private void renderToBuffer(EntityModel<LivingEntity> model, PoseStack ps, VertexConsumer vc, int packedLight, int i, int color, Operation<Void> o) {
		
		//model.renderToBuffer(ps, vc, i, color);
		
		o.call(model, ps, vc, packedLight, i, 654311423);
		
	}*/
	
	@ModifyReturnValue(
			at = @At(value="RETURN", args= {"log=true"}), 
			method = "getRenderType"
			)
	private RenderType getRenderType(RenderType type, @Local ResourceLocation rl) {
		//System.out.println("YEEES" + type);
		return RenderType.itemEntityTranslucentCull(rl);
	}
	
	
	@WrapOperation(
			method = "render", 
			at = @At(value="INVOKE", 
					target="Lnet/minecraft/client/renderer/entity/layers/RenderLayer;render(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/world/entity/Entity;FFFFFF)V"
					, args= {"log=true"})
			)
	private void render(
			RenderLayer<Entity, EntityModel<Entity>> layer,
	        PoseStack pPoseStack,
	        MultiBufferSource pBuffer,
	        int pPackedLight,
	        Entity pEntity,
	        float pLimbSwing,
	        float pLimbSwingAmount,
	        float pPartialTick,
	        float pAgeInTicks,
	        float pNetHeadYaw,
	        float pHeadPitch, Operation<Void> o,
	        @Local RenderType renderType,
	        @Local EntityModel<Entity> model
	        ) {

        
		if (renderType != null) {
			int i = LivingEntityRenderer.getOverlayCoords((LivingEntity)pEntity, pPartialTick);
			model.renderToBuffer(pPoseStack, pBuffer.getBuffer(renderType), i, 654311423);
		}
		
		o.call(layer, pPoseStack, pBuffer, pPackedLight, pEntity, pLimbSwing, pLimbSwingAmount, pPartialTick, pAgeInTicks, pNetHeadYaw, pHeadPitch);
		
	}
	
	/*Lnet/minecraft/client/renderer/entity/layers/RenderLayer;render(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/world/entity/Entity;FFFFFF)V
	 *Lnet/minecraft/client/renderer/entity/layers/RenderLayer;render(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/world/entity/LivingEntity;FFFFFF)V
	 * 
	@Inject(
			method = "render", 
			at = @At(value="INVOKE", 
					target="Lnet/minecraft/client/model/EntityModel;renderToBuffer(Lcom/mojang/blaze3d/vertex/PoseStack;Lcom/mojang/blaze3d/vertex/VertexConsumer;III)V",
					args= {"log=true"})
			)
	private void renderToBuffer(LivingEntity pEntity, float pEntityYaw, float pPartialTicks, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, CallbackInfo ci) {
		
		
		
	}
	
	
	 * target="Lnet/minecraft/client/model/Model;renderToBuffer(Lcom/mojang/blaze3d/vertex/PoseStack;Lcom/mojang/blaze3d/vertex/VertexConsumer;III)V")
			)
			Lnet/minecraft/client/model/      Model;renderToBuffer(Lcom/mojang/blaze3d/vertex/PoseStack;Lcom/mojang/blaze3d/vertex/VertexConsumer;III)V
			Lnet/minecraft/client/model/EntityModel;renderToBuffer(Lcom/mojang/blaze3d/vertex/PoseStack;Lcom/mojang/blaze3d/vertex/VertexConsumer;III)V
	 * 
	 * net.minecraft.client.renderer.entity.LivingEntityRenderer.render(T, float, float, PoseStack, MultiBufferSource, int)
	 * 
	@Inject(method = "render", at = @At(
			value = "INVOKE_STRING",
			target = "Lnet/minecraft/util/profiling/ProfilerFiller;popPush(Ljava/lang/String;)V",
			args = {"ldc=destroyProgress"}
	))//void net.minecraft.client.model.Model.renderToBuffer(PoseStack p_103111_, VertexConsumer p_103112_, int p_103113_, int p_103114_, int p_350308_)
	public void render(
			LivingEntity pEntity, float pEntityYaw, float pPartialTicks, 
			PoseStack pPoseStack, MultiBufferSource pBuffer, 
			int pPackedLight, CallbackInfo ci
	)
	{
		//VertexBufferHolder.afterTERRendering();
		System.out.println("Hello world " + pEntity.getClass().getName());
	}*/
	
}
