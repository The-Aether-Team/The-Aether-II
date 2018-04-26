package com.gildedgames.aether.common;

import com.gildedgames.aether.api.AetherAPI;
import com.gildedgames.aether.api.items.IItemProperties;
import com.gildedgames.aether.api.items.equipment.ItemEquipmentSlot;
import com.gildedgames.aether.api.player.IPlayerAether;
import com.gildedgames.aether.api.player.inventory.IInventoryEquipment;
import com.gildedgames.aether.common.capabilities.entity.player.PlayerAether;
import com.gildedgames.aether.common.entities.living.mobs.EntityAechorPlant;
import com.gildedgames.aether.common.entities.living.npc.EntityNPC;
import com.gildedgames.aether.common.entities.living.passive.EntityCarrionSprout;
import com.gildedgames.aether.common.items.ItemsAether;
import com.gildedgames.aether.common.items.armor.ItemAetherShield;
import com.gildedgames.aether.common.registry.content.DimensionsAether;
import com.gildedgames.aether.common.util.helpers.PlayerUtil;
import com.gildedgames.aether.common.world.aether.ChunkProviderAether;
import com.gildedgames.aether.common.world.aether.TeleporterAether;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.PlayerList;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.Teleporter;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.ChunkProviderServer;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.EntityMountEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingExperienceDropEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerWakeUpEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fluids.FluidEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Supplier;

public class CommonEvents
{
	@SubscribeEvent
	public static void onEntity(EntityJoinWorldEvent event)
	{
		if (event.getEntity() instanceof EntityXPOrb)
		{
			if (event.getWorld().provider.getDimensionType() == DimensionsAether.AETHER
					|| event.getWorld().provider.getDimensionType() == DimensionsAether.NECROMANCER_TOWER)
			{
				event.setCanceled(true);
			}
		}
	}

	@SubscribeEvent
	public static void onExperienceDrop(LivingExperienceDropEvent event)
	{
		if (event.getEntityLiving().world.provider.getDimensionType() == DimensionsAether.AETHER
				|| event.getEntityLiving().world.provider.getDimensionType() == DimensionsAether.NECROMANCER_TOWER)
		{
			event.setCanceled(true);
		}
	}

	@SubscribeEvent
	public static void onBlockPlaced(final BlockEvent.PlaceEvent event)
	{
		final List<EntityLiving> entities = event.getWorld().getEntitiesWithinAABB(EntityLiving.class, new AxisAlignedBB(event.getPos()));

		for (final EntityLiving entity : entities)
		{
			if (entity instanceof EntityAechorPlant || entity instanceof EntityCarrionSprout)
			{
				event.setCanceled(true);

				break;
			}
		}
	}

	@SubscribeEvent
	public static void onWorldLoaded(final WorldEvent.Load event)
	{
		if (event.getWorld() instanceof WorldServer)
		{
			if (event.getWorld().provider.getDimensionType() == DimensionsAether.AETHER)
			{
				final WorldServer world = (WorldServer) event.getWorld();

				AetherCore.TELEPORTER = new TeleporterAether(world);

				IChunkProvider provider = new ChunkProviderAether((WorldServer) event.getWorld(), (ChunkProviderServer) event.getWorld().getChunkProvider());

				ObfuscationReflectionHelper.setPrivateValue(World.class, event.getWorld(), provider, "field_73020_y", "chunkProvider");
			}
		}
	}

	@SubscribeEvent
	public static void onPlayerSleepInBed(final PlayerWakeUpEvent event)
	{
		final World world = event.getEntityPlayer().world;

		if (!world.isRemote && world.provider.getDimensionType() == DimensionsAether.AETHER)
		{
			final MinecraftServer server = FMLCommonHandler.instance().getMinecraftServerInstance();

			final WorldServer worldServer = server.getWorld(0);

			if (world.getGameRules().getBoolean("doDaylightCycle") && event.getEntityPlayer().isPlayerFullyAsleep())
			{
				final long i = worldServer.getWorldInfo().getWorldTime() + 24000L;

				worldServer.getWorldInfo().setWorldTime(i - i % 24000L);
			}
		}
	}

