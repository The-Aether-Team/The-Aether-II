package com.gildedgames.orbis.client.player.godmode;

import com.gildedgames.aether.api.orbis.IWorldRenderer;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.orbis.client.gui.util.GuiTexture;
import com.gildedgames.orbis.client.util.rect.Dim2D;
import com.gildedgames.orbis.common.player.PlayerOrbisModule;
import com.gildedgames.orbis.common.player.godmode.GodPowerDelete;
import com.gildedgames.orbis.common.player.godmode.GodPowerSelect;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import java.util.Collections;
import java.util.List;

public class GodPowerSelectClient implements IGodPowerClient
{
	private static final ResourceLocation TEXTURE = AetherCore.getResource("orbis/godmode/power_icons/select_icon.png");

	private final GuiTexture icon;

	private final GodPowerSelect server;

	public GodPowerSelectClient(final GodPowerSelect server)
	{
		this.server = server;

		this.icon = new GuiTexture(Dim2D.build().width(14).height(14).flush(), TEXTURE);
	}

	@Override
	public String displayName()
	{
		return "Select";
	}

	@Override
	public GuiTexture getIcon()
	{
		return this.icon;
	}

	@Override
	public boolean has3DCursor(PlayerOrbisModule module)
	{
		return true;
	}

	@Override
	public float minFade3DCursor(PlayerOrbisModule module)
	{
		return 0;
	}

	@Override
	public int getShapeColor(PlayerOrbisModule module)
	{
		return 0;
	}

	@Override
	public List<IWorldRenderer> getActiveRenderers(PlayerOrbisModule module, World world)
	{
		return Collections.emptyList();
	}
}
