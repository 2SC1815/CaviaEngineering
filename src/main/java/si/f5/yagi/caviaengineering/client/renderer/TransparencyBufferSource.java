package si.f5.yagi.caviaengineering.client.renderer;

import java.util.SequencedMap;

import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.ByteBufferBuilder;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat.Mode;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FastColor;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class TransparencyBufferSource extends MultiBufferSource.BufferSource {

	private int transparency = -1;
	
	public TransparencyBufferSource(ByteBufferBuilder bb, SequencedMap<RenderType, ByteBufferBuilder> sm) {
        super(bb, sm);
    }

    @Override
    public VertexConsumer getBuffer(RenderType pRenderType) {

    	if ( this.transparency >= 0 ) {
    		

    		ResourceLocation rl = CompositeRenderTypeAccess.getResourceLocation(pRenderType);
    		

    		if (	rl != null && 
    				!(
    					rl.equals(ItemRenderer.ENCHANTED_GLINT_ITEM)    || 
    					rl.equals(ItemRenderer.ENCHANTED_GLINT_ENTITY)
    				)
    			) {

                //RenderStateShard.ShaderStateShard sss = CompositeRenderTypeAccess.getShaderState(pRenderType);
                //boolean flag = sss.equals(RenderStateShardAccessor.getRT_entity_shadow_shader());
                
	    		pRenderType = RenderType.entityTranslucent(rl);
	    		
		        BufferBuilder bufferbuilder;//= this.startedBuilders.get(pRenderType);
		        
		        //if (bufferbuilder != null) {

	                //return bufferbuilder;
		
		        /*} else*/ if (pRenderType.canConsolidateConsecutiveGeometry()) {
	    		
		            	
	                ByteBufferBuilder bytebufferbuilder = this.fixedBuffers.get(pRenderType);
	                
	                if (bytebufferbuilder != null) {
	                	System.out.println("fixed");
	                    bufferbuilder = new TransparencyBufferBuilder(bytebufferbuilder, pRenderType.mode(), pRenderType.format());
	                    
	                } else {
	                	
                    if (this.lastSharedType != null) {
                        this.endBatch(this.lastSharedType);
                    }

                    
	                    bufferbuilder = new TransparencyBufferBuilder(this.sharedBuffer, pRenderType.mode(), pRenderType.format());
	                    this.lastSharedType = pRenderType;
                    
	                }
	
	                this.startedBuilders.put(pRenderType, bufferbuilder);
	                
	                ((TransparencyBufferBuilder)bufferbuilder).setTransparency(this.transparency);
	                
	                /*if (flag) {

	                	RenderType rt = RenderType.entityCutout(TransportRenderer.RL);
	                	
	                	VertexConsumer a = VertexMultiConsumer.create(this.getBuffer(rt, false), bufferbuilder);
	                	

	                	return a;
	                
	                } else {*/
	                
	                 
		            return bufferbuilder;
	                
	                //}
	                
	                //if (flag) {
	                /*} else {
	                
		                RenderType s = RenderType.entityTranslucent(TransportRenderer.RL);
	
	                    BufferBuilder bufferbuilder2 = new BufferBuilder(this.sharedBuffer, s.mode(), s.format());
		                //((TransparencyBufferBuilder)bufferbuilder2).setTransparency(this.transparency);
	                
	                }*/
		    		
		        }
            
    		}
    	
    	}
    	
    	return super.getBuffer(pRenderType);
    	
    }

    
    public void setTransparency(int value) {
    	this.transparency = value;
    }
    
    
    
    public static class TransparencyBufferBuilder extends BufferBuilder {
        
    	private int transparency = 0;
    	//private boolean glint = false;
    	
		public TransparencyBufferBuilder(ByteBufferBuilder p_350781_, Mode p_350789_, VertexFormat p_350682_/*, boolean glint*/) {
			super(p_350781_, p_350789_, p_350682_);
			//this.glint = glint;
		}
		
		public void setTransparency(int value) {
			this.transparency = value;
		}

	    @Override
	    public void addVertex(
	        float p_350423_,
	        float p_350381_,
	        float p_350383_,
	        int color,
	        float p_350977_,
	        float p_350674_,
	        int p_350816_,
	        int p_350690_,
	        float p_350640_,
	        float p_350490_,
	        float p_350810_
	    ) {
	    	
	    	
	    	super.addVertex(
	    			p_350423_, 
	    			p_350381_, 
	    			p_350383_, 
	    			FastColor.ARGB32.color(FastColor.ARGB32.alpha(color) * this.transparency / 255, color), 
	    			p_350977_, 
	    			p_350674_, 
	    			p_350816_, 
	    			p_350690_, 
	    			p_350640_, 
	    			p_350490_, 
	    			p_350810_);
	    }
    	
    }
    
}

/*


    		if (	rl != null && 
    				!(
    					rl.equals(ItemRenderer.ENCHANTED_GLINT_ITEM)    || 
    					rl.equals(ItemRenderer.ENCHANTED_GLINT_ENTITY)
    				)
    			) {

                RenderStateShard.ShaderStateShard sss = CompositeRenderTypeAccess.getShaderState(pRenderType);
	    		pRenderType = RenderType.entityTranslucent(rl);
	    		
		        BufferBuilder bufferbuilder;//= this.startedBuilders.get(pRenderType);
		        
		        //if (bufferbuilder != null) {

	                //return bufferbuilder;
		
		        /*} else if (pRenderType.canConsolidateConsecutiveGeometry()) {
	    		
		            	
	                ByteBufferBuilder bytebufferbuilder = this.fixedBuffers.get(pRenderType);
	                
	                if (bytebufferbuilder != null) {
	                	System.out.println("fixed");
	                    bufferbuilder = new TransparencyBufferBuilder(bytebufferbuilder, pRenderType.mode(), pRenderType.format());
	                    
	                } else {
	                	
                    if (this.lastSharedType != null) {
                        this.endBatch(this.lastSharedType);
                    }

                	//System.out.println("shared");
                    
                    //System.out.println(pRenderType.format());
                    
                    bufferbuilder = new TransparencyBufferBuilder(this.sharedBuffer, pRenderType.mode(), pRenderType.format());
                    this.lastSharedType = pRenderType;
                    
	                //}
	
	                this.startedBuilders.put(pRenderType, bufferbuilder);
	                
	                ((TransparencyBufferBuilder)bufferbuilder).setTransparency(this.transparency);
	                
	                
	                boolean flag = sss.equals(RenderStateShardAccessor.getRT_entity_shadow_shader());
	                
	                return f || flag ? bufferbuilder : VertexMultiConsumer.create(this.getBufferR( TransportEffectRenderType.ENTITY_GLINT, true), bufferbuilder);
		    		
		        }
            
    		}
    	
    	}
    	
    	return super.getBuffer(pRenderType);
    	
    }


*/