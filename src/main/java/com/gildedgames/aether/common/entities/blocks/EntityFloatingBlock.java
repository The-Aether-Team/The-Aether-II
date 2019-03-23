package com.gildedgames.aether.common.entities.blocks;

import com.gildedgames.aether.common.blocks.util.BlockFloating;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.MoverType;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class EntityFloatingBlock extends Entity
{
	private static final DataParameter<Integer> BLOCK_NAME = new DataParameter<>(20, DataSerializers.VARINT);

	private static final DataParameter<Byte> BLOCK_METADATA = new DataParameter<>(21, DataSerializers.BYTE);

	private boolean hasActivated = false;

	public EntityFloatingBlock(World world)
	{
		super(world);

		this.setSize(0.98F, 0.98F);
		this.motionX = 0.0D;
		this.motionY = 0.0D;
		this.motionZ = 0.0D;
	}

	public EntityFloatingBlock(World world, double x, double y, double z, IBlockState state)
	{
		this(world);

		this.setBlockState(state);

		this.setPosition(x, y, z);

		this.prevPosX = x;
		this.prevPosY = y;
		this.prevPosZ = z;
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
		// Destroys the source block, since deleting a neighboring block in the actual block class
		// causes a infinite loop of updates.

		if (!this.world.isRemote && !this.hasActivated)
		{
			BlockPos pos = new BlockPos(this);

			if (this.world.getBlockState(pos).getBlock() == this.getBlockState().getBlock())
			{
				this.world.setBlockToAir(pos);
			}
			else
			{
				this.setDead();
			}

			this.hasActivated = true;
		}

		if (this.ticksExisted > 200)
		{
			this.setDead();
		}
		else
		{
			this.prevPosX = this.posX;
			this.prevPosY = this.posY;
			this.prevPosZ = this.posZ;

			this.motionY += 0.04D;

			this.move(MoverType.SELF, this.motionX, this.motionY, this.motionZ);

			this.motionX *= 0.98D;
			this.motionY *= 0.98D;
			this.motionZ *= 0.98D;

			BlockPos pos = new BlockPos(this);

			if (!BlockFloating.canFallInto(this.world, pos.up()))
			{
				if (!this.world.isRemote)
				{
					this.world.setBlockState(pos, this.getBlockState());

					this.setDead();
				}

				this.posX = pos.getX() + 0.5D;
				this.posY = pos.getY();
				this.posZ = pos.getZ() + 0.5D;
			}

			if (this.world.isAirBlock(pos.down()) && this.world.isRemote)
			{
				int count = MathHelper.floor(this.motionY / 0.15D);

				if (count > 5)
				{
					count = 5;
				}

				for (int i = 0; i < count; i++)
				{
					this.world.spawnParticle(EnumParticleTypes.BLOCK_DUST,
							this.posX - 0.5D + (this.world.rand.nextDouble()),
							this.posY - 0.5D,
							this.posZ - 0.5D + (this.world.rand.nextDouble()), 0.0D, 0.0D, 0.0D,
							Block.getStateId(this.getBlockState()));
				}
			}
		}
	}

	@Override
	@OnlyIn(Dist.CLIENT)
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
		int meta = (int) this.dataManager.get(BLOCK_METADATA);

		return block.getStateFromMeta(meta);
	}

	public void setBlockState(IBlockState state)
	{
		Block block = state.getBlock();

		this.dataManager.set(BLOCK_NAME, Block.REGISTRY.getIDForObject(block));
		this.dataManager.set(BLOCK_METADATA, (byte) block.getMetaFromState(state));
	}
}
