package com.gildedgames.aether.common.network.packets;

import com.gildedgames.aether.common.capabilities.player.PlayerAether;
import com.gildedgames.aether.common.network.MessageHandlerClient;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PacketMoaJump implements IMessage
{

	private int midAirJumpsAllowed;

	public PacketMoaJump()
	{

	}

	public PacketMoaJump(int midAirJumpsAllowed)
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

	@SideOnly(Side.CLIENT)
	public static class HandlerClient extends MessageHandlerClient<PacketMoaJump, PacketMoaJump>
	{
		@Override
		public PacketMoaJump onMessage(PacketMoaJump message, EntityPlayer player)
		{
			PlayerAether playerAether = PlayerAether.getPlayer(player);

			playerAether.getAbilitiesModule().setMidAirJumpsAllowed(message.midAirJumpsAllowed);

			return null;
		}

	}
}
