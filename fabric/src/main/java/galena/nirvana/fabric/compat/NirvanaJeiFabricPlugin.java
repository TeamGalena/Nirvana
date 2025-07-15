package galena.nirvana.fabric.compat;

import galena.nirvana.compat.NirvanaJeiCompat;
import galena.nirvana.index.NirvanaItems;
import galena.nirvana.index.NirvanaRecipeTypes;
import galena.nirvana.platform.Services;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.constants.RecipeTypes;
import mezz.jei.api.registration.IRecipeRegistration;
import mezz.jei.api.registration.ISubtypeRegistration;
import net.minecraft.resources.ResourceLocation;

public class NirvanaJeiFabricPlugin implements IModPlugin {

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        if (Services.PLATFORM.createLoaded()) {
            CreateCompat.addJeiRecipes(registration);
        }

        registration.addRecipes(RecipeTypes.CRAFTING, NirvanaRecipeTypes.createSuspiciousRecipes());
    }

    @Override
    public ResourceLocation getPluginUid() {
        return NirvanaJeiCompat.ID;
    }

    @Override
    public void registerItemSubtypes(ISubtypeRegistration registration) {
        registration.registerSubtypeInterpreter(NirvanaItems.POTION_BONG.get(), NirvanaJeiCompat.potionInterpreter());
        registration.registerSubtypeInterpreter(NirvanaItems.HERBAL_SALVE.get(), NirvanaJeiCompat.suspiciousInterpreter());
        registration.registerSubtypeInterpreter(NirvanaItems.SUSPICIOUS_PIPE.get(), NirvanaJeiCompat.suspiciousInterpreter());
    }

}
