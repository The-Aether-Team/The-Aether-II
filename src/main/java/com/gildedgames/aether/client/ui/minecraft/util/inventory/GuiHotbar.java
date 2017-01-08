package com.gildedgames.aether.client.ui.minecraft.util.inventory;

import com.gildedgames.aether.client.ui.common.GuiFrame;
import com.gildedgames.aether.client.ui.data.AssetLocation;
import com.gildedgames.aether.client.ui.data.rect.Dim2D;
import com.gildedgames.aether.client.ui.graphics.Graphics2D;
import com.gildedgames.aether.client.ui.graphics.Sprite;
import com.gildedgames.aether.client.ui.input.InputProvider;
import com.gildedgames.aether.client.ui.minecraft.util.GuiFactory;
import com.gildedgames.aether.client.ui.minecraft.util.MinecraftAssetLocation;
import com.gildedgames.aether.client.ui.minecraft.util.inventory.data_structure.BasicInventoryListener;
import com.gildedgames.aether.client.ui.minecraft.util.inventory.data_structure.Inventory;
import com.gildedgames.aether.client.ui.minecraft.util.inventory.data_structure.InventoryElement;
import com.gildedgames.aether.client.ui.minecraft.util.inventory.data_structure.InventoryListener;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.capabilities.player.PlayerAether;

public class GuiHotbar extends GuiFrame
{

	private static final AssetLocation SLOT_OVLERAY = AetherCore.assetGui("inventory/slot_overlay.png");

	private final PlayerAether player;

	private InventoryListener<InventoryElement> listener;

	private Inventory<InventoryElement> inventory;

	public GuiHotbar(PlayerAether player)
	{
		this.player = player;

		this.dim().mod().area(182, 22).flush();
	}

	@Override
	public void initContent(InputProvider input)
	{
		super.initContent(input);
		final AssetLocation slotsAsset = new MinecraftAssetLocation("textures/gui/widgets.png");
		final GuiFrame slots = GuiFactory.texture(slotsAsset, Sprite.UV.build().area(182, 22).flush());
		this.content().set("slots", slots);

		Inventory<InventoryElement> inventory = new Inventory<>(InventoryElement.class);

		if (inventory != null)
		{
			this.listener = new BasicInventoryListener<>(this.content());

			for (int i = 0; i < inventory.getElements().size(); i++)
			{
				GuiFrame slot = new GuiFrame(Dim2D.build().area(22, 22).flush());

				StackSlotBehavior<InventoryElement> elementSlot = new StackSlotBehavior<>(inventory, i, GuiFactory.texture(SLOT_OVLERAY), new InventoryElementIconParser());
				slot.dim().mod().x(20 * i).y(0).flush();

				slot.events().set("elementSlot", elementSlot);
				this.content().set("slot" + i, slot);
			}

			inventory.addListener(this.listener);
		}
	}
	@Override
	public void onClose(InputProvider input)
	{
		super.onClose(input);

		/*Inventory<GodElement> inventory = this.player.getCurrentPower().getHotbarInventory(this.player);
		if (inventory != null)
		{
			inventory.removeListener(this.listener);
		}*/
	}

	@Override
	public void draw(Graphics2D graphics, InputProvider input)
	{
		super.draw(graphics, input);
	}


}
