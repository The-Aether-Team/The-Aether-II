package com.gildedgames.aether.common.capabilities.player;

import com.gildedgames.aether.api.capabilites.AetherCapabilities;
import com.gildedgames.aether.api.player.IPlayerAetherCapability;
import com.gildedgames.aether.common.capabilities.player.modules.*;
import com.gildedgames.aether.common.containers.inventory.InventoryEquipment;
import com.gildedgames.aether.common.items.ItemsAether;
import com.gildedgames.aether.common.util.PlayerUtil;
import com.google.common.collect.Lists;
import net.minecraft.block.material.Material;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
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

	public PlayerAetherImpl(EntityPlayer player)
	{
		this.player = player;

		this.equipmentInventory = new InventoryEquipment(this);

		this.companionModule = new PlayerCompanionModule(this);
		this.abilitiesModule = new AbilitiesModule(this);
		this.gravititeAbilityModule = new GravititeAbilityModule(this);

		this.modules.add(this.companionModule);
		this.modules.add(this.abilitiesModule);
		this.modules.add(this.gravititeAbilityModule);

		this.modules.add(new EquipmentModule(this, this.equipmentInventory));
		this.modules.add(new ExtendedReachModule(this));
	}

	public static PlayerAetherImpl getPlayer(Entity player)
	{
		return (PlayerAetherImpl) player.getCapability(AetherCapabilities.PLAYER_DATA, null);
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
	public void onDeath(LivingDeathEvent event)
	{
		for (PlayerAetherModule module : this.modules)
		{
			module.onDeath(event);
		}
	}

	@Override
	public void onDrops(LivingDropsEvent event)
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
		if (PlayerUtil.isWearingEquipment(this, ItemsAether.neptune_armor_set))
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
	public boolean performDoubleJump()
	{
		return this.abilitiesModule.performDoubleJump();
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

	public static class Storage implements IStorage<IPlayerAetherCapability>
	{
		@Override
		public NBTBase writeNBT(Capability<IPlayerAetherCapability> capability, IPlayerAetherCapability instance, EnumFacing side)
		{
			NBTTagCompound compound = new NBTTagCompound();

			NBTTagCompound equipment = new NBTTagCompound();
			instance.getEquipmentInventory().write(equipment);

			compound.setTag("equipment", equipment);

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
		}
	}
}
