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

public class WeightToleranceEffect extends AbstractEffectProcessorPlayer<EntityEffectInstance>
{

	public WeightToleranceEffect()
	{
		super("ability.weightTolerance.localizedName", "ability.weightTolerance.desc");
	}

}
