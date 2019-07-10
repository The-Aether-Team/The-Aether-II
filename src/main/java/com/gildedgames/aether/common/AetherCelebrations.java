package com.gildedgames.aether.common;

import com.gildedgames.aether.api.shop.IShopBuy;
import com.gildedgames.aether.api.shop.IShopInstance;
import com.gildedgames.aether.common.entities.characters.EntityEdison;
import com.gildedgames.aether.common.items.consumables.ItemEggnog;

import java.time.LocalDateTime;
import java.time.Month;

public class AetherCelebrations
{
	/**
	 * Added because some old shops were initiated in-game without a shop definition location
	 * serialized to the disk. Meaning new code cannot evaluate whether an old shop is a christmas
	 * shop or not.
	 * @param instance The shop instance.
	 * @return Whether or not it's a christmas shop.
	 */
	private static boolean isChristmasShop(IShopInstance instance)
	{
		for (IShopBuy buy : instance.getStock())
		{
			if (buy.getItemStack().getItem() instanceof ItemEggnog)
			{
				return true;
			}
		}

		return false;
	}

	public static boolean isEdisonNewYearsSale(IShopInstance instance)
	{
		return (instance.getShopDefinitionLocation() == EntityEdison.HOLIDAY_SHOP || isChristmasShop(instance)) && AetherCelebrations.isNewYearsEvent();
	}

	public static boolean isNewYearsEvent()
	{
		LocalDateTime time = LocalDateTime.now();

		return time.getMonth() == Month.JANUARY && time.getDayOfMonth() <= 7;
	}

	public static boolean isChristmasEvent()
	{
		LocalDateTime time = LocalDateTime.now();

		return time.getMonth() == Month.DECEMBER && time.getDayOfMonth() >= 18;
	}

	public static boolean isHalloweenEvent()
	{
		LocalDateTime time = LocalDateTime.now();

		return time.getMonth() == Month.OCTOBER;
	}
}
