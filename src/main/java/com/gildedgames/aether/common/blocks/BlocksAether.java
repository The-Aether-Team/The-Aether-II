package com.gildedgames.aether.common.blocks;

import com.gildedgames.aether.common.AetherCreativeTabs;
import com.gildedgames.aether.common.blocks.construction.BlockSkyrootWorkbench;
import com.gildedgames.aether.common.blocks.natural.BlockAercloud;
import com.gildedgames.aether.common.blocks.natural.BlockAetherGrass;
import com.gildedgames.aether.common.blocks.natural.BlockAetherLeaves;
import com.gildedgames.aether.common.blocks.natural.BlockAetherLog;
import com.gildedgames.aether.common.blocks.natural.BlockAmbrosiumOre;
import com.gildedgames.aether.common.blocks.natural.BlockContinuumOre;
import com.gildedgames.aether.common.blocks.natural.BlockGravititeOre;
import com.gildedgames.aether.common.blocks.natural.BlockHolystone;
import com.gildedgames.aether.common.blocks.natural.BlockQuicksoil;
import com.gildedgames.aether.common.blocks.natural.BlockTallAetherGrass;
import com.gildedgames.aether.common.blocks.natural.BlockZaniteOre;
import com.gildedgames.aether.common.blocks.util.BlockAether;
import com.gildedgames.aether.common.items.itemblocks.ItemBlockAetherSubtypes;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class BlocksAether
{
	public static Block aether_dirt;

	public static Block aether_grass;

	public static Block holystone;

	public static Block aercloud;

	public static Block aether_log;

	public static Block ambrosium_ore;

	public static Block zanite_ore;

	public static Block gravitite_ore;

	public static Block continuum_ore;

	public static Block skyroot_planks;

	public static Block aether_leaves;

	public static Block aether_portal;

	public static Block tall_aether_grass;

	public static Block quicksoil;

	public static Block skyroot_crafting_table;

	public static void preInit()
	{
		aether_dirt = registerBlock("aether_dirt", new BlockAether(Material.ground).setStepSound(Block.soundTypeGravel).setHardness(0.5f)
				.setCreativeTab(AetherCreativeTabs.tabBlocks));

		aether_grass = registerBlock("aether_grass", ItemBlockAetherSubtypes.class, new BlockAetherGrass());

		holystone = registerBlock("holystone", ItemBlockAetherSubtypes.class, new BlockHolystone());

		aercloud = registerBlock("aercloud", ItemBlockAetherSubtypes.class, new BlockAercloud());

		aether_log = registerBlock("aether_log", ItemBlockAetherSubtypes.class, new BlockAetherLog());

		skyroot_planks = registerBlock("skyroot_planks", new BlockAether(Material.wood).setStepSound(Block.soundTypeWood).setHardness(2.0f)
				.setResistance(5.0f).setCreativeTab(AetherCreativeTabs.tabBlocks));

		ambrosium_ore = registerBlock("ambrosium_ore", new BlockAmbrosiumOre());

		zanite_ore = registerBlock("zanite_ore", new BlockZaniteOre());

		gravitite_ore = registerBlock("gravitite_ore", new BlockGravititeOre());

		continuum_ore = registerBlock("continuum_ore", new BlockContinuumOre());

		aether_leaves = registerBlock("aether_leaves", ItemBlockAetherSubtypes.class, new BlockAetherLeaves());

		aether_portal = registerBlock("aether_portal", new BlockAetherPortal());

		tall_aether_grass = registerBlock("tall_aether_grass", new BlockTallAetherGrass());

		quicksoil = registerBlock("quicksoil", new BlockQuicksoil());

		skyroot_crafting_table = registerBlock("skyroot_crafting_table", new BlockSkyrootWorkbench());
	}

	private static Block registerBlock(String name, Block block)
	{
		return registerBlock(name, ItemBlock.class, block);
	}

	private static Block registerBlock(String name, Class<? extends ItemBlock> itemClass, Block block)
	{
		block.setUnlocalizedName(name);
		GameRegistry.registerBlock(block, itemClass, name);

		return block;
	}
}
