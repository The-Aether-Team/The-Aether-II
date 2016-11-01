package com.gildedgames.aether.client.ui.util;

import com.gildedgames.aether.client.ui.common.Gui;
import com.gildedgames.aether.client.ui.common.GuiFrame;
import com.gildedgames.aether.client.ui.common.Ui;
import com.gildedgames.aether.client.ui.data.TickInfo;
import com.gildedgames.aether.client.ui.data.UIContainer;
import com.gildedgames.aether.client.ui.data.rect.RectHolder;
import com.gildedgames.aether.client.ui.graphics.Graphics2D;
import com.gildedgames.aether.client.ui.input.InputProvider;
import com.gildedgames.aether.client.ui.input.KeyboardInputPool;
import com.gildedgames.aether.client.ui.input.MouseInputPool;
import com.gildedgames.aether.client.ui.listeners.KeyboardListener;
import com.gildedgames.aether.client.ui.listeners.MouseListener;
import com.gildedgames.aether.common.util.ObjectFilter;

public final class GuiProcessingHelper
{

	public static void processInitPre(RectHolder parent, InputProvider input, UIContainer... containers)
	{
		for (UIContainer container : containers)
		{
			if (container == null)
			{
				return;
			}

			for (Ui element : container.elements())
			{
				element.init(input);
			}
		}
	}

	public static void processResolutionChange(RectHolder parent, InputProvider input, UIContainer... containers)
	{
		GuiProcessingHelper.processInitPre(parent, input, containers);
	}

	public static void processClose(InputProvider input, UIContainer... containers)
	{
		for (UIContainer container : containers)
		{
			if (container == null)
			{
				return;
			}

			for (Ui element : container.elements())
			{
				if (element.isEnabled())
				{
					element.onClose(input);
				}
			}
		}
	}

	public static void processMouseInput(InputProvider input, MouseInputPool pool, UIContainer... containers)
	{
		for (UIContainer container : containers)
		{
			if (container == null)
			{
				return;
			}

			for (MouseListener element : ObjectFilter.getTypesFrom(container.elements(), MouseListener.class))
			{
				if (element.isEnabled())
				{
					element.onMouseInput(pool, input);
				}
			}
		}
	}

	public static void processMouseScroll(InputProvider input, int scrollDifference, UIContainer... containers)
	{
		for (UIContainer container : containers)
		{
			if (container == null)
			{
				return;
			}

			for (MouseListener element : ObjectFilter.getTypesFrom(container.elements(), MouseListener.class))
			{
				if (element.isEnabled())
				{
					element.onMouseScroll(scrollDifference, input);
				}
			}
		}
	}

	public static boolean processKeyboardInput(KeyboardInputPool pool, InputProvider input, UIContainer... containers)
	{
		boolean success = false;

		for (UIContainer container : containers)
		{
			if (container == null)
			{
				return false;
			}

			for (KeyboardListener element : ObjectFilter.getTypesFrom(container.elements(), KeyboardListener.class))
			{
				if (element.isEnabled())
				{
					GuiFrame frame = ObjectFilter.cast(element, GuiFrame.class);

					success = element.onKeyboardInput(pool, input) || success;
				}
			}
		}

		return success;
	}

	public static void processDraw(Graphics2D graphics, InputProvider input, UIContainer... containers)
	{
		for (UIContainer container : containers)
		{
			if (container == null)
			{
				return;
			}

			for (Gui element : ObjectFilter.getTypesFrom(container.elements(), Gui.class))
			{
				if (element.isVisible())
				{
					element.draw(graphics, input);
				}
			}
		}
	}

	public static void processTick(InputProvider input, TickInfo tickInfo, UIContainer... containers)
	{
		for (UIContainer container : containers)
		{
			if (container == null)
			{
				return;
			}

			for (Ui element : container.elements())
			{
				if (element.isEnabled())
				{
					element.tick(tickInfo, input);
				}
			}
		}
	}

}
