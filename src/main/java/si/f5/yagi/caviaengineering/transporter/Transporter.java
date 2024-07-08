package si.f5.yagi.caviaengineering.transporter;

import org.joml.Matrix4f;
import org.joml.Quaternionf;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.BufferUploader;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.VertexFormat;

import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Leashable;
import net.minecraft.world.level.Level;
import si.f5.yagi.caviaengineering.Attachments;
import si.f5.yagi.caviaengineering.CaviaEngineering;
import si.f5.yagi.caviaengineering.Sounds;

public class Transporter {
	
	public static final ResourceLocation BEAM = CaviaEngineering.rl("textures/transporter_beam.png");

    //public static final Material STREAM = new Material(TextureAtlas.LOCATION_BLOCKS, ResourceLocation.fromNamespaceAndPath(CaviaEngineering.MODID, "textures/transporter_beam.png"));
	public static final ResourceLocation STREAM = CaviaEngineering.rl("textures/matter_stream.png");

	public static void beaming(Entity e, BlockPos pos) {
		beaming(e, pos, 0);
	}
	
	
	public static void beaming(Entity target, BlockPos pos, int delay) {
		
		energize(target, pos, delay);
		
        target.getSelfAndPassengers().forEach(e -> {

            for (Entity entity : e.getPassengers()) {
            	energize(entity, pos, delay);
            }
    		
        });
        
	}
	
	@SuppressWarnings("resource")
	private static void energize(Entity target, BlockPos pos, int delay) {
		
		BeamingData t = BeamingData.get(target);
		t.setDestinationWithDelay(delay, pos);
		
		if (!target.level().isClientSide) {
			BeamingPacket.beaming(t, target);
		}
		
	}
	
	public static void beaming(BeamingPacket packet, Level level) {
		
		if (level.isClientSide()) {
			Entity e = level.getEntity(packet.id());
			if (e != null) {
				BeamingData.get(e).loadTag(packet.tag());
			}
		}
		
	}
	

	public static void tick(Entity e) {
		
		
		BeamingData data = e.getData(Attachments.BEAMING.get());
		
		int timer = data.getTimer();
		
		if ( timer > 0 ) {
			

			BlockPos pos = data.getDestination();
		
			if ( timer == BeamingData.TIMER_INIT ) {
				
				// start of transport process
				
				e.invulnerableTime = BeamingData.TIMER_INIT;
				
				e.level().playSound(null, e.blockPosition(), Sounds.transporterDeparture(), SoundSource.NEUTRAL, 1.0f, 1.0f);
				
			} else if (pos != null) {
				if( timer == BeamingData.TIMER_INIT / 2 ) {
					
					
					// actual transporting
				
					if (e instanceof Leashable) {
						((Leashable)e).dropLeash(true, true);
					}
					
					if (e.isVehicle()) {
						
						BeamingData passenger = BeamingData.get(e.getFirstPassenger());

						if ((!passenger.isBeaming()) || passenger.getProgress() < data.getProgress()) {
							
						}
						
					}
					
					
					if (e.isPassenger()) {
						BeamingData vehicle = BeamingData.get(e.getVehicle());
						
						System.out.println(vehicle.isBeaming() + " " + vehicle.getTimer() + " " + BeamingData.TIMER_INIT / 2);
						
						if ((!vehicle.isBeaming()) || vehicle.getProgress() > data.getProgress()) {
							e.stopRiding();
							System.out.println("unko");
						}
					}
						
					if (!e.isPassenger()) {
						
						e.setDeltaMovement(0, 0, 0);
						
						e.resetFallDistance();
			            
						e.teleportTo(
								pos.getX(), 
								pos.getY(), 
								pos.getZ()
								);
					}
					
					
					
				} else if (timer == (BeamingData.TIMER_INIT / 2) - 1) {
					
					// arrival to destination
	
					e.level().playSound(null, pos, Sounds.transporterArrival(), SoundSource.NEUTRAL, 1.0f, 1.0f);
					
					data.setDestination(null);
				
				}
			}
			
			data.decrementTimer();
			
		}
		
	}
	

