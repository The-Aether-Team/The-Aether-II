package com.gildedgames.orbis.client.player.godmode;

import com.gildedgames.aether.api.orbis.IWorldRenderer;
import com.gildedgames.aether.api.orbis.region.IRegion;
import com.gildedgames.aether.api.orbis.util.RotationHelp;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.orbis.client.gui.util.GuiTexture;
import com.gildedgames.orbis.client.renderers.RenderBlueprint;
import com.gildedgames.orbis.client.renderers.RenderShape;
import com.gildedgames.orbis.client.util.rect.Dim2D;
import com.gildedgames.orbis.common.player.PlayerOrbisModule;
import com.gildedgames.orbis.common.player.godmode.GodPowerBlueprint;
import com.gildedgames.orbis.common.util.RaytraceHelp;
import com.gildedgames.orbis.common.world_objects.Blueprint;
import com.google.common.collect.Lists;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;

public class GodPowerBlueprintClient implements IGodPowerClient
{
	private static final ResourceLocation TEXTURE = AetherCore.getResource("orbis/godmode/power_icons/blueprint_icon.png");

	private static final int SHAPE_COLOR = 0x99B6FF;

	private final GodPowerBlueprint server;

	private final GuiTexture icon;

	public GodPowerBlueprintClient(final GodPowerBlueprint server)
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
		return "Blueprint";
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

		return held == ItemStack.EMPTY;
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
		final List<IWorldRenderer> renderers = Lists.newArrayList();

		if (this.server.getPlacingBlueprint() != null)
		{
			final BlockPos pos = RaytraceHelp.doOrbisRaytrace(module.getPlayer(), module.raytraceWithRegionSnapping());

			final IRegion renderRegion = RotationHelp.regionFromCenter(pos, this.server.getPlacingBlueprint(), this.server.getPlacingRotation());

			final Blueprint region = new Blueprint(world, renderRegion.getMin(), this.server.getPlacingRotation(), this.server.getPlacingBlueprint());
			final IWorldRenderer renderer = new RenderBlueprint(region, world);

			renderers.add(renderer);

			final RenderShape shape = new RenderShape(region);

			shape.useCustomColors = true;

			shape.colorBorder = SHAPE_COLOR;
			shape.colorGrid = SHAPE_COLOR;

			shape.boxAlpha = 0.1F;

			renderers.add(shape);
		}

		return renderers;
	}
}
