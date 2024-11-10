package store.domain;

import camp.nextstep.edu.missionutils.DateTimes;
import store.constant.Constant;

import java.time.LocalDateTime;
import java.time.Month;

public class Promotion {
    private final String name;
    private final int buy;
    private final int get;
    private final LocalDateTime startDate;
    private final LocalDateTime endDate;

    public Promotion(String[] split) {
        this.name = split[Constant.PROMOTION_NAME_INDEX.getValue()];
        this.buy = Integer.parseInt(split[Constant.PROMOTION_BUY_INDEX.getValue()]);
        this.get = Integer.parseInt(split[Constant.PROMOTION_GET_INDEX.getValue()]);
        this.startDate = parseLocalDateTime(split[Constant.PROMOTION_START_DATE_INDEX.getValue()]);
        this.endDate = parseLocalDateTime(split[Constant.PROMOTION_END_DATE_INDEX.getValue()]);
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
        return new Promotion( "null"
                ,Constant.PROMOTION_NULL_BUY.getValue()
                ,Constant.PROMOTION_NULL_GET.getValue()
                ,getNullDate()
                ,getNullDate());
    }

    public boolean isEmpty() {
        return !( this.name.equals("null")
                && this.buy == Constant.PROMOTION_NULL_BUY.getValue()
                && this.get == Constant.PROMOTION_NULL_GET.getValue());
    }

    private static LocalDateTime parseLocalDateTime(String startDate) {
        String[] parts = startDate.split("-");
        return LocalDateTime.of(Integer.parseInt(parts[Constant.YEAR_INDEX.getValue()]),
                Month.of(Integer.parseInt(parts[Constant.MONTH_INDEX.getValue()])),
                Integer.parseInt(parts[Constant.DAY_INDEX.getValue()]),
                0,
                0);
    }

    private static LocalDateTime getNullDate() {
        return parseLocalDateTime("0-1-1");
    }
}
