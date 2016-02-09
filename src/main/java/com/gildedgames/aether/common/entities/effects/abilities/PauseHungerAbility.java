package com.gildedgames.aether.common.entities.effects.abilities;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.FoodStats;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

import com.gildedgames.aether.common.entities.effects.EntityEffect;
import com.gildedgames.aether.common.entities.effects.PlayerAbility;

public class PauseHungerAbility implements PlayerAbility
{
	
	@Override
	public String getUnlocalizedName(EntityPlayer source, EntityEffect<EntityPlayer> instance, NBTTagCompound attributes)
	{
		return "ability.pauseHunger.name";
	}

	@Override
	public String[] getUnlocalizedDesc(EntityPlayer source, EntityEffect<EntityPlayer> instance, NBTTagCompound attributes)
	{
		return new String[] { "ability.pauseHunger.desc" };
	}
	
	@Override
	public void initAttributes(EntityPlayer source, NBTTagCompound attributes)
	{
		attributes.setInteger("foodLevel", 0);
		attributes.setFloat("foodSaturation", 0);
	}

	@Override
	public void apply(EntityPlayer source, EntityEffect<EntityPlayer> holder, NBTTagCompound attributes)
	{
		attributes.setInteger("foodLevel", source.getFoodStats().getFoodLevel());
		attributes.setFloat("foodSaturation", source.getFoodStats().getSaturationLevel());
	}

	@Override
	public void tick(EntityPlayer source, EntityEffect<EntityPlayer> holder, NBTTagCompound attributes)
	{
		World world = source.worldObj;
		
		if (!world.isRemote)
		{
			if (source.getFoodStats().getFoodLevel() > attributes.getInteger("foodLevel") || source.getFoodStats().getSaturationLevel() > attributes.getFloat("foodSaturation"))
			{
				attributes.setInteger("foodLevel", source.getFoodStats().getFoodLevel());
				attributes.setFloat("foodSaturation", source.getFoodStats().getSaturationLevel());
			}
			
			ObfuscationReflectionHelper.setPrivateValue(FoodStats.class, source.getFoodStats(), attributes.getInteger("foodLevel"), "field_75127_a", "foodLevel");
			ObfuscationReflectionHelper.setPrivateValue(FoodStats.class, source.getFoodStats(), attributes.getFloat("foodSaturation"), "field_75125_b", "foodSaturationLevel");
		}
	}

	@Override
	public void cancel(EntityPlayer source, EntityEffect<EntityPlayer> holder, NBTTagCompound attributes)
	{
		attributes.setInteger("foodLevel", 0);
		attributes.setFloat("foodSaturation", 0);
	}

	@Override
	public void onInteract(PlayerInteractEvent event, EntityPlayer source, EntityEffect<EntityPlayer> holder, NBTTagCompound attributes) {}

	@Override
	public void onKill(LivingDropsEvent event, EntityPlayer source, EntityEffect<EntityPlayer> holder, NBTTagCompound attributes) {}

	@Override
	public void onAttack(LivingHurtEvent event, EntityPlayer source, EntityEffect<EntityPlayer> holder, NBTTagCompound attributes) {}

	@Override
	public void formatLocalizedDesc(List<String> localizedDesc, EntityPlayer source, EntityEffect<EntityPlayer> instance, NBTTagCompound attributes) {}

}
