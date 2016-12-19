package com.gildedgames.aether.common.dialog.data;

import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.dialog.*;
import com.gildedgames.aether.common.dialog.util.DialogNodeFactory;
import com.gildedgames.aether.common.dialog.util.DialogTreeFactory;
import net.minecraft.util.ResourceLocation;

public class EdisonDialog
{

	private EdisonDialog() {}

	public static class Nodes
	{

		private Nodes()
		{

		}

		public static final ResourceLocation EDISON = AetherCore.getResource("edison");

		public static final ResourceLocation DISPLEASED = AetherCore.getResource("textures/gui/dialog/edison_displeased.png");

		public static final ResourceLocation GRIN = AetherCore.getResource("textures/gui/dialog/edison_grin.png");

		public static final ResourceLocation NEUTRAL = AetherCore.getResource("textures/gui/dialog/edison_neutral.png");

		public static final ResourceLocation SIGH = AetherCore.getResource("textures/gui/dialog/edison_sigh.png");

		public static final ResourceLocation THOUGHT = AetherCore.getResource("textures/gui/dialog/edison_thought.png");

		public static final IDialogNode BUSY = DialogNodeFactory.build("busy").speaker(EDISON).text(NEUTRAL, "edison.them.busy")
				.closeButton("edison.us.goodbye").flush();

		public static final IDialogNode OUTPOST_START = DialogNodeFactory.build("outpost_start").speaker(EDISON).text(SIGH, "edison.them.outpost_start")
				.button("edison.us.who_are_you", "who_are_you")
				.button("edison.us.what_is_this_place", "what_is_this_place")
				.button("edison.us.why_am_i_here", "why_am_i_here")
				.closeButton("edison.us.goodbye").flush();

		public static final IDialogNode WHO_ARE_YOU = DialogNodeFactory.build("who_are_you").speaker(EDISON).text(GRIN, "edison.them.who_are_you").backToRootAction().flush();

		public static final IDialogNode WHAT_IS_THIS_PLACE = DialogNodeFactory.build("what_is_this_place").speaker(EDISON).text(NEUTRAL, "edison.them.what_is_this_place")
				.button("edison.us.the_world_were_in", "the_world_were_in")
				.button("edison.us.this_outpost", "this_outpost").flush();

		public static final IDialogNode VALKYRIES = DialogNodeFactory.build("valkyries").speaker(EDISON)
				.text(GRIN, "edison.them.valkyries.1")
				.text(THOUGHT, "edison.them.valkyries.2")
				.text(NEUTRAL, "edison.them.valkyries.3")
				.backToRootAction().flush();

		public static final IDialogNode THE_WORLD_WERE_IN = DialogNodeFactory.build("the_world_were_in").speaker(EDISON)
				.text(GRIN, "edison.them.the_world_were_in.1")
				.text(NEUTRAL, "edison.them.the_world_were_in.2")
				.button("edison.us.valkyries", "valkyries").flush();

		public static final IDialogNode THIS_OUTPOST = DialogNodeFactory.build("this_outpost").speaker(EDISON)
				.text(GRIN, "edison.them.this_outpost.1")
				.text(NEUTRAL, "edison.them.this_outpost.2")
				.button("edison.us.valkyries", "valkyries").flush();

		public static final IDialogNode WHY_AM_I_HERE = DialogNodeFactory.build("why_am_i_here").speaker(EDISON)
				.text(THOUGHT, "edison.them.why_am_i_here.1")
				.text(NEUTRAL, "edison.them.why_am_i_here.2")
				.text(THOUGHT, "edison.them.why_am_i_here.3")
				.text(GRIN, "edison.them.why_am_i_here.4")
				.backToRootAction().flush();
	}

	public static class Scenes
	{

		private Scenes()
		{

		}

		public static final DialogTree OUTPOST_SCENE = DialogTreeFactory.build().addNodes(Nodes.OUTPOST_START, Nodes.WHO_ARE_YOU, Nodes.WHAT_IS_THIS_PLACE, Nodes.THE_WORLD_WERE_IN, Nodes.THIS_OUTPOST, Nodes.WHY_AM_I_HERE, Nodes.VALKYRIES).setRootNode(Nodes.OUTPOST_START).flush();

		public static final DialogTree BUSY_SCENE = DialogTreeFactory.build().addNodes(Nodes.BUSY).setRootNode(Nodes.BUSY).flush();

	}

}
