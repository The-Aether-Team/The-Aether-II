package com.gildedgames.aether.common.entities.tiles;

import com.gildedgames.aether.common.entities.TileEntityTypesAether;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.FurnaceContainer;
import net.minecraft.item.crafting.AbstractCookingRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.tileentity.AbstractFurnaceTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class TileEntityHolystoneFurnace extends AbstractFurnaceTileEntity
{
	public TileEntityHolystoneFurnace()
	{
		super(TileEntityTypesAether.HOLYSTONE_FURNACE, IRecipeType.SMELTING);
	}

	@Override
	protected ITextComponent getDefaultName()
	{
		return new TranslationTextComponent("container.holystone_furnace");
	}

	@Override
	protected Container createMenu(int id, PlayerInventory inventory)
	{
		return new FurnaceContainer(id, inventory, this, this.field_214013_b);
	}
}
