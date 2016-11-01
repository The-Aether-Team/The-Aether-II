package com.gildedgames.aether.client.ui.common;

import com.gildedgames.aether.client.ui.data.TickInfo;
import com.gildedgames.aether.client.ui.data.UIContainer;
import com.gildedgames.aether.client.ui.data.UIContainerEvents;
import com.gildedgames.aether.client.ui.data.UIContainerMutable;
import com.gildedgames.aether.client.ui.data.rect.ModDim2D;
import com.gildedgames.aether.client.ui.data.rect.RectHolder;
import com.gildedgames.aether.client.ui.graphics.Graphics2D;
import com.gildedgames.aether.client.ui.input.InputProvider;
import com.gildedgames.aether.client.ui.input.KeyboardInputPool;
import com.gildedgames.aether.client.ui.input.MouseInputPool;
import com.gildedgames.aether.client.ui.listeners.KeyboardListener;
import com.gildedgames.aether.client.ui.listeners.MouseListener;
import com.gildedgames.aether.client.ui.util.GuiProcessingHelper;
import com.gildedgames.aether.common.util.ObjectFilter;
import net.minecraft.nbt.NBTTagCompound;

import java.util.List;

public abstract class GuiDecorator<T extends Ui> extends GuiFrame implements Decorator<T>
{

	private T element;

	public GuiDecorator(T element)
	{
		this.element = element;
	}

	@Override
	public T getDecoratedElement()
	{
		return this.element;
	}

	public <D> D findDecoratedElement(Class<? extends D> clazz)
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
				Decorator decorator = (Decorator) element;

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
	public void draw(Graphics2D graphics, InputProvider input)
	{
		Gui view = ObjectFilter.cast(this.element, Gui.class);

		if (view != null)
		{
			view.draw(graphics, input);
		}
	}

	@Override
	public boolean isVisible()
	{
		Gui view = ObjectFilter.cast(this.element, Gui.class);

		if (view != null)
		{
			return view.isVisible();
		}

		return false;
	}

	@Override
	public void setVisible(boolean visible)
	{
		Gui view = ObjectFilter.cast(this.element, Gui.class);

		if (view != null)
		{
			view.setVisible(visible);
		}
	}

	@Override
	public ModDim2D dim()
	{
		RectHolder holder = ObjectFilter.cast(this.element, RectHolder.class);

		if (holder != null)
		{
			return holder.dim();
		}

		return null;
	}

	@Override
	public void tick(TickInfo tickInfo, InputProvider input)
	{
		this.element.tick(tickInfo, input);
	}

	@Override
	public final void init(InputProvider input)
	{
		this.initContent(input);

		GuiProcessingHelper.processInitPre(this, input, this.content(), this.events());
	}

	@Override
	public final void initContent(InputProvider input)
	{
		this.preInitContent(input);

		this.element.initContent(input);

		this.postInitContent(input);
	}

	@Override
	public final void onClose(InputProvider input)
	{
		this.preClose(input);

		this.element.onClose(input);

		this.postClose(input);
	}

	protected void preClose(InputProvider input)
	{

	}

	protected void postClose(InputProvider input)
	{

	}

	protected abstract void preInitContent(InputProvider input);

	protected abstract void postInitContent(InputProvider input);

	@Override
	public void onResolutionChange(InputProvider input)
	{
		this.init(input);
	}

	@Override
	public boolean isEnabled()
	{
		return this.element.isEnabled();
	}

	@Override
	public void setEnabled(boolean enabled)
	{
		this.element.setEnabled(enabled);
	}

	@Override
	public boolean onKeyboardInput(KeyboardInputPool pool, InputProvider input)
	{
		KeyboardListener listener = ObjectFilter.cast(this.element, KeyboardListener.class);

		if (listener != null)
		{
			return listener.onKeyboardInput(pool, input);
		}

		return false;
	}

	@Override
	public void onMouseInput(MouseInputPool pool, InputProvider input)
	{
		MouseListener listener = ObjectFilter.cast(this.element, MouseListener.class);

		if (listener != null)
		{
			listener.onMouseInput(pool, input);
		}
	}

	@Override
	public void onMouseScroll(int scrollDifference, InputProvider input)
	{
		MouseListener listener = ObjectFilter.cast(this.element, MouseListener.class);

		if (listener != null)
		{
			listener.onMouseScroll(scrollDifference, input);
		}
	}

	@Override
	public boolean isFocused()
	{
		Gui view = ObjectFilter.cast(this.element, Gui.class);

		if (view != null)
		{
			return view.isFocused();
		}

		return false;
	}

	@Override
	public void setFocused(boolean focused)
	{
		Gui view = ObjectFilter.cast(this.element, Gui.class);

		if (view != null)
		{
			view.setFocused(focused);
		}
	}

	@Override
	public boolean query(Object... input)
	{
		Gui view = ObjectFilter.cast(this.element, Gui.class);

		if (view != null)
		{
			return view.query(input);
		}

		return false;
	}

	@Override
	public UIContainer seekContent()
	{
		return this.element.seekContent();
	}

	@Override
	public UIContainerMutable content()
	{
		GuiFrame frame = ObjectFilter.cast(this.element, GuiFrame.class);

		if (frame != null)
		{
			return frame.content();
		}

		return null;
	}

	@Override
	public UIContainerEvents events()
	{
		return this.element.events();
	}

	@Override
	public List<UIContainer> seekAllContent()
	{
		return this.element.seekAllContent();
	}

	@Override
	public void write(NBTTagCompound output)
	{
		this.element.write(output);
	}

	@Override
	public void read(NBTTagCompound input)
	{
		this.element.read(input);
	}

}
