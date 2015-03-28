package net.aetherteam.aether.blocks;

import net.aetherteam.aether.Aether;
import net.aetherteam.aether.blocks.natural.BlockAetherDirt;
import net.aetherteam.aether.blocks.natural.BlockHolystone;
import net.aetherteam.aether.client.models.ModelsAether;
import net.aetherteam.aether.items.itemblocks.ItemBlockSubtypes;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class BlocksAether
{
	public BlockAetherDirt aether_dirt;

	public BlockHolystone holystone;

	public void preInit()
	{
		this.aether_dirt = this.registerBlock("aether_dirt", new BlockAetherDirt());

		this.holystone = this.registerBlock("holystone", ItemBlockSubtypes.class, new BlockHolystone());
	}

	private <T extends Block> T registerBlock(String name, Class<? extends ItemBlock> itemblock, T block)
	{
		block.setUnlocalizedName(name);
		GameRegistry.registerBlock(block, itemblock, name);

		return block;
	}

	private <T extends Block> T registerBlock(String name, T block)
	{
		block.setUnlocalizedName(name);
		GameRegistry.registerBlock(block, name);

		return block;
	}

	public void init()
	{
		if (Aether.PROXY.getModels() != null)
		{
			ModelsAether models = Aether.PROXY.getModels();

			models.registerItemRenderer(this.aether_dirt, 0, "aether_dirt", "inventory");
			models.registerItemRenderer(this.holystone, BlockHolystone.HolystoneVariant.NORMAL.getMetadata(), "holystone", "inventory");
			models.registerItemRenderer(this.holystone, BlockHolystone.HolystoneVariant.MOSSY.getMetadata(), "holystone", "inventory");
			models.registerItemRenderer(this.holystone, BlockHolystone.HolystoneVariant.BLOOD.getMetadata(), "holystone", "inventory");
		}
	}
}
