//package project.ohlife.controller;
//
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.doNothing;
//import static org.mockito.Mockito.doThrow;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//import project.ohlife.exception.CustomException;
//import project.ohlife.exception.ErrorCode;
//import project.ohlife.repository.dto.UserDto.LoginRequest;
//import project.ohlife.repository.dto.UserDto.SignupRequest;
//import project.ohlife.service.UserService;
//
//@SpringBootTest
//@AutoConfigureMockMvc
//class UserControllerTest {
//
//  @Autowired
//  private MockMvc mockMvc;
//  @Autowired
//  private ObjectMapper objectMapper;
//
//  @MockBean
//  private UserService service;
//
//  @Test
//  void givenSignupRequest_whenSignup_thenSuccess() throws Exception {
//    SignupRequest request = SignupRequest.builder()
//        .email("abcd@naver.com")
//        .password("kfcayo123")
//        .nickname("abcd")
//        .phoneNumber("01012345678")
//        .build();
//
//    doNothing().when(service).signup(request);
//
//    // TODO : mocking
//    mockMvc.perform(post("/users/signup")
//            .contentType(MediaType.APPLICATION_JSON)
//            .content(
//                objectMapper.writeValueAsBytes(
//                    request))
//        ).andDo(print())
//        .andExpect(status().isOk());
//  }
//
//
//  @Test
//  void givenSignupRequest_whenSignup_thenFailDuplicate() throws Exception {
//    SignupRequest request = SignupRequest.builder()
//        .email("abcd@naver.com")
//        .password("kfcayo123")
//        .nickname("abcd")
//        .phoneNumber("01012345678")
//        .build();
//
//   doThrow(new CustomException(ErrorCode.EMAIL_DUPLICATE)).when(service).signup(request);
//
//    mockMvc.perform(post("/users/signup")
//            .contentType(MediaType.APPLICATION_JSON)
//            .content(
//                objectMapper.writeValueAsString(
//                  request))
//        ).andDo(print())
//        .andExpect(status().isConflict());
//  }
//
//  @Test
//  void givenLoginRequest_whenLogin_thenSuccess() throws Exception {
//    LoginRequest request = LoginRequest.builder()
//        .email("abcd@naver.com")
//        .password("1234")
//        .build();
//
//    doNothing().when(service).login(request);
//
//    mockMvc.perform(post("/users/login")
//            .contentType(MediaType.APPLICATION_JSON)
//            .content(
//                objectMapper.writeValueAsBytes(
//                    request))
//        ).andDo(print())
//        .andExpect(status().isOk());
//  }
//
//  @Test
//  void givenLoginRequest_whenLogin_thenFailUserNotFound() throws Exception {
//    LoginRequest request = LoginRequest.builder()
//        .email("abcd@naver.com")
//        .password("1234")
//        .build();
//
//    doThrow(new CustomException(ErrorCode.USER_NOT_FOUND)).when(service).login(any());
//
//    mockMvc.perform(post("/users/login")
//            .contentType(MediaType.APPLICATION_JSON)
//            .content(
//                objectMapper.writeValueAsBytes(
//                    request))
//        ).andDo(print())
//        .andExpect(status().isNotFound());
//  }
//
//}
