/*
 * Jaffas and more!
 * author: monnef
 */

package monnef.jaffas.food.nei;

import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.ICraftingHandler;
import codechicken.nei.recipe.IUsageHandler;
import codechicken.nei.recipe.TemplateRecipeHandler;
import monnef.jaffas.power.block.common.ContainerBasicProcessingMachine;
import monnef.jaffas.power.block.common.TileEntityBasicProcessingMachine;
import monnef.jaffas.power.common.IProcessingRecipe;
import monnef.jaffas.power.common.RecipeItemType;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.Collection;

public class ProcessingMachineRecipeHandler extends TemplateRecipeHandler implements ICraftingHandler, IUsageHandler {
    private TileEntityBasicProcessingMachine tile;
    private final static int SLOT_SHIFT_X = -5;
    private final static int SLOT_SHIFT_Y = -11;

    public ProcessingMachineRecipeHandler(TileEntityBasicProcessingMachine tile) {
        this.tile = tile;
    }

    @Override
    public String getGuiTexture() {
        return tile.getGuiBackgroundTexture();
    }

    @Override
    public String getRecipeName() {
        return tile.getMachineTitle();
    }

    @Override
    public void loadCraftingRecipes(ItemStack result) {
        processRecipes(true, result, RecipeItemType.OUTPUT);
    }

    @Override
    public void loadUsageRecipes(ItemStack ingredient) {
        processRecipes(false, ingredient, RecipeItemType.INPUT);
    }

    private void processRecipes(boolean repeatForEachResult, ItemStack filter, RecipeItemType type) {
        Collection<IProcessingRecipe> recipes = tile.getRecipeHandler().copyRecipes();
        for (IProcessingRecipe recipe : recipes) {
            if (!recipe.isAny(filter, type)) continue;
            for (int i = 0; i < recipe.getOutput().length; i++) {
                arecipes.add(new CachedMachineRecipe(recipe, i, tile.getMyContainerPrototype()));
                if (!repeatForEachResult) break;
            }
        }
    }

    public class CachedMachineRecipe extends CachedRecipe {
        private final IProcessingRecipe recipe;
        private final int outputNumber;
        private final ContainerBasicProcessingMachine container;

        public CachedMachineRecipe(IProcessingRecipe recipe, int outputNumber, ContainerBasicProcessingMachine container) {
            this.recipe = recipe;
            this.outputNumber = outputNumber;
            this.container = container;
        }

        @Override
        public ArrayList<PositionedStack> getIngredients() {
            ArrayList<PositionedStack> res = new ArrayList<PositionedStack>();

            for (int i = 0; i < recipe.getInput().length; i++) {
                res.add(getPositionStack(i, RecipeItemType.INPUT));
            }

            for (int i = 0; i < recipe.getOutput().length; i++) {
                if (i != outputNumber)
                    res.add(getPositionStack(i, RecipeItemType.OUTPUT));
            }

            return res;
        }

        @Override
        public PositionedStack getResult() {
            return getPositionStack(outputNumber, RecipeItemType.OUTPUT);
        }

        protected PositionedStack getPositionStack(int numberInGroup, RecipeItemType type) {
            ItemStack outputStack = (type == RecipeItemType.INPUT ? recipe.getInput() : recipe.getOutput())[numberInGroup].copy();
            Slot slot = type == RecipeItemType.INPUT ? container.getInputSlot(numberInGroup) : container.getOutputSlot(numberInGroup);
            return new PositionedStack(outputStack, slot.xDisplayPosition + SLOT_SHIFT_X, slot.yDisplayPosition + SLOT_SHIFT_Y);
        }
    }

}