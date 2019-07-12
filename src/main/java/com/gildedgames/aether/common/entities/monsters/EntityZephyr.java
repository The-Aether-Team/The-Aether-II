package com.gildedgames.aether.common.entities.monsters;

import com.gildedgames.aether.api.entity.damage.DamageTypeAttributes;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.entities.flying.EntityFlying;
import com.gildedgames.aether.common.init.LootTablesAether;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;

public class EntityZephyr extends EntityFlying
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
		return new SoundEvent(AetherCore.getResource("mob.zephyr.ambient"));
	}

	@Override
	protected SoundEvent getHurtSound(final DamageSource src)
	{
		return new SoundEvent(AetherCore.getResource("mob.zephyr.ambient"));
	}

	@Override
	protected SoundEvent getDeathSound()
	{
		return new SoundEvent(AetherCore.getResource("mob.zephyr.ambient"));
	}

	@Override
	protected void registerAttributes()
	{
		super.registerAttributes();

		this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(13.0D);

		this.getAttribute(DamageTypeAttributes.SLASH_DEFENSE_LEVEL).setBaseValue(8);
		this.getAttribute(DamageTypeAttributes.PIERCE_DEFENSE_LEVEL).setBaseValue(4);
		this.getAttribute(DamageTypeAttributes.IMPACT_DEFENSE_LEVEL).setBaseValue(8);
	}
}
