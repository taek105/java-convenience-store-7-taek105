package store.service;

import org.junit.jupiter.api.Test;
import store.HtTest;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

public class ConvenienceServiceTest extends HtTest {
    ConvenienceService convenienceService = new ConvenienceService();

    public ConvenienceServiceTest() throws IOException {}

    @Test
    void 투쁠투_4개구매_테스트() {
        String productName = "효택도시락";
        int amount = 4;

        convenienceService.purchase(productName, amount);

        convenienceService.getReceipt();
        assertThat(output()).contains(
                "효택도시락     \t   4\t    20,000",
                "내실돈       \t\t     10,000"
        );
    }

    @Test
    void 투쁠원_3개구매_테스트() {
        String productName = "탄산수";
        int amount = 3;

        convenienceService.purchase(productName, amount);

        convenienceService.getReceipt();
        assertThat(output()).contains(
                "탄산수       \t   3\t     3,600",
                "내실돈       \t\t      2,400"
        );
    }
}
