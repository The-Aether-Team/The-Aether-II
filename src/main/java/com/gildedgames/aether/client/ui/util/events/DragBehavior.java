package com.gildedgames.aether.client.ui.util.events;

import com.gildedgames.aether.client.ui.common.Gui;
import com.gildedgames.aether.client.ui.data.rect.BuildIntoRectHolder;
import com.gildedgames.aether.client.ui.data.rect.ModDim2D;
import com.gildedgames.aether.client.ui.data.rect.RectModifier;
import com.gildedgames.aether.client.ui.data.rect.RectModifier.ModifierType;
import com.gildedgames.aether.client.ui.event.GuiEvent;
import com.gildedgames.aether.client.ui.graphics.Graphics2D;
import com.gildedgames.aether.client.ui.input.ButtonState;
import com.gildedgames.aether.client.ui.input.InputProvider;
import com.gildedgames.aether.client.ui.input.MouseInputPool;
import com.gildedgames.aether.client.ui.util.GuiCanvas;
import com.gildedgames.aether.client.ui.util.InputHelper;
import com.gildedgames.aether.client.ui.util.InputHelper.InputCondition;
import com.gildedgames.aether.client.ui.util.events.slots.SlotBehavior;
import com.gildedgames.aether.client.ui.util.events.slots.SlotStack;
import com.gildedgames.aether.client.ui.util.events.slots.SlotStackFactory;

import java.util.List;

public class DragBehavior<T> extends GuiEvent<SlotStack<T>>
{

	private List<RectModifier> prevModifiers;

	private int ticksSinceCreation;

	public DragBehavior()
	{

	}

	@Override
	public void initEvent()
	{
		Gui gui = this.getGui();

		ModDim2D dim = gui.dim();

		dim.clear(ModifierType.POS);
	}

	@Override
	public void draw(Graphics2D graphics, InputProvider input)
	{
		super.draw(graphics, input);

		Gui gui = this.getGui();

		ModDim2D dim = gui.dim();

		BuildIntoRectHolder mod = dim.mod();

		mod.center(true).pos(input.getMouseX(), input.getMouseY()).flush();
	}

	@Override
	public void onMouseInput(MouseInputPool pool, InputProvider input)
	{
		final boolean hoveringSlot = InputHelper.isHovered(new InputCondition()
		{

			@Override
			public boolean isMet(Gui gui)
			{
				return gui != null && gui.isVisible() && gui.isEnabled() && (gui.events().contains(SlotBehavior.class) || gui.events().contains(SlotStackFactory.class));
			}

		}, input);

		if (pool.has(ButtonState.PRESS) && !hoveringSlot)
		{
			GuiCanvas canvas = GuiCanvas.fetch("dragCanvas", 550.0F);

			if (canvas != null)
			{
				canvas.remove("draggedObject");
			}
		}

		super.onMouseInput(pool, input);
	}

	@Override
	public boolean query(Object... input)
	{
		for (Object obj : input)
		{
			if (obj == DragBehavior.class)
			{
				return true;
			}
		}

		return false;
	}

}
