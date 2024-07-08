package si.f5.yagi.caviaengineering.item;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import si.f5.yagi.caviaengineering.transporter.Transporter;

public class TransporterItem extends Item {

	public TransporterItem(Properties pProperties) {
		super(pProperties);
		// TODO 自動生成されたコンストラクター・スタブ
	}
	
	@Override
    public InteractionResult interactLivingEntity(ItemStack pStack, Player pPlayer, LivingEntity pInteractionTarget, InteractionHand pUsedHand) {
		//pInteractionTarget.getData(Attachments.BEAMING.get()).beaming();
		
		Transporter.beaming(pInteractionTarget, new BlockPos((int)pPlayer.getX(), (int)pPlayer.getY(), (int)pPlayer.getZ() + 8));
		
		//System.out.println("UNKO");
        return InteractionResult.PASS;
    }

}
