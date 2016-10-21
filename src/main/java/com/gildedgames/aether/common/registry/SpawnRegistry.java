package com.gildedgames.aether.common.registry;

import com.gildedgames.aether.api.capabilites.AetherCapabilities;
import com.gildedgames.aether.api.capabilites.entity.spawning.ISpawningInfo;
import com.gildedgames.aether.api.capabilites.entity.spawning.EntitySpawn;
import com.gildedgames.aether.common.ReflectionAether;
import com.gildedgames.aether.common.blocks.BlocksAether;
import com.gildedgames.aether.common.blocks.natural.BlockAetherGrass;
import com.gildedgames.aether.common.entities.living.*;
import com.gildedgames.aether.common.entities.living.enemies.EntityCockatrice;
import com.gildedgames.aether.common.entities.living.enemies.EntitySwet;
import com.gildedgames.aether.common.entities.living.enemies.EntityTempest;
import com.gildedgames.aether.common.entities.living.enemies.EntityZephyr;
import com.gildedgames.aether.common.entities.living.mounts.EntityFlyingCow;
import com.gildedgames.aether.common.entities.living.mounts.EntityPhyg;
import com.gildedgames.aether.common.registry.minecraft.BiomesAether;
import com.gildedgames.aether.common.registry.minecraft.DimensionsAether;
import com.gildedgames.aether.common.world.spawning.PosCondition;
import com.gildedgames.aether.common.world.spawning.SpawnArea;
import com.gildedgames.aether.common.world.spawning.SpawnEntry;
import com.gildedgames.aether.common.world.spawning.SpawnHandler;
import com.gildedgames.aether.common.world.spawning.conditions.CheckBiome;
import com.gildedgames.aether.common.world.spawning.conditions.CheckBlockStateUnderneath;
import com.gildedgames.aether.common.world.spawning.conditions.CheckDimension;
import com.gildedgames.aether.common.world.spawning.conditions.CheckTime;
import com.gildedgames.aether.common.world.spawning.util.FlyingPositionSelector;
import com.gildedgames.util.core.util.GGHelper;
import com.google.common.collect.Lists;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldEventListener;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.common.gameevent.TickEvent.WorldTickEvent;

import javax.annotation.Nullable;
import java.util.List;

public class SpawnRegistry
{

	private final List<SpawnHandler> spawnHandlers = Lists.newArrayList();

	private final SpawnListener listener;

	public SpawnRegistry()
	{
		this.listener = new SpawnListener(this.spawnHandlers);
	}

	public void registerAetherSpawnHandlers()
	{
		/** PASSIVE **/
		SpawnHandler animals = new SpawnHandler("aether_animals").chunkArea(4).targetEntityCountPerArea(9).updateFrequencyInTicks(200);
		animals.worldCondition(new CheckDimension(DimensionsAether.AETHER));

		PosCondition grassCheck = new CheckBlockStateUnderneath(BlocksAether.aether_grass.getDefaultState(), BlocksAether.aether_grass.getDefaultState().withProperty(BlockAetherGrass.PROPERTY_VARIANT, BlockAetherGrass.ENCHANTED_AETHER_GRASS));

		SpawnEntry flying_cow = new SpawnEntry(EntityFlyingCow.class, 10F, 2, 3).conditiion(grassCheck);
		SpawnEntry ram = new SpawnEntry(EntityKirrid.class, 10F, 2, 3).conditiion(grassCheck);
		SpawnEntry aerbunny = new SpawnEntry(EntityAerbunny.class, 13F, 3, 5).conditiion(grassCheck);
		SpawnEntry phygs = new SpawnEntry(EntityPhyg.class, 13F, 2, 3).conditiion(grassCheck);
		SpawnEntry carrion_sprout = new SpawnEntry(EntityCarrionSprout.class, 10F, 2, 3).conditiion(grassCheck);

		animals.addEntry(flying_cow);
		animals.addEntry(ram);
		animals.addEntry(aerbunny);
		animals.addEntry(phygs);
		animals.addEntry(carrion_sprout);

		/** HOSTILES **/
		SpawnHandler hostiles = new SpawnHandler("aether_hostiles").chunkArea(4).targetEntityCountPerArea(9).updateFrequencyInTicks(1200);
		hostiles.worldCondition(new CheckDimension(DimensionsAether.AETHER));

		PosCondition groundCheck = new CheckBlockStateUnderneath(BlocksAether.aether_grass.getDefaultState(), BlocksAether.holystone.getDefaultState(), BlocksAether.aether_grass.getDefaultState().withProperty(BlockAetherGrass.PROPERTY_VARIANT, BlockAetherGrass.ENCHANTED_AETHER_GRASS));

		SpawnEntry zephyr = new SpawnEntry(EntityZephyr.class, 3F, 2, 3, new FlyingPositionSelector()).conditiion(new CheckBlockStateUnderneath(Blocks.AIR.getDefaultState()));
		SpawnEntry tempest = new SpawnEntry(EntityTempest.class, 10F, 2, 3, new FlyingPositionSelector()).conditiion(new CheckTime(CheckTime.Time.NIGHT)).conditiion(new CheckBlockStateUnderneath(Blocks.AIR.getDefaultState()));
		SpawnEntry cockatrice = new SpawnEntry(EntityCockatrice.class, 12F, 1, 1).conditiion(new CheckTime(CheckTime.Time.NIGHT)).conditiion(groundCheck);
		SpawnEntry swet = new SpawnEntry(EntitySwet.class, 10F, 2, 4).conditiion(groundCheck);
		SpawnEntry aechor_plant = new SpawnEntry(EntityAechorPlant.class, 10F, 2, 3).conditiion(grassCheck);

		hostiles.addEntry(zephyr);
		hostiles.addEntry(tempest);
		hostiles.addEntry(cockatrice);
		hostiles.addEntry(swet);
		hostiles.addEntry(aechor_plant);

		/** FLYING **/
		SpawnHandler flying = new SpawnHandler("aether_flying").chunkArea(9).targetEntityCountPerArea(1).updateFrequencyInTicks(1200);
		flying.worldCondition(new CheckDimension(DimensionsAether.AETHER)).condition(new CheckBiome(BiomesAether.HIGHLANDS));

		SpawnEntry aerwhale = new SpawnEntry(EntityAerwhale.class, 10F, 1, 1, new FlyingPositionSelector()).conditiion(new CheckBlockStateUnderneath(Blocks.AIR.getDefaultState()));

		flying.addEntry(aerwhale);

		this.registerSpawnHandler(animals);
		this.registerSpawnHandler(hostiles);
		this.registerSpawnHandler(flying);
	}

