package store.domain;

import store.constant.PromotionConstant;
import store.constant.ErrorMessage;
import store.constant.FilePath;
import store.util.Util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Promotions {
    private final List<Promotion> promotions;

    public Promotions() {
        this.promotions = new ArrayList<>();
        readFile();
    }

    public Promotion getPromotion(String promotionName) {
        if ( promotionName.equals(PromotionConstant.NULL_PROMOTION_NAME.getStringValue()) ) {
            return Promotion.nullPromotion();
        }

        for (Promotion promotion : promotions) {
            if ( promotion.getName().equals(promotionName) ) {
                return promotion;
            }
        }
        throw new IllegalArgumentException(ErrorMessage.NOT_EXISTENCE_PROMOTION.getMessages());
    }

    private void readFile() {
        try {
            FileReader fr = new FileReader(FilePath.PROMOTION_MD.getValue());
            parseFile(new BufferedReader(fr));
        } catch (IOException e) {
            throw new IllegalArgumentException(ErrorMessage.NOT_VALID_FILE_ROUTE.getMessages());
        }
    }

    private void parseFile(BufferedReader br) throws IOException {
        Util.skipFirstLine(br);

        String line;
        while ( (line = br.readLine()) != null ) {
            promotions.add(parsePromotion(line));
        }
    }

    private Promotion parsePromotion(String line) {
        String[] split = line.split(",");
        parsePromotionValidate(split);

        return new Promotion(split);
    }

    private static void parsePromotionValidate(String[] split) {
        if ( split.length-1 != PromotionConstant.PROMOTION_END_DATE_INDEX.getValue() ) {
            throw new IllegalArgumentException(ErrorMessage.NOT_VALID_FILE_FORMAT.getMessages());
        }
    }
}
