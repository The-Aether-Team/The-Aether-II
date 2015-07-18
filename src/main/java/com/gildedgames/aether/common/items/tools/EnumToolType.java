package com.gildedgames.aether.common.items.tools;

import com.google.common.collect.Sets;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;

import java.util.Set;

public enum EnumToolType
{
	// It might be a good idea to look into using reflection instead of
	// copying the lists out of the respective tool classes.

	PICKAXE("pickaxe", 2f, Sets.newHashSet(Blocks.activator_rail, Blocks.coal_ore, Blocks.cobblestone, Blocks.detector_rail, Blocks.diamond_block, Blocks.diamond_ore, Blocks.double_stone_slab,
			Blocks.golden_rail, Blocks.gold_block, Blocks.gold_ore, Blocks.ice, Blocks.iron_block, Blocks.iron_ore, Blocks.lapis_block, Blocks.lapis_ore, Blocks.lit_redstone_ore,
			Blocks.mossy_cobblestone, Blocks.netherrack, Blocks.packed_ice, Blocks.rail, Blocks.redstone_ore, Blocks.sandstone, Blocks.red_sandstone, Blocks.stone, Blocks.stone_slab)),

	AXE("axe", 3f, Sets.newHashSet(Blocks.planks, Blocks.bookshelf, Blocks.log, Blocks.log2, Blocks.chest, Blocks.pumpkin, Blocks.lit_pumpkin, Blocks.melon_block, Blocks.ladder)),

	SHOVEL("shovel", 1f, Sets.newHashSet(Blocks.clay, Blocks.dirt, Blocks.farmland, Blocks.grass, Blocks.gravel, Blocks.mycelium, Blocks.sand, Blocks.snow, Blocks.snow_layer, Blocks.soul_sand));

	private final String toolClass;

	private final float baseDamage;

	private final Set<Block> effectiveBlocks;

	EnumToolType(String toolClass, float baseDamage, Set<Block> effectiveBlocks)
	{
		this.toolClass = toolClass;
		this.baseDamage = baseDamage;
		this.effectiveBlocks = effectiveBlocks;
	}

	public String getToolClass()
	{
		return this.toolClass;
	}

	public Set<Block> getEffectiveBlocks()
	{
		return this.effectiveBlocks;
	}

	public float getBaseDamage()
	{
		return this.baseDamage;
	}
}
