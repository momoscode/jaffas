/*
 * Jaffas and more!
 * author: monnef
 */

package monnef.jaffas.food.item.common;

import monnef.core.utils.WolfFoodRegistry;
import monnef.jaffas.food.JaffasFood;
import monnef.jaffas.food.block.BlockPie;
import monnef.jaffas.food.block.BlockSink;
import monnef.jaffas.food.block.TileMeatDryer;
import monnef.jaffas.food.common.ModulesEnum;
import monnef.jaffas.food.crafting.Recipes;
import monnef.jaffas.food.item.ItemCleaver;
import monnef.jaffas.food.item.ItemDuckEgg;
import monnef.jaffas.food.item.ItemJaffaFood;
import monnef.jaffas.food.item.ItemJaffaPack;
import monnef.jaffas.food.item.ItemJaffaPlate;
import monnef.jaffas.food.item.ItemJaffaRecipeTool;
import monnef.jaffas.food.item.ItemMagnifier;
import monnef.jaffas.food.item.ItemMeatDryer;
import monnef.jaffas.food.item.ItemPizza;
import monnef.jaffas.food.item.ItemSink;
import monnef.jaffas.food.item.ItemSpawnStone;
import monnef.jaffas.food.item.JaffaItem;
import monnef.jaffas.food.item.JaffaItemInfo;
import monnef.jaffas.food.item.JaffasHelper;
import net.minecraft.block.Block;
import net.minecraft.item.EnumArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraftforge.oredict.OreDictionary;

import static monnef.jaffas.food.JaffasFood.proxy;
import static monnef.jaffas.food.crafting.Recipes.getItemStack;
import static monnef.jaffas.food.crafting.Recipes.getItemStackAnyDamage;
import static monnef.jaffas.food.item.JaffaItem.*;

public class Items extends ItemManagerAccessor {
    public static final String MINCEABLEMEAT = "jaffasMinceAbleMeat";
    public static final String JAFFA = "jaffasAny";
    public static final String JAFFA_FILLED = "jaffasFilled";
    public static final String MUSHROOM = "jaffasMushroom";
    public static final String EGG = "jaffasEgg";
    public static final String MALLET = "jaffasMallet";

    public enum Juice {
        LEMON(JaffaItem.juiceLemon, 181, "Lemon Juice", JaffaItem.glassLemon, 186, ""),
        ORANGE(juiceOrange, 182, "Orange Juice", glassOrange, 187, ""),
        APPLE(juiceApple, 183, "Apple Juice", glassApple, 188, ""),
        RASPBERRY(juiceRaspberry, 184, "Raspberry Juice", glassRaspberry, 189, "");

        public final JaffaItem juiceBottle;
        public final int textureIndex;
        public final String title;
        public final JaffaItem glass;
        public final int textureIndexGlass;
        public final String glassTitle;

        Juice(JaffaItem juiceBottle, int textureIndex, String title, JaffaItem glass, int textureIndexGlass, String glassTitle) {
            this.juiceBottle = juiceBottle;
            this.textureIndex = textureIndex;
            this.title = title;
            this.textureIndexGlass = textureIndexGlass;
            this.glass = glass;

            if (glassTitle.equals("")) {
                this.glassTitle = "Glass of " + this.title;
            } else {
                this.glassTitle = glassTitle;
            }
        }
    }

    @Override
    public ModulesEnum getMyModule() {
        return ModulesEnum.food;
    }

    private ItemJaffaFood createJaffaFood(JaffaItem jaffaItem, int heal, float saturation) {
        return (ItemJaffaFood) createJaffaFood(jaffaItem).Setup(heal, saturation);
    }

    private ItemJaffaRecipeTool createJaffaTool(JaffaItem ji, int durability) {
        return (ItemJaffaRecipeTool) createJaffaTool(ji).Setup(durability);
    }

    private Item getItem(JaffaItem jaffaItem) {
        return ItemManager.getItem(jaffaItem);
    }

