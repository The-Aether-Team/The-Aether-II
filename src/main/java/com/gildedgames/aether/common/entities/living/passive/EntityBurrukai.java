package com.gildedgames.aether.common.entities.living.passive;

import com.gildedgames.aether.api.AetherCapabilities;
import com.gildedgames.aether.api.damage_system.DamageTypeAttributes;
import com.gildedgames.aether.api.effects_system.IAetherStatusEffects;
import com.gildedgames.aether.common.blocks.BlocksAether;
import com.gildedgames.aether.common.capabilities.entity.player.PlayerAether;
import com.gildedgames.aether.common.entities.ai.AetherNavigateGround;
import com.gildedgames.aether.common.entities.ai.EntityAIHideFromRain;
import com.gildedgames.aether.common.entities.ai.EntityAIRestrictRain;
import com.gildedgames.aether.common.entities.ai.EntityAIUnstuckBlueAercloud;
import com.gildedgames.aether.common.entities.util.AetherMultiPartEntity;
import com.gildedgames.aether.common.items.ItemsAether;
import com.gildedgames.aether.common.registry.content.LootTablesAether;
import com.gildedgames.aether.common.registry.content.SoundsAether;
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

public class EntityBurrukai extends EntityAetherAnimal implements IEntityMultiPart
{

	private static final Set<Item> TEMPTATION_ITEMS = Sets.newHashSet(ItemsAether.brettl_grass);

	private final EntityAIAttackMelee AIAttackMelee = new EntityAIAttackMelee(this, 1.2D, false);

	private final MultiPartEntityPart[] parts;

	private final MultiPartEntityPart head = new AetherMultiPartEntity(this, "head", .8F, 1.1F);

	private double prevHeadX, prevHeadY, prevHeadZ;

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

		this.tasks.addTask(2, new EntityAIRestrictRain(this));
		this.tasks.addTask(3, new EntityAIHideFromRain(this, 1.3D));
		this.tasks.addTask(3, new EntityAIUnstuckBlueAercloud(this));
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

		this.prevHeadX = this.head.posX;
		this.prevHeadY = this.head.posY;
		this.prevHeadZ = this.head.posZ;

		final float headDist = 1.2f;
		float f = MathUtil.interpolateRotation(this.prevRenderYawOffset, this.renderYawOffset, 1);
		float f1 = MathHelper.cos(-f * 0.017453292F - (float) Math.PI) * headDist;
		float f2 = MathHelper.sin(-f * 0.017453292F - (float) Math.PI) * headDist;

		this.head.setLocationAndAngles(this.posX - f2, this.posY + .7f, this.posZ - f1, 0F, 0F);
		this.head.onUpdate();

		this.head.prevPosX = this.prevHeadX;
		this.head.prevPosY = this.prevHeadY;
		this.head.prevPosZ = this.prevHeadZ;
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

		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(15.0D);
		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.2D);
		this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(16.0D);
		this.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(8.0);

		this.getEntityAttribute(DamageTypeAttributes.SLASH_DEFENSE_LEVEL).setBaseValue(8);
		this.getEntityAttribute(DamageTypeAttributes.PIERCE_DEFENSE_LEVEL).setBaseValue(4);
		this.getEntityAttribute(DamageTypeAttributes.IMPACT_DEFENSE_LEVEL).setBaseValue(8);
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

		if (this.getAttackingEntity() != null)
		{
			if (this.getAttackingEntity() instanceof EntityPlayer)
			{
				final PlayerAether player = PlayerAether.getPlayer(this.getAttackingEntity());
				if (player.getEntity().isCreative())
				{
					return;
				}
			}

			this.tasks.addTask(3, this.AIAttackMelee);
			this.updateAITasks();

			if (this.getDistance(this.getAttackingEntity()) > this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).getAttributeValue())
			{
				this.tasks.removeTask(this.AIAttackMelee);
				this.updateAITasks();
			}
		}

		this.setAttackTarget(this.getAttackingEntity());
	}

	@Override
	public boolean attackEntityAsMob(final Entity entityIn)
	{
		if (entityIn instanceof EntityLivingBase)
		{
			final EntityLivingBase living = (EntityLivingBase) entityIn;

			living.attackEntityFrom(DamageSource.causeMobDamage(this), 5.0F);
			this.playSound(SoundsAether.burrukai_attack, 0.5F, 1.0F);
			living.knockBack(this, 1.0F, 0.2D, 0.2D);

			this.applyStatusEffectOnAttack(entityIn);
		}

		return true;
	}

	private void applyStatusEffectOnAttack(final Entity target)
	{
		if (target instanceof EntityLivingBase)
		{
			final EntityLivingBase living = (EntityLivingBase) target;

			if (!living.isActiveItemStackBlocking())
			{
				if (target.hasCapability(AetherCapabilities.STATUS_EFFECT_POOL, null))
				{
					target.getCapability(AetherCapabilities.STATUS_EFFECT_POOL, null).applyStatusEffect(IAetherStatusEffects.effectTypes.FRACTURE, 30);
					target.getCapability(AetherCapabilities.STATUS_EFFECT_POOL, null).applyStatusEffect(IAetherStatusEffects.effectTypes.STUN, 70);
				}
			}
		}
	}

}
