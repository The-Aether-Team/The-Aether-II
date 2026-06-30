package com.aetherteam.aetherii.item.miscellaneous;

import com.aetherteam.aetherii.api.Mural;
import com.aetherteam.aetherii.api.registries.AetherIIRegistries;
import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.block.dungeon.MuralBlock;
import com.aetherteam.aetherii.blockentity.MuralBlockEntity;
import com.aetherteam.aetherii.item.components.AetherIIDataComponents;
import com.aetherteam.aetherii.item.components.DataComponentGetter;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundSource;
import net.minecraft.Util;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import com.aetherteam.aetherii.item.components.TooltipDisplay;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class MuralItem extends Item {
    private final Block muralBlock;
    
    public MuralItem(Block muralBlock, Properties properties) {
        super(properties);
        this.muralBlock = muralBlock;
    }

    @SuppressWarnings("deprecation")
    @Override
    public InteractionResult useOn(UseOnContext context) {
        var player = context.getPlayer();
        if (player != null && !context.getLevel().isClientSide()) {
            if (this.createMural(context)) {
                player.swing(context.getHand());
                player.level().playSound(null, context.getClickedPos(), this.muralBlock.defaultBlockState().getSoundType().getPlaceSound(), SoundSource.PLAYERS, 1.0F, 1.0F);
                if (!player.getAbilities().instabuild) {
                    context.getItemInHand().shrink(1);
                }
                return InteractionResult.CONSUME;
            }
        }
        return InteractionResult.FAIL;
    }

    private boolean createMural(UseOnContext context) {
        Level level = context.getLevel();
        var blockPlaceContext = new BlockPlaceContext(context);
        final BlockPos pos = !level.getBlockState(context.getClickedPos()).canBeReplaced(blockPlaceContext) ? context.getClickedPos().relative(context.getClickedFace()) : context.getClickedPos();
        Direction horizontalDirection = context.getHorizontalDirection();
        Direction facing = horizontalDirection.getOpposite();
        Direction side = facing.getCounterClockWise();
        Holder<Mural> muralHolder = AetherIIDataComponents.get(context.getItemInHand(), AetherIIDataComponents.MURAL);
        if (muralHolder == null) {
            var list = level.registryAccess().lookupOrThrow(AetherIIRegistries.MURAL).listElements().collect(Collectors.toCollection(ArrayList::new));
            list.removeIf(muralRef -> !canAllBlocksBeReplaced(muralRef.get(), blockPlaceContext, level, pos, side, horizontalDirection));
            if (list.isEmpty()) {
                return false;
            }
            muralHolder = Util.getRandom(list, context.getPlayer().getRandom());
        } else {
            if (!canAllBlocksBeReplaced(muralHolder.get(), blockPlaceContext, level, pos, side, horizontalDirection)) {
                return false;
            }
        }
        var pos2 = new MutableBlockPos(pos.getX(), pos.getY(), pos.getZ());
        var mural = muralHolder.get();
        for (int offsetX = 0; offsetX < mural.width(); offsetX++, pos2.move(side)) {
            pos2.setY(pos.getY()).move(Direction.UP, mural.height() - 1);
            for (int offsetY = 0; offsetY < mural.height(); offsetY++, pos2.move(Direction.DOWN)) {
                BlockState state = this.muralBlock.defaultBlockState()
                        .setValue(BlockStateProperties.HORIZONTAL_FACING, facing)
                        .setValue(MuralBlock.X_OFFSET, offsetX)
                        .setValue(MuralBlock.Y_OFFSET, offsetY);
                level.setBlockAndUpdate(pos2, state);
                if (level.getBlockEntity(pos2) instanceof MuralBlockEntity muralBlockEntity) {
                    muralBlockEntity.setMural(muralHolder);
                }
            }
        }
        return true;
    }

    private static boolean canAllBlocksBeReplaced(Mural mural, BlockPlaceContext blockPlaceContext, Level level, BlockPos pos, Direction side, Direction horizontalDirection) {
        var pos2 = new MutableBlockPos(pos.getX(), pos.getY(), pos.getZ());
        for (int offsetX = 0; offsetX < mural.width(); offsetX++, pos2.move(side)) {
            pos2.setY(pos.getY());
            for (int offsetY = 0; offsetY < mural.height(); offsetY++, pos2.move(Direction.UP)) {
                if (!level.getBlockState(pos2).canBeReplaced(offsetX == 0 && offsetY == 0 ? blockPlaceContext : BlockPlaceContext.at(blockPlaceContext, pos2, horizontalDirection))) {
                    return false;
                }
            }
        }
        return true;
    }

    private static final Component TOOLTIP_RANDOM_VARIANT = Component.translatable("mural.random").withStyle(ChatFormatting.GRAY);

    @Override
    public void appendHoverText(ItemStack stack, net.minecraft.world.level.Level level, List<Component> tooltipAdder, TooltipFlag flag) {
        if (this.muralBlock == AetherIIBlocks.MURAL.get()) {
            Holder<Mural> holder = AetherIIDataComponents.get(stack, AetherIIDataComponents.MURAL);
            if (holder != null) {
                holder.get().addToTooltip(level, tooltipAdder::add, flag, DataComponentGetter.EMPTY);
            } else if (flag.isCreative()) {
                tooltipAdder.add(TOOLTIP_RANDOM_VARIANT);
            }
        }
    }
}
