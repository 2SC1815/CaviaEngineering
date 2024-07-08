package si.f5.yagi.caviaengineering;

import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterRenderBuffersEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.handling.DirectionalPayloadHandler;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import si.f5.yagi.caviaengineering.block.CEBlocks;
import si.f5.yagi.caviaengineering.client.entity.GuineaPigModel;
import si.f5.yagi.caviaengineering.client.entity.GuineaPigRenderer;
import si.f5.yagi.caviaengineering.client.packet.ClientBeamingHandler;
import si.f5.yagi.caviaengineering.entity.animal.GuineaPig;
import si.f5.yagi.caviaengineering.item.CEItems;
import si.f5.yagi.caviaengineering.transporter.BeamingPacket;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(CaviaEngineering.MODID)
public class CaviaEngineering
{
    // Define mod id in a common place for everything to reference
    public static final String MODID = "caviaengineering";
    
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();
    
    //public static final TagKey<Item> GUINEAPIG_FOOD = ItemTags.create(ResourceLocation.fromNamespaceAndPath(MODID, "a"));

    
    
    // Create a Deferred Register to hold Items which will all be registered under the "examplemod" namespace
    // Create a Deferred Register to hold CreativeModeTabs which will all be registered under the "examplemod" namespace
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);

    
    
    // Creates a new Block with the id "examplemod:example_block", combining the namespace and path
    //public static final DeferredBlock<Block> EXAMPLE_BLOCK = BLOCKS.registerSimpleBlock("example_block", BlockBehaviour.Properties.of().mapColor(MapColor.STONE));
    

    // Creates a creative tab with the id "examplemod:example_tab" for the example item, that is placed after the combat tab
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> EXAMPLE_TAB = CREATIVE_MODE_TABS.register("example_tab", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.caviaengineering")) //The language key for the title of your CreativeModeTab
            .withTabsBefore(CreativeModeTabs.COMBAT)
            .icon(() -> CEItems.GUINEAPIG_POOP.get().getDefaultInstance())
            .displayItems((parameters, output) -> {
                output.accept(CEItems.GUINEAPIG_POOP.get()); // Add the example item to the tab. For your own tabs, this method is preferred over the event
            }).build());

    
    
    
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(Registries.ENTITY_TYPE, MODID);
    //public static final DeferredRegister<?> ENTITY_RENDERER = DeferredRegister.create(Registries.ENTITY_, MODID);

    /*
    public static final DeferredHolder<EntityType<GuineaPig>, EntityType<GuineaPig>> GUINEAPIG = ENTITIES.register(
            "guineapig", EntityType.Builder.of(GuineaPig::new, MobCategory.CREATURE).sized(0.6F, 0.7F).eyeHeight(0.35F).passengerAttachments(0.5125F).clientTrackingRange(8)
    	    );
  	*/
    

    

    public static final DeferredHolder<EntityType<?>, EntityType<GuineaPig>> GUINEAPIG = ENTITIES.register(
            "guineapig", 
            () -> EntityType.Builder.of(GuineaPig::new, MobCategory.CREATURE)
            .sized(0.4F, 0.4F)
            .eyeHeight(0.2F)
            .passengerAttachments(0.5125F)
            .clientTrackingRange(8)
            .build(MODID)
    	    );
    
    
    // The constructor for the mod class is the first code that is run when your mod is loaded.
    // FML will recognize some parameter types like IEventBus or ModContainer and pass them in automatically.
    public CaviaEngineering(IEventBus modEventBus, ModContainer modContainer)
    {
        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(this::onEntityAttributeCreation);
        modEventBus.addListener(CETagProvider::gather);

        modEventBus.addListener(this::register);

        Sounds.SOUND_EVENTS.register(modEventBus);
        
        // Register the Deferred Register to the mod event bus so blocks get registered
        CEBlocks.BLOCKS.register(modEventBus);
        // Register the Deferred Register to the mod event bus so items get registered
        CEItems.ITEMS.register(modEventBus);
        //modEventBus.register(GUINEAPIG_FOOD);
        // Register the Deferred Register to the mod event bus so tabs get registered
        CREATIVE_MODE_TABS.register(modEventBus);
        
        ENTITIES.register(modEventBus);
        
        Attachments.ATTACHMENTS.register(modEventBus);
  

        // Register ourselves for server and other game events we are interested in.
        // Note that this is necessary if and only if we want *this* class (ExampleMod) to respond directly to events.
        // Do not add this line if there are no @SubscribeEvent-annotated functions in this class, like onServerStarting() below.
        NeoForge.EVENT_BUS.register(this);
        //NeoForge.EVENT_BUS.register(SoundDefinitions.class);
 

        // Register the item to a creative tab
        modEventBus.addListener(this::addCreative);

        // Register our mod's ModConfigSpec so that FML can create and load the config file for us
        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }
    

    
    private void commonSetup(final FMLCommonSetupEvent event)
    {
        // Some common setup code
        LOGGER.info("HELLO FROM COMMON SETUP");
        
        LOGGER.info("apple:"+new ItemStack(Items.APPLE).is(CEItems.GUINEAPIG_FOOD));
        LOGGER.info("gold:"+new ItemStack(Items.GOLD_INGOT).is(CEItems.GUINEAPIG_FOOD));
        
        LOGGER.info(CEItems.GUINEAPIG_FOOD.location().getPath());

        if (Config.logDirtBlock)
            LOGGER.info("DIRT BLOCK >> {}", BuiltInRegistries.BLOCK.getKey(Blocks.DIRT));

        LOGGER.info(Config.magicNumberIntroduction + Config.magicNumber);

        Config.items.forEach((item) -> LOGGER.info("ITEM >> {}", item.toString()));
    }

    // Add the example block item to the building blocks tab
    private void addCreative(BuildCreativeModeTabContentsEvent event)
    {
        if (event.getTabKey() == CreativeModeTabs.BUILDING_BLOCKS) {
            event.accept(CEItems.EXAMPLE_BLOCK_ITEM);
            event.accept(CEItems.UNKO_BLOCK_ITEM);
        }
    }
    

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {
        // Do something when the server starts
        LOGGER.info("HELLO from server starting");
    }

    
    public void onEntityAttributeCreation(EntityAttributeCreationEvent event) {
    	event.put(GUINEAPIG.get(), GuineaPig.createAttributes().build());
    }
    
    public void register(final RegisterPayloadHandlersEvent event) {
        // Sets the current network version
        final PayloadRegistrar registrar = event.registrar("1");
        
        registrar.playBidirectional(
            BeamingPacket.TYPE_P,
            BeamingPacket.STREAM_CODEC,
            new DirectionalPayloadHandler<>(
                ClientBeamingHandler::handleData,
                null
            )
        );
    }
    
    public static ResourceLocation rl(String path) {
    	return ResourceLocation.fromNamespaceAndPath(MODID, path);
    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @EventBusSubscriber(modid = MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents
    {
    	public static ModelLayerLocation GUINEAPIG_LAYER = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(CaviaEngineering.MODID, "guineapig"), "main");
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {
            // Some client setup code
            LOGGER.info("HELLO FROM CLIENT SETUP");
            LOGGER.info("MINECRAFT NAME >> {}", Minecraft.getInstance().getUser().getName());
            
            //ItemBlockRenderTypes.setRenderLayer(GUINEAPIG_HOUSE.get(), RenderType.cutout());
        }
        // LayerDefinition layerdefinition9 = LayerDefinition.create(OcelotModel.createBodyMesh(CubeDeformation.NONE), 64, 32);
	    @SubscribeEvent
	    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
	    	event.registerEntityRenderer(GUINEAPIG.get(), GuineaPigRenderer::new);
	        //event.registerBlockEntityRenderer(MyBlockEntityTypes.MYBE.get(), MyBlockEntityRenderer::new);
	    }
	    
	    @SubscribeEvent
	    public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
	    	event.registerLayerDefinition(GUINEAPIG_LAYER, () -> LayerDefinition.create( GuineaPigModel.createBodyMesh(CubeDeformation.NONE), 32, 16));
	    }

	    
	    
	    public static void registerRenderBuffers(RegisterRenderBuffersEvent event) {
	    	//event.registerRenderBuffer(TransparencyBufferSource.TransportEffectRenderType.GL);
	    }

	    
	    
    }
