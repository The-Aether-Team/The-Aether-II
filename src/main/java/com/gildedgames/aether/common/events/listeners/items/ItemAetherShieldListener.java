package com.gildedgames.aether.common.events.listeners.items;

import com.gildedgames.aether.api.entity.effects.IAetherStatusEffectPool;
import com.gildedgames.aether.api.entity.effects.IAetherStatusEffects;
import com.gildedgames.aether.api.registrar.CapabilitiesAether;
import com.gildedgames.aether.api.registrar.ItemsAether;
import com.gildedgames.aether.common.items.armor.ItemAetherShield;
import com.gildedgames.aether.common.items.weapons.crossbow.ItemCrossbow;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.UUID;

@Mod.EventBusSubscriber
public class ItemAetherShieldListener
{
	@SubscribeEvent
	public static void onEntityAttacked(final LivingAttackEvent event)
	{
		if (!(event.getEntityLiving() instanceof EntityPlayer))
		{
			return;
		}

		final EntityPlayer player = (EntityPlayer) event.getEntityLiving();

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

					if (player.getActiveItemStack().getItem() instanceof ItemAetherShield)
					{
						ItemAetherShield itemAetherShield = (ItemAetherShield) player.getActiveItemStack().getItem();

						IAetherStatusEffectPool statusEffectPool = player.getCapability(CapabilitiesAether.STATUS_EFFECT_POOL, null);

						int buildup = (int) (((int) damage * 5) * (1 - itemAetherShield.getStunResistance()));

						if (statusEffectPool != null)
						{
							if (!statusEffectPool.isEffectApplied(IAetherStatusEffects.effectTypes.GUARD_BREAK))
							{
								statusEffectPool.applyStatusEffect(IAetherStatusEffects.effectTypes.GUARD_BREAK, buildup);
							}
							else
							{
								statusEffectPool.modifyActiveEffectBuildup(IAetherStatusEffects.effectTypes.GUARD_BREAK,
										statusEffectPool.getBuildupFromEffect(IAetherStatusEffects.effectTypes.GUARD_BREAK) + buildup);
							}
						}

						if (damage >= 3.0F)
						{
							final int itemDamage = 1 + MathHelper.floor(damage);

							player.getActiveItemStack().damageItem(itemDamage, player);

							if (player.getActiveItemStack().getCount() <= 0)
							{
								final EnumHand hand = player.getActiveHand();

								ForgeEventFactory.onPlayerDestroyItem(player, player.getActiveItemStack(), hand);

								if (hand == EnumHand.MAIN_HAND)
								{
									player.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, ItemStack.EMPTY);
								}
								else
								{
									player.setItemStackToSlot(EntityEquipmentSlot.OFFHAND, ItemStack.EMPTY);
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

	@SubscribeEvent
	public static void onPlayerBlockStart(final LivingEntityUseItemEvent.Start event)
	{
		if (event.getItem().getItem() instanceof ItemAetherShield)
		{
			if (event.getEntityLiving() != null)
			{
				IAetherStatusEffectPool statusEffectPool = event.getEntityLiving().getCapability(CapabilitiesAether.STATUS_EFFECT_POOL, null);

				if (statusEffectPool != null)
				{
					if (statusEffectPool.isEffectApplied(IAetherStatusEffects.effectTypes.GUARD_BREAK))
					{
						event.setCanceled(true);
					}
				}
			}
		}
	}

	@SubscribeEvent
	public static void onPlayerBlock(final LivingEntityUseItemEvent.Tick event)
	{
		if (event.getItem().getItem() instanceof ItemAetherShield)
		{
			if (event.getEntityLiving() != null)
			{
				IAetherStatusEffectPool statusEffectPool = event.getEntityLiving().getCapability(CapabilitiesAether.STATUS_EFFECT_POOL, null);

				if (statusEffectPool != null)
				{
					for (IAetherStatusEffects effect : statusEffectPool.getPool().values())
					{
						if (effect.getEffectType() == IAetherStatusEffects.effectTypes.GUARD_BREAK)
						{
							if (event.getItem().getCount() > 0)
							{
								effect.adjustDecrease(0);
							}
							else
							{
								effect.resetDecrease();
							}

							if (statusEffectPool.isEffectApplied(IAetherStatusEffects.effectTypes.GUARD_BREAK))
							{
								event.setCanceled(true);
							}
						}
					}
				}
			}
		}
	}

	@SubscribeEvent
	public static void onPlayerBlockStop(final LivingEntityUseItemEvent.Stop event)
	{
		if (event.getItem().getItem() instanceof ItemAetherShield)
		{
			if (event.getEntityLiving() != null)
			{
				IAetherStatusEffectPool statusEffectPool = event.getEntityLiving().getCapability(CapabilitiesAether.STATUS_EFFECT_POOL, null);

				if (statusEffectPool != null)
				{
					for (IAetherStatusEffects effect : statusEffectPool.getPool().values())
					{
						if (effect.getEffectType() == IAetherStatusEffects.effectTypes.GUARD_BREAK)
						{
							effect.resetDecrease();
						}
					}
				}
			}
		}
	}

	@SubscribeEvent
	public static void onSkyrootBlock(final TickEvent.PlayerTickEvent event)
	{
		final UUID SHIELD_UUID = UUID.fromString("8A4950B1-D935-4E22-BAEE-0715A104300D");

		final AttributeModifier SHIELD_BLOCK = new AttributeModifier(SHIELD_UUID, "aether.statusBlockingSkyrootShield", 1.05D, 1);
		IAttributeInstance shieldSlowedLevel = event.player.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED);

		if (event.player.getActiveItemStack().getItem() != ItemsAether.skyroot_shield)
		{
			if (shieldSlowedLevel.getModifier(SHIELD_BLOCK.getID()) != null)
			{
				shieldSlowedLevel.removeModifier(SHIELD_BLOCK);
			}
		}
		else
		{
			if (!shieldSlowedLevel.hasModifier(SHIELD_BLOCK))
			{
				shieldSlowedLevel.applyModifier(SHIELD_BLOCK);
			}
		}
	}

	@SubscribeEvent
	public static void onArkeniumBlock(final TickEvent.PlayerTickEvent event)
	{
		final UUID SHIELD_UUID = UUID.fromString("CB4C4DF3-B0C6-4156-9793-CF46A1DD6116");

		final AttributeModifier SHIELD_BLOCK = new AttributeModifier(SHIELD_UUID, "aether.statusBlockingArkeniumShield", -1.0D, 1);
		IAttributeInstance shieldSlowedLevel = event.player.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED);

		if (event.player.getActiveItemStack().getItem() != ItemsAether.arkenium_shield)
		{
			if (shieldSlowedLevel.getModifier(SHIELD_BLOCK.getID()) != null)
			{
				shieldSlowedLevel.removeModifier(SHIELD_BLOCK);
			}
		}
		else
		{
			if (!shieldSlowedLevel.hasModifier(SHIELD_BLOCK))
			{
				shieldSlowedLevel.applyModifier(SHIELD_BLOCK);
			}
		}
	}

	@SubscribeEvent
	public static void onGravititeBlock(final TickEvent.PlayerTickEvent event)
	{
		final UUID SHIELD_UUID = UUID.fromString("21594E31-3DF3-47CE-9263-6D1D4E75CB70");

		final AttributeModifier SHIELD_BLOCK = new AttributeModifier(SHIELD_UUID, "aether.statusBlockingGravititeShield", -0.2D, 1);
		IAttributeInstance shieldSlowedLevel = event.player.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED);

		if (event.player.getActiveItemStack().getItem() != ItemsAether.gravitite_shield)
		{
			if (shieldSlowedLevel.getModifier(SHIELD_BLOCK.getID()) != null)
			{
				shieldSlowedLevel.removeModifier(SHIELD_BLOCK);
			}
		}
		else
		{
			if (!shieldSlowedLevel.hasModifier(SHIELD_BLOCK))
			{
				shieldSlowedLevel.applyModifier(SHIELD_BLOCK);
			}
		}
	}
}
