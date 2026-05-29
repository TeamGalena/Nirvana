package galena.nirvana.forge.data;

import galena.nirvana.NirvanaConstants;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;

public class DataRegistries extends DatapackBuiltinEntriesProvider {

    public static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
        .add(Registries.CONFIGURED_FEATURE, WorldgenFeatureProvider::generateConfigured)
        .add(Registries.PLACED_FEATURE, WorldgenFeatureProvider::generatePlaced);

    public DataRegistries(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, BUILDER, Set.of("minecraft", NirvanaConstants.MOD_ID));
    }

}
