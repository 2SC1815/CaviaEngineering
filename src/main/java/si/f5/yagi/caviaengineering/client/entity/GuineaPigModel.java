package si.f5.yagi.caviaengineering.client.entity;


import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.minecraft.client.model.AgeableListModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.FastColor;
import net.minecraft.util.Mth;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import si.f5.yagi.caviaengineering.entity.animal.GuineaPig;

@OnlyIn(Dist.CLIENT)
public class GuineaPigModel<T extends GuineaPig> extends AgeableListModel<T> {
	
    /*private static final int CROUCH_STATE = 0;
    private static final int WALK_STATE = 1;
    private static final int SPRINT_STATE = 2;*/
    protected static final int SITTING_STATE = 3;
    
    /*private static final float XO = 0.0F;
    private static final float YO = 16.0F;
    private static final float ZO = -9.0F;
    private static final float HEAD_WALK_Y = 15.0F;
    private static final float HEAD_WALK_Z = -9.0F;
    private static final float BODY_WALK_Y = 12.0F;
    private static final float BODY_WALK_Z = -10.0F;
    private static final float TAIL_1_WALK_Y = 15.0F;
    private static final float TAIL_1_WALK_Z = 8.0F;
    private static final float TAIL_2_WALK_Y = 20.0F;
    private static final float TAIL_2_WALK_Z = 14.0F;*/
    protected static final float BACK_LEG_Y = 18.0F;
    protected static final float BACK_LEG_Z = 5.0F;
    protected static final float FRONT_LEG_Y = 14.1F;
    /*private static final float FRONT_LEG_Z = -5.0F;
    private static final String TAIL_1 = "tail1";
    private static final String TAIL_2 = "tail2";*/
    
    protected final ModelPart leftHindLeg;
    protected final ModelPart rightHindLeg;
    protected final ModelPart leftFrontLeg;
    protected final ModelPart rightFrontLeg;
    protected final ModelPart head;
    protected final ModelPart body;
    protected final ModelPart rightEar;
    protected final ModelPart leftEar;
    
    protected int state = 1;
    private int color = -1;

    public GuineaPigModel(ModelPart pRoot) {
        super(true, 9.0F, 1.5F);
        this.head = pRoot.getChild("head");
        this.body = pRoot.getChild("body");
        
        this.leftHindLeg = pRoot.getChild("left_hind_leg");
        this.rightHindLeg = pRoot.getChild("right_hind_leg");
        this.leftFrontLeg = pRoot.getChild("left_front_leg");
        this.rightFrontLeg = pRoot.getChild("right_front_leg");
        
        this.rightEar = this.head.getChild("right_ear");
        this.leftEar = this.head.getChild("left_ear");
        
    }

    public static MeshDefinition createBodyMesh(CubeDeformation pCubeDeformation) {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();
        
        

        PartDefinition head = partdefinition.addOrReplaceChild(
            "head",
            CubeListBuilder.create().texOffs(16, 0)
            .addBox("main", -2.0F, -0.0F, -4.0F, 3.0F, 3.0F, 4.0F, pCubeDeformation),
            PartPose.offset(0.5F, 18.0F, -2.5F)
        );
        
        head.addOrReplaceChild(
    		"right_ear",
            CubeListBuilder.create().texOffs(0, 0).addBox(0.0F, 0.0F, 0.0F, 0.01F, 1.0F, 1.0F, pCubeDeformation),
            PartPose.offsetAndRotation(-2F, 0F, -2F, 0.0F, 0.0F, (float) (Math.PI / 8))
        );
        
        head.addOrReplaceChild(
    		"left_ear",
            CubeListBuilder.create().texOffs(0, 1).addBox(0.0F, 0.0F, 0.0F, 0.01F, 1.0F, 1.0F, pCubeDeformation),
            PartPose.offsetAndRotation(1F, 0F, -2F, 0.0F, 0.0F, (float) (-Math.PI / 8))
        );

        partdefinition.addOrReplaceChild(
            "body",
            CubeListBuilder.create().texOffs(0, 0).addBox(-2.0F, -1.0F, -5F, 4.0F, 7.0F, 4.0F, pCubeDeformation),
            PartPose.offsetAndRotation(-0F, 18.0F, -2.5F, (float) (Math.PI / 2), 0.0F, 0.0F)
        );
        
        partdefinition.addOrReplaceChild(
        		"left_hind_leg", 
        		CubeListBuilder.create().texOffs(18, 11).addBox(-0.5F, 0.0F, -2.0F, 1.0F, 1.0F, 2.0F, pCubeDeformation)
        		, PartPose.offset(1.1F, 23.0F, 3.0F));
        
        partdefinition.addOrReplaceChild(
        		"right_hind_leg", 
        		CubeListBuilder.create().texOffs(12, 11).addBox(-0.5F, 0.0F, -2.0F, 1.0F, 1.0F, 2.0F, pCubeDeformation)
        		, PartPose.offset(-1.1F, 23.0F, 3.0F));
        
        partdefinition.addOrReplaceChild(
        		"left_front_leg", 
        		CubeListBuilder.create().texOffs(6, 11).addBox(-0.5F, 0.0F, -2.0F, 1.0F, 1.0F, 2.0F, pCubeDeformation)
        		, PartPose.offset(1.2F, 23.0F, -2.0F));
        
        partdefinition.addOrReplaceChild(
        		"right_front_leg", 
        		CubeListBuilder.create().texOffs(0, 11).addBox(-0.5F, 0.0F, -2.0F, 1.0F, 1.0F, 2.0F, pCubeDeformation)
        		, PartPose.offset(-1.2F, 23.0F, -2.0F));

        
        return meshdefinition;
    }
    

