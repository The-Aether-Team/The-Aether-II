package com.gildedgames.aether.common.entities.projectiles;

import com.gildedgames.aether.common.items.ItemsAether;
import com.gildedgames.aether.common.items.weapons.ItemDartType;
import com.gildedgames.aether.common.items.weapons.crossbow.ItemBoltType;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.ItemStack;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.world.World;

public class EntityBolt extends EntityArrow
{
	private static final DataParameter<Byte> TYPE = new DataParameter<>(20, DataSerializers.BYTE);

	public EntityBolt(World worldIn)
	{
		super(worldIn);
	}

	public EntityBolt(World worldIn, EntityLivingBase shooter)
	{
		super(worldIn, shooter);
	}

	@Override
	protected ItemStack getArrowStack()
	{
		return new ItemStack(ItemsAether.dart, this.getBoltType().ordinal());
	}

	@Override
	protected void arrowHit(EntityLivingBase living)
	{

	}

	@Override
	protected void entityInit()
	{
		super.entityInit();

		this.dataManager.register(TYPE, (byte) 0);
	}

	public void setBoltType(ItemBoltType type)
	{
		this.dataManager.set(TYPE, (byte) type.ordinal());
	}

	public ItemBoltType getBoltType()
	{
		return ItemBoltType.values()[this.dataManager.get(TYPE)];
	}
}
