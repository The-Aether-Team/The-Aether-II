package com.gildedgames.aether.common.network.packets;

import com.gildedgames.aether.common.capabilities.entity.player.PlayerAether;
import com.gildedgames.aether.common.capabilities.entity.player.modules.PlayerConditionModule;
import com.gildedgames.aether.common.network.MessageHandlerClient;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class PacketFlagPlayerCondition implements IMessage
{

	private ResourceLocation conditionUniqueId;

	public PacketFlagPlayerCondition()
	{

	}

	public PacketFlagPlayerCondition(final ResourceLocation conditionUniqueId)
	{
		this.conditionUniqueId = conditionUniqueId;
	}

	@Override
	public void fromBytes(final ByteBuf buf)
	{
		final String conditionUniqueIdString = ByteBufUtils.readUTF8String(buf);

		this.conditionUniqueId = new ResourceLocation(conditionUniqueIdString);
	}

	@Override
	public void toBytes(final ByteBuf buf)
	{
		ByteBufUtils.writeUTF8String(buf, this.conditionUniqueId.toString());
	}

	public static class HandlerClient extends MessageHandlerClient<PacketFlagPlayerCondition, IMessage>
	{
		@Override
		public IMessage onMessage(final PacketFlagPlayerCondition message, final EntityPlayer player)
		{
			if (player == null || player.world == null)
			{
				return null;
			}

			final PlayerAether playerAether = PlayerAether.getPlayer(player);

			playerAether.getModule(PlayerConditionModule.class).flagCondition(message.conditionUniqueId);

			return null;
		}
	}
}
