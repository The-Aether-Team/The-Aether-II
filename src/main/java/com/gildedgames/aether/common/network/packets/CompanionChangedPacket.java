package com.gildedgames.aether.common.network.packets;

import com.gildedgames.aether.common.capabilities.player.PlayerAetherImpl;
import com.gildedgames.aether.common.network.MessageHandlerClient;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class CompanionChangedPacket implements IMessage
{
	private int entityId;

	public CompanionChangedPacket()
	{

	}

	public CompanionChangedPacket(int companionId)
	{
		this.entityId = companionId;
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		this.entityId = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		buf.writeInt(this.entityId);
	}

	public static class Handler extends MessageHandlerClient<CompanionChangedPacket, IMessage>
	{
		@Override
		public IMessage onMessage(CompanionChangedPacket message, EntityPlayer player)
		{
			PlayerAetherImpl aePlayer = PlayerAetherImpl.getPlayer(player);
			aePlayer.getCompanionModule().handleCompanionChange(message.entityId);

			return null;
		}
	}
}
