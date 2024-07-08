package si.f5.yagi.caviaengineering.client.unused;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Entity;

@Mixin(Entity.class)
public interface EntityAccessor {

	@Accessor
	public SynchedEntityData getEntityData_();
	
}
