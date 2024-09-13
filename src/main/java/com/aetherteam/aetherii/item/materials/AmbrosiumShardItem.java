package com.aetherteam.aetherii.item.materials;

import com.aetherteam.aetherii.client.AetherIISoundEvents;
import com.aetherteam.aetherii.client.particle.AetherIIParticleTypes;
import com.aetherteam.aetherii.item.miscellaneous.UsableItem;
import com.aetherteam.aetherii.item.miscellaneous.ItemUseConversion;
import com.aetherteam.aetherii.recipe.recipes.AetherIIRecipeTypes;
import com.aetherteam.aetherii.recipe.recipes.block.AmbrosiumRecipe;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;

public class AmbrosiumShardItem extends Item implements ItemUseConversion<AmbrosiumRecipe>, UsableItem {
    public AmbrosiumShardItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos().above();
        RandomSource random = level.getRandom();
        InteractionResult result = this.convertBlock(AetherIIRecipeTypes.AMBROSIUM_ENCHANTING.get(), context);
        if (context.getLevel().isClientSide() && result == InteractionResult.SUCCESS) {
            for (int j = 0; j < 25; ++j) {
                level.addParticle(AetherIIParticleTypes.AMBROSIUM.get(), pos.getX() + random.nextFloat(), pos.getY() + 0.1, pos.getZ() + random.nextFloat(), 0.0, 0.0, 0.0);
            }
            context.getLevel().playSound(context.getPlayer(), context.getClickedPos(), AetherIISoundEvents.ITEM_AMBROSIUM_SHARD.get(), SoundSource.BLOCKS, 1.0F, 3.0F + (context.getLevel().getRandom().nextFloat() - context.getLevel().getRandom().nextFloat()) * 0.8F);
        }
        return result;
    }
}
