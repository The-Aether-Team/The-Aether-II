package com.gildedgames.aether.common.entities.projectiles;

import com.gildedgames.aether.api.entity.damage.IDamageLevelsHolder;
import com.gildedgames.aether.api.entity.effects.IAetherStatusEffects;
import com.gildedgames.aether.common.entities.EntityTypesAether;
import com.gildedgames.aether.api.registrar.ItemsAether;
import com.gildedgames.aether.common.items.weapons.ItemDartType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.world.World;

public class EntityDart extends AbstractArrowEntity implements IDamageLevelsHolder
{
	private static final DataParameter<Byte> TYPE = new DataParameter<>(20, DataSerializers.BYTE);

	public EntityDart(EntityType<? extends EntityDart> type, World world) {
		super(type, world);
	}

	public EntityDart(World worldIn, LivingEntity shooter)
	{
		super(EntityTypesAether.DART, shooter, worldIn);
	}


	@Override
	protected ItemStack getArrowStack()
	{
		return new ItemStack(ItemsAether.dart, this.getDartType().ordinal());
	}

	@Override
	public void tick()
	{
		super.tick();

		if (this.world.isRemote() && this.world.getWorldTime() % 3 == 0)
		{
			if (this.getDartType() == ItemDartType.ENCHANTED)
			{
				this.world.spawnParticle(ParticleTypes.CRIT_MAGIC, this.posX, this.posY, this.posZ, 0.0D, 0.0D, 0.0D);
			}
		}
	}

	@Override
	protected void arrowHit(LivingEntity entity)
	{
		if (this.getDartType() == ItemDartType.POISON)
		{
			IAetherStatusEffects.applyStatusEffect(entity, IAetherStatusEffects.effectTypes.TOXIN, 100);
		}
	}

	@Override
	protected void registerData()
	{
		super.registerData();

		this.dataManager.register(TYPE, (byte) 0);
	}

	public ItemDartType getDartType()
	{
		return ItemDartType.values()[this.dataManager.get(TYPE)];
	}

	public void setDartType(ItemDartType type)
	{
		this.dataManager.set(TYPE, (byte) type.ordinal());
	}

	@Override
	public int getSlashDamageLevel()
	{
		return this.getDartType().getSlashDamageLevel();
	}

	@Override
	public int getPierceDamageLevel()
	{
		return this.getDartType().getPierceDamageLevel();
	}

	@Override
	public int getImpactDamageLevel()
	{
		return this.getDartType().getImpactDamageLevel();
	}
}
