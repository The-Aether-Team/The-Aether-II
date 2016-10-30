package com.gildedgames.aether.common.capabilities.entity.effects.processors.player;

import com.gildedgames.aether.common.capabilities.entity.effects.processors.ModifyMaxHealthEffect;
import com.gildedgames.aether.common.capabilities.player.PlayerAetherImpl;
import com.gildedgames.aether.common.entities.living.companions.EntityCompanion;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextFormatting;

import java.util.List;

public class ModifyCompanionMaxHealthEffect extends ModifyMaxHealthEffect
{

	@Override
	public String getUnlocalizedName(Entity source, Instance instance)
	{
		return "ability.companionMaxHealthMod.name";
	}

	@Override
	public String[] getUnlocalizedDesc(Entity source, Instance instance)
	{
		return new String[] { "ability.maxHealthMod.desc", "\u2022 " + TextFormatting.ITALIC + I18n.format("ability.companion.desc") };
	}

	@Override
	public void tick(Entity source, List<ModifyMaxHealthEffect.Instance> all)
	{
		for (ModifyMaxHealthEffect.Instance instance : all)
		{
			this.apply(source, instance, all);
		}
	}

	@Override
	public void apply(Entity source, Instance instance, List<Instance> all)
	{
		if (!(source instanceof EntityPlayer))
		{
			return;
		}

		PlayerAetherImpl playerAether = PlayerAetherImpl.getPlayer(source);

		EntityCompanion companion = playerAether.getCompanionModule().getCompanionEntity();

		if (companion != null)
		{
			float maxHealth = companion.getMaxHealth();

			super.apply(companion, instance, all);

			if (maxHealth != companion.getMaxHealth())
			{
				companion.setHealth(companion.getMaxHealth());
			}
		}
	}

	@Override
	public void cancel(Entity source, Instance instance, List<Instance> all)
	{
		if (!(source instanceof EntityPlayer))
		{
			return;
		}

		PlayerAetherImpl playerAether = PlayerAetherImpl.getPlayer(source);

		Entity companion = playerAether.getCompanionModule().getCompanionEntity();

		if (companion != null)
		{
			super.cancel(companion, instance, all);
		}
	}

}
