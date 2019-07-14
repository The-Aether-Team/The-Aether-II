package com.gildedgames.aether.common.entities;

import com.gildedgames.aether.api.registrar.AbstractRegistrar;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.registries.ObjectHolder;

@ObjectHolder("aether")
public class TileEntityTypesAether extends AbstractRegistrar
{
	public static final TileEntityType<?> ALTAR = getDefault();

	public static final TileEntityType<?> HOLYSTONE_FURNACE = getDefault();

	public static final TileEntityType<?> SKYROOT_CHEST = getDefault();

	public static final TileEntityType<?> SKYROOT_SIGN = getDefault();

	public static final TileEntityType<?> MULTIBLOCK_DUMMY = getDefault();

	public static final TileEntityType<?> MOA_EGG = getDefault();

	public static final TileEntityType<?> ICESTONE_COOLER = getDefault();

	public static final TileEntityType<?> INCUBATOR = getDefault();

	public static final TileEntityType<?> PRESENT = getDefault();

	public static final TileEntityType<?> WILDCARD = getDefault();

	public static final TileEntityType<?> MASONRY_BENCH = getDefault();

	public static final TileEntityType<?> OUTPOST_CAMPFIRE = getDefault();

	public static final TileEntityType<?> TELEPORTER = getDefault();
}

