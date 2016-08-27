package com.gildedgames.aether.common.capabilities.player.modules;

import com.gildedgames.aether.api.player.IPlayerAetherCapability;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.capabilities.player.PlayerAetherModule;
import com.gildedgames.aether.common.items.ItemsAether;
import com.gildedgames.aether.common.util.PlayerUtil;
import net.minecraft.block.material.Material;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.fml.common.eventhandler.Event;

public class AbilitiesModule extends PlayerAetherModule
{

	private boolean hasDoubleJumped;

	private int ticksAirborne;

	public AbilitiesModule(IPlayerAetherCapability playerAether)
	{
		super(playerAether);
	}

	@Override
	public void onUpdate(LivingEvent.LivingUpdateEvent event)
	{
		if (this.getPlayer().onGround)
		{
			this.hasDoubleJumped = false;
			this.ticksAirborne = 0;
		}
		else
		{
			this.ticksAirborne++;
		}

		if (PlayerUtil.isWearingEquipment(this.getPlayerAether(), ItemsAether.obsidian_armor_set))
		{
			this.getPlayer().setSprinting(false);
		}
		else if (PlayerUtil.isWearingEquipment(this.getPlayerAether(), ItemsAether.phoenix_armor_set))
		{
			this.getPlayer().addPotionEffect(new PotionEffect(MobEffects.FIRE_RESISTANCE, 2, 0, false, false));

			this.getPlayer().extinguish();
		}
		else if (PlayerUtil.isWearingEquipment(this.getPlayerAether(), ItemsAether.neptune_armor_set))
		{
			if (this.getPlayer().isInsideOfMaterial(Material.WATER))
			{
				this.getPlayer().addPotionEffect(new PotionEffect(MobEffects.WATER_BREATHING, 2, 0, false, false));
			}
		}
	}

	@Override
	public void onFall(LivingFallEvent event)
	{
		if (PlayerUtil.isWearingEquipment(this.getPlayerAether(), ItemsAether.gravitite_armor_set))
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
	public boolean performDoubleJump()
	{
		if (!this.hasDoubleJumped && this.ticksAirborne > 2)
		{
			AetherCore.PROXY.spawnJumpParticles(this.getPlayer().worldObj, this.getPlayer().posX, this.getPlayer().posY, this.getPlayer().posZ, 1.5D, 12);

			this.getPlayer().motionY = 0.55D;
			this.getPlayer().fallDistance = -4;

			this.getPlayer().motionX *= 1.4D;
			this.getPlayer().motionZ *= 1.4D;

			this.hasDoubleJumped = true;

			return true;
		}

		return false;
	}

}
