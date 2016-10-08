package us.ichun.mods.ichunutil.common.module.tabula.common.project.components;

import com.google.common.collect.Ordering;
import us.ichun.mods.ichunutil.common.module.tabula.common.project.ProjectInfo;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

public class Animation
{
    public String name;
    public String identifier;

    public boolean loops;

    public TreeMap<String, ArrayList<AnimationComponent>> sets = new TreeMap<String, ArrayList<AnimationComponent>>(Ordering.natural()); // cube identifier to animation component

    public transient float playTime;
    public transient boolean playing;

    public transient float playSpeed = 1.0F;

    public Animation(String name)
    {
        this.name = name;
        identifier = RandomStringUtils.randomAscii(ProjectInfo.IDENTIFIER_LENGTH);
    }

    public void createAnimComponent(String cubeIdent, String name, int length, int pos)
    {
        ArrayList<AnimationComponent> set = sets.get(cubeIdent);
        if(set == null)
        {
            set = new ArrayList<AnimationComponent>();
            sets.put(cubeIdent, set);
        }

        set.add(new AnimationComponent(name, length, pos));
    }

    public void update()
    {
        if(playing)
        {
            playTime += this.playSpeed;

            if(playTime > getLength())
            {
                if(loops)
                {
                    playTime = 0;
                }
                else
                {
                    stop();
                }
            }
        }
    }

    public void play()
    {
        if(!playing)
        {
            playing = true;
            playTime = 0;
        }
    }

    public void stop()
    {
        playing = false;
    }

    public float getLength()
    {
        int lastTick = 0;
        for(Map.Entry<String, ArrayList<AnimationComponent>> e : sets.entrySet())
        {
            for(AnimationComponent comp : e.getValue())
            {
                if(comp.startKey + comp.length > lastTick)
                {
                    lastTick = comp.startKey + comp.length;
                }
            }
        }
        return lastTick;
    }
}
