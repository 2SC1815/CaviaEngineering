package si.f5.yagi.caviaengineering.client.entity;

import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;
import si.f5.yagi.caviaengineering.CaviaEngineering;
import si.f5.yagi.caviaengineering.entity.animal.GuineaPig;
import si.f5.yagi.caviaengineering.entity.animal.GuineaPig.Pattern;

public class GuineaPigPatternLayer extends RenderLayer<GuineaPig, GuineaPigModel<GuineaPig>> {
	
    private final ResourceLocation[] PATTERNS;

    private static ResourceLocation[] setupPatterns(String suffix) {
    	GuineaPig.Pattern[] patterns = GuineaPig.Pattern.values();
    	ResourceLocation set[] = new ResourceLocation[patterns.length];
    	for (Pattern p : GuineaPig.Pattern.values()) {
    		String texture = p.getTextureName();
    		set[p.ordinal()] = (texture == null || ((suffix == "b") && !p.isAvailableB())) ? null : ResourceLocation.fromNamespaceAndPath(CaviaEngineering.MODID, 
    				"textures/entity/guineapig/guineapig_" + texture + "_" + suffix + ".png");
    	}
    	return set;
    }
    
    private final GuineaPigModel<GuineaPig> model;
    private final String suffix;
    
	public GuineaPigPatternLayer(RenderLayerParent<GuineaPig, GuineaPigModel<GuineaPig>> pRenderer, EntityModelSet pModelSet, String suffix) {
		super(pRenderer);
		this.PATTERNS = setupPatterns(suffix);
		this.suffix = suffix;
        this.model = new GuineaPigModel<>(pModelSet.bakeLayer(CaviaEngineering.ClientModEvents.GUINEAPIG_LAYER));
	}

	@Override
	public void render(
			PoseStack pPoseStack, 
			MultiBufferSource pBuffer, 
			int pPackedLight, 
			GuineaPig pLivingEntity,
			float pLimbSwing,
			float pLimbSwingAmount,
			float pPartialTick, 
			float pAgeInTicks, 
			float pNetHeadYaw,
			float pHeadPitch
			) {

		ResourceLocation texture = PATTERNS[pLivingEntity.getPattern().ordinal()];
        
		if (texture != null) {
	        coloredCutoutModelCopyLayerRender(
	            this.getParentModel(),
	            this.model,
	            texture,
	            pPoseStack,
	            pBuffer,
	            pPackedLight,
	            pLivingEntity,
	            pLimbSwing,
	            pLimbSwingAmount,
	            pAgeInTicks,
	            pNetHeadYaw,
	            pHeadPitch,
	            pPartialTick,
	            ((this.suffix == "b") ? pLivingEntity.getPatternColorB() : pLivingEntity.getPatternColorA()).getColor()
	        );
		}
		
	}

}
