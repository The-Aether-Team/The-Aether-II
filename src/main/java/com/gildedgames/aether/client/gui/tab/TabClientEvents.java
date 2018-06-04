package com.gildedgames.aether.client.gui.tab;

import com.gildedgames.aether.api.AetherAPI;
import com.gildedgames.aether.api.registry.tab.ITabClient;
import com.gildedgames.aether.api.registry.tab.ITabGroup;
import com.gildedgames.aether.api.registry.tab.ITabGroupHandler;
import com.gildedgames.aether.common.network.NetworkingAether;
import com.gildedgames.aether.common.network.packets.PacketOpenTab;
import com.gildedgames.aether.common.registry.content.DimensionsAether;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Mouse;

@Mod.EventBusSubscriber(Side.CLIENT)
public class TabClientEvents
{
	@SideOnly(Side.CLIENT)
	private final static RenderTabGroup tabGroupRenderer = new RenderTabGroup();

	@SubscribeEvent
	public static void onGuiOpen(final GuiOpenEvent event)
	{
		if (Minecraft.getMinecraft().world == null || Minecraft.getMinecraft().world.provider.getDimensionType() != DimensionsAether.AETHER)
		{
			return;
		}

		final GuiScreen gui = event.getGui();

		final ITabGroupHandler groupHandler = AetherAPI.content().tabs().getActiveGroup();

		if (groupHandler != null)
		{
			final ITabClient selectedTab = groupHandler.getClientGroup().getSelectedTab();

			if (gui != null && selectedTab.isTabValid(gui))
			{
				return;
			}

			AetherAPI.content().tabs().setActiveGroup(null);
		}

		for (final ITabGroupHandler tabGroupHandler : AetherAPI.content().tabs().getRegisteredTabGroups().values())
		{
			final ITabGroup<ITabClient> tabGroup = tabGroupHandler.getClientGroup();

			for (final ITabClient tab : tabGroup.getTabs())
			{
				if (event.getGui() != null && tab.isTabValid(gui))
				{
					if (tabGroup.getRememberSelectedTab() && tabGroup.getRememberedTab() != null)
					{
						tabGroup.setSelectedTab(tabGroup.getRememberedTab());
					}
					else
					{
						tabGroup.setSelectedTab(tab);
					}

					AetherAPI.content().tabs().setActiveGroup(tabGroupHandler);

					if (gui == null)
					{
						tabGroup.getSelectedTab().onOpen(Minecraft.getMinecraft().player);
						NetworkingAether.sendPacketToServer(new PacketOpenTab(tabGroup.getSelectedTab()));

						event.setCanceled(true);
					}

					return;
				}
			}
		}
	}

	@SubscribeEvent
	public static void onGuiMouseEvent(final GuiScreenEvent.MouseInputEvent.Pre event)
	{
		if (Minecraft.getMinecraft().world == null || Minecraft.getMinecraft().world.provider.getDimensionType() != DimensionsAether.AETHER)
		{
			return;
		}

		final EntityPlayer player = Minecraft.getMinecraft().player;

		if (player == null || !player.inventory.getItemStack().isEmpty())
		{
			return;
		}

		final ITabGroupHandler groupHandler = AetherAPI.content().tabs().getActiveGroup();

		if (groupHandler != null)
		{
			final ITabGroup<ITabClient> activeGroup = groupHandler.getClientGroup();

			if (activeGroup != null)
			{
				final ITabClient hoveredTab;

				hoveredTab = tabGroupRenderer.getHoveredTab(activeGroup);

				if (Mouse.getEventButtonState() && hoveredTab != null)
				{
					if (hoveredTab != activeGroup.getSelectedTab())
					{
						activeGroup.getSelectedTab().onClose(Minecraft.getMinecraft().player);
						activeGroup.setSelectedTab(hoveredTab);

						if (hoveredTab != activeGroup.getRememberedTab() && hoveredTab.isRemembered())
						{
							if (activeGroup.getRememberedTab() != null)
							{
								activeGroup.getRememberedTab().onClose(Minecraft.getMinecraft().player);
							}

							activeGroup.setRememberedTab(hoveredTab);
						}

						hoveredTab.onOpen(Minecraft.getMinecraft().player);
						NetworkingAether.sendPacketToServer(new PacketOpenTab(hoveredTab));

						event.setCanceled(true);
					}
				}
			}
		}
	}

	@SubscribeEvent
	public static void tickEnd(final TickEvent.RenderTickEvent event)
	{
		if (Minecraft.getMinecraft().world == null || Minecraft.getMinecraft().world.provider.getDimensionType() != DimensionsAether.AETHER)
		{
			return;
		}

		if (event.phase == TickEvent.Phase.END)
		{
			final EntityPlayer player = Minecraft.getMinecraft().player;

			if (player == null || !player.inventory.getItemStack().isEmpty())
			{
				return;
			}

			final ITabGroupHandler groupHandler = AetherAPI.content().tabs().getActiveGroup();

			if (groupHandler != null)
			{
				final ITabGroup<ITabClient> activeGroup = groupHandler.getClientGroup();

				if (activeGroup != null)
				{
					tabGroupRenderer.render(activeGroup);
				}
			}
		}
	}
}
