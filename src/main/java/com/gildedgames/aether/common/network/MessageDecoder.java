package com.gildedgames.aether.common.network;

import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.unsafe.UnsafeHacks;

import java.util.function.Function;

public class MessageDecoder<MSG extends IMessage> implements Function<PacketBuffer, MSG>
{
	private final Class<MSG> clazz;

	public MessageDecoder(Class<MSG> clazz)
	{
		this.clazz = clazz;
	}

	@Override
	public MSG apply(PacketBuffer packetBuffer)
	{
		MSG obj = UnsafeHacks.newInstance(this.clazz);
		obj.fromBytes(packetBuffer);

		return obj;
	}
}
