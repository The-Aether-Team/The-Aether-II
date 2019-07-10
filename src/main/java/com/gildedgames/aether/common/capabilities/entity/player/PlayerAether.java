package com.gildedgames.aether.common.capabilities.entity.player;

import com.gildedgames.aether.api.player.IPlayerAether;
import com.gildedgames.aether.api.player.IPlayerAetherModule;
import com.gildedgames.aether.api.registrar.CapabilitiesAether;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.capabilities.entity.player.modules.*;
import com.gildedgames.aether.common.capabilities.entity.player.modules.guidebook.PlayerTGEventsModule;
import com.gildedgames.aether.common.network.NetworkingAether;
import com.gildedgames.aether.common.network.packets.*;
import com.gildedgames.aether.common.world.instances.necromancer_tower.NecromancerTowerInstance;
import com.gildedgames.orbis.lib.util.io.NBTFunnel;
import com.gildedgames.orbis.lib.util.mc.NBTHelper;
import net.minecraft.block.material.Material;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerDropsEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.List;

public class PlayerAether implements IPlayerAether
{
	private final EntityPlayer entity;

	private NecromancerTowerInstance towerInstance;

	private ItemStack lastDestroyedStack;

	private final IdentityHashMap<Class<? extends IPlayerAetherModule>, IPlayerAetherModule> modulesKeyed = new IdentityHashMap<>();

	private final List<IPlayerAetherModule> modules = new ArrayList<>();
	private final List<IPlayerAetherModule.Serializable> modulesSerializable = new ArrayList<>();

	private int ticksWithEggnogEffect;

	private boolean isTrading;

	public PlayerAether()
	{
		this.entity = null;
	}

	public PlayerAether(final EntityPlayer entity)
	{
		this.entity = entity;

		this.registerModule(new PlayerAbilitiesModule(this));
		this.registerModule(new PlayerBlockLevitateModule(this));
		this.registerModule(new PlayerTeleportingModule(this));
		this.registerModule(new PlayerParachuteModule(this));
		this.registerModule(new PlayerEquipmentModule(this));
		this.registerModule(new PlayerDialogModule(this));
		this.registerModule(new PlayerSwetTrackerModule(this));
		this.registerModule(new PlayerCampfiresModule(this));
		this.registerModule(new PlayerPreventDropsModule(this));
		this.registerModule(new PlayerPatronRewardModule(this));
		this.registerModule(new PlayerRollMovementModule(this));
		this.registerModule(new PlayerConfigModule(this));
		this.registerModule(new PlayerProgressModule(this));
		this.registerModule(new PlayerCurrencyModule(this));
		this.registerModule(new PlayerSectorModule(this));
		this.registerModule(new PlayerTradeModule(this));
		this.registerModule(new PlayerCaveSpawnModule(this));
		this.registerModule(new PlayerTGEventsModule(this));
	}

