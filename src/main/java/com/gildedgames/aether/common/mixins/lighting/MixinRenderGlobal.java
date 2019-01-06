package com.gildedgames.aether.common.mixins.lighting;

import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(RenderGlobal.class)
public abstract class MixinRenderGlobal
{
	@Shadow
	protected abstract void markBlocksForUpdate(int minX, int minY, int minZ, int maxX, int maxY, int maxZ, boolean updateImmediately);

	/**
	 * @author Angeline
	 */
	@Overwrite
	public void notifyLightSet(BlockPos pos)
	{
		int x = pos.getX();
		int y = pos.getY();
		int z = pos.getZ();

		this.markBlocksForUpdate(x - 1, y - 1, z - 1, x + 1, y + 1, z + 1, true);
	}

}
