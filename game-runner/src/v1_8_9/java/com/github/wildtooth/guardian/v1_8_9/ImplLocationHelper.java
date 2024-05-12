package com.github.wildtooth.guardian.v1_8_9;

import com.github.wildtooth.guardian.api.refrences.LocationHelper;
import net.labymod.api.Laby;
import net.labymod.api.client.Minecraft;
import net.labymod.api.models.Implements;
import net.labymod.api.util.IntIntTriple;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import org.jetbrains.annotations.Nullable;

@Implements(LocationHelper.class)
public class ImplLocationHelper implements LocationHelper {

  @Override
  public IntIntTriple<Integer> getIntCoordinates(double range) {
    Minecraft mc = Laby.labyAPI().minecraft();
    EntityPlayerSP player = (EntityPlayerSP) mc.getClientPlayer();
    if (player == null) {
      return null;
    }
    BlockPos pos = getLookedBlock(player, range);
    if (pos == null || mc.clientWorld() == null) {
      return null;
    }
    int x = pos.getX();
    int y = pos.getY();
    int z = pos.getZ();
    return new IntIntTriple<>(x, y, z);
  }

  @Nullable
  private BlockPos getLookedBlock(EntityPlayer player, double range) {
    Vec3 eyesPos = player.getPositionEyes(0F);
    Vec3 lookVec = player.getLook(0F);
    Vec3 rangeLookVec = eyesPos.addVector(lookVec.xCoord * range, lookVec.yCoord * range, lookVec.zCoord * range);
    MovingObjectPosition hit = player.worldObj.rayTraceBlocks(eyesPos, rangeLookVec, false, false, true);
    if (hit != null && hit.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
      return hit.getBlockPos();
    } else {
      return null;
    }
  }
}
