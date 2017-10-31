package com.gildedgames.orbis.client.gui.util.directory.nodes;

import com.gildedgames.aether.api.orbis_core.data.management.IProject;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.network.NetworkingAether;
import com.gildedgames.orbis.client.gui.data.DropdownElement;
import com.gildedgames.orbis.client.gui.data.IDropdownElement;
import com.gildedgames.orbis.client.gui.data.directory.IDirectoryNavigator;
import com.gildedgames.orbis.client.gui.data.directory.IDirectoryNode;
import com.gildedgames.orbis.client.gui.util.GuiDropdownList;
import com.gildedgames.orbis.client.gui.util.GuiFactory;
import com.gildedgames.orbis.client.gui.util.GuiTexture;
import com.gildedgames.orbis.client.util.rect.Dim2D;
import com.gildedgames.orbis.common.network.packets.projects.PacketRequestProject;
import com.google.common.collect.Lists;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentString;

import java.io.File;
import java.util.Collection;
import java.util.List;

public class ProjectNode implements IDirectoryNode
{
	private static final ResourceLocation TEXTURE = AetherCore.getResource("orbis/navigator/project.png");

	private final File file;

	private final GuiTexture icon = new GuiTexture(Dim2D.build().area(32).flush(), TEXTURE);

	private final IProject project;

	public ProjectNode(final File file, final IProject project)
	{
		if (!file.isDirectory())
		{
			throw new RuntimeException("File given to FolderNode is not a directory! Aborting.");
		}

		this.file = file;
		this.project = project;
	}

	public IProject getProject()
	{
		return this.project;
	}

	@Override
	public File getFile()
	{
		return this.file;
	}

	@Override
	public GuiTexture getIcon()
	{
		return this.icon;
	}

	@Override
	public boolean isOnClient()
	{
		return this.project.getMetadata().isDownloaded();
	}

	@Override
	public boolean isDownloading()
	{
		return this.project.getMetadata().isDownloading();
	}

	@Override
	public void onOpen(final IDirectoryNavigator navigator)
	{
		if (this.project.getMetadata().isDownloaded() || Minecraft.getMinecraft().isIntegratedServerRunning())
		{
			navigator.openDirectory(this.file);
		}
	}

	@Override
	public void onDelete(final IDirectoryNavigator navigator)
	{

	}

	@Override
	public Collection<IDropdownElement> getRightClickElements(final IDirectoryNavigator navigator)
	{
		final List<IDropdownElement> elements = Lists.newArrayList();

		if (!Minecraft.getMinecraft().isIntegratedServerRunning() && !this.isOnClient() && !this.isDownloading())
		{
			elements.add(new DropdownElement(new TextComponentString("Download"))
			{
				@Override
				public void onClick(final GuiDropdownList list, final EntityPlayer player)
				{
					ProjectNode.this.project.getMetadata().setDownloading(true);
					NetworkingAether.sendPacketToServer(new PacketRequestProject(ProjectNode.this.project.getProjectIdentifier()));
				}
			});
		}

		elements.add(GuiFactory.createCloseDropdownElement(this.file, navigator));

		return elements;
	}
}
