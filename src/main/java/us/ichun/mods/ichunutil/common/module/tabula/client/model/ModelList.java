package us.ichun.mods.ichunutil.common.module.tabula.client.model;

import net.minecraft.client.model.ModelBase;

import java.util.ArrayList;

public final class ModelList
{
    public static ArrayList<ModelInfo> models = new ArrayList<ModelInfo>();
    public static ArrayList<Class<? extends ModelBase>> modelBlacklist = new ArrayList<Class<? extends ModelBase>>();
}
