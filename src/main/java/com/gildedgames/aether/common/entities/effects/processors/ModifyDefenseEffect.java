package com.gildedgames.aether.common.entities.effects.processors;

import com.gildedgames.aether.api.capabilites.entity.effects.EntityEffectInstance;
import com.gildedgames.aether.api.capabilites.entity.effects.EntityEffectProcessor;
import com.gildedgames.aether.api.capabilites.entity.effects.EntityEffectRule;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

import java.util.List;

public class ModifyDefenseEffect implements EntityEffectProcessor<ModifyDefenseEffect.Instance>
{

	public static class Instance extends EntityEffectInstance
	{

		public Instance(float defenseMod, EntityEffectRule... rules)
		{
			super(rules);

			this.getAttributes().setFloat("defenseMod", defenseMod);
		}

		@Override
		public EntityEffectInstance cloneInstance()
		{
			return new Instance(this.getAttributes().getFloat("defenseMod"), this.getRules());
		}

	}

	public ModifyDefenseEffect()
	{

	}

	@Override
	public String getUnlocalizedName(Entity source, Instance instance)
	{
		return "ability.defenseMod.localizedName";
	}

	@Override
	public String[] getUnlocalizedDesc(Entity source, Instance instance)
	{
		return new String[] { "ability.defenseMod.desc" };
	}

	@Override
	public String[] getFormatParameters(Entity source, Instance instance)
	{
		float maxHealthMod = instance.getAttributes().getFloat("defenseMod");

		String prefix = maxHealthMod > 0 ? (TextFormatting.BLUE + "+") : (TextFormatting.RED + "");

		String par = prefix + (maxHealthMod == (int) Math.floor(maxHealthMod) ? String.valueOf((int) Math.floor(maxHealthMod)) : String.valueOf(maxHealthMod));

		return new String[] { par };
	}

	@Override
	public void apply(Entity source, Instance instance, List<Instance> all)
	{
		if (!(source instanceof EntityLivingBase))
		{
			return;
		}
	}

	@Override
	public void tick(Entity source, List<Instance> all)
	{

	}

	@Override
	public void cancel(Entity source, Instance instance, List<Instance> all)
	{
		if (!(source instanceof EntityLivingBase))
		{
			return;
		}
	}

	@Override
	public void onKill(LivingDropsEvent event, Entity source, List<Instance> all)
	{

	}

	@Override
	public void onAttack(LivingHurtEvent event, Entity source, List<Instance> all)
	{

	}

}
