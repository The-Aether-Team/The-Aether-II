package com.gildedgames.aether.common.network.packets;

import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.entities.animals.EntityAerbunny;
import com.gildedgames.aether.common.network.MessageHandlerClient;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import com.gildedgames.aether.common.network.IMessage;

public class PacketAerbunnySetRiding implements IMessage
{
	private int entityId;

	private int playerId;

	public PacketAerbunnySetRiding(PlayerEntity player, EntityAerbunny bunny)
	{
		if (player != null)
		{
			this.playerId = player.getEntityId();
		}
		else
		{
			this.playerId = -1;
		}

		this.entityId = bunny.getEntityId();
	}

	@Override
	public void fromBytes(PacketBuffer buf)
	{
		this.entityId = buf.readInt();
		this.playerId = buf.readInt();
	}

	@Override
	public void toBytes(PacketBuffer buf)
	{
		buf.writeInt(this.entityId);
		buf.writeInt(this.playerId);
	}

	public static class HandlerClient extends MessageHandlerClient<PacketAerbunnySetRiding>
	{
		@Override
		protected void onMessage(PacketAerbunnySetRiding message, ClientPlayerEntity player)
		{
			Entity aerbunny = player.world.getEntityByID(message.entityId);

			if (!(aerbunny instanceof EntityAerbunny))
			{
				AetherCore.LOGGER.warn("Received PacketAerbunnySetRiding for invalid entity with identifier " + message.entityId);

				return;
			}

			((EntityAerbunny) aerbunny).dismountEntity(player);

			if (message.playerId >= 0)
			{
				Entity otherPlayer = player.world.getEntityByID(message.playerId);

				if (otherPlayer != null)
				{
					aerbunny.startRiding(otherPlayer, true);

					player.world.playSound(player, player.getPosition(), new SoundEvent(AetherCore.getResource("mob.aerbunny.lift")), SoundCategory.NEUTRAL, 1.0F,
							0.8F + (player.getRNG().nextFloat() * 0.5F));

					AetherCore.PROXY.displayDismountMessage(player);
				}
			}
		}
	}
}
