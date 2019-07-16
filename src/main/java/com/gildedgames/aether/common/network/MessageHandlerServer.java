package com.gildedgames.aether.common.network;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.world.ServerWorld;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public abstract class MessageHandlerServer<REQ extends IMessage> extends MessageHandler<REQ>
{
	private final boolean executesOnGameThread;

	public MessageHandlerServer()
	{
		this(true);
	}

	/**
	 * Creates a message handler for the server-side.
	 * @param mainThread True if this packet should process on the main game thread.
	 *               You almost always want this unless you need to reply instantly
	 *               to the packet.
	 */
	public MessageHandlerServer(final boolean mainThread)
	{
		this.executesOnGameThread = mainThread;
	}

	@Override
	public void accept(REQ message, Supplier<NetworkEvent.Context> contextSupplier)
	{
		NetworkEvent.Context context = contextSupplier.get();

		if (context == null)
		{
			throw new IllegalStateException("Could not obtain network event context");
		}

		ServerPlayerEntity entity = context.getSender();

		if (entity == null)
		{
			throw new IllegalStateException("Could not obtain sending entity for network packet");
		}

		ServerWorld world = entity.getServerWorld();

		if (this.executesOnGameThread)
		{
			context.enqueueWork(() -> this.onMessage(message, entity));
		}
		else
		{
			this.onMessage(message, entity);
		}
	}

	protected abstract void onMessage(REQ message, ServerPlayerEntity player);
}
