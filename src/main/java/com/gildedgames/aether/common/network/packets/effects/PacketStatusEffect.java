package com.gildedgames.aether.common.network.packets.effects;

import com.gildedgames.aether.api.AetherCapabilities;
import com.gildedgames.aether.api.effects_system.IAetherStatusEffectPool;
import com.gildedgames.aether.api.effects_system.IAetherStatusEffects;
import com.gildedgames.aether.common.network.MessageHandlerClient;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

import java.util.HashMap;

public class PacketStatusEffect implements IMessage
{

	private HashMap<String, IAetherStatusEffects> map;
	private int entityID;

	public PacketStatusEffect()
	{

	}

	public PacketStatusEffect(final Entity entity)
	{
		this.entityID = entity.getEntityId();

		this.map = entity.getCapability(AetherCapabilities.STATUS_EFFECT_POOL, null).getPool();
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		this.entityID = buf.readInt();

		for (int i = 0; i < IAetherStatusEffects.NUMBER_OF_EFFECTS; i++)
		{
			int effectId = buf.readInt();
			int effectBuildup = buf.readInt();
			boolean isEffectApplied = buf.readBoolean();

			String effectKey = IAetherStatusEffects.effectTypes.getEffectFromNumericValue(effectId).name;

			World world = Minecraft.getMinecraft().world;

			if (world != null)
			{
				Entity entity = world.getEntityByID(this.entityID);

				IAetherStatusEffectPool pool = entity.getCapability(AetherCapabilities.STATUS_EFFECT_POOL, null);

				this.map = pool.getPool();

				if (this.map != null)
				{
					this.map.get(effectKey).setBuildup(effectBuildup);
					this.map.get(effectKey).setApplied(isEffectApplied);

				}
			}
		}
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		buf.writeInt(this.entityID);

		for (IAetherStatusEffects effect : this.map.values())
		{
			buf.writeInt(effect.getEffectType().numericValue);
			buf.writeInt(effect.getBuildup());
			buf.writeBoolean(effect.getIsEffectApplied());
		}
	}

	public static class HandlerClient extends MessageHandlerClient<PacketStatusEffect, IMessage>
	{

		@Override
		public IMessage onMessage(PacketStatusEffect message, EntityPlayer player)
		{
			if (player == null || player.world == null)
			{
				return null;
			}

			final Entity entity = player.world.getEntityByID(message.entityID);

			if (entity != null && entity.hasCapability(AetherCapabilities.STATUS_EFFECT_POOL, null))
			{
				HashMap<String, IAetherStatusEffects> map = entity.getCapability(AetherCapabilities.STATUS_EFFECT_POOL, null).getPool();

				if (map != null)
				{
					for (IAetherStatusEffects effect : message.map.values())
					{
						map.put(effect.getEffectName(), effect);
					}
				}
			}

			return null;
		}
	}
}
