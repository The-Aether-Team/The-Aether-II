package com.gildedgames.aether.common.world.util;

public class FaceList
{
    ConvexFace first, last;


    public ConvexFace getFirst()
    {
    	return this.first;
    }


    void addFirst(ConvexFace face)
    {
        face.inList = true;
        this.first.previous = face;
        face.next = this.first;
        this.first = face;
    }

    public void add(ConvexFace face)
    {
        if (face.inList)
        {
            if (this.first.verticesBeyond.size() < face.verticesBeyond.size())
            {
				this.remove(face);
				this.addFirst(face);
            }
            return;
        }

        face.inList = true;

        if (this.first != null && this.first.verticesBeyond.size() < face.verticesBeyond.size())
        {
            this.first.previous = face;
            face.next = this.first;
            this.first = face;
        }
        else
        {
            if (this.last != null)
            {
                this.last.next = face;
            }
            face.previous = this.last;
            this.last = face;
            if (this.first == null)
            {
                this.first = face;
            }
        }
    }

    public void remove(ConvexFace face)
    {
        if (!face.inList) return;

        face.inList = false;

        if (face.previous != null)
        {
            face.previous.next = face.next;
        }
        else if (face.previous == null)
        {
            this.first = face.next;
        }

        if (face.next != null)
        {
            face.next.previous = face.previous;
        }
        else if (face.next == null)
        {
            this.last = face.previous;
        }

        face.next = null;
        face.previous = null;
    }
}