    @Override
    public void InitializeItemInfos() {
        setCurrentSheetNumber(1);
        AddItemInfo(pastrySweet, "Pastry", 13, "Sweet Pastry");
        AddItemInfo(cake, "Cake", 1, "Sponge Cake");
        AddItemInfo(jamO, "Jam Orange", 2, "Orange Jam");
        AddItemInfo(jamR, "Jam Red", 3, "Apple Jam");
        AddItemInfoForJaffa(jaffaO, "Jaffa Orange", 4);
        AddItemInfoForJaffa(jaffaR, "Jaffa Red", 5);
        AddItemInfoForJaffa(jaffa, "Jaffa", 6);
        AddItemInfo(chocolate, "Chocolate", 7, "Chocolate");
        AddItemInfo(apples, "Apples", 10, "Apples");
        AddItemInfo(beans, "Beans", 8, "Cocoa Powder");
        AddItemInfo(sweetBeans, "Sweet Beans", 9, "Sweet Cocoa Powder");
        AddItemInfo(butter, "Butter", 12, "Butter");
        AddItemInfo(mallet, "Mallet", 23, "Little Wooden Mallet");
        AddItemInfo(malletStone, "Mallet Stone", 24, "Little Stone Mallet");
        AddItemInfo(malletIron, "Mallet Iron", 25, "Little Iron Mallet");
        AddItemInfo(malletDiamond, "Mallet Diamond", 26, "Little Diamond Mallet");
        AddItemInfo(malletHead, "Mallet Head", 27, "Wooden Mallet Head");
        AddItemInfo(malletHeadStone, "Mallet Head Stone", 28, "Stone Mallet Head");
        AddItemInfo(malletHeadIron, "Mallet Head Iron", 29, "Iron Mallet Head");
        AddItemInfo(malletHeadDiamond, "Mallet Head Diamond", 30, "Diamond Mallet Head");
        AddItemInfo(browniesPastry, "Brown Pastry", 14, "Brownies Pastry");
        AddItemInfo(puffPastry, "Puff Pastry", 15, "Puff Pastry");
        //AddItemInfo(JaffaItem.peanut, "Peanut", 16, "Peanut");
        AddItemInfo(cream, "Cream", 17, "Cream");
        AddItemInfo(sweetRoll, "Roll", 18, "Roll");
        AddItemInfo(creamRoll, "Cream Roll", 19, "Cream Roll");
        AddItemInfo(cakeTin, "Cake Tin", 20, "Cake Tin");
        AddItemInfo(browniesInTin, "Brownies", 21, "Brownies");
        AddItemInfo(brownie, "Brownie", 22, "Brownie");
        AddItemInfo(sweetRollRaw, "Roll Raw", 31, "Raw Roll");
        AddItemInfo(browniesInTinRaw, "Raw Brownies", 32, "Raw Brownies");
        AddItemInfo(bunRaw, "Raw Bun", 44, "Raw Bun");
        AddItemInfo(bun, "Bun", 45, "Bun");
        AddItemInfo(sausageRaw, "Sausage Raw", 46, "Raw Sausage");
        AddItemInfo(sausage, "Sausage", 47, "Sausage");
        AddItemInfo(hotdog, "Hotdog", 48, "Hotdog");
        AddItemInfo(flour, "Flour", 49, "Flour");
        AddItemInfo(chocolateWrapper, "Chocolate Wrapper", 33, "Chocolate Wrapper");
        AddItemInfo(chocolateBar, "Chocolate Bar", 34, "Chocolate Bar");
        AddItemInfo(wrapperJaffas, "Wrapper Jaffas", 50, JaffasFood.jaffasTitle + " Wrapper");
        AddItemInfo(jaffasPack, "Jaffa Cakes Pack", 51, JaffasFood.jaffasTitle + " Pack");
        AddItemInfo(vanillaBeans, "Vanilla Beans", 52, "Vanilla Beans");
        AddItemInfo(waferIcecream, "Wafer Ice-cream", 53, "Wafer");
        AddItemInfo(cone, "Icecream Cone", 54, "Cone");
        AddItemInfo(vanillaPowder, "Vanilla Powder", 55, "Vanilla Powder");
        AddItemInfo(vanillaIcecreamRaw, "Vanilla Ice-cream Raw", 56, "Vanilla Ice-cream");
        AddItemInfo(chocolateIcecreamRaw, "Chocolate Ice-cream Raw", 57, "Chocolate Ice-cream");
        AddItemInfo(icecreamRaw, "Ice-cream Raw", 58, "Ice-cream");
        AddItemInfo(vanillaIcecream, "Vanilla Scooped Ice-cream", 59, "Scooped Ice-cream");
        AddItemInfo(chocolateIcecream, "Chocolate Scooped Ice-cream", 60, "Scooped Ice-cream");
        AddItemInfo(russianIcecream, "Russian Ice-cream", 61, "Russian Ice-cream");
        AddItemInfo(vanillaIcecreamFrozen, "Vanilla Ice-cream Frozen", 62, "Vanilla Ice-cream *");
        AddItemInfo(chocolateIcecreamFrozen, "Chocolate Ice-cream Frozen", 63, "Chocolate Ice-cream *");
        AddItemInfo(icecreamFrozen, "Ice-cream Frozen", 64, "Ice-cream *");
        AddItemInfo(donutRaw, "Donut Raw", 71, "Raw Donut");
        AddItemInfo(donut, "Donut", 72, "Donut");
        AddItemInfo(donutChocolate, "Donut Chocolate", 73, "Chocolate Donut");
        AddItemInfo(donutPink, "Donut Apple", 74, "Apple Donut");
        AddItemInfo(donutSugar, "Donut Sugar", 75, "Powdered Donut");
        AddItemInfo(donutSprinkled, "Donut Sprinkled", 76, "Sprinkled Donut");
        AddItemInfoForJaffa(jaffaV, "Jaffa Vanilla", 77);
        AddItemInfoForJaffa(jaffaL, "Jaffa Lemon", 78);
        AddItemInfo(jamP, "Jam Plum", 79, "Plum Jam");
        AddItemInfo(jamL, "Jam Lemon", 80, "Lemon Jam");
        AddItemInfo(jamV, "Vanilla Jam", 81, "Vanilla Jam");
        AddItemInfo(lemons, "Lemons", 82, "Lemons");
        AddItemInfo(oranges, "Oranges", 83, "Oranges");
        AddItemInfo(plums, "Plums", 84, "Plums");
        AddItemInfo(sprinkles, "Sprinkles", 87, "Sprinkles");
        AddItemInfo(magnifier, "Magnifier", 91, "Magnifier");
        AddItemInfoForJaffa(jaffaP, "Jaffa Plum", 86);
        AddItemInfo(jamMix, "Jam Mix", 110, "Mix of Jams");

        AddItemInfo(kettle, "Kettle", 92, "Empty Kettle");
        AddItemInfo(cup, "Cup", 93, "Cup");
        AddItemInfo(cupCoffee, "Coffee Cup", 94, "Cup of Coffee");
        AddItemInfo(cupRaw, "Raw Cup", 109, "Raw Cup");
        AddItemInfo(kettleWaterCold, "Kettle Cold", 92, "Kettle With Cold Water");
        AddItemInfo(kettleWaterHot, "Kettle Hot", 92, "Kettle With Hot Water");

        AddItemInfo(omeletteRaw, "Omelette Raw", 97, "Raw Omelette");
        AddItemInfo(omelette, "Omelette", 98, "Omelette");
        AddItemInfo(tomatoChopped, "Tomato Chopped", 99, "Chopped Tomatos");
        AddItemInfo(paprikaChopped, "Pepper Chopped", 100, "Chopped Peppers");

        AddItemInfo(wienerCocktail, "Cocktail Wiener", 102, "Cocktail Wiener");
        AddItemInfoForJaffa(jaffaStrawberry, "Jaffa Strawberry", 103);
        AddItemInfoForJaffa(jaffaRaspberry, "Jaffa Raspberry", 104);
        AddItemInfo(raspberries, "Raspberries", 105, "Raspberries");
        AddItemInfo(strawberries, "Strawberries", 106, "Strawberries");
        AddItemInfo(jamRaspberry, "Jam Raspberry", 107, "Raspberry Jam");
        AddItemInfo(jamStrawberry, "Jam Strawberry", 108, "Strawberry Jam");

        AddItemInfo(rollRaw, "Not-Sweet Raw Roll", 111, "Raw Roll");
        AddItemInfo(roll, "Not-Sweet Roll", 65, "Roll");
        AddItemInfo(rollChopped, "Chopped Not-Sweet Roll", 66, "Chopped Roll");
        AddItemInfo(meatChopped, "Chopped Meat", 67, "Chopped Meat");
        AddItemInfo(skewer, "Skewer", 68, "Skewer");
        AddItemInfo(ironSkewer, "Iron Skewer", 69, "Iron Skewer");
        AddItemInfo(knifeKitchen, "Kitchen Knife", 70, "Kitchen Knife");

        AddItemInfo(coffee, "Coffee", 8, "Coffee");
        AddItemInfo(coffeeRoasted, "Roasted Coffee", 112, "Roasted Coffee");

        AddItemInfo(skewerRaw, "Skewer Raw", 85, "Raw Skewer");

        AddItemInfo(brownPastry, "Brown Pastry 2", 14, "Brown Pastry");

        AddItemInfo(coconutPowder, "Coconut Powder", 139, "Coconut Powder");
        AddItemInfo(honey, "Honey", 140, "Honey");

        AddItemInfo(gingerbread, "Gingerbread", 14, "Gingerbread");

        AddItemInfo(hamburgerBunRaw, "Hamburger Bun Raw", 113, "Raw Hamburger Bun");
        AddItemInfo(hamburgerBun, "Hamburger Bun", 114, "Hamburger Bun");
        AddItemInfo(cheese, "Cheese", 115, "Cheese");
        AddItemInfo(cheeseSlice, "Cheese Slice", 116, "Slice of Cheese");
        AddItemInfo(rawBurger, "Raw Burger", 117, "Raw Burger");
        AddItemInfo(burger, "Burger", 118, "Burger");
        AddItemInfo(onionSliced, "Sliced Onion", 119, "Sliced Onion");
        AddItemInfo(hamburger, "Hamburger", 120, "Hamburger");
        AddItemInfo(cheeseburger, "Cheeseburger", 121, "Cheeseburger");
        AddItemInfo(fryingPan, "Frying Pan", 122, "Frying Pan");
        AddItemInfo(fryingPanBurgerRaw, "Frying Pan Burger Raw", 123, "Raw Burger in Frying Pan");
        AddItemInfo(fryingPanEggRaw, "Frying Pan Egg Raw", 124, "Raw Egg in Frying Pan");
        AddItemInfo(fryingPanBurger, "Frying Pan Burger", 123, "Burger in Frying Pan");
        AddItemInfo(fryingPanEgg, "Frying Pan Egg", 124, "Egg in Frying Pan");
        AddItemInfo(eggFried, "Fried Egg", 125, "Fried Egg");
        AddItemInfo(breadRaw, "Raw Bread", 126, "Raw Bread");
        AddItemInfo(bread, "Bread", 127, "Bread");
        AddItemInfo(breadSlice, "Bread Slice", 128, "Bread Slice");
        AddItemInfo(breadSliceToasted, "Bread Slice Toasted", 129, "Bread Slice Toasted");
        AddItemInfo(breadSliceJam, "Bread Slice with Jam", 130, "Bread Slice with Jam");
        AddItemInfo(breadSliceButter, "Bread Slice with Butter", 131, "Bread Slice with Butter");
        AddItemInfo(breadSliceEgg, "Bread Slice with Egg", 132, "Bread Slice with Egg");

        AddItemInfo(bottleEmpty, "Bottle", 133, "Empty Bottle");
        AddItemInfo(bottleKetchup, "Bottle of Ketchup", 134, "Bottle of Catchup");
        AddItemInfo(bottleMustard, "Bottle of Mustard", 135, "Bottle of Mustard");
        AddItemInfo(bottleBrownMustard, "Bottle of Brown Mustard", 136, "Bottle of Brown Mustard");

        AddItemInfo(meatCleaver, "Meat Cleaver", 137, "Meat Cleaver");
        AddItemInfo(mincedMeat, "Minced Meat", 138, "Minced Meat");

        AddItemInfo(sink, "Faucet", 141, "Basin");
        AddItemInfo(coneRaw, "Cone Raw", 143, "Raw Cone");
        AddItemInfo(waferIcecreamRaw, "Wafer Ice-cream Raw", 144, "Raw Wafer");

        AddItemInfo(grater, "Grater", 145, "Grater");
        AddItemInfo(cheeseGrated, "Grated Cheese", 146, "Grated Cheese");
        AddItemInfo(salami, "Salami", 147, "Salami");
        AddItemInfo(salamiSliced, "Sliced Salami", 148, "Sliced Salami");

        AddItemInfo(pizza, "Pizza In Tin", 149, "Pizza");
        AddItemInfo(pizzaRaw, "Pizza In Tin Raw", 150, "Raw Pizza");

        AddItemInfo(wolfHelmet, "Wolf Helmet", 153, "Wolf Helmet");
        AddItemInfo(wolfBoots, "Wolf Boots", 154, "Wolf Boots");
        AddItemInfo(wolfChest, "Wolf Chest", 152, "Wolf Chestplate");
        AddItemInfo(wolfLeggins, "Wolf Leggings", 155, "Wolf Leggings");
        AddItemInfo(wolfSkin, "Wolf Skin", 151, "Wolf Skin");

        AddItemInfo(pastry, "Sugar-Free Pastry", 13, "Pastry");

        AddItemInfo(milkBoxEmpty, "Milk Box", 164, "Empty Milk Box");
        AddItemInfo(milkBoxFull, "Milk Box Full", 165, "Milk");

        AddItemInfo(crumpledPaper, "Crumpled Paper", 162, "Crumpled Paper");
        AddItemInfo(scrap, "Scrap", 162, "Scrap");

        AddItemInfo(JaffaItem.chipsRaw, "Raw Chips", 166, "Raw Chips");
        AddItemInfo(JaffaItem.chips, "Chips", 167, "Chips");
        AddItemInfo(JaffaItem.fryingPanChipsRaw, "Frying Pan Chips Raw", 168, "Raw Chips in Frying Pan");
        AddItemInfo(JaffaItem.fryingPanChips, "Frying Pan Chips", 168, "Chips in Frying Pan");

        AddItemInfo(JaffaItem.pieStrawberryRaw, "Raw Strawberry Pie", BlockPie.textureIndexFromMeta[0] + 13, "");
        AddItemInfo(JaffaItem.pieRaspberryRaw, "Raw Raspberry Pie", BlockPie.textureIndexFromMeta[1] + 13, "");
        AddItemInfo(JaffaItem.pieVanillaRaw, "Raw Vanilla Pie", BlockPie.textureIndexFromMeta[2] + 13, "");
        AddItemInfo(JaffaItem.piePlumRaw, "Raw Plum Pie", BlockPie.textureIndexFromMeta[3] + 13, "");

        AddItemInfo(JaffaItem.spawnStoneLittle, "Little Spawn Stone", 173, "Iron Home Stone");
        AddItemInfo(JaffaItem.spawnStoneMedium, "Crude Spawn Stone", 174, "Golden Home Stone");
        AddItemInfo(JaffaItem.spawnStoneBig, "Fine Spawn Stone", 175, "Diamond Home Stone");

        AddItemInfo(JaffaItem.jaffarrolHelmet, "Jarmor Helmet", 177, "");
        AddItemInfo(JaffaItem.jaffarrolBoots, "Jarmor Boots", 178, "");
        AddItemInfo(JaffaItem.jaffarrolChest, "Jarmor Chestplate", 176, "");
        AddItemInfo(JaffaItem.jaffarrolLeggins, "Jarmor Leggings", 179, "");

        AddItemInfo(JaffaItem.juiceBottle, "Juice Bottle", 180, "");
        for (Juice juice : Juice.values()) {
            AddItemInfo(juice.juiceBottle, juice.juiceBottle.toString().toLowerCase(), juice.textureIndex, juice.title);
            AddItemInfo(juice.glass, juice.glass.toString().toLowerCase(), juice.textureIndexGlass, juice.glassTitle);
        }
        AddItemInfo(JaffaItem.glassEmpty, "Glass", 185, "");
        AddItemInfo(JaffaItem.glassMilk, "glassMilk", 190, "Glass of Milk");

        AddItemInfo(JaffaItem.woodenBowl, "Wooden Bowl", 195, "");
        AddItemInfo(JaffaItem.cookedMushroomsRaw, "Raw Mushrooms", 196, "");
        AddItemInfo(JaffaItem.cookedMushrooms, "Cooked Mushrooms", 197, "");

        AddItemInfo(JaffaItem.peanutsSugar, "Sugarcoated Peanuts", 191, "");
        AddItemInfo(JaffaItem.peanutsCaramelized, "Caramelized Peanuts", 192, "");

        AddItemInfo(JaffaItem.pepperStuffedRaw, "Raw Stuffed Pepper", 193, "");
        AddItemInfo(JaffaItem.pepperStuffed, "Stuffed Pepper", 194, "");

        AddItemInfo(JaffaItem.wolfMeatRaw, "Raw Wolf Meat", 198, "");
        AddItemInfo(JaffaItem.wolfMeat, "Wolf Meat", 199, "");

        AddItemInfo(JaffaItem.muttonRaw, "Raw Mutton", 200, "");
        AddItemInfo(JaffaItem.mutton, "Mutton", 201, "");

        AddItemInfo(JaffaItem.spiderLegRaw, "Raw Spider Leg", 202, "");
        AddItemInfo(JaffaItem.spiderLeg, "Spider Leg", 203, "");

        AddItemInfo(JaffaItem.featherDuck, "Duck Feather", 207);
        AddItemInfo(JaffaItem.duckRaw, "Raw Duck", 208);
        AddItemInfo(JaffaItem.duck, "Duck", 209);
        AddItemInfo(JaffaItem.plateRaw, "Raw Plate", 230);
        AddItemInfo(JaffaItem.plate, "Plate", 210);
        AddItemInfo(JaffaItem.plateDuckOrange, "Plate Duck à l'Orange", 211, "Duck à l'Orange");
        AddItemInfo(JaffaItem.tinDuckOrangeRaw, "Duck à l'Orange Raw", 212);
        AddItemInfo(JaffaItem.tinDuckOrange, "Duck à l'Orange", 213, "Duck à l'Orange");

        AddItemInfo(JaffaItem.duckHelmet, "Duck Helmet", 215);
        AddItemInfo(JaffaItem.duckBoots, "Duck Boots", 216);
        AddItemInfo(JaffaItem.duckChest, "Duck Chestplate", 214);
        AddItemInfo(JaffaItem.duckLeggins, "Duck Leggings", 217);

        AddItemInfo(JaffaItem.strawberryIcecreamRaw, "Strawberry Ice-cream", 204);
        AddItemInfo(JaffaItem.strawberryIcecreamFrozen, "Strawberry Ice-cream *", 205);
        AddItemInfo(JaffaItem.strawberryIcecream, "Scooped Strawberry Ice-cream", 206);

        AddItemInfo(JaffaItem.chocIce, "Choc-ice", 219);
        AddItemInfo(JaffaItem.chocIceStick, "Choc-ice Stick", 218);

        AddItemInfo(duckEgg, "Duck Egg", 226);

        AddItemInfo(JaffaItem.muffinRaw, "Raw Muffin", 220);
        AddItemInfo(JaffaItem.muffinUnfinished, "Unfinished Muffin", 221);
        AddItemInfo(JaffaItem.muffin, "Muffin", 222);

        AddItemInfo(JaffaItem.beansWithTomatoRaw, "Raw Beans With Tomato Sauce", 223);
        AddItemInfo(JaffaItem.beansWithTomato, "Beans With Tomato Sauce", 224);

        AddItemInfo(JaffaItem.sandwich1, "Sandwich 1", 225, "Sandwich");

        AddItemInfo(JaffaItem.lambWithPeasInTinRaw, "Raw Lamb With Peas In Tin", 227);
        AddItemInfo(JaffaItem.lambWithPeasInTin, "Lamb With Peas In Tin", 228);
        AddItemInfo(JaffaItem.lambWithPeas, "Lamb With Peas", 229);

        AddItemInfo(JaffaItem.cocoBarWrapper, "Wrapper", 231);
        AddItemInfo(JaffaItem.cocoBar, "Coco Bar", 232);
        AddItemInfo(JaffaItem.cupCocoa, "Hot Cocoa", 233);
        AddItemInfo(JaffaItem.cookingPot, "Cooking Pot", 234);
        AddItemInfo(JaffaItem.cookingPotCocoaCold, "Cooking Pot Cocoa Cold", 235, "Cold Cocoa");
        AddItemInfo(JaffaItem.cookingPotCocoaHot, "Cooking Pot Cocoa Hot", 235, "Hot Cocoa");
        AddItemInfo(JaffaItem.eggHardBoiled, "Hard Boiled Egg", 237);
        AddItemInfo(JaffaItem.cookingPotWater, "Cooking Pot With Water", 236);
        AddItemInfo(JaffaItem.cookingPotEggsRaw, "Cooking Pot With Raw Eggs", 236);
        AddItemInfo(JaffaItem.cookingPotEggs, "Cooking Pot With Eggs", 236);

        AddItemInfo(JaffaItem.bananaInChocolate, "Chocolate Covered Banana", 238);

        AddItemInfo(JaffaItem.fruitSalad, "Fruit Salad", 245);
        AddItemInfo(JaffaItem.friedMushroomsInTinRaw, "Raw Mushrooms In Tin", 244);
        AddItemInfo(JaffaItem.friedMushroomsInTinCooked, "Fried Mushrooms In Tin", 243);
        AddItemInfo(JaffaItem.friedMushrooms, "Fried Mushrooms", 246);

        AddItemInfo(JaffaItem.shroomburger, "Shroomburger Incomplete", 242, "Shroomburger");
        AddItemInfo(JaffaItem.shroomburgerInBun, "Shroomburger", 240);
        AddItemInfo(JaffaItem.shroomburgerInBunWithCheese, "Shroomburger With Cheese", 239);
        AddItemInfo(JaffaItem.mincedMushrooms, "Minced Mushrooms", 241);
        AddItemInfo(JaffaItem.shroomburgerRaw, "Shroomburger Raw", 247);
        AddItemInfo(fryingPanShroomburgerRaw, "Frying Pan Shroomburger Raw", 123, "Raw Shroomburger in Frying Pan");
        AddItemInfo(fryingPanShroomburger, "Frying Pan Shroomburger", 123, "Shroomburger in Frying Pan");

        AddItemInfo(JaffaItem.flyAgaricChopped, "Chopped Fly Agaric", 248);

        AddItemInfo(JaffaItem.meatDryer, "Drying Rack", 250);
        AddItemInfo(JaffaItem.driedMeat, "Dried Meat", 249);

        AddItemInfo(JaffaItem.beerMugEmpty, "Empty Beer Mug", 251);
        AddItemInfo(JaffaItem.beerMugFull, "Full Beer Mug", 252);

        AddItemInfo(JaffaItem.potatesSliced, "Sliced Potatoes", 253);
        AddItemInfo(JaffaItem.potatesSlicedInTinRaw, "Raw Crisps", 254);
        AddItemInfo(JaffaItem.potatesSlicedInTin, "Crisps In Tin", 255);
        AddItemInfo(JaffaItem.crisps, "Crisps", 256);
    }

