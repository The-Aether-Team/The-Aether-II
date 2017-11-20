package com.gildedgames.orbis.client.gui;

import com.gildedgames.orbis.client.gui.util.GuiTexture;
import com.gildedgames.orbis.common.player.PlayerOrbisModule;
import com.gildedgames.orbis.common.player.godmode.selection_input.ISelectionInput;

public class GuiChoiceMenuSelectionInputs extends GuiChoiceMenu
{

	public GuiChoiceMenuSelectionInputs(final PlayerOrbisModule module)
	{
		super();

		this.choices = new Choice[module.selectionInputs().array().length];

		for (int i = 0; i < module.selectionInputs().array().length; i++)
		{
			final ISelectionInput selectionInput = module.selectionInputs().array()[i];
			final Choice choice = new SelectionInputChoice(selectionInput, selectionInput.getClient().displayName());

			this.choices[i] = choice;
		}
	}

	public class SelectionInputChoice implements Choice
	{
		private final String name;

		private final ISelectionInput selectionInput;

		public SelectionInputChoice(final ISelectionInput power, final String name)
		{
			this.selectionInput = power;
			this.name = name;
		}

		@Override
		public void onSelect(final PlayerOrbisModule module)
		{
			module.selectionInputs().setCurrentSelectionInput(module.selectionInputs().getSelectionInputIndex(this.selectionInput));
		}

		@Override
		public GuiTexture getIcon()
		{
			return this.selectionInput.getClient().getIcon();
		}

		@Override
		public String name()
		{
			return this.name;
		}
	}

}
