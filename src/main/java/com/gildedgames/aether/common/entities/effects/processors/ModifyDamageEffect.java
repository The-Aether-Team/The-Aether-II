package com.gildedgames.aether.common.entities.effects.processors;

import com.gildedgames.aether.common.entities.effects.EffectInstance;
import com.gildedgames.aether.common.entities.effects.EffectProcessor;
import com.gildedgames.aether.common.entities.effects.EffectRule;
import com.gildedgames.aether.common.entities.effects.processors.ModifyDamageEffect.Instance;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author Brandon Pearce
 */
public class ModifyDamageEffect implements EffectProcessor<Instance>
{

	public static class Instance extends EffectInstance
	{

		public Instance(float damage, EffectRule... rules)
		{
			this(damage, damage, false, rules);
		}

		public Instance(int min, int max, EffectRule... rules)
		{
			this(min, max, false, rules);
		}

		public Instance(float min, float max, boolean floatRanges, EffectRule... rules)
		{
			super(rules);

			this.getAttributes().setFloat("min", min);
			this.getAttributes().setFloat("max", max);

			this.getAttributes().setBoolean("floatRanges", floatRanges);
		}

		@Override
		public EffectInstance cloneInstance()
		{
			return new Instance(this.getAttributes().getFloat("min"), this.getAttributes().getFloat("max"), this.getAttributes().getBoolean("floatRanges"), this.getRules());
		}

	}

	public ModifyDamageEffect()
	{

	}

	@Override
	public String getUnlocalizedName(Entity source, Instance instance)
	{
		return "ability.extraDamage.name";
	}

	@Override
	public String[] getUnlocalizedDesc(Entity source, Instance instance)
	{
		return new String[] { "ability.extraDamage.desc" };
	}

	private String displayValue(float value)
	{
		return value == (int) Math.floor(value) ? String.valueOf((int) Math.floor(value)) : String.valueOf(value);
	}

	@Override
	public String[] getFormatParameters(Entity source, Instance instance)
	{
		float min = instance.getAttributes().getFloat("min");
		float max = instance.getAttributes().getFloat("max");

		String prefix = min > 0 ? (EnumChatFormatting.BLUE + "+") : (EnumChatFormatting.RED + "");

		String par;

		if (min == max)
		{
			par = prefix + this.displayValue(min);
		}
		else
		{
			par = prefix + this.displayValue(min) + "-" + this.displayValue(max);
		}

		return new String[] { par };
	}

	@Override
	public void onAttack(LivingHurtEvent event, Entity source, List<Instance> all)
	{
		if (!(source instanceof EntityLivingBase))
		{
			return;
		}

		EntityLivingBase living = (EntityLivingBase) source;

		if (living.swingProgressInt == -1)
		{
			living.swingProgressInt = 0;

			Entity entity = event.entityLiving;

			if (entity != null)
			{
				float allDamage = 0.0F;

				for (Instance instance : all)
				{
					float min = instance.getAttributes().getFloat("min");
					float max = instance.getAttributes().getFloat("max");

					float rangeResult = 0.0F;

					if (instance.getAttributes().getBoolean("floatRanges"))
					{
						rangeResult = min + (max - min) * living.getRNG().nextFloat();
					}
					else
					{
						rangeResult = ThreadLocalRandom.current().nextInt((int) min, (int) max + 1);
					}

					allDamage += rangeResult;
				}

				float result = Math.max(0, event.entityLiving.getHealth() - allDamage);

				event.entityLiving.setHealth(result);
			}
		}
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

}
