package com.aetherteam.aetherii.item.miscellaneous;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.item.components.AetherIIDataComponents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class GliderItem extends Item {
    public static final int GLIDING_MAX = 1000; //todo

    public GliderItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        Integer timer = stack.get(AetherIIDataComponents.GLIDING_TIMER);
        if (!player.onGround()) {
            player.startUsingItem(hand);
            if (stack.has(AetherIIDataComponents.GLIDING_TIMER) && timer != null) {
                stack.set(AetherIIDataComponents.GLIDING_TIMER, GLIDING_MAX);
            }
            return super.use(level, player, hand);
        } else {
            return InteractionResultHolder.fail(stack);
        }
    }

    @Override
    public void onUseTick(Level level, LivingEntity entity, ItemStack stack, int remainingTicks) {
        Integer timer = stack.get(AetherIIDataComponents.GLIDING_TIMER);
        if (!level.isClientSide() && remainingTicks % 10 == 0) {
            stack.hurtAndBreak(1, entity, LivingEntity.getSlotForHand(entity.getUsedItemHand()));
        } else {
            float x = entity.xxa * 0.5F; // Side-to-side movement is slowed.
            float z = entity.zza; // Forward movement is normal.
            if (z <= 0.0F) {
                z *= 0.25F; // Backwards movement is slowed.
            }
            Vec3 travelVec = new Vec3(x, entity.yya, z);

            AttributeInstance gravity = entity.getAttribute(Attributes.GRAVITY);
            double gravityModifier = gravity != null ? gravity.getValue() : 0.08;

            Vec3 movement = this.calculateMovement(entity, travelVec);
            double y = movement.y();
            if (!entity.isNoGravity()) {
                y -= gravityModifier;
            }
            y *= 0.98;

            double fallSpeed = Math.max(gravityModifier * -3.125, -0.01); // Slows fall speed and slows the parachute from falling too slow and getting stuck midair.
            entity.setDeltaMovement(movement.x(), Math.max(y, fallSpeed), movement.z());

            entity.resetFallDistance();
        }
        if (entity.onGround() || (timer != null && timer <= 0)) {
            entity.stopUsingItem();
            if (timer != null) {
                stack.set(AetherIIDataComponents.GLIDING_TIMER, 0);
            }
        } else {
            if (stack.has(AetherIIDataComponents.GLIDING_TIMER) && timer != null) {
                stack.set(AetherIIDataComponents.GLIDING_TIMER, Math.max(timer - 1, 0));
            }
        }
        super.onUseTick(level, entity, stack, remainingTicks);
    }

    public Vec3 calculateMovement(LivingEntity entity, Vec3 vec3) {
        float speed = 0.03F;
        entity.moveRelative(speed, vec3);
        entity.move(MoverType.SELF, entity.getDeltaMovement());
        return entity.getDeltaMovement();
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity) {
        Integer timer = stack.get(AetherIIDataComponents.GLIDING_TIMER);
        if (entity instanceof Player player) {
            player.getCooldowns().addCooldown(stack.getItem(), 100); //todo
            if (timer != null) {
                stack.set(AetherIIDataComponents.GLIDING_TIMER, 0);
            }
        }
        return super.finishUsingItem(stack, level, entity);
    }

    @Override
    public int getUseDuration(ItemStack stack, LivingEntity entity) {
        return GLIDING_MAX;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack p_41452_) {
        return UseAnim.CUSTOM;
    }

    public static int getGlidingTimer(ItemStack stack) {
        Integer value = stack.get(AetherIIDataComponents.GLIDING_TIMER);
        if (value != null) {
            return value;
        }
        return 0;
    }
}
