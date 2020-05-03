package com.gildedgames.aether.common.entities.monsters;

import com.gildedgames.aether.api.entity.damage.DamageTypeAttributes;
import com.gildedgames.aether.api.entity.effects.EEffectIntensity;
import com.gildedgames.aether.api.entity.effects.IAetherStatusEffectIntensity;
import com.gildedgames.aether.api.entity.effects.IAetherStatusEffects;
import com.gildedgames.aether.common.entities.ai.EntityAIHideFromLight;
import com.gildedgames.aether.common.entities.ai.EntityAIUnstuckBlueAercloud;
import com.gildedgames.aether.common.entities.ai.EntityAIWanderAvoidLight;
import com.gildedgames.aether.common.entities.effects.StatusEffectCockatriceVenom;
import com.gildedgames.aether.common.entities.effects.StatusEffectFreeze;
import com.gildedgames.aether.common.entities.multipart.AetherMultiPartEntity;
import com.gildedgames.aether.common.util.helpers.MathUtil;
import com.google.common.collect.Maps;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import javax.vecmath.Point3d;
import java.util.Map;

public class EntityVaranys extends EntityAetherMob implements IEntityMultiPart
{
	protected Map<String, Float> defenseMap = Maps.newHashMap();
	{{
		this.defenseMap.put("Very Weak", 2.0F);
		this.defenseMap.put("Weak", 1.0F);
		this.defenseMap.put("Average", 0.0F);
		this.defenseMap.put("Strong", -1.0F);
		this.defenseMap.put("Very Strong", -2.0F);
	}}

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

		this.tasks.addTask(0, new EntityAISwimming(this));
		this.tasks.addTask(0, new EntityAIUnstuckBlueAercloud(this));
		this.tasks.addTask(1, this.lightAI);
		this.tasks.addTask(1, new EntityAIWanderAvoidLight(this, 0.8D, 5));
		this.tasks.addTask(2, new EntityAILeapAtTarget(this, 0.4F));
		this.tasks.addTask(3, new EntityAIFleeSun(this, 1.0D));
		this.tasks.addTask(3, new EntityAIAttackMelee(this, 1D, false));
		this.tasks.addTask(4, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
		this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
		this.targetTasks.addTask(2, new EntityAINearestAttackableTarget<>(this, EntityPlayer.class, true));

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
	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();

		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.4D);
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(24.0D);
		this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(2.0D);

		this.getEntityAttribute(DamageTypeAttributes.SLASH_DEFENSE_LEVEL).setBaseValue(0.0f);
		this.getEntityAttribute(DamageTypeAttributes.IMPACT_DEFENSE_LEVEL).setBaseValue(-1.0f);
		this.getEntityAttribute(DamageTypeAttributes.PIERCE_DEFENSE_LEVEL).setBaseValue(1.0f);
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

		if (flag && entity instanceof EntityLivingBase)
		{
			this.applyStatusEffectOnAttack(entity);
		}

		return flag;
	}

	@Override
	protected void applyStatusEffectOnAttack(final Entity target)
	{
		if (target instanceof EntityLivingBase)
		{
			final EntityLivingBase living = (EntityLivingBase) target;

			if (!living.isActiveItemStackBlocking())
			{
				int buildup = IAetherStatusEffectIntensity.getBuildupFromEffect(new StatusEffectFreeze(living), EEffectIntensity.MAJOR);
				IAetherStatusEffects.applyStatusEffect(living, IAetherStatusEffects.effectTypes.FREEZE, buildup);
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
