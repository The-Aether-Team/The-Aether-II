package com.gildedgames.aether.common.network;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;

public abstract class MessageHandler<REQ extends IMessage, RES extends IMessage> implements IMessageHandler<REQ, RES>
{
	public abstract RES onMessage(REQ message, PlayerEntity player);
}
