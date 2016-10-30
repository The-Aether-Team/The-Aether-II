package com.gildedgames.aether.common.entities.genes.moa;

import com.gildedgames.aether.api.genes.Gene;
import com.gildedgames.aether.common.entities.genes.util.DataGene;
import com.gildedgames.aether.common.entities.genes.util.SimpleInheritance;
import com.google.common.collect.Lists;

import java.awt.*;
import java.util.LinkedList;
import java.util.Random;

public class MoaGenePoolDataSet<T extends Gene>
{

	private LinkedList<T> genes = Lists.newLinkedList();

	private MoaGenePoolDataSet()
	{

	}

	private LinkedList<T> genes()
	{
		return this.genes;
	}

	public T pickRandom(Random random)
	{
		return this.genes().get(random.nextInt(this.genes().size()));
	}

	public static final MoaGenePoolDataSet<DataGene<Color>> KERATIN = new MoaGenePoolDataSet<>();
	public static final MoaGenePoolDataSet<DataGene<Color>> EYES = new MoaGenePoolDataSet<>();
	public static final MoaGenePoolDataSet<DataGene<Color>> FEATHERS = new MoaGenePoolDataSet<>();
	public static final MoaGenePoolDataSet<MoaMarkGene> MARKS = new MoaGenePoolDataSet<>();

	public static final MoaGenePoolDataSet<DataGene<Integer>> WING_STRENGTH = new MoaGenePoolDataSet<>();

	static
	{
		KERATIN.genes().add(new DataGene<>("moa.keratin.skyBlue", new Color(0x97c6cb), new SimpleInheritance(SimpleInheritance.Type.RECESSIVE)));
		KERATIN.genes().add(new DataGene<>("moa.keratin.deepSky", new Color(0x717e7f), new SimpleInheritance(SimpleInheritance.Type.DOMINANT)));
		KERATIN.genes().add(new DataGene<>("moa.keratin.sand", new Color(0x7f7e71), new SimpleInheritance(SimpleInheritance.Type.RECESSIVE)));
		KERATIN.genes().add(new DataGene<>("moa.keratin.midnightSky", new Color(0x353f64), new SimpleInheritance(SimpleInheritance.Type.DOMINANT)));
		KERATIN.genes().add(new DataGene<>("moa.keratin.swamp", new Color(0x576449), new SimpleInheritance(SimpleInheritance.Type.RECESSIVE)));
		KERATIN.genes().add(new DataGene<>("moa.keratin.royal", new Color(0x494964), new SimpleInheritance(SimpleInheritance.Type.DOMINANT)));
		KERATIN.genes().add(new DataGene<>("moa.keratin.tempest", new Color(0x644949), new SimpleInheritance(SimpleInheritance.Type.RECESSIVE)));

		EYES.genes().add(new DataGene<>("moa.eyes.enchanted", new Color(0xbfb62b), new SimpleInheritance(SimpleInheritance.Type.RECESSIVE)));
		EYES.genes().add(new DataGene<>("moa.eyes.navy", new Color(0x2354a5), new SimpleInheritance(SimpleInheritance.Type.RECESSIVE)));
		EYES.genes().add(new DataGene<>("moa.eyes.crimson", new Color(0x9e1d1d), new SimpleInheritance(SimpleInheritance.Type.RECESSIVE)));
		EYES.genes().add(new DataGene<>("moa.eyes.cream", new Color(0xe8e9df), new SimpleInheritance(SimpleInheritance.Type.DOMINANT)));
		EYES.genes().add(new DataGene<>("moa.eyes.emerald", new Color(0x41b543), new SimpleInheritance(SimpleInheritance.Type.RECESSIVE)));

		FEATHERS.genes().add(new DataGene<>("moa.feathers.cream", new Color(0xecebe1), new SimpleInheritance(SimpleInheritance.Type.RECESSIVE)));
		FEATHERS.genes().add(new DataGene<>("moa.feathers.midnightSky", new Color(0x53576a), new SimpleInheritance(SimpleInheritance.Type.DOMINANT)));
		FEATHERS.genes().add(new DataGene<>("moa.feathers.dawn", new Color(0x83c4e2), new SimpleInheritance(SimpleInheritance.Type.RECESSIVE)));
		FEATHERS.genes().add(new DataGene<>("moa.feathers.nightsPeak", new Color(0x484345), new SimpleInheritance(SimpleInheritance.Type.DOMINANT)));
		FEATHERS.genes().add(new DataGene<>("moa.feathers.sakura", new Color(0xf2b4ce), new SimpleInheritance(SimpleInheritance.Type.RECESSIVE)));
		FEATHERS.genes().add(new DataGene<>("moa.feathers.mud", new Color(0xab7a5c), new SimpleInheritance(SimpleInheritance.Type.DOMINANT)));
		FEATHERS.genes().add(new DataGene<>("moa.feathers.enchanted", new Color(0xffc342), new SimpleInheritance(SimpleInheritance.Type.DOMINANT)));

		MARKS.genes().add(new MoaMarkGene("moa.marks.spots", "spots", new SimpleInheritance(SimpleInheritance.Type.DOMINANT)));
		MARKS.genes().add(new MoaMarkGene("moa.marks.circles", "circles", new SimpleInheritance(SimpleInheritance.Type.DOMINANT)));
		MARKS.genes().add(new MoaMarkGene("moa.marks.curves", "curves", new SimpleInheritance(SimpleInheritance.Type.DOMINANT)));
		MARKS.genes().add(new MoaMarkGene("moa.marks.ladder", "ladder", new SimpleInheritance(SimpleInheritance.Type.DOMINANT)));
		MARKS.genes().add(new MoaMarkGene("moa.marks.lines", "lines", new SimpleInheritance(SimpleInheritance.Type.DOMINANT)));

		WING_STRENGTH.genes().add(new DataGene<>("moa.wingStrength.weak", 1, new SimpleInheritance(SimpleInheritance.Type.RANDOM)));
		WING_STRENGTH.genes().add(new DataGene<>("moa.wingStrength.normal", 2, new SimpleInheritance(SimpleInheritance.Type.RANDOM)));
		WING_STRENGTH.genes().add(new DataGene<>("moa.wingStrength.strong", 3, new SimpleInheritance(SimpleInheritance.Type.RANDOM)));
		WING_STRENGTH.genes().add(new DataGene<>("moa.wingStrength.excellent", 4, new SimpleInheritance(SimpleInheritance.Type.RANDOM)));
	}

}
