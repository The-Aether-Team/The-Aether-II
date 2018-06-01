package com.gildedgames.aether.common.commands;

import com.gildedgames.aether.api.world.islands.IIslandData;
import com.gildedgames.aether.api.world.islands.precipitation.PrecipitationStrength;
import com.gildedgames.aether.api.world.islands.precipitation.PrecipitationType;
import com.gildedgames.aether.common.util.helpers.IslandHelper;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class CommandIsland extends CommandBase
{
	@Override
	public String getName()
	{
		return "aeisland";
	}

	@Override
	public String getUsage(ICommandSender sender)
	{
		return "/aeisland [toggleDownfall]";
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException
	{
		if (args.length < 1)
		{
			throw new CommandException("Missing argument 'subcommand'. Usage: " + this.getUsage(sender));
		}
		else if (args.length > 1) {
			throw new CommandException("Too many arguments!");
		}

		String subcommand = args[0].toLowerCase();

		switch (subcommand)
		{
			case "toggledownfall":
				this.togglePrecipitation(sender);
				break;
			default:
				throw new CommandException("Invalid argument for 'subcommand'. Usage: " + this.getUsage(sender));
		}

	}

	private void togglePrecipitation(ICommandSender sender) throws CommandException
	{
		BlockPos pos = sender.getPosition();
		World world = sender.getEntityWorld();

		IIslandData data = IslandHelper.get(world, pos.getX() >> 4, pos.getZ() >> 4);

		if (data == null)
		{
			throw new CommandException("Not currently on an island.");
		}

		if (data.getPrecipitation().getType() == PrecipitationType.NONE)
		{
			data.getPrecipitation().startPrecipitation(PrecipitationStrength.LIGHT);
		}
		else
		{
			data.getPrecipitation().endPrecipitation();
		}
	}
}
