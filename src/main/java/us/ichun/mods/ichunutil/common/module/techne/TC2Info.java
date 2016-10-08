package us.ichun.mods.ichunutil.common.module.techne;

import com.gildedgames.aether.common.AetherCore;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.io.IOUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.imageio.ImageIO;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * Deserialized version of Techne 2's JSON/Techne 1's XML/JSON save files
 * Techne support in iChunUtil has been dropped in favour of Tabula support. Tabula support however, covers Techne file importing. Use that instead if you require reading of a Techne file
 */
public class TC2Info
{
    public Techne Techne = new Techne();

    public transient boolean tampered;

    public class Techne
    {
        @SerializedName("@Version")
        public String Version = "2.2";
        public String Author = "NotZeuX";
        public String Name = "";
        public String PreviewImage = "";
        public String ProjectName = "";
        public String ProjectType = "";
        public String Description = "";
        public String DateCreated = "";
        public Model[] Models = new Model[] { };

        private String _comment = "Generated using iChunUtil";

        public void createNewModelArray(int size)
        {
            Models = new Model[size];
            for(int i = 0; i < Models.length; i++)
            {
                Models[i] = new Model();
            }
        }
    }

    public class Model
    {
        public ModelInfo Model = new ModelInfo();
    }

    public class ModelInfo
    {
        public String GlScale = "1,1,1";
        public String Name = "";
        public String TextureSize = "64,32";
        @SerializedName("@texture")
        public String texture = "texture.png";
        public String BaseClass = "ModelBase";
        public Group Geometry = new Group();

        public transient BufferedImage image;
    }

    public class Group
    {
        public Circular[] Circular = new Circular[] {};
        public Shape[] Shape = new Shape[] {};
        public Linear[] Linear = new Linear[] {};
        public Null[] Null = new Null[] {};

        public void createNewShapeArray(int size)
        {
            Shape = new Shape[size];
            for(int i = 0; i < Shape.length; i++)
            {
                Shape[i] = new Shape();
            }
        }

        public void extendNullArray()
        {
            Null[] nulls = new Null[Null.length + 1];
            for(int i = 0; i < Null.length; i++)
            {
                nulls[i] = Null[i];
            }
            nulls[Null.length] = new Null();
            Null = nulls;
        }
    }

    public class Circular extends Null
    {
        {
            Type = "16932820-ef7c-4b4b-bf05-b72063b3d23c";
            Name = "Circular Array";
        }
        public int Count = 5;
        public int Radius = 16;
    }

    public class Shape
    {
        public int Id = 1; //is a variable
        @SerializedName("@Type")
        public String Type = "d9e621f7-957f-4b77-b1ae-20dcd0da7751";
        @SerializedName("@Name")
        public String Name = "new cube";
        public String IsDecorative = "False";
        public String IsFixed = "False";
        public String IsMirrored = "False";
        public String Position = "0,0,0";
        public String Offset = "0,0,0";
        public String Rotation = "0,0,0";
        public String Size = "1,1,1";
        public String TextureOffset = "0,0";
    }

    public class Linear extends Null
    {
        {
            Type = "fc4f63c9-8296-4c97-abd8-414f20e49bd5";
            Name = "Linear Array";
        }
        public String Count = "0,0,0";
        public String Spacing = "0,0,0";
    }

    public class Null
    {
        @SerializedName("@Type")
        public String Type = "3b3bb6e5-2f8b-4bbd-8dbb-478b67762fd0";
        @SerializedName("@Name")
        public String Name = "null element";
        public String Position = "0,0,0";
        public String Rotation = "0,0,0";
        public Group Children = new Group();
    }

    /**
     * Returns a TC2Info file from a Techne save file. Works for Techne 1 and 2. TC2Info is a Techne 2 file format, Techne 1 saves will be converted to this format.
     * @param file Techne file location..
     * @return Techne 2 save info, deserialized. Null if file is not a valid Techne file with model info and textures.
     */
    public static TC2Info readTechneFile(File file)
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

            TC2Info info = null;
            if(modelInfo != null)
            {
                if(modelInfo.getName().endsWith(".xml"))
                {
                    info = convertTechneFile(zipFile.getInputStream(modelInfo), images);
                }
                else
                {
                    info = readTechne2File(zipFile.getInputStream(modelInfo), images);
                }
            }

            zipFile.close();

