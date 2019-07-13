package com.gildedgames.aether.common.items.weapons.crossbow;

import com.gildedgames.aether.api.registrar.ItemsAether;
import com.gildedgames.aether.common.entities.projectiles.EntityBolt;
import com.gildedgames.aether.common.entities.projectiles.EntityBolt.BoltAbility;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.util.SoundEvents;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.*;
import net.minecraft.util.text.*;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;

public class ItemCrossbow extends Item
{
	//TODO: add Zanite ability (as weapon degrades, weapon fires faster from FAST speed to FASTEST), speed currently assigned in ItemsAether using setDurationInTicks
	//TODO: add Gravitite ability : No bolt drop & Load blocks as ammo to be fired at long range.

	public static final ItemBoltType[] BOLT_TYPES = ItemBoltType.values();

	private float durationInTicks;

	private float knockBackValue;

	private Type type;

	private boolean isSpecialLoaded = false;

	public ItemCrossbow(Type type, Item.Properties properties)
	{
		super(properties.maxDamage(type.maxDurability));

		this.type = type;

		this.knockBackValue = 0;
		this.durationInTicks = 20.0F;

		this.addPropertyOverride(new ResourceLocation("pull"), (stack, worldIn, entity) -> {
			if (entity == null)
			{
				return 0.0F;
			}
			else
			{
				final ItemStack itemstack = entity.getActiveItemStack();

				if (ItemCrossbow.isLoaded(stack))
				{
					return 1.0F;
				}

				return itemstack.getItem() == ItemCrossbow.this ?
						((float) (stack.getUseDuration() - entity.getItemInUseCount()) / ItemCrossbow.this.durationInTicks) :
						0.0F;
			}
		});

		this.addPropertyOverride(new ResourceLocation("pulling"),
				(stack, worldIn, entityIn) -> entityIn != null && (entityIn.isHandActive() && entityIn.getActiveItemStack() == stack)
						|| ItemCrossbow.isLoaded(stack) ? 1.0F : 0.0F);
	}

	private static void checkTag(final ItemStack stack)
	{
		if (stack.getTag() == null)
		{
			stack.setTag(new CompoundNBT());
		}
	}

	public static boolean isLoaded(final ItemStack stack)
	{
		if (stack == null)
		{
			return false;
		}

		checkTag(stack);

		return stack.getTag().getBoolean("loaded");
	}

	public static void setLoaded(final ItemStack stack, final boolean loaded)
	{
		if (stack == null)
		{
			return;
		}

		checkTag(stack);

		stack.getTag().putBoolean("loaded", loaded);
	}

	public static ItemBoltType getLoadedBoltType(final ItemStack stack)
	{
		if (stack == null)
		{
			return null;
		}

		checkTag(stack);

		return ItemCrossbow.BOLT_TYPES[stack.getTag().getInt("boltType")];
	}

	public static void setLoadedBoltType(final ItemStack stack, final ItemBoltType type)
	{
		if (stack == null)
		{
			return;
		}

		checkTag(stack);

		stack.getTag().putInt("boltType", type.ordinal());
	}

	private boolean hasAmmo(final PlayerEntity player)
	{
		final ItemStack boltStack = player.getItemStackFromSlot(EquipmentSlotType.OFFHAND);

		return boltStack.getItem() instanceof ItemBolt && boltStack.getCount() > 0;
	}

