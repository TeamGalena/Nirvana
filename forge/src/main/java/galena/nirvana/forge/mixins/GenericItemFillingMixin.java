package galena.nirvana.forge.mixins;

import com.simibubi.create.AllDataComponents;
import com.simibubi.create.AllFluids;
import com.simibubi.create.content.fluids.potion.PotionFluid;
import com.simibubi.create.content.fluids.potion.PotionFluidHandler;
import com.simibubi.create.content.fluids.transfer.GenericItemFilling;
import galena.nirvana.index.NirvanaItems;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.fluids.FluidStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = GenericItemFilling.class, remap = false)
public class GenericItemFillingMixin {

    @Unique
    private static boolean nirvana$isValidPotion(FluidStack fluid) {
        return fluid.getFluid().isSame(AllFluids.POTION.get())
                && fluid.get(AllDataComponents.POTION_FLUID_BOTTLE_TYPE) == PotionFluid.BottleType.REGULAR;
    }

    @Inject(require = 0, cancellable = true, at = @At("HEAD"), method = "canItemBeFilled(Lnet/minecraft/world/level/Level;Lnet/minecraft/world/item/ItemStack;)Z")
    private static void canBongBeFilled(Level world, ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
        if (NirvanaItems.BONG.isIn(stack)) cir.setReturnValue(true);
    }

    @Inject(require = 0, cancellable = true, at = @At("HEAD"), method = "getRequiredAmountForItem(Lnet/minecraft/world/level/Level;Lnet/minecraft/world/item/ItemStack;Lnet/neoforged/neoforge/fluids/FluidStack;)I")
    private static void getRequiredAmountForBong(Level world, ItemStack stack, FluidStack availableFluid, CallbackInfoReturnable<Integer> cir) {
        if (NirvanaItems.BONG.isIn(stack) && nirvana$isValidPotion(availableFluid)) {
            cir.setReturnValue(PotionFluidHandler.getRequiredAmountForFilledBottle(stack, availableFluid));
        }
    }

    @Inject(require = 0, cancellable = true, at = @At("HEAD"), method = "fillItem(Lnet/minecraft/world/level/Level;ILnet/minecraft/world/item/ItemStack;Lnet/neoforged/neoforge/fluids/FluidStack;)Lnet/minecraft/world/item/ItemStack;")
    private static void fillBong(Level world, int requiredAmount, ItemStack stack, FluidStack availableFluid, CallbackInfoReturnable<ItemStack> cir) {
        if (NirvanaItems.BONG.isIn(stack) && nirvana$isValidPotion(availableFluid)) {
            var bongStack = NirvanaItems.POTION_BONG.asStack();
            bongStack.set(DataComponents.POTION_CONTENTS, availableFluid.get(DataComponents.POTION_CONTENTS));
            stack.shrink(1);
            cir.setReturnValue(bongStack);
        }
    }

}
