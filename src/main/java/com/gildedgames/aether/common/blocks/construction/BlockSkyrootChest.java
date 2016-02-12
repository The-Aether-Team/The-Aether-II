package com.gildedgames.aether.common.blocks.construction;

import com.gildedgames.aether.common.tile_entities.TileEntitySkyrootChest;
import net.minecraft.block.Block;
import net.minecraft.block.BlockChest;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.inventory.InventoryLargeChest;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.ILockableContainer;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Iterator;

public class BlockSkyrootChest extends BlockChest
{
	public static final int CHEST_TYPE_ID = 2;

	public BlockSkyrootChest()
	{
		super(CHEST_TYPE_ID);

		this.setHardness(2.5f);

		this.setStepSound(Block.soundTypeWood);
	}

	@Override
	public ILockableContainer getLockableContainer(World worldIn, BlockPos pos)
	{
		TileEntity tileentity = worldIn.getTileEntity(pos);

		if (tileentity instanceof  TileEntitySkyrootChest)
		{
			Object object = tileentity;

			if (!this.isBlocked(worldIn, pos))
			{
				for (EnumFacing facing : EnumFacing.Plane.HORIZONTAL)
				{
					BlockPos adjPos = pos.offset(facing);
					Block adjBlock = worldIn.getBlockState(adjPos).getBlock();

					if (adjBlock == this)
					{
						if (this.isBlocked(worldIn, adjPos))
						{
							return null;
						}

						TileEntity adjTileEntity = worldIn.getTileEntity(adjPos);

						if (adjTileEntity instanceof TileEntityChest)
						{
							if (facing != EnumFacing.WEST && facing != EnumFacing.NORTH)
							{
								object = new InventoryLargeChest("container.skyroot_double_chest", (ILockableContainer) object, (TileEntityChest) adjTileEntity);
							}
							else
							{
								object = new InventoryLargeChest("container.skyroot_double_chest", (TileEntityChest) adjTileEntity, (ILockableContainer) object);
							}
						}
					}
				}

				return (ILockableContainer)object;
			}
		}

		return null;
	}

	private boolean isBlocked(World worldIn, BlockPos pos)
	{
		return this.isBelowSolidBlock(worldIn, pos) || this.isOcelotSittingOnChest(worldIn, pos);
	}

	private boolean isBelowSolidBlock(World worldIn, BlockPos pos)
	{
		return worldIn.isSideSolid(pos.up(), EnumFacing.DOWN, false);
	}

	private boolean isOcelotSittingOnChest(World worldIn, BlockPos pos)
	{
		Iterator iterator = worldIn.getEntitiesWithinAABB(EntityOcelot.class, new AxisAlignedBB((double)pos.getX(), (double)(pos.getY() + 1), (double)pos.getZ(), (double)(pos.getX() + 1), (double)(pos.getY() + 2), (double)(pos.getZ() + 1))).iterator();
		EntityOcelot entityocelot;

		do
		{
			if (!iterator.hasNext())
			{
				return false;
			}

			Entity entity = (Entity)iterator.next();
			entityocelot = (EntityOcelot)entity;
		}
		while (!entityocelot.isSitting());

		return true;
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta)
	{
		return new TileEntitySkyrootChest();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getRenderType()
	{
		return 2;
	}
}
