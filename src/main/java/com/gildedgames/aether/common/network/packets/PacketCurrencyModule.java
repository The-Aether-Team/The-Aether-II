package com.gildedgames.aether.common.network.packets;

import com.gildedgames.aether.common.capabilities.entity.player.PlayerAether;
import com.gildedgames.aether.common.capabilities.entity.player.modules.PlayerCurrencyModule;
import com.gildedgames.aether.common.network.MessageHandlerClient;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class PacketCurrencyModule implements IMessage
{

	private PlayerCurrencyModule module;

	private CompoundNBT tag;

	public PacketCurrencyModule()
	{

	}

	public PacketCurrencyModule(PlayerCurrencyModule module)
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

	public static class HandlerClient extends MessageHandlerClient<PacketCurrencyModule, IMessage>
	{
		@Override
		public IMessage onMessage(final PacketCurrencyModule message, final PlayerEntity player)
		{
			if (player == null || player.world == null)
			{
				return null;
			}

			final PlayerAether playerAether = PlayerAether.getPlayer(player);
			playerAether.getModule(PlayerCurrencyModule.class).read(message.tag);

			return null;
		}
	}
}