	@Override
	public boolean onEntitySwing(final ItemStack stack, final LivingEntity entityLiving)
	{
		if (ItemCrossbow.isLoaded(stack))
		{
			if (entityLiving.world.isRemote())
			{
				final ItemRenderer renderer = Minecraft.getInstance().getItemRenderer();
//				renderer.equippedProgressMainHand = 1.5F; TODO:  1.14
			}

			this.shootBolt(entityLiving, stack);

			if (!entityLiving.world.isRemote())
			{
				ItemCrossbow.setLoaded(stack, false);
			}

//			entityLiving.activeItemStackUseCount = 0; TODO:  1.14

			return true;
		}

		return false;
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(final World worldIn, final PlayerEntity playerIn, final Hand hand)
	{
		final ItemStack stack = playerIn.getHeldItem(hand);

		if (!ItemCrossbow.isLoaded(stack))
		{
			if (this.hasAmmo(playerIn))
			{
				playerIn.setActiveHand(Hand.MAIN_HAND);
			}
		}

		return new ActionResult<>(ActionResultType.PASS, stack);
	}

	private void shootBolt(final LivingEntity entityLiving, final ItemStack stack)
	{
		if (!entityLiving.world.isRemote())
		{
			final World world = entityLiving.world;

			world.playSound(null, entityLiving.posX, entityLiving.posY, entityLiving.posZ, SoundEvents.ENTITY_ARROW_SHOOT, SoundCategory.NEUTRAL, 1.0F,
					1.0F / (world.getRandom().nextFloat() * 0.4F + 1.2F));

			final float speed = 1.0f;

			final ItemBoltType boltType = ItemCrossbow.getLoadedBoltType(stack);

			EntityBolt bolt0 = null,
					bolt1 = null,
					bolt2 = null;


			// calculate bolts that are special is being loaded.
			if (this.isSpecialLoaded)
			{
				float specialShotMultiplier = this.type.damageMultiplier * this.type.specialMultiplier; // bolt damage multiplier gets applied in #creatBolt
				if (specialShotMultiplier < 1) {
					specialShotMultiplier = 1;
				}

				if (this.type == Type.SKYROOT)
				{
					bolt0 = this.createBolt(entityLiving, speed, 0, 0, 0, specialShotMultiplier, boltType);
					bolt1 = this.createBolt(entityLiving, speed, 0, 0, 0, specialShotMultiplier, boltType);
				}
				else if (this.type == Type.HOLYSTONE)
				{
					bolt0 = this.createBolt(entityLiving, speed / 2, 0, 0, 0, specialShotMultiplier, boltType);
					bolt1 = this.createBolt(entityLiving, speed / 2, 10, 0, 0, specialShotMultiplier, boltType);
					bolt2 = this.createBolt(entityLiving, speed / 2, -10, 0, 0, specialShotMultiplier, boltType);
				}
				else if (this.type == Type.ZANITE)
				{
					bolt0 = this.createBolt(entityLiving, speed, 0, 0, 0, specialShotMultiplier, boltType);
				}
				else if (this.type == Type.ARKENIUM)
				{
					bolt0 = this.createBolt(entityLiving, speed * 2.5f, 0, -1, 0, specialShotMultiplier, boltType);
				}
				else if (this.type == Type.GRAVETITE)
				{

				}
				else
				{
					bolt0 = this.createBolt(entityLiving, speed, 0, 0, -0.5f, specialShotMultiplier, boltType);
				}
			}
			// standard bolts
			else
			{
				bolt0 = this.createBolt(entityLiving, speed, 0, 0, 0, this.type.damageMultiplier, boltType);
			}

			if (entityLiving instanceof PlayerEntity)
			{
				final PlayerEntity player = (PlayerEntity) entityLiving;

				if (player.isCreative())
				{
					if (bolt0 != null)
					{
						bolt0.pickupStatus = ArrowEntity.PickupStatus.CREATIVE_ONLY;
					}
					if (bolt1 != null)
					{
						bolt1.pickupStatus = ArrowEntity.PickupStatus.CREATIVE_ONLY;
					}
					if (bolt2 != null)
					{
						bolt2.pickupStatus = ArrowEntity.PickupStatus.CREATIVE_ONLY;
					}
				}
				else
				{
					if (this.getDamage(stack) >= this.getMaxDamage(stack))
					{
						player.inventory.deleteStack(stack);
						world.playSound(null, entityLiving.posX, entityLiving.posY, entityLiving.posZ, SoundEvents.ENTITY_ITEM_BREAK, SoundCategory.PLAYERS,
								1.0F,
								1.0F / (world.getRandom().nextFloat() * 0.4F + 1.2F));
					}

					// damage crossbow
					if (this.isSpecialLoaded)
					{
						this.setDamage(stack, this.getDamage(stack) + 2);
					}
					else
					{
						this.setDamage(stack, this.getDamage(stack) + 1);
					}
				}
			}

			if (bolt0 != null)
			{
				entityLiving.getEntityWorld().addEntity(bolt0);
			}

			if (bolt1 != null)
			{
				entityLiving.getEntityWorld().addEntity(bolt1);
			}

			if (bolt2 != null)
			{
				entityLiving.getEntityWorld().addEntity(bolt2);
			}

			this.isSpecialLoaded = false;
		}
	}

	private EntityBolt createBolt(LivingEntity entityLiving, float speed, float addRotationYaw, float addRotationPitch, float addInaccuracy,
			float damageMultiplier, ItemBoltType boltType)
	{
		if (!entityLiving.world.isRemote())
		{
			final EntityBolt bolt = new EntityBolt(entityLiving.getEntityWorld(), entityLiving);

			bolt.setBoltType(boltType);

			bolt.shoot(entityLiving, entityLiving.rotationPitch + addRotationPitch, entityLiving.rotationYaw + addRotationYaw,
					0.0F, speed * 2.0F, 1.0F + addInaccuracy);
			bolt.setBoltAbility(BoltAbility.NORMAL);
			bolt.setDamage(bolt.getDamage() * damageMultiplier);
			bolt.pickupStatus = ArrowEntity.PickupStatus.ALLOWED;

			return bolt;
		}

		return null;
	}

	@Override
	public void onUsingTick(final ItemStack stack, final LivingEntity living, final int count)
	{
		if (living instanceof PlayerEntity)
		{
			final PlayerEntity player = (PlayerEntity) living;
			final ItemStack boltStack = player.getItemStackFromSlot(EquipmentSlotType.OFFHAND);

			if (this.hasAmmo(player))
			{
				final float use = (float) (this.getUseDuration(stack) - living.getItemInUseCount()) / 20.0F;
				if (use == (this.durationInTicks / 20.0F))
				{
					if (!living.getEntityWorld().isRemote)
					{
						ItemCrossbow.setLoadedBoltType(stack, ItemCrossbow.BOLT_TYPES[boltStack.getDamage()]);
						ItemCrossbow.setLoaded(stack, true);

						this.isSpecialLoaded = player.isSneaking();

						if (!player.isCreative())
						{
							int shrinkQuantity = 1;
							if (this.isSpecialLoaded)
							{
								if (this.type == Type.HOLYSTONE)
								{
									shrinkQuantity = 3;
								}
								if (this.type == Type.SKYROOT)
								{
									shrinkQuantity = 2;
								}
							}
							boltStack.shrink(shrinkQuantity);

							if (boltStack.getCount() == 0)
							{
								player.inventory.deleteStack(boltStack);
							}
						}
					}
				}
			}
		}
	}

	@Override
	public void onPlayerStoppedUsing(final ItemStack stack, final World worldIn, final LivingEntity entityLiving, final int timeLeft)
	{

	}

	@Override
	public int getUseDuration(final ItemStack stack)
	{
		return 72000;
	}

	@Override
	public boolean shouldSyncTag()
	{
		return true;
	}

	public float getKnockBackValue()
	{
		return this.knockBackValue;
	}

	public ItemCrossbow setKnockBackValue(final float x)
	{
		this.knockBackValue = x;

		return this;
	}

	public float getDurationInTicks()
	{
		return this.durationInTicks;
	}

	public ItemCrossbow setDurationInTicks(final int x)
	{
		this.durationInTicks = x;

		return this;
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public void addInformation(final ItemStack stack, final World world, final List<ITextComponent> tooltip, final ITooltipFlag flag)
	{
		final float seconds = this.durationInTicks / 20.0F;

		tooltip.add(new TranslationTextComponent("item.aether.crossbow.desc1").setStyle(new Style().setColor(TextFormatting.BLUE)));

		if (seconds == Math.floor(seconds))
		{
			tooltip.add(new StringTextComponent(TextFormatting.GRAY + "\u2022 " + (int) Math.floor(seconds) + " " + I18n.format(
					"item.aether.crossbow.desc" + (seconds < 1 || seconds > 1 ? "2" : "3"))));
		}
		else
		{
			tooltip.add(new StringTextComponent(TextFormatting.GRAY + "\u2022 " + seconds + " " + I18n.format(
					"item.aether.crossbow.desc" + (seconds < 1 || seconds > 1 ? "2" : "3"))));
		}

		tooltip.add(new TranslationTextComponent("item.aether.crossbow.desc4").setStyle(new Style().setColor(TextFormatting.BLUE)));

		tooltip.add(new StringTextComponent(TextFormatting.GRAY + "\u2022 " + I18n.format("item.aether." + this.type.name + ".ability")));
	}

	@Override
	public int getBurnTime(ItemStack itemStack)
	{
		return 100;
	}

	public Type getType()
	{
		return this.type;
	}

	public enum Type
	{
		SKYROOT(1F, 1.f,200, "skyroot_crossbow"),
		HOLYSTONE(1.2F, 1.f,250, "holystone_crossbow"),
		ZANITE(1.3F, 1.5f,300, "zanite_crossbow"),
		ARKENIUM(1.4F, 2.0f,400, "arkenium_crossbow"),
		GRAVETITE(1.6F, 1.f,350, "gravitite_crossbow");

		final float damageMultiplier;

		final float specialMultiplier;

		final int maxDurability;

		final String name;

		Type(float damageMultiplier, float specialMultiplier, int maxDurability, String name)
		{
			this.damageMultiplier = damageMultiplier;
			this.specialMultiplier = specialMultiplier;
			this.maxDurability = maxDurability;
			this.name = name;
		}
	}
}
