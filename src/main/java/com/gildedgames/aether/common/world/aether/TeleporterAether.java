package com.gildedgames.aether.common.world.aether;

import com.gildedgames.aether.api.util.BlockAccessExtendedWrapper;
import com.gildedgames.aether.api.util.NBT;
import com.gildedgames.aether.api.util.NBTHelper;
import com.gildedgames.aether.api.world.generation.TemplateLoc;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.blocks.BlocksAether;
import com.gildedgames.aether.common.registry.content.DimensionsAether;
import com.gildedgames.aether.common.registry.content.GenerationAether;
import com.gildedgames.aether.common.util.WorldPos;
import com.gildedgames.aether.common.util.helpers.BlockUtil;
import com.gildedgames.aether.common.world.templates.TemplatePlacer;
import com.gildedgames.aether.common.world.templates.TemplatePrimer;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import net.minecraft.block.BlockFlower;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
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

	/**
	 * Boolean here contains if the chunk is solid.
	 */
	public static Map<ChunkPos, Boolean> chunksMarkedForPortal = new HashMap<>();

	private final Random random;

	private final BiMap<WorldPos, WorldPos> portalPairs = HashBiMap.create();

	public boolean createPortal = true, hasLoadedPortalPairs = false;

	private WorldServer worldServerInstance;

	private World previousWorld;

	public TeleporterAether(final WorldServer world)
	{
		super(world);

		this.random = new Random(world.getSeed());

		this.worldServerInstance = world;
		this.worldServerInstance.customTeleporters.add(this);
	}

	@Override
	public void placeInPortal(final Entity entity, final float entityRotation)
	{
		this.previousWorld = entity.getEntityWorld();

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
			final NBTTagCompound tag = NBTHelper.readNBTFromFile("//data//teleporter.dat");

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

	public void markForPortals(final int chunkX, final int chunkZ)
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
	public void onChunkUnload(final ChunkEvent.Unload event)
	{
		final ChunkPos chunkPosition = new ChunkPos(event.getChunk().xPosition, event.getChunk().zPosition);

		chunksMarkedForPortal.remove(chunkPosition);
	}

	public WorldPos getPortalPosition(final World world, final int x, final int y, final int z, final boolean findPortalBlock)
	{
		final int radius = 2;
		final WorldPos check = new WorldPos(x, y, z, world.provider.getDimension());

		for (int x2 = x - radius; x2 < x + radius; ++x2)
		{
			for (int y2 = z - radius; y2 < z + radius; ++y2)
			{
				for (int z2 = y - radius; z2 < y + radius; ++z2)
				{
					final WorldPos pos = new WorldPos(x2, z2, y2, world.provider.getDimension());

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
	public boolean placeInExistingPortal(final Entity entity, final float entityRotation)
	{
		final int posX = MathHelper.floor(entity.posX);
		final int posY = MathHelper.floor(entity.posY);
		final int posZ = MathHelper.floor(entity.posZ);

		final WorldPos previousPortal = this.getPortalPosition(this.previousWorld, posX, posY, posZ, false);

		final WorldPos linkedPortal = this.getLinkedPortal(previousPortal);

		if (linkedPortal != null)
		{
			if (entity instanceof EntityPlayerMP)
			{
				final EntityPlayerMP player = (EntityPlayerMP) entity;
				final World world = FMLCommonHandler.instance().getMinecraftServerInstance().worldServerForDimension(linkedPortal.getDimension());
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

	@Override
	public boolean makePortal(final Entity entity)
	{
		final World world = entity.getEntityWorld();

		int x = MathHelper.floor(entity.posX);
		int z = MathHelper.floor(entity.posZ);

		int chunkX = x >> 4;
		int chunkZ = z >> 4;

		boolean isSolidChunk = false;

		int attempts = 0;
		final int maxAttempts = 250;

		final EnumFacing outerDirection = EnumFacing.HORIZONTALS[world.rand.nextInt(4)];
		ChunkPos outerPosition = new ChunkPos(chunkX, chunkZ);

		final boolean isWorldAether = this.worldServerInstance.provider.getDimensionType() == DimensionsAether.AETHER;

		while (attempts < maxAttempts)
		{
			final EnumFacing outerPosFacing = world.rand.nextBoolean() ? outerDirection.rotateY() : outerDirection;

			chunkX = outerPosition.chunkXPos + outerPosFacing.getFrontOffsetX();
			chunkZ = outerPosition.chunkZPos + outerPosFacing.getFrontOffsetZ();

			outerPosition = new ChunkPos(chunkX, chunkZ);

			if (isWorldAether)
			{
				final IChunkProvider chunkProvider = world.getChunkProvider();

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

						final int y1 = this.getFirstUncoveredCoord(world, xInner, zInner);
						final BlockPos pos = new BlockPos(xInner, y1, zInner);

						final Rotation rotation = TemplatePlacer.getRandomRotation(world.rand);

						final PlacementSettings settings = new PlacementSettings().setMirror(Mirror.NONE).setRotation(rotation).setIgnoreEntities(false)
								.setChunk(null).setReplacedBlock(null).setIgnoreStructureBlock(false);

						final IBlockState blockID = world.getBlockState(pos);

						final TemplateLoc loc = new TemplateLoc().set(pos.up()).set(settings).set(true);

						if (BlockUtil.isSolid(blockID, world, pos) && TemplatePrimer
								.canGenerateWithoutAreaCheck(world, GenerationAether.aether_portal_for_world, loc))
						{
							hasFoundPosition = true;
						}

						if (hasFoundPosition)
						{
							final int posX = MathHelper.floor(entity.posX);
							final int posY = MathHelper.floor(entity.posY);
							final int posZ = MathHelper.floor(entity.posZ);

							final WorldPos oldPortal = this.getPortalPosition(this.previousWorld, posX, posY,
									posZ, true);
							final WorldPos linkedPortal = new WorldPos(xInner, y1 + 1, zInner,
									world.provider.getDimension());

							this.portalPairs.put(oldPortal, linkedPortal);

							TemplatePrimer.generateTemplate(new BlockAccessExtendedWrapper(world), GenerationAether.aether_portal_for_world, loc);

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

	@Override
	public void removeStalePortalLocations(final long totalWorldTime)
	{
		final Iterator<Entry<WorldPos, WorldPos>> iterator = this.portalPairs.entrySet().iterator();

		while (iterator.hasNext())
		{
			final Entry<WorldPos, WorldPos> entry = iterator.next();
			final WorldPos portal1 = entry.getKey();
			final WorldPos portal2 = entry.getValue();

			final WorldPos ourPortal;

			if (portal1.getDimension() == this.worldServerInstance.provider.getDimension())
			{
				ourPortal = portal1;
			}
			else
			{
				ourPortal = portal2;
			}

			final WorldPos checkPosition = this.getPortalPosition(this.worldServerInstance, ourPortal.getX(),
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

	public int getFirstUncoveredCoord(final World world, final int x, final int z)
	{
		for (int i = world.getActualHeight(); i > 0; i--)
		{
			final IBlockState state = world.getBlockState(new BlockPos(x, i, z));
			if (!BlockUtil.isAir(state) && !(state.getBlock() instanceof BlockLeaves)
					&& !(state.getBlock() instanceof BlockFlower))
			{
				return i;
			}
		}
		return 0;
	}

	public BiMap<WorldPos, WorldPos> getPortalLinks()
	{
		return this.portalPairs;
	}

	public WorldPos getLinkedPortal(final WorldPos previousPortal)
	{
		WorldPos linkedPortal = null;

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
	public void write(final NBTTagCompound output)
	{
		output.setInteger("amtPortals", this.portalPairs.size());

		int i = 0;

		for (final Entry<WorldPos, WorldPos> entry : this.portalPairs.entrySet())
		{
			final WorldPos pos1 = entry.getKey();
			final WorldPos pos2 = entry.getValue();
			output.setInteger("tpx1" + i, pos1.getX());
			output.setInteger("tpy1" + i, pos1.getY());
			output.setInteger("tpz1" + i, pos1.getZ());
			output.setInteger("tpd1" + i, pos1.getDimension());

			output.setInteger("tpx2" + i, pos2.getX());
			output.setInteger("tpy2" + i, pos2.getY());
			output.setInteger("tpz2" + i, pos2.getZ());
			output.setInteger("tpd2" + i, pos2.getDimension());
			i++;
		}
	}

	@Override
	public void read(final NBTTagCompound input)
	{
		final int amount = input.getInteger("amtPortals");

		this.portalPairs.clear();
		for (int i = 0; i < amount; i++)
		{
			final WorldPos pos1 = new WorldPos(input.getInteger("tpx1" + i),
					input.getInteger("tpy1" + i), input.getInteger("tpz1" + i), input.getInteger("tpd1" + i));
			final WorldPos pos2 = new WorldPos(input.getInteger("tpx2" + i),
					input.getInteger("tpy2" + i), input.getInteger("tpz2" + i), input.getInteger("tpd2" + i));
			this.portalPairs.put(pos1, pos2);
		}
	}
}
