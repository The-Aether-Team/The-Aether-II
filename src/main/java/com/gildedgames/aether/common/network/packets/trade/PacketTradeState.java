package com.gildedgames.aether.common.network.packets.trade;

import com.gildedgames.aether.common.capabilities.entity.player.PlayerAether;
import com.gildedgames.aether.common.capabilities.entity.player.modules.PlayerTradeModule;
import com.gildedgames.aether.common.containers.ContainerTrade;
import com.gildedgames.aether.common.network.MessageHandlerClient;
import com.gildedgames.aether.common.network.MessageHandlerServer;
import com.gildedgames.aether.common.network.NetworkingAether;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class PacketTradeState implements IMessage
{
	private byte state;

	public PacketTradeState()
	{

	}

	public PacketTradeState(byte state)
	{
		this.state = state;
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		buf.writeByte(this.state);
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		this.state = buf.readByte();
	}

	public static class HandlerClient extends MessageHandlerClient<PacketTradeState, IMessage>
	{
		@Override
		public IMessage onMessage(PacketTradeState message, EntityPlayer player)
		{
			PlayerAether aePlayer = PlayerAether.getPlayer(player);

			if (aePlayer != null)
			{
				PlayerTradeModule tradeModule = aePlayer.getTradingModule();

				if (message.state == -1)
				{
					tradeModule.clear();
				}

				tradeModule.updateClientState(message.state);
			}

			return null;
		}
	}

	public static class HandlerServer extends MessageHandlerServer<PacketTradeState, IMessage>
	{
		@Override
		public IMessage onMessage(PacketTradeState message, EntityPlayer player)
		{
			PlayerAether aePlayer = PlayerAether.getPlayer(player);

			if (aePlayer != null)
			{
				PlayerTradeModule tradeModule = aePlayer.getTradingModule();
				PlayerTradeModule targetTrade = tradeModule.getTarget().getTradingModule();

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

								aePlayer.getCurrencyModule().add((long) (targetTrade.getCoinAmount() - tradeModule.getCoinAmount()));
								tradeModule.getTarget().getCurrencyModule().add((long) (tradeModule.getCoinAmount() - targetTrade.getCoinAmount()));

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

			return null;
		}
	}
}
