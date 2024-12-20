package com.aetherteam.aetherii.item.materials;

import com.aetherteam.aetherii.client.AetherIISoundEvents;
import com.aetherteam.aetherii.client.particle.AetherIIParticleTypes;
import com.aetherteam.aetherii.item.miscellaneous.ItemUseConversion;
import com.aetherteam.aetherii.item.miscellaneous.UsableItem;
import com.aetherteam.aetherii.mixin.mixins.common.accessor.SaplingBlockAccessor;
import com.aetherteam.aetherii.recipe.recipes.AetherIIRecipeTypes;
import com.aetherteam.aetherii.recipe.recipes.block.AmbrosiumRecipe;
import com.aetherteam.aetherii.recipe.recipes.block.IrradiationRecipe;
import com.aetherteam.aetherii.world.tree.AetherIITreeGrowers;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.grower.TreeGrower;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;

public class IrradiatedDustItem extends Item implements ItemUseConversion<IrradiationRecipe>, UsableItem {
    public IrradiatedDustItem(Properties properties) {
        super(properties);
    }

    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        BlockPos blockpos = context.getClickedPos();
        if (applyDust(context.getItemInHand(), level, blockpos)) {
            if (!level.isClientSide()) {
                context.getPlayer().gameEvent(GameEvent.ITEM_INTERACT_FINISH);
                level.levelEvent(1505, blockpos, 15);
            }
            return InteractionResult.sidedSuccess(level.isClientSide);
        } else {
//            RandomSource random = level.getRandom();
            InteractionResult result = this.convertBlock(AetherIIRecipeTypes.DUST_IRRADIATION.get(), context);
            if (context.getLevel().isClientSide() && result == InteractionResult.SUCCESS) {
//                for (int j = 0; j < 25; ++j) {
//                    level.addParticle(AetherIIParticleTypes.AMBROSIUM.get(), pos.getX() + random.nextFloat(), pos.getY() + 0.1, pos.getZ() + random.nextFloat(), 0.0, 0.0, 0.0);
//                }
//                context.getLevel().playSound(context.getPlayer(), context.getClickedPos(), AetherIISoundEvents.ITEM_AMBROSIUM_SHARD.get(), SoundSource.BLOCKS, 1.0F, 3.0F + (context.getLevel().getRandom().nextFloat() - context.getLevel().getRandom().nextFloat()) * 0.8F);
            }
            return result;
        }
    }

    public static boolean applyDust(ItemStack stack, Level level, BlockPos pos) {
        BlockState blockState = level.getBlockState(pos);
        Block block = blockState.getBlock();
        if (block instanceof BonemealableBlock bonemealableblock) {
            if (bonemealableblock.isValidBonemealTarget(level, pos, blockState)) {
                if (level instanceof ServerLevel serverLevel) {
                    if (bonemealableblock.isBonemealSuccess(level, level.random, pos, blockState)) {
                        if (block instanceof SaplingBlock saplingBlock) {
                            if (blockState.getValue(SaplingBlock.STAGE) == 0) {
                                level.setBlock(pos, blockState.cycle(SaplingBlock.STAGE), 4);
                            } else {
                                TreeGrower normalTreeGrower = ((SaplingBlockAccessor) saplingBlock).aether_ii$getTreeGrower();
                                if (AetherIITreeGrowers.NORMAL_TO_IRRADIATED.containsKey(normalTreeGrower)) {
                                    AetherIITreeGrowers.NORMAL_TO_IRRADIATED.get(normalTreeGrower).growTree(serverLevel, serverLevel.getChunkSource().getGenerator(), pos, blockState, serverLevel.getRandom());
                                }
                            }
                        }
                    }
                    stack.shrink(1);
                }
                return true;
            }
        }
        return false;
    }
}
