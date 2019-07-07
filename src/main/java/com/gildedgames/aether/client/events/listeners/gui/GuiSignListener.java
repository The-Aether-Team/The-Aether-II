package com.gildedgames.aether.client.events.listeners.gui;

import com.gildedgames.aether.client.gui.misc.GuiSkyrootSign;
import com.gildedgames.aether.common.entities.tiles.TileEntitySkyrootSign;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiEditSign;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

@Mod.EventBusSubscriber(Side.CLIENT)
public class GuiSignListener
{
	@SubscribeEvent
	public static void onOpenGui(final GuiOpenEvent event)
	{
		GuiScreen gui = event.getGui();

		if (gui instanceof GuiEditSign)
		{
			if (((GuiEditSign) gui).tileSign instanceof TileEntitySkyrootSign)
			{
				event.setGui(new GuiSkyrootSign(((GuiEditSign) gui).tileSign));
			}
		}
	}

}
