package us.ichun.mods.ichunutil.common.module.tabula.client.formats.types;

import us.ichun.mods.ichunutil.common.module.tabula.common.project.ProjectInfo;

import java.io.File;

public abstract class Importer
{
    public abstract ProjectInfo createProjectInfo(File file);
    public abstract int getProjectVersion();//Returns the project info version which the importer creates. This is for Tabula to repair outdated save files if possible.
}
