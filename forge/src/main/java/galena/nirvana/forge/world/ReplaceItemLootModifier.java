package galena.nirvana.forge.world;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.util.List;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.common.loot.LootModifier;
import org.jetbrains.annotations.NotNull;

public class ReplaceItemLootModifier extends LootModifier {

    public static final MapCodec<ReplaceItemLootModifier> CODEC = RecordCodecBuilder.mapCodec(builder ->
        codecStart(builder).and(
                Codec.list(BuiltInRegistries.ITEM.byNameCodec()).fieldOf("items").forGetter(it -> it.items)
        ).apply(builder, ReplaceItemLootModifier::new)
    );

    private final List<Item> items;

    public ReplaceItemLootModifier(LootItemCondition[] conditions, List<Item> items) {
        super(conditions);
        this.items = items;
    }

    @Override
    protected @NotNull ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
        var item = items.get(context.getRandom().nextInt(items.size()));
        return ObjectArrayList.of(new ItemStack(item));
    }

    @Override
    public MapCodec<? extends IGlobalLootModifier> codec() {
        return CODEC;
    }

}
