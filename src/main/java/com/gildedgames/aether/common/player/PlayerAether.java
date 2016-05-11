package com.gildedgames.aether.common.player;

import com.gildedgames.aether.api.capabilites.AetherCapabilities;
import com.gildedgames.aether.api.player.IPlayerAetherCapability;
import com.gildedgames.aether.api.player.inventory.IInventoryEquipment;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.containers.inventory.InventoryEquipment;
import com.gildedgames.aether.common.items.ItemsAether;
import com.gildedgames.aether.common.items.armor.ItemAetherArmor;
import com.gildedgames.aether.common.items.armor.ItemGravititeArmor;
import com.gildedgames.aether.common.items.armor.ItemNeptuneArmor;
import com.gildedgames.aether.common.items.tools.ItemValkyrieTool;
import com.gildedgames.aether.common.util.PlayerUtil;
import net.minecraft.block.material.Material;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingJumpEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public class PlayerAether implements IPlayerAetherCapability
{
	private final EntityPlayer player;

	private InventoryEquipment equipmentInventory;

	public PlayerAether(EntityPlayer player)
	{
		this.player = player;
		this.equipmentInventory = new InventoryEquipment(this);
	}

	public static IPlayerAetherCapability getPlayer(Entity player)
	{
		return player.getCapability(AetherCapabilities.PLAYER_DATA, null);
	}

	@Override
	public void onUpdate(LivingUpdateEvent event)
	{
		float extendedReach = 0.0f;

		if (this.player.getHeldItem() != null)
		{
			Item item = this.player.getHeldItem().getItem();

			if (item instanceof ItemValkyrieTool || item == ItemsAether.valkyrie_lance)
			{
				extendedReach = 3.5f;
			}
		}

		AetherCore.PROXY.setExtendedReachDistance(this.player, extendedReach);
	}

	@Override
	public void onDeath(LivingDeathEvent event) { }

	@Override
	public void onDrops(LivingDropsEvent event)
	{
		if (!this.player.getEntityWorld().getGameRules().getBoolean("keepInventory"))
		{
			this.player.captureDrops = true;

			this.equipmentInventory.dropAllItems();

			this.player.captureDrops = false;
		}
	}

	@Override
	public void onHurt(LivingHurtEvent event)
	{
		if (!event.source.isUnblockable())
		{
			for (ItemStack stack : this.player.inventory.armorInventory)
			{
				if (stack != null && stack.getItem() instanceof ItemAetherArmor)
				{
					event.ammount -= ((ItemAetherArmor) stack.getItem()).getExtraDamageReduction(stack);
				}
			}
		}
	}

	@Override
	public void onFall(LivingFallEvent event)
	{
		Class<? extends Item> fullSet = PlayerUtil.findArmorSet(player);

		if (fullSet == ItemGravititeArmor.class)
		{
			if (player.isSneaking())
			{
				player.motionY += 0.55F;

				AetherCore.PROXY.spawnJumpParticles(player.worldObj, player.posX, player.posY, player.posZ, 1.2D, 12);
			}
		}
	}

	@Override
	public void onJump(LivingJumpEvent event)
	{
		if (PlayerUtil.wearingArmor(player, 0, ItemsAether.sentry_boots) || PlayerUtil.isWearingFullSet(player, ItemGravititeArmor.class))
		{
			event.setCanceled(true);
		}
	}

	@Override
	public IInventoryEquipment getEquipmentInventory()
	{
		return this.equipmentInventory;
	}

	@Override
	public float getMiningSpeedMultiplier()
	{
		if (PlayerUtil.isWearingFullSet(this.player, ItemNeptuneArmor.class))
		{
			if (!EnchantmentHelper.getAquaAffinityModifier(this.player) && this.player.isInsideOfMaterial(Material.water))
			{
				return 5.0f;
			}
		}

		return 1.0f;
	}

	@Override
	public EntityPlayer getPlayer()
	{
		return this.player;
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