    private void registerWolfFood(JaffaItem item) {
        WolfFoodRegistry.registerWolfFood(getItem(item), ((IItemFood) getItem(item)).getHealAmount());
    }

    private void AddItemInfoForJaffa(JaffaItem ji, String name, int textureId) {
        AddItemInfo(ji, name, textureId, JaffasHelper.getJaffaTitleForItem(ji));
    }

    private void AddItemInfo(JaffaItem item, String name, int id) {
        AddItemInfo(item, name, id, "");
    }

    @Override
    public void CreateItems() {
        createJaffaItem(pastrySweet);
        createJaffaItem(jamO);
        createJaffaItem(jamR);

        createJaffaFood(cake, 1, 0.2F);
        createJaffaFood(jaffaO, 3, 0.7F).setPotionEffect(Potion.regeneration.id, 2, 1, 0.4F);
        createJaffaFood(jaffaR, 3, 0.7F).setPotionEffect(Potion.regeneration.id, 2, 1, 0.4F);
        createJaffaFood(jaffa, 2, 0.5F).setPotionEffect(Potion.regeneration.id, 2, 1, 0.2F);

        createJaffaItem(chocolate);
        createJaffaItem(apples);
        createJaffaItem(beans);
        createJaffaItem(sweetBeans);
        createJaffaItem(butter);

        createJaffaTool(mallet, 8);
        createJaffaTool(malletStone, 16);
        createJaffaTool(malletIron, 64);
        createJaffaTool(malletDiamond, 512);

        createJaffaItem(malletHead);
        createJaffaItem(malletHeadStone);
        createJaffaItem(malletHeadIron);
        createJaffaItem(malletHeadDiamond);

        createJaffaItem(browniesPastry);
        createJaffaItem(puffPastry);
        //createJaffaItem(JaffaItem.peanut);
        createJaffaItem(cream);
        createJaffaItem(sweetRoll);
        createJaffaItem(cakeTin);
        createJaffaItem(browniesInTin);

        createJaffaItem(sweetRollRaw);
        createJaffaItem(browniesInTinRaw);

        createJaffaFood(creamRoll, 4, 1F).setPotionEffect(Potion.digSpeed.id, 60, 0, 0.15F);
        createJaffaFood(brownie, 2, 0.6F).setPotionEffect(Potion.jump.id, 60, 0, 0.15F);

        createJaffaItem(bunRaw);
        createJaffaItem(bun);
        createJaffaItem(sausageRaw);
        createJaffaItem(sausage);
        createJaffaItem(flour);

        createJaffaFood(hotdog, 3, 0.7F).setPotionEffect(Potion.damageBoost.id, 60, 0, 0.15F);

        createJaffaItem(chocolateWrapper);

        createJaffaFood(chocolateBar, 3, 0.9F).setPotionEffect(Potion.moveSpeed.id, 60, 0, 0.25F);

        createJaffaItem(wrapperJaffas);

        ((IItemJaffa) createJaffaItemManual(jaffasPack, ItemJaffaPack.class)).setRarity(proxy.getEpicRarity());

        createJaffaItem(vanillaBeans);
        createJaffaItem(waferIcecream);
        createJaffaItem(cone);
        createJaffaItem(vanillaPowder);
        createJaffaItem(vanillaIcecreamRaw);
        createJaffaItem(chocolateIcecreamRaw);
        createJaffaItem(icecreamRaw);
        createJaffaItem(vanillaIcecreamFrozen);
        createJaffaItem(chocolateIcecreamFrozen);
        createJaffaItem(icecreamFrozen);
        createJaffaFood(vanillaIcecream, 2, 0.3F).setPotionEffect(Potion.moveSpeed.id, 70, 0, 0.25F);
        createJaffaFood(chocolateIcecream, 2, 0.3F).setPotionEffect(Potion.moveSpeed.id, 70, 0, 0.25F);
        createJaffaFood(russianIcecream, 2, 0.3F).setPotionEffect(Potion.moveSpeed.id, 70, 0, 0.25F);

        createJaffaItem(donutRaw);
        createJaffaItem(donut);
        createJaffaItem(jamP);
        createJaffaItem(jamL);
        createJaffaItem(jamV);
        createJaffaItem(lemons);
        createJaffaItem(oranges);
        createJaffaItem(plums);
        createJaffaItem(sprinkles);
        createJaffaItemManual(magnifier, ItemMagnifier.class);

        createJaffaFood(jaffaP, 3, 0.7F).setPotionEffect(Potion.regeneration.id, 2, 1, 0.4F);
        createJaffaFood(jaffaV, 3, 0.7F).setPotionEffect(Potion.regeneration.id, 2, 1, 0.4F);
        createJaffaFood(jaffaL, 3, 0.7F).setPotionEffect(Potion.regeneration.id, 2, 1, 0.4F);

        createJaffaFood(donutChocolate, 2, 0.3F).setPotionEffect(Potion.digSpeed.id, 60, 0, 0.15F);
        createJaffaFood(donutPink, 2, 0.3F).setPotionEffect(Potion.digSpeed.id, 60, 0, 0.15F);
        createJaffaFood(donutSugar, 2, 0.3F).setPotionEffect(Potion.damageBoost.id, 60, 0, 0.15F);
        createJaffaFood(donutSprinkled, 2, 0.9F).setPotionEffect(Potion.damageBoost.id, 45, 1, 0.20F);

        createJaffaItem(jamMix);

        createJaffaItem(kettle);
        createJaffaItem(kettleWaterCold);
        createJaffaItem(kettleWaterHot).setMaxDamage(5).setMaxStackSize(1);
        createJaffaItem(cup);
        createJaffaFood(cupCoffee, 2, 0.2F).
                setReturnItem(new ItemStack(getItem(cup))).setIsDrink().
                setPotionEffect(Potion.digSpeed.id, 90, 0, 1F).setAlwaysEdible().setMaxStackSize(16);
        createJaffaItem(cupRaw);
        createJaffaItem(omeletteRaw);
        createJaffaFood(omelette, 3, 0.5F).setPotionEffect(Potion.regeneration.id, 4, 0, 0.2F).setMaxStackSize(16);
        createJaffaItem(tomatoChopped);
        createJaffaItem(paprikaChopped);
        createJaffaItem(wienerCocktail);
        createJaffaItem(jamRaspberry);
        createJaffaItem(jamStrawberry);
        createJaffaItem(raspberries);
        createJaffaItem(strawberries);
        createJaffaItem(rollRaw);
        createJaffaItem(roll);
        createJaffaItem(rollChopped);
        createJaffaItem(meatChopped);
        createJaffaItem(ironSkewer);
        createJaffaFood(skewer, 4, 0.5F).setReturnItem(new ItemStack(getItem(ironSkewer))).setPotionEffect(Potion.jump.id, 80, 0, 0.15F);
        createJaffaItem(skewerRaw);
        createJaffaItem(knifeKitchen).setMaxDamage(256).setMaxStackSize(1);

        createJaffaFood(jaffaStrawberry, 3, 0.7F).setPotionEffect(Potion.regeneration.id, 2, 1, 0.4F);
        createJaffaFood(jaffaRaspberry, 3, 0.7F).setPotionEffect(Potion.regeneration.id, 2, 1, 0.4F);

        createJaffaItem(coffee);
        createJaffaItem(coffeeRoasted);

        createJaffaItem(brownPastry);

        createJaffaItem(coconutPowder);
        createJaffaItem(honey);
        createJaffaItem(gingerbread);

        createJaffaItem(hamburgerBunRaw);
        createJaffaItem(hamburgerBun);
        createJaffaItem(cheese);
        createJaffaItem(cheeseSlice);
        createJaffaItem(rawBurger);
        createJaffaItem(burger);
        createJaffaItem(onionSliced);

        createJaffaFood(hamburger, 6, 0.9f).setPotionEffect(Potion.nightVision.id, 60, 0, 0.3f);
        createJaffaFood(cheeseburger, 6, 0.7f).setPotionEffect(Potion.nightVision.id, 80, 0, 0.3f);

        createJaffaItem(fryingPan);
        createJaffaItem(fryingPanBurgerRaw);
        createJaffaItem(fryingPanEggRaw);
        createJaffaItem(fryingPanBurger);
        createJaffaItem(fryingPanEgg);
        createJaffaFood(eggFried, 2, 0.5f).setMaxStackSize(16);
        createJaffaItem(bread);
        createJaffaItem(breadRaw);
        createJaffaItem(breadSlice);

        createJaffaFood(breadSliceToasted, 3, 0.33f);
        createJaffaFood(breadSliceJam, 3, 0.33f).setPotionEffect(Potion.waterBreathing.id, 60, 0, 0.15F);
        createJaffaFood(breadSliceButter, 2, 0.33f).setPotionEffect(Potion.waterBreathing.id, 30, 0, 0.1F);
        createJaffaFood(breadSliceEgg, 3, 0.33f).setPotionEffect(Potion.waterBreathing.id, 60, 0, 0.15F);

        createJaffaItem(bottleEmpty);
        createJaffaItem(bottleKetchup);
        createJaffaItem(bottleMustard);
        createJaffaItem(bottleBrownMustard);
        createJaffaItem(mincedMeat);
        JaffaItemInfo cleaverInfo = ItemManager.getItemInfo(meatCleaver);
        createJaffaItemManual(meatCleaver,
                new ItemCleaver(cleaverInfo.getId(), cleaverInfo.getIconIndex(), JaffasFood.EnumToolMaterialCleaver));

        createJaffaItemManual(sink, ItemSink.class);

        createJaffaItem(coneRaw);
        createJaffaItem(waferIcecreamRaw);

        createJaffaItem(grater);
        createJaffaItem(cheeseGrated);
        createJaffaItem(salami);
        createJaffaItem(salamiSliced);
        createJaffaItem(pizzaRaw);
        createJaffaItemManual(pizza, ItemPizza.class);

        createJaffaItem(wolfSkin);
        createJaffaArmorSet("wolf", JaffasFood.EnumArmorMaterialWolf, "/jaffas_wolf1.png", "/jaffas_wolf2.png", getItem(wolfSkin), new JaffaItem[]{wolfHelmet, wolfChest, wolfLeggins, wolfBoots});

        createJaffaItem(pastry);

        createJaffaItem(milkBoxEmpty);
        createJaffaItem(milkBoxFull);

        ((IItemJaffa) createJaffaItem(crumpledPaper)).setInfo("temporary recipe");
        createJaffaItem(scrap);

        createJaffaFood(chips, 2, 1.5f).setPotionEffect(Potion.moveSlowdown.id, 5, 0, 0.2f);
        createJaffaItem(chipsRaw);
        createJaffaItem(fryingPanChips);
        createJaffaItem(fryingPanChipsRaw);

        createJaffaItem(pieStrawberryRaw);
        createJaffaItem(pieRaspberryRaw);
        createJaffaItem(pieVanillaRaw);
        createJaffaItem(piePlumRaw);

        if (JaffasFood.spawnStonesEnabled) {
            createJaffaItemManual(spawnStoneLittle, new ItemSpawnStone(ItemManager.getItemInfo(spawnStoneLittle), JaffasFood.spawnStoneLittleCD));
            createJaffaItemManual(spawnStoneMedium, new ItemSpawnStone(ItemManager.getItemInfo(spawnStoneMedium), JaffasFood.spawnStoneMediumCD));
            createJaffaItemManual(spawnStoneBig, new ItemSpawnStone(ItemManager.getItemInfo(spawnStoneBig), JaffasFood.spawnStoneBigCD));
        }

        createJaffaItem(juiceBottle);
        createJaffaItem(glassEmpty);
        for (Juice juice : Juice.values()) {
            createJaffaFood(juice.juiceBottle, 12, 1f).setIsDrink().setReturnItem(new ItemStack(getItem(juiceBottle)));
            createJaffaFood(juice.glass, 5, 0.25f).setIsDrink().setReturnItem(new ItemStack(getItem(glassEmpty)));
        }

        ((ItemJaffaFood) createJaffaFood(glassMilk).Setup(1, 0.1f)).setIsDrink().setReturnItem(new ItemStack(getItem(glassEmpty)));

        createJaffaItem(woodenBowl);
        createJaffaItem(cookedMushroomsRaw);
        createJaffaFood(cookedMushrooms, 6, 0.5f).setReturnItem(new ItemStack(getItem(woodenBowl))).setPotionEffect(Potion.fireResistance.id, 30, 0, 0.1f).setMaxStackSize(32);

        createJaffaItem(pepperStuffedRaw);
        createJaffaFood(pepperStuffed, 5, 1.0f).setPotionEffect(Potion.resistance.id, 60, 0, 0.2F).setMaxStackSize(16);

        createJaffaItem(peanutsSugar);
        createJaffaFood(peanutsCaramelized, 4, 0.2f).setPotionEffect(Potion.jump.id, 30, 0, 0.2F);

        createJaffaFood(wolfMeatRaw, 1, 0.05f).setPotionEffect(Potion.hunger.id, 15, 1, 0.2F);
        createJaffaFood(muttonRaw, 1, 0.05f).setPotionEffect(Potion.hunger.id, 15, 1, 0.2F);
        registerWolfFood(muttonRaw);
        createJaffaFood(spiderLegRaw, 1, 0.07f).setPotionEffect(Potion.poison.id, 4, 0, 0.2F);
        registerWolfFood(spiderLegRaw);

        createJaffaFood(wolfMeat, 4, 0.7f);
        createJaffaFood(mutton, 4, 0.7f);
        registerWolfFood(mutton);
        createJaffaFood(spiderLeg, 4, 0.7f);
        registerWolfFood(spiderLeg);

        createJaffaItem(JaffaItem.featherDuck);
        createJaffaFood(duckRaw, 1, 0.05f).setPotionEffect(Potion.hunger.id, 15, 1, 0.2F);
        registerWolfFood(duckRaw);
        createJaffaFood(JaffaItem.duck, 4, 0.7f);
        registerWolfFood(duck);

        createJaffaItem(JaffaItem.plateRaw);
        createJaffaItem(JaffaItem.plate);

        createJaffaFood(JaffaItem.plateDuckOrange, 8, 0.8f).setReturnItem(getItemStack(plate, 1)).setPotionEffect(Potion.regeneration.id, 10, 0, 1f).setMaxStackSize(24);
        createJaffaItem(JaffaItem.tinDuckOrangeRaw);
        createJaffaItem(JaffaItem.tinDuckOrange);

        createJaffaArmorSet("duck", JaffasFood.EnumArmorMaterialDuck, "/jaffas_duckarmor1.png", "/jaffas_duckarmor2.png", getItem(featherDuck), new JaffaItem[]{duckHelmet, duckChest, duckLeggins, duckBoots});

        createJaffaItem(strawberryIcecreamRaw);
        createJaffaItem(strawberryIcecreamFrozen);
        createJaffaFood(strawberryIcecream, 2, 0.3f).setPotionEffect(Potion.moveSpeed.id, 70, 0, 0.25F);

        createJaffaItem(chocIceStick);
        createJaffaFood(chocIce, 4, 1f).setReturnItem(new ItemStack(getItem(chocIceStick))).setPotionEffect(Potion.moveSpeed.id, 70, 0, 0.25F);

        createJaffaItemManual(duckEgg, ItemDuckEgg.class).setMaxStackSize(16);

        createJaffaItem(muffinRaw);
        createJaffaItem(muffinUnfinished);
        createJaffaFood(muffin, 3, 0.5f).setReturnItem(getItemStack(crumpledPaper, 1)).setPotionEffect(Potion.regeneration.id, 4, 0, 0.1f);

        createJaffaItem(beansWithTomatoRaw);
        createJaffaFood(beansWithTomato, 6, 0.7f).setReturnItem(getItemStack(woodenBowl, 1)).setPotionEffect(Potion.fireResistance.id, 30, 0, 0.1f).setMaxStackSize(16);

        createJaffaFood(sandwich1, 6, 0.5f).setPotionEffect(Potion.waterBreathing.id, 90, 0, 0.25f);

        createJaffaItem(lambWithPeasInTinRaw);
        createJaffaItem(lambWithPeasInTin);
        createJaffaFood(lambWithPeas, 8, 1f).setReturnItem(getItemStack(plate, 1)).setPotionEffect(Potion.regeneration.id, 6, 0, 1f).setMaxStackSize(24);

        createJaffaItem(cocoBarWrapper);
        createJaffaItem(cookingPot).setMaxStackSize(16);
        createJaffaItem(cookingPotCocoaCold).setMaxStackSize(16);
        createJaffaItem(cookingPotCocoaHot).setMaxStackSize(16);
        createJaffaFood(eggHardBoiled, 2, 0.5f).setMaxStackSize(16);
        createJaffaFood(cupCocoa, 4, 0.5F).
                setReturnItem(new ItemStack(getItem(cup))).setIsDrink().
                setPotionEffect(Potion.moveSpeed.id, 60, 0, .55F).setAlwaysEdible().setMaxStackSize(16);
        createJaffaFood(cocoBar, 5, 0.5F).setReturnItem(getItemStack(crumpledPaper), 0.3333f).setPotionEffect(Potion.moveSpeed.id, 60, 0, 0.15F);
        createJaffaItem(cookingPotWater).setMaxStackSize(16);
        createJaffaItem(cookingPotEggsRaw).setMaxStackSize(16);
        createJaffaItem(cookingPotEggs).setMaxStackSize(16);

        BlockSink.addFillableItem(getItem(kettle), getItem(kettleWaterCold));
        BlockSink.addFillableItem(getItem(cookingPot), getItem(cookingPotWater));

        createJaffaFood(bananaInChocolate, 3, 1.2f).setPotionEffect(Potion.regeneration.id, 4, 0, 0.1f);

        createJaffaFood(fruitSalad, 4, 0.6f).setPotionEffect(Potion.moveSpeed.id, 30, 0, 0.33f).setReturnItem(getItemStack(woodenBowl));
        createJaffaItem(friedMushroomsInTinRaw);
        createJaffaItem(friedMushroomsInTinCooked);
        createJaffaFood(friedMushrooms, 6, 0.95f).setPotionEffect(Potion.regeneration.id, 6, 0, 1f).setReturnItem(getItemStack(plate)).setMaxStackSize(24);

        createJaffaItem(mincedMushrooms);
        createJaffaItem(shroomburger);
        createJaffaItem(shroomburgerRaw);

        createJaffaFood(shroomburgerInBunWithCheese, 6, 0.7f).setPotionEffect(Potion.nightVision.id, 80, 0, 0.15f);
        createJaffaFood(shroomburgerInBun, 5, 0.85f).setPotionEffect(Potion.nightVision.id, 60, 0, 0.15f);

        createJaffaItem(fryingPanShroomburgerRaw);
        createJaffaItem(fryingPanShroomburger);

        createJaffaItem(flyAgaricChopped);

        createJaffaItemManual(meatDryer, ItemMeatDryer.class);
        createJaffaFood(driedMeat, 5, 1f);

        createJaffaItem(beerMugEmpty).setMaxStackSize(16);
        createJaffaFood(beerMugFull, 4, 1.5f).setIsDrink().setAlwaysEdible().setReturnItem(getItemStack(beerMugEmpty)).setPotionEffect(Potion.damageBoost.id, 30, 0, 0.35f).setMaxStackSize(1);

        /*
                AddItemInfo(JaffaItem.potatesSliced, "Sliced Potatoes", 253);
        AddItemInfo(JaffaItem.potatesSlicedInTinRaw, "Raw Crisps", 254);
        AddItemInfo(JaffaItem.potatesSlicedInTin, "Crisps In Tin", 255);
        AddItemInfo(JaffaItem.crisps, "Crisps", 256);

         */
        createJaffaItem(potatesSliced);
        createJaffaItem(potatesSlicedInTinRaw);
        createJaffaItem(potatesSlicedInTin);
        createJaffaFood(crisps, 3, 0.8f).setReturnItem(getItemStack(woodenBowl));

        createItemsOreDictRegistration();

        addMeatsToDryerDatabase();

        markJaffasRare();
    }

