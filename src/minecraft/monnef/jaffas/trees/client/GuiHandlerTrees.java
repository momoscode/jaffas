/*
 * Jaffas and more!
 * author: monnef
 */

package monnef.jaffas.trees.client;

import cpw.mods.fml.common.network.IGuiHandler;
import monnef.jaffas.trees.block.ContainerFruitCollector;
import monnef.jaffas.trees.block.TileFruitCollector;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class GuiHandlerTrees implements IGuiHandler {
    //returns an instance of the Container you made earlier
    @Override
    public Object getServerGuiElement(int id, EntityPlayer player, World world,
                                      int x, int y, int z) {
        TileEntity tileEntity = world.getBlockTileEntity(x, y, z);
        if (tileEntity instanceof TileFruitCollector) {
            return new ContainerFruitCollector(player.inventory, (TileFruitCollector) tileEntity);
        }
        return null;
    }

    //returns an instance of the Gui you made earlier
    @Override
    public Object getClientGuiElement(int id, EntityPlayer player, World world,
                                      int x, int y, int z) {
        TileEntity tileEntity = world.getBlockTileEntity(x, y, z);
        if (tileEntity instanceof TileFruitCollector) {
            return new GuiFruitCollector(player.inventory, (TileFruitCollector) tileEntity);
        }
        return null;
    }
}

