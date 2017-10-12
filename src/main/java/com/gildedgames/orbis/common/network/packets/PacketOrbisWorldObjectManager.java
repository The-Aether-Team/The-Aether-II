package com.gildedgames.orbis.common.network.packets;

import com.gildedgames.aether.api.orbis.IWorldObjectManager;
import com.gildedgames.aether.client.ClientEventHandler;
import com.gildedgames.aether.common.capabilities.world.WorldObjectManager;
import com.gildedgames.aether.common.network.MessageHandlerClient;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class PacketOrbisWorldObjectManager implements IMessage
{

	private IWorldObjectManager manager;

	private NBTTagCompound tag;

	public PacketOrbisWorldObjectManager()
	{

	}

	public PacketOrbisWorldObjectManager(final IWorldObjectManager manager)
	{
		this.manager = manager;
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

		this.manager.write(tag);

		ByteBufUtils.writeTag(buf, tag);
	}

	public static class HandlerClient extends MessageHandlerClient<PacketOrbisWorldObjectManager, IMessage>
	{
		@Override
		public IMessage onMessage(final PacketOrbisWorldObjectManager message, final EntityPlayer player)
		{
			if (player == null || player.world == null)
			{
				return null;
			}

			final IWorldObjectManager manager = WorldObjectManager.get(player.world);

			if (!manager.containsObserver(ClientEventHandler.CHUNK_RENDERER_MANAGER))
			{
				manager.addObserver(ClientEventHandler.CHUNK_RENDERER_MANAGER);
			}

			manager.read(message.tag);

			return null;
		}
	}
}
