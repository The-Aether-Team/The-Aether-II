package com.gildedgames.aether.common.capabilities.player.modules;

import com.gildedgames.aether.api.capabilites.entity.boss.IBoss;
import com.gildedgames.aether.api.capabilites.entity.boss.IBossManager;
import com.gildedgames.aether.common.capabilities.player.PlayerAether;
import com.gildedgames.aether.common.capabilities.player.PlayerAetherModule;
import com.gildedgames.aether.common.network.NetworkingAether;
import com.gildedgames.aether.common.network.packets.BossChangePacket;
import com.gildedgames.aether.common.util.helpers.EntityUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;

import java.util.UUID;

public class BossModule extends PlayerAetherModule
{

	private IBoss<?> boss;

	private UUID bossEntityUUID;

	public BossModule(PlayerAether playerAether)
	{
		super(playerAether);
	}

	@Override
	public void onUpdate()
	{
		if (this.boss != null)
		{
			IBossManager<?> manager = this.boss.getBossManager();

			if (manager.getEntity() == null || manager.getEntity().isDead || this.getEntity().isDead || this.getEntity().getHealth() <= 0)
			{
				this.boss = null;
				this.bossEntityUUID = null;
			}
		}
		else if (this.bossEntityUUID != null)
		{
			Entity entity = EntityUtil.getEntityFromUUID(this.getEntity().getEntityWorld(), this.bossEntityUUID);

			if (entity instanceof IBoss)
			{
				this.boss = (IBoss) entity;
			}
			else
			{
				this.bossEntityUUID = null;
			}
		}
	}

	public IBoss<?> getCurrentBoss()
	{
		return this.boss;
	}

	public void setCurrentBoss(IBoss<?> boss)
	{
		if (boss == this.boss || boss.getBossManager().getEntity() == null || boss.getBossManager().getEntity().isDead)
		{
			return;
		}

		this.boss = boss;
		this.bossEntityUUID = this.boss.getBossManager().getEntity().getUniqueID();

		if (!this.getEntity().worldObj.isRemote)
		{
			NetworkingAether.sendPacketToPlayer(new BossChangePacket(this.bossEntityUUID), (EntityPlayerMP) this.getEntity());
		}
	}

	@Override
	public void write(NBTTagCompound tag)
	{
		NBTTagCompound root = new NBTTagCompound();
		tag.setTag("Boss", root);

		if (this.bossEntityUUID != null)
		{
			root.setUniqueId("EntityUUID", this.bossEntityUUID);
		}
	}

	@Override
	public void read(NBTTagCompound tag)
	{
		NBTTagCompound root = tag.getCompoundTag("Boss");

		if (root.hasKey("EntityUUID"))
		{
			this.bossEntityUUID = root.getUniqueId("EntityUUID");
		}
	}

}
