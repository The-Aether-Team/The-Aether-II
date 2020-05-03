package com.gildedgames.aether.common.capabilities.entity.player.modules;

import com.gildedgames.aether.api.entity.damage.DamageTypeAttributes;
import com.gildedgames.aether.client.gui.container.guidebook.GuiGuidebookInventory;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.capabilities.entity.player.PlayerAether;
import com.gildedgames.aether.common.capabilities.entity.player.PlayerAetherModule;
import com.gildedgames.aether.common.capabilities.item.effects.stats.StatEffectFactory;
import com.gildedgames.aether.common.items.armor.ItemAetherGloves;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.UUID;

public class PlayerSpecialEquipmentModule extends PlayerAetherModule
{
    public boolean hasOpenScreen = false;

    public PlayerSpecialEquipmentModule(PlayerAether playerAether)
    {
        super(playerAether);
    }

    @Override
    public void tickStart(TickEvent.PlayerTickEvent event)
    {
        this.applyGloveAbility(event);
    }

    private void applyGloveAbility(TickEvent.PlayerTickEvent event)
    {
        final ItemStack gloveStack = PlayerAether.getPlayer(event.player).getModule(PlayerEquipmentModule.class).getInventory().getStackInSlot(2);

        final UUID GLOVE_MODIFIERS = UUID.fromString("1A1D30BD-CF8E-4996-B76A-5C139A8F9685");

        AttributeModifier ATTACK_SPEED = new AttributeModifier(GLOVE_MODIFIERS, "Attack speed modifier", 0, StatEffectFactory.StatProvider.OP_ADD);

        IAttributeInstance attackSpeed = event.player.getEntityAttribute(SharedMonsterAttributes.ATTACK_SPEED);

        if (AetherCore.isClient())
        {
            this.hasOpenScreen = Minecraft.getMinecraft().currentScreen != null;
        }

        if (!event.player.getHeldItemMainhand().isEmpty() || gloveStack.isEmpty())
        {
            if (attackSpeed.getModifier(ATTACK_SPEED.getID()) != null)
            {
                attackSpeed.setBaseValue(4.0D);
                attackSpeed.removeModifier(ATTACK_SPEED);
            }
        }
        else if (event.player.getHeldItemMainhand().isEmpty() && gloveStack.getItem() instanceof ItemAetherGloves && !gloveStack.isEmpty())
        {
            double newAttackSpeed = ((ItemAetherGloves) gloveStack.getItem()).getAttackSpeed();

            if (this.hasOpenScreen)
            {
                if (attackSpeed.getModifier(ATTACK_SPEED.getID()) != null)
                {
                    attackSpeed.setBaseValue(4.0D);
                    attackSpeed.removeModifier(ATTACK_SPEED);
                }
            }
            else
            {
                if (!attackSpeed.hasModifier(ATTACK_SPEED))
                {
                    attackSpeed.setBaseValue(newAttackSpeed);
                    attackSpeed.applyModifier(ATTACK_SPEED);
                }
            }
        }
    }
}
