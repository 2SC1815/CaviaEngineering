package si.f5.yagi.caviaengineering.client.renderer;

import java.lang.reflect.Field;
import java.util.Optional;

import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderStateShard.ShaderStateShard;
import net.minecraft.client.renderer.RenderStateShard.TextureStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderType.CompositeState;
import net.minecraft.resources.ResourceLocation;

/*
 *  THIS IS NOT A MIXIN CLASS!
 */
public class CompositeRenderTypeAccess {

    //private static final Logger LOGGER = LogUtils.getLogger();
    
	//private static final String INNER_CLASS_NAME = "CompositeRenderType";
	private static final String COMPOSITE_STATE_FIELD_NAME = "state";
	
	private static final String TEXTURE_STATE = "textureState";
	private static final String SHADER_STATE = "shaderState";
	private static final String TEXTURE_FIELD = "texture";

	
	
	public static ResourceLocation getResourceLocation(RenderType rt) {
		
		try {
			
			return getResourceLocation( getCompositeState(rt) );
			
		} catch (Throwable e) {
			//LOGGER.error("Failed to access ResourceLocation of RenderType$" + INNER_CLASS_NAME + ".");
			//e.printStackTrace();
		}
		
		return null;
	}
	
	private static RenderType.CompositeState getCompositeState(RenderType rt) throws ClassNotFoundException, NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		
		Class<? extends RenderType> innerclazz = rt.getClass();
		
		Object innerObject = innerclazz.cast(rt);
		
		Field field = innerclazz.getDeclaredField(COMPOSITE_STATE_FIELD_NAME);
		
		field.setAccessible(true);
		
		return (CompositeState) field.get(innerObject);
		
	}
	
	private static ResourceLocation getResourceLocation(CompositeState cs) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		
		
		Field field1 = cs.getClass().getDeclaredField(TEXTURE_STATE);
		
		field1.setAccessible(true);
		
		RenderStateShard.TextureStateShard tss = (TextureStateShard) field1.get(cs);
		
		Field field2 = TextureStateShard.class.getDeclaredField(TEXTURE_FIELD);
		
		field2.setAccessible(true);
		
		@SuppressWarnings("unchecked")
		Optional<ResourceLocation> orl = (Optional<ResourceLocation>) field2.get(tss);
		
		return orl.get();
	}

	public static ShaderStateShard getShaderState(RenderType rt) {
		
		try {
			
			CompositeState cs = getCompositeState(rt);
			
			Field field1 = cs.getClass().getDeclaredField(SHADER_STATE);
			
			field1.setAccessible(true);
			
			RenderStateShard.ShaderStateShard tss = (ShaderStateShard) field1.get(cs);
			
			return tss;
			
		} catch(Throwable e) {}
		
		return null;
	}
	
}
