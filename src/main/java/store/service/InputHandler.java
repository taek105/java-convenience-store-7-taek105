package store.service;

import store.model.readProductDTO;

import java.util.ArrayList;
import java.util.List;

public class InputHandler {
    public static List<readProductDTO> parsePurchaseInput(String input) {
        List<readProductDTO> purchaseList = new ArrayList<>();
        String[] split = input.split(",");
        for (String inputProductFormat : split) {
            InputValidator.purchaseInputValidate(inputProductFormat);

            inputProductFormat = inputProductFormat.substring(1, inputProductFormat.length() - 1);
            String[] splitByDash = inputProductFormat.split("-");
            purchaseList.add(new readProductDTO(splitByDash[0], Integer.parseInt(splitByDash[1])));
        }
        return purchaseList;
    }

    public static boolean parseMembershipFlag(String input) {
        InputValidator.flagValidate(input);
        return input.equals("Y");
    }

    public static boolean parseNotifyPromotionQunatity(String input) {
        InputValidator.flagValidate(input);
        return input.equals("Y");
    }

    public static boolean parseNotifyExtraProducts(String input) {
        InputValidator.flagValidate(input);
        return input.equals("Y");
    }

    public static boolean parseNextPurchase(String input) {
        InputValidator.flagValidate(input);
        return input.equals("Y");
    }
}
