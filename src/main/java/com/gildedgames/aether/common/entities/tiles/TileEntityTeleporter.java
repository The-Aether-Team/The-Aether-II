package com.gildedgames.aether.common.entities.tiles;

import com.gildedgames.aether.common.blocks.BlocksAether;
import com.gildedgames.aether.common.blocks.containers.BlockAltar;
import com.gildedgames.aether.common.entities.tiles.util.TileEntitySynced;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TileEntityTeleporter extends TileEntitySynced implements ITickable
{

	@SideOnly(Side.CLIENT)
	public double animationTicks, prevAnimationTicks;

	private int buildTime;

	public EnumFacing getFacing()
	{
		IBlockState state = this.world.getBlockState(this.pos);

		if (state.getBlock() == BlocksAether.aether_teleporter)
		{
			return state.getValue(BlockAltar.PROPERTY_FACING);
		}

		return EnumFacing.NORTH;
	}

	@Override
	public void update()
	{
		if (this.world.isRemote)
		{
			this.prevAnimationTicks = this.animationTicks;

			this.animationTicks++;
		}

		if (this.buildTime < 200)
		{
			this.buildTime++;
		}
		else
		{
			this.buildTime = 0;
		}
	}

	@Override
	public void readFromNBT(NBTTagCompound compound)
	{
		super.readFromNBT(compound);

		this.buildTime = compound.getInteger("BuildTime");
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound)
	{
		super.writeToNBT(compound);

		compound.setInteger("BuildTime", (short) this.buildTime);

		return compound;
	}

	public int getBuildTime()
	{
		return this.buildTime;
	}
}
