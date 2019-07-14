package com.gildedgames.aether.common.entities.animals;

import com.gildedgames.aether.api.entity.IEntityEyesComponent;
import com.gildedgames.aether.api.entity.damage.DamageTypeAttributes;
import com.gildedgames.aether.api.entity.effects.EEffectIntensity;
import com.gildedgames.aether.api.entity.effects.IAetherStatusEffectIntensity;
import com.gildedgames.aether.api.entity.effects.IAetherStatusEffects;
import com.gildedgames.aether.api.registrar.ItemsAether;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.capabilities.entity.player.PlayerAether;
import com.gildedgames.aether.common.entities.EntityTypesAether;
import com.gildedgames.aether.common.entities.ai.AetherNavigateGround;
import com.gildedgames.aether.common.entities.ai.EntityAIHideFromRain;
import com.gildedgames.aether.common.entities.ai.EntityAIRestrictRain;
import com.gildedgames.aether.common.entities.ai.EntityAIUnstuckBlueAercloud;
import com.gildedgames.aether.common.entities.effects.StatusEffectFracture;
import com.gildedgames.aether.common.entities.util.eyes.EntityEyesComponent;
import com.gildedgames.aether.common.entities.util.eyes.IEntityEyesComponentProvider;
import com.gildedgames.aether.common.init.LootTablesAether;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class EntityTaegore extends EntityAetherAnimal implements IEntityEyesComponentProvider
{

	private final MeleeAttackGoal AIAttackMelee = new MeleeAttackGoal(this, 2.0D, true);

	private final PanicGoal AIPanic = new PanicGoal(this, 2.0D);

	private final IEntityEyesComponent eyes = new EntityEyesComponent(this);

	public EntityTaegore(EntityType<? extends AnimalEntity> type, World world)
	{
		super(type, world);
	}

	@Override
	protected void registerGoals()
	{
		super.registerGoals();

		this.goalSelector.addGoal(2, new EntityAIRestrictRain(this));
		this.goalSelector.addGoal(3, new EntityAIUnstuckBlueAercloud(this));
		this.goalSelector.addGoal(3, new EntityAIHideFromRain(this, 1.3D));
		this.goalSelector.addGoal(3, new TemptGoal(this, 1.2D, false, Ingredient.fromItems(ItemsAether.wyndberry)));
		this.goalSelector.addGoal(0, new SwimGoal(this));
		this.goalSelector.addGoal(2, new BreedGoal(this, 1.0D));
		this.goalSelector.addGoal(4, new FollowParentGoal(this, 1.25D));
		this.goalSelector.addGoal(5, new RandomWalkingGoal(this, 1.0D));
		this.goalSelector.addGoal(6, new LookAtGoal(this, PlayerEntity.class, 6.0F));
		this.goalSelector.addGoal(7, new LookRandomlyGoal(this));
	}

	@Override
	protected PathNavigator createNavigator(final World worldIn)
	{
		return new AetherNavigateGround(this, worldIn);
	}

	@Override
	public float getBlockPathWeight(BlockPos pos, IWorldReader reader)
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
		return new EntityTaegore(EntityTypesAether.TAEGORE, this.world);
	}

	@Override
	public boolean isBreedingItem(final ItemStack stack)
	{
		return stack.getItem() == ItemsAether.wyndberry;
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
	protected void playStepSound(final BlockPos pos, final BlockState blockIn)
	{
		this.playSound(SoundEvents.ENTITY_PIG_STEP, 0.15F, 1.0F);
	}

	@Override
	public ResourceLocation getLootTable()
	{
		return LootTablesAether.ENTITY_TAEGORE;
	}

	@Override
	public void livingTick()
	{
		super.livingTick();

		this.eyes.update();
	}

	@Override
	public void tick()
	{
		super.tick();

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
			this.goalSelector.removeGoal(this.AIAttackMelee);
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
				int buildup = IAetherStatusEffectIntensity.getBuildupFromEffect(new StatusEffectFracture(living), EEffectIntensity.MINOR) / 4;
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
