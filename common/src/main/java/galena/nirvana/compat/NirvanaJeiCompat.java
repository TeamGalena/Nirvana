package galena.nirvana.compat;

import galena.nirvana.NirvanaConstants;
import galena.nirvana.world.item.SuspiciousItem;
import java.util.StringJoiner;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.PotionUtils;

public class NirvanaJeiCompat {

    public static final ResourceLocation ID = NirvanaConstants.createId("jei");

    public static String interpretPotion(ItemStack ingredient, Object unused) {
        var potion = PotionUtils.getPotion(ingredient);
        var effects = PotionUtils.getMobEffects(ingredient);
        var builder = new StringBuilder(potion.getName(""));
        effects.forEach(it -> builder.append(";").append(it));
        return builder.toString();
    }

    public static String interpretSuspiciousItem(ItemStack ingredient, Object unused) {
        var effects = SuspiciousItem.getEffects(ingredient, 1000);

        if(effects.isEmpty()) return "";

        var joiner = new StringJoiner(",", "[", "]");
        effects.stream()
                .map(it -> MobEffect.getId(it.getEffect()) + "." + it.getDuration())
                .forEach(joiner::add);

        return joiner.toString();
    }

}