    private void markJaffasRare() {
        for (JaffaItem ji : JaffasHelper.getJaffas()) {
            ((IItemJaffa) getItem(ji)).setRarity(proxy.getUncommonRarity());
        }
    }

    private void addMeatsToDryerDatabase() {
        TileMeatDryer.addNormalMeat(Recipes.getItem(JaffaItem.duckRaw));
        TileMeatDryer.addNormalMeat(Recipes.getItem(JaffaItem.muttonRaw));
        TileMeatDryer.addNormalMeat(Recipes.getItem(JaffaItem.wolfMeatRaw));
        TileMeatDryer.addZombieMeat(Recipes.getItem(JaffaItem.spiderLegRaw));
    }

    public void createJaffaArmor(JaffaItem item, EnumArmorMaterial material, int renderIndex, ItemJaffaPlate.ArmorType type, String texture, Item repairItem) {
        JaffaItemInfo info = ItemManager.getItemInfo(item);
        createJaffaItemManual(item, new ItemJaffaPlate(info.getId(), material, renderIndex, type, texture, repairItem, info.getIconIndex()));
    }

    public void createJaffaArmor(JaffaItem item, EnumArmorMaterial material, int renderIndex, ItemJaffaPlate.ArmorType type, String texture, JaffaItem repairItem) {
        createJaffaArmor(item, material, renderIndex, type, texture, (repairItem == null || repairItem == _last) ? null : getItem(repairItem));
    }

