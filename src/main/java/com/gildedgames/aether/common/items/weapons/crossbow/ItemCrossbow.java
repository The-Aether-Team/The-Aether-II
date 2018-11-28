package com.gildedgames.aether.common.items.weapons.crossbow;

import com.gildedgames.aether.common.ReflectionAether;
import com.gildedgames.aether.common.entities.projectiles.EntityBolt;
import com.gildedgames.aether.common.entities.projectiles.EntityBolt.BoltAbility;
import com.gildedgames.aether.common.items.ItemsAether;
import com.gildedgames.aether.common.registry.content.CreativeTabsAether;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.*;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

public class ItemCrossbow extends Item
{
	//TODO: add Zanite ability (as weapon degrades, weapon fires faster from FAST speed to FASTEST), speed currently assigned in ItemsAether using setDurationInTicks
	//TODO: add Gravitite ability : No bolt drop & Load blocks as ammo to be fired at long range.

	public static final ItemBoltType[] BOLT_TYPES = ItemBoltType.values();

	public enum crossBowTypes {
		SKYROOT(1F, 200, "skyroot_crossbow"),
		HOLYSTONE(1.2F, 250, "holystone_crossbow"),
		ZANITE(1.3F, 300, "zanite_crossbow"),
		ARKENIUM(1.4F, 400, "arkenium_crossbow"),
		GRAVETITE(1.6F, 350, "gravitite_crossbow");

		float damageMultiplier;
		int maxDurability;
		String name;

		crossBowTypes(float damageMultiplier, int maxDurability, String name)
		{
			this.damageMultiplier = damageMultiplier;
			this.maxDurability = maxDurability;
			this.name = name;
		}
	}

	private float durationInTicks;

	private float knockBackValue;

	private crossBowTypes cBType;

	private boolean isSpecialLoaded = false;

	public ItemCrossbow()
	{
		this.maxStackSize = 1;

		this.knockBackValue = 0;
		this.durationInTicks = 20.0F;

		this.setCreativeTab(CreativeTabsAether.WEAPONS);

		this.addPropertyOverride(new ResourceLocation("pull"), new IItemPropertyGetter()
		{
			@Override
			@SideOnly(Side.CLIENT)
			public float apply(final ItemStack stack, @Nullable final World worldIn, @Nullable final EntityLivingBase entityIn)
			{
				if (entityIn == null)
				{
					return 0.0F;
				}
				else
				{
					final ItemStack itemstack = entityIn.getActiveItemStack();

					if (ItemCrossbow.isLoaded(stack))
					{
						return 1.0F;
					}

					return itemstack.getItem() == ItemCrossbow.this ?
							((float) (stack.getMaxItemUseDuration() - entityIn.getItemInUseCount()) / ItemCrossbow.this.durationInTicks) :
							0.0F;
				}
			}
		});

		this.addPropertyOverride(new ResourceLocation("pulling"), new IItemPropertyGetter()
		{
			@Override
			@SideOnly(Side.CLIENT)
			public float apply(final ItemStack stack, @Nullable final World worldIn, @Nullable final EntityLivingBase entityIn)
			{
				return entityIn != null && (entityIn.isHandActive() && entityIn.getActiveItemStack() == stack)
						|| ItemCrossbow.isLoaded(stack) ? 1.0F : 0.0F;
			}
		});
	}

	private static void checkTag(final ItemStack stack)
	{
		if (stack.getTagCompound() == null)
		{
			stack.setTagCompound(new NBTTagCompound());
		}
	}

	public static boolean isLoaded(final ItemStack stack)
	{
		if (stack == null)
		{
			return false;
		}

		checkTag(stack);

		return stack.getTagCompound().getBoolean("loaded");
	}

	public static void setLoaded(final ItemStack stack, final boolean loaded)
	{
		if (stack == null)
		{
			return;
		}

		checkTag(stack);

		stack.getTagCompound().setBoolean("loaded", loaded);
	}

	public static ItemBoltType getLoadedBoltType(final ItemStack stack)
	{
		if (stack == null)
		{
			return null;
		}

		checkTag(stack);

		return ItemCrossbow.BOLT_TYPES[stack.getTagCompound().getInteger("boltType")];
	}

	public static void setLoadedBoltType(final ItemStack stack, final ItemBoltType type)
	{
		if (stack == null)
		{
			return;
		}

		checkTag(stack);

		stack.getTagCompound().setInteger("boltType", type.ordinal());
	}

