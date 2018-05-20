package com.gildedgames.aether.common.network.packets;

import com.gildedgames.aether.api.AetherAPI;
import com.gildedgames.aether.api.shop.IShopBuy;
import com.gildedgames.aether.common.capabilities.entity.player.PlayerAether;
import com.gildedgames.aether.common.containers.ContainerShop;
import com.gildedgames.aether.common.network.MessageHandlerServer;
import com.gildedgames.aether.common.util.helpers.ItemHelper;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class PacketShopSell implements IMessage
{

	public PacketShopSell()
	{

	}

	@Override
	public void fromBytes(final ByteBuf buf)
	{

	}

	@Override
	public void toBytes(final ByteBuf buf)
	{

	}

	public static class HandlerServer extends MessageHandlerServer<PacketShopSell, IMessage>
	{
		@Override
		public IMessage onMessage(final PacketShopSell message, final EntityPlayer player)
		{
			if (player == null || player.world == null)
			{
				return null;
			}

			if (player.openContainer instanceof ContainerShop)
			{
				ContainerShop container = (ContainerShop) player.openContainer;
				ItemStack stack = container.getSlot(0).getStack();

				int hash = ItemHelper.getHashForItemStack(stack);
				IShopBuy shopBuy = null;

				for (IShopBuy buy : container.getShopInstance().getStock())
				{
					int buyHash = ItemHelper.getHashForItemStack(buy.getItemStack());

					if (buyHash == hash)
					{
						shopBuy = buy;
						break;
					}
				}

				double value;

				if (shopBuy != null)
				{
					value = shopBuy.getSellingPrice() * stack.getCount();
				}
				else
				{
					value = AetherAPI.content().currency().getValue(stack);
				}

				if (value > 0)
				{
					ItemStack s = container.getSlot(0).getStack();
					double singleValue = AetherAPI.content().currency().getSingleValue(s);

					if (shopBuy != null)
					{
						singleValue = shopBuy.getSellingPrice();
					}

					if (singleValue < 1)
					{
						double wholeValue = AetherAPI.content().currency().getValue(s);

						if (shopBuy != null)
						{
							wholeValue = shopBuy.getSellingPrice() * s.getCount();
						}

						double floored = MathHelper.floor(wholeValue);

						double decimals = wholeValue - floored;

						double howManyTimesDivInto = decimals / singleValue;

						int leftover = MathHelper.floor(howManyTimesDivInto);

						s.setCount(leftover);

						PlayerAether playerAether = PlayerAether.getPlayer(player);

						playerAether.getCurrencyModule().add((long) floored);
					}
					else
					{
						container.getSlot(0).putStack(ItemStack.EMPTY);

						PlayerAether playerAether = PlayerAether.getPlayer(player);

						playerAether.getCurrencyModule().add((long) value);
					}
				}
			}

			return null;
		}
	}

}
