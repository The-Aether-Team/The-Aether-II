package com.gildedgames.aether.client.events.listeners;

import com.gildedgames.aether.api.registrar.ItemsAether;
import com.gildedgames.aether.common.capabilities.entity.player.PlayerAether;
import com.gildedgames.aether.common.capabilities.entity.player.modules.PlayerEquipmentModule;
import com.gildedgames.aether.common.items.armor.ItemAetherGloves;
import com.gildedgames.aether.common.items.armor.ItemArkeniumArmor;
import com.gildedgames.aether.common.items.weapons.crossbow.ItemCrossbow;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraftforge.client.event.FOVUpdateEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

@Mod.EventBusSubscriber(Side.CLIENT)
public class FOVUpdateListener
{
    @SubscribeEvent
    public static void onFOVUpdate(final FOVUpdateEvent event)
    {
        EntityPlayer player = Minecraft.getMinecraft().player;

        int armorCount = 0;

        for (ItemStack armor : player.getArmorInventoryList())
        {
            if (armor.getItem() instanceof ItemArkeniumArmor)
            {
                ++armorCount;

                if (event.getNewfov() < 1.0f)
                {
                    event.setNewfov(event.getFov() + (0.0375f * armorCount));
                }
            }
        }

        final ItemStack gloveStack = PlayerAether.getPlayer(player).getModule(PlayerEquipmentModule.class).getInventory().getStackInSlot(2);

        if (!gloveStack.isEmpty() && gloveStack.getItem() == ItemsAether.arkenium_gloves)
        {
            ++armorCount;

            if (event.getNewfov() < 1.0f)
            {
                event.setNewfov(event.getFov() + (0.0375f * armorCount));
            }
        }

        Item item = player.getActiveItemStack().getItem();

        if (item instanceof ItemCrossbow)
        {
            if (((ItemCrossbow) item).getIsSpecialLoaded() && player.isSneaking())
            {
                if (event.getFov() <= 0.5f)
                {
                    event.setNewfov(event.getNewfov() + 0.3f);
                }
            }
        }

        if ((player.getHeldItem(EnumHand.MAIN_HAND).getItem() == ItemsAether.arkenium_shield && player.getActiveItemStack() != player.getHeldItemOffhand())
                || (player.getHeldItem(EnumHand.OFF_HAND).getItem() == ItemsAether.arkenium_shield && player.getActiveItemStack() != player.getHeldItemMainhand()))
        {
            if (event.getFov() <= 0.5f)
            {
                event.setNewfov(event.getNewfov() + 0.5f);
            }
        }

        if ((player.getHeldItem(EnumHand.MAIN_HAND).getItem() == ItemsAether.skyroot_shield && player.getActiveItemStack() != player.getHeldItemOffhand())
                || (player.getHeldItem(EnumHand.OFF_HAND).getItem() == ItemsAether.skyroot_shield && player.getActiveItemStack() != player.getHeldItemMainhand()))
        {
            if (event.getFov() >= 1.525f)
            {
                event.setNewfov(event.getNewfov() - 0.525f);
            }
        }

        System.out.println(event.getFov());
    }
}
