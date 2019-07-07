package com.gildedgames.aether.common.capabilities.entity.player.modules;

import com.gildedgames.aether.api.registrar.BiomesAether;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.capabilities.entity.player.PlayerAether;
import com.gildedgames.aether.common.capabilities.entity.player.PlayerAetherModule;
import com.gildedgames.aether.common.entities.living.mobs.EntityCockatrice;
import com.gildedgames.aether.common.entities.living.mobs.EntitySwet;
import com.gildedgames.aether.common.entities.living.mobs.EntityTempest;
import com.gildedgames.aether.common.entities.living.mobs.EntityVaranys;
import com.gildedgames.aether.common.util.helpers.AetherHelper;
import com.gildedgames.aether.common.world.spawning.SpawnHandler;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.ArrayList;
import java.util.Random;

public class PlayerCaveSpawnModule extends PlayerAetherModule
{
	private int playerYPos;
	private Biome currentPlayerBiome;
	private ArrayList<Class<? extends EntityCreature>> spawnableMobs;
	private boolean hasSpawned;			// stops things from trying to spawn every update, resets from timer.
	private int timer;

	private final int timerMax = 100;
	private final int maximumYPos = 70;
	private final int minimumYPos = 10;
	private final int maxLightLevel = 8;

	public PlayerCaveSpawnModule(PlayerAether playerAether)
	{
		super(playerAether);
		this.playerYPos = 0;
		this.spawnableMobs = new ArrayList<>();
		this.currentPlayerBiome = null;
		this.hasSpawned = true;
		this.timer = this.timerMax;
	}

	@Override
	public void tickStart(TickEvent.PlayerTickEvent event)
	{
		if (!AetherHelper.isAether(this.getWorld()) || this.getWorld().isRemote)
		{
			return;
		}

		if (this.currentPlayerBiome != this.getWorld().getBiome(this.getEntity().getPosition()))
		{
			this.currentPlayerBiome = this.getWorld().getBiome(this.getEntity().getPosition());
			this.updateEntityList(this.currentPlayerBiome, this.getWorld());
		}

		this.playerYPos = MathHelper.floor(this.getPlayer().getEntity().posY);

		if (this.playerYPos < this.minimumYPos)
		{
			this.playerYPos = this.minimumYPos;
		}
	}

	@Override
	public void tickEnd(TickEvent.PlayerTickEvent event)
	{
		if (this.getEntity().world.isRemote || !AetherHelper.isAether(this.getWorld()))
		{
			return;
		}

		int yTranslation = this.playerYPos / 10;

		if (yTranslation <= 0)
		{
			yTranslation = 1;
		}

		if (!this.spawnableMobs.isEmpty() && this.playerYPos < this.maximumYPos)
		{
			if (!this.hasSpawned)
			{
				int mobInList = this.getWorld().rand.nextInt(this.spawnableMobs.size());
				int numberOfMobs = this.getWorld().rand.nextInt(2);

				for (int i = 0; i < numberOfMobs; i++)
				{
					if (this.getWorld().rand.nextInt(yTranslation) != 0)
					{
						break;
					}
					BlockPos spawnPos;
					EntityCreature mobToSpawn = (EntityCreature)EntityList.newEntity(this.spawnableMobs.get(mobInList), this.getWorld());
					EntityLiving.SpawnPlacementType placementType;
					double distanceToSpawn = 16.0D;
					int yController = 16;

					if (mobToSpawn instanceof EntityTempest)
					{
						if (this.playerYPos < 25)
						{
							yController = 8;
							distanceToSpawn = 8.0D;
						}
					}

					placementType = EntityLiving.SpawnPlacementType.ON_GROUND;
					for (int j = 0; j < 100; j++)
					{
						spawnPos = this.getPosToSpawn(
								(MathHelper.floor(this.getEntity().posX) >> 4),
								(MathHelper.floor(this.getEntity().posZ) >> 4),
								yController,
								this.getWorld().rand);

						mobToSpawn.setPosition(spawnPos.getX(), spawnPos.getY(), spawnPos.getZ());

						if (SpawnHandler.isNotColliding(placementType, this.getWorld(), mobToSpawn))
						{
							continue;
						}

						if (this.getWorld().isAnyPlayerWithinRangeAt(spawnPos.getX(), spawnPos.getY(), spawnPos.getZ(), distanceToSpawn))
						{
							continue;
						}
						if (!this.isGroundBelowPosition(spawnPos, mobToSpawn))
						{
							continue;
						}
						if (this.getWorld().collidesWithAnyBlock(mobToSpawn.getEntityBoundingBox()))
						{
							continue;
						}
						if (this.getWorld().getLight(spawnPos) > this.maxLightLevel)
						{
							continue;
						}

						mobToSpawn.rotationYawHead = mobToSpawn.rotationYaw;
						mobToSpawn.renderYawOffset = mobToSpawn.rotationYaw;
						this.getWorld().spawnEntity(mobToSpawn);
						AetherCore.LOGGER.info("PlayerCaveSpawnModule spawned " + mobToSpawn.toString());
						break;
					}
				}
				this.hasSpawned = true; // set to true regardless of spawning to control how often things spawn a little more.
			}
		}

		--this.timer;
		if (this.timer <= 0)
		{
			this.timer = (this.timerMax * yTranslation);
			this.hasSpawned = false;
		}
	}

	private void updateEntityList(Biome biome, World world)
	{
		this.spawnableMobs.clear();

		this.spawnableMobs.add(EntityCockatrice.class);
		this.spawnableMobs.add(EntityTempest.class);
		this.spawnableMobs.add(EntitySwet.class);

		if (biome == BiomesAether.HIGHLANDS)
		{

		}

		if (biome == BiomesAether.ARCTIC_PEAKS)
		{
			this.spawnableMobs.add(EntityVaranys.class);
		}

		if (biome == BiomesAether.MAGNETIC_HILLS)
		{

		}

		if (biome == BiomesAether.FORGOTTEN_HIGHLANDS)
		{

		}

		if (biome == BiomesAether.IRRADIATED_FORESTS)
		{

		}
	}

	private BlockPos getPosToSpawn(final int chunkX, final int chunkZ, final int yDistance, Random rand)
	{
		int x, y, z;
		x = rand.nextInt(16) + (chunkX*16);
		y = rand.nextInt(yDistance);
		z = rand.nextInt(16) + (chunkZ*16);

		if (rand.nextBoolean())
		{
			y = y * -1;
		}

		y = y + MathHelper.floor(this.getEntity().posY);

		return new BlockPos(x,y,z);
	}

	private boolean isGroundBelowPosition(final BlockPos position, final EntityCreature entity)
	{
		if (entity instanceof EntityTempest)
		{
			return true;
		}

		for (int i = 0; i < 8; i++)
		{
			if (!this.getWorld().isAirBlock(position.down(i)))
			{
				return true;
			}
		}

		return false;
	}
}
