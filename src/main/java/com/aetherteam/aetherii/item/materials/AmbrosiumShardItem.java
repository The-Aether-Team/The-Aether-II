package com.aetherteam.aetherii.item.materials;

import com.aetherteam.aetherii.client.AetherIISoundEvents;
import com.aetherteam.aetherii.item.miscellaneous.ConsumableItem;
import com.aetherteam.aetherii.item.miscellaneous.ItemUseConversion;
import com.aetherteam.aetherii.recipe.AetherIIRecipeTypes;
import com.aetherteam.aetherii.recipe.recipes.block.AmbrosiumRecipe;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;

public class AmbrosiumShardItem extends Item implements ItemUseConversion<AmbrosiumRecipe>, ConsumableItem {
    public AmbrosiumShardItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        InteractionResult result = this.convertBlock(AetherIIRecipeTypes.AMBROSIUM_ENCHANTING.get(), context);
        if (context.getLevel().isClientSide() && result == InteractionResult.SUCCESS) { //todo particles.
            context.getLevel().playSound(context.getPlayer(), context.getClickedPos(), AetherIISoundEvents.ITEM_AMBROSIUM_SHARD.get(), SoundSource.BLOCKS, 1.0F, 3.0F + (context.getLevel().getRandom().nextFloat() - context.getLevel().getRandom().nextFloat()) * 0.8F);
        }
        return result;
    }
}
