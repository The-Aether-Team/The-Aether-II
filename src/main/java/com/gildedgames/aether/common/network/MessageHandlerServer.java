package com.gildedgames.aether.common.network;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public abstract class MessageHandlerServer<REQ extends IMessage, RES extends IMessage> implements IMessageHandler<REQ, RES>
{
	@Override
	public RES onMessage(REQ message, MessageContext ctx)
	{
		return this.onMessage(message, ctx.getServerHandler().playerEntity);
	}

	public abstract RES onMessage(REQ message, EntityPlayer player);
}
