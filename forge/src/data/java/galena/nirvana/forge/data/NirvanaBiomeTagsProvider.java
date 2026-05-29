package galena.nirvana.forge.data;

import galena.nirvana.NirvanaConstants;
import galena.nirvana.index.NirvanaTags;
import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.BiomeTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class NirvanaBiomeTagsProvider extends BiomeTagsProvider {

    public NirvanaBiomeTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookup, ExistingFileHelper existing) {
        super(output, lookup, NirvanaConstants.MOD_ID, existing);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(NirvanaTags.GENERATES_WILD_HEMP)
                .addOptionalTag(BiomeTags.IS_JUNGLE.location())
                .addOptionalTag(ResourceLocation.fromNamespaceAndPath("atmospheric", "is_rainforest"))
                .addOptionalTag(ResourceLocation.fromNamespaceAndPath("biomeswevegone", "jungle"));
    }
}
