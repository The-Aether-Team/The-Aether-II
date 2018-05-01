package com.gildedgames.aether.client.gui.misc;

import com.gildedgames.aether.common.network.NetworkingAether;
import com.gildedgames.aether.common.network.packets.PacketSetShouldRespawnAtCampfire;
import com.gildedgames.orbis_api.client.gui.data.Text;
import com.gildedgames.orbis_api.client.gui.util.GuiFrame;
import com.gildedgames.orbis_api.client.gui.util.GuiText;
import com.gildedgames.orbis_api.client.gui.util.vanilla.GuiButtonVanilla;
import com.gildedgames.orbis_api.client.rect.Dim2D;
import com.gildedgames.orbis_api.client.rect.Pos2D;
import com.gildedgames.orbis_api.util.InputHelper;
import net.minecraft.util.text.TextComponentString;

import java.io.IOException;

public class GuiRespawnAether extends GuiFrame
{
	private GuiText respawnInfo;

	private GuiButtonVanilla bed, campfire;

	@Override
	public void onGuiClosed()
	{
		super.onGuiClosed();
	}

	@Override
	public void init()
	{
		this.setDrawDefaultBackground(true);

		this.dim().mod().width(this.width).height(this.height).flush();

		final Pos2D center = InputHelper.getCenter();

		this.respawnInfo = new GuiText(Dim2D.build().center(true).pos(center).addY(0).flush(),
				new Text(new TextComponentString("Would you like to respawn at your Bed or your last ignited Campfire?"), 1.0F));

		this.bed = new GuiButtonVanilla(Dim2D.build().center(true).pos(center).addY(40).addX(-50).width(60).height(20).flush());
		this.campfire = new GuiButtonVanilla(Dim2D.build().center(true).pos(center).addY(40).addX(50).width(60).height(20).flush());

		this.bed.getInner().displayString = "Bed";
		this.campfire.getInner().displayString = "Campfire";

		this.addChildren(this.respawnInfo, this.bed, this.campfire);
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks)
	{
		this.drawDefaultBackground();
		super.drawScreen(mouseX, mouseY, partialTicks);
	}

	@Override
	public void draw()
	{

	}

	@Override
	protected void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) throws IOException
	{
		super.mouseClicked(mouseX, mouseY, mouseButton);

		if (InputHelper.isHovered(this.bed))
		{
			this.mc.player.respawnPlayer();
			this.mc.displayGuiScreen(null);
		}
		else if (InputHelper.isHovered(this.campfire))
		{
			NetworkingAether.sendPacketToServer(new PacketSetShouldRespawnAtCampfire());
			this.mc.displayGuiScreen(null);
		}
	}
}
