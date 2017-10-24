package com.gildedgames.orbis.common.player.godmode;

import com.gildedgames.aether.api.orbis.util.OrbisRotation;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.network.AetherGuiHandler;
import com.gildedgames.orbis.client.player.godmode.GodPowerBlueprintClient;
import com.gildedgames.orbis.client.player.godmode.IGodPowerClient;
import com.gildedgames.orbis.common.data.BlueprintData;
import com.gildedgames.orbis.common.items.ItemBlueprint;
import com.gildedgames.orbis.common.player.PlayerOrbisModule;
import com.gildedgames.orbis.common.player.godmode.selectors.ShapeSelectorBlueprint;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;

import javax.annotation.Nullable;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class GodPowerBlueprint implements IGodPower
{

	private final ShapeSelectorBlueprint shapeSelector;

	private final OrbisRotation placingRotation = OrbisRotation.neutral();

	private GodPowerBlueprintClient clientHandler;

	private BlueprintData placingBlueprint;

	private File placingBlueprintFile;

	public GodPowerBlueprint()
	{
		if (AetherCore.isClient())
		{
			this.clientHandler = new GodPowerBlueprintClient(this);
		}

		this.shapeSelector = new ShapeSelectorBlueprint(this);
	}

	public OrbisRotation getPlacingRotation()
	{
		return this.placingRotation;
	}

	public BlueprintData getPlacingBlueprint()
	{
		return this.placingBlueprint;
	}

	public void setPlacingBlueprint(final BlueprintData placingBlueprint)
	{
		this.placingBlueprint = placingBlueprint;
	}

	@Override
	public void write(final NBTTagCompound tag)
	{

	}

	@Override
	public void read(final NBTTagCompound tag)
	{

	}

	@Override
	public void onUpdate(final EntityPlayer player, final PlayerOrbisModule module, final boolean isPowerActive)
	{
		final ItemStack stack = player.getHeldItemMainhand();

		if (stack.getItem() instanceof ItemBlueprint)
		{
			final File file = ItemBlueprint.getBlueprintPath(stack);

			if ((this.placingBlueprintFile == null || !this.placingBlueprintFile.getPath().equals(file.getPath())) && file != null)
			{
				this.placingBlueprintFile = file;

				try (FileInputStream in = new FileInputStream(this.placingBlueprintFile))
				{
					final NBTTagCompound tag = CompressedStreamTools.readCompressed(in);

					final BlueprintData data = new BlueprintData(player.world);

					data.read(tag);

					this.placingBlueprint = data;
				}
				catch (final IOException e)
				{
					AetherCore.LOGGER.error("Failed to save Blueprint to disk", e);
				}
			}
		}
		else
		{
			this.placingBlueprint = null;
			this.placingBlueprintFile = null;
		}
	}

	@Override
	public boolean hasCustomGui()
	{
		return true;
	}

	@Override
	public void onOpenGui(final EntityPlayer player)
	{
		final BlockPos pos = player.getPosition();

		player.openGui(AetherCore.INSTANCE, AetherGuiHandler.ORBIS_BLUEPRINT_LOAD, player.world, pos.getX(), pos.getY(), pos.getZ());
	}

	@Override
	public boolean canInteractWithItems(final PlayerOrbisModule module)
	{
		final ItemStack held = module.getEntity().getHeldItemMainhand();

		return held != ItemStack.EMPTY;
	}

	@Nullable
	@Override
	public IShapeSelector getShapeSelector()
	{
		return this.shapeSelector;
	}

	@Override
	public IGodPowerClient getClientHandler()
	{
		return this.clientHandler;
	}
}
