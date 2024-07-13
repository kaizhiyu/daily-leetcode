package org.dc.array;

import java.util.HashMap;
import java.util.Map;

public class ArrayInit {
    public static void main(String[] args) {
        Map[] mapArray = {
          new HashMap() {{
              put(' ', 0);
          }}
        };
    }
}
