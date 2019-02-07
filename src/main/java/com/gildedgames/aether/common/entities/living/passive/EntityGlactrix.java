package com.gildedgames.aether.common.entities.living.passive;

import com.gildedgames.aether.api.damage_system.DamageTypeAttributes;
import com.gildedgames.aether.api.effects_system.IAetherStatusEffects;
import com.gildedgames.aether.common.blocks.BlocksAether;
import com.gildedgames.aether.common.entities.ai.EntityAIHideFromTarget;
import com.gildedgames.aether.common.entities.ai.EntityAIWanderFavorBlocks;
import com.gildedgames.aether.common.entities.ai.glactrix.GlactrixAIHideFromEntity;
import com.gildedgames.aether.common.items.ItemsAether;
import com.gildedgames.aether.common.registry.content.LootTablesAether;
import com.gildedgames.aether.common.registry.content.SoundsAether;
import com.google.common.collect.Sets;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
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
	private EntityAIHideFromTarget runeAndHideAI = new EntityAIHideFromTarget(this, EntityPlayer.class, 0.8D);
	private GlactrixAIHideFromEntity glactrixHideAI = new GlactrixAIHideFromEntity(this, EntityPlayer.class);


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
		this.setSize(1f,1f);

		this.favoriteBlocks.add(BlocksAether.highlands_packed_ice);
		this.favoriteBlocks.add(BlocksAether.highlands_ice);
	}

	@Override
	protected void entityInit()
	{
		super.entityInit();
		this.dataManager.register(IS_HIDING, Boolean.FALSE);
		this.dataManager.register(IS_TOPPLED, Boolean.FALSE);
		this.dataManager.register(IS_SHEARED, Boolean.FALSE);
	}

	@Override
	protected void initEntityAI()
	{
		//this.tasks.addTask(this.hideAIPriority, this.glactrixHideAI);
		this.tasks.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
	}

	@Override
	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();

		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(.5D);
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(10.0D);

		this.getEntityAttribute(DamageTypeAttributes.SLASH_DEFENSE_LEVEL).setBaseValue(6);
		this.getEntityAttribute(DamageTypeAttributes.PIERCE_DEFENSE_LEVEL).setBaseValue(6);
		this.getEntityAttribute(DamageTypeAttributes.IMPACT_DEFENSE_LEVEL).setBaseValue(4);
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

				this.tasks.addTask(this.wanderAIPriority, this.wanderAI);

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

			this.tasks.addTask(this.hideAIPriority, this.runeAndHideAI);
			this.tasks.removeTask(this.glactrixHideAI);
		}
		else
		{
			this.tasks.removeTask(this.runeAndHideAI);
			this.tasks.addTask(this.hideAIPriority, this.glactrixHideAI);
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
	public EntityAgeable createChild(EntityAgeable ageable)
	{
		return new EntityGlactrix(this.world);
	}

	@Override
	public void setAttackTarget(@Nullable final EntityLivingBase entitylivingbaseIn)
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
		return SoundsAether.taegore_ambient;
	}

	@Override
	protected SoundEvent getHurtSound(final DamageSource src)
	{
		return SoundsAether.taegore_hurt;
	}

	@Override
	protected SoundEvent getDeathSound()
	{
		return SoundsAether.taegore_death;
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
	public boolean isShearable(@Nonnull ItemStack item, IBlockAccess world, BlockPos pos)
	{
		return !(this.getIsSheared()) && this.getIsToppled();
	}

	@Nonnull
	@Override
	public List<ItemStack> onSheared(@Nonnull ItemStack item, IBlockAccess world, BlockPos pos, int fortune)
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
	public void writeEntityToNBT(NBTTagCompound compound)
	{
		super.writeEntityToNBT(compound);
		compound.setInteger("glactrix_refreeze", this.timer);
		compound.setBoolean("glactrix_sheared", this.getIsSheared());
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound compound)
	{
		super.readEntityFromNBT(compound);
		this.timer = compound.getInteger("glactrix_refreeze");
		this.setSheared(compound.getBoolean("glactrix_sheared"));
	}
}
