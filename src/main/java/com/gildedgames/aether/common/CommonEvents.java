package com.gildedgames.aether.common;

import com.gildedgames.aether.api.AetherAPI;
import com.gildedgames.aether.api.entity.EntityNPC;
import com.gildedgames.aether.api.items.IItemProperties;
import com.gildedgames.aether.api.items.equipment.ItemEquipmentSlot;
import com.gildedgames.aether.api.player.IPlayerAether;
import com.gildedgames.aether.api.player.inventory.IInventoryEquipment;
import com.gildedgames.aether.common.capabilities.entity.player.PlayerAether;
import com.gildedgames.aether.common.capabilities.entity.player.modules.PlayerEquipmentModule;
import com.gildedgames.aether.common.capabilities.entity.player.modules.PlayerTeleportingModule;
import com.gildedgames.aether.common.capabilities.entity.player.modules.PlayerTradeModule;
import com.gildedgames.aether.common.containers.ContainerLoadingScreen;
import com.gildedgames.aether.common.entities.living.mobs.EntityAechorPlant;
import com.gildedgames.aether.common.entities.living.passive.EntityCarrionSprout;
import com.gildedgames.aether.common.events.PostAetherTravelEvent;
import com.gildedgames.aether.common.items.ItemsAether;
import com.gildedgames.aether.common.items.armor.ItemAetherShield;
import com.gildedgames.aether.common.network.AetherGuiHandler;
import com.gildedgames.aether.common.network.NetworkingAether;
import com.gildedgames.aether.common.network.packets.PacketCloseLoadingScreen;
import com.gildedgames.aether.common.network.packets.PacketLoadingScreenPercent;
import com.gildedgames.aether.common.registry.content.DimensionsAether;
import com.gildedgames.aether.common.util.helpers.AetherHelper;
import com.gildedgames.aether.common.util.helpers.MathUtil;
import com.gildedgames.aether.common.util.helpers.PlayerUtil;
import com.gildedgames.orbis.lib.preparation.impl.util.PrepHelper;
import com.gildedgames.orbis.lib.util.mc.BlockPosDimension;
import com.google.common.collect.Lists;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.EntityMountEvent;
import net.minecraftforge.event.entity.EntityTravelToDimensionEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingExperienceDropEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerWakeUpEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

import java.util.List;

@Mod.EventBusSubscriber
public class CommonEvents
{

	@SubscribeEvent
	public static void onEvent(EntityTravelToDimensionEvent event)
	{
		if (AetherHelper.isEnabled(event.getDimension()) && AetherHelper.isEnabled(event.getDimension()))
		{
			PlayerAether playerAether = PlayerAether.getPlayer((EntityPlayer) event.getEntity());

			if (!AetherHelper.isNecromancerTower(event.getEntity().dimension))
			{
				playerAether.getModule(PlayerTeleportingModule.class)
						.setNonAetherPos(new BlockPosDimension(event.getEntity().getPosition(), event.getEntity().dimension));
			}
		}
	}

	@SubscribeEvent
	public static void onEvent(PostAetherTravelEvent event)
	{
		if (!event.getEntity().getEntityWorld().isRemote)
		{
			if (event.getEntity() instanceof EntityPlayer)
			{
				EntityPlayer player = (EntityPlayer) event.getEntity();

				player.openGui(AetherCore.INSTANCE, AetherGuiHandler.AETHER_LOADING_ID, player.getEntityWorld(), player.getPosition().getX(),
						player.getPosition().getY(), player.getPosition().getZ());
			}
		}
	}

