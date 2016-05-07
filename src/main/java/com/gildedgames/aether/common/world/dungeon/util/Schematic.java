package com.gildedgames.aether.common.world.dungeon.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.ModContainer;
import net.minecraftforge.fml.common.registry.GameData;

import com.gildedgames.aether.common.AetherCore;

public class Schematic
{

	public class BlockData
	{

		public int x, y, z;
		
		public IBlockState state;

		public BlockData(int x, int y, int z, IBlockState state)
		{
			this.x = x;
			this.y = y;
			this.z = z;
			
			this.state = state;
		}
	}

	private int width, height, length;

	private ArrayList<BlockPos> scheduledGenerations = new ArrayList<BlockPos>();

	private ArrayList<BlockData> blocks = new ArrayList<BlockData>();

	private NBTTagList tileEntities;

	public Schematic(String resourceLocation)
	{
		this.loadSchematicFile(resourceLocation);
	}
	
	public int width()
	{
		return this.width;
	}
	
	public int height()
	{
		return this.height;
	}
	
	public int length()
	{
		return this.length;
	}
	
	public void applyTileEntityData(World world, int chunkX, int chunkZ)
	{
		int worldXOff = chunkX * 16;
		int worldZOff = chunkZ * 16;

		for (BlockPos location : this.scheduledGenerations)
		{
			if (((location.getX() + this.width) < worldXOff) || (location.getX() > worldXOff + 16))
			{
				continue;
			}

			if (((location.getZ() + this.length) < worldZOff) || (location.getZ() > worldZOff + 16))
			{
				continue;
			}

			for (BlockData blockData : this.blocks)
			{
				if ((location.getX() + blockData.x) - worldXOff >= 16 || (location.getX() + blockData.x) - worldXOff < 0)
				{
					continue;
				}

				if ((location.getZ() + blockData.z) - worldZOff >= 16 || (location.getZ() + blockData.z) - worldZOff < 0)
				{
					continue;
				}

				if (blockData.state.getBlock().hasTileEntity(blockData.state))
				{
					for (int index = 0; index < this.tileEntities.tagCount(); index++)
					{
						NBTTagCompound tileEntityData = (NBTTagCompound) this.tileEntities.getCompoundTagAt(index);

						if ((tileEntityData.getInteger("x") == blockData.x) && (tileEntityData.getInteger("y") == blockData.y) && (tileEntityData.getInteger("z") == blockData.z))
						{
							tileEntityData.setInteger("x", blockData.x + location.getX());
							tileEntityData.setInteger("y", blockData.y + location.getY());
							tileEntityData.setInteger("z", blockData.z + location.getZ());

							world.setTileEntity(new BlockPos(blockData.x + location.getX(), blockData.y + location.getY(), blockData.z + location.getZ()), TileEntity.createAndLoadEntity(tileEntityData));
						}
					}
				}
				
				if (blockData.state.getBlock().getLightValue() > 0)
				{
					world.notifyLightSet(new BlockPos(blockData.x + location.getX(), blockData.y + location.getY(), blockData.z + location.getZ()));
				}
			}
		}
	}

	public void generateChunk(World world, ChunkPrimer primer, int chunkX, int chunkZ)
	{
		int worldXOff = chunkX * 16;
		int worldZOff = chunkZ * 16;

		for (BlockPos loc : this.scheduledGenerations)
		{
			if (((loc.getX() + this.width) < worldXOff) || (loc.getX() > worldXOff + 16))
			{
				continue;
			}

			if (((loc.getZ() + this.length) < worldZOff) || (loc.getZ() > worldZOff + 16))
			{
				continue;
			}

			this.place(world, primer, loc, chunkX, chunkZ);
		}
	}
	
