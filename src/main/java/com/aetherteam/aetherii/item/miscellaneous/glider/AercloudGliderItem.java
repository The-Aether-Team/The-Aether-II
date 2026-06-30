package com.aetherteam.aetherii.item.miscellaneous.glider;

import com.aetherteam.aetherii.AetherIITags;
import com.aetherteam.aetherii.attachment.AetherIIDataAttachments;
import com.aetherteam.aetherii.attachment.player.AbilityBehaviorAttachment;
import com.aetherteam.aetherii.client.AetherIIClientExtensions;
import com.aetherteam.aetherii.client.AetherIIClientProxy;
import com.aetherteam.aetherii.entity.attributes.AetherIIAttributes;
import com.aetherteam.aetherii.item.miscellaneous.ToggleItem;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.minecraftforge.common.ForgeMod;

import java.util.function.Consumer;

public class AercloudGliderItem extends Item implements ToggleItem {
    public AercloudGliderItem(Properties properties) {
        super(properties);
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(AetherIIClientExtensions.GLIDER);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (AetherIIDataAttachments.get(player, AetherIIDataAttachments.ABILITY_BEHAVIOR).getGlidingTimer() < 0) {
            AetherIIDataAttachments.get(player, AetherIIDataAttachments.ABILITY_BEHAVIOR).setGlidingTimer(this.getUseDuration(stack, player));
        }
        if (!player.onGround() && AetherIIDataAttachments.get(player, AetherIIDataAttachments.ABILITY_BEHAVIOR).getGlidingTimer() > 0) {
            player.startUsingItem(hand);
            this.onParachuteOpen(level, player, hand, stack);
            if (AetherIIDataAttachments.get(player, AetherIIDataAttachments.ABILITY_BEHAVIOR).getCanRefuelGlide()) {
                AetherIIDataAttachments.get(player, AetherIIDataAttachments.ABILITY_BEHAVIOR).setCanRefuelGlide(false);
            }
            return InteractionResultHolder.consume(stack);
        } else {
            return InteractionResultHolder.fail(stack);
        }
    }

    @Override
    public void onUseTick(Level level, LivingEntity entity, ItemStack stack, int remainingTicks) {
        if (entity instanceof Player player) {
            int timer = AetherIIDataAttachments.get(player, AetherIIDataAttachments.ABILITY_BEHAVIOR).getGlidingTimer();

            if (level.isClientSide()) {
                float x = entity.xxa * 0.5F; // Side-to-side movement is slowed.
                float z = entity.zza; // Forward movement is normal.
                if (z <= 0.0F) {
                    z *= 0.25F; // Backwards movement is slowed.
                }
                Vec3 travelVec = new Vec3(x, entity.yya, z);

                AttributeInstance gravity = entity.getAttribute(ForgeMod.ENTITY_GRAVITY.get());
                double gravityModifier = gravity != null ? gravity.getValue() : 0.08;

                double y = entity.getDeltaMovement().y();
                if (!entity.isNoGravity()) {
                    y -= gravityModifier;
                }
                y *= 0.98;

                double descent = !entity.isShiftKeyDown() ? -0.025 : -0.15;
                double fallSpeed = Math.max(gravityModifier * -3.125, descent); // Slows fall speed and slows the parachute from falling too slow and getting stuck midair.

                if (entity.getDeltaMovement().y() < -0.075) {
                    entity.setDeltaMovement(entity.getDeltaMovement().x(), Math.max(y, fallSpeed), entity.getDeltaMovement().z());
                    this.calculateMovement(entity, travelVec);
                }
            }

            if (entity.onGround() || timer <= 0) {
                entity.stopUsingItem();
            } else {
                AetherIIDataAttachments.get(player, AetherIIDataAttachments.ABILITY_BEHAVIOR).setGlidingTimer(Math.max(timer - 1, 0));
            }

            if (timer > this.getUseDuration(stack, entity)) {
                AetherIIDataAttachments.get(player, AetherIIDataAttachments.ABILITY_BEHAVIOR).setGlidingTimer(this.getUseDuration(stack, entity));
            }
        }
        super.onUseTick(level, entity, stack, remainingTicks);
    }