	@SubscribeEvent
	public static void onFluidEvent(final FluidEvent.FluidDrainingEvent event)
	{

	}

	@SubscribeEvent
	public static void onLivingEntityUpdate(final LivingEvent.LivingUpdateEvent event)
	{
		final Entity entity = event.getEntity();

		// Checks whether or not an entity is in the Aether's void
		if (entity.world.provider.getDimensionType() == DimensionsAether.AETHER && entity.posY < -10)
		{
			/*List<UUID> uniquePassengerIDs = Lists.newArrayList();

			for (Entity passenger : entity.getPassengers())
			{
				uniquePassengerIDs.add(passenger.getUniqueID());
			}

			if (!entity.worldObj.isRemote)
			{

			}

			List<Entity> entities = entity.worldObj.getEntitiesWithinAABB(Entity.class, entity.getEntityBoundingBox().expand(16.0D, 16.0D, 16.0D));

			for (Entity passenger : entities)
			{
				if (uniquePassengerIDs.contains(passenger.getUniqueID()))
				{
					passenger.startRiding(entity, true);
				}
			}*/

			if (!entity.world.isRemote)
			{
				if (entity instanceof EntityPlayer)
				{
					final EntityPlayer player = (EntityPlayer) entity;

					player.attackEntityFrom(DamageSource.OUT_OF_WORLD, 200.0F);

					if (player.getHealth() <= 0 && !player.isDead)
					{
						player.isDead = true;
					}
				}
				else
				{
					entity.setDead();
				}
			}
		}
	}

	public static Entity teleportEntity(final Entity entity, final WorldServer toWorld, final Teleporter teleporter, final int dimension)
	{
		return teleportEntity(entity, toWorld, teleporter, dimension, null);
	}

	/**
	 * Teleports any entity by duplicating it and . the old one. If {@param entity} is a player,
	 * the entity will be transferred instead of duplicated.
	 *
	 * @return A newsystem entity if {@param entity} wasn't a player, or the same entity if it was a player
	 */
	public static Entity teleportEntity(final Entity entity, final WorldServer toWorld, final Teleporter teleporter, final int dimension,
			@Nullable final Supplier<BlockPos> optionalLoc)
	{
		if (entity == null)
		{
			return null;
		}

		if (entity instanceof EntityPlayer)
		{
			final PlayerList playerList = FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList();

			// Players require special magic to be teleported correctly, and are not duplicated

			if (!toWorld.isRemote)
			{
				final EntityPlayerMP player = (EntityPlayerMP) entity;

				if (!net.minecraftforge.common.ForgeHooks.onTravelToDimension(player, dimension))
				{
					return player;
				}

				playerList.transferPlayerToDimension((EntityPlayerMP) entity, dimension, teleporter);

				if (optionalLoc == null)
				{
					player.connection.setPlayerLocation(player.posX, player.posY, player.posZ, 0, 0);
				}
				else
				{
					final BlockPos loc = optionalLoc.get();

					player.connection.setPlayerLocation(loc.getX(), loc.getY(), loc.getZ(), 225, 0);
				}

				/** Strange flag that needs to be set to prevent the NetHandlerPlayServer instances from resetting your position **/
				ObfuscationReflectionHelper.setPrivateValue(EntityPlayerMP.class, player, true, ReflectionAether.INVULNERABLE_DIMENSION_CHANGE.getMappings());
			}

			return entity;
		}
		else
		{
			final Entity newEntity = entity.changeDimension(dimension);

			// Forces the entity to be sent to clients as early as possible
			newEntity.forceSpawn = true;
			newEntity.setPositionAndUpdate(entity.posX, entity.posY, entity.posZ);

			return newEntity;
		}
	}

