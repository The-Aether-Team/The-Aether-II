package com.gildedgames.aether.common.network.packets.trade;

import com.gildedgames.aether.common.capabilities.entity.player.PlayerAether;
import com.gildedgames.aether.common.capabilities.entity.player.modules.PlayerTradeModule;
import com.gildedgames.aether.common.network.MessageHandlerClient;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class PacketTradeInitial implements IMessage
{
	private int entityId;

	public PacketTradeInitial()
	{

	}

	public PacketTradeInitial(int id)
	{
		this.entityId = id;
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		buf.writeInt(this.entityId);
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		this.entityId = buf.readInt();
	}

	public static class HandlerClient extends MessageHandlerClient<PacketTradeInitial, IMessage>
	{
		@Override
		public IMessage onMessage(PacketTradeInitial message, EntityPlayer player)
		{
			PlayerAether aePlayer = PlayerAether.getPlayer(player);

			if (aePlayer != null)
			{
				PlayerTradeModule tradeModule = aePlayer.getTradingModule();
				Entity entity = player.world.getEntityByID(message.entityId);

				tradeModule.clear();

				if (entity instanceof EntityPlayer)
				{
					PlayerAether target = PlayerAether.getPlayer(entity);

					target.getTradingModule().clear();
					tradeModule.setTarget(target);
				}
			}

			return null;
		}
	}
}
