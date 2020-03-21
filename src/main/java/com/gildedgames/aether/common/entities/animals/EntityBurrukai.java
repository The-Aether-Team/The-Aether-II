package com.gildedgames.aether.common.entities.animals;

import com.gildedgames.aether.api.entity.IEntityEyesComponent;
import com.gildedgames.aether.api.entity.damage.DamageTypeAttributes;
import com.gildedgames.aether.api.entity.effects.EEffectIntensity;
import com.gildedgames.aether.api.entity.effects.IAetherStatusEffectIntensity;
import com.gildedgames.aether.api.entity.effects.IAetherStatusEffects;
import com.gildedgames.aether.api.registrar.BlocksAether;
import com.gildedgames.aether.api.registrar.ItemsAether;
import com.gildedgames.aether.api.registrar.SoundsAether;
import com.gildedgames.aether.common.entities.ai.*;
import com.gildedgames.aether.common.entities.effects.StatusEffectFracture;
import com.gildedgames.aether.common.entities.multipart.AetherMultiPartEntity;
import com.gildedgames.aether.common.entities.util.eyes.EntityEyesComponent;
import com.gildedgames.aether.common.entities.util.eyes.IEntityEyesComponentProvider;
import com.gildedgames.aether.common.init.LootTablesAether;
import com.gildedgames.aether.common.util.helpers.MathUtil;
import com.google.common.collect.Sets;
import net.minecraft.block.Block;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.Set;

public class EntityBurrukai extends EntityAetherAnimal implements IEntityMultiPart, IEntityEyesComponentProvider
{

	private static final Set<Item> TEMPTATION_ITEMS = Sets.newHashSet(ItemsAether.brettl_grass);

	private final MultiPartEntityPart[] parts;

	private final MultiPartEntityPart head = new AetherMultiPartEntity(this, "head", .8F, 1.1F);

	private EntityAIRamAttack ramAttack;

	private final IEntityEyesComponent eyes = new EntityEyesComponent(this);

	public EntityBurrukai(final World world)
	{
		super(world);

		this.parts = new MultiPartEntityPart[] { this.head };
		this.setSize(1.5F, 1.9F);

		this.spawnableBlock = BlocksAether.aether_grass;
	}

	@Override
	protected void initEntityAI()
	{
		super.initEntityAI();

		this.ramAttack = new EntityAIRamAttack(this, 0.5D, 0.5f, 2, 16.0f);

		this.tasks.addTask(2, this.ramAttack);
		this.tasks.addTask(2, new EntityAIRestrictRain(this));
		this.tasks.addTask(3, new EntityAIHideFromRain(this, 1.3D));
		this.tasks.addTask(3, new EntityAIUnstuckBlueAercloud(this));
		this.tasks.addTask(3, new EntityAITempt(this, 1.2D, false, TEMPTATION_ITEMS));
		this.tasks.addTask(0, new EntityAISwimming(this));
		this.tasks.addTask(2, new EntityAIMate(this, 1.0D));
		this.tasks.addTask(3, new EntityAITempt(this, 1.2D, false, TEMPTATION_ITEMS));
		this.tasks.addTask(4, new EntityAIFollowParent(this, 1.25D));
		this.tasks.addTask(5, new EntityAIWander(this, 1.0D));
		this.tasks.addTask(5, new EntityAIWanderAvoidWater(this, 1.0D));
		this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
		this.tasks.addTask(7, new EntityAILookIdle(this));
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
	public void onLivingUpdate()
	{
		super.onLivingUpdate();

		this.eyes.update();

		double prevHeadX = this.head.posX;
		double prevHeadY = this.head.posY;
		double prevHeadZ = this.head.posZ;

		final float headDist = 1.2f;
		float f = MathUtil.interpolateRotation(this.prevRenderYawOffset, this.renderYawOffset, 1);
		float f1 = MathHelper.cos(-f * 0.017453292F - (float) Math.PI) * headDist;
		float f2 = MathHelper.sin(-f * 0.017453292F - (float) Math.PI) * headDist;

		this.head.setLocationAndAngles(this.posX - f2, this.posY + .7f, this.posZ - f1, 0F, 0F);
		this.head.onUpdate();

		this.head.prevPosX = prevHeadX;
		this.head.prevPosY = prevHeadY;
		this.head.prevPosZ = prevHeadZ;
	}

	@Override
	protected PathNavigate createNavigator(final World worldIn)
	{
		return new AetherNavigateGround(this, worldIn);
	}

	@Override
	public float getBlockPathWeight(BlockPos pos)
	{
		return super.getBlockPathWeight(pos);
	}

	@Override
	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();

		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(20.0D);
		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.2D);
		this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(16.0D);

