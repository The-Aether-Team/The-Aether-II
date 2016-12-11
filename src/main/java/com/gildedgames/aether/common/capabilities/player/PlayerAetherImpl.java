package com.gildedgames.aether.common.capabilities.player;

import com.gildedgames.aether.api.capabilites.AetherCapabilities;
import com.gildedgames.aether.api.player.IPlayerAetherCapability;
import com.gildedgames.aether.common.capabilities.player.modules.*;
import com.gildedgames.aether.common.containers.inventory.InventoryEquipment;
import com.gildedgames.aether.common.util.io.NBTHelper;
import com.google.common.collect.Lists;
import net.minecraft.block.material.Material;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerDropsEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

import java.util.LinkedList;

public class PlayerAetherImpl implements IPlayerAetherCapability
{

	private LinkedList<PlayerAetherModule> modules = Lists.newLinkedList();

	private final EntityPlayer player;

	private final InventoryEquipment equipmentInventory;

	private final PlayerCompanionModule companionModule;

	private final AbilitiesModule abilitiesModule;

	private final GravititeAbilityModule gravititeAbilityModule;

	private final TeleportingModule teleportingModule;

	private final ParachuteModule parachuteModule;

	private final EquipmentModule equipmentModule;

	private final BossModule bossModule;

	private final KeepInventoryModule keepInventoryModule;

	private BlockPos deathPos;

	private boolean hasDiedInAetherBefore;

	public PlayerAetherImpl(EntityPlayer player)
	{
		this.player = player;

		this.equipmentInventory = new InventoryEquipment(this);

		this.companionModule = new PlayerCompanionModule(this);
		this.abilitiesModule = new AbilitiesModule(this);
		this.gravititeAbilityModule = new GravititeAbilityModule(this);
		this.teleportingModule = new TeleportingModule(this);
		this.parachuteModule = new ParachuteModule(this);
		this.equipmentModule = new EquipmentModule(this, this.equipmentInventory);
		this.bossModule = new BossModule(this);
		this.keepInventoryModule = new KeepInventoryModule(this);

		this.modules.add(this.companionModule);
		this.modules.add(this.abilitiesModule);
		this.modules.add(this.gravititeAbilityModule);
		this.modules.add(this.teleportingModule);
		this.modules.add(this.parachuteModule);
		this.modules.add(this.bossModule);

		this.modules.add(this.equipmentModule);
		this.modules.add(new ExtendedReachModule(this));
		this.modules.add(new DungeonModule(this));

		this.modules.add(this.keepInventoryModule);
	}

	public static PlayerAetherImpl getPlayer(Entity player)
	{
		return (PlayerAetherImpl) player.getCapability(AetherCapabilities.PLAYER_DATA, null);
	}

	public void setAetherDeathPos(BlockPos pos)
	{
		this.deathPos = pos;
	}

