package com.gildedgames.aether.client.ui.minecraft.util.inventory;

import com.gildedgames.aether.api.util.NBT;
import com.gildedgames.aether.client.ui.common.GuiFrame;
import com.gildedgames.aether.client.ui.data.DrawingData;
import com.gildedgames.aether.client.ui.data.rect.RectModifier;
import com.gildedgames.aether.client.ui.event.GuiEvent;
import com.gildedgames.aether.client.ui.graphics.Graphics2D;
import com.gildedgames.aether.client.ui.input.InputProvider;
import com.gildedgames.aether.client.ui.minecraft.util.GuiFactory;
import com.gildedgames.aether.client.ui.minecraft.util.events.MinecraftHoveredDesc;
import com.gildedgames.aether.client.ui.minecraft.util.inventory.data_structure.BasicSlotParser;
import com.gildedgames.aether.client.ui.minecraft.util.inventory.data_structure.IconParser;
import com.gildedgames.aether.client.ui.minecraft.util.inventory.data_structure.Inventory;
import com.gildedgames.aether.client.ui.minecraft.util.inventory.data_structure.InventoryElement;
import com.gildedgames.aether.client.ui.util.GuiCanvas;
import com.gildedgames.aether.client.ui.util.TextureElement;
import com.gildedgames.aether.client.ui.util.events.OnHover;
import com.gildedgames.aether.client.ui.util.events.slots.SlotBehavior;
import com.gildedgames.aether.client.ui.util.events.slots.SlotParser;
import com.gildedgames.aether.client.ui.util.events.slots.SlotStack;

import java.awt.Color;

public class StackSlotBehavior<E extends NBT> extends GuiEvent<GuiFrame>
{

	private Inventory<E> inventory;

	private GuiFrame overlayTexture;

	private int slotIndex;

	private SlotParser<E> parser;

	private boolean setInitialElement = true;

	private boolean hasOverlay = true, hasDescription = true;

	private IconParser<E> iconParser;

	private final OnHover<TextureElement> changeVisibilityOnHover = new OnHover<TextureElement>()
	{

		@Override
		public void initEvent()
		{
			this.exitHover();
		}

		@Override
		public void onHover()
		{
			this.getGui().drawingData(new DrawingData(new Color(1.0F, 1.0F, 1.0F, 0.6F)));
		}

		@Override
		public void exitHover()
		{
			this.getGui().drawingData(new DrawingData(new Color(1.0F, 1.0F, 1.0F, 0.0F)));
		}

		@Override
		public void draw(Graphics2D graphics, InputProvider input)
		{
			super.draw(graphics, input);
		}

	};

	public StackSlotBehavior(Inventory<E> inventory, int slotIndex, GuiFrame overlayTexture, IconParser<E> iconParser)
	{
		this(inventory, slotIndex, overlayTexture, new BasicSlotParser<E>(inventory, slotIndex), iconParser);
	}

	public StackSlotBehavior(Inventory<E> inventory, GuiFrame overlayTexture, SlotParser<E> parser, IconParser<E> iconParser)
	{
		this(inventory, 0, overlayTexture, parser, iconParser);

		this.setInitialElement = false;
	}

	public StackSlotBehavior(Inventory<E> inventory, int slotIndex, GuiFrame overlayTexture, SlotParser<E> parser, IconParser<E> iconParser)
	{
		this.inventory = inventory;
		this.slotIndex = slotIndex;

		this.overlayTexture = overlayTexture;
		this.parser = parser;

		this.iconParser = iconParser;
	}

	public void setHasOverlay(boolean hasOverlay)
	{
		this.hasOverlay = hasOverlay;
	}

	public void setHasDescription(boolean hasDescription)
	{
		this.hasDescription = hasDescription;
	}

	@Override
	public void initEvent()
	{
		final SlotBehavior<E> slotBehavior = new SlotBehavior<E>(this.parser);

		this.getGui().events().set("slotBehavior", slotBehavior);

		if (this.hasDescription)
		{
			this.getGui().events().set("description", new MinecraftHoveredDesc(GuiFactory.text("", Color.WHITE))
			{

				@Override
				public void draw(Graphics2D graphics, InputProvider input)
				{
					super.draw(graphics, input);

					if (slotBehavior.getSlotContents() != null && slotBehavior.getSlotContents().getData() instanceof InventoryElement)
					{
						InventoryElement element = (InventoryElement) slotBehavior.getSlotContents().getData();

						this.textElement.setData(element.name());

						this.background.dim().mod().width(this.text.font().getWidth(element.name()) + 5).flush();
						this.background.setVisible(true);
					}
					else
					{
						this.textElement.setData("");
						this.background.setVisible(false);
					}
				}

			});
		}

		if (this.setInitialElement)
		{
			E element = this.inventory.getElement(this.slotIndex);

			if (element != null)
			{
				slotBehavior.setSlotContents(new SlotStack<E>(this.iconParser.parse(element), element));
			}
		}

		this.overlayTexture.events().set("changeVisibilityOnHover", this.changeVisibilityOnHover);

		this.overlayTexture.dim().mod().pos(this.getGui().dim().width() / 2, this.getGui().dim().height() / 2).center(true).flush();

		this.overlayTexture.dim().add(this.getGui(), RectModifier.ModifierType.POS, RectModifier.ModifierType.SCALE);
	}

	@Override
	public void draw(Graphics2D graphics, InputProvider input)
	{
		super.draw(graphics, input);

		if (this.hasOverlay)
		{
			if (input.isHovered(this.getGui()))
			{
				GuiCanvas.fetch("overlay", 550F).set("slotOverlay", this.overlayTexture);
			}
			else
			{
				GuiCanvas.fetch("overlay", 550F).remove("slotOverlay", this.overlayTexture);
			}
		}
	}

}
