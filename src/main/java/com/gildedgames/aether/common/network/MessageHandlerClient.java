package com.gildedgames.aether.common.network;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public abstract class MessageHandlerClient<REQ extends IMessage, RES extends IMessage> implements IMessageHandler<REQ, RES>
{
	@Override
	@SideOnly(Side.CLIENT)
	public RES onMessage(REQ message, MessageContext ctx)
	{
		return this.onMessage(message, Minecraft.getMinecraft().player);
	}

	@SideOnly(Side.CLIENT)
	public abstract RES onMessage(REQ message, EntityPlayer player);
}
