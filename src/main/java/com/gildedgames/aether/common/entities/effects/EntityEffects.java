package com.gildedgames.aether.common.entities.effects;

import com.gildedgames.aether.common.entities.effects.processors.BreatheUnderwaterEffect;
import com.gildedgames.aether.common.entities.effects.processors.DoubleDropEffect;
import com.gildedgames.aether.common.entities.effects.processors.FreezeBlocksEffect;
import com.gildedgames.aether.common.entities.effects.processors.ModifyDamageEffect;
import com.gildedgames.aether.common.entities.effects.processors.ModifyMaxHealthEffect;
import com.gildedgames.aether.common.entities.effects.processors.ModifySpeedEffect;
import com.gildedgames.aether.common.entities.effects.processors.RegenerateHealthEffect;
import com.gildedgames.aether.common.entities.effects.processors.player.DaggerfrostEffect;
import com.gildedgames.aether.common.entities.effects.processors.player.ModifyXPCollectionEffect;
import com.gildedgames.aether.common.entities.effects.processors.player.PauseHungerEffect;
import com.gildedgames.util.modules.entityhook.api.IEntityHookFactory;
import com.gildedgames.util.modules.entityhook.impl.hooks.EntityHook;
import com.gildedgames.util.modules.entityhook.impl.providers.LivingHookProvider;
import com.google.common.collect.Lists;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.IMob;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

import java.util.ArrayList;
import java.util.List;

public class EntityEffects extends EntityHook<Entity>
{

	public static final EffectProcessorPlayer<EffectInstance> DAGGERFROST = new DaggerfrostEffect();

	public static final EffectProcessorPlayer<ModifyXPCollectionEffect.Instance> MODIFY_XP_COLLECTION = new ModifyXPCollectionEffect();

	public static final EffectProcessorPlayer<EffectInstance> PAUSE_HUNGER = new PauseHungerEffect();

	public static final EffectProcessor<EffectInstance> BREATHE_UNDERWATER = new BreatheUnderwaterEffect();

	public static final EffectProcessor<DoubleDropEffect.Instance> DOUBLE_DROPS = new DoubleDropEffect();

	public static final EffectProcessor<FreezeBlocksEffect.Instance> FREEZE_BLOCKS = new FreezeBlocksEffect();

	public static final EffectProcessor<ModifyDamageEffect.Instance> MODIFY_DAMAGE = new ModifyDamageEffect();

	public static final EffectProcessor<ModifyMaxHealthEffect.Instance> MODIFY_MAX_HEALTH = new ModifyMaxHealthEffect();

	public static final EffectProcessor<ModifySpeedEffect.Instance> MODIFY_SPEED = new ModifySpeedEffect();

	public static final EffectProcessor<RegenerateHealthEffect.Instance> REGENERATE_HEALTH = new RegenerateHealthEffect();

	public static final LivingHookProvider<EntityEffects> PROVIDER = new LivingHookProvider<>("aether:effects", new Factory());

	private List<EffectPool<?>> effects = Lists.newArrayList();

	private int ticksSinceAttacked;

	private EntityEffects()
	{

	}

	public static EntityEffects get(Entity entity)
	{
		return EntityEffects.PROVIDER.getHook(entity);
	}

	public List<EffectPool<?>> getEffectPools()
	{
		return this.effects;
	}

	@SuppressWarnings("unchecked")
	private <I extends EffectInstance> EffectPool<I> getPool(EffectProcessor<I> processor)
	{
		for (EffectPool<?> pool : this.effects)
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

	public <I extends EffectInstance> void put(EffectProcessor<I> processor, I instance)
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

		processor.apply(this.getEntity(), instance, pool.getInstances());
	}

	@Override
	public void onLoaded()
	{
	}

	@Override
	public void onUnloaded()
	{
	}

	public <I extends EffectInstance> void removeEntry(EffectProcessor<I> processor)
	{
		if (processor == null)
		{
			return;
		}

		EffectPool<I> pool = this.getPool(processor);

		for (I instance : pool.getInstances())
		{
			processor.cancel(this.getEntity(), instance, pool.getInstances());
		}

		this.effects.remove(pool);
	}

	public <I extends EffectInstance> void removeInstance(EffectProcessor<I> processor, I instance)
	{
		if (processor == null)
		{
			return;
		}

		EffectPool<I> pool = this.getPool(processor);

		processor.cancel(this.getEntity(), instance, pool.getInstances());

		pool.getInstances().remove(instance);
	}

	@Override
	public void saveNBTData(NBTTagCompound compound)
	{

	}

	@Override
	public void loadNBTData(NBTTagCompound compound)
	{

	}

	@Override
	public void onUpdate()
	{
		for (EffectPool<?> pool : this.getEffectPools())
		{
			pool.tick(this.getEntity());
		}

		this.ticksSinceAttacked++;
	}

	public void onHurt(LivingHurtEvent event)
	{
		if (event.source.getSourceOfDamage() instanceof IMob)
		{
			this.ticksSinceAttacked = 0;
		}
	}

	public void clearEffects()
	{
		for (EffectPool<?> pool : this.effects)
		{
			this.removeEntry(pool.getProcessor());
		}
	}

	public int getTicksSinceAttacked()
	{
		return this.ticksSinceAttacked;
	}

	public static class Factory implements IEntityHookFactory<EntityEffects>
	{
		@Override
		public EntityEffects createHook()
		{
			return new EntityEffects();
		}

		@Override
		public void writeFull(ByteBuf buf, EntityEffects hook)
		{
		}

		@Override
		public void readFull(ByteBuf buf, EntityEffects hook)
		{
		}
	}
}
