package edu.hw3.task6;


interface StockMarket {
    void add(Stock stock);

    void remove(Stock stock);

    Stock getMostValuableStock();

    record Stock(String name, String ticker, double price) implements Comparable<Stock> {
        @Override
        public int compareTo(StockMarket.Stock o) {
            return Double.compare(o.price, this.price);
        }
    }
}
