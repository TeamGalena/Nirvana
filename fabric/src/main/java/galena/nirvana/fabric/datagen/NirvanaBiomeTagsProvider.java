package galena.nirvana.fabric.datagen;

import galena.nirvana.index.NirvanaTags;
import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.BiomeTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;

public class NirvanaBiomeTagsProvider extends BiomeTagsProvider {

    public NirvanaBiomeTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookup) {
        super(output, lookup);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(NirvanaTags.GENERATES_WILD_HEMP)
                .addOptionalTag(BiomeTags.IS_JUNGLE.location())
                .addOptionalTag(new ResourceLocation("atmospheric", "is_rainforest"))
                .addOptionalTag(new ResourceLocation("biomeswevegone", "jungle"));
    }
}
