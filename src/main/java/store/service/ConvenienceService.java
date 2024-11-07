package store.service;

import store.constant.ErrorMessage;
import store.domain.Product;
import store.domain.Products;
import store.domain.Promotions;
import store.model.PurchaseDTO;
import store.model.PurchaseResult;
import store.model.Receipt;
import store.view.OutputView;

import java.io.IOException;

public class ConvenienceService {
    PromotionService promotionService;
    Products products;
    Receipt receipt;

    public ConvenienceService() throws IOException {
        this.products = new Products(new Promotions());
        this.promotionService = new PromotionService(products);
        this.receipt = new Receipt();
    }

    public void open() {
        OutputView.printWelcome();
        OutputView.printProducts(products);
    }

    public PurchaseDTO purchase(String name, int amount) {
        Product promoteProduct = products.getProduct(name, true);
        Product justProduct = products.getProduct(name, false);
        validate(amount, promoteProduct, justProduct);

        PurchaseDTO purchaseDTO = new PurchaseDTO(amount);

        promoteProductPurchase(promoteProduct, purchaseDTO);
        justProductPurchase(justProduct, purchaseDTO);

        receipt.add(new PurchaseResult(name, purchaseDTO.getAmount(), purchaseDTO.getExtraAmount(), purchaseDTO.getPromotedPrice()));
        return purchaseDTO;
    }

    private void promoteProductPurchase(Product promoteProduct, PurchaseDTO purchaseDTO) {
        if ( promoteProduct.isEmpty() ) {
            return;
        }
        promotionService.promotionQuantityCheck(promoteProduct, purchaseDTO);
        promotionService.askServeExtraProduct(promoteProduct, purchaseDTO);

        while ( purchaseDTO.getPurchaseCount() < promoteProduct.getQuantity()
                && purchaseDTO.getPurchaseCount() < purchaseDTO.getAmount()) {
            purchaseDTO.incrementPurchaseCount();
            purchaseDTO.addPayAmount(promoteProduct.getPrice());

            promotionService.promotionCheck(promoteProduct, purchaseDTO);
        }

        promoteProduct.sold(purchaseDTO.getPurchaseCount());
    }

    private void justProductPurchase(Product justProduct, PurchaseDTO purchaseDTO) {
        if ( justProduct.isEmpty() ) {
            return;
        }
        int amount = 0;
        while ( amount < justProduct.getQuantity()
                && purchaseDTO.getPurchaseCount() < purchaseDTO.getAmount()) {
            amount++;
            purchaseDTO.incrementPurchaseCount();
            purchaseDTO.addPayAmount(justProduct.getPrice());
        }

        justProduct.sold(amount);
    }

    public void membership(boolean flag) {
        if ( flag ) {
            receipt.membership();
        }
    }

    public void getReceipt() {
        OutputView.printReceipt(receipt);


    }

    private static void validate(int amount, Product promoteProduct, Product justProduct) {
        if ( amount > promoteProduct.getQuantity() + justProduct.getQuantity() ) {
            throw new IllegalArgumentException(ErrorMessage.EXCEED_QUANTITY.getMessages());
        }
    }
}
