package galena.nirvana.world;

import galena.nirvana.index.NirvanaEffects;
import galena.nirvana.index.NirvanaParticles;
import galena.nirvana.platform.Services;
import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class THCCloud {

    public static AreaEffectCloud spawnCloud(Level level, Vec3 at, float size, int nauseaSeconds, int peaceSeconds) {
        level.explode(null, at.x, at.y, at.z, size / 10, Level.ExplosionInteraction.NONE);

        var cloud = new AreaEffectCloud(level, at.x, at.y, at.z);

        var amplifier = Services.CONFIG.common().nauseaAfterHits() + 1;
        cloud.addEffect(new MobEffectInstance(NirvanaEffects.PEACE, 20 * peaceSeconds, amplifier));

        cloud.setParticle(NirvanaParticles.THC_SMOKE.get());
        cloud.setRadius(1.5F * size);
        cloud.setRadiusPerTick(-0.01F);
        cloud.setDuration(200);

        level.addFreshEntity(cloud);
        return cloud;
    }

    public static void tickCloud(Level level, BlockPos pos, int range) {
        var x = pos.getX() + level.getRandom().nextFloat() * range * 2 - range + 0.5;
        var y = pos.getY() + level.getRandom().nextFloat() * range * 2 - range + 0.5;
        var z = pos.getZ() + level.getRandom().nextFloat() * range * 2 - range + 0.5;

        var containing = BlockPos.containing(x, y, z);
        if(level.getBlockState(containing).isSolid()) return;

        level.addParticle(NirvanaParticles.THC_SMOKE.get(), x, y, z, 0.01, 0.01, 0.01);

    }

}
