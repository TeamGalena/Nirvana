package galena.nirvana;

import galena.nirvana.index.NirvanaParticles;
import galena.nirvana.world.particle.SmokeRingParticle;
import galena.nirvana.world.particle.ThcSmokeParticle;
import net.minecraft.client.color.item.ItemColor;
import net.minecraft.client.particle.SuspendedTownParticle;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.alchemy.PotionContents;

public class NirvanaClient {

    public static final ItemColor POTION_COLOR = (stack, i) -> {
        if (i != 1) return -1;
        return stack.getOrDefault(DataComponents.POTION_CONTENTS, PotionContents.EMPTY).getColor();
    };

    public static void registerParticles(NirvanaParticles.ParticleRegister event) {
        event.register(NirvanaParticles.SMOKE_RING.get(), SmokeRingParticle.Provider::new);
        event.register(NirvanaParticles.HERBAL_SALVE.get(), SuspendedTownParticle.HappyVillagerProvider::new);
        event.register(NirvanaParticles.THC_SMOKE.get(), ThcSmokeParticle.Provider::new);
    }
}
