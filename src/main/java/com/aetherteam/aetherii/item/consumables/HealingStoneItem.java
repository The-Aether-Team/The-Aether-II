package com.aetherteam.aetherii.item.consumables;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.attachment.AetherIIDataAttachments;
import com.aetherteam.aetherii.effect.buildup.EffectBuildupPresets;
import com.aetherteam.aetherii.item.components.AetherIIDataComponents;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;

public class HealingStoneItem extends Item {
    public static final ResourceLocation BONUS_HEALTH = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "healing_stone.bonus_health");

    public HealingStoneItem(Properties properties) {
        super(properties);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entityLiving) {
        Integer charges = stack.get(AetherIIDataComponents.HEALING_STONE_CHARGES);
        Player player = entityLiving instanceof Player ? (Player) entityLiving : null;

        if (charges != null && charges > 0) {
            if (player instanceof ServerPlayer serverPlayer) {
                CriteriaTriggers.CONSUME_ITEM.trigger(serverPlayer, stack);
            }

            if (player != null) {
                player.awardStat(Stats.ITEM_USED.get(this));
                player.getData(AetherIIDataAttachments.EFFECTS_SYSTEM).addBuildup(EffectBuildupPresets.AMBROSIUM_POISONING, 350);
                if (player.getHealth() + 8.0F > player.getMaxHealth()) {
                    if (!player.getAttribute(Attributes.MAX_HEALTH).hasModifier(BONUS_HEALTH)) {
                        player.getAttribute(Attributes.MAX_HEALTH).addPermanentModifier(new AttributeModifier(BONUS_HEALTH, player.getHealth() + 8.0F - player.getMaxHealth(), AttributeModifier.Operation.ADD_VALUE));
                    }
                }
                player.heal(8.0F);
            }

            if (player != null && !player.hasInfiniteMaterials()) {
                stack.set(AetherIIDataComponents.HEALING_STONE_CHARGES, Math.max(charges - 1, 0));
            }
        }

        return stack;
    }

    @Override
    public int getUseDuration(ItemStack stack, LivingEntity entity) {
        return 32;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.DRINK;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack itemStack = player.getItemInHand(hand);
        Integer charges = itemStack.get(AetherIIDataComponents.HEALING_STONE_CHARGES);
        if (charges != null && charges > 0 && player.getHealth() < player.getMaxHealth()) {
            return ItemUtils.startUsingInstantly(level, player, hand);
        }
        return InteractionResultHolder.pass(itemStack);
    }
}
