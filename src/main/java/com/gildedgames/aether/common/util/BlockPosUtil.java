package com.gildedgames.aether.common.util;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.MutableBlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;

public class BlockPosUtil
{

	public static boolean setTileEntityNBT(World worldIn, BlockPos pos, ItemStack stack)
	{
		MinecraftServer minecraftserver = FMLCommonHandler.instance().getMinecraftServerInstance();

		if (minecraftserver == null)
		{
			return false;
		}
		else
		{
			if (stack.hasTagCompound() && stack.getTagCompound().hasKey("BlockEntityTag", 10))
			{
				TileEntity tileentity = worldIn.getTileEntity(pos);

				if (tileentity != null)
				{
					// TODO: fix?
					if (!worldIn.isRemote /*&& tileentity.func_183000_F()*/)
					{
						return false;
					}

					NBTTagCompound nbttagcompound = new NBTTagCompound();
					NBTTagCompound nbttagcompound1 = nbttagcompound.copy();
					tileentity.writeToNBT(nbttagcompound);
					NBTTagCompound nbttagcompound2 = (NBTTagCompound)stack.getTagCompound().getTag("BlockEntityTag");
					nbttagcompound.merge(nbttagcompound2);
					nbttagcompound.setInteger("x", pos.getX());
					nbttagcompound.setInteger("y", pos.getY());
					nbttagcompound.setInteger("z", pos.getZ());

					if (!nbttagcompound.equals(nbttagcompound1))
					{
						tileentity.readFromNBT(nbttagcompound);
						tileentity.markDirty();
						return true;
					}
				}
			}

			return false;
		}
	}

	public static AxisAlignedBB bounds(BlockPos start, BlockPos end)
	{
		return new AxisAlignedBB(start.getX(), start.getY(), start.getZ(), end.getX(), end.getY(), end.getZ());
	}
	
	public static MutableBlockPos add(MutableBlockPos pos, BlockPos add)
	{
		return BlockPosUtil.add(pos, add.getX(), add.getY(), add.getZ());
	}

	public static MutableBlockPos add(MutableBlockPos pos, int x, int y, int z)
	{
		pos.setPos(pos.getX() + x, pos.getY() + y, pos.getZ() + z);

		return pos;
	}

	public static MutableBlockPos down(MutableBlockPos pos, int n)
	{
		pos.setPos(pos.getX(), pos.getY() - n, pos.getZ());

		return pos;
	}

	public static MutableBlockPos set(MutableBlockPos pos, int x, int y, int z)
	{
		pos.setPos(x, y, z);

		return pos;
	}

	public static MutableBlockPos toMutable(BlockPos pos)
	{
		return new MutableBlockPos(pos.getX(), pos.getY(), pos.getZ());
	}
}
