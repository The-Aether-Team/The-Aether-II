package com.aetherteam.aetherii.event.hooks;

import com.aetherteam.aetherii.AetherIITags;
import com.aetherteam.aetherii.attachment.AetherIIDataAttachments;
import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.block.dungeon.MimicOption;
import com.aetherteam.aetherii.block.natural.AercloudBlock;
import com.aetherteam.aetherii.block.natural.AetherGrassBlock;
import com.aetherteam.aetherii.block.natural.Snowable;
import com.aetherteam.aetherii.block.portal.AetherPortalShape;
import com.aetherteam.aetherii.block.utility.SkyrootBedBlock;
import com.aetherteam.aetherii.client.sound.AetherIISoundEvents;
import com.aetherteam.aetherii.data.resources.registries.AetherIIDimensions;
import com.aetherteam.aetherii.entity.attributes.AetherIIAttributes;
import com.aetherteam.aetherii.entity.monster.CarrionSprout;
import com.aetherteam.aetherii.entity.passive.FlyingCow;
import com.aetherteam.aetherii.entity.passive.MountableAetherAnimal;
import com.aetherteam.aetherii.item.AetherIIItems;
import com.aetherteam.aetherii.item.equipment.weapons.TieredCrossbowItem;
import com.aetherteam.aetherii.item.miscellaneous.bucket.SkyrootBucketItem;
import com.aetherteam.aetherii.world.LevelUtil;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.util.ParticleUtils;
import net.minecraft.world.Containers;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Bucketable;
import net.minecraft.world.entity.animal.Cow;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3f;

import javax.annotation.Nullable;
import java.util.Optional;

