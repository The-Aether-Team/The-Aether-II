package com.gildedgames.aether.common.capabilities.player;

import com.gildedgames.aether.api.capabilites.AetherCapabilities;
import com.gildedgames.aether.api.capabilites.entity.IPlayerAether;
import com.gildedgames.aether.api.player.inventory.IInventoryEquipment;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.capabilities.player.modules.*;
import net.minecraft.block.material.Material;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
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
import net.minecraftforge.fml.common.Optional;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

import java.util.ArrayList;
import java.util.Collection;

public class PlayerAether implements IPlayerAether
{
	private final PlayerAetherModule[] modules;

	private final EntityPlayer entity;

	private final PlayerCompanionModule companionModule;

	private final AbilitiesModule abilitiesModule;

	private final GravititeAbilityModule gravititeAbilityModule;

	private final TeleportingModule teleportingModule;

	private final ParachuteModule parachuteModule;

	private final EquipmentModule equipmentModule;

	private final BossModule bossModule;

	private final ExtendedReachModule extendedReachModule;

	private final DungeonModule dungeonModule;

	private boolean hasDiedInAetherBefore;

	public PlayerAether(EntityPlayer entity)
	{
		this.entity = entity;

		this.companionModule = new PlayerCompanionModule(this);
		this.abilitiesModule = new AbilitiesModule(this);
		this.gravititeAbilityModule = new GravititeAbilityModule(this);
		this.teleportingModule = new TeleportingModule(this);
		this.parachuteModule = new ParachuteModule(this);
		this.bossModule = new BossModule(this);
		this.equipmentModule = new EquipmentModule(this);
		this.extendedReachModule = new ExtendedReachModule(this);
		this.dungeonModule = new DungeonModule(this);

		Collection<PlayerAetherModule> modules = new ArrayList<>();
		modules.add(this.companionModule);
		modules.add(this.abilitiesModule);
		modules.add(this.gravititeAbilityModule);
		modules.add(this.teleportingModule);
		modules.add(this.parachuteModule);
		modules.add(this.bossModule);
		modules.add(this.equipmentModule);
		modules.add(this.extendedReachModule);
		modules.add(this.dungeonModule);

		this.modules = modules.toArray(new PlayerAetherModule[modules.size()]);
	}

	public static PlayerAether getPlayer(Entity player)
	{
		if (!player.hasCapability(AetherCapabilities.PLAYER_DATA, null))
		{
			return null;
		}

		return (PlayerAether) player.getCapability(AetherCapabilities.PLAYER_DATA, null);
	}

	public boolean hasDiedInAetherBefore()
	{
		return this.hasDiedInAetherBefore;
	}

	public void setHasDiedInAetherBefore(boolean flag)
	{
		this.hasDiedInAetherBefore = flag;
	}

	@Override
	public void onUpdate(LivingUpdateEvent event)
	{
		for (PlayerAetherModule module : this.modules)
		{
			module.onUpdate();
		}
	}

	@Override
	public void onRespawn(PlayerEvent.PlayerRespawnEvent event)
	{
		this.dungeonModule.onRespawn();
	}

	@Override
	public void onPlaceBlock(BlockEvent.PlaceEvent event)
	{
		this.dungeonModule.onPlaceBlock(event);
	}

	@Override
	public void onDeath(LivingDeathEvent event)
	{
		this.companionModule.onDeath(event);
		this.gravititeAbilityModule.onDeath(event);
	}

	@Override
	public void onDrops(PlayerDropsEvent event)
	{

	}

	@Override
	public void onHurt(LivingHurtEvent event)
	{
		PlayerAether aePlayer = PlayerAether.getPlayer(event.getEntity());

		if (aePlayer != null)
		{
			if (aePlayer.getEquipmentModule().getEffectPool(new ResourceLocation(AetherCore.MOD_ID, "fire_immunity")).isPresent())
			{
				if (event.getSource() == DamageSource.inFire || event.getSource() == DamageSource.onFire || event.getSource() == DamageSource.lava)
				{
					event.setCanceled(true);
				}
			}
		}
	}

	@Override
	public void onFall(LivingFallEvent event)
	{
		this.abilitiesModule.onFall(event);
	}

	@Override
	public void onTeleport(PlayerEvent.PlayerChangedDimensionEvent event)
	{
		this.companionModule.onTeleport(event);
	}

	@Override
	public void onSpawned(PlayerEvent.PlayerLoggedInEvent event)
	{
		this.companionModule.onSpawned();
	}

	@Override
	public void onDespawn(PlayerEvent.PlayerLoggedOutEvent event)
	{
		this.companionModule.onDespawn(event);
	}

	@Override
	public IInventoryEquipment getEquipmentInventory()
	{
		return this.equipmentModule.getInventory();
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
	public void write(NBTTagCompound tag)
	{
		NBTTagCompound modules = new NBTTagCompound();

		for (PlayerAetherModule module : this.modules)
		{
			module.write(modules);
		}

		tag.setTag("Modules", modules);
		tag.setBoolean("HasDiedInAether", this.hasDiedInAetherBefore);
	}

	@Override
	public void read(NBTTagCompound tag)
	{
		NBTTagCompound modules = tag.getCompoundTag("Modules");

		for (PlayerAetherModule module : this.modules)
		{
			module.read(modules);
		}

		this.hasDiedInAetherBefore = tag.getBoolean("HasDiedInAether");
	}

	public EntityPlayer getEntity()
	{
		return this.entity;
	}

	public PlayerCompanionModule getCompanionModule()
	{
		return this.companionModule;
	}

	public GravititeAbilityModule getGravititeAbility()
	{
		return this.gravititeAbilityModule;
	}

	public TeleportingModule getTeleportingModule()
	{
		return this.teleportingModule;
	}

	public ParachuteModule getParachuteModule()
	{
		return this.parachuteModule;
	}

	public AbilitiesModule getAbilitiesModule()
	{
		return this.abilitiesModule;
	}

	public BossModule getBossModule()
	{
		return this.bossModule;
	}

	public EquipmentModule getEquipmentModule()
	{
		return this.equipmentModule;
	}

	public static class Storage implements IStorage<IPlayerAether>
	{
		@Override
		public NBTBase writeNBT(Capability<IPlayerAether> capability, IPlayerAether instance, EnumFacing side)
		{
			NBTTagCompound compound = new NBTTagCompound();
			instance.write(compound);

			return compound;
		}

		@Override
		public void readNBT(Capability<IPlayerAether> capability, IPlayerAether instance, EnumFacing side, NBTBase nbt)
		{
			NBTTagCompound compound = (NBTTagCompound) nbt;

			instance.read(compound);
		}
	}
}
