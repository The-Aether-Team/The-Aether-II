package com.gildedgames.aether.client.ui.event.view;

import com.gildedgames.aether.client.ui.event.MouseEvent;
import com.gildedgames.aether.client.ui.input.InputProvider;
import com.gildedgames.aether.client.ui.input.MouseInput;
import com.gildedgames.aether.client.ui.input.MouseInputPool;

import java.util.ArrayList;
import java.util.List;

public abstract class MouseEventGui extends MouseEvent
{

	public MouseEventGui()
	{
		this(new ArrayList<MouseInput>());
	}

	public MouseEventGui(List<MouseInput> events)
	{
		super(events);
	}

	public MouseEventGui(MouseInput... events)
	{
		super(events);
	}

	@Override
	public void onMouseInput(MouseInputPool pool, InputProvider input)
	{
		if (input.isHovered(this.getGui().dim()) && this.behaviorsMet(input, pool, this.scrollDifference)
				&& pool.containsAll(this.getEvents()))
		{
			this.onTrue(input, pool);
		}
		else
		{
			this.onFalse(input, pool);
		}
	}

	protected abstract void onTrue(InputProvider input, MouseInputPool pool);

	protected abstract void onFalse(InputProvider input, MouseInputPool pool);

}
