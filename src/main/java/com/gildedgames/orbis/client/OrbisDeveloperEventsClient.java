package com.gildedgames.orbis.client;

import com.gildedgames.aether.api.orbis.IShape;
import com.gildedgames.aether.api.orbis_core.block.BlockDataContainer;
import com.gildedgames.aether.api.orbis_core.block.BlockFilter;
import com.gildedgames.aether.api.orbis_core.util.BlockFilterHelper;
import com.gildedgames.aether.api.orbis_core.util.RotationHelp;
import com.gildedgames.aether.api.world_object.IWorldObjectGroup;
import com.gildedgames.aether.common.capabilities.entity.player.PlayerAether;
import com.gildedgames.aether.common.capabilities.world.WorldObjectManager;
import com.gildedgames.aether.common.network.NetworkingAether;
import com.gildedgames.orbis.client.gui.GuiChoiceMenuHolder;
import com.gildedgames.orbis.client.gui.GuiChoiceMenuPowers;
import com.gildedgames.orbis.client.gui.GuiChoiceMenuSelectionTypes;
import com.gildedgames.orbis.client.renderers.AirSelectionRenderer;
import com.gildedgames.orbis.common.Orbis;
import com.gildedgames.orbis.common.items.ItemBlockDataContainer;
import com.gildedgames.orbis.common.items.ItemsOrbis;
import com.gildedgames.orbis.common.network.packets.*;
import com.gildedgames.orbis.common.player.PlayerOrbisModule;
import com.gildedgames.orbis.common.player.PlayerSelectionModule;
import com.gildedgames.orbis.common.player.godmode.GodPowerBlueprint;
import com.gildedgames.orbis.common.player.godmode.GodPowerSelect;
import com.gildedgames.orbis.common.player.godmode.IShapeSelector;
import com.gildedgames.orbis.common.util.OrbisRaytraceHelp;
import com.gildedgames.orbis.common.util.RaytraceHelp;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiIngameMenu;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.client.event.MouseEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;

public class OrbisDeveloperEventsClient
{

	private static final Minecraft mc = Minecraft.getMinecraft();

	private static double prevReach;

	private static IShape prevShape;

	private static GuiChoiceMenuHolder choiceMenuHolder;

