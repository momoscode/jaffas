/*
 * Jaffas and more!
 * author: monnef
 */

package monnef.jaffas.power.block.common;

import monnef.jaffas.technic.client.SlotOutput;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;

public abstract class ContainerBasicProcessingMachine extends ContainerMachine {
    public static final int SLOT_INPUT = 0;
    public static final int SLOT_OUTPUT = 1;

    // dummy constructor
    public ContainerBasicProcessingMachine() {
    }

    public ContainerBasicProcessingMachine(InventoryPlayer inventoryPlayer, TileEntityBasicProcessingMachine te) {
        super(inventoryPlayer, te);
    }

    @Override
    public void constructSlots(IInventory inv) {
        addSlotToContainer(new Slot(inv, SLOT_INPUT, 42, 35));
        addSlotToContainer(new SlotOutput(inv, SLOT_OUTPUT, 111, 35));
    }
}