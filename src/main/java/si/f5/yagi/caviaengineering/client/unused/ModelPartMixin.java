package si.f5.yagi.caviaengineering.client.unused;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.minecraft.client.model.geom.ModelPart;

@Mixin(ModelPart.class)
public class ModelPartMixin {

	@WrapOperation(
			method = {"render(Lcom/mojang/blaze3d/vertex/PoseStack;Lcom/mojang/blaze3d/vertex/VertexConsumer;III)V"},
			at = @At(value="INVOKE", 
					target="Lnet/minecraft/client/model/geom/ModelPart;compile(Lcom/mojang/blaze3d/vertex/PoseStack$Pose;Lcom/mojang/blaze3d/vertex/VertexConsumer;III)V",
					 args= {"log=true"}
					)
			)
	private void compile(ModelPart modelpart, PoseStack.Pose pose, VertexConsumer vc, int a, int b, int c, Operation<Void> o) {
		
		//model.renderToBuffer(ps, vc, i, color);

		//Nuller.e();
		
		
		
		o.call(modelpart, pose, vc, a, b, 654311423);
		//Lnet/minecraft/client/model/geom/ModelPart;compile(Lcom/mojang/blaze3d/vertex/PoseStack$Pose;Lcom/mojang/blaze3d/vertex/VertexConsumer;III)V
		//Lnet/minecraft/client/model/geom/ModelPart;compile(Lcom/mojang/blaze3d/vertex/PoseStack/Pose;Lcom/mojang/blaze3d/vertex/VertexConsumer;III)V
	}
}
