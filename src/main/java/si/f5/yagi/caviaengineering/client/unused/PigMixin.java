package si.f5.yagi.caviaengineering.client.unused;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.animal.Pig;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level.ExplosionInteraction;
import si.f5.yagi.caviaengineering.Sounds;

@Mixin(Pig.class)
public class PigMixin {
	@ModifyReturnValue(
			at = @At("RETURN"), 
			method = "getHurtSound"
			)
	private SoundEvent getHurtSound(SoundEvent e) {
		//System.out.println("YEEES" + e.getLocation().toString());
		return Sounds.TRANSPORTER_ARRIVAL.get();
	}
//public InteractionResult mobInteract(Player pPlayer, InteractionHand pHand)
	
	@Inject(at = @At("HEAD"), 
			method="mobInteract")
	private void mobInteract(Player pPlayer, InteractionHand pHand, CallbackInfoReturnable<InteractionResult> ci) {
		pPlayer.playSound(Sounds.TRANSPORTER_DEPARTURE.get());
		
		pPlayer.level().explode(pPlayer, pPlayer.getX(), pPlayer.getY(), pPlayer.getZ(), 10f, ExplosionInteraction.MOB);
		
	}
}