    public static void renderBeam(PoseStack pPoseStack, MultiBufferSource pBuffer, Entity pEntity, Quaternionf pQuaternion, float x) {

        pPoseStack.pushPose();
        float f = pEntity.getBbWidth() * 1.4F;
        float f3 = pEntity.getBbHeight();
        pPoseStack.scale(f, f3, f);
        float f1 = 0.5F;
        //float f2 = 0.0F;
        float f4 = 0.0F;
        pPoseStack.mulPose(pQuaternion);
        pPoseStack.translate(0.0F, 0.0F, 0.3F - (float)((int)f3) * 0.02F);
        float f5 = 0.0F;

        VertexConsumer vertexconsumer = pBuffer.getBuffer(RenderType.entityTranslucentEmissive(BEAM));

        PoseStack.Pose posestack$pose = pPoseStack.last();
        

        float f6 = 0;//textureatlassprite2.getU0();
        float f7 = x;//textureatlassprite2.getV0();
        float f8 = 1;//textureatlassprite2.getU1();
        float f9 = x + (1.0f/64.0f);//textureatlassprite2.getV1();

        beamVertex(posestack$pose, vertexconsumer, -f1 - 0.0F, 0.0F - f4, f5, f8, f9);
        beamVertex(posestack$pose, vertexconsumer, f1 - 0.0F, 0.0F - f4, f5, f6, f9);
        beamVertex(posestack$pose, vertexconsumer, f1 - 0.0F, 1.4F - f4, f5, f6, f7);
        beamVertex(posestack$pose, vertexconsumer, -f1 - 0.0F, 1.4F - f4, f5, f8, f7);
            

        pPoseStack.popPose();
    }

    private static void beamVertex(
        PoseStack.Pose pMatrixEntry, VertexConsumer pBuffer, float pX, float pY, float pZ, float pTexU, float pTexV
    ) {
        pBuffer.addVertex(pMatrixEntry, pX, pY, pZ)
            .setColor(-1)
            .setUv(pTexU, pTexV)
            .setUv1(0, 10)
            .setLight(240)
            .setNormal(pMatrixEntry, 0.0F, 1.0F, 0.0F);
    }
    

    public static void renderStream(BeamingData data, PoseStack pPoseStack) {
        RenderSystem.setShaderTexture(0, STREAM);
        RenderSystem.setShader(GameRenderer::getPositionTexColorShader);
        
        float t = (float)(0xFF - data.getTransparency()) / (float)0xFF;
        float x = data.getProgress();

        float f6 = 0;//textureatlassprite2.getU0();
        float f7 = x;//textureatlassprite2.getV0();
        float f8 = 1;//textureatlassprite2.getU1();
        float f9 = x + (1.0f/64.0f);//textureatlassprite2.getV1();
        
        Matrix4f matrix4f = pPoseStack.last().pose();
        BufferBuilder bufferbuilder = Tesselator.getInstance().begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX_COLOR);
        bufferbuilder.addVertex(matrix4f, -1.0F, -1.0F, -0.5F).setUv(f8, f9).setUv1(0, 10).setColor(1.0F, 1.0F, 1.0F, t);
        bufferbuilder.addVertex(matrix4f, 1.0F, -1.0F, -0.5F).setUv(f6, f9).setUv1(0, 10).setColor(1.0F, 1.0F, 1.0F, t);
        bufferbuilder.addVertex(matrix4f, 1.0F, 1.0F, -0.5F).setUv(f6, f7).setUv1(0, 10).setColor(1.0F, 1.0F, 1.0F, t);
        bufferbuilder.addVertex(matrix4f, -1.0F, 1.0F, -0.5F).setUv(f8, f7).setUv1(0, 10).setColor(1.0F, 1.0F, 1.0F, t);
        BufferUploader.drawWithShader(bufferbuilder.buildOrThrow());
    }


}
