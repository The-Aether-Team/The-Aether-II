package com.gildedgames.aether.common.world.aether.island.gen;

import com.gildedgames.aether.api.world.noise.INoiseTransformer;
import com.gildedgames.orbis.lib.util.mc.NBT;
import net.minecraft.nbt.NBTTagCompound;

public class IslandVariables implements NBT
{

	/**
	 * The height level of the coast. If this is 1, it will only generate the 1st y value of the island.
	 * 2, it will generate the 1st, then the height level above it - and so on.
	 */
	private int coastHeight = 2;

	/**
	 * This dictates how spread out the edge of the coast is. The higher the number, the more spread it is.
	 */
	private double coastSpread;

	/**
	 * The scale of the lake noise. The higher this number is, the more spread out and large
	 * the lakes will be. Likewise, the lower this is, the smaller the lakes will be. HOWEVER,
	 * the lakes will also be closer together, so you'll find lots of small lakes everywhere.
	 */
	private double lakeScale = 65D;

	/**
	 * This is the spread range between the lake itself and the terrain around it. Be warned, that the more
	 * blend you add, the less lake there is. If you blend too much, there's practically no lake.
	 */
	private double lakeBlendRange = 0.5;

	/**
	 * This is where the terrain begins to recognise the area as a lake. If you put this at a low number, you'll find
	 * larger concentrations of lakes.
	 */
	private double lakeThreshold = 0.2;

	/**
	 * The depth at which the lakes dig into the island.
	 */
	private int lakeDepth = 25;

	/**
	 * This dictates the highest point the terrain can go. The noise values will be multiplied by this.
	 * If the max terrain height is lower, you'll get more plain-like terrain. Higher values will result in rolling,
	 * erratic hills.
	 */
	private int maxTerrainHeight = 80;

	/**
	 * Whether or not terraces will try to generate.
	 */
	private boolean terraces = true;

	/**
	 * This modifier adds directly to the lake noise. If the modifier is higher, there will be large lakes.
	 * If it is lower, the lakes will be smaller, or you'll have less in general.
	 */
	private double lakeConcentrationModifier = 0.0;

	/**
	 * Whether or not this island will generate snow caps for high pieces of terrain.
	 */
	private boolean snowCaps;

	/**
	 * A height sample filter which allows the variables to change the heightSample as they see fit
	 *
	 * Default filter returns original value.
	 */
	private INoiseTransformer heightSampleFilter = (heightSample) -> heightSample;

	/**
	 * Whether or not this island will generate magnetic pillars.
	 */
	private boolean magneticPillars;

	/**
	 * A max y filter which allows the variables to change the maxY as they see fit.
	 *
	 * Default filter returns the filter sample multiplied by the top height of the island, negating the cutoff point.
	 */
	private MaxYFilter maxYFilter = (bottomMaxY, filteredSample, cutoffPoint) -> bottomMaxY + ((filteredSample - cutoffPoint) * this.getMaxTerrainHeight());

	/**
	 * A filter which allows the variables to change the bottom value of the water/lakes.
	 *
	 * Default filter returns original value.
	 */
	private INoiseTransformer lakeBottomValueFilter = (lakeBottomValue) -> lakeBottomValue;

	private IslandVariables()
	{

	}

	public static IslandVariables build()
	{
		return new IslandVariables();
	}

	public INoiseTransformer getLakeBottomValueFilter()
	{
		return this.lakeBottomValueFilter;
	}

	public MaxYFilter getMaxYFilter()
	{
		return this.maxYFilter;
	}

	public boolean hasSnowCaps()
	{
		return this.snowCaps;
	}

	public INoiseTransformer getHeightSampleFilter()
	{
		return this.heightSampleFilter;
	}

	public double getLakeConcentrationModifier()
	{
		return this.lakeConcentrationModifier;
	}

	public int getLakeDepth()
	{
		return this.lakeDepth;
	}

	public boolean hasTerraces()
	{
		return this.terraces;
	}

