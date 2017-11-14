package com.gildedgames.orbis.client.gui;

import com.gildedgames.aether.api.orbis_core.util.BlockFilterHelper;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.orbis.client.gui.data.Text;
import com.gildedgames.orbis.client.gui.util.GuiAbstractButton;
import com.gildedgames.orbis.client.gui.util.GuiFactory;
import com.gildedgames.orbis.client.gui.util.GuiText;
import com.gildedgames.orbis.client.gui.util.GuiTexture;
import com.gildedgames.orbis.client.gui.util.vanilla.GuiContainerCreativePublic;
import com.gildedgames.orbis.client.gui.util.vanilla.GuiFrameCreative;
import com.gildedgames.orbis.client.util.rect.Dim2D;
import com.gildedgames.orbis.client.util.rect.Pos2D;
import com.gildedgames.orbis.common.containers.slots.SlotForge;
import com.gildedgames.orbis.common.items.ItemBlockPalette;
import com.gildedgames.orbis.common.items.ItemsOrbis;
import com.gildedgames.orbis.common.util.InputHelper;
import com.google.common.collect.Lists;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentString;

import java.io.IOException;
import java.util.List;

public class GuiFillMenu extends GuiFrameCreative
{
	private static final ResourceLocation MATRIX_ICON = AetherCore.getResource("orbis/filter_gui/filter_matrix.png");

	private static final ResourceLocation FLOW_ICON = AetherCore.getResource("orbis/filter_gui/flow_icon.png");

	private final ContainerFillMenu container;

	private GuiAbstractButton forgeButton;

	public GuiFillMenu(final EntityPlayer player, final IInventory forgeInventory)
	{
		super(player);

		this.container = new ContainerFillMenu(player, forgeInventory, this);

		this.inventorySlots = this.container;
		player.openContainer = this.inventorySlots;

		this.setExtraSlots(12);
	}

	private List<ItemStack> getItemStacksInForge()
	{
		final List<ItemStack> stacks = Lists.newArrayList();

		for (int i = 0; i < this.container.slots.length; i++)
		{
			final SlotForge slot = this.container.slots[i];

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
			final ItemStack stack = new ItemStack(ItemsOrbis.block_palette);

			ItemBlockPalette.setFilterLayer(stack, BlockFilterHelper.createFillLayer(this.getItemStacksInForge()));

			Minecraft.getMinecraft().player.inventory.setItemStack(stack);
		}
	}

	public static class ContainerFillMenu extends ContainerCreativePublic
	{

		public SlotForge[] slots;

		public ContainerFillMenu(final EntityPlayer player, final IInventory forgeInventory, final GuiContainerCreativePublic gui)
		{
			super(player, gui);

			this.slots = new SlotForge[3 * 4];

			final int indexOffset = 55;

			for (int i = 0; i < 3; ++i)
			{
				for (int j = 0; j < 4; ++j)
				{
					final SlotForge slot = new SlotForge(forgeInventory, indexOffset, indexOffset + (i * 4 + j), 222 + j * 18, 36 + i * 18);

					this.addSlotToContainer(slot);

					this.slots[i * 4 + j] = slot;
				}
			}
		}

	}
}
