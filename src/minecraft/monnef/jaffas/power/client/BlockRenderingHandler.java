package monnef.jaffas.power.client;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import monnef.jaffas.power.block.TileEntityAntenna;
import monnef.jaffas.power.block.TileEntityGenerator;
import monnef.jaffas.power.block.TileEntityLightningConductor;
import monnef.jaffas.power.mod_jaffas_power;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.world.IBlockAccess;

public class BlockRenderingHandler implements ISimpleBlockRenderingHandler {
    public static final double POSITION_FIX = -0.5D;
    private static TileEntityGenerator generator = new TileEntityGenerator();
    private static TileEntityAntenna antenna = new TileEntityAntenna();
    private static TileEntityLightningConductor conductor = new TileEntityLightningConductor();

    @Override
    public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer) {
        //TODO: cache TEs?
        if (modelID == mod_jaffas_power.generator.getRenderType()) {
            TileEntityRenderer.instance.renderTileEntityAt(generator, POSITION_FIX, POSITION_FIX, POSITION_FIX, 0.0F);
        } else if (modelID == mod_jaffas_power.antenna.getRenderType()) {
            TileEntityRenderer.instance.renderTileEntityAt(antenna, POSITION_FIX, POSITION_FIX, POSITION_FIX, 0.0F);
        } else if (modelID == mod_jaffas_power.lightningConductor.getRenderType()) {
            TileEntityRenderer.instance.renderTileEntityAt(conductor, POSITION_FIX, POSITION_FIX, POSITION_FIX, 0.0F);
        }
    }

    @Override
    public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {
        return true;
    }

    @Override
    public boolean shouldRender3DInInventory() {
        return true;
    }

    @Override
    public int getRenderId() {
        return mod_jaffas_power.generator.getRenderType();
    }
}
