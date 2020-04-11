package com.gildedgames.aether.common.entities.animals;

import com.gildedgames.aether.api.entity.IEntityEyesComponent;
import com.gildedgames.aether.api.entity.IMount;
import com.gildedgames.aether.api.entity.IMountProcessor;
import com.gildedgames.aether.api.entity.damage.DamageTypeAttributes;
import com.gildedgames.aether.api.registrar.ItemsAether;
import com.gildedgames.aether.api.registrar.SoundsAether;
import com.gildedgames.aether.common.entities.ai.AetherNavigateGround;
import com.gildedgames.aether.common.entities.ai.EntityAIUnstuckBlueAercloud;
import com.gildedgames.aether.common.entities.ai.moa.*;
import com.gildedgames.aether.common.entities.genes.AnimalGender;
import com.gildedgames.aether.common.entities.genes.EntityGeneStorage;
import com.gildedgames.aether.common.entities.genes.EntityGeneticAnimal;
import com.gildedgames.aether.common.entities.genes.GeneUtil;
import com.gildedgames.aether.common.entities.genes.moa.MoaGenePool;
import com.gildedgames.aether.common.entities.genes.moa.MoaNest;
import com.gildedgames.aether.common.entities.mounts.FlyingMount;
import com.gildedgames.aether.common.entities.mounts.IFlyingMountData;
import com.gildedgames.aether.common.entities.multipart.AetherMultiPartEntity;
import com.gildedgames.aether.common.entities.multipart.AetherMultiPartMount;
import com.gildedgames.aether.common.entities.util.eyes.EntityEyesComponent;
import com.gildedgames.aether.common.entities.util.eyes.IEntityEyesComponentProvider;
import com.gildedgames.aether.common.entities.util.groups.EntityGroup;
import com.gildedgames.aether.common.entities.util.groups.EntityGroupMember;
import com.gildedgames.aether.common.items.other.ItemMoaEgg;
import com.gildedgames.aether.common.items.other.ItemMoaFeather;
import com.gildedgames.aether.common.items.other.ItemMoaFeed;
import com.gildedgames.aether.common.util.helpers.MathUtil;
import com.gildedgames.orbis.lib.client.PartialTicks;
import com.google.common.collect.Sets;
import net.minecraft.block.Block;
import net.minecraft.entity.*;
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
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import javax.vecmath.Point3d;
import java.util.Set;

