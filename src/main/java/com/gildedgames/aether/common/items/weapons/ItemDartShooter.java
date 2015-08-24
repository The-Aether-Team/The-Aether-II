package com.gildedgames.aether.common.items.weapons;

import com.gildedgames.aether.common.Aether;
import com.gildedgames.aether.common.entities.projectiles.EntityDart;
import com.gildedgames.aether.common.items.ItemsAether;
import com.gildedgames.aether.common.items.weapons.ItemDart.DartType;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class ItemDartShooter extends Item
{
	public enum DartShooterType
	{
		GOLDEN("golden", DartType.GOLDEN),
		ENCHANTED("enchanted", DartType.ENCHANTED),
		POISON("poison", DartType.POISON),
		PHOENIX("phoenix", DartType.PHOENIX);

		private final String name;

		private final DartType ammoType;

		DartShooterType(String name, DartType ammoType)
		{
			this.name = name;
			this.ammoType = ammoType;
		}

		public static DartShooterType fromOrdinal(int ordinal)
		{
			DartShooterType[] darts = values();

			return darts[ordinal > darts.length || ordinal < 0 ? 0 : ordinal];
		}
	}

	public ItemDartShooter()
	{
		this.setHasSubtypes(true);

		this.setMaxStackSize(1);
	}

	@Override
	@SuppressWarnings("unchecked")
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item item, CreativeTabs tab, List subItems)
	{
		for (DartShooterType type : DartShooterType.values())
		{
			subItems.add(new ItemStack(item, 1, type.ordinal()));
		}
	}

	@Override
	public int getMaxItemUseDuration(ItemStack stack)
	{
		return 72000;
	}

	@Override
	public EnumAction getItemUseAction(ItemStack stack)
	{
		return EnumAction.BOW;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
	{
		DartShooterType shooterType = DartShooterType.fromOrdinal(stack.getMetadata());
		DartType ammoType = shooterType.ammoType;

		ItemStack ammoStack = this.getMatchingAmmo(player.inventory, ammoType);

		if (ammoStack != null || player.capabilities.isCreativeMode)
		{
			player.setItemInUse(stack, this.getMaxItemUseDuration(stack) - 5);
		}

		return stack;
	}

	@Override
	public void onPlayerStoppedUsing(ItemStack stack, World world, EntityPlayer player, int timeLeft)
	{
		DartShooterType shooterType = DartShooterType.fromOrdinal(stack.getMetadata());
		DartType ammoType = shooterType.ammoType;

		ItemStack ammoStack = this.getMatchingAmmo(player.inventory, ammoType);

		if (ammoStack == null && !player.capabilities.isCreativeMode)
		{
			return;
		}

		int duration = this.getMaxItemUseDuration(stack) - timeLeft - 5;

		if (duration > 2)
		{
			float speed = duration / 15f;
			speed = (speed * speed + speed * 2.0F) / 4.0F;

			if (speed > 1f)
			{
				speed = 1f;
			}

			EntityDart dart = new EntityDart(world, player, speed * 2.0F);
			dart.setDartType(ammoType);
			dart.setDamage(ammoType.getDamage());

			if (speed >= 0.8f)
			{
				dart.setIsCritical(true);
			}

			world.playSoundAtEntity(player, Aether.getResourcePath("aerandom.dart_shooter"), 1.0F, 1.0F / (itemRand.nextFloat() * 0.4F + 1.2F) + speed * 0.5F);

			if (!world.isRemote)
			{
				world.spawnEntityInWorld(dart);
			}

			if (ammoStack != null && !player.capabilities.isCreativeMode)
			{
				ammoStack.stackSize--;
			}
		}
	}

	private ItemStack getMatchingAmmo(InventoryPlayer inventory, DartType ammo)
	{
		int searchMeta = ammo.ordinal();

		if (ammo == DartType.PHOENIX)
		{
			searchMeta = DartType.GOLDEN.ordinal();
		}

		for (ItemStack stack : inventory.mainInventory)
		{
			if (stack != null && stack.getItem() == ItemsAether.dart)
			{
				if (stack.getMetadata() == searchMeta)
				{
					return stack;
				}
			}
		}

		return null;
	}

	@Override
	public String getUnlocalizedName(ItemStack stack)
	{
		return super.getUnlocalizedName(stack) + "." + DartShooterType.fromOrdinal(stack.getMetadata()).name;
	}
}
