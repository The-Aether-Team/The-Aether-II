package com.gildedgames.aether.common.util.helpers;

import com.gildedgames.aether.client.ui.data.AssetLocation;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.IResource;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.ModContainer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class ImageUtil
{

	public static IResource getResourceFrom(AssetLocation asset)
	{
		ResourceLocation resource = new ResourceLocation(asset.getDomain(), asset.getPath());

		try
		{
			return Minecraft.getMinecraft().getResourceManager().getResource(resource);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

		return null;
	}

	@SuppressWarnings("resource")
	public static InputStream getStreamFromAsset(AssetLocation asset) throws IOException
	{
		if (asset.getDomain().equals("minecraft"))
		{
			return ImageUtil.getResourceFrom(asset).getInputStream();
		}

		File source = null;

		String path = "assets/" + asset.getDomain() + "/" + asset.getPath();

		for (ModContainer container : Loader.instance().getActiveModList())
		{
			if (container.getModId().equals(asset.getDomain()))
			{
				source = container.getSource();
			}
		}

		if (source != null)
		{
			if (source.isFile())
			{
				ZipFile zipfile = new ZipFile(source);
				ZipEntry zipentry = zipfile.getEntry(path);

				return zipfile.getInputStream(zipentry);
			}

			return new FileInputStream(new File(source, path));
		}

		return null;
	}

	public static BufferedImage getBufferedImage(AssetLocation asset)
	{
		try
		{
			return ImageIO.read(ImageUtil.getStreamFromAsset(asset));
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

		return null;
	}

}
