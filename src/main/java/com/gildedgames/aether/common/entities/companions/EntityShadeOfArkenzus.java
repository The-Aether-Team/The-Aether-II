package com.gildedgames.aether.common.entities.companions;

import com.gildedgames.aether.common.player.PlayerAether;
import net.minecraft.potion.Potion;
import net.minecraft.world.World;

public class EntityShadeOfArkenzus extends EntityCompanion
{
	private final Potion potion = Potion.getPotionFromResourceLocation("slowfall");

	public EntityShadeOfArkenzus(World worldIn)
	{
		super(worldIn);
	}

	@Override
	public void tickEffects(PlayerAether aePlayer)
	{

	}

	@Override
	public void addEffects(PlayerAether aePlayer)
	{

	}

	@Override
	public void removeEffects(PlayerAether aePlayer)
	{

	}
}
