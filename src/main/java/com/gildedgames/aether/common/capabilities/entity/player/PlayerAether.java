package com.gildedgames.aether.common.capabilities.entity.player;

import com.gildedgames.aether.api.AetherCapabilities;
import com.gildedgames.aether.api.dialog.IDialogController;
import com.gildedgames.aether.api.player.IPlayerAether;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.capabilities.entity.player.modules.*;
import com.gildedgames.aether.common.network.NetworkingAether;
import com.gildedgames.aether.common.network.packets.*;
import com.gildedgames.aether.common.registry.content.DimensionsAether;
import com.gildedgames.aether.common.world.necromancer_tower.NecromancerTowerInstance;
import com.gildedgames.orbis_api.util.io.NBTFunnel;
import com.gildedgames.orbis_api.util.mc.NBTHelper;
import com.google.common.collect.Lists;
import net.minecraft.block.material.Material;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.MobEffects;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.DimensionType;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerDropsEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class PlayerAether implements IPlayerAether
{
	private final PlayerAetherModule[] modules;

	private final EntityPlayer entity;

	private final PlayerAbilitiesModule abilitiesModule;

	private final PlayerBlockLevitateModule gravititeAbilityModule;

	private final PlayerTeleportingModule teleportingModule;

	private final PlayerParachuteModule parachuteModule;

	private final PlayerEquipmentModule equipmentModule;

	private final PlayerDialogModule dialogModule;

	private final PlayerSwetTracker swetTracker;

	private final PlayerSeparateInventoryModule separateInventoryModule;

	private final PlayerCampfiresModule campfiresModule;

	private final PlayerPreventDropsModule preventDropsModule;

	private final List<PlayerAetherObserver> observers = Lists.newArrayList();

	private boolean hasDiedInAetherBefore;

	private NecromancerTowerInstance towerInstance;

	public PlayerAether()
	{
		this.modules = new PlayerAetherModule[0];
		this.entity = null;
		this.abilitiesModule = null;
		this.gravititeAbilityModule = null;
		this.teleportingModule = null;
		this.parachuteModule = null;
		this.equipmentModule = null;
		this.dialogModule = null;
		this.swetTracker = null;
		this.separateInventoryModule = null;
		this.campfiresModule = null;
		this.preventDropsModule = null;
	}

	public PlayerAether(final EntityPlayer entity)
	{
		this.entity = entity;

		this.abilitiesModule = new PlayerAbilitiesModule(this);
		this.gravititeAbilityModule = new PlayerBlockLevitateModule(this);
		this.teleportingModule = new PlayerTeleportingModule(this);
		this.parachuteModule = new PlayerParachuteModule(this);
		this.equipmentModule = new PlayerEquipmentModule(this);
		this.dialogModule = new PlayerDialogModule(this);
		this.swetTracker = new PlayerSwetTracker(this);
		this.separateInventoryModule = new PlayerSeparateInventoryModule(this);
		this.campfiresModule = new PlayerCampfiresModule(this);
		this.preventDropsModule = new PlayerPreventDropsModule(this);

		final Collection<PlayerAetherModule> modules = new ArrayList<>();

		modules.add(this.abilitiesModule);
		modules.add(this.gravititeAbilityModule);
		modules.add(this.teleportingModule);
		modules.add(this.parachuteModule);
		modules.add(this.equipmentModule);
		modules.add(this.dialogModule);
		modules.add(this.swetTracker);
		modules.add(this.separateInventoryModule);
		modules.add(this.campfiresModule);
		modules.add(this.preventDropsModule);

		this.modules = modules.toArray(new PlayerAetherModule[modules.size()]);
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

	public NecromancerTowerInstance getNecromancerTowerInstance()
	{
		return this.towerInstance;
	}

	public void setNecromancerTowerInstance(NecromancerTowerInstance towerInstance)
	{
		this.towerInstance = towerInstance;
	}

	public boolean hasDiedInAetherBefore()
	{
		return this.hasDiedInAetherBefore;
	}

	public void setHasDiedInAetherBefore(final boolean flag)
	{
		this.hasDiedInAetherBefore = flag;
	}

	public PlayerSeparateInventoryModule getSeparateInventoryModule()
	{
		return this.separateInventoryModule;
	}

	public void onLoggedOut()
	{

	}

	/**
	 * Syncs the client and watching entities completely.
	 */
	public void sendFullUpdate()
	{
		NetworkingAether.sendPacketToPlayer(new PacketMarkPlayerDeath(this.hasDiedInAetherBefore()), (EntityPlayerMP) this.getEntity());
		NetworkingAether.sendPacketToPlayer(new PacketSetPlayedIntro(this.getTeleportingModule().hasPlayedIntro()), (EntityPlayerMP) this.getEntity());
		NetworkingAether.sendPacketToPlayer(new PacketCampfires(this.getCampfiresModule().getCampfiresActivated()), (EntityPlayerMP) this.getEntity());
		NetworkingAether.sendPacketToPlayer(new PacketPreventDropsInventories(this.preventDropsModule), (EntityPlayerMP) this.getEntity());
		NetworkingAether.sendPacketToPlayer(new PacketSeparateInventoryModule(this.separateInventoryModule), (EntityPlayerMP) this.getEntity());
	}

	public void onUpdate(LivingEvent.LivingUpdateEvent event)
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

	public void onPlayerTick(TickEvent.PlayerTickEvent event)
	{
		for (final PlayerAetherModule module : this.modules)
		{
			if (event.phase == TickEvent.Phase.START)
			{
				module.tickStart(event);
			}

			if (event.phase == TickEvent.Phase.END)
			{
				module.tickEnd(event);
			}
		}
	}

	public void onRespawn(final PlayerEvent.PlayerRespawnEvent event)
	{
		this.sendFullUpdate();

		for (PlayerAetherModule module : this.modules)
		{
			module.onRespawn(event);
		}
	}

	public void onPlaceBlock(final BlockEvent.PlaceEvent event)
	{

	}

	public void onDeath(final LivingDeathEvent event)
	{
		for (PlayerAetherModule module : this.modules)
		{
			module.onDeath(event);
		}
	}

	public void onDrops(final PlayerDropsEvent event)
	{
		for (PlayerAetherModule module : this.modules)
		{
			module.onDrops(event);
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

		DimensionType to = DimensionManager.getProviderType(event.toDim);
		DimensionType from = DimensionManager.getProviderType(event.fromDim);

		boolean fromAether = from == DimensionsAether.AETHER || from == DimensionsAether.NECROMANCER_TOWER;
		boolean toAether = to == DimensionsAether.AETHER || to == DimensionsAether.NECROMANCER_TOWER;

		if (toAether && !fromAether)
		{
			this.separateInventoryModule.switchToAetherInventory();
		}
		else if (!toAether && fromAether)
		{
			this.separateInventoryModule.switchToMinecraftInventory(true);
		}
	}

	public void onPlayerBeginWatching(final IPlayerAether other)
	{
		NetworkingAether.sendPacketToPlayer(new PacketEquipment(this), (EntityPlayerMP) other.getEntity());
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
		NBTFunnel funnel = new NBTFunnel(tag);

		final NBTTagList modules = new NBTTagList();

		for (final PlayerAetherModule module : this.modules)
		{
			modules.appendTag(NBTHelper.writeRaw(module));
		}

		tag.setTag("Modules", modules);
		tag.setBoolean("HasDiedInAether", this.hasDiedInAetherBefore);

		funnel.set("towerInstance", this.towerInstance);
	}

	@Override
	public void read(final NBTTagCompound tag)
	{
		NBTFunnel funnel = new NBTFunnel(tag);

		final NBTTagList modules = tag.getTagList("Modules", 10);

		for (int i = 0; i < this.modules.length; i++)
		{
			final PlayerAetherModule module = this.modules[i];

			module.read(modules.getCompoundTagAt(i));
		}

		this.hasDiedInAetherBefore = tag.getBoolean("HasDiedInAether");

		NecromancerTowerInstance inst = funnel.get("towerInstance");

		if (inst != null)
		{
			this.towerInstance = inst;
		}
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

	public PlayerTeleportingModule getTeleportingModule()
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

	public PlayerCampfiresModule getCampfiresModule()
	{
		return this.campfiresModule;
	}

	@Override
	public PlayerEquipmentModule getEquipmentModule()
	{
		return this.equipmentModule;
	}

	public PlayerPreventDropsModule getPreventDropsModule()
	{
		return this.preventDropsModule;
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
