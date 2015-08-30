package com.gildedgames.aether.common.blocks.natural;

import com.gildedgames.aether.common.blocks.BlocksAether;
import com.gildedgames.aether.common.items.ItemsAether;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLog;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.Random;

public class BlockAetherLog extends Block
{
	public static final PropertyEnum PROPERTY_AXIS = PropertyEnum.create("axis", BlockLog.EnumAxis.class);

	public BlockAetherLog()
	{
		super(Material.wood);

		this.setStepSound(Block.soundTypeWood);

		this.setHardness(2.0f);

		this.setDefaultState(this.getBlockState().getBaseState().withProperty(PROPERTY_AXIS, BlockLog.EnumAxis.Y));
	}

	@Override
	public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
	{
		return super.onBlockPlaced(worldIn, pos, facing, hitX, hitY, hitZ, meta, placer).withProperty(PROPERTY_AXIS, BlockLog.EnumAxis.fromFacingAxis(facing.getAxis()));
	}

	@Override
	public void breakBlock(World world, BlockPos pos, IBlockState state)
	{
		byte size = 4;
		int chunkSize = size + 1;

		if (world.isAreaLoaded(pos.add(-chunkSize, -chunkSize, -chunkSize), pos.add(chunkSize, chunkSize, chunkSize)))
		{
			for (Object obj : BlockPos.getAllInBox(pos.add(-size, -size, -size), pos.add(size, size, size)))
			{
				BlockPos neighborPos = (BlockPos) obj;
				IBlockState neighborState = world.getBlockState(neighborPos);

				if (neighborState.getBlock().isLeaves(world, neighborPos))
				{
					neighborState.getBlock().beginLeavesDecay(world, neighborPos);
				}
			}
		}
	}

	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		IBlockState state = this.getDefaultState();

		switch (meta & 8)
		{
		case 0:
			state = state.withProperty(PROPERTY_AXIS, BlockLog.EnumAxis.Y);
			break;
		case 4:
			state = state.withProperty(PROPERTY_AXIS, BlockLog.EnumAxis.X);
			break;
		case 8:
			state = state.withProperty(PROPERTY_AXIS, BlockLog.EnumAxis.Z);
			break;
		default:
			state = state.withProperty(PROPERTY_AXIS, BlockLog.EnumAxis.NONE);
		}

		return state;
	}

	@Override
	public int getMetaFromState(IBlockState state)
	{
		int meta = 0;

		switch (((BlockLog.EnumAxis) state.getValue(PROPERTY_AXIS)))
		{
		case X:
			meta |= 4;
			break;
		case Y:
			meta |= 8;
			break;
		case NONE:
			meta |= 12;
		}

		return meta;
	}

	@Override
	public boolean canSustainLeaves(IBlockAccess world, BlockPos pos)
	{
		return true;
	}

	@Override
	public boolean isWood(IBlockAccess world, BlockPos pos)
	{
		return true;
	}

	@Override
	public void onBlockHarvested(World world, BlockPos pos, IBlockState state, EntityPlayer player)
	{
		if (this == BlocksAether.golden_oak_log)
		{
			Item heldItem = player.getHeldItem().getItem();

			if (heldItem instanceof ItemTool)
			{
				ToolMaterial material = ((ItemTool) heldItem).getToolMaterial();

				if (material == ToolMaterial.GOLD || material == ToolMaterial.IRON || material == ToolMaterial.EMERALD)
				{
					this.dropGoldenAmber(world, pos, world.rand);
				}
			}
		}
	}

	private void dropGoldenAmber(World world, BlockPos pos, Random random)
	{
		ItemStack stack = new ItemStack(ItemsAether.golden_amber, random.nextInt(3) + 1);

		EntityItem entity = new EntityItem(world, pos.getX(), pos.getY(), pos.getZ(), stack);
		world.spawnEntityInWorld(entity);
	}

	@Override
	public int damageDropped(IBlockState state)
	{
		return 0;
	}

	@Override
	protected BlockState createBlockState()
	{
		return new BlockState(this, PROPERTY_AXIS);
	}
}
