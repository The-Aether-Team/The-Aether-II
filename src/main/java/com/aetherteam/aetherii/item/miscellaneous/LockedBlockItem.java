package com.aetherteam.aetherii.item.miscellaneous;

import com.aetherteam.aetherii.AetherIITags;
import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.blockentity.LockedBlockEntity;
import com.aetherteam.aetherii.item.components.AetherIIDataComponents;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import java.util.function.Consumer;

public class LockedBlockItem extends BlockItem {
    public LockedBlockItem(Block block, Properties properties) {
        super(block, properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Player player = context.getPlayer();
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        ItemStack stack = context.getItemInHand();
        if (player != null && player.isShiftKeyDown()) {
            if (stack.is(this) && stack.get(AetherIIDataComponents.BLOCK_STATE) == null) {
                BlockState previousState = level.getBlockState(pos);
                if (previousState.is(AetherIITags.Blocks.LOCKABLE_BLOCKS)) {
                    level.setBlockAndUpdate(pos, AetherIIBlocks.LOCKED_BLOCK.get().defaultBlockState());
                    if (level.getBlockEntity(pos) instanceof LockedBlockEntity lockedBlockEntity) {
                        lockedBlockEntity.applyComponents(DataComponentMap.EMPTY, DataComponentPatch.builder().set(AetherIIDataComponents.BLOCK_STATE.get(), previousState).build());
                    }
                    return InteractionResult.SUCCESS;
                }
            }
        }
        return super.useOn(context);
    }

    @Override
    public boolean overrideOtherStackedOnMe(ItemStack stack, ItemStack other, Slot slot, ClickAction action, Player player, SlotAccess access) {
        if (stack.is(this) && !other.isEmpty() && other.getItem() instanceof BlockItem blockItem && blockItem.getBlock().defaultBlockState().is(AetherIITags.Blocks.LOCKABLE_BLOCKS)) {
            stack.set(AetherIIDataComponents.BLOCK_STATE, blockItem.getBlock().defaultBlockState());
            return true;
        }
        return super.overrideOtherStackedOnMe(stack, other, slot, action, player, access);
    }

    @Override
    public boolean overrideStackedOnOther(ItemStack stack, Slot slot, ClickAction action, Player player) {
        ItemStack other = slot.getItem();
        if (stack.is(this) && !other.isEmpty() && other.getItem() instanceof BlockItem blockItem && blockItem.getBlock().defaultBlockState().is(AetherIITags.Blocks.LOCKABLE_BLOCKS)) {
            stack.set(AetherIIDataComponents.BLOCK_STATE, blockItem.getBlock().defaultBlockState());
            return true;
        }
        return super.overrideStackedOnOther(stack, slot, action, player);
    }

    @Override
    public Component getName(ItemStack stack) {
        BlockState state = stack.get(AetherIIDataComponents.BLOCK_STATE);
        if (state != null) {
            return state.getBlock().asItem().getName();
        }
        return super.getName(stack);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay tooltipDisplay, Consumer<Component> tooltipAdder, TooltipFlag flag) {
        super.appendHoverText(stack, context, tooltipDisplay, tooltipAdder, flag);
        BlockState state = stack.get(AetherIIDataComponents.BLOCK_STATE);
        if (state != null) {
            state.getProperties().forEach(property -> {
                String name = property.getName();
                Comparable<?> value = state.getValue(property);
                tooltipAdder.accept(Component.literal(name + ": " + value).withStyle(ChatFormatting.GRAY));
            });
        }
    }
}
