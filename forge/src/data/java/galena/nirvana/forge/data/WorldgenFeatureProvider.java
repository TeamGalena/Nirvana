package galena.nirvana.forge.data;

import com.mojang.serialization.Lifecycle;
import galena.nirvana.NirvanaConstants;
import galena.nirvana.index.NirvanaBlocks;
import java.util.List;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.SimpleStateProvider;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.BlockPredicateFilter;
import net.minecraft.world.level.levelgen.placement.HeightmapPlacement;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.RarityFilter;

public class WorldgenFeatureProvider {

    public record Key(ResourceLocation id) {
        public ResourceKey<ConfiguredFeature<?, ?>> configured() {
            return ResourceKey.create(Registries.CONFIGURED_FEATURE, id());
        }

        public ResourceKey<PlacedFeature> placed() {
            return ResourceKey.create(Registries.PLACED_FEATURE, id());
        }
    }

    private static final Key PATCH_WILD_HEMP = new Key(NirvanaConstants.createId("patch_wild_hemp"));

    public static void generatePlaced(BootstrapContext<PlacedFeature> context) {
        var configured = context.lookup(Registries.CONFIGURED_FEATURE);

        context.register(
                PATCH_WILD_HEMP.placed(),
                new PlacedFeature(
                        configured.getOrThrow(PATCH_WILD_HEMP.configured()),
                        List.of(
                                RarityFilter.onAverageOnceEvery(10),
                                InSquarePlacement.spread(),
                                HeightmapPlacement.onHeightmap(Heightmap.Types.MOTION_BLOCKING),
                                BiomeFilter.biome()
                        )
                ),
                Lifecycle.stable()
        );
    }

    public static void generateConfigured(BootstrapContext<ConfiguredFeature<?, ?>> context) {
        var directFeature = new PlacedFeature(
                Holder.direct(new ConfiguredFeature<>(
                        Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(SimpleStateProvider.simple(NirvanaBlocks.WILD_HEMP.get()))
                )),
                List.of(
                        BlockPredicateFilter.forPredicate(BlockPredicate.ONLY_IN_AIR_PREDICATE)
                )
        );

        context.register(
                PATCH_WILD_HEMP.configured(),
                new ConfiguredFeature<>(
                        Feature.FLOWER,
                        new RandomPatchConfiguration(64, 6, 2, Holder.direct(directFeature))
                ),
                Lifecycle.stable()
        );
    }

}
