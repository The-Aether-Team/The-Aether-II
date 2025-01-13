package com.aetherteam.aetherii.item.materials;

import com.aetherteam.aetherii.client.AetherIISoundEvents;
import com.aetherteam.aetherii.client.particle.AetherIIParticleTypes;
import com.aetherteam.aetherii.item.miscellaneous.ItemUseConversion;
import com.aetherteam.aetherii.item.miscellaneous.UsableItem;
import com.aetherteam.aetherii.recipe.recipes.AetherIIRecipeTypes;
import com.aetherteam.aetherii.recipe.recipes.block.AmbrosiumRecipe;
import com.aetherteam.aetherii.recipe.set.AetherIIRecipePropertySets;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.ParticleUtils;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class AmbrosiumShardItem extends Item implements ItemUseConversion<AmbrosiumRecipe>, UsableItem {
    public AmbrosiumShardItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos().above();
        InteractionResult result = this.convertBlock(AetherIIRecipePropertySets.AMBROSIUM_ENCHANTING_STATES, AetherIIRecipeTypes.AMBROSIUM_ENCHANTING.get(), context);
        if (level.isClientSide() && result == InteractionResult.SUCCESS) {
            ParticleUtils.spawnParticlesOnBlockFace(level, pos, AetherIIParticleTypes.AMBROSIUM.get(), ConstantInt.of(25), Direction.UP, () -> Vec3.ZERO, -0.475);
            context.getLevel().playSound(context.getPlayer(), context.getClickedPos(), AetherIISoundEvents.ITEM_AMBROSIUM_SHARD.get(), SoundSource.BLOCKS, 1.0F, 3.0F + (context.getLevel().getRandom().nextFloat() - context.getLevel().getRandom().nextFloat()) * 0.8F);
        } else if (result == InteractionResult.PASS) {
            return super.useOn(context);
        }
        return result;
    }
}
