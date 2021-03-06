/*
 * Jaffas and more!
 * author: monnef
 */

package monnef.jaffas.power.client;

import monnef.core.utils.ColorEnum;
import monnef.core.utils.ColorHelper;
import monnef.core.utils.GuiHelper;
import monnef.core.utils.MathHelper;
import monnef.jaffas.power.block.TileWindGenerator;
import monnef.jaffas.power.block.common.ContainerMachine;
import monnef.jaffas.power.client.common.GuiContainerMachine;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;

import java.util.List;

public class GuiContainerWindGenerator extends GuiContainerMachine {
    private final TileWindGenerator generator;

    public GuiContainerWindGenerator(InventoryPlayer inventoryPlayer, TileWindGenerator tileEntity, ContainerMachine container) {
        super(inventoryPlayer, tileEntity, container);
        this.generator = tileEntity;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3) {
        super.drawGuiContainerBackgroundLayer(par1, par2, par3);
        int value = MathHelper.scaleValue(generator.getTurbineSpeed(), TileWindGenerator.TURBINE_MAX_SPEED, ENERGY_BAR_HEIGHT - 2);
        drawBottomUpBar(x + ENERGY_BAR_X, y + ENERGY_BAR_Y, value, ENERGY_BAR_WIDTH, ENERGY_BAR_HEIGHT, ColorHelper.getColor(ColorEnum.LIGHT_BLUE), ColorHelper.getColor(ColorEnum.BLUE));
    }

    @Override
    public List<String> fillTooltips(GuiContainer gui, int mousex, int mousey, List<String> currenttip) {
        if (GuiHelper.isMouseInRect(this, mousex, mousey, ENERGY_BAR_X, ENERGY_BAR_Y, ENERGY_BAR_WIDTH, ENERGY_BAR_HEIGHT)) {
            currenttip.add("§2Speed:§r");
            currenttip.add(String.format(" §7%d§8/§7%d§r", generator.getTurbineSpeed(), TileWindGenerator.TURBINE_MAX_SPEED));
            currenttip.add("§2Production:§r");
            currenttip.add(String.format(" §7%.1f §8MJ/t§r", generator.getLastPowerProduction() / 10f));
        }

        return currenttip;
    }
}
