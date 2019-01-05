package com.gildedgames.aether.common.asm.util;

import org.objectweb.asm.tree.MethodNode;

public class MethodSignature
{
	private final String name, desc;

	public MethodSignature(String name, String desc)
	{
		this.name = name;
		this.desc = desc;
	}

	public boolean matches(MethodNode node)
	{
		return node.name.equals(this.name) && node.desc.equals(this.desc);
	}
}
