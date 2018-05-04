package com.gildedgames.aether.common.capabilities.entity.player.modules;

import com.gildedgames.aether.api.AetherAPI;
import com.gildedgames.aether.api.patron.PatronDetails;
import com.gildedgames.aether.api.patron.PatronPayment;
import com.gildedgames.aether.common.capabilities.entity.player.PlayerAether;
import com.gildedgames.aether.common.capabilities.entity.player.PlayerAetherModule;
import com.gildedgames.aether.common.patron.PatronChoices;
import com.google.common.collect.Lists;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.time.YearMonth;

public class PlayerPatronRewards extends PlayerAetherModule
{
	private PatronChoices choices;

	private PatronDetails details;

	public PlayerPatronRewards(PlayerAether playerAether)
	{
		super(playerAether);

		this.details = new PatronDetails(Lists.newArrayList(new PatronPayment(5000, YearMonth.now())));
		this.choices = new PatronChoices(this.details, AetherAPI.content().patronRewards());
	}

	public PatronChoices getChoices()
	{
		return this.choices;
	}

	public PatronDetails getDetails()
	{
		return this.details;
	}

	@Override
	public void tickStart(TickEvent.PlayerTickEvent event)
	{

	}

	@Override
	public void tickEnd(TickEvent.PlayerTickEvent event)
	{

	}

	@Override
	public void onUpdate()
	{

	}

	@Override
	public void write(NBTTagCompound nbtTagCompound)
	{

	}

	@Override
	public void read(NBTTagCompound nbtTagCompound)
	{

	}
}
