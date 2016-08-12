package com.gildedgames.aether.common.entities.moa;

import com.gildedgames.aether.common.AetherCore;
import net.minecraft.client.resources.I18n;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import java.util.Random;

public class MoaGenetics
{

	private String name;
	
	public int geneticSeed, bodyColor, legColor, beakColor, eyeColor, markColor, jumps;
	
	public float spawnChance;
	
	public ResourceLocation body, legs, beak, eye, teeth, tongue, markBack, markHead, markTail, markWing, saddle;
	
	private static String moaDirectory = "textures/entities/moa/";
	
	public static BaseGenetics[] baseGenetics = new BaseGenetics[]
	{
		new BaseGenetics("moaColor.gold", 0xffc550, 8, 20, 1.0F),
		new BaseGenetics("moaColor.dullBrown", 0xb2aca5, 7, 19, 1.0F),
		new BaseGenetics("moaColor.orange", 0xb65519, 6, 17, 1.0F),
		new BaseGenetics("moaColor.earth", 0x608fbf, 5, 15, 1.0F),
		new BaseGenetics("moaColor.darkEarth", 0xcea096, 6, 15, 1.0F),
		new BaseGenetics("moaColor.tan", 0x91636a, 6, 15, 1.0F),
		new BaseGenetics("moaColor.darkTan", 0x754573, 6, 15, 1.0F),
		new BaseGenetics("moaColor.purple", 0x91636a, 7, 16, 1.0F),
		new BaseGenetics("moaColor.darkBrown", 0x5e3e46, 6, 13, 1.0F),
		new BaseGenetics("moaColor.darkPurple", 0x642449, 3, 9, 1.0F),
		new BaseGenetics("moaColor.gray", 0x796f71, 2, 6, 1.0F),
		new BaseGenetics("moaColor.swamp", 0x444035, 0, 5, 1.0F),
		new BaseGenetics("moaColor.lightPink", 0xe5cedd, 0, 8, 1.0F),
		new BaseGenetics("moaColor.lightPurple", 0xc4b1ca, 0, 7, 1.0F),
		new BaseGenetics("moaColor.lightSwamp", 0x6a7e53, 0, 7, 1.0F),
		new BaseGenetics("moaColor.dullYellow", 0xb0ac87, 0, 3, 1.0F),
		new BaseGenetics("moaColor.yellow", 0xc1bf71, 0, 5, 1.0F),
		new BaseGenetics("moaColor.darkAqua", 0x365049, 0, 10, 1.0F),
		new BaseGenetics("moaColor.lightGray", 0xb4b9a2, 0, 7, 1.0F),
		new BaseGenetics("moaColor.lightTan", 0xeadddf, 0, 3, 1.0F),
		new BaseGenetics("moaColor.burgundy", 0x965066, 0, 9, 1.0F),
		new BaseGenetics("moaColor.dullSwamp", 0x424950, 0, 5, 1.0F),
		new BaseGenetics("moaColor.dullBlue", 0x6a8195, 0, 3, 1.0F),
		new BaseGenetics("moaColor.blueGray", 0x8291a5, 0, 4, 1.0F),
		new BaseGenetics("moaColor.yellowTan", 0xd5d1a5, 0, 3, 1.0F),
		new BaseGenetics("moaColor.aqua", 0x339289, 0, 4, 1.0F),
		new BaseGenetics("moaColor.lightAqua", 0x5ea7ac, 0, 6, 1.0F),
		new BaseGenetics("moaColor.blue", 0x6384b1, 0, 3, 1.0F),
		new BaseGenetics("moaColor.highTan", 0xf97d76, 0, 4, 1.0F),
		new BaseGenetics("moaColor.jungle", 0x567531, 0, 4, 1.0F),
		new BaseGenetics("moaColor.dullPurple", 0x4b3750, 0, 3, 1.0F)
	};
	
	public static int[] beakLegColors = new int[]
	{
		0x473939,
		0x453947,
		0x393d47,
		0x394746,
		0x394739,
		0x474639,
		0xc671b2,
		0x96d3c5,
		0xd33434,
		0x296012
	};
	
	public static int[] markColors = new int[]
	{
		0xb1e2d8,
		0x1b272b,
		0x4b364f,
		0x1b3f0f,
		0xd294d3,
		0xefd44a,
		0x281709
	};
	
	public static int[] eyeColors = new int[]
	{
		0xbf1111,
		0xddcb23,
		0x81f25c,
		0x669dd1,
		0xab45ad
	};
	
	public static ResourceLocation[] backMarks = new ResourceLocation[]
	{
		getMark("back", "curves"),
		getMark("back", "ladder"),
		getMark("back", "lines"),
		getMark("back", "spots"),
		getMark("back", "circles")
	};
	
