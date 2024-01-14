package com.api.repositories;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
public class ClientRepoTest {
	@MockBean
    private ClientRepo clientRepo;
  

    @Test
    public void testExistsByClientEmail() {
        // Set up the mock behavior
        when(clientRepo.existsByClientEmail("ram@client.com")).thenReturn(true);

        // Call the repository method
        boolean exists = clientRepo.existsByClientEmail("ram@client.com");

        // Verify the result
        assertTrue(exists);
        verify(clientRepo, times(1)).existsByClientEmail("ram@client.com");
    }

}
