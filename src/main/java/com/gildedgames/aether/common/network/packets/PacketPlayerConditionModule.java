package com.gildedgames.aether.common.network.packets;

import com.gildedgames.aether.api.player.IPlayerConditionModule;
import com.gildedgames.aether.common.capabilities.entity.player.PlayerAether;
import com.gildedgames.aether.common.network.MessageHandlerClient;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class PacketPlayerConditionModule implements IMessage
{

	private IPlayerConditionModule module;

	private NBTTagCompound tag;

	public PacketPlayerConditionModule()
	{

	}

	public PacketPlayerConditionModule(final IPlayerConditionModule module)
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
		final NBTTagCompound tag = new NBTTagCompound();

		this.module.write(tag);

		ByteBufUtils.writeTag(buf, tag);
	}

	public static class HandlerClient extends MessageHandlerClient<PacketPlayerConditionModule, IMessage>
	{
		@Override
		public IMessage onMessage(final PacketPlayerConditionModule message, final EntityPlayer player)
		{
			if (player == null || player.world == null)
			{
				return null;
			}

			final PlayerAether playerAether = PlayerAether.getPlayer(player);

			playerAether.getPlayerConditionModule().read(message.tag);

			return null;
		}
	}
}
