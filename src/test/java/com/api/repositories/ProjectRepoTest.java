package com.api.repositories;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.api.entities.Project;


@SpringBootTest
public class ProjectRepoTest {
	
	@MockBean
    private ProjectRepo projectRepo;


    @Test
    public void testFindByClientIsNull() {
        // Create a list of projects with mock data
        List<Project> projectList = new ArrayList<>();
        projectList.add(new Project(1, "Project1", null, null, null, null, null));
        projectList.add(new Project(2, "Project2", null, null, null, null, null));

        // Mock the repository method to return the list of projects
        when(projectRepo.findByClientIsNull()).thenReturn(projectList);

        // Call the repository method
        List<Project> result = projectRepo.findByClientIsNull();

        // Verify the result
        assertEquals(2, result.size());
        assertEquals(1, result.get(0).getProject_id());
        assertNull(result.get(0).getClient());
        assertEquals(2, result.get(1).getProject_id());
        assertNull(result.get(1).getClient());
        verify(projectRepo, times(1)).findByClientIsNull();
    }

}
