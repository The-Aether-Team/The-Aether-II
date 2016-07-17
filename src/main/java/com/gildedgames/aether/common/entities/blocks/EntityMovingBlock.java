package com.gildedgames.aether.common.entities.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityMovingBlock extends Entity
{
	private static final DataParameter<Integer> BLOCK_NAME = new DataParameter<>(20, DataSerializers.VARINT);

	private static final DataParameter<Byte> BLOCK_METADATA = new DataParameter<>(21, DataSerializers.BYTE);

	private EntityPlayer holdingPlayer;

	private boolean hasActivated = false;

	private int invalidTicks = 0;

	private boolean isFalling;

	public EntityMovingBlock(World world)
	{
		super(world);

		this.setSize(0.9f, 0.9f);

		this.motionX = 0.0D;
		this.motionY = 0.0D;
		this.motionZ = 0.0D;
	}

	public EntityMovingBlock(World world, double x, double y, double z, IBlockState state, EntityPlayer picker)
	{
		this(world);

		this.setBlockState(state);

		this.setPosition(x, y, z);

		this.prevPosX = x;
		this.prevPosY = y;
		this.prevPosZ = z;

		this.holdingPlayer = picker;
	}

	@Override
	protected void entityInit()
	{
		this.dataManager.register(BLOCK_NAME, 2);
		this.dataManager.register(BLOCK_METADATA, (byte) 4);
	}

	@Override
	public void onUpdate()
	{
		super.onUpdate();

		if (!this.worldObj.isRemote && !this.hasActivated)
		{
			BlockPos pos = new BlockPos(this);

			if (this.worldObj.getBlockState(pos).getBlock() == this.getBlockState().getBlock())
			{
				this.worldObj.setBlockToAir(pos);
			}
			else
			{
				this.setDead();
			}

			this.hasActivated = true;
		}

		this.prevPosX = this.posX;
		this.prevPosY = this.posY;
		this.prevPosZ = this.posZ;

		this.moveEntity(this.motionX, this.motionY, this.motionZ);

		if (!this.worldObj.isRemote)
		{
			if (this.holdingPlayer == null)
			{
				this.drop();

				return;
			}

			if (!this.isFalling)
			{
				if (this.isCollided)
				{
					this.invalidTicks++;
				}
				else
				{
					this.invalidTicks = 0;
				}

				if (this.invalidTicks > 30 || this.getDistance(this.holdingPlayer.posX, this.holdingPlayer.posY, this.holdingPlayer.posZ) > 6.0D)
				{
					this.drop();
				}
			}

			this.updatePosition();
		}
	}

	public void updatePosition()
	{
		if (this.rotationYaw > 360f)
		{
			this.rotationYaw -= 360f;
		}

		this.rotationYaw += this.motionZ * 10f;
		this.rotationPitch += -this.motionX * 10f;

		this.rotationYaw *= 0.9f;
		this.rotationPitch *= 0.9f;

		if (this.isFalling)
		{
			this.motionY -= 0.04D;

			BlockPos pos = new BlockPos(this);

			if (this.onGround)
			{
				if (this.motionX + this.motionZ <= 0.03D)
				{
					// We've stopped moving

					if (!this.worldObj.isRemote)
					{
						this.worldObj.setBlockState(pos, this.getBlockState());

						this.setDead();
					}
				}
				else
				{
					// Try to move towards
					// Apply friction
					this.motionX *= 0.8D;
					this.motionZ *= 0.8D;
				}
			}
			else
			{
				this.motionX *= 0.95D;
				this.motionZ *= 0.95D;
			}

			this.motionY *= 0.94D;
		}
		else
		{
			// Get where the player is looking at
			Vec3d look = this.holdingPlayer.getLookVec();

			float distance = 2.5f;

			// Calculate the block's destination in front of the player
			double toX = this.holdingPlayer.posX + (look.xCoord * distance);
			double toY = this.holdingPlayer.posY + (look.yCoord * distance) + 1f;
			double toZ = this.holdingPlayer.posZ + (look.zCoord * distance);

			// Slow down our block's movement to simulate weight
			this.motionX *= 0.8D;
			this.motionY *= 0.8D;
			this.motionZ *= 0.8D;

			// Move the block towards where the player is looking
			this.motionX += (toX - this.posX) * 0.1D;
			this.motionY += (toY - this.posY) * 0.1D;
			this.motionZ += (toZ - this.posZ) * 0.1D;
		}
	}

	private void moveTowards(Vec3d vec)
	{

	}

	public void drop()
	{
		this.isFalling = true;
	}

	public boolean isFalling()
	{
		return this.isFalling;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean canRenderOnFire()
	{
		return false;
	}

	@Override
	protected boolean canTriggerWalking()
	{
		return false;
	}

	@Override
	public boolean canBeCollidedWith()
	{
		return !this.isDead;
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound compound)
	{
		Block block = Block.getBlockFromName(compound.getString("Block"));

		this.setBlockState(block.getStateFromMeta(compound.getByte("BlockState")));
		this.ticksExisted = compound.getInteger("TicksExisted");

		this.hasActivated = this.ticksExisted > 1;
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound compound)
	{
		IBlockState state = this.getBlockState();

		Block block = state.getBlock();

		compound.setString("Block", Block.REGISTRY.getNameForObject(block).toString());
		compound.setByte("BlockState", (byte) block.getMetaFromState(state));
		compound.setInteger("TicksExisted", this.ticksExisted);
	}

	public IBlockState getBlockState()
	{
		Block block = Block.getBlockById(this.dataManager.get(BLOCK_NAME));
		int meta = this.dataManager.get(BLOCK_METADATA);

		return block.getStateFromMeta(meta);
	}

	public void setBlockState(IBlockState state)
	{
		Block block = state.getBlock();

		this.dataManager.set(BLOCK_NAME, Block.REGISTRY.getIDForObject(block));
		this.dataManager.set(BLOCK_METADATA, (byte) block.getMetaFromState(state));
	}
}
