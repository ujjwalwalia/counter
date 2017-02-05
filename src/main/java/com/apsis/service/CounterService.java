package com.apsis.service;

import com.apsis.Counter;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Counter service is used for performing various different operations/functions for the counters.
 */
@Service
public class CounterService {

    private ConcurrentMap<String, AtomicInteger> counterMap;

    public CounterService() {
        counterMap = new ConcurrentHashMap<>();
    }

    public Optional<Counter> createCount(String counter) {
        counterMap.putIfAbsent(counter, new AtomicInteger(0));
        return getCount(counter);
    }

    public Optional<Counter> incrementCount(String counter) {
        counterMap.get(counter).incrementAndGet();
        return getCount(counter);
    }

    public List<Counter> getCounterMap() {
        List<Counter> counters = new ArrayList<>();

        counterMap.entrySet().stream().forEach(mapEntry -> {
            Counter counter = new Counter();
            counter.setCounter(mapEntry.getKey());
            counter.setValue(mapEntry.getValue().toString());
            counters.add(counter);
        });
        return counters;
    }

    public Optional<Counter> getCount(String counter) {
        if (counterMap.get(counter) == null) {
            return Optional.empty();
        }
        Counter counterObj = new Counter();
        counterObj.setCounter(counter);
        counterObj.setValue(counterMap.get(counter).toString());
        return Optional.of(counterObj);
    }

}
