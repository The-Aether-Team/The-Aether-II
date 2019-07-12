package com.gildedgames.aether.common.items.weapons.crossbow;

import com.gildedgames.aether.common.items.IDropOnDeath;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.api.distmarker.Dist;

import java.util.List;

public class ItemBolt extends Item implements IDropOnDeath
{
	private static final ItemBoltType[] ITEM_VARIANTS = new ItemBoltType[] {
			ItemBoltType.SKYROOT,
			ItemBoltType.HOLYSTONE,
			ItemBoltType.SCATTERGLASS,
			ItemBoltType.ZANITE,
			ItemBoltType.ARKENIUM,
			ItemBoltType.GRAVITITE
	};

	public ItemBolt()
	{
		this.setHasSubtypes(true);
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public void addInformation(final ItemStack stack, final World world, final List<String> tooltip, final ITooltipFlag flag)
	{
		final ItemBoltType type = ITEM_VARIANTS[stack.getDamage()];

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

	private void addDamageLevel(String damageType, int damageLevel, final List<String> tooltip)
	{
		tooltip.add(TextFormatting.GRAY + String.valueOf(damageLevel) + " " + I18n.format("item.aether.bolt." + damageType));
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public void getSubItems(final ItemGroup tab, final NonNullList<ItemStack> subItems)
	{
		if (!this.isInCreativeTab(tab))
		{
			return;
		}

		for (final ItemBoltType type : ITEM_VARIANTS)
		{
			subItems.add(new ItemStack(this, 1, type.ordinal()));
		}
	}

	@Override
	public String getTranslationKey(final ItemStack stack)
	{
		return super.getTranslationKey(stack) + "." + ItemBoltType.fromOrdinal(stack.getMetadata()).getID();
	}
}
