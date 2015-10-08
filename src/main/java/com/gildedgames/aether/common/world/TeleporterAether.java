package com.gildedgames.aether.common.world;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.blocks.BlocksAether;
import com.gildedgames.aether.common.blocks.construction.BlockAetherPortal;
import com.gildedgames.util.GGHelper;
import com.gildedgames.util.core.nbt.NBT;
import com.gildedgames.util.instances.BlockPosDimension;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

import net.minecraft.block.BlockFlower;
import net.minecraft.block.BlockLeavesBase;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.Teleporter;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraftforge.event.world.ChunkEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class TeleporterAether extends Teleporter implements NBT
{
	private WorldServer worldServerInstance;

	private final Random random;

	private final BiMap<BlockPosDimension, BlockPosDimension> portalPairs = HashBiMap.create();

	private World previousWorld;

	/**
	 * Boolean here contains if the chunk is solid.
	 */
	public static Map<ChunkCoordIntPair, Boolean> chunksMarkedForPortal = new HashMap<ChunkCoordIntPair, Boolean>();

	public boolean createPortal = true;

	public TeleporterAether(WorldServer worldServer)
	{
		super(worldServer);

		this.worldServerInstance = worldServer;
		this.worldServerInstance.customTeleporters.add(this);
		this.random = new Random(worldServer.getSeed());
	}

	@Override
	public void placeInPortal(Entity entity, float entityRotation)
	{
		this.previousWorld = entity.worldObj;

		if (entity.dimension != this.worldServerInstance.provider.getDimensionId())
		{
			this.worldServerInstance = MinecraftServer.getServer().worldServerForDimension(entity.dimension);

			boolean teleporterRegistered = false;

			for (final Teleporter teleporter : this.worldServerInstance.customTeleporters)
			{
				if (teleporter instanceof TeleporterAether)
				{
					teleporterRegistered = true;
				}
			}

			if (!teleporterRegistered)
			{
				this.worldServerInstance.customTeleporters.add(this);
			}
		}

		entity.setWorld(this.worldServerInstance);

		if (!this.placeInExistingPortal(entity, entityRotation))
		{
			this.makePortal(entity);

			this.placeInExistingPortal(entity, entityRotation);
		}
	}

	public void markForPortals(int chunkX, int chunkZ)
	{
		final int x = chunkX << 4;
		final int z = chunkZ << 4;

		final ChunkCoordIntPair chunkPosition = new ChunkCoordIntPair(chunkX, chunkZ);

		boolean isSolid = false;

		//Tries 16 random positions in the chunk to see if they're solid or not
		for (int count = 0; count < 16; count++)
		{
			final int x1 = x + this.random.nextInt(16);
			final int z1 = z + this.random.nextInt(16);

			final int y1 = this.getFirstUncoveredCoord(this.worldServerInstance, x1, z1);

			final BlockPos pos = new BlockPos(x1, y1, z1);

			final IBlockState block = this.worldServerInstance.getBlockState(pos);

			if (GGHelper.isSolid(block, this.worldServerInstance, pos))
			{
				isSolid = true;
				break;
			}
		}

		chunksMarkedForPortal.put(chunkPosition, isSolid);
	}

	@SubscribeEvent
	public void onChunkUnload(ChunkEvent.Unload event)
	{
		final ChunkCoordIntPair chunkPosition = new ChunkCoordIntPair(event.getChunk().xPosition, event.getChunk().zPosition);

		chunksMarkedForPortal.remove(chunkPosition);
	}

	public BlockPosDimension getPortalPosition(World world, int x, int y, int z, boolean findPortalBlock)
	{
		final int radius = 2;
		final BlockPosDimension check = new BlockPosDimension(x, y, z, world.provider.getDimensionId());

		for (int ix = x - radius; ix < x + radius; ++ix)
		{
			for (int iz = z - radius; iz < z + radius; ++iz)
			{
				for (int iy = y - radius; iy < y + radius; ++iy)
				{
					final BlockPosDimension pos = new BlockPosDimension(ix, iy, iz, world.provider.getDimensionId());
					if (!findPortalBlock && (this.portalPairs.containsKey(pos) || this.portalPairs.containsValue(pos))
							|| findPortalBlock && world.getBlockState(new BlockPos(ix, iy, iz)).getBlock() == BlocksAether.aether_portal)
					{
						return pos;
					}

				}
			}
		}

		return check;
	}

	@Override
	public boolean placeInExistingPortal(Entity entity, float entityRotation)
	{
		final int posX = MathHelper.floor_double(entity.posX);
		final int posY = MathHelper.floor_double(entity.posY);
		final int posZ = MathHelper.floor_double(entity.posZ);

		final BlockPosDimension previousPortal = this.getPortalPosition(this.previousWorld, posX, posY, posZ, false);

		final BlockPosDimension linkedPortal = this.getLinkedPortal(previousPortal);

		if (linkedPortal != null)
		{
			if (entity instanceof EntityPlayerMP)
			{
				final EntityPlayerMP player = (EntityPlayerMP) entity;
				final World world = MinecraftServer.getServer().worldServerForDimension(linkedPortal.dimId());
				player.setPositionAndUpdate(linkedPortal.getX() + 1.5D, linkedPortal.getY(), linkedPortal.getZ() + 1.5D);

				this.worldServerInstance.updateEntityWithOptionalForce(player, true);

				while (!world.getCollidingBoundingBoxes(player, player.getEntityBoundingBox()).isEmpty())
				{
					player.setPosition(player.posX, player.posY + 0.2D, player.posZ);
				}
			}

			entity.motionX = entity.motionY = entity.motionZ = 0.0D;

			return true;
		}

		return false;
	}

	public boolean hasSolidBlocks(World world, int minX, int minY, int minZ, int maxX, int maxY, int maxZ)
	{
		for (final BlockPos pos : GGHelper.getInBox(minX, minY, minZ, maxX, maxY, maxZ))
		{
			final IBlockState block = world.getBlockState(pos);
			if (!GGHelper.isAir(block) && !(block.getBlock() instanceof BlockFlower))
			{
				return true;
			}
		}

		return false;
	}

	@Override
	public boolean makePortal(Entity entity)
	{
		final World world = entity.worldObj;

		int x = MathHelper.floor_double(entity.posX);
		int z = MathHelper.floor_double(entity.posZ);

		int chunkX = x >> 4;
		int chunkZ = z >> 4;

		boolean isSolidChunk = false;

		int attempts = 0;
		final int maxAttempts = 250;

		final EnumFacing outerDirection = EnumFacing.HORIZONTALS[entity.worldObj.rand.nextInt(4)];
		ChunkCoordIntPair outerPosition = new ChunkCoordIntPair(chunkX, chunkZ);

		final boolean isWorldAether = this.worldServerInstance.provider.getDimensionId() == AetherCore.getAetherDimID();

		while (attempts < maxAttempts)
		{
			final EnumFacing outerPosFacing = entity.worldObj.rand.nextBoolean() ? outerDirection.rotateY() : outerDirection;

			chunkX = outerPosition.chunkXPos + outerPosFacing.getFrontOffsetX();
			chunkZ = outerPosition.chunkZPos + outerPosFacing.getFrontOffsetZ();

			outerPosition = new ChunkCoordIntPair(chunkX, chunkZ);

			if (isWorldAether)
			{
				final IChunkProvider chunkProvider = entity.worldObj.getChunkProvider();

				chunkProvider.provideChunk(chunkX, chunkZ);
				this.markForPortals(chunkX, chunkZ);
			}

			if (chunksMarkedForPortal.containsKey(outerPosition) || !isWorldAether)
			{
				isSolidChunk = !isWorldAether || chunksMarkedForPortal.get(outerPosition);

				if (isSolidChunk)
				{
					boolean hasFoundPosition = false;

					int innerAttempts = 0;
					final EnumFacing innerDirection = EnumFacing.SOUTH;
					x = (chunkX << 4) + 8;
					z = (chunkZ << 4) + 8;

					while (!hasFoundPosition && innerAttempts < 4)
					{
						final int xInner = x + innerDirection.getFrontOffsetX() * 7;
						final int zInner = z + innerDirection.getFrontOffsetZ() * 7;

						innerDirection.rotateY();

						final int y1 = this.getFirstUncoveredCoord(world, xInner, zInner);
						final BlockPos pos = new BlockPos(xInner, y1, zInner);

						final IBlockState blockID = world.getBlockState(pos);

						if (GGHelper.isSolid(blockID, world, pos))
						{
							hasFoundPosition = true;
						}

						//this.hasSolidBlocks call: Checks if there are no blocks where you would place the portal
						if ((hasFoundPosition))// && !this.hasSolidBlocks(world, xInner - 1, y1 + 1, zInner - 1, xInner, y1 + 4, zInner + 4)) || attempts > maxAttempts - 15)
						{
							final int posX = MathHelper.floor_double(entity.posX);
							final int posY = MathHelper.floor_double(entity.posY);
							final int posZ = MathHelper.floor_double(entity.posZ);

							final BlockPosDimension oldPortal = this.getPortalPosition(this.previousWorld, posX, posY, posZ, true);
							final BlockPosDimension linkedPortal = new BlockPosDimension(xInner, y1 + 1, zInner, world.provider.getDimensionId());

							this.portalPairs.put(oldPortal, linkedPortal);

							this.createPortalFrame(world, xInner, y1, zInner);

							AetherCore.LOGGER.debug("Created portal using " + (attempts + 1) + " attempts.");

							return true;
						}

						innerAttempts++;
					}
				}
			}
			attempts++;

		}
		AetherCore.LOGGER.debug("Failed generating portal");

		return isSolidChunk;
	}

	public boolean createPortalFrame(World world, int x, int y, int z)
	{
		if (!this.createPortal)
		{
			return false;
		}

		final int posX = x;
		final int posY = y + 1;
		final int posZ = z;

		final IBlockState frameBlock = Blocks.glowstone.getDefaultState();
		final IBlockState portalBlock = BlocksAether.aether_portal.getDefaultState().withProperty(BlockAetherPortal.PROPERTY_AXIS, EnumFacing.Axis.Z);

		for (int xi = -1; xi <= 1; ++xi)
		{
			for (int zi = 1; zi < 3; ++zi)
			{
				for (int yi = -1; yi < 3; ++yi)
				{
					final int blockX = posX + xi;
					final int blockY = posY + yi;
					final int blockZ = posZ + zi - 1;
					this.worldServerInstance.setBlockState(new BlockPos(blockX, blockY, blockZ), yi < 0 ? frameBlock : Blocks.air.getDefaultState());
				}
			}
		}

		for (int zi = 0; zi < 4; ++zi)
		{
			for (int yi = -1; yi < 4; ++yi)
			{
				final int blockX = posX;
				final int blockY = posY + yi;
				final int blockZ = posZ + zi - 1;
				final boolean border = zi == 0 || zi == 3 || yi == -1 || yi == 3;
				this.worldServerInstance.setBlockState(new BlockPos(blockX, blockY, blockZ), border ? frameBlock : portalBlock, 2);
			}
		}

		for (int zi = 0; zi < 4; ++zi)
		{
			for (int yi = -1; yi < 4; ++yi)
			{
				final int blockX = posX;
				final int blockY = posY + yi;
				final int blockZ = posZ + zi - 1;
				final BlockPos pos = new BlockPos(blockX, blockY, blockZ);
				this.worldServerInstance.notifyNeighborsOfStateChange(pos, this.worldServerInstance.getBlockState(pos).getBlock());
			}
		}

		return true;
	}

	@Override
	public void removeStalePortalLocations(long totalWorldTime)
	{
		final Iterator<Entry<BlockPosDimension, BlockPosDimension>> iterator = this.portalPairs.entrySet().iterator();

		while (iterator.hasNext())
		{
			final Entry<BlockPosDimension, BlockPosDimension> entry = iterator.next();
			final BlockPosDimension portal1 = entry.getKey();
			final BlockPosDimension portal2 = entry.getValue();

			BlockPosDimension ourPortal;

			if (portal1.dimId() == this.worldServerInstance.provider.getDimensionId())
			{
				ourPortal = portal1;
			}
			else
			{
				ourPortal = portal2;
			}

			final BlockPosDimension checkPosition = this.getPortalPosition(this.worldServerInstance, ourPortal.getX(), ourPortal.getY(), ourPortal.getZ(), false);

			if (this.worldServerInstance.getBlockState(checkPosition).getBlock() != BlocksAether.aether_portal)
			{
				iterator.remove();
				this.portalPairs.remove(portal2);

				//this.createPortalFrame(this.worldServerInstance, ourPortal.posX, ourPortal.posY, ourPortal.posZ);
			}
		}
	}

	public int getFirstUncoveredCoord(World world, int x, int z)
	{
		for (int i = world.getHeight(); i > 0; i--)
		{
			final IBlockState state = world.getBlockState(new BlockPos(x, i, z));
			if (!GGHelper.isAir(state) && !(state.getBlock() instanceof BlockLeavesBase) && !(state.getBlock() instanceof BlockFlower))
			{
				return i - 1;
			}
		}
		return 0;
	}

	public BiMap<BlockPosDimension, BlockPosDimension> getPortalLinks()
	{
		return this.portalPairs;
	}

	public BlockPosDimension getLinkedPortal(BlockPosDimension previousPortal)
	{
		BlockPosDimension linkedPortal = null;

		if (this.portalPairs.containsValue(previousPortal))
		{
			linkedPortal = this.portalPairs.inverse().get(previousPortal);
		}
		else if (this.portalPairs.containsKey(previousPortal))
		{
			linkedPortal = this.portalPairs.get(previousPortal);
		}

		return linkedPortal;
	}

	@Override
	public void write(NBTTagCompound output)
	{
		output.setInteger("amtPortals", this.portalPairs.size());
		int i = 0;
		for (final Entry<BlockPosDimension, BlockPosDimension> entry : this.portalPairs.entrySet())
		{
			final BlockPosDimension pos1 = entry.getKey();
			final BlockPosDimension pos2 = entry.getValue();
			output.setInteger("tpx1" + i, pos1.getX());
			output.setInteger("tpy1" + i, pos1.getY());
			output.setInteger("tpz1" + i, pos1.getZ());
			output.setInteger("tpd1" + i, pos1.dimId());

			output.setInteger("tpx2" + i, pos2.getX());
			output.setInteger("tpy2" + i, pos2.getY());
			output.setInteger("tpz2" + i, pos2.getZ());
			output.setInteger("tpd2" + i, pos2.dimId());
			i++;
		}
	}

	@Override
	public void read(NBTTagCompound input)
	{
		final int amount = input.getInteger("amtPortals");

		this.portalPairs.clear();
		for (int i = 0; i < amount; i++)
		{
			final BlockPosDimension pos1 = new BlockPosDimension(input.getInteger("tpx1" + i), input.getInteger("tpy1" + i), input.getInteger("tpz1" + i), input.getInteger("tpd1" + i));
			final BlockPosDimension pos2 = new BlockPosDimension(input.getInteger("tpx2" + i), input.getInteger("tpy2" + i), input.getInteger("tpz2" + i), input.getInteger("tpd2" + i));
			this.portalPairs.put(pos1, pos2);
		}
	}
}
