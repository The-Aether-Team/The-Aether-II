package com.gildedgames.aether.common.blocks.natural;

import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.blocks.BlocksAether;
import com.gildedgames.aether.common.blocks.util.ISkyrootMinable;
import com.gildedgames.aether.common.blocks.util.variants.IBlockVariants;
import com.gildedgames.aether.common.blocks.util.variants.blockstates.BlockVariant;
import com.gildedgames.aether.common.blocks.util.variants.blockstates.PropertyVariant;
import com.gildedgames.aether.common.items.ItemsAether;
import com.gildedgames.util.modules.chunk.ChunkModule;
import com.gildedgames.util.modules.chunk.impl.hooks.BlockBitFlagChunkHook;
import com.google.common.base.Predicate;
import com.google.common.collect.Lists;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Collection;
import java.util.List;
import java.util.Random;

public class BlockHolystone extends Block implements IBlockVariants, ISkyrootMinable
{
	public static final BlockVariant
			NORMAL_HOLYSTONE = new BlockVariant(0, "normal"),
			MOSSY_HOLYSTONE = new BlockVariant(1, "mossy"),
			BLOOD_MOSS_HOLYSTONE = new BlockVariant(2, "blood_moss");

	public static final PropertyVariant PROPERTY_VARIANT = PropertyVariant.create("variant", NORMAL_HOLYSTONE, MOSSY_HOLYSTONE, BLOOD_MOSS_HOLYSTONE);

	public BlockHolystone()
	{
		super(Material.ROCK);

		this.setHardness(2.0F);
		this.setResistance(10.0F);

		this.setSoundType(SoundType.STONE);

		this.setDefaultState(this.getBlockState().getBaseState().withProperty(PROPERTY_VARIANT, NORMAL_HOLYSTONE));
	}

	@Override
	public boolean isReplaceableOreGen(IBlockState state, IBlockAccess world, BlockPos pos, Predicate<IBlockState> target)
	{
		if (target.apply(BlocksAether.holystone.getDefaultState()))
		{
			if (state.getBlock() == this && state.getValue(PROPERTY_VARIANT) == NORMAL_HOLYSTONE)
			{
				return true;
			}
		}

		return false;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(Item itemIn, CreativeTabs tab, List<ItemStack> list)
	{
		for (BlockVariant variant : PROPERTY_VARIANT.getAllowedValues())
		{
			if (variant != BLOOD_MOSS_HOLYSTONE)
			{
				list.add(new ItemStack(itemIn, 1, variant.getMeta()));
			}
		}
	}

	@Override
	public float getBlockHardness(IBlockState blockState, World world, BlockPos pos)
	{
		IBlockState state = world.getBlockState(pos);

		return state.getBlock() == this && state.getValue(PROPERTY_VARIANT) == BLOOD_MOSS_HOLYSTONE ? -1.0f : this.blockHardness;
	}

	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		return this.getDefaultState().withProperty(PROPERTY_VARIANT, PROPERTY_VARIANT.fromMeta(meta & 7));
	}

	@Override
	public int getMetaFromState(IBlockState state)
	{
		return state.getValue(PROPERTY_VARIANT).getMeta();
	}

	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, PROPERTY_VARIANT);
	}

	@Override
	public int damageDropped(IBlockState state)
	{
		return state.getValue(PROPERTY_VARIANT).getMeta();
	}

	@Override
	public String getUnlocalizedName(ItemStack stack)
	{
		return PROPERTY_VARIANT.fromMeta(stack.getMetadata()).getName();
	}

	@Override
	public void harvestBlock(World world, EntityPlayer player, BlockPos pos, IBlockState state, TileEntity te, ItemStack stack)
	{
		BlockBitFlagChunkHook data = ChunkModule.api().getHook(world, pos, AetherCore.PROXY.getPlacementFlagProvider());

		boolean wasPlaced = data.isMarked(pos);

		if (!wasPlaced && world.rand.nextBoolean())
		{
			Block.spawnAsEntity(world, pos, new ItemStack(ItemsAether.holystone_chip, world.rand.nextInt(1) + 1));
		}

		super.harvestBlock(world, player, pos, state, te, stack);
	}

	@Override
	public boolean canBlockDropDoubles(EntityLivingBase player, ItemStack stack, IBlockState state)
	{
		return true;
	}

	@Override
	public Collection<ItemStack> getAdditionalDrops(World world, BlockPos pos, IBlockState state, EntityLivingBase living)
	{
		List<ItemStack> drops = Lists.newArrayList();

		drops.add(new ItemStack(state.getBlock().getItemDropped(state, living.getRNG(), 0)));

		if (world.rand.nextBoolean())
		{
			drops.add(new ItemStack(ItemsAether.holystone_chip, world.rand.nextInt(1) + 1));
		}

		return drops;
	}

}
