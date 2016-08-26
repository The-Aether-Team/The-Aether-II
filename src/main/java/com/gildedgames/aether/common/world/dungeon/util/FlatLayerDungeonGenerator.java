package com.gildedgames.aether.common.world.dungeon.util;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import com.gildedgames.aether.common.util.TemplatePrimer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.ChunkPrimer;

import com.gildedgames.aether.common.blocks.BlocksAether;
import com.gildedgames.aether.common.world.dungeon.DungeonGenerator;
import com.gildedgames.aether.common.world.dungeon.instance.DungeonInstance;
import com.gildedgames.aether.common.world.dungeon.DungeonRoom;
import com.gildedgames.aether.common.world.dungeon.DungeonRoomProvider;
import com.gildedgames.util.core.util.BlockPosDimension;
import com.google.common.collect.Lists;
import net.minecraft.world.gen.structure.template.BlockRotationProcessor;
import net.minecraft.world.gen.structure.template.ITemplateProcessor;
import net.minecraft.world.gen.structure.template.PlacementSettings;

public class FlatLayerDungeonGenerator implements DungeonGenerator
{

	private static final int CHANCE_FOR_CYCLE = 10;

	private static final int SEPARATION_ITERATIONS = 40;

	private static final int WALL = 0;

	private static final int BIG_ROOM = 1;

	private static final int SMALL_ROOM = 2;

	private static final int PATH = 3;

	private static final int PATH_WALL = 9;

	private static final int GOAL_ROOM = 4;

	private static final int START_ROOM = 5;

	private static final int END_ROOM = 6;

	private static final int ENTRANCE_ROOM = 7;

	private static final int TRUE_PATH = 8;

	private boolean layoutReady;

	private LinkedList<DungeonLayer> layers = Lists.newLinkedList();

	public FlatLayerDungeonGenerator(int layerCount)
	{
		for (int i = 0; i < layerCount; i++)
		{
			DungeonLayer layer = new DungeonLayer(40);

			this.layers.add(layer);
		}
	}

	@Override
	public BlockPosDimension getEntrancePos()
	{
		return null;
	}

	@Override
	public boolean isLayoutReady()
	{
		return this.layoutReady;
	}

	@Override
	public void generateLayout(MinecraftServer server, long seed, DungeonInstance instance, DungeonRoomProvider provider)
	{
		Random rand = new Random(seed);

		DungeonLayer prevLayer = null;

		int layerNum = 0;

		for (DungeonLayer layer : this.layers)
		{
			layerNum++;

			layer.defineMinY(prevLayer != null ? prevLayer.maxY() : 0);

			List<DungeonRoom> rooms = provider.createRooms(server, rand);

			DungeonRoom entrance;
			DungeonRoom end;

			if (layerNum == this.layers.size())
			{
				entrance = provider.createConnectionTop(server, rand);
				end = provider.createEntranceRoom(server, rand);
			}
			else
			{
				entrance = provider.createConnectionTop(server, rand);
				end = provider.createConnectionBottom(server, rand);
			}

			rooms.add(entrance);
			rooms.add(end);

			layer.defineEntranceRoom(entrance);
			layer.defineEndRoom(end);

			for (DungeonRoom room : rooms)
			{
				room.setPos(layer.minX() + rand.nextInt(layer.getDiameter()), layer.minX() + rand.nextInt(layer.getDiameter()));
			}

			if (prevLayer != null)
			{
				//layer.defineMinX(layer.entranceRoom().getMinX()prevLayer.endRoom().getMinX());
				//layer.defineMinZ(prevLayer.endRoom().getMinZ());

				//entrance.setPos(prevLayer.endRoom().getMinX(), prevLayer.endRoom().getMinZ());
				//end.setPos(layer.minX() + layer.getDiameter(), layer.minX() + layer.getDiameter());
			}
			else
			{
				//end.setPos(layer.minX(), layer.minZ());
				//entrance.setPos(layer.minX() + layer.getDiameter(), layer.minX() + layer.getDiameter());
			}

			rooms = this.separateRooms(rooms);

			int[][] tiles = this.createCorridors(instance, rooms, layer, prevLayer, rand);

			layer.setRooms(rooms);

			if (prevLayer != null)
			{
				int offsetX = prevLayer.endRoom().getMinX() - layer.entranceRoom().getMinX();
				int offsetZ = prevLayer.endRoom().getMinZ() - layer.entranceRoom().getMinZ();

				layer.definePos(offsetX, offsetZ);
			}

			int maxHeight = 0;

			for (DungeonRoom room : layer.getRooms())
			{
				if (room.getHeight() > maxHeight)
				{
					maxHeight = room.getHeight();
				}
			}

			layer.defineTiles(tiles);

			layer.defineHeight(maxHeight);

			int minHeight = maxHeight;

			for (DungeonRoom room : layer.getRooms())
			{
				if (room.getHeight() < maxHeight)
				{
					minHeight = room.getHeight();
				}
			}

			layer.setSmallestRoomHeight(minHeight);

			prevLayer = layer;

			if (layerNum == this.layers.size())
			{
				instance.setInsideEntrance(new BlockPosDimension((int) end.getCenterX() - 1, layer.minY() + 1, (int) end.getCenterZ() - 1, instance.getDimIdInside()));
			}
		}

		this.layoutReady = true;
	}

