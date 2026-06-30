package com.aetherteam.aetherii.item.equipment.armor.abilities;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.AetherIITags;
import com.aetherteam.aetherii.item.components.ItemAttributeModifiers;
import com.aetherteam.aetherii.item.equipment.EquipmentUtil;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.TickEvent;

public interface ArkeniumArmor {
    ResourceLocation ARKENIUM_BLAST_RESISTANCE = new ResourceLocation(AetherII.MODID, "armor_set.ability.arkenium.blast_resistance");

    static void updatePlayerAttributes(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.START) {
            return;
        }
        Player player = event.player;
        AttributeInstance blastResistanceAttribute = player.getAttribute(Attributes.KNOCKBACK_RESISTANCE);

        if (EquipmentUtil.hasArmorAbility(player, AetherIITags.Items.ARKENIUM_ARMOR)) {
            if (blastResistanceAttribute != null && !ItemAttributeModifiers.hasModifier(blastResistanceAttribute, ARKENIUM_BLAST_RESISTANCE)) {
                blastResistanceAttribute.addTransientModifier(ItemAttributeModifiers.modifier(ARKENIUM_BLAST_RESISTANCE, 0.3F, AttributeModifier.Operation.ADDITION));
            }
        } else {
            if (blastResistanceAttribute != null && ItemAttributeModifiers.hasModifier(blastResistanceAttribute, ARKENIUM_BLAST_RESISTANCE)) {
                ItemAttributeModifiers.removeModifier(blastResistanceAttribute, ARKENIUM_BLAST_RESISTANCE);
            }
        }
    }

    static void modifyIncomingDamage(LivingHurtEvent event) {
        DamageSource damageSource = event.getSource();
        LivingEntity entity = event.getEntity();
        if (EquipmentUtil.hasArmorAbility(entity, AetherIITags.Items.ARKENIUM_ARMOR)) {
            if (damageSource.is(DamageTypeTags.IS_EXPLOSION) && !damageSource.is(DamageTypeTags.BYPASSES_INVULNERABILITY)) {
                float f = Mth.clamp(4.0F, 0.0F, 20.0F);
                event.setAmount(event.getAmount() * (1.0F - f / 25.0F));
            }
        }
    }
}
