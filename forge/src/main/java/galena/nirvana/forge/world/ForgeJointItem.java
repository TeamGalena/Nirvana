package galena.nirvana.forge.world;

import galena.nirvana.client.CustomItemModel;
import galena.nirvana.forge.client.CustomModelExtensions;
import galena.nirvana.world.item.JointItem;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;

import java.util.function.Consumer;

public class ForgeJointItem extends JointItem {

    public ForgeJointItem(Properties properties) {
        super(properties);
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new CustomModelExtensions(CustomItemModel.JOINT));
    }
}
