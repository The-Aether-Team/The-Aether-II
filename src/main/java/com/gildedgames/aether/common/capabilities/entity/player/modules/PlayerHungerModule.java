package com.gildedgames.aether.common.capabilities.entity.player.modules;

import com.gildedgames.aether.common.capabilities.entity.player.PlayerAether;
import com.gildedgames.aether.common.capabilities.entity.player.PlayerAetherModule;
import com.gildedgames.aether.common.init.DimensionsAether;
import net.minecraft.potion.Potion;
import net.minecraft.util.FoodStats;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.api.distmarker.Dist;

public class PlayerHungerModule extends PlayerAetherModule
{

	private int startHunger;

	public PlayerHungerModule(PlayerAether playerAether)
	{
		super(playerAether);
	}

	private void setFoodData(FoodStats foodStats, int foodLevel, float saturationLevel)
	{
		foodStats.addStats(1, (saturationLevel - foodStats.getSaturationLevel()) / 2);
		foodStats.addStats(foodLevel - foodStats.getFoodLevel(), 0);
	}

	private int calcBaselineHunger()
	{
		if (this.getEntity().isPotionActive(Potion.getPotionFromResourceLocation("hunger")))
		{
			return 5;
		}
		else if (this.getEntity().isPotionActive(Potion.getPotionFromResourceLocation("regeneration")))
		{
			return 20;
		}
		else
		{
			return 10;
		}
	}

	@Override
	public void tickStart(TickEvent.PlayerTickEvent event)
	{
		if (this.getEntity().world.provider.getDimensionType() == DimensionsAether.NECROMANCER_TOWER)
		{
			this.setFoodData(this.getEntity().getFoodStats(), this.calcBaselineHunger(), 1);
			this.startHunger = this.getEntity().getFoodStats().getFoodLevel();
		}
	}

	@Override
	public void tickEnd(TickEvent.PlayerTickEvent event)
	{
		if (this.getEntity().world.provider.getDimensionType() == DimensionsAether.NECROMANCER_TOWER)
		{
			if (event.side == Dist.SERVER)
			{
				int foodDiff = this.getEntity().getFoodStats().getFoodLevel() - this.startHunger;

				if (foodDiff > 0)
				{
					this.getEntity().heal(foodDiff * 0.5F);
				}
			}

			this.setFoodData(this.getEntity().getFoodStats(), this.calcBaselineHunger(), 1);
		}
	}
}
