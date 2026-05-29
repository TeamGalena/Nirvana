package galena.nirvana.forge.data;

import com.tterrag.registrate.AbstractRegistrate;
import com.tterrag.registrate.providers.RegistrateLangProvider;
import galena.nirvana.index.NirvanaItems;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionContents;

public final class PotionTranslationProvider {

    private final Set<String> translated = new HashSet<>();
    private final AbstractRegistrate<?> registrate;

    public PotionTranslationProvider(AbstractRegistrate<?> registrate) {
        this.registrate = registrate;
    }

    public void addTranslations() {
        translatePotion("stunning", "Brain Damage");

        translateRemaining();
    }

    private void translateRemaining() {
        BuiltInRegistries.POTION.holders().forEach(potion -> {
            if (!translated.add(Potion.getName(Optional.of(potion), ""))) return;

            var id = potion.key().location().getPath();
            var stack = NirvanaItems.POTION_BONG.asStack();
            stack.set(DataComponents.POTION_CONTENTS, new PotionContents(potion));
            if (potion.value().getEffects().isEmpty()) {
                registrate.addRawLang(stack.getDescriptionId(), RegistrateLangProvider.toEnglishName(id) + " Bong");
            } else {
                registrate.addRawLang(stack.getDescriptionId(), "Bong of " + RegistrateLangProvider.toEnglishName(id));
            }
        });
    }

    private void translatePotion(String potion) {
        translatePotion(potion, RegistrateLangProvider.toEnglishName(potion));
    }

    private void translatePotion(String potion, String translation) {
        translated.add(potion);
        registrate.addRawLang("item.nirvana.potion_bong.effect." + potion, "Bong of " + translation);
    }

}
