package com.gildedgames.aether.common.entities.living.passive;

import com.gildedgames.aether.common.blocks.BlocksAether;
import com.gildedgames.aether.common.items.ItemsAether;
import com.gildedgames.aether.common.registry.content.LootTablesAether;
import com.gildedgames.aether.common.registry.content.SoundsAether;
import com.google.common.collect.Sets;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.Set;

public class EntityTaegore extends EntityAetherAnimal
{

	private static final Set<Item> TEMPTATION_ITEMS = Sets.newHashSet(Items.WHEAT, ItemsAether.blueberries, ItemsAether.orange, ItemsAether.enchanted_blueberry, ItemsAether.enchanted_wyndberry, ItemsAether.wyndberry);
	private final EntityAIAttackMelee AIAttackMelee = new EntityAIAttackMelee(this, 2.0D, true);
	private final EntityAIPanic AIPanic = new EntityAIPanic(this, 2.0D);

	public EntityTaegore(World world)
	{
		super(world);

		this.setSize(1.25F, 1.25F);

		this.spawnableBlock = BlocksAether.aether_grass;
	}

	@Override
	protected void initEntityAI()
	{
		super.initEntityAI();

		this.tasks.addTask(3, new EntityAITempt(this, 1.2D, false, TEMPTATION_ITEMS));

		this.tasks.addTask(0, new EntityAISwimming(this));
		this.tasks.addTask(2, new EntityAIMate(this, 1.0D));
		this.tasks.addTask(3, new EntityAITempt(this, 1.2D, false, TEMPTATION_ITEMS));
		this.tasks.addTask(4, new EntityAIFollowParent(this, 1.25D));
		this.tasks.addTask(5, new EntityAIWander(this, 1.0D));
		this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
		this.tasks.addTask(7, new EntityAILookIdle(this));
	}

	@Override
	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();

		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(10.0D);
		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.2D);
	}

	@Override
	public void setAttackTarget(@Nullable EntityLivingBase entitylivingbaseIn)
	{
		super.setAttackTarget(entitylivingbaseIn);
	}

	@Override
	public EntityTaegore createChild(EntityAgeable ageable)
	{
		return new EntityTaegore(this.world);
	}

	@Override
	public boolean isBreedingItem(@Nullable ItemStack stack)
	{
		return stack != null && TEMPTATION_ITEMS.contains(stack.getItem());
	}

	@Override
	protected SoundEvent getAmbientSound()
	{
		return SoundsAether.taegore_ambient;
	}

	@Override
	protected SoundEvent getHurtSound()
	{
		return SoundsAether.taegore_hurt;
	}

	@Override
	protected SoundEvent getDeathSound()
	{
		return SoundsAether.taegore_death;
	}

	@Override
	protected ResourceLocation getLootTable()
	{
		return LootTablesAether.ENTITY_TAEGORE;
	}

	@Override
	public void onEntityUpdate()
	{
		super.onEntityUpdate();

		if (this.getAttackingEntity() != null)
		{
			this.tasks.addTask(3, this.AIAttackMelee);
			this.updateAITasks();
		}
		this.setAttackTarget(this.getAttackingEntity());

		if (this.getHealth() < 4F)
		{
			this.tasks.addTask(0, this.AIPanic);
			this.tasks.removeTask(this.AIAttackMelee);
			this.updateAITasks();
		}

	}

	@Override
	public boolean attackEntityAsMob(Entity entityIn)
	{
		entityIn.attackEntityFrom(DamageSource.causeMobDamage(this), 2.0F);
		this.playSound(this.getDeathSound(), 0.5F, 1.0F);
		return true;
	}

}
