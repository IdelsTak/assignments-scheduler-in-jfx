package com.github.idelstak.schedulerfx.utils;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import org.junit.Test;

public class Formatter {

  @Test
  public void testFormatter() throws Exception {
    NumberFormat nf3 = new DecimalFormat("0000");
    String out = nf3.format(50);

    System.out.println("-------");
    System.out.println(out);
  }
}
