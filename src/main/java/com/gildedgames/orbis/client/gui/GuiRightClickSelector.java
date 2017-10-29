package com.gildedgames.orbis.client.gui;

import com.gildedgames.aether.api.orbis.IWorldObject;
import com.gildedgames.aether.api.orbis.IWorldObjectManager;
import com.gildedgames.aether.api.orbis.region.IRegion;
import com.gildedgames.aether.api.orbis.region.Region;
import com.gildedgames.aether.api.orbis.shapes.IShape;
import com.gildedgames.aether.common.capabilities.world.WorldObjectManager;
import com.gildedgames.aether.common.network.NetworkingAether;
import com.gildedgames.orbis.client.gui.data.DropdownElement;
import com.gildedgames.orbis.client.gui.util.GuiDropdownList;
import com.gildedgames.orbis.client.gui.util.GuiFrame;
import com.gildedgames.orbis.client.util.rect.Dim2D;
import com.gildedgames.orbis.client.util.rect.Pos2D;
import com.gildedgames.orbis.common.block.BlockFilter;
import com.gildedgames.orbis.common.network.packets.PacketOrbisFilterShape;
import com.gildedgames.orbis.common.network.packets.PacketOrbisWorldObjectRemove;
import com.gildedgames.orbis.common.util.BlockFilterHelper;
import com.gildedgames.orbis.common.world_objects.WorldRegion;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextComponentString;

import java.io.IOException;

public class GuiRightClickSelector extends GuiFrame
{
	private final WorldRegion region;

	public GuiRightClickSelector(final WorldRegion region)
	{
		super(Dim2D.flush());

		this.region = region;
	}

	@Override
	public void init()
	{
		this.addChild(new GuiDropdownList(Pos2D.flush(this.width / 2, this.height / 2),
				GuiRightClickElements.delete(this.region),
				GuiRightClickElements.copy(this.region),
				GuiRightClickElements.close()));
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
