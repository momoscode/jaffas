/*
 * Jaffas and more!
 * author: monnef
 */

package monnef.jaffas.food.block;

import cpw.mods.fml.relauncher.ReflectionHelper;
import monnef.core.MonnefCorePlugin;
import monnef.core.utils.BlockHelper;
import monnef.jaffas.food.JaffasFood;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.world.World;

import java.util.Random;

import static monnef.jaffas.food.JaffasFood.Log;

public class BlockSwitchgrassSolid extends BlockJDirectional {
    public BlockSwitchgrassSolid(int id, int textureStart, int texturesCountPerSet) {
        super(id, textureStart, texturesCountPerSet, Material.grass, TextureMappingType.LOG_LIKE);
        Block.setBurnProperties(id, 15, 100);
        setStepSound(Block.soundGrassFootstep);
        setResistance(5);
        setHardness(0.5f);
        setTickRandomly(true);
    }

    @Override
    public void updateTick(World world, int x, int y, int z, Random random) {
        super.updateTick(world, x, y, z, random);

        if (world.isRemote) return;
        if (!JaffasFood.slimeSpawningEnabled) return;
        if (!MonnefCorePlugin.debugEnv && random.nextFloat() > 0.2) return;
        if (validStructure(world, x, y, z) &&
                (!world.isDaytime() || world.isRaining()) &&
                lightCheck(world, x, y, z)) {
            boolean isRainingOnMe = world.canLightningStrikeAt(x, y + 3, z);

            // failure chance
            if (!isRainingOnMe && random.nextFloat() > 0.3) {
                int sx = 0, sz = 0;
                if (random.nextBoolean()) {
                    sx = random.nextBoolean() ? -1 : 1;
                } else {
                    sz = random.nextBoolean() ? -1 : 1;
                }
                BlockHelper.setBlock(world, x + sx, y + 1, z + sz, 0);

                Log.printDebug(String.format("Cube damaged - %d %d %d", x, y, z));
                return;
            }

            // remove all blocks
            for (int xx = x - 1; xx <= x + 1; xx++) {
                for (int yy = y; yy <= y + 2; yy++) {
                    for (int zz = z - 1; zz <= z + 1; zz++) {
                        BlockHelper.setBlock(world, xx, yy, zz, 0);
                    }
                }
            }

            // spawn a big slime
            EntitySlime slime = new EntitySlime(world);
            try {
                ReflectionHelper.findMethod(EntitySlime.class, slime, new String[]{"setSlimeSize", "func_70799_a"}, new Class[]{int.class}).invoke(slime, 4);
            } catch (Exception e) {
                Log.printSevere("Problem in slime spawning.");
                e.printStackTrace();
            }

            slime.setPosition(x, y + 1, z);
            world.spawnEntityInWorld(slime);
        }
    }

    private boolean lightCheck(World world, int x, int y, int z) {
        if (world.getBlockLightValue(x, y + 3, z) > 8) return false;
        if (world.getBlockLightValue(x + 2, y, z) > 8) return false;
        if (world.getBlockLightValue(x, y + 2, z) > 8) return false;
        if (world.getBlockLightValue(x - 2, y, z) > 8) return false;
        if (world.getBlockLightValue(x, y - 2, z) > 8) return false;
        return true;
    }

    private boolean validStructure(World world, int x, int y, int z) {
        for (int xx = x - 1; xx <= x + 1; xx++) {
            for (int yy = y; yy <= y + 2; yy++) {
                for (int zz = z - 1; zz <= z + 1; zz++) {
                    int currentBlockID = world.getBlockId(xx, yy, zz);
                    if (xx == x && yy == y + 1 && zz == z) {
                        //center
                        if (currentBlockID != Block.waterStill.blockID) {
                            return false;
                        }
                    } else {
                        if (currentBlockID != blockID) {
                            return false;
                        }
                    }
                }
            }
        }

        return true;
    }
}
