package com.gildedgames.aether.common.network;

import com.gildedgames.aether.client.gui.GuiStructureBuilder;
import com.gildedgames.aether.common.entities.tiles.builder.TileEntityStructureBuilder;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketUtilAether
{
	public static <REQ extends IMessage, RES extends IMessage>
	void checkThread(final MessageHandlerServer<REQ, RES> handler, final REQ message, final MessageContext ctx)
	{
		final WorldServer world = ctx.getServerHandler().player.getServerWorld();

		if (!world.isCallingFromMinecraftThread())
		{
			world.addScheduledTask(() ->
			{
				handler.onMessage(message, ctx);
			});
		}
	}

	public static void displayStructureBuilderGui(final EntityPlayer player, final BlockPos pos, final TileEntityStructureBuilder.Data data)
	{
		Minecraft.getMinecraft().displayGuiScreen(new GuiStructureBuilder(player, pos, data));
	}
}
