package com.gildedgames.aether.common.capabilities.entity.player.modules;

import com.gildedgames.aether.api.player.IPlayerAetherModule;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.capabilities.entity.player.PlayerAether;
import com.gildedgames.aether.common.capabilities.entity.player.PlayerAetherModule;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class PlayerAbilitiesModule extends PlayerAetherModule implements IPlayerAetherModule.Serializable
{

	private int jumpsSoFar;

	private int midAirJumpsAllowed;

	private int ticksAirborne;

	public PlayerAbilitiesModule(PlayerAether playerAether)
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
	public void tickStart(TickEvent.PlayerTickEvent event)
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
			AetherCore.PROXY.spawnJumpParticles(this.getEntity().world, this.getEntity().posX, this.getEntity().posY, this.getEntity().posZ, 1.5D, 12);

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
	public void write(CompoundNBT tag)
	{
		CompoundNBT root = new CompoundNBT();
		tag.put("Abilities", root);

		tag.putInt("midAirJumpsAllowed", this.midAirJumpsAllowed);
		tag.putInt("jumpsSoFar", this.jumpsSoFar);
	}

	@Override
	public void read(CompoundNBT tag)
	{
		CompoundNBT root = tag.getCompound("Abilities");

		this.midAirJumpsAllowed = root.getInt("midAirJumpsAllowed");
		this.jumpsSoFar = root.getInt("jumpsSoFar");
	}

	@Override
	public ResourceLocation getIdentifier()
	{
		return AetherCore.getResource("abilities");
	}
}