    // helm, chest, leggings, boots
    public void createJaffaArmorSet(String renderName, EnumArmorMaterial material, String file1, String file2, Item repairItem, JaffaItem[] pieces) {
        int renderIndex = proxy.addArmor(renderName);
        createJaffaArmor(pieces[0], material, renderIndex, ItemJaffaPlate.ArmorType.helm, file1, repairItem);
        createJaffaArmor(pieces[1], material, renderIndex, ItemJaffaPlate.ArmorType.chest, file1, repairItem);
        createJaffaArmor(pieces[2], material, renderIndex, ItemJaffaPlate.ArmorType.leggings, file2, repairItem);
        createJaffaArmor(pieces[3], material, renderIndex, ItemJaffaPlate.ArmorType.boots, file1, repairItem);
    }

    private void createItemsOreDictRegistration() {
        OreDictionary.registerOre(MINCEABLEMEAT, Item.porkRaw);
        OreDictionary.registerOre(MINCEABLEMEAT, Item.fishRaw);
        OreDictionary.registerOre(MINCEABLEMEAT, Item.beefRaw);
        OreDictionary.registerOre(MINCEABLEMEAT, Item.chickenRaw);
        OreDictionary.registerOre(MINCEABLEMEAT, getItem(muttonRaw));
        OreDictionary.registerOre(MINCEABLEMEAT, getItem(wolfMeatRaw));
        OreDictionary.registerOre(MINCEABLEMEAT, getItem(duckRaw));

        JaffasHelper.registerJaffasInOreDict();

        OreDictionary.registerOre(MUSHROOM, Block.mushroomBrown);
        OreDictionary.registerOre(MUSHROOM, Block.mushroomRed);

        OreDictionary.registerOre(EGG, Item.egg);
        OreDictionary.registerOre(EGG, getItem(duckEgg));

        OreDictionary.registerOre(MALLET, getItemStackAnyDamage(mallet));
        OreDictionary.registerOre(MALLET, getItemStackAnyDamage(malletStone));
        OreDictionary.registerOre(MALLET, getItemStackAnyDamage(malletIron));
        OreDictionary.registerOre(MALLET, getItemStackAnyDamage(malletDiamond));
    }
}
