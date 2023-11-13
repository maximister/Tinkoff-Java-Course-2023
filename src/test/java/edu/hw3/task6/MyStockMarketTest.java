package edu.hw3.task6;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.ArrayList;
import java.util.stream.Stream;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class MyStockMarketTest {
    @ParameterizedTest
    @MethodSource("stockMarketTestArgs")
    @DisplayName("Проверка работы функционала класса MyStockMarket")
    public void stockMarketTest(MyStockMarket market, ArrayList<StockMarket.Stock> answer) {

        assertThat(market.getStoksList()).isEqualTo(answer);
        assertThat(market.getMostValuableStock()).isEqualTo(answer.get(0));

        market.remove(market.getMostValuableStock());
        assertThat(market.getMostValuableStock()).isEqualTo(answer.get(1));
    }

    private static Stream<Arguments> stockMarketTestArgs() {
        StockMarket.Stock s1 = new StockMarket.Stock("Tinkoff Group", "TCSG", 3480.5);
        StockMarket.Stock s2 = new StockMarket.Stock("Лукоил", "LKOH", 7462.5);
        StockMarket.Stock s3 = new StockMarket.Stock("НМТП", "NMTP", 12.975);
        StockMarket.Stock s4 = new StockMarket.Stock("Сургутнефтегаз ап", "SNGSP", 53.125);
        StockMarket.Stock s5 = new StockMarket.Stock("Банк Санкт-Петербург", "BSPB", 261.3);
        MyStockMarket msm = new MyStockMarket();
        msm.add(s1);
        msm.add(s2);
        msm.add(s3);
        msm.add(s4);
        msm.add(s5);

        ArrayList<StockMarket.Stock> answer1 = new ArrayList<>();
        answer1.add(s2);
        answer1.add(s1);
        answer1.add(s5);
        answer1.add(s4);
        answer1.add(s3);

        return Stream.of(
            Arguments.of(msm, answer1)
        );
    }

    @Test
    @DisplayName("VTB test")
    public void vtbTest() {
        MyStockMarket market = new MyStockMarket();
        StockMarket.Stock vtbStock = new StockMarket.Stock("VTB bank", "VTBR", 0.02615);

        assertThrows(
            IllegalArgumentException.class, () -> market.add(vtbStock)
        );
    }
}
