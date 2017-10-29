package com.gildedgames.orbis.common.world_objects;

import com.gildedgames.aether.api.io.NBTFunnel;
import com.gildedgames.aether.api.orbis.IWorldObject;
import com.gildedgames.aether.api.orbis.IWorldRenderer;
import com.gildedgames.aether.api.orbis.region.AbstractRegion;
import com.gildedgames.aether.api.orbis.region.IMutableRegion;
import com.gildedgames.aether.api.orbis.region.IRegion;
import com.gildedgames.aether.api.orbis.region.IRotateable;
import com.gildedgames.aether.api.orbis.util.OrbisRotation;
import com.gildedgames.aether.api.orbis.util.RegionHelp;
import com.gildedgames.aether.api.orbis.util.RotationHelp;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.orbis.client.renderers.RenderShape;
import com.gildedgames.orbis.common.block.BlockData;
import com.gildedgames.orbis.common.block.BlockDataContainer;
import com.gildedgames.orbis.common.data.BlueprintData;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class Blueprint extends AbstractRegion implements IWorldObject, IMutableRegion, IRotateable
{
	private final World world;

	protected OrbisRotation rotation = OrbisRotation.NORTH;

	private IWorldRenderer renderer;

	private BlueprintData data;

	private BlockPos min = BlockPos.ORIGIN, max = BlockPos.ORIGIN;

	private Blueprint(final World world)
	{
		this.world = world;
	}

	public Blueprint(final World world, final IRegion region)
	{
		this.world = world;
		this.data = new BlueprintData(region);

		this.setBounds(region);
	}

	public Blueprint(final World world, final BlockPos pos, final BlueprintData data)
	{
		this.world = world;
		this.data = data;

		this.setPos(pos);
	}

	public Blueprint(final World world, final BlockPos pos, final OrbisRotation rotation, final BlueprintData data)
	{
		this.world = world;
		this.data = data;
		this.rotation = rotation;

		this.setPos(pos);
	}

	@Override
	public BlueprintData getData()
	{
		return this.data;
	}

	@Override
	public OrbisRotation getRotation()
	{
		return this.rotation;
	}

	public BlockData getBlock(final BlockPos pos)
	{
		final BlockPos transformed = this.transformForBlueprint(pos);
		return this.getBlockDataContainer().get(transformed);
	}

	public BlockPos transformForBlueprint(final BlockPos pos)
	{
		final OrbisRotation transformRot =
				this.rotation == OrbisRotation.EAST ? OrbisRotation.WEST : this.rotation == OrbisRotation.WEST ? OrbisRotation.EAST : this.rotation;
		final BlockPos rotated = RotationHelp.rotate(pos, this, transformRot);
		final IRegion rotatedRegion = RotationHelp.rotate(this, transformRot);
		return new BlockPos(rotated.getX() - rotatedRegion.getMin().getX(), rotated.getY() - rotatedRegion.getMin().getY(),
				rotated.getZ() - rotatedRegion.getMin().getZ());
	}

	public void saveRegionContent()
	{
		this.data.fetchBlocksInside(this, this.world, this.rotation);
	}

	@Override
	public boolean equals(final Object o)
	{
		final boolean flag = super.equals(o);

		if (flag)
		{
			return true;
		}

		if (!(o instanceof Blueprint))
		{
			return false;
		}

		final Blueprint b = (Blueprint) o;

		if (this.getMin().getX() == b.getMin().getX() && this.getMax().getX() == b.getMax().getX() && this.getMin().getY() == b.getMin().getY()
				&& this.getMax().getY() == b.getMax().getY() && this.getMin().getZ() == b.getMin().getZ() && this.getMax().getZ() == b.getMax()
				.getZ() && this.data == b.data)
		{
			return this.getWorld().equals(b.getWorld());
		}

		return false;
	}

	@Override
	public int getWidth()
	{
		return this.data.getWidth();
	}

	@Override
	public int getHeight()
	{
		return this.data.getHeight();
	}

	@Override
	public int getLength()
	{
		return this.data.getLength();
	}

	@Override
	public World getWorld()
	{
		return this.world;
	}

	@Override
	public BlockPos getPos()
	{
		return this.min;
	}

	@Override
	public void setPos(final BlockPos pos)
	{
		this.min = pos;
		this.max = RegionHelp.getMax(this.min, this.getWidth(), this.getHeight(), this.getLength());

		this.notifyDataChange();
	}

	@Override
	public IWorldRenderer getRenderer()
	{
		if (AetherCore.isClient() && this.renderer == null)
		{
			this.renderer = new RenderShape(this);
		}

		return this.renderer;
	}

	@Override
	public BlockPos getMin()
	{
		return this.min;
	}

	@Override
	public BlockPos getMax()
	{
		return this.max;
	}

	@Override
	public void write(final NBTTagCompound tag)
	{
		final NBTFunnel data = AetherCore.io().createFunnel(tag);

		data.setPos("min", this.min);

		tag.setString("rotation", this.rotation.name());

		data.set("data", this.data);
	}

	@Override
	public void read(final NBTTagCompound tag)
	{
		final NBTFunnel data = AetherCore.io().createFunnel(tag);

		this.min = data.getPos("min");

		this.rotation = OrbisRotation.valueOf(tag.getString("rotation"));

		this.data = data.get("data");

		this.max = RegionHelp.getMax(this.min, this.getWidth(), this.getHeight(), this.getLength());

		this.notifyDataChange();
	}

	@Override
	public void setBounds(final IRegion region)
	{
		this.min = region.getMin();
		this.max = region.getMax();

		this.notifyDataChange();
	}

	@Override
	public void setBounds(final BlockPos corner1, final BlockPos corner2)
	{
		this.min = RegionHelp.getMin(corner1, corner2);
		this.max = RegionHelp.getMax(corner1, corner2);

		this.notifyDataChange();
	}

	public BlockDataContainer getBlockDataContainer()
	{
		return this.data.getBlockDataContainer();
	}
}
