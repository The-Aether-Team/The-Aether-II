package com.aetherteam.aetherii.item.miscellaneous;

import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.block.miscellaneous.MoaEggBlock;
import com.aetherteam.aetherii.item.components.AetherIIDataComponents;
import com.aetherteam.aetherii.item.components.MoaEggType;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public class MoaEggItem extends BlockItem {
    public MoaEggItem(Properties properties) {
        this(AetherIIBlocks.MOA_EGG.get(), properties);
    }

    protected MoaEggItem(Block block, Properties properties) {
        super(block, properties);
    }

    @Nullable
    @Override
    protected BlockState getPlacementState(BlockPlaceContext context) {
        BlockState state = super.getPlacementState(context);
        ItemStack stack = context.getItemInHand();
        MoaEggType moaEggType = stack.get(AetherIIDataComponents.MOA_EGG_TYPE);
        if (moaEggType != null && state != null) {
            state = state
                    .setValue(MoaEggBlock.KERATIN, moaEggType.keratinColor())
                    .setValue(MoaEggBlock.EYES, moaEggType.eyeColor())
                    .setValue(MoaEggBlock.FEATHERS, moaEggType.featherColor())
                    .setValue(MoaEggBlock.FEATHER_SHAPE, moaEggType.featherShape());
        }
        return state;
    }

    @SuppressWarnings("deprecation")
    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay tooltipDisplay, Consumer<Component> tooltipAdder, TooltipFlag flag) {
        super.appendHoverText(stack, context, tooltipDisplay, tooltipAdder, flag);
        MoaEggType moaEggType = stack.get(AetherIIDataComponents.MOA_EGG_TYPE);
        if (moaEggType != null) {
            tooltipAdder.accept(Component.translatable("aether_ii.tooltip.item.moa_egg.keratin", Component.translatable("aether_ii.tooltip.item.moa_egg.keratin_color." + moaEggType.keratinColor().getSerializedName())).withStyle(ChatFormatting.GRAY));
            tooltipAdder.accept(Component.translatable("aether_ii.tooltip.item.moa_egg.eyes", Component.translatable("aether_ii.tooltip.item.moa_egg.eye_color." + moaEggType.eyeColor().getSerializedName())).withStyle(ChatFormatting.GRAY));
            tooltipAdder.accept(Component.translatable("aether_ii.tooltip.item.moa_egg.feathers", Component.translatable("aether_ii.tooltip.item.moa_egg.feather_shape." + moaEggType.featherShape().getSerializedName()), Component.translatable("aether_ii.tooltip.item.moa_egg.feather_color." + moaEggType.featherColor().getSerializedName())).withStyle(ChatFormatting.GRAY));
        }
    }
}
