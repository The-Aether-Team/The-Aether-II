package com.gildedgames.aether.common.entities.blocks;

import com.gildedgames.aether.api.chunk.IPlacementFlagCapability;
import com.gildedgames.aether.api.registrar.CapabilitiesAether;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.util.Direction;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class EntityMovingBlock extends Entity
{
	private static final DataParameter<Integer> BLOCK_NAME = new DataParameter<>(20, DataSerializers.VARINT);

	private static final DataParameter<Byte> BLOCK_METADATA = new DataParameter<>(21, DataSerializers.BYTE);

	private PlayerEntity holdingPlayer;

	private boolean hasActivated = false;

	private boolean allowDoubleDrops = true;

	private int ticksStuck = 0, ticksFalling = 0;

	public EntityMovingBlock(final World world)
	{
		super(world);

		this.setSize(0.9f, 0.9f);

		this.motionX = 0.0D;
		this.motionY = 0.0D;
		this.motionZ = 0.0D;
	}

	public EntityMovingBlock(final World world, final double x, final double y, final double z, final BlockState state)
	{
		this(world);

		this.setBlockState(state);

		this.setPosition(x, y, z);

		this.prevPosX = x;
		this.prevPosY = y;
		this.prevPosZ = z;
	}

	@Override
	protected void registerData()
	{
		this.dataManager.register(BLOCK_NAME, 2);
		this.dataManager.register(BLOCK_METADATA, (byte) 4);
	}

	@Override
	public void livingTick()
	{
		super.livingTick();

		this.prevRotationYaw = this.rotationYaw;
		this.prevRotationPitch = this.rotationPitch;

		if (this.rotationYaw > 360f)
		{
			this.rotationYaw -= 360f;
		}

		this.rotationYaw += this.motionZ * 14f;
		this.rotationPitch += -this.motionX * 14f;

		this.rotationYaw *= 0.9f;
		this.rotationPitch *= 0.9f;

		if (!this.world.isRemote() && !this.hasActivated)
		{
			final BlockPos pos = new BlockPos(this);

			if (this.world.getBlockState(pos).getBlock() == this.getBlockState().getBlock())
			{
				final IPlacementFlagCapability data = this.world.getChunk(pos)
						.getCapability(CapabilitiesAether.CHUNK_PLACEMENT_FLAG, Direction.UP);

				if (data != null)
				{
					this.allowDoubleDrops = !data.isModified(pos);

					this.world.removeBlock(pos, false);
				}
			}
			else
			{
				this.remove();
			}

			this.hasActivated = true;
		}

		this.prevPosX = this.posX;
		this.prevPosY = this.posY;
		this.prevPosZ = this.posZ;

		this.move(MoverType.SELF, this.motionX, this.motionY, this.motionZ);

		if (!this.world.isRemote())
		{
			if (this.holdingPlayer != null)
			{
				if (this.ticksStuck > 30
						|| this.getDistance(this.holdingPlayer.posX, this.holdingPlayer.posY, this.holdingPlayer.posZ) > 6.0D)
				{
					this.setHoldingPlayer(null);
				}

				if (this.collided)
				{
					this.ticksStuck++;
				}
				else
				{
					this.ticksStuck = 0;
				}
			}

			this.updatePosition();
		}
	}

	public void setHoldingPlayer(final PlayerEntity player)
	{
		this.holdingPlayer = player;
	}

	public void updatePosition()
	{
		if (this.holdingPlayer == null)
		{
			this.ticksFalling++;

			final BlockPos pos = new BlockPos(this);

			if (this.ticksFalling >= 160)
			{
				this.destroy();

				return;
			}

			this.motionY -= 0.045D;

			if (this.onGround)
			{
				// Try to snap into a coordinate on the ground
				this.motionX += (pos.getX() - this.posX + 0.45D) * 0.15D;
				this.motionZ += (pos.getZ() - this.posZ + 0.45D) * 0.15D;

				final BlockState state = this.world.getBlockState(pos);

				// We won't be able to land, reject and try to throw ourselves somewhere!
				if (state.getBlock() != Blocks.AIR && !state.isNormalCube() && !state.getBlock().isReplaceable(this.world, pos))
				{
					this.motionX = -0.15D + (this.rand.nextDouble() * 0.3D);
					this.motionY = 0.5D;
					this.motionZ = -0.15D + (this.rand.nextDouble() * 0.3D);
				}

				final double distanceFromCenter = pos.distanceSq(this.posX + 0.45D, this.posY, this.posZ + 0.45D);

				if ((this.motionY + this.motionX + this.motionZ) <= 0.04D && distanceFromCenter <= 2.0D)
				{
					// We've stopped moving
					if (!this.world.isRemote())
					{
						final BlockState replacingState = this.world.getBlockState(pos);

						if (!replacingState.getBlock().isReplaceable(this.world, pos))
						{
							this.destroy();

							return;
						}

						this.world.destroyBlock(pos, true);

						this.world.setBlockState(pos, this.getBlockState());
						this.world.notifyNeighborsOfStateChange(pos, this.getBlockState().getBlock(), false);

						if (!this.allowDoubleDrops)
						{
							final IPlacementFlagCapability data = this.world.getChunk(pos)
									.getCapability(CapabilitiesAether.CHUNK_PLACEMENT_FLAG, Direction.UP);

							if (data != null)
							{
								data.markModified(pos);
							}
						}

						this.remove();

						return;
					}
				}

				this.motionX *= 0.8D;
				this.motionZ *= 0.8D;
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
			this.ticksFalling = 0;

			// Get where the player is looking at
			final Vec3d look = this.holdingPlayer.getLookVec();

			final float distance = 2.5f;

			// Calculate the block's destination in front of the player
			final double toX = this.holdingPlayer.posX + (look.x * distance);
			final double toY = this.holdingPlayer.posY + (look.y * distance) + 1f;
			final double toZ = this.holdingPlayer.posZ + (look.z * distance);

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

	private void destroy()
	{
		this.remove();

		final BlockPos pos = new BlockPos(this);

		final BlockState state = this.getBlockState();

		final NonNullList<ItemStack> drops = NonNullList.create();

		state.getBlock().getDrops(drops, this.world, pos, state, 0);

		for (final ItemStack stack : drops)
		{
			Block.spawnAsEntity(this.world, pos, stack);
		}
	}

	public boolean isFalling()
	{
		return this.holdingPlayer == null;
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
	protected void readEntityFromNBT(final CompoundNBT compound)
	{
		final Block block = Block.getBlockById(compound.getInt("Block"));

		this.setBlockState(block.getStateFromMeta(compound.getByte("BlockMeta")));
		this.ticksFalling = compound.getInt("TicksFalling");
		this.allowDoubleDrops = compound.getBoolean("AllowDoubleDrops");

		this.hasActivated = this.ticksExisted > 1;
	}

	@Override
	protected void writeEntityToNBT(final CompoundNBT compound)
	{
		final BlockState state = this.getBlockState();

		final Block block = state.getBlock();

		compound.putInt("Block", Block.REGISTRY.getIDForObject(block));
		compound.putByte("BlockMeta", (byte) block.getMetaFromState(state));
		compound.putInt("TicksFalling", this.ticksFalling);
		compound.putBoolean("AllowDoubleDrops", this.allowDoubleDrops);
	}

	public BlockState getBlockState()
	{
		final Block block = Block.getBlockById(this.dataManager.get(BLOCK_NAME));

		final int meta = (int) this.dataManager.get(BLOCK_METADATA);

		return block.getStateFromMeta(meta);
	}

	public void setBlockState(final BlockState state)
	{
		final Block block = state.getBlock();

		this.dataManager.set(BLOCK_NAME, Block.REGISTRY.getIDForObject(block));
		this.dataManager.set(BLOCK_METADATA, (byte) block.getMetaFromState(state));
	}
}
