package com.gildedgames.aether.common.entities.effects;

import com.gildedgames.util.modules.entityhook.api.IEntityHookFactory;
import com.gildedgames.util.modules.entityhook.impl.hooks.EntityHook;
import com.gildedgames.util.modules.entityhook.impl.providers.LivingHookProvider;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.IMob;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

import java.util.ArrayList;
import java.util.List;

public class EntityEffects<E extends Entity> extends EntityHook<E>
{
	public static final LivingHookProvider<EntityEffects> PROVIDER = new LivingHookProvider<>("aether:effects", new Factory());

	private List<EntityEffect<E>> effects = new ArrayList<>();
	
	private int ticksSinceAttacked;
	
	@SuppressWarnings("unchecked")
	public static <S extends Entity> EntityEffects<S> get(S entity)
	{
		return EntityEffects.PROVIDER.getHook(entity);
	}

	public List<EntityEffect<E>> getEffects()
	{
		return this.effects;
	}
	
	public boolean addEffect(EntityEffect<E> effect)
	{
		if (!this.effects.contains(effect) && effect != null)
		{
			this.effects.add(effect);

			for (Ability<E> ability : effect.getAbilities())
			{
				ability.apply(this.getEntity(), effect, effect.getAttributes());
			}

			return true;
		}
		
		return false;
	}
	
	public void removeEffect(EntityEffect<E> effect)
	{
		for (Ability<E> ability : effect.getAbilities())
		{
			ability.cancel(this.getEntity(), effect, effect.getAttributes());
		}
		
		this.effects.remove(effect);
	}

	@Override
	public void onLoaded() { }

	@Override
	public void onUnloaded() { }

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
		for (EntityEffect<E> effect : this.getEffects())
		{
			boolean isMet = true;
			
			for (AbilityRule<E> rule : effect.getRules())
			{
				if (!rule.isMet(this.getEntity()))
				{
					isMet = false;
					break;
				}
			}
			
			if (isMet)
			{
				for (Ability<E> ability : effect.getAbilities())
				{
					ability.tick(this.getEntity(), effect, effect.getAttributes());
				}
			}
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
		for (EntityEffect<E> effect : new ArrayList<>(this.effects))
		{
			this.removeEffect(effect);
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
		public void writeFull(ByteBuf buf, EntityEffects hook) { }

		@Override
		public void readFull(ByteBuf buf, EntityEffects hook) { }
	}
}
