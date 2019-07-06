package com.gildedgames.aether.common.capabilities.entity.player;

import com.gildedgames.aether.api.AetherCapabilities;
import com.gildedgames.aether.api.chunk.IPlacementFlagCapability;
import com.gildedgames.aether.api.effects_system.IAetherStatusEffectPool;
import com.gildedgames.aether.api.effects_system.IAetherStatusEffects;
import com.gildedgames.aether.api.player.IPlayerAether;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.blocks.IBlockSnowy;
import com.gildedgames.aether.common.capabilities.entity.player.modules.PlayerCampfiresModule;
import com.gildedgames.aether.common.capabilities.entity.player.modules.PlayerProgressModule;
import com.gildedgames.aether.common.containers.ContainerTrade;
import com.gildedgames.aether.common.entities.util.shared.SharedAetherAttributes;
import com.gildedgames.aether.common.network.NetworkingAether;
import com.gildedgames.aether.common.network.packets.PacketMarkPlayerDeath;
import com.gildedgames.aether.common.network.packets.PacketRequestClientInfo;
import com.gildedgames.aether.common.registry.content.DimensionsAether;
import com.gildedgames.aether.common.util.helpers.IslandHelper;
import com.gildedgames.orbis.lib.util.mc.BlockPosDimension;
import com.gildedgames.orbis.lib.util.mc.BlockUtil;
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
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.event.entity.living.LivingHealEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerDropsEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.*;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

@Mod.EventBusSubscriber
public class PlayerAetherHooks
{
	private static final ResourceLocation TELEPORTER_RECIPE = AetherCore.getResource("misc/aether_teleporter");

	@SubscribeEvent
	public static void onPlayerJoined(final PlayerLoggedInEvent event)
	{
		NetworkingAether.sendPacketToPlayer(new PacketRequestClientInfo(), (EntityPlayerMP) event.player);

		final PlayerAether aePlayer = PlayerAether.getPlayer(event.player);
		aePlayer.sendFullUpdate();

		IRecipe recipe = ForgeRegistries.RECIPES.getValue(TELEPORTER_RECIPE);

		if (recipe != null)
		{
			event.player.unlockRecipes(Lists.newArrayList(recipe));
		}
	}

	@SubscribeEvent
	public static void onPlayerLoggedOut(final PlayerLoggedOutEvent event)
	{
		final PlayerAether aePlayer = PlayerAether.getPlayer(event.player);
		aePlayer.onLoggedOut();
	}

	@SubscribeEvent
	public static void onDeath(final LivingDeathEvent event)
	{
		if (!(event.getEntity() instanceof EntityPlayer))
		{
			return;
		}

		final PlayerAether aePlayer = PlayerAether.getPlayer((EntityPlayer) event.getEntity());
		aePlayer.onDeath(event);

		if (aePlayer.getEntity().world.provider.getDimensionType() == DimensionsAether.AETHER)
		{
			aePlayer.getModule(PlayerCampfiresModule.class).setDeathPos(new BlockPosDimension(event.getEntity().getPosition(), aePlayer.getEntity().dimension));
		}
	}

	@SubscribeEvent
	public static void onDrops(final PlayerDropsEvent event)
	{
		if (!(event.getEntity() instanceof EntityPlayer))
		{
			return;
		}

		final PlayerAether aePlayer = PlayerAether.getPlayer((EntityPlayer) event.getEntity());
		aePlayer.onDrops(event);
	}

	@SubscribeEvent
	public static void onPlayerTick(TickEvent.PlayerTickEvent event)
	{
		final PlayerAether aePlayer = PlayerAether.getPlayer(event.player);
		aePlayer.onPlayerTick(event);
	}

	@SubscribeEvent
	public static void onFall(final LivingFallEvent event)
	{
		if (!(event.getEntity() instanceof EntityPlayer))
		{
			return;
		}

		final PlayerAether aePlayer = PlayerAether.getPlayer((EntityPlayer) event.getEntity());
		aePlayer.onFall(event);
	}

	@SubscribeEvent
	public static void onCalculateBreakSpeed(final PlayerEvent.BreakSpeed event)
	{
		if (!(event.getEntity() instanceof EntityPlayer))
		{
			return;
		}

		final PlayerAether aePlayer = PlayerAether.getPlayer((EntityPlayer) event.getEntity());
		event.setNewSpeed(event.getOriginalSpeed() * aePlayer.getMiningSpeedMultiplier());
	}

