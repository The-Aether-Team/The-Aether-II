package us.ichun.mods.ichunutil.common.module.tabula.common.project.components;

import org.apache.commons.lang3.RandomStringUtils;
import us.ichun.mods.ichunutil.common.module.tabula.common.project.ProjectInfo;

import java.util.ArrayList;

public class CubeGroup
{
	public CubeGroup(String name)
	{
		this.name = name;
		this.identifier = RandomStringUtils.randomAscii(ProjectInfo.IDENTIFIER_LENGTH);
	}

	public ArrayList<CubeInfo> cubes = new ArrayList<>();

	public ArrayList<CubeGroup> cubeGroups = new ArrayList<>();

	public String name;

	public boolean txMirror = false;

	public boolean hidden = false;

	public ArrayList<String> metadata = new ArrayList<>();

	public String identifier;
}
