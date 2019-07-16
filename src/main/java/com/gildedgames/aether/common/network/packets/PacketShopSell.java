package com.gildedgames.aether.common.network.packets;

import com.gildedgames.aether.api.AetherAPI;
import com.gildedgames.aether.api.shop.IShopBuy;
import com.gildedgames.aether.api.shop.IShopInstance;
import com.gildedgames.aether.api.shop.IShopInstanceGroup;
import com.gildedgames.aether.api.shop.ShopUtil;
import com.gildedgames.aether.common.capabilities.entity.player.PlayerAether;
import com.gildedgames.aether.common.capabilities.entity.player.modules.PlayerDialogModule;
import com.gildedgames.aether.common.containers.ContainerShop;
import com.gildedgames.aether.common.network.IMessage;
import com.gildedgames.aether.common.network.MessageHandlerServer;
import com.gildedgames.aether.common.util.helpers.ItemHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.MathHelper;

public class PacketShopSell implements IMessage
{
	private int shopIndex;

	public PacketShopSell(int shopIndex)
	{
		this.shopIndex = shopIndex;
	}

	@Override
	public void fromBytes(final PacketBuffer buf)
	{
		this.shopIndex = buf.readInt();
	}

	@Override
	public void toBytes(final PacketBuffer buf)
	{
		buf.writeInt(this.shopIndex);
	}

	public static class HandlerServer extends MessageHandlerServer<PacketShopSell>
	{
		@Override
		protected void onMessage(PacketShopSell message, ServerPlayerEntity player)
		{
			if (player == null || player.world == null)
			{
				return;
			}

			PlayerAether playerAether = PlayerAether.getPlayer(player);
			PlayerDialogModule dialogModule = playerAether.getModule(PlayerDialogModule.class);

			if (player.openContainer instanceof ContainerShop)
			{
				if (dialogModule.getTalkingCharacter() != null)
				{
					IShopInstanceGroup group = dialogModule.getTalkingCharacter().getShopInstanceGroup();

					if (group == null)
					{
						return;
					}

					IShopInstance shopInstance = group.getShopInstance(message.shopIndex);

					if (shopInstance != null)
					{
						ContainerShop container = (ContainerShop) player.openContainer;
						ItemStack stackToSell = container.getSlot(0).getStack();

						int hash = ItemHelper.getKeyForItemStack(stackToSell);
						IShopBuy shopBuy = null;

						for (IShopBuy buy : container.getShopInstance().getStock())
						{
							int buyHash = ItemHelper.getKeyForItemStack(buy.getItemStack());

							if (buyHash == hash)
							{
								shopBuy = buy;
								break;
							}
						}

						double value;

						if (shopBuy != null)
						{
							value = ShopUtil.getFilteredPrice(shopInstance, stackToSell, shopBuy.getSellingPrice()) * stackToSell.getCount();
						}
						else
						{
							value = ShopUtil
									.getFilteredPrice(shopInstance, stackToSell,
											AetherAPI.content().currency().getValue(stackToSell, shopInstance.getCurrencyType().getClass()));
						}

						if (value > 0)
						{
							double singleValue = ShopUtil
									.getFilteredPrice(shopInstance, stackToSell,
											AetherAPI.content().currency().getSingleValue(stackToSell, shopInstance.getCurrencyType().getClass()));

							if (shopBuy != null)
							{
								singleValue = ShopUtil.getFilteredPrice(shopInstance, stackToSell, shopBuy.getSellingPrice());
							}

							if (singleValue < 1)
							{
								double wholeValue = ShopUtil
										.getFilteredPrice(shopInstance, stackToSell,
												AetherAPI.content().currency().getValue(stackToSell, shopInstance.getCurrencyType().getClass()));

								if (shopBuy != null)
								{
									wholeValue = ShopUtil.getFilteredPrice(shopInstance, stackToSell, shopBuy.getSellingPrice()) * stackToSell.getCount();
								}

								double floored = MathHelper.floor(wholeValue);

								double decimals = wholeValue - floored;

								double howManyTimesDivInto = decimals / singleValue;

								int leftover = MathHelper.floor(howManyTimesDivInto);

								stackToSell.setCount(leftover);

								shopInstance.getCurrencyType().addValue((long) floored, playerAether);
							}
							else
							{
								container.getSlot(0).putStack(ItemStack.EMPTY);

								shopInstance.getCurrencyType().addValue((long) value, playerAether);
							}
						}
					}
				}
			}
		}
	}

}
