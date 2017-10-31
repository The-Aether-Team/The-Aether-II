package com.gildedgames.orbis.client.gui.util.directory;

import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.orbis.client.gui.data.directory.IDirectoryNavigator;
import com.gildedgames.orbis.client.gui.data.directory.IDirectoryNavigatorListener;
import com.gildedgames.orbis.client.gui.data.directory.IDirectoryNode;
import com.gildedgames.orbis.client.gui.util.*;
import com.gildedgames.orbis.client.util.rect.Dim2D;
import com.gildedgames.orbis.client.util.rect.ModDim2D;
import com.gildedgames.orbis.client.util.rect.Pos2D;
import com.gildedgames.orbis.client.util.rect.Rect;
import com.gildedgames.orbis.common.util.InputHelper;
import com.google.common.collect.Lists;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.ResourceLocation;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class GuiDirectoryViewer extends GuiFrame implements IDirectoryNavigatorListener
{

	private static final ResourceLocation VIEWER_BACKDROP = AetherCore.getResource("orbis/navigator/directory_viewer.png");

	private final IDirectoryNavigator navigator;

	private final List<GuiDirectoryNode> visibleFiles = Lists.newArrayList();

	private int currentScroll, maxScroll;

	private GuiAbstractButton refreshButton, backButton, forwardButton;

	private GuiDropdownList dropdownList;

	public GuiDirectoryViewer(final Pos2D pos, final IDirectoryNavigator navigator)
	{
		super(Dim2D.build().width(176).height(190).pos(pos).flush());

		this.navigator = navigator;
		this.navigator.addListener(this);
	}

	public GuiDropdownList getDropdownList()
	{
		return this.dropdownList;
	}

	public IDirectoryNavigator getNavigator()
	{
		return this.navigator;
	}

	/**
	 * @param nodes
	 * @param scrollCount Represents how many node rows are scrolled down.
	 * @param width
	 * @param height
	 * @param padding
	 * @return
	 */
	private List<GuiDirectoryNode> listVisibleFiles(final List<IDirectoryNode> nodes, final int scrollCount, final int xOffset, final int yOffset,
			final int width, final int height,
			final int padding)
	{
		final List<GuiDirectoryNode> files = Lists.newArrayList();

		final Rect guiFileRect = ModDim2D.build().mod().width(GuiDirectoryNode.WIDTH).height(GuiDirectoryNode.HEIGHT).scale(0.75F).flush();

		final int guiFileWidth = (int) (guiFileRect.width() + padding);
		final int guiFileHeight = (int) (guiFileRect.height() + padding);

		final int possibleNumberOfColumns = width / guiFileWidth;
		final int possibleNumberOfRows = height / guiFileHeight;

		if (nodes.size() < possibleNumberOfColumns)
		{
			this.currentScroll = 0;
			this.maxScroll = 0;
		}
		else
		{
			this.maxScroll = (nodes.size() / possibleNumberOfColumns) - possibleNumberOfRows;

			// Add last row if lingering nodes
			if (nodes.size() % possibleNumberOfColumns > 0)
			{
				this.maxScroll++;
			}
		}

		final int numberOfColumns = Math.min(nodes.size(), possibleNumberOfColumns);

		final int frontNodeIndex = scrollCount * numberOfColumns;
		final int backNodeIndex = Math.min(nodes.size(), frontNodeIndex + (possibleNumberOfRows * numberOfColumns));

		for (int i = frontNodeIndex; i < backNodeIndex; i++)
		{
			final IDirectoryNode node = nodes.get(i);

			final int freshIndex = i - frontNodeIndex;

			final int column = freshIndex % numberOfColumns;
			final int row = freshIndex / possibleNumberOfColumns;

			final Pos2D pos = Pos2D.flush(xOffset + (column * guiFileWidth), yOffset + (row * guiFileHeight));

			final GuiDirectoryNode guiFile = new GuiDirectoryNode(pos, node, this);

			guiFile.dim().mod().scale(guiFileRect.scale()).flush();

			files.add(guiFile);
		}

		return files;
	}

	private void refreshFiles()
	{
		final List<GuiDirectoryNode> guiNodes = this
				.listVisibleFiles(this.navigator.getNodes(), this.currentScroll, 8, 22, (int) this.dim().width() - 8, (int) this.dim().height() - 22, 0);

		this.visibleFiles.forEach(this::removeChild);
		this.visibleFiles.clear();

		this.visibleFiles.addAll(guiNodes);
		this.visibleFiles.forEach(this::addChild);

		if (this.dropdownList != null)
		{
			this.removeChild(this.dropdownList);
			this.addChildNoMods(this.dropdownList);
		}
	}

	public void displayDropdownList(final int x, final int y)
	{
		this.dropdownList.dim().mod().pos(x, y).flush();
		this.dropdownList.setVisible(true);
	}

	@Override
	protected void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) throws IOException
	{
		if (!InputHelper.isHovered(this.dropdownList))
		{
			this.dropdownList.setVisible(false);
		}
		else
		{
			if (mouseButton == 0)
			{
				this.dropdownList.publicMouseClicked(mouseX, mouseY, mouseButton);
				this.dropdownList.setVisible(false);

				this.dropdownList.setDropdownElements(Collections.emptyList());
			}

			return;
		}

		if (this.isEnabled() && InputHelper.isHovered(this))
		{
			if (mouseButton == 1)
			{
				this.displayDropdownList(mouseX, mouseY);

				return;
			}
		}

		super.mouseClicked(mouseX, mouseY, mouseButton);
	}

	@Override
	public void handleMouseInput() throws IOException
	{
		super.handleMouseInput();
	}

	@Override
	public void onMouseWheel(final int state)
	{
		super.onMouseWheel(state);

		this.currentScroll -= (state / 120);

		this.currentScroll = Math.max(0, Math.min(this.maxScroll, this.currentScroll));

		this.refreshFiles();
	}

	@Override
	public void draw()
	{
		this.backButton.setEnabled(this.navigator.canGoBack());
		this.forwardButton.setEnabled(this.navigator.canGoForward());

		super.draw();

		Gui.drawRect((int) this.dim().x() + 8, (int) this.dim().y() + 22, (int) this.dim().maxX() - 8, (int) this.dim().maxY() - 8, Integer.MIN_VALUE);
	}

	@Override
	public void init()
	{
		this.refreshButton = GuiFactory.createRefreshButton();
		this.backButton = GuiFactory.createLeftArrowButton();
		this.forwardButton = GuiFactory.createRightArrowButton();

		this.refreshButton.dim().mod().pos(this.dim().width() - 18, 7).flush();

		this.backButton.dim().mod().pos(8, 6).flush();
		this.forwardButton.dim().mod().pos(28, 6).flush();

		this.backButton.addClickEvent(this.navigator::back);
		this.forwardButton.addClickEvent(this.navigator::forward);

		this.refreshButton.addClickEvent(() ->
		{
			this.currentScroll = 0;
			this.navigator.refresh();
		});

		final GuiTexture backdrop = new GuiTexture(Dim2D.buildWith(this).area().flush(), VIEWER_BACKDROP);

		this.addChild(backdrop);

		this.addChild(this.refreshButton);
		this.addChild(this.backButton);
		this.addChild(this.forwardButton);

		this.dropdownList = new GuiDropdownList(Pos2D.flush());

		this.dropdownList.setVisible(false);

		this.addChildNoMods(this.dropdownList);
	}

	@Override
	public void onNodeOpen(final IDirectoryNavigator navigator, final IDirectoryNode node)
	{

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
		this.currentScroll = 0;
		this.refreshFiles();
	}
}
