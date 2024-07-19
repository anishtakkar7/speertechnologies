package com.speertechnologies.NotesApplication;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
//import org.junit.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.speertechnologies.controller.AuthController;
import com.speertechnologies.controller.NotesController;
import com.speertechnologies.jwt.security.JwtUtil;
import com.speertechnologies.response.NotesResponse;
import com.speertechnologies.service.UserNotesService;
import com.speertechnologies.service.UserService;
import static org.hamcrest.Matchers.containsInAnyOrder;


class UnitTestController {

    @Autowired
    private MockMvc mockMvc; // MockMvc object to simulate HTTP requests

    @Mock
    private UserNotesService userNotesService; // Mocked service layer
    
    @Mock
	private UserService userService;
    
    @Mock
    private AuthenticationManager authenticationManager;
    
    @Mock
    private JwtUtil jwtUtil;
    
    @InjectMocks
    private NotesController myController; // Controller to be tested, with mocked dependencies injected
    
    @InjectMocks
    private AuthController authController;
    
    
    
    @BeforeEach
    void setUp() {
        // Initialize Mockito annotations and set up MockMvc with the controller
        MockitoAnnotations.openMocks(this);
        
        mockMvc = MockMvcBuilders.standaloneSetup(myController, authController).build();
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("User"));
        User user = new User("1", "dummy", false, false, false, false, authorities);
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(user,null));
        //when(userNotesService.getNotesByUserId(1)).thenReturn(NotesResponse.builder().noteNames(noteNamesList).build());
//        try {
//        	MockHttpServletResponse response =  mockMvc.perform(post("/api/auth/login")
//			      .contentType(MediaType.APPLICATION_JSON)
//			      .content("{\r\n"
//			      		+ "  \"username\" :  \"5\",\r\n"
//			      		+ "  \"password\" : \"honey1234\"\r\n"
//			      		+ "}"))
//			     //.andExpect(status().isNonAuthoritativeInformation())
//			     .andReturn().getResponse();
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
    }

    @Test
    //@WithMockUser(username = "user", roles = {"USER"})
    void testGetAllNotesForAuthUser() throws Exception {
        // Arrange: Mock the service call to return a specific entity
    	List<String> noteNamesList = List.of("abcd","saving a new note.","hello world note");
       // when(userNotesService.getNotesByUserId(1)).thenReturn(NotesResponse.builder().noteNames(noteNamesList).build());
       
        // Act and Assert: Perform GET request and verify the response  
        mockMvc.perform(get("/api/notes"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.noteNames").value(containsInAnyOrder("abcd", "saving a new note.", "hello world note")));
    }
    
    //@Test
    void testGetNotesByIdForAuthUser() throws Exception {
        // Arrange: Mock the service call to return a specific entity
        when(userNotesService.getNotesByIdAndUserId(15, 5)).thenReturn(NotesResponse.builder().noteName("honey note 3").build());
       
        // Act and Assert: Perform GET request and verify the response  
        mockMvc.perform(get("/api/notes/"+15))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.noteName").value("honey note 3"));
               //.andExpect(jsonPath("$.name").value("Test Entity"));
    }

    //@Test
//    void testPostEndpoint() throws Exception {
//        // Arrange: Mock the service call to return a saved entity
//        MyEntity entity = new MyEntity(null, "New Entity");
//        MyEntity savedEntity = new MyEntity(1L, "New Entity");
//
//        when(myService.saveEntity(any(MyEntity.class))).thenReturn(savedEntity);
//
//        // Act and Assert: Perform POST request and verify the response
//        mockMvc.perform(post("/api/entities")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content("{\"name\": \"New Entity\"}"))
//               .andExpect(status().isCreated())
//               .andExpect(jsonPath("$.id").value(1))
//               .andExpect(jsonPath("$.name").value("New Entity"));
//    }

//    @Test
//    void testPutEndpoint() throws Exception {
//        // Arrange: Mock the service call to return an updated entity
//        MyEntity updatedEntity = new MyEntity(1L, "Updated Entity");
//
//        when(myService.updateEntity(eq(1L), any(MyEntity.class))).thenReturn(updatedEntity);
//
//        // Act and Assert: Perform PUT request and verify the response
//        mockMvc.perform(put("/api/entities/1")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content("{\"name\": \"Updated Entity\"}"))
//               .andExpect(status().isOk())
//               .andExpect(jsonPath("$.id").value(1))
//               .andExpect(jsonPath("$.name").value("Updated Entity"));
//    }
//
//    @Test
//    void testDeleteEndpoint() throws Exception {
//        // Act and Assert: Perform DELETE request and verify the response
//        mockMvc.perform(delete("/api/entities/1"))
//               .andExpect(status().isNoContent());
//    }
}