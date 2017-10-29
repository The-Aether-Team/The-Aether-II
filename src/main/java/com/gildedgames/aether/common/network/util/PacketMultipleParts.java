package com.gildedgames.aether.common.network.util;

import com.gildedgames.aether.common.AetherCore;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Arrays;

public abstract class PacketMultipleParts implements IMessage, IMessageMultipleParts
{
	private static final int PACKET_SIZE_LIMIT = 32000;

	private static int nextPartID = 0;

	private byte[] data;

	public PacketMultipleParts()
	{

	}

	public PacketMultipleParts(final byte[] data)
	{
		this.data = data;
	}

	@Override
	public IMessage[] getParts()
	{
		final ByteBuf buf = Unpooled.buffer();

		this.write(buf);

		final byte[] byteArray = new byte[buf.readableBytes()];
		buf.readBytes(byteArray);

		int dataLength = byteArray.length;

		final int packetTotalParts = (int) Math.ceil(dataLength / ((float) PACKET_SIZE_LIMIT));
		int partIndex = 0;

		int dataSize;

		final IMessage[] parts = new IMessage[Math.max(1, packetTotalParts)];

		/**
		 * If packet is empty, simply return one part
		 */
		if (dataLength <= 0)
		{
			parts[0] = this.createPart(new byte[0]);

			return parts;
		}

		while (dataLength > 0)
		{
			if (partIndex >= packetTotalParts)
			{
				dataSize = dataLength;
			}
			else
			{
				dataSize = (partIndex + 1) * PACKET_SIZE_LIMIT;
			}

			final byte[] byteFragment = Arrays.copyOfRange(byteArray, (partIndex) * PACKET_SIZE_LIMIT, dataSize);

			final ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
			final DataOutputStream outputFragment = new DataOutputStream(byteStream);

			try
			{
				outputFragment.writeInt(nextPartID);
				outputFragment.writeByte(packetTotalParts);
				outputFragment.writeByte(partIndex);
				outputFragment.writeInt(dataLength > PACKET_SIZE_LIMIT ? PACKET_SIZE_LIMIT : dataLength);

				outputFragment.write(byteFragment);
			}
			catch (final IOException e)
			{
				AetherCore.LOGGER.error("Couldn't write output fragment for message parts!", e);
			}

			parts[partIndex] = this.createPart(byteStream.toByteArray());

			partIndex++;
			dataLength -= PACKET_SIZE_LIMIT;
		}

		nextPartID++;

		return parts;
	}

	@Override
	public final void fromBytes(final ByteBuf buf)
	{
		this.data = new byte[buf.readableBytes()];
		buf.getBytes(buf.readerIndex(), this.data);
	}

	@Override
	public final void toBytes(final ByteBuf buf)
	{
		buf.writeBytes(this.data);
	}

	@Override
	public byte[] getPartData()
	{
		return this.data;
	}

	@Override
	public void clearPartData()
	{
		this.data = null;
	}

	public abstract PacketMultipleParts createPart(byte[] data);

}
