package galena.nirvana;

import galena.nirvana.index.NirvanaBanners;
import galena.nirvana.index.NirvanaBlocks;
import galena.nirvana.index.NirvanaEffects;
import galena.nirvana.index.NirvanaEntities;
import galena.nirvana.index.NirvanaItems;
import galena.nirvana.index.NirvanaPaintings;
import galena.nirvana.index.NirvanaParticles;
import galena.nirvana.index.NirvanaRecipeTypes;
import galena.nirvana.index.NirvanaSounds;
import galena.nirvana.platform.Services;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;

public class NirvanaCommon {

    public static void init() {
        Services.PLATFORM.getRegistrate()
                .defaultCreativeTab((ResourceKey<CreativeModeTab>) null);

        NirvanaSounds.register();
        NirvanaEffects.register();
        NirvanaBlocks.register();
        NirvanaEntities.register();
        NirvanaItems.register();
        NirvanaRecipeTypes.register();
        NirvanaPaintings.register();
        NirvanaParticles.register();
        NirvanaBanners.register();
    }

}
