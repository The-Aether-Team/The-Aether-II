package com.gildedgames.orbis.client.gui.util;

import com.gildedgames.aether.api.orbis_core.util.Decorator;
import com.gildedgames.orbis.client.util.rect.ModDim2D;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.Slot;

import java.io.IOException;

public abstract class GuiDecorator<T extends GuiFrame> extends GuiFrame implements Decorator<T>
{

	private final T element;

	public GuiDecorator(final T element)
	{
		this.element = element;
	}

	@Override
	public T getDecoratedElement()
	{
		return this.element;
	}

	public <D> D findDecoratedElement(final Class<? extends D> clazz)
	{
		Object element = this.getDecoratedElement();

		while (element != null)
		{
			if (element.getClass().isAssignableFrom(clazz))
			{
				return (D) element;
			}

			if (element instanceof Decorator)
			{
				final Decorator decorator = (Decorator) element;

				element = decorator.getDecoratedElement();

				if (element == null)
				{
					break;
				}
			}
			else
			{
				break;
			}
		}

		return null;
	}

	@Override
	public void setDrawDefaultBackground(final boolean flag)
	{
		this.element.setDrawDefaultBackground(flag);
	}

	@Override
	public void init()
	{
		this.element.init();
	}

	@Override
	public ModDim2D dim()
	{
		return this.element.dim();
	}

	@Override
	public void updateState()
	{
		this.element.updateState();
	}

	@Override
	public void drawDefaultBackground()
	{
		this.element.drawDefaultBackground();
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(final float partialTicks, final int mouseX, final int mouseY)
	{
		this.element.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);
	}

	@Override
	public void addChild(final IGuiFrame element)
	{
		this.element.addChild(element);
	}

	@Override
	public void removeChild(final IGuiFrame gui)
	{
		this.element.removeChild(gui);
	}

	@Override
	public void initGui()
	{
		this.element.initGui();
	}

	@Override
	public void draw()
	{
		this.element.draw();
	}

	@Override
	public void drawScreen(final int mouseX, final int mouseY, final float partialTicks)
	{
		this.preDraw();

		this.element.drawScreen(mouseX, mouseY, partialTicks);

		this.postDraw();
	}

	@Override
	protected void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) throws IOException
	{
		this.element.mouseClicked(mouseX, mouseY, mouseButton);
	}

	@Override
	protected void mouseClickMove(final int mouseX, final int mouseY, final int clickedMouseButton, final long timeSinceLastClick)
	{
		this.element.mouseClickMove(mouseX, mouseY, clickedMouseButton, timeSinceLastClick);
	}

	@Override
	protected void mouseReleased(final int mouseX, final int mouseY, final int state)
	{
		this.element.mouseReleased(mouseX, mouseY, state);
	}

	@Override
	protected void handleMouseClick(final Slot slotIn, final int slotId, final int mouseButton, final ClickType type)
	{
		this.element.handleMouseClick(slotIn, slotId, mouseButton, type);
	}

	@Override
	protected void keyTyped(final char typedChar, final int keyCode) throws IOException
	{
		this.element.keyTyped(typedChar, keyCode);
	}

	@Override
	public void onGuiClosed()
	{
		this.element.onGuiClosed();
	}

	@Override
	public void updateScreen()
	{
		this.element.updateScreen();
	}

	@Override
	public void setWorldAndResolution(final Minecraft mc, final int width, final int height)
	{
		this.element.setWorldAndResolution(mc, width, height);
	}

	@Override
	protected void actionPerformed(final GuiButton button) throws IOException
	{
		this.element.actionPerformed(button);
	}

	@Override
	public void onResize(final Minecraft mcIn, final int w, final int h)
	{
		this.element.onResize(mcIn, w, h);
	}

	protected void postDraw()
	{

	}

}
