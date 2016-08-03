package com.gildedgames.aether.common.world.dungeon;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

import net.minecraft.client.Minecraft;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.gen.structure.template.Template;
import net.minecraft.world.gen.structure.template.TemplateManager;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.ModContainer;

import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.world.dungeon.util.FlatLayerDungeonGenerator;
import com.gildedgames.aether.common.world.dungeon.util.Schematic;
import com.google.common.collect.Lists;
import org.apache.commons.io.IOUtils;

import static com.google.common.io.Files.isFile;
import static com.ibm.icu.impl.PluralRulesLoader.loader;

public class DungeonDefinitions
{

	public static final TemplateManager MANAGER = new TemplateManager("structures");

	public static final DungeonDefinition SLIDERS_LABYRINTH = new DungeonDefinition()
	{

		@Override
		public DungeonGenerator createGenerator()
		{
			return new FlatLayerDungeonGenerator(3);
		}

		@Override
		public DungeonRoomProvider createRoomProvider()
		{
			return new DungeonRoomProvider()
			{

				@Override
				public List<DungeonRoom> createRooms(MinecraftServer server, Random rand)
				{
					List<DungeonRoom> rooms = Lists.newArrayList();

					Template pillarRoom = MANAGER.func_189942_b(server, new ResourceLocation(AetherCore.MOD_ID, "PillarRoom"));
					Template pillarRoomVar = MANAGER.func_189942_b(server, new ResourceLocation(AetherCore.MOD_ID, "PillarRoomVar"));
					Template corridor = MANAGER.func_189942_b(server, new ResourceLocation(AetherCore.MOD_ID, "Corridor"));

					Template labCorridor = MANAGER.func_189942_b(server, new ResourceLocation(AetherCore.MOD_ID, "Dun_LAB_Room_Corridor"));
					Template labLootLarge2 = MANAGER.func_189942_b(server, new ResourceLocation(AetherCore.MOD_ID, "Dun_LAB_Room_LootLarge_2"));
					Template labLootLarge4 = MANAGER.func_189942_b(server, new ResourceLocation(AetherCore.MOD_ID, "Dun_LAB_Room_LootLarge_4"));
					Template labLootMed1 = MANAGER.func_189942_b(server, new ResourceLocation(AetherCore.MOD_ID, "Dun_LAB_Room_LootMed_1"));
					Template labLootMed2 = MANAGER.func_189942_b(server, new ResourceLocation(AetherCore.MOD_ID, "Dun_LAB_Room_LootMed_2"));

					//Template labTunnel = MANAGER.func_189942_b(server, new ResourceLocation(AetherCore.MOD_ID, "Dun_LAB_Room_Tunnel"));
					//Template labTunnelLeft = MANAGER.func_189942_b(server, new ResourceLocation(AetherCore.MOD_ID, "Dun_LAB_Room_Tunnel_Left"));
					//Template labTunnelRight = MANAGER.func_189942_b(server, new ResourceLocation(AetherCore.MOD_ID, "Dun_LAB_Room_Tunnel_Right"));

					rooms.add(new DungeonRoom(pillarRoom));
					rooms.add(new DungeonRoom(pillarRoomVar));
					rooms.add(new DungeonRoom(pillarRoom));
					rooms.add(new DungeonRoom(pillarRoomVar));
					rooms.add(new DungeonRoom(pillarRoom));
					rooms.add(new DungeonRoom(corridor));
					rooms.add(new DungeonRoom(corridor));
					rooms.add(new DungeonRoom(corridor));
					rooms.add(new DungeonRoom(corridor));
					rooms.add(new DungeonRoom(corridor));

					rooms.add(new DungeonRoom(labCorridor));
					rooms.add(new DungeonRoom(labLootLarge2));
					rooms.add(new DungeonRoom(labLootLarge4));
					rooms.add(new DungeonRoom(labLootMed1));
					rooms.add(new DungeonRoom(labLootMed2));
					//rooms.add(new DungeonRoom(labTunnel));
					//rooms.add(new DungeonRoom(labTunnelLeft));
					//rooms.add(new DungeonRoom(labTunnelRight));

					/*for (int count = 0; count < 3; count++){
						rooms.add(new DungeonRoom(5, 5, 5));
					}

					for (int count = 0; count < 3; count++){
						rooms.add(new DungeonRoom(10, 5, 5));
					}

					for (int count = 0; count < 3; count++){
						rooms.add(new DungeonRoom(15, 5, 15));
					}*/

					return rooms;
				}

				@Override public DungeonRoom createEntranceRoom(MinecraftServer server, Random rand)
				{
					Template labEntrance = MANAGER.func_189942_b(server, new ResourceLocation(AetherCore.MOD_ID, "Dun_LAB_Room_Entrance"));

					return new DungeonRoom(labEntrance);
				}

				@Override public DungeonRoom createLayerConnectionRoom(MinecraftServer server, Random rand)
				{
					Template labStairs = MANAGER.func_189942_b(server, new ResourceLocation(AetherCore.MOD_ID, "Dun_LAB_Room_Stairs"));

					return new DungeonRoom(labStairs);
				}

			};
		}
		
	};
	
	public static final DungeonDefinition VALKYRIE_TEMPLE = new DungeonDefinition()
	{

		@Override
		public DungeonGenerator createGenerator()
		{
			return null;
		}

		@Override
		public DungeonRoomProvider createRoomProvider()
		{
			return null;
		}
		
	};
	
	public static final DungeonDefinition MOLTEN_CORE = new DungeonDefinition()
	{

		@Override
		public DungeonGenerator createGenerator()
		{
			return null;
		}

		@Override
		public DungeonRoomProvider createRoomProvider()
		{
			return null;
		}
		
	};
	
}
