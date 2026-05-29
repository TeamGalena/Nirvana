package galena.nirvana.forge.data;

import galena.nirvana.NirvanaConstants;
import galena.nirvana.index.NirvanaTags;
import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.Potions;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class NirvanaPotionTagsProvider extends TagsProvider<Potion> {

    public NirvanaPotionTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookup, ExistingFileHelper existing) {
        super(output, Registries.POTION, lookup, NirvanaConstants.MOD_ID, existing);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(NirvanaTags.NO_BONG).add(Potions.WATER.getKey());
    }

}
