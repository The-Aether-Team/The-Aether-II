package com.gildedgames.aether.common.network.packets;

import com.gildedgames.aether.api.capabilites.entity.boss.IBoss;
import com.gildedgames.aether.common.capabilities.player.PlayerAether;
import com.gildedgames.aether.common.util.helpers.EntityUtil;
import com.gildedgames.aether.common.network.MessageHandlerClient;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

import java.util.UUID;

public class BossChangePacket implements IMessage
{

	private UUID bossEntityUUID;

	public BossChangePacket()
	{

	}

	public BossChangePacket(UUID bossEntityUUID)
	{
		this.bossEntityUUID = bossEntityUUID;
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		NBTTagCompound tag = ByteBufUtils.readTag(buf);

		this.bossEntityUUID = tag.getUniqueId("uuid");
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		NBTTagCompound tag = new NBTTagCompound();

		tag.setUniqueId("uuid", this.bossEntityUUID);

		ByteBufUtils.writeTag(buf, tag);
	}

	public static class Handler extends MessageHandlerClient<BossChangePacket,BossChangePacket>
	{
		@Override
		public BossChangePacket onMessage(BossChangePacket message, EntityPlayer player)
		{
			PlayerAether playerAether = PlayerAether.getPlayer(player);

			Entity entity = EntityUtil.getEntityFromUUID(player.worldObj, message.bossEntityUUID);

			if (entity instanceof IBoss)
			{
				IBoss<?> boss = (IBoss)entity;

				playerAether.getBossModule().setCurrentBoss(boss);
			}

			return null;
		}

	}
}
