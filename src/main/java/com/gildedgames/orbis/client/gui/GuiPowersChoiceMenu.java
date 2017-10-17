package com.gildedgames.orbis.client.gui;

import com.gildedgames.orbis.client.gui.util.GuiTexture;
import com.gildedgames.orbis.common.player.PlayerOrbisModule;
import com.gildedgames.orbis.common.player.godmode.IGodPower;

public class GuiPowersChoiceMenu extends GuiChoiceMenu
{

	public GuiPowersChoiceMenu(final PlayerOrbisModule module)
	{
		super();

		this.choices = new Choice[module.powers().getPowers().length];

		for (int i = 0; i < module.powers().getPowers().length; i++)
		{
			final IGodPower power = module.powers().getPowers()[i];
			final GuiChoiceMenu.Choice choice = new PowerChoice(power, power.getClientHandler().displayName());

			this.choices[i] = choice;
		}
	}

	public class PowerChoice implements Choice
	{
		private final String name;

		private final IGodPower power;

		public PowerChoice(final IGodPower power, final String name)
		{
			this.power = power;
			this.name = name;
		}

		@Override
		public void onSelect(final PlayerOrbisModule module)
		{
			module.powers().setCurrentPower(this.power.getClass());
		}

		@Override
		public GuiTexture getIcon()
		{
			return this.power.getClientHandler().getIcon();
		}

		@Override
		public String name()
		{
			return this.name;
		}
	}

}
