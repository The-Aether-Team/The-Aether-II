package com.gildedgames.aether.common.world.dungeon;

import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.entities.dungeon.labyrinth.EntityBattleGolem;
import com.gildedgames.aether.common.entities.dungeon.labyrinth.EntityBattleSentry;
import com.gildedgames.aether.common.entities.dungeon.labyrinth.EntityDetonationSentry;
import com.gildedgames.aether.common.entities.dungeon.labyrinth.EntityTrackingSentry;
import com.gildedgames.aether.common.world.dungeon.util.FlatLayerDungeonGenerator;
import com.google.common.collect.Lists;
import net.minecraft.entity.Entity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.template.Template;
import net.minecraft.world.gen.structure.template.TemplateManager;

import java.util.List;
import java.util.Random;

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

					Template loot_large_2 = MANAGER.func_189942_b(server, new ResourceLocation(AetherCore.MOD_ID, "/labyrinth/loot_large_2"));
					Template loot_large_4 = MANAGER.func_189942_b(server, new ResourceLocation(AetherCore.MOD_ID, "/labyrinth/loot_large_4"));
					Template loot_medium = MANAGER.func_189942_b(server, new ResourceLocation(AetherCore.MOD_ID, "/labyrinth/loot_medium"));
					Template loot_small = MANAGER.func_189942_b(server, new ResourceLocation(AetherCore.MOD_ID, "/labyrinth/loot_small"));
					Template feature_water_1 = MANAGER.func_189942_b(server, new ResourceLocation(AetherCore.MOD_ID, "/labyrinth/feature_water_1"));
					Template feature_water_2 = MANAGER.func_189942_b(server, new ResourceLocation(AetherCore.MOD_ID, "/labyrinth/feature_water_2"));
					Template feature_pillars_1 = MANAGER.func_189942_b(server, new ResourceLocation(AetherCore.MOD_ID, "/labyrinth/feature_pillars_1"));
					Template feature_pillars_2 = MANAGER.func_189942_b(server, new ResourceLocation(AetherCore.MOD_ID, "/labyrinth/feature_pillars_2"));
					Template loot_pillars_1 = MANAGER.func_189942_b(server, new ResourceLocation(AetherCore.MOD_ID, "/labyrinth/loot_pillars_1"));
					Template loot_pillars_2 = MANAGER.func_189942_b(server, new ResourceLocation(AetherCore.MOD_ID, "/labyrinth/loot_pillars_2"));

					//Template labTunnel = MANAGER.func_189942_b(server, new ResourceLocation(AetherCore.MOD_ID, "Dun_LAB_Room_Tunnel"));
					//Template labTunnelLeft = MANAGER.func_189942_b(server, new ResourceLocation(AetherCore.MOD_ID, "Dun_LAB_Room_Tunnel_Left"));
					//Template labTunnelRight = MANAGER.func_189942_b(server, new ResourceLocation(AetherCore.MOD_ID, "Dun_LAB_Room_Tunnel_Right"));

					rooms.add(new DungeonRoom(loot_large_2));
					rooms.add(new DungeonRoom(loot_large_4));
					rooms.add(new DungeonRoom(feature_pillars_1));
					rooms.add(new DungeonRoom(feature_pillars_2));
					rooms.add(new DungeonRoom(loot_pillars_1));
					rooms.add(new DungeonRoom(loot_pillars_2));
					rooms.add(new DungeonRoom(feature_water_1));
					rooms.add(new DungeonRoom(feature_water_2));
					rooms.add(new DungeonRoom(loot_medium));
					rooms.add(new DungeonRoom(loot_medium));
					rooms.add(new DungeonRoom(loot_small));
					rooms.add(new DungeonRoom(loot_small));

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
					Template labEntrance = MANAGER.func_189942_b(server, new ResourceLocation(AetherCore.MOD_ID, "/labyrinth/entrance"));

					return new DungeonRoom(labEntrance);
				}

				@Override public DungeonRoom createConnectionBottom(MinecraftServer server, Random rand)
				{
					Template labStairs = MANAGER.func_189942_b(server, new ResourceLocation(AetherCore.MOD_ID, "/labyrinth/stairs_bottom"));

					return new DungeonRoom(labStairs);
				}

				@Override public DungeonRoom createConnectionTop(MinecraftServer server, Random rand)
				{
					Template labStairs = MANAGER.func_189942_b(server, new ResourceLocation(AetherCore.MOD_ID, "/labyrinth/stairs_top"));

					return new DungeonRoom(labStairs);
				}

			};
		}

		@Override
		public Entity createRandomMob(World world, Random rand)
		{
			int range = rand.nextInt(100);

			if (range < 60)
			{
				return rand.nextBoolean() ? new EntityBattleSentry(world) : new EntityDetonationSentry(world);
			}

			if (range < 80)
			{
				return new EntityBattleGolem(world);
			}

			return new EntityTrackingSentry(world);
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

		@Override
		public Entity createRandomMob(World world, Random rand)
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

		@Override
		public Entity createRandomMob(World world, Random rand)
		{
			return null;
		}
		
	};
	
}
