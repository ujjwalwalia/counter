package com.apsis;

import com.apsis.exception.ResourceNotFoundException;
import com.apsis.service.CounterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

/**
 * The main entry point REST controller for the application.
 */
@RestController
@RequestMapping("/")
public class CounterController {

    private static final Logger LOG = LoggerFactory.getLogger(CounterController.class);

    @Autowired
    private CounterService counterService;

    @RequestMapping(value = "{counter}", method = RequestMethod.POST)
    @ResponseBody
    DeferredResult<ResponseEntity<Counter>> createCounter(@PathVariable final String counter, final HttpServletRequest request) {
        LOG.info("Request received from: " + request.getRequestURL());
        Optional<Counter> counterView = counterService.createCount(counter);
        DeferredResult<ResponseEntity<Counter>> result = new DeferredResult<>();
        result.setResult(new ResponseEntity<Counter>(counterView.get(), HttpStatus.CREATED));
        LOG.info("Response: " + result.getResult());
        return result;
    }

    @RequestMapping(value = "{counter}", method = RequestMethod.GET)
    @ResponseBody
    DeferredResult<ResponseEntity<Counter>> getCounter(@PathVariable final String counter, final HttpServletRequest request) {
        LOG.info("Request received from: " + request.getRequestURL());
        Optional<Counter> counterView = counterService.getCount(counter);
        if (!counterView.isPresent()) {
            throw new ResourceNotFoundException("No such counter: " + counter);
        }
        DeferredResult<ResponseEntity<Counter>> result = new DeferredResult<>();
        result.setResult(new ResponseEntity<Counter>(counterView.get(), HttpStatus.OK));
        LOG.info("Response: " + result.getResult());
        return result;
    }

    @RequestMapping(value = "{counter}", method = RequestMethod.PUT)
    @ResponseBody
    DeferredResult<ResponseEntity<Counter>> incrementCounter(@PathVariable final String counter, final HttpServletRequest request) {
        LOG.info("Request received from: " + request.getRequestURL());
        Optional<Counter> counterView = counterService.getCount(counter);
        if (!counterView.isPresent()) {
            throw new ResourceNotFoundException("No such counter: " + counter);
        }
        counterView = counterService.incrementCount(counter);
        DeferredResult<ResponseEntity<Counter>> result = new DeferredResult<>();
        result.setResult(new ResponseEntity<Counter>(counterView.get(), HttpStatus.OK));
        LOG.info("Response: " + result.getResult());
        return result;
    }

    @RequestMapping(value = "all", method = RequestMethod.GET)
    @ResponseBody
    DeferredResult<ResponseEntity<List<Counter>>> getAllCounters(final HttpServletRequest request) {
        LOG.info("Request received from: " + request.getRequestURL());
        List<Counter> counters = counterService.getCounterMap();
        DeferredResult<ResponseEntity<List<Counter>>> result = new DeferredResult<>();
        result.setResult(new ResponseEntity<List<Counter>>(counters, HttpStatus.OK));
        LOG.info("Response: " + result.getResult());
        return result;
    }
}
