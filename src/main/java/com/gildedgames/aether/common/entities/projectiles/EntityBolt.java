package com.gildedgames.aether.common.entities.projectiles;

import com.gildedgames.aether.api.damage_system.IDamageLevelsHolder;
import com.gildedgames.aether.api.registrar.ItemsAether;
import com.gildedgames.aether.common.items.weapons.crossbow.ItemBoltType;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.ItemStack;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityBolt extends EntityArrow implements IDamageLevelsHolder
{
	private static final DataParameter<Byte> TYPE = new DataParameter<>(20, DataSerializers.BYTE);

	private static final DataParameter<Byte> ABILITY = new DataParameter<>(21, DataSerializers.BYTE);

	public EntityBolt(final World worldIn)
	{
		super(worldIn);
	}

	public EntityBolt(final World worldIn, final EntityLivingBase shooter)
	{
		super(worldIn, shooter);
	}

	@Override
	protected ItemStack getArrowStack()
	{
		return new ItemStack(ItemsAether.bolt, 1, this.getBoltType().ordinal());
	}

	@Override
	protected void arrowHit(final EntityLivingBase living)
	{
	}

	@Override
	protected void entityInit()
	{
		super.entityInit();

		this.dataManager.register(TYPE, (byte) 0);
		this.dataManager.register(ABILITY, (byte) 0);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public int getBrightnessForRender()
	{
		final BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos(MathHelper.floor(this.posX), 0, MathHelper.floor(this.posZ));

		if (this.world.isBlockLoaded(blockpos$mutableblockpos))
		{
			blockpos$mutableblockpos.setY(MathHelper.floor(this.posY + (double) this.getEyeHeight()));
			return this.world.getCombinedLight(blockpos$mutableblockpos, 0);
		}
		else
		{
			return 0;
		}
	}

	public BoltAbility getBoltAbility()
	{
		return BoltAbility.values()[this.dataManager.get(ABILITY)];
	}

	public void setBoltAbility(final BoltAbility ability)
	{
		this.dataManager.set(ABILITY, (byte) ability.ordinal());
	}

	public ItemBoltType getBoltType()
	{
		return ItemBoltType.values()[this.dataManager.get(TYPE)];
	}

	public void setBoltType(final ItemBoltType type)
	{
		this.dataManager.set(TYPE, (byte) type.ordinal());
	}

	@Override
	public int getSlashDamageLevel()
	{
		return this.getBoltType().getSlashDamageLevel();
	}

	@Override
	public int getPierceDamageLevel()
	{
		return this.getBoltType().getPierceDamageLevel();
	}

	@Override
	public int getImpactDamageLevel()
	{
		return this.getBoltType().getImpactDamageLevel();
	}

	public enum BoltAbility
	{
		NORMAL
	}
}
