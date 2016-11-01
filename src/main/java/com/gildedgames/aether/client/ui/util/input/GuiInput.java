package com.gildedgames.aether.client.ui.util.input;

import com.gildedgames.aether.client.ui.common.Gui;
import com.gildedgames.aether.client.ui.common.GuiFrame;
import com.gildedgames.aether.client.ui.data.AssetLocation;
import com.gildedgames.aether.client.ui.data.rect.Dim2D;
import com.gildedgames.aether.client.ui.data.rect.Rect;
import com.gildedgames.aether.client.ui.data.rect.RectModifier.ModifierType;
import com.gildedgames.aether.client.ui.event.GuiEvent;
import com.gildedgames.aether.client.ui.event.view.MouseEventGui;
import com.gildedgames.aether.client.ui.graphics.Sprite;
import com.gildedgames.aether.client.ui.input.*;
import com.gildedgames.aether.client.ui.minecraft.util.GuiFactory;
import com.gildedgames.aether.client.ui.minecraft.util.MinecraftAssetLocation;
import com.gildedgames.aether.client.ui.util.TextElement;
import com.gildedgames.aether.common.AetherCore;
import net.minecraft.util.ChatAllowedCharacters;
import org.lwjgl.input.Keyboard;

import java.awt.*;

public class GuiInput<T> extends GuiFrame
{

	private DataInput<T> data;

	private final AssetLocation texture = new MinecraftAssetLocation(AetherCore.MOD_ID, "textures/gui/test/inputBox.png");

	private final AssetLocation cursorTexture = new MinecraftAssetLocation(AetherCore.MOD_ID, "textures/gui/test/inputCursor.png");

	private boolean isClicked;

	private TextElement input, text;

	private String title, initContent;

	private int textIndex, cursorIndex;

	public GuiInput(DataInput<T> data, Rect rect, String title)
	{
		this(data, rect, title, "");
	}

	public GuiInput(DataInput<T> data, Rect rect, String title, String initContent)
	{
		this.data = data;
		this.data.setData(this.data.parse(initContent));
		this.dim().mod().set(rect).flush();

		this.title = title;
		this.initContent = initContent;
	}

	@Override
	public void initContent(InputProvider input)
	{
		super.initContent(input);

		this.text = new TextElement(GuiFactory.text(this.title, Color.WHITE), Dim2D.build().x(this.dim().width() / 2).centerX(true).flush());
		GuiFrame inputBox = GuiFactory.createResizableTexture(this.texture, this.dim().clone().clear(ModifierType.POS).mod().pos(0, this.text.dim().height()).flush(), Sprite.UV.build().area(1, 1).flush(), Sprite.UV.build().area(1, 18).flush(), Sprite.UV.build().area(198, 1).flush());
		this.input = new TextElement(GuiFactory.text(this.initContent, Color.WHITE), Dim2D.build().pos(2, this.text.dim().height() + inputBox.dim().height() / 2).centerY(true).flush());

		final GuiFrame cursor = GuiFactory.createResizableTexture(this.cursorTexture, Dim2D.build().width(1).height(this.text.getText().font().getHeight("h")).flush(), Sprite.UV.build().area(1, 1).flush(), Sprite.UV.build().area(1, 1).flush(), Sprite.UV.build().area(1, 1).flush());

		cursor.dim().mod().y(13).x(2).flush();

		cursor.setVisible(false);

		this.input.events().set("onTyping", new GuiEvent<Gui>()
		{

			@Override
			public boolean onKeyboardInput(KeyboardInputPool pool, InputProvider input)
			{
				boolean flag = GuiInput.this.isClicked;

				if (GuiInput.this.isClicked && pool.has(ButtonState.PRESS))
				{
					TextElement text = GuiInput.this.input;

					if (pool.has(Keyboard.KEY_BACK) && text.getData().length() > 0)
					{
						text.setData(text.getData().substring(0, text.getData().length() - 1));
						GuiInput.this.data.setData(GuiInput.this.data.parse(text.getData()));
					}

					for (KeyboardInput key : pool)
					{
						if (ChatAllowedCharacters.isAllowedCharacter(key.getChar()))
						{
							String keyString = String.valueOf(key.getChar());
							String newString = text.getData().concat(keyString);

							if (GuiInput.this.data.validString(newString))
							{
								GuiInput.this.data.setData(GuiInput.this.data.parse(newString));
								text.setData(newString);
							}
						}
					}

					cursor.dim().mod().x(text.getText().font().getWidth(text.getData()) + 2).flush();
				}

				super.onKeyboardInput(pool, input);

				return flag;
			}

			@Override
			public void initEvent()
			{

			}

		});

		inputBox.events().set("clicking", new MouseEventGui(new MouseInput(MouseButton.LEFT, ButtonState.PRESS))
		{

			@Override
			protected void onTrue(InputProvider input, MouseInputPool pool)
			{
				GuiInput.this.isClicked = true;
				cursor.setVisible(true);
			}

			@Override
			protected void onFalse(InputProvider input, MouseInputPool pool)
			{
				if (!input.isHovered(this.getGui().dim()))
				{
					GuiInput.this.isClicked = false;
					cursor.setVisible(false);
				}
			}

			@Override
			public void initEvent()
			{

			}

		});

		this.content().set("inputBox", inputBox);

		this.content().set("text", this.text);
		this.content().set("input", this.input);

		this.content().set("cursor", cursor);

		this.dim().mod().addHeight(this.text.dim().height()).flush();
	}

	public T getData()
	{
		return this.data.getData();
	}

}
