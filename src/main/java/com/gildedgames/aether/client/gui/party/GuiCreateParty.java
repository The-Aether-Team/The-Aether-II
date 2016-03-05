package com.gildedgames.aether.client.gui.party;

import com.gildedgames.util.modules.ui.common.GuiFrame;
import com.gildedgames.util.modules.ui.data.rect.Dim2D;
import com.gildedgames.util.modules.ui.input.InputProvider;
import com.gildedgames.util.modules.ui.util.input.GuiInput;
import com.gildedgames.util.modules.ui.util.input.StringInput;

public class GuiCreateParty extends GuiFrame
{
	@Override
	public void initContent(InputProvider input)
	{
		super.initContent(input);

		GuiInput<String> nameInput = new GuiInput<>(new StringInput(), Dim2D.build().pos(100, 100).area(200, 20).flush(), "Party Name");
		this.content().set("name", nameInput);
	}
}
