package com.gildedgames.aether.common.entities.genes.moa;

import com.gildedgames.aether.api.entity.genes.Gene;
import com.gildedgames.aether.common.entities.genes.util.DataGene;
import com.gildedgames.aether.common.entities.genes.util.SimpleInheritance;
import com.google.common.collect.Lists;

import java.awt.*;
import java.util.LinkedList;
import java.util.Random;

public class MoaGenePoolDataSet<T extends Gene>
{

	public static final MoaGenePoolDataSet<DataGene<Color>> KERATIN = new MoaGenePoolDataSet<>();

	public static final MoaGenePoolDataSet<DataGene<Color>> EYES = new MoaGenePoolDataSet<>();

	public static final MoaGenePoolDataSet<DataGene<Color>> FEATHERS = new MoaGenePoolDataSet<>();

	public static final MoaGenePoolDataSet<MoaMarkGene> MARKS = new MoaGenePoolDataSet<>();

	public static final MoaGenePoolDataSet<DataGene<Integer>> WING_STRENGTH = new MoaGenePoolDataSet<>();

	static
	{
		KERATIN.genes().add(new DataGene<>("moa.keratin.sky_blue", new Color(0x97c6cb), new SimpleInheritance(SimpleInheritance.Type.RECESSIVE)));
		KERATIN.genes().add(new DataGene<>("moa.keratin.deep_sky", new Color(0x717e7f), new SimpleInheritance(SimpleInheritance.Type.DOMINANT)));
		KERATIN.genes().add(new DataGene<>("moa.keratin.sand", new Color(0x7f7e71), new SimpleInheritance(SimpleInheritance.Type.RECESSIVE)));
		KERATIN.genes().add(new DataGene<>("moa.keratin.midnight_sky", new Color(0x353f64), new SimpleInheritance(SimpleInheritance.Type.DOMINANT)));
		KERATIN.genes().add(new DataGene<>("moa.keratin.swamp", new Color(0x576449), new SimpleInheritance(SimpleInheritance.Type.RECESSIVE)));
		KERATIN.genes().add(new DataGene<>("moa.keratin.royal", new Color(0x494964), new SimpleInheritance(SimpleInheritance.Type.DOMINANT)));
		KERATIN.genes().add(new DataGene<>("moa.keratin.tempest", new Color(0x644949), new SimpleInheritance(SimpleInheritance.Type.RECESSIVE)));

		EYES.genes().add(new DataGene<>("moa.eyes.alto", new Color(0xD9D9D9), new SimpleInheritance(SimpleInheritance.Type.RECESSIVE)));
		EYES.genes().add(new DataGene<>("moa.eyes.straw", new Color(0xD9BC8F), new SimpleInheritance(SimpleInheritance.Type.DOMINANT)));
		EYES.genes().add(new DataGene<>("moa.eyes.winter_hazel", new Color(0xD9CE8F), new SimpleInheritance(SimpleInheritance.Type.RECESSIVE)));
		EYES.genes().add(new DataGene<>("moa.eyes.gossip", new Color(0xB1F3A0), new SimpleInheritance(SimpleInheritance.Type.DOMINANT)));
		EYES.genes().add(new DataGene<>("moa.eyes.mint", new Color(0xA0F3C1), new SimpleInheritance(SimpleInheritance.Type.RECESSIVE)));
		EYES.genes().add(new DataGene<>("moa.eyes.ice", new Color(0xA0F3F3), new SimpleInheritance(SimpleInheritance.Type.RECESSIVE)));
		EYES.genes().add(new DataGene<>("moa.eyes.perano", new Color(0xA0C1F3), new SimpleInheritance(SimpleInheritance.Type.DOMINANT)));
		EYES.genes().add(new DataGene<>("moa.eyes.portage", new Color(0xB1A0F3), new SimpleInheritance(SimpleInheritance.Type.DOMINANT)));

		FEATHERS.genes().add(new DataGene<>("moa.feathers.black", new Color(0x1a1a1a), new SimpleInheritance(SimpleInheritance.Type.DOMINANT)));
		FEATHERS.genes().add(new DataGene<>("moa.feathers.grey", new Color(0x404040), new SimpleInheritance(SimpleInheritance.Type.RECESSIVE)));
		FEATHERS.genes().add(new DataGene<>("moa.feathers.white", new Color(0xE6E6E6), new SimpleInheritance(SimpleInheritance.Type.DOMINANT)));
		FEATHERS.genes().add(new DataGene<>("moa.feathers.iroko", new Color(0x4d3822), new SimpleInheritance(SimpleInheritance.Type.RECESSIVE)));
		FEATHERS.genes().add(new DataGene<>("moa.feathers.orange", new Color(0xd99e5f), new SimpleInheritance(SimpleInheritance.Type.DOMINANT)));
		FEATHERS.genes().add(new DataGene<>("moa.feathers.lisbon_brown", new Color(0x4d492b), new SimpleInheritance(SimpleInheritance.Type.RECESSIVE)));
		FEATHERS.genes().add(new DataGene<>("moa.feathers.yellow", new Color(0xd9c65d), new SimpleInheritance(SimpleInheritance.Type.DOMINANT)));
		FEATHERS.genes().add(new DataGene<>("moa.feathers.mallard", new Color(0x3f4d36), new SimpleInheritance(SimpleInheritance.Type.RECESSIVE)));
		FEATHERS.genes().add(new DataGene<>("moa.feathers.lime_green", new Color(0xc0f39b), new SimpleInheritance(SimpleInheritance.Type.DOMINANT)));
		FEATHERS.genes().add(new DataGene<>("moa.feathers.everglade", new Color(0x304d3b), new SimpleInheritance(SimpleInheritance.Type.RECESSIVE)));
		FEATHERS.genes().add(new DataGene<>("moa.feathers.green", new Color(0x79f39a), new SimpleInheritance(SimpleInheritance.Type.DOMINANT)));
		FEATHERS.genes().add(new DataGene<>("moa.feathers.plantation", new Color(0x364d4d), new SimpleInheritance(SimpleInheritance.Type.RECESSIVE)));
		FEATHERS.genes().add(new DataGene<>("moa.feathers.sky_blue", new Color(0xaa7f3f3), new SimpleInheritance(SimpleInheritance.Type.DOMINANT)));
		FEATHERS.genes().add(new DataGene<>("moa.feathers.cloud_burst", new Color(0x363e4d), new SimpleInheritance(SimpleInheritance.Type.RECESSIVE)));
		FEATHERS.genes().add(new DataGene<>("moa.feathers.blue", new Color(0xa0cdf3), new SimpleInheritance(SimpleInheritance.Type.DOMINANT)));
		FEATHERS.genes().add(new DataGene<>("moa.feathers.port_gore", new Color(0x3a364d), new SimpleInheritance(SimpleInheritance.Type.RECESSIVE)));
		FEATHERS.genes().add(new DataGene<>("moa.feathers.purple", new Color(0xaf9ef3), new SimpleInheritance(SimpleInheritance.Type.DOMINANT)));
		FEATHERS.genes().add(new DataGene<>("moa.feathers.bossanova", new Color(0x48364d), new SimpleInheritance(SimpleInheritance.Type.RECESSIVE)));
		FEATHERS.genes().add(new DataGene<>("moa.feathers.pink", new Color(0xe199f3), new SimpleInheritance(SimpleInheritance.Type.DOMINANT)));
		FEATHERS.genes().add(new DataGene<>("moa.feathers.livid_brown", new Color(0x4d3643), new SimpleInheritance(SimpleInheritance.Type.RECESSIVE)));
		FEATHERS.genes().add(new DataGene<>("moa.feathers.puse", new Color(0xf3aad6), new SimpleInheritance(SimpleInheritance.Type.DOMINANT)));
		FEATHERS.genes().add(new DataGene<>("moa.feathers.deep_red", new Color(0x4d2222), new SimpleInheritance(SimpleInheritance.Type.RECESSIVE)));
		FEATHERS.genes().add(new DataGene<>("moa.feathers.red", new Color(0xf34949), new SimpleInheritance(SimpleInheritance.Type.DOMINANT)));

		MARKS.genes().add(new MoaMarkGene("moa.marks.spots", "spots", new SimpleInheritance(SimpleInheritance.Type.DOMINANT)));
		MARKS.genes().add(new MoaMarkGene("moa.marks.circles", "circles", new SimpleInheritance(SimpleInheritance.Type.DOMINANT)));
		MARKS.genes().add(new MoaMarkGene("moa.marks.curves", "curves", new SimpleInheritance(SimpleInheritance.Type.DOMINANT)));
		MARKS.genes().add(new MoaMarkGene("moa.marks.ladder", "ladder", new SimpleInheritance(SimpleInheritance.Type.DOMINANT)));
		MARKS.genes().add(new MoaMarkGene("moa.marks.lines", "lines", new SimpleInheritance(SimpleInheritance.Type.DOMINANT)));

		WING_STRENGTH.genes().add(new DataGene<>("moa.wing_strength.weak", 1, new SimpleInheritance(SimpleInheritance.Type.RANDOM)));
		WING_STRENGTH.genes().add(new DataGene<>("moa.wing_strength.normal", 2, new SimpleInheritance(SimpleInheritance.Type.RANDOM)));
		WING_STRENGTH.genes().add(new DataGene<>("moa.wing_strength.strong", 3, new SimpleInheritance(SimpleInheritance.Type.RANDOM)));
		WING_STRENGTH.genes().add(new DataGene<>("moa.wing_strength.excellent", 4, new SimpleInheritance(SimpleInheritance.Type.RANDOM)));
	}

	private final LinkedList<T> genes = Lists.newLinkedList();

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

}