/*
    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @EventBusSubscriber(modid = MODID, bus = EventBusSubscriber.Bus.GAME, value = Dist.CLIENT)
    public static class ClientGameEvents
    {

        @SubscribeEvent
        public static void renderingEvent(RenderLivingEvent.Pre<LivingEntity, EntityModel<LivingEntity>> event) {

        	/*
        	event.getEntity().setInvisible(false);
        	
        	LivingEntityRenderer<LivingEntity, EntityModel<LivingEntity>> renderer = event.getRenderer();
        	
            ResourceLocation resourcelocation = renderer.getTextureLocation(event.getEntity());
            
            PoseStack pPoseStack = event.getPoseStack();
            
            pPoseStack.pushPose();
            pPoseStack.scale(-1.0F, -1.0F, 1.0F);
            pPoseStack.translate(0.0F, -1.501F, 0.0F);
                
        	renderer.getModel().renderToBuffer(
        			event.getPoseStack(), 
        			event.getMultiBufferSource().getBuffer(RenderType.itemEntityTranslucentCull(resourcelocation)), 
        			event.getPackedLight(), OverlayTexture.NO_OVERLAY, 654311423);
        	for (Field f : renderer.getClass().getFields()) 
        		System.out.println(f.getName());
        	/*
			try {
				Field field = renderer.getClass().getDeclaredField("layers");
				field.setAccessible(true);
				
				@SuppressWarnings("unchecked")
				List<RenderLayer<LivingEntity, EntityModel<LivingEntity>>> v = (List<RenderLayer<LivingEntity, EntityModel<LivingEntity>>>)field.get(renderer);

	            for (RenderLayer<LivingEntity, EntityModel<LivingEntity>> renderlayer :  v) {

	                renderlayer.render(pPoseStack, event.getMultiBufferSource(), event.getPackedLight(), event.getEntity(), 0, 0, event.getPartialTick(), 0, 0, 0);
	            }
	            
			} catch (Exception e) {
			}
        	  */
            
            
            //pPoseStack.popPose();
            
        /*}
        
    }*/
    
    
}


/*
 * 
 * 
 * 
    "coremods.client.ModelPartMixin",
    "coremods.client.LivingEntityRendererMixin",
    "coremods.client.ModelMixin",
    "coremods.client.LevelRendererMixin",
    */
