package com.gildedgames.aether.common.network.packets.effects;

import com.gildedgames.aether.api.AetherCapabilities;
import com.gildedgames.aether.api.effects_system.IAetherStatusEffects;
import com.gildedgames.aether.common.network.MessageHandlerClient;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

import java.util.ArrayList;
import java.util.HashMap;

public class PacketStatusEffect implements IMessage
{

	private ArrayList<StatusEffectData> statusEffectData;
	private int entityID;
	private int numberOfDirtyEffects;

	public PacketStatusEffect()
	{

	}

	public PacketStatusEffect(final Entity entity)
	{
		this.entityID = entity.getEntityId();

		HashMap<String, IAetherStatusEffects> map =  entity.getCapability(AetherCapabilities.STATUS_EFFECT_POOL, null).getPool();
		this.statusEffectData = new ArrayList<>();

		for (IAetherStatusEffects effect : map.values())
		{
			if (effect.isDirty())
			{
				this.statusEffectData.add(new StatusEffectData(effect.getEffectType().numericValue, effect.getBuildup(), effect.getIsEffectApplied()));
				this.numberOfDirtyEffects++;
			}
		}
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		this.numberOfDirtyEffects = buf.readByte();
		this.entityID = buf.readInt();

		if (this.statusEffectData == null)
		{
			this.statusEffectData = new ArrayList<>();
		}
		for (int i = 0; i < this.numberOfDirtyEffects; i++)
		{
			int effectId = buf.readByte();
			int effectBuildup = buf.readByte();
			boolean isEffectApplied = buf.readBoolean();

			this.statusEffectData.add(new StatusEffectData(effectId,effectBuildup,isEffectApplied));
		}
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		buf.writeByte(this.numberOfDirtyEffects);
		buf.writeInt(this.entityID);

		for (StatusEffectData data : this.statusEffectData)
		{
			buf.writeByte(data.effectId);
			buf.writeByte(data.buildup);
			buf.writeBoolean(data.isApplied);
		}
	}

	private class StatusEffectData
	{
		private final int effectId;
		private final int buildup;
		private final boolean isApplied;

		StatusEffectData(int effectTypeId, int buildup, boolean isApplied)
		{
			this.effectId = effectTypeId;
			this.buildup = buildup;
			this.isApplied = isApplied;
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
					if (message.statusEffectData != null)
					{
						for (StatusEffectData data : message.statusEffectData)
						{
							IAetherStatusEffects effect = map.get(IAetherStatusEffects.effectTypes.getEffectFromNumericValue(data.effectId).name);
							effect.setBuildup(data.buildup);
							effect.setApplied(data.isApplied);
						}
					}
				}
			}

			return null;
		}

	}
}
