package com.gildedgames.aether.common.blocks.containers;

import com.gildedgames.aether.common.blocks.BlocksAether;
import com.gildedgames.aether.common.items.blocks.ItemBlockPresent;
import com.gildedgames.aether.common.registry.content.SoundsAether;
import com.gildedgames.aether.common.entities.tiles.TileEntityPresent;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class BlockPresent extends BlockContainer
{

	private static final AxisAlignedBB BOUNDS = new AxisAlignedBB(0.15f, 0f, 0.15f, 0.85f, 0.7f, 0.85f);

	public BlockPresent()
	{
		super(Material.GROUND);

		this.setHardness(0.4F);
		this.setLightOpacity(0);
		this.setSoundType(SoundType.GROUND);
	}

	@Override
	public boolean isFullCube(IBlockState state)
	{
		return false;
	}

	@Override
	public boolean isFullBlock(IBlockState state)
	{
		return false;
	}

	@Override
	@Deprecated
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
	{
		return BOUNDS;
	}

	@Override
	public boolean canPlaceBlockAt(World worldIn, BlockPos pos)
	{
		return worldIn.getBlockState(pos.down()).getMaterial().isSolid();
	}

	@Override
	public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
	{
		if (stack.getItem() instanceof ItemBlockPresent)
		{
			TileEntityPresent tileEntity = (TileEntityPresent) world.getTileEntity(pos);
			tileEntity.setPresentData(ItemBlockPresent.getData(stack));
		}
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand,
			EnumFacing side, float hitX, float hitY, float hitZ)
	{
		if (!world.isRemote)
		{
			TileEntity tileEntity = world.getTileEntity(pos);

			if (tileEntity instanceof TileEntityPresent)
			{
				this.destroyAndDropPresent(world, (TileEntityPresent) tileEntity, pos);
				return true;
			}

			return false;
		}
		else
		{
			for (int i = 0; i < 6; i++)
			{
				double x2 = pos.getX() + (world.rand.nextFloat() * 1.1f) - 0.05f;
				double y2 = pos.getY() + (world.rand.nextFloat() * 1.1f) - 0.05f;
				double z2 = pos.getZ() + (world.rand.nextFloat() * 1.1f) - 0.05f;

				world.spawnParticle(EnumParticleTypes.CLOUD, x2, y2, z2, 0.0D, 0.0D, 0.0D);
			}

			world.playSound(pos.getX(), pos.getY(), pos.getZ(), SoundsAether.present_unwrap, SoundCategory.NEUTRAL, 0.5f,
					0.8f + (world.rand.nextFloat() * 0.5f), false);

			return true;
		}
	}

	@Override
	public void neighborChanged(IBlockState state, World world, BlockPos pos, Block block, BlockPos fromPos)
	{
		if (!this.canPlaceBlockAt(world, pos))
		{
			this.destroyPresent(world, pos);
		}

		super.neighborChanged(state, world, pos, block, fromPos);
	}

	private void destroyAndDropPresent(World world, TileEntityPresent tileEntity, BlockPos pos)
	{
		tileEntity.dropItem();
		ItemStack drop = new ItemStack(Items.PAPER, 1);

		Block.spawnAsEntity(world, pos, drop);
		world.removeTileEntity(pos);
		world.setBlockToAir(pos);
	}

	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state)
	{
	}

	private void destroyPresent(World world, BlockPos pos)
	{
		TileEntity tileEntity = world.getTileEntity(pos);

		if (!(tileEntity instanceof TileEntityPresent))
		{
			return;
		}

		ItemBlockPresent.PresentData data = ((TileEntityPresent) tileEntity).getPresentData();
		ItemStack present = new ItemStack(BlocksAether.present);

		present.setTagCompound(new NBTTagCompound());

		data.writeToNBT(present.getTagCompound());
		Block.spawnAsEntity(world, pos, present);
		world.removeTileEntity(pos);
		world.setBlockToAir(pos);
	}

	@Override
	public void onBlockHarvested(World world, BlockPos pos, IBlockState state, EntityPlayer player)
	{
		this.destroyPresent(world, pos);
	}

	@Override
	public void harvestBlock(World worldIn, EntityPlayer player, BlockPos pos, IBlockState state, @Nullable TileEntity te,
			@Nullable ItemStack stack)
	{
		player.addStat(StatList.getBlockStats(this));
		player.addExhaustion(0.025F);
	}

	@Override
	public void onBlockDestroyedByExplosion(World world, BlockPos pos, Explosion explosionIn)
	{
		world.removeTileEntity(pos);
		world.setBlockToAir(pos);
	}

	@Override
	@Deprecated
	public boolean isOpaqueCube(IBlockState state)
	{
		return false;
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta)
	{
		return new TileEntityPresent();
	}

}
