package com.gildedgames.aether.common.entities.biology.moa;

import com.gildedgames.aether.api.biology.Gene;
import com.gildedgames.aether.common.entities.biology.util.DataGene;
import com.gildedgames.aether.common.entities.biology.util.SimpleInheritance;
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
	public static final MoaGenePoolDataSet<DataGene<Color>> PATTERNS = new MoaGenePoolDataSet<>();
	public static final MoaGenePoolDataSet<DataGene<Color>> FEATHERS = new MoaGenePoolDataSet<>();
	public static final MoaGenePoolDataSet<MoaMarkGene> MARKS = new MoaGenePoolDataSet<>();

	public static final MoaGenePoolDataSet<DataGene<Integer>> WING_STRENGTH = new MoaGenePoolDataSet<>();

	static
	{
		KERATIN.genes().add(new DataGene<>("moaBiology.keratin.skyBlue", new Color(0x97c6cb), new SimpleInheritance(SimpleInheritance.Type.RECESSIVE)));
		KERATIN.genes().add(new DataGene<>("moaBiology.keratin.deepSky", new Color(0x717e7f), new SimpleInheritance(SimpleInheritance.Type.DOMINANT)));
		KERATIN.genes().add(new DataGene<>("moaBiology.keratin.sand", new Color(0x7f7e71), new SimpleInheritance(SimpleInheritance.Type.DOMINANT)));

		EYES.genes().add(new DataGene<>("moaBiology.eyes.enchanted", new Color(0xbfb62b), new SimpleInheritance(SimpleInheritance.Type.RECESSIVE)));
		EYES.genes().add(new DataGene<>("moaBiology.eyes.navy", new Color(0x2354a5), new SimpleInheritance(SimpleInheritance.Type.DOMINANT)));
		EYES.genes().add(new DataGene<>("moaBiology.eyes.crimson", new Color(0x9e1d1d), new SimpleInheritance(SimpleInheritance.Type.DOMINANT)));
		EYES.genes().add(new DataGene<>("moaBiology.eyes.cream", new Color(0xe8e9df), new SimpleInheritance(SimpleInheritance.Type.DOMINANT)));

		PATTERNS.genes().add(new DataGene<>("moaBiology.patterns.skyBlue", new Color(0x97c6cb).darker(), new SimpleInheritance(SimpleInheritance.Type.RECESSIVE)));
		PATTERNS.genes().add(new DataGene<>("moaBiology.patterns.deepSky", new Color(0x717e7f).darker(), new SimpleInheritance(SimpleInheritance.Type.DOMINANT)));
		PATTERNS.genes().add(new DataGene<>("moaBiology.patterns.sand", new Color(0x7f7e71).darker(), new SimpleInheritance(SimpleInheritance.Type.DOMINANT)));

		FEATHERS.genes().add(new DataGene<>("moaBiology.feathers.cream", new Color(0xecebe1), new SimpleInheritance(SimpleInheritance.Type.RECESSIVE)));
		FEATHERS.genes().add(new DataGene<>("moaBiology.feathers.midnightSky", new Color(0x53576a), new SimpleInheritance(SimpleInheritance.Type.DOMINANT)));
		FEATHERS.genes().add(new DataGene<>("moaBiology.feathers.dawn", new Color(0x83c4e2), new SimpleInheritance(SimpleInheritance.Type.RECESSIVE)));

		MARKS.genes().add(new MoaMarkGene("moaBiology.marks.spots", "spots", new SimpleInheritance(SimpleInheritance.Type.DOMINANT)));
		MARKS.genes().add(new MoaMarkGene("moaBiology.marks.circles", "circles", new SimpleInheritance(SimpleInheritance.Type.DOMINANT)));
		MARKS.genes().add(new MoaMarkGene("moaBiology.marks.curves", "curves", new SimpleInheritance(SimpleInheritance.Type.DOMINANT)));
		MARKS.genes().add(new MoaMarkGene("moaBiology.marks.ladder", "ladder", new SimpleInheritance(SimpleInheritance.Type.DOMINANT)));
		MARKS.genes().add(new MoaMarkGene("moaBiology.marks.lines", "lines", new SimpleInheritance(SimpleInheritance.Type.DOMINANT)));

		WING_STRENGTH.genes().add(new DataGene<>("moaBiology.wingStrength.weak", 1, new SimpleInheritance(SimpleInheritance.Type.RANDOM)));
		WING_STRENGTH.genes().add(new DataGene<>("moaBiology.wingStrength.normal", 2, new SimpleInheritance(SimpleInheritance.Type.RANDOM)));
		WING_STRENGTH.genes().add(new DataGene<>("moaBiology.wingStrength.strong", 3, new SimpleInheritance(SimpleInheritance.Type.RANDOM)));
		WING_STRENGTH.genes().add(new DataGene<>("moaBiology.wingStrength.excellent", 4, new SimpleInheritance(SimpleInheritance.Type.RANDOM)));
	}

}
