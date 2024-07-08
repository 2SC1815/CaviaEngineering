package si.f5.yagi.caviaengineering.mixin.coremods.client;

import java.util.SequencedMap;

import org.slf4j.Logger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.blaze3d.vertex.ByteBufferBuilder;
import com.mojang.logging.LogUtils;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.MultiBufferSource.BufferSource;
import si.f5.yagi.caviaengineering.client.renderer.TransparencyBufferSource;
import net.minecraft.client.renderer.RenderBuffers;
import net.minecraft.client.renderer.RenderType;

@Mixin(RenderBuffers.class)
public class RenderBuffersMixin {

    private static final Logger LOGGER = LogUtils.getLogger();
    
	@WrapOperation(
			method = "<init>(I)V", 
			at = @At(
					value="INVOKE", 
					target="Lnet/minecraft/client/renderer/MultiBufferSource;immediateWithBuffers(Ljava/util/SequencedMap;Lcom/mojang/blaze3d/vertex/ByteBufferBuilder;)Lnet/minecraft/client/renderer/MultiBufferSource$BufferSource;",
					args= {"log=false"}
					)
			)
	private BufferSource constructor(SequencedMap<RenderType, ByteBufferBuilder> sm, ByteBufferBuilder bbb, Operation<MultiBufferSource.BufferSource> o) {

		LOGGER.info("Replacing default buffersource with overrided buffersource \"" + TransparencyBufferSource.class.getName() + "\".");
		
		return new TransparencyBufferSource(bbb, sm);
		
	}
	/*
	 * 
		Lnet/minecraft/client/renderer/MultiBufferSource;immediateWithBuffers(Ljava/util/SequencedMap;Lcom/mojang/blaze3d/vertex/ByteBufferBuilder;)Lnet/minecraft/client/renderer/MultiBufferSource$BufferSource;
	 *  Lnet/minecraft/client/renderer/MultiBufferSource;immediateWithBuffers(Ljava/util/SequencedMap;Lcom/mojang/blaze3d/vertex/ByteBufferBuilder;)Lnet/minecraft/client/renderer/MultiBufferSource$BufferSource;
	 */
			
			
}
