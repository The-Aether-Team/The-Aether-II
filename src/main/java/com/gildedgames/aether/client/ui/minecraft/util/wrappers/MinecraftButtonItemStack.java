package com.gildedgames.aether.client.ui.minecraft.util.wrappers;

import com.gildedgames.aether.client.ui.common.GuiFrame;
import com.gildedgames.aether.client.ui.data.rect.Dim2D;
import com.gildedgames.aether.client.ui.data.rect.Rect;
import com.gildedgames.aether.client.ui.graphics.Graphics2D;
import com.gildedgames.aether.client.ui.input.InputProvider;
import net.minecraft.item.ItemStack;

public class MinecraftButtonItemStack extends GuiFrame
{

	protected MinecraftItemStackRender itemStackRender;

	public MinecraftButtonItemStack(ItemStack stack)
	{
		this(Dim2D.build().area(20, 20).flush(), stack);
	}

	public MinecraftButtonItemStack(Rect dim, ItemStack stack)
	{
		super(dim);

		this.itemStackRender = new MinecraftItemStackRender(stack);
	}

	@Override
	public void initContent(InputProvider input)
	{
		super.initContent(input);

		this.content().set("button", new MinecraftButton(Dim2D.build().buildWith(this).area().flush(), ""));
		this.content().set("itemStackRender", this.itemStackRender);

		this.itemStackRender.dim().mod().center(true).x(this.dim().width() / 2).y(this.dim().height() / 2).flush();
	}

	@Override
	public void draw(Graphics2D graphics, InputProvider input)
	{
		super.draw(graphics, input);

		MinecraftButton button = this.content().get("button", MinecraftButton.class);

		button.dim().mod().buildWith(this).area().rebuild().flush();
	}

	public ItemStack getItemStack()
	{
		return this.itemStackRender.getItemStack();
	}

	@Override
	public boolean query(Object... input)
	{
		for (Object obj : input)
		{
			if (obj instanceof String)
			{
				String string = (String) obj;

				if (this.getItemStack().getDisplayName().toLowerCase().contains(string.toLowerCase()))
				{
					return true;
				}
			}
		}

		return false;
	}

}
