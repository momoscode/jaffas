package monnef.jaffas.ores;

import cpw.mods.fml.common.IWorldGenerator;
import monnef.jaffas.food.Log;
import monnef.jaffas.food.mod_jaffas_food;
import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenMinable;

import java.util.Random;

import static monnef.jaffas.food.mod_jaffas_food.blockSwitchgrass;
import static monnef.jaffas.food.mod_jaffas_food.blockSwitchgrassID;

public class OresWorldGen implements IWorldGenerator {
    private final WorldGenMinable jaffarrolGenRich;
    private final WorldGenMinable jaffarrolGenSmall; // higher layers
    private final WorldGenMinable limsewGenSmall;
    private final WorldGenMinable limsewGenRich;    // higher layers

    private World world;
    private Random rand;
    private int posX;
    private int posZ;
    private IChunkProvider provider;
    private IChunkProvider generator;

    public OresWorldGen() {
        jaffarrolGenRich = new WorldGenMinable(mod_jaffas_ores.blockJaffarrolOre.blockID, 10);
        jaffarrolGenSmall = new WorldGenMinable(mod_jaffas_ores.blockJaffarrolOre.blockID, 4);
        limsewGenSmall = new WorldGenMinable(mod_jaffas_ores.blockLimsewOre.blockID, 2);
        limsewGenRich = new WorldGenMinable(mod_jaffas_ores.blockLimsewOre.blockID, 5);
    }

    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
        saveData(world, random, chunkX * 16, chunkZ * 16, chunkGenerator, chunkProvider);

        int dimensionId = world.provider.dimensionId;
        if (!mod_jaffas_food.isGenerationEnabled(dimensionId)) {
            return;
        }

        switch (dimensionId) {
            case -1:
                generateNether();
                break;
            case 0:
                generateAll();
                break;
            case 1:
                generateEnd();
                break;

            default:
                generateAll();
        }

    }

    private void saveData(World world, Random random, int chX, int chZ, IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
        this.world = world;
        this.rand = random;
        this.posX = chX;
        this.posZ = chZ;
        this.provider = chunkProvider;
        this.generator = chunkGenerator;
    }

    private void generateEnd() {
    }

    public void generateAll() {
        if (mod_jaffas_ores.generateOres) {
            generateOre(jaffarrolGenRich, 2, 0, 32);
            generateOre(jaffarrolGenSmall, 3, 33, 60);
            generateOre(limsewGenSmall, 2, 0, 32);
            generateOre(limsewGenRich, 3, 33, 70);
        }
        generateSwitchgrass(1, mod_jaffas_ores.switchgrassProbability);
    }

    private void generateSwitchgrass(int shots, float chance) {
        int toGen = 0;
        for (int i = 0; i < shots; i++) {
            if (rand.nextFloat() < chance) {
                toGen++;
            }
        }

        if (toGen <= 0) return;

        int tries = 50;
        int done = 0;
        do {
            int randPosX = posX + rand.nextInt(16);
            int randPosZ = posZ + rand.nextInt(16);

            if (generateSwitchgrass(randPosX, randPosZ, 4, 3)) {
                done++;
            }
            tries--;
        } while (tries > 0 && done < toGen);
    }

    private boolean generateSwitchgrass(int x, int z, int radius, int radiusVert) {
        int y = 127;
        Block block = null;
        do {
            block = Block.blocksList[world.getBlockId(x, y, z)];
            if (block != null && !block.isLeaves(world, x, y, z)) {
                break;
            }
            y--;
        } while (y > 0);

        if (y <= 0) return false;

        if (!blockSwitchgrass.canPlaceBlockAt(world, x, y + 1, z) || !isNearWater(world, x, y + 1, z, 3, 2) || wrongBlockNearby(world, x, y + 1, z, 2, 1)) {
            return false;
        }

        int hit = 0;
        Log.printDebug("spawned switchgrass group at " + x + "x" + y + "x" + z);
        for (int i = 0; i < 128; i++) {
            int cX = x + rand.nextInt(radius) - rand.nextInt(radius);
            int cY = y + rand.nextInt(radiusVert) - rand.nextInt(radiusVert);
            int cZ = z + rand.nextInt(radius) - rand.nextInt(radius);

            if (world.isAirBlock(cX, cY, cZ) && blockSwitchgrass.canPlaceBlockAt(world, cX, cY, cZ)) {
                world.setBlockAndMetadata(cX, cY, cZ, blockSwitchgrassID, 8);
                hit++;
            }
        }
        return hit > 0;
    }

    private boolean wrongBlockNearby(World world, int x, int y, int z, int radius, int radiusVertical) {
        for (int cx = x - radius; cx <= x + radius; cx++) {
            for (int cy = y - radiusVertical; cy <= y + radiusVertical; cy++) {
                for (int cz = z - radius; cz <= z + radius; cz++) {
                    int id = world.getBlockId(cx, cy, cz);
                    if (id == Block.snow.blockID || id == Block.ice.blockID || id == Block.blockSnow.blockID) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    private boolean isNearWater(World world, int x, int y, int z, int radius, int radiusVertical) {
        for (int cx = x - radius; cx <= x + radius; cx++) {
            for (int cy = y - radiusVertical; cy <= y + radiusVertical; cy++) {
                for (int cz = z - radius; cz <= z + radius; cz++) {
                    int id = world.getBlockId(cx, cy, cz);
                    if (id == Block.waterStill.blockID || id == Block.waterMoving.blockID) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    protected void generateOre(WorldGenMinable gen, int veinsCount, int minHeight, int maxHeight) {
        for (int i = 0; i < veinsCount; i++) {
            int randPosX = posX + rand.nextInt(16);
            int randPosZ = posZ + rand.nextInt(16);

            int randPosY = minHeight + rand.nextInt(maxHeight - minHeight);

            gen.generate(world, rand, randPosX, randPosY, randPosZ);
        }
    }

    private void generateNether() {
    }
}
