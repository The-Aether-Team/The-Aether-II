package com.gildedgames.aether.common.entities.effects.processors.player;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerPickupXpEvent;

import com.gildedgames.aether.common.entities.effects.EffectInstance;
import com.gildedgames.aether.common.entities.effects.EffectProcessorPlayer;
import com.gildedgames.aether.common.entities.effects.EffectRule;
import com.gildedgames.aether.common.entities.effects.processors.player.ModifyXPCollectionEffect.Instance;

public class ModifyXPCollectionEffect implements EffectProcessorPlayer<Instance>
{
	
	public static class Instance extends EffectInstance
	{

		public Instance(float percentMod, EffectRule... rules)
		{
			super(rules);
			
			this.getAttributes().setFloat("percentMod", percentMod);
		}
		
		@Override
		public EffectInstance cloneInstance()
		{
			return new Instance(this.getAttributes().getFloat("percentMod"), this.getRules());
		}
		
	}
	
	public ModifyXPCollectionEffect()
	{
		
	}
	
	@Override
	public String getUnlocalizedName(Entity source, Instance instance)
	{
		return "ability.modifyXPCollection.name";
	}

	@Override
	public String[] getUnlocalizedDesc(Entity source, Instance instance)
	{
		return new String[] { "ability.modifyXPCollection.desc" };
	}
	
	private String displayValue(float value)
	{
		return value == (int)Math.floor(value) ? String.valueOf((int)Math.floor(value)) : String.valueOf(value);
	}

	@Override
	public String[] getFormatParameters(Entity source, Instance instance)
	{
		float percentMod = instance.getAttributes().getFloat("percentMod") * 100;

		String prefix = percentMod > 0 ? "§9+" : "§c";
		
		return new String[] { (prefix + this.displayValue(percentMod) + "%") };
	}

	@Override
	public void apply(Entity source, Instance instance, List<Instance> all)
	{
		
	}

	@Override
	public void tick(Entity source, List<Instance> all)
	{
		
	}

	@Override
	public void cancel(Entity source, Instance instance, List<Instance> all)
	{
		
	}

	@Override
	public void onKill(LivingDropsEvent event, Entity source, List<Instance> all)
	{
		
	}

	@Override
	public void onAttack(LivingHurtEvent event, Entity source, List<Instance> all)
	{
		
	}

	@Override
	public void onInteract(PlayerInteractEvent event, EntityPlayer source, List<Instance> all)
	{
		
	}

	@Override
	public void onPickupXP(PlayerPickupXpEvent event, EntityPlayer source, List<Instance> all)
	{
		float totalPercent = 0.0F;
		
		for (Instance instance : all)
		{
			totalPercent += instance.getAttributes().getFloat("percentMod");
		}
		
		event.orb.xpValue += event.orb.xpValue * totalPercent;
	}

}
