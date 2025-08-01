package galena.nirvana;

import net.minecraft.client.color.item.ItemColor;
import net.minecraft.world.item.alchemy.PotionUtils;

public class NirvanaClient {

    public static final ItemColor POTION_COLOR = (stack, i) -> {
        if (i != 1) return -1;
        return PotionUtils.getColor(stack);
    };

}
