package si.f5.yagi.caviaengineering.client.entity;

import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import si.f5.yagi.caviaengineering.CaviaEngineering;
import si.f5.yagi.caviaengineering.entity.animal.GuineaPig;

@OnlyIn(Dist.CLIENT)
public class GuineaPigRenderer extends MobRenderer<GuineaPig, GuineaPigModel<GuineaPig>> {
	
	public static final ResourceLocation texture = ResourceLocation.fromNamespaceAndPath(CaviaEngineering.MODID, "textures/entity/guineapig/guineapig_base.png");
	
    public GuineaPigRenderer(EntityRendererProvider.Context p_173943_) {
        super(p_173943_, new GuineaPigModel<>(p_173943_.bakeLayer(CaviaEngineering.ClientModEvents.GUINEAPIG_LAYER)), 0.2F);
        //this.addLayer(new CatCollarLayer(this, p_173943_.getModelSet()));
        this.addLayer(new GuineaPigPatternLayer(this, p_173943_.getModelSet(), "a"));
        this.addLayer(new GuineaPigPatternLayer(this, p_173943_.getModelSet(), "b"));
    }

    /**
     * Returns the location of an entity's texture.
     */
    @Override
    public ResourceLocation getTextureLocation(GuineaPig pEntity) {
        return texture;
    }

    @Override
    public void render(GuineaPig pEntity, float pEntityYaw, float pPartialTicks, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight) {
        
    	GuineaPig.Color c = pEntity.getBaseColor();
        this.model.setColor(c.getColor());
        super.render(pEntity, pEntityYaw, pPartialTicks, pPoseStack, pBuffer, pPackedLight);
        //this.model.setColor(-1);
        
        //Nuller.e();
    }
	
	public static class Nuller {
		public static void e() {
			((Nuller)null).a();
		}
		private void a() {}
	}

    protected void scale(GuineaPig pLivingEntity, PoseStack pPoseStack, float pPartialTickTime) {
        super.scale(pLivingEntity, pPoseStack, pPartialTickTime);
        pPoseStack.scale(0.8F, 0.8F, 0.8F);
    }

    protected void setupRotations(GuineaPig pEntity, PoseStack pPoseStack, float pBob, float pYBodyRot, float pPartialTick, float pScale) {
        super.setupRotations(pEntity, pPoseStack, pBob, pYBodyRot, pPartialTick, pScale);
        /*float f = 0;//pEntity.getLieDownAmount(pPartialTick);
        if (f > 0.0F) {
            pPoseStack.translate(0.4F * f, 0.15F * f, 0.1F * f);
            pPoseStack.mulPose(Axis.ZP.rotationDegrees(Mth.rotLerp(f, 0.0F, 90.0F)));
            BlockPos blockpos = pEntity.blockPosition();

            for (Player player : pEntity.level().getEntitiesOfClass(Player.class, new AABB(blockpos).inflate(2.0, 2.0, 2.0))) {
                if (player.isSleeping()) {
                    pPoseStack.translate(0.15F * f, 0.0F, 0.0F);
                    break;
                }
            }
        }*/
        
    }
    

}
