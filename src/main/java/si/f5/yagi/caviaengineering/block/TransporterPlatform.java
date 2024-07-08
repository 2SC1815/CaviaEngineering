package si.f5.yagi.caviaengineering.block;

import java.util.List;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import si.f5.yagi.caviaengineering.Sounds;
import si.f5.yagi.caviaengineering.transporter.Transporter;

public class TransporterPlatform extends Block {

    public static final BooleanProperty TRIGGERED = BlockStateProperties.TRIGGERED;
    
	public TransporterPlatform(Properties p_49795_) {
		super(p_49795_);
		// TODO 自動生成されたコンストラクター・スタブ
	}

    @Override
    protected void neighborChanged(BlockState pState, Level pLevel, BlockPos pPos, Block pBlock, BlockPos pFromPos, boolean pIsMoving) {
        boolean flag = pLevel.hasNeighborSignal(pPos) || pLevel.hasNeighborSignal(pPos.above());
        boolean flag1 = pState.getValue(TRIGGERED);
        if (flag && !flag1) {
            pLevel.scheduleTick(pPos, this, 4);
            pLevel.setBlock(pPos, pState.setValue(TRIGGERED, Boolean.valueOf(true)), 2);
        } else if (!flag && flag1) {
            pLevel.setBlock(pPos, pState.setValue(TRIGGERED, Boolean.valueOf(false)), 2);
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(TRIGGERED);
    }
    
    
    private void transport(Level pLevel, BlockPos pPos) {

        Vec3 vec3 = Vec3.atBottomCenterOf(pPos);
        List<Entity> list = pLevel
            .getEntitiesOfClass(
                Entity.class,
                new AABB(vec3.x() - 2.0, vec3.y(), vec3.z() - 2.0, vec3.x() + 2.0, vec3.y() + 2.0, vec3.z() + 2.0)
            );
        
        for (Entity e : list) {
        	Transporter.beaming(e, pPos.east(15), 30);
        }
        
        if (!list.isEmpty()) {
        	pLevel.playSound(null, pPos, Sounds.transporterEnergize(), SoundSource.NEUTRAL, 1.0f, 1.0f);
        }
        
    }
    
    @Override
    protected void tick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom) {
    	
    	
        this.transport(pLevel, pPos);
        
        super.tick(pState, pLevel, pPos, pRandom);
        
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, BlockHitResult pHitResult) {

        //this.transport(pLevel, pPos);
        
        //System.out.println("use!" + pLevel.isClientSide);
        
        if (pLevel.isClientSide) {
            return InteractionResult.SUCCESS;
        } else {


            return InteractionResult.CONSUME;
        }
        
    }
    

}
