package galena.nirvana.world.item;

import java.util.List;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.SuspiciousStewEffects;

public class SuspiciousItem {

    public static List<MobEffectInstance> getEffects(ItemStack stack, int defaultDuration) {
        var effects = stack.getOrDefault(DataComponents.SUSPICIOUS_STEW_EFFECTS, SuspiciousStewEffects.EMPTY);
        return effects.effects().stream()
                .map(SuspiciousStewEffects.Entry::createEffectInstance)
                .toList();
    }

    private SuspiciousItem() {
    }

}
