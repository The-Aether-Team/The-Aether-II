package com.gildedgames.aether.common.capabilities.entity.player.modules;

import com.gildedgames.aether.api.AetherAPI;
import com.gildedgames.aether.api.net.data.UserFeatures;
import com.gildedgames.aether.common.capabilities.entity.player.PlayerAether;
import com.gildedgames.aether.common.capabilities.entity.player.PlayerAetherModule;
import com.gildedgames.aether.common.patron.PatronChoices;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class PlayerPatronRewards extends PlayerAetherModule
{
	private PatronChoices choices;

	private UserFeatures features = new UserFeatures();

	private Future<UserFeatures> featuresFuture;

	public PlayerPatronRewards(PlayerAether playerAether)
	{
		super(playerAether);

		this.choices = new PatronChoices(this, AetherAPI.content().patronRewards());
	}

	public PatronChoices getChoices()
	{
		return this.choices;
	}

	public UserFeatures getFeatures()
	{
		return this.features;
	}

	@Override
	public void onEntityJoinWorld()
	{
		this.featuresFuture = AetherAPI.services().gildedGamesAccountApi().retrieveUserFeatures(this.getEntity().getGameProfile().getId());
	}

	@Override
	public void tickStart(TickEvent.PlayerTickEvent event)
	{
		if (this.featuresFuture != null && this.featuresFuture.isDone())
		{
			try
			{
				UserFeatures features = this.featuresFuture.get();

				if (features != null)
				{
					this.features = features;
				}
			}
			catch (InterruptedException | ExecutionException e)
			{
				e.printStackTrace();
			}

			this.featuresFuture = null;
		}
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
