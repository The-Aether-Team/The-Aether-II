package com.gildedgames.aether.common.items.weapons.crossbow;

import com.gildedgames.aether.common.entities.projectiles.EntityBolt;
import com.gildedgames.aether.common.entities.projectiles.EntityBolt.BoltAbility;
import com.gildedgames.aether.common.items.ItemsAether;

import com.gildedgames.aether.common.player.PlayerAether;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentKnockback;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemCrossbow extends Item
{

	public static final String[] crossbowIconNameArray = new String[] {"_fired", "_loaded", "_loading1", "_loading2"};

	private final int DURATION = 180;

	// How far a mob gets knock-backed by attacking with a crossbow.
	private float knockBackValue;

	// How much damage is done to an entity attacked by the crossbow.
	private float damageValue;

	public ItemCrossbow()
	{
		this.maxStackSize = 1;
		knockBackValue = 0;
		damageValue = 0;
	}

	// Should be adjusted to check for quiver accessory slot, currently checks the first space in players inventory for ammo.
	private int getBoltType(EntityPlayer player)
	{
		ItemStack boltStack = player.getItemStackFromSlot(EntityEquipmentSlot.OFFHAND);

		if (this.hasAmmo(player))
			return boltStack.getMetadata();

		return -1;
	}

	private boolean hasAmmo(EntityPlayer player)
	{
		//ItemStack boltStack = player.getEquipmentInventory().getStackInSlot(5);
		ItemStack boltStack = player.getItemStackFromSlot(EntityEquipmentSlot.OFFHAND);

		if (boltStack != null && boltStack.getItem() == ItemsAether.bolt && boltStack.stackSize > 0)
		{
			return true;
		}

		return false;
	}

	public float getKnockBackValue() { return knockBackValue; }

	public void setKnockBackValue(float x)  { knockBackValue = x; }

	public float getDamageValue() { return damageValue; }

	public void setDamageValue(float x) { damageValue = x; }

	@Override
	public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected)
	{
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn, EnumHand hand)
	{
		if (itemStackIn.getMetadata() < 3 || itemStackIn.getMetadata() == 4)
		{
			if (this.hasAmmo(playerIn))
			{
				playerIn.setActiveHand(EnumHand.MAIN_HAND);
			}
		}

		return new ActionResult<>(EnumActionResult.PASS, itemStackIn);
	}

	@Override
	public boolean onEntitySwing(EntityLivingBase entityLiving, ItemStack stack)
	{
		if (entityLiving.worldObj.isRemote)
		{
			return true;
		}
		
		float speed = 1.0f;

		EntityBolt dart = new EntityBolt(entityLiving.getEntityWorld(), entityLiving);
		dart.setAim(entityLiving, entityLiving.rotationPitch, entityLiving.rotationYaw, 0.0F, speed * 2.0F, 1.0F);
		dart.setBoltAbility(stack.getItem() instanceof ItemArkeniumCrossbow ? BoltAbility.DESTROY_BLOCKS : BoltAbility.NORMAL);
		dart.pickupStatus = EntityArrow.PickupStatus.ALLOWED;
		
		if (stack.getItemDamage() > 0)
		{
			entityLiving.getEntityWorld().spawnEntityInWorld(dart);
			stack.setItemDamage(stack.getItemDamage()-1);
			return true;
		}

		return false;
	}

	@Override
	public void onUsingTick(ItemStack stack, EntityLivingBase player, int count)
	{
		if (player instanceof EntityPlayer) {
			EntityPlayer entityPlayer = (EntityPlayer) player;
			ItemStack boltStack = entityPlayer.getItemStackFromSlot(EntityEquipmentSlot.OFFHAND);

			if (stack.getItemDamage() == 4)
			{
				stack.setItemDamage(0);
				player.stopActiveHand();
			}

			if (this.hasAmmo(entityPlayer))
			{
				if (count == this.DURATION -10 && stack.getItemDamage() == 0)
				{
					stack.setItemDamage(1);
					boltStack.stackSize -= 1;
				}
				if (count == (this.DURATION - (this.DURATION / 3)) && stack.getItemDamage() == 1)
				{
					stack.setItemDamage(2);
					boltStack.stackSize -= 1;
				}
				if (count <= 20 && stack.getItemDamage() == 2)
				{
					stack.setItemDamage(3);
					boltStack.stackSize -=1;
				}
			}
			else
			{
				player.stopActiveHand();
			}
		}
	}

	@Override
	public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityLivingBase playerIn)
	{
		stack.setItemDamage(3);

		return stack;
	}

	@Override
	public int getMaxItemUseDuration(ItemStack stack)
	{
		return this.DURATION; //80~4 seconds
	}

	@Override
	public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity)
	{
		if (entity != null)
		{
			EntityLivingBase entityBase = (EntityLivingBase) entity;
			DamageSource source = DamageSource.causePlayerDamage(player);

			entityBase.attackEntityFrom(source, damageValue);

			if (source.getSourceOfDamage() instanceof EntityLivingBase)
			{
				entityBase.knockBack(entity, knockBackValue, -(entity.posX - source.getSourceOfDamage().posX), -(entity.posZ - source.getSourceOfDamage().posZ));
			}
		}
		return true;
	}
}
