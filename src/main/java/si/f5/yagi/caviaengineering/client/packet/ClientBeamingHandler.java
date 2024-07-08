package si.f5.yagi.caviaengineering.client.packet;

import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import si.f5.yagi.caviaengineering.transporter.BeamingPacket;
import si.f5.yagi.caviaengineering.transporter.Transporter;

@OnlyIn(Dist.CLIENT)
public class ClientBeamingHandler {
	    
	public static void handleData(final BeamingPacket data, final IPayloadContext context) {
    	
    	Minecraft mc = Minecraft.getInstance();
    	
    	Transporter.beaming(data, mc.level);
    	
        // Do something with the data, on the network thread
       // blah(data.name());
        
        // Do something with the data, on the main thread
        /*
        context.enqueueWork(() -> {
            blah(data.age());
        })
        .exceptionally(e -> {
            // Handle exception
            context.disconnect(Component.translatable("my_mod.networking.failed", e.getMessage()));
            return null;
        });*/
    }
    
}
