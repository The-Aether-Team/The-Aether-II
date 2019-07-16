package com.gildedgames.aether.common.network;

import net.minecraft.network.PacketBuffer;

public interface IMessage
{
	default void fromBytes(final PacketBuffer buf)
	{

	}

	default void toBytes(final PacketBuffer buf)
	{

	}
}