public class PlayerHooks {
    public static void forceSpecialLoadingCrouch(Player player) {
        ItemStack useStack = player.getUseItem();
        if (useStack.getItem() instanceof TieredCrossbowItem && AetherIIDataAttachments.get(player, AetherIIDataAttachments.ABILITY_BEHAVIOR).isCrossbowSpecial()) {
            if (!player.getAbilities().flying && !player.isSwimming() && !player.isPassenger()) {
                player.setPose(Pose.CROUCHING);
            }
        }
    }

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
                                player.addItem(stack);
                            } else if (stack.isDamageableItem()) {
                                stack.hurtAndBreak(1, player, (entity) -> entity.broadcastBreakEvent(hand));
                            } else {
                                player.setItemInHand(hand, stack);
                            }
                        }
                        return true;
                    }
                }
            }
        }
        return cancellationStatus;
    }

    public static boolean cancelPlacementOnAercloud(Player player, Level level, BlockPos pos, ItemStack itemStack, boolean cancellationStatus) {
        BlockState state = level.getBlockState(pos);

        if (state.is(AetherIITags.Blocks.AERCLOUDS)) {
            if (itemStack.getItem() instanceof BlockItem && !itemStack.is(AetherIITags.Items.CAN_USE_ON_AERCLOUD)) {
                if (level.isClientSide()) {
                    for (int i = 0; i < 10; i++) {
                        ParticleUtils.spawnParticleOnFace(level, pos, Direction.UP, ParticleTypes.POOF, Vec3.ZERO, 0.5F);
                    }
                }
                level.playSound(null, pos, state.getSoundType(level, pos, player).getPlaceSound(), SoundSource.BLOCKS, 1.0F, 1.0F);
                return true;
            }
        }
        return cancellationStatus;
    }

    public static boolean snowlogBlock(Player player, Level level, BlockPos pos, ItemStack itemStack, InteractionHand hand, boolean cancellationStatus) {
        BlockState stateInLevel = level.getBlockState(pos);
        if (itemStack.getItem() instanceof BlockItem blockItem) {
            boolean success = false;
            Block blockInHand = blockItem.getBlock();
            if (stateInLevel.is(AetherIIBlocks.ARCTIC_SNOW.get()) && blockInHand instanceof Snowable snowable) {
                level.setBlock(pos, snowable.setSnowy(blockInHand.defaultBlockState()), 1 | 2);
                level.playSound(null, pos, blockInHand.getSoundType(blockInHand.defaultBlockState(), level, pos, player).getPlaceSound(), SoundSource.BLOCKS, 1.0F, 1.0F);
                success = true;
            } else if (blockInHand == AetherIIBlocks.ARCTIC_SNOW.get() && AetherGrassBlock.plantNotSnowed(stateInLevel) && stateInLevel.getBlock() instanceof Snowable snowable) {
                level.setBlock(pos, snowable.setSnowy(stateInLevel), 1 | 2);
                level.playSound(null, pos, blockInHand.getSoundType(blockInHand.defaultBlockState(), level, pos, player).getPlaceSound(), SoundSource.BLOCKS, 1.0F, 1.0F);
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
        if ((face != Direction.DOWN && PotionUtils.getPotion(itemStack) == Potions.WATER)) {
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

                if (!level.isClientSide()) {
                    if(level instanceof ServerLevel serverLevel) {
                        for (int i = 0; i < 5; i++) {
                            serverLevel.sendParticles(ParticleTypes.SPLASH,
                                    (double) pos.getX() + level.getRandom().nextDouble(),
                                    pos.getY() + 1,
                                    (double) pos.getZ() + level.getRandom().nextDouble(),
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

    public static boolean interactWithMimicContainer(LevelAccessor level, BlockPos pos, boolean cancellationStatus) {
        BlockState state = level.getBlockState(pos);
        BlockEntity blockEntity = level.getBlockEntity(pos);

        if (blockEntity instanceof RandomizableContainerBlockEntity containerBlockEntity) {
            if (blockEntity.getPersistentData().getBoolean("aether_ii:mimic")) {
                containerBlockEntity.setLootTable((ResourceLocation) null, 0L);
                containerBlockEntity.clearContent();
                level.destroyBlock(pos, false);
                if (level instanceof ServerLevel serverLevel) {
                    MimicOption.spawnMimic(state, serverLevel, pos);
                }
                return true;
            }
        }
        return cancellationStatus;
    }

    public static void milkWithSkyrootBucket(Entity target, Player player, InteractionHand hand) {
        if ((target instanceof Cow || target instanceof FlyingCow) && !((Animal) target).isBaby()) {
            ItemStack heldStack = player.getItemInHand(hand);
            if (heldStack.is(AetherIIItems.SKYROOT_BUCKET.get())) {
                if (target instanceof FlyingCow) {
                    player.playSound(AetherIISoundEvents.ENTITY_FLYING_COW_MILK.get(), 1.0F, 1.0F);
                } else {
                    player.playSound(SoundEvents.COW_MILK, 1.0F, 1.0F);
                }
                ItemStack filledBucket = ItemUtils.createFilledResult(heldStack, player, AetherIIItems.SKYROOT_MILK_BUCKET.get().getDefaultInstance());
                player.swing(hand);
                player.setItemInHand(hand, filledBucket);
            }
        }
    }

    public static void feedCarrionSprout(Level level, Entity target, Player player, InteractionHand hand) {
        ItemStack itemInHand = player.getItemInHand(hand);
        if (target instanceof CarrionSprout) {
            ItemStack heldStack = player.getItemInHand(hand);
            if (itemInHand.getItem() == AetherIIItems.GOLDEN_AMBER.get()) {
                if (!player.getAbilities().instabuild) {
                    heldStack.shrink(1);
                }
                ItemEntity itemEntity = new ItemEntity(level, target.getX(), target.getY(), target.getZ(), new ItemStack(AetherIIItems.GOLDEN_WYNDBERRY.get()));
                itemEntity.setDefaultPickUpDelay();
                level.addFreshEntity(itemEntity);
                player.swing(hand);
                target.level().playSound(null, target.blockPosition(), SoundEvents.PLAYER_BURP, SoundSource.HOSTILE, 1.0F, 1.0F);
                ParticleUtils.spawnParticleOnFace(level, target.blockPosition(), Direction.UP, new DustParticleOptions(new Vector3f(1.0F, 0.847F, 0.302F), 0.5F), Vec3.ZERO, 0.5F);
                if (player instanceof ServerPlayer serverPlayer) {
                    CriteriaTriggers.PLAYER_INTERACTED_WITH_ENTITY.trigger(serverPlayer, itemInHand, target);
                }
            }
        }
    }

    public static void useGoldenWyndberry(Entity target, Player player, InteractionHand hand) {
        ItemStack itemInHand = player.getItemInHand(hand);
        if (target instanceof AgeableMob ageableMob) {
            if (itemInHand.getItem() == AetherIIItems.GOLDEN_WYNDBERRY.get() && ageableMob.isBaby() && !ageableMob.getPersistentData().getBoolean("aether_ii:age_locked")) {
                ageableMob.getPersistentData().putBoolean("aether_ii:age_locked", true);
                ageableMob.setAge(-24000);
                if (!player.getAbilities().instabuild) {
                    itemInHand.shrink(1);
                }
                player.swing(hand);
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
                    interactionResult = Optional.of(InteractionResult.SUCCESS);
                } else {
                    interactionResult = Optional.of(InteractionResult.FAIL);
                }
            }
        }
        return interactionResult;
    }

    public static void valkyrieTeaAbility(Player player, ItemStack stack) {
        FoodProperties food = stack.getItem().getFoodProperties(stack, player);
        if (food != null) {
            float originalSaturation = food.getSaturationModifier();
            double saturationBoost = player.getAttributeValue(AetherIIAttributes.SATURATION_BOOST.get());
            float bonusSaturation = (float) (originalSaturation * (saturationBoost - 1.0F));
            if (bonusSaturation > 0.0F) {
                player.getFoodData().eat(0, bonusSaturation);
            }
        }
    }

    public static boolean cancelBedrollSpawn(Player player, BlockPos pos) {
        if (player != null && pos != null) {
            return player.level().getBlockState(pos).is(AetherIIBlocks.CLOUDWOOL_BEDROLL.get());
        }
        return false;
    }

    public static Player.BedSleepingProblem handleBedrollSleeping(ServerPlayer player, Level level, BlockPos pos, BlockState state, Player.BedSleepingProblem vanillaProblem) {
        if (level.dimension() == AetherIIDimensions.AETHER_HOLY_ISLES_LEVEL && isAetherIIBed(state)) {
            if (vanillaProblem == Player.BedSleepingProblem.NOT_POSSIBLE_HERE) {
                return null;
            }
        }
        if (state.is(AetherIIBlocks.CLOUDWOOL_BEDROLL.get())) {
            if (vanillaProblem == null && level.getLightEngine().getRawBrightness(pos, 15) < 10) {
                player.displayClientMessage(Component.translatable("aether_ii.bedroll.too_dark"), true);
                return Player.BedSleepingProblem.OTHER_PROBLEM;
            }
        }
        return vanillaProblem;
    }

    public static void breakBedrollAfterSleeping(Player player) {
        Level level = player.level();
        BlockPos pos = player.getOnPos();
        BlockState state = level.getBlockState(pos);
        if (level instanceof ServerLevel) {
            if (state.is(AetherIIBlocks.CLOUDWOOL_BEDROLL.get())) {
                level.destroyBlock(pos, false);
            }
        }
    }

    public static void skipAetherSleep(Player player) {
        if (!(player instanceof ServerPlayer serverPlayer) || !(player.level() instanceof ServerLevel level) || level.dimension() != AetherIIDimensions.AETHER_HOLY_ISLES_LEVEL) {
            return;
        }
        if (!serverPlayer.isSleeping() || !isSleepingInAetherIIBed(serverPlayer)) {
            return;
        }

        int requiredSleepPercentage = level.getGameRules().getInt(GameRules.RULE_PLAYERS_SLEEPING_PERCENTAGE);
        int activePlayers = 0;
        int deepSleepingPlayers = 0;
        for (ServerPlayer levelPlayer : level.players()) {
            if (!levelPlayer.isSpectator()) {
                activePlayers++;
                if (levelPlayer.isSleepingLongEnough() && isSleepingInAetherIIBed(levelPlayer)) {
                    deepSleepingPlayers++;
                }
            }
        }

        if (deepSleepingPlayers >= Math.max(1, Mth.ceil((float) (activePlayers * requiredSleepPercentage) / 100.0F))) {
            if (level.getGameRules().getBoolean(GameRules.RULE_DAYLIGHT)) {
                long dayTime = level.getDayTime();
                long targetTime = dayTime + 24000L;
                long skippedTime = targetTime - targetTime % 24000L;
                long eventTime = net.minecraftforge.event.ForgeEventFactory.onSleepFinished(level, skippedTime, dayTime);
                for (ServerLevel serverLevel : level.getServer().getAllLevels()) {
                    serverLevel.setDayTime(eventTime);
                }
            }
            if (level.getGameRules().getBoolean(GameRules.RULE_WEATHER_CYCLE) && level.isRaining()) {
                level.setWeatherParameters(0, 0, false, false);
            }
            for (ServerPlayer levelPlayer : level.players()) {
                if (levelPlayer.isSleeping()) {
                    levelPlayer.stopSleepInBed(false, true);
                }
            }
        }
    }

    private static boolean isSleepingInAetherIIBed(ServerPlayer player) {
        Optional<BlockPos> sleepingPos = player.getSleepingPos();
        if (sleepingPos.isEmpty()) {
            return false;
        }
        return isAetherIIBed(player.level().getBlockState(sleepingPos.get()));
    }

    private static boolean isAetherIIBed(BlockState state) {
        return state.is(AetherIIBlocks.CLOUDWOOL_BEDROLL.get()) || state.getBlock() instanceof SkyrootBedBlock;
    }

    public static boolean dismountPrevention(Entity rider, Entity mount, boolean dismounting) {
        if (dismounting && rider.isShiftKeyDown()) {
            return (mount instanceof MountableAetherAnimal && !mount.onGround() && !mount.isInWater() && !mount.isInLava() && !mount.isPassenger());
        }
        return false;
    }

    public static void mountAercloudEffects(Player player) {
        Entity mount = player.getVehicle();
        if (player.isPassenger() && mount != null) {
            Optional<BlockState> blockState = mount.level().getBlockStates(mount.getBoundingBox()).filter((state) -> state.getBlock() instanceof AercloudBlock).findAny();
            if (blockState.isPresent()) {
                if (player.level().isClientSide()) {
                    ((AercloudBlock) blockState.get().getBlock()).runAercloudEffect(blockState.get(), player.level(), mount.blockPosition(), mount);
                }
            }
        }
    }
}
