package store.controller;

import store.model.ReadProductDTO;
import store.service.ConvenienceService;
import store.view.InputView;

import java.io.IOException;
import java.util.List;

public class ConvenienceController {
    ConvenienceService convenienceService;

    public ConvenienceController() {
        try {
            convenienceService = new ConvenienceService();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void Start() {
        boolean flag = true;
        while ( flag ) {
            open();
            purchase();
            getReceipt();
            save();
            flag = nextPurchase();
        }
    }

    private void open() {
        convenienceService.open();
    }

    private void purchase() {
        while (true) {
            try {
                convenienceService.init();
                purchaseList(InputView.readPurchaseProduct());
                break;
            } catch (IllegalArgumentException | IOException e) {
                System.out.println(e.getMessage());
            }
        }
        convenienceService.membership(InputView.membership());
    }

    private void purchaseList(List<ReadProductDTO> input) throws IOException {
        for ( ReadProductDTO purchaseInfo : input) {
            convenienceService.purchase(purchaseInfo.getName(), purchaseInfo.getAmount());
        }
    }

    private void getReceipt() {
        convenienceService.getReceipt();
    }

    private void save() {
        convenienceService.save();
    }

    private boolean nextPurchase() {
        return InputView.nextPurchase();
    }
}
