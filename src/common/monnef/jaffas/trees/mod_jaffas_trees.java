package monnef.jaffas.trees;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.network.IGuiHandler;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import forestry.api.cultivation.CropProviders;
import monnef.core.Version;
import monnef.jaffas.food.mod_jaffas;
import net.minecraft.server.MinecraftServer;
import net.minecraft.src.*;
import net.minecraftforge.common.Configuration;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.logging.Level;

import static monnef.jaffas.food.mod_jaffas.getJaffaItem;

@Mod(modid = "moen-jaffas-trees", name = "Jaffas - trees", version = Version.Version, dependencies = "required-after:moen-jaffas;required-after:moen-monnef-core")
@NetworkMod(clientSideRequired = true, serverSideRequired = false, channels = mod_jaffas_trees.channel, packetHandler = PacketHandler.class)
public class mod_jaffas_trees {
    private static MinecraftServer server;
    public static boolean bonemealingAllowed;

    public static final String channel = "jaffas-02";

    public static final String[] treeTypes = new String[]{"normal", "apple", "cocoa", "vanilla", "lemon", "orange", "plum"};
    public static final String[] seedsNames = new String[]{"[UNUSED]", "Apple Seeds", "Cocoa Seeds", "Vanilla Seeds", "Lemon Seeds", "Orange Seeds", "Plum Seeds"};

    private static IGuiHandler guiHandler;

    public static BlockFruitCollector blockFruitCollector;
    public static int blockFruitCollectorID;

    public static fruitType getActualLeavesType(Block block, int blockMetadata) {
        BlockFruitLeaves b = (BlockFruitLeaves) block;
        return getActualLeavesType(b.serialNumber, blockMetadata);
    }

    public static fruitType getActualLeavesType(int serialNumber, int blockMetadata) {
        int index = serialNumber * 4 + blockMetadata;
        fruitType fruitType = mod_jaffas_trees.fruitType.indexToFruitType(index);
        if (fruitType == null) {
            throw new RuntimeException("fruit not found!");
        }

        return fruitType;
    }

    public static enum fruitType {
        Normal(0), Apple(1), Cocoa(2), Vanilla(3), Lemon(4), Orange(5), Plum(6);
        private int value;
        private int blockNumber;
        private int metaNumber;

        fruitType(int value) {
            this.value = value;
            this.blockNumber = value % 4;
            this.metaNumber = value / 4;

            mapper.indexToFruitMap.put(value, this);
        }

        public int getValue() {
            return value;
        }

        public int getBlockNumber() {
            return blockNumber;
        }

        public static fruitType indexToFruitType(int index) {
            return mapper.indexToFruitMap.get(index);
        }

        private static class mapper {
            private static HashMap<Integer, fruitType> indexToFruitMap;

            static {
                indexToFruitMap = new HashMap<Integer, fruitType>();
            }
        }
    }

    @Mod.Instance("moen-jaffas-trees")
    public static mod_jaffas_trees instance;

    public static ArrayList<LeavesInfo> leavesList = new ArrayList<LeavesInfo>();

    public static final int leavesBlocksAllocated = 3;
    public static final int leavesTypesCount = 6;

    public static int startID;
    private int actualID;

    public static boolean debug;

    private int itemLemonID;
    private int itemOrangeID;
    private int itemPlumID;
    public static ItemJaffaFruit itemLemon;
    public static ItemJaffaFruit itemOrange;
    public static ItemJaffaFruit itemPlum;

    private int itemDebugID;
    public static ItemJaffaTreeDebugTool itemDebug;

    private int itemStickID;
    public static ItemJaffaT itemStick;
    private int itemRodID;
    public static ItemJaffaT itemRod;
    private int itemFruitPickerID;
    public static ItemJaffaT itemFruitPicker;
    private int itemFruitPickerHeadID;
    public static ItemJaffaT itemFruitPickerHead;

    public static enum bushType {
        Coffee, Strawberry, Onion, Paprika, Raspberry, Tomato;
    }

    public static EnumMap<bushType, BushInfo> BushesList = new EnumMap<bushType, BushInfo>(bushType.class);

    public final static String textureFile = "/jaffas_02.png";

