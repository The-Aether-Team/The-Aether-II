package com.gildedgames.aether.common.capabilities.entity.player.modules;

import com.gildedgames.aether.common.capabilities.entity.player.PlayerAether;
import com.gildedgames.aether.common.capabilities.entity.player.PlayerAetherModule;
import com.gildedgames.orbis_api.util.io.NBTFunnel;
import com.google.common.collect.Maps;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.Map;

public class PlayerProgressModule extends PlayerAetherModule
{

	public boolean hasDiedInAether;

	private Map<ResourceLocation, Boolean> hasTalkedTo = Maps.newHashMap();

	private boolean returnedToBed;

	private BlockPos beforeReturnToBed;

	public PlayerProgressModule(PlayerAether playerAether)
	{
		super(playerAether);
	}

	public boolean hasDiedInAether()
	{
		return this.hasDiedInAether;
	}

	public void setHasDiedInAether(boolean flag)
	{
		this.hasDiedInAether = flag;
	}

	public void setHasTalkedTo(ResourceLocation speaker, boolean flag)
	{
		this.hasTalkedTo.put(speaker, flag);
	}

	public boolean hasTalkedTo(ResourceLocation speaker)
	{
		if (!this.hasTalkedTo.containsKey(speaker))
		{
			return false;
		}

		return this.hasTalkedTo.get(speaker);
	}

	public boolean hasReturnedToBed()
	{
		return this.returnedToBed;
	}

	public void setHasReturnedToBed(boolean flag)
	{
		this.returnedToBed = flag;
	}

	public BlockPos getBeforeReturnToBed()
	{
		return this.beforeReturnToBed;
	}

	public void setBeforeReturnToBed(BlockPos blockPos)
	{
		this.beforeReturnToBed = blockPos;
	}

	@Override
	public void onDeath(LivingDeathEvent event)
	{

	}

	@Override
	public void onRespawn(PlayerEvent.PlayerRespawnEvent event)
	{

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
	public void write(NBTTagCompound tag)
	{
		NBTFunnel funnel = new NBTFunnel(tag);

		tag.setBoolean("hasDiedInAether", this.hasDiedInAether);
		tag.setBoolean("returnedToBed", this.returnedToBed);
		funnel.setMap("hasTalkedTo", this.hasTalkedTo, NBTFunnel.LOC_SETTER, NBTFunnel.BOOLEAN_SETTER);
		funnel.setPos("beforeReturnToBed", this.beforeReturnToBed);
	}

	@Override
	public void read(NBTTagCompound tag)
	{
		NBTFunnel funnel = new NBTFunnel(tag);

		this.hasDiedInAether = tag.getBoolean("hasDiedInAether");
		this.returnedToBed = tag.getBoolean("returnedToBed");
		this.hasTalkedTo = funnel.getMap("hasTalkedTo", NBTFunnel.LOC_GETTER, NBTFunnel.BOOLEAN_GETTER);
		this.beforeReturnToBed = funnel.getPos("beforeReturnToBed");
	}
}
