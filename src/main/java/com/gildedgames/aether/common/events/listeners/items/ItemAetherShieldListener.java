package com.gildedgames.aether.common.events.listeners.items;

import com.gildedgames.aether.common.items.armor.ItemAetherShield;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.SoundEvents;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;

@Mod.EventBusSubscriber
public class ItemAetherShieldListener
{

	@SubscribeEvent
	public static void onEntityAttacked(final LivingAttackEvent event)
	{
		if (!(event.getEntityLiving() instanceof PlayerEntity))
		{
			return;
		}

		final PlayerEntity player = (PlayerEntity) event.getEntityLiving();

		if (!event.getSource().isUnblockable() && player.isActiveItemStackBlocking())
		{
			final Vec3d vec3d = event.getSource().getDamageLocation();

			if (vec3d != null)
			{
				final Vec3d look = player.getLook(1.0F);

				Vec3d reverse = vec3d.subtractReverse(new Vec3d(player.posX, player.posY, player.posZ)).normalize();
				reverse = new Vec3d(reverse.x, 0.0D, reverse.z);

				if (reverse.dotProduct(look) < 0.0D)
				{
					final float damage = event.getAmount();

					if (damage >= 3.0F && player.getActiveItemStack().getItem() instanceof ItemAetherShield)
					{
						final int itemDamage = 1 + MathHelper.floor(damage);

						player.getActiveItemStack().damageItem(itemDamage, player);

						if (player.getActiveItemStack().getCount() <= 0)
						{
							final Hand hand = player.getActiveHand();

							ForgeEventFactory.onPlayerDestroyItem(player, player.getActiveItemStack(), hand);

							if (hand == Hand.MAIN_HAND)
							{
								player.setItemStackToSlot(EquipmentSlotType.MAINHAND, ItemStack.EMPTY);
							}
							else
							{
								player.setItemStackToSlot(EquipmentSlotType.OFFHAND, ItemStack.EMPTY);
							}

							player.setHeldItem(player.getActiveHand(), ItemStack.EMPTY);

							player.playSound(SoundEvents.ITEM_SHIELD_BREAK, 0.8F, 0.8F + player.world.rand.nextFloat() * 0.4F);
						}
					}
				}
			}
		}
	}
}
