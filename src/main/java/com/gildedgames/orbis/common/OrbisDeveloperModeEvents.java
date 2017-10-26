package com.gildedgames.orbis.common;

import com.gildedgames.aether.common.capabilities.entity.player.PlayerAether;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.CommandGameMode;
import net.minecraft.command.NumberInvalidException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.GameType;
import net.minecraftforge.event.CommandEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class OrbisDeveloperModeEvents
{

	@SubscribeEvent
	public static void onCommandEvent(final CommandEvent event)
	{
		if (event.getCommand() instanceof CommandGameMode)
		{
			final String[] args = event.getParameters();

			final String gamemodeString = args[0];
			boolean setsDeveloperMode = false;

			if (gamemodeString.equals("developer"))
			{
				setsDeveloperMode = true;
			}
			else
			{
				try
				{
					final int gamemode = CommandBase.parseInt(gamemodeString, 0, GameType.values().length - 1);

					if (gamemode == 4)
					{
						setsDeveloperMode = true;
					}
				}
				catch (final NumberInvalidException e)
				{
					return;
				}
			}

			try
			{
				final EntityPlayer player = args.length >= 2 ?
						CommandBase.getPlayer(event.getSender().getServer(), event.getSender(), args[1]) :
						CommandBase.getCommandSenderAsPlayer(event.getSender());

				if (setsDeveloperMode)
				{
					PlayerAether.getPlayer(player).getOrbisModule().setDeveloperMode(true);
					player.setGameType(GameType.CREATIVE);

					final ITextComponent itextcomponent = new TextComponentTranslation("gameMode.developer", new Object[0]);

					event.setCanceled(true);

					if (event.getSender().getEntityWorld().getGameRules().getBoolean("sendCommandFeedback"))
					{
						player.sendMessage(new TextComponentTranslation("gameMode.changed", new Object[] { itextcomponent }));
					}

					if (player == event.getSender())
					{
						CommandBase.notifyCommandListener(event.getSender(), event.getCommand(), 1, "commands.gamemode.success.self",
								new Object[] { itextcomponent });
					}
					else
					{
						CommandBase.notifyCommandListener(event.getSender(), event.getCommand(), 1, "commands.gamemode.success.other",
								new Object[] { player.getName(), itextcomponent });
					}
				}
				else
				{
					PlayerAether.getPlayer(player).getOrbisModule().setDeveloperMode(false);
				}
			}
			catch (final CommandException e)
			{
				e.printStackTrace();
			}
		}
	}

	/**
	 * Prevents players in Developer Mode from right clicking
	 * blocks or interacting with them.
	 * @param event
	 */
	@SubscribeEvent
	public void onPlayerInteract(final PlayerInteractEvent.RightClickBlock event)
	{
		final EntityPlayer player = event.getEntityPlayer();
		final PlayerAether playerAether = PlayerAether.getPlayer(player);

		if (!playerAether.getOrbisModule().canInteractWithItems())
		{
			event.setCanceled(true);
		}
	}

	/**
	 * Prevents players in Developer Mode from right clicking
	 * with items in their hand and activating their interact
	 * function.
	 * @param event
	 */
	@SubscribeEvent
	public void onPlayerInteract(final PlayerInteractEvent.RightClickItem event)
	{
		final EntityPlayer player = event.getEntityPlayer();
		final PlayerAether playerAether = PlayerAether.getPlayer(player);

		if (!playerAether.getOrbisModule().canInteractWithItems())
		{
			event.setCanceled(true);
		}
	}

	/**
	 * Prevents players in Developer Mode from placing blocks.
	 * @param event
	 */
	@SubscribeEvent
	public void onPlayerPlacesBlock(final BlockEvent.PlaceEvent event)
	{
		final EntityPlayer player = event.getPlayer();
		final PlayerAether playerAether = PlayerAether.getPlayer(player);

		if (!playerAether.getOrbisModule().canInteractWithItems())
		{
			event.setCanceled(true);
		}
	}

}
