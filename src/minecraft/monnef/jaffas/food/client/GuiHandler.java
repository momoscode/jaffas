/*
 * Jaffas and more!
 * author: monnef
 */

package monnef.jaffas.food.client;

import cpw.mods.fml.common.network.IGuiHandler;
import monnef.jaffas.food.block.ContainerBoard;
import monnef.jaffas.food.block.ContainerFridge;
import monnef.jaffas.food.block.TileEntityBoard;
import monnef.jaffas.food.block.TileEntityFridge;
import monnef.jaffas.technic.block.ContainerCompost;
import monnef.jaffas.technic.block.TileEntityCompostCore;
import monnef.jaffas.technic.client.GuiCompost;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class GuiHandler implements IGuiHandler {
    public enum GuiTypes {
        FRIDGE, BOARD, COMPOST
    }

    @Override
    public Object getServerGuiElement(int id, EntityPlayer player, World world,
                                      int x, int y, int z) {
        TileEntity tileEntity = world.getBlockTileEntity(x, y, z);

        if (tileEntity instanceof TileEntityFridge) {
            return new ContainerFridge(player.inventory, (TileEntityFridge) tileEntity);
        } else if (tileEntity instanceof TileEntityBoard) {
            return new ContainerBoard(player.inventory, (TileEntityBoard) tileEntity);
        } else if (tileEntity instanceof TileEntityCompostCore) {
            return new ContainerCompost(player.inventory, (TileEntityCompostCore) tileEntity);
        }

        return null;
    }

    @Override
    public Object getClientGuiElement(int id, EntityPlayer player, World world,
                                      int x, int y, int z) {
        TileEntity tileEntity = world.getBlockTileEntity(x, y, z);

        if (tileEntity instanceof TileEntityFridge) {
            return new GuiFridge(player.inventory, (TileEntityFridge) tileEntity);
        } else if (tileEntity instanceof TileEntityBoard) {
            return new GuiBoard(player.inventory, (TileEntityBoard) tileEntity);
        } else if (tileEntity instanceof TileEntityCompostCore) {
            return new GuiCompost(player.inventory, (TileEntityCompostCore) tileEntity);
        }

        return null;
    }
}
