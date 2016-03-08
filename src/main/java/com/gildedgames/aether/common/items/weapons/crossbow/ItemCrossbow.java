package com.gildedgames.aether.common.items.weapons.crossbow;

import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.entities.projectiles.EntityBolt;
import com.gildedgames.aether.common.entities.projectiles.EntityDart;
import com.gildedgames.aether.common.items.ItemsAether;
import com.gildedgames.aether.common.items.weapons.ItemDart;
import com.gildedgames.aether.common.items.weapons.ItemDartType;
import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

import java.util.Stack;

/**
 * Created by Chris on 3/8/2016.
 */
public class ItemCrossbow extends Item
{

	private final int QUIVERSLOT = 0;
	private boolean isReadyToFire;
	private ItemStack ammoBoltStack;
	private int ammoBoltCount;

	public ItemCrossbow()
	{
		this.maxStackSize = 1;
		isReadyToFire = false;
		ammoBoltStack = null;
		ammoBoltCount = 0;
	}

	@Override
	public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity)
	{
		if (ammoBoltStack == null)
		{
			return false;
		}
		if (!isReadyToFire)
		{
			return false;
		}

		return true;
	}

	public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected)
	{
		EntityPlayer player = (EntityPlayer) entityIn;

		if (isSelected)
		{
			ammoBoltStack =  player.inventory.getStackInSlot(QUIVERSLOT);

			if (ammoBoltStack != null)
			{
				ammoBoltCount = ammoBoltStack.stackSize;
			}

			if (ammoBoltCount <= 0)
			{
				isReadyToFire = false;
				player.inventory.setInventorySlotContents(QUIVERSLOT, null);
			}
		}
	}

	// When weapon is being used (right click pressed) it fires
	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
	{

		if (this.isReadyToFire)
		{
			if (getBoltTypeFromAmmoSlot(player.inventory) == -1)
			{
				return stack;
			}

			ItemBoltType boltType = ItemBoltType.fromOrdinal(getBoltTypeFromAmmoSlot(player.inventory));

			float speed = 1.0f;
			boolean isInfiniteBolt = player.capabilities.isCreativeMode || EnchantmentHelper.getEnchantmentLevel(Enchantment.infinity.effectId, stack) > 0;

			if (ammoBoltCount > 0 || isInfiniteBolt)
			{
				EntityBolt bolt = new EntityBolt(world, player, speed * 2.0F);
				bolt.setBoltType(boltType);
				bolt.setDamage(boltType.getAmmoItem().getDamage());


				/*
				* Uploading this even though there is a really weird bug here.
				* For some reason I cannot for the life of me get the bolt to fire out of the crossbow.
				* It was essentially working up until I added the isReadyToFire.
				* The isReadyToFire switches properly, and when it needs to, all the statements run, etc. etc.
				* but for some reason it won't play the sound effect, and it won't always spawn the bolt.
				* IF you grab a stack, and fire a shitton of bolts at an entity, one will eventually shoot and hurt the entity
				* However this is like a 1/32 chance, and I'm not sure what is causing it.
				*
				* Really could use some assistance on how to fix this issue!
				* Thanks
				*/

				// dummy sound effect
				world.playSoundAtEntity(player, AetherCore.getResourcePath("aerandom.dart_shooter"), 1.0F, 1.0F / (itemRand.nextFloat() * 0.4F + 1.2F) + speed * 0.5F);

				if (!world.isRemote)
				{
					world.spawnEntityInWorld(bolt);
				}

				this.isReadyToFire = false;

//   			Pretty sure this if statement isn't needed, but I'm leaving it just in case.
//				if (!isInfiniteBolt && ammoBoltCount > 0)
//				{
//					this.isReadyToFire = false;
//				}
			}
		}


		return stack;

	}

	@Override
	public boolean onEntitySwing(EntityLivingBase entityLiving, ItemStack stack)
	{

		// If we have no bolts crossbow can be swung freely.
		if (ammoBoltStack == null)
		{
			return false;
		}

		if (entityLiving instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer) entityLiving;
			if (ammoBoltStack != null)
			{
				if (entityLiving.getHeldItem().getItem() == ItemsAether.crossbow)
				{
					// reloading
					if (this.isReadyToFire == false)
					{
						this.ammoBoltStack.stackSize--;
						this.isReadyToFire = true;

						// When crossbow has an bolt loaded it can not be swung.
						return false;
					}
				}
			}
			if (isReadyToFire)
			{
				// Cancel the swing animation/damage when a bolt is loaded.
				return true;
			}
		}

		// Should never reach this point
		return false;
	}

	// Should be adjusted to check for quiver accessory slot, currently checks the first space in players inventory for ammo.
	private int getBoltTypeFromAmmoSlot(InventoryPlayer inventory)
	{
		if (ammoBoltStack != null && ammoBoltStack.getItem() == ItemsAether.bolt)
		{
			return ammoBoltStack.getMetadata();
		}

		return -1;
	}



}
