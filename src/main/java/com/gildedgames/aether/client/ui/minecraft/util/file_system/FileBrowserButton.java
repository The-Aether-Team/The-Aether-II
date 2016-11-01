package com.gildedgames.aether.client.ui.minecraft.util.file_system;

import com.gildedgames.aether.client.ui.common.GuiFrame;
import com.gildedgames.aether.client.ui.data.rect.Dim2D;
import com.gildedgames.aether.client.ui.data.rect.Rect;
import com.gildedgames.aether.client.ui.event.view.MouseEventGui;
import com.gildedgames.aether.client.ui.input.*;
import com.gildedgames.aether.client.ui.minecraft.util.GuiFactory;
import com.gildedgames.aether.client.ui.util.*;
import com.gildedgames.aether.client.ui.util.Button;

import java.awt.*;
import java.io.File;

public class FileBrowserButton extends GuiFrame
{

	private final TextureElement texture;

	private final File file;

	private final String directory, name;

	public FileBrowserButton(Rect dim, TextureElement texture, String name, File file, String directory)
	{
		super(dim.rebuild().area(16, 23).flush());

		this.texture = texture;
		this.file = file;
		this.directory = directory;
		this.name = name;
	}

	@Override
	public void initContent(InputProvider input)
	{
		this.content().displayDim(this);

		final Button button = new Button(Dim2D.build().width(31).height(31).scale(0.5f).flush(), this.texture);

		button.events().set("pushDown", new MouseEventGui(new MouseInput(MouseButton.LEFT, ButtonState.PRESS))
		{
			@Override
			protected void onTrue(InputProvider inputM, MouseInputPool pool)
			{
				button.dim().mod().pos(1, 1).flush();
			}

			@Override
			protected void onFalse(InputProvider inputM, MouseInputPool pool)
			{
				button.dim().mod().pos(0, 0).flush();
			}

			@Override
			public void initEvent()
			{

			}

		});

		this.content().set("button", button);

		GuiFrame textBox = GuiFactory.centeredTextBox(Dim2D.build().pos(this.dim().width() / 2, this.dim().height() / 2 + 12).centerX(true).width(16).height(12).flush(), false, GuiFactory.text(this.name, Color.WHITE, 0.5f));

		this.content().set("text", textBox);

		super.initContent(input);
	}
}
