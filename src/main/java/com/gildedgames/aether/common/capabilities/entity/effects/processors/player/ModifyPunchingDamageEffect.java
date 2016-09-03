package com.gildedgames.aether.common.capabilities.entity.effects.processors.player;

import com.gildedgames.aether.api.capabilites.entity.properties.ElementalState;
import com.gildedgames.aether.common.capabilities.entity.effects.processors.ModifyDamageEffect;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;

import java.util.List;

/**
 * @author Brandon Pearce
 */
public class ModifyPunchingDamageEffect extends ModifyDamageEffect
{

	public ModifyPunchingDamageEffect()
	{

	}

	@Override
	public String getUnlocalizedName(Entity source, Instance instance)
	{
		return "ability.punchingDamage.localizedName";
	}

	@Override
	public String[] getUnlocalizedDesc(Entity source, Instance instance)
	{
		return new String[] { instance.getElementalDamageSource().getElementalState() == ElementalState.BIOLOGICAL ? "ability.punchingDamage.desc1" : "ability.punchingDamage.desc2"};
	}

	@Override
	public void apply(Entity source, Instance instance, List<Instance> all)
	{
		if (!(source instanceof EntityPlayer))
		{
			return;
		}

		EntityPlayer player = (EntityPlayer)source;

		if (player.getHeldItemMainhand() == null)
		{
			super.apply(source, instance, all);
		}
	}

}
