package com.gildedgames.orbis.client.gui.util.directory;

import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.orbis.client.gui.data.Text;
import com.gildedgames.orbis.client.gui.data.directory.IDirectoryNavigator;
import com.gildedgames.orbis.client.gui.data.directory.IDirectoryNode;
import com.gildedgames.orbis.client.gui.util.GuiFrame;
import com.gildedgames.orbis.client.gui.util.GuiTextBox;
import com.gildedgames.orbis.client.gui.util.GuiTexture;
import com.gildedgames.orbis.client.util.rect.Dim2D;
import com.gildedgames.orbis.client.util.rect.Pos2D;
import com.gildedgames.orbis_core.util.InputHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.io.IOException;

public class GuiDirectoryNode extends GuiFrame
{
	private static final ResourceLocation TICK_TEXTURE = AetherCore.getResource("orbis/navigator/tick.png");

	private static final ResourceLocation CROSS_TEXTURE = AetherCore.getResource("orbis/navigator/cross.png");

	private static final ResourceLocation DOWNLOADING_TEXTURE = AetherCore.getResource("orbis/navigator/downloading.png");

	public static int WIDTH = 40, HEIGHT = 54;

	private final IDirectoryNode directoryNode;

	private final IDirectoryNavigator navigator;

	private final GuiDirectoryViewer viewer;

	private final GuiTexture tick_icon = new GuiTexture(Dim2D.build().width(10).height(12).flush(), TICK_TEXTURE);

	private final GuiTexture cross_icon = new GuiTexture(Dim2D.build().width(9).height(9).flush(), CROSS_TEXTURE);

	private final GuiTexture downloading_icon = new GuiTexture(Dim2D.build().width(9).height(9).flush(), DOWNLOADING_TEXTURE);

	public long lastClickTime = System.currentTimeMillis();

	private GuiTexture icon;

	private GuiTextBox nameplate;

	public GuiDirectoryNode(final Pos2D pos, final IDirectoryNode navigatorNode, final GuiDirectoryViewer viewer)
	{
		super(Dim2D.build().width(WIDTH).height(HEIGHT).pos(pos).flush());

		this.directoryNode = navigatorNode;

		this.viewer = viewer;
		this.navigator = viewer.getNavigator();
	}

	@Override
	public void init()
	{
		this.icon = this.directoryNode.getIcon().clone();

		final File file = this.directoryNode.getFile();

		final ITextComponent text = new TextComponentString(file.getName().replace("." + FilenameUtils.getExtension(file.getName()), ""));

		this.nameplate = new GuiTextBox(Dim2D.build().width(WIDTH).height(10).y(27).x(WIDTH / 2).centerX(true).flush(), true,
				new Text(text, 1.0F));

		this.icon.dim().mod().x(WIDTH / 2).addX(1).centerX(true).flush();

		this.cross_icon.dim().mod().x(WIDTH / 2).addX(3).y(HEIGHT / 2).addY(-11).centerX(true).flush();
		this.tick_icon.dim().mod().x(WIDTH / 2).addX(3).y(HEIGHT / 2).addY(-11).centerX(true).flush();
		this.downloading_icon.dim().mod().x(WIDTH / 2).addX(3).y(HEIGHT / 2).addY(-11).centerX(true).flush();

		this.cross_icon.setVisible(false);
		this.tick_icon.setVisible(false);
		this.downloading_icon.setVisible(false);

		this.addChild(this.icon);
		this.addChild(this.nameplate);

		this.addChild(this.cross_icon);
		this.addChild(this.tick_icon);
		this.addChild(this.downloading_icon);
	}

	@Override
	public void preDraw()
	{
		if (Minecraft.getMinecraft().isIntegratedServerRunning())
		{
			this.tick_icon.setVisible(false);
			this.cross_icon.setVisible(false);
			this.downloading_icon.setVisible(false);
		}
		else
		{
			this.tick_icon.setVisible(this.directoryNode.isOnClient());
			this.cross_icon.setVisible(!this.directoryNode.isOnClient() && !this.directoryNode.isDownloading());
			this.downloading_icon.setVisible(this.directoryNode.isDownloading() && !this.directoryNode.isOnClient());
		}
	}

	@Override
	protected void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) throws IOException
	{
		super.mouseClicked(mouseX, mouseY, mouseButton);

		if (this.isEnabled() && InputHelper.isHovered(this))
		{
			if (mouseButton == 0)
			{
				this.icon.dim().mod().scale(1.0F).flush();

				final long millisecondsSince = System.currentTimeMillis() - this.lastClickTime;

				this.lastClickTime = System.currentTimeMillis();

				if (millisecondsSince < 200)
				{
					this.directoryNode.onOpen(this.navigator);
					this.navigator.onOpenNode(this.directoryNode);
				}
			}
		}
	}

	@Override
	protected void mouseReleased(final int mouseX, final int mouseY, final int state)
	{
		super.mouseReleased(mouseX, mouseY, state);

		if (this.isEnabled() && InputHelper.isHovered(this))
		{
			this.icon.dim().mod().scale(1.025F).flush();
		}
	}

	@Override
	public void onHovered()
	{
		Gui.drawRect((int) this.dim().x(), (int) this.dim().y(), (int) this.dim().maxX(), (int) this.dim().maxY(), Integer.MAX_VALUE);
	}

	@Override
	public void onHoverEnter()
	{
		this.icon.dim().mod().scale(1.025F).flush();

		this.viewer.getDropdownList().setDropdownElements(this.directoryNode.getRightClickElements(this.navigator));
	}

	@Override
	public void onHoverExit()
	{
		this.icon.dim().mod().scale(1.0F).flush();
	}

}
