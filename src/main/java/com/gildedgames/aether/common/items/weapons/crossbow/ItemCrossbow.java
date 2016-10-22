package com.gildedgames.aether.common.items.weapons.crossbow;

import com.gildedgames.aether.common.ReflectionAether;
import com.gildedgames.aether.common.entities.projectiles.EntityBolt;
import com.gildedgames.aether.common.entities.projectiles.EntityBolt.BoltAbility;
import com.gildedgames.aether.common.items.ItemsAether;
import com.gildedgames.aether.common.registry.minecraft.CreativeTabsAether;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumAction;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

import static sun.audio.AudioPlayer.player;

public class ItemCrossbow extends Item
{

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

					return itemstack != null && itemstack.getItem() == ItemCrossbow.this ? ((float)(stack.getMaxItemUseDuration() - entityIn.getItemInUseCount()) / ItemCrossbow.this.durationInTicks) : 0.0F;
				}
			}
		});

		this.addPropertyOverride(new ResourceLocation("pulling"), new IItemPropertyGetter()
		{
			@SideOnly(Side.CLIENT)
			public float apply(ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase entityIn)
			{
				return entityIn != null && (entityIn.isHandActive() && entityIn.getActiveItemStack() == stack) || ItemCrossbow.isLoaded(stack) ? 1.0F : 0.0F;
			}
		});
	}

	private boolean hasAmmo(EntityPlayer player)
	{
		ItemStack boltStack = player.getItemStackFromSlot(EntityEquipmentSlot.OFFHAND);

		if (boltStack != null && boltStack.getItem() == ItemsAether.bolt && boltStack.stackSize > 0)
		{
			return true;
		}

		return false;
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
		if (stack == null) return false;

		checkTag(stack);

		return stack.getTagCompound().getBoolean("loaded");
	}

	public static void setLoaded(ItemStack stack, boolean loaded)
	{
		if (stack == null) return;

		checkTag(stack);

		stack.getTagCompound().setBoolean("loaded", loaded);
	}

	public static boolean isReadyToShoot(ItemStack stack)
	{
		if (stack == null) return false;

		checkTag(stack);

		return stack.getTagCompound().getBoolean("readyToShoot");
	}

	public static void setReadyToShoot(ItemStack stack, boolean readyToShoot)
	{
		if (stack == null) return;

		checkTag(stack);

		stack.getTagCompound().setBoolean("readyToShoot", readyToShoot);
	}

	@Override
	public boolean onEntitySwing(EntityLivingBase entityLiving, ItemStack stack)
	{
		if (ItemCrossbow.isReadyToShoot(stack))
		{
			if (entityLiving.worldObj.isRemote)
			{
				ItemRenderer renderer = Minecraft.getMinecraft().getItemRenderer();

				ObfuscationReflectionHelper.setPrivateValue(ItemRenderer.class, renderer, 1.5F, ReflectionAether.EQUIPPED_PROGRESS_MAIN_HAND.getMappings());
			}

			this.shootBolt(entityLiving, stack);

			return true;
		}

		return false;
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(ItemStack stack, World worldIn, EntityPlayer playerIn, EnumHand hand)
	{
		if (!ItemCrossbow.isLoaded(stack) || !ItemCrossbow.isReadyToShoot(stack))
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
		if (ItemCrossbow.isLoaded(stack) && !entityLiving.worldObj.isRemote)
		{
			float speed = 1.0f;

			EntityBolt dart = new EntityBolt(entityLiving.getEntityWorld(), entityLiving);
			dart.setAim(entityLiving, entityLiving.rotationPitch, entityLiving.rotationYaw, 0.0F, speed * 2.0F, 1.0F);
			dart.setBoltAbility(stack.getItem() == ItemsAether.arkenium_crossbow ? BoltAbility.DESTROY_BLOCKS : BoltAbility.NORMAL);
			dart.setBoltType(ItemBoltType.values()[stack.getItemDamage()]);
			dart.pickupStatus = EntityArrow.PickupStatus.ALLOWED;

			entityLiving.getEntityWorld().spawnEntityInWorld(dart);

			ItemCrossbow.setLoaded(stack, false);
			ItemCrossbow.setReadyToShoot(stack, false);
		}
	}

	@Override
	public void onUsingTick(ItemStack stack, EntityLivingBase player, int count)
	{
		if (player instanceof EntityPlayer)
		{
			EntityPlayer entityPlayer = (EntityPlayer) player;
			ItemStack boltStack = entityPlayer.getItemStackFromSlot(EntityEquipmentSlot.OFFHAND);

			if (this.hasAmmo(entityPlayer))
			{
				float use = (float)(stack.getMaxItemUseDuration() - player.getItemInUseCount()) / 20.0F;

				if (use == (this.durationInTicks / 20.0F))
				{
					boltStack.stackSize--;
				}
			}
		}
	}

	@Override
	public void onPlayerStoppedUsing(ItemStack stack, World worldIn, EntityLivingBase entityLiving, int timeLeft)
	{
		float use = (float)(stack.getMaxItemUseDuration() - entityLiving.getItemInUseCount()) / 20.0F;

		if (use >= (this.durationInTicks / 20.0F))
		{
			ItemCrossbow.setLoaded(stack, true);
		}

		if (ItemCrossbow.isLoaded(stack))
		{
			ItemCrossbow.setReadyToShoot(stack, true);
		}
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

	public float getKnockBackValue() { return this.knockBackValue; }

	public ItemCrossbow setKnockBackValue(float x)
	{
		this.knockBackValue = x;

		return this;
	}

	public float getDurationInTicks() { return this.durationInTicks; }

	public ItemCrossbow setDurationInTicks(int x)
	{
		this.durationInTicks = x;

		return this;
	}

}
