package com.gildedgames.aether.api.orbis;

import com.gildedgames.aether.api.util.NBT;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public interface IWorldRenderer extends NBT
{

	@Nullable
	Object getRenderedObject();

	IRegion getBoundingBox();

	void startRendering(World world, float partialTicks);

	void finishRendering(World world);

	void prepare(World world);

	void doGlobalRendering(World world, float partialTicks);

	List<IWorldRenderer> getSubRenderers(World world);

	void onRemoved();

}
