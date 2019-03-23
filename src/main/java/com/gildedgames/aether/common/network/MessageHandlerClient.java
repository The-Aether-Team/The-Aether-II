package com.gildedgames.aether.common.network;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public abstract class MessageHandlerClient<REQ extends IMessage, RES extends IMessage> extends MessageHandler<REQ, RES>
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
	public MessageHandlerClient(final boolean safety)
	{
		this.executesOnGameThread = safety;
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public RES onMessage(final REQ message, final MessageContext ctx)
	{
		final Minecraft mc = Minecraft.getMinecraft();

		if (this.executesOnGameThread)
		{
			mc.addScheduledTask(new FutureMessage<>(this, message));

			return null;
		}

		return this.onMessage(message, mc.player);
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public abstract RES onMessage(REQ message, EntityPlayer player);

	/**
	 * This is needed to prevent a crash on dedicated servers. It seems that
	 * {@link SideOnly} annotations don't apply to lambdas inside a method, causing
	 * client code to be loaded on the server. It's unfortunate, but it is what it is.
	 *
	 * @param <REQ> The original {@link IMessage} type
	 */
	@OnlyIn(Dist.CLIENT)
	private static class FutureMessage<REQ extends IMessage> implements Runnable
	{
		private final MessageHandlerClient<REQ, ?> handler;

		private final REQ message;

		private FutureMessage(final MessageHandlerClient<REQ, ?> handler, final REQ message)
		{
			this.message = message;
			this.handler = handler;
		}

		@Override
		public void run()
		{
			this.handler.onMessage(this.message, Minecraft.getMinecraft().player);
		}
	}
}
