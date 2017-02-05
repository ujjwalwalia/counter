package com.apsis;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CounterApplicationTests {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void createCounter() throws Exception {
        MvcResult result = this.mockMvc.perform(post("/countA"))
                .andExpect(status().isOk())
                .andReturn();

        ResponseEntity<Counter> responseEntity = (ResponseEntity<Counter>) result.getAsyncResult();
        assertTrue(responseEntity.getBody().getValue().equals("0"));
    }

    @Test
    public void incrementCounter() throws Exception {
        MvcResult result = this.mockMvc.perform(put("/countA"))
                .andExpect(status().isOk())
                .andReturn();

        ResponseEntity<Counter> responseEntity = (ResponseEntity<Counter>) result.getAsyncResult();
        assertTrue(responseEntity.getBody().getValue().equals("1"));
    }

    @Test
    public void getCounter() throws Exception {
        MvcResult result = this.mockMvc.perform(get("/countA"))
                .andExpect(status().isOk())
                .andReturn();

        ResponseEntity<Counter> responseEntity = (ResponseEntity<Counter>) result.getAsyncResult();
        assertTrue(responseEntity.getBody().getValue().equals("1"));
    }

    @Test
    public void getAllCounters() throws Exception {
        MvcResult result = this.mockMvc.perform(get("/all"))
                .andExpect(status().isOk())
                .andReturn();

        ResponseEntity<List<Counter>> responseEntity = (ResponseEntity<List<Counter>>) result.getAsyncResult();
        List<Counter> counters = responseEntity.getBody();
        assertTrue(counters.size() == 1);
    }

}
