package com.gildedgames.aether.common.player;

import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.containers.inventory.InventoryAccessories;
import com.gildedgames.aether.common.items.ItemsAether;
import com.gildedgames.aether.common.items.armor.ItemAetherArmor;
import com.gildedgames.aether.common.items.armor.ItemNeptuneArmor;
import com.gildedgames.aether.common.party.Party;
import com.gildedgames.aether.common.util.PlayerUtil;
import com.gildedgames.util.core.io.ByteBufHelper;
import com.gildedgames.util.modules.entityhook.api.IEntityHookFactory;
import com.gildedgames.util.modules.entityhook.impl.hooks.EntityHook;
import com.gildedgames.util.modules.entityhook.impl.providers.PlayerHookProvider;
import io.netty.buffer.ByteBuf;
import net.minecraft.block.material.Material;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;

public class PlayerAether extends EntityHook<EntityPlayer>
{
	public static final PlayerHookProvider<PlayerAether> PROVIDER = new PlayerHookProvider<>("aether:player", new Factory());

	private InventoryAccessories inventoryAccessories = new InventoryAccessories(this);

	private Party currentParty;

	@Override
	public void init(Entity entity, World world)
	{
		super.init(entity, world);
	}

	public static PlayerAether get(Entity entity)
	{
		return PlayerAether.PROVIDER.getHook(entity);
	}

	/**
	 * Called when the bound player ticks
	 */
	public void onUpdate()
	{
		
	}

	/**
	 * Called when the living player attached to this instance dies
	 */
	public void onDeath()
	{

	}

	public void dropAccessories()
	{
		if (!this.getEntity().getEntityWorld().getGameRules().getBoolean("keepInventory"))
		{
			this.getEntity().captureDrops = true;

			this.getInventoryAccessories().dropAllItems();
			
			this.getEntity().captureDrops = false;
		}
	}

	public void onCalculateBreakSpeed(PlayerEvent.BreakSpeed event)
	{
		if (PlayerUtil.isWearingFullSet(this.getEntity(), ItemNeptuneArmor.class))
		{
			if (!EnchantmentHelper.getAquaAffinityModifier(this.getEntity()) && this.getEntity().isInsideOfMaterial(Material.water))
			{
				event.newSpeed = event.originalSpeed * 5.0f;
			}
		}

		if (PlayerUtil.wearingAccessory(this.getEntity(), ItemsAether.zanite_ring) || PlayerUtil.wearingAccessory(this.getEntity(), ItemsAether.zanite_pendant))
		{
			event.newSpeed = event.newSpeed * 5.0f; // testing code!!!! Should be removed.

			// TODO: rings don't have durability so the below code won't do anything
			// when rings do have durability this should be uncommented and the above removed.

				/*
				* PlayerAether aePlayer = PlayerAether.get(player);
				* InventoryAccessories inventory = new InventoryAccessories(aePlayer);
				*
				*
				* for (ItemStack stack : inventory.getInventory())
				* {
				*	if (stack != null && stack.getItem() == ItemsAether.zanite_ring)
				*	{
				*		event.newSpeed = 1.0f + (stack.getItemDamage() / stack.getMaxDamage() * 3);
				*	}
				* }
				* */
		}
	}

	public void onHurt(LivingHurtEvent event)
	{
		if (!event.source.isUnblockable())
		{
			for (ItemStack stack : this.getEntity().inventory.armorInventory)
			{
				if (stack != null && stack.getItem() instanceof ItemAetherArmor)
				{
					event.ammount -= ((ItemAetherArmor) stack.getItem()).getExtraDamageReduction(stack);
				}
			}
		}
	}

	public InventoryAccessories getInventoryAccessories()
	{
		return this.inventoryAccessories;
	}

	public boolean isAccessoryEquipped(Item item)
	{
		return this.getInventoryAccessories().isAccessoryEquipped(item);
	}

	@Override
	public void loadNBTData(NBTTagCompound compound)
	{
		this.inventoryAccessories.read(compound);
	}

	@Override
	public void saveNBTData(NBTTagCompound compound)
	{
		this.inventoryAccessories.write(compound);
	}

	public Party getCurrentParty()
	{
		return this.currentParty;
	}

	public void setCurrentParty(Party party)
	{
		this.currentParty = party;
	}

	public static class Factory implements IEntityHookFactory<PlayerAether>
	{
		@Override
		public PlayerAether createHook()
		{
			return new PlayerAether();
		}

		@Override
		public void writeFull(ByteBuf buf, PlayerAether hook)
		{
			ByteBufHelper.writeItemStacks(buf, hook.getInventoryAccessories().getInventory());
		}

		@Override
		public void readFull(ByteBuf buf, PlayerAether hook)
		{
			ItemStack[] accessories = ByteBufHelper.readItemStacks(buf);

			for (int i = 0; i < accessories.length; i++)
			{
				hook.getInventoryAccessories().setInventorySlotContents(i, accessories[i]);
			}
		}
	}
}
