package com.gildedgames.aether.api.shop;

import com.gildedgames.orbis_api.client.gui.util.gui_library.IGuiElement;

public interface IGuiCurrencyValue extends IGuiElement
{
	void setCurrencyValue(double value);

	void setNonFilteredCurrencyValue(double value);
}