    public void setColor(int p_351056_) {
        this.color = p_351056_;
    }

    @Override
    public void renderToBuffer(PoseStack p_170506_, VertexConsumer p_170507_, int p_170508_, int p_170509_, int p_350900_) {
        super.renderToBuffer(p_170506_, p_170507_, p_170508_, p_170509_, FastColor.ARGB32.multiply(p_350900_, this.color));
    }
    
    @Override
    protected Iterable<ModelPart> headParts() {
        return ImmutableList.of(this.head);
    }

    @Override
    protected Iterable<ModelPart> bodyParts() {
        return ImmutableList.of(this.body, this.leftHindLeg, this.rightHindLeg, this.leftFrontLeg, this.rightFrontLeg);//, this.tail1, this.tail2);
    }

    /**
     * Sets this entity's model rotation angles
     */
    @Override
    public void setupAnim(T pEntity, float pLimbSwing, float pLimbSwingAmount, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) {
        this.head.xRot = pHeadPitch * (float) (Math.PI / 180.0);
        this.head.yRot = pNetHeadYaw * (float) (Math.PI / 180.0);
        
        this.body.xRot = (float) (Math.PI / 2);
        if (this.state == 2) {
            this.leftHindLeg.xRot = Mth.cos(pLimbSwing * 0.6662F * 3) * pLimbSwingAmount;
            this.rightHindLeg.xRot = Mth.cos(pLimbSwing * 0.6662F * 3 + 0.3F) * pLimbSwingAmount;
            this.leftFrontLeg.xRot = Mth.cos(pLimbSwing * 0.6662F * 3 + (float) Math.PI + 0.3F) * pLimbSwingAmount;
            this.rightFrontLeg.xRot = Mth.cos(pLimbSwing * 0.6662F * 3 + (float) Math.PI) * pLimbSwingAmount;
            //this.tail2.xRot = 1.7278761F + (float) (Math.PI / 10) * Mth.cos(pLimbSwing) * pLimbSwingAmount;
        } else {
            this.leftHindLeg.xRot = Mth.cos(pLimbSwing * 0.6662F * 3) * pLimbSwingAmount;
            this.rightHindLeg.xRot = Mth.cos(pLimbSwing * 0.6662F * 3 + (float) Math.PI) * pLimbSwingAmount;
            this.leftFrontLeg.xRot = Mth.cos(pLimbSwing * 0.6662F * 3 + (float) Math.PI) * pLimbSwingAmount;
            this.rightFrontLeg.xRot = Mth.cos(pLimbSwing * 0.6662F * 3) * pLimbSwingAmount;
            /*if (this.state == 1) {
                this.tail2.xRot = 1.7278761F + (float) (Math.PI / 4) * Mth.cos(pLimbSwing) * pLimbSwingAmount;
            } else {
                this.tail2.xRot = 1.7278761F + 0.47123894F * Mth.cos(pLimbSwing) * pLimbSwingAmount;
            }*/
        }
        
    }

    @Override
    public void prepareMobModel(T pEntity, float pLimbSwing, float pLimbSwingAmount, float pPartialTick) {
        /*this.body.y = 0.0F;
        this.body.z = 0.0F;
        this.head.y = 0.0F;
        this.head.z = 0.0F;   */     
        
        /*this.body.y = 15.0F;
        this.body.z = -11.5F;
        this.head.y = 15.0F;
        this.head.z = -8.0F;*/
        
        /*this.leftFrontLeg.y = 20.0F;
        this.leftFrontLeg.z = -5.0F;
        this.rightFrontLeg.y = 18.0F;
        this.rightFrontLeg.z = -5.0F;
        this.leftHindLeg.y = 18.0F;
        this.leftHindLeg.z = 5.0F;
        this.rightHindLeg.y = 18.0F;
        this.rightHindLeg.z = 5.0F;*/
        
        /*this.tail1.y = 15.0F;
        this.tail1.z = 8.0F;
        this.tail2.y = 20.0F;
        this.tail2.z = 14.0F;
        this.leftFrontLeg.y = 14.1F;
        this.leftFrontLeg.z = -5.0F;
        this.rightFrontLeg.y = 14.1F;
        this.rightFrontLeg.z = -5.0F;
        this.leftHindLeg.y = 18.0F;
        this.leftHindLeg.z = 5.0F;
        this.rightHindLeg.y = 18.0F;
        this.rightHindLeg.z = 5.0F;
        //this.tail1.xRot = 0.9F;
        /*
        if (pEntity.isCrouching()) {
            this.body.y++;
            this.head.y += 2.0F;/*
            this.tail1.y++;
            this.tail2.y += -4.0F;
            this.tail2.z += 2.0F;
            this.tail1.xRot = (float) (Math.PI / 2);
            this.tail2.xRot = (float) (Math.PI / 2);
            this.state = 0;
        } else if (pEntity.isSprinting()) {/*
            this.tail2.y = this.tail1.y;
            this.tail2.z += 2.0F;
            this.tail1.xRot = (float) (Math.PI / 2);
            this.tail2.xRot = (float) (Math.PI / 2);
        
            this.state = 2;
        } else {
            this.state = 1;
        }*/
    }
}

