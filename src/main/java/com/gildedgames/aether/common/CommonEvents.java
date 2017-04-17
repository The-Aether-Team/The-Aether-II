package com.gildedgames.aether.common;

import com.gildedgames.aether.api.AetherAPI;
import com.gildedgames.aether.api.capabilites.entity.IPlayerAether;
import com.gildedgames.aether.api.items.equipment.IEquipmentProperties;
import com.gildedgames.aether.api.player.inventory.IInventoryEquipment;
import com.gildedgames.aether.common.blocks.BlocksAether;
import com.gildedgames.aether.common.blocks.construction.BlockAetherPortal;
import com.gildedgames.aether.common.blocks.natural.BlockAercloud;
import com.gildedgames.aether.common.capabilities.player.PlayerAether;
import com.gildedgames.aether.common.entities.living.mobs.EntityAechorPlant;
import com.gildedgames.aether.common.entities.living.npc.EntityNPC;
import com.gildedgames.aether.common.entities.living.passive.EntityCarrionSprout;
import com.gildedgames.aether.common.items.ItemsAether;
import com.gildedgames.aether.common.items.armor.ItemAetherShield;
import com.gildedgames.aether.common.registry.content.DimensionsAether;
import com.gildedgames.aether.common.util.helpers.PlayerUtil;
import com.gildedgames.aether.common.world.dimensions.aether.TeleporterAether;
import com.gildedgames.aether.common.world.util.TeleporterGeneric;
import com.google.common.collect.Lists;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.PlayerList;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.Teleporter;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.event.entity.EntityMountEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.event.entity.player.FillBucketEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerWakeUpEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fluids.FluidActionResult;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.List;
import java.util.Optional;
import java.util.Random;

public class CommonEvents
{
	@SubscribeEvent
	public static void onBlockPlaced(BlockEvent.PlaceEvent event)
	{
		List<EntityLiving> entities = event.getWorld().getEntitiesWithinAABB(EntityLiving.class, new AxisAlignedBB(event.getPos()));

		for (EntityLiving entity : entities)
		{
			if (entity instanceof EntityAechorPlant || entity instanceof EntityCarrionSprout)
			{
				event.setCanceled(true);

				break;
			}
		}
	}

	@SubscribeEvent
	public static void onWorldLoaded(WorldEvent.Load event)
	{
		if (event.getWorld() instanceof WorldServer)
		{
			if (event.getWorld().provider.getDimensionType() == DimensionsAether.AETHER)
			{
				WorldServer world = (WorldServer) event.getWorld();

				AetherCore.TELEPORTER = new TeleporterAether(world);
			}
		}
	}

	@SubscribeEvent
	public static void onPlayerSleepInBed(PlayerWakeUpEvent event)
	{
		World world = event.getEntityPlayer().world;

		if (!world.isRemote && world.provider.getDimensionType() == DimensionsAether.AETHER)
		{
			MinecraftServer server = FMLCommonHandler.instance().getMinecraftServerInstance();

			WorldServer worldServer = server.worldServerForDimension(0);

			if (world.getGameRules().getBoolean("doDaylightCycle") && event.getEntityPlayer().isPlayerFullyAsleep())
			{
				long i = worldServer.getWorldInfo().getWorldTime() + 24000L;

				worldServer.getWorldInfo().setWorldTime(i - i % 24000L);
			}
		}
	}

