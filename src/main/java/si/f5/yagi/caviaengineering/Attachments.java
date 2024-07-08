package si.f5.yagi.caviaengineering;

import java.util.function.Supplier;

import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import si.f5.yagi.caviaengineering.transporter.BeamingData;

public class Attachments {

	public static final DeferredRegister<AttachmentType<?>> ATTACHMENTS = 
			DeferredRegister.create(NeoForgeRegistries.Keys.ATTACHMENT_TYPES, CaviaEngineering.MODID);

	public static final Supplier<AttachmentType<BeamingData>> BEAMING = ATTACHMENTS.register(
        "beaming", () -> AttachmentType.serializable(() -> new BeamingData()).build());

	
}