    private int getID() {
        return this.actualID++;
    }

    private int getBlockID() {
        return getID()/* + 256*/;
    }

    public mod_jaffas_trees() {
        instance = this;
    }

    @SidedProxy(clientSide = "monnef.jaffas.trees.ClientProxyTutorial", serverSide = "monnef.jaffas.trees.CommonProxyTutorial")
    public static CommonProxyTutorial proxy;

    @Mod.PreInit
    public void PreLoad(FMLPreInitializationEvent event) {
        //this.startID = mod_jaffas.topDefaultID;
        this.startID = 3500;
        this.actualID = this.startID;

        PopulateBushInfo();

        Configuration config = new Configuration(
                event.getSuggestedConfigurationFile());

        try {
            config.load();

            if (this.actualID <= 0) {
                throw new RuntimeException("unable to get ID from parent");
            }

            itemLemonID = config.getOrCreateIntProperty("lemon", Configuration.CATEGORY_ITEM, getID()).getInt();
            itemOrangeID = config.getOrCreateIntProperty("orange", Configuration.CATEGORY_ITEM, getID()).getInt();
            itemPlumID = config.getOrCreateIntProperty("plum", Configuration.CATEGORY_ITEM, getID()).getInt();

            for (int i = 0; i < leavesBlocksAllocated; i++) {
                int leavesID = config.getOrCreateIntProperty("fruit leaves " + i, Configuration.CATEGORY_BLOCK, getBlockID()).getInt();
                int saplingID = config.getOrCreateIntProperty("fruit tree sapling " + i, Configuration.CATEGORY_BLOCK, getBlockID()).getInt();
                int seedsID = config.getOrCreateIntProperty("fruit seeds " + i, Configuration.CATEGORY_ITEM, getID()).getInt();

                leavesList.add(new LeavesInfo(leavesID, saplingID, seedsID, i));
            }

            for (EnumMap.Entry<bushType, BushInfo> entry : BushesList.entrySet()) {
                BushInfo info = entry.getValue();

                info.itemSeedsID = config.getOrCreateIntProperty(info.getSeedsConfigName(), Configuration.CATEGORY_ITEM, getID()).getInt();
                info.blockID = config.getOrCreateIntProperty(info.getBlockConfigName(), Configuration.CATEGORY_BLOCK, getBlockID()).getInt();
                info.itemFruitID = config.getOrCreateIntProperty(info.getFruitConfigName(), Configuration.CATEGORY_ITEM, getID()).getInt();
            }

            blockFruitCollectorID = config.getOrCreateIntProperty("fruit collector", Configuration.CATEGORY_BLOCK, getBlockID()).getInt();
            itemDebugID = config.getOrCreateIntProperty("debug tool", Configuration.CATEGORY_ITEM, getID()).getInt();

            itemStickID = config.getOrCreateIntProperty("stick", Configuration.CATEGORY_ITEM, getID()).getInt();
            itemRodID = config.getOrCreateIntProperty("rod", Configuration.CATEGORY_ITEM, getID()).getInt();
            itemFruitPickerID = config.getOrCreateIntProperty("fruit picker", Configuration.CATEGORY_ITEM, getID()).getInt();
            itemFruitPickerHeadID = config.getOrCreateIntProperty("fruit picker head", Configuration.CATEGORY_ITEM, getID()).getInt();

            debug = config.getOrCreateBooleanProperty("debug", Configuration.CATEGORY_GENERAL, false).getBoolean(false);
            bonemealingAllowed = config.getOrCreateBooleanProperty("bonemeal", Configuration.CATEGORY_GENERAL, false).getBoolean(false);

        } catch (Exception e) {
            FMLLog.log(Level.SEVERE, e, "Mod Jaffas (trees) can't read config file.");
        } finally {
            config.save();
        }
    }

