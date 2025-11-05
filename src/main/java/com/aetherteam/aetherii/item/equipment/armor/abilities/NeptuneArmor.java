package com.aetherteam.aetherii.item.equipment.armor.abilities;


import com.aetherteam.aetherii.AetherIITags;
import com.aetherteam.aetherii.attachment.AetherIIDataAttachments;
import com.aetherteam.aetherii.item.equipment.EquipmentUtil;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.event.tick.EntityTickEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

public interface NeptuneArmor {
    /**
     * Boosts the entity's movement in water or bubble columns if wearing a full set of Neptune Armor. The default boost is modified based on duration in water and whether the boots have Depth Strider.
     *
     * @param entity The {@link LivingEntity} wearing the armor.
     * @see com.aetherteam.aether.event.listeners.abilities.ArmorAbilityListener#onEntityUpdate(EntityTickEvent.Post)
     */
    static void playerUpdate(PlayerTickEvent.Post event) {
        Player player = event.getEntity();
        if (EquipmentUtil.hasArmorAbility(player, AetherIITags.Items.NEPTUNE_ARMOR) && player.isInWater()) {
            var data = player.getData(AetherIIDataAttachments.ABILITY_BEHAVIOR);
            float defaultBoost = boostWithDepthStrider(player);
            data.setNeptuneSubmergeLength(Math.min(data.getNeptuneSubmergeLength() + 0.1, 1.0));
            defaultBoost *= (float) data.getNeptuneSubmergeLength();
            player.moveRelative(0.04F * defaultBoost, new Vec3(player.xxa, player.yya, player.zza));
            if (player.isSwimming() || player.getDeltaMovement().y() > 0 || player.isCrouching()) {
                player.move(MoverType.SELF, player.getDeltaMovement().multiply(0.0, defaultBoost, 0.0));
            }
        } else {
            player.getData(AetherIIDataAttachments.ABILITY_BEHAVIOR).setNeptuneSubmergeLength(0.0);
        }
    }

    /**
     * Adds an extra 0.15 to the boost for every Depth Strider level up to Depth Strider 3.
     *
     * @param entity The {@link LivingEntity} wearing the armor.
     * @return The modified boost as a {@link Float}.
     */
    private static float boostWithDepthStrider(LivingEntity entity) {
        float defaultBoost = 0.4F;
        float depthStriderModifier = Math.min(EnchantmentHelper.getEnchantmentLevel(entity.level().holderOrThrow(Enchantments.INFINITY), entity), 3.0F);
        if (depthStriderModifier > 0.0F) {
            defaultBoost += depthStriderModifier * 0.4F;
        }
        if (entity.isSwimming() && entity.getDeltaMovement().y() < 0) {
            defaultBoost *= 0.5F;
        }
        return defaultBoost;
    }
}