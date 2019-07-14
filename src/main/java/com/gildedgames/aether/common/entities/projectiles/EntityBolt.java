package com.gildedgames.aether.common.entities.projectiles;

import com.gildedgames.aether.api.entity.damage.IDamageLevelsHolder;
import com.gildedgames.aether.api.registrar.ItemsAether;
import com.gildedgames.aether.common.entities.EntityTypesAether;
import com.gildedgames.aether.common.items.weapons.crossbow.ItemBoltType;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class EntityBolt extends AbstractArrowEntity implements IDamageLevelsHolder
{
	private static final DataParameter<Byte> TYPE = new DataParameter<>(20, DataSerializers.BYTE);

	private static final DataParameter<Byte> ABILITY = new DataParameter<>(21, DataSerializers.BYTE);

	private ItemStack stack = ItemStack.EMPTY;

	public EntityBolt(EntityType<? extends EntityBolt> type, World world) {
		super(type, world);
	}

	public EntityBolt(World world, double x, double y, double z) {
		super(EntityTypesAether.BOLT, x, y, z, world);
	}

	public EntityBolt(World world, LivingEntity shooter, ItemStack stack) {
		super(EntityTypesAether.BOLT, shooter, world);

		this.stack = stack.copy();
		this.stack.setCount(1);
	}

	@Override
	protected ItemStack getArrowStack()
	{
		return this.stack;
	}

	@Override
	protected void arrowHit(final LivingEntity living)
	{
	}

	@Override
	protected void registerData()
	{
		super.registerData();

		this.dataManager.register(TYPE, (byte) 0);
		this.dataManager.register(ABILITY, (byte) 0);
	}

	@OnlyIn(Dist.CLIENT)
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
