package com.gildedgames.aether.common.entities;

import com.gildedgames.aether.api.capabilites.entity.boss.IBoss;
import com.gildedgames.aether.api.capabilites.entity.boss.IBossManager;
import com.gildedgames.aether.api.capabilites.items.properties.ItemRarity;
import com.gildedgames.aether.api.loot.LootGenerator;
import com.gildedgames.aether.api.loot.LootPool;
import com.gildedgames.aether.common.capabilities.player.PlayerAetherImpl;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Random;

public class BossProcessor
{

	@SubscribeEvent
	public static void onDeath(LivingDeathEvent event)
	{
		if (event.getEntity() instanceof IBoss)
		{
			IBoss boss = (IBoss)event.getEntity();
			LootPool lootPool = boss.getLootPool();

			if (lootPool != null)
			{
				BlockPos pos = new BlockPos(event.getEntity().posX, event.getEntity().posY, event.getEntity().posZ);

				Random random = event.getEntity().getEntityWorld().rand;

				for (int i = 0; i < 6; i++)
				{
					ItemRarity rarity = ItemRarity.COMMON;

					if (random.nextInt(5) == 0)
					{
						rarity = ItemRarity.RARE;

						if (random.nextInt(10) == 0)
						{
							rarity = ItemRarity.EPIC;

							if (random.nextInt(20) == 0)
							{
								rarity = ItemRarity.MYTHIC;
							}
						}
					}

					ItemStack stack = LootGenerator.generate(lootPool, rarity, event.getEntity().worldObj.rand);

					Block.spawnAsEntity(event.getEntity().worldObj, pos, stack);
				}
			}
		}
	}

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
