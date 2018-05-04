package com.gildedgames.aether.common.items.armor;

import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.capabilities.entity.player.PlayerAether;
import com.gildedgames.aether.common.patron.armor.PatronRewardArmor;
import com.gildedgames.aether.common.registry.content.CreativeTabsAether;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemAetherGloves extends Item
{
	private final GloveType gloveType;

	public ItemAetherGloves(GloveType type)
	{
		this.gloveType = type;

		this.setMaxStackSize(1);

		this.setCreativeTab(CreativeTabsAether.ARMOR);
	}

	@SideOnly(Side.CLIENT)
	public ResourceLocation getGloveTexture(EntityPlayer player)
	{
		if (ItemAetherArmor.PATRON_TEXTURE_TEMP_OVERRIDE != null && player == Minecraft.getMinecraft().player)
		{
			return AetherCore
					.getResource("textures/armor/" + ItemAetherArmor.PATRON_TEXTURE_TEMP_OVERRIDE + "_gloves.png");
		}

		PlayerAether playerAether = PlayerAether.getPlayer(player);

		if (playerAether != null)
		{
			PatronRewardArmor armorChoice = playerAether.getPatronRewardsModule().getChoices().getArmorChoice();

			if (armorChoice != null)
			{
				return armorChoice.getArmorGloveTexture();
			}
		}

		return this.gloveType.getGloveTexture();
	}

	public enum GloveType
	{
		TAEGOREHIDE("aether:textures/armor/taegore_hide_gloves.png"),
		ZANITE("aether:textures/armor/zanite_gloves.png"),
		ARKENIUM("aether:textures/armor/arkenium_gloves.png"),
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
}
