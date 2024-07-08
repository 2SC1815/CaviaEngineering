package si.f5.yagi.caviaengineering.client.unused;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.client.model.Model;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;

@Mixin(Model.class)
public class ModelMixin {

	@Inject(at = @At("RETURN"), 
			method="renderType",
			cancellable = true)
	private void renderType(ResourceLocation rl, CallbackInfoReturnable<RenderType> ci) {
		
		ci.setReturnValue(RenderType.itemEntityTranslucentCull(rl));
		
	}
	
}