    private void PopulateBushInfo() {
        AddBushInfo(bushType.Coffee, "coffee", "Coffee Seeds", 34, "Coffee Plant", 96, "Coffee Beans", 128, null, 2, 1);
        AddBushInfo(bushType.Strawberry, "strawberry", "Strawberry Seeds", 34, "Strawberry Plant", 99, "Strawberry", 129, null, 2, 1);

        AddBushInfo(bushType.Onion, "onion", "Onion Seeds", 34, "Onion Plant", 102, "Onion", 130, null, 2, 1);
        AddBushInfo(bushType.Paprika, "paprika", "Pepper Seeds", 34, "Pepper Plant", 105, "Pepper", 131, null, 2, 1);
        AddBushInfo(bushType.Raspberry, "raspberry", "Raspberry Seeds", 34, "Raspberry Plant", 108, "Raspberry", 132, null, 2, 1);
        AddBushInfo(bushType.Tomato, "tomato", "Tomato Seeds", 34, "Tomato Plant", 111, "Tomato", 133, null, 2, 1);
    }

    private void constructItemsInBushInfo() {
        for (EnumMap.Entry<bushType, BushInfo> entry : BushesList.entrySet()) {
            BushInfo info = entry.getValue();

            ItemJaffaSeeds seeds = new ItemJaffaSeeds(info.itemSeedsID, info.blockID, Block.tilledField.blockID);
            seeds.setItemName(info.getSeedsLanguageName()).setIconIndex(info.seedsTexture);
            LanguageRegistry.addName(seeds, info.seedsTitle);
            info.itemSeeds = seeds;

            ItemJaffaBerry fruit = new ItemJaffaBerry(info.itemFruitID);
            fruit.setItemName(info.getFruitLanguageName()).setIconIndex(info.fruitTexture).setTabToDisplayOn(CreativeTabs.tabMaterials);
            LanguageRegistry.addName(fruit, info.fruitTitle);
            info.itemFruit = fruit;

            BlockJaffaCrops crops = new BlockJaffaCrops(info.blockID, info.plantTexture, info.phases, info.product == null ? info.itemFruit : info.product, info.itemSeeds, info.renderer);
            crops.setBlockName(info.getPlantLanguageName());
            GameRegistry.registerBlock(crops);
            LanguageRegistry.addName(crops, info.plantTitle);
            info.block = crops;

        }
    }

    private void AddBushInfo(bushType type, String name, String seedsTitle, int seedsTexture, String plantTitle, int plantTexture, String fruitTitle, int fruitTexture, Item product, int phases, int renderer) {
        BushInfo info = new BushInfo();

        info.name = name;
        info.seedsTitle = seedsTitle;
        info.seedsTexture = seedsTexture;
        info.plantTitle = plantTitle;
        info.plantTexture = plantTexture;
        info.fruitTitle = fruitTitle;
        info.fruitTexture = fruitTexture;
        info.product = product;
        info.phases = phases;
        info.renderer = renderer;
        info.type = type;

        BushesList.put(type, info);
    }

