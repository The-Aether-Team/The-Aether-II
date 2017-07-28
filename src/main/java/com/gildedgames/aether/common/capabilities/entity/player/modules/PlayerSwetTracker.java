package com.gildedgames.aether.common.capabilities.entity.player.modules;

import com.gildedgames.aether.common.capabilities.entity.player.PlayerAether;
import com.gildedgames.aether.common.capabilities.entity.player.PlayerAetherModule;
import com.gildedgames.aether.common.entities.living.mobs.EntitySwet;
import com.gildedgames.aether.common.util.helpers.EntityUtil;
import com.google.common.collect.Lists;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

import java.util.List;

public class PlayerSwetTracker extends PlayerAetherModule
{

	public static final int MAX_SWET_COUNT = 3;

	private final List<EntitySwet> swets = Lists.newArrayList();

	public PlayerSwetTracker(final PlayerAether playerAether)
	{
		super(playerAether);
	}

	public List<EntitySwet> getLatchedSwets()
	{
		return this.swets;
	}

	public void detachSwets()
	{
		if (!this.getEntity().world.isRemote)
		{
			for (final EntitySwet swet : this.swets)
			{
				swet.setPosition(this.getEntity().posX, this.getEntity().posY, this.getEntity().posZ);
				swet.setFoodSaturation(0);

				this.getEntity().world.spawnEntity(swet);
			}
		}

		this.swets.clear();
	}

	public void detachSwet(final EntitySwet swet)
	{
		this.swets.remove(swet);

		if (!this.getEntity().world.isRemote)
		{
			swet.setPosition(this.getEntity().posX, this.getEntity().posY, this.getEntity().posZ);

			this.getEntity().world.spawnEntity(swet);
		}
	}

	public boolean canLatchOn()
	{
		return this.swets.size() < PlayerSwetTracker.MAX_SWET_COUNT;
	}

	public void latchSwet(final EntitySwet swet)
	{
		if (!this.canLatchOn())
		{
			return;
		}

		this.swets.add(EntityUtil.clone(swet));

		swet.setDead();
	}

	@Override
	public void onUpdate()
	{
		final EntityPlayer player = this.getEntity();

		if (player.isInWater())
		{
			this.detachSwets();
		}

		for (int i = 0; i < this.swets.size(); i++)
		{
			final EntitySwet swet = this.swets.get(i);

			swet.processSucking(this.getEntity());
		}
	}

	@Override
	public void write(final NBTTagCompound output)
	{
		final NBTTagList list = new NBTTagList();

		for (int i = 0; i < this.swets.size(); i++)
		{
			final EntitySwet swet = this.swets.get(i);
			final NBTTagCompound tag = new NBTTagCompound();

			swet.writeEntityToNBT(tag);

			list.appendTag(tag);
		}

		output.setTag("swets", list);
	}

	@Override
	public void read(final NBTTagCompound input)
	{
		final NBTTagList list = input.getTagList("swets", 10);

		for (int i = 0; i < list.tagCount(); i++)
		{
			final NBTTagCompound compound = list.getCompoundTagAt(i);

			final EntitySwet swet = new EntitySwet(this.getEntity().getEntityWorld());
			swet.readEntityFromNBT(compound);

			this.swets.add(swet);
		}
	}

}