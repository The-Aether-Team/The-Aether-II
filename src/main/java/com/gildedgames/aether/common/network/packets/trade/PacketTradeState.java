package com.gildedgames.aether.common.network.packets.trade;

import com.gildedgames.aether.common.capabilities.entity.player.PlayerAether;
import com.gildedgames.aether.common.capabilities.entity.player.modules.PlayerCurrencyModule;
import com.gildedgames.aether.common.capabilities.entity.player.modules.PlayerTradeModule;
import com.gildedgames.aether.common.containers.ContainerTrade;
import com.gildedgames.aether.common.network.IMessage;
import com.gildedgames.aether.common.network.MessageHandlerClient;
import com.gildedgames.aether.common.network.MessageHandlerServer;
import com.gildedgames.aether.common.network.NetworkingAether;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;

public class PacketTradeState implements IMessage
{
	private byte state;

	public PacketTradeState(byte state)
	{
		this.state = state;
	}

	@Override
	public void toBytes(PacketBuffer buf)
	{
		buf.writeByte(this.state);
	}

	@Override
	public void fromBytes(PacketBuffer buf)
	{
		this.state = buf.readByte();
	}

	public static class HandlerClient extends MessageHandlerClient<PacketTradeState>
	{
		@Override
		protected void onMessage(PacketTradeState message, ClientPlayerEntity player)
		{
			PlayerAether aePlayer = PlayerAether.getPlayer(player);

			PlayerTradeModule tradeModule = aePlayer.getModule(PlayerTradeModule.class);

			if (message.state == -1)
			{
				tradeModule.clear();
			}

			tradeModule.updateClientState(message.state);
		}
	}

	public static class HandlerServer extends MessageHandlerServer<PacketTradeState>
	{
		@Override
		protected void onMessage(PacketTradeState message, ServerPlayerEntity player)
		{
			PlayerAether aePlayer = PlayerAether.getPlayer(player);

			PlayerTradeModule tradeModule = aePlayer.getModule(PlayerTradeModule.class);
			PlayerTradeModule targetTrade = tradeModule.getTarget().getModule(PlayerTradeModule.class);

			if (tradeModule.isTrading())
			{
				String statusMessage = "";

				if (message.state == 0)
				{
					tradeModule.setLockedIn(true);
					targetTrade.sendState(0);
					statusMessage = "targetlock";
				}
				else if (message.state == 1)
				{
					tradeModule.setLockedIn(false);

					if (targetTrade.isLockedIn())
					{
						targetTrade.setLockedIn(false);
						tradeModule.sendState(0);
						statusMessage = "unlockwarn";
					}
					else
					{
						statusMessage = "unlocksafe";
					}

					targetTrade.sendState(0);
				}
				else if (message.state == 2 && tradeModule.isLockedIn() && targetTrade.isLockedIn())
				{
					if (targetTrade.isConfirmed())
					{
						if (tradeModule.getEntity().openContainer instanceof ContainerTrade && targetTrade.getEntity().openContainer instanceof ContainerTrade)
						{
							ContainerTrade hostContainer = (ContainerTrade) tradeModule.getEntity().openContainer;
							ContainerTrade otherContainer = (ContainerTrade) targetTrade.getEntity().openContainer;

							aePlayer.getModule(PlayerCurrencyModule.class).add((long) (targetTrade.getCoinAmount() - tradeModule.getCoinAmount()));
							tradeModule.getTarget().getModule(PlayerCurrencyModule.class).add((long) (tradeModule.getCoinAmount() - targetTrade.getCoinAmount()));

							for (int i = 0; i < 16; i++)
							{
								Slot slotA = hostContainer.getSlot(36 + i);
								Slot slotB = otherContainer.getSlot(36 + i);

								if (!slotB.getStack().isEmpty())
								{
									tradeModule.getEntity().addItemStackToInventory(slotB.getStack().copy());
									slotB.putStack(ItemStack.EMPTY);
								}

								if (!slotA.getStack().isEmpty())
								{
									targetTrade.getEntity().addItemStackToInventory(slotA.getStack().copy());
									slotA.putStack(ItemStack.EMPTY);
								}
							}
						}

						tradeModule.closeGui();
						targetTrade.closeGui();
					}
					else
					{
						tradeModule.setConfirmed(true);
						targetTrade.sendState(3);
						statusMessage = "targetconfirm";
					}
				}

				if (!statusMessage.isEmpty())
				{
					NetworkingAether.sendPacketToPlayer(new PacketTradeMessage("aether.trade.message." + statusMessage), targetTrade.getPlayerMP());
				}
			}
		}
	}
}
