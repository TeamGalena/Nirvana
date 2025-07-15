package galena.nirvana.fabric.datagen;

import galena.nirvana.NirvanaConstants;
import java.util.Optional;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.DetectedVersion;
import net.minecraft.data.metadata.PackMetadataGenerator;
import net.minecraft.network.chat.Component;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.metadata.pack.PackMetadataSection;

public class PackMetadataProvider extends PackMetadataGenerator {

    public PackMetadataProvider(FabricDataOutput output) {
        super(output);
        add(PackMetadataSection.TYPE, new PackMetadataSection(
                Component.literal(NirvanaConstants.MOD_NAME + " resources"),
                DetectedVersion.BUILT_IN.getPackVersion(PackType.CLIENT_RESOURCES),
                Optional.empty()
        ));
    }

}
