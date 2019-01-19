package com.gildedgames.aether.common.capabilities.entity.player.modules;

import com.gildedgames.aether.common.capabilities.entity.effects.*;
import com.gildedgames.aether.common.capabilities.entity.player.PlayerAether;
import com.gildedgames.aether.common.capabilities.entity.player.PlayerAetherModule;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.ArrayList;

public class PlayerEffectsModule extends PlayerAetherModule
{
	private ArrayList<AetherEffects> effects;

	public PlayerEffectsModule(PlayerAether player)
	{
		super(player);

		this.initEffectsStorage();
	}

	private void initEffectsStorage()
	{
		this.effects = new ArrayList<>();

		this.effects.add(new EffectAmbrosiumPoisoning());
		this.effects.add(new EffectToxin());
		this.effects.add(new EffectCockatriceVenom());
		this.effects.add(new EffectStun());
		this.effects.add(new EffectBleed());
		this.effects.add(new EffectFracture());
		this.effects.add(new EffectFungalRot());
	}

	public void applyAilment(IAetherEffects.effectTypes effect, int amount)
	{
		if (this.getEntity().isCreative())
		{
			return;
		}

		double additionalResistanceModifier;

		if (this.isEffectApplied(IAetherEffects.effectTypes.AMBROSIUM_POISONING))
		{
			additionalResistanceModifier = 0.5D;	// add additional -50% resistance.
		}
		else
		{
			additionalResistanceModifier = 0.0D;
		}

		for (int i = 0; i < this.effects.size(); i++)
		{
			if (effect == this.effects.get(i).getEffectType())
			{
				this.effects.get(i).addBuildup(amount, additionalResistanceModifier);
			}
		}
	}

	public boolean isEffectApplied(IAetherEffects.effectTypes effectType)
	{
		for (AetherEffects effect : this.effects)
		{
			if (effect == null)
			{
				continue;
			}

			if (effect.getEffectType() == effectType)
			{
				if (effect.isEffectApplied())
				{
					return true;
				}
			}
		}

		return false;
	}


	@Override
	public void tickStart(TickEvent.PlayerTickEvent event)
	{
		if (this.getEntity().isCreative())
		{
			return;
		}

		for (AetherEffects effect : this.effects)
		{
			if (effect == null)
			{
				continue;
			}

			effect.tickStart(event);
		}
	}

	@Override
	public void tickEnd(TickEvent.PlayerTickEvent event)
	{
		if (this.getEntity().isCreative())
		{
			for (AetherEffects effect : this.effects)
			{
				if (effect == null)
				{
					continue;
				}

				if (effect.getEffectBuildup() > 0|| effect.isEffectApplied())
				{
					effect.resetEffect();
				}
			}
			return;
		}

		for (AetherEffects effect : this.effects)
		{
			if (effect == null)
			{
				continue;
			}
			effect.tickEnd(this.getEntity());
		}
	}


	@Override
	public void onUpdate()
	{
	}

	@Override
	public void onDeath(final LivingDeathEvent event)
	{
		for (AetherEffects effect : this.effects)
		{
			if (effect == null)
			{
				continue;
			}
			effect.resetEffect();
		}
	}


	@Override
	public void write(NBTTagCompound tag)
	{

	}

	@Override
	public void read(NBTTagCompound tag)
	{
	}

}