	public static ResourceLocation[] headMarks = new ResourceLocation[]
	{
		getMark("head", "curves"),
		getMark("head", "lines"),
		getMark("head", "spots"),
		getMark("head", "arrow"),
		getMark("head", "aviator"),
		getMark("head", "headphones"),
		getMark("head", "sound"),
		getMark("head", "trident")
	};
	
	public static ResourceLocation[] tailMarks = new ResourceLocation[]
	{
		getMark("tail", "plus"),
		getMark("tail", "stripes"),
		getMark("tail", "warp"),
		getMark("tail", "wave"),
		getMark("tail", "zagg")
	};
	
	public static ResourceLocation[] wingMarks = new ResourceLocation[]
	{
		getMark("wing", "curves"),
		getMark("wing", "ladder"),
		getMark("wing", "lines"),
		getMark("wing", "spots")
	};
	
	public MoaGenetics()
	{
		this.body = getBaseTexture("body");
		this.legs = getBaseTexture("legs");
		this.beak = getBaseTexture("beak");
		this.eye = getBaseTexture("eyes");
		this.teeth = getBaseTexture("teeth");
		this.tongue = getBaseTexture("tongue");
		this.saddle = getBaseTexture("saddle");
	}

	public String getUnlocalizedName()
	{
		return this.name;
	}

	public String getLocalizedName()
	{
		return I18n.format(this.getUnlocalizedName());
	}
	
	public MoaGenetics setBaseGenetics(BaseGenetics baseGenetics)
	{
		this.name = baseGenetics.getUnlocalizedName();
		this.bodyColor = baseGenetics.color;
		this.jumps = Math.max(baseGenetics.minJumps, baseGenetics.maxJumps);
		this.spawnChance = baseGenetics.spawnChance;
		
		return this;
	}
	
	public static MoaGenetics getFromSeed(World world, int geneticSeed)
	{
		MoaGenetics genetics = new MoaGenetics();
		Random generator = new Random();
		
		generator.setSeed(geneticSeed);
		
		genetics.setBaseGenetics(baseGenetics[generator.nextInt(baseGenetics.length)]);
		
		int beakLegColor = beakLegColors[generator.nextInt(beakLegColors.length)];
		
		genetics.legColor = beakLegColor;
		genetics.beakColor = beakLegColor;
		genetics.eyeColor = eyeColors[generator.nextInt(eyeColors.length)];
		genetics.markColor = markColors[generator.nextInt(markColors.length)];
		
		genetics.markBack = backMarks[generator.nextInt(backMarks.length)];
		genetics.markHead = headMarks[generator.nextInt(headMarks.length)];
		genetics.markTail = tailMarks[generator.nextInt(tailMarks.length)];
		genetics.markWing = wingMarks[generator.nextInt(wingMarks.length)];
		
		genetics.geneticSeed = geneticSeed;
		
		return genetics;
	}
	
	public static int getRandomGeneticSeed(World world)
	{
		int modifier = world.rand.nextBoolean() ? 1 : -1;
		
		return modifier * world.rand.nextInt(Integer.MAX_VALUE);
	}
	
	public static MoaGenetics getMixedResult(World world, MoaGenetics maleParent, MoaGenetics femaleParent)
	{
		MoaGenetics finalGenetics = new MoaGenetics();
		
		Random generator;
		
		if (world == null)
		{
			generator = new Random();
		}
		else
		{
			generator = world.rand;
		}
		
		int mixedBodyColor = mixTwoColors(maleParent.bodyColor, femaleParent.bodyColor);
		int mixedBeakColor = mixTwoColors(maleParent.beakColor, femaleParent.beakColor);
		int mixedLegColor = mixTwoColors(maleParent.legColor, femaleParent.legColor);
		int mixedEyeColor = mixTwoColors(maleParent.eyeColor, femaleParent.eyeColor);
		int mixedMarkColor = mixTwoColors(maleParent.markColor, femaleParent.markColor);
		
		int minJumps = Math.min(maleParent.jumps, femaleParent.jumps);
		int maxJumps = Math.max(maleParent.jumps, femaleParent.jumps);
		
		String mixedName = maleParent.getUnlocalizedName() + " & " + femaleParent.getUnlocalizedName() + " Mix";
		
		if (maleParent.getUnlocalizedName().equals(femaleParent.getUnlocalizedName()))
		{
			mixedName = maleParent.getUnlocalizedName();
		}
		
		BaseGenetics mixedBaseGenetics = new BaseGenetics(mixedName, mixedBodyColor, minJumps, maxJumps, 1.0F);
		
		finalGenetics.setBaseGenetics(mixedBaseGenetics);
		
		finalGenetics.legColor = mixedLegColor;
		finalGenetics.beakColor = mixedBeakColor;
		finalGenetics.eyeColor = mixedEyeColor;
		finalGenetics.markColor = mixedMarkColor;
		
		finalGenetics.markBack = generator.nextBoolean() ? maleParent.markBack : femaleParent.markBack;
		finalGenetics.markHead = generator.nextBoolean() ? maleParent.markHead : femaleParent.markHead;
		finalGenetics.markTail = generator.nextBoolean() ? maleParent.markTail : femaleParent.markTail;
		finalGenetics.markWing = generator.nextBoolean() ? maleParent.markWing : femaleParent.markWing;
		
		return finalGenetics;
	}
	
