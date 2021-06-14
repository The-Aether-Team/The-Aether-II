package com.gildedgames.aether.common.capabilities.entity.player.modules;

import com.gildedgames.aether.api.player.IPlayerAetherModule;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.capabilities.entity.player.PlayerAether;
import com.gildedgames.aether.common.capabilities.entity.player.PlayerAetherModule;
import com.gildedgames.aether.common.entities.monsters.EntitySwet;
import com.gildedgames.aether.common.util.helpers.EntityUtil;
import com.google.common.collect.Lists;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.Iterator;
import java.util.List;

public class PlayerSwetTrackerModule extends PlayerAetherModule implements IPlayerAetherModule.Serializable
{

	public static final int MAX_SWET_COUNT = 3;

	private final List<EntitySwet> swets = Lists.newArrayList();

	public PlayerSwetTrackerModule(final PlayerAether playerAether)
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
				swet.setFoodSaturation(0);
				this.spawnSwet(swet);
			}
		}

		this.swets.clear();
	}

	public void detachSwet(final EntitySwet swet)
	{
		this.swets.remove(swet);
		this.spawnSwet(swet);
	}

	public void spawnSwet(final EntitySwet swet)
	{
		if (!this.getEntity().world.isRemote)
		{
			swet.setPosition(this.getEntity().posX, this.getEntity().posY, this.getEntity().posZ);

			//When the server loads the swet from nbt with read() it is created in dimension 0, because the player has not loaded yet
			int dimension = this.getEntity().dimension;
			if (swet.dimension != dimension)
			{
				swet.changeDimension(dimension);
			}
			else
			{
				this.getEntity().world.spawnEntity(swet);
			}
		}
	}

	public boolean canLatchOn()
	{
		return this.swets.size() < PlayerSwetTrackerModule.MAX_SWET_COUNT;
	}

	public void latchSwet(final EntitySwet swet)
	{
		if (!this.canLatchOn())
		{
			return;
		}

		this.swets.add(EntityUtil.clone(swet));

		this.getEntity().world.removeEntity(swet);
	}

	@Override
	public void tickStart(TickEvent.PlayerTickEvent event)
	{
		final EntityPlayer player = this.getEntity();

		if (player.isInWater())
		{
			this.detachSwets();
		}

		final Iterator<EntitySwet> it = this.swets.iterator();

		while (it.hasNext())
		{
			final EntitySwet swet = it.next();

			if (swet.processSucking(this.getEntity()))
			{
				it.remove();
				this.spawnSwet(swet);
			}
		}
	}

	@Override
	public void tickEnd(TickEvent.PlayerTickEvent event)
	{

	}

	@Override
	public void write(final NBTTagCompound output)
	{
		final NBTTagList list = new NBTTagList();

		for (final EntitySwet swet : this.swets)
		{
			final NBTTagCompound tag = new NBTTagCompound();

			swet.writeToNBT(tag);

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
			compound.removeTag("Dimension");

			final EntitySwet swet = new EntitySwet(this.getEntity().getEntityWorld());
			swet.readFromNBT(compound);

			this.swets.add(swet);
		}
	}

	@Override
	public ResourceLocation getIdentifier()
	{
		return AetherCore.getResource("swet");
	}
}