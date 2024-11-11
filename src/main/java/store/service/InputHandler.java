package store.service;

import store.constant.InputConstant;
import store.model.ReadProductDTO;

import java.util.ArrayList;
import java.util.List;

public class InputHandler {
    public static List<ReadProductDTO> parsePurchaseInput(String input) {
        List<ReadProductDTO> purchaseList = new ArrayList<>();
        initPurchaseList(input, purchaseList);
        return purchaseList;
    }

    public static String subFormatting(String inputProductFormat) {
        return inputProductFormat.substring(InputConstant.INPUT_FORMAT_INDEX.getValue(),
                inputProductFormat.length() - InputConstant.INPUT_FORMAT_INDEX.getValue());
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

    private static void initPurchaseList(String input, List<ReadProductDTO> purchaseList) {
        String[] split = input.split(",");
        for (String inputProductFormat : split) {
            InputValidator.purchaseInputValidate(inputProductFormat);

            addPurchaseProduct(purchaseList, inputProductFormat);
        }
    }

    private static void addPurchaseProduct(List<ReadProductDTO> purchaseList, String inputProductFormat) {
        inputProductFormat = subFormatting(inputProductFormat);
        String[] splitByDash = inputProductFormat.split("-");
        purchaseList.add(new ReadProductDTO(splitByDash[0], Integer.parseInt(splitByDash[1])));
    }
}
