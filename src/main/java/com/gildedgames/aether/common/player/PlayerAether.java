package com.gildedgames.aether.common.player;

import io.netty.buffer.ByteBuf;
import net.minecraft.block.material.Material;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;

import org.apache.commons.lang3.tuple.Pair;

import com.gildedgames.aether.common.AetherCapabilities;
import com.gildedgames.aether.common.containers.inventory.InventoryEquipment;
import com.gildedgames.aether.common.entities.effects.EffectInstance;
import com.gildedgames.aether.common.entities.effects.EffectProcessor;
import com.gildedgames.aether.common.entities.effects.EntityEffects;
import com.gildedgames.aether.common.items.ItemEffectsBase;
import com.gildedgames.aether.common.items.armor.ItemAetherArmor;
import com.gildedgames.aether.common.items.armor.ItemNeptuneArmor;
import com.gildedgames.aether.common.party.Party;
import com.gildedgames.aether.common.util.PlayerUtil;
import com.gildedgames.util.core.io.ByteBufHelper;
import com.gildedgames.util.modules.entityhook.api.IEntityHookFactory;
import com.gildedgames.util.modules.entityhook.impl.hooks.EntityHook;
import com.gildedgames.util.modules.entityhook.impl.providers.PlayerHookProvider;

public class PlayerAether extends EntityHook<EntityPlayer>
{
	public static final PlayerHookProvider<PlayerAether> PROVIDER = new PlayerHookProvider<>("aether:player", new Factory());

	private InventoryEquipment inventoryEquipment = new InventoryEquipment(this);

	private Party currentParty;

	@Override
	public void onLoaded()
	{
		if (!this.getEntity().worldObj.isRemote)
		{
			EntityEffects effects = EntityEffects.get(this.getEntity());
			
			effects.clearEffects();
			
			for (ItemStack stack : this.getEquipment().getInventory())
			{
				if (stack != null && stack.hasCapability(AetherCapabilities.ITEM_EFFECTS, null))
				{
					ItemEffectsBase itemEffects = stack.getCapability(AetherCapabilities.ITEM_EFFECTS, null);

					if (itemEffects != null)
					{
						for (Pair<EffectProcessor, EffectInstance> effect : itemEffects.getEffectPairs())
						{
							EffectProcessor processor = effect.getLeft();
							EffectInstance instance = effect.getRight();
							
							effects.put(processor, instance);
						}
					}
				}
			}
		}
	}

	@Override
	public void onUnloaded()
	{
		if (!this.getEntity().worldObj.isRemote)
		{
			EntityEffects effects = EntityEffects.get(this.getEntity());
	
			for (ItemStack stack : this.getEquipment().getInventory())
			{
				if (stack != null && stack.hasCapability(AetherCapabilities.ITEM_EFFECTS, null))
				{
					ItemEffectsBase itemEffects = stack.getCapability(AetherCapabilities.ITEM_EFFECTS, null);
	
					if (itemEffects != null)
					{
						for (Pair<EffectProcessor, EffectInstance> effect : itemEffects.getEffectPairs())
						{
							EffectProcessor processor = effect.getLeft();
							EffectInstance instance = effect.getRight();
							
							effects.removeInstance(processor, instance);
						}
					}
				}
			}
		}
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

	public void dropEquipment()
	{
		if (!this.getEntity().getEntityWorld().getGameRules().getBoolean("keepInventory"))
		{
			this.getEntity().captureDrops = true;

			this.getEquipment().dropAllItems();
			
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

	public InventoryEquipment getEquipment()
	{
		return this.inventoryEquipment;
	}

	public boolean isItemEquipped(Item item)
	{
		return this.getEquipment().isItemEquipped(item);
	}

	@Override
	public void loadNBTData(NBTTagCompound compound)
	{
		this.inventoryEquipment.read(compound);
	}

	@Override
	public void saveNBTData(NBTTagCompound compound)
	{
		this.inventoryEquipment.write(compound);
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
			ByteBufHelper.writeItemStacks(buf, hook.getEquipment().getInventory());
		}

		@Override
		public void readFull(ByteBuf buf, PlayerAether hook)
		{
			ItemStack[] accessories = ByteBufHelper.readItemStacks(buf);

			for (int i = 0; i < accessories.length; i++)
			{
				hook.getEquipment().setInventorySlotContents(i, accessories[i]);
			}
		}
	}
}
