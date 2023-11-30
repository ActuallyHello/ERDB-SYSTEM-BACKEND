package com.ustu.erdbsystem.external;

import java.util.List;

public interface TestDataLoader {
    List<List<String>> loadData(String modelTitle, List<String> attributes, Integer testDataAmount);
}
