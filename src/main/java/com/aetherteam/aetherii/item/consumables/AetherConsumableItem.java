package com.aetherteam.aetherii.item.consumables;

import com.aetherteam.aetherii.item.AetherIIConsumables;
import com.aetherteam.aetherii.item.components.Consumable;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;

public class AetherConsumableItem extends Item {
    public AetherConsumableItem(Properties properties) {
        super(properties);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity) {
        return finishUsingItem(this, stack, level, entity, ItemStack.EMPTY);
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        return getUseDurationFor(stack, super.getUseDuration(stack));
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return getUseAnimationFor(stack, super.getUseAnimation(stack));
    }

    @Override
    public void onUseTick(Level level, LivingEntity entity, ItemStack stack, int remainingUseDuration) {
        triggerUseEffects(stack, level, entity, remainingUseDuration);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        return useConsumable(level, player, hand);
    }

    public static ItemStack finishUsingItem(Item item, ItemStack stack, Level level, LivingEntity entity, ItemStack remainder) {
        Consumable consumable = AetherIIConsumables.get(stack);
        UseAnim animation = consumable != null ? consumable.animation() : UseAnim.NONE;
        Player player = entity instanceof Player playerEntity ? playerEntity : null;
        if (player instanceof ServerPlayer serverPlayer) {
            CriteriaTriggers.CONSUME_ITEM.trigger(serverPlayer, stack);
        }
        if (player != null) {
            player.awardStat(Stats.ITEM_USED.get(item));
        }
        if (player == null || !player.getAbilities().instabuild) {
            stack.shrink(1);
            if (!remainder.isEmpty()) {
                if (stack.isEmpty()) {
                    stack = remainder;
                } else if (player != null && !player.getInventory().add(remainder)) {
                    player.drop(remainder, false);
                }
            }
        }
        entity.gameEvent((consumable != null && consumable.consumeParticles()) || animation == UseAnim.EAT ? GameEvent.EAT : GameEvent.DRINK);
        return stack;
    }

    public static int getUseDurationFor(ItemStack stack, int fallback) {
        Consumable consumable = AetherIIConsumables.get(stack);
        return consumable != null ? Math.max(1, Math.round(consumable.consumeSeconds() * 20.0F)) : fallback;
    }

    public static UseAnim getUseAnimationFor(ItemStack stack, UseAnim fallback) {
        Consumable consumable = AetherIIConsumables.get(stack);
        return consumable != null ? consumable.animation() : fallback;
    }

    public static InteractionResultHolder<ItemStack> useConsumable(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        return AetherIIConsumables.get(stack) != null ? ItemUtils.startUsingInstantly(level, player, hand) : InteractionResultHolder.pass(stack);
    }

    public static void triggerUseEffects(ItemStack stack, Level level, LivingEntity entity, int remainingUseDuration) {
        Consumable consumable = AetherIIConsumables.get(stack);
        if (consumable == null || !consumable.consumeParticles() || consumable.animation() == UseAnim.EAT) {
            return;
        }
        int duration = Math.max(1, Math.round(consumable.consumeSeconds() * 20.0F));
        if (remainingUseDuration <= duration - 7 && remainingUseDuration % 4 == 0) {
            triggerFoodUseEffects(stack, level, entity, 5);
        }
        if (remainingUseDuration == 1) {
            triggerFoodUseEffects(stack, level, entity, 16);
        }
    }

    private static void triggerFoodUseEffects(ItemStack stack, Level level, LivingEntity entity, int amount) {
        spawnItemParticles(stack, level, entity, amount);
        entity.playSound(entity.getEatingSound(stack), 0.5F + 0.5F * (float) entity.random.nextInt(2), (entity.random.nextFloat() - entity.random.nextFloat()) * 0.2F + 1.0F);
    }

    private static void spawnItemParticles(ItemStack stack, Level level, LivingEntity entity, int amount) {
        for (int i = 0; i < amount; ++i) {
            Vec3 motion = new Vec3(((double) entity.random.nextFloat() - 0.5) * 0.1, Math.random() * 0.1 + 0.1, 0.0);
            motion = motion.xRot(-entity.getXRot() * ((float) Math.PI / 180.0F));
            motion = motion.yRot(-entity.getYRot() * ((float) Math.PI / 180.0F));
            double yOffset = (double) (-entity.random.nextFloat()) * 0.6 - 0.3;
            Vec3 position = new Vec3(((double) entity.random.nextFloat() - 0.5) * 0.3, yOffset, 0.6);
            position = position.xRot(-entity.getXRot() * ((float) Math.PI / 180.0F));
            position = position.yRot(-entity.getYRot() * ((float) Math.PI / 180.0F));
            position = position.add(entity.getX(), entity.getEyeY(), entity.getZ());
            if (level instanceof ServerLevel serverLevel) {
                serverLevel.sendParticles(new ItemParticleOption(ParticleTypes.ITEM, stack), position.x, position.y, position.z, 1, motion.x, motion.y + 0.05, motion.z, 0.0);
            } else {
                level.addParticle(new ItemParticleOption(ParticleTypes.ITEM, stack), position.x, position.y, position.z, motion.x, motion.y + 0.05, motion.z);
            }
        }
    }
}
