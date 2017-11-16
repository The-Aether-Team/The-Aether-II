package com.gildedgames.aether.api.orbis;

import com.gildedgames.aether.api.util.NBT;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public interface IWorldRenderer extends NBT
{

	/**
	 * Should not render this object if it's disabled.
	 * @return Whether or not this world renderer is
	 * disabled.
	 */
	boolean isDisabled();

	/**
	 * Should not render this object if it's disabled.
	 * @param disabled Whether or not this world renderer is
	 * disabled.
	 */
	void setDisabled(boolean disabled);

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