	public int getMaxTerrainHeight()
	{
		return this.maxTerrainHeight;
	}

	public double getLakeThreshold()
	{
		return this.lakeThreshold;
	}

	public double getLakeBlendRange()
	{
		return this.lakeBlendRange;
	}

	public double getLakeScale()
	{
		return this.lakeScale;
	}

	public int getCoastHeight()
	{
		return this.coastHeight;
	}

	public double getCoastSpread()
	{
		return this.coastSpread;
	}

	public boolean hasMagneticPillars()
	{
		return this.magneticPillars;
	}

	public IslandVariables coastHeight(int coastHeight)
	{
		this.coastHeight = coastHeight;

		return this;
	}

	public IslandVariables coastSpread(double coastSpread)
	{
		this.coastSpread = coastSpread;

		return this;
	}

	public IslandVariables lakeScale(double lakeScale)
	{
		this.lakeScale = lakeScale;

		return this;
	}

	public IslandVariables lakeBlendRange(double lakeBlendRange)
	{
		this.lakeBlendRange = lakeBlendRange;

		return this;
	}

	public IslandVariables lakeThreshold(double lakeThreshold)
	{
		this.lakeThreshold = lakeThreshold;

		return this;
	}

	public IslandVariables maxTerrainHeight(int maxTerrainHeight)
	{
		this.maxTerrainHeight = maxTerrainHeight;

		return this;
	}

	public IslandVariables terraces(boolean terraces)
	{
		this.terraces = terraces;

		return this;
	}

	public IslandVariables lakeDepth(int lakeDepth)
	{
		this.lakeDepth = lakeDepth;

		return this;
	}

	public IslandVariables lakeConcentrationModifier(double lakeConcentrationModifier)
	{
		this.lakeConcentrationModifier = lakeConcentrationModifier;

		return this;
	}

	public IslandVariables heightSampleFilter(INoiseTransformer heightSampleFilter)
	{
		this.heightSampleFilter = heightSampleFilter;

		return this;
	}

	public IslandVariables snowCaps(boolean snowCaps)
	{
		this.snowCaps = snowCaps;

		return this;
	}

	public IslandVariables magneticPillars(boolean magneticPillars)
	{
		this.magneticPillars = magneticPillars;

		return this;
	}

	public IslandVariables maxYFilter(MaxYFilter maxYFilter)
	{
		this.maxYFilter = maxYFilter;

		return this;
	}

	public IslandVariables lakeBottomValueFilter(INoiseTransformer lakeBottomValueFilter)
	{
		this.lakeBottomValueFilter = lakeBottomValueFilter;

		return this;
	}

	@Override
	public void write(NBTTagCompound tag)
	{
		tag.setInteger("coastHeight", this.coastHeight);
		tag.setDouble("coastSpread", this.coastSpread);
		tag.setDouble("lakeScale", this.lakeScale);
		tag.setDouble("lakeBlendRange", this.lakeBlendRange);
		tag.setDouble("lakeThreshold", this.lakeThreshold);
		tag.setInteger("maxTerrainHeight", this.maxTerrainHeight);
		tag.setBoolean("terraces", this.terraces);
		tag.setInteger("lakeDepth", this.lakeDepth);
	}

	@Override
	public void read(NBTTagCompound tag)
	{
		this.coastHeight = tag.getInteger("coastHeight");
		this.coastSpread = tag.getDouble("coastSpread");
		this.lakeScale = tag.getDouble("lakeScale");
		this.lakeBlendRange = tag.getDouble("lakeBlendRange");
		this.lakeThreshold = tag.getDouble("lakeThreshold");
		this.maxTerrainHeight = tag.getInteger("maxTerrainHeight");
		this.terraces = tag.getBoolean("terraces");
		this.lakeDepth = tag.getInteger("lakeDepth");
	}

	public interface MaxYFilter
	{
		double transform(double bottomMaxY, double filteredSample, double cutoffPoint);
	}
}
