package com.aetherteam.aetherii.item.consumables;

import java.util.List;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.attachment.AetherIIDataAttachments;
import com.aetherteam.aetherii.effect.AetherIIMobEffects;
import com.aetherteam.aetherii.effect.buildup.EffectBuildupPresets;
import com.aetherteam.aetherii.item.components.AetherIIDataComponents;
import net.minecraft.ChatFormatting;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.level.Level;

import java.util.function.Consumer;

public class HealingStoneItem extends Item {
    public static final ResourceLocation BONUS_ABSORPTION = new ResourceLocation(AetherII.MODID, "healing_stone.bonus_health");

    public HealingStoneItem(Properties properties) {
        super(properties);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entityLiving) {
        Integer charges = AetherIIDataComponents.get(stack, AetherIIDataComponents.HEALING_STONE_CHARGES);
        Player player = entityLiving instanceof Player ? (Player) entityLiving : null;

        if (charges != null && charges > 0) {
            if (player instanceof ServerPlayer serverPlayer) {
                CriteriaTriggers.CONSUME_ITEM.trigger(serverPlayer, stack);
            }

            if (player != null) {
                player.awardStat(Stats.ITEM_USED.get(this));
                AetherIIDataAttachments.get(player, AetherIIDataAttachments.EFFECTS_SYSTEM).addBuildup(player, EffectBuildupPresets.AMBROSIUM_POISONING, 350);
                int absorption = 0;
                if (player.getHealth() + 8.0F > player.getMaxHealth()) {
                    absorption = (int) (Mth.floor(player.getHealth()) + 8.0F - player.getMaxHealth());
                    player.addEffect(new MobEffectInstance(AetherIIMobEffects.HEALING_OVERFLOW.get(), -1, absorption, false, false, false));
                }
                player.heal(8.0F);
                if (absorption > 0) {
                    player.setAbsorptionAmount(Math.max(player.getAbsorptionAmount(), absorption));
                }
            }

            if (player != null && !player.getAbilities().instabuild) {
                AetherIIDataComponents.set(stack, AetherIIDataComponents.HEALING_STONE_CHARGES, Math.max(charges - 1, 0));
            }
        }

        return stack;
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        return 32;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.DRINK;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack itemStack = player.getItemInHand(hand);
        Integer charges = AetherIIDataComponents.get(itemStack, AetherIIDataComponents.HEALING_STONE_CHARGES);
        if (charges != null && charges > 0 && player.getHealth() < player.getMaxHealth()) {
            return ItemUtils.startUsingInstantly(level, player, hand);
        }
        return InteractionResultHolder.pass(itemStack);
    }

    @Override
    public void appendHoverText(ItemStack stack, net.minecraft.world.level.Level level, List<Component> tooltipComponents, TooltipFlag flag) {
        Integer charges = AetherIIDataComponents.get(stack, AetherIIDataComponents.HEALING_STONE_CHARGES);
        tooltipComponents.add(Component.translatable("aether_ii.tooltip.item.healing_stone.charges", charges).withStyle(ChatFormatting.GRAY));
    }
}
