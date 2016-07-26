package com.gildedgames.aether.common.entities.companions;

import com.gildedgames.aether.common.player.PlayerAether;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class EntityFrostpineTotem extends EntityCompanion
{
	private final Potion potion = Potion.getPotionFromResourceLocation("night_vision");

	public EntityFrostpineTotem(World worldIn)
	{
		super(worldIn);
	}

	@Override
	public void tickEffects(PlayerAether aePlayer)
	{
		// Avoid sending potion packets every tick
		if (this.ticksExisted % 100 == 0)
		{
			this.giveNightVision(aePlayer.getPlayer());
		}
	}

	@Override
	public void addEffects(PlayerAether aePlayer)
	{
		this.giveNightVision(aePlayer.getPlayer());
	}

	@Override
	public void removeEffects(PlayerAether aePlayer)
	{
		this.removeNightVision(aePlayer.getPlayer());
	}

	private void giveNightVision(EntityPlayer player)
	{
		player.addPotionEffect(new PotionEffect(this.potion, 600, 0, true, false));
	}

	private void removeNightVision(EntityPlayer player)
	{
		player.removePotionEffect(this.potion);
	}
}
