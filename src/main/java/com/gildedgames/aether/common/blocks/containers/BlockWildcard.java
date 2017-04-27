package com.gildedgames.aether.common.blocks.containers;

import com.gildedgames.aether.common.items.ItemsAether;
import com.gildedgames.aether.common.registry.content.SoundsAether;
import com.gildedgames.aether.common.entities.tiles.TileEntitySchematicBlock;
import com.gildedgames.aether.common.entities.tiles.TileEntityWildcard;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ILockableContainer;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockWildcard extends BlockContainer
{

	public BlockWildcard()
	{
		super(Material.ROCK);

		this.setHardness(2.5f);

		this.setSoundType(SoundType.STONE);
	}

	@Override
	public EnumBlockRenderType getRenderType(IBlockState state)
	{
		return EnumBlockRenderType.MODEL;
	}

	@Override
	public boolean isOpaqueCube(IBlockState state)
	{
		return false;
	}

	@Override
	public boolean isFullCube(IBlockState state)
	{
		return true;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public BlockRenderLayer getBlockLayer()
	{
		return BlockRenderLayer.CUTOUT;
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta)
	{
		return new TileEntityWildcard();
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing side,
			float hitX, float hitY, float hitZ)
	{
		if (world.isRemote)
		{
			return true;
		}

		TileEntity te = world.getTileEntity(pos);

		if (te instanceof TileEntitySchematicBlock)
		{
			TileEntitySchematicBlock schematic = (TileEntitySchematicBlock) te;

			ItemStack heldItem = player.getHeldItem(hand);

			if (heldItem.getItem() == ItemsAether.aether_developer_wand)
			{
				world.playSound(player, pos, SoundsAether.tempest_electric_shock, SoundCategory.NEUTRAL, 1.0F,
						0.8F + (world.rand.nextFloat() * 0.5F));

				schematic.setMarkedForGeneration(!schematic.isMarkedForGeneration());

				return true;
			}
		}

		if (!player.isSneaking())
		{
			return false;
		}

		if (te instanceof ILockableContainer)
		{
			player.displayGUIChest((ILockableContainer) te);

			return true;
		}

		return true;
	}

}
