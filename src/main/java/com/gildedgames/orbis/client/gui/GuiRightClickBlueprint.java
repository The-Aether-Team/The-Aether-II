package com.gildedgames.orbis.client.gui;

import com.gildedgames.aether.api.orbis.IWorldObjectManager;
import com.gildedgames.aether.common.capabilities.world.WorldObjectManager;
import com.gildedgames.orbis.client.gui.data.DropdownElement;
import com.gildedgames.orbis.client.gui.util.GuiDropdownList;
import com.gildedgames.orbis.client.gui.util.GuiFrame;
import com.gildedgames.orbis.client.util.rect.Dim2D;
import com.gildedgames.orbis.client.util.rect.Pos2D;
import com.gildedgames.orbis.common.world_objects.Blueprint;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextComponentString;

import java.io.IOException;

public class GuiRightClickBlueprint extends GuiFrame
{
	public static long lastCloseTime;

	private final Blueprint blueprint;

	public GuiRightClickBlueprint(final Blueprint blueprint)
	{
		super(Dim2D.flush());

		this.blueprint = blueprint;
	}

	@Override
	public void init()
	{
		this.addChild(new GuiDropdownList(Pos2D.flush(this.width / 2, this.height / 2),
				new DropdownElement(new TextComponentString("Edit"))
				{
					@Override
					public void onClick(final GuiDropdownList list, final EntityPlayer player)
					{
						Minecraft.getMinecraft().displayGuiScreen(new GuiEditBlueprint(GuiRightClickBlueprint.this.blueprint));
					}
				},
				new DropdownElement(new TextComponentString("Remove"))
				{
					@Override
					public void onClick(final GuiDropdownList list, final EntityPlayer player)
					{
						final IWorldObjectManager manager = WorldObjectManager.get(player.world);

						manager.getGroup(0).removeObject(GuiRightClickBlueprint.this.blueprint);
					}
				},
				new DropdownElement(new TextComponentString("Close"))
				{
					@Override
					public void onClick(final GuiDropdownList list, final EntityPlayer player)
					{

					}
				}));
	}

	@Override
	protected void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) throws IOException
	{
		super.mouseClicked(mouseX, mouseY, mouseButton);

		if (Minecraft.getMinecraft().currentScreen == this)
		{
			Minecraft.getMinecraft().displayGuiScreen(null);
			GuiRightClickBlueprint.lastCloseTime = System.currentTimeMillis();
		}
	}
}