    @Mod.Init
    public void load(FMLInitializationEvent event) {
        guiHandler = new GuiHandlerTrees();
        NetworkRegistry.instance().registerGuiHandler(this, guiHandler);

        AddFruitTreesSequence(0, 0, 32, 4);
        AddFruitTreesSequence(1, 4, 32 + 4, 3);

        GameRegistry.registerTileEntity(TileEntityFruitLeaves.class, "fruitLeaves");
        GameRegistry.registerTileEntity(TileEntityJaffaCrops.class, "jaffaCrops");

        itemLemon = new ItemJaffaFruit(itemLemonID);
        itemLemon.setItemName("lemon").setIconCoord(4, 4);
        LanguageRegistry.addName(itemLemon, "Lemon");

        itemOrange = new ItemJaffaFruit(itemOrangeID);
        itemOrange.setItemName("orange").setIconCoord(5, 4);
        LanguageRegistry.addName(itemOrange, "Orange");

        itemPlum = new ItemJaffaFruit(itemPlumID);
        itemPlum.setItemName("plum").setIconCoord(6, 4);
        LanguageRegistry.addName(itemPlum, "Plum");

        constructItemsInBushInfo();

        blockFruitCollector = new BlockFruitCollector(blockFruitCollectorID);
        GameRegistry.registerBlock(blockFruitCollector);
        LanguageRegistry.addName(blockFruitCollector, "Fruit Collector");
        GameRegistry.registerTileEntity(TileEntityFruitCollector.class, "fruitcollector");

        itemDebug = new ItemJaffaTreeDebugTool(itemDebugID);
        itemDebug.setMaxStackSize(1).setItemName("jaffaTreeDebug").setIconCoord(13, 0);
        LanguageRegistry.addName(itemDebug, "Jaffa Tree's Debug Tool");

        itemStick = new ItemJaffaT(itemStickID);
        itemStick.setItemName("stickImpregnated").setIconIndex(4);
        LanguageRegistry.addName(itemStick, "Impregnated Stick");

        itemRod = new ItemJaffaT(itemRodID);
        itemRod.setItemName("rod").setIconIndex(5).setMaxStackSize(1).setMaxDamage(128);
        LanguageRegistry.addName(itemRod, "Reinforced Rod");

        itemFruitPickerHead = new ItemJaffaT(itemFruitPickerHeadID);
        itemFruitPickerHead.setItemName("fruitPickerHead").setIconIndex(3);
        LanguageRegistry.addName(itemFruitPickerHead, "Head of Fruit Picker");

        itemFruitPicker = new ItemJaffaT(itemFruitPickerID);
        itemFruitPicker.setItemName("fruitPicker").setIconIndex(6).setMaxStackSize(1).setMaxDamage(256);
        LanguageRegistry.addName(itemFruitPicker, "Fruit Picker");

        installRecipes();

        // texture stuff
        proxy.registerRenderThings();

        //GameRegistry.registerCraftingHandler(new JaffaCraftingHandler());

        //forestry stuff
        CropProviders.cerealCrops.add(new JaffaCropProvider());

        System.out.println("trees module from 'Jaffas and more!' initialized");
    }

    private void AddFruitTreesSequence(int i, int leavesTexture, int seedTexture, int subCount) {
        LeavesInfo leaves = leavesList.get(i);
        leaves.leavesBlock = new BlockFruitLeaves(leaves.leavesID, leavesTexture, subCount);
        leaves.leavesBlock.serialNumber = i;
        leaves.leavesBlock.setLeavesRequiresSelfNotify().setBlockName("fruitLeaves" + i).setCreativeTab(CreativeTabs.tabDeco).setHardness(0.2F).setLightOpacity(1).setStepSound(Block.soundGrassFootstep);
        GameRegistry.registerBlock(leaves.leavesBlock);
        LanguageRegistry.addName(leaves.leavesBlock, "Leaves");

        leaves.saplingBlock = new BlockFruitSapling(leaves.saplingID, 15);
        leaves.saplingBlock.serialNumber = i;
        leaves.saplingBlock.setBlockName("fruitSapling" + i).setCreativeTab(CreativeTabs.tabMisc);
        GameRegistry.registerBlock(leaves.saplingBlock);
        LanguageRegistry.addName(leaves.saplingBlock, "Fruit Sapling");

        for (int j = 0; j < subCount; j++) {
            LanguageRegistry.instance().addStringLocalization("item.fruitSeeds" + i + "." + j + ".name", seedsNames[j + i * 4]);
        }
        leaves.seedsItem = new ItemFruitSeeds(leaves.seedsID, leaves.saplingID, seedTexture, subCount);
        leaves.seedsItem.setItemName("fruitSeeds" + i);
        leaves.seedsItem.serialNumber = i;
        LanguageRegistry.addName(leaves.seedsItem, "Fruit Seeds");
    }

    @Mod.ServerStarting
    public void serverStarting(FMLServerStartingEvent event) {
        server = ModLoader.getMinecraftServerInstance();
        ICommandManager commandManager = server.getCommandManager();
        ServerCommandManager serverCommandManager = ((ServerCommandManager) commandManager);
        addCommands(serverCommandManager);
    }

    private void addCommands(ServerCommandManager manager) {
        if (debug) {
            manager.registerCommand(new CommandFruitDebug());
        }
    }

