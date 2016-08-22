package com.gildedgames.aether.common.world.dungeon.util;

import java.util.Stack;

class ConvexObjectManager
{
	Stack<ConvexFace> recycledFaceStack;

	Stack<FaceConnector> connectorStack;

	Stack<VertexBuffer> emptyBufferStack;

	Stack<DeferredFace> deferredFaceStack;

	public void depositFace(ConvexFace face)
	{
		for (int i = 0; i < 3; i++)
		{
			face.adjacentFaces[i] = null;
		}
		this.recycledFaceStack.push(face);
	}

	public ConvexFace getFace()
	{
		return this.recycledFaceStack.size() != 0 ? this.recycledFaceStack.pop() : new ConvexFace(this.getVertexBuffer());
	}

	public void depositConnector(FaceConnector connector)
	{
		this.connectorStack.push(connector);
	}

	public FaceConnector getConnector()
	{
		return this.connectorStack.size() != 0 ? this.connectorStack.pop() : new FaceConnector();
	}

	public void depositVertexBuffer(VertexBuffer buffer)
	{
		buffer.clear();
		this.emptyBufferStack.push(buffer);
	}

	public VertexBuffer getVertexBuffer()
	{
		return this.emptyBufferStack.size() != 0 ? this.emptyBufferStack.pop() : new VertexBuffer();
	}

	public void depositDeferredFace(DeferredFace face)
	{
		this.deferredFaceStack.push(face);
	}

	public DeferredFace getDeferredFace()
	{
		return this.deferredFaceStack.size() != 0 ? this.deferredFaceStack.pop() : new DeferredFace();
	}

	public ConvexObjectManager()
	{
		this.recycledFaceStack = new Stack<>();
		this.connectorStack = new Stack<>();
		this.emptyBufferStack = new Stack<>();
		this.deferredFaceStack = new Stack<>();
	}
}
