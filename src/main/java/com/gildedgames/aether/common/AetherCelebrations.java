package com.gildedgames.aether.common;

import java.time.LocalDateTime;
import java.time.Month;

public class AetherCelebrations
{
	public static boolean isHolidayEvent()
	{
		LocalDateTime time = LocalDateTime.now();

		return (time.getMonth() == Month.DECEMBER && time.getDayOfMonth() >= 20) || (time.getMonth() == Month.JANUARY && time.getDayOfMonth() <= 14);
	}

	public static boolean isHalloweenEvent()
	{
		LocalDateTime time = LocalDateTime.now();

		return time.getMonth() == Month.OCTOBER;
	}
}
