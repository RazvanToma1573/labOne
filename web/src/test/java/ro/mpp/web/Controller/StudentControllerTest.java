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
import ro.mpp.core.Domain.Student;
import ro.mpp.core.Service.IStudentService;
import ro.mpp.web.controller.StudentController;
import ro.mpp.web.converter.StudentConverter;
import ro.mpp.web.dto.StudentDTO;

import java.util.*;

import static org.hamcrest.CoreMatchers.anyOf;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class StudentControllerTest {
    private MockMvc mockMvc;

    private Student student1;
    private Student student2;
    private StudentDTO studentDTO1;
    private StudentDTO studentDTO2;

    @InjectMocks
    private StudentController studentController;

    @Mock
    private IStudentService studentService;

    @Mock
    private StudentConverter studentConverter;

    @Before
    public void setup() {
        initMocks(this);
        this.mockMvc = MockMvcBuilders
                .standaloneSetup(studentController)
                .build();
        initData();
    }

    @Test
    public void getStudents() throws Exception {
        List<Student> students = Arrays.asList(student1, student2);
        List<StudentDTO> studentDTOs = new ArrayList<>(Arrays.asList(studentDTO1, studentDTO2));
        when(studentService.findAll()).thenReturn(students);
        when(studentConverter.convertModelsToDTOs(students)).thenReturn(studentDTOs);

        ResultActions resultActions = mockMvc
                .perform(MockMvcRequestBuilders.get("/students"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].firstName", anyOf(is("Student1"), is("Student2"))));

        String result = resultActions.andReturn().getResponse().getContentAsString();

        verify(studentService, times(1)).findAll();
        verify(studentConverter, times(1)).convertModelsToDTOs(students);
        verifyNoMoreInteractions(studentService, studentConverter);

    }

    @Test
    public void addStudent() throws Exception {
        when(studentService.add(studentDTO1.getId(), studentDTO1.getFirstName(), studentDTO1.getLastName()))
                .thenReturn(student1);
        when(studentConverter.convertModelToDTO(student1)).thenReturn(studentDTO1);
        when(studentConverter.convertDTOtoModel(studentDTO1)).thenReturn(student1);
        ResultActions resultActions = mockMvc
                .perform(MockMvcRequestBuilders.post("/students", studentDTO1)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(toJsonString(studentDTO1)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", is("Student1")));

        verify(studentService, times(1)).add(
                studentDTO1.getId(), studentDTO1.getFirstName(), studentDTO1.getLastName()
        );
        verify(studentConverter, times(1)).convertModelToDTO(student1);
        verifyNoMoreInteractions(studentService, studentConverter);
    }

    @Test
    public void updateStudent() throws Exception {
        when(studentService.update(studentDTO1.getId(), studentDTO1.getFirstName(), studentDTO1.getLastName()))
                .thenReturn(student1);
        when(studentConverter.convertModelToDTO(student1)).thenReturn(studentDTO1);
        when(studentConverter.convertDTOtoModel(studentDTO1)).thenReturn(student1);
        ResultActions resultActions = mockMvc
                .perform(MockMvcRequestBuilders.put("/students/{id}", studentDTO1.getId(), studentDTO1)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(toJsonString(studentDTO1)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.firstName", is("Student1")));

        verify(studentService, times(1)).update(
                student1.getId(), studentDTO1.getFirstName(), studentDTO1.getLastName()
        );
        verify(studentConverter, times(1)).convertModelToDTO(student1);
        verifyNoMoreInteractions(studentService, studentConverter);
    }

    @Test
    public void removeStudent() throws Exception {
        when(studentService.remove(studentDTO1.getId()))
                .thenReturn(student1);
        when(studentConverter.convertModelToDTO(student1)).thenReturn(studentDTO1);
        ResultActions resultActions = mockMvc
                .perform(MockMvcRequestBuilders.delete("/students/{id}", student1.getId())
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(toJsonString(studentDTO1)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.firstName", is("Student1")));

        verify(studentService, times(1)).remove(
                studentDTO1.getId()
        );
        verify(studentConverter, times(1)).convertModelToDTO(student1);
        verifyNoMoreInteractions(studentService, studentConverter);
    }

    private String toJsonString(StudentDTO studentDTO) {
        try{
            return new ObjectMapper().writeValueAsString(studentDTO);
        }
        catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private void initData() {
        student1 = Student.builder().firstName("Student1").lastName("Student1").build();
        student1.setId(1000);
        student2 = Student.builder().firstName("Student2").lastName("Student2").build();
        student2.setId(2000);

        studentDTO1 = StudentDTO.builder()
                .firstName(student1.getFirstName())
                .lastName(student1.getLastName())
                .build();
        studentDTO1.setId(student1.getId());

        studentDTO2 = StudentDTO.builder()
                .firstName(student2.getFirstName())
                .lastName(student2.getLastName())
                .build();
        studentDTO2.setId(student2.getId());
    }
}
