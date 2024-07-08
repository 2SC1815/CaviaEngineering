package si.f5.yagi.caviaengineering.entity.animal;

import javax.annotation.Nullable;

import net.minecraft.Util;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.FastColor;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.VariantHolder;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.BreedGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.FollowParentGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.PanicGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.gameevent.GameEvent;
import si.f5.yagi.caviaengineering.CaviaEngineering;
import si.f5.yagi.caviaengineering.Sounds;
import si.f5.yagi.caviaengineering.item.CEItems;

public class GuineaPig extends TamableAnimal implements VariantHolder<GuineaPig.Variant> {

    private static final EntityDataAccessor<Integer> DATA_ID_TYPE_VARIANT = SynchedEntityData.defineId(GuineaPig.class, EntityDataSerializers.INT);
    
	public static final int POOP_TICK = 800;
	public static final int MAX_POOP_REMAIN = 8;
	
	public int poopRemain = 0;
    public int poopTime = 0;
    
    public GuineaPig(EntityType<? extends GuineaPig> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new PanicGoal(this, 1.25));
        this.goalSelector.addGoal(3, new BreedGoal(this, 1.0));
        this.goalSelector.addGoal(4, new TemptGoal(this, 1.1, p_336182_ -> p_336182_.is(Items.GOLDEN_APPLE), false));
        this.goalSelector.addGoal(4, new TemptGoal(this, 1.1, p_336182_ -> p_336182_.is(Items.APPLE), false));
        this.goalSelector.addGoal(5, new FollowParentGoal(this, 1.1));
        this.goalSelector.addGoal(5, new GoHomeGoal(this, 1.1));
        this.goalSelector.addGoal(6, new WaterAvoidingRandomStrollGoal(this, 1.0));
        this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 6.0F));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 10.0).add(Attributes.MOVEMENT_SPEED, 0.35);
    }
    

    @SuppressWarnings("resource")
	@Override
    public void aiStep() {
        super.aiStep();
        
        if (!this.level().isClientSide && this.isAlive()) {
        	
        	poopTick();
        		
        	if (this.isTimeToPoop()) {
        		//this.playSound(SoundEvents.CHICKEN_EGG, 1.0F, (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
        		this.poop();
        	}
            
        } 
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return Sounds.GUINEAPIG_EEK.get();
    }
    
    public void poopTick() {
    	if (this.poopRemain > 0 && this.poopTime > 0) {
    		this.poopTime--;
    	}
    }
    
    public boolean isTimeToPoop() {
    	return this.poopRemain > 0 && this.poopTime <= 0;
    }
    
    public void poop() {
		this.spawnAtLocation(CEItems.GUINEAPIG_POOP);
		this.gameEvent(GameEvent.ENTITY_PLACE);
		this.poopTime = this.random.nextInt(POOP_TICK) + POOP_TICK;
		if (this.poopRemain > 0)
			this.poopRemain --;
    }
    
    public boolean feed() {
    	if (this.poopRemain < MAX_POOP_REMAIN) {
    		this.heal(2.0f);
    		this.poopRemain++;
    		if (this.poopTime <= 0) {
    			this.poopTime = this.random.nextInt(POOP_TICK) + POOP_TICK;
    		}
    		return true;
    	}
    	return false;
    }
    
    public void spawnHeartParticle() {

        double d0 = this.random.nextGaussian() * 0.05;
        double d1 = this.random.nextGaussian() * 0.05;
        double d2 = this.random.nextGaussian() * 0.05;
        this.level().addParticle(ParticleTypes.HEART, this.getRandomX(0.2), this.getRandomY() + 0.2, this.getRandomZ(0.2), d0, d1, d2);
    }

    @Override
    public InteractionResult mobInteract(Player pPlayer, InteractionHand pHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pHand);
        
        if (itemstack.isEmpty()) {
        	this.playAmbientSound();
        	this.spawnHeartParticle();
        	return InteractionResult.SUCCESS;
        	
        } else if (itemstack.is(Items.APPLE)) {
        	
        	if (this.feed()) {
        		itemstack.consume(1, pPlayer);
            	this.spawnHeartParticle();
        		return InteractionResult.SUCCESS;
        	} else {
        		return InteractionResult.FAIL;
        	}
        	
        } else {
        	return super.mobInteract(pPlayer, pHand);
        }
    }
    
	@Override
	public boolean isFood(ItemStack pStack) {
		return pStack.is(Items.GOLDEN_APPLE);
	}

	@Override
	public AgeableMob getBreedOffspring(ServerLevel pLevel, AgeableMob pOtherParent) {
		return CaviaEngineering.GUINEAPIG.get().create(pLevel);
	}

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor pLevel, DifficultyInstance pDifficulty, MobSpawnType pSpawnType, @Nullable SpawnGroupData pSpawnGroupData) {
        pSpawnGroupData = super.finalizeSpawn(pLevel, pDifficulty, pSpawnType, pSpawnGroupData);
        RandomSource randomsource = pLevel.getRandom();

        Pattern pattern = Util.getRandom(Pattern.values(), randomsource);
        Color baseColor = Util.getRandom(Color.values(), randomsource);
        Color patternColorA = Util.getRandom(Color.values(), randomsource);
        Color patternColorB = Util.getRandom(Color.values(), randomsource);
        
        switch(pattern) {
        case Pattern.HIMALAYAN:
    		patternColorA = Color.BLACK;
    		break;
        case Pattern.CRESTED:
    		patternColorA = Color.WHITE;
    		break;
        default:
        	break;
        }
        
        Variant v = new Variant(pattern, baseColor, patternColorA, patternColorB);
        
        this.setVariant(v);
        return pSpawnGroupData;
    }
	
	
    @Override
    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        if (pCompound.contains("PoopRemain")) {
            this.poopRemain = pCompound.getInt("PoopRemain");
        }
        if (pCompound.contains("PoopLayTime")) {
            this.poopTime = pCompound.getInt("PoopLayTime");
        }
        if (pCompound.contains("Variant")) {
            this.setVariant( new Variant( pCompound.getInt("Variant") ) );
        }
    }

    @Override
    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        pCompound.putInt("PoopRemain", this.poopRemain);
        pCompound.putInt("PoopLayTime", this.poopTime);
        pCompound.putInt("Variant", this.getVariant().getPackedId());
    }
    
    public static enum Pattern {
    	SINGLE_COLOR(0, null),
    	TRICOLOR(1, "tricolor"),
    	DUTCH(2, "dutch"),
    	HIMALAYAN(3, "himalayan", false),
    	BELTED(4, "belted"),
    	CRESTED(5, "crested", false)
    	
    	;

    	private final int id;
    	private final String texture;
    	private final boolean availableB;
    	
    	Pattern(int id, String texturename, boolean availableB) {
    		this.id = id;
    		this.texture = texturename;
    		this.availableB = availableB;
    	}
    	
    	Pattern(int id, String texturename) {
    		this(id, texturename, texturename != null);
    	}
    	
    	public int getId() {
    		return this.id;
    	}
    	
    	public String getTextureName() {
    		return this.texture;
    	}
    	
    	public boolean isAvailableB() {
    		return this.availableB;
    	}
    	
    	
    	public static Pattern fromId(int id) {
    		for (Pattern p : Pattern.values()) {
    			if (id == p.getId()) {
    				return p;
    			}
    		}
    		return Pattern.SINGLE_COLOR;
    	}
    	
    }
    
    public static enum Color {
    	ORANGE(0, FastColor.ARGB32.color(0xCD, 0x85, 0x3F)),
    	BROWN(1, FastColor.ARGB32.color(0x8B, 0x45, 0x13)),
    	CREAM(2, FastColor.ARGB32.color(0xFF, 0xE4, 0xB5)),
    	WHITE(3, FastColor.ARGB32.color(0xFF, 0xFA, 0xF0)),
    	BLACK(4, FastColor.ARGB32.color(0x2B, 0x20, 0x20)),
    	GRAY(5, FastColor.ARGB32.color(0x85, 0x82, 0x7D)),
    	LILAC(6, FastColor.ARGB32.color(0x6B, 0x5E, 0x48)),
    	
    	;
    	
    	private int id;
    	private int color;
    	
    	Color(int id, int color) {
    		this.id = id;
    		this.color = color;
    	}
    	
    	public int getId() {
    		return this.id;
    	}
    	
    	public int getColor() {
    		return this.color;
    	}
    	
    	public static Color fromId(int id) {
    		for (Color c : Color.values()) {
    			if (id == c.getId()) {
    				return c;
    			}
    		}
    		//System.out.println(id + " is not available. return orange code.");
    		return Color.ORANGE;
    	}
    	
    }

    public static record Variant(Pattern pattern, Color baseColor, Color patternColorA, Color patternColorB) {
    	
    	
    	
    	public Variant(int id) {
    		this(getPattern(id), getBaseColor(id), getPatternColorA(id), getPatternColorB(id));
    	}

    	public int getPackedId() {
    		return packVariant(this.pattern, this.baseColor, this.patternColorA, this.patternColorB);
    	}
    	
    }
    
    public static int packVariant(Pattern pattern, Color baseColor, Color patternColorA, Color patternColorB) {
    	return (pattern.getId() & 0x0F) | ((baseColor.getId() & 0x0F) << 4) | ((patternColorA.getId() & 0x0F) << 8) | ((patternColorB.getId() & 0x0F) << 16);
    }

    private int getPackedVariant() {
        return this.entityData.get(DATA_ID_TYPE_VARIANT);
    }
    
    public Pattern getPattern() {
    	return getPattern(this.getPackedVariant());
    }

    public Color getBaseColor() {
    	return getBaseColor(this.getPackedVariant());
    }
    
    public Color getPatternColorA() {
    	return getPatternColorA(this.getPackedVariant());
    }
    
    public Color getPatternColorB() {
    	return getPatternColorB(this.getPackedVariant());
    }
    
    public static Pattern getPattern(int packed) {
    	return Pattern.fromId(packed & 0x0F);
    }
    
    public static Color getBaseColor(int packed) {
    	return Color.fromId(packed >> 4 & 0x0F);
    }
    
    public static Color getPatternColorA(int packed) {
    	return Color.fromId(packed >> 8 & 0x0F);
    }
    
    public static Color getPatternColorB(int packed) {
    	return Color.fromId(packed >> 12 & 0x0F);
    }
    
    /*public ResourceLocation texture() {
    	return this.getVariant().texture;
    }*/

	@Override
	public void setVariant(Variant pVariant) {
		this.entityData.set(DATA_ID_TYPE_VARIANT, pVariant.getPackedId());
	}

	@Override
	public Variant getVariant() {
        return new Variant( this.entityData.get(DATA_ID_TYPE_VARIANT) );
	}
	
    @Override
    protected void defineSynchedData(SynchedEntityData.Builder pBuilder) {
        super.defineSynchedData(pBuilder);
        pBuilder.define(DATA_ID_TYPE_VARIANT, 0);
    }

}

