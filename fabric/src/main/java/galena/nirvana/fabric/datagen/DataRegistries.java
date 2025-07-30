package galena.nirvana.fabric.datagen;

import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.registries.RegistriesDatapackGenerator;
import net.minecraft.data.registries.RegistryPatchGenerator;

public class DataRegistries extends RegistriesDatapackGenerator {

    public DataRegistries(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, fillRegistries(registries));
    }

    private static CompletableFuture<HolderLookup.Provider> fillRegistries(CompletableFuture<HolderLookup.Provider> registries) {
        var patched = RegistryPatchGenerator.createLookup(registries, createBuilder());
        return patched.thenApply(RegistrySetBuilder.PatchedRegistries::patches);
    }

    private static RegistrySetBuilder createBuilder() {
        return new RegistrySetBuilder()
                .add(Registries.CONFIGURED_FEATURE, WorldgenFeatureProvider::generateConfigured)
                .add(Registries.PLACED_FEATURE, WorldgenFeatureProvider::generatePlaced);
    }

}
