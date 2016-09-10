package com.gildedgames.aether.common.capabilities.player.modules;

import com.gildedgames.aether.api.player.IPlayerAetherCapability;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.capabilities.player.PlayerAetherImpl;
import com.gildedgames.aether.common.capabilities.player.PlayerAetherModule;
import com.gildedgames.aether.common.items.ItemsAether;
import com.gildedgames.aether.common.util.PlayerUtil;
import net.minecraft.block.material.Material;
import net.minecraft.init.MobEffects;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.fml.common.eventhandler.Event;

public class AbilitiesModule extends PlayerAetherModule
{

	private int jumpsSoFar;

	private int midAirJumpsAllowed;

	private int ticksAirborne;

	public AbilitiesModule(PlayerAetherImpl playerAether)
	{
		super(playerAether);
	}

	public int getMidAirJumpsAllowed()
	{
		return this.midAirJumpsAllowed;
	}

	public void setMidAirJumpsAllowed(int midAirJumpsAllowed)
	{
		this.midAirJumpsAllowed = midAirJumpsAllowed;
	}

	@Override
	public void onUpdate(LivingEvent.LivingUpdateEvent event)
	{
		if (this.getPlayer().onGround)
		{
			this.jumpsSoFar = 0;
			this.ticksAirborne = 0;
		}
		else
		{
			this.ticksAirborne++;
		}
	}

	@Override
	public void onFall(LivingFallEvent event)
	{
		if (this.getMidAirJumpsAllowed() > 0)
		{
			event.setResult(Event.Result.DENY);
		}
	}

	@Override
	public int getTicksAirborne()
	{
		return this.ticksAirborne;
	}

	@Override
	public boolean performMidAirJump()
	{
		if (this.jumpsSoFar < this.midAirJumpsAllowed && this.ticksAirborne > 2)
		{
			AetherCore.PROXY.spawnJumpParticles(this.getPlayer().worldObj, this.getPlayer().posX, this.getPlayer().posY, this.getPlayer().posZ, 1.5D, 12);

			this.getPlayer().motionY = 0.55D;
			this.getPlayer().fallDistance = -4;

			this.getPlayer().motionX *= 1.4D;
			this.getPlayer().motionZ *= 1.4D;

			this.jumpsSoFar++;

			return true;
		}

		return false;
	}

	@Override
	public void write(NBTTagCompound tag)
	{
		tag.setInteger("midAirJumpsAllowed", this.midAirJumpsAllowed);
		tag.setInteger("jumpsSoFar", this.jumpsSoFar);
	}

	@Override
	public void read(NBTTagCompound tag)
	{
		this.midAirJumpsAllowed = tag.getInteger("midAirJumpsAllowed");
		this.jumpsSoFar = tag.getInteger("jumpsSoFar");
	}

}
