package jaffas.common;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.network.IGuiHandler;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import net.minecraft.server.MinecraftServer;
import net.minecraft.src.*;
import net.minecraftforge.common.Configuration;

import java.util.Hashtable;
import java.util.logging.Level;

@Mod(modid = "moen-jaffas", name = "Jaffas", version = "0.3.3")
@NetworkMod(clientSideRequired = true, serverSideRequired = false)
public class mod_jaffas {
    public static Hashtable<JaffaItem, JaffaItemInfo> ItemsInfo;
    private static MinecraftServer server;
    public static JaffaItem[] mallets;
    public static JaffaItem[] malletHeads;

    public static JaffaBombBlock blockJaffaBomb;
    public static int blockJaffaBombID;

    public static BlockFridge blockFridge;
    public static int blockFridgeID;

    private static IGuiHandler guiHandler;
    public static Object instance;

    public enum JaffaItem {
        pastry, cake, jamO, jamR, jaffaO, jaffaR, jaffa, chocolate, apples, beans, sweetBeans,
        butter, mallet, malletStone, malletIron, malletDiamond, malletHead, malletHeadStone, malletHeadIron, malletHeadDiamond,
        brownPastry, puffPastry, peanut, cream, roll, creamRoll, cakeTin, browniesInTin, brownie, rollRaw, browniesInTinRaw,
        bunRaw, bun, sausageRaw, sausage, hotdog, flour, chocolateWrapper, chocolateBar, wrapperJaffas, jaffasPack, jaffasPackO,
        jaffasPackR
    }

    public static final int startID = 3753;
    private int actualID = startID;

    private int getID() {
        return this.actualID++;
    }

    private void AddItemInfo(JaffaItem item, String name, int iconIndex, String title) {
        JaffaItemInfo newItem = new JaffaItemInfo(name);
        newItem.setIconIndex(iconIndex);
        newItem.setTitle(title);
        ItemsInfo.put(item, newItem);
    }

    private void InitializeItemInfos() {
        ItemsInfo = new Hashtable<JaffaItem, JaffaItemInfo>();
        AddItemInfo(JaffaItem.pastry, "Pastry", 13, "Pastry");
        AddItemInfo(JaffaItem.cake, "Cake", 1, "Sponge Cake");
        AddItemInfo(JaffaItem.jamO, "Jam Orange", 2, "Orange Jam");
        AddItemInfo(JaffaItem.jamR, "Jam Red", 3, "Jam");
        AddItemInfo(JaffaItem.jaffaO, "Jaffa Orange", 4, "Orange Jaffa Cake");
        AddItemInfo(JaffaItem.jaffaR, "Jaffa Red", 5, "Jaffa Cake");
        AddItemInfo(JaffaItem.jaffa, "Jaffa", 6, "Jaffa Cake");
        AddItemInfo(JaffaItem.chocolate, "Chocolate", 7, "Chocolate");
        AddItemInfo(JaffaItem.apples, "Apples", 10, "Apples");
        AddItemInfo(JaffaItem.beans, "Beans", 8, "Cocoa Powder");
        AddItemInfo(JaffaItem.sweetBeans, "Sweet Beans", 9, "Sweet Cocoa Powder");
        AddItemInfo(JaffaItem.butter, "Butter", 12, "Butter");
        AddItemInfo(JaffaItem.mallet, "Mallet", 23, "Little Wooden Mallet");
        AddItemInfo(JaffaItem.malletStone, "Mallet Stone", 24, "Little Stone Mallet");
        AddItemInfo(JaffaItem.malletIron, "Mallet Iron", 25, "Little Iron Mallet");
        AddItemInfo(JaffaItem.malletDiamond, "Mallet Diamond", 26, "Little Diamond Mallet");
        AddItemInfo(JaffaItem.malletHead, "Mallet Head", 27, "Wooden Mallet Head");
        AddItemInfo(JaffaItem.malletHeadStone, "Mallet Head Stone", 28, "Stone Mallet Head");
        AddItemInfo(JaffaItem.malletHeadIron, "Mallet Head Iron", 29, "Iron Mallet Head");
        AddItemInfo(JaffaItem.malletHeadDiamond, "Mallet Head Diamond", 30, "Diamond Mallet Head");
        AddItemInfo(JaffaItem.brownPastry, "Brown Pastry", 14, "Brown Pastry");
        AddItemInfo(JaffaItem.puffPastry, "Puff Pastry", 15, "Puff Pastry");
        AddItemInfo(JaffaItem.peanut, "Peanut", 16, "Peanut");
        AddItemInfo(JaffaItem.cream, "Cream", 17, "Cream");
        AddItemInfo(JaffaItem.roll, "Roll", 18, "Roll");
        AddItemInfo(JaffaItem.creamRoll, "Cream Roll", 19, "Cream Roll");
        AddItemInfo(JaffaItem.cakeTin, "Cake Tin", 20, "Cake Tin");
        AddItemInfo(JaffaItem.browniesInTin, "Brownies", 21, "Brownies");
        AddItemInfo(JaffaItem.brownie, "Brownie", 22, "Brownie");
        AddItemInfo(JaffaItem.rollRaw, "Roll Raw", 31, "Raw Roll");
        AddItemInfo(JaffaItem.browniesInTinRaw, "Raw Brownies", 32, "Raw Brownies");
        AddItemInfo(JaffaItem.bunRaw, "Raw Bun", 44, "Raw Bun");
        AddItemInfo(JaffaItem.bun, "Bun", 45, "Bun");
        AddItemInfo(JaffaItem.sausageRaw, "Sausage Raw", 46, "Raw Sausage");
        AddItemInfo(JaffaItem.sausage, "Sausage", 47, "Sausage");
        AddItemInfo(JaffaItem.hotdog, "Hotdog", 48, "Hotdog");
        AddItemInfo(JaffaItem.flour, "Flour", 49, "Flour");
        AddItemInfo(JaffaItem.chocolateWrapper, "Chocolate Wrapper", 33, "Chocolate Wrapper");
        AddItemInfo(JaffaItem.chocolateBar, "Chocolate Bar", 34, "Chocolate Bar");
        AddItemInfo(JaffaItem.wrapperJaffas, "Wrapper Jaffas", 50, "Jaffa Cakes Wrapper");
        AddItemInfo(JaffaItem.jaffasPack, "Jaffa Cakes Pack", 51, "Jaffa Cakes Pack");
        AddItemInfo(JaffaItem.jaffasPackO, "Orange Jaffa Cakes Pack", 51, "Orange Jaffa Cakes Pack");
        AddItemInfo(JaffaItem.jaffasPackR, "Red Jaffa Cakes Pack", 51, "Jaffa Cakes Pack");
    }

