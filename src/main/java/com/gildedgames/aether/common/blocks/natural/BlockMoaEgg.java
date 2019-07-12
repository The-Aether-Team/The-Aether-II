package com.gildedgames.aether.common.blocks.natural;

import com.gildedgames.aether.api.registrar.ItemsAether;
import com.gildedgames.aether.common.blocks.IInternalBlock;
import com.gildedgames.aether.common.entities.animals.EntityMoa;
import com.gildedgames.aether.common.entities.genes.moa.MoaGenePool;
import com.gildedgames.aether.common.entities.genes.moa.MoaNest;
import com.gildedgames.aether.common.entities.tiles.TileEntityMoaEgg;
import com.gildedgames.aether.common.entities.util.groups.EntityGroup;
import com.gildedgames.aether.common.items.other.ItemMoaEgg;
import net.minecraft.block.Block;
import net.minecraft.block.ContainerBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import java.util.Collections;
import java.util.List;
import java.util.Random;

public class BlockMoaEgg extends ContainerBlock implements IInternalBlock
{

	public static final AxisAlignedBB BOUNDING_BOX = new AxisAlignedBB(0.2F, 0.0F, 0.2F, 0.8F, 0.75F, 0.8F);

	public BlockMoaEgg()
	{
		super(Material.GROUND);
		this.setSoundType(SoundType.STONE);
		this.setHardness(0.1F);
	}

	@Override
	public AxisAlignedBB getBoundingBox(BlockState state, IBlockReader source, BlockPos pos)
	{
		return BOUNDING_BOX;
	}

	@Override
	public void onBlockAdded(World world, BlockPos pos, BlockState state)
	{
		super.onBlockAdded(world, pos, state);

		TileEntity te = world.getTileEntity(pos);

		if (te instanceof TileEntityMoaEgg)
		{
			TileEntityMoaEgg moaEgg = (TileEntityMoaEgg) te;

			moaEgg.familyNest = new MoaNest(world, pos.add(0, -1, 0));
		}
	}

	@Override
	public void onBlockClicked(World world, BlockPos pos, PlayerEntity player)
	{
		if (!player.isCreative())
		{
			TileEntityMoaEgg egg = (TileEntityMoaEgg) world.getTileEntity(pos);

			if (egg != null)
			{
				EntityGroup pack = egg.familyNest.getAnimalPack();
				pack.addOrRenewAggressor(player);
				List moas = world.getEntitiesWithinAABB(EntityMoa.class, new AxisAlignedBB(
						pos.getX() - 12, pos.getY() - 8, pos.getZ() - 12, pos.getX() + 12, pos.getY() + 8, pos.getZ() + 12));

				for (Object moa : moas)
				{
					EntityMoa moa1 = (EntityMoa) moa;

					if (moa1.getGroup().getID() == pack.getID())
					{
						moa1.setEggStolen(true);
					}
				}

				ItemStack eggStack = new ItemStack(ItemsAether.moa_egg_item, 1, 0);

				MoaGenePool stackGenes = ItemMoaEgg.getGenePool(eggStack);

				stackGenes.transformFromParents(egg.getGenePool().getStorage().getSeed(), egg.getGenePool().getStorage().getFatherSeed(),
						egg.getGenePool().getStorage().getMotherSeed());

				ItemMoaEgg.setGenePool(eggStack, stackGenes);

				world.setBlockToAir(pos);

				Block.spawnAsEntity(world, pos, eggStack);
			}
		}
	}

	@Override
	public boolean removedByPlayer(BlockState state, World world, BlockPos pos, PlayerEntity player, boolean willHarvest)
	{
		this.onBlockClicked(world, pos, player);

		return super.removedByPlayer(state, world, pos, player, willHarvest);
	}

	@Override
	public List<ItemStack> getDrops(IBlockReader world, BlockPos pos, BlockState state, int fortune)
	{
		return Collections.emptyList();
	}

	@Override
	public Item getItemDropped(BlockState state, Random rand, int fortune)
	{
		return null;
	}

	@Override
	public boolean isOpaqueCube(BlockState state)
	{
		return false;
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
	public TileEntity createNewTileEntity(World world, int meta)
	{
		return new TileEntityMoaEgg();
	}

	@Override
	public ItemStack getPickBlock(BlockState state, RayTraceResult target, World world, BlockPos pos, PlayerEntity player)
	{
		ItemStack eggStack = new ItemStack(ItemsAether.moa_egg_item);
		TileEntityMoaEgg egg = (TileEntityMoaEgg) world.getTileEntity(pos);

		if (egg != null)
		{
			MoaGenePool teGenes = egg.getGenePool();
			MoaGenePool stackGenes = ItemMoaEgg.getGenePool(eggStack);

			stackGenes.transformFromParents(teGenes.getStorage().getSeed(), teGenes.getStorage().getFatherSeed(), teGenes.getStorage().getMotherSeed());

			ItemMoaEgg.setGenePool(eggStack, stackGenes);
		}

		return eggStack;
	}

}
