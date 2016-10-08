package us.ichun.mods.ichunutil.common.module.techne;

import com.google.gson.annotations.SerializedName;

/**
 * Deserialized version of Techne 1's JSON save files. Although the save files are mainly XML, there are some exceptions... Don't use this if possible.
 * Techne support in iChunUtil has been dropped in favour of Tabula support. Tabula support however, covers Techne file importing. Use that instead if you require reading of a Techne file
 */
public class TC1Json
{
    public Techne Techne = new Techne();

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
    }

    public class Group
    {
        public Shape[] Shape = new Shape[] {};
        public Null[] Folder = new Null[] {};
    }

    public class Shape
    {
        @SerializedName("@type")
        public String Type = "d9e621f7-957f-4b77-b1ae-20dcd0da7751";
        @SerializedName("@name")
        public String Name = "new cube";
        public Animation Animation = new Animation();
        public String IsDecorative = "False";
        public String IsFixed = "False";
        public String IsMirrored = "False";
        public String Position = "0,0,0";
        public String Offset = "0,0,0";
        public String Rotation = "0,0,0";
        public String Size = "1,1,1";
        public String TextureOffset = "0,0";
    }

    public class Animation
    {
        public String AnimationAngles = "0,0,0";
        public String AnimationDuration = "0,0,0";
        public String AnimationType = "0,0,0";
    }

    public class Null
    {
        @SerializedName("@type")
        public String Type = "3b3bb6e5-2f8b-4bbd-8dbb-478b67762fd0";
        @SerializedName("@Name")
        public String Name = "null element";

        public Shape[] Shape = new Shape[] {};
        public Null[] Folder = new Null[] {};
    }

    public TC2Info toTC2Info()
    {
        TC2Info info = new TC2Info();

        info.Techne.Version = Techne.Version;
        info.Techne.Author = Techne.Author.equals("ZeuX") ? "NotZeuX" : Techne.Author;
        info.Techne.Name = Techne.Name;
        info.Techne.PreviewImage = Techne.PreviewImage;
        info.Techne.ProjectName = Techne.ProjectName;
        info.Techne.ProjectType = Techne.ProjectType;
        info.Techne.Description = Techne.Description;
        info.Techne.DateCreated = Techne.DateCreated;

        info.Techne.createNewModelArray(Techne.Models.length);

        int id = 0;

        for(int i = 0; i < Techne.Models.length; i++)
        {
            info.Techne.Models[i].Model.GlScale = Techne.Models[i].Model.GlScale;

            info.Techne.Models[i].Model.Name = Techne.Models[i].Model.Name;
            info.Techne.Models[i].Model.TextureSize = Techne.Models[i].Model.TextureSize;
            info.Techne.Models[i].Model.texture = "texture.png";
            info.Techne.Models[i].Model.BaseClass = Techne.Models[i].Model.BaseClass;

            info.Techne.Models[i].Model.Geometry.createNewShapeArray(Techne.Models[i].Model.Geometry.Shape.length);

            for(int j = 0; j < Techne.Models[i].Model.Geometry.Shape.length; j++)
            {
                info.Techne.Models[i].Model.Geometry.Shape[j].Id = id++;

                info.Techne.Models[i].Model.Geometry.Shape[j].Name = Techne.Models[i].Model.Geometry.Shape[j].Name;
                info.Techne.Models[i].Model.Geometry.Shape[j].IsDecorative = Techne.Models[i].Model.Geometry.Shape[j].IsDecorative;
                info.Techne.Models[i].Model.Geometry.Shape[j].IsFixed = Techne.Models[i].Model.Geometry.Shape[j].IsFixed;
                info.Techne.Models[i].Model.Geometry.Shape[j].IsMirrored = Techne.Models[i].Model.Geometry.Shape[j].IsMirrored;
                info.Techne.Models[i].Model.Geometry.Shape[j].Position = Techne.Models[i].Model.Geometry.Shape[j].Position;
                info.Techne.Models[i].Model.Geometry.Shape[j].Offset = Techne.Models[i].Model.Geometry.Shape[j].Offset;

                String[] rotation = Techne.Models[i].Model.Geometry.Shape[j].Rotation.split(",");
                info.Techne.Models[i].Model.Geometry.Shape[j].Rotation = (rotation[0].equals("0") ? "0" : Float.toString((float)Math.toRadians(Float.parseFloat(rotation[0])))) + "," + (rotation[1].equals("0") ? "0" : Float.toString((float)Math.toRadians(Float.parseFloat(rotation[1])))) + "," + (rotation[2].equals("0") ? "0" : Float.toString((float)Math.toRadians(Float.parseFloat(rotation[2]))));
//TODO check and see if the Rotation is radians or degrees.
                info.Techne.Models[i].Model.Geometry.Shape[j].Size = Techne.Models[i].Model.Geometry.Shape[j].Size;
                info.Techne.Models[i].Model.Geometry.Shape[j].TextureOffset = Techne.Models[i].Model.Geometry.Shape[j].TextureOffset;
            }

            if(Techne.Models[i].Model.Geometry.Folder != null)
            {
                for(int j = 0; j < Techne.Models[i].Model.Geometry.Folder.length; j++)
                {
                    info.Techne.Models[i].Model.Geometry.extendNullArray();
                    id = populateNull(id, info.Techne.Models[i].Model.Geometry.Null[j], Techne.Models[i].Model.Geometry.Folder[j]);
                }
            }
        }

        return info;
    }

    public int populateNull(int id, TC2Info.Null tc2Null, Null tc1Null)
    {
        if(tc1Null.Folder != null)
        {
            for(int j = 0; j < tc1Null.Folder.length; j++)
            {
                tc2Null.Children.extendNullArray();
                id = populateNull(id, tc2Null.Children.Null[j], tc1Null.Folder[j]);
            }
        }

        tc2Null.Children.createNewShapeArray(tc1Null.Shape.length);

        for(int j = 0; j < tc1Null.Shape.length; j++)
        {
            tc2Null.Children.Shape[j].Id = id++;

            tc2Null.Children.Shape[j].Name = tc1Null.Shape[j].Name;
            tc2Null.Children.Shape[j].IsDecorative = tc1Null.Shape[j].IsDecorative;
            tc2Null.Children.Shape[j].IsFixed = tc1Null.Shape[j].IsFixed;
            tc2Null.Children.Shape[j].IsMirrored = tc1Null.Shape[j].IsMirrored;
            tc2Null.Children.Shape[j].Position = tc1Null.Shape[j].Position;
            tc2Null.Children.Shape[j].Offset = tc1Null.Shape[j].Offset;

            String[] rotation = tc1Null.Shape[j].Rotation.split(",");
            tc2Null.Children.Shape[j].Rotation = (rotation[0].equals("0") ? "0" : Float.toString((float)Math.toRadians(Float.parseFloat(rotation[0])))) + "," + (rotation[1].equals("0") ? "0" : Float.toString((float)Math.toRadians(Float.parseFloat(rotation[1])))) + "," + (rotation[2].equals("0") ? "0" : Float.toString((float)Math.toRadians(Float.parseFloat(rotation[2]))));

            tc2Null.Children.Shape[j].Size = tc1Null.Shape[j].Size;
            tc2Null.Children.Shape[j].TextureOffset = tc1Null.Shape[j].TextureOffset;
        }
        return id;
    }
}
