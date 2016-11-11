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
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.gui.inventory.GuiContainerCreative;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Mouse;

import java.io.IOException;
import java.util.List;

public class TabClientEvents
{
	@SideOnly(Side.CLIENT)
	private final static RenderTabGroup tabGroupRenderer = new RenderTabGroup();

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
	public static void onGuiDrawScreen(GuiScreenEvent.DrawScreenEvent event)
	{
		/** Hack to prevent page text from rendering in creative inventory **/
		if (event.getGui() instanceof GuiContainerCreative)
		{
			GuiContainerCreative gui = (GuiContainerCreative)event.getGui();

			FontRenderer original = ObfuscationReflectionHelper.getPrivateValue(GuiScreen.class, gui, ReflectionAether.FONT_RENDERER_OBJ.getMappings());

			if (!(original instanceof PageNumberHack))
			{
				PageNumberHack hack = new PageNumberHack(original);
				ObfuscationReflectionHelper.setPrivateValue(GuiScreen.class, gui, hack, ReflectionAether.FONT_RENDERER_OBJ.getMappings());
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

	private static class PageNumberHack extends FontRendererWrapper
	{

		public PageNumberHack(FontRenderer parent)
		{
			super(parent);
		}

		@Override
		public int drawString(String text, int x, int y, int color)
		{
			GuiScreen gui = Minecraft.getMinecraft().currentScreen;

			if (gui instanceof GuiContainer)
			{
				int guiLeft = ObfuscationReflectionHelper.getPrivateValue(GuiContainer.class, (GuiContainer)gui, ReflectionAether.GUI_LEFT.getMappings());
				int xArea = guiLeft + 75;

				if (y < 25 && x > xArea && x < xArea + 10)
				{
					return 0;
				}
			}

			return this.parent.drawString(text, (float)x, (float)y, color, false);
		}

	}

}
