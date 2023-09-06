package galena.blissful.world.item;

import galena.blissful.index.BlissfulEffects;
import galena.blissful.platform.Services;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.stream.Stream;

public class BongItem extends SmokingItem {

    public BongItem(Properties properties) {
        super(properties);
    }

    @Override
    Stream<MobEffectInstance> getEffects(ItemStack stack, @Nullable Level level, @Nullable LivingEntity entity) {
        return Stream.of(new MobEffectInstance(BlissfulEffects.PEACE.get(), 20 * Services.CONFIG.common().bongPeaceSeconds(), 0));
    }

    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        PotionUtils.addPotionTooltip(getEffects(stack, level, null).toList(), tooltip, 1.0F);
    }
}
