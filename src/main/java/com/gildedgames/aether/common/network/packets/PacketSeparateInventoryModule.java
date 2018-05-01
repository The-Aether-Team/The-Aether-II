package com.gildedgames.aether.common.network.packets;

import com.gildedgames.aether.common.capabilities.entity.player.PlayerAether;
import com.gildedgames.aether.common.capabilities.entity.player.modules.PlayerSeparateInventoryModule;
import com.gildedgames.aether.common.network.MessageHandlerClient;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class PacketSeparateInventoryModule implements IMessage
{

	private PlayerSeparateInventoryModule module;

	private NBTTagCompound tag;

	public PacketSeparateInventoryModule()
	{

	}

	public PacketSeparateInventoryModule(PlayerSeparateInventoryModule module)
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
		NBTTagCompound tag = new NBTTagCompound();

		this.module.write(tag);

		ByteBufUtils.writeTag(buf, tag);
	}

	public static class HandlerClient extends MessageHandlerClient<PacketSeparateInventoryModule, IMessage>
	{
		@Override
		public IMessage onMessage(final PacketSeparateInventoryModule message, final EntityPlayer player)
		{
			if (player == null || player.world == null)
			{
				return null;
			}

			final PlayerAether playerAether = PlayerAether.getPlayer(player);

			playerAether.getSeparateInventoryModule().read(message.tag);

			return null;
		}
	}
}