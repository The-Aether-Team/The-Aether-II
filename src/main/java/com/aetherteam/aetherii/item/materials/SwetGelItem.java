package com.aetherteam.aetherii.item.materials;

import com.aetherteam.aetherii.client.AetherIISoundEvents;
import com.aetherteam.aetherii.item.miscellaneous.ItemUseConversion;
import com.aetherteam.aetherii.recipe.recipes.AetherIIRecipeTypes;
import com.aetherteam.aetherii.recipe.recipes.block.SwetGelRecipe;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.BoneMealItem;
import net.minecraft.world.item.context.UseOnContext;

public class SwetGelItem extends BoneMealItem implements ItemUseConversion<SwetGelRecipe> {
    public SwetGelItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        InteractionResult result = this.convertBlock(AetherIIRecipeTypes.SWET_GEL_CONVERSION.get(), context);
        if (context.getLevel().isClientSide() && result == InteractionResult.SUCCESS) { //todo partickles
            context.getLevel().playSound(context.getPlayer(), context.getClickedPos(), AetherIISoundEvents.ITEM_SWET_BALL_USE.get(), SoundSource.BLOCKS, 0.8F, 1.0F + (context.getLevel().getRandom().nextFloat() - context.getLevel().getRandom().nextFloat()) * 0.2F);
        } else if (result == InteractionResult.PASS) {
            return super.useOn(context);
        }
        return result;
    }
}
