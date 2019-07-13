package com.gildedgames.aether.common.blocks.containers;

import com.gildedgames.aether.common.entities.tiles.TileEntitySkyrootChest;
import net.minecraft.block.Block;
import net.minecraft.block.ChestBlock;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.inventory.InventoryLargeChest;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.Direction;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.ILockableContainer;
import net.minecraft.world.World;
import net.minecraftforge.common.util.EnumHelper;

import java.util.Iterator;

public class BlockSkyrootChest extends ChestBlock
{
	public BlockSkyrootChest(Block.Properties properties)
	{
		super(properties.hardnessAndResistance(2.5f));
	}


	@Override
	public TileEntity createNewTileEntity(IBlockReader reader)
	{
		return new TileEntitySkyrootChest();
	}
}