	@SubscribeEvent
	public static void onLivingEntityHurt(final LivingHurtEvent event)
	{
		if (!(event.getEntity() instanceof EntityPlayer))
		{
			return;
		}

		final PlayerAether aePlayer = PlayerAether.getPlayer((EntityPlayer) event.getEntity());
		aePlayer.onHurt(event);

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
		final IPlacementFlagCapability data = event.getWorld().getChunk(event.getPos())
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

		aePlayer.onPlaceBlock(event);
	}

	@SubscribeEvent
	public static void onPlayerClone(final PlayerEvent.Clone event)
	{
		if (!(event.getEntity() instanceof EntityPlayer))
		{
			return;
		}

		final PlayerAether oldPlayer = PlayerAether.getPlayer(event.getOriginal());
		final PlayerAether newPlayer = PlayerAether.getPlayer((EntityPlayer) event.getEntity());

		final IStorage<IPlayerAether> storage = AetherCapabilities.PLAYER_DATA.getStorage();

		final NBTBase state = storage.writeNBT(AetherCapabilities.PLAYER_DATA, oldPlayer, null);
		storage.readNBT(AetherCapabilities.PLAYER_DATA, newPlayer, null, state);
	}

	@SubscribeEvent
	public static void onPlayerChangedDimension(final PlayerChangedDimensionEvent event)
	{
		final PlayerAether aePlayer = PlayerAether.getPlayer(event.player);
		aePlayer.onTeleport(event);
	}

	@SubscribeEvent
	public static void onPlayerRespawn(final PlayerRespawnEvent event)
	{
		final PlayerAether aePlayer = PlayerAether.getPlayer(event.player);
		aePlayer.onRespawn(event);

		if (event.player instanceof EntityPlayerMP && ((EntityPlayerMP) event.player).world.provider.getDimensionType() == DimensionsAether.AETHER)
		{
			BlockPos bedPos = event.player.getBedLocation(((EntityPlayerMP) event.player).dimension);
			final EntityPlayerMP mp = (EntityPlayerMP) event.player;

			if (bedPos != null)
			{
				bedPos = EntityPlayer.getBedSpawnLocation(mp.getServerWorld(), bedPos, mp.isSpawnForced(mp.dimension));
			}

			PlayerCampfiresModule campfiresModule = aePlayer.getModule(PlayerCampfiresModule.class);
			BlockPos closestCampfire = campfiresModule.getClosestCampfire();
			BlockPos islandSpawn = IslandHelper.getOutpostPos(mp.getServerWorld(), mp.getPosition());

			final BlockPos respawnPoint = closestCampfire != null ? closestCampfire.add(-1, 0, -1) : islandSpawn;
			boolean obstructed = false;

			if (campfiresModule.shouldRespawnAtCampfire() || bedPos == null)
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

					PlayerProgressModule progressModule = aePlayer.getModule(PlayerProgressModule.class);

					if (!progressModule.hasDiedInAether())
					{
						progressModule.setHasDiedInAether(true);

						NetworkingAether.sendPacketToPlayer(new PacketMarkPlayerDeath(progressModule.hasDiedInAether()), mp);
					}
				}

				campfiresModule.setShouldRespawnAtCampfire(false);
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
		if (!(event.getTarget() instanceof EntityPlayer))
		{
			return;
		}

		final PlayerAether aeSourcePlayer = PlayerAether.getPlayer(event.getEntityPlayer());
		final PlayerAether aeTargetPlayer = PlayerAether.getPlayer((EntityPlayer) event.getTarget());

		aeTargetPlayer.onPlayerBeginWatching(aeSourcePlayer);
	}

	@SubscribeEvent
	public static void onPlayerPickUpItem(final ItemPickupEvent event)
	{
		if (event.player.openContainer instanceof ContainerTrade)
		{
			((ContainerTrade) event.player.openContainer).addItemToQueue(event.getStack());
		}
	}

	@SubscribeEvent
	public static void onLivingHeal(final LivingHealEvent event)
	{
		if (event.getEntityLiving() != null)
		{
			if (event.getAmount() <= 1.5)
			{
				IAetherStatusEffectPool statusEffectPool = event.getEntityLiving().getCapability(AetherCapabilities.STATUS_EFFECT_POOL, null);

				if (statusEffectPool != null)
				{
					if (statusEffectPool.isEffectApplied(IAetherStatusEffects.effectTypes.AMBROSIUM_POISONING))
					{
						event.setCanceled(true);
					}
				}
			}
		}
	}

}
