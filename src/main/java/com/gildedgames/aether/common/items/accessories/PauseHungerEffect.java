package com.gildedgames.aether.common.items.accessories;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.FoodStats;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

import com.gildedgames.aether.common.player.PlayerAether;

public class PauseHungerEffect implements AccessoryEffect
{
	
	private NBTTagCompound data(ItemStack stack)
	{
		if (!stack.getTagCompound().hasKey("pauseHungerEffect"))
		{
			stack.getTagCompound().setTag("pauseHungerEffect", new NBTTagCompound());
		}
		
		return stack.getTagCompound().getCompoundTag("pauseHungerEffect");
	}
	
	private int foodLevel(ItemStack stack)
	{
		return this.data(stack).getInteger("foodLevel");
	}
	
	private float foodSaturation(ItemStack stack)
	{
		return this.data(stack).getFloat("foodSaturation");
	}

	@Override
	public void onEquipped(PlayerAether aePlayer, ItemStack stack, AccessoryType type)
	{
		EntityPlayer player = aePlayer.getPlayer();
		
		this.data(stack).setInteger("foodLevel", player.getFoodStats().getFoodLevel());
		this.data(stack).setFloat("foodSaturation", player.getFoodStats().getSaturationLevel());
	}

	@Override
	public void onUnequipped(PlayerAether aePlayer, ItemStack stack, AccessoryType type)
	{
		this.data(stack).setInteger("foodLevel", 0);
		this.data(stack).setFloat("foodSaturation", 0);
	}

	@Override
	public void onUpdate(PlayerAether aePlayer, ItemStack stack, AccessoryType type)
	{
		EntityPlayer player = aePlayer.getPlayer();
		World world = player.worldObj;
		
		if (!world.isRemote)
		{
			if (player.getFoodStats().getFoodLevel() > this.foodLevel(stack) || player.getFoodStats().getSaturationLevel() > this.foodSaturation(stack))
			{
				this.data(stack).setInteger("foodLevel", player.getFoodStats().getFoodLevel());
				this.data(stack).setFloat("foodSaturation", player.getFoodStats().getSaturationLevel());
			}
			
			ObfuscationReflectionHelper.setPrivateValue(FoodStats.class, player.getFoodStats(), this.foodLevel(stack), "field_75127_a", "foodLevel");
			ObfuscationReflectionHelper.setPrivateValue(FoodStats.class, player.getFoodStats(), this.foodSaturation(stack), "field_75125_b", "foodSaturationLevel");
		}
	}

	@Override
	public void onInteract(PlayerInteractEvent event, PlayerAether aePlayer, ItemStack stack, AccessoryType type)
	{
		
	}

	@Override
	public void onKillEntity(LivingDropsEvent event, EntityLivingBase killedEntity, PlayerAether aePlayer, ItemStack stack, AccessoryType type)
	{
		
	}
	
	@Override
	public void onAttackEntity(LivingHurtEvent event, PlayerAether aePlayer, ItemStack stack, AccessoryType type)
	{
		
	}
	
}
