package com.gildedgames.aether.common.events.listeners.items;

import com.gildedgames.aether.api.player.inventory.IInventoryEquipment;
import com.gildedgames.aether.common.capabilities.entity.player.PlayerAether;
import com.gildedgames.aether.common.capabilities.entity.player.modules.PlayerEquipmentModule;
import com.gildedgames.aether.common.items.armor.ItemAetherGloves;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class ItemGlovesListener
{
    @SubscribeEvent
    public static void onPlayerAttack(final AttackEntityEvent event)
    {
        if (!(event.getEntityLiving() instanceof EntityPlayer))
        {
            return;
        }

        final EntityPlayer player = (EntityPlayer) event.getEntityLiving();
        final PlayerAether playerAether = PlayerAether.getPlayer(player);

        final IInventoryEquipment equipmentInventory = playerAether.getModule(PlayerEquipmentModule.class).getInventory();

        final ItemStack gloveStack = equipmentInventory.getStackInSlot(2);

        if (!event.getTarget().getIsInvulnerable())
        {
            if (player.getHeldItemMainhand().isEmpty())
            {
                if (gloveStack.getItem() instanceof ItemAetherGloves && !gloveStack.isEmpty())
                {
                    gloveStack.damageItem(1, player);

                    if (gloveStack.getItem().getDamage(gloveStack) < 0)
                    {
                        equipmentInventory.setInventorySlotContents(2, ItemStack.EMPTY);
                        player.playSound(SoundEvents.ENTITY_ITEM_BREAK, 0.8F, 0.8F + player.world.rand.nextFloat() * 0.4F);
                    }
                }
            }
        }
    }
}