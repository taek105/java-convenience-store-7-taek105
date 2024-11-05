package store.util;

import java.io.BufferedReader;
import java.io.IOException;

public class Utils {
    public static void skipFirstLine(BufferedReader br) throws IOException {
        br.readLine();
    }
}
