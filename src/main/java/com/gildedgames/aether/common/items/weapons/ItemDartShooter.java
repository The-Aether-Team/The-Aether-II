package com.gildedgames.aether.common.items.weapons;

import com.gildedgames.aether.common.entities.projectiles.EntityDart;
import com.gildedgames.aether.common.items.ItemsAether;
import com.gildedgames.aether.common.registry.content.SoundsAether;
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
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemDartShooter extends Item
{
	public ItemDartShooter()
	{
		this.setHasSubtypes(true);

		this.setMaxStackSize(1);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(final CreativeTabs tab, final NonNullList<ItemStack> subItems)
	{
		if (!this.isInCreativeTab(tab))
		{
			return;
		}

		for (final ItemDartType type : ItemDartType.values())
		{
			subItems.add(new ItemStack(this, 1, type.ordinal()));
		}
	}

	@Override
	public int getMaxItemUseDuration(final ItemStack stack)
	{
		return 72000;
	}

	@Override
	public EnumAction getItemUseAction(final ItemStack stack)
	{
		return EnumAction.BOW;
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(final World worldIn, final EntityPlayer player, final EnumHand hand)
	{
		final ItemStack stack = player.getHeldItem(hand);

		final ItemDartType dartType = ItemDartType.fromOrdinal(stack.getMetadata());

		final int ammoSlot = this.getMatchingAmmoSlot(player.inventory, dartType.getAmmoItem());

		if (ammoSlot > 0 || player.capabilities.isCreativeMode)
		{
			player.setActiveHand(hand);
		}

		return new ActionResult<>(EnumActionResult.PASS, stack);
	}

	@Override
	public void onPlayerStoppedUsing(final ItemStack stack, final World world, final EntityLivingBase entity, final int timeLeft)
	{
		if (!(entity instanceof EntityPlayer))
		{
			return;
		}

		final EntityPlayer player = (EntityPlayer) entity;

		final ItemDartType dartType = ItemDartType.fromOrdinal(stack.getMetadata());

		final int inventorySlot = this.getMatchingAmmoSlot(player.inventory, dartType.getAmmoItem());

		if (inventorySlot < 0 && !player.capabilities.isCreativeMode)
		{
			return;
		}

		final boolean isInfiniteArrow = player.capabilities.isCreativeMode
				|| EnchantmentHelper.getEnchantmentLevel(Enchantment.getEnchantmentByLocation("infinity"), stack) > 0;

		final int duration = this.getMaxItemUseDuration(stack) - timeLeft - 5;

		if (duration > 3)
		{
			float speed = duration / 15f;
			speed = (speed * speed + speed * 2.0F) / 4.0F;

			if (speed > 1f)
			{
				speed = 1f;
			}

			final EntityDart dart = new EntityDart(world, player);
			dart.shoot(player, player.rotationPitch, player.rotationYaw, 0.0F, speed * 3.0F, 1.0F);
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

			player.playSound(SoundsAether.dart_shooter_fire, 1.0F, 1.0F / (itemRand.nextFloat() * 0.4F + 1.2F) + speed * 0.5F);

			if (!world.isRemote)
			{
				world.spawnEntity(dart);
			}

			if (inventorySlot >= 0 && !player.capabilities.isCreativeMode)
			{
				final ItemStack ammoStack = player.inventory.getStackInSlot(inventorySlot);
				ammoStack.shrink(1);

				if (ammoStack.getCount() <= 0)
				{
					player.inventory.deleteStack(ammoStack);
				}
			}
		}
	}

	private int getMatchingAmmoSlot(final InventoryPlayer inventory, final ItemDartType ammo)
	{
		final int searchMeta = ammo.ordinal();

		for (int i = 0; i < inventory.mainInventory.size(); i++)
		{
			final ItemStack stack = inventory.mainInventory.get(i);

			if (stack.getItem() == ItemsAether.dart)
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
	public String getUnlocalizedName(final ItemStack stack)
	{
		return super.getUnlocalizedName(stack) + "." + ItemDartType.fromOrdinal(stack.getMetadata()).getID();
	}
}
