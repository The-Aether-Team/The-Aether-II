package com.gildedgames.aether.common.blocks.natural.plants;

import com.gildedgames.aether.common.AetherCreativeTabs;
import com.gildedgames.aether.common.blocks.BlocksAether;
import com.gildedgames.aether.common.blocks.natural.BlockAetherGrass;
import com.gildedgames.aether.common.items.ItemsAether;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Random;

public class BlockOrangeTree extends Block
{
	private static int STAGE_COUNT = 5;

	public static final PropertyBool PROPERTY_IS_TOP_BLOCK = PropertyBool.create("is_top_block");

	public static final PropertyInteger PROPERTY_STAGE = PropertyInteger.create("stage", 1, STAGE_COUNT);

	public BlockOrangeTree()
	{
		super(Material.leaves);

		this.setHardness(1f);

		this.setTickRandomly(true);

		this.setStepSound(Block.soundTypeGrass);

		this.setCreativeTab(AetherCreativeTabs.tabBlocks);

		this.setDefaultState(this.getBlockState().getBaseState().withProperty(PROPERTY_IS_TOP_BLOCK, false).withProperty(PROPERTY_STAGE, 1));
	}

	@Override
	public void randomTick(World world, BlockPos pos, IBlockState state, Random random)
	{
		int nextStage = (Integer) state.getValue(PROPERTY_STAGE) + 1;

		if (nextStage <= STAGE_COUNT)
		{
			BlockPos topBlock = (Boolean) state.getValue(PROPERTY_IS_TOP_BLOCK) ? pos : pos.up();
			BlockPos bottomBlock = (Boolean) state.getValue(PROPERTY_IS_TOP_BLOCK) ? pos.down() : pos;

			if (nextStage >= 3)
			{
				if (!world.isAirBlock(topBlock) && world.getBlockState(topBlock).getBlock() != this)
				{
					return;
				}

				world.setBlockState(topBlock, state.withProperty(PROPERTY_STAGE, nextStage).withProperty(PROPERTY_IS_TOP_BLOCK, true));
			}

			world.setBlockState(bottomBlock, state.withProperty(PROPERTY_STAGE, nextStage).withProperty(PROPERTY_IS_TOP_BLOCK, false));
		}
	}

	@Override
	public void harvestBlock(World world, EntityPlayer player, BlockPos pos, IBlockState state, TileEntity tileEntity)
	{
		boolean isTop = (Boolean) state.getValue(PROPERTY_IS_TOP_BLOCK);

		if ((Integer) state.getValue(PROPERTY_STAGE) == STAGE_COUNT)
		{
			BlockPos bottomPos = isTop ? pos.down() : pos;
			BlockPos topPos = isTop ? pos : pos.up();

			player.triggerAchievement(StatList.mineBlockStatArray[getIdFromBlock(this)]);
			player.addExhaustion(0.025F);

			this.stripTree(world, state, bottomPos, topPos);
			this.dropOranges(world, pos);
		}
		else
		{
			super.harvestBlock(world, player, pos, state, tileEntity);
		}
	}

	private void stripTree(World world, IBlockState state, BlockPos bottomPos, BlockPos topPos)
	{
		world.setBlockState(bottomPos, state.withProperty(PROPERTY_STAGE, 4).withProperty(PROPERTY_IS_TOP_BLOCK, false));
		world.setBlockState(topPos, state.withProperty(PROPERTY_STAGE, 4).withProperty(PROPERTY_IS_TOP_BLOCK, true));
	}

	private void dropOranges(World world, BlockPos pos)
	{
		IBlockState stateUnderneath = world.getBlockState(pos);

		boolean applyBonus = stateUnderneath.getBlock() == BlocksAether.aether_grass
				&& stateUnderneath.getValue(BlockAetherGrass.PROPERTY_VARIANT) == BlockAetherGrass.ENCHANTED_AETHER_GRASS;

		int count = world.rand.nextInt(3) + (applyBonus ? 2 : 1);

		ItemStack stack = new ItemStack(ItemsAether.orange, count);
		EntityItem entityItem = new EntityItem(world, pos.getX(), pos.getY(), pos.getZ(), stack);

		world.spawnEntityInWorld(entityItem);
	}

	@Override
	public void onBlockDestroyedByPlayer(World world, BlockPos pos, IBlockState state)
	{
		boolean isTop = (Boolean) state.getValue(PROPERTY_IS_TOP_BLOCK);

		BlockPos adjacentPos = isTop ? pos.down() : pos.up();
		world.setBlockToAir(adjacentPos);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public EnumWorldBlockLayer getBlockLayer()
	{
		return EnumWorldBlockLayer.CUTOUT;
	}

	@Override
	public boolean isOpaqueCube()
	{
		return false;
	}

	@Override
	public boolean isFullCube()
	{
		return false;
	}

	@Override
	public boolean isPassable(IBlockAccess world, BlockPos pos)
	{
		return true;
	}

	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		int isTop = (meta / STAGE_COUNT);
		int stage = ((meta - (STAGE_COUNT * isTop)) % STAGE_COUNT) + 1;

		return this.getDefaultState().withProperty(PROPERTY_IS_TOP_BLOCK, isTop > 0).withProperty(PROPERTY_STAGE, stage);
	}

	@Override
	public int getMetaFromState(IBlockState state)
	{
		int top = ((Boolean) state.getValue(PROPERTY_IS_TOP_BLOCK)) ? STAGE_COUNT : 0;
		int stage = ((Integer) state.getValue(PROPERTY_STAGE));

		return top + stage;
	}

	@Override
	protected BlockState createBlockState()
	{
		return new BlockState(this, PROPERTY_IS_TOP_BLOCK, PROPERTY_STAGE);
	}
}
