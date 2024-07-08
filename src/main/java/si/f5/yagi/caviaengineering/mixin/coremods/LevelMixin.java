package si.f5.yagi.caviaengineering.mixin.coremods;

import java.util.function.Consumer;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import si.f5.yagi.caviaengineering.transporter.Transporter;

@Mixin(Level.class)
public class LevelMixin {
	
	@Inject(
			method="guardEntityTick",
			at = @At(
					value="INVOKE",
					target="Ljava/util/function/Consumer;accept(Ljava/lang/Object;)V",
					args= {"log=false"}
					)
				)
	private void TransporterTick(Consumer<Entity> c, Entity e, CallbackInfo ci) {
		
		Transporter.tick(e);
		
        for (Entity entity : e.getPassengers()) {
            this.TransporterTick(c, entity, ci);
        }
		
	}

	
}
