package store.domain;

import camp.nextstep.edu.missionutils.DateTimes;
import store.constant.Constants;
import store.constant.ErrorMessage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
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
            readPromotions(new BufferedReader(fr));
        } catch (IOException e) {
            throw new IllegalArgumentException(ErrorMessage.NOT_VALID_FILEROUTE.getMessages());
        }
    }

    private void readPromotions(BufferedReader br) throws IOException {
        String line = br.readLine(); // 입력 양식 빼기

        while ( (line = br.readLine()) != null ) {
            String[] split = line.split(",");
            Promotion promotion =
                    new Promotion(split[Constants.PROMOTION_NAME_INDEX.getValue()],
                            (split[Constants.PROMOTION_BUY_INDEX.getValue()]),
                            (split[Constants.PROMOTION_GET_INDEX.getValue()]),
                            (split[Constants.PROMOTION_START_DATE_INDEX.getValue()]),
                            (split[Constants.PROMOTION_END_DATE_INDEX.getValue()]));
            promotions.add(promotion);
        }
    }

    public Promotion getPromotion(String promotionName) {
        for (Promotion promotion : promotions) {
            if ( promotion.getName().equals(promotionName) )
                return promotion;
        }

        throw new IllegalArgumentException(ErrorMessage.NOT_EXISTENCE_PROMOTION.getMessages());
    }

    public boolean isPromotionNow(String promotionName) {
        LocalDateTime now = DateTimes.now();
        Promotion promotion = getPromotion(promotionName);
        return now.isAfter(promotion.getStartDate()) && now.isBefore(promotion.getEndDate());
    }
}
