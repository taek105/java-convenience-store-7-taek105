package store;

import org.junit.jupiter.api.Test;
import store.domain.Warehouse;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WarehouseTest {
    Warehouse warehouse = new Warehouse();

    @Test
    void 결제_가능_테스트() {
        String name = "물";
        int howMuchBought = 2;

        assertEquals(true, warehouse.isSellable(name, howMuchBought));
    }

    @Test
    void 결제_불가능_테스트() {
        String name = "물";

        int howMuchBought = 11;


        assertEquals(false, warehouse.isSellable(name, howMuchBought));
    }

    @Test
    void 구매_테스트() {
        String name = "물";

        int howMuchBought = 2;

        warehouse.selled(name, howMuchBought);

        assertEquals(8, warehouse.getProductStock(name));
    }

    @Test
    void 프로모션_테스트() {

    }
}
