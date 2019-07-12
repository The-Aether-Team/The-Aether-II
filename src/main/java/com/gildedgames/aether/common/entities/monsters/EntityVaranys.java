package com.gildedgames.aether.common.entities.monsters;

import com.gildedgames.aether.api.entity.damage.DamageTypeAttributes;
import com.gildedgames.aether.api.entity.effects.EEffectIntensity;
import com.gildedgames.aether.api.entity.effects.IAetherStatusEffectIntensity;
import com.gildedgames.aether.api.entity.effects.IAetherStatusEffects;
import com.gildedgames.aether.common.entities.ai.EntityAIHideFromLight;
import com.gildedgames.aether.common.entities.ai.EntityAIUnstuckBlueAercloud;
import com.gildedgames.aether.common.entities.ai.EntityAIWanderAvoidLight;
import com.gildedgames.aether.common.entities.effects.StatusEffectBleed;
import com.gildedgames.aether.common.entities.multipart.AetherMultiPartEntity;
import com.gildedgames.aether.common.util.helpers.MathUtil;
import net.minecraft.entity.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import javax.vecmath.Point3d;

public class EntityVaranys extends EntityAetherMonster implements IEntityMultiPart
{
	private final MultiPartEntityPart[] parts;

	private final MultiPartEntityPart head = new AetherMultiPartEntity(this, "head", .7F, .7F);

	private final MultiPartEntityPart tail1 = new AetherMultiPartEntity(this, "tail", 1F, .7F);

	private final MultiPartEntityPart tail2 = new AetherMultiPartEntity(this, "tail2", 1F, .7F);

	private final EntityAIHideFromLight lightAI;

	private final Point3d[] old;

	public EntityVaranys(final World world)
	{
		super(world);

		this.lightAI = new EntityAIHideFromLight(this, 0.8F, 5);
		this.parts = new MultiPartEntityPart[] { this.head, this.tail1, this.tail2 };

		this.goalSelector.addGoal(0, new EntityAISwimming(this));
		this.goalSelector.addGoal(0, new EntityAIUnstuckBlueAercloud(this));
		this.goalSelector.addGoal(1, this.lightAI);
		this.goalSelector.addGoal(1, new EntityAIWanderAvoidLight(this, 0.8D, 5));
		this.goalSelector.addGoal(2, new EntityAILeapAtTarget(this, 0.4F));
		this.goalSelector.addGoal(3, new EntityAIFleeSun(this, 1.0D));
		this.goalSelector.addGoal(3, new EntityAIAttackMelee(this, 1D, false));
		this.goalSelector.addGoal(4, new EntityAIWatchClosest(this, PlayerEntity.class, 6.0F));
		this.targetSelector.addGoal(1, new EntityAIHurtByTarget(this, false));
		this.targetSelector.addGoal(2, new EntityAINearestAttackableTarget<>(this, PlayerEntity.class, true));

		this.setSize(1.4F, 1F);

		this.stepHeight = 1.0F;
		this.head.setInvisible(true);
		this.tail1.setInvisible(true);
		this.tail2.setInvisible(true);

		this.experienceValue = 7;

		this.old = new Point3d[this.parts.length];

		for (int i = 0; i < this.old.length; i++)
		{
			this.old[i] = new Point3d();
		}
	}

	@Override
	protected void registerAttributes()
	{
		super.registerAttributes();

		this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.4D);
		this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(30.0D);
		this.getAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(2.5D);
		this.getAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(2.0D);

		this.getAttribute(DamageTypeAttributes.SLASH_DEFENSE_LEVEL).setBaseValue(15);
		this.getAttribute(DamageTypeAttributes.PIERCE_DEFENSE_LEVEL).setBaseValue(8);
		this.getAttribute(DamageTypeAttributes.IMPACT_DEFENSE_LEVEL).setBaseValue(4);
	}

	@Override
	public boolean attackEntityFromPart(MultiPartEntityPart part, DamageSource source, float damage)
	{
		switch (part.partName)
		{
			case "head":
				damage *= 1.05f;
				break;

			default:
				damage *= .7f;
				break;
		}

		if (this.hurtResistantTime <= 10)
		{
			return this.attackEntityFrom(source, damage);
		}
		else
		{
			return false;
		}
	}

	@Override
	public World getWorld()
	{
		return this.getEntityWorld();
	}

	@Nullable
	@Override
	public MultiPartEntityPart[] getParts()
	{
		return this.parts;
	}

	@Override
	public boolean attackEntityAsMob(final Entity entity)
	{
		final boolean flag = super.attackEntityAsMob(entity);

		if (flag && entity instanceof LivingEntity)
		{
			this.applyStatusEffectOnAttack(entity);
		}

		return flag;
	}

	@Override
	protected void applyStatusEffectOnAttack(final Entity target)
	{
		if (target instanceof LivingEntity)
		{
			final LivingEntity living = (LivingEntity) target;

			if (!living.isActiveItemStackBlocking())
			{
				int buildup = IAetherStatusEffectIntensity.getBuildupFromEffect(new StatusEffectBleed(living), EEffectIntensity.MINOR);
				IAetherStatusEffects.applyStatusEffect(living, IAetherStatusEffects.effectTypes.BLEED, buildup);
			}
		}
	}

	@Override
	public void onLivingUpdate()
	{
		super.onLivingUpdate();

		this.lightAI.setEnabled(this.getAttackTarget() == null);

		this.setMultiPartLocations();
	}

	private void setMultiPartLocations()
	{
		for (int i = 0; i < this.parts.length; i++)
		{
			this.old[i].set(this.parts[i].posX, this.parts[i].posY, this.parts[i].posZ);
		}

		float f = MathUtil.interpolateRotation(this.prevRenderYawOffset, this.renderYawOffset, 1);
		float f1 = MathHelper.cos(-f * 0.017453292F - (float) Math.PI);
		float f2 = MathHelper.sin(-f * 0.017453292F - (float) Math.PI);

		this.head.onUpdate();
		this.head.setLocationAndAngles(this.posX - f2, this.posY + .25f, this.posZ - f1, 0F, 0F);
		this.tail1.onUpdate();
		this.tail1.setLocationAndAngles(this.posX + f2 * 1.1f, this.posY + .25f, this.posZ + f1 * 1.1f, 0F, 0F);
		this.tail2.onUpdate();
		this.tail2.setLocationAndAngles(this.posX + f2 * 2f, this.posY, this.posZ + f1 * 2f, 0F, 0F);

		for (int i = 0; i < this.parts.length; i++)
		{
			this.parts[i].prevPosX = this.old[i].getX();
			this.parts[i].prevPosY = this.old[i].getY();
			this.parts[i].prevPosZ = this.old[i].getZ();
		}
	}

}
