package com.gildedgames.orbis.client.gui.util;

import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.orbis.client.gui.data.DropdownElement;
import com.gildedgames.orbis.client.gui.data.IDropdownElement;
import com.gildedgames.orbis.client.gui.data.directory.IDirectoryNavigator;
import com.gildedgames.orbis.client.util.rect.Dim2D;
import com.gildedgames.orbis.client.util.rect.Rect;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentString;

import java.io.File;
import java.util.Collections;

public class GuiFactory
{

	public static final ResourceLocation REFRESH_ICON = AetherCore.getResource("orbis/navigator/refresh.png");

	public static final ResourceLocation REFRESH_ICON_CLICKED = AetherCore.getResource("orbis/navigator/refresh_clicked.png");

	public static final ResourceLocation REFRESH_ICON_DISABLED = AetherCore.getResource("orbis/navigator/refresh_disabled.png");

	public static final ResourceLocation REFRESH_ICON_HOVERED = AetherCore.getResource("orbis/navigator/refresh_hovered.png");

	public static final ResourceLocation LEFT_ARROW_ICON = AetherCore.getResource("orbis/navigator/left_arrow.png");

	public static final ResourceLocation LEFT_ARROW_ICON_CLICKED = AetherCore.getResource("orbis/navigator/left_arrow_clicked.png");

	public static final ResourceLocation LEFT_ARROW_ICON_DISABLED = AetherCore.getResource("orbis/navigator/left_arrow_disabled.png");

	public static final ResourceLocation LEFT_ARROW_ICON_HOVERED = AetherCore.getResource("orbis/navigator/left_arrow_hovered.png");

	public static final ResourceLocation RIGHT_ARROW_ICON = AetherCore.getResource("orbis/navigator/right_arrow.png");

	public static final ResourceLocation RIGHT_ARROW_ICON_CLICKED = AetherCore.getResource("orbis/navigator/right_arrow_clicked.png");

	public static final ResourceLocation RIGHT_ARROW_ICON_DISABLED = AetherCore.getResource("orbis/navigator/right_arrow_disabled.png");

	public static final ResourceLocation RIGHT_ARROW_ICON_HOVERED = AetherCore.getResource("orbis/navigator/right_arrow_hovered.png");

	public static final ResourceLocation FORGE_BUTTON = AetherCore.getResource("orbis/filter_gui/forge_button.png");

	public static final ResourceLocation FORGE_BUTTON_CLICKED = AetherCore.getResource("orbis/filter_gui/forge_button_clicked.png");

	public static final ResourceLocation FORGE_BUTTON_DISABLED = AetherCore.getResource("orbis/filter_gui/forge_button_disabled.png");

	public static final ResourceLocation FORGE_BUTTON_HOVERED = AetherCore.getResource("orbis/filter_gui/forge_button_hovered.png");

	private GuiFactory()
	{

	}

	public static IDropdownElement createCloseDropdownElement(final File file, final IDirectoryNavigator navigator)
	{
		return new DropdownElement(new TextComponentString("Close"))
		{
			@Override
			public void onClick(final GuiDropdownList list, final EntityPlayer player)
			{
				list.setDropdownElements(Collections.emptyList());
			}
		};
	}

	public static IDropdownElement createDeleteFileDropdownElement(final File file, final IDirectoryNavigator navigator)
	{
		return new DropdownElement(new TextComponentString("Delete"))
		{
			@Override
			public void onClick(final GuiDropdownList list, final EntityPlayer player)
			{
				if (file.isDirectory())
				{
					//FileHelper.deleteDirectory(file);
				}
				else
				{
					//file.delete();
				}

				list.setDropdownElements(Collections.emptyList());
				list.setVisible(false);

				navigator.refresh();
			}
		};
	}

	public static GuiAbstractButton createForgeButton()
	{
		final Rect rect = Dim2D.build().width(22).height(22).flush();

		final GuiTexture defaultState = new GuiTexture(rect, FORGE_BUTTON);
		final GuiTexture hoveredState = new GuiTexture(rect, FORGE_BUTTON_HOVERED);
		final GuiTexture clickedState = new GuiTexture(rect, FORGE_BUTTON_CLICKED);
		final GuiTexture disabledState = new GuiTexture(rect, FORGE_BUTTON_DISABLED);

		final GuiAbstractButton button = new GuiAbstractButton(rect, defaultState, hoveredState, clickedState, disabledState);

		return button;
	}

	public static GuiAbstractButton createRefreshButton()
	{
		final Rect rect = Dim2D.build().width(10).height(10).flush();

		final GuiTexture defaultState = new GuiTexture(rect, REFRESH_ICON);
		final GuiTexture hoveredState = new GuiTexture(rect, REFRESH_ICON_HOVERED);
		final GuiTexture clickedState = new GuiTexture(rect, REFRESH_ICON_CLICKED);
		final GuiTexture disabledState = new GuiTexture(rect, REFRESH_ICON_DISABLED);

		final GuiAbstractButton button = new GuiAbstractButton(rect, defaultState, hoveredState, clickedState, disabledState);

		return button;
	}

	public static GuiAbstractButton createLeftArrowButton()
	{
		final Rect rect = Dim2D.build().width(15).height(12).flush();

		final GuiTexture defaultState = new GuiTexture(rect, LEFT_ARROW_ICON);
		final GuiTexture hoveredState = new GuiTexture(rect, LEFT_ARROW_ICON_HOVERED);
		final GuiTexture clickedState = new GuiTexture(rect, LEFT_ARROW_ICON_CLICKED);
		final GuiTexture disabledState = new GuiTexture(rect, LEFT_ARROW_ICON_DISABLED);

		final GuiAbstractButton button = new GuiAbstractButton(rect, defaultState, hoveredState, clickedState, disabledState);

		return button;
	}

	public static GuiAbstractButton createRightArrowButton()
	{
		final Rect rect = Dim2D.build().width(15).height(12).flush();

		final GuiTexture defaultState = new GuiTexture(rect, RIGHT_ARROW_ICON);
		final GuiTexture hoveredState = new GuiTexture(rect, RIGHT_ARROW_ICON_HOVERED);
		final GuiTexture clickedState = new GuiTexture(rect, RIGHT_ARROW_ICON_CLICKED);
		final GuiTexture disabledState = new GuiTexture(rect, RIGHT_ARROW_ICON_DISABLED);

		final GuiAbstractButton button = new GuiAbstractButton(rect, defaultState, hoveredState, clickedState, disabledState);

		return button;
	}

}
