package galena.nirvana.index;

import com.possible_triangle.multikulti.registrate.MultikultiRegistrate;
import com.tterrag.registrate.util.entry.RegistryEntry;
import galena.nirvana.platform.Services;
import galena.nirvana.world.particle.SmokeRingParticle;
import galena.nirvana.world.particle.ThcSmokeParticle;
import java.util.function.Function;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.SuspendedTownParticle;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;

public class NirvanaParticles {

    private static final MultikultiRegistrate<?> REGISTRATE = Services.PLATFORM.getRegistrate();

    public static final RegistryEntry<SimpleParticleType> SMOKE_RING = REGISTRATE
            .particle("smoke_ring")
            .sprites(4)
            .provider(() -> SmokeRingParticle.Provider::new)
            .register();

    public static final RegistryEntry<SimpleParticleType> HERBAL_SALVE = REGISTRATE
            .particle("herbal_salve")
            .provider(() -> SuspendedTownParticle.HappyVillagerProvider::new)
            .register();

    public static final RegistryEntry<SimpleParticleType> THC_SMOKE = REGISTRATE
            .particle("thc_smoke")
            .sprites(new ResourceLocation("big_smoke"), 12)
            .provider(() -> ThcSmokeParticle.Provider::new)
            .register();

    public static void spawnRing(Level level, LivingEntity user) {
        if (level instanceof ServerLevel serverLevel) {
            var pos = user.getEyePosition();
            var motion = user.getLookAngle();
            serverLevel.sendParticles(SMOKE_RING.get(), pos.x, pos.y, pos.z, 0, motion.x, motion.y, motion.z, 0.1);
        }
    }

    public static void register() {
        // loads this class
    }

    @FunctionalInterface
    public interface ParticleRegister {
        <T extends ParticleOptions> void register(ParticleType<T> options, Function<SpriteSet, ParticleProvider<T>> factory);
    }

}
