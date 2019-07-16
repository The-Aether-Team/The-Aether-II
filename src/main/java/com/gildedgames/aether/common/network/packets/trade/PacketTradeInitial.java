package com.gildedgames.aether.common.network.packets.trade;

import com.gildedgames.aether.common.capabilities.entity.player.PlayerAether;
import com.gildedgames.aether.common.capabilities.entity.player.modules.PlayerTradeModule;
import com.gildedgames.aether.common.network.IMessage;
import com.gildedgames.aether.common.network.MessageHandlerClient;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketBuffer;

public class PacketTradeInitial implements IMessage
{
	private int entityId;

	public PacketTradeInitial(int id)
	{
		this.entityId = id;
	}

	@Override
	public void toBytes(PacketBuffer buf)
	{
		buf.writeInt(this.entityId);
	}

	@Override
	public void fromBytes(PacketBuffer buf)
	{
		this.entityId = buf.readInt();
	}

	public static class HandlerClient extends MessageHandlerClient<PacketTradeInitial>
	{
		@Override
		protected void onMessage(PacketTradeInitial message, ClientPlayerEntity player)
		{
			Entity entity = player.world.getEntityByID(message.entityId);

			if (!(entity instanceof PlayerEntity))
			{
				throw new IllegalArgumentException("Entity is not a player");
			}

			PlayerAether aePlayer = PlayerAether.getPlayer(player);

			PlayerTradeModule tradeModule = aePlayer.getModule(PlayerTradeModule.class);
			tradeModule.clear();

			PlayerAether target = PlayerAether.getPlayer((PlayerEntity) entity);
			target.getModule(PlayerTradeModule.class).clear();

			tradeModule.setTarget(target);

		}
	}
}