public class EntityMoa extends EntityGeneticAnimal<MoaGenePool>
		implements EntityGroupMember, IMount, IFlyingMountData, IEntityMultiPart, IEntityEyesComponentProvider
{

	private static final Set<Item> TEMPTATION_ITEMS = Sets
			.newHashSet(ItemsAether.blueberries, ItemsAether.skyroot_lizard_stick);

	private static final DataParameter<Integer> REMAINING_JUMPS = EntityDataManager.createKey(EntityMoa.class, DataSerializers.VARINT);

	private static final DataParameter<Boolean> EGG_STOLEN = EntityDataManager.createKey(EntityMoa.class, DataSerializers.BOOLEAN);

	private static final DataParameter<Boolean> RAISED_BY_PLAYER = EntityDataManager.createKey(EntityMoa.class, DataSerializers.BOOLEAN);

	private static final DataParameter<Boolean> GENDER = EntityDataManager.createKey(EntityMoa.class, DataSerializers.BOOLEAN);

	private static final DataParameter<Boolean> SADDLED = EntityDataManager.createKey(EntityMoa.class, DataSerializers.BOOLEAN);

	private static final DataParameter<Float> AIRBORNE_TIME = EntityDataManager.createKey(EntityMoa.class, DataSerializers.FLOAT);

	private static final DataParameter<Integer> FOOD_REQUIRED = EntityDataManager.createKey(EntityMoa.class, DataSerializers.VARINT);

	private static final DataParameter<Integer> FOOD_EATEN = EntityDataManager.createKey(EntityMoa.class, DataSerializers.VARINT);

	private static final DataParameter<Boolean> IS_HUNGRY = EntityDataManager.createKey(EntityMoa.class, DataSerializers.BOOLEAN);

	private final IMountProcessor mountProcessor = new FlyingMount(this);

	private final MultiPartEntityPart[] parts;

	private final Point3d[] old;

	private final AetherMultiPartEntity neck = new AetherMultiPartMount(this, "neck", 0.4F, 0.8F);

	private final AetherMultiPartEntity head = new AetherMultiPartMount(this, "head", 0.8F, 0.6F);

	private final AetherMultiPartEntity beak = new AetherMultiPartMount(this, "beak", 0.4F, 0.5F);

	private final AetherMultiPartEntity body = new AetherMultiPartMount(this, "body", 1.1F, 1.325F);

	private final AetherMultiPartEntity tail = new AetherMultiPartMount(this, "tail", 1.1F, 0.6F);

	private final IEntityEyesComponent eyes = new EntityEyesComponent(this);

	public float wingRotation, destPos, prevDestPos, prevWingRotation;

	public int ticksUntilFlap;

	private int hungryTimer, dropFeatherTimer;

	private int timeUntilDropFeather;

	private float ageSinceOffGround;

	private boolean wasOnGround = true, isFastFalling = false;

	private EntityGroup pack;

	private MoaNest familyNest;

	public EntityMoa(final World world)
	{
		super(world);

		this.initAI();

		this.familyNest = new MoaNest(world);

		this.setSize(1.0F, 2.0F);
		this.stepHeight = 1.0F;
		this.parts = new MultiPartEntityPart[] { this.head, this.neck, this.beak, this.body, this.tail };
		this.old = new Point3d[this.parts.length];

		for (int i = 0; i < this.old.length; i++)
		{
			this.old[i] = new Point3d();
		}
	}

	public EntityMoa(final World world, final int geneticSeed)
	{
		this(world);

		this.getGenePool().transformFromSeed(geneticSeed);
	}

	public EntityMoa(final World world, final MoaNest familyNest)
	{
		this(world, familyNest.familyGeneticSeed);

		this.familyNest = familyNest;
		this.initAI();
	}

	public EntityMoa(final World world, final MoaNest familyNest, final int fatherSeed, final int motherSeed)
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

	public float getAgeSinceOffGround()
	{
		return this.ageSinceOffGround;
	}

	@Override
	protected PathNavigate createNavigator(final World worldIn)
	{
		return new AetherNavigateGround(this, worldIn);
	}

	private void initAI()
	{
		this.tasks.addTask(0, new EntityAISwimming(this));
		this.tasks.addTask(0, new AIMoaPackBreeding(this, 0.25F));
		this.tasks.addTask(1, new AIPanicPack(this, 0.55F));
		this.tasks.addTask(3, new EntityAIUnstuckBlueAercloud(this));
		this.tasks.addTask(3, new EntityAIWander(this, 0.50F));
		this.tasks.addTask(4, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
		this.tasks.addTask(5, new EntityAILookIdle(this));
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
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(20.0D);

		this.getEntityAttribute(DamageTypeAttributes.SLASH_DEFENSE_LEVEL).setBaseValue(28);
		this.getEntityAttribute(DamageTypeAttributes.IMPACT_DEFENSE_LEVEL).setBaseValue(38);
		this.getEntityAttribute(DamageTypeAttributes.PIERCE_DEFENSE_LEVEL).setBaseValue(25);
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
	public boolean processInteract(final EntityPlayer player, final EnumHand hand)
	{
		if (!this.isRaisedByPlayer())
		{
			return false;
		}

		final ItemStack stack = player.getHeldItem(hand);

		if (stack.getItem() instanceof ItemMoaFeed)
		{
			ItemMoaFeed moaFeed = (ItemMoaFeed) stack.getItem();

			float healAmount = (float) moaFeed.getHealingAmount();

			if (healAmount != 0)
			{
				this.heal(healAmount);

				if (!player.capabilities.isCreativeMode)
				{
					stack.shrink(1);
				}
			}
		}

		if (this.isChild())
		{
			if (this.isHungry() && (stack.getItem() == ItemsAether.aechor_petal || stack.getItem() == ItemsAether.skyroot_lizard_stick))
			{
				this.setIsHungry(false);
				this.setHealth(this.getMaxHealth());

				this.setFoodEaten(this.getFoodEaten() + 1);

				if (!player.capabilities.isCreativeMode)
				{
					stack.shrink(1);
				}

				return true;
			}
		}
		else
		{
			if (!this.isSaddled() && stack.getItem() == ItemsAether.aether_saddle)
			{
				this.setIsSaddled(true);

				this.world.playSound(player, this.posX, this.posY, this.posZ, SoundEvents.ENTITY_PIG_SADDLE, SoundCategory.NEUTRAL, 0.5F, 1.0F);

				if (!player.capabilities.isCreativeMode)
				{
					stack.shrink(1);
				}

				return true;
			}
			else return super.processInteract(player, hand);
		}

		return false;
	}

	@Override
	public void onUpdate()
	{
		super.onUpdate();

		this.eyes.update();

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

		if (!this.world.isRemote)
		{
			//TODO: When the Moa system is implemented, there should be a check to stop active/tamed Moas from dropping feathers based on it.
			this.dropFeatherTimer++;

			if (this.timeUntilDropFeather == 0)
			{
				this.timeUntilDropFeather = (120 + this.getRNG().nextInt(80)) * 20;
			}

			if (this.dropFeatherTimer >= this.timeUntilDropFeather)
			{
				this.timeUntilDropFeather = 0;

				final ItemStack feather = new ItemStack(ItemsAether.moa_feather);
				ItemMoaFeather.setColor(feather, this.getGenePool().getFeathers().gene().unlocalizedName(), this.getGenePool().getFeathers().gene().data().getRGB());

				Block.spawnAsEntity(this.world, this.getPosition(), feather);
				this.dropFeatherTimer = 0;
			}

			if (this.isChild() && this.isRaisedByPlayer())
			{
				if (this.getFoodEaten() >= this.getFoodRequired())
				{
					this.setGrowingAge(-1);
				}

				this.hungryTimer++;

				if (this.isHungry())
				{
					if (this.hungryTimer > 2400)
					{
						if (this.hungryTimer % 200 == 0)
						{
							this.attackEntityFrom(DamageSource.STARVE, 1.0F);
						}
					}
				}
				else
				{
					if (this.hungryTimer > 2400)
					{
						this.setIsHungry(true);
					}
				}
			}
		}
	}

	private void fallSlowly()
	{
		this.fallDistance = 0;

		final boolean riderSneaking =
				!this.getPassengers().isEmpty() && this.getPassengers().get(0) != null && this.getPassengers().get(0).isSneaking();

		if (!this.onGround)
		{
			if (riderSneaking)
			{
				this.isFastFalling = true;
			}
			else if (this.motionY < 0.0D)
			{
				this.motionY *= 0.63749999999999996D;
			}
		}

		if (!riderSneaking)
		{
			this.isFastFalling = false;
		}
	}

	@Override
	public boolean isFastFalling()
	{
		return this.isFastFalling;
	}

	public void updateWingRotation()
	{
		if (!this.onGround)
		{
			if (this.wasOnGround)
			{
				this.ageSinceOffGround = (float) this.ticksExisted + PartialTicks.get();
				this.wasOnGround = false;
			}

			if (this.ticksUntilFlap == 0)
			{
				this.world.playSound(this.posX, this.posY, this.posZ, SoundsAether.generic_wing_flap, SoundCategory.NEUTRAL, 0.4f,
						0.8f + (this.getRNG().nextFloat() * 0.6f), false);

				this.ticksUntilFlap = 11;
			}
			else
			{
				this.ticksUntilFlap--;
			}
		}
		else if (this.world.isRemote)
		{
			if (!this.wasOnGround)
			{
				this.ageSinceOffGround = (float) this.ticksExisted + PartialTicks.get();
				this.wasOnGround = true;
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
	public boolean attackEntityAsMob(final Entity entity)
	{
		if (entity instanceof EntityPlayer)
		{
			final EntityPlayer player = (EntityPlayer) entity;

			if (player.capabilities.isCreativeMode)
			{
				return super.attackEntityAsMob(entity);
			}
		}

		entity.motionY = 0.8F;
		entity.motionZ = this.getLookVec().z;
		entity.motionX = this.getLookVec().x;
		entity.velocityChanged = true;

		entity.attackEntityFrom(DamageSource.causeMobDamage(this), this.isGroupLeader() ? 3 : 2);

		return super.attackEntityAsMob(entity);
	}

	@Override
	public void writeEntityToNBT(final NBTTagCompound nbt)
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

		nbt.setInteger("hungryTimer", this.hungryTimer);
		nbt.setInteger("dropFeatherTimer", this.dropFeatherTimer);

		nbt.setInteger("timeUntilDropFeather", this.timeUntilDropFeather);
	}

	@Override
	public void readEntityFromNBT(final NBTTagCompound nbt)
	{
		super.readEntityFromNBT(nbt);

		this.setRaisedByPlayer(nbt.getBoolean("playerGrown"));

		if (nbt.hasKey("creatureGender"))
		{
			this.setGender(AnimalGender.get(nbt.getString("creatureGender")));
		}

		this.setRemainingJumps(nbt.getInteger("remainingJumps"));

		this.familyNest.readFromNBT(nbt);

		this.setIsSaddled(nbt.getBoolean("isSaddled"));
		this.setIsHungry(nbt.getBoolean("isHungry"));
		this.setFoodRequired(nbt.getInteger("foodRequired"));
		this.setFoodEaten(nbt.getInteger("foodEaten"));

		this.hungryTimer = nbt.getInteger("hungryTimer");
		this.dropFeatherTimer = nbt.getInteger("dropFeatherTimer");

		this.timeUntilDropFeather = nbt.getInteger("timeUntilDropFeather");
	}

	@Override
	protected void dropFewItems(final boolean p_70628_1_, final int looting)
	{
		super.dropFewItems(p_70628_1_, looting);

		final ItemStack feather = new ItemStack(ItemsAether.moa_feather, this.getRNG().nextInt(3));
		ItemMoaFeather.setColor(feather, this.getGenePool().getFeathers().gene().unlocalizedName(), this.getGenePool().getFeathers().gene().data().getRGB());

		Block.spawnAsEntity(this.world, this.getPosition(), feather);

		if (this.isSaddled())
		{
			this.dropItem(ItemsAether.aether_saddle, 1);
		}
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

	public void setEggStolen(final boolean flag)
	{
		this.dataManager.set(EntityMoa.EGG_STOLEN, flag);
	}

	public boolean isRaisedByPlayer()
	{
		return this.dataManager.get(EntityMoa.RAISED_BY_PLAYER);
	}

	public void setRaisedByPlayer(final boolean flag)
	{
		this.dataManager.set(EntityMoa.RAISED_BY_PLAYER, flag);
	}

	public AnimalGender getGender()
	{
		return this.dataManager.get(EntityMoa.GENDER) ? AnimalGender.MALE : AnimalGender.FEMALE;
	}

	public void setGender(final AnimalGender gender)
	{
		this.dataManager.set(EntityMoa.GENDER, gender == AnimalGender.MALE);
		this.updateMultiPart();
	}

	public int getRemainingJumps()
	{
		return this.dataManager.get(EntityMoa.REMAINING_JUMPS);
	}

	private void setRemainingJumps(final int jumps)
	{
		this.dataManager.set(EntityMoa.REMAINING_JUMPS, jumps);
	}

	public int getFoodEaten()
	{
		return this.dataManager.get(EntityMoa.FOOD_EATEN);
	}

	public void setFoodEaten(final int foodEaten)
	{
		this.dataManager.set(EntityMoa.FOOD_EATEN, foodEaten);
	}

	public int getFoodRequired()
	{
		return this.dataManager.get(EntityMoa.FOOD_REQUIRED);
	}

	public void setFoodRequired(final int foodRequired)
	{
		this.dataManager.set(EntityMoa.FOOD_REQUIRED, foodRequired);
	}

	public boolean isHungry()
	{
		return this.dataManager.get(EntityMoa.IS_HUNGRY);
	}

	public void setIsHungry(final boolean flag)
	{
		if (this.dataManager.get(EntityMoa.IS_HUNGRY) != flag)
		{
			this.hungryTimer = 0;
		}

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

	public void setAnimalPack(final EntityGroup pack)
	{
		this.pack = pack;
		this.pack.onAnimalJoin(this);
	}

	@Override
	public void onDeath(final DamageSource damageSource)
	{
		if (this.pack != null)
		{
			this.pack.onAnimalDeath(this);
		}

		super.onDeath(damageSource);
	}

	@Override
	public void setRevengeTarget(final EntityLivingBase entity)
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
	public void onLivingUpdate()
	{
		super.onLivingUpdate();

		this.setMultiPartLocations();
	}

	public float getSize()
	{
		return (this.isChild() ? 0.65f : 1) + (this.isGroupLeader() ? 0.15F : 0.0F);
	}

	@Override
	protected void onGrowingAdult()
	{
		this.updateMultiPart();
	}

	private void updateMultiPart()
	{
		float scale = this.getSize();

		this.neck.updateSize(0.4F * scale, 0.8F * scale);
		this.head.updateSize(0.8F * scale, 0.6F * scale);
		this.beak.updateSize(0.4F * scale, 0.5F * scale);
		this.body.updateSize(1.1F * scale, 1.325F * scale);
		this.tail.updateSize(1.1F * scale, 0.6F * scale);
	}

	private void setMultiPartLocations()
	{
		float scale = this.getSize();

		for (int i = 0; i < this.parts.length; i++)
		{
			this.old[i].set(this.parts[i].posX, this.parts[i].posY, this.parts[i].posZ);
		}

		this.updateMultiPart();

		float f = MathUtil.interpolateRotation(this.prevRenderYawOffset, this.renderYawOffset, 1);
		float f1 = MathHelper.cos(-f * 0.017453292F - (float) Math.PI) * scale;
		float f2 = MathHelper.sin(-f * 0.017453292F - (float) Math.PI) * scale;

		this.head.onUpdate();
		this.head.setLocationAndAngles(this.posX - f2 * 0.5f, this.posY + 1.45f * scale, this.posZ - f1 * 0.5f, 0F, 0F);
		this.beak.onUpdate();
		this.beak.setLocationAndAngles(this.posX - f2 * 1.1f, this.posY + 1.5f * scale, this.posZ - f1 * 1.1f, 0F, 0F);
		this.neck.onUpdate();
		this.neck.setLocationAndAngles(this.posX - f2 * 0.6f, this.posY + 0.75f * scale, this.posZ - f1 * 0.6f, 0F, 0F);
		this.tail.onUpdate();
		this.tail.setLocationAndAngles(this.posX + f2 * 1.1f, this.posY + 0.5f * scale, this.posZ + f1 * 1.1f, 0F, 0F);
		this.body.onUpdate();
		this.body.setLocationAndAngles(this.posX, this.posY, this.posZ, 0F, 0F);

		for (int i = 0; i < this.parts.length; i++)
		{
			this.parts[i].prevPosX = this.old[i].getX();
			this.parts[i].prevPosY = this.old[i].getY();
			this.parts[i].prevPosZ = this.old[i].getZ();
		}
	}

	@Override
	public boolean attackEntityFromPart(MultiPartEntityPart part, DamageSource source, float damage)
	{
		switch (part.partName)
		{
			case "head":
				damage *= 1.3f;
				break;
			case "beak":
				damage *= 1.1f;
				break;
			case "tail":
				damage *= .2f;
				break;
		}

		if (this.hurtResistantTime <= 10)
		{
			return this.attackEntityFrom(source, damage);
		}

		return false;
	}

	@Override
	public World getWorld()
	{
		return this.getEntityWorld();
	}

	@Override
	public boolean canBeCollidedWith()
	{
		return false;
	}

	@Nullable
	@Override
	public MultiPartEntityPart[] getParts()
	{
		return this.parts;
	}

	@Override
	public EntityAgeable createChild(final EntityAgeable matingAnimal)
	{
		return new EntityMoa(this.world);
	}

	@Override
	public ItemStack getPickedResult(final RayTraceResult target)
	{
		final ItemStack moaEgg = new ItemStack(ItemsAether.moa_egg_item);

		final MoaGenePool stackGenes = ItemMoaEgg.getGenePool(moaEgg);

		stackGenes.transformFromParents(this.getGenePool().getStorage().getSeed(), this.getGenePool().getStorage().getFatherSeed(),
				this.getGenePool().getStorage().getMotherSeed());

		ItemMoaEgg.setGenePool(moaEgg, stackGenes);

		return moaEgg;
	}

	@Override
	protected SoundEvent getAmbientSound()
	{
		return SoundsAether.moa_ambient;
	}

	@Override
	protected SoundEvent getHurtSound(final DamageSource src)
	{
		return SoundsAether.moa_hurt;
	}

	@Override
	protected SoundEvent getDeathSound()
	{
		return SoundsAether.moa_hurt;
	}

	public boolean isSaddled()
	{
		return this.dataManager.get(SADDLED);
	}

	public void setIsSaddled(final boolean isSaddled)
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
	public void setRemainingAirborneTime(final float set)
	{
		this.dataManager.set(EntityMoa.AIRBORNE_TIME, set);
	}

	@Override
	public void addRemainingAirborneTime(final float add)
	{
		this.setRemainingAirborneTime(this.getRemainingAirborneTime() + add);
	}

	@Override
	public boolean canBeMounted()
	{
		return this.isSaddled();
	}

	@Override
	public boolean canProcessMountInteraction(final EntityPlayer rider, final ItemStack stack)
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
	public boolean isBreedingItem(@Nullable final ItemStack stack)
	{
		return stack != null && TEMPTATION_ITEMS.contains(stack.getItem());
	}

	@Override
	public IEntityEyesComponent getEyes()
	{
		return this.eyes;
	}
}
