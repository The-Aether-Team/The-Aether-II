package com.gildedgames.aether.common.capabilities.entity.effects.processors;

import com.gildedgames.aether.api.capabilites.entity.effects.EntityEffectInstance;
import com.gildedgames.aether.api.capabilites.entity.effects.EntityEffectRule;
import com.gildedgames.aether.api.capabilites.entity.properties.ElementalState;
import com.gildedgames.aether.common.capabilities.entity.effects.AbstractEffectProcessor;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.event.entity.living.LivingAttackEvent;

import java.util.List;

public class SetEnemiesOnFireEffect extends AbstractEffectProcessor<EntityEffectInstance>
{

	public SetEnemiesOnFireEffect()
	{
		super("ability.setEnemiesOnFire.name", "ability.setEnemiesOnFire.desc");
	}

	@Override
	public void onAttack(float amount, Entity target, Entity source, List<EntityEffectInstance> all)
	{
		if (target != null)
		{
			target.setFire(2);
		}
	}

}