/*
@OnlyIn(Dist.CLIENT)
public class GuineaPigModel <T extends GuineaPig> extends OcelotModel<T> {
    private float lieDownAmount;
    private float lieDownAmountTail;
    private float relaxStateOneAmount;

    public GuineaPigModel(ModelPart pRoot) {
        super(pRoot);
    }

    public void prepareMobModel(T pEntity, float pLimbSwing, float pLimbSwingAmount, float pPartialTick) {
        this.lieDownAmount = 0;//pEntity.getLieDownAmount(pPartialTick);
        this.lieDownAmountTail = 0;//pEntity.getLieDownAmountTail(pPartialTick);
        this.relaxStateOneAmount = 0;//pEntity.getRelaxStateOneAmount(pPartialTick);
        if (this.lieDownAmount <= 0.0F) {
            this.head.xRot = 0.0F;
            this.head.zRot = 0.0F;
            this.leftFrontLeg.xRot = 0.0F;
            this.leftFrontLeg.zRot = 0.0F;
            this.rightFrontLeg.xRot = 0.0F;
            this.rightFrontLeg.zRot = 0.0F;
            this.rightFrontLeg.x = -1.2F;
            this.leftHindLeg.xRot = 0.0F;
            this.rightHindLeg.xRot = 0.0F;
            this.rightHindLeg.zRot = 0.0F;
            this.rightHindLeg.x = -1.1F;
            this.rightHindLeg.y = 18.0F;
        }

        super.prepareMobModel(pEntity, pLimbSwing, pLimbSwingAmount, pPartialTick);
        if (pEntity.isInSittingPose()) {
            this.body.xRot = (float) (Math.PI / 4);
            this.body.y += -4.0F;
            this.body.z += 5.0F;
            this.head.y += -3.3F;
            this.head.z++;
            this.tail1.y += 8.0F;
            this.tail1.z += -2.0F;
            this.tail2.y += 2.0F;
            this.tail2.z += -0.8F;
            this.tail1.xRot = 1.7278761F;
            this.tail2.xRot = 2.670354F;
            this.leftFrontLeg.xRot = (float) (-Math.PI / 20);
            this.leftFrontLeg.y = 16.1F;
            this.leftFrontLeg.z = -7.0F;
            this.rightFrontLeg.xRot = (float) (-Math.PI / 20);
            this.rightFrontLeg.y = 16.1F;
            this.rightFrontLeg.z = -7.0F;
            this.leftHindLeg.xRot = (float) (-Math.PI / 2);
            this.leftHindLeg.y = 21.0F;
            this.leftHindLeg.z = 1.0F;
            this.rightHindLeg.xRot = (float) (-Math.PI / 2);
            this.rightHindLeg.y = 21.0F;
            this.rightHindLeg.z = 1.0F;
            this.state = 3;
        }
    }

    /**
     * Sets this entity's model rotation angles
     *
    public void setupAnim(T pEntity, float pLimbSwing, float pLimbSwingAmount, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) {
        super.setupAnim(pEntity, pLimbSwing, pLimbSwingAmount, pAgeInTicks, pNetHeadYaw, pHeadPitch);
        if (this.lieDownAmount > 0.0F) {
            this.head.zRot = ModelUtils.rotlerpRad(this.head.zRot, -1.2707963F, this.lieDownAmount);
            this.head.yRot = ModelUtils.rotlerpRad(this.head.yRot, 1.2707963F, this.lieDownAmount);
            this.leftFrontLeg.xRot = -1.2707963F;
            this.rightFrontLeg.xRot = -0.47079635F;
            this.rightFrontLeg.zRot = -0.2F;
            this.rightFrontLeg.x = -0.2F;
            this.leftHindLeg.xRot = -0.4F;
            this.rightHindLeg.xRot = 0.5F;
            this.rightHindLeg.zRot = -0.5F;
            this.rightHindLeg.x = -0.3F;
            this.rightHindLeg.y = 20.0F;
            this.tail1.xRot = ModelUtils.rotlerpRad(this.tail1.xRot, 0.8F, this.lieDownAmountTail);
            this.tail2.xRot = ModelUtils.rotlerpRad(this.tail2.xRot, -0.4F, this.lieDownAmountTail);
        }

        if (this.relaxStateOneAmount > 0.0F) {
            this.head.xRot = ModelUtils.rotlerpRad(this.head.xRot, -0.58177644F, this.relaxStateOneAmount);
        }
    }
}*/
