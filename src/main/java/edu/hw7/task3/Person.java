package edu.hw7.task3;

import lombok.Builder;

@Builder
public record Person(int id, String name, String address, String phoneNumber) {}
