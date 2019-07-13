package com.gildedgames.aether.common.blocks.containers;

import com.gildedgames.aether.api.registrar.BlocksAether;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.blocks.IBlockWithItem;
import com.gildedgames.aether.common.entities.tiles.TileEntityPresent;
import com.gildedgames.aether.common.items.blocks.ItemBlockPresent;
import net.minecraft.block.Block;
import net.minecraft.block.ContainerBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class BlockPresent extends ContainerBlock implements IBlockWithItem
{

	private static final AxisAlignedBB BOUNDS = new AxisAlignedBB(0.15f, 0f, 0.15f, 0.85f, 0.7f, 0.85f);

	public BlockPresent(Block.Properties properties)
	{
		super(properties);

		this.setLightOpacity(0);
	}

	@Override
	public boolean isFullCube(BlockState state)
	{
		return false;
	}

	@Override
	public boolean isFullBlock(BlockState state)
	{
		return false;
	}

	@Override
	@Deprecated
	public AxisAlignedBB getBoundingBox(BlockState state, IBlockReader source, BlockPos pos)
	{
		return BOUNDS;
	}

	@Override
	public boolean canPlaceBlockAt(World worldIn, BlockPos pos)
	{
		return worldIn.getBlockState(pos.down()).getMaterial().isSolid();
	}

	@Override
	public void onBlockPlacedBy(World world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack)
	{
		if (stack.getItem() instanceof ItemBlockPresent)
		{
			TileEntityPresent tileEntity = (TileEntityPresent) world.getTileEntity(pos);
			tileEntity.setPresentData(ItemBlockPresent.getData(stack));
		}
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, BlockState state, PlayerEntity player, Hand hand,
			Direction side, float hitX, float hitY, float hitZ)
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

				world.spawnParticle(ParticleTypes.CLOUD, x2, y2, z2, 0.0D, 0.0D, 0.0D);
			}

			world.playSound(pos.getX(), pos.getY(), pos.getZ(), new SoundEvent(AetherCore.getResource("random.present_unwrap")), SoundCategory.NEUTRAL, 0.5f,
					0.8f + (world.rand.nextFloat() * 0.5f), false);

			return true;
		}
	}

	@Override
	public void neighborChanged(BlockState state, World world, BlockPos pos, Block block, BlockPos fromPos, boolean p_220069_6_)
	{
		if (!this.canPlaceBlockAt(world, pos))
		{
			this.destroyPresent(world, pos);
		}

		super.neighborChanged(state, world, pos, block, fromPos, p_220069_6_);
	}

	private void destroyAndDropPresent(World world, TileEntityPresent tileEntity, BlockPos pos)
	{
		tileEntity.dropItem();
		ItemStack drop = new ItemStack(Items.PAPER, 1);

		Block.spawnAsEntity(world, pos, drop);
		world.removeTileEntity(pos);
		world.removeBlock(pos, false);
	}

	@Override
	public void breakBlock(World worldIn, BlockPos pos, BlockState state)
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

		present.setTag(new CompoundNBT());

		data.writeToNBT(present.getTag());
		Block.spawnAsEntity(world, pos, present);
		world.removeTileEntity(pos);
		world.removeBlock(pos, false);
	}

	@Override
	public void onBlockHarvested(World world, BlockPos pos, BlockState state, PlayerEntity player)
	{
		this.destroyPresent(world, pos);
	}

	@Override
	public void harvestBlock(World worldIn, PlayerEntity player, BlockPos pos, BlockState state, @Nullable TileEntity te,
			@Nullable ItemStack stack)
	{
		player.addStat(StatList.getBlockStats(this));
		player.addExhaustion(0.025F);
	}

	@Override
	public void onExplosionDestroy(World world, BlockPos pos, Explosion explosionIn)
	{
		world.removeTileEntity(pos);
		world.removeBlock(pos, false);
	}

	@Override
	@Deprecated
	public boolean isOpaqueCube(BlockState state)
	{
		return false;
	}

	@Override
	public TileEntity createNewTileEntity(IBlockReader reader)
	{
		return new TileEntityPresent();
	}

	@Override
	public BlockItem createItemBlock()
	{
		return new ItemBlockPresent(this);
	}
}
