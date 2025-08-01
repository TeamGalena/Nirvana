package galena.nirvana;

import net.minecraft.client.color.item.ItemColor;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.alchemy.PotionContents;

public class NirvanaClient {

    public static final ItemColor POTION_COLOR = (stack, i) -> {
        if (i != 1) return -1;
        return stack.getOrDefault(DataComponents.POTION_CONTENTS, PotionContents.EMPTY).getColor();
    };

}
