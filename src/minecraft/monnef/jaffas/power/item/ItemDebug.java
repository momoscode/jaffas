/*
 * Jaffas and more!
 * author: monnef
 */

package monnef.jaffas.power.item;

import buildcraft.api.power.IPowerProvider;
import monnef.jaffas.power.api.IMachineTool;
import monnef.jaffas.power.block.common.TileEntityMachine;
import net.minecraft.entity.player.EntityPlayer;

public class ItemDebug extends ItemPower implements IMachineTool {
    private EntityPlayer player;

    public ItemDebug(int id, int textureIndex) {
        super(id, textureIndex);
        setUnlocalizedName("debugPower");
    }

    @Override
    public boolean onMachineClick(TileEntityMachine machine, EntityPlayer player, int side) {
        this.player = player;

        if (machine == null) {
            print("TE is null");
        } else {
            IPowerProvider provider = machine.getPowerProvider();
            if (provider != null) {
                print(String.format("%s: %d/%d(%d)", machine.getPosition().format(), Math.round(provider.getEnergyStored()), provider.getMaxEnergyStored(), provider.getActivationEnergy()));
            }

            if (machine.getRotation() != null) {
                print("dir: [" + machine.getRotation().ordinal() + "] " + machine.getRotation().toString() + (machine.getMachineBlock().supportRotation() ? "" : "(doesn't support rotation)"));
            }

            machine.onItemDebug(player);
        }

        return true;
    }

    @Override
    public boolean renderPowerLabels(EntityPlayer p) {
        return true;
    }

    private void print(String message) {
        player.addChatMessage(message);
    }
}
