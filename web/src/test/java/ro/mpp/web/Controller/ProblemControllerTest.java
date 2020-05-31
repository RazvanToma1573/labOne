package ro.mpp.web.Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ro.mpp.core.Domain.Problem;
import ro.mpp.core.Domain.Student;
import ro.mpp.core.Service.IProblemService;
import ro.mpp.web.controller.ProblemController;
import ro.mpp.web.converter.ProblemConverter;
import ro.mpp.web.dto.ProblemDTO;
import ro.mpp.web.dto.StudentDTO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.anyOf;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class ProblemControllerTest {
    private MockMvc mockMvc;

    private Problem problem1;
    private Problem problem2;
    private ProblemDTO problemDTO1;
    private ProblemDTO problemDTO2;

    @InjectMocks
    private ProblemController problemController;

    @Mock
    private IProblemService problemService;

    @Mock
    private ProblemConverter problemConverter;

    @Before
    public void setup() {
        initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(problemController)
                .build();
        initData();
    }

    @Test
    public void getProblems() throws Exception {
        List<Problem> problems = Arrays.asList(problem1, problem2);
        List<ProblemDTO> problemDTOs = new ArrayList<>(Arrays.asList(problemDTO1, problemDTO2));
        when(problemService.findAll()).thenReturn(problems);
        when(problemConverter.convertModelsToDTOs(problems)).thenReturn(problemDTOs);

        ResultActions resultActions = mockMvc
                .perform(MockMvcRequestBuilders.get("/problems"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].description", anyOf(is("P1"), is("P2"))));

        String result = resultActions.andReturn().getResponse().getContentAsString();

        verify(problemService, times(1)).findAll();
        verify(problemConverter, times(1)).convertModelsToDTOs(problems);
        verifyNoMoreInteractions(problemService, problemConverter);

    }

    @Test
    public void addProblem() throws Exception {
        when(problemService.add(problemDTO1.getId(), problemDTO1.getDescription(), problemDTO1.getDifficulty()))
                .thenReturn(problem1);
        when(problemConverter.convertModelToDTO(problem1)).thenReturn(problemDTO1);
        when(problemConverter.convertDTOtoModel(problemDTO1)).thenReturn(problem1);
        ResultActions resultActions = mockMvc
                .perform(MockMvcRequestBuilders.post("/problems", problemDTO1)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(toJsonString(problemDTO1)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.description", is("P1")));

        verify(problemService, times(1)).add(
                problemDTO1.getId(), problemDTO1.getDescription(), problemDTO1.getDifficulty()
        );
        verify(problemConverter, times(1)).convertModelToDTO(problem1);
        verifyNoMoreInteractions(problemService, problemConverter);
    }

    @Test
    public void updateProblem() throws Exception {
        when(problemService.update(problemDTO1.getId(), problemDTO1.getDescription(), problemDTO1.getDifficulty()))
                .thenReturn(problem1);
        when(problemConverter.convertModelToDTO(problem1)).thenReturn(problemDTO1);
        when(problemConverter.convertDTOtoModel(problemDTO1)).thenReturn(problem1);
        ResultActions resultActions = mockMvc
                .perform(MockMvcRequestBuilders.put("/problems/{id}", problemDTO1.getId(), problemDTO1)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(toJsonString(problemDTO1)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.description", is("P1")));

        verify(problemService, times(1)).update(
                problemDTO1.getId(), problemDTO1.getDescription(), problemDTO1.getDifficulty()
        );
        verify(problemConverter, times(1)).convertModelToDTO(problem1);
        verifyNoMoreInteractions(problemService, problemConverter);
    }

    @Test
    public void removeProblem() throws Exception {
        when(problemService.remove(problemDTO1.getId()))
                .thenReturn(problem1);
        when(problemConverter.convertModelToDTO(problem1)).thenReturn(problemDTO1);
        ResultActions resultActions = mockMvc
                .perform(MockMvcRequestBuilders.delete("/problems/{id}", problem1.getId())
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(toJsonString(problemDTO1)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.description", is("P1")));

        verify(problemService, times(1)).remove(
                problemDTO1.getId()
        );
        verify(problemConverter, times(1)).convertModelToDTO(problem1);
        verifyNoMoreInteractions(problemService, problemConverter);
    }

    private String toJsonString(ProblemDTO problemDTO) {
        try{
            return new ObjectMapper().writeValueAsString(problemDTO);
        }
        catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private void initData() {
        problem1 = Problem.builder().description("P1").difficulty("easy").build();
        problem2 = Problem.builder().description("P2").difficulty("easy").build();
        problem1.setId(9998);
        problem2.setId(9999);
        problemDTO1 = ProblemDTO.builder()
                .description(problem1.getDescription())
                .difficulty(problem1.getDifficulty())
                .build();
        problemDTO1.setId(problem1.getId());
        problemDTO2 = ProblemDTO.builder()
                .description(problem2.getDescription())
                .difficulty(problem2.getDifficulty())
                .build();
        problemDTO2.setId(problem2.getId());
    }
}
