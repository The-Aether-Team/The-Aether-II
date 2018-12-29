package com.gildedgames.aether.common.capabilities.entity.player.modules;

import com.gildedgames.aether.common.capabilities.entity.player.PlayerAether;
import com.gildedgames.aether.common.capabilities.entity.player.PlayerAetherModule;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class PlayerEffectsModule extends PlayerAetherModule
{
	public PlayerEffectsModule(PlayerAether player)
	{
		super(player);
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
	}

	@Override
	public void read(NBTTagCompound tag)
	{
	}
}