    public mod_jaffas() {
        InitializeItemInfos();
        instance = this;
    }

    @SidedProxy(clientSide = "jaffas.common.ClientProxyTutorial", serverSide = "jaffas.common.CommonProxyTutorial")
    public static CommonProxyTutorial proxy;

    @PreInit
    public void PreLoad(FMLPreInitializationEvent event) {
        Configuration config = new Configuration(
                event.getSuggestedConfigurationFile());

        try {
            config.load();


            for (JaffaItem item : JaffaItem.values()) {
                JaffaItemInfo info = ItemsInfo.get(item);
                int id = config.getOrCreateIntProperty(info.getConfigName(), Configuration.CATEGORY_ITEM, getID()).getInt();
                info.setId(id);
            }

            blockJaffaBombID = config.getOrCreateIntProperty("jaffa bomb", Configuration.CATEGORY_BLOCK, getID()).getInt();
            blockFridgeID = config.getOrCreateIntProperty("fridge", Configuration.CATEGORY_BLOCK, getID()).getInt();

        } catch (Exception e) {
            FMLLog.log(Level.SEVERE, e, "Mod Jaffas can't read config file.");
        } finally {
            config.save();
        }
    }

    private void finilizeItemSetup(JaffaItemInfo info, Item item) {
        item.setItemName(info.getTitle());
        item.setIconIndex(info.getIconIndex());
        info.setItem(item);
        LanguageRegistry.addName(item, info.getTitle());
    }

    private ItemJaffaBase createJaffaItem(JaffaItem ji) {
        JaffaItemInfo info = ItemsInfo.get(ji);
        ItemJaffaBase newJaffaItem = new ItemJaffaBase(info.getId());
        finilizeItemSetup(info, newJaffaItem);
        return newJaffaItem;
    }

    private ItemJaffaFood createJaffaFood(JaffaItem ji, int healAmount, float saturation) {
        JaffaItemInfo info = ItemsInfo.get(ji);
        ItemJaffaFood newJaffaItem = new ItemJaffaFood(info.getId(), healAmount, saturation);
        finilizeItemSetup(info, newJaffaItem);
        return newJaffaItem;
    }

