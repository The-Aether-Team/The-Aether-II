package com.gildedgames.aether.client.events.listeners.gui;

import com.gildedgames.aether.client.gui.misc.GuiSkyrootSign;
import com.gildedgames.aether.common.entities.tiles.TileEntitySkyrootSign;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.inventory.GuiEditSign;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.api.distmarker.Dist;

@Mod.EventBusSubscriber(Dist.CLIENT)
public class GuiSignListener
{
	@SubscribeEvent
	public static void onOpenGui(final GuiOpenEvent event)
	{
		Screen gui = event.getGui();

		if (gui instanceof GuiEditSign)
		{
			if (((GuiEditSign) gui).tileSign instanceof TileEntitySkyrootSign)
			{
				event.setGui(new GuiSkyrootSign(((GuiEditSign) gui).tileSign));
			}
		}
	}

}
