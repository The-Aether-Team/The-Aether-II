package com.gildedgames.aether.common.entities.companions;

import com.gildedgames.aether.common.player.PlayerAether;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class EntityFrostpineTotem extends EntityBasicCompanion
{
	public EntityFrostpineTotem(World worldIn)
	{
		super(worldIn);

		this.setSize(0.9f, 2.1f);
		this.setPotion(Potion.getPotionFromResourceLocation("night_vision"), 0);
	}
}