	@SubscribeEvent
	public static void onGuiOpen(final GuiOpenEvent event)
	{
		if (event.getGui() instanceof GuiIngameMenu)
		{
			final PlayerSelectionModule selectionModule = PlayerAether.getPlayer(mc.player).getSelectionModule();
			final PlayerOrbisModule module = PlayerOrbisModule.get(mc.player);

			if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE))
			{
				if (selectionModule.getActiveSelection() != null)
				{
					selectionModule.clearSelection();

					NetworkingAether.sendPacketToServer(new PacketClearSelection());

					event.setCanceled(true);
				}

				if (module.powers().getSelectPower().getSelectedRegion() != null)
				{
					final WorldObjectManager manager = WorldObjectManager.get(mc.world);
					final IWorldObjectGroup group = manager.getGroup(0);

					NetworkingAether.sendPacketToServer(new PacketClearSelectedRegion());
					NetworkingAether.sendPacketToServer(new PacketWorldObjectRemove(mc.world, group, module.powers().getSelectPower().getSelectedRegion()));

					module.powers().getSelectPower().setSelectedRegion(null);

					event.setCanceled(true);
				}
			}
		}

		if (event.getGui() instanceof GuiInventory)
		{
			final Minecraft mc = Minecraft.getMinecraft();

			final PlayerOrbisModule module = PlayerOrbisModule.get(mc.player);

			if (module.powers().getCurrentPower().hasCustomGui())
			{
				module.powers().getCurrentPower().onOpenGui(mc.player);
				NetworkingAether.sendPacketToServer(new PacketOpenGui());

				event.setCanceled(true);
			}
		}
	}

	@SubscribeEvent
	public static void onClienTick(final TickEvent.ClientTickEvent event)
	{
		if (mc.world != null && mc.player != null)
		{
			final PlayerOrbisModule module = PlayerOrbisModule.get(mc.player);
			final GodPowerSelect select = module.powers().getSelectPower();

			if (module.inDeveloperMode())
			{
				final Minecraft mc = Minecraft.getMinecraft();

				final GuiScreen current = Minecraft.getMinecraft().currentScreen;

				final PlayerAether player = PlayerAether.getPlayer(mc.player);

				final double reach = module.getReach();

				if (Keyboard.isKeyDown(OrbisKeyBindings.keyBindIncreaseReach.getKeyCode()))
				{
					player.getOrbisModule().setDeveloperReach(reach + 1);
					NetworkingAether.sendPacketToServer(new PacketDeveloperReach(reach + 1));
				}

				if (Keyboard.isKeyDown(OrbisKeyBindings.keyBindDecreaseReach.getKeyCode()))
				{
					player.getOrbisModule().setDeveloperReach(reach - 1);
					NetworkingAether.sendPacketToServer(new PacketDeveloperReach(reach - 1));
				}

				if (select.getSelectedRegion() != null && (Keyboard.isKeyDown(Keyboard.KEY_LCONTROL) || Keyboard.isKeyDown(Keyboard.KEY_RCONTROL))
						&& OrbisKeyBindings.keyBindCopy.isPressed())
				{
					final BlockDataContainer container = BlockDataContainer.fromShape(mc.player.world, select.getSelectedRegion());
					final ItemStack item = new ItemStack(ItemsOrbis.blockdata);
					ItemBlockDataContainer.setDatacontainer(item, container);

					NetworkingAether.sendPacketToServer(new PacketSetItemStackInHand(item));
					mc.player.inventory.setInventorySlotContents(mc.player.inventory.currentItem, item);
				}

				if (OrbisKeyBindings.keyBindChangeSelectionMode.isPressed())
				{
					player.getSelectionModule().nextSelectionMode();
					NetworkingAether.sendPacketToServer(new PacketNextSelectionMode());
				}

				player.getSelectionModule().processSelectionMode();

				if (OrbisKeyBindings.keyBindDelete.isPressed())
				{
					if (select.getSelectedRegion() != null)
					{
						final BlockFilter filter = new BlockFilter(BlockFilterHelper.getNewDeleteLayer(mc.player.getHeldItemMainhand()));

						NetworkingAether.sendPacketToServer(new PacketFilterShape(select.getSelectedRegion(), filter));
					}
				}

				if (OrbisKeyBindings.keyBindRotate.isPressed())
				{
					final GodPowerBlueprint power = module.powers().getBlueprintPower();

					if (power.getPlacingBlueprint() != null)
					{
						power.setPlacingRotation(RotationHelp.getNextRotation(power.getPlacingRotation(), true));
						NetworkingAether.sendPacketToServer(new PacketRotateBlueprint());
					}
				}

				if (Keyboard.isKeyDown(OrbisKeyBindings.keyBindFindPower.getKeyCode()))
				{
					if (current == null)
					{
						final GuiChoiceMenuHolder choiceMenuHolder = new GuiChoiceMenuHolder(new GuiChoiceMenuPowers(module),
								new GuiChoiceMenuSelectionTypes(module));

						Minecraft.getMinecraft().displayGuiScreen(choiceMenuHolder);
					}
				}
				else if (current instanceof GuiChoiceMenuHolder)
				{
					final GuiChoiceMenuHolder menu = (GuiChoiceMenuHolder) current;

					if (menu.getCurrentMenu().getHoveredChoice() != null)
					{
						menu.getCurrentMenu().getHoveredChoice().onSelect(module);
					}

					Minecraft.getMinecraft().displayGuiScreen(null);
				}
			}

			final PlayerAether player = PlayerAether.getPlayer(mc.player);

			if (player.getSelectionModule().getActiveSelection() == null && prevShape != null && prevReach != 0.0D)
			{
				prevShape = null;
				player.getOrbisModule().setDeveloperReach(prevReach);

				NetworkingAether.sendPacketToServer(new PacketDeveloperReach(prevReach));
			}
		}
		else
		{
			Orbis.stopProjectManager();
		}
	}

	@SubscribeEvent
	public static void onMouseEvent(final MouseEvent event)
	{
		final PlayerAether player = PlayerAether.getPlayer(Minecraft.getMinecraft().player);
		final PlayerOrbisModule module = player.getOrbisModule();

		final IShapeSelector selector = module.powers().getCurrentPower().getShapeSelector();

		if (event.getButton() == 0 || event.getButton() == 1)
		{
			if (module.inDeveloperMode() && selector.isSelectorActive(module, mc.world))
			{
				event.setCanceled(true);

				final IShape selectedShape = player.getOrbisModule().getSelectedRegion();

				if (selectedShape == null ||
						module.powers().getCurrentPower().getClientHandler().onRightClickShape(module, selectedShape, event))
				{
					final PlayerSelectionModule selectionModule = module.getPlayer().getSelectionModule();

					if (!event.isButtonstate() && selectionModule.getActiveSelection() != null
							|| event.isButtonstate() && selectionModule.getActiveSelection() == null)
					{
						final BlockPos pos = OrbisRaytraceHelp.raytraceNoSnapping(module.getEntity());
						selectionModule.setActiveSelectionCorner(pos);
					}
				}
			}
		}

		final IShape activeRegion = player.getSelectionModule().getActiveSelection();

		//Change reach
		if (OrbisKeyBindings.keyBindControl.isKeyDown() || activeRegion != null)
		{
			if (activeRegion != null && prevShape == null)
			{
				prevShape = activeRegion;

				prevReach = player.getOrbisModule().getDeveloperReach();
			}

			if (OrbisKeyBindings.keyBindControl.isKeyDown())
			{
				prevReach = player.getOrbisModule().getDeveloperReach();
			}

			final RayTraceResult blockRaytrace = RaytraceHelp
					.rayTraceNoBlocks(player.getOrbisModule().getReach(), AirSelectionRenderer.PARTIAL_TICKS, player.getEntity());
			double reach = player.getOrbisModule().getReach();

			if (event.getDwheel() > 0)
			{
				player.getOrbisModule().setDeveloperReach(reach + 1);
				NetworkingAether.sendPacketToServer(new PacketDeveloperReach(reach + 1));

				event.setCanceled(true);
			}
			else if (event.getDwheel() < 0)
			{
				if (blockRaytrace != null)
				{
					final int x = MathHelper.floor(player.getEntity().posX);
					final int y = MathHelper.floor(player.getEntity().posY);
					final int z = MathHelper.floor(player.getEntity().posZ);

					final double deltaX = x - blockRaytrace.hitVec.xCoord;
					final double deltaY = y - blockRaytrace.hitVec.yCoord;
					final double deltaZ = z - blockRaytrace.hitVec.zCoord;

					final float distance = MathHelper.floor((float) Math.sqrt(deltaX * deltaX + deltaY * deltaY + deltaZ * deltaZ));

					player.getOrbisModule().setDeveloperReach(distance);
					NetworkingAether.sendPacketToServer(new PacketDeveloperReach(distance));

					reach = player.getOrbisModule().getReach();
				}

				player.getOrbisModule().setDeveloperReach(reach - 1);
				NetworkingAether.sendPacketToServer(new PacketDeveloperReach(reach - 1));

				event.setCanceled(true);
			}
		}
	}

}
