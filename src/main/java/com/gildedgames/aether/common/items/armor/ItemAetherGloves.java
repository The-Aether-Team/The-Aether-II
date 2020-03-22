package com.gildedgames.aether.common.items.armor;

import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.capabilities.entity.player.PlayerAether;
import com.gildedgames.aether.common.capabilities.entity.player.modules.PlayerPatronRewardModule;
import com.gildedgames.aether.common.init.CreativeTabsAether;
import com.gildedgames.aether.common.patron.armor.PatronRewardArmor;
import com.gildedgames.aether.common.util.helpers.EntityUtil;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class ItemAetherGloves extends Item
{
	public final GloveType gloveType;

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

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(final ItemStack stack, final World world, final List<String> tooltip, final ITooltipFlag flag)
	{
		tooltip.add("");

		tooltip.add(TextFormatting.GRAY + I18n.format("item.aether.tooltip.modifiers.gloves"));

		if (getSlashDamageLevel() > 0)
		{
			tooltip.add(TextFormatting.BLUE + "+" + getSlashDamageLevel() + " " + I18n.format("attribute.name.aether.slashDamageLevel"));
		}
		if (getPierceDamageLevel() > 0)
		{
			tooltip.add(TextFormatting.BLUE + "+" + getPierceDamageLevel() + " " + I18n.format("attribute.name.aether.pierceDamageLevel"));
		}
		if (getImpactDamageLevel() > 0)
		{
			tooltip.add(TextFormatting.BLUE + "+" + getImpactDamageLevel() + " " + I18n.format("attribute.name.aether.impactDamageLevel"));
		}
	}

	public SoundEvent getGloveSound()
	{
		return this.gloveType.getEquipSound();
	}

	public int getSlashDamageLevel()
	{
		return this.gloveType.getSlashDamageLevel();
	}

	public int getPierceDamageLevel()
	{
		return this.gloveType.getPierceDamageLevel();
	}

	public int getImpactDamageLevel()
	{
		return this.gloveType.getImpactDamageLevel();
	}

	public enum GloveType
	{
		TAEGOREHIDE("taegore_hide_gloves", SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0, 0, 14),
		BURRUKAIPELT("burrukai_pelt_gloves", SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0, 0, 21),
		ZANITE("zanite_gloves", SoundEvents.ITEM_ARMOR_EQUIP_IRON, 0, 0, 34),
		ARKENIUM("arkenium_gloves", SoundEvents.ITEM_ARMOR_EQUIP_IRON, 0, 0, 40),
		GRAVITITE("gravitite_gloves", SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 0, 0, 42),
		VALKYRIE("valkyrie_gloves", SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 0, 0, 0),
		NEPTUNE("neptune_gloves", SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 0, 0, 0),
		PHOENIX("phoenix_gloves", SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 0, 0, 0),
		OBSIDIAN("obsidian_gloves", SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 0, 0, 0);

		private final ResourceLocation texture, textureSlim;

		private final SoundEvent equipSound;

		private final int slashDamageLevel, pierceDamageLevel, impactDamageLevel;

		GloveType(String texture, SoundEvent equipSound, int slashDamageLevel, int pierceDamageLevel, int impactDamageLevel)
		{
			this.texture = AetherCore.getResource("textures/armor/" + texture + ".png");
			this.textureSlim = AetherCore.getResource("textures/armor/" + texture + "_slim.png");
			this.equipSound = equipSound;
			this.slashDamageLevel = slashDamageLevel;
			this.pierceDamageLevel = pierceDamageLevel;
			this.impactDamageLevel = impactDamageLevel;
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

		public int getSlashDamageLevel()
		{
			return this.slashDamageLevel;
		}

		public int getPierceDamageLevel()
		{
			return this.pierceDamageLevel;
		}

		public int getImpactDamageLevel()
		{
			return this.impactDamageLevel;
		}
	}
}
