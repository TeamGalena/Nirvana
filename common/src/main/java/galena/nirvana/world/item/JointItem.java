package galena.nirvana.world.item;

import galena.nirvana.index.NirvanaEffects;
import galena.nirvana.platform.Services;
import java.util.stream.Stream;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class JointItem extends SmokingItem {

    public JointItem(Properties properties) {
        super(properties);
        registerDispenserBehaviour();
    }

    @Override
    Stream<MobEffectInstance> getEffects(ItemStack stack, @Nullable Level level, @Nullable LivingEntity entity) {
        return Stream.of(new MobEffectInstance(NirvanaEffects.PEACE, 20 * Services.CONFIG.common().jointPeaceSeconds(), 0));
    }

    @Override
    double getRadius(ItemStack stack, @Nullable Level level, @Nullable LivingEntity entity) {
        return Services.CONFIG.common().jointRadius();
    }

}
