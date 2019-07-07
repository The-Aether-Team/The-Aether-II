package com.gildedgames.aether.common.world.util;

import com.gildedgames.aether.api.registrar.BlocksAether;
import com.gildedgames.aether.api.world.generation.WorldDecoration;
import com.gildedgames.aether.api.world.generation.WorldDecorationSimple;
import com.gildedgames.aether.api.world.generation.positioners.PositionerLevels;
import com.gildedgames.aether.api.world.generation.positioners.PositionerSurface;
import com.gildedgames.aether.common.blocks.natural.plants.BlockAetherFlower;
import com.gildedgames.aether.common.world.aether.features.WorldGenCaveFloorPlacer;
import com.gildedgames.aether.common.world.aether.features.WorldGenFloorPlacer;
import com.google.common.collect.Lists;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.event.terraingen.DecorateBiomeEvent;

import java.util.List;
import java.util.Random;
import java.util.function.Function;

public class GenUtil
{
	public static List<IBlockState> GENERAL_FLOWER_STATES = Lists.newArrayList(
			BlocksAether.pink_swingtip.getDefaultState(),
			BlocksAether.green_swingtip.getDefaultState(),
			BlocksAether.neverbloom.getDefaultState(),
			BlocksAether.aether_flower.getStateFromMeta(BlockAetherFlower.BURSTBLOSSOM.getMeta()),
			BlocksAether.aether_flower.getStateFromMeta(BlockAetherFlower.WHITE_ROSE.getMeta()),
			BlocksAether.aether_flower.getStateFromMeta(BlockAetherFlower.PURPLE_FLOWER.getMeta()),
			BlocksAether.quickshoot.getDefaultState(),
			BlocksAether.blue_swingtip.getDefaultState());

	public static List<IBlockState> SHROOM_STATES = Lists.newArrayList(
			BlocksAether.barkshroom.getDefaultState(),
			BlocksAether.stoneshroom.getDefaultState());

	public static WorldDecoration createShroomDecorations(List<IBlockState> toPickFrom)
	{
		WorldGenCaveFloorPlacer shroomPlacer = new WorldGenCaveFloorPlacer((random) -> toPickFrom.get(random.nextInt(toPickFrom.size())), 7);

		return new WorldDecorationSimple(2, 0.2f, DecorateBiomeEvent.Decorate.EventType.CUSTOM, new PositionerLevels(26, 90), shroomPlacer);
	}

	public static WorldDecoration createFlowerDecorations(Random rand, List<IBlockState> toPickFrom, List<IBlockState> mustHave)
	{
		final int amountOfFlowerTypes = 2 + rand.nextInt(4);
		List<IBlockState> flowerStates = Lists.newArrayList();

		for (int i = 0; i < amountOfFlowerTypes; i++)
		{
			flowerStates.add(toPickFrom.get(rand.nextInt(toPickFrom.size())));
		}

		WorldGenFloorPlacer flowers = new WorldGenFloorPlacer(4, GenUtil.equalStateFetcher(),
				(random) ->
				{
					List<IBlockState> returned = Lists.newArrayList();

					for (int i = 0; i < 1 + random.nextInt(amountOfFlowerTypes); i++)
					{
						returned.add(flowerStates.get(random.nextInt(flowerStates.size())));
					}

					returned.addAll(mustHave);

					return returned;
				});

		return new WorldDecorationSimple(1 + rand.nextInt(6), 0.05F + rand.nextFloat() * 0.2F, DecorateBiomeEvent.Decorate.EventType.FLOWERS, new PositionerSurface(), flowers);
	}

	public static BlockPos rotate(final BlockPos origin, final BlockPos pos, final Rotation rotation)
	{
		final int i = pos.getX() - origin.getX();
		final int j = pos.getY() - origin.getY();
		final int k = pos.getZ() - origin.getZ();

		switch (rotation)
		{
			case COUNTERCLOCKWISE_90:
				return new BlockPos(origin.getX() + k, pos.getY(), origin.getZ() - i);
			case CLOCKWISE_90:
				return new BlockPos(origin.getX() - i, pos.getY(), origin.getZ() + i);
			case CLOCKWISE_180:
				return new BlockPos(origin.getX() - i, pos.getY(), origin.getZ() - k);
			default:
				return pos;
		}
	}

	public static WorldGenFloorPlacer.StateFetcher equalStateFetcher()
	{
		return (random, states) -> states.get(random.nextInt(states.size()));
	}

	public static Function<Random, List<IBlockState>> standardStateDefiner(IBlockState... states)
	{
		return (random) -> Lists.newArrayList(states);
	}
}
