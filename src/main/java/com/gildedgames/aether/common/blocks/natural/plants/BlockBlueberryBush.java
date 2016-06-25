package com.gildedgames.aether.common.blocks.natural.plants;

import com.gildedgames.aether.common.blocks.BlocksAether;
import com.gildedgames.aether.common.blocks.natural.BlockAetherGrass;
import com.gildedgames.aether.common.blocks.util.variants.IAetherBlockWithVariants;
import com.gildedgames.aether.common.items.ItemsAether;
import net.minecraft.block.IGrowable;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;
import java.util.Random;

public class BlockBlueberryBush extends BlockAetherPlant implements IAetherBlockWithVariants, IGrowable
{
	public static final int
			BERRY_BUSH_STEM = 0,
			BERRY_BUSH_RIPE = 1;

	public static final PropertyBool PROPERTY_HARVESTABLE = PropertyBool.create("harvestable");

	public BlockBlueberryBush()
	{
		super(Material.LEAVES);

		this.setHardness(1f);

		this.setSoundType(SoundType.PLANT);

		this.setTickRandomly(true);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(Item itemIn, CreativeTabs tab, List<ItemStack> list)
	{
		list.add(new ItemStack(itemIn, 1, BERRY_BUSH_STEM));
		list.add(new ItemStack(itemIn, 1, BERRY_BUSH_RIPE));
	}

	@Override
	public boolean removedByPlayer(IBlockState state, World world, BlockPos pos, EntityPlayer player, boolean willHarvest)
	{
		if (state.getValue(PROPERTY_HARVESTABLE))
		{
			world.setBlockState(pos, state.withProperty(PROPERTY_HARVESTABLE, false));

			if (!world.isRemote && !player.capabilities.isCreativeMode)
			{
				this.dropBerries(world, pos, world.rand);
			}

			return false;
		}

		return super.removedByPlayer(state, world, pos, player, willHarvest);
	}

	@Override
	protected void invalidateBlock(World world, BlockPos pos, IBlockState state)
	{
		if (state.getValue(PROPERTY_HARVESTABLE))
		{
			this.dropBerries(world, pos, world.rand);
		}

		super.invalidateBlock(world, pos, state);
	}

	private void dropBerries(World world, BlockPos pos, Random random)
	{
		IBlockState stateUnderneath = world.getBlockState(pos.down());

		boolean applyBonus = stateUnderneath.getBlock() == BlocksAether.aether_grass
				&& stateUnderneath.getValue(BlockAetherGrass.PROPERTY_VARIANT) == BlockAetherGrass.ENCHANTED_AETHER_GRASS;

		int count = random.nextInt(2) + (applyBonus ? 2 : 1);

		ItemStack itemStack = new ItemStack(ItemsAether.blueberry, count);
		EntityItem entityItem = new EntityItem(world, pos.getX(), pos.getY(), pos.getZ(), itemStack);

		world.spawnEntityInWorld(entityItem);
	}

	@Override
	public void updateTick(World world, BlockPos pos, IBlockState state, Random random)
	{
		if (!state.getValue(PROPERTY_HARVESTABLE))
		{
			if (world.getLight(pos) >= 9)
			{
				if (random.nextInt(60) == 0)
				{
					world.setBlockState(pos, state.withProperty(PROPERTY_HARVESTABLE, true));
				}
			}
		}
	}

	@Override
	public int damageDropped(IBlockState state)
	{
		return BERRY_BUSH_STEM;
	}

	@Override
	public boolean canGrow(World worldIn, BlockPos pos, IBlockState state, boolean isClient)
	{
		return !state.getValue(PROPERTY_HARVESTABLE);
	}

	@Override
	public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, IBlockState state)
	{
		return !state.getValue(PROPERTY_HARVESTABLE);
	}

	@Override
	public void grow(World world, Random rand, BlockPos pos, IBlockState state)
	{
		world.setBlockState(pos, state.withProperty(PROPERTY_HARVESTABLE, true));
	}

	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		return this.getDefaultState().withProperty(PROPERTY_HARVESTABLE, meta == BERRY_BUSH_RIPE);
	}

	@Override
	public int getMetaFromState(IBlockState state)
	{
		return state.getValue(PROPERTY_HARVESTABLE) ? BERRY_BUSH_RIPE : BERRY_BUSH_STEM;
	}

	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, PROPERTY_HARVESTABLE);
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
	{
		return FULL_BLOCK_AABB;
	}

	@Override
	public String getSubtypeUnlocalizedName(ItemStack stack)
	{
		switch (stack.getMetadata())
		{
		case BERRY_BUSH_STEM:
			return "stem";
		case BERRY_BUSH_RIPE:
			return "ripe";
		default:
			return "missingno";
		}
	}
}