	public static MoaGenetics getMixedResult(World world, int parentGeneticSeed1, int parentGeneticSeed2)
	{
		return getMixedResult(world, getFromSeed(world, parentGeneticSeed1), getFromSeed(world, parentGeneticSeed2));
	}
	
	private static int mixTwoColors(int color1, int color2)
	{
		return ((color1 & 0xFEFEFEFE) >> 1) + ((color2 & 0xFEFEFEFE) >> 1);
	}
	
	public static ResourceLocation getMark(String markType, String markName)
	{
		return new ResourceLocation(AetherCore.MOD_ID, moaDirectory + markType + "/" + markName + ".png");
	}
	
	public static ResourceLocation getBaseTexture(String textureName)
	{
		return new ResourceLocation(AetherCore.MOD_ID, moaDirectory + textureName + ".png");
	}
	
	public static class BaseGenetics
	{
		
		public int color, minJumps, maxJumps;
		
		public float spawnChance;
		
		private String name;
		
		public BaseGenetics(String name, int color, int minJumps, int maxJumps, float spawnChance)
		{
			this.name = name;
			this.color = color;
			this.minJumps = minJumps;
			this.maxJumps = maxJumps;
			this.spawnChance = Math.min(1.0F, spawnChance);
		}
		
		public BaseGenetics(String name, int color, int minJumps, int maxJumps)
		{
			this(name, color, minJumps, maxJumps, 1.0F);
		}

		public String getUnlocalizedName()
		{
			return this.name;
		}

		public String getLocalizedName()
		{
			return I18n.format(this.getUnlocalizedName());
		}

	}

	public void writeToNBT(NBTTagCompound tag)
	{
		tag.setString("name", this.getUnlocalizedName());
		
		tag.setInteger("geneticSeed", this.geneticSeed);

		tag.setInteger("bodyColor", this.bodyColor);
		tag.setInteger("legColor", this.legColor);
		tag.setInteger("beakColor", this.beakColor);
		tag.setInteger("eyeColor", this.eyeColor);
		tag.setInteger("markColor", this.markColor);
		tag.setInteger("jumps", this.jumps);
		
		tag.setFloat("spawnChance", this.spawnChance);
		
		tag.setString("body", this.body.getResourcePath());
		tag.setString("legs", this.legs.getResourcePath());
		tag.setString("beak", this.beak.getResourcePath());
		tag.setString("eye", this.eye.getResourcePath());
		tag.setString("teeth", this.teeth.getResourcePath());
		tag.setString("tongue", this.tongue.getResourcePath());
		
		tag.setString("markBack", this.markBack.getResourcePath());
		tag.setString("markHead", this.markHead.getResourcePath());
		tag.setString("markTail", this.markTail.getResourcePath());
		tag.setString("markWing", this.markWing.getResourcePath());
		
		tag.setString("saddle", this.saddle.getResourcePath());
	}

	public void readFromNBT(NBTTagCompound tag)
	{
		this.name = tag.getString("name");
		
		this.geneticSeed = tag.getInteger("geneticSeed");
		
		this.bodyColor = tag.getInteger("bodyColor");
		this.legColor = tag.getInteger("legColor");
		this.beakColor = tag.getInteger("beakColor");
		this.eyeColor = tag.getInteger("eyeColor");
		this.markColor = tag.getInteger("markColor");
		this.jumps = tag.getInteger("jumps");
		
		this.spawnChance = tag.getFloat("spawnChance");
		
		this.body = getBaseTexture(tag.getString("body"));
		this.legs = getBaseTexture(tag.getString("legs"));
		this.beak = getBaseTexture(tag.getString("beak"));
		this.eye = getBaseTexture(tag.getString("eye"));
		this.teeth = getBaseTexture(tag.getString("teeth"));
		this.tongue = getBaseTexture(tag.getString("tongue"));
		
		this.markBack = new ResourceLocation(AetherCore.MOD_ID, moaDirectory + "back/" + tag.getString("markBack"));
		this.markHead = new ResourceLocation(AetherCore.MOD_ID, moaDirectory + "back/" + tag.getString("markHead"));
		this.markTail = new ResourceLocation(AetherCore.MOD_ID, moaDirectory + "back/" + tag.getString("markTail"));
		this.markWing = new ResourceLocation(AetherCore.MOD_ID, moaDirectory + "back/" + tag.getString("markWing"));
	
		this.saddle = getBaseTexture(tag.getString("saddle"));
	}
	
}
