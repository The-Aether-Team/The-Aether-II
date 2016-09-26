package us.ichun.mods.ichunutil.common.module.tabula.client.formats.types;

import com.gildedgames.aether.common.AetherCore;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import us.ichun.mods.ichunutil.common.module.tabula.common.project.ProjectInfo;
import us.ichun.mods.ichunutil.common.module.tabula.common.project.components.CubeInfo;
import us.ichun.mods.ichunutil.common.module.tabula.common.project.TechneConverter;
import us.ichun.mods.ichunutil.common.module.tabula.common.project.components.CubeGroup;
import us.ichun.mods.ichunutil.common.module.techne.TC1Json;
import us.ichun.mods.ichunutil.common.module.techne.TC2Info;
import org.apache.commons.io.IOUtils;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class ImportTabula extends Importer
{
    @Override
    public ProjectInfo createProjectInfo(File file)
    {
        try
        {
            ZipFile zipFile = new ZipFile(file);
            Enumeration entries = zipFile.entries();

            ZipEntry modelInfo = null;
            HashMap<String, InputStream> images = new HashMap<String, InputStream>();

            boolean tampered = false;

            while(entries.hasMoreElements())
            {
                ZipEntry entry = (ZipEntry)entries.nextElement();
                if(!entry.isDirectory())
                {
                    if(entry.getName().endsWith(".png") && entry.getCrc() != Long.decode("0xf970c898"))
                    {
                        images.put(entry.getName(), zipFile.getInputStream(entry));
                    }
                    if(entry.getName().endsWith(".xml") || entry.getName().endsWith(".json"))
                    {
                        modelInfo = entry;
                    }
                    if(!entry.getName().endsWith(".png") && !entry.getName().endsWith(".xml") && !entry.getName().endsWith(".json"))
                    {
                        tampered = true;
                    }
                }
            }

            ProjectInfo info = null;
            if(modelInfo != null)
            {
                if(modelInfo.getName().endsWith(".xml"))
                {
                    info = convertTechneFile(TC2Info.convertTechneFile(zipFile.getInputStream(modelInfo), images), file.getName());
                }
                else
                {
                    info = readModelFile(zipFile.getInputStream(modelInfo), images, file.getName());
                }
            }

            zipFile.close();

            if(tampered)
            {
                info.tampered = true;
                AetherCore.LOGGER.warn(file.getName() + " is a tampered model file.");
            }

            return info;
        }
        catch (Exception e1)
        {
            e1.printStackTrace();
            return null;
        }
    }

    @Override
    public int getProjectVersion()
    {
        return ProjectInfo.PROJ_VERSION;//TODO update this over time;
    }

    public static ProjectInfo convertTechneFile(TC2Info tc2Info, String fileName)
    {
        if(tc2Info == null)
        {
            return null;
        }

        ProjectInfo project = new ProjectInfo(fileName, "TechneToTabulaImporter");

        project.projVersion = ProjectInfo.PROJ_VERSION;
        if(!tc2Info.Techne.Author.equals("ZeuX") && !tc2Info.Techne.Author.equals("NotZeux"))
        {
            project.authorName = tc2Info.Techne.Author;
        }

        float scaleX = 1.0F;
        float scaleY = 1.0F;
        float scaleZ = 1.0F;

        for(TC2Info.Model model : tc2Info.Techne.Models)
        {
            try
            {
                String[] textureSize = model.Model.TextureSize.split(",");
                project.textureWidth = Integer.parseInt(textureSize[0]);
                project.textureHeight = Integer.parseInt(textureSize[1]);

                if(project.bufferedTexture == null)
                {
                    project.bufferedTexture = model.Model.image;
                }

                String[] scale = model.Model.GlScale.split(",");
                scaleX = Float.parseFloat(scale[0]);
                scaleY = Float.parseFloat(scale[1]);
                scaleZ = Float.parseFloat(scale[2]);

                boolean degrees = determineAngleType(model.Model.Geometry);
                project.cubeCount += exploreTC2Info(project.cubes, project.cubeGroups, model.Model.Geometry, 0, 0, 0, 0, 0, 0, scaleX, scaleY, scaleZ, degrees);
            }
            catch(NumberFormatException e)
            {
                AetherCore.LOGGER.warn("Error parsing Techne 2 model for Tabula: Invalid number");
                e.printStackTrace();
            }
            catch(ArrayIndexOutOfBoundsException e)
            {
                AetherCore.LOGGER.warn("Error parsing Techne 2 model for Tabula: Array too short");
            }
        }

        return project;
    }

    private static boolean determineAngleType(TC2Info.Group geometry)
    {
        boolean degrees = false;
//        if(geometry.Shape != null)
//        {
//            for(TC2Info.Shape shape : geometry.Shape)
//            {
//                String[] rot = shape.Rotation.split(",");
//                double rX = Float.parseFloat(rot[0]);
//                double rY = Float.parseFloat(rot[1]);
//                double rZ = Float.parseFloat(rot[2]);
//
//                double pi = Math.PI + 0.01D;
//
//                if(rX < -pi || rX > pi || rY < -pi || rY > pi || rZ < -pi || rZ > pi)
//                {
//                    degrees = true;
//                    break;
//                }
//            }
//
//        }
//        if(geometry.Null != null && !degrees)
//        {
//            for(TC2Info.Null nul : geometry.Null)
//            {
//                boolean degInner = determineAngleType(nul.Children);
//                if(degInner)
//                {
//                    degrees = degInner;
//                    break;
//                }
//            }
//        }
        return degrees;
    }

    private static int exploreTC2Info(ArrayList<CubeInfo> cubes, ArrayList<CubeGroup> groups, TC2Info.Group geometry, double posX, double posY, double posZ, double rotX, double rotY, double rotZ, float scaleX, float scaleY, float scaleZ, boolean isDegrees)
    {
        int cubeCount = 0;
        if(geometry.Shape != null)
        {
            for(TC2Info.Shape shape : geometry.Shape)
            {
                CubeInfo info = new CubeInfo(shape.Name == null ? "shape" + Integer.toString(cubeCount + 1) : shape.Name);

                String[] textureOffset = shape.TextureOffset.split(",");
                info.txOffset = new int[] { Integer.parseInt(textureOffset[0]), Integer.parseInt(textureOffset[1]) };
                info.txMirror = !shape.IsMirrored.equals("False");

                String[] pos = shape.Position.split(",");
                String[] rot = shape.Rotation.split(",");
                String[] size = shape.Size.split(",");
                String[] offset = new String[] { "0", "0", "0" }; //Techne 2 files by default lack the Offset field.
                if(shape.Offset != null)
                {
                    offset = shape.Offset.split(",");
                }
                info.position = new double[] { Float.parseFloat(pos[0]) + posX, Float.parseFloat(pos[1]) + posY, Float.parseFloat(pos[2]) + posZ };
                info.offset = new double[] { Float.parseFloat(offset[0]), Float.parseFloat(offset[1]), Float.parseFloat(offset[2]) };
                info.dimensions = new int[] { Integer.parseInt(size[0]), Integer.parseInt(size[1]), Integer.parseInt(size[2]) };

                double rX = Float.parseFloat(rot[0]) + rotX;
                double rY = Float.parseFloat(rot[1]) + rotY;
                double rZ = Float.parseFloat(rot[2]) + rotZ;

                TechneConverter.Rotation rotation;
                if(isDegrees)
                {
                    rotation = TechneConverter.fromTechne(TechneConverter.Rotation.createFromDegrees(rX, rY, rZ));
                }
                else
                {
                    rotation = TechneConverter.fromTechne(TechneConverter.Rotation.createFromRadians(rX, rY, rZ));
                }
                info.rotation = new double[] { rotation.getDegreesX(), rotation.getDegreesY(), rotation.getDegreesZ() };
                info.scale = new double[] { scaleX, scaleY, scaleZ };

                cubes.add(info);

                cubeCount++;
            }
        }
        if(geometry.Null != null)
        {
            for(TC2Info.Null nul : geometry.Null)
            {
                CubeGroup group = new CubeGroup(nul.Name);
                groups.add(group);
                String[] pos = nul.Position.split(",");
                String[] rot = nul.Rotation.split(",");
                cubeCount += exploreTC2Info(group.cubes, group.cubeGroups, nul.Children, Float.parseFloat(pos[0]), Float.parseFloat(pos[1]), Float.parseFloat(pos[2]), Float.parseFloat(rot[0]), Float.parseFloat(rot[1]), Float.parseFloat(rot[2]), scaleX, scaleY, scaleZ, isDegrees);
            }
        }
        return cubeCount;
    }

    public static ProjectInfo readModelFile(InputStream json, HashMap<String, InputStream> images, String fileName) throws IOException
    {
        if(json == null || images == null)
        {
            return null;
        }

        StringWriter writer = new StringWriter();
        IOUtils.copy(json, writer);
        String jsonString = writer.toString();

        ProjectInfo project = null;

        if((jsonString.contains("\"Techne\"") || jsonString.contains("\"\u0000T\u0000e\u0000c\u0000h\u0000n\u0000e\u0000\"")) && (jsonString.contains("@Version") || jsonString.contains("\u0000@\u0000V\u0000e\u0000r\u0000s\u0000i\u0000o\u0000n\u0000")) && !jsonString.contains("TechneToTabulaImporter"))
        {
            TC2Info info;

            try
            {
                info = (new Gson()).fromJson(jsonString, TC2Info.class);
            }
            catch(JsonSyntaxException e1)
            {
                info = (new Gson()).fromJson(jsonString.replaceAll("\u0000", ""), TC1Json.class).toTC2Info();
            }
            if(info != null)
            {
                for(TC2Info.Model model : info.Techne.Models)
                {
                    InputStream stream = images.get(model.Model.texture);
                    if(stream != null)
                    {
                        model.Model.image = ImageIO.read(stream);
                        stream.close();
                    }
                }

                project = convertTechneFile(info, fileName);
            }
        }
        else
        {
            project = (new Gson()).fromJson(jsonString, ProjectInfo.class);

            InputStream stream = images.get("texture.png");
            if(stream != null)
            {
                project.bufferedTexture = ImageIO.read(stream);
                stream.close();
            }
        }

        json.close();

        for(Map.Entry<String, InputStream> img : images.entrySet())
        {
            img.getValue().close();
        }
        return project;
    }
}