	public void registerSpawnHandler(SpawnHandler spawnHandler)
	{
		this.spawnHandlers.add(spawnHandler);
	}

	public void read()
	{
		for (SpawnHandler handler : this.spawnHandlers)
		{
			NBTTagCompound tag = GGHelper.readNBTFromFile("//data/spawn_areas/" + handler.getUniqueID() + ".dat");

			if (tag == null)
			{
				return;
			}

			handler.read(tag);
		}
	}

	public void write()
	{
		for (SpawnHandler handler : this.spawnHandlers)
		{
			NBTTagCompound tag = new NBTTagCompound();

			handler.write(tag);

			GGHelper.writeNBTToFile(tag, "//data/spawn_areas/" + handler.getUniqueID() + ".dat");
		}
	}

	@SubscribeEvent
	public void onWorldLoad(WorldEvent.Load event)
	{
		if (!event.getWorld().isRemote)
		{
			List<IWorldEventListener> eventListeners = ObfuscationReflectionHelper.getPrivateValue(World.class, event.getWorld(), ReflectionAether.EVENT_LISTENERS.getMappings());

			if (!eventListeners.contains(this.listener))
			{
				event.getWorld().addEventListener(this.listener);
			}
		}
	}

	@SubscribeEvent
	public void onTick(WorldTickEvent event)
	{
		if (event.phase == Phase.END)
		{
			World world = event.world;

			if (!world.isRemote)
			{
				for (SpawnHandler handler : this.spawnHandlers)
				{
					handler.tick(world, world.playerEntities);
				}
			}
		}
	}

	@SubscribeEvent
	public void onLivingDeath(LivingDeathEvent event)
	{

	}

	private static class SpawnListener implements IWorldEventListener
	{

		private List<SpawnHandler> spawnHandlers;

		public SpawnListener(List<SpawnHandler> spawnHandlers)
		{
			this.spawnHandlers = spawnHandlers;
		}

		@Override
		public void notifyBlockUpdate(World worldIn, BlockPos pos, IBlockState oldState, IBlockState newState, int flags)
		{

		}

		@Override
		public void notifyLightSet(BlockPos pos)
		{

		}

		@Override
		public void markBlockRangeForRenderUpdate(int x1, int y1, int z1, int x2, int y2, int z2)
		{

		}

		@Override
		public void playSoundToAllNearExcept(@Nullable EntityPlayer player, SoundEvent soundIn, SoundCategory category, double x, double y, double z, float volume, float pitch)
		{

		}

		@Override
		public void playRecord(SoundEvent soundIn, BlockPos pos)
		{

		}

		@Override
		public void spawnParticle(int particleID, boolean ignoreRange, double xCoord, double yCoord, double zCoord, double xSpeed, double ySpeed, double zSpeed, int... parameters)
		{

		}

		@Override
		public void onEntityAdded(Entity entityIn)
		{

		}

		@Override
		public void onEntityRemoved(Entity entity)
		{
			if (!entity.worldObj.isRemote && entity.isDead && entity.hasCapability(AetherCapabilities.ENTITY_SPAWNING_INFO, null) && !entity.getEntityWorld().isRemote)
			{
				ISpawningInfo spawningInfo = entity.getCapability(AetherCapabilities.ENTITY_SPAWNING_INFO, null);

				EntitySpawn area = spawningInfo.getSpawnArea();

				if (area != null)
				{
					for (SpawnHandler handler : this.spawnHandlers)
					{
						if (handler.getUniqueID().equals(area.getSpawnHandlerUniqueID()))
						{
							boolean areaLoaded = handler.isAreaLoaded(area.getDim(), area.getAreaX(), area.getAreaZ());
							SpawnArea fetchedArea = handler.getAreaReadOnly(area.getDim(), area.getAreaX(), area.getAreaZ());

							fetchedArea.addToEntityCount(-1);

							if (!areaLoaded)
							{
								handler.saveArea(area.getDim(), fetchedArea);
							}
						}
					}
				}
			}
		}

		@Override
		public void broadcastSound(int soundID, BlockPos pos, int data)
		{

		}

		@Override
		public void playEvent(EntityPlayer player, int type, BlockPos blockPosIn, int data)
		{

		}

		@Override
		public void sendBlockBreakProgress(int breakerId, BlockPos pos, int progress)
		{

		}
	}

}
