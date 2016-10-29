package com.gildedgames.aether.common.entities.dungeon.labyrinth;

import com.gildedgames.aether.common.entities.EntitiesAether;
import com.gildedgames.aether.common.entities.util.AetherSpawnEggInfo;
import com.gildedgames.aether.common.items.ItemsAether;
import com.gildedgames.aether.common.items.misc.ItemAetherSpawnEgg;
import com.gildedgames.aether.common.util.EntityUtil;
import com.gildedgames.aether.common.util.TickTimer;
import com.gildedgames.aether.common.world.dungeon.DungeonDefinitions;
import com.gildedgames.util.core.nbt.NBTHelper;
import com.google.common.collect.Lists;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;

public class EntityProductionLine extends EntityCreature
{

	private boolean broken;

	private TickTimer spawningTimer = new TickTimer();

	private List<UUID> spawnedUUIDs = Lists.newArrayList();

	public EntityProductionLine(World worldIn)
	{
		super(worldIn);

		this.setSize(1.0F, 1.0F);
	}

	public boolean isBroken()
	{
		return this.broken;
	}

	@Override
	protected void entityInit()
	{
		super.entityInit();
	}

	@Override
	public void playLivingSound()
	{

	}

	@Override
	protected void playStepSound(BlockPos pos, Block blockIn)
	{

	}

	@Override
	public boolean canTriggerWalking()
	{
		return false;
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound tag)
	{
		super.writeEntityToNBT(tag);

		tag.setBoolean("broken", this.broken);
		NBTHelper.fullySerialize("spawningTimer", this.spawningTimer, tag);

		tag.setInteger("spawnedSize", this.spawnedUUIDs.size());

		int i = 0;

		for (UUID uuid : this.spawnedUUIDs)
		{
			tag.setUniqueId("spawned" + i++, uuid);
		}
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound tag)
	{
		super.readEntityFromNBT(tag);

		this.broken = tag.getBoolean("broken");
		this.spawningTimer = NBTHelper.fullyDeserialize("spawningTimer", tag);

		int spawnedSize = tag.getInteger("spawnedSize");

		for (int i = 0; i < spawnedSize; i++)
		{
			UUID uuid = tag.getUniqueId("spawned" + i);

			this.spawnedUUIDs.add(uuid);
		}
	}

	@Override
	public void onUpdate()
	{
		this.setHealth(Math.max(1.0F, this.getHealth()));

		this.spawningTimer.tick();

		if (!this.broken && !this.worldObj.isRemote && !this.isDead && this.spawningTimer.isMultipleOfTicks(60))
		{
			if (this.spawnedUUIDs.size() < 3)
			{
				Entity entity = DungeonDefinitions.SLIDERS_LABYRINTH.createRandomMob(this.worldObj, this.worldObj.rand);

				int scatterX = this.rand.nextBoolean() ? 1 : -1;
				int scatterZ = this.rand.nextBoolean() ? 1 : -1;

				entity.setPosition(this.posX + scatterX, this.posY, this.posZ + scatterZ);

				this.worldObj.spawnEntityInWorld(entity);

				this.spawnedUUIDs.add(entity.getUniqueID());
			}
			else
			{
				List<UUID> toRemove = Lists.newArrayList();

				for (UUID uuid : this.spawnedUUIDs)
				{
					Entity entity = EntityUtil.getEntityFromUUID(this.worldObj, uuid);

					if (entity == null || entity.isDead)
					{
						toRemove.add(uuid);
					}
				}

				this.spawnedUUIDs.removeAll(toRemove);
			}
		}

		this.motionX = 0.0F;
		this.motionZ = 0.0F;
		this.motionY = 0.0F;

		if (this.getHealth() <= 1.0F)
		{
			this.broken = true;
		}

		if (this.getHealth() > 1.0F)
		{
			this.broken = false;
		}

		super.onUpdate();
	}

	@Override
	public boolean canDespawn()
	{
		return false;
	}

	@Nullable
	protected SoundEvent getHurtSound()
	{
		return SoundEvents.ENTITY_GENERIC_HURT;
	}

	@Nullable
	protected SoundEvent getDeathSound()
	{
		return SoundEvents.ENTITY_GENERIC_DEATH;
	}

	@Override
	public boolean canBePushed()
	{
		return false;
	}

	@Override
	public boolean canBeCollidedWith()
	{
		return !this.isDead;
	}

	@Override
	protected void jump()
	{

	}

	@Override
	public AxisAlignedBB getCollisionBoundingBox()
	{
		return this.getEntityBoundingBox();
	}

	@Override
	public AxisAlignedBB getCollisionBox(Entity entity)
	{
		return this.getEntityBoundingBox();
	}

}
