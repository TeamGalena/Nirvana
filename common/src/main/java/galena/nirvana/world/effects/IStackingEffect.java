package galena.nirvana.world.effects;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public interface IStackingEffect {

    boolean shouldIncrease(ItemStack source, LivingEntity target, Level level);

    void onIncreasedTo(MobEffectInstance instance, ItemStack source, LivingEntity target, Level level);

}
