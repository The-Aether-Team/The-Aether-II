package com.gildedgames.orbis.client.gui;

import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.capabilities.entity.player.PlayerAether;
import com.gildedgames.aether.common.network.NetworkingAether;
import com.gildedgames.orbis.client.gui.data.directory.DirectoryNavigator;
import com.gildedgames.orbis.client.gui.data.directory.IDirectoryNavigator;
import com.gildedgames.orbis.client.gui.data.directory.IDirectoryNavigatorListener;
import com.gildedgames.orbis.client.gui.data.directory.IDirectoryNode;
import com.gildedgames.orbis.client.gui.util.GuiFrame;
import com.gildedgames.orbis.client.gui.util.directory.GuiDirectoryViewer;
import com.gildedgames.orbis.client.gui.util.directory.nodes.BlueprintNode;
import com.gildedgames.orbis.client.gui.util.directory.nodes.OrbisNavigatorNodeFactory;
import com.gildedgames.orbis.client.util.rect.Dim2D;
import com.gildedgames.orbis.client.util.rect.Pos2D;
import com.gildedgames.orbis.common.OrbisLocations;
import com.gildedgames.orbis.common.containers.ContainerGenericInventory;
import com.gildedgames.orbis.common.items.ItemBlueprint;
import com.gildedgames.orbis.common.items.ItemsOrbis;
import com.gildedgames.orbis.common.network.packets.PacketSetItemStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import java.io.File;

public class GuiLoadBlueprint extends GuiFrame
{
	private static final ResourceLocation BACKPACK_CREATIVE = AetherCore.getResource("orbis/vanilla/backpack_creative.png");

	private GuiDirectoryViewer directoryViewer;

	public GuiLoadBlueprint(final PlayerAether playerAether)
	{
		super(Dim2D.flush(), new ContainerGenericInventory(playerAether));

		this.allowUserInput = true;
	}

	@Override
	public void initGui()
	{
		super.initGui();

		this.guiLeft = this.width / 2 - 90 - (176 / 2);
		this.guiTop = this.height / 2 - (147 / 2);

		this.xSize = 179 * 2;
		this.ySize = 169;
	}

	@Override
	public void init()
	{
		final Pos2D center = Pos2D.flush((this.width / 2) + 100, this.height / 2);

		this.directoryViewer = new GuiDirectoryViewer(center.clone().addX(-190).flush(),
				new DirectoryNavigator(new OrbisNavigatorNodeFactory()));

		this.directoryViewer.dim().mod().center(true).flush();

		OrbisLocations.BLUEPRINTS.mkdirs();

		this.directoryViewer.getNavigator().openDirectory(OrbisLocations.BLUEPRINTS);

		this.directoryViewer.getNavigator().addListener(new IDirectoryNavigatorListener()
		{
			@Override
			public void onNodeOpen(final IDirectoryNavigator navigator, final IDirectoryNode node)
			{
				if (node instanceof BlueprintNode)
				{
					final ItemStack stack = new ItemStack(ItemsOrbis.blueprint);

					ItemBlueprint.setBlueprintPath(stack, node.getFile());

					NetworkingAether.sendPacketToServer(new PacketSetItemStack(stack));
					Minecraft.getMinecraft().player.inventory.setItemStack(stack);
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

			}
		});

		this.addChild(this.directoryViewer);
	}

	@Override
	public void drawScreen(final int mouseX, final int mouseY, final float partialTicks)
	{
		this.drawWorldBackground(0);

		this.mc.renderEngine.bindTexture(BACKPACK_CREATIVE);

		this.drawTexturedModalRect(this.width / 2 + 90 - 176 / 2, this.height / 2 - 166 / 2, 0, 0, 176, 166);

		this.fontRenderer.drawString(I18n.format("container.crafting"),
				this.width / 2 + 70, this.height / 2 - 135 / 2, 4210752);

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

}
