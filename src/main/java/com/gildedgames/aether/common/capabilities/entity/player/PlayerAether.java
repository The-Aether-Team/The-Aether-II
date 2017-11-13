package com.gildedgames.aether.common.capabilities.entity.player;

import com.gildedgames.aether.api.AetherCapabilities;
import com.gildedgames.aether.api.dialog.IDialogController;
import com.gildedgames.aether.api.player.IPlayerAether;
import com.gildedgames.aether.api.util.NBTHelper;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.capabilities.entity.player.modules.*;
import com.gildedgames.aether.common.network.NetworkingAether;
import com.gildedgames.aether.common.network.packets.PacketEquipment;
import com.gildedgames.aether.common.network.packets.PacketMarkPlayerDeath;
import com.gildedgames.orbis.common.network.packets.PacketChangePower;
import com.gildedgames.orbis.common.network.packets.PacketDeveloperMode;
import com.gildedgames.orbis.common.network.packets.PacketDeveloperReach;
import com.gildedgames.orbis.common.network.packets.PacketForgeInventoryChanged;
import com.gildedgames.orbis.common.player.PlayerOrbisModule;
import com.gildedgames.orbis.common.player.PlayerSelectionModule;
import com.google.common.collect.Lists;
import net.minecraft.block.material.Material;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerDropsEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class PlayerAether implements IPlayerAether
{
	private final PlayerAetherModule[] modules;

	private final EntityPlayer entity;

	private final PlayerAbilitiesModule abilitiesModule;

	private final PlayerBlockLevitateModule gravititeAbilityModule;

	private final PlayerPortalModule teleportingModule;

	private final PlayerParachuteModule parachuteModule;

	private final PlayerEquipmentModule equipmentModule;

	private final PlayerDialogModule dialogModule;

	private final PlayerSwetTracker swetTracker;

	private final PlayerOrbisModule orbisModule;

	private final PlayerSelectionModule selectionModule;

	private final List<PlayerAetherObserver> observers = Lists.newArrayList();

	private boolean hasDiedInAetherBefore;

	public PlayerAether(final EntityPlayer entity)
	{
		this.entity = entity;

		this.abilitiesModule = new PlayerAbilitiesModule(this);
		this.gravititeAbilityModule = new PlayerBlockLevitateModule(this);
		this.teleportingModule = new PlayerPortalModule(this);
		this.parachuteModule = new PlayerParachuteModule(this);
		this.equipmentModule = new PlayerEquipmentModule(this);
		this.dialogModule = new PlayerDialogModule(this);
		this.swetTracker = new PlayerSwetTracker(this);
		this.orbisModule = new PlayerOrbisModule(this);
		this.selectionModule = new PlayerSelectionModule(this);

		final Collection<PlayerAetherModule> modules = new ArrayList<>();

		modules.add(this.abilitiesModule);
		modules.add(this.gravititeAbilityModule);
		modules.add(this.teleportingModule);
		modules.add(this.parachuteModule);
		modules.add(this.equipmentModule);
		modules.add(this.dialogModule);
		modules.add(this.swetTracker);
		modules.add(this.orbisModule);
		modules.add(this.selectionModule);

		this.modules = modules.toArray(new PlayerAetherModule[modules.size()]);
	}

	public static PlayerOrbisModule getOrbisModule(final Entity player)
	{
		final PlayerAether playerAether = PlayerAether.getPlayer(player);

		return playerAether.getOrbisModule();
	}

	public static PlayerAether getPlayer(final Entity player)
	{
		if (!PlayerAether.hasCapability(player))
		{
			return null;
		}

		return (PlayerAether) player.getCapability(AetherCapabilities.PLAYER_DATA, null);
	}

	public static boolean hasCapability(final Entity entity)
	{
		return entity.hasCapability(AetherCapabilities.PLAYER_DATA, null);
	}

	public boolean hasDiedInAetherBefore()
	{
		return this.hasDiedInAetherBefore;
	}

	public void setHasDiedInAetherBefore(final boolean flag)
	{
		this.hasDiedInAetherBefore = flag;
	}

	/**
	 * Syncs the client and watching entities completely.
	 */
	public void sendFullUpdate()
	{
		NetworkingAether.sendPacketToPlayer(new PacketMarkPlayerDeath(this.hasDiedInAetherBefore()), (EntityPlayerMP) this.getEntity());
		NetworkingAether.sendPacketToPlayer(new PacketDeveloperMode(this.getOrbisModule().inDeveloperMode()), (EntityPlayerMP) this.getEntity());
		NetworkingAether.sendPacketToPlayer(new PacketDeveloperReach(this.getOrbisModule().getDeveloperReach()), (EntityPlayerMP) this.getEntity());
		NetworkingAether
				.sendPacketToPlayer(new PacketChangePower(this.getOrbisModule().powers().getCurrentPowerIndex()), (EntityPlayerMP) this.getEntity());
		//NetworkingAether.sendPacketToPlayer(new PacketWorldObjectAdd(this.getSelectionModule().get), (EntityPlayerMP) this.getEntity());
	}

	public void onUpdate(final LivingUpdateEvent event)
	{
		for (final PlayerAetherModule module : this.modules)
		{
			module.onUpdate();
		}

		for (final PlayerAetherObserver observer : this.observers)
		{
			observer.onUpdate(this);
		}
	}

	public void onRespawn(final PlayerEvent.PlayerRespawnEvent event)
	{
		this.sendFullUpdate();
	}

	public void onPlaceBlock(final BlockEvent.PlaceEvent event)
	{

	}

	public void onDeath(final LivingDeathEvent event)
	{
		this.gravititeAbilityModule.onDeath(event);
	}

	public void onDrops(final PlayerDropsEvent event)
	{
		if (!this.getEntity().world.isRemote && !this.getEntity().world.getGameRules().getBoolean("keepInventory"))
		{
			for (int i = 0; i < this.getEquipmentModule().getInventory().getSizeInventory(); i++)
			{
				final ItemStack stack = this.getEquipmentModule().getInventory().removeStackFromSlot(i);

				if (!stack.isEmpty())
				{
					this.getEntity().dropItem(stack, true, true);
				}
			}
		}
	}

	public void onHurt(final LivingHurtEvent event)
	{
		final PlayerAether aePlayer = PlayerAether.getPlayer(event.getEntity());

		if (aePlayer != null)
		{
			if (aePlayer.getEquipmentModule().getEffectPool(new ResourceLocation(AetherCore.MOD_ID, "fire_immunity")).isPresent())
			{
				if (event.getSource() == DamageSource.ON_FIRE || event.getSource() == DamageSource.IN_FIRE || event.getSource() == DamageSource.LAVA)
				{
					event.setCanceled(true);
				}
			}
		}
	}

	public void onFall(final LivingFallEvent event)
	{
		this.abilitiesModule.onFall(event);
	}

	public void onTeleport(final PlayerEvent.PlayerChangedDimensionEvent event)
	{
		this.sendFullUpdate();

		this.equipmentModule.onTeleport();
	}

	public void onPlayerBeginWatching(final IPlayerAether other)
	{
		NetworkingAether.sendPacketToPlayer(new PacketEquipment(this), (EntityPlayerMP) other.getEntity());
		NetworkingAether.sendPacketToPlayer(new PacketForgeInventoryChanged(this), (EntityPlayerMP) other.getEntity());
	}

	@Override
	public IDialogController getDialogController()
	{
		return this.dialogModule;
	}

	@Override
	public float getMiningSpeedMultiplier()
	{
		if (this.getEntity().getAir() == 300 && this.getEntity().isPotionActive(MobEffects.WATER_BREATHING))
		{
			if (!EnchantmentHelper.getAquaAffinityModifier(this.entity) &&
					this.entity.isInsideOfMaterial(Material.WATER))
			{
				return 5.0f;
			}
		}

		return 1.0f;
	}

	@Override
	public void write(final NBTTagCompound tag)
	{
		final NBTTagList modules = new NBTTagList();

		for (final PlayerAetherModule module : this.modules)
		{
			modules.appendTag(NBTHelper.write(module));
		}

		tag.setTag("Modules", modules);
		tag.setBoolean("HasDiedInAether", this.hasDiedInAetherBefore);
	}

	@Override
	public void read(final NBTTagCompound tag)
	{
		final NBTTagList modules = tag.getTagList("Modules", 10);

		for (int i = 0; i < modules.tagCount(); i++)
		{
			final PlayerAetherModule module = this.modules[i];

			module.read(modules.getCompoundTagAt(i));
		}

		this.hasDiedInAetherBefore = tag.getBoolean("HasDiedInAether");
	}

	@Override
	public EntityPlayer getEntity()
	{
		return this.entity;
	}

	public PlayerBlockLevitateModule getGravititeAbility()
	{
		return this.gravititeAbilityModule;
	}

	public PlayerPortalModule getTeleportingModule()
	{
		return this.teleportingModule;
	}

	public PlayerParachuteModule getParachuteModule()
	{
		return this.parachuteModule;
	}

	public PlayerAbilitiesModule getAbilitiesModule()
	{
		return this.abilitiesModule;
	}

	public PlayerSwetTracker getSwetTracker()
	{
		return this.swetTracker;
	}

	public PlayerOrbisModule getOrbisModule()
	{
		return this.orbisModule;
	}

	public PlayerSelectionModule getSelectionModule()
	{
		return this.selectionModule;
	}

	@Override
	public PlayerEquipmentModule getEquipmentModule()
	{
		return this.equipmentModule;
	}

	public boolean containsObserver(final PlayerAetherObserver observer)
	{
		return this.observers.contains(observer);
	}

	public void addObserver(final PlayerAetherObserver observer)
	{
		this.observers.add(observer);
	}

	public boolean removeObserver(final PlayerAetherObserver observer)
	{
		return this.observers.remove(observer);
	}

	public static class Storage implements IStorage<IPlayerAether>
	{
		@Override
		public NBTBase writeNBT(final Capability<IPlayerAether> capability, final IPlayerAether instance, final EnumFacing side)
		{
			final NBTTagCompound compound = new NBTTagCompound();
			instance.write(compound);

			return compound;
		}

		@Override
		public void readNBT(final Capability<IPlayerAether> capability, final IPlayerAether instance, final EnumFacing side, final NBTBase nbt)
		{
			final NBTTagCompound compound = (NBTTagCompound) nbt;

			instance.read(compound);
		}
	}
}
