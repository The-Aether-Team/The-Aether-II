package com.gildedgames.aether.common.entities.companions;

import com.gildedgames.aether.common.player.PlayerAether;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class EntityFrostpineTotem extends EntityCompanion
{
	public EntityFrostpineTotem(World worldIn)
	{
		super(worldIn);
	}

	@Override
	public void tickEffects(PlayerAether aePlayer)
	{
		aePlayer.getPlayer().addPotionEffect(new PotionEffect(Potion.getPotionFromResourceLocation("night_vision"), 250, 0));
	}
}
