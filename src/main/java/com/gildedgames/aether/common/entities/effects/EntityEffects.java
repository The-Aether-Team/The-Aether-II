package com.gildedgames.aether.common.entities.effects;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.IMob;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

import com.gildedgames.aether.common.AetherCore;

public class EntityEffects<S extends Entity> implements IExtendedEntityProperties
{
	
	private S entity;
	
	private final static String PROP_ID = AetherCore.MOD_ID + "Effects";
	
	private List<EntityEffect<S>> effects = new ArrayList<EntityEffect<S>>();
	
	private int ticksSinceAttacked;
	
	private EntityEffects()
	{
		
	}
	
	@SuppressWarnings("unchecked")
	public static <S extends Entity> EntityEffects<S> get(S entity)
	{
		return (EntityEffects<S>) entity.getExtendedProperties(PROP_ID);
	}

	public static <S extends Entity> void register(S entity)
	{
		entity.registerExtendedProperties(PROP_ID, new EntityEffects<S>());
	}
	
	public S getEntity()
	{
		return this.entity;
	}
	
	public List<EntityEffect<S>> getEffects()
	{
		return this.effects;
	}
	
	public boolean addEffect(EntityEffect<S> effect)
	{
		boolean hasAbility = false;
		
		for (EntityEffect<S> itEffect : this.effects)
		{
			if (itEffect.getAbility() == effect.getAbility())
			{
				hasAbility = true;
				break;
			}
		}
		
		if (!this.effects.contains(effect) && !hasAbility)
		{
			this.effects.add(effect);
			
			effect.getAbility().apply(this.getEntity(), effect, effect.getAttributes());
			
			return true;
		}
		
		return false;
	}
	
	public void removeEffect(EntityEffect<S> effect)
	{
		effect.getAbility().cancel(this.getEntity(), effect, effect.getAttributes());
		
		this.effects.remove(effect);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void init(Entity entity, World world)
	{
		this.entity = (S) entity;
	}

	@Override
	public void saveNBTData(NBTTagCompound compound)
	{
		
	}

	@Override
	public void loadNBTData(NBTTagCompound compound)
	{
		
	}

	public void onUpdate()
	{
		for (EntityEffect<S> effect : this.getEffects())
		{
			boolean isMet = true;
			
			for (AbilityRule<S> rule : effect.getRules())
			{
				if (!rule.isMet(this.getEntity()))
				{
					isMet = false;
					break;
				}
			}
			
			if (isMet)
			{
				effect.getAbility().tick(this.getEntity(), effect, effect.getAttributes());
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
		for (EntityEffect<S> effect : new ArrayList<EntityEffect<S>>(this.effects))
		{
			this.removeEffect(effect);
		}
	}
	
	public int getTicksSinceAttacked()
	{
		return this.ticksSinceAttacked;
	}

}
