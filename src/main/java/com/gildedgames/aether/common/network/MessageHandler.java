package com.gildedgames.aether.common.network;

import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.network.util.IMessageMultipleParts;
import com.google.common.collect.Lists;
import io.netty.buffer.Unpooled;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayList;

public abstract class MessageHandler<REQ extends IMessage, RES extends IMessage> implements IMessageHandler<REQ, RES>
{
	/**
	 * Processes a packet part if the message sent is an instance of
	 * IMessageMultipleParts. This is used for uploading or downloading
	 * large packets of data.
	 * @param message The message.
	 * @param messageParts The multiple parts interface.
	 * @param player The player.
	 * @return The reply.
	 */
	protected RES processPart(final REQ message, final IMessageMultipleParts messageParts, final EntityPlayer player)
	{
		final DataInputStream input = new DataInputStream(new ByteArrayInputStream(messageParts.getPartData()));
		// Clear data in memory, copied into buffer
		//messageParts.clearPartData();

		try
		{
			/**
			 * If packet is empty, simply read and return
			 */
			if (input.available() <= 0)
			{
				messageParts.read(Unpooled.buffer());
				return this.onMessage(message, player);
			}

			final int partID = input.readInt();
			final int packetTotalParts = input.readByte();
			final int packetPart = input.readByte();
			final int dataLength = input.readInt();

			final byte[] byteFragment = new byte[dataLength];
			input.read(byteFragment);

			input.close();

			ArrayList<byte[]> byteArray = NetworkingAether.getPacketParts().get(partID);

			if (byteArray == null)
			{
				byteArray = Lists.newArrayList();

				NetworkingAether.getPacketParts().put(partID, byteArray);

				for (int i = 0; i < packetTotalParts; i++)
				{
					byteArray.add(new byte[0]);
				}
			}

			byteArray.set(packetPart, byteFragment);

			boolean hasAllInfo = true;

			for (final byte[] byteList : byteArray)
			{
				if (byteList.length == 0)
				{
					hasAllInfo = false;
					break;
				}
			}

			if (hasAllInfo)
			{
				final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

				for (final byte[] byteList : byteArray)
				{
					outputStream.write(byteList);
				}

				NetworkingAether.getPacketParts().remove(partID);

				messageParts.read(Unpooled.copiedBuffer(outputStream.toByteArray()));
				return this.onMessage(message, player);
			}
		}
		catch (final IOException e)
		{
			AetherCore.LOGGER.error("Couldn't process message part!", e);
		}

		return null;
	}

	public abstract RES onMessage(REQ message, EntityPlayer player);
}
