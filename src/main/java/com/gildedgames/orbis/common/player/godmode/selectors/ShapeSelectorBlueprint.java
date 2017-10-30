package com.gildedgames.orbis.common.player.godmode.selectors;

import com.gildedgames.aether.api.orbis.IWorldObjectGroup;
import com.gildedgames.aether.api.orbis.shapes.IShape;
import com.gildedgames.aether.common.capabilities.world.WorldObjectManager;
import com.gildedgames.aether.common.network.NetworkingAether;
import com.gildedgames.orbis.client.gui.GuiRightClickBlueprint;
import com.gildedgames.orbis.common.network.packets.PacketWorldObjectAdd;
import com.gildedgames.orbis.common.player.PlayerOrbisModule;
import com.gildedgames.orbis.common.player.godmode.GodPowerBlueprint;
import com.gildedgames.orbis.common.player.godmode.IShapeSelector;
import com.gildedgames.orbis.common.world_objects.Blueprint;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.client.event.MouseEvent;

public class ShapeSelectorBlueprint implements IShapeSelector
{
	private final GodPowerBlueprint power;

	public ShapeSelectorBlueprint(final GodPowerBlueprint power)
	{
		this.power = power;
	}

	@Override
	public boolean isSelectorActive(final PlayerOrbisModule module, final World world)
	{
		final ItemStack held = module.getEntity().getHeldItemMainhand();

		return this.power.getPlacingBlueprint() == null && held == ItemStack.EMPTY;
	}

	@Override
	public boolean canSelectShape(final PlayerOrbisModule module, final IShape shape, final World world)
	{
		final WorldObjectManager manager = WorldObjectManager.get(world);
		final IWorldObjectGroup group = manager.getGroup(0);

		return !group.isIntersectingShapes(shape);
	}

	@Override
	public void onSelect(final PlayerOrbisModule module, final IShape selectedShape, final World world)
	{
		final WorldObjectManager manager = WorldObjectManager.get(world);
		final IWorldObjectGroup group = manager.getGroup(0);

		final Blueprint blueprint = new Blueprint(world, selectedShape.getBoundingBox());

		if (!world.isRemote)
		{
			if (world.getMinecraftServer().isDedicatedServer())
			{
				group.addObject(blueprint);
			}

			NetworkingAether.sendPacketToDimension(new PacketWorldObjectAdd(world, group, blueprint), world.provider.getDimension());
		}
	}

	@Override
	public boolean onRightClickShape(final PlayerOrbisModule module, final IShape selectedShape, final MouseEvent event)
	{
		final EntityPlayer entity = module.getEntity();

		final int x = MathHelper.floor(entity.posX);
		final int y = MathHelper.floor(entity.posY);
		final int z = MathHelper.floor(entity.posZ);

		if (selectedShape instanceof Blueprint)
		{
			final boolean playerInside = selectedShape.contains(x, y, z) || selectedShape.contains(x, MathHelper.floor(entity.posY + entity.height), z);

			if (entity.world.isRemote && !playerInside)
			{
				if (System.currentTimeMillis() - GuiRightClickBlueprint.lastCloseTime > 200)
				{
					Minecraft.getMinecraft().displayGuiScreen(new GuiRightClickBlueprint((Blueprint) selectedShape));
				}
			}
			return false;
		}
		return true;
	}
}