	@Override
	public void generateChunk(World world, Random rand, DungeonInstance inst, ChunkPrimer primer, int chunkX, int chunkZ)
	{
		if (!this.isLayoutReady())
		{
			return;
		}

		int posX = chunkX * 16;
		int posZ = chunkZ * 16;

		for (DungeonLayer layer : this.layers)
		{
			/*if (posX < layer.minX() || posZ < layer.minZ() || posX > layer.maxX() || posZ > layer.maxZ())
			{
				continue;
			}*/

			for (int x = 0; x < 16; x++)
			{
				for (int z = 0; z < 16; z++)
				{
					int stepX = posX - layer.minX() + x;
					int stepZ = posZ - layer.minZ() + z;

					if (stepX < 0 || stepZ < 0 || stepX > layer.getWidth() - 1 || stepZ > layer.getLength() - 1)
					{
						continue;
					}

					int tile = layer.tiles()[stepX][stepZ];

					switch (tile)
					{
						case SMALL_ROOM:
						{
							this.generateTile(layer, x, layer.smallestRoomHeight(), z, BlocksAether.labyrinth_wall.getDefaultState(), Blocks.AIR.getDefaultState(), primer);
							break;
						}
						case END_ROOM:
						{
							this.generateTile(layer, x, layer.smallestRoomHeight(), z, Blocks.GLOWSTONE.getDefaultState(), Blocks.AIR.getDefaultState(), primer);
							break;
						}
						case ENTRANCE_ROOM:
						{
							this.generateTile(layer, x, layer.smallestRoomHeight(), z, Blocks.DIAMOND_BLOCK.getDefaultState(), Blocks.AIR.getDefaultState(), primer);
							break;
						}
						case BIG_ROOM:
						{
							this.generateTile(layer, x, layer.smallestRoomHeight(), z, BlocksAether.labyrinth_wall.getDefaultState(), Blocks.AIR.getDefaultState(), primer);
							break;
						}
						case PATH:
						{
							boolean createPillar = x % 4 == z % 4;

							this.generateTile(layer, x, 4, z, BlocksAether.labyrinth_base.getDefaultState(), BlocksAether.labyrinth_wall.getDefaultState(), primer);

							if (createPillar)
							{
								for (int i = layer.minY() + 1; i <= layer.minY() + 4; i++)
								{
									primer.setBlockState(x, i, z, BlocksAether.labyrinth_glowing_pillar.getDefaultState());
								}

								primer.setBlockState(x, layer.minY() + 1, z, BlocksAether.labyrinth_base.getDefaultState());
								primer.setBlockState(x, layer.minY() + 4, z, BlocksAether.labyrinth_capstone.getDefaultState());
							}

							primer.setBlockState(x, layer.minY() + 5, z, BlocksAether.labyrinth_base.getDefaultState());

							break;
						}
						case TRUE_PATH:
						{
							this.generateTile(layer, x, 4, z, BlocksAether.labyrinth_capstone.getDefaultState(), BlocksAether.labyrinth_wall.getDefaultState(), primer);
							primer.setBlockState(x, layer.minY() + 5, z, BlocksAether.labyrinth_capstone.getDefaultState());

							break;
						}
						case PATH_WALL:
						{
							for (int i = layer.minY(); i <= layer.minY() + 4; i++)
							{
								primer.setBlockState(x, i, z, BlocksAether.labyrinth_wall.getDefaultState());
							}

							primer.setBlockState(x, layer.minY() + 1, z, BlocksAether.labyrinth_base.getDefaultState());
							primer.setBlockState(x, layer.minY() + 4, z, BlocksAether.labyrinth_capstone.getDefaultState());

							break;
						}
						case WALL:
						{
							this.generateTile(layer, x, 4, z, BlocksAether.labyrinth_wall.getDefaultState(), primer);

							for (int i = layer.minY(); i <= layer.minY() + 4; i++)
							{
								primer.setBlockState(x, i, z, BlocksAether.labyrinth_wall.getDefaultState());
							}

							primer.setBlockState(x, layer.minY() + 1, z, BlocksAether.labyrinth_base.getDefaultState());
							primer.setBlockState(x, layer.minY() + 4, z, BlocksAether.labyrinth_capstone.getDefaultState());

							break;
						}
						default:
							break;
					}
				}
			}
		}

		for (DungeonLayer layer : this.layers)
		{
			for (DungeonRoom room : layer.getRooms())
			{
				if (room.template != null)
				{
					PlacementSettings settings = (new PlacementSettings()).setMirror(Mirror.NONE).setRotation(Rotation.NONE).setIgnoreEntities(false).setChunk(new ChunkPos(chunkX, chunkZ)).setReplacedBlock(null).setIgnoreStructureBlock(false);

					BlockPos pos = new BlockPos(room.getMinX(), layer.minY(), room.getMinZ());

					ITemplateProcessor processor = new BlockRotationProcessor(pos, settings);

					TemplatePrimer.primeChunk(room.template, world, new ChunkPos(chunkX, chunkZ), primer, pos, processor, settings);
				}
			}
		}
	}

