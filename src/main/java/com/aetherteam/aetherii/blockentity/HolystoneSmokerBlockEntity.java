package com.aetherteam.aetherii.blockentity;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.inventory.menu.HolystoneFurnaceMenu;
import com.aetherteam.aetherii.inventory.menu.HolystoneSmokerMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class HolystoneSmokerBlockEntity extends AbstractFurnaceBlockEntity {
    public HolystoneSmokerBlockEntity(BlockPos pos, BlockState state) {
        super(AetherIIBlockEntityTypes.HOLYSTONE_SMOKER.get(), pos, state, RecipeType.SMOKING);
    }

    protected Component getDefaultName() {
        return Component.translatable("menu." + AetherII.MODID + ".holystone_smoker");
    }

    protected AbstractContainerMenu createMenu(int id, Inventory playerInventory) {
        return new HolystoneSmokerMenu(id, playerInventory, this, this.dataAccess);
    }
}