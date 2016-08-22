package com.gildedgames.aether.common.entities.genes.moa;

import com.gildedgames.aether.api.genes.IGenePool;
import com.gildedgames.aether.api.genes.BiologyUtil;
import com.gildedgames.aether.api.genes.GeneRegion;
import com.gildedgames.aether.api.capabilites.AetherCapabilities;
import com.gildedgames.aether.api.genes.util.DataGene;
import com.gildedgames.aether.common.entities.moa.EntityMoa;
import com.google.common.collect.Lists;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.tileentity.TileEntity;

import java.awt.*;
import java.util.List;
import java.util.Random;

public class MoaGenePool implements IGenePool
{

	private static final DataParameter<Integer> SEED = EntityDataManager.createKey(EntityMoa.class, DataSerializers.VARINT);

	private static final DataParameter<Integer> FATHER_SEED = EntityDataManager.createKey(EntityMoa.class, DataSerializers.VARINT);

	private static final DataParameter<Integer> MOTHER_SEED = EntityDataManager.createKey(EntityMoa.class, DataSerializers.VARINT);

	private static final DataParameter<Boolean> SHOULD_RETRANSFORM = EntityDataManager.createKey(EntityMoa.class, DataSerializers.BOOLEAN);

	private GeneRegion<DataGene<Color>> keratin, feathers, eyes;

	private GeneRegion<MoaMarkGene> marks;

	private GeneRegion<DataGene<Integer>> wingStrength;

	private List<GeneRegion> geneRegions;

	private Entity entity;

	public static MoaGenePool get(EntityMoa entity)
	{
		return (MoaGenePool) entity.getCapability(AetherCapabilities.GENE_POOL, null);
	}

	public static MoaGenePool get(ItemStack stack)
	{
		return (MoaGenePool) stack.getCapability(AetherCapabilities.GENE_POOL, null);
	}

	public static MoaGenePool get(TileEntity te)
	{
		return (MoaGenePool) te.getCapability(AetherCapabilities.GENE_POOL, null);
	}

	protected MoaGenePool()
	{

	}

	public MoaGenePool(Entity entity)
	{
		this.entity = entity;

		EntityDataManager dataManager = this.entity.getDataManager();

		dataManager.register(SEED, 0);
		dataManager.register(FATHER_SEED, 0);
		dataManager.register(MOTHER_SEED, 0);
		dataManager.register(SHOULD_RETRANSFORM, false);
	}

	@Override
	public void onUpdate()
	{
		if (this.shouldRetransform() && this.getEntity().worldObj.isRemote)
		{
			if (this.getSeed() == this.getFatherSeed() && this.getSeed() == this.getMotherSeed())
			{
				this.transformFromSeed(this.getSeed());
			}
			else
			{
				this.transformFromParents(this.getSeed(), this.getFatherSeed(), this.getMotherSeed());
			}

			this.setShouldRetransform(false);
		}
	}

	@Override
	public Entity getEntity()
	{
		return this.entity;
	}

	@Override
	public void transformFromSeed(int seed)
	{
		this.setSeed(seed);
		this.setFatherSeed(seed);
		this.setMotherSeed(seed);

		Random r = new Random(this.getSeed());

		this.keratin = new GeneRegion<>("moaBiology.keratin", MoaGenePoolDataSet.KERATIN.pickRandom(r));
		this.feathers = new GeneRegion<>("moaBiology.feathers", MoaGenePoolDataSet.FEATHERS.pickRandom(r));
		this.eyes = new GeneRegion<>("moaBiology.eyes", MoaGenePoolDataSet.EYES.pickRandom(r));
		this.marks = new GeneRegion<>("moaBiology.marks", MoaGenePoolDataSet.MARKS.pickRandom(r));

		this.wingStrength = new GeneRegion<>("moaBiology.wingStrength", MoaGenePoolDataSet.WING_STRENGTH.pickRandom(r));

		this.setShouldRetransform(true);
	}

	public List<GeneRegion> getGeneRegions()
	{
		if (this.geneRegions == null)
		{
			this.geneRegions = Lists.newArrayList();

			this.geneRegions.add(this.keratin);
			this.geneRegions.add(this.feathers);
			this.geneRegions.add(this.eyes);
			this.geneRegions.add(this.marks);
			this.geneRegions.add(this.wingStrength);
		}

		return this.geneRegions;
	}

	@Override
	public void transformFromParents(int seed, int fatherSeed, int motherSeed)
	{
		this.setSeed(seed);
		this.setFatherSeed(fatherSeed);
		this.setMotherSeed(motherSeed);

		MoaGenePool father = new UntrackedMoaGenePool();
		MoaGenePool mother = new UntrackedMoaGenePool();

		father.transformFromSeed(this.getFatherSeed());
		mother.transformFromSeed(this.getMotherSeed());

		Random r = new Random(this.getSeed());

		this.keratin = new GeneRegion<>("moaBiology.keratin", BiologyUtil.evaluateInheritedGene(r, father.keratin, mother.keratin));
		this.feathers = new GeneRegion<>("moaBiology.feathers", BiologyUtil.evaluateInheritedGene(r, father.feathers, mother.feathers));
		this.eyes = new GeneRegion<>("moaBiology.eyes", BiologyUtil.evaluateInheritedGene(r, father.eyes, mother.eyes));
		this.marks = new GeneRegion<>("moaBiology.marks", BiologyUtil.evaluateInheritedGene(r, father.marks, mother.marks));

		this.wingStrength = new GeneRegion<>("moaBiology.wingStrength", BiologyUtil.evaluateInheritedGene(r, father.wingStrength, mother.wingStrength));

		this.setShouldRetransform(true);
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

	public GeneRegion<DataGene<Integer>> getWingStrength() { return this.wingStrength; }

	@Override
	public int getSeed()
	{
		return this.entity.getDataManager().get(MoaGenePool.SEED);
	}

	@Override
	public int getFatherSeed()
	{
		return this.entity.getDataManager().get(MoaGenePool.FATHER_SEED);
	}

	@Override
	public int getMotherSeed()
	{
		return this.entity.getDataManager().get(MoaGenePool.MOTHER_SEED);
	}

	protected void setSeed(int seed)
	{
		this.entity.getDataManager().set(MoaGenePool.SEED, seed);
	}

	protected void setFatherSeed(int seed)
	{
		this.entity.getDataManager().set(MoaGenePool.FATHER_SEED, seed);
	}

	protected void setMotherSeed(int seed)
	{
		this.entity.getDataManager().set(MoaGenePool.MOTHER_SEED, seed);
	}

	protected boolean shouldRetransform()
	{
		return this.entity.getDataManager().get(MoaGenePool.SHOULD_RETRANSFORM);
	}

	protected void setShouldRetransform(boolean flag)
	{
		this.entity.getDataManager().set(MoaGenePool.SHOULD_RETRANSFORM, flag);
	}
	
}
