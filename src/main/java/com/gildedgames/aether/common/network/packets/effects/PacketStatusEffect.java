package com.gildedgames.aether.common.network.packets.effects;

import com.gildedgames.aether.api.entity.effects.IAetherStatusEffectPool;
import com.gildedgames.aether.api.entity.effects.IAetherStatusEffects;
import com.gildedgames.aether.api.registrar.CapabilitiesAether;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.network.MessageHandlerClient;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class PacketStatusEffect implements IMessage
{
	IAetherStatusEffectPool capability;
	private int entityID;
	private NBTTagCompound tag;

	public PacketStatusEffect()
	{

	}

	public PacketStatusEffect(final Entity entity)
	{
		this.entityID = entity.getEntityId();

		capability =  entity.getCapability(CapabilitiesAether.STATUS_EFFECT_POOL, null);
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		this.tag = ByteBufUtils.readTag(buf);
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		NBTTagCompound tag = new NBTTagCompound();
		tag.setInteger("entityID", entityID);

		NBTTagList effects = new NBTTagList();

		for (IAetherStatusEffects effect : capability.getPool().values())
		{
			if(!effect.isDirty())
				continue;

			NBTTagCompound effectData = new NBTTagCompound();
			effect.write(effectData);

			effects.appendTag(effectData);
		}
		tag.setTag("statusEffects", effects);

		ByteBufUtils.writeTag(buf, tag);
	}

	public static class HandlerClient extends MessageHandlerClient<PacketStatusEffect, IMessage>
	{

		@Override
		public IMessage onMessage(PacketStatusEffect message, EntityPlayer player)
		{
			if (player == null || player.world == null || message.tag == null)
			{
				return null;
			}

			message.entityID = message.tag.getInteger("entityID");
			final Entity entity = player.world.getEntityByID(message.entityID);

			if (!(entity instanceof EntityLivingBase))
			{
				AetherCore.LOGGER.warn("Tried to set effects for non-living entity with ID " + message.entityID);

				return null;
			}

			final EntityLivingBase entityLiving = (EntityLivingBase) entity;
			IAetherStatusEffectPool map = entityLiving.getCapability(CapabilitiesAether.STATUS_EFFECT_POOL, null);

			if (map != null)
			{
				NBTTagList effects = message.tag.getTagList("statusEffects", 10);

				for(int i = 0; i < effects.tagCount(); i++)
				{
					NBTTagCompound compound = effects.getCompoundTagAt(i);

					IAetherStatusEffects effect = map.createEffect(compound.getString("type"), entityLiving);
					effect.read(compound);
				}
			}

			return null;
		}

	}
}
