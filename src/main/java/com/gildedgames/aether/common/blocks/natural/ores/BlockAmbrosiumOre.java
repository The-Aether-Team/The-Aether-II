package com.gildedgames.aether.common.blocks.natural.ores;

import com.gildedgames.aether.common.blocks.util.ISkyrootMinable;
import com.gildedgames.aether.common.items.ItemsAether;
import com.gildedgames.aether.common.items.tools.ItemAetherTool;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

public class BlockAmbrosiumOre extends Block implements ISkyrootMinable
{
	public BlockAmbrosiumOre()
	{
		super(Material.rock);

		this.setHardness(3.0f);
		this.setResistance(5.0f);
		this.setHarvestLevel("pickaxe", 0);

		this.setStepSound(Block.soundTypeStone);
	}

	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune)
	{
		return ItemsAether.ambrosium_shard;
	}

	@Override
	public boolean canBlockDropDoubles(EntityLivingBase player, ItemStack stack, IBlockState state)
	{
		return true;
	}

	@Override
	public Collection<ItemStack> getAdditionalDrops(World world, BlockPos pos, IBlockState state, EntityLivingBase living)
	{
		Collection<ItemStack> drops = new ArrayList<>();
		drops.add(new ItemStack(this.getItemDropped(state, world.rand, EnchantmentHelper.getFortuneModifier(living))));

		return drops;
	}
}
