package com.gildedgames.aether.common.capabilities.entity.player;

import com.gildedgames.aether.api.AetherCapabilities;
import com.gildedgames.aether.api.dialog.IDialogController;
import com.gildedgames.aether.api.player.IPlayerAether;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.capabilities.entity.player.modules.*;
import com.gildedgames.aether.common.network.NetworkingAether;
import com.gildedgames.aether.common.network.packets.PacketEquipment;
import com.gildedgames.aether.common.network.packets.PacketMarkPlayerDeath;
import com.gildedgames.aether.common.network.packets.PacketSetPlayedIntro;
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
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
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
import net.minecraftforge.fml.common.registry.ForgeRegistries;

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

		final Collection<PlayerAetherModule> modules = new ArrayList<>();

		modules.add(this.abilitiesModule);
		modules.add(this.gravititeAbilityModule);
		modules.add(this.teleportingModule);
		modules.add(this.parachuteModule);
		modules.add(this.equipmentModule);
		modules.add(this.dialogModule);
		modules.add(this.swetTracker);
		modules.add(this.separateInventoryModule);

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
		DimensionType dim = this.getEntity().world.provider.getDimensionType();

		this.separateInventoryModule.switchToMinecraftInventory(dim == DimensionsAether.AETHER || dim == DimensionsAether.NECROMANCER_TOWER);
	}

	/**
	 * Syncs the client and watching entities completely.
	 */
	public void sendFullUpdate()
	{
		NetworkingAether.sendPacketToPlayer(new PacketMarkPlayerDeath(this.hasDiedInAetherBefore()), (EntityPlayerMP) this.getEntity());
		NetworkingAether.sendPacketToPlayer(new PacketSetPlayedIntro(this.getTeleportingModule().hasPlayedIntro()), (EntityPlayerMP) this.getEntity());
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
			this.separateInventoryModule.switchToMinecraftInventory(fromAether);
		}

		if (to == DimensionsAether.AETHER)
		{
			List<IRecipe> toUnlock = Lists.newArrayList();

			for (IRecipe r : ForgeRegistries.RECIPES)
			{
				ResourceLocation loc = Item.REGISTRY.getNameForObject(r.getRecipeOutput().getItem());

				if (loc != null && loc.getResourceDomain().equals("aether"))
				{
					toUnlock.add(r);
				}
			}

			event.player.unlockRecipes(toUnlock);
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
