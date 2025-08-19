package galena.nirvana.forge.services;

import com.possible_triangle.multikulti.registrate.MultikultiRegistrate;
import com.tterrag.registrate.builders.EntityBuilder;
import com.tterrag.registrate.util.nullness.NonNullConsumer;
import com.tterrag.registrate.util.nullness.NonNullFunction;
import com.tterrag.registrate.util.nullness.NonNullSupplier;
import galena.nirvana.forge.ForgeEntrypoint;
import galena.nirvana.forge.world.ForgeDeerstalkerItem;
import galena.nirvana.platform.registrate.EntityPropertiesBuilder;
import galena.nirvana.platform.services.IPlatformHelper;
import galena.nirvana.world.item.DeerStalkerItem;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.common.DeferredSpawnEggItem;
import net.neoforged.neoforge.common.crafting.DataComponentIngredient;
import net.neoforged.neoforge.common.util.FakePlayer;

public class ForgePlatformHelper implements IPlatformHelper {

    @Override
    public MultikultiRegistrate<?> getRegistrate() {
        return ForgeEntrypoint.REGISTRATE;
    }

    @Override
    public DeerStalkerItem createDeerstalkerItem(Item.Properties properties) {
        return new ForgeDeerstalkerItem(properties);
    }

    @Override
    public Item createSpawnEggItem(NonNullSupplier<? extends EntityType<? extends Mob>> type, int primary, int secodary, Item.Properties properties) {
        return new DeferredSpawnEggItem(type, primary, secodary, properties);
    }

    private static <E extends Entity> NonNullConsumer<EntityType.Builder<E>> mapFactory(NonNullConsumer<EntityPropertiesBuilder> factory) {
        return builder -> {
            factory.accept(new EntityPropertiesBuilder() {
                @Override
                public EntityPropertiesBuilder sized(float width, float height) {
                    builder.sized(width, height);
                    return this;
                }

                @Override
                public EntityPropertiesBuilder clientTrackingRange(int range) {
                    builder.clientTrackingRange(range);
                    return this;
                }

                @Override
                public EntityPropertiesBuilder updateInterval(int interval) {
                    builder.updateInterval(interval);
                    return this;
                }

                @Override
                public EntityPropertiesBuilder fireImmune() {
                    builder.fireImmune();
                    return this;
                }
            });
        };
    }

    @Override
    public <E extends Entity, P> NonNullFunction<EntityBuilder<E, P>, EntityBuilder<E, P>> entityProperties(NonNullConsumer<EntityPropertiesBuilder> factory) {
        return entry -> entry.properties(mapFactory(factory));
    }

    @Override
    public Ingredient createNBTIngredient(ItemStack stack) {
        return DataComponentIngredient.of(false, stack);
    }

    @Override
    public boolean isFakePlayer(LivingEntity entity) {
        return entity instanceof FakePlayer;
    }

    @Override
    public boolean createLoaded() {
        var classLoader = getClass().getClassLoader();
        try {
            var buildInfo = classLoader.loadClass("com.simibubi.create.CreateBuildInfo");
            var buildVersion = (String) buildInfo.getField("VERSION").get(null);
            return buildVersion.startsWith("6.");
        } catch (ClassNotFoundException | NoSuchFieldException | IllegalAccessException e) {
            return false;
        }
    }
}
