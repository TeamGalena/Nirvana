package galena.nirvana.platform.services;

import com.tterrag.registrate.builders.EntityBuilder;
import com.tterrag.registrate.util.nullness.NonNullConsumer;
import com.tterrag.registrate.util.nullness.NonNullFunction;
import com.tterrag.registrate.util.nullness.NonNullSupplier;
import galena.nirvana.platform.registrate.EntityPropertiesBuilder;
import galena.nirvana.platform.registrate.NirvanaRegistrate;
import galena.nirvana.world.item.DeerStalkerItem;
import galena.nirvana.world.item.JointItem;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.item.crafting.Ingredient;

public interface IPlatformHelper {

    NirvanaRegistrate<?> getRegistrate();

    default JointItem createJointItem(Item.Properties properties) {
        return new JointItem(properties);
    }

    default DeerStalkerItem createDeerstalkerItem(Item.Properties properties) {
        return new DeerStalkerItem(properties);
    }

    default Item createSpawnEggItem(NonNullSupplier<? extends EntityType<? extends Mob>> type, int primary, int secodary, Item.Properties properties) {
        return new SpawnEggItem(type.get(), primary, secodary, properties);
    }

    <E extends Entity, P> NonNullFunction<EntityBuilder<E, P>, EntityBuilder<E, P>> entityProperties(NonNullConsumer<EntityPropertiesBuilder> factory);

    Ingredient createNBTIngredient(ItemStack stack);

    boolean isFakePlayer(LivingEntity entity);
}
