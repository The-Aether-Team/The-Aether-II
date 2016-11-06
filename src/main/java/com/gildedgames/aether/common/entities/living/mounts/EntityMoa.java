package com.gildedgames.aether.common.entities.living.mounts;

import com.gildedgames.aether.api.entity.IMount;
import com.gildedgames.aether.api.entity.IMountProcessor;
import com.gildedgames.aether.common.registry.minecraft.SoundsAether;
import com.gildedgames.aether.common.entities.ai.moa.*;
import com.gildedgames.aether.common.entities.util.*;
import com.gildedgames.aether.common.entities.util.mounts.FlyingMount;
import com.gildedgames.aether.common.entities.util.mounts.IFlyingMountData;
import com.gildedgames.aether.common.entities.genes.moa.MoaGenePool;
import com.gildedgames.aether.common.entities.genes.util.GeneUtil;
import com.gildedgames.aether.common.items.ItemsAether;
import com.gildedgames.aether.common.items.misc.ItemMoaEgg;
import com.gildedgames.aether.common.util.TickTimer;
import com.google.common.collect.Sets;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.Set;

public class EntityMoa extends EntityGeneticAnimal<MoaGenePool> implements EntityGroupMember, IMount, IFlyingMountData
{

	private static final Set<Item> TEMPTATION_ITEMS = Sets.newHashSet(Items.WHEAT, ItemsAether.blueberries, ItemsAether.orange, ItemsAether.enchanted_blueberry, ItemsAether.enchanted_wyndberry, ItemsAether.wyndberry);

	private static final DataParameter<Integer> REMAINING_JUMPS = EntityDataManager.createKey(EntityMoa.class, DataSerializers.VARINT);

	private static final DataParameter<Boolean> EGG_STOLEN = EntityDataManager.createKey(EntityMoa.class, DataSerializers.BOOLEAN);

	private static final DataParameter<Boolean> RAISED_BY_PLAYER = EntityDataManager.createKey(EntityMoa.class, DataSerializers.BOOLEAN);

	private static final DataParameter<Boolean> GENDER = EntityDataManager.createKey(EntityMoa.class, DataSerializers.BOOLEAN);

	private static final DataParameter<Boolean> SADDLED = EntityDataManager.createKey(EntityMoa.class, DataSerializers.BOOLEAN);

	private static final DataParameter<Float> AIRBORNE_TIME = EntityDataManager.createKey(EntityMoa.class, DataSerializers.FLOAT);

	private static final DataParameter<Integer> FOOD_REQUIRED = EntityDataManager.createKey(EntityMoa.class, DataSerializers.VARINT);

	private static final DataParameter<Integer> FOOD_EATEN = EntityDataManager.createKey(EntityMoa.class, DataSerializers.VARINT);

	private static final DataParameter<Boolean> IS_HUNGRY = EntityDataManager.createKey(EntityMoa.class, DataSerializers.BOOLEAN);

	private IMountProcessor mountProcessor = new FlyingMount(this);

	public float wingRotation, destPos, prevDestPos, prevWingRotation;

	public int ticksUntilFlap;

	private TickTimer hungryTimer = new TickTimer();

	private EntityGroup pack;

	private MoaNest familyNest;

	public EntityMoa(World world)
	{
		super(world);

		this.initAI();

		this.familyNest = new MoaNest(world);

		this.setSize(1.0F, 2.0F);
		this.stepHeight = 1.0F;
	}

	public EntityMoa(World world, int geneticSeed)
	{
		this(world);

		this.getGenePool().transformFromSeed(geneticSeed);
	}

	public EntityMoa(World world, MoaNest familyNest)
	{
		this(world, familyNest.familyGeneticSeed);

		this.familyNest = familyNest;
		this.initAI();
	}

	public EntityMoa(World world, MoaNest familyNest, int fatherSeed, int motherSeed)
	{
		this(world, familyNest);

		if (fatherSeed == motherSeed)
		{
			this.getGenePool().transformFromSeed(fatherSeed);
		}
		else
		{
			this.getGenePool().transformFromParents(GeneUtil.getRandomSeed(world), fatherSeed, motherSeed);
		}
	}

