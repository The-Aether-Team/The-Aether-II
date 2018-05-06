package com.gildedgames.aether.common.items.armor;

import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.capabilities.entity.player.PlayerAether;
import com.gildedgames.aether.common.patron.armor.PatronRewardArmor;
import com.gildedgames.aether.common.registry.content.CreativeTabsAether;
import com.gildedgames.aether.common.util.helpers.EntityUtil;
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
		String skinType = EntityUtil.getSkin(player);
		boolean slim = skinType.equals("slim");

		if (ItemAetherArmor.PATRON_TEXTURE_TEMP_OVERRIDE != null && player == Minecraft.getMinecraft().player)
		{
			return AetherCore
					.getResource("textures/armor/" + ItemAetherArmor.PATRON_TEXTURE_TEMP_OVERRIDE + "_gloves" + (slim ? "_slim" : "") + ".png");
		}

		if (!ItemAetherArmor.RENDER_NORMAL_TEMP)
		{
			PlayerAether playerAether = PlayerAether.getPlayer(player);

			if (playerAether != null)
			{
				PatronRewardArmor armorChoice = playerAether.getPatronRewardsModule().getChoices().getArmorChoice();

				if (armorChoice != null)
				{
					return armorChoice.getArmorGloveTexture(slim);
				}
			}
		}

		return slim ? this.gloveType.getTextureSlim() : this.gloveType.getTexture();
	}

	public enum GloveType
	{
		TAEGOREHIDE("taegore_hide_gloves"),
		ZANITE("zanite_gloves"),
		ARKENIUM("arkenium_gloves"),
		GRAVITITE("gravitite_gloves"),
		VALKYRIE("valkyrie_gloves"),
		NEPTUNE("neptune_gloves"),
		PHOENIX("phoenix_gloves"),
		OBSIDIAN("obsidian_gloves");

		private final ResourceLocation texture, textureSlim;

		GloveType(String texture)
		{
			this.texture = AetherCore.getResource("textures/armor/" + texture + ".png");
			this.textureSlim = AetherCore.getResource("textures/armor/" + texture + "_slim.png");
		}

		public ResourceLocation getTextureSlim()
		{
			return this.textureSlim;
		}

		public ResourceLocation getTexture()
		{
			return this.texture;
		}
	}
}
