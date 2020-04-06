package com.gildedgames.aether.common.items.other;

import com.gildedgames.aether.api.registrar.BlocksAether;
import com.gildedgames.aether.common.blocks.natural.BlockAetherDirt;
import com.gildedgames.aether.common.blocks.natural.BlockAetherGrass;
import com.gildedgames.aether.common.blocks.natural.BlockTheraDirt;
import com.gildedgames.aether.common.entities.monsters.EntitySwet;
import com.gildedgames.aether.common.items.IDropOnDeath;
import com.gildedgames.aether.common.world.biomes.arctic_peaks.BiomeArcticPeaks;
import com.gildedgames.aether.common.world.biomes.irradiated_forests.BiomeIrradiatedForests;
import com.gildedgames.aether.common.world.biomes.magnetic_hills.BiomeMagneticHills;
import net.minecraft.block.BlockDirt;
import net.minecraft.block.IGrowable;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.HashMap;

public class ItemSwetGel extends Item implements IDropOnDeath
{
	private static final HashMap<IBlockState, IBlockState> growables = new HashMap<>();

	public ItemSwetGel()
	{
		super();

		this.setHasSubtypes(true);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(final CreativeTabs tab, final NonNullList<ItemStack> subItems)
	{
		if (!this.isInCreativeTab(tab))
		{
			return;
		}

		for (final EntitySwet.Type types : EntitySwet.Type.values())
		{
			subItems.add(new ItemStack(this, 1, types.ordinal()));
		}
	}

	@Override
	public EnumActionResult onItemUse(final EntityPlayer player, final World world, final BlockPos pos, final EnumHand hand, final EnumFacing facing,
									  final float hitX, final float hitY, final float hitZ)
	{
		ItemStack stack = player.getHeldItem(hand);

		final IBlockState state = world.getBlockState(pos);

		Biome biome = world.getBiome(pos);

		ItemSwetGel.growables.put(Blocks.DIRT.getDefaultState().withProperty(BlockDirt.VARIANT, BlockDirt.DirtType.COARSE_DIRT),
				Blocks.DIRT.getDefaultState().withProperty(BlockDirt.VARIANT, BlockDirt.DirtType.DIRT));
		ItemSwetGel.growables.put(Blocks.DIRT.getDefaultState().withProperty(BlockDirt.VARIANT, BlockDirt.DirtType.DIRT), Blocks.GRASS.getDefaultState());
		ItemSwetGel.growables.put(BlocksAether.aether_dirt.getDefaultState().withProperty(BlockAetherDirt.PROPERTY_VARIANT, BlockAetherDirt.COARSE_DIRT),
				BlocksAether.aether_dirt.getDefaultState().withProperty(BlockAetherDirt.PROPERTY_VARIANT, BlockAetherDirt.DIRT));
		ItemSwetGel.growables.put(BlocksAether.thera_dirt.getDefaultState().withProperty(BlockTheraDirt.PROPERTY_VARIANT, BlockTheraDirt.DIRT), BlocksAether.thera_grass.getDefaultState());

		if (biome instanceof BiomeArcticPeaks)
		{
			ItemSwetGel.growables.put(BlocksAether.aether_dirt.getDefaultState().withProperty(BlockAetherDirt.PROPERTY_VARIANT, BlockAetherDirt.DIRT),
					BlocksAether.aether_grass.getDefaultState().withProperty(BlockAetherGrass.PROPERTY_VARIANT, BlockAetherGrass.ARCTIC));
		}
		else if (biome instanceof BiomeIrradiatedForests)
		{
			ItemSwetGel.growables.put(BlocksAether.aether_dirt.getDefaultState().withProperty(BlockAetherDirt.PROPERTY_VARIANT, BlockAetherDirt.DIRT),
					BlocksAether.aether_grass.getDefaultState().withProperty(BlockAetherGrass.PROPERTY_VARIANT, BlockAetherGrass.IRRADIATED));
		}
		else if (biome instanceof BiomeMagneticHills)
		{
			ItemSwetGel.growables.put(BlocksAether.aether_dirt.getDefaultState().withProperty(BlockAetherDirt.PROPERTY_VARIANT, BlockAetherDirt.DIRT),
					BlocksAether.aether_grass.getDefaultState().withProperty(BlockAetherGrass.PROPERTY_VARIANT, BlockAetherGrass.MAGNETIC));
		}
		else
		{
			ItemSwetGel.growables.put(BlocksAether.aether_dirt.getDefaultState().withProperty(BlockAetherDirt.PROPERTY_VARIANT, BlockAetherDirt.DIRT),
					BlocksAether.aether_grass.getDefaultState());
		}

		if (ItemSwetGel.growables.containsKey(state))
		{
			final IBlockState nState = ItemSwetGel.growables.get(state);

			final int radius = 1;

			for (int x = pos.getX() - radius; x <= pos.getX() + radius; x++)
			{
				for (int z = pos.getZ() - radius; z <= pos.getZ() + radius; z++)
				{
					final BlockPos nPos = new BlockPos(x, pos.getY(), z);
x
					if (world.getBlockState(nPos) == state && !world.getBlockState(nPos.up()).isNormalCube())
					{
						world.setBlockState(nPos, nState);
					}
				}
			}

			if (!player.capabilities.isCreativeMode)
			{
				stack.shrink(1);
			}

			return EnumActionResult.SUCCESS;
		}

		if (applyBonemeal(stack, world, pos, player, hand))
		{
			if (!world.isRemote)
			{
				world.playEvent(2005, pos, 0);
			}

			return EnumActionResult.SUCCESS;
		}

		return EnumActionResult.FAIL;
	}

	private static boolean applyBonemeal(ItemStack stack, World worldIn, BlockPos target, EntityPlayer player, @javax.annotation.Nullable EnumHand hand)
	{
		IBlockState iblockstate = worldIn.getBlockState(target);

		int hook = net.minecraftforge.event.ForgeEventFactory.onApplyBonemeal(player, worldIn, target, iblockstate, stack, hand);
		if (hook != 0) return hook > 0;

		if (iblockstate.getBlock() instanceof IGrowable)
		{
			IGrowable igrowable = (IGrowable)iblockstate.getBlock();

			if (igrowable.canGrow(worldIn, target, iblockstate, worldIn.isRemote))
			{
				if (!worldIn.isRemote)
				{
					if (igrowable.canUseBonemeal(worldIn, worldIn.rand, target, iblockstate))
					{
						igrowable.grow(worldIn, worldIn.rand, target, iblockstate);
					}

					stack.shrink(1);
				}

				return true;
			}
		}

		return false;
	}

	@Override
	public String getTranslationKey(final ItemStack stack)
	{
		return "item.aether.swet_gel." + EntitySwet.Type.fromOrdinal(stack.getMetadata()).name;
	}
}