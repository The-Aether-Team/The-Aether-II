package com.gildedgames.aether.common.entities.effects.processors.player;

import com.gildedgames.aether.api.capabilites.entity.effects.EntityEffectInstance;
import com.gildedgames.aether.common.entities.effects.AbstractEffectProcessorPlayer;
import com.gildedgames.aether.common.entities.effects.EffectProcessorPlayer;
import com.gildedgames.aether.common.entities.effects.processors.ModifyDamageEffect;
import com.gildedgames.aether.common.entities.projectiles.EntityDaggerfrostSnowball;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemSnowball;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerPickupXpEvent;

import java.util.List;

public class DaggerfrostEffect extends AbstractEffectProcessorPlayer<EntityEffectInstance>
{

	public DaggerfrostEffect()
	{
		super("ability.daggerfrost.localizedName", "ability.daggerfrost.desc");
	}

	@Override
	public void onInteract(PlayerInteractEvent event, EntityPlayer source, List<EntityEffectInstance> instances)
	{
		World world = source.worldObj;

		ItemStack currentStack = source.getHeldItem(EnumHand.MAIN_HAND);

		if (currentStack != null && currentStack.getItem() instanceof ItemSnowball)
		{
			if (!world.isRemote)
			{
				EntityDaggerfrostSnowball snowball = new EntityDaggerfrostSnowball(world, source);
				snowball.setHeadingFromThrower(source, source.rotationPitch, source.rotationYaw, 0.0F, 1.5F, 1.0F);

				world.spawnEntityInWorld(snowball);

				event.setCanceled(true);

				if (!source.capabilities.isCreativeMode)
				{
					--currentStack.stackSize;
				}
			}
		}
	}

}
