package com.gildedgames.aether.common.blocks.dungeon;

import com.gildedgames.aether.api.player.IPlayerAetherCapability;
import com.gildedgames.aether.common.player.PlayerAether;
import com.gildedgames.aether.common.tile_entities.TileEntityBoundary;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.ILockableContainer;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockBoundary extends BlockContainer
{
	public BlockBoundary()
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
	
	@SideOnly(Side.CLIENT)
	public BlockRenderLayer getBlockLayer()
	{
		return BlockRenderLayer.CUTOUT;
	}
	
	@Override
    public boolean removedByPlayer(IBlockState state, World world, BlockPos pos, EntityPlayer player, boolean willHarvest)
    {
		TileEntity te = world.getTileEntity(pos);
		
		if (te instanceof TileEntityBoundary)
		{
			TileEntityBoundary sb = (TileEntityBoundary)te;
			
			if (sb.isLinked())
			{
				if (sb.isMasterBoundary())
				{
					sb.destroyLinkAndContents();
				}
				else
				{
					te = world.getTileEntity(sb.getLink());
					
					if (te instanceof TileEntityBoundary)
					{
						sb = (TileEntityBoundary)te;
						
						sb.destroyLinkAndContents();
					}
				}
			}
		}
		
		return super.removedByPlayer(state, world, pos, player, willHarvest);
    }

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ)
	{
		if (!world.isRemote)
		{
			if (player.isSneaking())
			{
				TileEntity tileentity = world.getTileEntity(pos);

	            if (tileentity instanceof ILockableContainer)
	            {
	                ILockableContainer container = (ILockableContainer) tileentity;
					player.displayGUIChest(container);

	            	return true;
	            }
			}
			
			TileEntity te = world.getTileEntity(pos);
			
			if (te instanceof TileEntityBoundary)
			{
				IPlayerAetherCapability hook = PlayerAether.getPlayer(player);
				
				TileEntityBoundary sb = (TileEntityBoundary)te;
				
				if (hook.getLinkingSchematicBoundary() != null)
				{
					if (sb.isLinked())
					{
						player.addChatComponentMessage(new TextComponentTranslation("This boundary has already been linked with something else."));
					}
					else
					{
						sb.linkToPos(hook.getLinkingSchematicBoundary());
						hook.setLinkingSchematicBoundary(null);
						
						player.addChatComponentMessage(new TextComponentTranslation("Successfully linked."));
					}
				}
				else if (!sb.isLinked())
				{
					hook.setLinkingSchematicBoundary(pos);
					player.addChatComponentMessage(new TextComponentTranslation("Right-click another boundary to link them together."));
				}
				else
				{
					if (sb.isMarkedForGeneration())
					{
						sb.unmarkForGeneration();
						player.addChatComponentMessage(new TextComponentTranslation("Unmarked for generation."));
					}
					else
					{
						sb.markForGeneration();
						player.addChatComponentMessage(new TextComponentTranslation("Marked for generation."));
					}
				}
			}
		}

		return true;
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta)
	{
		return new TileEntityBoundary();
	}
}
