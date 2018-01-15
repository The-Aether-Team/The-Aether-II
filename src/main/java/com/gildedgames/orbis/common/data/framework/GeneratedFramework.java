package com.gildedgames.orbis.common.data.framework;

import com.gildedgames.aether.api.orbis_core.data.BlueprintData;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;

public class GeneratedFramework
{

	private final List<BlueprintData> blueprints;

	//private final BlockPos min;

	//private final BlockPos max;

	public GeneratedFramework(List<BlueprintData> blueprints)
	{
		this.blueprints = blueprints;
	}

	public int getWidth()
	{
		return 0;//return RegionHelp.getWidth(this.min, this.max);
	}

	public int getHeight()
	{
		return 0; //return RegionHelp.getHeight(this.min, this.max);
	}

	public int getLength()
	{
		return 0;//return RegionHelp.getLength(this.min, this.max);
	}

	public void create(BlockPos pos, World world, Rotation rotation)
	{
		// TODO Auto-generated method stub

	}

//	public List<Entrance> entrances()
//	{
//		// TODO Auto-generated method stub
//		return null;
//	}

}
