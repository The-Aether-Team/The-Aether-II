package com.gildedgames.aether.common.entities.genes.moa;

import com.gildedgames.aether.api.entity.genes.Gene;
import com.gildedgames.aether.common.entities.genes.util.DataGene;
import com.gildedgames.aether.common.entities.genes.util.SimpleInheritance;
import com.google.common.collect.Lists;

import java.awt.*;
import java.util.LinkedList;
import java.util.Random;

// TODO: Replace with enums, this system is not upgrade-friendly
@Deprecated
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
		EYES.genes().add(new DataGene<>("moa.eyes.perfume", new Color(0xE2A0F3), new SimpleInheritance(SimpleInheritance.Type.RECESSIVE)));
		EYES.genes().add(new DataGene<>("moa.eyes.illusion", new Color(0xF3A0D2), new SimpleInheritance(SimpleInheritance.Type.RECESSIVE)));
		EYES.genes().add(new DataGene<>("moa.eyes.wewak", new Color(0xF3A0A0), new SimpleInheritance(SimpleInheritance.Type.RECESSIVE)));

		FEATHERS.genes().add(new DataGene<>("moa.feathers.tundora", new Color(0x4D4D4D), new SimpleInheritance(SimpleInheritance.Type.DOMINANT)));
		FEATHERS.genes().add(new DataGene<>("moa.feathers.iroko", new Color(0x4D3C22), new SimpleInheritance(SimpleInheritance.Type.DOMINANT)));
		FEATHERS.genes().add(new DataGene<>("moa.feathers.lisbon_brown", new Color(0x4D4622), new SimpleInheritance(SimpleInheritance.Type.RECESSIVE)));
		FEATHERS.genes().add(new DataGene<>("moa.feathers.mallard", new Color(0x2A4D22), new SimpleInheritance(SimpleInheritance.Type.RECESSIVE)));
		FEATHERS.genes().add(new DataGene<>("moa.feathers.everglad", new Color(0x224D33), new SimpleInheritance(SimpleInheritance.Type.RECESSIVE)));
		FEATHERS.genes().add(new DataGene<>("moa.feathers.plantation", new Color(0x224D4D), new SimpleInheritance(SimpleInheritance.Type.RECESSIVE)));
		FEATHERS.genes().add(new DataGene<>("moa.feathers.cloud_burst", new Color(0x22324D), new SimpleInheritance(SimpleInheritance.Type.RECESSIVE)));
		FEATHERS.genes().add(new DataGene<>("moa.feathers.port_gore", new Color(0x2A224D), new SimpleInheritance(SimpleInheritance.Type.DOMINANT)));
		FEATHERS.genes().add(new DataGene<>("moa.feathers.bossanova", new Color(0x44224D), new SimpleInheritance(SimpleInheritance.Type.DOMINANT)));
		FEATHERS.genes().add(new DataGene<>("moa.feathers.livid_brown", new Color(0x4D223B), new SimpleInheritance(SimpleInheritance.Type.RECESSIVE)));
		FEATHERS.genes().add(new DataGene<>("moa.feathers.cocoa", new Color(0x4D2222), new SimpleInheritance(SimpleInheritance.Type.DOMINANT)));

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

}
