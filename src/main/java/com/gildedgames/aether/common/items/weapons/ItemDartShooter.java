package com.gildedgames.aether.common.items.weapons;

import com.gildedgames.aether.common.SoundsAether;
import com.gildedgames.aether.common.entities.projectiles.EntityDart;
import com.gildedgames.aether.common.items.ItemsAether;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class ItemDartShooter extends Item
{
	public ItemDartShooter()
	{
		this.setHasSubtypes(true);

		this.setMaxStackSize(1);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item item, CreativeTabs tab, List<ItemStack> subItems)
	{
		for (ItemDartType type : ItemDartType.values())
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
	public ActionResult<ItemStack> onItemRightClick(ItemStack stack, World worldIn, EntityPlayer player, EnumHand hand)
	{
		ItemDartType dartType = ItemDartType.fromOrdinal(stack.getMetadata());

		int ammoSlot = this.getMatchingAmmoSlot(player.inventory, dartType.getAmmoItem());

		if (ammoSlot > 0 || player.capabilities.isCreativeMode)
		{
			player.setActiveHand(EnumHand.MAIN_HAND);
		}

		return new ActionResult<>(EnumActionResult.PASS, stack);
	}

	@Override
	public void onPlayerStoppedUsing(ItemStack stack, World world, EntityLivingBase entity, int timeLeft)
	{
		if (!(entity instanceof EntityPlayer))
		{
			return;
		}

		EntityPlayer player = (EntityPlayer) entity;

		ItemDartType dartType = ItemDartType.fromOrdinal(stack.getMetadata());

		int inventorySlot = this.getMatchingAmmoSlot(player.inventory, dartType.getAmmoItem());

		if (inventorySlot < 0 && !player.capabilities.isCreativeMode)
		{
			return;
		}

		boolean isInfiniteArrow = player.capabilities.isCreativeMode || EnchantmentHelper.getEnchantmentLevel(Enchantment.getEnchantmentByLocation("infinity"), stack) > 0;

		int duration = this.getMaxItemUseDuration(stack) - timeLeft - 5;

		if (duration > 2)
		{
			float speed = duration / 15f;
			speed = (speed * speed + speed * 2.0F) / 4.0F;

			if (speed > 1f)
			{
				speed = 1f;
			}

			EntityDart dart = new EntityDart(world, player);
			dart.setAim(player, player.rotationPitch, player.rotationYaw, 0.0F, speed * 2.0F, 1.0F);
			dart.setDartType(dartType);
			dart.setDamage(dartType.getAmmoItem().getDamage());

			if (speed >= 0.8f)
			{
				dart.setIsCritical(true);
			}

			if (isInfiniteArrow)
			{
				dart.pickupStatus = EntityArrow.PickupStatus.CREATIVE_ONLY;
			}

			world.playSound(player, player.getPosition(), SoundsAether.dart_shooter_fire, SoundCategory.PLAYERS, 1.0F, 1.0F / (itemRand.nextFloat() * 0.4F + 1.2F) + speed * 0.5F);

			if (!world.isRemote)
			{
				world.spawnEntityInWorld(dart);
			}

			if (inventorySlot >= 0 && !player.capabilities.isCreativeMode)
			{
				ItemStack ammoStack = player.inventory.getStackInSlot(inventorySlot);

				ammoStack.stackSize--;

				if (ammoStack.stackSize <= 0)
				{
					player.inventory.setInventorySlotContents(inventorySlot, null);
				}
			}
		}
	}

	private int getMatchingAmmoSlot(InventoryPlayer inventory, ItemDartType ammo)
	{
		int searchMeta = ammo.ordinal();

		for (int i = 0; i < inventory.mainInventory.length; i++)
		{
			ItemStack stack = inventory.mainInventory[i];

			if (stack != null && stack.getItem() == ItemsAether.dart)
			{
				if (stack.getMetadata() == searchMeta)
				{
					return i;
				}
			}
		}

		return -1;
	}

	@Override
	public String getUnlocalizedName(ItemStack stack)
	{
		return super.getUnlocalizedName(stack) + "." + ItemDartType.fromOrdinal(stack.getMetadata()).getID();
	}
}
