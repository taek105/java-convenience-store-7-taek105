package store.domain;

import store.constant.Constants;
import store.constant.ErrorMessage;
import store.util.Utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Promotions {
    private final List<Promotion> promotions;

    public Promotions() {
        this.promotions = new ArrayList<>();
        init();
    }

    private void init() {
        try {
            FileReader fr = new FileReader("src/main/resources/promotions.md");
            readFile(new BufferedReader(fr));
        } catch (IOException e) {
            throw new IllegalArgumentException(ErrorMessage.NOT_VALID_FILEROUTE.getMessages());
        }
    }

    private void readFile(BufferedReader br) throws IOException {
        Utils.skipFirstLine(br);

        String line;
        while ( (line = br.readLine()) != null ) {
            promotions.add(parsePromotion(line));
        }
    }

    private Promotion parsePromotion(String line) {
        String[] split = line.split(",");

        return new Promotion(split[Constants.PROMOTION_NAME_INDEX.getValue()],
                (split[Constants.PROMOTION_BUY_INDEX.getValue()]),
                (split[Constants.PROMOTION_GET_INDEX.getValue()]),
                (split[Constants.PROMOTION_START_DATE_INDEX.getValue()]),
                (split[Constants.PROMOTION_END_DATE_INDEX.getValue()]));
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
