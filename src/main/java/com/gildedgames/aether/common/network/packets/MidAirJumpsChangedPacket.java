package com.gildedgames.aether.common.network.packets;

import com.gildedgames.aether.common.capabilities.player.PlayerAether;
import com.gildedgames.aether.common.network.MessageHandlerClient;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class MidAirJumpsChangedPacket implements IMessage
{

	private int midAirJumpsAllowed;

	public MidAirJumpsChangedPacket()
	{

	}

	public MidAirJumpsChangedPacket(int midAirJumpsAllowed)
	{
		this.midAirJumpsAllowed = midAirJumpsAllowed;
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		this.midAirJumpsAllowed = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		buf.writeInt(this.midAirJumpsAllowed);
	}

	public static class Handler extends MessageHandlerClient<MidAirJumpsChangedPacket, MidAirJumpsChangedPacket>
	{
		@Override
		public MidAirJumpsChangedPacket onMessage(MidAirJumpsChangedPacket message, EntityPlayer player)
		{
			PlayerAether playerAether = PlayerAether.getPlayer(player);

			playerAether.getAbilitiesModule().setMidAirJumpsAllowed(message.midAirJumpsAllowed);

			return null;
		}

	}
}
