package monnef.jaffas.food.block;

import monnef.jaffas.food.JaffasFood;
import monnef.jaffas.food.client.GuiHandler;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockRipeningBox extends BlockContainerJaffas {
    public BlockRipeningBox(int id, int index, Material material) {
        super(id, index, material);
    }

    @Override
    public TileEntity createNewTileEntity(World world) {
        return new TileRipeningBox();
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z,
                                    EntityPlayer player, int idk, float what, float these, float are) {
        TileEntity tileEntity = world.getBlockTileEntity(x, y, z);
        if (tileEntity == null || player.isSneaking()) {
            return false;
        }

        player.openGui(JaffasFood.instance, GuiHandler.GuiTypes.RIPENING_BOX.ordinal(), world, x, y, z);
        return true;
    }
}
