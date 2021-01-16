package com.gildedgames.aether.common.entities.monsters;

import com.gildedgames.aether.api.entity.damage.IDefenseLevelsHolder;
import com.gildedgames.aether.api.entity.effects.IAetherStatusEffectPool;
import com.gildedgames.aether.api.entity.effects.IAetherStatusEffects;
import com.gildedgames.aether.api.registrar.CapabilitiesAether;
import com.gildedgames.aether.common.entities.ai.AetherNavigateGround;
import com.gildedgames.aether.common.entities.effects.IEntityResistanceHolder;
import com.google.common.collect.Maps;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.world.World;

import java.util.HashMap;
import java.util.Map;

public class EntityAetherMob extends EntityMob implements IDefenseLevelsHolder, IEntityResistanceHolder
{
	private Map<IAetherStatusEffects.effectTypes, Boolean> applicationTracker = new HashMap<>();
	private Map<IAetherStatusEffects.effectTypes, Double> resistances = new HashMap<>();

	public EntityAetherMob(World world)
	{
		super(world);
	}

	@Override
	protected PathNavigate createNavigator(final World worldIn)
	{
		AetherNavigateGround navigateGround = new AetherNavigateGround(this, worldIn);

		navigateGround.setAvoidSun(true);

		return navigateGround;
	}

	protected void applyStatusEffectOnAttack(final Entity target)
	{

	}

	@Override
	public void onEntityUpdate()
	{
		IAetherStatusEffectPool statusEffectPool = this.getCapability(CapabilitiesAether.STATUS_EFFECT_POOL, null);

		if (statusEffectPool == null)
		{
			return;
		}

		for (Map.Entry<IAetherStatusEffects.effectTypes, Double> effect : resistances.entrySet())
		{
			applicationTracker.putIfAbsent(effect.getKey(), false);

			if (!applicationTracker.get(effect.getKey()))
			{
				statusEffectPool.addResistanceToEffect(effect.getKey(), effect.getValue());

				if (statusEffectPool.getResistanceToEffect(effect.getKey()) != 1.0D)
				{
					applicationTracker.put(effect.getKey(), true);
				}
			}
			else
			{
				if (statusEffectPool.getResistanceToEffect(effect.getKey()) == 1.0D)
				{
					applicationTracker.put(effect.getKey(), false);
				}
			}
		}

		super.onEntityUpdate();
	}

	public Map<IAetherStatusEffects.effectTypes, Double> getResistances()
	{
		return resistances;
	}
}
