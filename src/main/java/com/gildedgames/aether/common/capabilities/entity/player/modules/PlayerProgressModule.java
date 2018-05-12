package com.gildedgames.aether.common.capabilities.entity.player.modules;

import com.gildedgames.aether.common.capabilities.entity.player.PlayerAether;
import com.gildedgames.aether.common.capabilities.entity.player.PlayerAetherModule;
import com.gildedgames.aether.common.items.IDropOnDeath;
import com.gildedgames.aether.common.registry.content.DimensionsAether;
import com.gildedgames.orbis_api.util.io.NBTFunnel;
import com.google.common.collect.Lists;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemDoor;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.PlayerDropsEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.List;

public class PlayerProgressModule extends PlayerAetherModule
{

	public boolean hasDiedInAether;

	private boolean hasTalkedToEdison;

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

	public boolean hasTalkedToEdison()
	{
		return this.hasTalkedToEdison;
	}

	public void setHasTalkedToEdison(boolean flag)
	{
		this.hasTalkedToEdison = flag;
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
		tag.setBoolean("hasDiedInAether", this.hasDiedInAether);
		tag.setBoolean("hasTalkedToEdison", this.hasTalkedToEdison);
	}

	@Override
	public void read(NBTTagCompound tag)
	{
		this.hasDiedInAether = tag.getBoolean("hasDiedInAether");
		this.hasTalkedToEdison = tag.getBoolean("hasTalkedToEdison");
	}
}
