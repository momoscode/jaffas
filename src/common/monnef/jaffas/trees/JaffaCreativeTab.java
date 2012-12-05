package monnef.jaffas.trees;

import monnef.jaffas.food.ItemManager;
import monnef.jaffas.food.JaffaItem;
import net.minecraft.src.CreativeTabs;
import net.minecraft.src.ItemStack;

public class JaffaCreativeTab extends CreativeTabs {
    public JaffaCreativeTab(String label) {
        super(label);
    }

    @Override
    public ItemStack getIconItemStack() {
        return new ItemStack(ItemManager.getItem(JaffaItem.oranges));
    }
}