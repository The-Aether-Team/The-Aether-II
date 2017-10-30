package com.gildedgames.orbis.common.player.godmode.selectors;

import com.gildedgames.aether.api.orbis.IWorldObjectGroup;
import com.gildedgames.aether.api.orbis.shapes.IShape;
import com.gildedgames.aether.common.capabilities.world.WorldObjectManager;
import com.gildedgames.aether.common.network.NetworkingAether;
import com.gildedgames.orbis.client.gui.GuiRightClickBlueprint;
import com.gildedgames.orbis.client.gui.GuiRightClickSelector;
import com.gildedgames.orbis.common.block.BlockFilter;
import com.gildedgames.orbis.common.data.CreationData;
import com.gildedgames.orbis.common.data.ICreationData;
import com.gildedgames.orbis.common.items.ItemsOrbis;
import com.gildedgames.orbis.common.network.packets.PacketWorldObjectAdd;
import com.gildedgames.orbis.common.network.packets.PacketWorldObjectRemove;
import com.gildedgames.orbis.common.player.PlayerOrbisModule;
import com.gildedgames.orbis.common.player.godmode.GodPowerSelect;
import com.gildedgames.orbis.common.player.godmode.IShapeSelector;
import com.gildedgames.orbis.common.util.BlockFilterHelper;
import com.gildedgames.orbis.common.world_objects.Blueprint;
import com.gildedgames.orbis.common.world_objects.WorldShape;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.client.event.MouseEvent;

public class ShapeSelectorSelect implements IShapeSelector
{
	private final GodPowerSelect power;

	public ShapeSelectorSelect(final GodPowerSelect power)
	{
		this.power = power;
	}

	@Override
	public boolean isSelectorActive(final PlayerOrbisModule module, final World world)
	{
		final ItemStack held = module.getEntity().getHeldItemMainhand();

		return held == ItemStack.EMPTY || !(held.getItem() == ItemsOrbis.blockdata || held.getItem() == ItemsOrbis.blueprint);
	}

	@Override
	public boolean canSelectShape(final PlayerOrbisModule module, final IShape shape, final World world)
	{
		return true;
	}

	@Override
	public void onSelect(final PlayerOrbisModule module, final IShape selectedShape, final World world)
	{
		if (world.isRemote)
		{
			return;
		}

		final ItemStack held = module.getEntity().getHeldItemMainhand();

		if (held.getItem() instanceof ItemBlock)
		{
			final ItemStack offhand = module.getEntity().getHeldItem(EnumHand.OFF_HAND);
			final BlockFilter filter;
			if (offhand.getItem() instanceof ItemBlock)
			{
				filter = new BlockFilter(BlockFilterHelper.getNewReplaceLayer(held, offhand));
			}
			else
			{
				filter = new BlockFilter(BlockFilterHelper.getNewFillLayer(held));
			}

			final ICreationData creationData = new CreationData(world, module.getEntity());
			filter.apply(selectedShape, world, creationData);
			return;
		}

		final WorldObjectManager manager = WorldObjectManager.get(world);
		final IWorldObjectGroup group = manager.getGroup(0);

		final WorldShape region = new WorldShape(selectedShape, world);
		group.addObject(region);

		NetworkingAether.sendPacketToPlayer(new PacketWorldObjectAdd(world, group, region), (EntityPlayerMP) module.getEntity());

		if (this.power.getSelectedRegion() != null)
		{
			NetworkingAether.sendPacketToPlayer(new PacketWorldObjectRemove(world, group, this.power.getSelectedRegion()), (EntityPlayerMP) module.getEntity());
			group.removeObject(this.power.getSelectedRegion());
		}

		this.power.setSelectedRegion(region);
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
