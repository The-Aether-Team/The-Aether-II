package com.gildedgames.aether.common.entities.flying;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.*;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.AxeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.Difficulty;
import net.minecraft.world.World;

public class EntityFlyingMob extends EntityFlying implements IMob
{

	private static final DataParameter<Boolean> ATTACKED = EntityDataManager.createKey(EntityFlyingMob.class, DataSerializers.BOOLEAN);

	protected EntityFlyingMob(EntityType<? extends CreatureEntity> type, World world)
	{
		super(type, world);
	}

	@Override
	protected PathNavigator createNavigator(final World worldIn)
	{
		PathNavigateFlyer navigateFlyer = new PathNavigateFlyer(this, worldIn);

		navigateFlyer.setAvoidSun(true);

		return navigateFlyer;
	}

	protected void handleClientAttack()
	{

	}

	@Override
	public void tick()
	{
		super.tick();

		if (this.hasAttacked() && this.world.isRemote())
		{
			this.handleClientAttack();

			this.setAttacked(false);
		}

		if (!this.world.isRemote() && this.world.getDifficulty() == Difficulty.PEACEFUL)
		{
			this.remove();
		}
	}

	@Override
	protected void registerData()
	{
		super.registerData();

		this.dataManager.register(EntityFlyingMob.ATTACKED, Boolean.FALSE);
	}

	public void setAttacked(final boolean flag)
	{
		this.dataManager.set(EntityFlyingMob.ATTACKED, flag);
	}

	public boolean hasAttacked()
	{
		return this.dataManager.get(EntityFlyingMob.ATTACKED);
	}

	@Override
	public void livingTick()
	{
		this.updateArmSwingProgress();
		this.ageInSunlight();

		super.livingTick();
	}

	@Override
	public boolean attackEntityAsMob(final Entity entityIn)
	{
		this.setAttacked(true);

		float f = (float) this.getAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getValue();
		int i = 0;

		if (entityIn instanceof LivingEntity)
		{
			f += EnchantmentHelper.getModifierForCreature(this.getHeldItemMainhand(), ((LivingEntity) entityIn).getCreatureAttribute());
			i += EnchantmentHelper.getKnockbackModifier(this);
		}

		final boolean flag = entityIn.attackEntityFrom(DamageSource.causeMobDamage(this), f);

		if (flag)
		{
			if (i > 0)
			{
				((LivingEntity) entityIn).knockBack(this,
						(float) i * 0.5F, (double) MathHelper.sin(this.rotationYaw * 0.017453292F), (double) (-MathHelper.cos(
								this.rotationYaw * 0.017453292F)));

				this.setMotion(this.getMotion().mul(0.6D, 1.0D, 0.6D));
			}

			final int j = EnchantmentHelper.getFireAspectModifier(this);

			if (j > 0)
			{
				entityIn.setFire(j * 4);
			}

			if (entityIn instanceof PlayerEntity)
			{
				final PlayerEntity player = (PlayerEntity) entityIn;
				final ItemStack heldItem = this.getHeldItemMainhand();
				final ItemStack activeStack = player.isHandActive() ? player.getActiveItemStack() : ItemStack.EMPTY;

				if (heldItem.getItem() instanceof AxeItem && activeStack.getItem() == Items.SHIELD)
				{
					final float f1 = 0.25F + (float) EnchantmentHelper.getEfficiencyModifier(this) * 0.05F;

					if (this.rand.nextFloat() < f1)
					{
						player.getCooldownTracker().setCooldown(Items.SHIELD, 100);
						this.world.setEntityState(player, (byte) 30);
					}
				}
			}

			this.applyEnchantments(this, entityIn);
		}

		return flag;
	}

	@Override
	protected void registerAttributes()
	{
		super.registerAttributes();
		this.getAttributes().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
	}

	protected void ageInSunlight()
	{
		final float f = this.getBrightness();

		if (f > 0.5F)
		{
			this.idleTime += 2;
		}
	}
}