	@SubscribeEvent
	public static void onPlayerRightClickItem(final PlayerInteractEvent.RightClickItem event)
	{
		final IPlayerAether aePlayer = PlayerAether.getPlayer(event.getEntityPlayer());

		if (event.getItemStack().isEmpty() || aePlayer == null)
		{
			return;
		}

		final boolean result = CommonEvents.tryEquipEquipment(aePlayer, event.getItemStack(), event.getHand());

		if (result)
		{
			// Unfortunately we have to play the equip animation manually...
			if (event.getEntityPlayer().world.isRemote)
			{
				Minecraft.getMinecraft().getItemRenderer().resetEquippedProgress(EnumHand.MAIN_HAND);
			}

			event.getEntityPlayer().world
					.playSound(event.getEntityPlayer(), event.getEntityPlayer().getPosition(), SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, SoundCategory.NEUTRAL,
							1.0f, 1.0f);
			event.setCanceled(true);
		}
	}

	private static boolean tryEquipEquipment(final IPlayerAether player, final ItemStack stack, final EnumHand hand)
	{
		final IInventoryEquipment inventory = player.getEquipmentModule().getInventory();

		final IItemProperties equipment = AetherAPI.content().items().getProperties(stack.getItem());

		if (equipment.getEquipmentSlot() != ItemEquipmentSlot.NONE)
		{
			final int slot = inventory.getNextEmptySlotForType(equipment.getEquipmentSlot());

			if (slot >= 0)
			{
				final ItemStack newStack = stack.copy();
				newStack.setCount(1);

				inventory.setInventorySlotContents(slot, newStack);

				if (!player.getEntity().capabilities.isCreativeMode)
				{
					// Technically, there should never be STACKABLE equipment, but in case there is, we need to handle it.
					stack.shrink(1);
				}

				player.getEntity().setHeldItem(hand, stack.getCount() <= 0 ? ItemStack.EMPTY : stack);

				return true;
			}
		}

		return false;
	}

	@SubscribeEvent
	public static void onPlayerInteract(final PlayerInteractEvent.EntityInteract event)
	{
		if (event.getTarget() instanceof EntityCow && !((EntityCow) event.getTarget()).isChild())
		{
			final ItemStack stack = event.getEntityPlayer().inventory.getCurrentItem();

			if (stack.getItem() == ItemsAether.skyroot_bucket)
			{
				PlayerUtil.fillBucketInHand(event.getEntityPlayer(), event.getItemStack(), new ItemStack(ItemsAether.skyroot_milk_bucket));
			}
		}
	}

	@SubscribeEvent
	public static void onEntityAttacked(final LivingAttackEvent event)
	{
		if (!(event.getEntityLiving() instanceof EntityPlayer))
		{
			return;
		}

		final EntityPlayer player = (EntityPlayer) event.getEntityLiving();

		if (!event.getSource().isUnblockable() && player.isActiveItemStackBlocking())
		{
			final Vec3d vec3d = event.getSource().getDamageLocation();

			if (vec3d != null)
			{
				final Vec3d look = player.getLook(1.0F);

				Vec3d reverse = vec3d.subtractReverse(new Vec3d(player.posX, player.posY, player.posZ)).normalize();
				reverse = new Vec3d(reverse.x, 0.0D, reverse.z);

				if (reverse.dotProduct(look) < 0.0D)
				{
					final float damage = event.getAmount();

					if (damage >= 3.0F && player.getActiveItemStack().getItem() instanceof ItemAetherShield)
					{
						final int itemDamage = 1 + MathHelper.floor(damage);

						player.getActiveItemStack().damageItem(itemDamage, player);

						if (player.getActiveItemStack().getCount() <= 0)
						{
							final EnumHand hand = player.getActiveHand();

							ForgeEventFactory.onPlayerDestroyItem(player, player.getActiveItemStack(), hand);

							if (hand == EnumHand.MAIN_HAND)
							{
								player.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, ItemStack.EMPTY);
							}
							else
							{
								player.setItemStackToSlot(EntityEquipmentSlot.OFFHAND, ItemStack.EMPTY);
							}

							player.setHeldItem(player.getActiveHand(), ItemStack.EMPTY);

							player.playSound(SoundEvents.ITEM_SHIELD_BREAK, 0.8F, 0.8F + player.world.rand.nextFloat() * 0.4F);
						}
					}
				}
			}
		}
	}

	@SubscribeEvent
	public static void onEntityMounted(final EntityMountEvent event)
	{
		if (event.getEntityMounting() instanceof EntityNPC)
		{
			event.setCanceled(true);
		}
	}
}
