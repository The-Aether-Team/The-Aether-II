package com.aetherteam.aetherii.item.equipment.armor.abilities;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.item.equipment.EquipmentUtil;
import com.aetherteam.aetherii.item.equipment.armor.AetherIIArmorMaterials;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.common.damagesource.DamageContainer;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

public interface ArkeniumArmor {
    ResourceLocation ARKENIUM_BLAST_RESISTANCE = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "armor_set.ability.arkenium.blast_resistance");

    static void updatePlayerAttributes(PlayerTickEvent.Pre event) {
        Player player = event.getEntity();
        AttributeInstance blastResistanceAttribute = player.getAttribute(Attributes.EXPLOSION_KNOCKBACK_RESISTANCE);

        if (EquipmentUtil.hasArmorAbility(player, AetherIIArmorMaterials.ARKENIUM)) {
            if (blastResistanceAttribute != null && !blastResistanceAttribute.hasModifier(ARKENIUM_BLAST_RESISTANCE)) {
                blastResistanceAttribute.addTransientModifier(new AttributeModifier(ARKENIUM_BLAST_RESISTANCE, 0.3F, AttributeModifier.Operation.ADD_VALUE));
            }
        } else {
            if (blastResistanceAttribute != null && blastResistanceAttribute.hasModifier(ARKENIUM_BLAST_RESISTANCE)) {
                blastResistanceAttribute.removeModifier(ARKENIUM_BLAST_RESISTANCE);
            }
        }
    }

    static void modifyIncomingDamage(LivingIncomingDamageEvent event) {
        DamageSource damageSource = event.getSource();
        LivingEntity entity = event.getEntity();
        if (EquipmentUtil.hasArmorAbility(entity, AetherIIArmorMaterials.ARKENIUM)) {
            if (damageSource.is(DamageTypeTags.IS_EXPLOSION) && !damageSource.is(DamageTypeTags.BYPASSES_INVULNERABILITY)) {
                event.addReductionModifier(DamageContainer.Reduction.ARMOR, (container, reduction) -> {
                    float f = Mth.clamp(4.0F, 0.0F, 20.0F);
                    float f1 = container.getNewDamage() * (1.0F - f / 25.0F);
                    float f2 = container.getNewDamage() - f1;
                    return reduction + f2;
                });
            }
        }
    }
}
