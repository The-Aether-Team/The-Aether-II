package com.gildedgames.aether.common.dialog.util;

import com.gildedgames.aether.common.dialog.DialogTree;
import com.gildedgames.aether.common.dialog.IDialogNode;
import com.google.common.collect.Lists;

import java.util.List;

public class DialogTreeFactory
{

	private List<IDialogNode> nodes = Lists.newArrayList();

	private IDialogNode rootNode;

	private DialogTreeFactory()
	{

	}

	public static DialogTreeFactory build()
	{
		return new DialogTreeFactory();
	}

	public DialogTreeFactory addNodes(IDialogNode... nodes)
	{
		for (IDialogNode node : nodes)
		{
			if (node != null)
			{
				this.nodes.add(node);
			}
		}

		return this;
	}

	public DialogTreeFactory setRootNode(IDialogNode node)
	{
		this.rootNode = node;

		return this;
	}

	public DialogTree flush()
	{
		return new DialogTree(this.nodes, this.rootNode);
	}

}
