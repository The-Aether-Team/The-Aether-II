package com.gildedgames.aether.common.blocks.natural;

import com.gildedgames.aether.api.registrar.ItemsAether;
import com.gildedgames.aether.common.entities.animals.EntityMoa;
import com.gildedgames.aether.common.entities.genes.moa.MoaGenePool;
import com.gildedgames.aether.common.entities.genes.moa.MoaNest;
import com.gildedgames.aether.common.entities.tiles.TileEntityMoaEgg;
import com.gildedgames.aether.common.entities.util.groups.EntityGroup;
import com.gildedgames.aether.common.items.other.ItemMoaEgg;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ContainerBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.IFluidState;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import java.util.List;

public class BlockMoaEgg extends ContainerBlock
{

	public static final VoxelShape BOUNDING_BOX = Block.makeCuboidShape(0.2F, 0.0F, 0.2F, 0.8F, 0.75F, 0.8F);

	public BlockMoaEgg(Block.Properties properties)
	{
		super(properties);
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context)
	{
		return BOUNDING_BOX;
	}

	@Override
	public void onBlockAdded(BlockState state1, World world, BlockPos pos, BlockState state2, boolean isMoving)
	{
		super.onBlockAdded(state1, world, pos, state2, isMoving);

		TileEntity te = world.getTileEntity(pos);

		if (te instanceof TileEntityMoaEgg)
		{
			TileEntityMoaEgg moaEgg = (TileEntityMoaEgg) te;

			moaEgg.familyNest = new MoaNest(world, pos.add(0, -1, 0));
		}
	}

	@Override
	public void onBlockClicked(BlockState state, World world, BlockPos pos, PlayerEntity player)
	{
		if (!player.isCreative())
		{
			TileEntityMoaEgg egg = (TileEntityMoaEgg) world.getTileEntity(pos);

			if (egg != null)
			{
				EntityGroup pack = egg.familyNest.getAnimalPack();
				pack.addOrRenewAggressor(player);
				List<EntityMoa> list = world.getEntitiesWithinAABB(EntityMoa.class, new AxisAlignedBB(
						pos.getX() - 12, pos.getY() - 8, pos.getZ() - 12, pos.getX() + 12, pos.getY() + 8, pos.getZ() + 12));

				for (EntityMoa moa : list)
				{
					if (moa.getGroup().getID() == pack.getID())
					{
						moa.setEggStolen(true);
					}
				}

				ItemStack eggStack = new ItemStack(ItemsAether.moa_egg_item, 1);

				MoaGenePool stackGenes = ItemMoaEgg.getGenePool(eggStack);

				stackGenes.transformFromParents(egg.getGenePool().getStorage().getSeed(), egg.getGenePool().getStorage().getFatherSeed(),
						egg.getGenePool().getStorage().getMotherSeed());

				ItemMoaEgg.setGenePool(eggStack, stackGenes);

				world.removeBlock(pos, false);

				Block.spawnAsEntity(world, pos, eggStack);
			}
		}
	}

	@Override
	public boolean removedByPlayer(BlockState state, World world, BlockPos pos, PlayerEntity player, boolean willHarvest, IFluidState fluid)
	{
		this.onBlockClicked(state, world, pos, player);

		return super.removedByPlayer(state, world, pos, player, willHarvest, fluid);
	}

	@Override
	public TileEntity createNewTileEntity(IBlockReader reader)
	{
		return new TileEntityMoaEgg();
	}

	@Override
	public ItemStack getItem(IBlockReader world, BlockPos pos, BlockState state)
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
