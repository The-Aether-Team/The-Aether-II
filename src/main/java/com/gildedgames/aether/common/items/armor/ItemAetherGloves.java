package com.gildedgames.aether.common.items.armor;

import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.capabilities.entity.player.PlayerAether;
import com.gildedgames.aether.common.capabilities.entity.player.modules.PlayerPatronRewardModule;
import com.gildedgames.aether.common.init.CreativeTabsAether;
import com.gildedgames.aether.common.patron.armor.PatronRewardArmor;
import com.gildedgames.aether.common.util.helpers.EntityUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemAetherGloves extends Item
{
	private final GloveType gloveType;

	public ItemAetherGloves(GloveType type)
	{
		this.gloveType = type;

		this.setMaxStackSize(1);

		this.setCreativeTab(CreativeTabsAether.TAB_ARMOR);
	}

	@SideOnly(Side.CLIENT)
	public ResourceLocation getGloveTexture(EntityPlayer player)
	{
		String skinType = EntityUtil.getSkin(player);

		boolean slim = skinType.equals("slim");

		PlayerAether playerAether = PlayerAether.getPlayer(player);

		PatronRewardArmor armorChoice = playerAether.getModule(PlayerPatronRewardModule.class).getChoices().getArmorChoice();

		if (armorChoice != null)
		{
			return armorChoice.getArmorGloveTexture(slim);
		}

		return slim ? this.gloveType.getTextureSlim() : this.gloveType.getTexture();
	}

	public SoundEvent getGloveSound()
	{
		return this.gloveType.getEquipSound();
	}

	public enum GloveType
	{
		TAEGOREHIDE("taegore_hide_gloves", SoundEvents.ITEM_ARMOR_EQUIP_LEATHER),
		BURRUKAIPELT("burrukai_pelt_gloves", SoundEvents.ITEM_ARMOR_EQUIP_LEATHER),
		ZANITE("zanite_gloves", SoundEvents.ITEM_ARMOR_EQUIP_IRON),
		ARKENIUM("arkenium_gloves", SoundEvents.ITEM_ARMOR_EQUIP_IRON),
		GRAVITITE("gravitite_gloves", SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND),
		VALKYRIE("valkyrie_gloves", SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND),
		NEPTUNE("neptune_gloves", SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND),
		PHOENIX("phoenix_gloves", SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND),
		OBSIDIAN("obsidian_gloves", SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND);

		private final ResourceLocation texture, textureSlim;

		private final SoundEvent equipSound;

		GloveType(String texture, SoundEvent equipSound)
		{
			this.texture = AetherCore.getResource("textures/armor/" + texture + ".png");
			this.textureSlim = AetherCore.getResource("textures/armor/" + texture + "_slim.png");
			this.equipSound = equipSound;
		}

		public ResourceLocation getTextureSlim()
		{
			return this.textureSlim;
		}

		public ResourceLocation getTexture()
		{
			return this.texture;
		}

		public SoundEvent getEquipSound()
		{
			return this.equipSound;
		}
	}
}
