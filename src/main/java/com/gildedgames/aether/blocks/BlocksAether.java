package com.gildedgames.aether.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.registry.GameRegistry;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.blocks.natural.BlockAercloud;
import com.gildedgames.aether.blocks.natural.BlockAetherDirt;
import com.gildedgames.aether.blocks.natural.BlockAetherLog;
import com.gildedgames.aether.blocks.natural.BlockAmbrosiumOre;
import com.gildedgames.aether.blocks.natural.BlockContinuumOre;
import com.gildedgames.aether.blocks.natural.BlockGravititeOre;
import com.gildedgames.aether.blocks.natural.BlockHolystone;
import com.gildedgames.aether.blocks.natural.BlockZaniteOre;
import com.gildedgames.aether.blocks.util.BlockCustom;
import com.gildedgames.aether.client.models.ModelsAether;
import com.gildedgames.aether.items.itemblocks.ItemBlockVariants;

public class BlocksAether
{
	public BlockAetherDirt aether_dirt;

	public BlockHolystone holystone;

	public BlockAercloud aercloud;

	public BlockAetherLog aether_log;

	public BlockAmbrosiumOre ambrosium_ore;

	public BlockZaniteOre zanite_ore;

	public BlockGravititeOre gravitite_ore;

	public BlockContinuumOre continuum_ore;

	public BlockCustom skyroot_planks;

	public void preInit()
	{
		this.aether_dirt = this.registerBlock("aether_dirt", ItemBlockVariants.class, new BlockAetherDirt());

		this.holystone = this.registerBlock("holystone", ItemBlockVariants.class, new BlockHolystone());

		this.aercloud = this.registerBlock("aercloud", ItemBlockVariants.class, new BlockAercloud());

		this.aether_log = this.registerBlock("aether_log", ItemBlockVariants.class, new BlockAetherLog());

		this.skyroot_planks = this.registerBlock("skyroot_planks", (BlockCustom) new BlockCustom(Material.wood).setStepSound(Block.soundTypeWood).setHardness(2.0f)
				.setResistance(5.0f).setCreativeTab(Aether.getCreativeTabs().tabBlocks));

		this.ambrosium_ore = this.registerBlock("ambrosium_ore", new BlockAmbrosiumOre());

		this.zanite_ore = this.registerBlock("zanite_ore", new BlockZaniteOre());

		this.gravitite_ore = this.registerBlock("gravitite_ore", new BlockGravititeOre());

		this.continuum_ore = this.registerBlock("continuum_ore", new BlockContinuumOre());
	}

	private <T extends Block> T registerBlock(String name, T block)
	{
		block.setUnlocalizedName(name);
		GameRegistry.registerBlock(block, name);

		return block;
	}

	private <T extends Block> T registerBlock(String name, Class<? extends ItemBlock> itemblock, T block)
	{
		block.setUnlocalizedName(name);
		GameRegistry.registerBlock(block, itemblock, name);

		return block;
	}

	public void init()
	{
		if (Aether.PROXY.getModels() != null)
		{
			ModelsAether models = Aether.PROXY.getModels();

			models.registerItemRenderer(this.aether_dirt, BlockAetherDirt.GRASS_VARIANT.getAllowedValues());
			models.registerItemRenderer(this.holystone, BlockHolystone.HOLYSTONE_VARIANT.getAllowedValues());
			models.registerItemRenderer(this.aercloud, BlockAercloud.AERCLOUD_VARIANT.getAllowedValues());
			models.registerItemRenderer(this.aether_log, BlockAetherLog.LOG_VARIANT.getAllowedValues());
			models.registerItemRenderer(0, this.skyroot_planks);
			models.registerItemRenderer(0, this.ambrosium_ore);
			models.registerItemRenderer(0, this.zanite_ore);
			models.registerItemRenderer(0, this.gravitite_ore);
			models.registerItemRenderer(0, this.continuum_ore);
		}
	}
}
