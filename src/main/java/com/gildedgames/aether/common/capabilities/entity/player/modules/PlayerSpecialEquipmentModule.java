package com.gildedgames.aether.common.capabilities.entity.player.modules;

import com.gildedgames.aether.api.entity.damage.DamageTypeAttributes;
import com.gildedgames.aether.client.gui.container.guidebook.GuiGuidebookInventory;
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
    private int abilityDelay = 0;

    public PlayerSpecialEquipmentModule(PlayerAether playerAether)
    {
        super(playerAether);
    }

    @Override
    public void tickStart(TickEvent.PlayerTickEvent event)
    {
        this.applyGloveAbility(event);
    }

    private void abilityDelayTimer()
    {
        ++this.abilityDelay;
    }

    private void applyGloveAbility(TickEvent.PlayerTickEvent event)
    {
        final ItemStack gloveStack = PlayerAether.getPlayer(event.player).getModule(PlayerEquipmentModule.class).getInventory().getStackInSlot(2);

        final UUID GLOVE_MODIFIERS = UUID.fromString("1A1D30BD-CF8E-4996-B76A-5C139A8F9685");

        AttributeModifier ATTACK_SPEED = new AttributeModifier(GLOVE_MODIFIERS, "Attack speed modifier", 0, StatEffectFactory.StatProvider.OP_ADD);
        AttributeModifier SLASH_DAMAGE = new AttributeModifier(GLOVE_MODIFIERS, "Slash damage level modifier", 0, StatEffectFactory.StatProvider.OP_ADD);
        AttributeModifier PIERCE_DAMAGE = new AttributeModifier(GLOVE_MODIFIERS,"Pierce damage level modifier", 0, StatEffectFactory.StatProvider.OP_ADD);
        AttributeModifier IMPACT_DAMAGE = new AttributeModifier(GLOVE_MODIFIERS,"Impact damage level modifier", 0, StatEffectFactory.StatProvider.OP_ADD);

        IAttributeInstance attackSpeed = event.player.getEntityAttribute(SharedMonsterAttributes.ATTACK_SPEED);
        IAttributeInstance slashDamageLevel = event.player.getEntityAttribute(DamageTypeAttributes.SLASH_DAMAGE_LEVEL);
        IAttributeInstance pierceDamageLevel = event.player.getEntityAttribute(DamageTypeAttributes.PIERCE_DAMAGE_LEVEL);
        IAttributeInstance impactDamageLevel = event.player.getEntityAttribute(DamageTypeAttributes.IMPACT_DAMAGE_LEVEL);

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

            if (event.player.openContainer != event.player.inventoryContainer)
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

        if (this.abilityDelay < 15)
        {
            this.abilityDelayTimer();
        }

        if (!event.player.getHeldItemMainhand().isEmpty() || gloveStack.isEmpty())
        {
            if (slashDamageLevel.getModifier(SLASH_DAMAGE.getID()) != null)
            {
                slashDamageLevel.setBaseValue(0);
                slashDamageLevel.removeModifier(SLASH_DAMAGE);
            }
            if (pierceDamageLevel.getModifier(PIERCE_DAMAGE.getID()) != null)
            {
                pierceDamageLevel.setBaseValue(0);
                pierceDamageLevel.removeModifier(PIERCE_DAMAGE);
            }
            if (impactDamageLevel.getModifier(IMPACT_DAMAGE.getID()) != null)
            {
                impactDamageLevel.setBaseValue(0);
                impactDamageLevel.removeModifier(IMPACT_DAMAGE);
            }

            this.abilityDelay = 0;
        }
        else if (event.player.getHeldItemMainhand().isEmpty() && gloveStack.getItem() instanceof ItemAetherGloves && !gloveStack.isEmpty())
        {
            if (this.abilityDelay >= 7)
            {
                double newSlashDamageLevel = ((ItemAetherGloves) gloveStack.getItem()).getSlashDamageLevel();
                double newPierceDamageLevel = ((ItemAetherGloves) gloveStack.getItem()).getPierceDamageLevel();
                double newImpactDamageLevel = ((ItemAetherGloves) gloveStack.getItem()).getImpactDamageLevel();

                if (!slashDamageLevel.hasModifier(SLASH_DAMAGE))
                {
                    slashDamageLevel.setBaseValue(newSlashDamageLevel);
                    slashDamageLevel.applyModifier(SLASH_DAMAGE);
                }
                if (!pierceDamageLevel.hasModifier(PIERCE_DAMAGE))
                {
                    pierceDamageLevel.setBaseValue(newPierceDamageLevel);
                    pierceDamageLevel.applyModifier(PIERCE_DAMAGE);
                }
                if (!impactDamageLevel.hasModifier(IMPACT_DAMAGE))
                {
                    impactDamageLevel.setBaseValue(newImpactDamageLevel);
                    impactDamageLevel.applyModifier(IMPACT_DAMAGE);
                }
            }
        }
    }
}
