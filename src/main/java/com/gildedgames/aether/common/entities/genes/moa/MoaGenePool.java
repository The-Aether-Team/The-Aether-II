package com.gildedgames.aether.common.entities.genes.moa;

import com.gildedgames.aether.api.genes.GeneRegion;
import com.gildedgames.aether.api.genes.IGeneStorage;
import com.gildedgames.aether.api.util.NBT;
import com.gildedgames.aether.common.entities.genes.util.DataGene;
import com.gildedgames.aether.common.entities.genes.util.GenePool;
import com.gildedgames.aether.common.entities.genes.util.GeneUtil;
import com.gildedgames.aether.common.entities.genes.util.SimpleGeneStorage;
import com.google.common.collect.Lists;
import net.minecraft.nbt.NBTTagCompound;

import java.awt.*;
import java.util.List;
import java.util.Random;

public class MoaGenePool extends GenePool implements NBT
{

	private GeneRegion<DataGene<Color>> keratin, feathers, eyes;

	private GeneRegion<MoaMarkGene> marks;

	private GeneRegion<DataGene<Integer>> wingStrength;

	public MoaGenePool()
	{
		this(new SimpleGeneStorage());
	}

	public MoaGenePool(IGeneStorage storage)
	{
		super(storage);
	}

	@Override
	public void transformFromSeed(int seed)
	{
		this.getStorage().setSeed(seed);
		this.getStorage().setFatherSeed(seed);
		this.getStorage().setMotherSeed(seed);

		Random r = new Random(this.getStorage().getSeed());

		this.keratin = new GeneRegion<>("moaBiology.keratin", MoaGenePoolDataSet.KERATIN.pickRandom(r));
		this.feathers = new GeneRegion<>("moaBiology.feathers", MoaGenePoolDataSet.FEATHERS.pickRandom(r));
		this.eyes = new GeneRegion<>("moaBiology.eyes", MoaGenePoolDataSet.EYES.pickRandom(r));
		this.marks = new GeneRegion<>("moaBiology.marks", MoaGenePoolDataSet.MARKS.pickRandom(r));

		this.wingStrength = new GeneRegion<>("moaBiology.wingStrength", MoaGenePoolDataSet.WING_STRENGTH.pickRandom(r));

		this.getStorage().setShouldRetransform(true);
	}

	@Override
	public void transformFromParents(int seed, int fatherSeed, int motherSeed)
	{
		this.getStorage().setSeed(seed);
		this.getStorage().setFatherSeed(fatherSeed);
		this.getStorage().setMotherSeed(motherSeed);

		MoaGenePool father = new MoaGenePool(new SimpleGeneStorage());
		MoaGenePool mother = new MoaGenePool(new SimpleGeneStorage());

		father.transformFromSeed(this.getStorage().getFatherSeed());
		mother.transformFromSeed(this.getStorage().getMotherSeed());

		Random r = new Random(this.getStorage().getSeed());

		this.keratin = new GeneRegion<>("moaBiology.keratin", GeneUtil.evaluateInheritedGene(r, father.keratin, mother.keratin));
		this.feathers = new GeneRegion<>("moaBiology.feathers", GeneUtil.evaluateInheritedGene(r, father.feathers, mother.feathers));
		this.eyes = new GeneRegion<>("moaBiology.eyes", GeneUtil.evaluateInheritedGene(r, father.eyes, mother.eyes));
		this.marks = new GeneRegion<>("moaBiology.marks", GeneUtil.evaluateInheritedGene(r, father.marks, mother.marks));

		this.wingStrength = new GeneRegion<>("moaBiology.wingStrength", GeneUtil.evaluateInheritedGene(r, father.wingStrength, mother.wingStrength));

		this.getStorage().setShouldRetransform(true);
	}

	@Override
	public List<GeneRegion> createGeneRegions()
	{
		List<GeneRegion> gr = Lists.newArrayList();

		gr.add(this.keratin);
		gr.add(this.feathers);
		gr.add(this.eyes);
		gr.add(this.marks);
		gr.add(this.wingStrength);

		return gr;
	}

	public GeneRegion<DataGene<Color>> getKeratin()
	{
		return this.keratin;
	}

	public GeneRegion<DataGene<Color>> getFeathers()
	{
		return this.feathers;
	}

	public GeneRegion<DataGene<Color>> getEyes()
	{
		return this.eyes;
	}

	public GeneRegion<MoaMarkGene> getMarks()
	{
		return this.marks;
	}

	public GeneRegion<DataGene<Integer>> getWingStrength()
	{
		return this.wingStrength;
	}

	@Override
	public void write(NBTTagCompound output)
	{
		this.getStorage().write(output);
	}

	@Override
	public void read(NBTTagCompound input)
	{
		this.getStorage().read(input);

		GeneUtil.transformFromStorage(this, this.getStorage());
	}
}
