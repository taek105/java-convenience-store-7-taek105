package store;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import java.io.*;

public class HtTest {
    private PrintStream originalOut;
    private OutputStream captor;

    @BeforeEach
    void init() {
        originalOut = System.out;
        captor = new ByteArrayOutputStream();
        System.setOut(new PrintStream(captor));
    }

    @AfterEach
    void setAfter() {
        System.setOut(originalOut);
        System.out.println(output());
    }

    protected final String output() {
        return captor.toString().trim();

    }
}