	private void initAI()
	{
		this.tasks.addTask(0, new EntityAISwimming(this));
		this.tasks.addTask(0, new AIMoaPackBreeding(this, 0.25F));
		this.tasks.addTask(1, new AIPanicPack(this, 0.55F));
		this.tasks.addTask(3, new EntityAIWander(this, 0.50F));
		this.tasks.addTask(4, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
		this.tasks.addTask(5, new EntityAILookIdle(this));
		this.tasks.addTask(6, new EntityAIMate(this, 0.65F));
		this.tasks.addTask(8, new AIAnimalPack(this, 0.55F));
		this.tasks.addTask(12, new AIAvoidEntityAsChild(this, EntityPlayer.class, 5.0F, 0.3D, 0.3D));
		this.tasks.addTask(14, new EntityAIAttackMelee(this, 0.7D, true));
		this.tasks.addTask(15, new AIStayNearNest(this, 8, 0.55F));
		this.tasks.addTask(16, new EntityAITempt(this, 1.2D, false, TEMPTATION_ITEMS));

		this.targetTasks.addTask(1, new AIProtectPack(this));
	}

	@Override
	public MoaGenePool createNewGenePool()
	{
		return new MoaGenePool(new EntityGeneStorage(this));
	}

	@Override
	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();

		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.4D);
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(20);
	}

	@Override
	public void entityInit()
	{
		super.entityInit();

		this.dataManager.register(EntityMoa.REMAINING_JUMPS, 0);
		this.dataManager.register(EntityMoa.EGG_STOLEN, Boolean.FALSE);
		this.dataManager.register(EntityMoa.RAISED_BY_PLAYER, Boolean.FALSE);
		this.dataManager.register(EntityMoa.GENDER, Boolean.FALSE);
		this.dataManager.register(EntityMoa.SADDLED, false);
		this.dataManager.register(EntityMoa.AIRBORNE_TIME, this.maxAirborneTime());
		this.dataManager.register(EntityMoa.FOOD_REQUIRED, 0);
		this.dataManager.register(EntityMoa.FOOD_EATEN, 0);
		this.dataManager.register(EntityMoa.IS_HUNGRY, false);
	}

	@Override
	public boolean processInteract(EntityPlayer player, EnumHand hand, @Nullable ItemStack stack)
	{
		if (!super.processInteract(player, hand, stack))
		{
			if (!player.worldObj.isRemote)
			{
				if (this.isChild())
				{
					if (this.isRaisedByPlayer() && stack != null && stack.getItem() == ItemsAether.aechor_petal && this.isHungry())
					{
						if (!player.capabilities.isCreativeMode)
						{
							stack.stackSize--;
						}

						this.setIsHungry(false);
						this.hungryTimer.reset();

						this.setHealth(this.getMaxHealth());

						this.setFoodEaten(this.getFoodEaten() + 1);

						return true;
					}
				}
				else
				{
					if (this.isRaisedByPlayer() && !this.isSaddled() && stack != null && stack.getItem() == Items.SADDLE)
					{
						this.setIsSaddled(true);

						if (!player.capabilities.isCreativeMode)
						{
							stack.stackSize--;
						}

						return true;
					}
				}

				return false;
			}
		}

		return true;
	}

	@Override
	public void onUpdate()
	{
		super.onUpdate();

		if (this.isJumping)
		{
			this.motionY += 0.05F;
		}

		if (this.pack == null)
		{
			this.pack = this.familyNest.getAnimalPack();
		}

		this.updateWingRotation();

		this.fallSlowly();

		if (!this.worldObj.isRemote)
		{
			if (this.isChild() && this.isRaisedByPlayer())
			{
				if (this.getFoodEaten() >= this.getFoodRequired())
				{
					this.setGrowingAge(-1);
				}

				this.hungryTimer.tick();

				if (this.isHungry())
				{
					if (this.hungryTimer.getSecondsPassed() > 120)
					{
						if (this.hungryTimer.isMultipleOfSeconds(10))
						{
							this.attackEntityFrom(DamageSource.starve, 1.0F);
						}
					}
				}
				else
				{
					if (this.hungryTimer.getSecondsPassed() > 120)
					{
						this.setIsHungry(true);
						this.hungryTimer.reset();
					}
				}
			}
		}
	}

	private void fallSlowly()
	{
		this.fallDistance = 0;

		boolean riderSneaking = !this.getPassengers().isEmpty() && this.getPassengers().get(0) != null && this.getPassengers().get(0).isSneaking();

		if (!this.onGround && this.motionY < 0.0D && !riderSneaking)
		{
			this.motionY *= 0.63749999999999996D;
		}
	}

	public void updateWingRotation()
	{
		if (!this.onGround)
		{
			if (this.ticksUntilFlap == 0)
			{
				this.worldObj.playSound(this.posX, this.posY, this.posZ, SoundsAether.generic_wing_flap, SoundCategory.NEUTRAL, 0.4f, 0.8f + (this.getRNG().nextFloat() * 0.6f), false);

				this.ticksUntilFlap = 11;
			}
			else
			{
				this.ticksUntilFlap--;
			}
		}

		this.prevWingRotation = this.wingRotation;
		this.prevDestPos = this.destPos;

		this.destPos += 0.2D;
		this.destPos = Math.min(1.0F, Math.max(0.01F, this.destPos));

		if (this.onGround)
		{
			this.destPos = 0.0F;
		}

		this.wingRotation += 0.533F;
	}

	@Override
	public boolean attackEntityAsMob(Entity entity)
	{
		if (entity instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer) entity;

			if (player.capabilities.isCreativeMode)
			{
				return super.attackEntityAsMob(entity);
			}
		}

		entity.motionY = 0.8F;
		entity.motionZ = this.getLookVec().zCoord;
		entity.motionX = this.getLookVec().xCoord;
		entity.velocityChanged = true;

		entity.attackEntityFrom(DamageSource.causeMobDamage(this), this.isGroupLeader() ? 3 : 2);

		return super.attackEntityAsMob(entity);
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound nbt)
	{
		super.writeEntityToNBT(nbt);

		nbt.setBoolean("playerGrown", this.isRaisedByPlayer());

		if (this.getGender() != null)
		{
			nbt.setString("creatureGender", this.getGender().name());
		}

		nbt.setInteger("remainingJumps", this.getRemainingJumps());

		this.familyNest.writeToNBT(nbt);

		nbt.setBoolean("isSaddled", this.isSaddled());
		nbt.setBoolean("isHungry", this.isHungry());
		nbt.setInteger("foodRequired", this.getFoodRequired());
		nbt.setInteger("foodEaten", this.getFoodEaten());
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound nbt)
	{
		super.readEntityFromNBT(nbt);

		this.setRaisedByPlayer(nbt.getBoolean("playerGrown"));

		if (nbt.getString("creatureGender") != null)
		{
			this.setGender(AnimalGender.get(nbt.getString("creatureGender")));
		}

		this.setRemainingJumps(nbt.getInteger("remainingJumps"));

		this.familyNest.readFromNBT(nbt);

		this.setIsSaddled(nbt.getBoolean("isSaddled"));
		this.setIsHungry(nbt.getBoolean("isHungry"));
		this.setFoodRequired(nbt.getInteger("foodRequired"));
		this.setFoodEaten(nbt.getInteger("foodEaten"));
	}

	@Override
	protected void dropFewItems(boolean p_70628_1_, int looting)
	{
		super.dropFewItems(p_70628_1_, looting);

		if (this.isSaddled())
		{
			this.dropItem(Items.SADDLE, 1);
		}

		if (this.getRNG().nextBoolean())
		{
			this.dropItem(ItemsAether.bone_shard, this.getRNG().nextInt(5) + 1);
		}

		this.dropItem(Items.FEATHER, this.getRNG().nextInt(2) + 2);
	}

	@Override
	public boolean isGroupLeader()
	{
		return this.getGender() == AnimalGender.MALE;
	}

	@Override
	public EntityGroup getGroup()
	{
		return this.familyNest != null ? this.familyNest.getAnimalPack() : null;
	}

	@Override
	public boolean isProtective()
	{
		return this.isGroupLeader() || this.hasEggBeenStolen();
	}

	public boolean hasEggBeenStolen()
	{
		return this.dataManager.get(EntityMoa.EGG_STOLEN);
	}

	public void setEggStolen(boolean flag)
	{
		this.dataManager.set(EntityMoa.EGG_STOLEN, flag);
	}

	public boolean isRaisedByPlayer()
	{
		return this.dataManager.get(EntityMoa.RAISED_BY_PLAYER);
	}

	public void setRaisedByPlayer(boolean flag)
	{
		this.dataManager.set(EntityMoa.RAISED_BY_PLAYER, flag);
	}

	public AnimalGender getGender()
	{
		return this.dataManager.get(EntityMoa.GENDER) ? AnimalGender.MALE : AnimalGender.FEMALE;
	}

	public void setGender(AnimalGender gender)
	{
		this.dataManager.set(EntityMoa.GENDER, gender == AnimalGender.MALE);
	}

	public int getRemainingJumps()
	{
		return this.dataManager.get(EntityMoa.REMAINING_JUMPS);
	}

	private void setRemainingJumps(int jumps)
	{
		this.dataManager.set(EntityMoa.REMAINING_JUMPS, jumps);
	}

	public int getFoodEaten()
	{
		return this.dataManager.get(EntityMoa.FOOD_EATEN);
	}

	public void setFoodEaten(int foodEaten)
	{
		this.dataManager.set(EntityMoa.FOOD_EATEN, foodEaten);
	}

	public int getFoodRequired()
	{
		return this.dataManager.get(EntityMoa.FOOD_REQUIRED);
	}

	public void setFoodRequired(int foodRequired)
	{
		this.dataManager.set(EntityMoa.FOOD_REQUIRED, foodRequired);
	}

	public boolean isHungry()
	{
		return this.dataManager.get(EntityMoa.IS_HUNGRY);
	}

	public void setIsHungry(boolean flag)
	{
		this.dataManager.set(EntityMoa.IS_HUNGRY, flag);
	}

	public MoaNest getFamilyNest()
	{
		return this.familyNest;
	}

	public EntityGroup getAnimalPack()
	{
		return this.pack;
	}

	public void setAnimalPack(EntityGroup pack)
	{
		this.pack = pack;
		this.pack.onAnimalJoin(this);
	}

	@Override
	public void onDeath(DamageSource damageSource)
	{
		if (this.pack != null)
		{
			this.pack.onAnimalDeath(this);
		}

		super.onDeath(damageSource);
	}

	@Override
	public void setRevengeTarget(EntityLivingBase entity)
	{
		super.setRevengeTarget(entity);

		if (this.pack != null && entity != null)
		{
			if (this.isRaisedByPlayer())
			{
				return;
			}

			this.pack.addOrRenewAggressor(entity);
		}
	}

	@Override
	public EntityAgeable createChild(EntityAgeable matingAnimal)
	{
		if (matingAnimal instanceof EntityMoa)
		{
			EntityMoa mate = (EntityMoa) matingAnimal;

			/*if (this.getGender() == AnimalGender.FEMALE)
			{
				this.tasks.addTask(2, new AIMoaLayEgg(this, this.getSeed(), mate.getSeed(), 0.35F));
			}*/

			this.setGrowingAge(6000);
			mate.setGrowingAge(6000);
			this.resetInLove();

			this.setAttackTarget(null);
			mate.setAttackTarget(null);

			mate.resetInLove();
		}

		return null;
	}

	@Override
	public ItemStack getPickedResult(RayTraceResult target)
	{
		ItemStack moaEgg = new ItemStack(ItemsAether.moa_egg);

		MoaGenePool stackGenes = ItemMoaEgg.getGenePool(moaEgg);

		stackGenes.transformFromParents(this.getGenePool().getStorage().getSeed(), this.getGenePool().getStorage().getFatherSeed(), this.getGenePool().getStorage().getMotherSeed());

		ItemMoaEgg.setGenePool(moaEgg, stackGenes);

		return moaEgg;
	}

	@Override
	protected net.minecraft.util.SoundEvent getAmbientSound()
	{
		return SoundsAether.moa_ambient;
	}

	@Override
	protected net.minecraft.util.SoundEvent getHurtSound()
	{
		return SoundsAether.moa_hurt;
	}

	@Override
	protected net.minecraft.util.SoundEvent getDeathSound()
	{
		return SoundsAether.moa_hurt;
	}

	public boolean isSaddled()
	{
		return this.dataManager.get(SADDLED);
	}

	public void setIsSaddled(boolean isSaddled)
	{
		this.dataManager.set(SADDLED, isSaddled);
	}

	@Override
	public IMountProcessor getMountProcessor()
	{
		return this.mountProcessor;
	}

	@Override
	public void resetRemainingAirborneTime()
	{
		this.dataManager.set(EntityMoa.AIRBORNE_TIME, this.maxAirborneTime());
	}

	@Override
	public float getRemainingAirborneTime()
	{
		return this.dataManager.get(EntityMoa.AIRBORNE_TIME);
	}

	@Override
	public void setRemainingAirborneTime(float set)
	{
		this.dataManager.set(EntityMoa.AIRBORNE_TIME, set);
	}

	@Override
	public void addRemainingAirborneTime(float add)
	{
		this.setRemainingAirborneTime(this.getRemainingAirborneTime() + add);
	}

	@Override
	public boolean canBeMounted()
	{
		return this.isSaddled();
	}

	@Override
	public boolean canProcessMountInteraction(EntityPlayer rider, ItemStack stack)
	{
		return !this.isBreedingItem(stack) && (stack == null || stack.getItem() != Items.LEAD);
	}

	@Override
	public double getMountedYOffset()
	{
		return this.getGender() == AnimalGender.MALE ? 1.15D : 0.90F;
	}

	public float maxAirborneTime()
	{
		return 20.0F;
	}

	@Override
	public boolean isBreedingItem(@Nullable ItemStack stack)
	{
		return stack != null && TEMPTATION_ITEMS.contains(stack.getItem());
	}



}
