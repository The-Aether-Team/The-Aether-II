package com.gildedgames.orbis.common.player.godmode.selection_types;

import com.gildedgames.aether.api.orbis.shapes.IShape;
import com.gildedgames.aether.api.util.NBT;
import com.gildedgames.orbis.client.player.godmode.selection_types.ISelectionTypeClient;
import com.gildedgames.orbis.common.player.PlayerOrbisModule;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface ISelectionType extends NBT
{

	ISelectionTypeClient getClient();

	IShape createShape(BlockPos start, BlockPos end, PlayerOrbisModule module, World world);

}