		this.getEntityAttribute(DamageTypeAttributes.SLASH_DEFENSE_LEVEL).setBaseValue(25);
		this.getEntityAttribute(DamageTypeAttributes.IMPACT_DEFENSE_LEVEL).setBaseValue(25);
		this.getEntityAttribute(DamageTypeAttributes.PIERCE_DEFENSE_LEVEL).setBaseValue(38);
	}

	@Override
	public EntityBurrukai createChild(final EntityAgeable ageable)
	{
		return new EntityBurrukai(this.world);
	}

	@Override
	public boolean isBreedingItem(@Nullable final ItemStack stack)
	{
		return stack != null && TEMPTATION_ITEMS.contains(stack.getItem());
	}

	@Override
	protected SoundEvent getAmbientSound()
	{
		return SoundsAether.burrukai_ambient;
	}

	@Override
	protected SoundEvent getHurtSound(final DamageSource src)
	{
		return SoundsAether.burrukai_hurt;
	}

	@Override
	protected SoundEvent getDeathSound()
	{
		return SoundsAether.burrukai_death;
	}

	@Override
	protected float getSoundVolume()
	{
		return 0.6F;
	}

	@Override
	protected void playStepSound(final BlockPos pos, final Block blockIn)
	{
		this.playSound(SoundEvents.ENTITY_COW_STEP, 0.15F, 1.0F);
	}

	@Override
	protected ResourceLocation getLootTable()
	{
		return LootTablesAether.ENTITY_BURRUKAI;
	}

	@Override
	public void onEntityUpdate()
	{
		super.onEntityUpdate();

		if (this.ramAttack == null)
		{
			return;
		}

//		if (this.getAttackTarget() != null)
//		{
//			if (this.getAttackTarget() instanceof EntityPlayer)
//			{
//				final PlayerAether player = PlayerAether.getPlayer(this.getAttackTarget());
//				if (player.getEntity().isCreative())
//				{
//					return;
//				}
//			}
//		}

		this.ramAttack.update();

		this.setAttackTarget(this.getAttackTarget());
	}

	@Override
	public boolean attackEntityFrom(DamageSource source, float amount)
	{
		if (source.getTrueSource() instanceof EntityLivingBase)
		{
			EntityLivingBase attacker = (EntityLivingBase) source.getTrueSource();

			this.setAttackTarget(attacker);

			if (this.ramAttack != null && (!(attacker instanceof EntityPlayer) || !((EntityPlayer) attacker).isCreative()))
			{
				this.ramAttack.setTarget(attacker);
			}
		}

		return super.attackEntityFrom(source, amount);
	}

	@Override
	public boolean attackEntityAsMob(final Entity entityIn)
	{
		if (entityIn instanceof EntityLivingBase)
		{
			final EntityLivingBase living = (EntityLivingBase) entityIn;

			double xRatio = this.getLook(1.0F).x * 360;
			double zRatio = this.getLook(1.0F).z * 360;

			living.attackEntityFrom(DamageSource.causeMobDamage(this), 5.0F);
			this.playSound(SoundsAether.burrukai_attack, 0.5F, 1.0F);
			living.knockBack(this, 1.0F, xRatio * -1, zRatio * -1);

			this.applyStatusEffectOnAttack(entityIn);
			this.ramAttack.setTarget(this.getAttackTarget());
			this.setAttackTarget(null);
		}

		return true;
	}

	@Override
	protected void applyStatusEffectOnAttack(final Entity target)
	{
		if (target instanceof EntityLivingBase)
		{
			final EntityLivingBase living = (EntityLivingBase) target;

			if (!living.isActiveItemStackBlocking())
			{
				int buildup = IAetherStatusEffectIntensity.getBuildupFromEffect(new StatusEffectFracture(living), EEffectIntensity.ORDINARY);
				IAetherStatusEffects.applyStatusEffect(living, IAetherStatusEffects.effectTypes.FRACTURE, buildup);
			}
			else
			{
				int buildup = IAetherStatusEffectIntensity.getBuildupFromEffect(new StatusEffectFracture(living), EEffectIntensity.MINOR);
				IAetherStatusEffects.applyStatusEffect(living, IAetherStatusEffects.effectTypes.FRACTURE, buildup);
			}
		}
	}

	@Override
	public IEntityEyesComponent getEyes()
	{
		return this.eyes;
	}

	@Override
	public void knockBack(Entity entityIn, float strength, double xRatio, double zRatio)
	{
		super.knockBack(entityIn, strength * 0.2f, xRatio, zRatio);
	}
}
