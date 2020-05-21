package com.gildedgames.aether.common.entities.monsters;

import com.gildedgames.aether.api.entity.damage.DamageTypeAttributes;
import com.gildedgames.aether.api.registrar.BlocksAether;
import com.gildedgames.aether.api.registrar.ItemsAether;
import com.gildedgames.aether.common.entities.ai.EntityAIAechorPlantAttack;
import com.gildedgames.aether.common.init.LootTablesAether;
import com.gildedgames.aether.common.util.helpers.PlayerUtil;
import com.google.common.collect.Maps;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Arrays;
import java.util.Map;

public class EntityAechorPlant extends EntityAetherMob
{
	protected Map<String, Float> defenseMap = Maps.newHashMap();
	{{
		this.defenseMap.put("Very Weak", 4.0F);
		this.defenseMap.put("Weak", 2.0F);
		this.defenseMap.put("Average", 0.0F);
		this.defenseMap.put("Strong", -2.0F);
		this.defenseMap.put("Very Strong", -4.0F);
	}}

	private static final int MAX_PETALS = 10;

	private static final DataParameter<Boolean> CAN_SEE_PREY = new DataParameter<>(16, DataSerializers.BOOLEAN);

	private static final DataParameter<Byte> PLANT_SIZE = new DataParameter<>(17, DataSerializers.BYTE);

	private static final DataParameter<Byte> PLANT_PETALS = new DataParameter<>(18, DataSerializers.BYTE);

	private boolean[] petals;

	@SideOnly(Side.CLIENT)
	public float sinage, prevSinage;

	private int poisonLeft;

	private int petalGrowTimer = 3000;

	public EntityAechorPlant(World world)
	{
		super(world);

		this.tasks.addTask(0, new EntityAIAechorPlantAttack(this));
		this.targetTasks.addTask(1, new EntityAINearestAttackableTarget<>(this, EntityPlayer.class, true));

		this.setSize(0.8F, 0.6F);

		this.setPoisonLeft(2);

		if (world != null)
		{
			if (world.isRemote)
			{
				this.sinage = this.rand.nextFloat() * 6F;
			}
		}

		this.experienceValue = 3;

		Arrays.fill(this.petals, true);
	}

	public boolean[] getPetalsPresent()
	{
		return this.petals;
	}

	@Override
	protected boolean canDespawn()
	{
		return false;
	}

	@Override
	protected void entityInit()
	{
		super.entityInit();

		this.petals = new boolean[MAX_PETALS];

		Arrays.fill(this.petals, true);

		this.dataManager.register(EntityAechorPlant.CAN_SEE_PREY, Boolean.FALSE);
		this.dataManager.register(EntityAechorPlant.PLANT_SIZE, (byte) 0);
		this.dataManager.register(EntityAechorPlant.PLANT_PETALS, this.serializePlantPetals());
	}

	@Override
	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();

		this.setPlantSize(this.rand.nextInt(3) + 1);

		this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(3.0F);
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(10.0D);

