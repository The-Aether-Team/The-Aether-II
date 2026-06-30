package com.aetherteam.aetherii.item.miscellaneous;

import java.util.List;
import java.util.function.Consumer;

import com.aetherteam.aetherii.AetherIITags;
import com.aetherteam.aetherii.block.dungeon.CopyBlock;
import com.aetherteam.aetherii.blockentity.CopyBlockEntity;
import com.aetherteam.aetherii.client.renderer.item.AetherIIBlockEntityItemRenderer;
import com.aetherteam.aetherii.item.components.AetherIIDataComponents;
import net.minecraft.ChatFormatting;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;

public class CopyBlockItem extends BlockItem {
    public CopyBlockItem(Block block, Properties properties) {
        super(block, properties);
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {
            @Override
            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                return AetherIIBlockEntityItemRenderer.getInstance();
            }
        });
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Player player = context.getPlayer();
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        ItemStack stack = context.getItemInHand();
        if (player != null && player.isCreative() && !player.isShiftKeyDown()) {
            if (stack.is(this) && AetherIIDataComponents.get(stack, AetherIIDataComponents.BLOCK_STATE) == null) {
                BlockState previousState = level.getBlockState(pos);
                if (previousState.is(AetherIITags.Blocks.COPYABLE_DUNGEON_BLOCKS)) {
                    level.setBlockAndUpdate(pos, this.getBlock().defaultBlockState().setValue(CopyBlock.EMPTY, false));
                    if (level.getBlockEntity(pos) instanceof CopyBlockEntity blockEntity) {
                        blockEntity.setCopyState(previousState);
                        blockEntity.setChanged();
                    }
                    return InteractionResult.SUCCESS;
                }
            }
        }
        return super.useOn(context);
    }

    @Override
    public boolean overrideOtherStackedOnMe(ItemStack stack, ItemStack other, Slot slot, ClickAction action, Player player, SlotAccess access) {
        if (stack.is(this) && !other.isEmpty() && other.getItem() instanceof BlockItem blockItem && blockItem.getBlock().defaultBlockState().is(AetherIITags.Blocks.COPYABLE_DUNGEON_BLOCKS)) {
            AetherIIDataComponents.set(stack, AetherIIDataComponents.BLOCK_STATE, blockItem.getBlock().defaultBlockState());
            return true;
        }
        return super.overrideOtherStackedOnMe(stack, other, slot, action, player, access);
    }

    @Override
    public boolean overrideStackedOnOther(ItemStack stack, Slot slot, ClickAction action, Player player) {
        ItemStack other = slot.getItem();
        if (stack.is(this) && !other.isEmpty() && other.getItem() instanceof BlockItem blockItem && blockItem.getBlock().defaultBlockState().is(AetherIITags.Blocks.COPYABLE_DUNGEON_BLOCKS)) {
            AetherIIDataComponents.set(stack, AetherIIDataComponents.BLOCK_STATE, blockItem.getBlock().defaultBlockState());
            return true;
        }
        return super.overrideStackedOnOther(stack, slot, action, player);
    }

    @Override
    public Component getName(ItemStack stack) {
        BlockState state = AetherIIDataComponents.get(stack, AetherIIDataComponents.BLOCK_STATE);
        if (state != null) {
            return state.getBlock().asItem().getName(stack);
        }
        return super.getName(stack);
    }

    @Override
    public void appendHoverText(ItemStack stack, net.minecraft.world.level.Level level, List<Component> tooltipAdder, TooltipFlag flag) {
        super.appendHoverText(stack, level, tooltipAdder, flag);
        BlockState state = AetherIIDataComponents.get(stack, AetherIIDataComponents.BLOCK_STATE);
        if (state != null) {
            state.getProperties().forEach(property -> {
                String name = property.getName();
                Comparable<?> value = state.getValue(property);
                tooltipAdder.add(Component.literal(name + ": " + value).withStyle(ChatFormatting.GRAY));
            });
        }
    }
}
