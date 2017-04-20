package com.gildedgames.aether.common.network;

import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketUtilAether
{
	public static <REQ extends IMessage, RES extends IMessage>
			void checkThread(MessageHandlerServer<REQ, RES> handler, REQ message, MessageContext ctx)
	{
		WorldServer world = ctx.getServerHandler().player.getServerWorld();

		if (!world.isCallingFromMinecraftThread())
		{
			world.addScheduledTask(() -> {
				handler.onMessage(message, ctx);
			});
		}
	}
}
