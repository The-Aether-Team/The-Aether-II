package com.gildedgames.aether.common.network.packets;

import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.entities.living.passive.EntityAerbunny;
import com.gildedgames.aether.common.network.MessageHandlerClient;
import com.gildedgames.aether.common.registry.content.SoundsAether;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.SoundCategory;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class PacketAerbunnySetRiding implements IMessage
{
	private int entityId;
	private int playerId;

	public PacketAerbunnySetRiding(EntityPlayer player, EntityAerbunny bunny)
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

	public PacketAerbunnySetRiding()
	{

	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		this.entityId = buf.readInt();
		this.playerId = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		buf.writeInt(this.entityId);
		buf.writeInt(this.playerId);
	}

	public static class HandlerClient extends MessageHandlerClient<PacketAerbunnySetRiding, IMessage>
	{
		@Override
		public IMessage onMessage(PacketAerbunnySetRiding message, EntityPlayer player)
		{
			Entity aerbunny = player.world.getEntityByID(message.entityId);

			if (!(aerbunny instanceof EntityAerbunny))
			{
				return null;
			}

			aerbunny.dismountRidingEntity();

			if (message.playerId >= 0)
			{
				Entity otherPlayer = player.world.getEntityByID(message.playerId);

				if (otherPlayer != null)
				{
					aerbunny.startRiding(otherPlayer, true);

					player.world.playSound(player, player.getPosition(), SoundsAether.aerbunny_lift, SoundCategory.NEUTRAL, 1.0F,
							0.8F + (player.getRNG().nextFloat() * 0.5F));

					AetherCore.PROXY.displayDismountMessage(player);
				}
			}

			return null;
		}
	}
}
