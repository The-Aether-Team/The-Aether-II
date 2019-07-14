package com.gildedgames.aether.common.entities.blocks;

import com.gildedgames.aether.api.chunk.IPlacementFlagCapability;
import com.gildedgames.aether.api.registrar.CapabilitiesAether;
import com.gildedgames.aether.common.entities.EntityTypesAether;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.play.server.SSpawnObjectPacket;
import net.minecraft.util.Direction;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.ServerWorld;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.util.LazyOptional;

import java.util.List;

public class EntityMovingBlock extends Entity
{
	private static final DataParameter<Integer> BLOCK_STATE = new DataParameter<>(20, DataSerializers.VARINT);

	private PlayerEntity holdingPlayer;

	private boolean hasActivated = false;

	private boolean allowDoubleDrops = true;

	private int ticksStuck = 0, ticksFalling = 0;

	public EntityMovingBlock(final EntityType<? extends EntityMovingBlock> type, final World world)
	{
		super(type, world);
	}

	public EntityMovingBlock(final World world, final double x, final double y, final double z, final BlockState state)
	{
		this(EntityTypesAether.MOVING_BLOCK, world);

		this.setBlockState(state);

		this.setPosition(x, y, z);

		this.prevPosX = x;
		this.prevPosY = y;
		this.prevPosZ = z;
	}

	@Override
	protected void registerData()
	{
		this.dataManager.register(BLOCK_STATE, -1);
	}

	@Override
	public void tick()
	{
		super.tick();

		this.prevRotationYaw = this.rotationYaw;
		this.prevRotationPitch = this.rotationPitch;

		if (this.rotationYaw > 360f)
		{
			this.rotationYaw -= 360f;
		}

		this.rotationYaw += this.getMotion().getZ() * 14f;
		this.rotationPitch += -this.getMotion().getX() * 14f;

		this.rotationYaw *= 0.9f;
		this.rotationPitch *= 0.9f;

		if (!this.world.isRemote() && !this.hasActivated)
		{
			final BlockPos pos = new BlockPos(this);

			if (this.world.getBlockState(pos).getBlock() == this.getBlockState().getBlock())
			{
				this.allowDoubleDrops = ((Chunk) this.world.getChunk(pos))
						.getCapability(CapabilitiesAether.CHUNK_PLACEMENT_FLAG, Direction.UP)
						.map((data) -> !data.isModified(pos))
						.orElseThrow(IllegalStateException::new);

				this.world.removeBlock(pos, false);
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

		this.move(MoverType.SELF, this.getMotion());

		if (!this.world.isRemote())
		{
			if (this.holdingPlayer != null)
			{
				if (this.ticksStuck > 30 || this.getDistance(this.holdingPlayer) > 6.0D)
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

			this.setMotion(this.getMotion().getX(), this.getMotion().getY() - 0.045D, this.getMotion().getZ());

			if (this.onGround)
			{
				// Try to snap into a coordinate on the ground
				this.setMotion(
						this.getMotion().getX() + (pos.getX() - this.posX + 0.45D) * 0.15D,
						this.getMotion().getY(),
						this.getMotion().getZ() + (pos.getZ() - this.posZ + 0.45D) * 0.15D
				);

				final BlockState state = this.world.getBlockState(pos);

				// We won't be able to land, reject and try to throw ourselves somewhere!
				if (state.getBlock() != Blocks.AIR && !state.isNormalCube(this.world, pos) && !state.getMaterial().isReplaceable())
				{
					this.setMotion(this.getMotion().mul(-0.15D + (this.rand.nextDouble() * 0.3D), 0.5D,  -0.15D + (this.rand.nextDouble() * 0.3D)));
				}

				final double distanceFromCenter = pos.distanceSq(this.posX + 0.45D, this.posY, this.posZ + 0.45D, true);

				if (this.getMotion().length() <= 0.05D && distanceFromCenter <= 2.0D)
				{
					// We've stopped moving
					if (!this.world.isRemote())
					{
						final BlockState replacingState = this.world.getBlockState(pos);

						if (!replacingState.getMaterial().isReplaceable())
						{
							this.destroy();

							return;
						}

						this.world.destroyBlock(pos, true);

						this.world.setBlockState(pos, this.getBlockState());
						this.world.notifyNeighborsOfStateChange(pos, this.getBlockState().getBlock());

						if (!this.allowDoubleDrops)
						{
							((Chunk) this.world.getChunk(pos))
									.getCapability(CapabilitiesAether.CHUNK_PLACEMENT_FLAG, Direction.UP)
									.ifPresent((data) -> data.markModified(pos));
						}

						this.remove();

						return;
					}
				}

				this.setMotion(this.getMotion().mul(0.8D, 0.94D, 0.8D));
			}
			else
			{
				this.setMotion(this.getMotion().mul(0.8D, 0.94D, 0.8D));
			}
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
			this.setMotion(this.getMotion().mul(0.8D, 0.8D, 0.8D));

			// Move the block towards where the player is looking
			this.setMotion(
					this.getMotion().getX() + ((toX - this.posX) * 0.1D),
					this.getMotion().getY() + ((toY - this.posY) * 0.1D),
					this.getMotion().getZ() + ((toZ - this.posZ) * 0.1D)
			);
		}
	}

	private void destroy()
	{
		this.remove();

		if (!this.world.isRemote())
		{
			final List<ItemStack> drops = Block.getDrops(this.getBlockState(), (ServerWorld) this.world, new BlockPos(this), null, this, ItemStack.EMPTY);

			for (final ItemStack stack : drops)
			{
				Block.spawnAsEntity(this.world, new BlockPos(this), stack);
			}
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
		return this.isAlive();
	}

	@Override
	protected void readAdditional(CompoundNBT nbt)
	{
		this.setBlockState(Block.getStateById(nbt.getInt("State")));

		this.ticksFalling = nbt.getInt("TicksFalling");
		this.allowDoubleDrops = nbt.getBoolean("AllowDoubleDrops");

		this.hasActivated = this.ticksExisted > 1;
	}

	@Override
	protected void writeAdditional(CompoundNBT compound)
	{
		compound.putInt("State", Block.getStateId(this.getBlockState()));
		compound.putInt("TicksFalling", this.ticksFalling);
		compound.putBoolean("AllowDoubleDrops", this.allowDoubleDrops);
	}

	public BlockState getBlockState()
	{
		return Block.getStateById(this.dataManager.get(BLOCK_STATE));
	}

	public void setBlockState(final BlockState state)
	{
		this.dataManager.set(BLOCK_STATE, Block.getStateId(state));
	}

	@Override
	public IPacket<?> createSpawnPacket() {
		return new SSpawnObjectPacket(this, Block.getStateId(this.getBlockState()));
	}
}
