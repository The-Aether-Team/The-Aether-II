package com.gildedgames.aether.common.dialog;

import com.gildedgames.aether.api.dialog.IDialogNode;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class DialogTree
{

	private final List<IDialogNode> nodes;

	private final IDialogNode rootNode;

	public DialogTree(List<IDialogNode> nodes, IDialogNode rootNode)
	{
		this.nodes = nodes;
		this.rootNode = rootNode;
	}

	@Nonnull
	public IDialogNode getRootNode()
	{
		return this.rootNode;
	}

	@Nullable
	public IDialogNode getNode(String id)
	{
		for (IDialogNode node : this.nodes)
		{
			if (node.getIdentifier().equals(id))
			{
				return node;
			}
		}

		return null;
	}

}