            if(info != null)
            {
                info.tampered = tampered;
            }

            return info;
        }
        catch (Exception e1)
        {
            e1.printStackTrace();
            return null;
        }
    }

    /**
     * Returns a TC2Info file from a Techne save file. Works for Techne 1 and 2. TC2Info is a Techne 2 file format, Techne 1 saves will be converted to this format.
     * @param stream InputStream, basically getting the Techne file as a Resource. DO NOT pass a ZipInputStream.
     * @return Techne 2 save info, deserialized. Null if file is not a valid Techne file with model info and textures.
     */
    public static TC2Info readTechneFile(InputStream stream)
    {
        try
        {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            IOUtils.copy(stream, baos);
            baos.flush();

            ZipInputStream cloneXML = new ZipInputStream(new ByteArrayInputStream(baos.toByteArray()));

            ZipEntry entry = null;

            boolean hasXML = false;
            boolean hasJSON = false;
            while((entry = cloneXML.getNextEntry()) != null)
            {
                if(!entry.isDirectory())
                {
                    if(entry.getName().endsWith(".xml"))
                    {
                        hasXML = true;
                        break;
                    }
                    if(entry.getName().endsWith(".json"))
                    {
                        hasJSON = true;
                        break;
                    }
                }
            }

            entry = null;

            ZipInputStream clonePNG = new ZipInputStream(new ByteArrayInputStream(baos.toByteArray()));

            HashMap<String, InputStream> images = new HashMap<String, InputStream>();

            boolean tampered = false;

            while((entry = clonePNG.getNextEntry()) != null)
            {
                if(!entry.isDirectory())
                {
                    if(entry.getName().endsWith(".png") && !images.containsKey(entry.getName()) && entry.getCrc() != Long.decode("0xf970c898"))
                    {
                        images.put(entry.getName(), clonePNG);
                        clonePNG = new ZipInputStream(new ByteArrayInputStream(baos.toByteArray()));
                    }
                    else if(!entry.getName().endsWith(".png") && !entry.getName().endsWith(".xml") && !entry.getName().endsWith(".json"))
                    {
                        tampered = true;
                    }
                }
            }

            stream.close();

            if(!images.isEmpty())
            {
                if(hasXML)
                {
                    TC2Info info = convertTechneFile(cloneXML, images);
                    if(info != null)
                    {
                        info.tampered = tampered;
                    }
                    return info;
                }
                if(hasJSON)
                {
                    TC2Info info = readTechne2File(cloneXML, images);
                    if(info != null)
                    {
                        info.tampered = tampered;
                    }
                    return info;
                }
            }
            return null;
        }
        catch(Exception e1)
        {
            return null;
        }
    }

    /**
     * Converts a Techne 1 file into a TC2Info (Techne 2 compatible format), with the exception of Techne 1's "pieces" function.
     */
    public static TC2Info convertTechneFile(InputStream xml, HashMap<String, InputStream> images) throws IOException, ParserConfigurationException, SAXException
    {
        if(xml == null || images == null)
        {
            return null;
        }

        TC2Info info = new TC2Info();

        DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();

        Document doc = builder.parse(xml);

        info.Techne.Version     = doc.getElementsByTagName("Techne").item(0).getAttributes().item(0).getNodeValue();
        info.Techne.Author      = doc.getElementsByTagName("Author").item(0).getTextContent().equals("ZeuX") ? "NotZeux" : doc.getElementsByTagName("Techne").item(0).getTextContent();
        info.Techne.Name        = doc.getElementsByTagName("Name").item(0).getTextContent();
        info.Techne.PreviewImage= doc.getElementsByTagName("PreviewImage").item(0).getTextContent();
        info.Techne.ProjectName = doc.getElementsByTagName("ProjectName").item(0).getTextContent();
        info.Techne.ProjectType = doc.getElementsByTagName("ProjectType").item(0).getTextContent();
        info.Techne.Description = doc.getElementsByTagName("Description").item(0).getTextContent();
        info.Techne.DateCreated = doc.getElementsByTagName("DateCreated").item(0).getTextContent();

        NodeList list = doc.getElementsByTagName("Model");

        info.Techne.createNewModelArray(list.getLength());

        int id = 1;

        for(int i = 0; i < list.getLength(); i++)
        {
            Model model = info.Techne.Models[i];

            Node node = list.item(i);

            for(int j = 0; j < node.getAttributes().getLength(); j++)
            {
                Node attribute = node.getAttributes().item(j);

                if(attribute.getNodeName().equals("texture"))
                {
                    model.Model.texture = attribute.getNodeValue();

                    InputStream stream = images.get(model.Model.texture);
                    if(stream != null)
                    {
                        model.Model.image = ImageIO.read(stream);
                    }
                }
            }

            Node Geometry = null;
            int shapeCount = 0;
            for(int k = 0; k < node.getChildNodes().getLength(); k++)
            {
                Node child = node.getChildNodes().item(k);
                if(child.getNodeName().equals("GlScale"))
                {
                    model.Model.GlScale = child.getTextContent();
                }
                else if(child.getNodeName().equals("Name"))
                {
                    model.Model.Name = child.getTextContent();
                }
                else if(child.getNodeName().equals("TextureSize"))
                {
                    model.Model.TextureSize = child.getTextContent();
                }
                else if(child.getNodeName().equals("BaseClass"))
                {
                    model.Model.BaseClass = child.getTextContent();
                }
                else if(child.getNodeName().equals("Geometry"))
                {
                    id = createShapesFromNode(id, child, model.Model.Geometry);
                }
            }
        }

        xml.close();

        for(Map.Entry<String, InputStream> img : images.entrySet())
        {
            img.getValue().close();
        }

        return info;
    }

    private static int createShapesFromNode(int id, Node node, Group group)
    {
        int shapeCount = 0;
        for(int l = 0; l < node.getChildNodes().getLength(); l++)
        {
            Node child = node.getChildNodes().item(l);
            if(child.getNodeName().equals("Shape"))
            {
                for(int j = 0; j < child.getAttributes().getLength(); j++)
                {
                    Node attribute = child.getAttributes().item(j);
                    if(attribute.getNodeName().equalsIgnoreCase("type") && (attribute.getNodeValue().equalsIgnoreCase("d9e621f7-957f-4b77-b1ae-20dcd0da7751") || attribute.getNodeValue().equalsIgnoreCase("de81aa14-bd60-4228-8d8d-5238bcd3caaa")))
                    {
                        shapeCount++;
                    }
                }
            }
            else if(child.getNodeName().equals("Folder") || child.getNodeName().equals("Piece"))
            {
                group.extendNullArray();
                for(int j = 0; j < child.getAttributes().getLength(); j++)
                {
                    Node attribute = child.getAttributes().item(j);
                    if(attribute.getNodeName().equals("Name"))
                    {
                        group.Null[group.Null.length - 1].Name = attribute.getNodeValue();
                    }
                }
                if(child.getNodeName().equals("Piece"))
                {
                    for(int k = 0; k < child.getChildNodes().getLength(); k++)
                    {
                        if(child.getChildNodes().item(k).equals("Position"))
                        {
                            group.Null[group.Null.length - 1].Position = child.getChildNodes().item(k).getTextContent();
                        }
                    }
                }
                createShapesFromNode(id, child, group.Null[group.Null.length - 1].Children);
            }
        }

        group.createNewShapeArray(shapeCount);

        int shapeNum = 0;
        for(int k = 0; k < node.getChildNodes().getLength(); k++)
        {
            Node child = node.getChildNodes().item(k);
            if(child.getNodeName().equals("Shape"))
            {
                for(int j = 0; j < child.getAttributes().getLength(); j++)
                {
                    Node attribute = child.getAttributes().item(j);
                    if(attribute.getNodeName().equals("type") && (attribute.getNodeValue().equalsIgnoreCase("d9e621f7-957f-4b77-b1ae-20dcd0da7751") || attribute.getNodeValue().equalsIgnoreCase("de81aa14-bd60-4228-8d8d-5238bcd3caaa")) && shapeNum < shapeCount)
                    {
                        Shape shape = group.Shape[shapeNum];
                        shape.Id = id++;

                        for(int jj = 0; jj < child.getAttributes().getLength(); jj++)
                        {
                            Node attribute1 = child.getAttributes().item(jj);
                            if(attribute1.getNodeName().equals("name"))
                            {
                                shape.Name = attribute1.getNodeValue();
                                break;
                            }
                        }

                        for(int kk = 0; kk < child.getChildNodes().getLength(); kk++)
                        {
                            Node child1 = child.getChildNodes().item(kk);
                            if(child1.getNodeName().equals("IsDecorative"))
                            {
                                shape.IsDecorative = child1.getTextContent();
                            }
                            else if(child1.getNodeName().equals("IsFixed"))
                            {
                                shape.IsFixed = child1.getTextContent();
                            }
                            else if(child1.getNodeName().equals("IsMirrored"))
                            {
                                shape.IsMirrored = child1.getTextContent();
                            }
                            else if(child1.getNodeName().equals("Offset"))
                            {
                                shape.Offset = child1.getTextContent();
                            }
                            else if(child1.getNodeName().equals("Position"))
                            {
                                shape.Position = child1.getTextContent();
                            }
                            else if(child1.getNodeName().equals("Rotation"))
                            {
                                String[] rotation = child1.getTextContent().split(",");
                                shape.Rotation = (rotation[0].equals("0") ? "0" : Float.toString((float)Math.toRadians(Float.parseFloat(rotation[0])))) + "," + (rotation[1].equals("0") ? "0" : Float.toString((float)Math.toRadians(Float.parseFloat(rotation[1])))) + "," + (rotation[2].equals("0") ? "0" : Float.toString((float)Math.toRadians(Float.parseFloat(rotation[2]))));
                            }
                            else if(child1.getNodeName().equals("Size"))
                            {
                                shape.Size = child1.getTextContent();
                            }
                            else if(child1.getNodeName().equals("TextureOffset"))
                            {
                                shape.TextureOffset = child1.getTextContent();
                            }
                        }
                        shapeNum++;
                    }
                }
            }
        }
        return id;
    }

    public static TC2Info readTechne2File(InputStream json, HashMap<String, InputStream> images) throws IOException
    {
        if(json == null || images == null)
        {
            return null;
        }

        StringWriter writer = new StringWriter();
        IOUtils.copy(json, writer);
        String jsonString = writer.toString();

        TC2Info info;

        try
        {
            info = (new Gson()).fromJson(jsonString, TC2Info.class);
        }
        catch(JsonSyntaxException e)
        {
            info = (new Gson()).fromJson(jsonString.replaceAll("\u0000", ""), TC1Json.class).toTC2Info();
        }

        if(info != null)
        {
            for(Model model : info.Techne.Models)
            {
                InputStream stream = images.get(model.Model.texture);
                if(stream != null)
                {
                    model.Model.image = ImageIO.read(stream);
                    stream.close();
                }
            }
        }

        json.close();

        for(Map.Entry<String, InputStream> img : images.entrySet())
        {
            img.getValue().close();
        }
        return info;
    }

    /**
     * Saves the TC2Info as a Techne 2 compatible file.
     * At the time of writing, the Techne 2 editor does not support "Offsets" which Techne 1 supported and I've urged ZeuX to implement support for it as it is a key feature.
     * Again, at the time of writing, the Techne 2 editor does not work with textures properly. I've written most of this importer and converter on the basis of what I seemed should be logical.
     * It should export as a proper Techne 2 file that hopefully works in the future. However, the importer should work 100% with files exported by this function and files made with Techne 1 and 2. (With exceptions to Pieces from Techne 1)
     * @param file Save file location. Ideally name the file a *.tc2 file
     * @param overwrite Can overwrite existing file?
     * @return Save successful.
     */
    public boolean saveAsFile(File file, boolean overwrite)
    {
        try
        {
            if(!overwrite && file.exists())
            {
                return false;
            }

            file.getParentFile().mkdirs();

            ZipOutputStream out = new ZipOutputStream(new FileOutputStream(file));
            out.setLevel(0);
            ZipEntry entry = new ZipEntry("model.json");

            out.putNextEntry(entry);

            byte[] data = (new Gson()).toJson(this).getBytes();
            out.write(data, 0, data.length);
            out.closeEntry();

            for(Model model : Techne.Models)
            {
                if(model.Model.image != null)
                {
                    entry = new ZipEntry(model.Model.texture);
                    out.putNextEntry(entry);

                    ImageIO.write(model.Model.image, "png", out);

                    out.closeEntry();
                }
            }

            out.close();
            return true;
        }
        catch(Exception e)
        {
            AetherCore.LOGGER.warn("Failed to save Techne 2 Model.");
            e.printStackTrace();
            return false;
        }
    }
}
