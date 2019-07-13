package com.gildedgames.aether.common.items.weapons.swords;

import com.gildedgames.aether.api.registrar.ItemsAether;
import com.gildedgames.aether.common.items.ItemAbilityType;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.IItemTier;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;

public class ItemAetherSword extends SwordItem
{
	private final ItemAbilityType abilityType;

	public ItemAetherSword(IItemTier tier, final ItemAbilityType abilityType, int attackDamageIn, float attackSpeedIn, Item.Properties builder)
	{
		super(tier, attackDamageIn, attackSpeedIn, builder);

		this.abilityType = abilityType;
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public void addInformation(final ItemStack stack, final World world, final List<ITextComponent> tooltip, final ITooltipFlag flag)
	{
		if (this.abilityType != ItemAbilityType.NONE)
		{
			tooltip.add(new StringTextComponent(String.format("%s: %s",
					TextFormatting.BLUE + I18n.format("item.aether.tooltip.ability"),
					TextFormatting.WHITE + I18n.format(this.getTranslationKey() + ".ability.desc"))));

			if (!this.abilityType.isPassive())
			{
				tooltip.add(new StringTextComponent(String.format("%s: %s",
						TextFormatting.DARK_AQUA + I18n.format("item.aether.tooltip.use"),
						TextFormatting.WHITE + I18n.format(this.getTranslationKey() + ".use.desc"))));
			}
		}
	}

	@Override
	public int getBurnTime(ItemStack itemStack)
	{
		if (this == ItemsAether.skyroot_sword)
		{
			return 100;
		}

		return super.getBurnTime(itemStack);
	}
}
