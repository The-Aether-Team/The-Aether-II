package com.gildedgames.aether.common.world.dimensions.aether;

import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.blocks.BlocksAether;
import com.gildedgames.aether.common.registry.TemplatesAether;
import com.gildedgames.aether.common.registry.minecraft.DimensionsAether;
import com.gildedgames.aether.api.util.BlockPosDimension;
import com.gildedgames.aether.api.util.NBT;
import com.gildedgames.aether.common.util.helpers.BlockUtil;
import com.gildedgames.aether.common.util.io.NBTHelper;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import net.minecraft.block.BlockFlower;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.Teleporter;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.structure.template.PlacementSettings;
import net.minecraft.world.gen.structure.template.TemplateManager;
import net.minecraftforge.event.world.ChunkEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

public class TeleporterAether extends Teleporter implements NBT
{

	public static final TemplateManager MANAGER = new TemplateManager("structures");

	/**
	 * Boolean here contains if the chunk is solid.
	 */
	public static Map<ChunkPos, Boolean> chunksMarkedForPortal = new HashMap<>();

	private final Random random;

	private final BiMap<BlockPosDimension, BlockPosDimension> portalPairs = HashBiMap.create();

	private WorldServer worldServerInstance;

	private World previousWorld;

	public boolean createPortal = true, hasLoadedPortalPairs = false;

	public TeleporterAether(WorldServer world)
	{
		super(world);

		this.random = new Random(world.getSeed());

		this.worldServerInstance = world;
		this.worldServerInstance.customTeleporters.add(this);
	}

