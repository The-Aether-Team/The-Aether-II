package com.gildedgames.aether.common.entities.living.passive;

import com.gildedgames.aether.api.damage_system.DamageTypeAttributes;
import com.gildedgames.aether.common.entities.util.flying.EntityFlying;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.world.World;

public class EntityGlitterwing extends EntityFlying
{

	public EntityGlitterwing(World world)
	{
		super(world);

		this.setSize(0.3F, 0.3F);
	}

	@Override
	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();

		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(1.0D);

		this.getEntityAttribute(DamageTypeAttributes.SLASH_DEFENSE_LEVEL).setBaseValue(1);
		this.getEntityAttribute(DamageTypeAttributes.PIERCE_DEFENSE_LEVEL).setBaseValue(1);
		this.getEntityAttribute(DamageTypeAttributes.IMPACT_DEFENSE_LEVEL).setBaseValue(1);
	}

}