		this.getEntityAttribute(DamageTypeAttributes.SLASH_DEFENSE_LEVEL).setBaseValue(0.0f);
		this.getEntityAttribute(DamageTypeAttributes.IMPACT_DEFENSE_LEVEL).setBaseValue(0.0f);
		this.getEntityAttribute(DamageTypeAttributes.PIERCE_DEFENSE_LEVEL).setBaseValue(0.0f);
	}

	@Override
	public void onUpdate()
	{
		super.onUpdate();

		this.motionX = 0.0D;
		this.motionZ = 0.0D;

		if (!this.world.isRemote)
		{
			this.petalGrowTimer--;

			if (this.petalGrowTimer <= 0)
			{
				this.petalGrowTimer = 2400 + this.getRNG().nextInt(1600);

				int remainingPetals = this.getPetalCountInState(true);

				if (remainingPetals < MAX_PETALS)
				{
					this.setPetalState(this.getRandomPetal(false), true);
				}

				this.heal((float) (Math.round((this.getMaxHealth() / MAX_PETALS) * 2.0) / 2.0));
			}
		}

		if (this.world.isRemote)
		{
			this.tickAnimation();

			return;
		}

		boolean isTargeting = this.getAttackTarget() != null;

		if (this.canSeePrey() != isTargeting)
		{
			this.setCanSeePrey(isTargeting);
		}

		if (!this.canStayHere(new BlockPos(this)))
		{
			this.setHealth(0);
		}
	}

	/*
	@Override
	protected void damageEntity(DamageSource damageSrc, float damageAmount)
	{
		float prevHealth = this.getHealth();

		super.damageEntity(damageSrc, damageAmount);

		if (this.getHealth() != prevHealth)
		{
			this.petalGrowTimer = 6000;

			if (!this.world.isRemote)
			{
				int targetPetals = (int) Math.floor((this.getHealth() / this.getMaxHealth()) * MAX_PETALS);
				int remainingPetals = this.getPetalCountInState(true);

				int damage = remainingPetals - targetPetals;

				Block.spawnAsEntity(this.world, this.getPosition(), new ItemStack(ItemsAether.aechor_petal, damage / 2));

				while (remainingPetals > targetPetals)
				{
					this.setPetalState(this.getRandomPetal(true), false);

					remainingPetals--;
				}
			}
		}
	}
	 */

	@Override
	public void onDeath(DamageSource cause)
	{
		super.onDeath(cause);

		Block.spawnAsEntity(this.world, this.getPosition(), new ItemStack(ItemsAether.aechor_petal, 5));
	}

	private int getRandomPetal(boolean state)
	{
		int total = this.getPetalCountInState(state);
		int nth = this.rand.nextInt(total);

		int selected = -1;

		for (int i = 0, k = 0; i < this.petals.length; i++)
		{
			boolean present = this.petals[i];

			if (present == state)
			{
				if (k == nth)
				{
					selected = i;

					break;
				}

				k++;
			}
		}

		return selected;
	}

	private void setPetalState(int index, boolean state)
	{
		this.petals[index] = state;
		this.sendPetalUpdate();
	}

	private void sendPetalUpdate()
	{
		this.dataManager.set(PLANT_PETALS, this.serializePlantPetals());
	}

	private int getPetalCountInState(boolean state)
	{
		int i = 0;

		for (boolean a : this.petals)
		{
			if (a == state)
			{
				i++;
			}
		}

		return i;
	}

	private boolean canStayHere(final BlockPos pos)
	{
		if (this.world.getBlockState(pos).isFullCube())
		{
			return false;
		}

		final IBlockState rootBlock = this.world.getBlockState(pos.down());

		return rootBlock.getBlock() == BlocksAether.aether_grass
				|| rootBlock.getBlock() == BlocksAether.aether_dirt
				|| rootBlock.getBlock() == BlocksAether.highlands_snow_layer
				|| rootBlock.getBlock() == BlocksAether.highlands_snow;
	}

	@Override
	public void knockBack(Entity entity, float distance, double x, double y)
	{
	}

	@Override
	public void move(MoverType type, double x, double y, double z)
	{
		if (type == MoverType.PISTON)
		{
			super.move(type, x, y, z);
		}
	}

	@Override
	protected ResourceLocation getLootTable()
	{
		return LootTablesAether.ENTITY_AECHOR_PLANT;
	}

	@Override
	public boolean processInteract(EntityPlayer player, EnumHand hand)
	{
		ItemStack stack = player.getHeldItem(hand);

		if (!player.capabilities.isCreativeMode && stack.getItem() == ItemsAether.skyroot_bucket)
		{
			if (this.getPoisonLeft() > 0)
			{
				PlayerUtil.fillBucketInHand(player, hand, stack, new ItemStack(ItemsAether.skyroot_poison_bucket));

				this.setPoisonLeft(this.getPoisonLeft() - 1);

				return true;
			}
		}

		return false;
	}

	@SideOnly(Side.CLIENT)
	private void tickAnimation()
	{
		this.prevSinage = this.sinage;

		if (this.hurtTime > 0)
		{
			this.sinage += 0.5F;
		}
		else
		{
			this.sinage += this.canSeePrey() ? 0.3F : 0.1F;
		}

		float pie2 = 3.141593F * 2F;

		if (this.sinage > pie2)
		{
			this.sinage -= pie2;
			this.prevSinage -= pie2;
		}
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound tagCompound)
	{
		super.writeEntityToNBT(tagCompound);

		tagCompound.setInteger("plantSize", this.getPlantSize());
		tagCompound.setInteger("poisonLeft", this.getPoisonLeft());

		tagCompound.setByte("petals", this.serializePlantPetals());

		tagCompound.setInteger("petalGrowTimer", this.petalGrowTimer);
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound tagCompound)
	{
		super.readEntityFromNBT(tagCompound);

		this.setPlantSize(tagCompound.getInteger("plantSize"));
		this.setPoisonLeft(tagCompound.getInteger("poisonLeft"));

		if (tagCompound.hasKey("petals"))
		{
			this.deserializePlantPetals(tagCompound.getByte("petals"));
		}
		else
		{
			Arrays.fill(this.petals, true);
		}

		this.petalGrowTimer = tagCompound.getInteger("petalGrowTimer");
	}

	@Override
	public boolean getCanSpawnHere()
	{
		return this.world.getDifficulty() != EnumDifficulty.PEACEFUL;
	}

	public boolean canSeePrey()
	{
		return this.dataManager.get(EntityAechorPlant.CAN_SEE_PREY);
	}

	public void setCanSeePrey(boolean canSee)
	{
		this.dataManager.set(EntityAechorPlant.CAN_SEE_PREY, canSee);
	}

	public int getPlantSize()
	{
		return this.dataManager.get(EntityAechorPlant.PLANT_SIZE);
	}

	public void setPlantSize(int size)
	{
		this.dataManager.set(EntityAechorPlant.PLANT_SIZE, (byte) size);
	}

	public int getPoisonLeft()
	{
		return this.poisonLeft;
	}

	public void setPoisonLeft(int poisonLeft)
	{
		this.poisonLeft = poisonLeft;
	}

	@Override
	public void notifyDataManagerChange(DataParameter<?> key)
	{
		if (key == PLANT_PETALS)
		{
			this.deserializePlantPetals(this.dataManager.get(PLANT_PETALS));
		}
	}

	private void deserializePlantPetals(byte val)
	{
		for (int i = 0; i < this.petals.length; i++)
		{
			this.petals[i] = ((val >>> i) & 1) == 1;
		}
	}

	private byte serializePlantPetals()
	{
		byte val = 0;

		for (int i = 0; i < this.petals.length; i++)
		{
			if (this.petals[i])
			{
				val |= (1 << i);
			}
		}

		return val;
	}
}