	@Override
	public void placeInPortal(Entity entity, float entityRotation)
	{
		this.previousWorld = entity.worldObj;

		if (entity.dimension != this.worldServerInstance.provider.getDimension())
		{
			this.worldServerInstance = FMLCommonHandler.instance().getMinecraftServerInstance().worldServerForDimension(entity.dimension);

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

		if (!this.hasLoadedPortalPairs)
		{
			NBTTagCompound tag = NBTHelper.readNBTFromFile("//data//teleporter.dat");

			if (tag != null)
			{
				this.read(tag);
			}

			this.hasLoadedPortalPairs = true;
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

		final ChunkPos chunkPosition = new ChunkPos(chunkX, chunkZ);

		boolean isSolid = false;

		// Tries 16 random positions in the chunk to see if they're solid or not
		for (int count = 0; count < 16; count++)
		{
			final int x1 = x + this.random.nextInt(16);
			final int z1 = z + this.random.nextInt(16);

			final int y1 = this.getFirstUncoveredCoord(this.worldServerInstance, x1, z1);

			final BlockPos pos = new BlockPos(x1, y1, z1);

			final IBlockState block = this.worldServerInstance.getBlockState(pos);

			if (BlockUtil.isSolid(block, this.worldServerInstance, pos) && block != BlocksAether.quicksoil.getDefaultState())
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
		final ChunkPos chunkPosition = new ChunkPos(event.getChunk().xPosition, event.getChunk().zPosition);

		chunksMarkedForPortal.remove(chunkPosition);
	}

	public BlockPosDimension getPortalPosition(World world, int x, int y, int z, boolean findPortalBlock)
	{
		final int radius = 2;
		final BlockPosDimension check = new BlockPosDimension(x, y, z, world.provider.getDimension());

		for (int x2 = x - radius; x2 < x + radius; ++x2)
		{
			for (int y2 = z - radius; y2 < z + radius; ++y2)
			{
				for (int z2 = y - radius; z2 < y + radius; ++z2)
				{
					final BlockPosDimension pos = new BlockPosDimension(x2, z2, y2, world.provider.getDimension());

					if (!findPortalBlock && (this.portalPairs.containsKey(pos) || this.portalPairs.containsValue(pos))
							|| findPortalBlock && world.getBlockState(new BlockPos(x2, z2, y2))
							.getBlock() == BlocksAether.aether_portal)
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
				final World world = FMLCommonHandler.instance().getMinecraftServerInstance().worldServerForDimension(linkedPortal.dimId());
				player.setPositionAndUpdate(linkedPortal.getX() + 1.5D, linkedPortal.getY(), linkedPortal.getZ() + 1.5D);

				//this.worldServerInstance.updateEntityWithOptionalForce(player, true);

				while (!world.getCollisionBoxes(player, player.getEntityBoundingBox()).isEmpty())
				{
					player.setPositionAndUpdate(player.posX, player.posY + 0.2D, player.posZ);
				}
			}

			entity.motionX = entity.motionY = entity.motionZ = 0.0D;

			return true;
		}

		return false;
	}

	public boolean hasSolidBlocks(World world, int minX, int minY, int minZ, int maxX, int maxY, int maxZ)
	{
		for (final BlockPos pos : BlockUtil.getInBox(minX, minY, minZ, maxX, maxY, maxZ))
		{
			final IBlockState block = world.getBlockState(pos);
			if (!BlockUtil.isAir(block) && !(block.getBlock() instanceof BlockFlower))
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
		ChunkPos outerPosition = new ChunkPos(chunkX, chunkZ);

		final boolean isWorldAether = this.worldServerInstance.provider.getDimensionType() == DimensionsAether.AETHER;

		while (attempts < maxAttempts)
		{
			final EnumFacing outerPosFacing = entity.worldObj.rand.nextBoolean() ? outerDirection.rotateY() : outerDirection;

			chunkX = outerPosition.chunkXPos + outerPosFacing.getFrontOffsetX();
			chunkZ = outerPosition.chunkZPos + outerPosFacing.getFrontOffsetZ();

			outerPosition = new ChunkPos(chunkX, chunkZ);

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

					final EnumFacing innerDirection = EnumFacing.SOUTH;

					int innerAttempts = 0;
					x = (chunkX << 4) + 8;
					z = (chunkZ << 4) + 8;

					while (innerAttempts < 4)
					{
						final int xInner = x + innerDirection.getFrontOffsetX() * 7;
						final int zInner = z + innerDirection.getFrontOffsetZ() * 7;

						innerDirection.rotateY();

						final int y1 = this.getFirstUncoveredCoord(world, xInner, zInner) + 1;
						final BlockPos pos = new BlockPos(xInner, y1, zInner).down();

						final IBlockState blockID = world.getBlockState(pos);

						if (BlockUtil.isSolid(blockID, world, pos))
						{
							hasFoundPosition = true;
						}

						// this.hasSolidBlocks call: Checks if there are no
						// blocks where you would place the portal
						if ((hasFoundPosition))// && !this.hasSolidBlocks(world,
						// xInner - 1, y1 + 1, zInner -
						// 1, xInner, y1 + 4, zInner +
						// 4)) || attempts > maxAttempts
						// - 15)
						{
							final int posX = MathHelper.floor_double(entity.posX);
							final int posY = MathHelper.floor_double(entity.posY);
							final int posZ = MathHelper.floor_double(entity.posZ);

							final BlockPosDimension oldPortal = this.getPortalPosition(this.previousWorld, posX, posY,
									posZ, true);
							final BlockPosDimension linkedPortal = new BlockPosDimension(xInner, y1 + 1, zInner,
									world.provider.getDimension());

							this.portalPairs.put(oldPortal, linkedPortal);

							this.createPortalFrame(world, xInner, y1, zInner + 1);

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
		return this.createPortalFrame(world, x, y, z, Rotation.NONE);
	}

	public boolean createPortalFrame(World world, int x, int y, int z, Rotation rotation)
	{
		if (!this.createPortal)
		{
			return false;
		}

		PlacementSettings placementsettings = (new PlacementSettings()).setMirror(Mirror.NONE).setRotation(rotation).setIgnoreEntities(false).setChunk(null).setReplacedBlock(Blocks.AIR).setIgnoreStructureBlock(false);

		BlockPos size = TemplatesAether.aether_portal.transformedSize(rotation);
		BlockPos pos = new BlockPos(x, y, z);

		switch (rotation)
		{
		case NONE:
			pos = pos.add(-size.getX() / 2, 0, -size.getZ() / 2);
			break;
		default:
			break;
		case CLOCKWISE_90:
			pos = pos.add(size.getX() / 2, 0, -size.getZ() / 2);
			break;
		case COUNTERCLOCKWISE_90:
			pos = pos.add(-size.getX() / 2, 0, size.getZ() / 2);
			break;
		case CLOCKWISE_180:
			pos = pos.add(size.getX() / 2, 0, size.getZ() / 2);
		}

		TemplatesAether.aether_portal.addBlocksToWorld(world, pos, placementsettings);

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

			if (portal1.dimId() == this.worldServerInstance.provider.getDimension())
			{
				ourPortal = portal1;
			}
			else
			{
				ourPortal = portal2;
			}

			final BlockPosDimension checkPosition = this.getPortalPosition(this.worldServerInstance, ourPortal.getX(),
					ourPortal.getY(), ourPortal.getZ(), true);

			if (this.worldServerInstance.getBlockState(checkPosition).getBlock() != BlocksAether.aether_portal)
			{
				iterator.remove();
				this.portalPairs.remove(portal2);

				// this.createPortalFrame(this.worldServerInstance,
				// ourPortal.posX, ourPortal.posY, ourPortal.posZ);
			}
		}
	}

	public int getFirstUncoveredCoord(World world, int x, int z)
	{
		for (int i = world.getHeight(); i > 0; i--)
		{
			final IBlockState state = world.getBlockState(new BlockPos(x, i, z));
			if (!BlockUtil.isAir(state) && !(state.getBlock() instanceof BlockLeaves)
					&& !(state.getBlock() instanceof BlockFlower))
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
			final BlockPosDimension pos1 = new BlockPosDimension(input.getInteger("tpx1" + i),
					input.getInteger("tpy1" + i), input.getInteger("tpz1" + i), input.getInteger("tpd1" + i));
			final BlockPosDimension pos2 = new BlockPosDimension(input.getInteger("tpx2" + i),
					input.getInteger("tpy2" + i), input.getInteger("tpz2" + i), input.getInteger("tpd2" + i));
			this.portalPairs.put(pos1, pos2);
		}
	}
}
