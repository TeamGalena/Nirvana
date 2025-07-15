package galena.nirvana.index;

import com.tterrag.registrate.AbstractRegistrate;
import com.tterrag.registrate.util.entry.RegistryEntry;
import galena.nirvana.NirvanaConstants;
import galena.nirvana.platform.Services;
import galena.nirvana.world.effects.PeaceEffect;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

public class NirvanaEffects {

    private static final AbstractRegistrate<?> REGISTRATE = Services.PLATFORM.getRegistrate();

    public static final TagKey<MobEffect> STACKING_EFFECTS = TagKey.create(Registries.MOB_EFFECT, NirvanaConstants.createId("stacking"));

    public static final RegistryEntry<MobEffect, PeaceEffect> PEACE = REGISTRATE
            .generic("peace", Registries.MOB_EFFECT, PeaceEffect::new)
            .register();

    public static boolean arePeaceful(Entity target, LivingEntity attacker) {
        if (!(target instanceof LivingEntity living)) return false;
        return attacker.hasEffect(NirvanaEffects.PEACE) || living.hasEffect(NirvanaEffects.PEACE);
    }

    public static void register() {
        // loads this class
    }

}
