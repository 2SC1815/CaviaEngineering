package si.f5.yagi.caviaengineering.transporter;

import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.entity.Entity;
import net.neoforged.neoforge.network.PacketDistributor;
import si.f5.yagi.caviaengineering.CaviaEngineering;

public class BeamingPacket implements CustomPacketPayload {

    public static final CustomPacketPayload.Type<BeamingPacket> TYPE_P = new CustomPacketPayload.Type<>(CaviaEngineering.rl("beaming_data"));

    public static final StreamCodec<ByteBuf, BeamingPacket> STREAM_CODEC = StreamCodec.composite(
        ByteBufCodecs.COMPOUND_TAG,
        BeamingPacket::tag,
        ByteBufCodecs.INT,
        BeamingPacket::id,
        BeamingPacket::new
    );
    
    private CompoundTag tag;
    private int entityId;
    
    public static void beaming(BeamingData t, Entity e) {
		PacketDistributor.sendToAllPlayers(new BeamingPacket(t.toTag(), e.getId()));
    }
    
    public BeamingPacket(CompoundTag tag, int entityId) {
    	this.tag = tag;
    	this.entityId = entityId;
    }
    
    public CompoundTag tag() {
    	return this.tag;
    }
    
    public int id() {
    	return this.entityId;
    }
    
    @Override
    public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
        return TYPE_P;
    }
    
}
