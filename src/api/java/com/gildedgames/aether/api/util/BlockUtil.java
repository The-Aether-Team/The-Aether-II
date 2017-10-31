package com.gildedgames.aether.api.util;

import com.gildedgames.aether.api.world.generation.IBlockAccessExtended;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.registry.GameData;
import org.apache.logging.log4j.LogManager;

import javax.annotation.Nullable;

public class BlockUtil
{
	@Nullable
	public static TileEntity createTE(final NBTTagCompound compound)
	{
		TileEntity tileentity = null;
		final String s = compound.getString("id");
		Class<? extends TileEntity> oclass = null;

		try
		{
			oclass = (Class) GameData.getTileEntityRegistry().getObject(new ResourceLocation(s));

			if (oclass != null)
			{
				tileentity = (TileEntity) oclass.newInstance();
			}
		}
		catch (final Throwable throwable1)
		{
			LogManager.getLogger().error("Failed to createTE block entity {}", new Object[] { s, throwable1 });
			net.minecraftforge.fml.common.FMLLog.log(org.apache.logging.log4j.Level.ERROR, throwable1,
					"A TileEntity %s(%s) has thrown an exception during loading, its state cannot be restored. Report this to the mod author",
					s, oclass.getName());
		}

		if (tileentity != null)
		{
			try
			{

				tileentity.readFromNBT(compound);
			}
			catch (final Throwable throwable)
			{
				LogManager.getLogger().error("Failed to load data for block entity {}", new Object[] { s, throwable });
				net.minecraftforge.fml.common.FMLLog.log(org.apache.logging.log4j.Level.ERROR, throwable,
						"A TileEntity %s(%s) has thrown an exception during loading, its state cannot be restored. Report this to the mod author",
						s, oclass.getName());
				tileentity = null;
			}
		}
		else
		{
			LogManager.getLogger().warn("Skipping BlockEntity with id {}", new Object[] { s });
		}

		return tileentity;
	}

	public static IBlockState getBlockState(final ItemStack stack)
	{
		if (stack.getItem() instanceof ItemBlock)
		{
			return ((ItemBlock) stack.getItem()).block.getStateFromMeta(stack.getItemDamage());
		}

		return null;
	}

	@Deprecated
	public static boolean setTileEntityNBT(final IBlockAccessExtended worldIn, final BlockPos pos, final ItemStack stack)
	{
		final MinecraftServer minecraftserver = FMLCommonHandler.instance().getMinecraftServerInstance();

		if (minecraftserver == null)
		{
			return false;
		}
		else
		{
			if (stack.hasTagCompound() && stack.getTagCompound().hasKey("BlockEntityTag", 10))
			{
				final TileEntity tileentity = worldIn.getTileEntity(pos);

				if (tileentity != null)
				{
					// TODO: fix?
					/*if (!worldIn.isRemote *//*&& tileentity.func_183000_F()*//*)
					{
						return false;
					}*/

					final NBTTagCompound nbttagcompound = new NBTTagCompound();
					final NBTTagCompound nbttagcompound1 = nbttagcompound.copy();
					tileentity.writeToNBT(nbttagcompound);
					final NBTTagCompound nbttagcompound2 = (NBTTagCompound) stack.getTagCompound().getTag("BlockEntityTag");
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

	public static boolean isVoid(final IBlockState state)
	{
		return state.getBlock() == Blocks.STRUCTURE_VOID;
	}

	public static boolean isAir(final IBlockState state)
	{
		return state.getBlock().getMaterial(state) == Material.AIR;
	}

	public static boolean isSolid(final IBlockState state, final IBlockAccess world, final BlockPos pos)
	{
		return !isAir(state) && state.getBlock().isBlockSolid(world, pos, EnumFacing.DOWN)
				&& state.getBlock().getMaterial(state).isOpaque();
	}

	public static boolean isSolid(final IBlockState state)
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