	@Override
	public void registerModule(IPlayerAetherModule module)
	{
		Class<? extends IPlayerAetherModule> clazz = module.getClass();

		if (this.modulesKeyed.containsKey(clazz))
		{
			throw new IllegalStateException("Module is already registered for class: " + clazz);
		}

		this.modulesKeyed.put(clazz, module);
		this.modules.add(module);

		if (module instanceof IPlayerAetherModule.Serializable)
		{
			this.modulesSerializable.add((IPlayerAetherModule.Serializable) module);
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T extends IPlayerAetherModule> T getModule(Class<T> clazz)
	{
		T ret = (T) this.modulesKeyed.get(clazz);

		if (ret == null)
		{
			throw new NullPointerException("No module registered for class: " + clazz);
		}

		return ret;
	}

	@Nonnull
	public static PlayerAether getPlayer(final EntityPlayer player)
	{
		if (player == null)
		{
			throw new NullPointerException("Player entity is null");
		}

		PlayerAether ret = (PlayerAether) player.getCapability(CapabilitiesAether.PLAYER_DATA, null);

		if (ret == null)
		{
			throw new NullPointerException("Player does not contain capability");
		}

		return ret;
	}

	public static boolean hasCapability(final Entity entity)
	{
		return entity.hasCapability(CapabilitiesAether.PLAYER_DATA, null);
	}

	public ItemStack getLastDestroyedStack()
	{
		return this.lastDestroyedStack;
	}

	public void setLastDestroyedStack(ItemStack lastDestroyedStack)
	{
		this.lastDestroyedStack = lastDestroyedStack;
	}

	public void setDrankEggnog()
	{
		this.ticksWithEggnogEffect = 5000;
	}

	public boolean hasEggnogEffect()
	{
		return this.ticksWithEggnogEffect > 0;
	}

	public void onLoggedOut()
	{
		this.getModule(PlayerSectorModule.class).releaseAll();
	}

	/**
	 * Syncs the client and watching entities completely.
	 */
	public void sendFullUpdate()
	{
		EntityPlayerMP player = (EntityPlayerMP) this.getEntity();

		NetworkingAether.sendPacketToPlayer(new PacketCurrencyModule(this.getModule(PlayerCurrencyModule.class)), player);
		NetworkingAether.sendPacketToPlayer(new PacketProgressModule(this.getModule(PlayerProgressModule.class)), player);
		NetworkingAether.sendPacketToPlayer(new PacketSetPlayedIntro(this.getModule(PlayerTeleportingModule.class).hasPlayedIntro()), player);
		NetworkingAether.sendPacketToPlayer(new PacketCampfires(this.getModule(PlayerCampfiresModule.class).getCampfiresActivated()), player);
		NetworkingAether.sendPacketToPlayer(new PacketPreventDropsInventories(this.getModule(PlayerPreventDropsModule.class)), player);
	}

	public void onUpdate()
	{
		if (this.ticksWithEggnogEffect > 0)
		{
			this.ticksWithEggnogEffect--;
		}
	}

	public void onPlayerTick(TickEvent.PlayerTickEvent event)
	{
		this.onUpdate();

		for (final IPlayerAetherModule module : this.modules)
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

		for (IPlayerAetherModule module : this.modules)
		{
			module.onRespawn(event);
		}
	}

	public void onPlaceBlock(final BlockEvent.PlaceEvent event)
	{
	}

	public void onDeath(final LivingDeathEvent event)
	{
		for (IPlayerAetherModule module : this.modules)
		{
			module.onDeath(event);
		}
	}

	public void onDrops(final PlayerDropsEvent event)
	{
		for (IPlayerAetherModule module : this.modules)
		{
			module.onDrops(event);
		}
	}

	public void onHurt(final LivingHurtEvent event)
	{
		PlayerEquipmentModule equipmentModule = this.getModule(PlayerEquipmentModule.class);

		if (equipmentModule.getEffectPool(new ResourceLocation(AetherCore.MOD_ID, "fire_immunity")).isPresent())
		{
			if (event.getSource() == DamageSource.ON_FIRE || event.getSource() == DamageSource.IN_FIRE || event.getSource() == DamageSource.LAVA)
			{
				event.setCanceled(true);
			}
		}

		PlayerRollMovementModule movementModule = this.getModule(PlayerRollMovementModule.class);

		if (movementModule.isRolling())
		{
			event.setAmount(movementModule.getDamageReduction(event.getAmount()));
		}
	}

	public void onFall(final LivingFallEvent event)
	{
		this.getModule(PlayerAbilitiesModule.class).onFall(event);
	}

	public void onTeleport(final PlayerEvent.PlayerChangedDimensionEvent event)
	{
		this.getModule(PlayerSectorModule.class).releaseAll();
		this.getModule(PlayerEquipmentModule.class).onTeleport();

		this.sendFullUpdate();
	}

	public void onPlayerBeginWatching(final IPlayerAether other)
	{
		NetworkingAether.sendPacketToPlayer(new PacketEquipment(this), (EntityPlayerMP) other.getEntity());
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

		final NBTTagCompound modules = new NBTTagCompound();

		for (final IPlayerAetherModule.Serializable module : this.modulesSerializable)
		{
			modules.setTag(module.getIdentifier().toString(), NBTHelper.writeRaw(module));
		}

		tag.setTag("Modules", modules);

		funnel.set("towerInstance", this.towerInstance);
	}

	@Override
	public void read(final NBTTagCompound tag)
	{
		NBTFunnel funnel = new NBTFunnel(tag);

		NBTTagCompound modules = tag.getCompoundTag("Modules");

		for (final IPlayerAetherModule.Serializable module : this.modulesSerializable)
		{
			String key = module.getIdentifier().toString();

			if (modules.hasKey(key))
			{
				module.read(modules.getCompoundTag(key));
			}
		}

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
