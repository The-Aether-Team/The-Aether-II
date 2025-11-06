package com.aetherteam.aetherii.item.miscellaneous;

import com.aetherteam.aetherii.attachment.AetherIIDataAttachments;
import com.aetherteam.aetherii.client.sound.AetherIISoundEvents;
import com.aetherteam.aetherii.entity.AetherIIEntityTypes;
import com.aetherteam.aetherii.entity.projectile.LassoLoop;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Leashable;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUseAnimation;
import net.minecraft.world.item.LeadItem;
import net.minecraft.world.level.Level;

public class LassoItem extends LeadItem {
    public LassoItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        if (Leashable.leashableLeashedTo(player).stream().noneMatch((leashable) -> ((Entity) leashable).getType() == AetherIIEntityTypes.LASSO_LOOP.get())) { //todo or just isempty check?
            player.startUsingItem(hand);
        }
        return InteractionResult.CONSUME;
    }

    @Override
    public boolean releaseUsing(ItemStack stack, Level level, LivingEntity livingEntity, int timeLeft) {
        LassoLoop projectile = new LassoLoop(level, livingEntity);
        level.playSound(null, livingEntity.getX(), livingEntity.getY(), livingEntity.getZ(), AetherIISoundEvents.ENTITY_LASSO_THROW.get(), SoundSource.NEUTRAL, 0.5F, 0.4F / (level.getRandom().nextFloat() * 0.4F + 0.8F));
        if (!level.isClientSide()) {
            int i = stack.getUseDuration(livingEntity) - timeLeft;
            float velocity = Math.min(1.5F * (i / 20.0F), 1.5F);
            float inaccuracy = Mth.clamp(4.0F - (velocity * 2.0F), 1.0F, 4.0F);
            projectile.shootFromRotation(livingEntity, livingEntity.getXRot(), livingEntity.getYRot(), 0.0F, velocity, inaccuracy);
            projectile.setLeashedTo(livingEntity, true);
            projectile.setData(AetherIIDataAttachments.LASSO_CONNECTION, true);
            level.addFreshEntity(projectile);
        }
        if (level.isClientSide()) {
            livingEntity.swing(livingEntity.getUsedItemHand());
        }
        if (livingEntity instanceof Player player) {
            player.awardStat(Stats.ITEM_USED.get(stack.getItem()));
            if (!player.getAbilities().instabuild) {
                stack.hurtAndBreak(1, livingEntity, livingEntity.getUsedItemHand());
                player.getCooldowns().addCooldown(stack, 10);
            }
        }
        return true;
    }

    @Override
    public ItemUseAnimation getUseAnimation(ItemStack pStack) {
        return ItemUseAnimation.NONE;
    }

    @Override
    public int getUseDuration(ItemStack stack, LivingEntity livingEntity) {
        return 72000;
    }
}
