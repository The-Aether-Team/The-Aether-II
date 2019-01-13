package com.gildedgames.aether.api.damage_system;

import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.RangedAttribute;

public class DamageTypeAttributes
{
	public static final IAttribute SLASH_DAMAGE_LEVEL = new RangedAttribute(null, "aether.slashDamageLevel", 0.0D, 0.0D, 2048.0D).setShouldWatch(true);

	public static final IAttribute PIERCE_DAMAGE_LEVEL = new RangedAttribute(null, "aether.pierceDamageLevel", 0.0D, 0.0D, 2048.0D).setShouldWatch(true);

	public static final IAttribute IMPACT_DAMAGE_LEVEL = new RangedAttribute(null, "aether.impactDamageLevel", 0.0D, 0.0D, 2048.0D).setShouldWatch(true);

	public static final IAttribute SLASH_DEFENSE_LEVEL = new RangedAttribute(null, "aether.slashDefenseLevel", 0.0D, 0.0D, 2048.0D);

	public static final IAttribute PIERCE_DEFENSE_LEVEL = new RangedAttribute(null, "aether.pierceDefenseLevel", 0.0D, 0.0D, 2048.0D);

	public static final IAttribute IMPACT_DEFENSE_LEVEL = new RangedAttribute(null, "aether.impactDefenseLevel", 0.0D, 0.0D, 2048.0D);
}
