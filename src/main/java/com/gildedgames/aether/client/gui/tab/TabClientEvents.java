package com.gildedgames.aether.client.gui.tab;

import com.gildedgames.aether.api.AetherAPI;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.ReflectionAether;
import com.gildedgames.aether.common.network.NetworkingAether;
import com.gildedgames.aether.common.network.packets.PacketOpenTab;
import com.gildedgames.aether.api.registry.tab.ITabClient;
import com.gildedgames.aether.api.registry.tab.ITabGroup;
import com.gildedgames.aether.api.registry.tab.ITabGroupHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.gui.inventory.GuiContainerCreative;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Mouse;

import java.io.IOException;

public class TabClientEvents
{
	@SideOnly(Side.CLIENT)
	private final static RenderTabGroup tabGroupRenderer = new RenderTabGroup();

	private static int prevMaxPages;

	@SubscribeEvent
	public static void onGuiOpen(GuiOpenEvent event)
	{
		GuiScreen gui = event.getGui();

		ITabGroupHandler groupHandler = AetherAPI.tabs().getActiveGroup();

		if (groupHandler != null)
		{
			ITabClient selectedTab = groupHandler.getClientGroup().getSelectedTab();

			if (gui != null && selectedTab.isTabValid(gui))
			{
				return;
			}

			AetherAPI.tabs().setActiveGroup(null);
		}

		for (ITabGroupHandler tabGroupHandler : AetherAPI.tabs().getRegisteredTabGroups().values())
		{
			ITabGroup<ITabClient> tabGroup = tabGroupHandler.getClientGroup();

			for (ITabClient tab : tabGroup.getTabs())
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

					AetherAPI.tabs().setActiveGroup(tabGroupHandler);

					if (gui == null || !tabGroup.getSelectedTab().isMainGui(gui))
					{
						tabGroup.getSelectedTab().onOpen(Minecraft.getMinecraft().thePlayer);
						NetworkingAether.sendPacketToServer(new PacketOpenTab(tabGroup.getSelectedTab()));

						event.setCanceled(true);
					}

					return;
				}
			}
		}
	}

	@SubscribeEvent
	public static void onGuiMouseEvent(GuiScreenEvent.DrawScreenEvent event)
	{
		/** Hack to prevent page text from rendering in creative inventory **/
		if (event.getGui() instanceof GuiContainerCreative)
		{
			GuiContainerCreative gui = (GuiContainerCreative)event.getGui();

			int maxPages = ObfuscationReflectionHelper.getPrivateValue(GuiContainerCreative.class, gui, ReflectionAether.MAX_PAGES.getMappings());

			int guiTop = ObfuscationReflectionHelper.getPrivateValue(GuiContainer.class, gui, ReflectionAether.GUI_TOP.getMappings());

			if (guiTop > 70 || AetherCore.CONFIG.getDisplayTabsOnLeft())
			{
				if (TabClientEvents.prevMaxPages != 0)
				{
					ObfuscationReflectionHelper.setPrivateValue(GuiContainerCreative.class, gui, TabClientEvents.prevMaxPages, ReflectionAether.MAX_PAGES.getMappings());
				}
			}
			else if (maxPages != 0)
			{
				TabClientEvents.prevMaxPages = maxPages;
				ObfuscationReflectionHelper.setPrivateValue(GuiContainerCreative.class, gui, 0, ReflectionAether.MAX_PAGES.getMappings());
			}
		}
	}

	@SubscribeEvent
	public static void onGuiMouseEvent(GuiScreenEvent.MouseInputEvent.Pre event)
	{
		EntityPlayer player = Minecraft.getMinecraft().thePlayer;

		if (player == null || player.inventory.getItemStack() != null)
		{
			return;
		}

		ITabGroupHandler groupHandler = AetherAPI.tabs().getActiveGroup();

		if (groupHandler != null)
		{
			ITabGroup<ITabClient> activeGroup = groupHandler.getClientGroup();

			if (activeGroup != null)
			{
				ITabClient hoveredTab;

				hoveredTab = tabGroupRenderer.getHoveredTab(activeGroup);

				if (Mouse.getEventButtonState() && hoveredTab != null)
				{
					if (hoveredTab != activeGroup.getSelectedTab())
					{
						activeGroup.getSelectedTab().onClose(Minecraft.getMinecraft().thePlayer);
						activeGroup.setSelectedTab(hoveredTab);

						if (hoveredTab != activeGroup.getRememberedTab() && hoveredTab.isRemembered())
						{
							if (activeGroup.getRememberedTab() != null)
							{
								activeGroup.getRememberedTab().onClose(Minecraft.getMinecraft().thePlayer);
							}

							activeGroup.setRememberedTab(hoveredTab);
						}

						hoveredTab.onOpen(Minecraft.getMinecraft().thePlayer);
						NetworkingAether.sendPacketToServer(new PacketOpenTab(hoveredTab));

						event.setCanceled(true);
					}
				}
			}
		}
	}

	@SubscribeEvent
	public static void tickEnd(TickEvent.RenderTickEvent event)
	{
		if (event.phase == TickEvent.Phase.END)
		{
			/** Hack to prevent page text from rendering in creative inventory **/
			if (Minecraft.getMinecraft().currentScreen instanceof GuiContainerCreative)
			{
				GuiContainerCreative gui = (GuiContainerCreative)Minecraft.getMinecraft().currentScreen;

				if (TabClientEvents.prevMaxPages != 0)
				{
					ObfuscationReflectionHelper.setPrivateValue(GuiContainerCreative.class, gui, TabClientEvents.prevMaxPages, ReflectionAether.MAX_PAGES.getMappings());
				}
			}

			EntityPlayer player = Minecraft.getMinecraft().thePlayer;

			if (player == null || player.inventory.getItemStack() != null)
			{
				return;
			}

			ITabGroupHandler groupHandler = AetherAPI.tabs().getActiveGroup();

			if (groupHandler != null)
			{
				ITabGroup<ITabClient> activeGroup = groupHandler.getClientGroup();

				if (activeGroup != null)
				{
					tabGroupRenderer.render(activeGroup);
				}
			}
		}
	}

}
