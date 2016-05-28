package com.gildedgames.aether.api.entities.effects;

import net.minecraft.entity.Entity;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerPickupXpEvent;

import java.util.List;

public interface IEffectPool<I extends EntityEffectInstance>
{
	EntityEffectProcessor<I> getProcessor();

	List<I> getInstances();

	<S extends Entity> void tick(S entity);

	<S extends Entity> void onKill(LivingDropsEvent event, S entity);

	<S extends Entity> void onPlayerInteract(PlayerInteractEvent event, S entity);

	<S extends Entity> void onPickupXP(PlayerPickupXpEvent event, S entity);

	<S extends Entity> void onLivingAttack(LivingHurtEvent event, S entity);
}
