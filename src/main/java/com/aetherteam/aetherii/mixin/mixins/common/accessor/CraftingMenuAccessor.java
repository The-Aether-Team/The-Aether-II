package com.aetherteam.aetherii.mixin.mixins.common.accessor;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.inventory.CraftingMenu;
import net.minecraft.world.inventory.ResultContainer;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import javax.annotation.Nullable;

@Mixin(CraftingMenu.class)
public interface CraftingMenuAccessor {
    @Invoker
    static void callSlotChangedCraftingGrid(AbstractContainerMenu p_150547_, ServerLevel p_379963_, Player p_150549_, CraftingContainer p_150550_, ResultContainer p_150551_, @Nullable RecipeHolder<CraftingRecipe> p_345124_) {
        throw new UnsupportedOperationException();
    }
}
