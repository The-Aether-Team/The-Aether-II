package com.gildedgames.aether.common.entities.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.List;

public class EntityFloatingBlock extends Entity
{
	private static final int BLOCK_NAME_ID = 20, BLOCK_STATE_ID = 21;

	private final List<ItemStack> drops = new ArrayList<>();

	private int inAirTicks;

	public EntityFloatingBlock(World world)
	{
		super(world);

		this.setSize(0.98F, 0.98F);
		this.motionX = 0.0D;
		this.motionY = 0.0D;
		this.motionZ = 0.0D;
	}

	public EntityFloatingBlock(World world, double x, double y, double z, IBlockState state, List<ItemStack> drops)
	{
		this(world);

		this.setBlockState(state);

		this.setPosition(x, y, z);

		this.prevPosX = x;
		this.prevPosY = y;
		this.prevPosZ = z;

		this.drops.addAll(drops);
	}

	@Override
	protected void entityInit()
	{
		this.dataWatcher.addObjectByDataType(BLOCK_STATE_ID, 2);
		this.dataWatcher.addObjectByDataType(BLOCK_NAME_ID, 4);
	}

	@Override
	public void onUpdate()
	{
		// Destroys the source block, since deleting a neighboring block in the actual block class
		// causes a infinite loop of updates.

		if (!this.worldObj.isRemote && this.inAirTicks++ == 0)
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
		}

		if (this.inAirTicks > 200)
		{
			this.setDead();

			if (!this.worldObj.isRemote)
			{
				if (this.worldObj.getGameRules().getBoolean("doTileDrops"))
				{
					for (ItemStack stack : this.drops)
					{
						EntityItem entityItem = new EntityItem(this.worldObj, this.posX, this.posY, this.posZ, stack);

						this.worldObj.spawnEntityInWorld(entityItem);
					}
				}
			}
		}
		else
		{
			this.prevPosX = this.posX;
			this.prevPosY = this.posY;
			this.prevPosZ = this.posZ;

			this.motionY += 0.04D;

			this.moveEntity(this.motionX, this.motionY, this.motionZ);

			this.motionX *= 0.98D;
			this.motionY *= 0.98D;
			this.motionZ *= 0.98D;

			BlockPos pos = new BlockPos(this);

			if (!this.worldObj.isAirBlock(pos.up()))
			{
				if (!this.worldObj.isRemote)
				{
					this.worldObj.setBlockState(pos, this.getBlockState());

					this.setDead();
				}

				this.posX = pos.getX() + 0.5D;
				this.posY = pos.getY();
				this.posZ = pos.getZ() + 0.5D;
			}

			if (this.worldObj.isAirBlock(pos.down()) && this.worldObj.isRemote)
			{
				int count = MathHelper.floor_double(this.motionY / 0.15D);

				if (count > 5)
				{
					count = 5;
				}

				for (int i = 0; i < count; i++)
				{
					this.worldObj.spawnParticle(EnumParticleTypes.BLOCK_DUST,
							this.posX - 0.5D + (this.worldObj.rand.nextDouble()),
							this.posY - 0.5D,
							this.posZ - 0.5D + (this.worldObj.rand.nextDouble()), 0.0D, 0.0D, 0.0D,
							Block.getStateId(this.getBlockState()));
				}
			}
		}
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
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound compound)
	{
		IBlockState state = this.getBlockState();
		Block block = state.getBlock();
		ResourceLocation resourceLocation = Block.blockRegistry.getNameForObject(block);

		compound.setString("Block", resourceLocation.toString());
		compound.setByte("BlockState", (byte) block.getMetaFromState(state));
		compound.setInteger("TicksExisted", this.ticksExisted);
	}

	public IBlockState getBlockState()
	{
		Block block = Block.getBlockFromName(this.dataWatcher.getWatchableObjectString(BLOCK_NAME_ID));
		int meta = this.dataWatcher.getWatchableObjectInt(BLOCK_STATE_ID);

		return block.getStateFromMeta(meta);
	}

	public void setBlockState(IBlockState state)
	{
		Block block = state.getBlock();

		this.dataWatcher.updateObject(BLOCK_NAME_ID, Block.blockRegistry.getNameForObject(block).toString());
		this.dataWatcher.updateObject(BLOCK_STATE_ID, block.getMetaFromState(state));
	}
}
