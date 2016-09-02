package com.gildedgames.aether.common.items.armor;

import com.gildedgames.aether.common.registry.minecraft.CreativeTabsAether;
import com.gildedgames.aether.common.capabilities.item.properties.IPhoenixChillable;
import com.gildedgames.aether.common.items.ItemsAether;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemAetherGloves extends Item
{
	public enum GloveType
	{
		ZANITE("aether:textures/armor/zanite_gloves.png"),
		GRAVITITE("aether:textures/armor/gravitite_gloves.png"),
		VALKYRIE("aether:textures/armor/valkyrie_gloves.png"),
		NEPTUNE("aether:textures/armor/neptune_gloves.png"),
		PHOENIX("aether:textures/armor/phoenix_gloves.png"),
		OBSIDIAN("aether:textures/armor/obsidian_gloves.png"),
		LEATHER("aether:textures/armor/leather_gloves.png"),
		IRON("aether:textures/armor/iron_gloves.png"),
		GOLD("aether:textures/armor/gold_gloves.png"),
		CHAIN("aether:textures/armor/chain_gloves.png"),
		DIAMOND("aether:textures/armor/diamond_gloves.png");

		private final ResourceLocation resource;

		GloveType(String texture)
		{
			this.resource = new ResourceLocation(texture);
		}

		public ResourceLocation getGloveTexture()
		{
			return this.resource;
		}
	}

	private final GloveType gloveType;

	public ItemAetherGloves(GloveType type)
	{
		this.gloveType = type;

		this.setMaxStackSize(1);

		this.setCreativeTab(CreativeTabsAether.tabArmor);
	}

	@SideOnly(Side.CLIENT)
	public ResourceLocation getGloveTexture(int layer)
	{
		return this.gloveType.getGloveTexture();
	}
}
