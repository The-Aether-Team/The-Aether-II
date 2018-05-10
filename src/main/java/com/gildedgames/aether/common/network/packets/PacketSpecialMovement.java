package com.gildedgames.aether.common.network.packets;

import com.gildedgames.aether.common.capabilities.entity.player.PlayerAether;
import com.gildedgames.aether.common.network.MessageHandlerServer;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class PacketSpecialMovement implements IMessage
{
	private Action action;

	public PacketSpecialMovement()
	{

	}

	public PacketSpecialMovement(final Action action)
	{
		this.action = action;
	}

	@Override
	public void fromBytes(final ByteBuf buf)
	{
		this.action = Action.values()[buf.readByte()];
	}

	@Override
	public void toBytes(final ByteBuf buf)
	{
		buf.writeByte(this.action.ordinal());
	}

	public enum Action
	{
		EXTRA_JUMP,
		ROLL_FORWARD,
		ROLL_BACK,
		ROLL_LEFT,
		ROLL_RIGHT
	}

	public static class HandlerServer extends MessageHandlerServer<PacketSpecialMovement, PacketSpecialMovement>
	{
		@Override
		public PacketSpecialMovement onMessage(final PacketSpecialMovement message, final EntityPlayer player)
		{
			final PlayerAether aePlayer = PlayerAether.getPlayer(player);

			switch (message.action)
			{
				case EXTRA_JUMP:
					aePlayer.getAbilitiesModule().performMidAirJump();
				case ROLL_FORWARD:
					aePlayer.getRollMovementModule().startRolling(message.action);
				case ROLL_BACK:
					aePlayer.getRollMovementModule().startRolling(message.action);
				case ROLL_LEFT:
					aePlayer.getRollMovementModule().startRolling(message.action);
				case ROLL_RIGHT:
					aePlayer.getRollMovementModule().startRolling(message.action);
			}

			return null;
		}
	}
}
