package com.gildedgames.aether.common.entities.living;

import com.gildedgames.aether.common.blocks.BlocksAether;
import com.gildedgames.aether.common.items.ItemsAether;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
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

	public EntityCarrionSprout(World world)
	{
		super(world);

		this.setSize(0.5F, 1.4F);

		this.spawnableBlock = BlocksAether.aether_grass;

		this.tasks.addTask(0, new EntityAIWatchClosest(this, EntityPlayer.class, 8));
	}

	@Override
	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();

		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(10);

		this.setMaxSproutSize(8);
		this.setSproutSize(1 + this.rand.nextInt(this.getMaxSproutSize()));
	}

	@Override
	public void entityInit()
	{
		super.entityInit();

		this.dataManager.register(SIZE, 0);
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt)
	{
		super.writeToNBT(nbt);

		nbt.setInteger("size", this.getSproutSize());
		nbt.setInteger("maxSize", this.getMaxSproutSize());

		return nbt;
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt)
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
			BlockPos beneathPos = new BlockPos(this.posX, this.getEntityBoundingBox().minY - 0.1D, this.posZ);
			Block blockBeneath = this.worldObj.getBlockState(beneathPos).getBlock();

			if (blockBeneath != BlocksAether.aether_grass && blockBeneath != BlocksAether.aether_dirt)
			{
				this.setHealth(0);
			}
		}
	}

	@Override
	public void onLivingUpdate()
	{
		if (!this.isFullyGrown() && this.ticksExisted % 800 == 0)
		{
			this.setSproutSize(this.getSproutSize() + 2);
		}
	}

	@Override
	protected int getItemQuantityDropped()
	{
		return (this.getSproutSize() / 3) + 1;
	}

	@Override
	protected Item getDropItem()
	{
		return ItemsAether.wyndberry;
	}

	public boolean isFullyGrown()
	{
		return (this.getSproutSize() >= this.getMaxSproutSize());
	}

	public int getMaxSproutSize()
	{
		return this.maxSproutSize;
	}

	public void setMaxSproutSize(int x)
	{
		this.maxSproutSize = x;
	}

	public int getSproutSize()
	{
		return this.dataManager.get(SIZE);
	}

	public void setSproutSize(int newSize)
	{
		this.dataManager.set(SIZE, newSize);

		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(8f + (newSize * 1.5f));
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
	public void knockBack(Entity entityIn, float distance, double motionX, double motionY)
	{
	} // remove player damage knock-back

	@Override
	public EntityAgeable createChild(EntityAgeable ageable)
	{
		return null;
	}
}
