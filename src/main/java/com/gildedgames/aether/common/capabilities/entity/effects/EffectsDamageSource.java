package com.gildedgames.aether.common.capabilities.entity.effects;

import net.minecraft.util.DamageSource;

public class EffectsDamageSource extends DamageSource
{
	public static final DamageSource BLEED = (new EffectsDamageSource("aether.effect.bleed")).setDamageBypassesArmor();
	public static final DamageSource TOXIN = (new EffectsDamageSource("aether.effect.toxin")).setDamageBypassesArmor();
	public static final DamageSource COCKATRICE_VENOM = (new EffectsDamageSource("aether.effect.cockatriceVenom")).setDamageBypassesArmor();
	public static final DamageSource FUNGAL_ROT = (new EffectsDamageSource("aether.effect.fungalRot")).setDamageBypassesArmor();

	public EffectsDamageSource(String damageTypeIn)
	{
		super(damageTypeIn);
	}
}
