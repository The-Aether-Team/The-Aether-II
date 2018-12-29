package com.gildedgames.aether.common.network.packets;

import com.gildedgames.aether.api.shop.IShopBuy;
import com.gildedgames.aether.api.shop.IShopInstance;
import com.gildedgames.aether.api.shop.IShopInstanceGroup;
import com.gildedgames.aether.api.shop.ShopUtil;
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

	private int shopIndex;

	public PacketShopBuy()
	{

	}

	public PacketShopBuy(int stockIndex, int buyCount, int shopIndex)
	{
		this.stockIndex = stockIndex;
		this.buyCount = buyCount;
		this.shopIndex = shopIndex;
	}

	@Override
	public void fromBytes(final ByteBuf buf)
	{
		this.stockIndex = buf.readInt();
		this.buyCount = buf.readInt();
		this.shopIndex = buf.readInt();
	}

	@Override
	public void toBytes(final ByteBuf buf)
	{
		buf.writeInt(this.stockIndex);
		buf.writeInt(this.buyCount);
		buf.writeInt(this.shopIndex);
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
					IShopInstanceGroup group = playerAether.getDialogController().getTalkingNPC().getShopInstanceGroup();

					if (group == null)
					{
						return null;
					}

					IShopInstance shopInstance = group.getShopInstance(message.shopIndex);

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

							boolean canAfford = shopInstance.getCurrencyType().getValue(playerAether) >= (ShopUtil.getFilteredPrice(buy) * amount);
							boolean isBuyItem = ItemHelper.getKeyForItemStack(player.inventory.getItemStack()) == ItemHelper
									.getKeyForItemStack(buy.getItemStack());
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

								shopInstance.getCurrencyType().removeValue(ShopUtil.getFilteredPrice(buy) * amount, playerAether);
							}
						}
					}
				}
			}

			return null;
		}
	}

}
