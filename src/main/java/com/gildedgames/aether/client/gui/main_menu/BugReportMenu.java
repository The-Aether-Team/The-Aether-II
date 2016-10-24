package com.gildedgames.aether.client.gui.main_menu;

import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.util.core.gui.util.GuiFactory;
import com.gildedgames.util.modules.ui.UiModule;
import com.gildedgames.util.modules.ui.common.GuiFrame;
import com.gildedgames.util.modules.ui.data.rect.Dim2D;
import com.gildedgames.util.modules.ui.event.view.MouseEventGui;
import com.gildedgames.util.modules.ui.graphics.Graphics2D;
import com.gildedgames.util.modules.ui.input.*;
import com.gildedgames.util.modules.ui.util.InputHelper;
import com.gildedgames.util.modules.ui.util.Text;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.text.TextFormatting;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class BugReportMenu extends GuiFrame
{

	public BugReportMenu()
	{
		this.dim().mod().area(28, 28).flush();
	}

	@Override
	public void initContent(InputProvider input)
	{
		super.initContent(input);

		GuiFrame button = GuiFactory.button(InputHelper.getCenter(input).clone().addY(52).flush(), 80, I18n.format("bug_report.button"), true);

		button.events().set("press", new MouseEventGui(new MouseInput(MouseButton.LEFT, ButtonState.PRESS))
		{

			@Override
			protected void onTrue(InputProvider input, MouseInputPool pool)
			{
				try
				{
					BugReportMenu.this.openWebLink(new URI("https://git.gildedgames.com/GildedGames/Aether-II/issues"));
				}
				catch (URISyntaxException e)
				{
					e.printStackTrace();
				}
			}

			@Override
			protected void onFalse(InputProvider input, MouseInputPool pool)
			{

			}

			@Override
			public void initEvent()
			{

			}

		});

		this.content().set("button", button);

		Text[] texts = new Text[5];

		texts[0] = GuiFactory.text(TextFormatting.BOLD + I18n.format("bug_report.line1"), Color.WHITE);
		texts[1] = GuiFactory.text(" ", Color.WHITE);
		texts[2] = GuiFactory.text(I18n.format("bug_report.line2"), Color.LIGHT_GRAY);
		texts[3] = GuiFactory.text(" ", Color.WHITE);
		texts[4] = GuiFactory.text(I18n.format("bug_report.line3"), Color.LIGHT_GRAY);

		GuiFrame text = GuiFactory.textBox(Dim2D.build().area(350, 200).pos(InputHelper.getCenter(input).clone().addY(-8).flush()).center(true).flush(), false, texts);

		this.content().set("text", text);
	}

	@Override
	public void draw(Graphics2D graphics, InputProvider input)
	{
		super.draw(graphics, input);
	}

	private void openWebLink(URI url)
	{
		try
		{
			Class<?> oclass = Class.forName("java.awt.Desktop");
			Object object = oclass.getMethod("getDesktop", new Class[0]).invoke((Object)null, new Object[0]);
			oclass.getMethod("browse", new Class[] {URI.class}).invoke(object, new Object[] {url});
		}
		catch (Throwable throwable1)
		{
			Throwable throwable = throwable1.getCause();
			AetherCore.LOGGER.error("Couldn\'t open link: {}", new Object[] {throwable == null ? "<UNKNOWN>" : throwable.getMessage()});
		}
	}

}
