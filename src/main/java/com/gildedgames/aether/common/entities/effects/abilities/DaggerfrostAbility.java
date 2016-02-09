package com.gildedgames.aether.common.entities.effects.abilities;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemSnowball;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.Action;

import com.gildedgames.aether.common.entities.effects.EntityEffect;
import com.gildedgames.aether.common.entities.effects.PlayerAbility;
import com.gildedgames.aether.common.entities.projectiles.EntityDaggerfrostSnowball;

public class DaggerfrostAbility implements PlayerAbility
{

	@Override
	public String getUnlocalizedName(EntityPlayer source, EntityEffect<EntityPlayer> instance, NBTTagCompound attributes)
	{
		return "ability.daggerfrost.name";
	}

	@Override
	public String[] getUnlocalizedDesc(EntityPlayer source, EntityEffect<EntityPlayer> instance, NBTTagCompound attributes)
	{
		return new String[] { "ability.daggerfrost.desc" };
	}
	
	@Override
	public void formatLocalizedDesc(List<String> localizedDesc, EntityPlayer source, EntityEffect<EntityPlayer> instance, NBTTagCompound attributes)
	{
		
	}
	
	@Override
	public void apply(EntityPlayer source, EntityEffect<EntityPlayer> holder, NBTTagCompound attributes) {}

	@Override
	public void onInteract(PlayerInteractEvent event, EntityPlayer source, EntityEffect<EntityPlayer> holder, NBTTagCompound attributes)
	{
		World world = source.worldObj;
		ItemStack currentStack = source.getCurrentEquippedItem();
		
		if (currentStack != null && currentStack.getItem() instanceof ItemSnowball)
		{
			if (event.action == Action.RIGHT_CLICK_AIR || event.action == Action.RIGHT_CLICK_BLOCK)
			{
		        if (!world.isRemote)
		        {
		            world.spawnEntityInWorld(new EntityDaggerfrostSnowball(world, source));
		            
		            event.setCanceled(true);
		            
		            if (!source.capabilities.isCreativeMode)
					{
		            	--currentStack.stackSize;
					}
		        }
			}
		}
	}

	@Override
	public void initAttributes(EntityPlayer source, NBTTagCompound attributes) {}

	@Override
	public void tick(EntityPlayer source, EntityEffect<EntityPlayer> holder, NBTTagCompound attributes) {}
	
	@Override
	public void cancel(EntityPlayer source, EntityEffect<EntityPlayer> holder, NBTTagCompound attributes) {}

	@Override
	public void onKill(LivingDropsEvent event, EntityPlayer source, EntityEffect<EntityPlayer> holder, NBTTagCompound attributes) {}

	@Override
	public void onAttack(LivingHurtEvent event, EntityPlayer source, EntityEffect<EntityPlayer> holder, NBTTagCompound attributes) {}

}
