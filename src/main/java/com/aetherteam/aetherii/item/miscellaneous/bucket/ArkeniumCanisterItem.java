package com.aetherteam.aetherii.item.miscellaneous.bucket;

import com.aetherteam.aetherii.block.natural.CanisterPickup;
import com.aetherteam.aetherii.item.AetherIIItems;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;

public class ArkeniumCanisterItem extends BucketItem {
    public ArkeniumCanisterItem(Fluid content, Properties properties) {
        super(content, properties);
    }

    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        BlockHitResult blockhitresult = getPlayerPOVHitResult(level, player, this.content == Fluids.EMPTY ? net.minecraft.world.level.ClipContext.Fluid.SOURCE_ONLY : net.minecraft.world.level.ClipContext.Fluid.NONE);
        if (blockhitresult.getType() == HitResult.Type.MISS) {
            return InteractionResultHolder.pass(itemstack);
        } else if (blockhitresult.getType() != HitResult.Type.BLOCK) {
            return InteractionResultHolder.pass(itemstack);
        } else {
            BlockPos blockpos = blockhitresult.getBlockPos();
            Direction direction = blockhitresult.getDirection();
            BlockPos blockpos1 = blockpos.relative(direction);
            if (level.mayInteract(player, blockpos) && player.mayUseItemAt(blockpos1, direction, itemstack)) {
                BlockState blockstate1;
                ItemStack itemstack3;
                if (this.content == Fluids.EMPTY) {
                    blockstate1 = level.getBlockState(blockpos);
                    Block var14 = blockstate1.getBlock();
                    if (var14 instanceof CanisterPickup pickup) {
                        itemstack3 = pickup.pickupBlockWithCanister(player, level, blockpos, blockstate1);
                        if (!itemstack3.isEmpty()) {
                            player.awardStat(Stats.ITEM_USED.get(this));
                            pickup.getCanisterPickupSound(blockstate1).ifPresent((p_150709_) -> {
                                player.playSound(p_150709_, 1.0F, 1.0F);
                            });
                            level.gameEvent(player, GameEvent.FLUID_PICKUP, blockpos);
                            ItemStack itemstack2 = ItemUtils.createFilledResult(itemstack, player, itemstack3);
                            if (!level.isClientSide) {
                                CriteriaTriggers.FILLED_BUCKET.trigger((ServerPlayer)player, itemstack3);
                            }

                            return InteractionResultHolder.sidedSuccess(itemstack2, level.isClientSide());
                        }
                    }

                    return InteractionResultHolder.fail(itemstack);
                } else {
                    blockstate1 = level.getBlockState(blockpos);
                    BlockPos blockpos2 = this.canBlockContainFluid(player, level, blockpos, blockstate1) ? blockpos : blockpos1;
                    if (this.emptyContents(player, level, blockpos2, blockhitresult, itemstack)) {
                        this.checkExtraContent(player, level, itemstack, blockpos2);
                        if (player instanceof ServerPlayer) {
                            CriteriaTriggers.PLACED_BLOCK.trigger((ServerPlayer)player, blockpos2, itemstack);
                        }

                        player.awardStat(Stats.ITEM_USED.get(this));
                        itemstack3 = ItemUtils.createFilledResult(itemstack, player, getEmptySuccessItem(itemstack, player));
                        return InteractionResultHolder.sidedSuccess(itemstack3, level.isClientSide());
                    } else {
                        return InteractionResultHolder.fail(itemstack);
                    }
                }
            } else {
                return InteractionResultHolder.fail(itemstack);
            }
        }
    }

    public static ItemStack getEmptySuccessItem(ItemStack canisterStack, Player player) {
        return !player.hasInfiniteMaterials() ? new ItemStack(AetherIIItems.ARKENIUM_CANISTER.get()) : canisterStack;
    }
}
