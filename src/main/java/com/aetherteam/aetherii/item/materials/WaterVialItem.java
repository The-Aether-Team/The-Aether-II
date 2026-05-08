package com.aetherteam.aetherii.item.materials;

import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.client.sound.AetherIISoundEvents;
import com.aetherteam.aetherii.item.AetherIIItems;
import com.aetherteam.aetherii.item.AetherIITooltips;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;

import java.util.function.Consumer;

public class WaterVialItem extends Item {
    public WaterVialItem(Item.Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        Player player = context.getPlayer();
        ItemStack itemStack = context.getItemInHand();
        BlockState state = level.getBlockState(pos);
        if (context.getClickedFace() != Direction.DOWN && (state.is(BlockTags.CONVERTABLE_TO_MUD) || state.is(AetherIIBlocks.FERROSITE_SAND.get()))) {
            level.playSound(null, pos, SoundEvents.GENERIC_SPLASH, SoundSource.BLOCKS, 1.0F, 1.0F);
            assert player != null;
            player.setItemInHand(context.getHand(), ItemUtils.createFilledResult(itemStack, player, new ItemStack(AetherIIItems.SCATTERGLASS_VIAL.get())));
            player.awardStat(Stats.ITEM_USED.get(itemStack.getItem()));
            if (!level.isClientSide()) {
                ServerLevel serverLevel = (ServerLevel) level;

                for (int i = 0; i < 5; i++) {
                    serverLevel.sendParticles(ParticleTypes.SPLASH,
                            (double) pos.getX() + level.getRandom().nextDouble(),
                            pos.getY() + 1,
                            (double) pos.getZ() + level.getRandom().nextDouble(),
                            1, 0.0, 0.0, 0.0, 1.0
                    );
                }
            }

            level.playSound(null, pos, AetherIISoundEvents.ITEM_SCATTERGLASS_VIAL_EMPTY.get(), SoundSource.BLOCKS, 1.0F, 1.0F);
            level.gameEvent(null, GameEvent.FLUID_PLACE, pos);
            if (state.is(AetherIIBlocks.FERROSITE_SAND.get())) {
                level.setBlockAndUpdate(pos, AetherIIBlocks.FERROSITE_MUD.get().defaultBlockState());
            }
            else level.setBlockAndUpdate(pos, Blocks.MUD.defaultBlockState());

            return InteractionResult.SUCCESS;
        } else {
            return InteractionResult.PASS;
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay tooltipDisplay, Consumer<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        AetherIITooltips.CURATIVE.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }
}