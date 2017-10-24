package com.gildedgames.orbis.client.gui;

import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.orbis.client.gui.data.Text;
import com.gildedgames.orbis.client.gui.data.directory.DirectoryNavigator;
import com.gildedgames.orbis.client.gui.util.GuiButtonGeneric;
import com.gildedgames.orbis.client.gui.util.GuiFrame;
import com.gildedgames.orbis.client.gui.util.GuiInput;
import com.gildedgames.orbis.client.gui.util.GuiText;
import com.gildedgames.orbis.client.gui.util.directory.GuiDirectoryViewer;
import com.gildedgames.orbis.client.gui.util.directory.nodes.OrbisNavigatorNodeFactory;
import com.gildedgames.orbis.client.util.rect.Dim2D;
import com.gildedgames.orbis.client.util.rect.Pos2D;
import com.gildedgames.orbis.common.OrbisLocations;
import com.gildedgames.orbis.common.util.InputHelper;
import com.gildedgames.orbis.common.world_objects.Blueprint;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.TextComponentString;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class GuiEditBlueprint extends GuiFrame
{
	private final Blueprint blueprint;

	private GuiInput nameInput;

	private GuiButtonGeneric saveButton, closeButton;

	private GuiDirectoryViewer directoryViewer;

	public GuiEditBlueprint(final Blueprint blueprint)
	{
		super(Dim2D.flush());

		this.setDrawDefaultBackground(true);
		this.blueprint = blueprint;
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

		this.directoryViewer = new GuiDirectoryViewer(center.clone().addX(-200).flush(),
				new DirectoryNavigator(new OrbisNavigatorNodeFactory()));

		this.directoryViewer.dim().mod().center(true).flush();

		OrbisLocations.BLUEPRINTS.mkdirs();

		this.directoryViewer.getNavigator().openDirectory(OrbisLocations.BLUEPRINTS);

		this.addChild(this.directoryViewer);
	}

	@Override
	protected void keyTypedInner(final char typedChar, final int keyCode) throws IOException
	{
		if (!this.nameInput.getInner().isFocused())
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

		if (InputHelper.isHovered(this.saveButton) && mouseButton == 0)
		{
			this.blueprint.saveRegionContent();

			final File file = new File(this.directoryViewer.getNavigator().currentDirectory(), this.nameInput.getInner().getText() + ".blueprint");

			try (FileOutputStream out = new FileOutputStream(file))
			{
				final NBTTagCompound tag = new NBTTagCompound();
				this.blueprint.getData().write(tag);

				CompressedStreamTools.writeCompressed(tag, out);
			}
			catch (final IOException e)
			{
				AetherCore.LOGGER.error("Failed to save Blueprint to disk", e);
			}

			this.directoryViewer.getNavigator().refresh();
		}
	}
}
