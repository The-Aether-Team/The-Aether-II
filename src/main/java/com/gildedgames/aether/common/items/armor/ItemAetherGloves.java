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

import java.text.DecimalFormat;
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

		if (getAttackSpeed() > 0)
		{
			tooltip.add(TextFormatting.GRAY + " " + new DecimalFormat("#.#").format(0.2 + getAttackSpeed()) + " " + I18n.format("attribute.name.generic.attackSpeed"));
		}

		if (getSlashDamageLevel() > 0)
		{
			String slashValue;

			if (getSlashDamageLevel() % 1 == 0)
			{
				int n = Math.round(getSlashDamageLevel());
				slashValue = String.valueOf(n);
			}
			else
			{
				float n = getSlashDamageLevel();
				slashValue = String.valueOf(n);
			}

			tooltip.add(String.format(" %s %s",
					slashValue,
					String.format("%s %s",
							TextFormatting.BLUE + I18n.format("attribute.name.aether.slash"),
							TextFormatting.GRAY + I18n.format("attribute.name.aether.damageLevel"))));
		}

		if (getPierceDamageLevel() > 0)
		{
			String pierceValue;

			if (getPierceDamageLevel() % 1 == 0)
			{
				int n = Math.round(getPierceDamageLevel());
				pierceValue = String.valueOf(n);
			}
			else
			{
				float n = getPierceDamageLevel();
				pierceValue = String.valueOf(n);
			}

			tooltip.add(String.format(" %s %s",
					pierceValue,
					String.format("%s %s",
							TextFormatting.RED + I18n.format("attribute.name.aether.pierce"),
							TextFormatting.GRAY + I18n.format("attribute.name.aether.damageLevel"))));
		}

		if (getImpactDamageLevel() > 0)
		{
			String impactValue;

			if (getImpactDamageLevel() % 1 == 0)
			{
				int n = Math.round(getImpactDamageLevel());
				impactValue = String.valueOf(n);
			}
			else
			{
				float n = getImpactDamageLevel();
				impactValue = String.valueOf(n);
			}

			tooltip.add(String.format(" %s %s",
					impactValue,
					String.format("%s %s",
							TextFormatting.YELLOW + I18n.format("attribute.name.aether.impact"),
							TextFormatting.GRAY + I18n.format("attribute.name.aether.damageLevel"))));
		}
	}

	public SoundEvent getGloveSound()
	{
		return this.gloveType.getEquipSound();
	}

	public float getSlashDamageLevel()
	{
		return this.gloveType.getSlashDamageLevel();
	}

	public float getPierceDamageLevel()
	{
		return this.gloveType.getPierceDamageLevel();
	}

	public float getImpactDamageLevel()
	{
		return this.gloveType.getImpactDamageLevel();
	}

	public double getAttackSpeed()
	{
		return this.gloveType.getAttackSpeed();
	}

	public enum GloveType
	{
		TAEGOREHIDE("taegore_hide_gloves", SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0, 0, 4, 1.4D),
		BURRUKAIPELT("burrukai_pelt_gloves", SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0, 0, 5, 1.4D),
		ZANITE("zanite_gloves", SoundEvents.ITEM_ARMOR_EQUIP_IRON, 0, 0, 6, 1.4D),
		ARKENIUM("arkenium_gloves", SoundEvents.ITEM_ARMOR_EQUIP_IRON, 0, 0, 8, 0.8D),
		GRAVITITE("gravitite_gloves", SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 0, 0, 7, 1.4D),
		VALKYRIE("valkyrie_gloves", SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 0, 0, 0, 1.4D),
		NEPTUNE("neptune_gloves", SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 0, 0, 0, 1.4D),
		PHOENIX("phoenix_gloves", SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 0, 0, 0, 1.4D),
		OBSIDIAN("obsidian_gloves", SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 0, 0, 0, 1.4D);

		private final ResourceLocation texture, textureSlim;

		private final SoundEvent equipSound;

		private final float slashDamageLevel, pierceDamageLevel, impactDamageLevel;

		private final double attackSpeed;

		GloveType(String texture, SoundEvent equipSound, float slashDamageLevel, float pierceDamageLevel, float impactDamageLevel, double attackSpeed)
		{
			this.texture = AetherCore.getResource("textures/armor/" + texture + ".png");
			this.textureSlim = AetherCore.getResource("textures/armor/" + texture + "_slim.png");
			this.equipSound = equipSound;
			this.slashDamageLevel = slashDamageLevel;
			this.pierceDamageLevel = pierceDamageLevel;
			this.impactDamageLevel = impactDamageLevel;
			this.attackSpeed = attackSpeed;
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

		public float getSlashDamageLevel()
		{
			return this.slashDamageLevel;
		}

		public float getPierceDamageLevel()
		{
			return this.pierceDamageLevel;
		}

		public float getImpactDamageLevel()
		{
			return this.impactDamageLevel;
		}

		public double getAttackSpeed()
		{
			return this.attackSpeed;
		}
	}
}