    public void calculateMovement(LivingEntity entity, Vec3 vec3) {
        float speed = 0.03F;
        entity.moveRelative(speed, vec3);
        entity.move(MoverType.SELF, entity.getDeltaMovement());
        entity.getDeltaMovement();
    }

    @Override
    public void releaseUsing(ItemStack stack, Level level, LivingEntity entity, int timeLeft) {
        if (!level.isClientSide()) {
            stack.hurtAndBreak(1, entity, livingEntity -> livingEntity.broadcastBreakEvent(entity.getUsedItemHand()));
        }
        if (entity instanceof Player player) {
            boolean reset = false;
            if (AetherIIDataAttachments.get(player, AetherIIDataAttachments.ABILITY_BEHAVIOR).getGlidingTimer() <= 0) {
                this.setCooldowns(player, 80);
                reset = true;
            } else {
                this.setCooldowns(player, 2);
                if (player.onGround()) {
                    reset = true;
                }
            }
            if (reset) {
                AetherIIDataAttachments.get(player, AetherIIDataAttachments.ABILITY_BEHAVIOR).setGlidingTimer(-1);
                if (AetherIIDataAttachments.get(player, AetherIIDataAttachments.ABILITY_BEHAVIOR).getCanRefuelAbilities().containsKey(stack.getItemHolder()) && !AetherIIDataAttachments.get(player, AetherIIDataAttachments.ABILITY_BEHAVIOR).getCanRefuelAbilities().get(stack.getItemHolder())) {
                    AetherIIDataAttachments.get(player, AetherIIDataAttachments.ABILITY_BEHAVIOR).getCanRefuelAbilities().put(stack.getItemHolder(), true);
                }
            }
        }
        super.releaseUsing(stack, level, entity, timeLeft);
    }

    public int getUseDuration(ItemStack stack, LivingEntity entity) {
        return AetherIIAttributes.getMaxEndurance(entity) * 5;
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        return 72000;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack p_41452_) {
        return UseAnim.NONE;
    }

    @Override
    public boolean isBarVisible(ItemStack stack) {
        if (this.isGliding()) {
            return true;
        }
        return super.isBarVisible(stack);
    }

    @Override
    public int getBarWidth(ItemStack stack) {
        if (this.isGliding()) {
            AbilityBehaviorAttachment data = AetherIIClientProxy.getClientPlayerData(AetherIIDataAttachments.ABILITY_BEHAVIOR);
            if (data != null) {
                return Math.round((float) data.getGlidingTimer() * 13.0F / this.getUseDuration(stack, AetherIIClientProxy.getClientPlayer()));
            }
        }
        return super.getBarWidth(stack);
    }

    @Override
    public int getBarColor(ItemStack stack) {
        if (this.isGliding()) {
            return 3183871;
        }
        return super.getBarColor(stack);
    }

    private boolean isGliding() {
        Player player = AetherIIClientProxy.getClientPlayer();
        if (player != null && player.getUseItem().getItem() instanceof AercloudGliderItem) {
            int progress = AetherIIDataAttachments.get(player, AetherIIDataAttachments.ABILITY_BEHAVIOR).getGlidingTimer();
            return progress > 0 && progress < this.getUseDuration(player.getUseItem(), player);
        }
        return false;
    }

    private void setCooldowns(Player player, int cooldown) {
        BuiltInRegistries.ITEM.getTagOrEmpty(AetherIITags.Items.TOOLS_GLIDERS).forEach((item) -> player.getCooldowns().addCooldown(item.value(), cooldown));
    }

    protected void onParachuteOpen(Level level, Player player, InteractionHand hand, ItemStack stack) {

    }
}
