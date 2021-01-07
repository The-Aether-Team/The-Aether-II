package com.gildedgames.aether.common.entities.animals;

import com.gildedgames.aether.api.entity.damage.IDefenseLevelsHolder;
import com.gildedgames.aether.api.entity.effects.IAetherStatusEffectPool;
import com.gildedgames.aether.api.entity.effects.IAetherStatusEffects;
import com.gildedgames.aether.api.registrar.BlocksAether;
import com.gildedgames.aether.api.registrar.CapabilitiesAether;
import com.gildedgames.aether.common.entities.ai.AetherNavigateGround;
import com.gildedgames.aether.common.entities.effects.StatusEffect;
import com.google.common.collect.Maps;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.item.ItemStack;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.HashMap;
import java.util.Map;

public abstract class EntityAetherAnimal extends EntityAnimal implements IDefenseLevelsHolder
{
	private Map<IAetherStatusEffects.effectTypes, Boolean> applicationTracker = new HashMap<>();
	private Map<IAetherStatusEffects.effectTypes, Double> resistances = new HashMap<>();

	public EntityAetherAnimal(World world)
	{
		super(world);
	}

	@Override
	public float getBlockPathWeight(BlockPos pos)
	{
		return this.world.getBlockState(pos.down()).getBlock() == BlocksAether.aether_grass ? 10.0F :
				this.world.getLightBrightness(pos) - 0.5F;
	}

	@Override
	protected PathNavigate createNavigator(final World worldIn)
	{
		return new AetherNavigateGround(this, worldIn);
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

	protected Map<IAetherStatusEffects.effectTypes, Double> getResistances()
	{
		return resistances;
	}
}
