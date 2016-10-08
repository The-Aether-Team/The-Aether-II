package us.ichun.mods.ichunutil.common.module.tabula.common.project.components;

import us.ichun.mods.ichunutil.common.module.tabula.common.project.ProjectInfo;
import net.minecraft.util.math.MathHelper;
import org.apache.commons.lang3.RandomStringUtils;
import us.ichun.mods.ichunutil.common.module.tabula.common.math.PolynomialFunctionLagrangeForm;

import java.util.ArrayList;

public class AnimationComponent
        implements Comparable
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
        identifier = RandomStringUtils.randomAscii(ProjectInfo.IDENTIFIER_LENGTH);
    }

    public void animate(CubeInfo info, float time)
    {
        float prog = MathHelper.clamp_float((time - startKey) / (float)length, 0F, 1F);
        float mag = prog;
        if(getProgressionCurve() != null)
        {
            mag = MathHelper.clamp_float((float)getProgressionCurve().value(prog), 0.0F, 1.0F);
        }
        if(time >= startKey)
        {
            for(int i = 0; i < 3; i++)
            {
                info.position[i] += posOffset[i];
                info.rotation[i] += rotOffset[i];
                info.scale[i] += scaleOffset[i];
            }
            info.opacity += opacityOffset;
        }
        for(int i = 0; i < 3; i++)
        {
            info.position[i] += posChange[i] * mag;
            info.rotation[i] += rotChange[i] * mag;
            info.scale[i] += scaleChange[i] * mag;
        }
        info.opacity += opacityChange * mag;

        if(info.modelCube != null)
        {
            info.modelCube.setRotationPoint((float)info.position[0], (float)info.position[1], (float)info.position[2]);
            info.modelCube.rotateAngleX = (float)Math.toRadians(info.rotation[0]);
            info.modelCube.rotateAngleY = (float)Math.toRadians(info.rotation[1]);
            info.modelCube.rotateAngleZ = (float)Math.toRadians(info.rotation[2]);
        }
    }

    public void reset(CubeInfo info, float time)
    {
        float prog = MathHelper.clamp_float((time - startKey) / (float)length, 0F, 1F);
        float mag = prog;
        if(getProgressionCurve() != null)
        {
            mag = MathHelper.clamp_float((float)getProgressionCurve().value(prog), 0.0F, 1.0F);
        }
        if(time >= startKey)
        {
            for(int i = 0; i < 3; i++)
            {
                info.position[i] -= posOffset[i];
                info.rotation[i] -= rotOffset[i];
                info.scale[i] -= scaleOffset[i];
            }
            info.opacity -= opacityOffset;
        }
        for(int i = 0; i < 3; i++)
        {
            info.position[i] -= posChange[i] * mag;
            info.rotation[i] -= rotChange[i] * mag;
            info.scale[i] -= scaleChange[i] * mag;
        }
        info.opacity -= opacityChange * mag;

        if(info.modelCube != null)
        {
            info.modelCube.setRotationPoint((float)info.position[0], (float)info.position[1], (float)info.position[2]);
            info.modelCube.rotateAngleX = (float)Math.toRadians(info.rotation[0]);
            info.modelCube.rotateAngleY = (float)Math.toRadians(info.rotation[1]);
            info.modelCube.rotateAngleZ = (float)Math.toRadians(info.rotation[2]);
        }
    }

    public double getProgressionFactor(double progression)
    {
        if(progressionCurve == null)
        {
            return progression;
        }
        return progressionCurve.value(progression);
    }

    public void addProgressionCoords(double x, double y)
    {
        if(progressionCoords == null)
        {
            progressionCoords = new ArrayList<double[]>();
        }
        progressionCoords.add(new double[] { x, y });

        createProgressionCurve();
    }

    public void removeProgressionCoords(double x, double y)
    {
        for(int i = progressionCoords.size() - 1; i >= 0; i--)
        {
            double[] coord = progressionCoords.get(i);
            if(coord[0] == x && coord[1] == y)
            {
                progressionCoords.remove(i);
            }
        }

        if(progressionCoords.isEmpty())
        {
            progressionCoords = null;
            progressionCurve = null;
        }
        else
        {
            createProgressionCurve();
        }
    }

    public void moveProgressionCoords(double x, double y, double newX, double newY)
    {
        for(int i = progressionCoords.size() - 1; i >= 0; i--)
        {
            double[] coord = progressionCoords.get(i);
            if(coord[0] == x && coord[1] == y)
            {
                coord[0] = newX;
                coord[1] = newY;
            }
        }

        createProgressionCurve();
    }

    public void createProgressionCurve()
    {
        if(progressionCoords != null)
        {
            double[] xes = new double[progressionCoords.size() + 2];
            double[] yes = new double[progressionCoords.size() + 2];

            xes[0] = yes[0] = 0;
            xes[1] = yes[1] = 1;

            for(int i = 0; i < progressionCoords.size(); i++)
            {
                xes[2 + i] = progressionCoords.get(i)[0];
                yes[2 + i] = progressionCoords.get(i)[1];
            }

            progressionCurve = new PolynomialFunctionLagrangeForm(xes, yes);
        }
    }

    public PolynomialFunctionLagrangeForm getProgressionCurve()
    {
        if(progressionCoords != null && progressionCurve == null)
        {
            createProgressionCurve();
        }
        return progressionCurve;
    }

    @Override
    public int compareTo(Object arg0)
    {
        if(arg0 instanceof AnimationComponent)
        {
            AnimationComponent comp = (AnimationComponent)arg0;
            return ((Integer)startKey).compareTo(comp.startKey);
        }
        return 0;
    }
}
