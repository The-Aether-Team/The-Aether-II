package com.aetherteam.aetherii.item.materials;

import com.aetherteam.aetherii.client.AetherIISoundEvents;
import com.aetherteam.aetherii.item.miscellaneous.ItemUseConversion;
import com.aetherteam.aetherii.recipe.recipes.AetherIIRecipeTypes;
import com.aetherteam.aetherii.recipe.recipes.block.SwetGelRecipe;
import com.aetherteam.aetherii.recipe.set.AetherIIRecipePropertySets;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.BoneMealItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;

public class SwetGelItem extends BoneMealItem implements ItemUseConversion<SwetGelRecipe> {
    public SwetGelItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos().above();
        RandomSource random = level.getRandom();
        InteractionResult result = this.convertBlock(AetherIIRecipePropertySets.SWET_GEL_CONVERSION_STATES, AetherIIRecipeTypes.SWET_GEL_CONVERSION.get(), context);
        if (level.isClientSide() && result == InteractionResult.SUCCESS) {
            for (int j = 0; j < 10; ++j) {
                level.addParticle(new ItemParticleOption(ParticleTypes.ITEM, new ItemStack(this)), pos.getX() + random.nextFloat(), pos.getY(), pos.getZ() + random.nextFloat(), random.nextFloat() * (random.nextBoolean() ? -1 : 1) * 0.05, random.nextFloat() * 0.1, random.nextFloat() * (random.nextBoolean() ? -1 : 1) * 0.05);
            }
            context.getLevel().playSound(context.getPlayer(), context.getClickedPos(), AetherIISoundEvents.ITEM_SWET_BALL_USE.get(), SoundSource.BLOCKS, 0.8F, 1.0F + (context.getLevel().getRandom().nextFloat() - context.getLevel().getRandom().nextFloat()) * 0.2F);
        } else if (result == InteractionResult.PASS) {
            return super.useOn(context);
        }
        return result;
    }
}
