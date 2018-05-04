package com.gildedgames.aether.common.items.armor;

import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.capabilities.entity.player.PlayerAether;
import com.gildedgames.aether.common.patron.armor.PatronRewardArmor;
import com.gildedgames.aether.common.registry.content.CreativeTabsAether;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;

public class ItemAetherArmor extends ItemArmor
{
	public static String PATRON_TEXTURE_TEMP_OVERRIDE;

	public static boolean RENDER_NORMAL_TEMP;

	private final String name;

	public ItemAetherArmor(final ArmorMaterial material, final String name, final EntityEquipmentSlot armorType)
	{
		super(material, 0, armorType);

		this.name = name;

		this.setCreativeTab(CreativeTabsAether.ARMOR);
	}

	@Override
	public boolean getIsRepairable(final ItemStack target, final ItemStack stack)
	{
		return false;
	}

	protected boolean isAbilityPassive()
	{
		return true;
	}

	public float getExtraDamageReduction(final ItemStack stack)
	{
		return 0.0f;
	}

	@Override
	public String getArmorTexture(final ItemStack stack, final Entity entity, final EntityEquipmentSlot slot, final String type)
	{
		if (PATRON_TEXTURE_TEMP_OVERRIDE != null && entity.world.isRemote && entity == Minecraft.getMinecraft().player)
		{
			return AetherCore
					.getResourcePath("textures/armor/" + PATRON_TEXTURE_TEMP_OVERRIDE + "_layer_" + (slot == EntityEquipmentSlot.LEGS ? 2 : 1) + ".png");
		}

		if (!RENDER_NORMAL_TEMP)
		{
			PlayerAether playerAether = PlayerAether.getPlayer(entity);

			if (playerAether != null)
			{
				PatronRewardArmor armorChoice = playerAether.getPatronRewardsModule().getChoices().getArmorChoice();

				if (armorChoice != null)
				{
					String patronTexture = armorChoice.getArmorTextureName();

					return AetherCore.getResourcePath("textures/armor/" + patronTexture + "_layer_" + (slot == EntityEquipmentSlot.LEGS ? 2 : 1) + ".png");
				}
			}
		}

		return AetherCore.getResourcePath("textures/armor/" + this.name + "_layer_" + (slot == EntityEquipmentSlot.LEGS ?
				2 :
				String.valueOf(1) + (AetherCore.CONFIG.isTransparentArmorFace() ? "b" : "")) + ".png");
	}
}
