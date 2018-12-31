package com.gildedgames.aether.common.capabilities.entity.player.modules;

import com.gildedgames.aether.common.capabilities.entity.player.PlayerAether;
import com.gildedgames.aether.common.capabilities.entity.player.PlayerAetherModule;
import com.gildedgames.aether.common.entities.living.mobs.EntitySwet;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.math.Vec3i;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class PlayerTradeModule extends PlayerAetherModule
{

	public EntityPlayerMP target;

	public long requestedTime;

	public boolean trading;

	public Vec3i requestPosition;

	public PlayerTradeModule(final PlayerAether playerAether)
	{
		super(playerAether);
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
	public void write(final NBTTagCompound output)
	{
	}

	@Override
	public void read(final NBTTagCompound input)
	{
	}

}
