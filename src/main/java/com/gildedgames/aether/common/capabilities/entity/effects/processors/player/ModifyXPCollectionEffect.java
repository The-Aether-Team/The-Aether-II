package com.gildedgames.aether.common.capabilities.entity.effects.processors.player;

import com.gildedgames.aether.api.capabilites.entity.effects.EntityEffectInstance;
import com.gildedgames.aether.api.capabilites.entity.effects.EntityEffectRule;
import com.gildedgames.aether.common.capabilities.entity.effects.AbstractEffectProcessorPlayer;
import com.gildedgames.aether.common.capabilities.entity.effects.processors.player.ModifyXPCollectionEffect.Instance;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.event.entity.player.PlayerPickupXpEvent;

import java.util.List;

public class ModifyXPCollectionEffect extends AbstractEffectProcessorPlayer<Instance>
{

	public static class Instance extends EntityEffectInstance
	{

		public Instance(double percentMod, EntityEffectRule... rules)
		{
			super(rules);

			this.getAttributes().setDouble("percentMod", percentMod);
		}

		@Override
		public EntityEffectInstance cloneInstance()
		{
			return new Instance(this.getAttributes().getDouble("percentMod"), this.getRules());
		}

	}

	public ModifyXPCollectionEffect()
	{
		super("ability.modifyXPCollection.localizedName", "ability.modifyXPCollection.desc");
	}

	private String displayValue(double value)
	{
		return value == (int) Math.floor(value) ? String.valueOf((int) Math.floor(value)) : String.valueOf(value);
	}

	@Override
	public String[] getFormatParameters(Entity source, Instance instance)
	{
		double percentMod = instance.getAttributes().getDouble("percentMod") * 100.0D;

		String prefix = percentMod > 0 ? (TextFormatting.BLUE + "+") : (TextFormatting.RED + "");

		return new String[] { (prefix + this.displayValue(percentMod) + "%") };
	}

	@Override
	public void onPickupXP(PlayerPickupXpEvent event, EntityPlayer source, List<Instance> all)
	{
		float totalPercent = 0.0F;

		for (Instance instance : all)
		{
			totalPercent += instance.getAttributes().getFloat("percentMod");
		}

		event.getOrb().xpValue += event.getOrb().xpValue * totalPercent;
	}

}
