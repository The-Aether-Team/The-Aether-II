package com.aetherteam.aetherii.event.hooks;

import com.aetherteam.aetherii.entity.passive.FlyingCow;
import com.aetherteam.aetherii.item.AetherIIItems;
import com.aetherteam.aetherii.item.miscellaneous.bucket.SkyrootBucketItem;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Bucketable;
import net.minecraft.world.entity.animal.Cow;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.level.Level;

import java.util.Optional;

public class EntityInteractionHooks {
    public static void skyrootBucketMilking(Entity target, Player player, InteractionHand hand) {
        if ((target instanceof Cow || target instanceof FlyingCow) && !((Animal) target).isBaby()) {
            ItemStack heldStack = player.getItemInHand(hand);
            if (heldStack.is(AetherIIItems.SKYROOT_BUCKET.get())) {
//                if (target instanceof FlyingCow) {
//                    player.playSound(AetherSoundEvents.ENTITY_FLYING_COW_MILK.get(), 1.0F, 1.0F);
//                } else {
                    player.playSound(SoundEvents.COW_MILK, 1.0F, 1.0F);
//                }
                ItemStack filledBucket = ItemUtils.createFilledResult(heldStack, player, AetherIIItems.SKYROOT_MILK_BUCKET.get().getDefaultInstance());
                player.swing(hand);
                player.setItemInHand(hand, filledBucket);
            }
        }
    }

    public static Optional<InteractionResult> pickupBucketable(Entity target, Player player, InteractionHand hand) {
        ItemStack heldStack = player.getItemInHand(hand);
        Optional<InteractionResult> interactionResult = Optional.empty();
        if (heldStack.is(AetherIIItems.SKYROOT_WATER_BUCKET.get())) { // Checks if the player is interacting with an entity with a Skyroot Water Bucket.
            if (target instanceof Bucketable bucketable && target instanceof LivingEntity livingEntity && livingEntity.isAlive()) {
                ItemStack bucketStack = bucketable.getBucketItemStack();
                bucketStack = SkyrootBucketItem.swapBucketType(bucketStack); // Swaps the bucket stack that contains an entity with a Skyroot equivalent.
                if (!bucketStack.isEmpty()) {
                    target.playSound(bucketable.getPickupSound(), 1.0F, 1.0F);
                    bucketable.saveToBucketTag(bucketStack);
                    ItemStack filledStack = ItemUtils.createFilledResult(heldStack, player, bucketStack, false);
                    player.setItemInHand(hand, filledStack);
                    Level level = livingEntity.level();
                    if (!level.isClientSide()) {
                        CriteriaTriggers.FILLED_BUCKET.trigger((ServerPlayer) player, bucketStack);
                    }
                    target.discard();
                    interactionResult = Optional.of(InteractionResult.sidedSuccess(level.isClientSide()));
                } else {
                    interactionResult = Optional.of(InteractionResult.FAIL);
                }
            }
        }
        return interactionResult;
    }
}
