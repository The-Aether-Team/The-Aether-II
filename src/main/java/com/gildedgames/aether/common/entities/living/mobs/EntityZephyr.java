package com.gildedgames.aether.common.entities.living.mobs;

import com.gildedgames.aether.api.damage_system.DamageTypeAttributes;
import com.gildedgames.aether.common.entities.util.flying.EntityFlyingDayMob;
import com.gildedgames.aether.common.registry.content.LootTablesAether;
import com.gildedgames.aether.common.registry.content.SoundsAether;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;

public class EntityZephyr extends EntityFlyingDayMob
{
	public EntityZephyr(final World world)
	{
		super(world);

		this.setSize(1.0F, 1.0F);

		this.experienceValue = 3;
	}

	@Override
	protected ResourceLocation getLootTable()
	{
		return LootTablesAether.ENTITY_ZEPHYR;
	}

	@Override
	protected SoundEvent getAmbientSound()
	{
		return SoundsAether.zephyr_ambient;
	}

	@Override
	protected SoundEvent getHurtSound(final DamageSource src)
	{
		return SoundsAether.zephyr_ambient;
	}

	@Override
	protected SoundEvent getDeathSound()
	{
		return SoundsAether.zephyr_ambient;
	}

	@Override
	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();

		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(13.0D);

		this.getEntityAttribute(DamageTypeAttributes.SLASH_DEFENSE_LEVEL).setBaseValue(8);
		this.getEntityAttribute(DamageTypeAttributes.PIERCE_DEFENSE_LEVEL).setBaseValue(4);
		this.getEntityAttribute(DamageTypeAttributes.IMPACT_DEFENSE_LEVEL).setBaseValue(8);
	}
}
