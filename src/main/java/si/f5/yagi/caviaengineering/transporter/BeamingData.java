package si.f5.yagi.caviaengineering.transporter;

import org.jetbrains.annotations.UnknownNullability;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.IntTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.common.util.INBTSerializable;
import si.f5.yagi.caviaengineering.Attachments;

public class BeamingData implements INBTSerializable<CompoundTag> {

	private static final AttachmentType<BeamingData> TYPE = Attachments.BEAMING.get();
	
	public static final int TIMER_INIT = 160;

	private BlockPos destination = null;
	private int timer = 0;

	public static BeamingData get(Entity e) {
		return e.getData(TYPE);
	}

	public void setDestination(BlockPos p) {
		this.setDestinationWithDelay(0, p);
	}
	
	
	public void setDestinationWithDelay(int delay, BlockPos p) {
		if (p != null) {
			this.timer = TIMER_INIT + delay;
		}
		this.destination = p;
	}
	
	public void decrementTimer() {
		if (this.timer > 0) {
			this.timer--;
		}
	}
	
	public int getTimer() {
		return this.timer;
	}

	private int clampTimer() {
		return Math.min(timer, TIMER_INIT);
	}
	
	public BlockPos getDestination() {
		return this.destination;
	}

	public float getProgress() {
		return (float)this.clampTimer() / (float)TIMER_INIT;
	}
	
	public int getTransparency() {
		
		float triangle = Math.abs( this.getProgress() - 0.5f ) * 2.05f;
		
		float trapezoid = Mth.clamp( ((triangle - 0.5f) * 1.2f) + 0.5f , 0.0f, 1.0f);
		
		trapezoid *= 0xFF;
		
		return (int) trapezoid;
	}
	
	public boolean isBeaming() {
		return this.timer > 0 && this.timer <= TIMER_INIT;
	}
	
	public float getBeamProgress() {
		float t = (this.clampTimer() * 2.0f) - (float)TIMER_INIT;
		t += t < 0 ? (float)TIMER_INIT : 0f;
		return t / (float)TIMER_INIT;
	}
	
	public CompoundTag toTag() {
        ListTag listtag = new ListTag();

        if (this.destination != null) {
	        listtag.add(IntTag.valueOf(this.destination.getX()));
	        listtag.add(IntTag.valueOf(this.destination.getY()));
	        listtag.add(IntTag.valueOf(this.destination.getZ()));
        }
        
		CompoundTag tag = new CompoundTag();

        tag.put("Dest", listtag);
        tag.put("Timer", IntTag.valueOf(this.timer));
		
		return tag;
	}
	
	public void loadTag(CompoundTag nbt) {

		ListTag listtag = nbt.getList("Dest", TIMER_INIT);
		
		if (!listtag.isEmpty()) {
			this.destination = new BlockPos( listtag.getInt(0), listtag.getInt(1), listtag.getInt(2));
		}
		
		this.timer = nbt.getInt("Timer");
	}
	
	@Override
	public @UnknownNullability CompoundTag serializeNBT(Provider provider) {
		return toTag();
	}

	@Override
	public void deserializeNBT(Provider provider, CompoundTag nbt) {
		this.loadTag(nbt);
	}

	@Override
	public String toString() {
		return "BeamingData:" + this.timer + ", " + (this.destination != null ? this.destination.toString() : "null");
	}
	
}
