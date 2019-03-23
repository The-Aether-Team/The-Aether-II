package com.gildedgames.aether.common.entities.living.mobs;

import com.gildedgames.aether.api.damage_system.DamageTypeAttributes;
import com.gildedgames.aether.common.blocks.BlocksAether;
import com.gildedgames.aether.common.entities.ai.EntityAIAechorPlantAttack;
import com.gildedgames.aether.common.items.ItemsAether;
import com.gildedgames.aether.common.registry.content.LootTablesAether;
import com.gildedgames.aether.common.util.helpers.PlayerUtil;
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
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Arrays;

public class EntityAechorPlant extends EntityAetherMob
{
	private static final DataParameter<Boolean> CAN_SEE_PREY = new DataParameter<>(16, DataSerializers.BOOLEAN);

	private static final DataParameter<Byte> PLANT_SIZE = new DataParameter<>(17, DataSerializers.BYTE);

	private final boolean[] petalPresent = new boolean[4];

	@OnlyIn(Dist.CLIENT)
	public float sinage, prevSinage;

	private int poisonLeft;

	private int petalsLeft = 4, lastPetalsLeft = 4;

	public EntityAechorPlant(World world)
	{
		super(world);

		this.tasks.addTask(0, new EntityAIAechorPlantAttack(this));
		this.targetTasks.addTask(1, new EntityAINearestAttackableTarget<>(this, EntityPlayer.class, true));

		this.setSize(0.8F, 0.6F);

		this.setPoisonLeft(2);

		if (world.isRemote)
		{
			this.sinage = this.rand.nextFloat() * 6F;
		}

		this.experienceValue = 3;

		Arrays.fill(this.petalPresent, true);
	}

	public boolean[] getPetalsPresent()
	{
		return this.petalPresent;
	}

	@Override
	protected void entityInit()
	{
		super.entityInit();

		this.dataManager.register(EntityAechorPlant.CAN_SEE_PREY, Boolean.FALSE);
		this.dataManager.register(EntityAechorPlant.PLANT_SIZE, (byte) 0);
	}

	@Override
	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();

		this.setPlantSize(this.rand.nextInt(3) + 1);

		this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(3.0F);
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(15.0D);

		this.getEntityAttribute(DamageTypeAttributes.SLASH_DEFENSE_LEVEL).setBaseValue(4);
		this.getEntityAttribute(DamageTypeAttributes.PIERCE_DEFENSE_LEVEL).setBaseValue(8);
		this.getEntityAttribute(DamageTypeAttributes.IMPACT_DEFENSE_LEVEL).setBaseValue(8);
	}

	@Override
	public void onUpdate()
	{
		super.onUpdate();

		this.motionX = 0.0D;
		this.motionZ = 0.0D;

		if (this.getHealth() <= 20)
		{
			if (((this.getHealth() - 2) / 6) + 1 < this.petalsLeft && this.petalsLeft == this.lastPetalsLeft)
			{
				this.petalsLeft--;
			}

			if (this.petalsLeft != this.lastPetalsLeft)
			{
				boolean found = false;

				while (!found)
				{
					int index = this.getRNG().nextInt(this.petalPresent.length);

					if (this.petalPresent[index])
					{
						found = true;

						this.petalPresent[index] = false;

						ItemStack petal = new ItemStack(ItemsAether.aechor_petal);

						Block.spawnAsEntity(this.world, this.getPosition(), petal);
					}
				}

				this.lastPetalsLeft = this.petalsLeft;
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

	@OnlyIn(Dist.CLIENT)
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

		tagCompound.setBoolean("petal0", this.petalPresent[0]);
		tagCompound.setBoolean("petal1", this.petalPresent[1]);
		tagCompound.setBoolean("petal2", this.petalPresent[2]);
		tagCompound.setBoolean("petal3", this.petalPresent[3]);

		tagCompound.setInteger("petalsLeft", this.petalsLeft);
		tagCompound.setInteger("lastPetalsLeft", this.lastPetalsLeft);
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound tagCompound)
	{
		super.readEntityFromNBT(tagCompound);

		this.setPlantSize(tagCompound.getInteger("plantSize"));
		this.setPoisonLeft(tagCompound.getInteger("poisonLeft"));

		this.petalPresent[0] = tagCompound.getBoolean("petal0");
		this.petalPresent[1] = tagCompound.getBoolean("petal1");
		this.petalPresent[2] = tagCompound.getBoolean("petal2");
		this.petalPresent[3] = tagCompound.getBoolean("petal3");

		this.petalsLeft = tagCompound.getInteger("petalsLeft");
		this.lastPetalsLeft = tagCompound.getInteger("lastPetalsLeft");
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
}
