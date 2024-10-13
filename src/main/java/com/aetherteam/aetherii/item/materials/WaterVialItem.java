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
import net.minecraft.world.InteractionResultHolder;
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
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entityLiving) {
        Player player = entityLiving instanceof Player ? (Player) entityLiving : null;
        if (player instanceof ServerPlayer serverPlayer) {
            CriteriaTriggers.CONSUME_ITEM.trigger(serverPlayer, stack);
        }

        if (player != null) {
            player.awardStat(Stats.ITEM_USED.get(this));
            stack.consume(1, player);
        }

        if (player == null || !player.hasInfiniteMaterials()) {
            if (stack.isEmpty()) {
                return new ItemStack(AetherIIItems.SCATTERGLASS_VIAL.get());
            }
            if (player != null) {
                player.getInventory().add(new ItemStack(AetherIIItems.SCATTERGLASS_VIAL.get()));
            }
        }

        entityLiving.gameEvent(GameEvent.DRINK);
        return stack;
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

            return InteractionResult.sidedSuccess(level.isClientSide());
        } else {
            return InteractionResult.PASS;
        }
    }

    @Override
    public int getUseDuration(ItemStack stack, LivingEntity entity) {
        return 32;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.DRINK;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        return ItemUtils.startUsingInstantly(level, player, hand);
    }
}