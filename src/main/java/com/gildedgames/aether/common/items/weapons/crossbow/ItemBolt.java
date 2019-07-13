package com.gildedgames.aether.common.items.weapons.crossbow;

import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.api.distmarker.Dist;

import java.util.List;

public class ItemBolt extends Item
{
	private final ItemBoltType type;

	public ItemBolt(ItemBoltType type, Properties properties)
	{
		super(properties);

		this.type = type;
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public void addInformation(final ItemStack stack, final World world, final List<ITextComponent> tooltip, final ITooltipFlag flag)
	{
		final int slashDamageLevel = this.type.getSlashDamageLevel();
		final int pierceDamageLevel = this.type.getPierceDamageLevel();
		final int impactDamageLevel = this.type.getImpactDamageLevel();

		if (slashDamageLevel > 0)
		{
			this.addDamageLevel("slash", slashDamageLevel, tooltip);
		}

		if (pierceDamageLevel > 0)
		{
			this.addDamageLevel("pierce", pierceDamageLevel, tooltip);
		}

		if (impactDamageLevel > 0)
		{
			this.addDamageLevel("impact", impactDamageLevel, tooltip);
		}
	}

	private void addDamageLevel(String damageType, int damageLevel, final List<ITextComponent> tooltip)
	{
		tooltip.add(new StringTextComponent(TextFormatting.GRAY + String.valueOf(damageLevel) + " " + I18n.format("item.aether.bolt." + damageType)));
	}
}
