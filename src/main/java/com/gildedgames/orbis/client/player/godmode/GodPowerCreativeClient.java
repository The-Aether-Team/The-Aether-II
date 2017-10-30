package com.gildedgames.orbis.client.player.godmode;

import com.gildedgames.aether.api.orbis.IWorldRenderer;
import com.gildedgames.aether.api.orbis.shapes.IShape;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.orbis.client.gui.util.GuiTexture;
import com.gildedgames.orbis.client.util.rect.Dim2D;
import com.gildedgames.orbis.common.player.PlayerOrbisModule;
import com.gildedgames.orbis.common.player.godmode.GodPowerCreative;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.client.event.MouseEvent;

import java.util.Collections;
import java.util.List;

public class GodPowerCreativeClient implements IGodPowerClient
{
	private static final ResourceLocation TEXTURE = AetherCore.getResource("orbis/godmode/power_icons/creative_icon.png");

	private final GodPowerCreative server;

	private final GuiTexture icon;

	public GodPowerCreativeClient(final GodPowerCreative server)
	{
		this.server = server;

		this.icon = new GuiTexture(Dim2D.build().width(14).height(14).flush(), TEXTURE);
	}

	@Override
	public String displayName()
	{
		return "Creative";
	}

	@Override
	public GuiTexture getIcon()
	{
		return this.icon;
	}

	@Override
	public boolean has3DCursor(final PlayerOrbisModule module)
	{
		return false;
	}

	@Override
	public float minFade3DCursor(final PlayerOrbisModule module)
	{
		return 0.0F;
	}

	@Override
	public int getShapeColor(final PlayerOrbisModule module)
	{
		return 0;
	}

	@Override
	public List<IWorldRenderer> getActiveRenderers(final PlayerOrbisModule module, final World world)
	{
		return Collections.emptyList();
	}

	@Override
	public boolean onRightClickShape(final PlayerOrbisModule module, final IShape selectedShape, final MouseEvent event)
	{
		return false;
	}
}
