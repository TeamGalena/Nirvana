package galena.nirvana.fabric.compat;

import galena.nirvana.compat.NirvanaJeiCompat;
import galena.nirvana.index.NirvanaItems;
import galena.nirvana.index.NirvanaRecipeTypes;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.constants.RecipeTypes;
import mezz.jei.api.registration.IRecipeRegistration;
import mezz.jei.api.registration.ISubtypeRegistration;
import mezz.jei.library.plugins.vanilla.ingredients.subtypes.PotionSubtypeInterpreter;
import mezz.jei.library.plugins.vanilla.ingredients.subtypes.SuspiciousStewSubtypeInterpreter;
import net.minecraft.resources.ResourceLocation;

public class NirvanaJeiFabricPlugin implements IModPlugin {

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        registration.addRecipes(RecipeTypes.CRAFTING, NirvanaRecipeTypes.createSuspiciousRecipes());
    }

    @Override
    public ResourceLocation getPluginUid() {
        return NirvanaJeiCompat.ID;
    }

    @Override
    public void registerItemSubtypes(ISubtypeRegistration registration) {
        registration.registerSubtypeInterpreter(NirvanaItems.POTION_BONG.get(), PotionSubtypeInterpreter.INSTANCE);
        registration.registerSubtypeInterpreter(NirvanaItems.HERBAL_SALVE.get(), SuspiciousStewSubtypeInterpreter.INSTANCE);
        registration.registerSubtypeInterpreter(NirvanaItems.SUSPICIOUS_PIPE.get(), SuspiciousStewSubtypeInterpreter.INSTANCE);
    }

}
