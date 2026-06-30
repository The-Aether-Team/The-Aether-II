package com.aetherteam.aetherii.block;

import com.aetherteam.aetherii.item.AetherIIItems;
import com.aetherteam.aetherii.item.miscellaneous.bucket.SkyrootBucketItem;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockSource;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.core.dispenser.DispenseItemBehavior;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.DispensibleContainerItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BucketPickup;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.block.entity.DispenserBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;

public class AetherIIDispenseBehaviors {
    public static final DispenseItemBehavior SKYROOT_BUCKET_DISPENSE_BEHAVIOR = new DefaultDispenseItemBehavior() {
        private final DefaultDispenseItemBehavior defaultDispenseItemBehavior = new DefaultDispenseItemBehavior();

        @Override
        public ItemStack execute(BlockSource source, ItemStack stack) {
            DispensibleContainerItem dispensibleContainerItem = (DispensibleContainerItem) stack.getItem();
            BlockPos blockpos = source.getPos().relative(source.getBlockState().getValue(DispenserBlock.FACING));
            Level level = source.getLevel();
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
            LevelAccessor levelAccessor = source.getLevel();
            BlockPos blockPos = source.getPos().relative(source.getBlockState().getValue(DispenserBlock.FACING));
            BlockState blockState = levelAccessor.getBlockState(blockPos);
            Block block = blockState.getBlock();
            if (block instanceof BucketPickup bucketPickup) {
                ItemStack bucketStack = bucketPickup.pickupBlock(levelAccessor, blockPos, blockState);
                bucketStack = SkyrootBucketItem.swapBucketType(bucketStack);
                if (bucketStack.isEmpty()) {
                    return super.execute(source, stack);
                } else {
                    levelAccessor.gameEvent((Entity) null, GameEvent.FLUID_PICKUP, blockPos);
                    Item item = bucketStack.getItem();
                    stack.shrink(1);
                    if (stack.isEmpty()) {
                        return new ItemStack(item);
                    } else {
                        if (source.<DispenserBlockEntity>getEntity().addItem(new ItemStack(item)) < 0) {
                            this.defaultDispenseItemBehavior.dispense(source, new ItemStack(item));
                        }
                        return stack;
                    }
                }
            } else {
                return super.execute(source, stack);
            }
        }
    };
}
