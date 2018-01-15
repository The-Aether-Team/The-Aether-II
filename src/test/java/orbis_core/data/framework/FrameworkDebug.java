package orbis_core.data.framework;

import com.gildedgames.aether.api.orbis.IRegion;
import com.gildedgames.aether.api.orbis_core.data.region.Region;
import com.gildedgames.orbis.common.data.framework.FrameworkAlgorithm;
import com.gildedgames.orbis.common.data.framework.FrameworkData;
import com.gildedgames.orbis.common.data.framework.Graph;
import com.gildedgames.orbis.common.data.framework.generation.ComputedParamFac;
import com.gildedgames.orbis.common.data.framework.generation.FDGDEdge;
import com.gildedgames.orbis.common.data.framework.generation.FDGDNode;
import net.minecraft.init.Bootstrap;
import net.minecraft.util.math.BlockPos;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

/**
 * Visualises the Framework and pathway algorithms
 * for debugging purposes. Hold down space to run, 
 * use the arrow keys to look around and use e and w
 * to zoom in and out.
 * @author Emile
 *
 */
public class FrameworkDebug
{
	private final FrameworkData toDebug = FrameworkDataset.framework1();

	private final FrameworkAlgorithm algorithm;

	private static double left = -200, right = 200, bottom = -200, top = 200;

	public FrameworkDebug()
	{
		this.algorithm = new FrameworkAlgorithm(this.toDebug, new ComputedParamFac(), null);
	}

	public static void main(String[] args)
	{
		try
		{
			Bootstrap.register();

			Display.setDisplayMode(new DisplayMode(1000, 800));
			Display.setTitle("Framework Debug");
			Display.create();
			final FrameworkDebug screen = new FrameworkDebug();
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

	public void update()
	{
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

		// set the color of the quad (R,G,B,A)
		GL11.glColor3f(0.5f, 0.5f, 1.0f);

		if (Keyboard.isKeyDown(Keyboard.KEY_SPACE))
		{
			this.algorithm.step();
		}
		double dx = right - left;
		double dy = top - bottom;

		double ddx = 0.01 * dx;
		double ddy = 0.01 * dy;

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

		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GL11.glOrtho(left, right, bottom, top, 1, -1);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);

		final Graph<FDGDNode, FDGDEdge> graph = this.algorithm.getFDGDDebug();
		if (graph == null)
		{
			return;
		}
		if (this.algorithm.getPhase() != FrameworkAlgorithm.Phase.PATHWAYS)
		{
			for (final FDGDNode node : graph.vertexSet())
			{
				glDrawRegion(node, 0.5f, 0.5f, 1.0f);
			}
		}
//		else
//		{
//			final List<FrameworkFragment> fragments = this.algorithm.getFragments();
//			for (final FrameworkFragment fragment : fragments)
//			{
//				glDrawRegion(fragment, 0.5f, 0.5f, 1.0f);
//			}
//
//			for (final PathwayNode node : this.algorithm.getPathfindingDebug())
//			{
//				glDrawRegion(node, 1.0f, 0.5f, 0.5f);
//				glDrawRegion(new Region(new BlockPos(node.endConnection.getX() - 1, 0, node.endConnection.getZ() - 1), new BlockPos(node.endConnection.getX() + 1, 0, node.endConnection.getZ() + 1)), 0.5f, 1.0f, 0.5f);
//			}
//		}
		for (final FDGDEdge edge : graph.edgeSet())
		{
			glDrawEdge(edge);
		}
		glDrawRegion(new Region(new BlockPos(-2, -2, -2), new BlockPos(2, 2, 2)), 1.0f, 1.0f, 0.5f);
	}

	public static void glDrawEdge(FDGDEdge edge)
	{
		GL11.glBegin(GL11.GL_LINES);
		GL11.glColor3f(1.0f, 0.2f, 1.0f);
		GL11.glVertex2f(edge.entrance1X(), edge.entrance1Z());
		GL11.glVertex2f(edge.entrance2X(), edge.entrance2Z());
		GL11.glEnd();
		glDrawRegion(new Region(new BlockPos(edge.entrance1X() - 1, 0, edge.entrance1Z() - 1), new BlockPos(edge.entrance1X() + 1, 0, edge.entrance1Z() + 1)), 0.5f, 1.0f, 0.5f);
		glDrawRegion(new Region(new BlockPos(edge.entrance2X() - 1, 0, edge.entrance2Z() - 1), new BlockPos(edge.entrance2X() + 1, 0, edge.entrance2Z() + 1)), 0.5f, 1.0f, 0.5f);
	}

	public static void glDrawRegion(IRegion region, float r, float g, float b)
	{
		GL11.glColor3f(r, g, b);
		final BlockPos min = region.getMin();
		final BlockPos max = region.getMax();
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glVertex2f(min.getX(), min.getZ());
		GL11.glVertex2f(max.getX(), min.getZ());
		GL11.glVertex2f(max.getX(), max.getZ());
		GL11.glVertex2f(min.getX(), max.getZ());
		GL11.glEnd();
	}
}
