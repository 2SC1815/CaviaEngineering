package si.f5.yagi.caviaengineering.block;

import com.mojang.serialization.MapCodec;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class GuineaPigHouse extends DirectionalBlock {

    protected static final VoxelShape SHAPE = Block.box(0.0, 0.0, 0.0, 16.0, 4.0, 16.0);
    public static final MapCodec<GuineaPigHouse> CODEC = simpleCodec(GuineaPigHouse::new);
    
    /*
    protected final VoxelShape northAabb = Shapes.join(
            Shapes.block(),
            Shapes.or(box(2.0, 0.0, 2.0, 14.0, 14.0, 14.0), box(3.0, 0.0, 14.0, 12.0, 10.0, 16.0) ),
            BooleanOp.ONLY_FIRST
        );
    
    protected final VoxelShape southAabb = Shapes.join(
            Shapes.block(),
            Shapes.or(box(2.0, 0.0, 2.0, 14.0, 14.0, 14.0), box(3.0, 0.0, 0.0, 12.0, 10.0, 2.0)),
            BooleanOp.ONLY_FIRST
        );
   
    protected final VoxelShape eastAabb = Shapes.join(
            Shapes.block(),
            Shapes.or(box(2.0, 0.0, 2.0, 14.0, 14.0, 14.0), box(0.0, 0.0, 3.0, 2.0, 10.0, 12.0) ),
            BooleanOp.ONLY_FIRST
        );
    
    protected final VoxelShape westAabb = Shapes.join(
            Shapes.block(),
            Shapes.or(box(2.0, 0.0, 2.0, 14.0, 14.0, 14.0), box(14.0, 0.0, 3.0, 16.0, 10.0, 12.0) ),
            BooleanOp.ONLY_FIRST
        );
        */
        
    /*
    protected final VoxelShape northAabb = box(3.0, 0.0, 14.0, 12.0, 10.0, 16.0);
    
    protected final VoxelShape southAabb = box(3.0, 0.0, 0.0, 12.0, 10.0, 2.0);
   
    protected final VoxelShape eastAabb = box(0.0, 0.0, 3.0, 2.0, 10.0, 12.0);
    
    protected final VoxelShape westAabb = box(14.0, 0.0, 3.0, 16.0, 10.0, 12.0); */
    
    
    
	public GuineaPigHouse(Properties p_49795_) {
		super(p_49795_);
		// TODO 自動生成されたコンストラクター・スタブ
	}

    @Override
    protected VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
    	/*
        Direction direction = pState.getValue(FACING);
        switch (direction) {
            case SOUTH:
                return this.southAabb;
            case EAST:
                return this.eastAabb;
            case WEST:
                return this.westAabb;
            case NORTH:
            default:
                return this.northAabb;
        }*/
        return SHAPE;
    }

    /*
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return this.defaultBlockState().setValue(FACING, pContext.getHorizontalDirection());
    }
    
    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(FACING);
    }*/

	@Override
	protected MapCodec<? extends DirectionalBlock> codec() {
		return CODEC;
	}

	/*
    @Override
    protected boolean isPathfindable(BlockState p_154341_, PathComputationType p_154344_) {
        return false;
    }*/
}