	@Override
	public void populateChunk(World world, Random rand, DungeonInstance inst, int chunkX, int chunkZ)
	{
		int posX = chunkX * 16;
		int posZ = chunkZ * 16;

		for (DungeonLayer layer : this.layers)
		{
			for (DungeonRoom room : layer.getRooms())
			{
				if (room.template != null)
				{
					PlacementSettings settings = (new PlacementSettings()).setMirror(Mirror.NONE).setRotation(Rotation.NONE).setIgnoreEntities(false).setChunk(new ChunkPos(chunkX, chunkZ)).setReplacedBlock(null).setIgnoreStructureBlock(false);

					BlockPos pos = new BlockPos(room.getMinX(), layer.minY(), room.getMinZ());

					ITemplateProcessor processor = new BlockRotationProcessor(pos, settings);

					TemplatePrimer.populateChunk(room.template, world, new ChunkPos(chunkX, chunkZ), pos, processor, settings, 2);
				}
			}
		}

		for (int x = 0; x < 16; x++)
		{
			for (int z = 0; z < 16; z++)
			{
				if (posX + x == inst.getInsideEntrance().getX() + 1 && posZ + z == inst.getInsideEntrance().getZ() + 1)
				{
					world.setBlockState(new BlockPos(posX + x, inst.getInsideEntrance().getY(), posZ + z), BlocksAether.labyrinth_totem.getDefaultState());
				}
			}
		}
	}

	private void generateTile(DungeonLayer layer, int x, int height, int z, IBlockState block, ChunkPrimer primer)
	{
		this.generateTile(layer, x, height, z, block, block, primer);
	}

