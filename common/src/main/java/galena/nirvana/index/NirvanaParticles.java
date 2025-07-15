package galena.nirvana.index;

import com.tterrag.registrate.AbstractRegistrate;
import com.tterrag.registrate.util.entry.RegistryEntry;
import galena.nirvana.platform.Services;
import galena.nirvana.world.particle.ModdedParticleType;
import java.util.function.Function;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;

public class NirvanaParticles {

    private static final AbstractRegistrate<?> REGISTRATE = Services.PLATFORM.getRegistrate();

    public static final RegistryEntry<ParticleType<?>, ModdedParticleType> SMOKE_RING = REGISTRATE
            .generic("smoke_ring", Registries.PARTICLE_TYPE, ModdedParticleType::new)
            .register();

    public static final RegistryEntry<ParticleType<?>, ModdedParticleType> HERBAL_SALVE = REGISTRATE
            .generic("herbal_salve", Registries.PARTICLE_TYPE, ModdedParticleType::new)
            .register();

    public static final RegistryEntry<ParticleType<?>, ModdedParticleType> THC_SMOKE = REGISTRATE
            .generic("thc_smoke", Registries.PARTICLE_TYPE, ModdedParticleType::new)
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
