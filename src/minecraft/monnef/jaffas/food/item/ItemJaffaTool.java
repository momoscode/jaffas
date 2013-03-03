package monnef.jaffas.food.item;

import monnef.jaffas.food.mod_jaffas_food;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class ItemJaffaTool extends ItemJaffaBase implements IItemTool {
    public ItemJaffaTool(int id) {
        super(id);
        initialize();
    }

    public ItemJaffaTool(int id, int usageCount) {
        super(id);
        setUsageCount(usageCount);
        initialize();
    }

    private void setUsageCount(int usageCount) {
        setMaxDamage(usageCount);
    }

    private void initialize() {
        setMaxStackSize(1);
        this.setCreativeTab(mod_jaffas_food.CreativeTab);
    }

    @Override
    public Item Setup(int usageCount) {
        setUsageCount(usageCount);
        return this;
    }
}
