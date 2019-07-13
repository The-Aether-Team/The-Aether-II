package com.gildedgames.aether.common.entities.animals;

import com.gildedgames.aether.api.entity.damage.DamageTypeAttributes;
import com.gildedgames.aether.api.entity.effects.IAetherStatusEffects;
import com.gildedgames.aether.api.registrar.BlocksAether;
import com.gildedgames.aether.api.registrar.ItemsAether;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.entities.ai.EntityAIHideFromTarget;
import com.gildedgames.aether.common.entities.ai.EntityAIWanderFavorBlocks;
import com.gildedgames.aether.common.entities.ai.glactrix.GlactrixAIHideFromEntity;
import com.gildedgames.aether.common.init.LootTablesAether;
import com.google.common.collect.Sets;
import net.minecraft.block.Block;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.common.IShearable;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class EntityGlactrix extends EntityAetherAnimal implements IShearable
{
	private static final Set<Item> TEMPTATION_ITEMS = Sets.newHashSet(ItemsAether.wyndberry);

	private ArrayList<Block> favoriteBlocks = new ArrayList<>();

	private EntityAIWanderFavorBlocks wanderAI = new EntityAIWanderFavorBlocks(this, 0.3D, this.favoriteBlocks);
	private EntityAIHideFromTarget runeAndHideAI = new EntityAIHideFromTarget(this, PlayerEntity.class, 0.8D);
	private GlactrixAIHideFromEntity glactrixHideAI = new GlactrixAIHideFromEntity(this, PlayerEntity.class);


	private final int wanderAIPriority = 5;
	private final int hideAIPriority = 1;

	private boolean isHiding = false;		// Pertains specifically to "hiding under shell", not if the mob is hiding in general.
	private boolean isToppled = false;
	private boolean isSheared = false;		// Whether crystals are present.

	private static final DataParameter<Boolean> IS_HIDING = EntityDataManager.createKey(EntityGlactrix.class, DataSerializers.BOOLEAN);
	private static final DataParameter<Boolean> IS_TOPPLED = EntityDataManager.createKey(EntityGlactrix.class, DataSerializers.BOOLEAN);
	private static final DataParameter<Boolean> IS_SHEARED = EntityDataManager.createKey(EntityGlactrix.class, DataSerializers.BOOLEAN);

	private int timer;
	private final int timerLimit = 2400; // 2 minutes

	public EntityGlactrix(World world)
	{
		super(world);
		this.setSize(.65f,.5f);

		this.favoriteBlocks.add(BlocksAether.highlands_packed_ice);
		this.favoriteBlocks.add(BlocksAether.highlands_ice);
	}

	@Override
	protected void registerData()
	{
		super.registerData();
		this.dataManager.register(IS_HIDING, Boolean.FALSE);
		this.dataManager.register(IS_TOPPLED, Boolean.FALSE);
		this.dataManager.register(IS_SHEARED, Boolean.FALSE);
	}

	@Override
	protected void registerGoals()
	{
		//this.goalSelector.addGoal(this.hideAIPriority, this.glactrixHideAI);
		this.goalSelector.addGoal(7, new LookAtGoal(this, PlayerEntity.class, 6.0F));
	}

	@Override
	protected void registerAttributes()
	{
		super.registerAttributes();

		this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(.5D);
		this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(10.0D);

		this.getAttribute(DamageTypeAttributes.SLASH_DEFENSE_LEVEL).setBaseValue(6);
		this.getAttribute(DamageTypeAttributes.PIERCE_DEFENSE_LEVEL).setBaseValue(6);
		this.getAttribute(DamageTypeAttributes.IMPACT_DEFENSE_LEVEL).setBaseValue(4);
	}

	public void setHiding(boolean isHiding)
	{
		this.isHiding = isHiding;
		this.dataManager.set(IS_HIDING, isHiding);
	}

	public void setToppled(boolean isToppled)
	{
		this.isToppled = isToppled;
		this.dataManager.set(IS_TOPPLED, isToppled);
	}

	public void setSheared(boolean isSheared)
	{
		this.isSheared = isSheared;
		this.dataManager.set(IS_SHEARED, isSheared);
	}

	@Override
	public void onEntityUpdate()
	{
		super.onEntityUpdate();


		// only allow hiding when ice crystal's are present.
		if (!this.getIsSheared())
		{
			if (this.getIsHiding())
			{
				this.setEntityInvulnerable(true);
				this.tasks.removeTask(this.wanderAI);
			}
			else
			{
				this.setEntityInvulnerable(false);

				this.goalSelector.addGoal(this.wanderAIPriority, this.wanderAI);

				// apply status effect on injury.
				if (this.hurtTime > 0)
				{
					IAetherStatusEffects.applyStatusEffect(this, IAetherStatusEffects.effectTypes.STUN, 100);
					this.setToppled(true);
				}
			}
		}

		if (this.getIsToppled())
		{
			this.setHiding(false);
		}

		// check for buildup instead of if the effect is applied, this is because it may take a few frames for an effect to apply,
		// due to potential buildups.
		if (!IAetherStatusEffects.doesEffectHaveBuildup(this, IAetherStatusEffects.effectTypes.STUN))
		{
			this.setToppled(false);
		}

		if (this.getIsSheared())
		{
			++this.timer;

			if (this.timer > this.timerLimit && !this.world.isDaytime())
			{
				this.setSheared(false);
				this.timer = 0;
			}

			this.goalSelector.addGoal(this.hideAIPriority, this.runeAndHideAI);
			this.tasks.removeTask(this.glactrixHideAI);
		}
		else
		{
			this.tasks.removeTask(this.runeAndHideAI);
			this.goalSelector.addGoal(this.hideAIPriority, this.glactrixHideAI);
		}
	}

	public boolean getIsToppled()
	{
		return this.dataManager.get(IS_TOPPLED);
	}

	public boolean getIsHiding()
	{
		return this.dataManager.get(IS_HIDING);
	}

	public boolean getIsSheared()
	{
		return this.dataManager.get(IS_SHEARED);
	}

	@Override
	public void onLivingUpdate()
	{
		super.onLivingUpdate();
	}

	@Nullable
	@Override
	public AgeableEntity createChild(AgeableEntity ageable)
	{
		return new EntityGlactrix(this.world);
	}

	@Override
	public void setAttackTarget(@Nullable final LivingEntity entitylivingbaseIn)
	{
		super.setAttackTarget(entitylivingbaseIn);
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
		return LootTablesAether.ENTITY_GLACTRIX;
	}

	@Override
	public boolean isShearable(@Nonnull ItemStack item, IBlockReader world, BlockPos pos)
	{
		return !(this.getIsSheared()) && this.getIsToppled();
	}

	@Nonnull
	@Override
	public List<ItemStack> onSheared(@Nonnull ItemStack item, IBlockReader world, BlockPos pos, int fortune)
	{
		this.setSheared(true);
		this.setToppled(false);
		this.timer = 0;

		int count = 2 + this.rand.nextInt(3);
		List<ItemStack> ret = new ArrayList<>();

		for (int i = 0; i < count; i++)
		{
			ret.add(new ItemStack(ItemsAether.icestone));
		}

		this.playSound(SoundEvents.ENTITY_SHEEP_SHEAR, 1.0F, 1.0F);

		return ret;
	}

	@Override
	public void writeEntityToNBT(CompoundNBT compound)
	{
		super.writeEntityToNBT(compound);
		compound.putInt("glactrix_refreeze", this.timer);
		compound.putBoolean("glactrix_sheared", this.getIsSheared());
	}

	@Override
	public void readEntityFromNBT(CompoundNBT compound)
	{
		super.readEntityFromNBT(compound);
		this.timer = compound.getInt("glactrix_refreeze");
		this.setSheared(compound.getBoolean("glactrix_sheared"));
	}
}
