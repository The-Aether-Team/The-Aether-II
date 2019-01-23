package com.gildedgames.aether.common.capabilities.entity.effects;

import com.gildedgames.aether.api.AetherCapabilities;
import com.gildedgames.aether.api.effects_system.IAetherStatusEffectPool;
import com.gildedgames.aether.api.effects_system.IAetherStatusEffects;
import com.gildedgames.aether.common.entities.effects.*;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nullable;
import java.util.HashMap;

public class StatusEffectPool implements IAetherStatusEffectPool
{
	private final EntityLivingBase livingBase;
	private HashMap<String, IAetherStatusEffects> statusEffects = new HashMap<>();

	public StatusEffectPool(EntityLivingBase livingBase)
	{
		this.livingBase = livingBase;
		this.initPool();
	}

	@Override
	public void initPool()
	{
		this.statusEffects.put(IAetherStatusEffects.effectTypes.AMBROSIUM_POISONING.name, new StatusEffectAmbrosiumPoisoning(this.livingBase));
		this.statusEffects.put(IAetherStatusEffects.effectTypes.BLEED.name, new StatusEffectBleed(this.livingBase));
		this.statusEffects.put(IAetherStatusEffects.effectTypes.COCKATRICE_VENOM.name, new StatusEffectCockatriceVenom(this.livingBase));
		this.statusEffects.put(IAetherStatusEffects.effectTypes.FRACTURE.name, new StatusEffectFracture(this.livingBase));
		this.statusEffects.put(IAetherStatusEffects.effectTypes.FUNGAL_ROT.name, new StatusEffectFungalRot(this.livingBase));
		this.statusEffects.put(IAetherStatusEffects.effectTypes.STUN.name, new StatusEffectStun(this.livingBase));
		this.statusEffects.put(IAetherStatusEffects.effectTypes.TOXIN.name, new StatusEffectToxin(this.livingBase));
	}

	@Override
	public void Tick()
	{
		for (IAetherStatusEffects effect : this.statusEffects.values())
		{
			if (this.livingBase instanceof EntityPlayerMP)
			{
				EntityPlayerMP player = (EntityPlayerMP) this.livingBase;
				if (player.isCreative())
				{
					if (effect.getIsEffectApplied()|| effect.getBuildup() > 0)
					{
						effect.resetEffect();
					}
				}
			}

			effect.Tick(this.livingBase);
		}
	}

	@Override
	public void applyStatusEffect(IAetherStatusEffects.effectTypes effectType, int buildup)
	{
		IAetherStatusEffects effect = this.statusEffects.get(effectType.name);
		IAetherStatusEffects ambroiumPoisoningEffect = this.statusEffects.get(IAetherStatusEffects.effectTypes.AMBROSIUM_POISONING.name);
		double additionalResistance = 0.0D;

		if (ambroiumPoisoningEffect != null)
		{
			if (ambroiumPoisoningEffect.getIsEffectApplied())
			{
				additionalResistance = 0.5D;	// apply 50% reduction to resistance.
			}
		}

		if (effect != null)
		{
			effect.addBuildup(buildup, additionalResistance);
		}
	}

	@Override
	public int getBuildupFromEffect(IAetherStatusEffects.effectTypes effectType)
	{
		return this.statusEffects.get(effectType.name).getBuildup();
	}

	@Override
	public boolean isEffectApplied(IAetherStatusEffects.effectTypes effectType)
	{
		IAetherStatusEffects effect = this.statusEffects.get(effectType.name);

		return effect != null && effect.getIsEffectApplied();
	}

	@Override
	public double getResistanceToEffect(IAetherStatusEffects.effectTypes effectType)
	{
		IAetherStatusEffects effect = this.statusEffects.get(effectType.name);
		if (effect != null)
		{
			return effect.getResistance();
		}
		return 1.0D;
	}

	@Override
	public void addResistanceToEffect(IAetherStatusEffects.effectTypes effectType, double addResistance)
	{
		IAetherStatusEffects effect = this.statusEffects.get(effectType.name);
		if (effect != null)
		{
			effect.addResistance(addResistance);
		}
	}

	@Override
	public void modifyActiveEffectTime(IAetherStatusEffects.effectTypes effectType, double activeEffectTimeModifier)
	{
		IAetherStatusEffects effect = this.statusEffects.get(effectType.name);
		if (effect != null)
		{
			effect.setActiveEffectTimeModifier(activeEffectTimeModifier);
		}
	}

	@Override
	public void modifyAllActiveEffectTimes(double activeEffectTimeModifier)
	{
		for (IAetherStatusEffects effect : this.statusEffects.values())
		{
			if (effect == null)
			{
				continue;
			}

			effect.setActiveEffectTimeModifier(activeEffectTimeModifier);
		}
	}

	@Override
	public void cureActiveEffect(IAetherStatusEffects.effectTypes effectType)
	{
		IAetherStatusEffects effect = this.statusEffects.get(effectType.name);
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
		return this.statusEffects;
	}

	public static IAetherStatusEffectPool get(EntityLivingBase livingBase)
	{
		return livingBase.getCapability(AetherCapabilities.STATUS_EFFECT_POOL, null);
	}

	public static class Storage implements Capability.IStorage<IAetherStatusEffectPool>
	{

		@Nullable
		@Override
		public NBTBase writeNBT(Capability<IAetherStatusEffectPool> capability, IAetherStatusEffectPool instance, EnumFacing side)
		{
			NBTTagCompound compound = new NBTTagCompound();

			for ( IAetherStatusEffects effect : instance.getPool().values())
			{
				effect.write(compound);
			}

			return compound;
		}

		@Override
		public void readNBT(Capability<IAetherStatusEffectPool> capability, IAetherStatusEffectPool instance, EnumFacing side, NBTBase nbt)
		{
			NBTTagCompound tag = (NBTTagCompound) nbt;

			for ( IAetherStatusEffects effect : instance.getPool().values())
			{
				effect.read(tag);
			}
		}
	}
}
