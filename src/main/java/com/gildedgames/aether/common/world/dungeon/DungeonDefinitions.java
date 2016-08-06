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
			return new FlatLayerDungeonGenerator(4);
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

					Template labLootL2 = MANAGER.func_189942_b(server, new ResourceLocation(AetherCore.MOD_ID, "Dun_LAB_Room_LootL_2"));
					Template labLootL4 = MANAGER.func_189942_b(server, new ResourceLocation(AetherCore.MOD_ID, "Dun_LAB_Room_LootL_4"));
					Template labLootM = MANAGER.func_189942_b(server, new ResourceLocation(AetherCore.MOD_ID, "Dun_LAB_Room_LootM"));
					Template labLootS = MANAGER.func_189942_b(server, new ResourceLocation(AetherCore.MOD_ID, "Dun_LAB_Room_LootS"));
					Template labWFeature1 = MANAGER.func_189942_b(server, new ResourceLocation(AetherCore.MOD_ID, "Dun_LAB_Room_WFeature_1"));
					Template labWFeature2 = MANAGER.func_189942_b(server, new ResourceLocation(AetherCore.MOD_ID, "Dun_LAB_Room_WFeature_2"));
					Template labPillars1F = MANAGER.func_189942_b(server, new ResourceLocation(AetherCore.MOD_ID, "Dun_LAB_Room_Pillars_1F"));
					Template labPillars2F = MANAGER.func_189942_b(server, new ResourceLocation(AetherCore.MOD_ID, "Dun_LAB_Room_Pillars_2F"));
					Template labPillarsLoot1F = MANAGER.func_189942_b(server, new ResourceLocation(AetherCore.MOD_ID, "Dun_LAB_Room_PillarsLoot_1F"));
					Template labPillarsLoot2F = MANAGER.func_189942_b(server, new ResourceLocation(AetherCore.MOD_ID, "Dun_LAB_Room_PillarsLoot_2F"));

					//Template labTunnel = MANAGER.func_189942_b(server, new ResourceLocation(AetherCore.MOD_ID, "Dun_LAB_Room_Tunnel"));
					//Template labTunnelLeft = MANAGER.func_189942_b(server, new ResourceLocation(AetherCore.MOD_ID, "Dun_LAB_Room_Tunnel_Left"));
					//Template labTunnelRight = MANAGER.func_189942_b(server, new ResourceLocation(AetherCore.MOD_ID, "Dun_LAB_Room_Tunnel_Right"));

					rooms.add(new DungeonRoom(labLootL2));
					rooms.add(new DungeonRoom(labLootL4));
					rooms.add(new DungeonRoom(labPillars1F));
					rooms.add(new DungeonRoom(labPillars2F));
					rooms.add(new DungeonRoom(labPillarsLoot1F));
					rooms.add(new DungeonRoom(labPillarsLoot2F));
					rooms.add(new DungeonRoom(labWFeature1));
					rooms.add(new DungeonRoom(labWFeature2));
					rooms.add(new DungeonRoom(labLootM));
					rooms.add(new DungeonRoom(labLootM));
					rooms.add(new DungeonRoom(labLootS));
					rooms.add(new DungeonRoom(labLootS));

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

				@Override public DungeonRoom createConnectionBottom(MinecraftServer server, Random rand)
				{
					Template labStairs = MANAGER.func_189942_b(server, new ResourceLocation(AetherCore.MOD_ID, "Dun_LAB_Stairs_Bottom"));

					return new DungeonRoom(labStairs);
				}

				@Override public DungeonRoom createConnectionTop(MinecraftServer server, Random rand)
				{
					Template labStairs = MANAGER.func_189942_b(server, new ResourceLocation(AetherCore.MOD_ID, "Dun_LAB_Stairs_Top"));

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
