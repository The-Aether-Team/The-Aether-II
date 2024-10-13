package com.aetherteam.aetherii.event.hooks;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.AetherIITags;
import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.block.natural.AetherGrassBlock;
import com.aetherteam.aetherii.block.natural.Snowable;
import com.aetherteam.aetherii.block.portal.AetherPortalShape;
import com.aetherteam.aetherii.entity.passive.FlyingCow;
import com.aetherteam.aetherii.item.AetherIIItems;
import com.aetherteam.aetherii.item.miscellaneous.bucket.SkyrootBucketItem;
import com.aetherteam.aetherii.world.LevelUtil;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Bucketable;
import net.minecraft.world.entity.animal.Cow;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;
import java.util.Optional;

public class PlayerHooks {
    public static boolean playerActivatePortal(Player player, Level level, BlockPos pos, @Nullable Direction direction, ItemStack stack, InteractionHand hand, boolean cancellationStatus) {
        if (direction != null) {
            BlockPos relativePos = pos.relative(direction);
            if (stack.is(AetherIITags.Items.AETHER_PORTAL_ACTIVATION_ITEMS)) { // Checks if the item can activate the portal.
                // Checks whether the dimension can have a portal created in it, and that the portal isn't disabled.
                if ((level.dimension() == LevelUtil.returnDimension() || level.dimension() == LevelUtil.destinationDimension())) {
                    Optional<AetherPortalShape> optional = AetherPortalShape.findEmptyAetherPortalShape(level, relativePos, Direction.Axis.X);
                    if (optional.isPresent()) {
                        optional.get().createPortalBlocks();
                        player.playSound(SoundEvents.BUCKET_EMPTY, 1.0F, 1.0F);
                        player.swing(hand);
                        if (!player.isCreative()) {
                            if (stack.getCount() > 1) {
                                stack.shrink(1);
                                player.addItem(stack.hasCraftingRemainingItem() ? stack.getCraftingRemainingItem() : ItemStack.EMPTY);
                            } else if (stack.isDamageableItem()) {
                                stack.hurtAndBreak(1, player, LivingEntity.getSlotForHand(hand));
                            } else {
                                player.setItemInHand(hand, stack.hasCraftingRemainingItem() ? stack.getCraftingRemainingItem() : ItemStack.EMPTY);
                            }
                        }
                        return true;
                    }
                }
            }
        }
        return cancellationStatus;
    }

    public static boolean snowlogBlock(Player player, Level level, BlockPos pos, ItemStack itemStack, InteractionHand hand, boolean cancellationStatus) {
        BlockState stateInLevel = level.getBlockState(pos);
        if (itemStack.getItem() instanceof BlockItem blockItem) {
            boolean success = false;
            Block blockInHand = blockItem.getBlock();
            if (stateInLevel.is(AetherIIBlocks.ARCTIC_SNOW) && blockInHand instanceof Snowable snowable) {
                level.setBlock(pos, snowable.setSnowy(blockInHand.defaultBlockState()), 1 | 2);
                success = true;
            } else if (blockInHand == AetherIIBlocks.ARCTIC_SNOW.get() && AetherGrassBlock.plantNotSnowed(stateInLevel) && stateInLevel instanceof Snowable snowable) {
                level.setBlock(pos, snowable.setSnowy(stateInLevel), 1 | 2);
                success = true;
            }
            if (success) {
                if (!player.getAbilities().instabuild) {
                    itemStack.shrink(1);
                }
                player.swing(hand);
                return true;
            }
        }
        return cancellationStatus;
    }

    public static boolean ferrositeMudBottleConversion(Player player, Level level, BlockPos pos, ItemStack itemStack, InteractionHand hand, Direction face, boolean cancellationStatus) {
        PotionContents potionContents = itemStack.get(DataComponents.POTION_CONTENTS);
        if ((face != Direction.DOWN && potionContents != null && potionContents.is(Potions.WATER))) {
            if (level.getBlockState(pos).getBlock() == AetherIIBlocks.FERROSITE_SAND.get()) {

                level.setBlockAndUpdate(pos, AetherIIBlocks.FERROSITE_MUD.get().defaultBlockState());
                player.awardStat(Stats.ITEM_USED.get(itemStack.getItem()));
                if (!player.getAbilities().instabuild) {
                    itemStack.shrink(1);
                    ItemStack bottleStack = new ItemStack(Items.GLASS_BOTTLE);
                    if (!player.addItem(bottleStack)) {
                        Containers.dropItemStack(player.level(), player.getX(), player.getY(), player.getZ(), bottleStack);
                    }
                }

                if (!level.isClientSide) {
                    if(level instanceof ServerLevel serverLevel) {
                        for (int i = 0; i < 5; i++) {
                            serverLevel.sendParticles(ParticleTypes.SPLASH,
                                    (double) pos.getX() + level.random.nextDouble(),
                                    pos.getY() + 1,
                                    (double) pos.getZ() + level.random.nextDouble(),
                                    1, 0.0, 0.0, 0.0, 1.0
                            );
                        }
                    }
                }

                level.playSound(null, pos, SoundEvents.GENERIC_SPLASH, SoundSource.BLOCKS, 1.0F, 1.0F);

                if (!player.getAbilities().instabuild) {
                    itemStack.shrink(1);
                }
                player.swing(hand);
                return true;
            }
        }
        return cancellationStatus;
    }

    public static void milkWithSkyrootBucket(Entity target, Player player, InteractionHand hand) {
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

    public static Optional<InteractionResult> pickupBucketableTarget(Entity target, Player player, InteractionHand hand, Optional<InteractionResult> interactionResult) {
        ItemStack heldStack = player.getItemInHand(hand);
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

    @Nullable
    public static void resetAetherDayAndWeather(LevelAccessor level, long newTime) {
        if (level instanceof ServerLevel serverLevel) {
            if (serverLevel.dimension().location().getNamespace().equals(AetherII.MODID)) {
                serverLevel.getServer().overworld().setWeatherParameters(0, 0, false, false);
                serverLevel.getServer().overworld().setDayTime(newTime);
            }
        }
    }
}
