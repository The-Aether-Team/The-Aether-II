package com.gildedgames.aether.island_gen;

import com.gildedgames.aether.api.util.OpenSimplexNoise;
import com.gildedgames.aether.api.world.islands.IIslandBounds;
import com.gildedgames.aether.api.world.islands.IIslandData;
import com.gildedgames.aether.common.registry.content.BiomesAether;
import com.gildedgames.aether.common.world.aether.biomes.BiomeAetherBase;
import com.gildedgames.aether.common.world.aether.island.data.IslandBounds;
import com.gildedgames.aether.common.world.aether.island.data.IslandData;
import com.gildedgames.aether.common.world.aether.island.voronoi.VoronoiGraph;
import com.gildedgames.aether.common.world.aether.island.voronoi.VoronoiGraphUtils;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Bootstrap;
import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;
import java.util.Random;

public class IslandGenPreview
{

	private static final int NUM_LLOYD_RELAXATIONS = 4;

	private static double left = -200, right = 200, bottom = -200, top = 200;

	private IIslandData island;

	private OpenSimplexNoise noise;

	private BufferedImage bufferedImage;

	private int textureID;

	private VoronoiGraph<Color> voronoi;

	public IslandGenPreview()
	{

	}

	public static void main(final String[] args)
	{
		try
		{
			Bootstrap.register();

			Display.setDisplayMode(new DisplayMode(1000, 800));
			Display.setTitle("Island Generation");
			Display.create();

			final IslandGenPreview screen = new IslandGenPreview();

			GL11.glMatrixMode(GL11.GL_PROJECTION);
			GL11.glLoadIdentity();
			GL11.glOrtho(left, right, bottom, top, 1, -1);
			GL11.glMatrixMode(GL11.GL_MODELVIEW);

			while (!Display.isCloseRequested())
			{
				screen.update();
				Display.update();
				Display.sync(60);
			}
		}
		catch (final LWJGLException e)
		{
			e.printStackTrace();
			Display.destroy();
			System.exit(1);
		}
	}

	private void generateIsland()
	{
		final Random rand = new Random();
		final long seed = rand.nextLong();

		this.noise = new OpenSimplexNoise(seed);

		final int width = 1280;
		final int height = 255;
		final int length = 1280;

		final int x = 0;
		final int y = 10;
		final int z = 0;

		final IIslandBounds bounds = new IslandBounds(x, y, z, x + width, y + height, z + length);

		final BiomeAetherBase chosen = BiomesAether.HIGHLANDS;

		this.island = new IslandData(null, bounds, chosen, seed);

		this.island.addComponents(chosen.createIslandComponents(this.island));
	}

