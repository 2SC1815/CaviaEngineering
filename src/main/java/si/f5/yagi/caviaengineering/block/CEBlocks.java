package si.f5.yagi.caviaengineering.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;
import si.f5.yagi.caviaengineering.CaviaEngineering;

public class CEBlocks {

    // Create a Deferred Register to hold Blocks which will all be registered under the "examplemod" namespace
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(CaviaEngineering.MODID);

    public static final DeferredBlock<Block> WHITE_GUINEAPIG_HOUSE = BLOCKS.register("white_guineapig_house", () -> new GuineaPigHouse(
	    		BlockBehaviour.Properties.of().noOcclusion().strength(0.2F).sound(SoundType.WOOL).ignitedByLava()
            	)
    		);

    public static final DeferredBlock<Block> UNKO_BLOCK = BLOCKS.registerSimpleBlock(
    			"unko",
	    		BlockBehaviour.Properties.of().friction(0.1f)
    		);
    
    public static final DeferredBlock<Block> TRANSPORTER = BLOCKS.register("transporter_platform", () -> new TransporterPlatform(
    		BlockBehaviour.Properties.of().noOcclusion().strength(1.5F).sound(SoundType.ANVIL)
        	)
		);
    
}