	public void generateFully(World world, BlockPos loc)
	{
		for (BlockData blockData : this.blocks)
		{
			world.setBlockState(new BlockPos(blockData.x + loc.getX(), blockData.y + loc.getY(), blockData.z + loc.getZ()), blockData.state, 2);

			if (blockData.state.getBlock().hasTileEntity(blockData.state))
			{
				for (int index = 0; index < this.tileEntities.tagCount(); index++)
				{
					NBTTagCompound tileEntityData = (NBTTagCompound) this.tileEntities.getCompoundTagAt(index);

					if ((tileEntityData.getInteger("x") == blockData.x) && (tileEntityData.getInteger("y") == blockData.y) && (tileEntityData.getInteger("z") == blockData.z))
					{
						tileEntityData.setInteger("x", blockData.x + loc.getX());
						tileEntityData.setInteger("y", blockData.y + loc.getY());
						tileEntityData.setInteger("z", blockData.z + loc.getZ());

						world.setTileEntity(new BlockPos(blockData.x + loc.getX(), blockData.y + loc.getY(), blockData.z + loc.getZ()), TileEntity.createAndLoadEntity(tileEntityData));
					}
				}
			}
		}
	}

	public void place(World world, ChunkPrimer primer, BlockPos loc, int chunkOffX, int chunkOffZ)
	{
		int worldXOff = chunkOffX * 16;
		int worldZOff = chunkOffZ * 16;
		
		for (int x = 0; x < 16; x++)
		{
			for (int y = 0; y < 256; y++)
			{
				for (int z = 0; z < 16; z++)
				{
					if (x + worldXOff >= loc.getX() && y >= loc.getY() && z + worldZOff >= loc.getZ())
					{
						if (x + worldXOff < loc.getX() + this.width && y < loc.getY() + this.height && z + worldZOff < loc.getZ() + this.length)
						{
							primer.setBlockState(x, y, z, Blocks.air.getDefaultState());
						}
					}
				}
			}
		}

		for (BlockData blockData : this.blocks)
		{
			if ((loc.getX() + blockData.x) - worldXOff >= 16 || (loc.getX() + blockData.x) - worldXOff < 0)
			{
				continue;
			}

			if ((loc.getZ() + blockData.z) - worldZOff >= 16 || (loc.getZ() + blockData.z) - worldZOff < 0)
			{
				continue;
			}

			primer.setBlockState((loc.getX() + blockData.x) - worldXOff, blockData.y + loc.getY(), (blockData.z + loc.getZ()) - worldZOff, blockData.state);
		}
	}

	public void scheduleGenerationAt(int x, int y, int z)
	{
		this.scheduledGenerations.add(new BlockPos(x, y, z));
	}

	public InputStream readAsset(String asset) throws ZipException, IOException
	{
		File source = new File(MinecraftServer.getServer().getDataDirectory(), "/dungeonSchematics/");

		if (source != null)
		{
			if (source.isFile())
			{
				ZipFile zipfile = new ZipFile(source);
				ZipEntry zipentry = zipfile.getEntry(asset);

				return zipfile.getInputStream(zipentry);
			}
			else
			{
				return new FileInputStream(new File(source, asset));
			}
		}

		return null;
	}

	public boolean loadSchematicFile(String resourceLocation)
	{
		NBTTagCompound nbt;

		try
		{
			nbt = CompressedStreamTools.readCompressed(this.readAsset(resourceLocation));

			this.width = nbt.getShort("Width");
			this.height = nbt.getShort("Height");
			this.length = nbt.getShort("Length");

			byte[] blockIds = nbt.getByteArray("Blocks");
			byte[] blockMetadata = nbt.getByteArray("Data");

			int index = 0;

			for (int y = 0; y < this.height; y++)
			{
				for (int z = 0; z < this.length; z++)
				{
					for (int x = 0; x < this.width; x++)
					{
						if ((short) (blockIds[index] & 0xFF) != 0)
						{
							short id = (short) (blockIds[index] & 0xFF);
							int meta = blockMetadata[index];
							
							BlockData blockData = new BlockData(x, y, z, GameData.getBlockRegistry().getObjectById(id).getStateFromMeta(meta));
							this.blocks.add(blockData);
						}

						++index;
					}
				}
			}

			this.tileEntities = nbt.getTagList("TileEntities", 10);

			return true;
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

		return false;
	}
	
}