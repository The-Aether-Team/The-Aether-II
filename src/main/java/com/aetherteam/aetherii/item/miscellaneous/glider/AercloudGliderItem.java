package com.aetherteam.aetherii.item.miscellaneous.glider;

import com.aetherteam.aetherii.AetherIITags;
import com.aetherteam.aetherii.attachment.AetherIIDataAttachments;
import net.minecraft.client.Minecraft;
import net.minecraft.core.registries.Registries;
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
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

public class AercloudGliderItem extends Item {
    public static final int GLIDING_MAX = 500;

    public AercloudGliderItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (!player.onGround() && player.getData(AetherIIDataAttachments.PLAYER).getGlidingTimer() > 0) {
            player.startUsingItem(hand);
            this.onParachuteOpen(level, player, hand, stack);
            if (player.getData(AetherIIDataAttachments.PLAYER).getCanRefuelGlide()) {
                player.getData(AetherIIDataAttachments.PLAYER).setCanRefuelGlide(false);
            }
            return super.use(level, player, hand);
        } else {
            return InteractionResultHolder.fail(stack);
        }
    }

    @Override
    public void onUseTick(Level level, LivingEntity entity, ItemStack stack, int remainingTicks) {
        if (entity instanceof Player player) {
            int timer = player.getData(AetherIIDataAttachments.PLAYER).getGlidingTimer();
            if (level.isClientSide()) {
                float x = entity.xxa * 0.5F; // Side-to-side movement is slowed.
                float z = entity.zza; // Forward movement is normal.
                if (z <= 0.0F) {
                    z *= 0.25F; // Backwards movement is slowed.
                }
                Vec3 travelVec = new Vec3(x, entity.yya, z);

                AttributeInstance gravity = entity.getAttribute(Attributes.GRAVITY);
                double gravityModifier = gravity != null ? gravity.getValue() : 0.08;

                double y = entity.getDeltaMovement().y();
                if (!entity.isNoGravity()) {
                    y -= gravityModifier;
                }
                y *= 0.98;

                double fallSpeed = Math.max(gravityModifier * -3.125, -0.025); // Slows fall speed and slows the parachute from falling too slow and getting stuck midair.

                if (entity.getDeltaMovement().y() < -0.075) {
                    entity.setDeltaMovement(entity.getDeltaMovement().x(), Math.max(y, fallSpeed), entity.getDeltaMovement().z());
                    this.calculateMovement(entity, travelVec);
                }
            }

            entity.resetFallDistance();

            if (entity.onGround() || timer <= 0) {
                entity.stopUsingItem();
            } else {
                player.getData(AetherIIDataAttachments.PLAYER).setGlidingTimer(Math.max(timer - 1, 0));
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
    public void onStopUsing(ItemStack stack, LivingEntity entity, int count) {
        if (!entity.level().isClientSide()) {
            stack.hurtAndBreak(1, entity, LivingEntity.getSlotForHand(entity.getUsedItemHand()));
        }
        if (entity instanceof Player player) {
            boolean reset = false;
            if (player.getData(AetherIIDataAttachments.PLAYER).getGlidingTimer() <= 0) {
                this.setCooldowns(player, 80);
                reset = true;
            } else {
                this.setCooldowns(player, 2);
                if (player.onGround()) {
                    reset = true;
                }
            }
            if (reset) {
                player.getData(AetherIIDataAttachments.PLAYER).setGlidingTimer(GLIDING_MAX);
                if (player.getData(AetherIIDataAttachments.PLAYER).getCanRefuelAbilities().containsKey(stack.getItemHolder()) && !player.getData(AetherIIDataAttachments.PLAYER).getCanRefuelAbilities().get(stack.getItemHolder())) {
                    player.getData(AetherIIDataAttachments.PLAYER).getCanRefuelAbilities().put(stack.getItemHolder(), true);
                }
            }
        }
        super.onStopUsing(stack, entity, count);
    }

    @Override
    public int getUseDuration(ItemStack stack, LivingEntity entity) {
        return GLIDING_MAX;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack p_41452_) {
        return UseAnim.CUSTOM;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public boolean isBarVisible(ItemStack stack) {
        if (this.isGliding()) {
            return true;
        }
        return super.isBarVisible(stack);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public int getBarWidth(ItemStack stack) {
        if (this.isGliding()) {
            return Math.round((float) Minecraft.getInstance().player.getData(AetherIIDataAttachments.PLAYER).getGlidingTimer() * 13.0F / (float) AercloudGliderItem.GLIDING_MAX);
        }
        return super.getBarWidth(stack);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public int getBarColor(ItemStack stack) {
        if (this.isGliding()) {
            return 3183871;
        }
        return super.getBarColor(stack);
    }

    @OnlyIn(Dist.CLIENT)
    private boolean isGliding() {
        Player player = Minecraft.getInstance().player;
        if (player != null && player.getUseItem().getItem() instanceof AercloudGliderItem) {
            int progress = player.getData(AetherIIDataAttachments.PLAYER).getGlidingTimer();
            return progress > 0 && progress < AercloudGliderItem.GLIDING_MAX;
        }
        return false;
    }

    private void setCooldowns(Player player, int cooldown) {
        player.level().registryAccess().registryOrThrow(Registries.ITEM).getTagOrEmpty(AetherIITags.Items.TOOLS_GLIDERS).forEach((item) -> player.getCooldowns().addCooldown(item.value(), cooldown)); //todo
    }

    protected void onParachuteOpen(Level level, Player player, InteractionHand hand, ItemStack stack) {

    }
}
