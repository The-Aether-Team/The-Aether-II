package com.gildedgames.orbis.client.gui;

import com.gildedgames.aether.api.orbis_core.api.exceptions.OrbisMissingDataException;
import com.gildedgames.aether.api.orbis_core.api.exceptions.OrbisMissingProjectException;
import com.gildedgames.aether.api.orbis_core.data.BlueprintData;
import com.gildedgames.aether.api.orbis_core.data.DataCondition;
import com.gildedgames.aether.api.orbis_core.data.management.IData;
import com.gildedgames.aether.api.orbis_core.data.management.IDataIdentifier;
import com.gildedgames.aether.api.orbis_core.data.management.IProject;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.capabilities.entity.player.PlayerAether;
import com.gildedgames.aether.common.network.NetworkingAether;
import com.gildedgames.orbis.client.gui.data.Text;
import com.gildedgames.orbis.client.gui.data.directory.DirectoryNavigator;
import com.gildedgames.orbis.client.gui.data.directory.IDirectoryNavigator;
import com.gildedgames.orbis.client.gui.data.directory.IDirectoryNavigatorListener;
import com.gildedgames.orbis.client.gui.data.directory.IDirectoryNode;
import com.gildedgames.orbis.client.gui.util.*;
import com.gildedgames.orbis.client.gui.util.directory.GuiDirectoryViewer;
import com.gildedgames.orbis.client.gui.util.directory.nodes.BlueprintNode;
import com.gildedgames.orbis.client.gui.util.directory.nodes.OrbisNavigatorNodeFactory;
import com.gildedgames.orbis.client.gui.util.directory.nodes.ProjectNode;
import com.gildedgames.orbis.client.util.rect.Dim2D;
import com.gildedgames.orbis.client.util.rect.Pos2D;
import com.gildedgames.orbis.common.Orbis;
import com.gildedgames.orbis.common.containers.ContainerBlueprintInventory;
import com.gildedgames.orbis.common.containers.slots.SlotForge;
import com.gildedgames.orbis.common.data.BlueprintPalette;
import com.gildedgames.orbis.common.items.ItemBlueprint;
import com.gildedgames.orbis.common.items.ItemBlueprintPalette;
import com.gildedgames.orbis.common.items.ItemsOrbis;
import com.gildedgames.orbis.common.network.packets.PacketSetItemStack;
import com.gildedgames.orbis.common.network.packets.projects.PacketRequestProjectListing;
import com.gildedgames.orbis.common.util.InputHelper;
import com.google.common.collect.Lists;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentString;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class GuiLoadBlueprint extends GuiFrame implements IDirectoryNavigatorListener
{
	private static final ResourceLocation MATRIX_ICON = AetherCore.getResource("orbis/filter_gui/filter_matrix.png");

	private static final ResourceLocation BLUEPRINT_INVENTORY = AetherCore.getResource("orbis/blueprint_gui/blueprint_inventory.png");

	private static final ResourceLocation MERGE_ICON = AetherCore.getResource("orbis/blueprint_gui/merge_icon_right.png");

	private final ContainerBlueprintInventory container;

	private GuiAbstractButton forgeButton;

	private GuiTexture matrix, flow;

	private GuiText combineTitle;

	private GuiDirectoryViewer directoryViewer;

	private boolean requestListing = true;

	private IProject project;

	public GuiLoadBlueprint(final PlayerAether playerAether)
	{
		super(Dim2D.flush(), null);

		this.container = new ContainerBlueprintInventory(playerAether, playerAether.getOrbisModule().powers().getBlueprintPower().getForgeInventory());

		this.inventorySlots = this.container;
		playerAether.getEntity().openContainer = this.inventorySlots;

		this.allowUserInput = true;
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

	public void refreshNavigator()
	{
		this.requestListing = false;
		this.directoryViewer.getNavigator().refresh();
		this.requestListing = true;
	}

	@Override
	public void initGui()
	{
		super.initGui();

		this.guiLeft = this.width / 2 - 90 - (176 / 2);
		this.guiTop = this.height / 2 - (147 / 2) + 12;

		this.xSize = 179 * 2;
		this.ySize = 169;
	}

	@Override
	public void init()
	{
		final Pos2D center = Pos2D.flush((this.width / 2), this.height / 2);

		this.directoryViewer = new GuiDirectoryViewer(center.clone().addX(-90).flush(),
				new DirectoryNavigator(new OrbisNavigatorNodeFactory()));

		this.directoryViewer.dim().mod().center(true).flush();

		if (!Orbis.getProjectManager().getLocation().exists())
		{
			if (!Orbis.getProjectManager().getLocation().mkdirs())
			{
				throw new RuntimeException("Project manager file could not be created!");
			}
		}

		this.directoryViewer.getNavigator().addListener(this);

		this.directoryViewer.getNavigator().openDirectory(Orbis.getProjectManager().getLocation());

		this.addChild(this.directoryViewer);

		final int xOffset = 15;
		final int yOffset = 7;

		this.forgeButton = GuiFactory.createForgeButton();

		this.forgeButton.dim().mod().pos(center).center(true).addY(52 - 6 - 102 + yOffset).addX(133 + xOffset).flush();

		this.matrix = new GuiTexture(Dim2D.build().width(85).height(105).pos(center).addX(2 + xOffset).addY(-15 - 100 + yOffset).flush(), MATRIX_ICON);
		this.flow = new GuiTexture(Dim2D.build().width(20).height(14).pos(center).addX(95 + xOffset).addY(52 - 6 - 110 + yOffset).flush(), MERGE_ICON);

		this.combineTitle = new GuiText(Dim2D.build().pos(center).centerX(true).addX(44 + xOffset).addY(-49 - 9 - 47 + yOffset).flush(),
				new Text(new TextComponentString("Group"), 1.0F));

		this.addChild(this.matrix);
		this.addChild(this.flow);
		this.addChild(this.combineTitle);

		this.addChild(this.forgeButton);
	}

	@Override
	public void drawScreen(final int mouseX, final int mouseY, final float partialTicks)
	{
		this.drawWorldBackground(0);

		this.mc.renderEngine.bindTexture(BLUEPRINT_INVENTORY);

		this.drawTexturedModalRect(this.width / 2 + 90 - 176 / 2, this.height / 2 - (166 / 2) + 12, 0, 0, 176, 166);

		super.drawScreen(mouseX, mouseY, partialTicks);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(final float partialTicks, final int mouseX, final int mouseY)
	{
	}

	@Override
	public void drawDefaultBackground()
	{
	}

	@Override
	protected void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) throws IOException
	{
		super.mouseClicked(mouseX, mouseY, mouseButton);

		if (InputHelper.isHovered(this.forgeButton) && mouseButton == 0)
		{
			final ItemStack stack = new ItemStack(ItemsOrbis.blueprint_palette);

			final BlueprintPalette palette = new BlueprintPalette();

			for (final ItemStack s : this.getItemStacksInForge())
			{
				try
				{
					final IDataIdentifier id = ItemBlueprint.getBlueprintId(s);

					if (palette.getIDToConditions().containsKey(id))
					{
						final DataCondition condition = palette.getIDToConditions().get(id);

						condition.setWeight(condition.getWeight() + 1.0F);
					}
					else
					{
						final BlueprintData data = Orbis.getProjectManager().findData(id);
						palette.add(data, new DataCondition());
					}
				}
				catch (final OrbisMissingDataException | OrbisMissingProjectException e)
				{
					AetherCore.LOGGER.error(e);
				}
			}

			ItemBlueprintPalette.setBlueprintPalette(stack, palette);

			NetworkingAether.sendPacketToServer(new PacketSetItemStack(stack));
			Minecraft.getMinecraft().player.inventory.setItemStack(stack);
		}
	}

	@Override
	public void onNodeOpen(final IDirectoryNavigator navigator, final IDirectoryNode node)
	{
		if (node instanceof BlueprintNode)
		{
			final ItemStack stack = new ItemStack(ItemsOrbis.blueprint);

			try
			{
				final IData data = Orbis.getProjectManager().findData(GuiLoadBlueprint.this.project, node.getFile());

				ItemBlueprint.setBlueprint(stack, data.getMetadata().getIdentifier());

				NetworkingAether.sendPacketToServer(new PacketSetItemStack(stack));
				Minecraft.getMinecraft().player.inventory.setItemStack(stack);
			}
			catch (OrbisMissingDataException | OrbisMissingProjectException e)
			{
				AetherCore.LOGGER.error(e);
			}
		}

		if (node instanceof ProjectNode)
		{
			final ProjectNode projectNode = (ProjectNode) node;

			this.project = projectNode.getProject();
		}
	}

	@Override
	public void onDirectoryOpen(final IDirectoryNavigator navigator, final File file)
	{

	}

	@Override
	public void onBack(final IDirectoryNavigator navigator)
	{

	}

	@Override
	public void onForward(final IDirectoryNavigator navigator)
	{

	}

	@Override
	public void onRefresh(final IDirectoryNavigator navigator)
	{
		if (!Minecraft.getMinecraft().isIntegratedServerRunning() && this.requestListing)
		{
			NetworkingAether.sendPacketToServer(new PacketRequestProjectListing());
		}
	}

}
