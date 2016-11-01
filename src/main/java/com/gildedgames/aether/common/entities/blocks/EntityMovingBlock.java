package com.gildedgames.aether.common.entities.blocks;

import com.gildedgames.aether.api.capabilites.AetherCapabilities;
import com.gildedgames.aether.api.capabilites.chunk.IPlacementFlagCapability;
import com.gildedgames.aether.common.world.chunk.hooks.capabilities.ChunkAttachmentCapability;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class EntityMovingBlock extends Entity
{
	private static final DataParameter<Integer> BLOCK_NAME = new DataParameter<>(20, DataSerializers.VARINT);

	private static final DataParameter<Byte> BLOCK_METADATA = new DataParameter<>(21, DataSerializers.BYTE);

	private EntityPlayer holdingPlayer;

	private boolean hasActivated = false;

	private boolean allowDoubleDrops = true;

	private int ticksStuck = 0, ticksFalling = 0;

	public EntityMovingBlock(World world)
	{
		super(world);

		this.setSize(0.9f, 0.9f);

		this.motionX = 0.0D;
		this.motionY = 0.0D;
		this.motionZ = 0.0D;
	}

	public EntityMovingBlock(World world, double x, double y, double z, IBlockState state)
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
		super.onUpdate();

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

		if (!this.worldObj.isRemote && !this.hasActivated)
		{
			BlockPos pos = new BlockPos(this);

			if (this.worldObj.getBlockState(pos).getBlock() == this.getBlockState().getBlock())
			{
				IPlacementFlagCapability data = ChunkAttachmentCapability.get(this.worldObj).getAttachment(new ChunkPos(pos), AetherCapabilities.CHUNK_PLACEMENT_FLAG);

				this.allowDoubleDrops = !data.isMarked(pos);

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
			if (this.holdingPlayer != null)
			{
				if (this.ticksStuck > 30 || this.getDistance(this.holdingPlayer.posX, this.holdingPlayer.posY, this.holdingPlayer.posZ) > 6.0D)
				{
					this.setHoldingPlayer(null);
				}

				if (this.isCollided)
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

	public void setHoldingPlayer(EntityPlayer player)
	{
		this.holdingPlayer = player;
	}

	public void updatePosition()
	{
		if (this.holdingPlayer == null)
		{
			this.ticksFalling++;

			BlockPos pos = new BlockPos(this);

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

				double distanceFromCenter = pos.distanceSq(this.posX + 0.45D, this.posY, this.posZ + 0.45D);

				if ((this.motionY + this.motionX + this.motionZ) <= 0.04D && distanceFromCenter <= 2.0D)
				{
					// We've stopped moving
					if (!this.worldObj.isRemote)
					{
						IBlockState replacingState = this.worldObj.getBlockState(pos);

						if (!replacingState.getBlock().isReplaceable(this.worldObj, pos)) {
							this.destroy();

							return;
						}

						this.worldObj.destroyBlock(pos, true);

						this.worldObj.setBlockState(pos, this.getBlockState());
						this.worldObj.notifyNeighborsOfStateChange(pos, this.getBlockState().getBlock());

						if (!this.allowDoubleDrops)
						{
							IPlacementFlagCapability data = ChunkAttachmentCapability.get(this.worldObj).getAttachment(new ChunkPos(pos), AetherCapabilities.CHUNK_PLACEMENT_FLAG);

							data.mark(pos);
						}

						this.setDead();

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

	private void destroy() {
		this.setDead();

		BlockPos pos = new BlockPos(this);

		IBlockState state = this.getBlockState();

		List<ItemStack> drops = state.getBlock().getDrops(this.worldObj, pos, state, 0);

		for (ItemStack stack : drops)
		{
			Block.spawnAsEntity(this.worldObj, pos, stack);
		}
	}

	public boolean isFalling()
	{
		return this.holdingPlayer == null;
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
		Block block = Block.getBlockById(compound.getInteger("Block"));

		this.setBlockState(block.getStateFromMeta(compound.getByte("BlockMeta")));
		this.ticksFalling = compound.getInteger("TicksFalling");
		this.allowDoubleDrops = compound.getBoolean("AllowDoubleDrops");

		this.hasActivated = this.ticksExisted > 1;
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound compound)
	{
		IBlockState state = this.getBlockState();

		Block block = state.getBlock();

		compound.setInteger("Block", Block.REGISTRY.getIDForObject(block));
		compound.setByte("BlockMeta", (byte) block.getMetaFromState(state));
		compound.setInteger("TicksFalling", this.ticksFalling);
		compound.setBoolean("AllowDoubleDrops", this.allowDoubleDrops);
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
