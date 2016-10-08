package us.ichun.mods.ichunutil.common.module.tabula.common.project.components;

import com.google.common.collect.Ordering;
import org.apache.commons.lang3.RandomStringUtils;
import us.ichun.mods.ichunutil.common.module.tabula.common.project.ProjectInfo;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

public class Animation
{
	public String name;

	public String identifier;

	public boolean loops;

	public TreeMap<String, ArrayList<AnimationComponent>> sets = new TreeMap<>(Ordering.natural()); // cube identifier to animation component

	public transient float playTime;

	public transient boolean playing;

	public transient float playSpeed = 1.0F;

	public Animation(String name)
	{
		this.name = name;
		this.identifier = RandomStringUtils.randomAscii(ProjectInfo.IDENTIFIER_LENGTH);
	}

	public void createAnimComponent(String cubeIdent, String name, int length, int pos)
	{
		ArrayList<AnimationComponent> set = this.sets.get(cubeIdent);
		if (set == null)
		{
			set = new ArrayList<>();
			this.sets.put(cubeIdent, set);
		}

		set.add(new AnimationComponent(name, length, pos));
	}

	public void update()
	{
		if (this.playing)
		{
			this.playTime += this.playSpeed;

			if (this.playTime > this.getLength())
			{
				if (this.loops)
				{
					this.playTime = 0;
				}
				else
				{
					this.stop();
				}
			}
		}
	}

	public void play()
	{
		if (!this.playing)
		{
			this.playing = true;
			this.playTime = 0;
		}
	}

	public void stop()
	{
		this.playing = false;
	}

	public float getLength()
	{
		int lastTick = 0;
		for (Map.Entry<String, ArrayList<AnimationComponent>> e : this.sets.entrySet())
		{
			for (AnimationComponent comp : e.getValue())
			{
				if (comp.startKey + comp.length > lastTick)
				{
					lastTick = comp.startKey + comp.length;
				}
			}
		}
		return lastTick;
	}
}
