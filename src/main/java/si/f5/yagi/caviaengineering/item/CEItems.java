package si.f5.yagi.caviaengineering.item;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import si.f5.yagi.caviaengineering.CaviaEngineering;
import si.f5.yagi.caviaengineering.block.CEBlocks;

public class CEItems {

    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(CaviaEngineering.MODID);
    //public static final Item RAW_IRON = registerItem("raw_iron", new Item(new Item.Properties()));

    public static final TagKey<Item> GUINEAPIG_FOOD = ITEMS.createTagKey(ResourceLocation.fromNamespaceAndPath(CaviaEngineering.MODID, "a"));

    // Creates a new BlockItem with the id "examplemod:example_block", combining the namespace and path
    public static final DeferredItem<BlockItem> EXAMPLE_BLOCK_ITEM = registerBlockItem(CEBlocks.WHITE_GUINEAPIG_HOUSE);
    public static final DeferredItem<BlockItem> UNKO_BLOCK_ITEM = registerBlockItem(CEBlocks.UNKO_BLOCK);
    public static final DeferredItem<BlockItem> TRANSPORTER_BLOCK_ITEM = registerBlockItem(CEBlocks.TRANSPORTER);

    // Creates a new food item with the id "examplemod:example_id", nutrition 1 and saturation 2
    public static final DeferredItem<Item> GUINEAPIG_POOP = ITEMS.registerSimpleItem("guineapig_poop");
    public static final DeferredItem<Item> TRANSPORTER_ITEM = ITEMS.register("transporter_item", () -> new TransporterItem(new Item.Properties().rarity(Rarity.RARE)));
    
    private static DeferredItem<BlockItem> registerBlockItem(DeferredBlock<Block> block) {
    	return ITEMS.registerSimpleBlockItem(block.getRegisteredName().split(":")[1], block);
    }
    
}
