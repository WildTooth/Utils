package com.github.wildtooth.guardian.core.util;

import net.labymod.api.util.io.web.request.Request;
import net.labymod.api.util.io.web.request.Response;
import java.util.ArrayList;

public class DataOutput {

  public static ArrayList<String[]> csv(String url) {
    Response<String> response = Request.ofString()
        .url(url)
        .executeSync();
    ArrayList<String[]> data = new ArrayList<>();
    for (String line : response.get().split("\n")) {
      data.add(line.split(","));
    }
    return data;
  }

}
