package com.gildedgames.aether.blocks;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.registry.GameRegistry;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.blocks.natural.BlockAercloud;
import com.gildedgames.aether.blocks.natural.BlockAetherDirt;
import com.gildedgames.aether.blocks.natural.BlockHolystone;
import com.gildedgames.aether.client.models.ModelsAether;
import com.gildedgames.aether.items.itemblocks.ItemBlockVariants;

public class BlocksAether
{
	public BlockAetherDirt aether_dirt;

	public BlockHolystone holystone;

	public BlockAercloud aercloud;

	public void preInit()
	{
		this.aether_dirt = this.registerBlock("aether_dirt", ItemBlockVariants.class, new BlockAetherDirt());

		this.holystone = this.registerBlock("holystone", ItemBlockVariants.class, new BlockHolystone());

		this.aercloud = this.registerBlock("aercloud", ItemBlockVariants.class, new BlockAercloud());
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

			models.registerItemRenderer(this.aether_dirt, BlockAetherDirt.AetherGrassVariant.values());
			models.registerItemRenderer(this.holystone, BlockHolystone.HolystoneVariant.values());
			models.registerItemRenderer(this.aercloud, BlockAercloud.AercloudVariant.values());
		}
	}
}
