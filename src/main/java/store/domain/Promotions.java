package store.domain;

import store.constant.Constant;
import store.constant.ErrorMessage;
import store.util.Util;
import store.util.Validate;

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

    private void readFile() {
        try {
            FileReader fr = new FileReader("src/main/resources/promotions.md");
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
        Validate.parsePromotion(split);

        return new Promotion(split[Constant.PROMOTION_NAME_INDEX.getValue()],
                (split[Constant.PROMOTION_BUY_INDEX.getValue()]),
                (split[Constant.PROMOTION_GET_INDEX.getValue()]),
                (split[Constant.PROMOTION_START_DATE_INDEX.getValue()]),
                (split[Constant.PROMOTION_END_DATE_INDEX.getValue()]));
    }

    public Promotion getPromotion(String promotionName) {
        if ( promotionName.equals("null") ) {
            return Promotion.nullPromotion();
        }

        for (Promotion promotion : promotions) {
            if ( promotion.getName().equals(promotionName) )
                return promotion;
        }

        throw new IllegalArgumentException(ErrorMessage.NOT_EXISTENCE_PROMOTION.getMessages());
    }

}
