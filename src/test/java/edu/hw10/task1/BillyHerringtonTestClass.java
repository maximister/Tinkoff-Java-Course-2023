package edu.hw10.task1;

import edu.hw10.task1.annotations.Max;
import edu.hw10.task1.annotations.Min;
import edu.hw10.task1.annotations.NotNull;
import lombok.Getter;

@Getter
public class BillyHerringtonTestClass {
    private final String name;
    private final Boolean visitedGymToday;
    private final double byceps;
    private final Integer price;

    public BillyHerringtonTestClass(
        @NotNull String name,
        @NotNull Boolean visitedGymToday,
        double byceps,
        @NotNull @Min(300) Integer price
    ) {
        this.name = name;
        this.visitedGymToday = visitedGymToday;
        this.byceps = byceps;
        this.price = price;
    }

    public static BillyHerringtonTestClass create(@NotNull String name, Boolean visitedGymToday, @Max(80) double byceps) {
        return new BillyHerringtonTestClass(name, visitedGymToday, byceps, 300);
    }
}
