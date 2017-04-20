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
	private final boolean executesOnGameThread;

	public MessageHandlerClient()
	{
		this(true);
	}


	/**
	 * Creates a message handler for the client-side.
	 * @param safety True if this packet should process on the main game thread.
	 *               You almost always want this unless you need to reply instantly
	 *               to the packet.
	 */
	public MessageHandlerClient(boolean safety)
	{
		this.executesOnGameThread = safety;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public RES onMessage(REQ message, MessageContext ctx)
	{
		if (this.executesOnGameThread)
		{
			Minecraft.getMinecraft().addScheduledTask(() -> {
				this.onMessage(message, Minecraft.getMinecraft().player);
			});

			return null;
		}


		return this.onMessage(message, Minecraft.getMinecraft().player);
	}

	@SideOnly(Side.CLIENT)
	public abstract RES onMessage(REQ message, EntityPlayer player);
}
