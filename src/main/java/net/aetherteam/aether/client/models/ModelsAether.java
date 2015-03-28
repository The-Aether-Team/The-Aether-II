package net.aetherteam.aether.client.models;

import net.aetherteam.aether.Aether;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;

public class ModelsAether
{
	private Minecraft mc;

	public ModelsAether()
	{
		this.mc = Minecraft.getMinecraft();
	}

	public void registerItemRenderer(Block block, int metadata, String name, String type)
	{
		this.registerItemRenderer(Item.getItemFromBlock(block), metadata, name, type);
	}

	public void registerItemRenderer(Item item, int metadata, String name, String type)
	{
		String modelName = Aether.MOD_ID + ":" + name;

		this.mc.getRenderItem().getItemModelMesher().register(item, metadata, new ModelResourceLocation(modelName, type));
	}
}
