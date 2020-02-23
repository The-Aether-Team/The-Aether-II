package com.gildedgames.aether.common.entities.projectiles;

import com.gildedgames.aether.api.entity.damage.IDamageLevelsHolder;
import com.gildedgames.aether.api.entity.effects.EEffectIntensity;
import com.gildedgames.aether.api.entity.effects.IAetherStatusEffectIntensity;
import com.gildedgames.aether.api.entity.effects.IAetherStatusEffects;
import com.gildedgames.aether.api.registrar.ItemsAether;
import com.gildedgames.aether.common.entities.effects.StatusEffectCockatriceVenom;
import com.gildedgames.aether.common.items.weapons.ItemDartType;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.ItemStack;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;

public class EntityDart extends EntityArrow implements IDamageLevelsHolder
{
	private static final DataParameter<Byte> TYPE = new DataParameter<>(20, DataSerializers.BYTE);

	public EntityDart(World worldIn)
	{
		super(worldIn);
	}

	public EntityDart(World worldIn, EntityLivingBase shooter)
	{
		super(worldIn, shooter);
	}

	@Override
	protected ItemStack getArrowStack()
	{
		return new ItemStack(ItemsAether.dart, this.getDartType().ordinal());
	}

	@Override
	public void onUpdate()
	{
		super.onUpdate();

		if (this.world.isRemote && this.world.getWorldTime() % 3 == 0)
		{
			if (this.getDartType() == ItemDartType.ENCHANTED)
			{
				this.world.spawnParticle(EnumParticleTypes.CRIT_MAGIC, this.posX, this.posY, this.posZ, 0.0D, 0.0D, 0.0D);
			}
		}
	}

	@Override
	protected void arrowHit(EntityLivingBase entity)
	{
		if (this.getDartType() == ItemDartType.POISON)
		{
			int buildup = IAetherStatusEffectIntensity.getBuildupFromEffect(new StatusEffectCockatriceVenom(entity), EEffectIntensity.MAJOR);
			IAetherStatusEffects.applyStatusEffect(entity, IAetherStatusEffects.effectTypes.TOXIN, buildup);
		}
	}

	@Override
	protected void entityInit()
	{
		super.entityInit();

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
