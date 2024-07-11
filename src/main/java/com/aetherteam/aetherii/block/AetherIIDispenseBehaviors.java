package com.aetherteam.aetherii.block;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.item.AetherIIItems;
import com.aetherteam.aetherii.item.miscellaneous.bucket.SkyrootBucketItem;
import net.minecraft.core.BlockPos;
import net.minecraft.core.dispenser.BlockSource;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.core.dispenser.DispenseItemBehavior;
import net.minecraft.world.item.DispensibleContainerItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BucketPickup;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;

public class AetherIIDispenseBehaviors {
    public static final DispenseItemBehavior SKYROOT_BUCKET_DISPENSE_BEHAVIOR = new DefaultDispenseItemBehavior() {
        private final DefaultDispenseItemBehavior defaultDispenseItemBehavior = new DefaultDispenseItemBehavior();

        @Override
        public ItemStack execute(BlockSource source, ItemStack stack) {
            DispensibleContainerItem dispensibleContainerItem = (DispensibleContainerItem) stack.getItem();
            BlockPos blockpos = source.pos().relative(source.state().getValue(DispenserBlock.FACING));
            Level level = source.level();
            if (dispensibleContainerItem.emptyContents(null, level, blockpos, null, stack)) {
                dispensibleContainerItem.checkExtraContent(null, level, stack, blockpos);
                return new ItemStack(AetherIIItems.SKYROOT_BUCKET.get());
            } else {
                return this.defaultDispenseItemBehavior.dispense(source, stack);
            }
        }
    };

    public static final DispenseItemBehavior SKYROOT_BUCKET_PICKUP_BEHAVIOR = new DefaultDispenseItemBehavior() {
        private final DefaultDispenseItemBehavior defaultDispenseItemBehavior = new DefaultDispenseItemBehavior();

        @Override
        public ItemStack execute(BlockSource source, ItemStack stack) {
            LevelAccessor levelAccessor = source.level();
            BlockPos blockPos = source.pos().relative(source.state().getValue(DispenserBlock.FACING));
            BlockState blockState = levelAccessor.getBlockState(blockPos);
            Block block = blockState.getBlock();
            if (block instanceof BucketPickup bucketPickup) {
                ItemStack bucketStack = bucketPickup.pickupBlock(null, levelAccessor, blockPos, blockState);
                bucketStack = SkyrootBucketItem.swapBucketType(bucketStack);
                if (bucketStack.isEmpty()) {
                    return super.execute(source, stack);
                } else {
                    levelAccessor.gameEvent(null, GameEvent.FLUID_PICKUP, blockPos);
                    Item item = bucketStack.getItem();
                    return this.consumeWithRemainder(source, stack, new ItemStack(item));
                }
            } else {
                return super.execute(source, stack);
            }
        }
    };
}
