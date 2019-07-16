package com.gildedgames.aether.common.network;

import net.minecraft.network.PacketBuffer;

import java.util.function.BiConsumer;

public class MessageEncoder<MSG extends IMessage> implements BiConsumer<MSG, PacketBuffer>
{
	@Override
	public void accept(MSG msg, PacketBuffer packetBuffer)
	{
		msg.toBytes(packetBuffer);
	}
}
