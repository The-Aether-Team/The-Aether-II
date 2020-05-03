package com.gildedgames.aether.api.entity.damage;

import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.RangedAttribute;

public class DamageTypeAttributes
{
	public static final IAttribute SLASH_DEFENSE_LEVEL = new RangedAttribute(null, "attribute.name.aether.slash", 0.0F, -2.0F, 2.0F).setShouldWatch(true);

	public static final IAttribute PIERCE_DEFENSE_LEVEL = new RangedAttribute(null, "attribute.name.aether.pierce", 0.0F, -2.0F, 2.0F).setShouldWatch(true);

	public static final IAttribute IMPACT_DEFENSE_LEVEL = new RangedAttribute(null, "attribute.name.aether.impact", 0.0F, -2.0F, 2.0F).setShouldWatch(true);
}
