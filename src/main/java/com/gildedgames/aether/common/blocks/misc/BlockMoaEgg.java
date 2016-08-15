package com.gildedgames.aether.common.blocks.misc;

import com.gildedgames.aether.common.entities.biology.moa.MoaGenePool;
import com.gildedgames.aether.common.entities.moa.EntityMoa;
import com.gildedgames.aether.common.entities.moa.MoaNest;
import com.gildedgames.aether.common.entities.util.EntityGroup;
import com.gildedgames.aether.common.items.ItemsAether;
import com.gildedgames.aether.common.items.miscellaneous.ItemMoaEgg;
import com.gildedgames.aether.common.tile_entities.TileEntityMoaEgg;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.Collections;
import java.util.List;
import java.util.Random;

public class BlockMoaEgg extends BlockContainer
{

	private static final AxisAlignedBB BOUNDING_BOX = new AxisAlignedBB(0.2F, 0.0F, 0.2F, 0.8F, 0.75F, 0.8F);

	public BlockMoaEgg()
	{
		super(Material.GROUND);
		this.setSoundType(SoundType.STONE);
		this.setHardness(0.1F);
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
	{
		return BOUNDING_BOX;
	}

	@Override
	public void onBlockAdded(World world, BlockPos pos, IBlockState state)
	{
		super.onBlockAdded(world, pos, state);

		TileEntity te = world.getTileEntity(pos);

		if (te instanceof TileEntityMoaEgg)
		{
			TileEntityMoaEgg moaEgg = (TileEntityMoaEgg)te;

			moaEgg.familyNest = new MoaNest(world, pos.add(0, - 1, 0));
		}
	}

	@Override
	public void onBlockClicked(World world, BlockPos pos, EntityPlayer player)
	{
		if (!player.capabilities.isCreativeMode)
		{
			TileEntityMoaEgg egg = (TileEntityMoaEgg) world.getTileEntity(pos);

			if (egg != null)
			{
				EntityGroup pack = egg.familyNest.getAnimalPack();
				pack.addOrRenewAggressor(player);
				List moas = world.getEntitiesWithinAABB(EntityMoa.class, new AxisAlignedBB(pos.getX() - 12, pos.getY() - 8, pos.getZ() - 12, pos.getX() + 12, pos.getY() + 8, pos.getZ() + 12));

				for (Object moa : moas)
				{
					EntityMoa moa1 = (EntityMoa) moa;

					if (moa1.getGroup().getID() == pack.getID())
					{
						moa1.setEggStolen(true);
					}
				}

				ItemStack eggStack = new ItemStack(ItemsAether.moa_egg, 1, 0);

				if (egg != null)
				{
					MoaGenePool teGenes = MoaGenePool.get(egg);
					MoaGenePool stackGenes = MoaGenePool.get(eggStack);

					stackGenes.transformFromParents(teGenes.getSeed(), teGenes.getFatherSeed(), teGenes.getMotherSeed());
				}

				world.setBlockToAir(pos);

				Block.spawnAsEntity(world, pos, eggStack);
			}
		}
	}

	@Override
	public boolean removedByPlayer(IBlockState state, World world, BlockPos pos, EntityPlayer player, boolean willHarvest)
	{
		this.onBlockClicked(world, pos, player);

		return super.removedByPlayer(state, world, pos, player, willHarvest);
	}

	@Override
	public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune)
	{
		return Collections.emptyList();
	}

	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune)
	{
		return null;
	}

	@Override
	public boolean isOpaqueCube(IBlockState state)
	{
		return false;
	}

	@Override
	public boolean isFullCube(IBlockState state)
	{
		return false;
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta)
	{
		return new TileEntityMoaEgg();
	}

	@Override
	public ItemStack getItem(World world, BlockPos pos, IBlockState state)
	{
		ItemStack eggStack = new ItemStack(ItemsAether.moa_egg, 1, 0);
		TileEntityMoaEgg egg = (TileEntityMoaEgg) world.getTileEntity(pos);

		if (egg != null)
		{
			MoaGenePool teGenes = MoaGenePool.get(egg);
			MoaGenePool stackGenes = MoaGenePool.get(eggStack);

			stackGenes.transformFromParents(teGenes.getSeed(), teGenes.getFatherSeed(), teGenes.getMotherSeed());
		}

		return eggStack;
	}

}
