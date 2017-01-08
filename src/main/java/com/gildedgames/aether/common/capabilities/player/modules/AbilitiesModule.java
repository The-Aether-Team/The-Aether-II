package com.gildedgames.aether.common.capabilities.player.modules;

import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.capabilities.player.PlayerAether;
import com.gildedgames.aether.common.capabilities.player.PlayerAetherModule;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.fml.common.eventhandler.Event;

public class AbilitiesModule extends PlayerAetherModule
{

	private int jumpsSoFar;

	private int midAirJumpsAllowed;

	private int ticksAirborne;

	public AbilitiesModule(PlayerAether playerAether)
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

	public void onUpdate()
	{
		if (this.getEntity().onGround)
		{
			this.jumpsSoFar = 0;
			this.ticksAirborne = 0;
		}
		else
		{
			this.ticksAirborne++;
		}
	}

	public void onFall(LivingFallEvent event)
	{
		if (this.getMidAirJumpsAllowed() > 0)
		{
			event.setResult(Event.Result.DENY);
		}
	}

	public int getTicksAirborne()
	{
		return this.ticksAirborne;
	}

	public boolean performMidAirJump()
	{
		if (this.jumpsSoFar < this.midAirJumpsAllowed && this.ticksAirborne > 2)
		{
			AetherCore.PROXY.spawnJumpParticles(this.getEntity().worldObj, this.getEntity().posX, this.getEntity().posY, this.getEntity().posZ, 1.5D, 12);

			this.getEntity().motionY = 0.55D;
			this.getEntity().fallDistance = -4;

			this.getEntity().motionX *= 1.4D;
			this.getEntity().motionZ *= 1.4D;

			this.jumpsSoFar++;

			return true;
		}

		return false;
	}

	@Override
	public void write(NBTTagCompound tag)
	{
		NBTTagCompound root = new NBTTagCompound();
		tag.setTag("Abilities", root);

		tag.setInteger("midAirJumpsAllowed", this.midAirJumpsAllowed);
		tag.setInteger("jumpsSoFar", this.jumpsSoFar);
	}

	@Override
	public void read(NBTTagCompound tag)
	{
		NBTTagCompound root = tag.getCompoundTag("Abilities");

		this.midAirJumpsAllowed = root.getInteger("midAirJumpsAllowed");
		this.jumpsSoFar = root.getInteger("jumpsSoFar");
	}

}
