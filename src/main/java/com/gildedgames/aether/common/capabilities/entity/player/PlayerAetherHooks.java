package com.gildedgames.aether.common.capabilities.entity.player;

import com.gildedgames.aether.api.AetherCapabilities;
import com.gildedgames.aether.api.chunk.IPlacementFlagCapability;
import com.gildedgames.aether.api.player.IPlayerAether;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.blocks.IBlockSnowy;
import com.gildedgames.aether.common.entities.util.shared.SharedAetherAttributes;
import com.gildedgames.aether.common.network.NetworkingAether;
import com.gildedgames.aether.common.network.packets.PacketMarkPlayerDeath;
import com.gildedgames.aether.common.network.packets.PacketRequestClientInfo;
import com.gildedgames.aether.common.registry.content.DimensionsAether;
import com.gildedgames.aether.common.util.helpers.IslandHelper;
import com.gildedgames.orbis_api.util.mc.BlockPosDimension;
import com.gildedgames.orbis_api.util.mc.BlockUtil;
import com.google.common.collect.Lists;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSnow;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.common.capabilities.Capability.IStorage;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerDropsEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerChangedDimensionEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerRespawnEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class PlayerAetherHooks
{
	private static ResourceLocation TELEPORTER_RECIPE = AetherCore.getResource("misc/aether_teleporter");

	@SubscribeEvent
	public static void onPlayerAddedToWorld(final EntityJoinWorldEvent event)
	{
		if (event.getEntity() instanceof EntityPlayer)
		{
			final PlayerAether aePlayer = PlayerAether.getPlayer(event.getEntity());

			if (aePlayer != null)
			{
				aePlayer.onEntityJoinWorld();
			}
		}
	}

	@SubscribeEvent
	public static void onPlayerJoined(final PlayerLoggedInEvent event)
	{
		final PlayerAether aePlayer = PlayerAether.getPlayer(event.player);

		if (aePlayer != null)
		{
			NetworkingAether.sendPacketToPlayer(new PacketRequestClientInfo(), (EntityPlayerMP) event.player);

			aePlayer.sendFullUpdate();

			IRecipe teleporterRecipe = ForgeRegistries.RECIPES.getValue(TELEPORTER_RECIPE);

			if (teleporterRecipe != null)
			{
				event.player.unlockRecipes(Lists.newArrayList(teleporterRecipe));
			}
		}
	}

	@SubscribeEvent
	public static void onPlayerLoggedOut(final net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedOutEvent event)
	{
		final PlayerAether aePlayer = PlayerAether.getPlayer(event.player);

		if (aePlayer != null)
		{
			aePlayer.onLoggedOut();
		}
	}

	@SubscribeEvent
	public static void onDeath(final LivingDeathEvent event)
	{
		final PlayerAether aePlayer = PlayerAether.getPlayer(event.getEntity());

		if (aePlayer != null)
		{
			aePlayer.onDeath(event);

			if (aePlayer.getEntity().world.provider.getDimensionType() == DimensionsAether.AETHER)
			{
				aePlayer.getCampfiresModule().setDeathPos(new BlockPosDimension(event.getEntity().getPosition(), aePlayer.getEntity().dimension));
			}
		}
	}

	@SubscribeEvent
	public static void onDrops(final PlayerDropsEvent event)
	{
		final PlayerAether aePlayer = PlayerAether.getPlayer(event.getEntity());

		if (aePlayer != null)
		{
			aePlayer.onDrops(event);
		}
	}

	@SubscribeEvent
	public static void onUpdate(LivingEvent.LivingUpdateEvent event)
	{
		final PlayerAether aePlayer = PlayerAether.getPlayer(event.getEntity());

		if (aePlayer != null)
		{
			aePlayer.onUpdate(event);
		}
	}

	@SubscribeEvent
	public static void onPlayerTick(TickEvent.PlayerTickEvent event)
	{
		final PlayerAether aePlayer = PlayerAether.getPlayer(event.player);

		if (aePlayer != null)
		{
			aePlayer.onPlayerTick(event);
		}
	}

	@SubscribeEvent
	public static void onFall(final LivingFallEvent event)
	{
		final PlayerAether aePlayer = PlayerAether.getPlayer(event.getEntity());

		if (aePlayer != null)
		{
			aePlayer.onFall(event);
		}
	}

	@SubscribeEvent
	public static void onCalculateBreakSpeed(final PlayerEvent.BreakSpeed event)
	{
		final PlayerAether aePlayer = PlayerAether.getPlayer(event.getEntity());

		if (aePlayer != null)
		{
			event.setNewSpeed(event.getOriginalSpeed() * aePlayer.getMiningSpeedMultiplier());
		}
	}

	@SubscribeEvent
	public static void onLivingEntityHurt(final LivingHurtEvent event)
	{
		final PlayerAether aePlayer = PlayerAether.getPlayer(event.getEntity());

		if (aePlayer != null)
		{
			aePlayer.onHurt(event);
		}

		// TODO: remove this dumb debug effect
		final EntityLivingBase entity = event.getEntityLiving();

		final IAttributeInstance attribute = entity.getEntityAttribute(SharedAetherAttributes.STAT_VOLATILE);

		if (attribute == null)
		{
			return;
		}

		final double attempt = Math.random();
		final double threshold = attribute.getAttributeValue();

		if (attempt <= threshold)
		{
			entity.getEntityWorld().newExplosion(entity, entity.posX, entity.posY, entity.posZ, 2.0f, false, true);
			entity.setDead();
		}
	}

	@SubscribeEvent
	public static void onPlaceBlockEvent(final BlockEvent.PlaceEvent event)
	{
		final IPlacementFlagCapability data = event.getWorld().getChunkFromBlockCoords(event.getPos())
				.getCapability(AetherCapabilities.CHUNK_PLACEMENT_FLAG, EnumFacing.UP);

		if (data != null)
		{
			data.markModified(event.getPos());
		}

		final PlayerAether aePlayer = PlayerAether.getPlayer(event.getPlayer());

		IBlockState replaced = event.getBlockSnapshot().getReplacedBlock(), placed = event.getPlacedBlock();
		Block block = placed.getBlock();

		if (replaced.getBlock() instanceof BlockSnow && replaced.getValue(BlockSnow.LAYERS) == 1 && block instanceof IBlockSnowy)
		{
			event.getWorld().setBlockState(event.getPos(), placed.withProperty(IBlockSnowy.PROPERTY_SNOWY, Boolean.TRUE), 2);
		}
		else if (replaced.getBlock() instanceof IBlockSnowy)
		{
			boolean snowy = replaced.getValue(IBlockSnowy.PROPERTY_SNOWY);

			if (block instanceof IBlockSnowy && snowy)
			{
				event.getWorld().setBlockState(event.getPos(), placed.withProperty(IBlockSnowy.PROPERTY_SNOWY, true), 2);
			}
			else if (block instanceof BlockSnow)
			{
				event.getWorld().setBlockState(event.getPos(), replaced.withProperty(IBlockSnowy.PROPERTY_SNOWY, Boolean.TRUE), 2);
			}
		}

		if (aePlayer != null)
		{
			aePlayer.onPlaceBlock(event);
		}
	}

	@SubscribeEvent
	public static void onPlayerClone(final PlayerEvent.Clone event)
	{
		final PlayerAether oldPlayer = PlayerAether.getPlayer(event.getOriginal());

		if (oldPlayer != null)
		{
			final PlayerAether newPlayer = PlayerAether.getPlayer(event.getEntity());

			final IStorage<IPlayerAether> storage = AetherCapabilities.PLAYER_DATA.getStorage();

			final NBTBase state = storage.writeNBT(AetherCapabilities.PLAYER_DATA, oldPlayer, null);
			storage.readNBT(AetherCapabilities.PLAYER_DATA, newPlayer, null, state);
		}
	}

	@SubscribeEvent
	public static void onPlayerChangedDimension(final PlayerChangedDimensionEvent event)
	{
		final PlayerAether aePlayer = PlayerAether.getPlayer(event.player);

		if (aePlayer != null)
		{
			aePlayer.onTeleport(event);
		}
	}

	@SubscribeEvent
	public static void onPlayerRespawn(final PlayerRespawnEvent event)
	{
		final PlayerAether aePlayer = PlayerAether.getPlayer(event.player);

		if (aePlayer != null)
		{
			aePlayer.onRespawn(event);
		}

		if (event.player instanceof EntityPlayerMP && ((EntityPlayerMP) event.player).world.provider.getDimensionType() == DimensionsAether.AETHER)
		{
			BlockPos bedPos = event.player.getBedLocation(((EntityPlayerMP) event.player).dimension);
			final EntityPlayerMP mp = (EntityPlayerMP) event.player;

			if (bedPos != null)
			{
				bedPos = EntityPlayer.getBedSpawnLocation(mp.getServerWorld(), bedPos, mp.isSpawnForced(mp.dimension));
			}

			BlockPos closestCampfire = aePlayer.getCampfiresModule().getClosestCampfire();
			BlockPos islandSpawn = IslandHelper.getOutpostPos(mp.getServerWorld(), mp.getPosition()).add(4, 1, 4);

			final BlockPos respawnPoint = closestCampfire != null ? closestCampfire.add(-1, 0, -1) : islandSpawn;
			boolean obstructed = false;

			if (aePlayer.getCampfiresModule().shouldRespawnAtCampfire() || bedPos == null)
			{
				BlockPos pos = mp.world.getTopSolidOrLiquidBlock(respawnPoint);

				if (pos.getY() > respawnPoint.getY())
				{
					pos = respawnPoint;
				}

				final BlockPos down = pos.down();

				obstructed = mp.getServerWorld().getBlockState(pos.up()) != Blocks.AIR.getDefaultState()
						|| mp.getServerWorld().getBlockState(pos.up(2)) != Blocks.AIR.getDefaultState();

				if (BlockUtil.isSolid(mp.getServerWorld().getBlockState(down)) && !obstructed)
				{
					mp.connection.setPlayerLocation(pos.getX(), pos.getY() + 1, pos.getZ(), 0, 0);

					if (!aePlayer.getProgressModule().hasDiedInAether())
					{
						aePlayer.getProgressModule().setHasDiedInAether(true);

						NetworkingAether.sendPacketToPlayer(new PacketMarkPlayerDeath(aePlayer.getProgressModule().hasDiedInAether()), mp);
					}
				}

				aePlayer.getCampfiresModule().setShouldRespawnAtCampfire(false);
			}

			if (obstructed)
			{
				mp.sendMessage(new TextComponentString("The nearest Outpost was obstructed with blocks - you could not respawn there."));
			}
		}
	}

	@SubscribeEvent
	public static void onBeginWatching(final PlayerEvent.StartTracking event)
	{
		final PlayerAether aeSourcePlayer = PlayerAether.getPlayer(event.getEntityPlayer());
		final PlayerAether aeTargetPlayer = PlayerAether.getPlayer(event.getTarget());

		if (aeSourcePlayer != null && aeTargetPlayer != null)
		{
			aeTargetPlayer.onPlayerBeginWatching(aeSourcePlayer);
		}
	}
}
