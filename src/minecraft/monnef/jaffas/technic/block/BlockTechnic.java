package monnef.jaffas.technic.block;

import monnef.core.base.BlockMonnefCore;
import monnef.jaffas.technic.jaffasTechnic;
import net.minecraft.block.material.Material;

public class BlockTechnic extends BlockMonnefCore {

    public BlockTechnic(int id, int textureID, Material material) {
        super(id, textureID, material);
        setCreativeTab(jaffasTechnic.CreativeTab);
    }

    public String getTextureFile() {
        return jaffasTechnic.textureFile;
    }
}