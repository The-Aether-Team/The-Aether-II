package com.gildedgames.orbis.client.gui;

import com.gildedgames.aether.api.orbis_core.api.exceptions.OrbisMissingProjectException;
import com.gildedgames.aether.api.orbis_core.data.management.IData;
import com.gildedgames.aether.api.orbis_core.data.management.IProject;
import com.gildedgames.aether.api.orbis_core.data.management.IProjectIdentifier;
import com.gildedgames.aether.api.orbis_core.data.management.impl.ProjectIdentifier;
import com.gildedgames.aether.api.world_object.IWorldObject;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.network.NetworkingAether;
import com.gildedgames.orbis.client.gui.data.Text;
import com.gildedgames.orbis.client.gui.data.directory.DirectoryNavigator;
import com.gildedgames.orbis.client.gui.data.directory.IDirectoryNavigator;
import com.gildedgames.orbis.client.gui.data.directory.IDirectoryNavigatorListener;
import com.gildedgames.orbis.client.gui.data.directory.IDirectoryNode;
import com.gildedgames.orbis.client.gui.util.GuiButtonGeneric;
import com.gildedgames.orbis.client.gui.util.GuiFrame;
import com.gildedgames.orbis.client.gui.util.GuiInput;
import com.gildedgames.orbis.client.gui.util.GuiText;
import com.gildedgames.orbis.client.gui.util.directory.GuiDirectoryViewer;
import com.gildedgames.orbis.client.gui.util.directory.nodes.OrbisNavigatorNodeFactory;
import com.gildedgames.orbis.client.gui.util.directory.nodes.ProjectNode;
import com.gildedgames.orbis.client.util.rect.Dim2D;
import com.gildedgames.orbis.client.util.rect.Pos2D;
import com.gildedgames.orbis.common.Orbis;
import com.gildedgames.orbis.common.network.packets.projects.PacketRequestCreateProject;
import com.gildedgames.orbis.common.network.packets.projects.PacketRequestProjectListing;
import com.gildedgames.orbis.common.network.packets.projects.PacketSaveWorldObjectToProject;
import com.gildedgames.orbis.common.util.InputHelper;
import com.gildedgames.orbis.common.world_objects.Blueprint;
import net.minecraft.client.Minecraft;
import net.minecraft.util.text.TextComponentString;

import java.io.File;
import java.io.IOException;

public class GuiViewProjects extends GuiFrame implements IDirectoryNavigatorListener
{
	private final Blueprint blueprint;

	private GuiInput nameInput;

	private GuiInput projectNameInput;

	private GuiButtonGeneric saveButton, closeButton, saveProject;

	private GuiDirectoryViewer directoryViewer;

	private boolean requestListing = true;

	private IProject project;

	public GuiViewProjects(final Blueprint blueprint)
	{
		super(Dim2D.flush());

		this.setDrawDefaultBackground(true);
		this.blueprint = blueprint;
	}

	public void refreshNavigator()
	{
		this.requestListing = false;
		this.directoryViewer.getNavigator().refresh();
		this.requestListing = true;
	}

