package galena.nirvana.fabric.datagen;

import galena.nirvana.NirvanaConstants;
import io.github.fabricators_of_create.porting_lib.data.DatapackBuiltinEntriesProvider;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;

public class DataRegistries extends DatapackBuiltinEntriesProvider {

    public DataRegistries(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, createBuilder(), Set.of(NirvanaConstants.MOD_ID));
    }

    private static RegistrySetBuilder createBuilder() {
        return new RegistrySetBuilder()
                .add(Registries.CONFIGURED_FEATURE, WorldgenFeatureProvider::generateConfigured)
                .add(Registries.PLACED_FEATURE, WorldgenFeatureProvider::generatePlaced);
    }

}
