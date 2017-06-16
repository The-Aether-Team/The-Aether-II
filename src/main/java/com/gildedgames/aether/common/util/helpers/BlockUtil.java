package com.gildedgames.aether.common.util.helpers;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.fml.common.FMLCommonHandler;

public class BlockUtil
{
	@Deprecated
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
					NBTTagCompound nbttagcompound2 = (NBTTagCompound) stack.getTagCompound().getTag("BlockEntityTag");
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

	public static boolean isAir(IBlockState state)
	{
		return state.getBlock().getMaterial(state) == Material.AIR;
	}

	public static boolean isSolid(IBlockState state, World world, BlockPos pos)
	{
		return !isAir(state) && state.getBlock().isBlockSolid(world, pos, EnumFacing.DOWN)
				&& state.getBlock().getMaterial(state).isOpaque();
	}

	public static boolean isSolid(IBlockState state)
	{
		return !isAir(state) && state.getMaterial().isSolid() && state.getBlock().isOpaqueCube(state);
	}

	public static int getTopBlockHeight(final World world, int posX, int posZ)
	{
		final Chunk chunk = world.getChunkFromChunkCoords(posX >> 4, posZ >> 4);
		final int x = posX;
		final int z = posZ;
		int k = chunk.getTopFilledSegment() + 15;
		posX &= 15;//Get the latest 4 bits of posX. idk what for. :D

		for (posZ &= 15; k > 0; --k)
		{
			final IBlockState l = chunk.getBlockState(posX, k, posZ);

			if (l != Blocks.AIR && l.getMaterial().blocksMovement() && l.getMaterial() != Material.LEAVES
					&& !l.getBlock().isFoliage(world, new BlockPos(x, k, z)))
			{
				return k + 1;
			}
		}

		return -1;
	}

}