	private void generateTile(DungeonLayer layer, int x, int height, int z, IBlockState block, IBlockState block2, ChunkPrimer primer)
	{
		primer.setBlockState(x, layer.minY(), z, block);

		for (int i = layer.minY() + 1; i <= layer.minY() + height; i++)
		{
			//primer.setBlockState(x, i, z, Blocks.STRUCTURE_VOID.getDefaultState());
		}

		for (int i = layer.minY() + 1 + height; i <= layer.minY() + layer.getHeight(); i++)
		{
			primer.setBlockState(x, i, z, block2);
		}
	}

	private List<DungeonRoom> separateRooms(List<DungeonRoom> rooms)
	{
		for (int iter = 0; iter < SEPARATION_ITERATIONS; iter++)//This part uses separation behavior to separate intersecting rooms.
		{
			for (DungeonRoom room1 : rooms)
			{
				if (room1.asleep)
				{
					continue;
				}

				double vx = 0;
				double vz = 0;
				int overlaps = 0;
				double room1x = room1.getCenterX();
				double room1z = room1.getCenterZ();
				double largestOverlap = 0;

				for (DungeonRoom room2 : rooms)
				{
					if (room1 == room2)
					{
						continue;
					}

					Rectangle intersection = room1.fullRectangle().intersection(room2.rectangle);

					if (intersection.isEmpty())
					{
						continue;
					}

					vx += room2.getCenterX() - room1x;
					vz += room2.getCenterZ() - room1z;

					double overlap = intersection.getWidth() > intersection.getHeight() ? intersection.getWidth() : intersection.getHeight();

					largestOverlap = largestOverlap > overlap ? largestOverlap : overlap;

					overlaps++;
					room2.asleep = false;
				}

				if (overlaps == 0)
				{
					room1.asleep = true;

					continue;
				}

				if (vx == 0 && vz == 0)
				{
					continue;
				}

				vx *= -1;
				vz *= -1;

				double length = Math.sqrt(vx * vx + vz * vz);//Normalizes the vector

				vx /= length;
				vz /= length;

				vx *= largestOverlap;
				vz *= largestOverlap;

				room1.setPositionOffset((int) vx, (int) vz);
			}
		}

		for (DungeonRoom room1 : rooms)
		{
			for (DungeonRoom room2 : rooms)
			{
				if (room1 == room2 || room2.toRemove)
				{
					continue;
				}

				if (room1.fullRectangle().intersects(room2.rectangle))
				{
					room1.toRemove = true;

					break;
				}
			}
		}

		int p = 0;

		while (p < rooms.size())
		{
			if (rooms.get(p).toRemove)
			{
				rooms.remove(p);
			}
			else
			{
				p++;
			}
		}

		return rooms;
	}

