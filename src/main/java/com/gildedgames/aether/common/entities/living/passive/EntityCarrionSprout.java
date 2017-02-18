package com.gildedgames.aether.common.entities.living.passive;

import com.gildedgames.aether.common.blocks.BlocksAether;
import com.gildedgames.aether.common.registry.content.LootTablesAether;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityCarrionSprout extends EntityAetherAnimal
{
	private static final DataParameter<Integer> SIZE = new DataParameter<>(13, DataSerializers.VARINT);

	private int maxSproutSize;

	@SideOnly(Side.CLIENT)
	public float sinage, prevSinage;

	public EntityCarrionSprout(final World world)
	{
		super(world);

		this.setSize(0.5F, 1.5F);

		this.spawnableBlock = BlocksAether.aether_grass;

		this.tasks.addTask(0, new EntityAIWatchClosest(this, EntityPlayer.class, 8));
	}

	@Override
	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();

		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(10.0D);

		this.setMaxSproutSize(8);

		final int min = 3;

		this.setSproutSize(min + this.rand.nextInt(this.getMaxSproutSize() - min));
	}

	@Override
	public void entityInit()
	{
		super.entityInit();

		this.dataManager.register(SIZE, 0);
	}

	@Override
	public NBTTagCompound writeToNBT(final NBTTagCompound nbt)
	{
		super.writeToNBT(nbt);

		nbt.setInteger("size", this.getSproutSize());
		nbt.setInteger("maxSize", this.getMaxSproutSize());

		return nbt;
	}

	@Override
	public void readFromNBT(final NBTTagCompound nbt)
	{
		super.readFromNBT(nbt);

		this.setSproutSize(nbt.getInteger("size"));
		this.setMaxSproutSize(nbt.getInteger("maxSize"));
	}

	@Override
	public void onUpdate()
	{
		super.onUpdate();

		if (this.worldObj.isRemote)
		{
			this.clientUpdate();
		}
		else
		{
			if (!this.canStayHere(new BlockPos(this)))
			{
				this.setHealth(0);
			}
		}
	}

	private boolean canStayHere(final BlockPos pos)
	{
		if (!this.worldObj.isAirBlock(pos))
		{
			return false;
		}

		final Block rootBlock = this.worldObj.getBlockState(pos.down()).getBlock();

		if (rootBlock != BlocksAether.aether_grass && rootBlock != BlocksAether.aether_dirt)
		{
			return false;
		}

		return true;
	}

	@Override
	public void onLivingUpdate()
	{
		this.motionX = 0.0F;
		this.motionZ = 0.0F;

		if (this.ticksExisted == 0)
		{
			this.setRotation(this.worldObj.rand.nextFloat() * 360F, 0.0F);
			this.renderYawOffset = this.rotationYaw;
		}

		super.onLivingUpdate();

		if (!this.isFullyGrown() && this.ticksExisted % 800 == 0)
		{
			this.setSproutSize(this.getSproutSize() + 2);
		}
	}

	public boolean isFullyGrown()
	{
		return (this.getSproutSize() >= this.getMaxSproutSize());
	}

	public int getMaxSproutSize()
	{
		return this.maxSproutSize;
	}

	public void setMaxSproutSize(final int x)
	{
		this.maxSproutSize = x;
	}

	public int getSproutSize()
	{
		return this.dataManager.get(SIZE);
	}

	public void setSproutSize(final int newSize)
	{
		this.dataManager.set(SIZE, newSize);

		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(8f + (newSize * 1.5f));
	}

	@Override
	protected ResourceLocation getLootTable()
	{
		return LootTablesAether.ENTITY_CARRION_SPROUT;
	}

	@SideOnly(Side.CLIENT)
	public void clientUpdate()
	{
		this.prevSinage = this.sinage;

		if (this.hurtTime > 0)
		{
			this.sinage += 0.9F;
		}
		else
		{
			this.sinage += 0.15F;
		}

		if (this.sinage > 3.141593F * 2F)
		{
			this.sinage -= (3.141593F * 2F);
		}
	}

	@Override
	public void jump()
	{
	} // remove jump

	@Override
	public void knockBack(final Entity entityIn, final float distance, final double motionX, final double motionY)
	{
	} // remove player damage knock-back

	@Override
	public EntityAgeable createChild(final EntityAgeable ageable)
	{
		return null;
	}
}
