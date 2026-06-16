package com.aetherteam.aetherii.item.consumables;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.attachment.AetherIIDataAttachments;
import com.aetherteam.aetherii.effect.AetherIIMobEffects;
import com.aetherteam.aetherii.effect.buildup.EffectBuildupPresets;
import com.aetherteam.aetherii.item.components.AetherIIDataComponents;
import net.minecraft.ChatFormatting;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.level.Level;

import java.util.function.Consumer;

public class HealingStoneItem extends Item {
    public static final Identifier BONUS_ABSORPTION = Identifier.fromNamespaceAndPath(AetherII.MODID, "healing_stone.bonus_health");

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
                player.getData(AetherIIDataAttachments.EFFECTS_SYSTEM).addBuildup(player, EffectBuildupPresets.AMBROSIUM_POISONING, 350);
                if (player.getHealth() + 8.0F > player.getMaxHealth()) {
                    int absorption = (int) (Mth.floor(player.getHealth()) + 8.0F - player.getMaxHealth());
                    player.addEffect(new MobEffectInstance(AetherIIMobEffects.HEALING_OVERFLOW, -1, absorption, false, false, false));
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
    public ItemUseAnimation getUseAnimation(ItemStack stack) {
        return ItemUseAnimation.DRINK;
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        ItemStack itemStack = player.getItemInHand(hand);
        Integer charges = itemStack.get(AetherIIDataComponents.HEALING_STONE_CHARGES);
        if (charges != null && charges > 0 && player.getHealth() < player.getMaxHealth()) {
            return ItemUtils.startUsingInstantly(level, player, hand);
        }
        return InteractionResult.PASS;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay tooltipDisplay, Consumer<Component> tooltipComponents, TooltipFlag flag) {
        Integer charges = stack.get(AetherIIDataComponents.HEALING_STONE_CHARGES);
        tooltipComponents.accept(Component.translatable("aether_ii.tooltip.item.healing_stone.charges", charges).withStyle(ChatFormatting.GRAY));
    }
}
