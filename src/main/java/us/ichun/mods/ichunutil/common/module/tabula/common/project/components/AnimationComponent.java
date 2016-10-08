package us.ichun.mods.ichunutil.common.module.tabula.common.project.components;

import net.minecraft.util.math.MathHelper;
import org.apache.commons.lang3.RandomStringUtils;
import us.ichun.mods.ichunutil.common.module.tabula.common.math.PolynomialFunctionLagrangeForm;
import us.ichun.mods.ichunutil.common.module.tabula.common.project.ProjectInfo;

import java.util.ArrayList;

public class AnimationComponent implements Comparable<AnimationComponent>
{
	public double[] posChange = new double[3];

	public double[] rotChange = new double[3];

	public double[] scaleChange = new double[3];

	public double opacityChange = 0.0D;

	public double[] posOffset = new double[3];

	public double[] rotOffset = new double[3];

	public double[] scaleOffset = new double[3];

	public double opacityOffset = 0.0D;

	public ArrayList<double[]> progressionCoords;

	public transient PolynomialFunctionLagrangeForm progressionCurve;

	public String name;

	public int length;

	public int startKey;

	public boolean hidden;

	public String identifier;

	public AnimationComponent(String name, int length, int startKey)
	{
		this.name = name;
		this.length = length;
		this.startKey = startKey;
		this.identifier = RandomStringUtils.randomAscii(ProjectInfo.IDENTIFIER_LENGTH);
	}

	public void animate(CubeInfo info, float time)
	{
		float prog = MathHelper.clamp_float((time - this.startKey) / (float) this.length, 0F, 1F);
		float mag = prog;
		if (this.getProgressionCurve() != null)
		{
			mag = MathHelper.clamp_float((float) this.getProgressionCurve().value(prog), 0.0F, 1.0F);
		}
		if (time >= this.startKey)
		{
			for (int i = 0; i < 3; i++)
			{
				info.position[i] += this.posOffset[i];
				info.rotation[i] += this.rotOffset[i];
				info.scale[i] += this.scaleOffset[i];
			}
			info.opacity += this.opacityOffset;
		}
		for (int i = 0; i < 3; i++)
		{
			info.position[i] += this.posChange[i] * mag;
			info.rotation[i] += this.rotChange[i] * mag;
			info.scale[i] += this.scaleChange[i] * mag;
		}
		info.opacity += this.opacityChange * mag;

		if (info.modelCube != null)
		{
			info.modelCube.setRotationPoint((float) info.position[0], (float) info.position[1], (float) info.position[2]);
			info.modelCube.rotateAngleX = (float) Math.toRadians(info.rotation[0]);
			info.modelCube.rotateAngleY = (float) Math.toRadians(info.rotation[1]);
			info.modelCube.rotateAngleZ = (float) Math.toRadians(info.rotation[2]);
		}
	}

	public void reset(CubeInfo info, float time)
	{
		float prog = MathHelper.clamp_float((time - this.startKey) / (float) this.length, 0F, 1F);
		float mag = prog;
		if (this.getProgressionCurve() != null)
		{
			mag = MathHelper.clamp_float((float) this.getProgressionCurve().value(prog), 0.0F, 1.0F);
		}
		if (time >= this.startKey)
		{
			for (int i = 0; i < 3; i++)
			{
				info.position[i] -= this.posOffset[i];
				info.rotation[i] -= this.rotOffset[i];
				info.scale[i] -= this.scaleOffset[i];
			}
			info.opacity -= this.opacityOffset;
		}
		for (int i = 0; i < 3; i++)
		{
			info.position[i] -= this.posChange[i] * mag;
			info.rotation[i] -= this.rotChange[i] * mag;
			info.scale[i] -= this.scaleChange[i] * mag;
		}
		info.opacity -= this.opacityChange * mag;

		if (info.modelCube != null)
		{
			info.modelCube.setRotationPoint((float) info.position[0], (float) info.position[1], (float) info.position[2]);
			info.modelCube.rotateAngleX = (float) Math.toRadians(info.rotation[0]);
			info.modelCube.rotateAngleY = (float) Math.toRadians(info.rotation[1]);
			info.modelCube.rotateAngleZ = (float) Math.toRadians(info.rotation[2]);
		}
	}

	public double getProgressionFactor(double progression)
	{
		if (this.progressionCurve == null)
		{
			return progression;
		}
		return this.progressionCurve.value(progression);
	}

	public void addProgressionCoords(double x, double y)
	{
		if (this.progressionCoords == null)
		{
			this.progressionCoords = new ArrayList<>();
		}
		this.progressionCoords.add(new double[] { x, y });

		this.createProgressionCurve();
	}

	public void removeProgressionCoords(double x, double y)
	{
		for (int i = this.progressionCoords.size() - 1; i >= 0; i--)
		{
			double[] coord = this.progressionCoords.get(i);
			if (coord[0] == x && coord[1] == y)
			{
				this.progressionCoords.remove(i);
			}
		}

		if (this.progressionCoords.isEmpty())
		{
			this.progressionCoords = null;
			this.progressionCurve = null;
		}
		else
		{
			this.createProgressionCurve();
		}
	}

	public void moveProgressionCoords(double x, double y, double newX, double newY)
	{
		for (int i = this.progressionCoords.size() - 1; i >= 0; i--)
		{
			double[] coord = this.progressionCoords.get(i);
			if (coord[0] == x && coord[1] == y)
			{
				coord[0] = newX;
				coord[1] = newY;
			}
		}

		this.createProgressionCurve();
	}

	public void createProgressionCurve()
	{
		if (this.progressionCoords != null)
		{
			double[] xes = new double[this.progressionCoords.size() + 2];
			double[] yes = new double[this.progressionCoords.size() + 2];

			xes[0] = yes[0] = 0;
			xes[1] = yes[1] = 1;

			for (int i = 0; i < this.progressionCoords.size(); i++)
			{
				xes[2 + i] = this.progressionCoords.get(i)[0];
				yes[2 + i] = this.progressionCoords.get(i)[1];
			}

			this.progressionCurve = new PolynomialFunctionLagrangeForm(xes, yes);
		}
	}

	public PolynomialFunctionLagrangeForm getProgressionCurve()
	{
		if (this.progressionCoords != null && this.progressionCurve == null)
		{
			this.createProgressionCurve();
		}
		return this.progressionCurve;
	}

	@Override
	public int compareTo(AnimationComponent arg0)
	{
		AnimationComponent comp = arg0;

		return ((Integer) this.startKey).compareTo(comp.startKey);
	}
}
