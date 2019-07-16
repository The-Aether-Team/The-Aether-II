package com.gildedgames.aether.common.network.packets;

import com.gildedgames.aether.common.capabilities.entity.player.PlayerAether;
import com.gildedgames.aether.common.capabilities.entity.player.modules.PlayerAbilitiesModule;
import com.gildedgames.aether.common.network.IMessage;
import com.gildedgames.aether.common.network.MessageHandlerServer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;

public class PacketSpecialMovement implements IMessage
{
	private Action action;

	public PacketSpecialMovement(final Action action)
	{
		this.action = action;
	}

	@Override
	public void fromBytes(final PacketBuffer buf)
	{
		this.action = Action.values()[buf.readByte()];
	}

	@Override
	public void toBytes(final PacketBuffer buf)
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

	public static class HandlerServer extends MessageHandlerServer<PacketSpecialMovement>
	{
		@Override
		protected void onMessage(PacketSpecialMovement message, ServerPlayerEntity player)
		{
			final PlayerAether aePlayer = PlayerAether.getPlayer(player);

			switch (message.action)
			{
				case EXTRA_JUMP:
					aePlayer.getModule(PlayerAbilitiesModule.class).performMidAirJump();
					break;
			}
		}
	}
}
