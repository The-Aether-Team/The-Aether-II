package com.gildedgames.aether.common.containers;

import com.gildedgames.orbis.client.gui.util.GuiFrameNoContainer;
import com.gildedgames.orbis.client.rect.Pos2D;
import com.gildedgames.orbis.client.rect.Rect;
import com.gildedgames.orbis.client.rect.RectBuilder;
import net.minecraft.entity.player.EntityPlayer;

public class ContainerDialogController extends GuiFrameNoContainer implements Rect {

	private final EntityPlayer player;

	public ContainerDialogController(final EntityPlayer player)
	{
		this.player = player;
	}


	@Override
	public void init() {

	}

	@Override
	public RectBuilder rebuild() {
		return null;
	}

	@Override
	public float degrees() {
		return 0;
	}

	@Override
	public float originX() {
		return 0;
	}

	@Override
	public float originY() {
		return 0;
	}

	@Override
	public float scale() {
		return 0;
	}

	@Override
	public float maxX() {
		return 0;
	}

	@Override
	public float maxY() {
		return 0;
	}

	@Override
	public float centerX() {
		return 0;
	}

	@Override
	public float centerY() {
		return 0;
	}

	@Override
	public Pos2D center() {
		return null;
	}

	@Override
	public float x() {
		return 0;
	}

	@Override
	public float y() {
		return 0;
	}

	@Override
	public float width() {
		return 0;
	}

	@Override
	public float height() {
		return 0;
	}

	@Override
	public boolean isCenteredX() {
		return false;
	}

	@Override
	public boolean isCenteredY() {
		return false;
	}

	@Override
	public boolean intersects(float v, float v1) {
		return false;
	}

	@Override
	public boolean intersects(Rect rect) {
		return false;
	}
}
