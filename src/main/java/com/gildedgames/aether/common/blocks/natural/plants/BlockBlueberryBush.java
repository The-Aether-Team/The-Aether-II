package com.gildedgames.aether.common.blocks.natural.plants;

import com.gildedgames.aether.common.blocks.BlocksAether;
import com.gildedgames.aether.common.blocks.natural.BlockAetherGrass;
import com.gildedgames.aether.common.blocks.util.variants.IBlockVariants;
import com.gildedgames.aether.common.items.ItemsAether;
import com.gildedgames.aether.common.registry.content.MaterialsAether;
import com.google.common.collect.Lists;
import net.minecraft.block.Block;
import net.minecraft.block.IGrowable;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

public class BlockBlueberryBush extends BlockAetherPlant implements IBlockVariants, IGrowable
{
	private static final AxisAlignedBB BUSH_AABB = new AxisAlignedBB(0.2D, 0.0D, 0.2D, 0.8D, 0.8D, 0.8D);

	public static final int
			BERRY_BUSH_STEM = 0,
			BERRY_BUSH_RIPE = 1;

	public static final PropertyBool PROPERTY_HARVESTABLE = PropertyBool.create("harvestable");

	public BlockBlueberryBush()
	{
		super(Material.LEAVES);

		this.setHardness(1.0f);

		this.setSoundType(SoundType.PLANT);

		this.setTickRandomly(true);
	}

	@Override
	public float getBlockHardness(IBlockState blockState, World worldIn, BlockPos pos)
	{
		if (blockState.getValue(PROPERTY_HARVESTABLE))
		{
			return 0.0f;
		}

		return super.getBlockHardness(blockState, worldIn, pos);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(Item itemIn, CreativeTabs tab, NonNullList<ItemStack> list)
	{
		list.add(new ItemStack(itemIn, 1, BERRY_BUSH_STEM));
		list.add(new ItemStack(itemIn, 1, BERRY_BUSH_RIPE));
	}

	@Override
	public boolean removedByPlayer(IBlockState state, World world, BlockPos pos, EntityPlayer player, boolean willHarvest)
	{
		if (state.getValue(PROPERTY_HARVESTABLE))
		{
			if (player.capabilities.isCreativeMode)
			{
				world.setBlockToAir(pos);

				return false;
			}

			if (!world.isRemote)
			{
				for (ItemStack item : this.getFruitDrops(world, pos, state, player))
				{
					Block.spawnAsEntity(world, pos, item);
				}
			}

			world.setBlockState(pos, state.withProperty(PROPERTY_HARVESTABLE, false));

			return false;
		}

		return super.removedByPlayer(state, world, pos, player, willHarvest);
	}

	@Override
	public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune)
	{
		List<ItemStack> items = super.getDrops(world, pos, state, fortune);

		items.addAll(this.getFruitDrops(world, pos, state,null));

		return items;
	}

	private List<ItemStack> getFruitDrops(IBlockAccess world, BlockPos pos, IBlockState state, @Nullable EntityPlayer player)
	{
		Random rand = world instanceof World ? ((World) world).rand : new Random();

		IBlockState stateUnderneath = world.getBlockState(pos.down());

		boolean applyBonus = stateUnderneath.getBlock() == BlocksAether.aether_grass
				&& stateUnderneath.getValue(BlockAetherGrass.PROPERTY_VARIANT) == BlockAetherGrass.ENCHANTED;

		int count = state.getValue(PROPERTY_HARVESTABLE) ? (rand.nextInt(2) + (applyBonus ? 2 : 1)) : 0;

		if (player != null && player.getHeldItem(EnumHand.MAIN_HAND).getItem() instanceof ItemTool)
		{
			ItemTool tool = (ItemTool) player.getHeldItem(EnumHand.MAIN_HAND).getItem();

			if (tool.getToolMaterial() == MaterialsAether.SKYROOT_TOOL)
			{
				count *= 2;
			}
		}

		return Lists.newArrayList(new ItemStack(ItemsAether.blueberries, count));
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
	public boolean isFullBlock(IBlockState state)
	{
		return state.getValue(PROPERTY_HARVESTABLE);
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
		return state.getValue(PROPERTY_HARVESTABLE) ? FULL_BLOCK_AABB : BUSH_AABB;
	}

	@Override
	@Nullable
	public AxisAlignedBB getCollisionBoundingBox(IBlockState state, IBlockAccess worldIn, BlockPos pos)
	{
		return state.getValue(PROPERTY_HARVESTABLE) ? FULL_BLOCK_AABB : NULL_AABB;
	}

	@Override
	public String getUnlocalizedName(ItemStack stack)
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