	private void renderImage(final BufferedImage image)
	{
		final int[] pixels = new int[image.getWidth() * image.getHeight()];
		image.getRGB(0, 0, image.getWidth(), image.getHeight(), pixels, 0, image.getWidth());

		final ByteBuffer buffer = BufferUtils.createByteBuffer(image.getWidth() * image.getHeight() * 4); //4 for RGBA, 3 for RGB

		this.textureID = GL11.glGenTextures();

		for (int y = 0; y < image.getHeight(); y++)
		{
			for (int x = 0; x < image.getWidth(); x++)
			{
				final int pixel = pixels[y * image.getWidth() + x];
				buffer.put((byte) ((pixel >> 16) & 0xFF));     // Red component
				buffer.put((byte) ((pixel >> 8) & 0xFF));      // Green component
				buffer.put((byte) (pixel & 0xFF));               // Blue component
				buffer.put((byte) ((pixel >> 24) & 0xFF));    // Alpha component. Only for RGBA
			}
		}

		buffer.flip(); //FOR THE LOVE OF GOD DO NOT FORGET THIS

		// You now have a ByteBuffer filled with the color data of each pixel.
		// Now just create a texture ID and bind it. Then you can load it using
		// whatever OpenGL method you want, for example:
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.textureID);
		GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA8, image.getWidth(), image.getHeight(), 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, buffer);
	}

	public BufferedImage paintIsland()
	{
		final BufferedImage bufferedImage = new BufferedImage(this.island.getBounds().getWidth(), this.island.getBounds().getLength(),
				BufferedImage.TYPE_INT_ARGB);

		final int chunkMaxX = this.island.getBounds().getWidth() >> 4;
		final int chunkMaxZ = this.island.getBounds().getLength() >> 4;

		for (int chunkX = 0; chunkX < chunkMaxX; chunkX++)
		{
			for (int chunkZ = 0; chunkZ < chunkMaxZ; chunkZ++)
			{
				final ChunkPrimerIsland primer = new ChunkPrimerIsland();

				this.island.getGenerator().genIslandForChunk(this.noise, new BlockAccessIsland(), primer, this.island, chunkX, chunkZ);

				for (int x = 0; x < 15; ++x)
				{
					for (int z = 0; z < 15; ++z)
					{
						final IBlockState state = primer.findGroundBlock(x, z);
						final int height = primer.findGroundBlockIdx(x, z);

						final float value = (float) (height - this.island.getBounds().getMinY()) / (this.island.getBounds().getHeight());

						if (state != null)
						{
							final int color = 0xFFFFFFFF;

							final float r = (color >> 16 & 0xff) / 255F;
							final float g = (color >> 8 & 0xff) / 255F;
							final float b = (color & 0xff) / 255F;

							bufferedImage.setRGB(x + (chunkX * 15), z + (chunkZ * 15), new Color(r, g, b, 1).getRGB());
						}
						else
						{
							final int color = 0xFF262626;

							bufferedImage.setRGB(x + (chunkX * 15), z + (chunkZ * 15), color);
						}
					}
				}
			}
		}

		return bufferedImage;
	}

	private void makeIsland()
	{
		this.generateIsland();

		this.bufferedImage = this.paintIsland();

		this.renderImage(this.bufferedImage);
	}

	public void update()
	{
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

		if (this.bufferedImage == null)
		{
			this.makeIsland();
		}

		this.controls();
		this.draw();
	}

	private BufferedImage paintVoronoi()
	{
		final BufferedImage bufferedImage = new BufferedImage((int) this.voronoi.bounds.width, (int) this.voronoi.bounds.height, BufferedImage.TYPE_4BYTE_ABGR);

		VoronoiGraphUtils.paint(this.voronoi, bufferedImage.createGraphics(), true, true, false, false, false);

		return bufferedImage;
	}

	public void controls()
	{
		while (Keyboard.next())
		{
			if (Keyboard.getEventKeyState() && Keyboard.getEventKey() == Keyboard.KEY_F)
			{
				this.makeIsland();
			}
		}

		final double dx = right - left;
		final double dy = top - bottom;

		final double ddx = 0.01 * dx;
		final double ddy = 0.01 * dy;

		if (Keyboard.isKeyDown(Keyboard.KEY_UP))
		{
			bottom += ddy;
			top += ddy;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_DOWN))
		{
			bottom -= ddy;
			top -= ddy;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT))
		{
			right += ddx;
			left += ddx;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_LEFT))
		{
			right -= ddx;
			left -= ddx;
		}

		if (Keyboard.isKeyDown(Keyboard.KEY_E))
		{
			top -= ddy;
			bottom += ddy;
			right -= ddx;
			left += ddx;
		}

		if (Keyboard.isKeyDown(Keyboard.KEY_W))
		{
			top += ddy;
			bottom -= ddy;
			right += ddx;
			left -= ddx;
		}
	}

	public void draw()
	{
		GL11.glPushMatrix();

		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GL11.glOrtho(left, right, bottom, top, 1, -1);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);

		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

		GL11.glEnable(GL11.GL_TEXTURE_2D);

		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);

		GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.textureID);
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glTexCoord2f(0, 0);
		GL11.glVertex2i(0, 0);

		GL11.glTexCoord2f(1, 0);
		GL11.glVertex2i(this.island.getBounds().getWidth(), 0);

		GL11.glTexCoord2f(1, 1);

		GL11.glVertex2i(this.island.getBounds().getWidth(), this.island.getBounds().getLength());

		GL11.glTexCoord2f(0, 1);
		GL11.glVertex2i(0, this.island.getBounds().getLength());
		GL11.glEnd();

		GL11.glPopMatrix();
	}
}
