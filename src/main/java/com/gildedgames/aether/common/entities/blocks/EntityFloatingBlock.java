package com.gildedgames.aether.common.entities.blocks;

import com.gildedgames.aether.common.blocks.util.BlockFloating;
import com.gildedgames.aether.common.entities.EntityTypesAether;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.particle.Particle;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MoverType;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.play.server.SSpawnObjectPacket;
import net.minecraft.particles.BlockParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.api.distmarker.Dist;

public class EntityFloatingBlock extends Entity
{
	private static final DataParameter<Integer> BLOCK_STATE = new DataParameter<>(20, DataSerializers.VARINT);

	private boolean hasActivated = false;


	public EntityFloatingBlock(World world, double x, double y, double z, BlockState state)
	{
		this(EntityTypesAether.FLOATING_BLOCK, world);

		this.setBlockState(state);
		this.setPosition(x, y, z);

		this.prevPosX = x;
		this.prevPosY = y;
		this.prevPosZ = z;
	}

	public EntityFloatingBlock(EntityType<? extends EntityFloatingBlock> type, World world)
	{
		super(type, world);
	}

	@Override
	protected void registerData()
	{
		this.dataManager.register(BLOCK_STATE, -1);
	}

	@Override
	public void tick()
	{
		// Destroys the source block, since deleting a neighboring block in the actual block class
		// causes a infinite loop of updates.

		if (!this.world.isRemote() && !this.hasActivated)
		{
			BlockPos pos = new BlockPos(this);

			if (this.world.getBlockState(pos).getBlock() == this.getBlockState().getBlock())
			{
				this.world.removeBlock(pos, false);
			}
			else
			{
				this.remove();
			}

			this.hasActivated = true;
		}

		if (this.ticksExisted > 200)
		{
			this.remove();
		}
		else
		{
			this.prevPosX = this.posX;
			this.prevPosY = this.posY;
			this.prevPosZ = this.posZ;

			this.setMotion(this.getMotion().getX(), this.getMotion().getY() + 0.04D, this.getMotion().getZ());

			this.move(MoverType.SELF, this.getMotion());

			this.setMotion(this.getMotion().mul(0.98D, 0.98D, 0.98D));

			BlockPos pos = new BlockPos(this);

			if (!BlockFloating.canFallInto(this.world, pos.up()))
			{
				if (!this.world.isRemote())
				{
					this.world.setBlockState(pos, this.getBlockState());

					this.remove();
				}

				this.posX = pos.getX() + 0.5D;
				this.posY = pos.getY();
				this.posZ = pos.getZ() + 0.5D;
			}

			if (this.world.isAirBlock(pos.down()) && this.world.isRemote())
			{
				int count = MathHelper.floor(this.getMotion().getY() / 0.15D);

				if (count > 5)
				{
					count = 5;
				}

				for (int i = 0; i < count; i++)
				{
					this.world.addParticle(new BlockParticleData(ParticleTypes.BLOCK, this.getBlockState()),
							this.posX - 0.5D + (this.world.rand.nextDouble()),
							this.posY - 0.5D,
							this.posZ - 0.5D + (this.world.rand.nextDouble()), 0.0D, 0.0D, 0.0D);
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
		return this.isAlive();
	}

	@Override
	public void writeAdditional(CompoundNBT nbt)
	{
		nbt.putInt("State", Block.getStateId(this.getBlockState()));
		nbt.putInt("TicksExisted", this.ticksExisted);
	}

	@Override
	public void readAdditional(CompoundNBT nbt)
	{
		this.setBlockState(Block.getStateById(nbt.getInt("State")));

		this.ticksExisted = nbt.getInt("TicksExisted");
		this.hasActivated = this.ticksExisted > 1;
	}

	public BlockState getBlockState()
	{
		return Block.getStateById(this.dataManager.get(BLOCK_STATE));
	}

	public void setBlockState(BlockState state)
	{
		this.dataManager.set(BLOCK_STATE, Block.getStateId(state));
	}

	@Override
	public IPacket<?> createSpawnPacket() {
		return new SSpawnObjectPacket(this, Block.getStateId(this.getBlockState()));
	}
}
