package com.gildedgames.orbis.client.player.godmode;

import com.gildedgames.aether.api.orbis.IWorldRenderer;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.orbis.client.gui.util.GuiTexture;
import com.gildedgames.orbis.client.util.rect.Dim2D;
import com.gildedgames.orbis.common.player.PlayerOrbisModule;
import com.gildedgames.orbis.common.player.godmode.GodPowerFill;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import java.util.Collections;
import java.util.List;

public class GodPowerFillClient implements IGodPowerClient
{
	private static final ResourceLocation TEXTURE = AetherCore.getResource("orbis/godmode/power_icons/fill_icon.png");

	private static final int SHAPE_COLOR = 0x52b015;

	private final GodPowerFill server;

	private final GuiTexture icon;

	public GodPowerFillClient(final GodPowerFill server)
	{
		this.server = server;

		this.icon = new GuiTexture(Dim2D.build().width(14).height(14).flush(), TEXTURE);
	}

	@Override
	public boolean openGuiScreen()
	{
		return false;
	}

	@Override
	public String displayName()
	{
		return "Fill";
	}

	@Override
	public GuiTexture getIcon()
	{
		return this.icon;
	}

	@Override
	public boolean has3DCursor(final PlayerOrbisModule module)
	{
		final ItemStack held = module.getEntity().getHeldItemMainhand();

		return held.getItem() instanceof ItemBlock;
	}

	@Override
	public float minFade3DCursor(final PlayerOrbisModule module)
	{
		return 0.0F;
	}

	@Override
	public int getShapeColor(final PlayerOrbisModule module)
	{
		return SHAPE_COLOR;
	}

	@Override
	public List<IWorldRenderer> getActiveRenderers(final PlayerOrbisModule module, final World world)
	{
		return Collections.emptyList();
	}
}
