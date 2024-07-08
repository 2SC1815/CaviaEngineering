package si.f5.yagi.caviaengineering.client.renderer;

import java.lang.reflect.Method;

import com.mojang.blaze3d.vertex.VertexFormat;

import net.minecraft.client.renderer.RenderType;

public class RenderTypeAccessor {

	public static RenderType create(String pName, VertexFormat pFormat, VertexFormat.Mode pMode, int pBufferSize, RenderType.CompositeState pState) {
		
		try {
			Method method = RenderType.class.getDeclaredMethod("create", String.class, VertexFormat.class, VertexFormat.Mode.class, int.class, RenderType.CompositeState.class);
			
			method.setAccessible(true);
			
			return (RenderType)method.invoke(RenderType.class, pName, pFormat, pMode, pBufferSize, pState);
			
		} catch (Throwable e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
}
