package com.aetherteam.aetherii.item.consumables;

import com.aetherteam.aetherii.attachment.AetherIIDataAttachments;
import com.aetherteam.aetherii.client.sound.AetherIISoundEvents;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUseAnimation;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class ShiftingGlassItem extends Item {
    public ShiftingGlassItem(Properties properties) {
        super(properties.durability(500));
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        if (player.getData(AetherIIDataAttachments.PLAYER).isMovingHorizontally() && player.getData(AetherIIDataAttachments.ABILITY_BEHAVIOR).isCanRefreshShiftingGlass()) {
            player.startUsingItem(hand);
            return InteractionResult.CONSUME;
        }
        return super.use(level, player, hand);
    }

    @Override
    public void onUseTick(Level level, LivingEntity livingEntity, ItemStack itemStack, int ticksRemaining) {
        if (livingEntity instanceof Player player) {
            if (!player.getData(AetherIIDataAttachments.PLAYER).isMovingHorizontally()) {
                player.stopUsingItem();
            }
        }
        super.onUseTick(level, livingEntity, itemStack, ticksRemaining);
    }

    @Override
    public boolean releaseUsing(ItemStack stack, Level level, LivingEntity livingEntity, int timeLeft) {
        if (livingEntity instanceof Player player && this.getUseDuration(stack, livingEntity) - timeLeft > 10) {
            ItemStack itemStack = player.getItemInHand(livingEntity.getUsedItemHand());
            float scale = 1.0F;
            if (player.onGround()) {
                scale = 1.35F;
            }
            if (player.isSprinting()) {
                scale *= 0.75F;
            }
            Vec3 boost = new Vec3(player.xxa * scale, 0.4, player.zza * scale).yRot(-player.getYRot() * Mth.DEG_TO_RAD);
            player.setDeltaMovement(boost);
            player.resetFallDistance();
            level.playSound(null, player.getX(), player.getY(), player.getZ(), AetherIISoundEvents.ITEM_SHIFTING_GLASS_USE, SoundSource.NEUTRAL, 1.0F, 0.4F / (level.getRandom().nextFloat() * 0.4F + 0.8F));
            if (!level.isClientSide()) {
                player.getData(AetherIIDataAttachments.ABILITY_BEHAVIOR).setShiftingGlassBoostTime(8);
                player.getData(AetherIIDataAttachments.ABILITY_BEHAVIOR).setCanRefreshShiftingGlass(false);
                player.syncData(AetherIIDataAttachments.ABILITY_BEHAVIOR);
            }
            if (!player.getAbilities().instabuild) {
                itemStack.hurtAndBreak(1, player, livingEntity.getUsedItemHand());
            }
            if (player instanceof ServerPlayer serverPlayer) {
                CriteriaTriggers.USING_ITEM.trigger(serverPlayer, itemStack);
                serverPlayer.awardStat(Stats.ITEM_USED.get(this));
            }
            return true;
        }
        return super.releaseUsing(stack, level, livingEntity, timeLeft);
    }

    @Override
    public ItemUseAnimation getUseAnimation(ItemStack stack) {
        return ItemUseAnimation.BOW;
    }

    @Override
    public int getUseDuration(ItemStack stack, LivingEntity livingEntity) {
        return 72000;
    }
}