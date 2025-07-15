package galena.nirvana.world.item;

import com.google.common.base.Suppliers;
import galena.nirvana.NirvanaConstants;
import galena.nirvana.index.NirvanaItems;
import java.util.function.Supplier;
import net.minecraft.core.Holder;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterials;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DispenserBlock;

public class DeerStalkerItem extends Item implements ArmorLike {

    private final Supplier<ItemAttributeModifiers> modifiers;

    public DeerStalkerItem(Properties properties) {
        super(properties);
        DispenserBlock.registerBehavior(this, ArmorItem.DISPENSE_ITEM_BEHAVIOR);

        this.modifiers = Suppliers.memoize(() -> {
            var builder = ItemAttributeModifiers.builder();
            builder.add(Attributes.ARMOR, new AttributeModifier(NirvanaConstants.createId("armor"), ArmorMaterials.LEATHER.value().getDefense(ArmorItem.Type.HELMET), AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.HEAD);
            builder.add(Attributes.ARMOR_TOUGHNESS, new AttributeModifier(NirvanaConstants.createId("toughness"), ArmorMaterials.LEATHER.value().toughness(), AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.HEAD);
            return builder.build();
        });
    }

    public boolean isValidRepairItem(ItemStack stack, ItemStack with) {
        return NirvanaItems.HEMP_CLOTH.isIn(with);
    }

    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        return swapWithEquipmentSlot(this, level, player, hand);
    }

    @Override
    public int getEnchantmentValue() {
        return ArmorMaterials.LEATHER.value().enchantmentValue();
    }

    @Override
    public Holder<SoundEvent> getEquipSound() {
        return ArmorMaterials.LEATHER.value().equipSound();
    }

    @Override
    public EquipmentSlot getEquipmentSlot() {
        return EquipmentSlot.HEAD;
    }

    @Override
    public ItemAttributeModifiers getDefaultAttributeModifiers() {
        return modifiers.get();
    }

}
