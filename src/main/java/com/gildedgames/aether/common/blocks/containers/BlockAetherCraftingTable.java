package com.gildedgames.aether.common.blocks.containers;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.CraftingTableBlock;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.inventory.container.SimpleNamedContainerProvider;
import net.minecraft.inventory.container.WorkbenchContainer;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

public class BlockAetherCraftingTable extends CraftingTableBlock
{
	private static final ITextComponent NAME = new TranslationTextComponent("container.crafting");

	public BlockAetherCraftingTable(Block.Properties properties)
	{
		super(properties);
	}

	@Override
	public INamedContainerProvider getContainer(BlockState state, World worldIn, BlockPos pos) {
		return new SimpleNamedContainerProvider((id, inventory, player) -> new WorkbenchContainer(id, inventory, IWorldPosCallable.of(worldIn, pos)), NAME);
	}
}
