package com.gildedgames.aether.client.ui.util.events.slots;

import com.gildedgames.aether.client.ui.common.GuiFrame;
import com.gildedgames.aether.client.ui.data.rect.RectModifier.ModifierType;
import com.gildedgames.aether.client.ui.event.GuiEvent;
import com.gildedgames.aether.client.ui.graphics.Graphics2D;
import com.gildedgames.aether.client.ui.input.ButtonState;
import com.gildedgames.aether.client.ui.input.InputProvider;
import com.gildedgames.aether.client.ui.input.MouseInputPool;
import com.gildedgames.aether.client.ui.util.GuiCanvas;
import com.gildedgames.aether.client.ui.util.events.DragBehavior;
import com.gildedgames.aether.client.ui.util.factory.Factory;
import com.google.common.base.Function;

public class SlotBehavior<T> extends GuiEvent<GuiFrame>
{

	private SlotStack<T> slotContents;

	private SlotParser<T> parser;

	private boolean takenContentsOut;

	public SlotBehavior(SlotParser<T> parser)
	{
		this.parser = parser;
	}

	public void setSlotContents(SlotStack<T> draggedState)
	{
		this.setSlotContents(draggedState, true);
	}

	public void clearSlotContents()
	{
		this.slotContents = null;
		this.content().remove("slotContents");

		this.parser.onContentsChange(SlotBehavior.this, null);
	}

	public void setSlotContents(SlotStack<T> draggedState, boolean notifyParser)
	{
		if (draggedState == null)
		{
			return;
		}

		if (notifyParser && !this.parser.isAllowed(draggedState))
		{
			return;
		}

		this.slotContents = draggedState;

		this.slotContents.dim().clear(ModifierType.POS);
		this.slotContents.dim().mod().resetPos().x(this.getGui().dim().width() / 2).y(
				this.getGui().dim().height() / 2).center(true).flush();

		this.content().set("slotContents", this.slotContents);

		if (notifyParser)
		{
			this.parser.onContentsChange(this, this.slotContents);
		}
	}

	public SlotStack<T> getSlotContents()
	{
		return this.slotContents;
	}

	@Override
	public void draw(Graphics2D graphics, InputProvider input)
	{
		super.draw(graphics, input);

		if (this.slotContents != null)
		{
			this.slotContents.dim().mod().center(true).flush();
		}
	}

	@Override
	public void onMouseInput(MouseInputPool pool, InputProvider input)
	{
		if (input.isHovered(this.getGui().dim()) && pool.has(ButtonState.PRESS) && this.parser.onMouseInput(pool, input))
		{
			return;
		}

		super.onMouseInput(pool, input);

		if (!this.takenContentsOut && pool.has(ButtonState.PRESS) && input.isHovered(this.getGui().dim()))
		{
			GuiCanvas canvas = GuiCanvas.fetch("dragCanvas", 550.0F);

			if (canvas != null)
			{
				GuiFrame draggedObject = canvas.get("draggedObject");

				if (draggedObject instanceof SlotStack)
				{
					@SuppressWarnings("unchecked")
					SlotStack<T> stack = (SlotStack<T>) draggedObject;

					if (stack.events().contains("dragBehavior"))
					{
						SlotStack<T> original = this.getSlotContents();

						stack.events().remove("dragBehavior");

						this.setSlotContents(stack);

						canvas.remove("draggedObject");

						if (original != null)
						{
							original.events().set("dragBehavior", new DragBehavior<T>(), original);

							canvas.set("draggedObject", original);
						}
					}
				}
			}
		}

		this.takenContentsOut = false;
	}

	@Override
	public void initEvent()
	{
		Function<T, GuiFrame> iconFactory = new Function<T, GuiFrame>()
		{

			@Override
			public GuiFrame apply(T input)
			{
				return SlotBehavior.this.getSlotContents();
			}

		};

		Factory<T> dataFunction = new Factory<T>()
		{

			@Override
			public T create()
			{
				return SlotBehavior.this.getSlotContents().getData();
			}

		};

		this.getGui().events().set("dragFactory", new SlotStackFactory<T>(iconFactory, dataFunction)
		{

			@Override
			public boolean shouldRemoveDragged(SlotStack<T> createdStack)
			{
				return false;
			}

			@Override
			public boolean isActive(MouseInputPool pool, InputProvider input)
			{
				return SlotBehavior.this.getSlotContents() != null && (!input.isHovered(this.getGui().dim())
						|| !SlotBehavior.this.parser.onMouseInput(pool, input));
			}

			@Override
			public void onCreateDraggedState()
			{
				SlotBehavior.this.takenContentsOut = true;

				SlotBehavior.this.slotContents = null;
				SlotBehavior.this.content().remove("slotContents");

				SlotBehavior.this.parser.onContentsChange(SlotBehavior.this, null);
			}

		});
	}

}
