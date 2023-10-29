package edu.hw2.task1;

public sealed interface Expr {
    double evaluate();

    record Constant(double value) implements Expr {
        @Override
        public double evaluate() {
            return value;
        }
    }

    record Negate(double value) implements Expr {
        public Negate(Expr constant) {
            this(constant.evaluate());
        }

        @Override
        public double evaluate() {
            return (value * (-1));
        }
    }

    record Exponent(double base, double exponent) implements Expr {
        public Exponent(double base, Expr exponent) {
            this(base, exponent.evaluate());
        }

        public Exponent(Expr base, double exponent) {
            this(base.evaluate(), exponent);
        }

        public Exponent(Expr base, Expr exponent) {
            this(base.evaluate(), exponent.evaluate());
        }

        @Override
        public double evaluate() {
            return Math.pow(base, exponent);
        }
    }

    record Addition(double summand1, double summand2) implements Expr {
        public Addition(Expr summand1, double summand2) {
            this(summand1.evaluate(), summand2);
        }

        public Addition(double summand1, Expr summand2) {
            this(summand1, summand2.evaluate());
        }

        public Addition(Expr summand1, Expr summand2) {
            this(summand1.evaluate(), summand2.evaluate());
        }

        @Override
        public double evaluate() {
            return (summand1 + summand2);
        }
    }

    record Multiplication(double multiplier, double multiplicand) implements Expr {
        public Multiplication(Expr multiplier, double multiplicand) {
            this(multiplier.evaluate(), multiplicand);
        }

        public Multiplication(double multiplier, Expr multiplicand) {
            this(multiplier, multiplicand.evaluate());
        }

        public Multiplication(Expr multiplier, Expr multiplicand) {
            this(multiplier.evaluate(), multiplicand.evaluate());
        }

        @Override
        public double evaluate() {
            return (multiplier * multiplicand);
        }
    }
}
