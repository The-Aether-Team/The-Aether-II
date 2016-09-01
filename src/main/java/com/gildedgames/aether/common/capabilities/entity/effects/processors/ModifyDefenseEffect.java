package com.gildedgames.aether.common.capabilities.entity.effects.processors;

import com.gildedgames.aether.api.capabilites.entity.effects.EntityEffectInstance;
import com.gildedgames.aether.api.capabilites.entity.effects.EntityEffectRule;
import com.gildedgames.aether.common.capabilities.entity.effects.AbstractEffectProcessor;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

import java.util.List;

public class ModifyDefenseEffect extends AbstractEffectProcessor<ModifyDefenseEffect.Instance>
{

	public static class Instance extends EntityEffectInstance
	{

		public Instance(double defenseMod, EntityEffectRule... rules)
		{
			super(rules);

			this.getAttributes().setDouble("defenseMod", defenseMod);
		}

		public double getDefense()
		{
			return this.getAttributes().getDouble("defenseMod");
		}

		@Override
		public EntityEffectInstance cloneInstance()
		{
			return new Instance(this.getDefense(), this.getRules());
		}

	}

	public ModifyDefenseEffect()
	{
		super("ability.defenseMod.localizedName", "ability.defenseMod.desc");
	}

	@Override
	public String[] getFormatParameters(Entity source, Instance instance)
	{
		double defense = instance.getDefense();

		String prefix = defense > 0 ? (TextFormatting.BLUE + "+") : (TextFormatting.RED + "");

		String par = prefix + (defense == (int) Math.floor(defense) ? String.valueOf((int) Math.floor(defense)) : String.valueOf(defense));

		return new String[] { par };
	}

	@Override
	public void onHurt(LivingHurtEvent event, Entity source, List<Instance> all)
	{
		if (!(source instanceof EntityLivingBase))
		{
			return;
		}

		float allDefense = 0.0F;

		for (Instance instance : all)
		{
			allDefense += instance.getDefense();
		}

		float defense = Math.min(event.getAmount(), allDefense * 2);

		defense = Math.max(1.0F, defense - 1.0F);

		EntityLivingBase living = (EntityLivingBase) source;

		living.setHealth(living.getHealth() + defense);
	}

}
