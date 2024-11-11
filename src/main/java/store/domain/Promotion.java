package store.domain;

import camp.nextstep.edu.missionutils.DateTimes;
import store.constant.PromotionConstant;

import java.time.LocalDateTime;
import java.time.Month;

public class Promotion {
    private final String name;
    private final int buy;
    private final int get;
    private final LocalDateTime startDate;
    private final LocalDateTime endDate;

    public Promotion(String[] split) {
        this.name = split[PromotionConstant.PROMOTION_NAME_INDEX.getValue()];
        this.buy = Integer.parseInt(split[PromotionConstant.PROMOTION_BUY_INDEX.getValue()]);
        this.get = Integer.parseInt(split[PromotionConstant.PROMOTION_GET_INDEX.getValue()]);
        this.startDate = parseLocalDateTime(split[PromotionConstant.PROMOTION_START_DATE_INDEX.getValue()]);
        this.endDate = parseLocalDateTime(split[PromotionConstant.PROMOTION_END_DATE_INDEX.getValue()]);
    }

    public Promotion(String name, int buy, int get, LocalDateTime startDate, LocalDateTime endDate) {
        this.name = name;
        this.buy = buy;
        this.get = get;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getName() {
        return name;
    }

    public int getBuy() {
        return buy;
    }

    public int getGet() {
        return get;
    }

    public boolean isPromotionNow() {
        LocalDateTime now = DateTimes.now();
        return ((!now.isBefore(startDate)) && now.isBefore(endDate));
    }

    public static Promotion nullPromotion() {
        return new Promotion( PromotionConstant.NULL_PROMOTION_NAME.getStringValue()
                , PromotionConstant.PROMOTION_NULL_BUY.getValue()
                , PromotionConstant.PROMOTION_NULL_GET.getValue()
                ,getNullDate()
                ,getNullDate());
    }

    public boolean isEmpty() {
        return !( this.name.equals(PromotionConstant.NULL_PROMOTION_NAME.getStringValue())
                && this.buy == PromotionConstant.PROMOTION_NULL_BUY.getValue()
                && this.get == PromotionConstant.PROMOTION_NULL_GET.getValue());
    }

    private static LocalDateTime parseLocalDateTime(String startDate) {
        String[] parts = startDate.split("-");
        return LocalDateTime.of(Integer.parseInt(parts[PromotionConstant.YEAR_INDEX.getValue()]),
                Month.of(Integer.parseInt(parts[PromotionConstant.MONTH_INDEX.getValue()])),
                Integer.parseInt(parts[PromotionConstant.DAY_INDEX.getValue()]),
                0,
                0);
    }

    private static LocalDateTime getNullDate() {
        return parseLocalDateTime(PromotionConstant.NULL_PROMOTION_DATE.getStringValue());
    }
}
