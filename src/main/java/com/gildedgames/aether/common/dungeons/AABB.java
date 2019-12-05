package com.gildedgames.aether.common.dungeons;

import com.gildedgames.orbis.lib.data.region.IRegion;

import java.util.List;

public class AABB
{
    public int minX, minY, maxX, maxY;

    public AABB(int minX, int minY, int maxX, int maxY) {
        this.minX = minX;
        this.minY = minY;
        this.maxX = maxX;
        this.maxY = maxY;
    }

    public int getWidth() {
        return this.maxX - this.minX;
    }

    public int getHeight() {
        return this.maxY - this.minY;
    }

    public void add(int x, int y) {
        this.minX += x;
        this.maxX += x;

        this.minY += y;
        this.maxY += y;
    }

    public boolean intersects(AABB aabb) {
        return intersects(aabb, 0);
    }

    public boolean intersects(AABB aabb, int padding) {
        return this.minX - padding <= aabb.maxX + padding && this.maxX + padding >= aabb.minX - padding
                && this.minY - padding <= aabb.maxY + padding && this.maxY >= aabb.minY + padding;
    }

    public static <T extends AABB> void fetchIntersecting(T region, List<T> regions, List<T> addTo, int padding) {
        for (T r : regions) {
            if (r.intersects(region, padding)) {
                addTo.add(r);
            }
        }
    }
}
