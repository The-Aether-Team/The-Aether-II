package com.gildedgames.aether.common.world.dungeon;

import java.io.File;
import java.io.FilenameFilter;
import java.util.List;
import java.util.Random;

import net.minecraft.client.Minecraft;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.ModContainer;

import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.world.dungeon.util.FlatLayerDungeonGenerator;
import com.gildedgames.aether.common.world.dungeon.util.Schematic;
import com.google.common.collect.Lists;

public class DungeonDefinitions
{

	public static final DungeonDefinition SLIDERS_LABYRINTH = new DungeonDefinition()
	{

		@Override
		public DungeonGenerator createGenerator()
		{
			return new FlatLayerDungeonGenerator(1);
		}

		@Override
		public DungeonRoomProvider createRoomProvider()
		{
			return new DungeonRoomProvider()
			{

				@Override
				public List<DungeonRoom> createRooms(Random rand)
				{
					List<DungeonRoom> rooms = Lists.newArrayList();

					File schemFile = new File(MinecraftServer.getServer().getDataDirectory(), "/dungeonSchematics/");
					
					schemFile.mkdirs();

					File[] files = schemFile.listFiles(new FilenameFilter()
					{
					    @Override
					    public boolean accept(File dir, String name)
					    {
					        return name.endsWith(".schematic");
					    }
					});
					
					for (File file : files)
					{
						final int amount = rand.nextInt(4) + 1;
						
						for (int i = 0; i < amount; i++)
						{
							rooms.add(new DungeonRoom(new Schematic("assets/aether/" + file.getName())));
						}
					}
					
					return rooms;
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