    private void installRecipes() {
        GameRegistry.addShapelessRecipe(new ItemStack(getJaffaItem(mod_jaffas.JaffaItem.lemons)),
                new ItemStack(mod_jaffas_trees.itemLemon),
                new ItemStack(mod_jaffas_trees.itemLemon),
                new ItemStack(mod_jaffas_trees.itemLemon),
                new ItemStack(mod_jaffas_trees.itemLemon));
        GameRegistry.addShapelessRecipe(new ItemStack(getJaffaItem(mod_jaffas.JaffaItem.oranges)),
                new ItemStack(mod_jaffas_trees.itemOrange),
                new ItemStack(mod_jaffas_trees.itemOrange),
                new ItemStack(mod_jaffas_trees.itemOrange),
                new ItemStack(mod_jaffas_trees.itemOrange));
        GameRegistry.addShapelessRecipe(new ItemStack(getJaffaItem(mod_jaffas.JaffaItem.plums)),
                new ItemStack(mod_jaffas_trees.itemPlum),
                new ItemStack(mod_jaffas_trees.itemPlum),
                new ItemStack(mod_jaffas_trees.itemPlum),
                new ItemStack(mod_jaffas_trees.itemPlum));

        GameRegistry.addShapelessRecipe(new ItemStack(getJaffaItem(mod_jaffas.JaffaItem.strawberries)),
                new ItemStack(BushesList.get(bushType.Strawberry).itemFruit),
                new ItemStack(BushesList.get(bushType.Strawberry).itemFruit),
                new ItemStack(BushesList.get(bushType.Strawberry).itemFruit),
                new ItemStack(BushesList.get(bushType.Strawberry).itemFruit));
        GameRegistry.addShapelessRecipe(new ItemStack(getJaffaItem(mod_jaffas.JaffaItem.raspberries)),
                new ItemStack(BushesList.get(bushType.Raspberry).itemFruit),
                new ItemStack(BushesList.get(bushType.Raspberry).itemFruit),
                new ItemStack(BushesList.get(bushType.Raspberry).itemFruit),
                new ItemStack(BushesList.get(bushType.Raspberry).itemFruit));

        GameRegistry.addSmelting(BushesList.get(bushType.Coffee).itemFruit.shiftedIndex, new ItemStack(getJaffaItem(mod_jaffas.JaffaItem.coffeeRoasted)), 0.5F);

        GameRegistry.addRecipe(new ItemStack(getJaffaItem(mod_jaffas.JaffaItem.paprikaChopped)), "K", "M", 'K', new ItemStack(getJaffaItem(mod_jaffas.JaffaItem.knifeKitchen), 1, -1), 'M', new ItemStack(BushesList.get(bushType.Paprika).itemFruit));
        GameRegistry.addRecipe(new ItemStack(getJaffaItem(mod_jaffas.JaffaItem.tomatoChopped)), "K", "M", 'K', new ItemStack(getJaffaItem(mod_jaffas.JaffaItem.knifeKitchen), 1, -1), 'M', new ItemStack(BushesList.get(bushType.Tomato).itemFruit));

        GameRegistry.addRecipe(new ItemStack(blockFruitCollector), "IDI", "DRD", "IGI",
                'I', new ItemStack(Block.blockSteel), 'D', new ItemStack(Item.diamond), 'R', new ItemStack(Block.torchRedstoneActive), 'G', new ItemStack(Block.blockGold));

        installFruitSeedsRecipes();
    }

    private void installFruitSeedsRecipes() {
        for (int i = 1; i <= leavesTypesCount; i++) {
            int type = i;
            ItemFruitSeeds item = mod_jaffas_trees.leavesList.get(type / 4).seedsItem;
            int meta = type % 4;

            ItemStack seed = new ItemStack(item, 1, meta);

            ItemFromFruitResult info = TileEntityFruitLeaves.getItemFromFruit(fruitType.indexToFruitType(i));

            if (info.exception != null) {
                throw (RuntimeException) info.exception;
            }

            if (info.getMessage() != null) {
                System.err.println(info.getMessage());
                throw new RuntimeException("unknown error during fruits->seeds recipe contruction");
            }

            ItemStack fruit = info.getStack();

            if (fruit == null) {
                throw new RuntimeException("null in fruit!");
            }

            seed.stackSize = 2;
            GameRegistry.addShapelessRecipe(seed,
                    fruit.copy(), fruit.copy(), fruit.copy(),
                    fruit.copy(), fruit.copy(), fruit.copy(),
                    fruit.copy(), fruit.copy(), fruit.copy()
            );
        }
    }
}