	private int[][] createCorridors(DungeonInstance instance, List<DungeonRoom> rooms, DungeonLayer newLayer, DungeonLayer previousLayer, Random rand)
	{
		List<DungeonRoom> largeRooms = this.findLargeRooms(rooms, rooms.size() / 2);

		List<DungeonRoom> smallRooms = new ArrayList<>(rooms);

		if (!largeRooms.contains(newLayer.endRoom()))
		{
			largeRooms.add(newLayer.endRoom());
		}

		if (!largeRooms.contains(newLayer.entranceRoom()))
		{
			largeRooms.add(newLayer.entranceRoom());
		}

		smallRooms.removeAll(largeRooms);

		int[][] tiles = this.getTiles(largeRooms, smallRooms, newLayer, previousLayer);

		List<Connection> connections = this.createConnections(largeRooms, rand);

		for (Connection c : connections)
		{
			DungeonRoom room1 = null;
			DungeonRoom room2 = null;

			for (DungeonRoom r : largeRooms)
			{
				if (c.getPointA()[0] == (int) r.getCenterX() && c.getPointA()[1] == (int) r.getCenterZ())
				{
					room1 = r;
				}

				if (c.getPointB()[0] == (int) r.getCenterX() && c.getPointB()[1] == (int) r.getCenterZ())
				{
					room2 = r;
				}
			}

			for (int x = room1.getMinX(); x < room1.getMaxX(); x++)
			{
				for (int z = room1.getMinZ(); z < room1.getMaxZ(); z++)
				{
					tiles[x][z] = START_ROOM;
				}
			}

			for (int x = room2.getMinX(); x < room2.getMaxX(); x++)
			{
				for (int z = room2.getMinZ(); z < room2.getMaxZ(); z++)
				{
					tiles[x][z] = GOAL_ROOM;
				}
			}

			List<int[]> path = this.connect((int) room1.getCenterX(), (int) room1.getCenterZ(), (int) room2.getCenterX(), (int) room2.getCenterZ(), tiles, rand);

			this.walkPath(path, smallRooms, tiles);

			for (int x = room1.getMinX(); x < room1.getMaxX(); x++)
			{
				for (int z = room1.getMinZ(); z < room1.getMaxZ(); z++)
				{
					tiles[x][z] = BIG_ROOM;
				}
			}

			for (int x = room2.getMinX(); x < room2.getMaxX(); x++)
			{
				for (int z = room2.getMinZ(); z < room2.getMaxZ(); z++)
				{
					tiles[x][z] = BIG_ROOM;
				}
			}
		}

		for (int i =  newLayer.endRoom().getMinX(); i < newLayer.endRoom().getMaxX(); i++)
		{
			for (int j = newLayer.endRoom().getMinZ(); j < newLayer.endRoom().getMaxZ(); j++)
			{
				tiles[i][j] = END_ROOM;
			}
		}

		for (int i =  newLayer.entranceRoom().getMinX(); i < newLayer.entranceRoom().getMaxX(); i++)
		{
			for (int j = newLayer.entranceRoom().getMinZ(); j < newLayer.entranceRoom().getMaxZ(); j++)
			{
				tiles[i][j] = ENTRANCE_ROOM;
			}
		}

		return tiles;
	}

	private int[][] getTiles(List<DungeonRoom> largeRooms, List<DungeonRoom> smallRooms, DungeonLayer newLayer, DungeonLayer previousLayer)
	{
		int[] minVector = new int[2];
		int[] maxVector = new int[2];

		for (DungeonRoom r : largeRooms)//Find the rectangle that fits the rooms
		{
			minVector[0] = Math.min(minVector[0], r.getMinX());
			minVector[1] = Math.min(minVector[1], r.getMinZ());
			maxVector[0] = Math.max(maxVector[0], r.getMaxX());
			maxVector[1] = Math.max(maxVector[1], r.getMaxZ());
		}

		for (DungeonRoom r : smallRooms)
		{
			minVector[0] = Math.min(minVector[0], r.getMinX());
			minVector[1] = Math.min(minVector[1], r.getMinZ());
			maxVector[0] = Math.max(maxVector[0], r.getMaxX());
			maxVector[1] = Math.max(maxVector[1], r.getMaxZ());
		}

		if (previousLayer != null)//Ensure the entrance from the other layer is in the tiles
		{
			maxVector[0] = Math.max(maxVector[0], previousLayer.endRoom().getMinX());
			maxVector[1] = Math.max(maxVector[1], previousLayer.endRoom().getMinZ());
		}

		newLayer.defineWidth(maxVector[0] - minVector[0] + 2);
		newLayer.defineLength(maxVector[1] - minVector[1] + 2);

		//newLayer.defineMinX(minVector[0]);
		//newLayer.defineMinZ(minVector[1]);

		//int offsetX = newLayer.entranceRoom().getMinX();
		//int offsetZ = newLayer.entranceRoom().getMinZ();

		int[][] tiles = new int[newLayer.getWidth()][newLayer.getLength()];

		for (int x = 0; x < newLayer.getWidth(); x++)
		{
			for (int z = 0; z < newLayer.getLength(); z++)
			{
				tiles[x][z] = WALL;
			}
		}

		for (DungeonRoom r : largeRooms)
		{
			r.setPositionOffset(-minVector[0] + 1, -minVector[1] + 1);

			for (int x = r.getMinX(); x < r.getMaxX(); x++)
			{
				for (int z = r.getMinZ(); z < r.getMaxZ(); z++)
				{
					tiles[x][z] = BIG_ROOM;
				}
			}
		}

		for (DungeonRoom r : smallRooms)
		{
			r.setPositionOffset(-minVector[0] + 1, -minVector[1] + 1);

			for (int x = r.getMinX(); x < r.getMaxX(); x++)
			{
				for (int z = r.getMinZ(); z < r.getMaxZ(); z++)
				{
					tiles[x][z] = SMALL_ROOM;
				}
			}
		}

		return tiles;
	}

