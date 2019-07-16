package com.gildedgames.aether.common.network;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public abstract class MessageHandlerClient<REQ extends IMessage> extends MessageHandler<REQ>
{
	private final boolean executesOnGameThread;

	public MessageHandlerClient()
	{
		this(true);
	}

	/**
	 * Creates a message handler for the client-side.
	 * @param mainThread True if this packet should process on the main game thread.
	 *               You almost always want this unless you need to reply instantly
	 *               to the packet.
	 */
	public MessageHandlerClient(final boolean mainThread)
	{
		this.executesOnGameThread = mainThread;
	}

	@Override
	public final void accept(REQ message, Supplier<NetworkEvent.Context> contextSupplier)
	{
		final Minecraft mc = Minecraft.getInstance();

		if (this.executesOnGameThread)
		{
			mc.deferTask(new FutureMessage<>(this, message));
		}
		else
		{
			this.handleMessage(message);
		}
	}

	private void handleMessage(REQ message)
	{
		this.onMessage(message, Minecraft.getInstance().player);
	}

	protected abstract void onMessage(REQ message, ClientPlayerEntity player);

	/**
	 * This is needed to prevent a crash on dedicated servers. It seems that
	 * {@link OnlyIn} annotations don't apply to lambdas inside a method, causing
	 * client code to be loaded on the server. It's unfortunate, but it is what it is.
	 *
	 * @param <REQ> The original {@link IMessage} type
	 */
	@OnlyIn(Dist.CLIENT)
	private static class FutureMessage<REQ extends IMessage> implements Runnable
	{
		private final MessageHandlerClient<REQ> handler;

		private final REQ message;

		private FutureMessage(final MessageHandlerClient<REQ> handler, final REQ message)
		{
			this.message = message;
			this.handler = handler;
		}

		@Override
		public void run()
		{
			this.handler.handleMessage(this.message);
		}
	}
}
