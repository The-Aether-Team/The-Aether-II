package com.gildedgames.aether.client.ui.common;

import com.gildedgames.aether.client.ui.data.TickInfo;
import com.gildedgames.aether.client.ui.graphics.Graphics2D;
import com.gildedgames.aether.client.ui.input.InputProvider;

public interface GuiViewer
{

	void open(GuiFrame frame);

	void close(GuiFrame frame);

	InputProvider getInputProvider();

	TickInfo getTickInfo();

	Graphics2D getGraphics();

}
