package com.gildedgames.orbis.client.gui;

import com.gildedgames.aether.api.orbis_core.util.BlockFilterHelper;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.ReflectionAether;
import com.gildedgames.orbis.client.gui.data.Text;
import com.gildedgames.orbis.client.gui.util.GuiAbstractButton;
import com.gildedgames.orbis.client.gui.util.GuiFactory;
import com.gildedgames.orbis.client.gui.util.GuiText;
import com.gildedgames.orbis.client.gui.util.GuiTexture;
import com.gildedgames.orbis.client.gui.util.vanilla.GuiFrameCreative;
import com.gildedgames.orbis.client.util.rect.Dim2D;
import com.gildedgames.orbis.client.util.rect.Pos2D;
import com.gildedgames.orbis.common.containers.slots.SlotForge;
import com.gildedgames.orbis.common.items.ItemForgedBlock;
import com.gildedgames.orbis.common.items.ItemsOrbis;
import com.gildedgames.orbis.common.util.InputHelper;
import com.google.common.collect.Lists;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainerCreative;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;

public class GuiFillMenu extends GuiFrameCreative
{
	private static final ResourceLocation MATRIX_ICON = AetherCore.getResource("orbis/filter_gui/filter_matrix.png");

	private static final ResourceLocation FLOW_ICON = AetherCore.getResource("orbis/filter_gui/flow_icon.png");

	private static final Method ADD_SLOT_TO_CONTAINER;

	static
	{
		// TODO: Add obfuscated entry
		ADD_SLOT_TO_CONTAINER = ReflectionAether.getMethod(Container.class, new Class<?>[] { Slot.class }, "addSlotToContainer", "func_75146_a");
	}

	private final SlotForge[] slots;

	private GuiAbstractButton forgeButton;

	public GuiFillMenu(final EntityPlayer player, final IInventory forgeInventory)
	{
		super(player);

		this.inventorySlots.inventorySlots.clear();

		final int indexOffset = 55;

		this.slots = new SlotForge[3 * 4];

		for (int i = 0; i < 3; ++i)
		{
			for (int j = 0; j < 4; ++j)
			{
				final SlotForge slot = new SlotForge(forgeInventory, indexOffset, indexOffset + (i * 4 + j), 222 + j * 18, 36 + i * 18);

				ReflectionAether.invokeMethod(ADD_SLOT_TO_CONTAINER, this.inventorySlots, slot);

				this.slots[i * 4 + j] = slot;
			}
		}

		final IInventory basicInventory = ObfuscationReflectionHelper.getPrivateValue(GuiContainerCreative.class,
				(GuiContainerCreative) Minecraft.getMinecraft().currentScreen, 1);

		final InventoryPlayer inventoryplayer = player.inventory;

		for (int i = 0; i < 5; ++i)
		{
			for (int j = 0; j < 9; ++j)
			{
				ReflectionAether.invokeMethod(ADD_SLOT_TO_CONTAINER, this.inventorySlots, new Slot(basicInventory, i * 9 + j, 9 + j * 18, 18 + i * 18));
			}
		}

		for (int k = 0; k < 9; ++k)
		{
			ReflectionAether.invokeMethod(ADD_SLOT_TO_CONTAINER, this.inventorySlots, new Slot(inventoryplayer, k, 9 + k * 18, 112));
		}
	}

	private List<ItemStack> getItemStacksInForge()
	{
		final List<ItemStack> stacks = Lists.newArrayList();

		for (int i = 0; i < this.slots.length; i++)
		{
			final SlotForge slot = this.slots[i];

			if (slot.getStack() != null && !slot.getStack().isEmpty())
			{
				stacks.add(slot.getStack());
			}
		}

		return stacks;
	}

	@Override
	public void init()
	{
		final Pos2D center = Pos2D.flush((this.width / 2) + 100, this.height / 2);

		this.forgeButton = GuiFactory.createForgeButton();

		this.forgeButton.dim().mod().pos(center).center(true).addY(72 - 15).addX(60).flush();

		final GuiTexture matrix = new GuiTexture(Dim2D.build().width(85).height(87).center(true).pos(center).addX(60).addY(-15).flush(), MATRIX_ICON);
		final GuiTexture flow = new GuiTexture(Dim2D.build().width(8).height(14).center(true).pos(center).addX(60).addY(52 - 15).flush(), FLOW_ICON);

		final GuiText combineTitle = new GuiText(Dim2D.build().pos(center).centerX(true).addX(60).addY(-49).flush(),
				new Text(new TextComponentString("Combine"), 1.0F));

		this.addChild(matrix);
		this.addChild(flow);
		this.addChild(combineTitle);

		this.addChild(this.forgeButton);
	}

	@Override
	public void draw()
	{
		this.forgeButton.setEnabled(this.getItemStacksInForge().size() >= 2);
	}

	@Override
	protected void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) throws IOException
	{
		super.mouseClicked(mouseX, mouseY, mouseButton);

		if (InputHelper.isHovered(this.forgeButton) && mouseButton == 0)
		{
			final ItemStack stack = new ItemStack(ItemsOrbis.forged_block);

			ItemForgedBlock.setFilterLayer(stack, BlockFilterHelper.createFillLayer(this.getItemStacksInForge()));

			Minecraft.getMinecraft().player.inventory.setItemStack(stack);
		}
	}
}
