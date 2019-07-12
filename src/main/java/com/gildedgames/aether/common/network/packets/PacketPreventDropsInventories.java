package com.gildedgames.aether.common.network.packets;

import com.gildedgames.aether.common.capabilities.entity.player.PlayerAether;
import com.gildedgames.aether.common.capabilities.entity.player.modules.PlayerPreventDropsModule;
import com.gildedgames.aether.common.network.MessageHandlerClient;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class PacketPreventDropsInventories implements IMessage
{

	private PlayerPreventDropsModule module;

	private CompoundNBT tag;

	public PacketPreventDropsInventories()
	{

	}

	public PacketPreventDropsInventories(PlayerPreventDropsModule module)
	{
		this.module = module;
	}

	@Override
	public void fromBytes(final ByteBuf buf)
	{
		this.tag = ByteBufUtils.readTag(buf);
	}

	@Override
	public void toBytes(final ByteBuf buf)
	{
		CompoundNBT tag = new CompoundNBT();

		this.module.write(tag);

		ByteBufUtils.writeTag(buf, tag);
	}

	public static class HandlerClient extends MessageHandlerClient<PacketPreventDropsInventories, IMessage>
	{
		@Override
		public IMessage onMessage(final PacketPreventDropsInventories message, final PlayerEntity player)
		{
			if (player == null || player.world == null)
			{
				return null;
			}

			final PlayerAether playerAether = PlayerAether.getPlayer(player);
			playerAether.getModule(PlayerPreventDropsModule.class).read(message.tag);

			return null;
		}
	}
}
