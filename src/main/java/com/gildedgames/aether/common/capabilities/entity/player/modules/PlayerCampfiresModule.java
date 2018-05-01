package com.gildedgames.aether.common.capabilities.entity.player.modules;

import com.gildedgames.aether.common.capabilities.entity.player.PlayerAether;
import com.gildedgames.aether.common.capabilities.entity.player.PlayerAetherModule;
import com.gildedgames.orbis_api.util.io.NBTFunnel;
import com.gildedgames.orbis_api.util.mc.BlockPosDimension;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class PlayerCampfiresModule extends PlayerAetherModule
{
	public BlockPosDimension lastCampfire;

	private boolean shouldRespawnAtCampfire;

	public PlayerCampfiresModule(final PlayerAether playerAether)
	{
		super(playerAether);
	}

	public BlockPosDimension getLastCampfire()
	{
		return this.lastCampfire;
	}

	public void setLastCampfire(BlockPosDimension lastCampfire)
	{
		this.lastCampfire = lastCampfire;
	}

	public void setShouldRespawnAtCampfire(boolean flag)
	{
		this.shouldRespawnAtCampfire = flag;
	}

	public boolean shouldRespawnAtCampfire()
	{
		return this.shouldRespawnAtCampfire;
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
	public void write(final NBTTagCompound compound)
	{
		NBTFunnel funnel = new NBTFunnel(compound);

		funnel.set("lastCampfire", this.lastCampfire);
		compound.setBoolean("shouldRespawnAtCampfire", this.shouldRespawnAtCampfire);
	}

	@Override
	public void read(final NBTTagCompound compound)
	{
		NBTFunnel funnel = new NBTFunnel(compound);

		this.lastCampfire = funnel.get("lastCampfire");
		this.shouldRespawnAtCampfire = compound.getBoolean("shouldRespawnAtCampfire");
	}
}
