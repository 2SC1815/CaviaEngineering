package si.f5.yagi.caviaengineering.entity.animal;

import javax.annotation.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.ai.goal.MoveToBlockGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;
import si.f5.yagi.caviaengineering.block.CEBlocks;

public class GoHomeGoal extends MoveToBlockGoal {

    public static final int HORIZONTAL_SCAN_RANGE = 8;
    public static final int VERTICAL_SCAN_RANGE = 4;
    public static final int DONT_FOLLOW_IF_CLOSER_THAN = 3;

    @Nullable
    private Animal parent;

    public GoHomeGoal(Animal pAnimal, double pSpeedModifier) {
        super(pAnimal, pSpeedModifier, 8);
    }

    @Override
    protected void moveMobToBlock() {
        this.mob
            .getNavigation()
            .moveTo((double)this.blockPos.getX() + 0.5, (double)(this.blockPos.getY()), (double)this.blockPos.getZ() + 0.5, this.speedModifier);
    }
    
    @Override
    protected BlockPos getMoveToTarget() {
        return this.blockPos;
    }

	@Override
	protected boolean isValidTarget(LevelReader pLevel, BlockPos pPos) {
        /*if (!pLevel.isEmptyBlock(pPos.above())) {
            return false;
        } else {*/
		/*if (pLevel.getSkyDarken() < 0) {
	        
		}*/
		if (pLevel.getSkyDarken() > 10) {
			BlockState blockstate = pLevel.getBlockState(pPos);
	        return blockstate.is(CEBlocks.WHITE_GUINEAPIG_HOUSE);
		}
        
		return false;
		
	}

}
