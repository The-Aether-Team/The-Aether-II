package com.aetherteam.aetherii.item.materials;

import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.item.AetherIIItems;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;

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
                            (double) pos.getX() + level.random.nextDouble(),
                            pos.getY() + 1,
                            (double) pos.getZ() + level.random.nextDouble(),
                            1, 0.0, 0.0, 0.0, 1.0
                    );
                }
            }

            level.playSound(null, pos, SoundEvents.BOTTLE_EMPTY, SoundSource.BLOCKS, 1.0F, 1.0F);
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
}