package com.gildedgames.aether.blocks;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.blocks.natural.BlockAercloud;
import com.gildedgames.aether.blocks.natural.BlockAetherDirt;
import com.gildedgames.aether.blocks.natural.BlockAetherLeaves;
import com.gildedgames.aether.blocks.natural.BlockAetherLog;
import com.gildedgames.aether.blocks.natural.BlockAmbrosiumOre;
import com.gildedgames.aether.blocks.natural.BlockContinuumOre;
import com.gildedgames.aether.blocks.natural.BlockGravititeOre;
import com.gildedgames.aether.blocks.natural.BlockHolystone;
import com.gildedgames.aether.blocks.natural.BlockZaniteOre;
import com.gildedgames.aether.blocks.util.BlockCustom;
import com.gildedgames.aether.items.itemblocks.ItemBlockVariants;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class BlocksAether
{
	public Block aether_dirt;

	public Block holystone;

	public Block aercloud;

	public Block aether_log;

	public Block ambrosium_ore;

	public Block zanite_ore;

	public Block gravitite_ore;

	public Block continuum_ore;

	public Block skyroot_planks;

	public Block aether_leaves;

	public void preInit()
	{
		this.aether_dirt = this.registerBlock("aether_dirt", ItemBlockVariants.class, new BlockAetherDirt());

		this.holystone = this.registerBlock("holystone", ItemBlockVariants.class, new BlockHolystone());

		this.aercloud = this.registerBlock("aercloud", ItemBlockVariants.class, new BlockAercloud());

		this.aether_log = this.registerBlock("aether_log", ItemBlockVariants.class, new BlockAetherLog());

		this.skyroot_planks = this.registerBlock("skyroot_planks", new BlockCustom(Material.wood).setStepSound(Block.soundTypeWood).setHardness(2.0f)
				.setResistance(5.0f).setCreativeTab(Aether.getCreativeTabs().tabBlocks));

		this.ambrosium_ore = this.registerBlock("ambrosium_ore", new BlockAmbrosiumOre());

		this.zanite_ore = this.registerBlock("zanite_ore", new BlockZaniteOre());

		this.gravitite_ore = this.registerBlock("gravitite_ore", new BlockGravititeOre());

		this.continuum_ore = this.registerBlock("continuum_ore", new BlockContinuumOre());

		this.aether_leaves = this.registerBlock("aether_leaves", ItemBlockVariants.class, new BlockAetherLeaves());
	}

	private Block registerBlock(String name, Block block)
	{
		block.setUnlocalizedName(name);
		GameRegistry.registerBlock(block, name);

		return block;
	}

	private Block registerBlock(String name, Class<? extends ItemBlock> itemblock, Block block)
	{
		block.setUnlocalizedName(name);
		GameRegistry.registerBlock(block, itemblock, name);

		return block;
	}
}
