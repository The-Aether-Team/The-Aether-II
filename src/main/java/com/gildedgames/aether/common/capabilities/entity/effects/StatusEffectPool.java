package com.gildedgames.aether.common.capabilities.entity.effects;

import com.gildedgames.aether.api.entity.effects.IAetherStatusEffectPool;
import com.gildedgames.aether.api.entity.effects.IAetherStatusEffects;
import com.gildedgames.aether.api.registrar.CapabilitiesAether;
import com.gildedgames.aether.common.entities.effects.*;
import com.gildedgames.aether.common.entities.effects.teas.StatusEffectSaturationBoost;
import com.gildedgames.aether.common.entities.effects.unique.StatusEffectGuardBreak;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nullable;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class StatusEffectPool implements IAetherStatusEffectPool
{
	private static final HashMap<String, StatusEffectFactory> types = new HashMap<>();

	// This is NOT flexible. We need to move the factory code elsewhere
	static
	{
		types.put(IAetherStatusEffects.effectTypes.AMBROSIUM_POISONING.name, new StatusEffectFactory(StatusEffectAmbrosiumPoisoning.class));
		types.put(IAetherStatusEffects.effectTypes.BLEED.name, new StatusEffectFactory(StatusEffectBleed.class));
		types.put(IAetherStatusEffects.effectTypes.COCKATRICE_VENOM.name, new StatusEffectFactory(StatusEffectCockatriceVenom.class));
		types.put(IAetherStatusEffects.effectTypes.FRACTURE.name, new StatusEffectFactory(StatusEffectFracture.class));
		types.put(IAetherStatusEffects.effectTypes.FUNGAL_ROT.name, new StatusEffectFactory(StatusEffectFungalRot.class));
		types.put(IAetherStatusEffects.effectTypes.STUN.name, new StatusEffectFactory(StatusEffectStun.class));
		types.put(IAetherStatusEffects.effectTypes.TOXIN.name, new StatusEffectFactory(StatusEffectToxin.class));
		types.put(IAetherStatusEffects.effectTypes.FREEZE.name, new StatusEffectFactory(StatusEffectFreeze.class));
		types.put(IAetherStatusEffects.effectTypes.WEBBING.name, new StatusEffectFactory(StatusEffectWebbing.class));
		types.put(IAetherStatusEffects.effectTypes.SATURATION_BOOST.name, new StatusEffectFactory(StatusEffectSaturationBoost.class));
		types.put(IAetherStatusEffects.effectTypes.GUARD_BREAK.name, new StatusEffectFactory(StatusEffectGuardBreak.class));
	}

	private EntityLivingBase livingBase;

	private HashMap<String, IAetherStatusEffects> activeEffects = new HashMap<>();

	public StatusEffectPool() throws InstantiationException
	{
		throw new InstantiationException("Do not use default constructor to instantiate pool!");
	}

	public StatusEffectPool(EntityLivingBase livingBase)
	{
		this.livingBase = livingBase;
	}

	@Override
	public void tick()
	{
		Iterator<Map.Entry<String, IAetherStatusEffects>> it = this.activeEffects.entrySet().iterator();

		boolean creative = this.livingBase instanceof EntityPlayerMP && ((EntityPlayerMP) this.livingBase).isCreative();

		while (it.hasNext())
		{
			IAetherStatusEffects effect = it.next().getValue();

			if (creative)
			{
				if (effect.getIsEffectApplied() || effect.getBuildup() > 0)
				{
					effect.resetEffect();
				}
			}

			effect.tick(this.livingBase);

			if (this.shouldRemoveEffect(effect))
			{
				it.remove();
			}
		}
	}

	private boolean shouldRemoveEffect(IAetherStatusEffects effect)
	{
		return !effect.isDirty() && !effect.getIsEffectApplied() && effect.getBuildup() == 0 && effect.getResistance() == 1.0D;
	}

	@Override
	public void applyStatusEffect(IAetherStatusEffects.effectTypes effectType, int buildup)
	{

//		IAetherStatusEffects ambroiumPoisoningEffect = this.getActiveEffect(IAetherStatusEffects.effectTypes.AMBROSIUM_POISONING.name);
//
//		double additionalResistance = 0.0D;
//
//		if (ambroiumPoisoningEffect != null)
//		{
//			if (ambroiumPoisoningEffect.getIsEffectApplied())
//			{
//				additionalResistance = 0.5D;    // apply 50% reduction to resistance.
//			}
//		}

		IAetherStatusEffects effect = this.createEffect(effectType.name, this.livingBase);
		effect.addBuildup(buildup, 0.0D);
	}

	@Override
	public int getBuildupFromEffect(IAetherStatusEffects.effectTypes effectType)
	{
		IAetherStatusEffects effect = this.getActiveEffect(effectType.name);

		return effect != null ? effect.getBuildup() : 0;
	}

	@Override
	public boolean isEffectApplied(IAetherStatusEffects.effectTypes effectType)
	{
		IAetherStatusEffects effect = this.getActiveEffect(effectType.name);

		return effect != null && effect.getIsEffectApplied();
	}

	@Override
	public double getResistanceToEffect(IAetherStatusEffects.effectTypes effectType)
	{
		IAetherStatusEffects effect = this.getActiveEffect(effectType.name);

		if (effect != null)
		{
			return effect.getResistance();
		}

		return 1.0D;
	}

	@Override
	public void addResistanceToEffect(IAetherStatusEffects.effectTypes effectType, double addResistance)
	{

		IAetherStatusEffects effect = this.getActiveEffect(effectType.name);

		if (effect != null)
		{
			effect.addResistance(addResistance);
		}
	}

	@Override
	public void resetResistanceToEffect(IAetherStatusEffects.effectTypes effectType)
	{
		IAetherStatusEffects effect = this.getActiveEffect(effectType.name);

		if (effect != null)
		{
			effect.resetResistance();
		}
	}

	@Override
	public void resetAllResistances()
	{
		for (IAetherStatusEffects effect : this.getActiveEffects())
		{
			if (effect == null)
			{
				continue;
			}

			effect.resetResistance();
		}
	}

	@Override
	public void modifyActiveEffectTime(IAetherStatusEffects.effectTypes effectType, double activeEffectTimeModifier)
	{
		IAetherStatusEffects effect = this.getActiveEffect(effectType.name);

		if (effect != null)
		{
			effect.setActiveEffectTimeModifier(activeEffectTimeModifier);
		}
	}

	@Override
	public void modifyAllActiveEffectTimes(double activeEffectTimeModifier)
	{
		for (IAetherStatusEffects effect : this.getActiveEffects())
		{
			if (effect == null)
			{
				continue;
			}

			effect.setActiveEffectTimeModifier(activeEffectTimeModifier);
		}
	}

	@Override
	public void modifyActiveEffectBuildup(IAetherStatusEffects.effectTypes effectType, int activeEffectBuildup)
	{
		IAetherStatusEffects effect = this.getActiveEffect(effectType.name);

		if (effect != null)
		{
			effect.setBuildup(activeEffectBuildup);
		}
	}

	@Override
	public void modifyActiveEffectApplication(IAetherStatusEffects.effectTypes effectType, boolean setApplied)
	{
		IAetherStatusEffects effect = this.getActiveEffect(effectType.name);

		if (effect != null)
		{
			effect.setApplied(setApplied);
		}
	}

	@Override
	public void cureActiveEffect(IAetherStatusEffects.effectTypes effectType)
	{
		IAetherStatusEffects effect = this.getActiveEffect(effectType.name);

		if (effect != null)
		{
			effect.setActiveEffectTimeModifier(0.0D);
		}
	}

	@Override
	public void cureAllActiveEffects()
	{
		this.modifyAllActiveEffectTimes(0.0D);
	}

	@Override
	public HashMap<String, IAetherStatusEffects> getPool()
	{
		return this.activeEffects;
	}

	public static IAetherStatusEffectPool get(EntityLivingBase livingBase)
	{
		return livingBase.getCapability(CapabilitiesAether.STATUS_EFFECT_POOL, null);
	}

	public static class Storage implements Capability.IStorage<IAetherStatusEffectPool>
	{

		@Nullable
		@Override
		public NBTBase writeNBT(Capability<IAetherStatusEffectPool> capability, IAetherStatusEffectPool instance, EnumFacing side)
		{
			NBTTagCompound compound = new NBTTagCompound();

			for (IAetherStatusEffects effect : instance.getPool().values())
			{
				effect.write(compound);
			}

			return compound;
		}

		@Override
		public void readNBT(Capability<IAetherStatusEffectPool> capability, IAetherStatusEffectPool instance, EnumFacing side, NBTBase nbt)
		{
			NBTTagCompound tag = (NBTTagCompound) nbt;

			for (IAetherStatusEffects effect : instance.getPool().values())
			{
				effect.read(tag);
			}
		}
	}

	private Collection<IAetherStatusEffects> getActiveEffects()
	{
		return this.activeEffects.values();
	}

	private IAetherStatusEffects getActiveEffect(String name)
	{
		return this.activeEffects.get(name);
	}

	@Override
	public IAetherStatusEffects createEffect(String name, EntityLivingBase entity)
	{
		return this.activeEffects.computeIfAbsent(name, (key) -> {
			StatusEffectFactory factory = types.get(name);

			if (factory == null)
			{
				throw new IllegalArgumentException("No factory registered for effect with name " + name);
			}

			return factory.create(entity);
		});
	}

	private static class StatusEffectFactory
	{
		private final Constructor<? extends IAetherStatusEffects> constructor;

		public StatusEffectFactory(Class<? extends IAetherStatusEffects> clazz)
		{
			try
			{
				this.constructor = clazz.getConstructor(EntityLivingBase.class);
			}
			catch (NoSuchMethodException e)
			{
				throw new RuntimeException("Couldn't find constructor for status effect " + clazz);
			}
		}

		public IAetherStatusEffects create(EntityLivingBase entity)
		{
			try
			{
				return this.constructor.newInstance(entity);
			}
			catch (InstantiationException | IllegalAccessException | InvocationTargetException e)
			{
				throw new RuntimeException("Failed to instantiate effect", e);
			}
		}
	}
}
