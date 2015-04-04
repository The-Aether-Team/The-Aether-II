package com.gildedgames.aether.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.registry.GameRegistry;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.blocks.natural.BlockAercloud;
import com.gildedgames.aether.blocks.natural.BlockAetherDirt;
import com.gildedgames.aether.blocks.natural.BlockAetherLog;
import com.gildedgames.aether.blocks.natural.BlockHolystone;
import com.gildedgames.aether.blocks.util.BlockCustom;
import com.gildedgames.aether.client.models.ModelsAether;
import com.gildedgames.aether.creativetabs.AetherCreativeTabs;
import com.gildedgames.aether.items.itemblocks.ItemBlockVariants;

public class BlocksAether
{
	public BlockAetherDirt aether_dirt;

	public BlockHolystone holystone;

	public BlockAercloud aercloud;

	public BlockAetherLog aether_log;

	public BlockCustom skyroot_planks;

	public void preInit()
	{
		this.aether_dirt = this.registerBlock("aether_dirt", ItemBlockVariants.class, new BlockAetherDirt());

		this.holystone = this.registerBlock("holystone", ItemBlockVariants.class, new BlockHolystone());

		this.aercloud = this.registerBlock("aercloud", ItemBlockVariants.class, new BlockAercloud());

		this.aether_log = this.registerBlock("aether_log", ItemBlockVariants.class, new BlockAetherLog());

		this.skyroot_planks = this.registerBlock("skyroot_planks", (BlockCustom) new BlockCustom(Material.wood).setStepSound(Block.soundTypeWood).setHardness(2.0f)
				.setResistance(5.0f).setCreativeTab(AetherCreativeTabs.tabBlocks));
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

			models.registerItemRenderer(this.aether_dirt, BlockAetherDirt.GRASS_TYPE.getAllowedValues());
			models.registerItemRenderer(this.holystone, BlockHolystone.HOLYSTONE_TYPE.getAllowedValues());
			models.registerItemRenderer(this.aercloud, BlockAercloud.AERCLOUD_TYPE.getAllowedValues());
			models.registerItemRenderer(this.aether_log, BlockAetherLog.LOG_TYPE.getAllowedValues());
			models.registerItemRenderer(this.skyroot_planks, 0);
		}
	}
}