	private boolean hasAmmo(final EntityPlayer player)
	{
		final ItemStack boltStack = player.getItemStackFromSlot(EntityEquipmentSlot.OFFHAND);

		return boltStack.getItem() == ItemsAether.bolt && boltStack.getCount() > 0;
	}

	@Override
	public boolean onEntitySwing(final EntityLivingBase entityLiving, final ItemStack stack)
	{
		if (ItemCrossbow.isLoaded(stack))
		{
			if (entityLiving.world.isRemote)
			{
				final ItemRenderer renderer = Minecraft.getMinecraft().getItemRenderer();

				ObfuscationReflectionHelper.setPrivateValue(ItemRenderer.class, renderer, 1.5F, ReflectionAether.EQUIPPED_PROGRESS_MAIN_HAND.getMappings());
			}

			this.shootBolt(entityLiving, stack);

			if (!entityLiving.world.isRemote)
			{
				ItemCrossbow.setLoaded(stack, false);
			}

			ObfuscationReflectionHelper.setPrivateValue(EntityLivingBase.class, entityLiving, 0, ReflectionAether.ACTIVE_ITEMSTACK_USE_COUNT.getMappings());

			return true;
		}

		return false;
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(final World worldIn, final EntityPlayer playerIn, final EnumHand hand)
	{
		final ItemStack stack = playerIn.getHeldItem(hand);

		if (!ItemCrossbow.isLoaded(stack))
		{
			if (this.hasAmmo(playerIn))
			{
				playerIn.setActiveHand(EnumHand.MAIN_HAND);
			}
		}

		return new ActionResult<>(EnumActionResult.PASS, stack);
	}

	private void shootBolt(final EntityLivingBase entityLiving, final ItemStack stack)
	{
		if (!entityLiving.world.isRemote)
		{
			final World world = entityLiving.world;

			world.playSound(null, entityLiving.posX, entityLiving.posY, entityLiving.posZ, SoundEvents.ENTITY_ARROW_SHOOT, SoundCategory.NEUTRAL, 1.0F,
					1.0F / (itemRand.nextFloat() * 0.4F + 1.2F));

			final float speed = 1.0f;

			final ItemBoltType boltType = ItemCrossbow.getLoadedBoltType(stack);

			EntityBolt bolt0 = null,
					bolt1 = null,
					bolt2 =  null;

			// calculate bolts that are special is being loaded.
			if (this.isSpecialLoaded)
			{
				if (this.cBType == crossBowTypes.SKYROOT)
				{
					bolt0 = createBolt(entityLiving, speed, 0, 0, 0, this.cBType.damageMultiplier);
					bolt1 = createBolt(entityLiving, speed, 0, 0, 0, this.cBType.damageMultiplier);
				}
				else if (this.cBType == crossBowTypes.HOLYSTONE)
				{
					bolt0 = createBolt(entityLiving, speed / 2, 0, 0, 0, this.cBType.damageMultiplier);
					bolt1 = createBolt(entityLiving, speed / 2, 10, 0, 0, this.cBType.damageMultiplier);
					bolt2 = createBolt(entityLiving, speed / 2, -10, 0, 0, this.cBType.damageMultiplier);
				}
				else if (this.cBType == crossBowTypes.ZANITE)
				{
					bolt0 = createBolt(entityLiving, speed, 0,0,0, 1.5F);
				}
				else if (this.cBType == crossBowTypes.ARKENIUM)
				{
					bolt0 = createBolt(entityLiving, speed * 2.5f, 0, -1, 0, 2.0f);
				}
				else if (this.cBType == crossBowTypes.GRAVETITE)
				{

				}
				else
				{
					bolt0 = createBolt(entityLiving, speed, 0, 0, -0.5f, this.cBType.damageMultiplier);
				}
			}
			else
			{
				bolt0 = createBolt(entityLiving, speed, 0,0,0, this.cBType.damageMultiplier);
			}

			if (entityLiving instanceof EntityPlayer)
			{
				final EntityPlayer player = (EntityPlayer) entityLiving;

				if (player.capabilities.isCreativeMode)
				{
					if (bolt0 != null)
					{
						bolt0.pickupStatus = EntityArrow.PickupStatus.CREATIVE_ONLY;
					}
					if (bolt1 != null)
					{
						bolt1.pickupStatus = EntityArrow.PickupStatus.CREATIVE_ONLY;
					}
					if (bolt2 != null)
					{
						bolt2.pickupStatus = EntityArrow.PickupStatus.CREATIVE_ONLY;
					}
				}
				else
				{
					if (this.getDamage(stack) >= this.getMaxDamage(stack))
					{
						player.inventory.deleteStack(stack);
						world.playSound(null, entityLiving.posX, entityLiving.posY, entityLiving.posZ, SoundEvents.ENTITY_ITEM_BREAK, SoundCategory.PLAYERS, 1.0F,
								1.0F / (itemRand.nextFloat() * 0.4F + 1.2F));
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
				entityLiving.getEntityWorld().spawnEntity(bolt0);
			}

			if (bolt1 != null)
			{
				entityLiving.getEntityWorld().spawnEntity(bolt1);
			}

			if (bolt2 != null)
			{
				entityLiving.getEntityWorld().spawnEntity(bolt2);
			}

			this.isSpecialLoaded = false;
		}
	}

	private EntityBolt createBolt(EntityLivingBase entityLiving, float speed, float addRotationYaw, float addRotationPitch, float addInaccuracy, float damageMultiplier)
	{
		if (!entityLiving.world.isRemote)
		{
			final EntityBolt bolt = new EntityBolt(entityLiving.getEntityWorld(), entityLiving);
			bolt.shoot(entityLiving, entityLiving.rotationPitch + addRotationPitch, entityLiving.rotationYaw + addRotationYaw,
					0.0F, speed * 2.0F, 1.0F + addInaccuracy);
			bolt.setBoltAbility(BoltAbility.NORMAL);
			bolt.setDamage(bolt.getDamage() * damageMultiplier);
			bolt.pickupStatus = EntityArrow.PickupStatus.ALLOWED;

			return bolt;
		}

		return null;
	}

	@Override
	public void onUsingTick(final ItemStack stack, final EntityLivingBase living, final int count)
	{
		if (living instanceof EntityPlayer)
		{
			final EntityPlayer player = (EntityPlayer) living;
			final ItemStack boltStack = player.getItemStackFromSlot(EntityEquipmentSlot.OFFHAND);

			if (this.hasAmmo(player))
			{
				final float use = (float) (this.getMaxItemUseDuration(stack) - living.getItemInUseCount()) / 20.0F;
				if (use == (this.durationInTicks / 20.0F))
				{
					if (!living.getEntityWorld().isRemote)
					{
						ItemCrossbow.setLoadedBoltType(stack, ItemCrossbow.BOLT_TYPES[boltStack.getItemDamage()]);
						ItemCrossbow.setLoaded(stack, true);

						this.isSpecialLoaded = player.isSneaking();

						if (!player.capabilities.isCreativeMode)
						{
							int shrinkQuantity = 1;
							if (isSpecialLoaded)
							{
								if (this.cBType == crossBowTypes.HOLYSTONE)
								{
									shrinkQuantity = 3;
								}
								if (this.cBType == crossBowTypes.SKYROOT)
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
	public void onPlayerStoppedUsing(final ItemStack stack, final World worldIn, final EntityLivingBase entityLiving, final int timeLeft)
	{

	}

	@Override
	public int getMaxItemUseDuration(final ItemStack stack)
	{
		return 72000;
	}

	@Override
	public boolean getShareTag()
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
	@SideOnly(Side.CLIENT)
	public void addInformation(final ItemStack stack, final World world, final List<String> tooltip, final ITooltipFlag flag)
	{
		final float seconds = this.durationInTicks / 20.0F;

		tooltip.add(TextFormatting.BLUE + I18n.format("item.aether.crossbow.desc1"));

		if (seconds == Math.floor(seconds))
		{
			tooltip.add(TextFormatting.GRAY + "\u2022 " + String.valueOf((int) Math.floor(seconds)) + " " + I18n.format(
					"item.aether.crossbow.desc" + (seconds < 1 || seconds > 1 ? "2" : "3")));
		}
		else
		{
			tooltip.add(TextFormatting.GRAY + "\u2022 " + String.valueOf(seconds) + " " + I18n.format(
					"item.aether.crossbow.desc" + (seconds < 1 || seconds > 1 ? "2" : "3")));
		}

		tooltip.add(TextFormatting.BLUE + I18n.format("item.aether.crossbow.desc4"));
		tooltip.add(TextFormatting.GRAY + "\u2022 " + I18n.format("item.aether." + this.cBType.name + ".ability"));
	}

	@Override
	public int getItemBurnTime(ItemStack itemStack)
	{
		return 100;
	}

	public crossBowTypes getType()
	{
		return this.cBType;
	}

	public ItemCrossbow setType(crossBowTypes type)
	{
		this.cBType = type;
		this.setMaxDamage(cBType.maxDurability);
		return this;
	}
}
