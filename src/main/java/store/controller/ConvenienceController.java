package store.controller;

import store.model.readProductDTO;
import store.service.ConvenienceService;
import store.view.InputView;

import java.io.IOException;
import java.util.List;

public class ConvenienceController {
    ConvenienceService convenienceService;

    public ConvenienceController() throws IOException {
        convenienceService = new ConvenienceService();
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

    private boolean nextPurchase() {
        return InputView.nextPurchase();
    }

    private void open() {
        convenienceService.open();
    }

    private void purchase() {
        while (true) {
            try {
                List<readProductDTO> input = InputView.readPurchaseProduct();
                purchaseList(input);
                break;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }

        convenienceService.membership(InputView.membership());
    }

    private void purchaseList(List<readProductDTO> input) {
        for ( readProductDTO purchaseInfo : input) {
            convenienceService.purchase(purchaseInfo.getName(), purchaseInfo.getAmount());
        }
    }

    private void getReceipt() {
        convenienceService.getReceipt();
    }


    private void save() {

    }
}
