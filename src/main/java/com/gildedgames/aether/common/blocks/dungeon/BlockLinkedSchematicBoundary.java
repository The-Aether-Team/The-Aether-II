package com.gildedgames.aether.common.blocks.dungeon;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.gildedgames.aether.api.player.IPlayerAetherCapability;
import com.gildedgames.aether.common.player.PlayerAether;
import com.gildedgames.aether.common.tile_entities.TileEntityLinkedSchematicBoundary;

public class BlockLinkedSchematicBoundary extends BlockContainer
{
	public BlockLinkedSchematicBoundary()
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
			
			if (te instanceof TileEntityLinkedSchematicBoundary)
			{
				IPlayerAetherCapability hook = PlayerAether.getPlayer(player);
				
				TileEntityLinkedSchematicBoundary sb = (TileEntityLinkedSchematicBoundary)te;
				
				if (sb.isMarkedForGeneration())
				{
					sb.unmarkForGeneration();
					player.addChatComponentMessage(new ChatComponentTranslation("Unmarked for generation.", new Object[0]));
				}
				else
				{
					sb.markForGeneration();
					player.addChatComponentMessage(new ChatComponentTranslation("Marked for generation.", new Object[0]));
				}
			}
		}

		return true;
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta)
	{
		return new TileEntityLinkedSchematicBoundary();
	}
}