    private ItemJaffaTool createJaffaTool(JaffaItem ji, int usageCount) {
        JaffaItemInfo info = ItemsInfo.get(ji);
        ItemJaffaTool newJaffaItem = new ItemJaffaTool(info.getId(), usageCount);
        finilizeItemSetup(info, newJaffaItem);
        return newJaffaItem;
    }

    @Init
    public void load(FMLInitializationEvent event) {
        blockFridge = new BlockFridge(blockFridgeID);
        GameRegistry.registerBlock(blockFridge);
        LanguageRegistry.addName(blockFridge, "Fridge");
        GameRegistry.registerTileEntity(TileEntityFridge.class, "Fridge");

        guiHandler = new GuiHandler();
        NetworkRegistry.instance().registerGuiHandler(this, guiHandler);

        blockJaffaBomb = new JaffaBombBlock(blockJaffaBombID, 35, Material.rock);
        GameRegistry.registerBlock(blockJaffaBomb);
        LanguageRegistry.addName(blockJaffaBomb, "Jaffa Cakes BOMB");

        createJaffaItem(JaffaItem.pastry);
        createJaffaItem(JaffaItem.jamO);
        createJaffaItem(JaffaItem.jamR);

        createJaffaFood(JaffaItem.cake, 1, 0.2F);
        createJaffaFood(JaffaItem.jaffaO, 3, 0.7F).setPotionEffect(Potion.regeneration.id, 2, 1, 0.4F);
        createJaffaFood(JaffaItem.jaffaR, 3, 0.7F).setPotionEffect(Potion.regeneration.id, 2, 1, 0.4F);
        createJaffaFood(JaffaItem.jaffa, 2, 0.5F).setPotionEffect(Potion.regeneration.id, 2, 1, 0.2F);

        createJaffaItem(JaffaItem.chocolate);
        createJaffaItem(JaffaItem.apples);
        createJaffaItem(JaffaItem.beans);
        createJaffaItem(JaffaItem.sweetBeans);
        createJaffaItem(JaffaItem.butter);

        createJaffaTool(JaffaItem.mallet, 8);
        createJaffaTool(JaffaItem.malletStone, 16);
        createJaffaTool(JaffaItem.malletIron, 64);
        createJaffaTool(JaffaItem.malletDiamond, 512);

        mallets = new JaffaItem[]{JaffaItem.mallet, JaffaItem.malletStone, JaffaItem.malletIron, JaffaItem.malletDiamond};

        createJaffaItem(JaffaItem.malletHead);
        createJaffaItem(JaffaItem.malletHeadStone);
        createJaffaItem(JaffaItem.malletHeadIron);
        createJaffaItem(JaffaItem.malletHeadDiamond);

        malletHeads = new JaffaItem[]{JaffaItem.malletHead, JaffaItem.malletHeadStone, JaffaItem.malletHeadIron, JaffaItem.malletHeadDiamond};

        createJaffaItem(JaffaItem.brownPastry);
        createJaffaItem(JaffaItem.puffPastry);
        createJaffaItem(JaffaItem.peanut);
        createJaffaItem(JaffaItem.cream);
        createJaffaItem(JaffaItem.roll);
        createJaffaItem(JaffaItem.cakeTin);
        createJaffaItem(JaffaItem.browniesInTin);

        createJaffaItem(JaffaItem.rollRaw);
        createJaffaItem(JaffaItem.browniesInTinRaw);

        createJaffaFood(JaffaItem.creamRoll, 4, 1F).setPotionEffect(Potion.digSpeed.id, 60, 1, 0.15F);
        createJaffaFood(JaffaItem.brownie, 2, 0.6F).setPotionEffect(Potion.jump.id, 60, 1, 0.15F);

        createJaffaItem(JaffaItem.bunRaw);
        createJaffaItem(JaffaItem.bun);
        createJaffaItem(JaffaItem.sausageRaw);
        createJaffaItem(JaffaItem.sausage);
        createJaffaItem(JaffaItem.flour);

        createJaffaFood(JaffaItem.hotdog, 3, 0.7F).setPotionEffect(Potion.damageBoost.id, 60, 1, 0.15F);

        createJaffaItem(JaffaItem.chocolateWrapper);

        createJaffaFood(JaffaItem.chocolateBar, 1, 0.5F).setPotionEffect(Potion.moveSpeed.id, 60, 1, 0.15F);

        createJaffaItem(JaffaItem.wrapperJaffas);
        createJaffaItem(JaffaItem.jaffasPack);
        createJaffaItem(JaffaItem.jaffasPackO);
        createJaffaItem(JaffaItem.jaffasPackR);

        installRecipes();

        // texture stuff
        proxy.registerRenderThings();

        GameRegistry.registerCraftingHandler(new JaffaCraftingHandler());
    }

