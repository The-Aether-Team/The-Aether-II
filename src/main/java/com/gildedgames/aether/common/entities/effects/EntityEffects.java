package com.gildedgames.aether.common.entities.effects;

import com.gildedgames.aether.api.capabilites.AetherCapabilities;
import com.gildedgames.aether.api.capabilites.entity.effects.EntityEffectInstance;
import com.gildedgames.aether.api.capabilites.entity.effects.EntityEffectProcessor;
import com.gildedgames.aether.api.capabilites.entity.effects.IEffectPool;
import com.gildedgames.aether.api.capabilites.entity.effects.IEntityEffectsCapability;
import com.gildedgames.aether.common.entities.effects.processors.*;
import com.gildedgames.aether.common.entities.effects.processors.player.*;
import com.google.common.collect.Lists;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.IMob;
import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

import java.util.ArrayList;
import java.util.List;

public class EntityEffects implements IEntityEffectsCapability
{

	public static final EntityEffectProcessor<ModifyDamageEffect.Instance> PUNCHING_DAMAGE = new ModifyPunchingDamageEffect();

	public static final EffectProcessorPlayer<EntityEffectInstance> DAGGERFROST = new DaggerfrostEffect();

	public static final EffectProcessorPlayer<EntityEffectInstance> WEIGHT_TOLERANCE = new WeightToleranceEffect();

	public static final EffectProcessorPlayer<ModifyXPCollectionEffect.Instance> MODIFY_XP_COLLECTION = new ModifyXPCollectionEffect();

	public static final EffectProcessorPlayer<EntityEffectInstance> REDUCE_HUNGER = new ReduceHungerEffect();

	public static final EffectProcessorPlayer<EntityEffectInstance> PAUSE_HUNGER = new PauseHungerEffect();

	public static final EntityEffectProcessor<EntityEffectInstance> BREATHE_UNDERWATER = new BreatheUnderwaterEffect();

	public static final EntityEffectProcessor<DoubleDropEffect.Instance> DOUBLE_DROPS = new DoubleDropEffect();

	public static final EntityEffectProcessor<FreezeBlocksEffect.Instance> FREEZE_BLOCKS = new FreezeBlocksEffect();

	public static final EntityEffectProcessor<ModifyDamageEffect.Instance> MODIFY_DAMAGE = new ModifyDamageEffect();

	public static final EntityEffectProcessor<ModifyDefenseEffect.Instance> MODIFY_DEFENSE = new ModifyDefenseEffect();

	public static final EntityEffectProcessor<ModifyMaxHealthEffect.Instance> MODIFY_MAX_HEALTH = new ModifyMaxHealthEffect();

	public static final EntityEffectProcessor<ModifySpeedEffect.Instance> MODIFY_SPEED = new ModifySpeedEffect();

	public static final EntityEffectProcessor<RegenerateHealthEffect.Instance> REGENERATE_HEALTH = new RegenerateHealthEffect();

	public static final EntityEffectProcessor<EntityEffectInstance> INVISIBILITY = new InvisibilityEffect();

	private final Entity entity;

	private final List<IEffectPool<?>> effects = Lists.newArrayList();

	private int ticksSinceAttacked;

	public static IEntityEffectsCapability get(Entity entity)
	{
		return entity.getCapability(AetherCapabilities.ENTITY_EFFECTS, null);
	}

	public EntityEffects(Entity entity)
	{
		this.entity = entity;
	}

	@Override
	public List<IEffectPool<?>> getEffectPools()
	{
		return this.effects;
	}

	@SuppressWarnings("unchecked")
	private <I extends EntityEffectInstance> EffectPool<I> getPool(EntityEffectProcessor<I> processor)
	{
		for (IEffectPool<?> pool : this.effects)
		{
			if (pool.equals(processor))
			{
				return (EffectPool<I>) pool;
			}
		}

		EffectPool<I> pool = new EffectPool<>(processor, new ArrayList<I>());

		this.effects.add(pool);

		return pool;
	}

	@Override
	public <I extends EntityEffectInstance> void addInstance(EntityEffectProcessor<I> processor, I instance)
	{
		if (processor == null || instance == null)
		{
			return;
		}

		EffectPool<I> pool = this.getPool(processor);

		if (!pool.getInstances().contains(instance))
		{
			pool.getInstances().add(instance);
		}

		processor.apply(this.entity, instance, pool.getInstances());
	}

	@Override
	public <I extends EntityEffectInstance> void removeEntry(EntityEffectProcessor<I> processor)
	{
		if (processor == null)
		{
			return;
		}

		EffectPool<I> pool = this.getPool(processor);

		for (I instance : pool.getInstances())
		{
			processor.cancel(this.entity, instance, pool.getInstances());
		}

		this.effects.remove(pool);
	}

	@Override
	public <I extends EntityEffectInstance> void removeInstance(EntityEffectProcessor<I> processor, I instance)
	{
		if (processor == null)
		{
			return;
		}

		EffectPool<I> pool = this.getPool(processor);

		processor.cancel(this.entity, instance, pool.getInstances());

		pool.getInstances().remove(instance);
	}

	@Override
	public void onUpdate(LivingUpdateEvent event)
	{
		for (IEffectPool<?> pool : this.getEffectPools())
		{
			pool.tick(this.entity);
		}

		this.ticksSinceAttacked++;
	}

	@Override
	public void onHurt(LivingHurtEvent event)
	{
		if (event.getSource().getSourceOfDamage() != null)
		{
			this.ticksSinceAttacked = 0;
		}
	}

	@Override
	public void onAttack(LivingAttackEvent event)
	{
		if (event.getSource().getSourceOfDamage() != null)
		{
			if (event.getSource().getSourceOfDamage().hasCapability(AetherCapabilities.ENTITY_EFFECTS, null))
			{
				IEntityEffectsCapability effects = event.getSource().getSourceOfDamage().getCapability(AetherCapabilities.ENTITY_EFFECTS, null);

				effects.setTicksSinceAttacked(0);
			}
		}
	}

	@Override
	public Entity getEntity()
	{
		return this.entity;
	}

	@Override
	public int getTicksSinceAttacked()
	{
		return this.ticksSinceAttacked;
	}

	@Override
	public void setTicksSinceAttacked(int ticks)
	{
		this.ticksSinceAttacked = ticks;
	}

	public static class Storage implements Capability.IStorage<IEntityEffectsCapability>
	{
		@Override
		public NBTBase writeNBT(Capability<IEntityEffectsCapability> capability, IEntityEffectsCapability instance, EnumFacing side)
		{
			return null;
		}

		@Override
		public void readNBT(Capability<IEntityEffectsCapability> capability, IEntityEffectsCapability instance, EnumFacing side, NBTBase nbt)
		{

		}
	}
}
