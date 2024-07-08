package si.f5.yagi.caviaengineering;

import java.util.concurrent.CompletableFuture;

import org.jetbrains.annotations.Nullable;

import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import si.f5.yagi.caviaengineering.item.CEItems;

public class CETagProvider extends ItemTagsProvider {

	public CETagProvider(
			PackOutput pOutput, 
			CompletableFuture<Provider> pLookupProvider,
			CompletableFuture<TagLookup<Block>> pBlockTags, 
			@Nullable ExistingFileHelper existingFileHelper) {
		
		super(pOutput, pLookupProvider, pBlockTags, CaviaEngineering.MODID, existingFileHelper);
		// TODO 自動生成されたコンストラクター・スタブ
	}

/*
	public CETagProvider(
			PackOutput pOutput, 
			CompletableFuture<Provider> pLookupProvider,
			CompletableFuture<TagLookup<Item>> pParentProvider, 
			CompletableFuture<TagLookup<Block>> pBlockTags, 
			@Nullable ExistingFileHelper existingFileHelper) {
		
		super(pOutput, pLookupProvider, pParentProvider, pBlockTags, CaviaEngineering.MODID, existingFileHelper);
		// TODO 自動生成されたコンストラクター・スタブ
	}*/
	

	@Override
	protected void addTags(Provider p) {
		// TODO 自動生成されたメソッド・スタブ
		
		tag(CEItems.GUINEAPIG_FOOD).add(Items.APPLE);


	}
	
	private static class BlockTagProvider extends BlockTagsProvider {

		public BlockTagProvider(PackOutput output, CompletableFuture<Provider> lookupProvider,
				@Nullable ExistingFileHelper existingFileHelper) {
			super(output, lookupProvider, CaviaEngineering.MODID, existingFileHelper);
			// TODO 自動生成されたコンストラクター・スタブ
		}

		@Override
		protected void addTags(Provider pProvider) {
			// TODO 自動生成されたメソッド・スタブ
			
		}
		
	}
	
	public static void gather(GatherDataEvent event) {
		
		PackOutput po = event.getGenerator().getPackOutput();
		
		BlockTagProvider unko = new BlockTagProvider(po, event.getLookupProvider(), event.getExistingFileHelper());
		
		event.getGenerator().addProvider(true, new CETagProvider(po, event.getLookupProvider(), unko.contentsGetter(), event.getExistingFileHelper()));
	}

}
