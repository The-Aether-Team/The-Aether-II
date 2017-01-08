package com.gildedgames.aether.common.items.weapons.crossbow;

import com.gildedgames.aether.common.ReflectionAether;
import com.gildedgames.aether.common.entities.projectiles.EntityBolt;
import com.gildedgames.aether.common.entities.projectiles.EntityBolt.BoltAbility;
import com.gildedgames.aether.common.items.ItemsAether;
import com.gildedgames.aether.common.registry.content.CreativeTabsAether;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.resources.I18n;
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

	public static final ItemBoltType[] BOLT_TYPES = ItemBoltType.values();

	private float durationInTicks;

	private float knockBackValue;

	public ItemCrossbow()
	{
		this.maxStackSize = 1;

		this.knockBackValue = 0;
		this.durationInTicks = 20.0F;

		this.setCreativeTab(CreativeTabsAether.WEAPONS);

		this.addPropertyOverride(new ResourceLocation("pull"), new IItemPropertyGetter()
		{
			@SideOnly(Side.CLIENT)
			public float apply(ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase entityIn)
			{
				if (entityIn == null)
				{
					return 0.0F;
				}
				else
				{
					ItemStack itemstack = entityIn.getActiveItemStack();

					if (ItemCrossbow.isLoaded(stack))
					{
						return 1.0F;
					}

					return itemstack != null && itemstack.getItem() == ItemCrossbow.this ?
							((float) (stack.getMaxItemUseDuration() - entityIn.getItemInUseCount()) / ItemCrossbow.this.durationInTicks) :
							0.0F;
				}
			}
		});

		this.addPropertyOverride(new ResourceLocation("pulling"), new IItemPropertyGetter()
		{
			@SideOnly(Side.CLIENT)
			public float apply(ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase entityIn)
			{
				return entityIn != null && (entityIn.isHandActive() && entityIn.getActiveItemStack() == stack)
						|| ItemCrossbow.isLoaded(stack) ? 1.0F : 0.0F;
			}
		});
	}

	private boolean hasAmmo(EntityPlayer player)
	{
		ItemStack boltStack = player.getItemStackFromSlot(EntityEquipmentSlot.OFFHAND);

		return boltStack != null && boltStack.getItem() == ItemsAether.bolt && boltStack.stackSize > 0;
	}

	private static void checkTag(ItemStack stack)
	{
		if (stack.getTagCompound() == null)
		{
			stack.setTagCompound(new NBTTagCompound());
		}
	}

	public static boolean isLoaded(ItemStack stack)
	{
		if (stack == null)
		{
			return false;
		}

		checkTag(stack);

		return stack.getTagCompound().getBoolean("loaded");
	}

	public static void setLoaded(ItemStack stack, boolean loaded)
	{
		if (stack == null)
		{
			return;
		}

		checkTag(stack);

		stack.getTagCompound().setBoolean("loaded", loaded);
	}

	public static ItemBoltType getLoadedBoltType(ItemStack stack)
	{
		if (stack == null)
		{
			return null;
		}

		checkTag(stack);

		return ItemCrossbow.BOLT_TYPES[stack.getTagCompound().getInteger("boltType")];
	}

	public static void setLoadedBoltType(ItemStack stack, ItemBoltType type)
	{
		if (stack == null)
		{
			return;
		}

		checkTag(stack);

		stack.getTagCompound().setInteger("boltType", type.ordinal());
	}

	@Override
	public boolean onEntitySwing(EntityLivingBase entityLiving, ItemStack stack)
	{
		if (ItemCrossbow.isLoaded(stack))
		{
			if (entityLiving.worldObj.isRemote)
			{
				ItemRenderer renderer = Minecraft.getMinecraft().getItemRenderer();

				ObfuscationReflectionHelper.setPrivateValue(ItemRenderer.class, renderer, 1.5F, ReflectionAether.EQUIPPED_PROGRESS_MAIN_HAND.getMappings());
			}

			this.shootBolt(entityLiving, stack);

			if (!entityLiving.worldObj.isRemote)
			{
				ItemCrossbow.setLoaded(stack, false);
			}

			ObfuscationReflectionHelper.setPrivateValue(EntityLivingBase.class, entityLiving, 0, ReflectionAether.ACTIVE_ITEMSTACK_USE_COUNT.getMappings());

			return true;
		}

		return false;
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(ItemStack stack, World worldIn, EntityPlayer playerIn, EnumHand hand)
	{
		if (!ItemCrossbow.isLoaded(stack))
		{
			if (this.hasAmmo(playerIn))
			{
				playerIn.setActiveHand(EnumHand.MAIN_HAND);
			}
		}

		return new ActionResult<>(EnumActionResult.PASS, stack);
	}

	private void shootBolt(EntityLivingBase entityLiving, ItemStack stack)
	{
		if (!entityLiving.worldObj.isRemote)
		{
			World world = entityLiving.worldObj;

			world.playSound(null, entityLiving.posX, entityLiving.posY, entityLiving.posZ, SoundEvents.ENTITY_ARROW_SHOOT, SoundCategory.NEUTRAL, 1.0F,
					1.0F / (itemRand.nextFloat() * 0.4F + 1.2F));

			float speed = 1.0f;

			ItemBoltType boltType = ItemCrossbow.getLoadedBoltType(stack);

			EntityBolt dart = new EntityBolt(entityLiving.getEntityWorld(), entityLiving);
			dart.setAim(entityLiving, entityLiving.rotationPitch, entityLiving.rotationYaw, 0.0F, speed * 2.0F, 1.0F);
			dart.setBoltAbility(BoltAbility.NORMAL);
			dart.setBoltType(boltType);
			dart.pickupStatus = EntityArrow.PickupStatus.ALLOWED;

			if (entityLiving instanceof EntityPlayer)
			{
				EntityPlayer player = (EntityPlayer) entityLiving;

				if (player.capabilities.isCreativeMode)
				{
					dart.pickupStatus = EntityArrow.PickupStatus.CREATIVE_ONLY;
				}
			}

			entityLiving.getEntityWorld().spawnEntityInWorld(dart);
		}
	}

	@Override
	public void onUsingTick(ItemStack stack, EntityLivingBase living, int count)
	{
		if (living instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer) living;
			ItemStack boltStack = player.getItemStackFromSlot(EntityEquipmentSlot.OFFHAND);

			if (this.hasAmmo(player))
			{
				float use = (float) (stack.getMaxItemUseDuration() - living.getItemInUseCount()) / 20.0F;

				if (use == (this.durationInTicks / 20.0F))
				{
					if (!living.getEntityWorld().isRemote)
					{
						ItemCrossbow.setLoadedBoltType(stack, ItemCrossbow.BOLT_TYPES[boltStack.getItemDamage()]);
						ItemCrossbow.setLoaded(stack, true);

						if (!player.capabilities.isCreativeMode)
						{
							boltStack.stackSize--;

							if (boltStack.stackSize == 0)
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
	public void onPlayerStoppedUsing(ItemStack stack, World worldIn, EntityLivingBase entityLiving, int timeLeft)
	{

	}

	@Override
	public int getMaxItemUseDuration(ItemStack stack)
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

	public ItemCrossbow setKnockBackValue(float x)
	{
		this.knockBackValue = x;

		return this;
	}

	public float getDurationInTicks()
	{
		return this.durationInTicks;
	}

	public ItemCrossbow setDurationInTicks(int x)
	{
		this.durationInTicks = x;

		return this;
	}

	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List<String> tooltip, boolean adv)
	{
		float seconds = this.durationInTicks / 20.0F;

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
	}

}
