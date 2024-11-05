package store.domain;

import camp.nextstep.edu.missionutils.DateTimes;
import store.constant.Constants;

import java.time.LocalDateTime;
import java.time.Month;

public class Promotion {
    String name;
    int buy;
    int get;
    LocalDateTime startDate;
    LocalDateTime endDate;

    public Promotion(String name, String buy, String get,
                     String startDate, String endDate) {
        this.name = name;
        this.buy = Integer.parseInt(buy);
        this.get = Integer.parseInt(get);
        this.startDate = parseLocalDateTime(startDate);
        this.endDate = parseLocalDateTime(endDate);
    }

    private LocalDateTime parseLocalDateTime(String startDate) {
        String[] parts = startDate.split("-");
        return LocalDateTime.of(Integer.parseInt(parts[Constants.YEAR_INDEX.getValue()]),
                Month.of(Integer.parseInt(parts[Constants.MONTH_INDEX.getValue()])),
                Integer.parseInt(parts[Constants.DAY_INDEX.getValue()]),
                0,
                0);
    }

    public String getName() {
        return name;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public boolean isPromotionNow() {
        LocalDateTime now = DateTimes.now();
        return now.isAfter(startDate) && now.isBefore(endDate);
    }

    public static Promotion nullPromotion() {
        return new Promotion("null", "0", "0", "0-1-1", "0-1-1");
    }
}
