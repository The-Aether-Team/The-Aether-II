package us.ichun.mods.ichunutil.common.module.tabula.common.project.components;

import us.ichun.mods.ichunutil.common.module.tabula.common.project.ProjectInfo;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.ArrayList;

public class CubeGroup
{
    public CubeGroup(String name)
    {
        this.name = name;
        identifier = RandomStringUtils.randomAscii(ProjectInfo.IDENTIFIER_LENGTH);
    }

    public ArrayList<CubeInfo> cubes = new ArrayList<CubeInfo>();
    public ArrayList<CubeGroup> cubeGroups = new ArrayList<CubeGroup>();

    public String name;

    public boolean txMirror = false;

    public boolean hidden = false;

    public ArrayList<String> metadata = new ArrayList<String>();

    public String identifier;
}
