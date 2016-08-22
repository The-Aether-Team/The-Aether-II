package com.gildedgames.aether.common.entities.projectiles;

import com.gildedgames.aether.common.items.ItemsAether;
import com.gildedgames.aether.common.items.weapons.ItemDartType;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class EntityDart extends EntityArrow
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
	protected void arrowHit(EntityLivingBase entity)
	{
		if (this.getDartType() == ItemDartType.POISON)
		{
			entity.addPotionEffect(new PotionEffect(MobEffects.POISON, 80, 0));
		}
	}

	@Override
	protected void entityInit()
	{
		super.entityInit();

		this.dataManager.register(TYPE, (byte) 0);
	}

	public void setDartType(ItemDartType type)
	{
		this.dataManager.set(TYPE, (byte) type.ordinal());
	}

	public ItemDartType getDartType()
	{
		return ItemDartType.values()[this.dataManager.get(TYPE)];
	}
}
