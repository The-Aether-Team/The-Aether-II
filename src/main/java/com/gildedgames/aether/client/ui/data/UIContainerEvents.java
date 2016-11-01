package com.gildedgames.aether.client.ui.data;

import com.gildedgames.aether.client.ui.common.Gui;
import com.gildedgames.aether.client.ui.common.Ui;
import com.gildedgames.aether.client.ui.event.GuiEvent;
import com.gildedgames.aether.client.ui.minecraft.viewing.MinecraftGuiViewer;

public class UIContainerEvents extends UIContainerMutable
{

	public UIContainerEvents(Ui attachedUi)
	{
		super(attachedUi);
	}

	@Override
	public void set(String key, Ui element)
	{
		if (element instanceof GuiEvent && this.getAttachedUi() instanceof Gui)
		{
			GuiEvent event = (GuiEvent) element;
			Gui gui = (Gui) this.getAttachedUi();

			event.setGui(gui);
		}

		element.init(MinecraftGuiViewer.instance().getInputProvider());

		super.set(key, element);
	}

	public void set(String key, Ui element, Gui attachedGui)
	{
		if (element instanceof GuiEvent)
		{
			GuiEvent event = (GuiEvent) element;

			event.setGui(attachedGui);
		}

		element.init(MinecraftGuiViewer.instance().getInputProvider());

		super.set(key, element);
	}

}
