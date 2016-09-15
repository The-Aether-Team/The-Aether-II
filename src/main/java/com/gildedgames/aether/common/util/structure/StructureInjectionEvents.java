package com.gildedgames.aether.common.util.structure;

import com.gildedgames.aether.common.ReflectionAether;
import com.gildedgames.aether.common.network.NetworkingAether;
import com.gildedgames.aether.common.network.packets.StructureInjectionPacket;
import com.gildedgames.aether.common.tile_entities.TileEntityStructureExtended;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.inventory.GuiEditStructure;
import net.minecraft.tileentity.TileEntityStructure;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class StructureInjectionEvents
{

	@SubscribeEvent
	public static void onActionPerformed(GuiScreenEvent.ActionPerformedEvent.Pre event)
	{
		if (event.getGui() instanceof GuiEditStructure)
		{
			if (event.getButton() == null)
			{
				return;
			}

			GuiEditStructure gui = (GuiEditStructure)event.getGui();

			TileEntityStructure structure = ObfuscationReflectionHelper.getPrivateValue(GuiEditStructure.class, gui, ReflectionAether.TILE_STRUCTURE.getMappings());

			if (!(structure instanceof TileEntityStructureExtended))
			{
				//return;
			}

			if (event.getButton().id == 0)
			{
				sendStructureInjection(gui, structure, (byte)1);

				Minecraft.getMinecraft().displayGuiScreen(null);

				event.setCanceled(true);
			}
			else if (event.getButton().id == 9)
			{
				if (structure.getMode() == TileEntityStructure.Mode.SAVE)
				{
					sendStructureInjection(gui, structure, (byte)2);

					Minecraft.getMinecraft().displayGuiScreen(null);

					event.setCanceled(true);
				}
			}
			else if (event.getButton().id == 10)
			{
				if (structure.getMode() == TileEntityStructure.Mode.LOAD)
				{
					sendStructureInjection(gui, structure, (byte)3);

					Minecraft.getMinecraft().displayGuiScreen(null);

					event.setCanceled(true);
				}
			}
			else if (event.getButton().id == 19)
			{
				if (structure.getMode() == TileEntityStructure.Mode.SAVE)
				{
					sendStructureInjection(gui, structure, (byte)4);

					Minecraft.getMinecraft().displayGuiScreen(null);

					event.setCanceled(true);
				}
			}
		}
	}

	private static void sendStructureInjection(GuiEditStructure gui, TileEntityStructure structure, byte action)
	{
		GuiTextField nameInput = ObfuscationReflectionHelper.getPrivateValue(GuiEditStructure.class, gui, ReflectionAether.NAME_INPUT.getMappings());

		GuiTextField relativeXInput = ObfuscationReflectionHelper.getPrivateValue(GuiEditStructure.class, gui, ReflectionAether.RELATIVE_X_INPUT.getMappings());
		GuiTextField relativeYInput = ObfuscationReflectionHelper.getPrivateValue(GuiEditStructure.class, gui, ReflectionAether.RELATIVE_Y_INPUT.getMappings());
		GuiTextField relativeZInput = ObfuscationReflectionHelper.getPrivateValue(GuiEditStructure.class, gui, ReflectionAether.RELATIVE_Z_INPUT.getMappings());

		GuiTextField sizeXInput = ObfuscationReflectionHelper.getPrivateValue(GuiEditStructure.class, gui, ReflectionAether.SIZE_X_INPUT.getMappings());
		GuiTextField sizeYInput = ObfuscationReflectionHelper.getPrivateValue(GuiEditStructure.class, gui, ReflectionAether.SIZE_Y_INPUT.getMappings());
		GuiTextField sizeZInput = ObfuscationReflectionHelper.getPrivateValue(GuiEditStructure.class, gui, ReflectionAether.SIZE_Z_INPUT.getMappings());

		GuiTextField metadataInput = ObfuscationReflectionHelper.getPrivateValue(GuiEditStructure.class, gui, ReflectionAether.METADATA_INPUT.getMappings());

		GuiTextField integrityInput = ObfuscationReflectionHelper.getPrivateValue(GuiEditStructure.class, gui, ReflectionAether.INTEGRITY_INPUT.getMappings());

		GuiTextField seedInput = ObfuscationReflectionHelper.getPrivateValue(GuiEditStructure.class, gui, ReflectionAether.SEED_INPUT.getMappings());

		BlockPos relative = new BlockPos(parseInt(relativeXInput.getText()), parseInt(relativeYInput.getText()), parseInt(relativeZInput.getText()));
		BlockPos size = new BlockPos(parseInt(sizeXInput.getText()), parseInt(sizeYInput.getText()), parseInt(sizeZInput.getText()));

		ByteBuf buf = Unpooled.buffer();

		structure.func_189705_a(buf);

		BlockPos pos = new BlockPos(buf.readInt(), buf.readInt(), buf.readInt());

		String metadata = metadataInput.getText();

		float integrity = parseFloat(integrityInput.getText());
		long seed = parseLong(seedInput.getText());

		StructureInjectionPacket packet = new StructureInjectionPacket(action, pos, relative, size, structure.getMode(), nameInput.getText(), structure.getMirror(), structure.getRotation(), metadata, structure.ignoresEntities(), structure.showsAir(), structure.showsBoundingBox(), integrity, seed, true);

		NetworkingAether.sendPacketToServer(packet);

		StructureInjectionPacket packetRepeatNoMessages = new StructureInjectionPacket(action, pos, relative, size, structure.getMode(), nameInput.getText(), structure.getMirror(), structure.getRotation(), metadata, structure.ignoresEntities(), structure.showsAir(), structure.showsBoundingBox(), integrity, seed, false);

		NetworkingAether.sendPacketToServer(packetRepeatNoMessages); // TODO: Fix weird logic here where a second packet needs to be sent to sync the TE correctly.. Hours of research gave me no insight.
	}

	private static long parseLong(String longString)
	{
		try
		{
			return Long.valueOf(longString).longValue();
		}
		catch (NumberFormatException var3)
		{
			return 0L;
		}
	}

	private static float parseFloat(String floatString)
	{
		try
		{
			return Float.valueOf(floatString).floatValue();
		}
		catch (NumberFormatException var3)
		{
			return 1.0F;
		}
	}

	private static int parseInt(String intString)
	{
		try
		{
			return Integer.parseInt(intString);
		}
		catch (NumberFormatException var3)
		{
			return 0;
		}
	}

}