	@SubscribeEvent
	public static void onEvent(LivingEvent.LivingUpdateEvent event)
	{
		if (!event.getEntity().getEntityWorld().isRemote && event.getEntity() instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer) event.getEntity();
			PlayerAether playerAether = PlayerAether.getPlayer(player);

			if (player.getEntityWorld().provider.getDimensionType() != DimensionsAether.AETHER)
			{
				return;
			}

			PlayerTeleportingModule teleportingModule = playerAether.getModule(PlayerTeleportingModule.class);

			if (player.openContainer instanceof ContainerLoadingScreen)
			{
				if (PrepHelper.isSectorLoaded(player.getEntityWorld(), player.chunkCoordX, player.chunkCoordZ))
				{
					boolean isLoaded = true;

					int radius = Math.min(player.getServer().getPlayerList().getViewDistance(), 10);

					int count = 0;

					for (int x = player.chunkCoordX - radius; x < player.chunkCoordX + radius; x++)
					{
						for (int z = player.chunkCoordZ - radius; z < player.chunkCoordZ + radius; z++)
						{
							Chunk chunk = player.world.getChunkProvider().getLoadedChunk(x, z);

							if (chunk == null)
							{
								isLoaded = false;
							}
							else
							{
								count++;
							}
						}
					}

					if (isLoaded)
					{
						player.closeScreen();

						NetworkingAether.sendPacketToPlayer(new PacketCloseLoadingScreen(), (EntityPlayerMP) player);

						NetworkingAether.sendPacketToPlayer(new PacketLoadingScreenPercent(0.0F), (EntityPlayerMP) player);

						List<IRecipe> toUnlock = Lists.newArrayList();

						for (IRecipe r : ForgeRegistries.RECIPES)
						{
							ResourceLocation loc = Item.REGISTRY.getNameForObject(r.getRecipeOutput().getItem());

							if (loc != null && loc.getNamespace().equals(AetherCore.MOD_ID))
							{
								toUnlock.add(r);
							}
						}

						player.unlockRecipes(toUnlock);
					}
					else
					{
						float diam = radius + radius;

						float percent = ((float) count / (diam * diam)) * 100.0F;

						if (!MathUtil.epsilonEquals(teleportingModule.getLastPercent(), percent))
						{
							teleportingModule.setLastPercent(percent);

							NetworkingAether.sendPacketToPlayer(new PacketLoadingScreenPercent(percent), (EntityPlayerMP) player);
						}
					}
				}
			}
		}
	}

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

	@SubscribeEvent
	public static void onPlayerRightClickItem(final PlayerInteractEvent.RightClickItem event)
	{
		final IPlayerAether aePlayer = PlayerAether.getPlayer(event.getEntityPlayer());

		if (event.getItemStack().isEmpty())
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
		final IInventoryEquipment inventory = player.getModule(PlayerEquipmentModule.class).getInventory();

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
			ItemStack item = event.getEntityPlayer().getHeldItem(event.getHand());

			if (item.getItem() == ItemsAether.skyroot_bucket)
			{
				PlayerUtil.fillBucketInHand(event.getEntityPlayer(), event.getHand(), item, new ItemStack(ItemsAether.skyroot_milk_bucket));
			}
		}
		else if (event.getSide().isServer() && event.getTarget() instanceof EntityPlayer && event.getHand() == EnumHand.MAIN_HAND && event.getItemStack()
				.isEmpty())
		{
			PlayerTradeModule me = PlayerAether.getPlayer(event.getEntityPlayer()).getModule(PlayerTradeModule.class);
			PlayerTradeModule other = PlayerAether.getPlayer((EntityPlayer) event.getTarget()).getModule(PlayerTradeModule.class);

			if (me.getPlayer().equals(other.getTarget()) && other.canAccept(event.getEntity().getPosition()))
			{
				me.setTrading(other);
				other.accept();
				AetherCore.LOGGER.info(event.getTarget().getDisplayName().getFormattedText() + " is now trading with " + event.getEntity().getDisplayName()
						.getFormattedText());
			}
			else if (!other.isTrading() && me.canRequest())
			{
				me.request(other.getPlayer());
			}
			else if (me.getFailTime() == 0)
			{
				me.failRequest(other);
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
		if (EntityNPC.class.isAssignableFrom(event.getEntityMounting().getClass()))
		{
			event.setCanceled(true);
		}
	}
}
