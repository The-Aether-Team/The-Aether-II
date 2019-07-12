package com.gildedgames.aether.common.entities.animals;

import com.gildedgames.aether.api.entity.IEntityEyesComponent;
import com.gildedgames.aether.api.entity.damage.DamageTypeAttributes;
import com.gildedgames.aether.api.entity.effects.EEffectIntensity;
import com.gildedgames.aether.api.entity.effects.IAetherStatusEffectIntensity;
import com.gildedgames.aether.api.entity.effects.IAetherStatusEffects;
import com.gildedgames.aether.api.registrar.BlocksAether;
import com.gildedgames.aether.api.registrar.ItemsAether;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.capabilities.entity.player.PlayerAether;
import com.gildedgames.aether.common.entities.ai.AetherNavigateGround;
import com.gildedgames.aether.common.entities.ai.EntityAIHideFromRain;
import com.gildedgames.aether.common.entities.ai.EntityAIRestrictRain;
import com.gildedgames.aether.common.entities.ai.EntityAIUnstuckBlueAercloud;
import com.gildedgames.aether.common.entities.effects.StatusEffectFracture;
import com.gildedgames.aether.common.entities.multipart.AetherMultiPartEntity;
import com.gildedgames.aether.common.entities.util.eyes.EntityEyesComponent;
import com.gildedgames.aether.common.entities.util.eyes.IEntityEyesComponentProvider;
import com.gildedgames.aether.common.init.LootTablesAether;
import com.gildedgames.aether.common.util.helpers.MathUtil;
import com.google.common.collect.Sets;
import net.minecraft.block.Block;
import net.minecraft.entity.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.util.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.Set;

public class EntityTaegore extends EntityAetherAnimal implements IEntityMultiPart, IEntityEyesComponentProvider
{

	private static final Set<Item> TEMPTATION_ITEMS = Sets.newHashSet(ItemsAether.wyndberry);

	private final EntityAIAttackMelee AIAttackMelee = new EntityAIAttackMelee(this, 2.0D, true);

	private final EntityAIPanic AIPanic = new EntityAIPanic(this, 2.0D);

	private final MultiPartEntityPart[] parts;

	private final MultiPartEntityPart head = new AetherMultiPartEntity(this, "head", .8F, .8F);

	private final IEntityEyesComponent eyes = new EntityEyesComponent(this);

	private double prevHeadX, prevHeadY, prevHeadZ;

	public EntityTaegore(final World world)
	{
		super(world);

		this.setSize(1.25F, 1.25F);

		this.parts = new MultiPartEntityPart[] { this.head };
		this.spawnableBlock = BlocksAether.aether_grass;
	}

	@Override
	protected void registerGoals()
	{
		super.registerGoals();

		this.goalSelector.addGoal(2, new EntityAIRestrictRain(this));
		this.goalSelector.addGoal(3, new EntityAIUnstuckBlueAercloud(this));
		this.goalSelector.addGoal(3, new EntityAIHideFromRain(this, 1.3D));
		this.goalSelector.addGoal(3, new EntityAITempt(this, 1.2D, false, TEMPTATION_ITEMS));
		this.goalSelector.addGoal(0, new EntityAISwimming(this));
		this.goalSelector.addGoal(2, new EntityAIMate(this, 1.0D));
		this.goalSelector.addGoal(3, new EntityAITempt(this, 1.2D, false, TEMPTATION_ITEMS));
		this.goalSelector.addGoal(4, new EntityAIFollowParent(this, 1.25D));
		this.goalSelector.addGoal(5, new EntityAIWander(this, 1.0D));
		this.goalSelector.addGoal(6, new EntityAIWatchClosest(this, PlayerEntity.class, 6.0F));
		this.goalSelector.addGoal(7, new EntityAILookIdle(this));
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
	protected PathNavigator createNavigator(final World worldIn)
	{
		return new AetherNavigateGround(this, worldIn);
	}

	@Override
	public float getBlockPathWeight(BlockPos pos)
	{
		return super.getBlockPathWeight(pos);
	}

	@Override
	protected void registerAttributes()
	{
		super.registerAttributes();

		this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(10.0D);
		this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.2D);

		this.getAttribute(DamageTypeAttributes.SLASH_DEFENSE_LEVEL).setBaseValue(8);
		this.getAttribute(DamageTypeAttributes.PIERCE_DEFENSE_LEVEL).setBaseValue(5);
		this.getAttribute(DamageTypeAttributes.IMPACT_DEFENSE_LEVEL).setBaseValue(2);
	}

