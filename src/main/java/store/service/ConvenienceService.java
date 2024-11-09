package store.service;

import store.constant.FilePath;
import store.domain.Product;
import store.domain.Products;
import store.domain.Promotions;
import store.model.PurchaseDTO;
import store.model.PurchaseResult;
import store.domain.Receipt;
import store.view.OutputView;

import java.io.FileWriter;
import java.io.IOException;

public class ConvenienceService {
    Products products;
    PromotionService promotionService;
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

    public void init() throws IOException {
        this.products = new Products(new Promotions());
        this.receipt = new Receipt();
    }

    public void purchase(String name, int amount) {
        PurchaseDTO purchaseDTO = new PurchaseDTO(
                products.getProduct(name, true),
                products.getProduct(name, false),
                amount
        );
        purchaseProduct(purchaseDTO);
        receipt.add(new PurchaseResult(name, purchaseDTO));
    }

    public void membership(boolean flag) {
        if ( flag ) {
            receipt.membership();
        }
    }

    public void getReceipt() {
        OutputView.printReceipt(receipt);
    }

    public void save() {
        StringBuilder sb = new StringBuilder("name,price,quantity,promotion\n");
        for ( Product product : products.getProductsList() ) {
            sb.append(product.getName()).append(',');
            sb.append(product.getPrice()).append(',');
            sb.append(product.getQuantity()).append(',');
            sb.append(product.getPromotion().getName()).append('\n');
        }
        saveAtProductsMd(sb);
    }

    private void purchaseProduct(PurchaseDTO purchaseDTO) {
        promoteProductPurchase(purchaseDTO);
        justProductPurchase(purchaseDTO);
    }

    private void promoteProductPurchase(PurchaseDTO purchaseDTO) {
        if ( purchaseDTO.getPromoteProduct().isEmpty() ) {
            return;
        }

        if ( purchaseDTO.getPromoteProduct().isPromotionNow() ) {
            beforePromoteProductPurchase(purchaseDTO);
            promoteProductPurchaseLogic(purchaseDTO);
        }
    }

    private void beforePromoteProductPurchase(PurchaseDTO purchaseDTO) {
        promotionService.promotionQuantityCheck(purchaseDTO);
        promotionService.askServeExtraProduct(purchaseDTO);
    }

    private void promoteProductPurchaseLogic(PurchaseDTO purchaseDTO) {
        Product promoteProduct = purchaseDTO.getPromoteProduct();

        while ( purchaseDTO.getPurchaseCount() < promoteProduct.getQuantity()
                && purchaseDTO.getPurchaseCount() < purchaseDTO.getAmount()) {
            purchaseDTO.incrementPurchaseCount();
            purchaseDTO.addPayAmount(promoteProduct.getPrice());

            promotionService.addExtraAmount(purchaseDTO);
        }
        promoteProduct.sold(purchaseDTO.getPurchaseCount());
    }

    private void justProductPurchase(PurchaseDTO purchaseDTO) {
        if ( purchaseDTO.getJustProduct().isEmpty() ) {
            return;
        }
        justProductPurchaseLogic(purchaseDTO);
    }

    private static void justProductPurchaseLogic(PurchaseDTO purchaseDTO) {
        Product justProduct = purchaseDTO.getJustProduct();

        int amount = 0;
        while ( amount < justProduct.getQuantity()
                && purchaseDTO.getPurchaseCount() < purchaseDTO.getAmount()) {
            amount++;
            purchaseDTO.incrementPurchaseCount();
            purchaseDTO.addPayAmount(justProduct.getPrice());
        }
        justProduct.sold(amount);
    }

    private static void saveAtProductsMd(StringBuilder sb) {
        try (FileWriter fileWriter = new FileWriter(
                FilePath.PRODUCT_MD.getValue(), false)) {
            fileWriter.write(sb.toString());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
