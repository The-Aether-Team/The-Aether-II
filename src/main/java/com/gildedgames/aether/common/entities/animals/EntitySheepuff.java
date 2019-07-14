package com.gildedgames.aether.common.entities.animals;

import com.gildedgames.aether.api.entity.damage.DamageTypeAttributes;
import com.gildedgames.aether.api.registrar.BlocksAether;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.entities.EntityTypesAether;
import com.gildedgames.aether.common.entities.ai.EntityAIHideFromRain;
import com.gildedgames.aether.common.entities.ai.EntityAIRestrictRain;
import com.gildedgames.aether.common.entities.ai.EntityAIUnstuckBlueAercloud;
import com.gildedgames.aether.common.entities.ai.kirrid.EntityAIEatAetherGrass;
import com.gildedgames.aether.common.init.LootTablesAether;
import net.minecraft.entity.*;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.item.DyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class EntitySheepuff extends SheepEntity
{

	public static final DataParameter<Float> PUFFINESS = EntityDataManager.createKey(EntitySheepuff.class, DataSerializers.FLOAT);

	private float offGroundCheck;

	protected EntityAIEatAetherGrass entityAIEatGrass;

	public EntitySheepuff(EntityType<? extends SheepEntity> type, World worldIn)
	{
		super(type, worldIn);
	}

	@Override
	protected void registerGoals()
	{
		super.registerGoals();

		this.entityAIEatGrass = new EntityAIEatAetherGrass(this, 350);

		this.goalSelector.addGoal(2, new EntityAIRestrictRain(this));
		this.goalSelector.addGoal(3, new EntityAIUnstuckBlueAercloud(this));
		this.goalSelector.addGoal(3, new EntityAIHideFromRain(this, 1.3D));
		//		this.goalSelector.addGoal(3, new EntityAITempt(this, 1.2D, false, TEMPTATION_ITEMS));
		this.goalSelector.addGoal(9, this.entityAIEatGrass);
	}

	@Override
	protected void registerData()
	{
		super.registerData();

		this.dataManager.register(PUFFINESS, 0f);
	}

	@Override
	protected void registerAttributes()
	{
		super.registerAttributes();

		this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(10.0D);

		this.getAttribute(DamageTypeAttributes.SLASH_DEFENSE_LEVEL).setBaseValue(8);
		this.getAttribute(DamageTypeAttributes.PIERCE_DEFENSE_LEVEL).setBaseValue(2);
		this.getAttribute(DamageTypeAttributes.IMPACT_DEFENSE_LEVEL).setBaseValue(5);
	}

	@Override
	public EntitySheepuff createChild(AgeableEntity ageable)
	{
		return new EntitySheepuff(EntityTypesAether.SHEEPUFF, this.world);
	}

	@Override
	public boolean attackEntityFrom(DamageSource source, float amount)
	{
		boolean result = super.attackEntityFrom(source, amount);

		if (result && this.onGround && !this.getSheared())
		{
			this.setMotion(this.getMotion().getX(), 1.5f + this.rand.nextFloat(), this.getMotion().getZ());
		}

		return result;
	}

	@Override
	public void tick()
	{
		super.tick();


		if (this.getMotion().getY() < -0.1d && !this.getSheared())
		{
			this.setMotion(this.getMotion().getX(), -0.1D, this.getMotion().getZ());
			this.fallDistance = 0;
		}

		if (!this.onGround)
		{
			this.offGroundCheck += Math.abs(this.getMotion().getY());

			if (this.offGroundCheck > 2)
			{
				this.addPuffiness(0.1f);
			}
		}
		else
		{
			this.offGroundCheck = 0;
			this.addPuffiness(-0.05f);
		}
	}

	@Override
	public List<ItemStack> onSheared(ItemStack item, IWorld world, BlockPos pos, int fortune)
	{
		this.setMotion(this.getMotion().getX(), 0.0D, this.getMotion().getZ());
		this.setSheared(true);

		int count = 1 + this.rand.nextInt(3);

		List<ItemStack> ret = new ArrayList<>();

		for (int i = 0; i < count; i++)
		{
			ret.add(new ItemStack(BlocksAether.cloudwool_block));
		}

		this.playSound(SoundEvents.ENTITY_SHEEP_SHEAR, 1.0F, 1.0F);

		return ret;
	}

	@Override
	@Nullable
	public ILivingEntityData onInitialSpawn(IWorld world, DifficultyInstance difficulty, SpawnReason reason, ILivingEntityData data, CompoundNBT nbt)
	{
		data = super.onInitialSpawn(world, difficulty, reason, data, nbt);

		this.setFleeceColor(DyeColor.WHITE);

		return data;
	}

	@Override
	protected float getSoundPitch()
	{
		return this.isChild() ? (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1F : (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 0.55F;
	}

	@Override
	protected SoundEvent getAmbientSound()
	{
		return new SoundEvent(AetherCore.getResource("mob.kirrid.ambient"));
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource source)
	{
		return new SoundEvent(AetherCore.getResource("mob.kirrid.hurt"));
	}

	@Override
	protected SoundEvent getDeathSound()
	{
		return new SoundEvent(AetherCore.getResource("mob.kirrid.death"));
	}

	@Override
	@Nullable
	public ResourceLocation getLootTable()
	{
		if (this.getSheared())
		{
			return LootTablesAether.ENTITY_KIRRID_SHEARED;
		}

		return LootTablesAether.ENTITY_KIRRID;
	}

	@Override
	public void fall(float distance, float damageMultiplier)
	{
		if (this.getSheared())
		{
			super.fall(distance, damageMultiplier);
		}
	}

	@Override
	public void eatGrassBonus()
	{
		super.eatGrassBonus();

		this.setMotion(this.getMotion().getX(), 1.5f + this.rand.nextFloat(), this.getMotion().getZ());
	}

	public void addPuffiness(float puffiness)
	{
		this.dataManager.set(PUFFINESS, MathHelper.clamp(this.getPuffiness() + puffiness, 0f, 1f));
	}

	public float getPuffiness()
	{
		return this.dataManager.get(PUFFINESS);
	}


}