	@Override
	public void setAttackTarget(@Nullable final LivingEntity entitylivingbaseIn)
	{
		super.setAttackTarget(entitylivingbaseIn);
	}

	@Override
	public EntityTaegore createChild(final AgeableEntity ageable)
	{
		return new EntityTaegore(this.world);
	}

	@Override
	public boolean isBreedingItem(@Nullable final ItemStack stack)
	{
		return stack != null && TEMPTATION_ITEMS.contains(stack.getItem());
	}

	@Override
	protected SoundEvent getAmbientSound()
	{
		return new SoundEvent(AetherCore.getResource("mob.taegore.ambient"));
	}

	@Override
	protected SoundEvent getHurtSound(final DamageSource src)
	{
		return new SoundEvent(AetherCore.getResource("mob.taegore.hurt"));
	}

	@Override
	protected SoundEvent getDeathSound()
	{
		return new SoundEvent(AetherCore.getResource("mob.taegore.death"));
	}

	@Override
	protected void playStepSound(final BlockPos pos, final Block blockIn)
	{
		this.playSound(SoundEvents.ENTITY_PIG_STEP, 0.15F, 1.0F);
	}

	@Override
	protected ResourceLocation getLootTable()
	{
		return LootTablesAether.ENTITY_TAEGORE;
	}

	@Override
	public void onLivingUpdate()
	{
		super.onLivingUpdate();

		this.eyes.update();

		this.prevHeadX = this.head.posX;
		this.prevHeadY = this.head.posY;
		this.prevHeadZ = this.head.posZ;

		final float headDist = 1.05f;
		float f = MathUtil.interpolateRotation(this.prevRenderYawOffset, this.renderYawOffset, 1);
		float f1 = MathHelper.cos(-f * 0.017453292F - (float) Math.PI) * headDist;
		float f2 = MathHelper.sin(-f * 0.017453292F - (float) Math.PI) * headDist;

		this.head.setLocationAndAngles(this.posX - f2, this.posY + .4f, this.posZ - f1, 0F, 0F);
		this.head.onUpdate();

		this.head.prevPosX = this.prevHeadX;
		this.head.prevPosY = this.prevHeadY;
		this.head.prevPosZ = this.prevHeadZ;
	}

	@Override
	public void onEntityUpdate()
	{
		super.onEntityUpdate();

		if (this.getAttackingEntity() != null)
		{
			if (this.getAttackingEntity() instanceof PlayerEntity)
			{
				final PlayerAether player = PlayerAether.getPlayer((PlayerEntity) this.getAttackingEntity());
				if (player.getEntity().isCreative())
				{
					return;
				}
			}
			this.goalSelector.addGoal(3, this.AIAttackMelee);
			this.updateAITasks();
		}
		this.setAttackTarget(this.getAttackingEntity());

		if (this.getHealth() < 4F)
		{
			this.goalSelector.addGoal(0, this.AIPanic);
			this.tasks.removeTask(this.AIAttackMelee);
			this.updateAITasks();
		}

	}

	@Override
	public boolean attackEntityAsMob(final Entity entityIn)
	{
		entityIn.attackEntityFrom(DamageSource.causeMobDamage(this), 2.0F);


		this.applyStatusEffectOnAttack(entityIn);

		this.playSound(new SoundEvent(AetherCore.getResource("mob.taegore.attack")), 0.5F, 1.0F);
		return true;
	}

	@Override
	protected void applyStatusEffectOnAttack(final Entity target)
	{
		if (target instanceof LivingEntity)
		{
			final LivingEntity living = (LivingEntity) target;

			if (!living.isActiveItemStackBlocking())
			{
				int buildup = IAetherStatusEffectIntensity.getBuildupFromEffect(new StatusEffectFracture(living), EEffectIntensity.MINOR);
				IAetherStatusEffects.applyStatusEffect(living, IAetherStatusEffects.effectTypes.FRACTURE, buildup);
			}
			else
			{
				int buildup = IAetherStatusEffectIntensity.getBuildupFromEffect(new StatusEffectFracture(living), EEffectIntensity.MINOR)/4;
				IAetherStatusEffects.applyStatusEffect(living, IAetherStatusEffects.effectTypes.FRACTURE, buildup);
			}
		}
	}

	@Override
	public IEntityEyesComponent getEyes()
	{
		return this.eyes;
	}
}
