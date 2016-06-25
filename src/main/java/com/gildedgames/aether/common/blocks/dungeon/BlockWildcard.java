package com.gildedgames.aether.common.blocks.dungeon;

import com.gildedgames.aether.common.tile_entities.TileEntityWildcard;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
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
	
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumFacing side, float hitX, float hitY, float hitZ)
    {
    	if (!playerIn.isSneaking())
    	{
    		return false;
    	}
    	
        if (worldIn.isRemote)
        {
            return true;
        }
        else
        {
        	TileEntity tileentity = worldIn.getTileEntity(pos);

            if (tileentity instanceof ILockableContainer)
            {
                ILockableContainer ilockablecontainer = (ILockableContainer)tileentity;

		        if (ilockablecontainer != null)
		        {
		            playerIn.displayGUIChest(ilockablecontainer);
		        }

            	return true;
            }
        }
        
        return true;
    }

}
