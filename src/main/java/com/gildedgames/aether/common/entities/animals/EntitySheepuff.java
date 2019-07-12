package com.gildedgames.aether.common.entities.animals;

import com.gildedgames.aether.api.entity.damage.DamageTypeAttributes;
import com.gildedgames.aether.api.registrar.BlocksAether;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.entities.ai.EntityAIHideFromRain;
import com.gildedgames.aether.common.entities.ai.EntityAIRestrictRain;
import com.gildedgames.aether.common.entities.ai.EntityAIUnstuckBlueAercloud;
import com.gildedgames.aether.common.entities.ai.kirrid.EntityAIEatAetherGrass;
import com.gildedgames.aether.common.entities.multipart.AetherMultiPartShearable;
import com.gildedgames.aether.common.init.LootTablesAether;
import com.gildedgames.aether.common.util.helpers.MathUtil;
import net.minecraft.entity.*;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.util.SoundEvents;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import javax.vecmath.Point3d;
import java.util.ArrayList;
import java.util.List;

public class EntitySheepuff extends SheepEntity implements IEntityMultiPart
{

	public static final DataParameter<Float> PUFFINESS = EntityDataManager.createKey(EntitySheepuff.class, DataSerializers.FLOAT);

	private float offGroundCheck;

	protected EntityAIEatAetherGrass entityAIEatGrass;

	private final Point3d old;

	private final MultiPartEntityPart[] parts;

	private final MultiPartEntityPart head = new AetherMultiPartShearable(this, "head", 0.6F, 0.6F);

	public EntitySheepuff(World worldIn)
	{
		super(worldIn);

		this.parts = new MultiPartEntityPart[] { this.head };
		this.old = new Point3d();
		this.setSize(1.1F, 1.4F);
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
	protected void entityInit()
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
		return new EntitySheepuff(this.world);
	}

	@Override
	public World getWorld()
	{
		return this.getEntityWorld();
	}

	@Override
	public boolean attackEntityFromPart(MultiPartEntityPart part, DamageSource source, float damage)
	{
		if (this.hurtResistantTime <= 10)
		{
			return this.attackEntityFrom(source, damage * 1.1f);
		}
		else
		{
			return false;
		}
	}

	@Nullable
	@Override
	public MultiPartEntityPart[] getParts()
	{
		return this.parts;
	}

	@Override
	public boolean attackEntityFrom(DamageSource source, float amount)
	{
		boolean result = super.attackEntityFrom(source, amount);

		if (result && this.onGround && !this.getSheared())
		{
			this.motionY = 1.5f + this.rand.nextFloat();
		}

		return result;
	}

	@Override
	public void onLivingUpdate()
	{
		super.onLivingUpdate();

		this.old.set(this.head.posX, this.head.posY, this.head.posZ);

		float f = MathUtil.interpolateRotation(this.prevRenderYawOffset, this.renderYawOffset, 1);
		float f1 = MathHelper.cos(-f * 0.017453292F - (float) Math.PI);
		float f2 = MathHelper.sin(-f * 0.017453292F - (float) Math.PI);

		this.head.setLocationAndAngles(this.posX - f2 * .8f, this.posY + .85f, this.posZ - f1 * .8f, 0F, 0F);
		this.head.onUpdate();

		this.head.prevPosX = this.old.getX();
		this.head.prevPosY = this.old.getY();
		this.head.prevPosZ = this.old.getZ();
	}

	@Override
	public void livingTick()
	{
		super.livingTick();

		if (this.motionY < -0.1d && !this.getSheared())
		{
			this.motionY = -0.1d;
			this.fallDistance = 0;
		}

		if (!this.onGround)
		{
			this.offGroundCheck += Math.abs(this.motionY);

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
	public List<ItemStack> onSheared(ItemStack item, IBlockReader world, BlockPos pos, int fortune)
	{
		this.motionY = 0;
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
	public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata)
	{
		livingdata = super.onInitialSpawn(difficulty, livingdata);

		this.setFleeceColor(EnumDyeColor.WHITE);

		return livingdata;
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
	protected ResourceLocation getLootTable()
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

		this.motionY = 1.5f + this.rand.nextFloat();
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
