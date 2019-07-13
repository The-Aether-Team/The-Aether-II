package com.gildedgames.aether.common.items.weapons;

import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;

public class ItemDart extends Item
{
	public static final ItemDartType[] ITEM_VARIANTS = new ItemDartType[] { ItemDartType.GOLDEN, ItemDartType.ENCHANTED,
			ItemDartType.POISON };

	public ItemDart(Item.Properties properties)
	{
		super(properties);
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public void addInformation(final ItemStack stack, final World world, final List<ITextComponent> tooltip, final ITooltipFlag flag)
	{
		final ItemDartType type = ITEM_VARIANTS[stack.getDamage()];

		final int slashDamageLevel = type.getSlashDamageLevel();
		final int pierceDamageLevel = type.getPierceDamageLevel();
		final int impactDamageLevel = type.getImpactDamageLevel();

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
