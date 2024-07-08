package si.f5.yagi.caviaengineering.client.unused;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderStateShard.ShaderStateShard;

@Mixin(RenderStateShard.class)
public interface RenderStateShardAccessor {
	
	@Accessor("")
	public ShaderStateShard getRT_entity_shadow_shader();
	
}
