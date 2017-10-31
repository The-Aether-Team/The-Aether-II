package com.gildedgames.orbis.client.player.godmode;

import com.gildedgames.aether.api.orbis.IShape;
import com.gildedgames.aether.api.orbis.IWorldRenderer;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.orbis.client.gui.GuiRightClickBlueprint;
import com.gildedgames.orbis.client.gui.GuiRightClickSelector;
import com.gildedgames.orbis.client.gui.util.GuiTexture;
import com.gildedgames.orbis.client.renderers.RenderShape;
import com.gildedgames.orbis.client.util.rect.Dim2D;
import com.gildedgames.orbis.common.player.PlayerOrbisModule;
import com.gildedgames.orbis.common.player.godmode.GodPowerSelect;
import com.gildedgames.orbis.common.world_objects.Blueprint;
import com.gildedgames.orbis.common.world_objects.WorldShape;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.client.event.MouseEvent;

import java.util.Collections;
import java.util.List;

public class GodPowerSelectClient implements IGodPowerClient
{
	private static final ResourceLocation TEXTURE = AetherCore.getResource("orbis/godmode/power_icons/select_icon.png");

	private static final int SHAPE_COLOR = 0x999999;

	private final GuiTexture icon;

	private final GodPowerSelect server;

	private final ItemStack prevItemstack = null;

	private IWorldRenderer renderer;

	private RenderShape renderShape;

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
	public boolean has3DCursor(final PlayerOrbisModule module)
	{
		return true;
	}

	@Override
	public float minFade3DCursor(final PlayerOrbisModule module)
	{
		return 0;
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

	@Override
	public boolean onRightClickShape(final PlayerOrbisModule module, final IShape selectedShape, final MouseEvent event)
	{
		final EntityPlayer entity = module.getEntity();

		final int x = MathHelper.floor(entity.posX);
		final int y = MathHelper.floor(entity.posY);
		final int z = MathHelper.floor(entity.posZ);

		final boolean playerInside = selectedShape.contains(x, y, z) || selectedShape.contains(x, MathHelper.floor(entity.posY + entity.height), z);

		if (entity.world.isRemote && !playerInside)
		{
			if (System.currentTimeMillis() - GuiRightClickBlueprint.lastCloseTime > 200)
			{
				if (selectedShape instanceof Blueprint)
				{
					Minecraft.getMinecraft().displayGuiScreen(new GuiRightClickBlueprint((Blueprint) selectedShape));
				}
				else if (selectedShape instanceof WorldShape)
				{
					Minecraft.getMinecraft().displayGuiScreen(new GuiRightClickSelector((WorldShape) selectedShape));
				}
				else
				{
					Minecraft.getMinecraft().displayGuiScreen(new GuiRightClickSelector(new WorldShape(selectedShape, entity.world)));
				}

				return false;
			}
		}
		return true;
	}
}
