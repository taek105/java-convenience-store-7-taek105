package store.controller;

import camp.nextstep.edu.missionutils.Console;
import store.model.readProductDTO;
import store.service.ConvenienceService;
import store.service.InputHandler;
import store.view.InputView;

import java.io.IOException;
import java.util.List;

public class ConvenienceController {
    ConvenienceService convenienceService;

    public ConvenienceController() throws IOException {
        convenienceService = new ConvenienceService();
    }

    public void Start() {
        open();
        purchase();
        getReceipt();
    }

    private void open() {
        convenienceService.open();
    }

    private void purchase() {
        InputView.readPurchaseProduct();
        String input = Console.readLine();
        List<readProductDTO> purchaseProductList = InputHandler.parsePurchaseInput(input);

        for ( readProductDTO purchaseInfo : purchaseProductList) {
            convenienceService.purchase(purchaseInfo.getName(), purchaseInfo.getAmount());
        }
        membership();
    }

    private void membership() {
        InputView.membership();
        String input = Console.readLine();
        convenienceService.membership(InputHandler.parseMembershipFlag(input));
    }

    private void getReceipt() {
        convenienceService.getReceipt();
    }
}
