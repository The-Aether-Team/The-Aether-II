package com.gildedgames.aether.common.capabilities.player.modules;

import com.gildedgames.aether.api.player.IPlayerAetherCapability;
import com.gildedgames.aether.api.player.companions.IPlayerCompanionManager;
import com.gildedgames.aether.common.capabilities.player.PlayerAetherImpl;
import com.gildedgames.aether.common.capabilities.player.PlayerAetherModule;
import com.gildedgames.aether.common.entities.companions.EntityCompanion;
import com.gildedgames.aether.common.items.companions.ItemCompanion;
import com.gildedgames.aether.common.network.NetworkingAether;
import com.gildedgames.aether.common.network.packets.CompanionChangedPacket;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PlayerCompanionModule extends PlayerAetherModule implements IPlayerCompanionManager
{
	private final PlayerAetherImpl aePlayer;

	private final EntityPlayer player;

	private int companionId;

	public PlayerCompanionModule(PlayerAetherImpl aePlayer)
	{
		super(aePlayer);

		this.aePlayer = aePlayer;
		this.player = aePlayer.getPlayer();
	}

	@Override
	public void onUpdate(LivingEvent.LivingUpdateEvent event)
	{
		if (this.getPlayer().worldObj.isRemote || this.getPlayer().isDead)
		{
			return;
		}

		this.update();
	}

	public void update()
	{
		ItemStack companionStack = this.getEquippedCompanionItem();

		EntityCompanion companion = this.getCompanionEntity();

		if (companionStack != null)
		{
			if (companion != null)
			{
				if (companion.isDead)
				{
					if (!companion.wasDespawned())
					{
						ItemCompanion.setRespawnTimer(companionStack, this.player.worldObj, 20 * 240);
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
				long respawnTimer = ItemCompanion.getTicksUntilRespawn(companionStack, this.player.worldObj);

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
		EntityCompanion companion = item.createCompanionEntity(this.aePlayer);

		companion.setOwner(this.player);

		BlockPos pos = this.player.getPosition();

		companion.setPosition(pos.getX(), pos.getY(), pos.getZ());

		this.player.worldObj.spawnEntityInWorld(companion);
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

	@Override
	public void onDeath(LivingDeathEvent event)
	{
		if (!event.isCanceled())
		{
			this.removeCompanion(true);
		}
	}

	@Override
	public void onTeleport(PlayerEvent.PlayerChangedDimensionEvent event)
	{
		if (!event.isCanceled())
		{
			this.removeCompanion(true);

			this.update();
		}
	}

	@Override
	public void onSpawned(PlayerEvent.PlayerLoggedInEvent event)
	{
		this.update();
	}

	@Override
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
		Entity entity = this.player.worldObj.getEntityByID(this.companionId);

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
