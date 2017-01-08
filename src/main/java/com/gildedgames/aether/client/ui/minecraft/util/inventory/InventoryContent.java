package com.gildedgames.aether.client.ui.minecraft.util.inventory;

import com.gildedgames.aether.api.util.NBT;
import com.gildedgames.aether.client.ui.common.GuiFrame;
import com.gildedgames.aether.client.ui.common.Ui;
import com.gildedgames.aether.client.ui.data.AssetLocation;
import com.gildedgames.aether.client.ui.data.rect.Dim2D;
import com.gildedgames.aether.client.ui.data.rect.Rect;
import com.gildedgames.aether.client.ui.minecraft.util.GuiFactory;
import com.gildedgames.aether.client.ui.minecraft.util.inventory.data_structure.BasicSlotParser;
import com.gildedgames.aether.client.ui.minecraft.util.inventory.data_structure.IconParser;
import com.gildedgames.aether.client.ui.minecraft.util.inventory.data_structure.Inventory;
import com.gildedgames.aether.client.ui.util.events.slots.SlotParser;
import com.gildedgames.aether.client.ui.util.factory.ContentFactory;
import com.gildedgames.aether.common.AetherCore;
import com.google.common.base.Function;
import com.google.common.collect.ImmutableMap;

import java.util.LinkedHashMap;

public class InventoryContent<E extends NBT> implements ContentFactory<GuiFrame>
{

	private Inventory<E> inventory;

	private Function<Integer, GuiFrame> slotIconFactory, slotOverlayFactory;

	private Function<Integer, SlotParser<E>> slotParserFactory;

	private IconParser<E> iconParser;

	public InventoryContent(Inventory<E> inventory, Function<Integer, GuiFrame> slotIconFactory,
			Function<Integer, GuiFrame> slotOverlayFactory, Function<Integer, SlotParser<E>> slotParserFactory, IconParser<E> iconParser)
	{
		this.inventory = inventory;

		this.slotIconFactory = slotIconFactory;
		this.slotOverlayFactory = slotOverlayFactory;
		this.slotParserFactory = slotParserFactory;
		this.iconParser = iconParser;
	}

	public static <E extends NBT> InventoryContent<E> createStandard(final Inventory<E> inventory, IconParser<E> iconParser)
	{
		final AssetLocation slotOverlayAsset = AetherCore.assetGui("inventory/slot_overlay.png");

		Function<Integer, GuiFrame> slotIconFactory = input -> GuiFactory.panelEmbedded(Dim2D.build().area(18, 18).flush());
		Function<Integer, GuiFrame> slotOverlayFactory = input -> GuiFactory.texture(slotOverlayAsset);
		Function<Integer, SlotParser<E>> slotParserFactory = input -> new BasicSlotParser<E>(inventory, input);

		return new InventoryContent<>(inventory, slotIconFactory, slotOverlayFactory, slotParserFactory, iconParser);
	}

	@Override
	public LinkedHashMap<String, GuiFrame> provideContent(ImmutableMap<String, Ui> currentContent, Rect contentArea)
	{
		LinkedHashMap<String, GuiFrame> content = new LinkedHashMap<String, GuiFrame>();

		for (int slotIndex = 0; slotIndex < this.inventory.getMaxSize(); slotIndex++)
		{
			GuiFrame slot = this.slotIconFactory.apply(slotIndex);
			GuiFrame slotOverlay = this.slotOverlayFactory.apply(slotIndex);
			SlotParser<E> slotParser = this.slotParserFactory.apply(slotIndex);

			slot.dim().mod().resetPos().flush();
			slot.dim().clear();

			slot.events().set("elementSlotBehavior", new StackSlotBehavior<E>(this.inventory, slotOverlay, slotParser, this.iconParser));

			content.put("slot" + slotIndex, slot);
		}

		return content;
	}

}
