package com.gildedgames.aether.common.capabilities.player.modules;

import com.gildedgames.aether.api.player.companions.IPlayerCompanionManager;
import com.gildedgames.aether.common.capabilities.player.PlayerAether;
import com.gildedgames.aether.common.capabilities.player.PlayerAetherModule;
import com.gildedgames.aether.common.entities.living.companions.EntityCompanion;
import com.gildedgames.aether.common.items.companions.ItemCompanion;
import com.gildedgames.aether.common.network.NetworkingAether;
import com.gildedgames.aether.common.network.packets.CompanionChangedPacket;
import com.gildedgames.aether.common.world.spawning.SpawnHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Random;

public class PlayerCompanionModule extends PlayerAetherModule implements IPlayerCompanionManager
{
	private final PlayerAether aePlayer;

	private final EntityPlayer player;

	private int companionId;

	public PlayerCompanionModule(PlayerAether aePlayer)
	{
		super(aePlayer);

		this.aePlayer = aePlayer;
		this.player = aePlayer.getEntity();
	}

	@Override
	public void onUpdate()
	{
		if (this.getEntity().world.isRemote || this.getEntity().isDead)
		{
			return;
		}

		this.update();
	}

	@Override
	public void write(NBTTagCompound compound)
	{

	}

	@Override
	public void read(NBTTagCompound compound)
	{

	}

	public void update()
	{
		ItemStack companionStack = this.getEquippedCompanionItem();

		EntityCompanion companion = this.getCompanionEntity();

		if (!this.player.world.isBlockLoaded(this.player.getPosition()))
		{
			return;
		}

		if (companionStack != null)
		{
			if (companion != null)
			{
				if (companion.isDead)
				{
					if (!companion.wasDespawned())
					{
						ItemCompanion.setRespawnTimer(companionStack, this.player.world, 20 * 240);
					}

					this.removeCompanion(true);
				}
				else if (((ItemCompanion) companionStack.getItem()).getCompanionEntityClass() != companion.getClass())
				{
					this.removeCompanion(true);
				}
				else
				{
					companion.tickEffects(this.aePlayer);
				}
			}

			if (companion == null)
			{
				long respawnTimer = ItemCompanion.getTicksUntilRespawn(companionStack, this.player.world);

				if (respawnTimer <= 0)
				{
					this.spawnCompanion((ItemCompanion) companionStack.getItem());
				}
			}
		}
		else if (companion != null)
		{
			this.removeCompanion(true);
		}
	}

	private void spawnCompanion(ItemCompanion item)
	{
		World world = this.player.getEntityWorld();
		EntityCompanion companion = item.createCompanionEntity(this.aePlayer);

		companion.setOwner(this.player);

		BlockPos pos = this.player.getPosition();

		companion.setLocationAndAngles(pos.getX(), pos.getY(), pos.getZ(), MathHelper.wrapDegrees(world.rand.nextFloat() * 360.0F), 0.0F);

		int attemptsToNotCollide = 0;

		if (world instanceof WorldServer)
		{
			WorldServer worldServer = (WorldServer) world;

			while (!SpawnHandler.isNotColliding(world, companion) && attemptsToNotCollide < 20)
			{
				Random rand = companion.getRNG();

				float xOffset = (rand.nextBoolean() ? -1 : 1) * rand.nextFloat();
				float zOffset = (rand.nextBoolean() ? -1 : 1) * rand.nextFloat();

				companion.setPositionAndUpdate(companion.posX + xOffset, companion.posY, companion.posZ + zOffset);
				worldServer.updateEntityWithOptionalForce(companion, true);

				attemptsToNotCollide++;
			}

			if (attemptsToNotCollide >= 20)
			{
				companion.setPositionAndUpdate(this.player.posX, this.player.posY, this.player.posZ);
				worldServer.updateEntityWithOptionalForce(companion, true);
			}
		}

		this.player.world.spawnEntity(companion);

		this.companionId = companion.getEntityId();

		NetworkingAether.sendPacketToPlayer(new CompanionChangedPacket(this.companionId), (EntityPlayerMP) this.player);

		companion.addEffects(this.aePlayer);
	}

	private void removeCompanion(boolean notifyClient)
	{
		EntityCompanion companion = this.getCompanionEntity();

		if (companion != null)
		{
			companion.removeEffects(this.aePlayer);

			companion.setOwner(null);
			companion.setDead();

			this.companionId = -1;

			if (notifyClient)
			{
				NetworkingAether.sendPacketToWatching(new CompanionChangedPacket(this.companionId), this.player);
			}
		}
	}

	public void onDeath(LivingDeathEvent event)
	{
		if (!event.isCanceled())
		{
			this.removeCompanion(true);
		}
	}

	public void onTeleport(PlayerEvent.PlayerChangedDimensionEvent event)
	{
		if (!event.isCanceled())
		{
			this.removeCompanion(true);

			this.update();
		}
	}

	public void onSpawned()
	{
		this.update();
	}

	public void onDespawn(PlayerEvent.PlayerLoggedOutEvent event)
	{
		this.removeCompanion(false);
	}

	@SideOnly(Side.CLIENT)
	public void handleCompanionChange(int id)
	{
		this.companionId = id;
	}

	@Override
	public EntityCompanion getCompanionEntity()
	{
		Entity entity = this.player.world.getEntityByID(this.companionId);

		if (entity != null && entity instanceof EntityCompanion)
		{
			return (EntityCompanion) entity;
		}

		return null;
	}

	@Override
	public ItemStack getEquippedCompanionItem()
	{
		ItemStack companionStack = this.aePlayer.getEquipmentInventory().getStackInSlot(6);

		if (companionStack != null && companionStack.getItem() instanceof ItemCompanion)
		{
			return companionStack;
		}

		return null;
	}

}