	@SubscribeEvent
	public static void onPlayerUseBucket(FillBucketEvent event)
	{
		if (event.getTarget() != null && event.getTarget().typeOfHit == RayTraceResult.Type.BLOCK)
		{
			FluidStack fluidStack = null;

			if (event.getEmptyBucket().hasCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, null))
			{
				fluidStack = FluidUtil.getFluidContained(event.getEmptyBucket());
			}

			final EntityPlayer player = event.getEntityPlayer();

			final BlockPos pos = event.getTarget().getBlockPos().offset(event.getTarget().sideHit);

			boolean hasWaterFluid = fluidStack != null && fluidStack.getFluid().getName().equals(FluidRegistry.WATER.getName());
			boolean hasLavaFluid = fluidStack != null && fluidStack.getFluid().getName().equals(FluidRegistry.LAVA.getName());

			if (hasWaterFluid || event.getEmptyBucket().getItem() == Items.WATER_BUCKET
					|| event.getEmptyBucket().getItem() == ItemsAether.skyroot_water_bucket)
			{
				CommonEvents.onWaterPlaced(event, player, pos);
			}
			else if (hasLavaFluid || event.getEmptyBucket().getItem() == Items.LAVA_BUCKET)
			{
				CommonEvents.onLavaPlaced(event, player, pos);
			}
		}
	}

	private static boolean tryToCreatePortal(World world, BlockPos target, BlockPos pos)
	{
		if (world.getBlockState(target).getBlock() == Blocks.GLOWSTONE)
		{
			BlockAetherPortal.Size size = new BlockAetherPortal.Size(world, pos, EnumFacing.Axis.X);

			if (size.isWithinSizeBounds() && size.getPortalBlocks() == 0)
			{
				size.createPortal();

				return true;
			}
			else
			{
				size = new BlockAetherPortal.Size(world, pos, EnumFacing.Axis.Z);

				if (size.isWithinSizeBounds() && size.getPortalBlocks() == 0)
				{
					size.createPortal();

					return true;
				}
			}
		}

		return false;
	}

	@SubscribeEvent
	public static void onLivingEntityUpdate(LivingEvent.LivingUpdateEvent event)
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
					EntityPlayer player = (EntityPlayer) entity;
					PlayerAether playerAether = PlayerAether.getPlayer(player);

					if (playerAether.getParachuteModule().isParachuting())
					{
						CommonEvents.onFallenFromAether(entity);
					}
					else
					{
						player.attackEntityFrom(DamageSource.OUT_OF_WORLD, 200.0F);

						if (player.getHealth() <= 0 && !player.isDead)
						{
							player.sendStatusMessage(new TextComponentString("You fell out of the Aether without a Cloud Parachute. Ouch!"), false);
							player.isDead = true;
						}
					}
				}
				else
				{
					entity.setDead();
				}
			}
		}
	}

	private static void onFallenFromAether(Entity entity)
	{
		MinecraftServer server = FMLCommonHandler.instance().getMinecraftServerInstance();

		WorldServer toWorld = DimensionManager.getWorld(0);

		Teleporter teleporter = new TeleporterGeneric(toWorld);

		// TODO: Recrusive teleporting, newsystem in 1.9/1.10
		Entity mount = entity.getRidingEntity();

		boolean hasPassengers = !entity.getPassengers().isEmpty();

		List<Entity> teleportedPassengers = Lists.newLinkedList();

		Entity teleportedEntity;

		BlockPos teleportPos = new BlockPos(entity.posX, 200 + entity.posY, entity.posZ);

		if (hasPassengers)
		{
			for (Entity passenger : entity.getPassengers())
			{
				passenger.dismountRidingEntity();

				teleportedPassengers.add(CommonEvents.teleportEntity(passenger, toWorld, teleporter, 0));

				passenger.setPositionAndUpdate(teleportPos.getX(), teleportPos.getY(), teleportPos.getZ());
			}
		}

		if (mount != null)
		{
			// Dismounts the entity it's riding first as teleporting will cause updates to the entities
			entity.dismountRidingEntity();

			// Teleports entity first, then what it's riding

			teleportedEntity = CommonEvents.teleportEntity(entity, toWorld, teleporter, 0);

			Entity teleportedMount = CommonEvents.teleportEntity(mount, toWorld, teleporter, 0);

			teleportedEntity.setPositionAndUpdate(teleportPos.getX(), teleportPos.getY(), teleportPos.getZ());
			teleportedMount.setPositionAndUpdate(teleportPos.getX(), teleportPos.getY(), teleportPos.getZ());

			// Re-onMounted what it was previously riding
			teleportedEntity.startRiding(teleportedMount, true);
		}
		else
		{
			teleportedEntity = CommonEvents.teleportEntity(entity, toWorld, teleporter, 0);

			teleportedEntity.setPositionAndUpdate(teleportPos.getX(), teleportPos.getY(), teleportPos.getZ());
		}

		if (hasPassengers)
		{
			for (Entity passenger : teleportedPassengers)
			{
				passenger.startRiding(teleportedEntity, true);
			}
		}
	}

	/**
	 * Teleports any entity by duplicating it and . the old one. If {@param entity} is a player,
	 * the entity will be transferred instead of duplicated.
	 *
	 * @return A newsystem entity if {@param entity} wasn't a player, or the same entity if it was a player
	 */
	public static Entity teleportEntity(Entity entity, WorldServer toWorld, Teleporter teleporter, int dimension)
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
				EntityPlayerMP player = (EntityPlayerMP) entity;

				playerList.transferPlayerToDimension((EntityPlayerMP) entity, dimension, teleporter);
				player.connection.setPlayerLocation(player.posX, player.posY, player.posZ, 0, 0);

				/** Strange flag that needs to be set to prevent the NetHandlerPlayServer instances from resetting your position **/
				ObfuscationReflectionHelper.setPrivateValue(EntityPlayerMP.class, player, true, ReflectionAether.INVULNERABLE_DIMENSION_CHANGE.getMappings());
			}

			return entity;
		}
		else
		{
			Entity newEntity = entity.changeDimension(dimension);

			// Forces the entity to be sent to clients as early as possible
			newEntity.forceSpawn = true;
			newEntity.setPositionAndUpdate(entity.posX, entity.posY, entity.posZ);

			return newEntity;
		}
	}

	@SubscribeEvent
	public static void onPlayerRightClickItem(PlayerInteractEvent.RightClickItem event)
	{
		IPlayerAether aePlayer = PlayerAether.getPlayer(event.getEntityPlayer());

		if (event.getItemStack() == ItemStack.EMPTY || aePlayer == null)
		{
			return;
		}

		boolean result = CommonEvents.tryEquipEquipment(aePlayer, event.getItemStack(), event.getHand());

		if (result)
		{
			// Unfortunately we have to play the equip animation manually...
			if (event.getEntityPlayer().world.isRemote)
			{
				Minecraft.getMinecraft().getItemRenderer().resetEquippedProgress(EnumHand.MAIN_HAND);
			}

			event.getEntityPlayer().world.playSound(event.getEntityPlayer(), event.getEntityPlayer().getPosition(), SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, SoundCategory.NEUTRAL, 1.0f, 1.0f);
			event.setCanceled(true);
		}
	}

	private static boolean tryEquipEquipment(IPlayerAether player, ItemStack stack, EnumHand hand)
	{
		IInventoryEquipment inventory = player.getEquipmentInventory();

		Optional<IEquipmentProperties> equipment = AetherAPI.items().getEquipmentProperties(stack.getItem());

		if (equipment.isPresent())
		{
			int slot = inventory.getNextEmptySlotForType(equipment.get().getSlot());

			if (slot >= 0)
			{
				ItemStack newStack = stack.copy();
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
	public static void onPlayerInteract(PlayerInteractEvent.EntityInteract event)
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

	private static void onWaterPlaced(FillBucketEvent event, EntityPlayer player, BlockPos pos)
	{
		if (event.getWorld().getBlockState(event.getTarget().getBlockPos()).getBlock() == Blocks.GLOWSTONE)
		{
			if (CommonEvents.tryToCreatePortal(event.getWorld(), event.getTarget().getBlockPos(), pos))
			{
				if (!event.getEntityPlayer().capabilities.isCreativeMode)
				{
					IFluidHandler fluidHandler = FluidUtil.getFluidHandler(event.getEmptyBucket());
					ItemStack stack = ItemStack.EMPTY;

					if (fluidHandler != null)
					{
						FluidActionResult result = FluidUtil.tryEmptyContainer(event.getEmptyBucket(), fluidHandler, Integer.MAX_VALUE, player, true);

						stack = result.getResult();
					}

					if (event.getEmptyBucket().getItem() == Items.WATER_BUCKET)
					{
						stack = new ItemStack(Items.BUCKET);
					}

					if (event.getEmptyBucket().getItem() == ItemsAether.skyroot_water_bucket)
					{
						stack = new ItemStack(ItemsAether.skyroot_bucket);
					}

					event.getEntityPlayer().inventory.setInventorySlotContents(event.getEntityPlayer().inventory.currentItem, stack);
				}

				event.setCanceled(true);
			}
		}
	}

	private static void onLavaPlaced(FillBucketEvent event, EntityPlayer player, BlockPos pos)
	{
		if (player.world.provider.getDimensionType() == DimensionsAether.AETHER)
		{
			player.world.setBlockState(pos, BlocksAether.crude_scatterglass.getDefaultState());

			if (!player.capabilities.isCreativeMode)
			{
				IFluidHandler fluidHandler = FluidUtil.getFluidHandler(event.getEmptyBucket());
				ItemStack stack = ItemStack.EMPTY;

				if (fluidHandler != null)
				{
					FluidActionResult result = FluidUtil.tryEmptyContainer(event.getEmptyBucket(), fluidHandler, Integer.MAX_VALUE, player, true);

					stack = result.getResult();
				}

				if (event.getEmptyBucket().getItem() == Items.LAVA_BUCKET)
				{
					stack = new ItemStack(Items.BUCKET);
				}

				player.inventory.setInventorySlotContents(player.inventory.currentItem, stack);
			}

			if (event.getWorld().isRemote)
			{
				final Random rand = event.getWorld().rand;

				for (int count = 0; count < 8; count++)
				{
					final double parX = pos.getX() + rand.nextDouble();
					final double parY = pos.getY() + rand.nextDouble();
					final double parZ = pos.getZ() + rand.nextDouble();

					event.getWorld().spawnParticle(EnumParticleTypes.CLOUD, parX, parY, parZ, 0, 0, 0);
				}

				event.getWorld().playSound(pos.getX(), pos.getY(), pos.getZ(), SoundEvents.ENTITY_GENERIC_BURN, SoundCategory.NEUTRAL, 0.8f,
						1.2f + (rand.nextFloat() * 0.2f), false);
			}

			event.setCanceled(true);
		}
	}

	@SubscribeEvent
	public static void onEntityFall(LivingFallEvent event)
	{
		EntityLivingBase ent = event.getEntityLiving();

		if (ent.world.isRemote)
		{
			BlockPos pos = ent.getPosition().up();
			IBlockState block = ent.world.getBlockState(pos);

			while (block.getBlock() == Blocks.AIR)
			{
				block = ent.world.getBlockState(pos = pos.down());
			}

			if (block.getBlock() instanceof BlockAercloud && ((BlockAercloud.AercloudVariant) block.getProperties().get(BlockAercloud.PROPERTY_VARIANT)).hasSolidBottom())
			{
				event.setCanceled(true);
			}
		}
	}

	@SubscribeEvent
	public static void onEntityAttacked(LivingAttackEvent event)
	{
		if (!(event.getEntityLiving() instanceof EntityPlayer))
		{
			return;
		}

		EntityPlayer player = (EntityPlayer) event.getEntityLiving();

		if (!event.getSource().isUnblockable() && player.isActiveItemStackBlocking())
		{
			Vec3d vec3d = event.getSource().getDamageLocation();

			if (vec3d != null)
			{
				Vec3d look = player.getLook(1.0F);

				Vec3d reverse = vec3d.subtractReverse(new Vec3d(player.posX, player.posY, player.posZ)).normalize();
				reverse = new Vec3d(reverse.xCoord, 0.0D, reverse.zCoord);

				if (reverse.dotProduct(look) < 0.0D)
				{
					float damage = event.getAmount();

					if (damage >= 3.0F && player.getActiveItemStack().getItem() instanceof ItemAetherShield)
					{
						int itemDamage = 1 + MathHelper.floor(damage);

						player.getActiveItemStack().damageItem(itemDamage, player);

						if (player.getActiveItemStack().getCount() <= 0)
						{
							EnumHand hand = player.getActiveHand();

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
	public static void onEntityMounted(EntityMountEvent event)
	{
		if (event.getEntityMounting() instanceof EntityNPC)
		{
			event.setCanceled(true);
		}
	}
}
