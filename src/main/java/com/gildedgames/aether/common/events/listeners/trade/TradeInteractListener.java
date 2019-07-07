package com.gildedgames.aether.common.events.listeners.trade;

import com.gildedgames.aether.api.registrar.ItemsAether;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.capabilities.entity.player.PlayerAether;
import com.gildedgames.aether.common.capabilities.entity.player.modules.PlayerTradeModule;
import com.gildedgames.aether.common.util.helpers.PlayerUtil;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class TradeInteractListener
{

	@SubscribeEvent
	public static void onPlayerInteract(final PlayerInteractEvent.EntityInteract event)
	{
		if (event.getTarget() instanceof EntityCow && !((EntityCow) event.getTarget()).isChild())
		{
			ItemStack item = event.getEntityPlayer().getHeldItem(event.getHand());

			if (item.getItem() == ItemsAether.skyroot_bucket)
			{
				PlayerUtil.fillBucketInHand(event.getEntityPlayer(), event.getHand(), item, new ItemStack(ItemsAether.skyroot_milk_bucket));
			}
		}
		else if (event.getSide().isServer() && event.getTarget() instanceof EntityPlayer && event.getHand() == EnumHand.MAIN_HAND && event.getItemStack()
				.isEmpty())
		{
			PlayerTradeModule me = PlayerAether.getPlayer(event.getEntityPlayer()).getModule(PlayerTradeModule.class);
			PlayerTradeModule other = PlayerAether.getPlayer((EntityPlayer) event.getTarget()).getModule(PlayerTradeModule.class);

			if (me.getPlayer().equals(other.getTarget()) && other.canAccept(event.getEntity().getPosition()))
			{
				me.setTrading(other);
				other.accept();
				AetherCore.LOGGER.info(event.getTarget().getDisplayName().getFormattedText() + " is now trading with " + event.getEntity().getDisplayName()
						.getFormattedText());
			}
			else if (!other.isTrading() && me.canRequest())
			{
				me.request(other.getPlayer());
			}
			else if (me.getFailTime() == 0)
			{
				me.failRequest(other);
			}
		}
	}
}
