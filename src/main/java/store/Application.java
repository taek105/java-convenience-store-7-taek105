package store;

import store.controller.ConvenienceController;

import java.io.IOException;

public class Application {
    public static void main(String[] args) throws IOException {
        ConvenienceController convenienceController = new ConvenienceController();

        convenienceController.Start();
    }
}
