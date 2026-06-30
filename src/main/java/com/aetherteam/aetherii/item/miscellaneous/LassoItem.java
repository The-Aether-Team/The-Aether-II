package com.aetherteam.aetherii.item.miscellaneous;

import com.aetherteam.aetherii.attachment.AetherIIDataAttachments;
import com.aetherteam.aetherii.client.AetherIIClientExtensions;
import com.aetherteam.aetherii.client.sound.AetherIISoundEvents;
import com.aetherteam.aetherii.entity.projectile.LassoLoop;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.LeadItem;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;

import java.util.function.Consumer;

public class LassoItem extends LeadItem {
    public LassoItem(Properties properties) {
        super(properties);
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(AetherIIClientExtensions.THROWABLE);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (!hasActiveLassoLoop(level, player)) {
            player.startUsingItem(hand);
        }
        return InteractionResultHolder.consume(stack);
    }

    @Override
    public InteractionResult interactLivingEntity(ItemStack stack, Player player, LivingEntity target, InteractionHand hand) {
        if (target instanceof Mob mob && !(mob.getLeashHolder() instanceof Player) && mob.canBeLeashed(player)) {
            if (!player.level().isClientSide()) {
                this.leashMob(stack, player, mob, hand);
            }
            return InteractionResult.sidedSuccess(player.level().isClientSide());
        }
        return super.interactLivingEntity(stack, player, target, hand);
    }

    @Override
    public void releaseUsing(ItemStack stack, Level level, LivingEntity livingEntity, int timeLeft) {
        LassoLoop projectile = new LassoLoop(level, livingEntity);
        level.playSound(null, livingEntity.getX(), livingEntity.getY(), livingEntity.getZ(), AetherIISoundEvents.ENTITY_LASSO_THROW.get(), SoundSource.NEUTRAL, 0.5F, 0.4F / (level.getRandom().nextFloat() * 0.4F + 0.8F));
        if (!level.isClientSide()) {
            int i = stack.getUseDuration() - timeLeft;
            float velocity = Math.min(1.5F * (i / 20.0F), 1.5F);
            float inaccuracy = Mth.clamp(4.0F - (velocity * 2.0F), 1.0F, 4.0F);
            projectile.shootFromRotation(livingEntity, livingEntity.getXRot(), livingEntity.getYRot(), 0.0F, velocity, inaccuracy);
            AetherIIDataAttachments.set(projectile, AetherIIDataAttachments.LASSO_CONNECTION, true);
            level.addFreshEntity(projectile);
            AetherIIDataAttachments.sync(projectile, AetherIIDataAttachments.LASSO_CONNECTION);
        }
        if (level.isClientSide()) {
            livingEntity.swing(livingEntity.getUsedItemHand());
        }
        if (livingEntity instanceof Player player) {
            player.awardStat(Stats.ITEM_USED.get(stack.getItem()));
            if (!player.getAbilities().instabuild) {
                stack.hurtAndBreak(1, livingEntity, (entity) -> entity.broadcastBreakEvent(livingEntity.getUsedItemHand()));
                player.getCooldowns().addCooldown(stack.getItem(), 10);
            }
        }
    }

    private void leashMob(ItemStack stack, Player player, Mob mob, InteractionHand hand) {
        mob.setLeashedTo(player, true);
        AetherIIDataAttachments.set(mob, AetherIIDataAttachments.LASSO_CONNECTION, true);
        AetherIIDataAttachments.sync(mob, AetherIIDataAttachments.LASSO_CONNECTION);
        player.awardStat(Stats.ITEM_USED.get(stack.getItem()));
        if (!player.getAbilities().instabuild) {
            stack.hurtAndBreak(1, player, entity -> entity.broadcastBreakEvent(hand));
            player.getCooldowns().addCooldown(stack.getItem(), 10);
        }
    }

    private static boolean hasActiveLassoLoop(Level level, Player player) {
        return !level.getEntitiesOfClass(LassoLoop.class, player.getBoundingBox().inflate(64.0), loop -> loop.getOwner() == player).isEmpty();
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.NONE;
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        return 72000;
    }
}
