package si.f5.yagi.caviaengineering;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class Sounds {
	
    // Assuming that your mod id is examplemod
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS =
            DeferredRegister.create(BuiltInRegistries.SOUND_EVENT, CaviaEngineering.MODID);
    
    // All vanilla sounds use variable range events.
    public static final DeferredHolder<SoundEvent, SoundEvent> GUINEAPIG_EEK = register("guineapig_eek");

    public static final DeferredHolder<SoundEvent, SoundEvent> TRANSPORTER_DEPARTURE = register("transporter_departure");
    public static final DeferredHolder<SoundEvent, SoundEvent> TRANSPORTER_ARRIVAL = register("transporter_arrival");
    public static final DeferredHolder<SoundEvent, SoundEvent> TRANSPORTER_ENERGIZE = register("transporter_energize");
    
    
    private static DeferredHolder<SoundEvent, SoundEvent> register(String name) {
    	return SOUND_EVENTS.register(
                name,  () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(CaviaEngineering.MODID, name)));
    }
    
    public static SoundEvent transporterArrival() {
    	return TRANSPORTER_ARRIVAL.get();
    }
    
    public static SoundEvent transporterDeparture() {
    	return TRANSPORTER_DEPARTURE.get();
    }
    
    public static SoundEvent transporterEnergize() {
    	return TRANSPORTER_ENERGIZE.get();
    }
    
    // There is a currently unused method to register fixed range (= non-attenuating) events as well:
    /*public static final DeferredHolder<SoundEvent, SoundEvent> MY_FIXED_SOUND = SOUND_EVENTS.register("my_fixed_sound",
            // 16 is the default range of sounds. Be aware that due to OpenAL limitations,
            // values above 16 have no effect and will be capped to 16.
            () -> SoundEvent.createFixedRangeEvent(ResourceLocation.fromNamespaceAndPath("examplemod", "my_fixed_sound"), 16)
    );*/
	
}
