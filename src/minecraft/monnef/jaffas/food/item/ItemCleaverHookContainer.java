package monnef.jaffas.food.item;

import monnef.core.PlayerHelper;
import monnef.jaffas.food.mod_jaffas;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

import java.util.HashMap;
import java.util.Random;

public class ItemCleaverHookContainer {
    private static String DamageSourcePlayer = "player";

    private static int meatCleaverID = 0;
    private static Random rand = new Random();

    private static HashMap<Class<? extends EntityAnimal>, ItemStack> AnimalToMeat;

    static {
        AnimalToMeat = new HashMap<Class<? extends EntityAnimal>, ItemStack>();
        AnimalToMeat.put(EntityCow.class, new ItemStack(Item.beefRaw));
        AnimalToMeat.put(EntityPig.class, new ItemStack(Item.porkRaw));
    }

    private ItemStack getMeatFromAnimal(EntityAnimal animal) {
        return AnimalToMeat.get(animal.getClass()).copy();
    }

    @ForgeSubscribe
    public void entityHurt(LivingHurtEvent event) {
        DamageSource source = event.source;
        EntityLiving mob = event.entityLiving;

        if (SourceIsPlayer(source)) {
            EntityPlayer player = (EntityPlayer) source.getEntity();
            if (PlayerHasEquipped(player, getMeatCleaverID())) {
                if (mob instanceof EntityCow || mob instanceof EntityPig) {
                    event.ammount += 10;
                }
                PlayerHelper.damageCurrentItem(player);
            }
        }
    }

    @ForgeSubscribe
    public void entityDeath(LivingDeathEvent event) {
        DamageSource source = event.source;
        EntityLiving mob = event.entityLiving;

        if (SourceIsPlayer(source)) {
            EntityPlayer player = (EntityPlayer) source.getEntity();
            if (PlayerHasEquipped(player, getMeatCleaverID())) {
                if (mob instanceof EntityCow || mob instanceof EntityPig) {
                    EntityAnimal animal = (EntityAnimal) mob;
                    if (AnimalIsAdult(animal)) {
                        for (int i = 0; i < 2; i++) {
                            if (rand.nextFloat() < .5) {
                                ItemStack loot = getMeatFromAnimal(animal);
                                DropLoot(animal, loot);
                            }
                        }
                    }
                }
            }
        }
    }

    private boolean AnimalIsAdult(EntityAnimal animal) {
        return animal.getGrowingAge() >= 0;
    }

    private boolean PlayerHasEquipped(EntityPlayer player, int itemId) {
        ItemStack equippedItem = player.getCurrentEquippedItem();
        if (equippedItem == null) return false;
        return equippedItem.itemID == itemId;
    }

    private boolean SourceIsPlayer(DamageSource source) {
        return source.damageType.equals(DamageSourcePlayer);
    }

    private void DropLoot(EntityLiving entity, ItemStack loot) {
        EntityItem item = new EntityItem(entity.worldObj, entity.posX, entity.posY, entity.posZ, loot);
        entity.worldObj.spawnEntityInWorld(item);
    }

    private int getMeatCleaverID() {
        if (meatCleaverID == 0) {
            meatCleaverID = mod_jaffas.getItem(JaffaItem.meatCleaver).shiftedIndex;
        }

        return meatCleaverID;
    }
}