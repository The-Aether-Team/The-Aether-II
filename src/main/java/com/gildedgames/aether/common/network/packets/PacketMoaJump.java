package com.gildedgames.aether.common.network.packets;

import com.gildedgames.aether.common.capabilities.entity.player.PlayerAether;
import com.gildedgames.aether.common.capabilities.entity.player.modules.PlayerAbilitiesModule;
import com.gildedgames.aether.common.network.MessageHandlerClient;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class PacketMoaJump implements IMessage
{

	private int midAirJumpsAllowed;

	public PacketMoaJump()
	{

	}

	public PacketMoaJump(final int midAirJumpsAllowed)
	{
		this.midAirJumpsAllowed = midAirJumpsAllowed;
	}

	@Override
	public void fromBytes(final ByteBuf buf)
	{
		this.midAirJumpsAllowed = buf.readInt();
	}

	@Override
	public void toBytes(final ByteBuf buf)
	{
		buf.writeInt(this.midAirJumpsAllowed);
	}

	public static class HandlerClient extends MessageHandlerClient<PacketMoaJump, PacketMoaJump>
	{
		@Override
		public PacketMoaJump onMessage(final PacketMoaJump message, final EntityPlayer player)
		{
			final PlayerAether playerAether = PlayerAether.getPlayer(player);
			playerAether.getModule(PlayerAbilitiesModule.class).setMidAirJumpsAllowed(message.midAirJumpsAllowed);

			return null;
		}

	}
}