	@Override
	public void init()
	{
		final Pos2D center = Pos2D.flush((this.width / 2) + 100, this.height / 2);

		final GuiText nameInputTitle = new GuiText(Dim2D.build().width(140).height(20).pos(center).addY(-25).addX(-70).flush(),
				new Text(new TextComponentString("Blueprint Name:"), 1.0F));

		this.nameInput = new GuiInput(Dim2D.build().center(true).width(140).height(20).pos(center).flush());

		this.saveButton = new GuiButtonGeneric(Dim2D.build().center(true).width(50).height(20).pos(center).addY(30).addX(-30).flush());

		this.saveButton.getInner().displayString = "Save";

		this.closeButton = new GuiButtonGeneric(Dim2D.build().center(true).width(50).height(20).pos(center).addY(30).addX(30).flush());

		this.closeButton.getInner().displayString = "Close";

		this.addChild(nameInputTitle);
		this.addChild(this.nameInput);
		this.addChild(this.saveButton);
		this.addChild(this.closeButton);

		final GuiText projectInputTitle = new GuiText(Dim2D.build().width(140).height(20).pos(center).addY(-95).addX(-30).flush(),
				new Text(new TextComponentString("Project Name:"), 1.0F));

		this.projectNameInput = new GuiInput(Dim2D.build().center(true).width(140).height(20).pos(center).addY(-70).flush());

		this.saveProject = new GuiButtonGeneric(Dim2D.build().center(true).width(50).height(20).pos(center).addY(-40).addX(-30).flush());

		this.saveProject.getInner().displayString = "Save";

		this.addChild(projectInputTitle);
		this.addChild(this.projectNameInput);
		this.addChild(this.saveProject);

		this.directoryViewer = new GuiDirectoryViewer(center.clone().addX(-200).flush(),
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
	}

	@Override
	protected void keyTypedInner(final char typedChar, final int keyCode) throws IOException
	{
		if (!this.nameInput.getInner().isFocused() && !this.projectNameInput.getInner().isFocused())
		{
			super.keyTypedInner(typedChar, keyCode);
		}
	}

	@Override
	protected void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) throws IOException
	{
		super.mouseClicked(mouseX, mouseY, mouseButton);

		if (InputHelper.isHovered(this.closeButton) && mouseButton == 0)
		{
			Minecraft.getMinecraft().displayGuiScreen(null);
			GuiRightClickBlueprint.lastCloseTime = System.currentTimeMillis();
		}

		if (InputHelper.isHovered(this.saveButton) && mouseButton == 0 && !Orbis.getProjectManager().getLocation()
				.equals(this.directoryViewer.getNavigator()
						.currentDirectory()))
		{
			final File file = new File(this.directoryViewer.getNavigator().currentDirectory(),
					this.nameInput.getInner().getText() + "." + this.blueprint.getData().getFileExtension());

			final String location = file.getCanonicalPath().replace(this.project.getLocationAsFile().getCanonicalPath() + File.separator, "");

			if (Minecraft.getMinecraft().isIntegratedServerRunning())
			{
				try
				{
					final IWorldObject worldObject = this.blueprint;

					if (this.project != null && worldObject.getData() != null && !file.exists())
					{
						IData data = worldObject.getData();

						/**
						 * Check if the data has already been stored.
						 * If so, we should create a new identifier for it as
						 * a clone. Many issues are caused if two files use
						 * the same identifier.
						 */
						if (data.getMetadata().getIdentifier() != null && this.project.getCache().hasData(data.getMetadata().getIdentifier().getDataId()))
						{
							data = data.clone();
							data.getMetadata().setIdentifier(this.project.getCache().createNextIdentifier());
						}

						data.preSaveToDisk(worldObject);

						this.project.getCache().setData(data, location);

						this.project.writeData(data, file);
						this.refreshNavigator();
					}
				}
				catch (final OrbisMissingProjectException e)
				{
					AetherCore.LOGGER.error(e);
				}
			}
			else
			{
				NetworkingAether.sendPacketToServer(new PacketSaveWorldObjectToProject(this.project, this.blueprint, location));
			}
		}

		if (InputHelper.isHovered(this.saveProject) && mouseButton == 0 && Orbis.getProjectManager().getLocation()
				.equals(this.directoryViewer.getNavigator()
						.currentDirectory()))
		{
			final IProjectIdentifier id = new ProjectIdentifier(this.projectNameInput.getInner().getText(), Minecraft.getMinecraft().player.getName());

			if (!Orbis.getProjectManager().projectNameExists(this.projectNameInput.getInner().getText()) && !Orbis.getProjectManager()
					.projectExists(id))
			{
				NetworkingAether.sendPacketToServer(new PacketRequestCreateProject(this.projectNameInput.getInner().getText(), id));
			}
		}
	}

	@Override
	public void onNodeOpen(final IDirectoryNavigator navigator, final IDirectoryNode node)
	{
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
