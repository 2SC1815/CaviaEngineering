package si.f5.yagi.caviaengineering.client.unused;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.commands.CommandSource;
import net.minecraft.network.syncher.SyncedDataHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.entity.EntityAccess;
import net.minecraft.world.scores.ScoreHolder;
import si.f5.yagi.caviaengineering.transporter.Transporter;

@Mixin(Entity.class)
public abstract class EntityMixin extends net.neoforged.neoforge.attachment.AttachmentHolder implements SyncedDataHolder, EntityAccess, CommandSource, ScoreHolder, net.neoforged.neoforge.common.extensions.IEntityExtension {

    
	/*@Inject(
			method="baseTick",
			at = @At(
				value="INVOKE",
				target="Lnet/minecraft/world/entity/Entity;updateInWaterStateAndDoFluidPushing()Z",
				args={"log=false"}
				)
			)*/
	@Inject(
			method="tick",
			at = @At("HEAD")
				)
	private void TransporterTick(CallbackInfo ci) {
		
		Transporter.tick((Entity)(Object)this);
		
	}


/*

	@Inject(
			method="<init>",
			at = @At(
				value="INVOKE",
				target="Lnet/minecraft/network/syncher/SynchedEntityData$Builder;build()Lnet/minecraft/network/syncher/SynchedEntityData;",
				args={"log=true"}
				)
			)
	private void dataDefine(CallbackInfo ci, @Local SynchedEntityData.Builder builder) {
		
		TransporterPlatform.set();
		
        builder.define(TransporterPlatform.TEST, 0xFF);
		
	}
	*/
	

}