	//Finds the 15 largest rooms that are generated.
	private List<DungeonRoom> findLargeRooms(List<DungeonRoom> rooms, int desiredLargeRoomCount)
	{
		List<DungeonRoom> largeRooms = new ArrayList<>();

		Collections.sort(rooms, new RoomAreaComparer());

		for (int i = 0; i < desiredLargeRoomCount; i++)
		{
			largeRooms.add(rooms.get(i));
		}

		return largeRooms;
	}

	//Uses Delaunay Triangulation and Minimal Spanning Trees to make connections between rooms
	private List<Connection> createConnections(List<DungeonRoom> rooms, Random random)
	{
		List<int[]> points = new ArrayList<>();

		for (DungeonRoom room : rooms)
		{
			points.add((room).getPositionArray());
		}

		//Find Delaunay triangulation from the centers of the large rooms
		TriangulationCell[] cells = (new ConvexHull()).getDelaunayTriangulation(points);//ConvexHull.create(verticesz);

		List<Connection> connections = new ArrayList<>();

		for (TriangulationCell cell : cells)
		{
			List<int[]> vertices = cell.vertices;
			new Connection(vertices.get(0), vertices.get(1), connections);
			new Connection(vertices.get(0), vertices.get(2), connections);
			new Connection(vertices.get(1), vertices.get(2), connections);
		}

		//Create minimal spanning tree from the delaunay triangulation
		Collections.sort(connections, new ConnectionComparer());//Sort by the length of the connection
		List<List<int[]>> branches = new ArrayList<>();//Contains branches of the minimal spanning tree while generating it
		List<Connection> chosenConnections = new ArrayList<>();

		for (Connection c : connections)
		{
			if (chosenConnections.size() < 2)//The first two connections wouldn't make a circle
			{
				this.addConnection(c, branches, chosenConnections);
				continue;
			}

			if (this.checkForCycle(c, branches))//If adding this connection to the branch would create a circle
			{
				continue;
			}

			this.addConnection(c, branches, chosenConnections);
		}

		for (Connection c : connections)
		{
			if (!chosenConnections.contains(c))
			{
				if (random.nextInt(CHANCE_FOR_CYCLE) == 0)
				{
					chosenConnections.add(c);
				}
			}
		}

		return chosenConnections;
	}

	private void addConnection(Connection connection, List<List<int[]>> branches, List<Connection> chosenConnections)
	{
		chosenConnections.add(connection);

		int b = -1;
		int c = -1;

		for (int i = 0; i < branches.size(); i++)
		{
			List<int[]> branch = branches.get(i);

			if (branch.contains(connection.getPointA()))
			{
				b = i;
			}

			if (branch.contains(connection.getPointB()))
			{
				c = i;
			}
		}

		if (b >= 0 && c >= 0)//If both points are already found on a branch, connect the branches together
		{
			branches.get(b).addAll(branches.get(c));
			branches.remove(c);
		}
		else if (b >= 0)//If it was found on one branch, add that point to the branch
		{
			branches.get(b).add(connection.getPointB());
		}
		else if (c >= 0)
		{
			branches.get(c).add(connection.getPointA());
		}
		else //Create a new branch containing the points of the connection
		{
			List<int[]> newBranch = new ArrayList<>();

			newBranch.add(connection.getPointA());
			newBranch.add(connection.getPointB());

			branches.add(newBranch);
		}
	}

