package com.gildedgames.aether.common;

import com.gildedgames.aether.common.blocks.BlocksAether;
import com.gildedgames.aether.common.blocks.construction.BlockAetherPortal;
import com.gildedgames.aether.common.items.ItemsAether;
import com.gildedgames.aether.common.util.PlayerUtil;
import com.gildedgames.util.modules.universe.common.util.TeleporterGeneric;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.ServerConfigurationManager;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.Teleporter;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.EntityInteractEvent;
import net.minecraftforge.event.entity.player.FillBucketEvent;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Random;

public class CommonEvents
{

	@SubscribeEvent
	public void onPlayerUseBucket(FillBucketEvent event)
	{
		if (event.target.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK)
		{
			if (FluidContainerRegistry.isFilledContainer(event.current))
			{
				final EntityPlayer player = event.entityPlayer;

				final BlockPos pos = event.target.getBlockPos().offset(event.target.sideHit);

				FluidStack fluidStack = FluidContainerRegistry.getFluidForFilledItem(event.current);

				if (fluidStack.getFluid().getName().equals(FluidRegistry.WATER.getName()))
				{
					this.onWaterPlaced(event, player, pos);
				}
				else if (fluidStack.getFluid().getName().equals(FluidRegistry.LAVA.getName()))
				{
					this.onLavaPlaced(event, player, pos);
				}
			}
		}
	}

	private boolean tryToCreatePortal(World world, BlockPos pos)
	{
		if (world.getBlockState(pos.down()).getBlock() == Blocks.glowstone)
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
	public void onLivingEntityUpdate(LivingEvent.LivingUpdateEvent event)
	{
		final Entity entity = event.entity;
		final Block blockBeneath = entity.worldObj.getBlockState(entity.getPosition().down()).getBlock();

		if (blockBeneath == BlocksAether.quicksoil)
		{
			// This doesn't work all the time, because it's only called when the player is
			// directly on top of Quicksoil. If you go slow enough, during the next player update, it
			// will see the player is on top of an air block instead, and this won't be called.

			if (entity.isSneaking())
			{
				entity.onGround = false;

				entity.motionX *= 1.03D;
				entity.motionZ *= 1.03D;
			}
		}

		// Checks whether or not an entity is in the Aether's void
		if (entity.dimension == AetherCore.getAetherDimID() && entity.posY < -4 && !entity.worldObj.isRemote)
		{
			this.onFallenFromAether(entity);
		}
	}

	private void onFallenFromAether(Entity entity)
	{
		// Don't teleport entities which are being ridden.
		if (entity.riddenByEntity != null)
		{
			return;
		}

		MinecraftServer server = FMLCommonHandler.instance().getMinecraftServerInstance();

		ServerConfigurationManager scm = server.getConfigurationManager();

		WorldServer toWorld = DimensionManager.getWorld(0);

		Teleporter teleporter = new TeleporterGeneric(toWorld);

		Entity mount = entity.ridingEntity;

		if (mount != null)
		{
			// Dismounts the entity it's riding first as teleporting will cause updates to the entities
			entity.mountEntity(null);

			// Teleports entity first, then what it's riding
			Entity teleportedEntity = this.teleportEntity(scm, entity, toWorld, teleporter);

			Entity teleportedMount= this.teleportEntity(scm, mount, toWorld, teleporter);

			// Re-mount what it was previously riding
			teleportedEntity.mountEntity(teleportedMount);
		}
		else
		{
			this.teleportEntity(scm, entity, toWorld, teleporter);
		}
	}

	/**
	 * Teleports any entity by duplicating it and . the old one. If {@param entity} is a player,
	 * the entity will be transferred instead of duplicated.
	 *
	 * @return A new entity if {@param entity} wasn't a player, or the same entity if it was a player
	 */
	private Entity teleportEntity(ServerConfigurationManager scm, Entity entity, WorldServer toWorld, Teleporter teleporter)
	{
		if (entity == null)
		{
			return null;
		}

		if (entity instanceof EntityPlayer)
		{
			// Players require special magic to be teleported correctly, and are not duplicated
			scm.transferPlayerToDimension((EntityPlayerMP) entity, 0, teleporter);
			entity.setPositionAndUpdate(entity.posX, 200 + entity.posY, entity.posZ);

			return entity;
		}
		else
		{
			Entity newEntity = EntityList.createEntityByName(EntityList.getEntityString(entity), toWorld);
			newEntity.copyDataFromOld(entity);

			entity.worldObj.removeEntity(entity);

			// Forces the entity to be sent to clients as early as possible
			newEntity.forceSpawn = true;

			newEntity.setPositionAndUpdate(entity.posX, 200 + entity.posY, entity.posZ);

			toWorld.spawnEntityInWorld(newEntity);

			return newEntity;
		}
	}

	@SubscribeEvent
	public void onPlayerInteract(EntityInteractEvent event)
	{
		if (event.target instanceof EntityCow && !((EntityCow) event.target).isChild())
		{
			final ItemStack stack = event.entityPlayer.inventory.getCurrentItem();

			if (stack != null && stack.getItem() == ItemsAether.skyroot_bucket)
			{
				PlayerUtil.fillBucketInHand(event.entityPlayer, new ItemStack(ItemsAether.skyroot_milk_bucket));
			}
		}
	}

	private void onWaterPlaced(FillBucketEvent event, EntityPlayer player, BlockPos pos)
	{
		if (event.world.getBlockState(event.target.getBlockPos()).getBlock() == Blocks.glowstone)
		{
			if (this.tryToCreatePortal(event.world, pos))
			{
				if (!event.entityPlayer.capabilities.isCreativeMode)
				{
					event.entityPlayer.inventory.setInventorySlotContents(event.entityPlayer.inventory.currentItem, FluidContainerRegistry.drainFluidContainer(event.current));
				}

				event.setCanceled(true);
			}
		}
	}

	private void onLavaPlaced(FillBucketEvent event, EntityPlayer player, BlockPos pos)
	{
		if (player.dimension == AetherCore.getAetherDimID())
		{
			player.worldObj.setBlockState(pos, BlocksAether.aerogel.getDefaultState());

			if (!player.capabilities.isCreativeMode)
			{
				final ItemStack newStack = FluidContainerRegistry.drainFluidContainer(event.current);

				player.inventory.setInventorySlotContents(player.inventory.currentItem, newStack);
			}

			if (event.world.isRemote)
			{
				final Random rand = event.world.rand;

				for (int count = 0; count < 8; count++)
				{
					final double parX = pos.getX() + rand.nextDouble();
					final double parY = pos.getY() + rand.nextDouble();
					final double parZ = pos.getZ() + rand.nextDouble();

					event.world.spawnParticle(EnumParticleTypes.CLOUD, parX, parY, parZ, 0, 0, 0);
				}

				event.world.playSound(pos.getX(), pos.getY(), pos.getZ(), "random.fizz", 0.8f, 1.2f + (rand.nextFloat() * 0.2f), false);
			}

			event.setCanceled(true);
		}
	}

}
