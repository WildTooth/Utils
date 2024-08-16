package com.github.wildtooth.guardian.api.util;

import net.labymod.api.util.Triple;

public class CoordinateUtil {
  private CoordinateUtil() {

  }

  public static double distance(Triple<Integer, Integer, Integer> point, Triple<Integer, Integer, Integer> otherPoint) {
    return Math.sqrt(
        Math.pow(otherPoint.getLeft() - point.getLeft(), 2) +
        Math.pow(otherPoint.getMiddle() - point.getMiddle(), 2) +
        Math.pow(otherPoint.getRight() - point.getRight(), 2)
    );
  }
}
