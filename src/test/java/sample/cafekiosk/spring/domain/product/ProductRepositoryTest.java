package sample.cafekiosk.spring.domain.product;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

@ActiveProfiles("test")
@SpringBootTest
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @DisplayName("원하는 상태")
    @Test
    void findAllBySellingStatusIn() {
        Product product1 = Product.builder()
            .productNumber("001")
            .type(ProductType.HANDMADE)
            .sellingStatus(ProductSellingStatus.SELLING)
            .name("아메리카노")
            .price(4000)
            .build();

        Product product2 = Product.builder()
            .productNumber("002")
            .type(ProductType.HANDMADE)
            .sellingStatus(ProductSellingStatus.HOLD)
            .name("카페라떼")
            .price(4500)
            .build();

        Product product3 = Product.builder()
            .productNumber("003")
            .type(ProductType.HANDMADE)
            .sellingStatus(ProductSellingStatus.STOP_SELLING)
            .name("팥빙수")
            .price(7000)
            .build();

        productRepository.saveAll(List.of(product1, product2, product3));

        List<Product> products = productRepository.findAllBySellingStatusIn(List.of(ProductSellingStatus.SELLING, ProductSellingStatus.HOLD));

        assertThat(products).hasSize(2)
            .extracting("productNumber", "name", "sellingStatus")
            .containsExactlyInAnyOrder(
                tuple("001", "아메리카노", ProductSellingStatus.SELLING),
                tuple("002", "카페라떼", ProductSellingStatus.HOLD)
            );

    }
}