package monnef.jaffas.food;

import net.minecraft.src.EnumToolMaterial;
import net.minecraft.src.ItemSword;

public class ItemJaffaSword extends ItemSword {

    public ItemJaffaSword(int ItemID, EnumToolMaterial material) {
        super(ItemID, material);
        this.setCreativeTab(null);
    }

    public String getTextureFile() {
        return "/jaffas_01.png";
    }
}
