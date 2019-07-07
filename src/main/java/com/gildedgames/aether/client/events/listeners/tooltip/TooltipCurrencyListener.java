package com.gildedgames.aether.client.events.listeners.tooltip;

import com.gildedgames.aether.api.AetherAPI;
import com.gildedgames.aether.client.gui.util.ToolTipCurrencyHelper;
import com.gildedgames.aether.common.shop.ShopCurrencyGilt;
import net.minecraftforge.client.event.RenderTooltipEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

@Mod.EventBusSubscriber(Side.CLIENT)
public class TooltipCurrencyListener
{
	private static final ToolTipCurrencyHelper toolTipHelper = new ToolTipCurrencyHelper();

	@SubscribeEvent
	public static void onTooltipConstruction(final ItemTooltipEvent event)
	{
		//Currency
		final double value = AetherAPI.content().currency().getValue(event.getItemStack(), ShopCurrencyGilt.class);

		if (value != 0)
		{
			event.getToolTip().addAll(toolTipHelper.getText(value));
		}
	}

	@SubscribeEvent
	public static void onToolTipRender(final RenderTooltipEvent.PostText event)
	{
		final double value = AetherAPI.content().currency().getValue(event.getStack(), ShopCurrencyGilt.class);

		toolTipHelper.render(event.getFontRenderer(), event.getX(), event.getY(), event.getHeight(), value);
	}
}
