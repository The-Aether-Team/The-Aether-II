package com.gildedgames.aether.common.entities;

import com.gildedgames.aether.api.capabilites.entity.boss.IBoss;
import com.gildedgames.aether.api.capabilites.entity.boss.IBossManager;
import com.gildedgames.aether.api.capabilites.entity.effects.IEntityEffectsCapability;
import com.gildedgames.aether.common.capabilities.entity.effects.EntityEffects;
import com.gildedgames.aether.common.capabilities.player.PlayerAetherImpl;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class BossProcessor
{

	@SubscribeEvent
	public static void onLivingEntityUpdate(LivingEvent.LivingUpdateEvent event)
	{
		if (event.getEntity() instanceof IBoss)
		{
			IBoss boss = (IBoss)event.getEntity();
			IBossManager manager = boss.getBossManager();

			manager.updateStagesAndActions();
		}
	}

	@SubscribeEvent
	public static void onLivingAttack(LivingAttackEvent event)
	{
		if (event.getEntity() instanceof IBoss && event.getSource().getSourceOfDamage() instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer)event.getSource().getSourceOfDamage();
			IBoss<?> boss = (IBoss)event.getEntity();

			PlayerAetherImpl playerAether = PlayerAetherImpl.getPlayer(player);

			playerAether.getBossModule().setCurrentBoss(boss);
		}
	}

}