	//Returns true if adding the connection would form a circle
	private boolean checkForCycle(Connection connection, List<List<int[]>> branches)
	{
		List<int[]> branch1 = null;
		List<int[]> branch2 = null;

		for (List<int[]> branch : branches)
		{
			if (branch.contains(connection.getPointA()))
			{
				branch1 = branch;
			}

			if (branch.contains(connection.getPointB()))
			{
				branch2 = branch;
			}
		}

		if (branch1 != null && branch2 != null)
		{
			if (branch1 == branch2)//If there already is a branch that can reach both points adding this connection would form a circle
			{
				return true;
			}
		}

		return false;
	}

	//A corridor cannot intersect another large room.
	private List<int[]> connect(int p1X, int p1Z, int p2X, int p2Z, int[][] tiles, Random random)
	{
		List<int[]> path = new ArrayList<>();
		int xVel = (int) Math.signum(p2X - p1X);
		int zVel = (int) Math.signum(p2Z - p1Z);
		int currentX = p1X;
		int currentZ = p1Z;
		path.add(new int[] { p1X, p1Z });
		boolean goHori = random.nextBoolean();
		boolean foundGoal = false;

		while (!foundGoal)
		{
			int iVel = goHori ? xVel : zVel;

			if (iVel == 0)
			{
				goHori = !goHori;
				iVel = goHori ? xVel : zVel;
			}

			int iMin = goHori ? currentX : currentZ;

			int iMax = goHori ? p2X : p2Z;

			currentLoop:
			for (int i = iMin; i != iMax; i += iVel)
			{
				int x, z;

				if (goHori)
				{
					currentX = i;
					x = i;
					z = currentZ;
				}
				else
				{
					currentZ = i;
					x = currentX;
					z = i;
				}

				switch (this.checkPosition(x, z, tiles))
				{
					/*case (1)://TODO: properly handle collision with third non related room
					{
						foundGoal = true;
						break currentLoop;
					}*/
					case (2):
					{
						path.add(new int[] { x, z });
						foundGoal = true;
						break currentLoop;
					}
					default:
					{
						path.add(new int[] { currentX, currentZ });
					}
				}

			}

			goHori = !goHori;
		}
		return path;
	}

	//Returns 0 if the position is valid, 1 if the position is not valid and 2 if the destination is reached
	private int checkPosition(int x, int z, int[][] tiles)
	{
		if (tiles[x][z] == BIG_ROOM)
		{
			return 1;
		}

		if (tiles[x][z] == GOAL_ROOM)
		{
			return 2;
		}

		return 0;
	}

	private void walkPath(List<int[]> path, List<DungeonRoom> smallRooms, int[][] tiles)
	{
		for (int[] pos : path)
		{
			if (tiles[pos[0]][pos[1]] == WALL || tiles[pos[0]][pos[1]] == PATH)
			{
				tiles[pos[0]][pos[1]] = TRUE_PATH;

				for (int x = pos[0] - 3; x <= pos[0] + 3; x++)
				{
					for (int z = pos[1] - 3; z <= pos[1] + 3; z++)
					{
						if (tiles[x][z] == WALL)
						{
							tiles[x][z] = PATH_WALL;
						}
					}
				}

				for (int x = pos[0] - 2; x <= pos[0] + 2; x++)
				{
					for (int z = pos[1] - 2; z <= pos[1] + 2; z++)
					{
						if (tiles[x][z] == PATH_WALL)
						{
							tiles[x][z] = PATH;
						}
					}
				}

				for (int x = pos[0] - 1; x <= pos[0] + 1; x++)
				{
					for (int z = pos[1] - 1; z <= pos[1] + 1; z++)
					{
						if (tiles[x][z] == PATH)
						{
							tiles[x][z] = TRUE_PATH;
						}
					}
				}
			}
		}
	}

}
