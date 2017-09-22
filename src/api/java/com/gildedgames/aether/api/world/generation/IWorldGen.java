package com.gildedgames.aether.api.world.generation;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public interface IWorldGen
{

	/**
	 * Should ONLY ever generate or interact with the world with blockAccess. Use world for
	 * other purposes as a last resort, like accessing a system tied to specific worlds.
	 *
	 * IMPORTANT: ALWAYS USE CANACCESS METHOD IN IBLOCKACCESSEXTENDED TO CHECK IF POSITION
	 * YOU'RE CHECKING OR SETTING IS ACCESSABLE - OR YOU WILL GET CONSTANT EXCEPTIONS
	 * @param blockAccess
	 * @param world
	 * @param rand
	 * @param position
	 * @param centered
	 * @return
	 */
	boolean generate(IBlockAccessExtended blockAccess, World world, Random rand, BlockPos position, boolean centered);

	/**
	 * Should ONLY ever generate or interact with the world with blockAccess. Use world for
	 * other purposes as a last resort, like accessing a system tied to specific worlds.
	 *
	 * IMPORTANT: ALWAYS USE CANACCESS METHOD IN IBLOCKACCESSEXTENDED TO CHECK IF POSITION
	 * YOU'RE CHECKING OR SETTING IS ACCESSABLE - OR YOU WILL GET CONSTANT EXCEPTIONS
	 * @param blockAccess
	 * @param world
	 * @param rand
	 * @param position
	 * @return
	 */
	boolean generate(IBlockAccessExtended blockAccess, World world, Random rand, BlockPos position);

}
