package com.gildedgames.aether.common.entities.living.mobs;

import com.gildedgames.aether.common.blocks.BlocksAether;
import com.gildedgames.aether.common.entities.ai.*;
import com.gildedgames.aether.common.entities.ai.cockatrice.EntityAICockatriceHide;
import com.gildedgames.aether.common.entities.ai.cockatrice.EntityAICockatriceSneakAttack;
import com.gildedgames.aether.common.entities.ai.cockatrice.EntityAICockatriceWander;
import com.gildedgames.aether.common.registry.content.LootTablesAether;
import com.gildedgames.aether.common.registry.content.SoundsAether;
import com.gildedgames.aether.common.util.helpers.EntityUtil;
import net.minecraft.util.math.Vec3d;
import net.minecraft.block.Block;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class EntityVaranys extends EntityAetherMob implements IEntityMultiPart
{

	public MultiPartEntityPart[] parts;
	public MultiPartEntityPart head = new MultiPartEntityPart(this, "head", .7F, .7F);
	public MultiPartEntityPart tail1 = new MultiPartEntityPart(this, "tail", 1F, .7F);
	public MultiPartEntityPart tail2 = new MultiPartEntityPart(this, "tail2", 1F, .7F);

	private EntityAIHideFromLight lightAI;
	private	Vec3d[] old;

	public EntityVaranys(final World world)
	{
		super(world);

		this.lightAI = new EntityAIHideFromLight(this, 0.8F, 5);
		this.parts = new MultiPartEntityPart[] { head, tail1, tail2};

		this.tasks.addTask(0, new EntityAISwimming(this));
		this.tasks.addTask(0, new EntityAIUnstuckBlueAercloud(this));
		this.tasks.addTask(1, lightAI);
		this.tasks.addTask(1, new EntityAIWanderAvoidLight(this, 0.8D, 5));
		this.tasks.addTask(2, new EntityAILeapAtTarget(this, 0.4F));
		this.tasks.addTask(3, new EntityAIAttackMelee(this, 1D, false));
		this.tasks.addTask(4, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
		//		this.tasks.addTask(1, new EntityAIBreakBlocks(this, 1F, destroyList));
		this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false, new Class[0]));
		this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));

		this.setSize(1.4F, 1.2F);

		this.stepHeight = 1.0F;
		this.head.setInvisible(true);
		this.tail1.setInvisible(true);
		this.tail2.setInvisible(true);
		this.experienceValue = 7;

		this.old = new Vec3d[parts.length];
	}

	@Override
	protected void entityInit()
	{
		super.entityInit();
	}

	@Override
	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();

		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.4D);
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(26.0D);
		this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(2.0D);
		this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(2.0D);
	}

	@Override
	public boolean attackEntityFromPart(MultiPartEntityPart part, DamageSource source, float damage)
	{
		switch(part.partName)
		{
			case "head":
				damage *= 1.05f;
				break;

			default:
				damage *= .7f;
				break;
		}

		if (hurtResistantTime <= 10)
		{
			return attackEntityFrom(source, damage);
		}
		else
		{
			return false;
		}
	}

	@Override
	public World getWorld()
	{
		return getEntityWorld();
	}

	@Nullable
	@Override
	public MultiPartEntityPart[] getParts()
	{
		return parts;
	}

	@Override
	public boolean attackEntityAsMob(final Entity entity)
	{
		final boolean flag = super.attackEntityAsMob(entity);

		if (flag && entity instanceof EntityLivingBase)
		{
			final EntityLivingBase living = (EntityLivingBase) entity;

			if (!living.isActiveItemStackBlocking())
			{
				living.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 40));
			}
		}

		return flag;
	}

	@Override
	public void onLivingUpdate()
	{
		super.onLivingUpdate();

		lightAI.setEnabled(this.getAttackTarget() == null);

		for (int i = 0; i < parts.length; i++)
		{
			old[i] = new Vec3d(parts[i].posX, parts[i].posY, parts[i].posZ);
		}

		setMultiPartLocations();

		for (int i = 0; i < parts.length; i++)
		{
			parts[i].prevPosX = old[i].x;
			parts[i].prevPosY = old[i].y;
			parts[i].prevPosZ = old[i].z;
		}
	}

	private void setMultiPartLocations()
	{
		float f = MathHelper.cos(-rotationYaw * 0.017453292F - (float)Math.PI);
		float f1 = MathHelper.sin(-rotationYaw * 0.017453292F - (float)Math.PI);

		head.onUpdate();
		head.setLocationAndAngles(posX - f1, posY + .25f, posZ - f, 0F, 0F);
		tail1.onUpdate();
		tail1.setLocationAndAngles(posX + f1 * 1.1f, posY + .25f, posZ + f * 1.1f, 0F, 0F);
		tail2.onUpdate();
		tail2.setLocationAndAngles(posX + f1 * 2f, posY, posZ + f * 2f, 0F, 0F);
	}

}
