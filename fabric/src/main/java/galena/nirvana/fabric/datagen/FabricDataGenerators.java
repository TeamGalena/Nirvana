package galena.nirvana.fabric.datagen;

import com.tterrag.registrate.providers.RegistrateLangProvider;
import galena.nirvana.NirvanaCommon;
import galena.nirvana.fabric.FabricEntrypoint;
import galena.nirvana.index.NirvanaEffects;
import galena.nirvana.index.NirvanaItems;
import io.github.fabricators_of_create.porting_lib.data.ExistingFileHelper;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.alchemy.PotionUtils;

public class FabricDataGenerators implements DataGeneratorEntrypoint {

    @Override
    public void onInitializeDataGenerator(FabricDataGenerator generator) {
        NirvanaCommon.init();
        addDefaultTranslations();
        CompatRegistrate.register();

        var fileHelper = ExistingFileHelper.withResourcesFromArg();
        var pack = generator.createPack();
        FabricEntrypoint.REGISTRATE.setupDatagen(pack, fileHelper);
        pack.addProvider(AdditionalNirvanaRecipes::new);
        pack.addProvider(PackMetadataProvider::new);
        pack.addProvider(DataRegistries::new);
        pack.addProvider(NirvanaBiomeTagsProvider::new);
    }

    private void addDefaultTranslations() {
        var peaceId = NirvanaEffects.PEACE.getId();
        FabricEntrypoint.REGISTRATE.addLang("effect", peaceId, "Peace");
        FabricEntrypoint.REGISTRATE.addLang("effect", peaceId, "description", "A blissful aura befogs your brain");

        FabricEntrypoint.REGISTRATE.addLang("item", NirvanaItems.POTION_BONG.getId(), "effect.empty", "Effect Bong");

        BuiltInRegistries.POTION.forEach(potion -> {
            var id = potion.getName("");
            var stack = PotionUtils.setPotion(NirvanaItems.POTION_BONG.asStack(), potion);
            if (potion.getEffects().isEmpty()) {
                FabricEntrypoint.REGISTRATE.addRawLang(stack.getDescriptionId(), RegistrateLangProvider.toEnglishName(id) + " Bong");
            } else {
                FabricEntrypoint.REGISTRATE.addRawLang(stack.getDescriptionId(), "Bong of " + RegistrateLangProvider.toEnglishName(id));
            }
        });

        translatePotion("stunning", "Brain Damage");
    }

    private void translatePotion(String potion) {
        translatePotion(potion, RegistrateLangProvider.toEnglishName(potion));
    }

    private void translatePotion(String potion, String translation) {
        FabricEntrypoint.REGISTRATE.addRawLang("item.nirvana.potion_bong.effect." + potion, "Bong of " + translation);
    }

}
