package com.gildedgames.aether.common.blocks.dungeon;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.gildedgames.aether.common.tile_entities.TileEntitySchematicBlock;
import com.gildedgames.aether.common.tile_entities.TileEntityWildcard;

public class BlockWildcard extends BlockContainer
{
	public BlockWildcard()
	{
		super(Material.rock);

		this.setHardness(2.5f);

		this.setStepSound(Block.soundTypeStone);
	}
	
	@Override
	public int getRenderType()
	{
		return 3;
	}
	
	@Override
	public boolean isOpaqueCube()
	{
		return false;
	}

	@Override
	public boolean isFullCube()
	{
		return true;
	}
	
	@SideOnly(Side.CLIENT)
	public EnumWorldBlockLayer getBlockLayer()
	{
		return EnumWorldBlockLayer.CUTOUT;
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumFacing side, float hitX, float hitY, float hitZ)
	{
		if (!world.isRemote)
		{
			TileEntity te = world.getTileEntity(pos);
			
			if (te instanceof TileEntitySchematicBlock)
			{
				TileEntitySchematicBlock sch = (TileEntitySchematicBlock)te;
				
				//sch.markForGeneration();
			}
			//player.openGui(AetherCore.INSTANCE, AetherGuiHandler.SKYROOT_WORKBENCH_ID, world, pos.getX(), pos.getY(), pos.getZ());
		}

		return false;
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta)
	{
		return new TileEntityWildcard();
	}
}