	public BlockPos getAetherDeathPos()
	{
		return this.deathPos;
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
			module.onUpdate(event);
		}
	}

	@Override
	public void onRespawn()
	{
		for (PlayerAetherModule module : this.modules)
		{
			module.onRespawn();
		}
	}

	@Override
	public void onPlaceBlock(BlockEvent.PlaceEvent event)
	{
		for (PlayerAetherModule module : this.modules)
		{
			module.onPlaceBlock(event);
		}
	}

	@Override
	public void onPlaceBlockMulti(BlockEvent.MultiPlaceEvent event)
	{
		for (PlayerAetherModule module : this.modules)
		{
			module.onPlaceBlockMulti(event);
		}
	}

	@Override
	public void onDeath(LivingDeathEvent event)
	{
		for (PlayerAetherModule module : this.modules)
		{
			module.onDeath(event);
		}
	}

	@Override
	public void onDrops(PlayerDropsEvent event)
	{
		for (PlayerAetherModule module : this.modules)
		{
			module.onDrops(event);
		}
	}

	@Override
	public void onHurt(LivingHurtEvent event)
	{
		for (PlayerAetherModule module : this.modules)
		{
			module.onHurt(event);
		}
	}

	@Override
	public void onFall(LivingFallEvent event)
	{
		for (PlayerAetherModule module : this.modules)
		{
			module.onFall(event);
		}
	}

	@Override
	public void onTeleport(PlayerEvent.PlayerChangedDimensionEvent event)
	{
		for (PlayerAetherModule module : this.modules)
		{
			module.onTeleport(event);
		}
	}

	@Override
	public void onSpawned(PlayerEvent.PlayerLoggedInEvent event)
	{
		for (PlayerAetherModule module : this.modules)
		{
			module.onSpawned(event);
		}
	}

	@Override
	public void onDespawn(PlayerEvent.PlayerLoggedOutEvent event)
	{
		for (PlayerAetherModule module : this.modules)
		{
			module.onDespawn(event);
		}
	}

	@Override
	public InventoryEquipment getEquipmentInventory()
	{
		return this.equipmentInventory;
	}

	@Override
	public float getMiningSpeedMultiplier()
	{
		if (this.getPlayer().getAir() == 300 && this.getPlayer().isPotionActive(MobEffects.WATER_BREATHING))
		{
			if (!EnchantmentHelper.getAquaAffinityModifier(this.player) && this.player.isInsideOfMaterial(Material.WATER))
			{
				return 5.0f;
			}
		}

		return 1.0f;
	}

	@Override
	public int getTicksAirborne()
	{
		return this.abilitiesModule.getTicksAirborne();
	}

	@Override
	public boolean performMidAirJump()
	{
		return this.abilitiesModule.performMidAirJump();
	}

	@Override
	public void write(NBTTagCompound tag)
	{
		NBTTagCompound teleportingModule = new NBTTagCompound();
		NBTTagCompound parachuteModule = new NBTTagCompound();
		NBTTagCompound abilitiesModule = new NBTTagCompound();
		NBTTagCompound bossModule = new NBTTagCompound();
		NBTTagCompound keepInventoryModule = new NBTTagCompound();

		this.getTeleportingModule().write(teleportingModule);
		this.getParachuteModule().write(parachuteModule);
		this.getAbilitiesModule().write(abilitiesModule);
		this.getBossModule().write(bossModule);
		this.keepInventoryModule.write(keepInventoryModule);

		tag.setTag("teleportingModule", teleportingModule);
		tag.setTag("parachuteModule", parachuteModule);
		tag.setTag("abilitiesModule", abilitiesModule);
		tag.setTag("bossModule", bossModule);
		tag.setTag("keepInventoryModule", keepInventoryModule);

		tag.setTag("deathPos", NBTHelper.serializeBlockPos(this.deathPos));
		tag.setBoolean("hasDiedInAetherBefore", this.hasDiedInAetherBefore);
	}

	@Override
	public void read(NBTTagCompound tag)
	{
		NBTTagCompound teleportingModule = tag.getCompoundTag("teleportingModule");
		NBTTagCompound parachuteModule = tag.getCompoundTag("parachuteModule");
		NBTTagCompound abilitiesModule = tag.getCompoundTag("abilitiesModule");
		NBTTagCompound bossModule = tag.getCompoundTag("bossModule");
		NBTTagCompound keepInventoryModule = tag.getCompoundTag("keepInventoryModule");

		this.getTeleportingModule().read(teleportingModule);
		this.getParachuteModule().read(parachuteModule);
		this.getAbilitiesModule().read(abilitiesModule);
		this.getBossModule().read(bossModule);
		this.keepInventoryModule.read(keepInventoryModule);

		if (tag.hasKey("deathPos"))
		{
			this.deathPos = NBTHelper.readBlockPos(tag.getCompoundTag("deathPos"));
		}

		this.hasDiedInAetherBefore = tag.getBoolean("hasDiedInAetherBefore");
	}

	@Override
	public EntityPlayer getPlayer()
	{
		return this.player;
	}

	public PlayerCompanionModule getCompanionModule()
	{
		return this.companionModule;
	}

	public GravititeAbilityModule getGravititeAbility()
	{
		return this.gravititeAbilityModule;
	}

	public TeleportingModule getTeleportingModule() { return this.teleportingModule; }

	public ParachuteModule getParachuteModule() { return this.parachuteModule; }

	public AbilitiesModule getAbilitiesModule() { return this.abilitiesModule; }

	public EquipmentModule getEquipmentModule() { return this.equipmentModule; }

	public BossModule getBossModule() { return this.bossModule; }

	public static class Storage implements IStorage<IPlayerAetherCapability>
	{
		@Override
		public NBTBase writeNBT(Capability<IPlayerAetherCapability> capability, IPlayerAetherCapability instance, EnumFacing side)
		{
			NBTTagCompound compound = new NBTTagCompound();

			NBTTagCompound equipment = new NBTTagCompound();
			instance.getEquipmentInventory().write(equipment);

			compound.setTag("equipment", equipment);

			instance.write(compound);

			return compound;
		}

		@Override
		public void readNBT(Capability<IPlayerAetherCapability> capability, IPlayerAetherCapability instance, EnumFacing side, NBTBase nbt)
		{
			NBTTagCompound compound = (NBTTagCompound) nbt;

			if (compound.hasKey("equipment"))
			{
				instance.getEquipmentInventory().read(compound.getCompoundTag("equipment"));
			}

			instance.read(compound);
		}
	}
}