    @Mod.ServerStarting
    public void serverStarting(FMLServerStartingEvent event) {
        server = ModLoader.getMinecraftServerInstance();
        ICommandManager commandManager = server.getCommandManager();
        ServerCommandManager serverCommandManager = ((ServerCommandManager) commandManager);
        addCommands(serverCommandManager);
    }

    private void addCommands(ServerCommandManager manager) {
        manager.registerCommand(new CommandJaffaHunger());
    }

    private void installRecipes() {
        GameRegistry.addShapelessRecipe(new ItemStack(getItem(JaffaItem.pastry)), new ItemStack(Item.sugar),
                new ItemStack(Item.egg), new ItemStack(getItem(JaffaItem.butter)), new ItemStack(getItem(JaffaItem.flour)), new ItemStack(getItem(JaffaItem.flour)));

        GameRegistry.addShapelessRecipe(new ItemStack(getItem(JaffaItem.sweetBeans)),
                new ItemStack(getItem(JaffaItem.beans)),
                new ItemStack(Item.sugar));

        GameRegistry.addSmelting(getItem(JaffaItem.sweetBeans).shiftedIndex, new ItemStack(getItem(JaffaItem.chocolate)), 0.1F);

        GameRegistry.addShapelessRecipe(new ItemStack(getItem(JaffaItem.apples)),
                new ItemStack(Item.appleRed),
                new ItemStack(Item.appleRed),
                new ItemStack(Item.appleRed),
                new ItemStack(Item.appleRed));

        GameRegistry.addShapelessRecipe(new ItemStack(Item.appleRed, 4),
                new ItemStack(getItem(JaffaItem.apples)));

        GameRegistry.addSmelting(getItem(JaffaItem.apples).shiftedIndex, new ItemStack(
                getItem(JaffaItem.jamR)), 0.5F);

        GameRegistry.addRecipe(new ItemStack(getItem(JaffaItem.jamO)), "X", "Y", 'X', new ItemStack(Item.dyePowder, 1, 14), 'Y',
                new ItemStack(getItem(JaffaItem.jamR)));

        GameRegistry.addRecipe(new ItemStack(getItem(JaffaItem.jaffa), 12), "X", "Y", 'X', new ItemStack(getItem(JaffaItem.chocolate)), 'Y',
                new ItemStack(getItem(JaffaItem.cake)));

        GameRegistry.addRecipe(new ItemStack(getItem(JaffaItem.jaffaR), 12), "X", "Y", "Z", 'X', new ItemStack(getItem(JaffaItem.chocolate)), 'Y',
                new ItemStack(getItem(JaffaItem.jamR)), 'Z', new ItemStack(getItem(JaffaItem.cake)));

        GameRegistry.addRecipe(new ItemStack(getItem(JaffaItem.jaffaO), 12),
                "X", "Y", "Z", 'X',
                new ItemStack(getItem(JaffaItem.chocolate)), 'Y',
                new ItemStack(getItem(JaffaItem.jamO)), 'Z',
                new ItemStack(getItem(JaffaItem.cake)));


        GameRegistry.addSmelting(getItem(JaffaItem.pastry).shiftedIndex, new ItemStack(
                getItem(JaffaItem.cake)), 0.1F);

        AddMalletRecipes();

        GameRegistry.addRecipe(new ItemStack(getItem(JaffaItem.malletHeadDiamond)), "BIS", "IDI", "SIB",
                'B', new ItemStack(Item.slimeBall), 'I', new ItemStack(Item.ingotIron),
                'S', new ItemStack(Item.silk), 'D', new ItemStack(Block.blockDiamond));

        GameRegistry.addRecipe(new ItemStack(getItem(JaffaItem.malletHead)), "SP ", "PWP", " P ",
                'S', new ItemStack(Item.silk), 'P', new ItemStack(Block.planks, 1, -1),
                'W', new ItemStack(Block.wood, 1, -1));

        GameRegistry.addRecipe(new ItemStack(getItem(JaffaItem.malletHeadStone)), "SC ", "COC", " CS",
                'S', new ItemStack(Item.silk), 'C', new ItemStack(Block.cobblestone),
                'O', new ItemStack(Block.stone));

        GameRegistry.addRecipe(new ItemStack(getItem(JaffaItem.malletHeadIron)), "SOS", "OBO", "SOS",
                'S', new ItemStack(Item.silk), 'B', new ItemStack(Block.blockSteel),
                'O', new ItemStack(Block.stone));

        AddMalletShapedRecipe(new ItemStack(getItem(JaffaItem.beans)), new ItemStack(Item.dyePowder, 1, 3));
        AddMalletShapedRecipe(new ItemStack(getItem(JaffaItem.butter)), new ItemStack(Item.bucketMilk));
        AddMalletShapedRecipe(new ItemStack(getItem(JaffaItem.cakeTin)), new ItemStack(Item.ingotIron));

        GameRegistry.addShapelessRecipe(new ItemStack(getItem(JaffaItem.brownPastry)), new ItemStack(getItem(JaffaItem.peanut)),
                new ItemStack(getItem(JaffaItem.pastry)), new ItemStack(getItem(JaffaItem.chocolate)));

        GameRegistry.addShapelessRecipe(new ItemStack(getItem(JaffaItem.puffPastry)), new ItemStack(getItem(JaffaItem.butter)),
                new ItemStack(getItem(JaffaItem.butter)), new ItemStack(getItem(JaffaItem.butter)), new ItemStack(Item.egg),
                new ItemStack(getItem(JaffaItem.flour)), new ItemStack(getItem(JaffaItem.flour)));

        GameRegistry.addRecipe(new ItemStack(getItem(JaffaItem.peanut)), "SSS", 'S', new ItemStack(Item.seeds));

        GameRegistry.addRecipe(new ItemStack(getItem(JaffaItem.browniesInTinRaw)), "P", "T", 'P', new ItemStack(getItem(JaffaItem.brownPastry)), 'T', new ItemStack(getItem(JaffaItem.cakeTin)));

        GameRegistry.addSmelting(getItem(JaffaItem.browniesInTinRaw).shiftedIndex, new ItemStack(getItem(JaffaItem.browniesInTin)), 1F);

        GameRegistry.addRecipe(new ItemStack(getItem(JaffaItem.brownie), 15), "S", "T", 'S', new ItemStack(Item.swordSteel), 'T', new ItemStack(getItem(JaffaItem.browniesInTin)));

        GameRegistry.addShapelessRecipe(new ItemStack(getItem(JaffaItem.rollRaw), 10), new ItemStack(getItem(JaffaItem.puffPastry)), new ItemStack(Item.stick));

        GameRegistry.addSmelting(getItem(JaffaItem.rollRaw).shiftedIndex, new ItemStack(getItem(JaffaItem.roll)), 0.1F);

        GameRegistry.addShapelessRecipe(new ItemStack(getItem(JaffaItem.cream), 4), new ItemStack(Item.egg), new ItemStack(Item.egg), new ItemStack(Item.sugar), new ItemStack(Item.bucketMilk));

        GameRegistry.addRecipe(new ItemStack(getItem(JaffaItem.creamRoll)), "RC", 'R', new ItemStack(getItem(JaffaItem.roll)), 'C', new ItemStack(getItem(JaffaItem.cream)));

        GameRegistry.addShapelessRecipe(new ItemStack(getItem(JaffaItem.flour), 3), new ItemStack(Item.wheat), new ItemStack(Item.wheat), new ItemStack(Item.wheat),
                new ItemStack(Item.wheat), new ItemStack(Item.wheat), new ItemStack(Item.wheat), new ItemStack(Item.paper));

        GameRegistry.addRecipe(new ItemStack(getItem(JaffaItem.sausageRaw), 5), " F ", "PPP", 'F', new ItemStack(getItem(JaffaItem.flour)), 'P', new ItemStack(Item.porkRaw));

        GameRegistry.addRecipe(new ItemStack(getItem(JaffaItem.bunRaw), 10), "PP", 'P', new ItemStack(getItem(JaffaItem.pastry)));

        GameRegistry.addSmelting(getItem(JaffaItem.bunRaw).shiftedIndex, new ItemStack(getItem(JaffaItem.bun)), 0.1F);
        GameRegistry.addSmelting(getItem(JaffaItem.sausageRaw).shiftedIndex, new ItemStack(getItem(JaffaItem.sausage)), 0.1F);

        GameRegistry.addRecipe(new ItemStack(getItem(JaffaItem.hotdog)), "S", "B", 'S', new ItemStack(getItem(JaffaItem.sausage)), 'B', new ItemStack(getItem(JaffaItem.bun)));

        GameRegistry.addRecipe(new ItemStack(getItem(JaffaItem.chocolateWrapper), 8), "XXX", "XCX", "XXX", 'X', new ItemStack(Item.paper), 'C', new ItemStack(Item.dyePowder, 1, 5));

        GameRegistry.addRecipe(new ItemStack(getItem(JaffaItem.chocolateBar), 2), "C", "C", "W", 'C', new ItemStack(getItem(JaffaItem.chocolate)), 'W', new ItemStack(getItem(JaffaItem.chocolateWrapper)));

        GameRegistry.addRecipe(new ItemStack(getItem(JaffaItem.wrapperJaffas), 8), "PPP", "PCP", "PPP", 'P', new ItemStack(Item.paper), 'C', new ItemStack(Item.dyePowder, 1, 12));

        GameRegistry.addShapelessRecipe(new ItemStack(getItem(JaffaItem.jaffasPack)), new ItemStack(getItem(JaffaItem.wrapperJaffas)),
                new ItemStack(getItem(JaffaItem.jaffa)), new ItemStack(getItem(JaffaItem.jaffa)),
                new ItemStack(getItem(JaffaItem.jaffa)), new ItemStack(getItem(JaffaItem.jaffa)), new ItemStack(getItem(JaffaItem.jaffa)),
                new ItemStack(getItem(JaffaItem.jaffa)), new ItemStack(getItem(JaffaItem.jaffa)), new ItemStack(getItem(JaffaItem.jaffa)));

        GameRegistry.addShapelessRecipe(new ItemStack(getItem(JaffaItem.jaffasPackR)), new ItemStack(getItem(JaffaItem.wrapperJaffas)),
                new ItemStack(getItem(JaffaItem.jaffaR)), new ItemStack(getItem(JaffaItem.jaffaR)),
                new ItemStack(getItem(JaffaItem.jaffaR)), new ItemStack(getItem(JaffaItem.jaffaR)), new ItemStack(getItem(JaffaItem.jaffaR)),
                new ItemStack(getItem(JaffaItem.jaffaR)), new ItemStack(getItem(JaffaItem.jaffaR)), new ItemStack(getItem(JaffaItem.jaffaR)));

        GameRegistry.addShapelessRecipe(new ItemStack(getItem(JaffaItem.jaffasPackO)), new ItemStack(getItem(JaffaItem.wrapperJaffas)),
                new ItemStack(getItem(JaffaItem.jaffaO)), new ItemStack(getItem(JaffaItem.jaffaO)),
                new ItemStack(getItem(JaffaItem.jaffaO)), new ItemStack(getItem(JaffaItem.jaffaO)), new ItemStack(getItem(JaffaItem.jaffaO)),
                new ItemStack(getItem(JaffaItem.jaffaO)), new ItemStack(getItem(JaffaItem.jaffaO)), new ItemStack(getItem(JaffaItem.jaffaO)));

        GameRegistry.addRecipe(new ItemStack(blockJaffaBomb), "J", "R", "T", 'J', new ItemStack(getItem(JaffaItem.jaffasPack)),
                'R', new ItemStack(getItem(JaffaItem.jaffasPackR)), 'T', new ItemStack(Block.tnt));
        GameRegistry.addRecipe(new ItemStack(blockJaffaBomb), "J", "O", "T", 'J', new ItemStack(getItem(JaffaItem.jaffasPack)),
                'O', new ItemStack(getItem(JaffaItem.jaffasPackO)), 'T', new ItemStack(Block.tnt));
    }

    private void AddMalletRecipes() {
        for (int i = 0; i < mallets.length; i++) {
            GameRegistry.addRecipe(new ItemStack(getItem(mallets[i])), "H", "S", "S", 'H', new ItemStack(getItem(malletHeads[i])), 'S', Item.stick);
        }
    }

    private void AddMalletShapedRecipe(ItemStack output, ItemStack input) {
        for (int i = 0; i < mallets.length; i++) {
            GameRegistry.addRecipe(output, "M", "O", 'M', new ItemStack(getItem(mallets[i]), 1, -1), 'O', input);
        }
    }

    private Item getItem(JaffaItem item) {
        return ItemsInfo.get(item).getItem();
    }
}