package com.gildedgames.aether.common.network.packets;

import com.gildedgames.aether.api.shop.IShopBuy;
import com.gildedgames.aether.api.shop.IShopInstance;
import com.gildedgames.aether.common.capabilities.entity.player.PlayerAether;
import com.gildedgames.aether.common.containers.ContainerShop;
import com.gildedgames.aether.common.network.MessageHandlerServer;
import com.gildedgames.aether.common.util.helpers.ItemHelper;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class PacketShopBuy implements IMessage
{
	private int stockIndex, buyCount;

	public PacketShopBuy()
	{

	}

	public PacketShopBuy(int stockIndex, int buyCount)
	{
		this.stockIndex = stockIndex;
		this.buyCount = buyCount;
	}

	@Override
	public void fromBytes(final ByteBuf buf)
	{
		this.stockIndex = buf.readInt();
		this.buyCount = buf.readInt();
	}

	@Override
	public void toBytes(final ByteBuf buf)
	{
		buf.writeInt(this.stockIndex);
		buf.writeInt(this.buyCount);
	}

	public static class HandlerServer extends MessageHandlerServer<PacketShopBuy, IMessage>
	{
		@Override
		public IMessage onMessage(final PacketShopBuy message, final EntityPlayer player)
		{
			if (player == null || player.world == null)
			{
				return null;
			}

			if (player.openContainer instanceof ContainerShop)
			{
				PlayerAether playerAether = PlayerAether.getPlayer(player);

				if (playerAether.getDialogController().getTalkingNPC() != null)
				{
					IShopInstance shopInstance = playerAether.getDialogController().getTalkingNPC().getShopInstance();

					if (shopInstance != null && shopInstance.getStock() != null)
					{
						boolean isHandFree = player.inventory.getItemStack().isEmpty();

						if (message.stockIndex >= 0 && message.stockIndex < shopInstance.getStock().size())
						{
							IShopBuy buy = shopInstance.getStock().get(message.stockIndex);

							if (buy.getStock() <= 0)
							{
								return null;
							}

							int maxAllowedWithHeldStack = buy.getItemStack().getMaxStackSize() - player.inventory.getItemStack().getCount();

							int amount = Math.min(maxAllowedWithHeldStack, Math.min(message.buyCount, buy.getStock()));

							if (amount <= 0)
							{
								return null;
							}

							boolean canAfford = playerAether.getCurrencyModule().getCurrencyValue() >= buy.getPrice();
							boolean isBuyItem = ItemHelper.getHashForItemStack(player.inventory.getItemStack()) == ItemHelper
									.getHashForItemStack(buy.getItemStack());
							boolean canStack = player.inventory.getItemStack().isStackable();
							boolean isAtStackLimit = player.inventory.getItemStack().getCount() >= player.inventory.getItemStack().getMaxStackSize();

							if (!isAtStackLimit && canStack && canAfford && (isHandFree || isBuyItem) && buy.getStock() > 0)
							{
								if (isHandFree)
								{
									buy.addStock(-amount);

									ItemStack stack = buy.getItemStack().copy();
									stack.setCount(amount);

									player.inventory.setItemStack(stack);
								}
								else
								{
									buy.addStock(-amount);

									player.inventory.getItemStack().setCount(player.inventory.getItemStack().getCount() + amount);
								}

								playerAether.getCurrencyModule().add(-buy.getPrice());
							}
						}
					}
				}
			}

			return null;
		}
	}

}
