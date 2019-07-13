package com.gildedgames.aether.common.items.weapons;

import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.entities.projectiles.EntityDart;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.UseAction;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;

public class ItemDartShooter extends Item
{
	private final ItemDartType type;

	public ItemDartShooter(ItemDartType type, Item.Properties properties)
	{
		super(properties);

		this.type = type;
	}

	@Override
	public int getUseDuration(final ItemStack stack)
	{
		return 72000;
	}

	@Override
	public UseAction getUseAction(final ItemStack stack)
	{
		return UseAction.BOW;
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(final World worldIn, final PlayerEntity player, final Hand hand)
	{
		final ItemStack stack = player.getHeldItem(hand);

		final int ammoSlot = this.getMatchingAmmoSlot(player.inventory, this.type.getAmmoItem());

		if (ammoSlot > 0 || player.isCreative())
		{
			player.setActiveHand(hand);
		}

		return new ActionResult<>(ActionResultType.PASS, stack);
	}

	@Override
	public void onPlayerStoppedUsing(final ItemStack stack, final World world, final LivingEntity entity, final int timeLeft)
	{
		if (!(entity instanceof PlayerEntity))
		{
			return;
		}

		final PlayerEntity player = (PlayerEntity) entity;

		final int inventorySlot = this.getMatchingAmmoSlot(player.inventory, this.type.getAmmoItem());

		if (inventorySlot < 0 && !player.isCreative())
		{
			return;
		}

		final boolean isInfiniteArrow = player.isCreative()
				|| EnchantmentHelper.getEnchantmentLevel(Enchantments.INFINITY, stack) > 0;

		final int duration = this.getUseDuration(stack) - timeLeft - 5;

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
			dart.setDartType(this.type);

			if (speed >= 0.8f)
			{
				dart.setIsCritical(true);
			}

			if (isInfiniteArrow)
			{
				dart.pickupStatus = ArrowEntity.PickupStatus.CREATIVE_ONLY;
			}

			player.playSound(new SoundEvent(AetherCore.getResource("random.dart_shooter.fire")), 1.0F, 1.0F / (world.getRandom().nextFloat() * 0.4F + 1.2F) + speed * 0.5F);

			if (!world.isRemote())
			{
				world.addEntity(dart);
			}

			if (inventorySlot >= 0 && !player.isCreative())
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

	private int getMatchingAmmoSlot(final PlayerInventory inventory, final ItemDartType ammo)
	{
		final int searchMeta = ammo.ordinal();

		for (int i = 0; i < inventory.mainInventory.size(); i++)
		{
			final ItemStack stack = inventory.mainInventory.get(i);

			if (stack.getItem() == this)
			{
				return i;
			}
		}

		return -1;
	}
}
