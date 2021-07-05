package pl.rest.application.controller;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import pl.rest.application.dto.ApplicationDto;
import pl.rest.application.entity.Status;
import pl.rest.application.mapper.JsonObjectMapperUtil;
import pl.rest.application.service.ApplicationService;

import java.util.Optional;
import java.util.stream.Stream;

@WebMvcTest(ApplicationController.class)
class ApplicationControllerTest {

    private static final String BASE_API_PATH = "/api/apps";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ApplicationService applicationServiceMock;

    @ParameterizedTest
    @MethodSource("applicationPostParameters")
    void shouldValidatePostPersistParameters(String name, String content, HttpStatus expectedHttpStatus) throws Exception {

        //given
        ApplicationDto applicationDto = new ApplicationDto();
        applicationDto.setName(name);
        applicationDto.setContent(content);
        //when
//        Mockito.when(applicationServiceMock.addApplication(Mockito.isA(ApplicationDto.class))).thenReturn(Optional.of(applicationDto));
        Mockito.doNothing().when(applicationServiceMock).addApplication(applicationDto);
        //then
        mockMvc.perform(MockMvcRequestBuilders.post(BASE_API_PATH + "/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonObjectMapperUtil.asJson(applicationDto)))
                .andExpect(MockMvcResultMatchers.status().is(expectedHttpStatus.value())
        );

    }

    static Stream<Arguments> applicationPostParameters() {
        return Stream.of(
                Arguments.of("test", "test", HttpStatus.CREATED),
                Arguments.of("", "test", HttpStatus.BAD_REQUEST)
        );
    }
//
//    @ParameterizedTest
//    @MethodSource("applicationPostParameters")
//    void shouldValidatePutRejectApplicationParameters(String name, String content, HttpStatus expectedHttpStatus) throws Exception {
//
//        //given
//        ApplicationDto applicationDto = new ApplicationDto();
//        applicationDto.setName(name);
//        applicationDto.setContent(content);
//        //when
////        Mockito.when(applicationServiceMock.addApplication(Mockito.isA(ApplicationDto.class))).thenReturn(Optional.of(applicationDto));
//        Mockito.doNothing().when(applicationServiceMock).addApplication(applicationDto);
//        //then
//        mockMvc.perform(MockMvcRequestBuilders.post(BASE_API_PATH + "/add")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(JsonObjectMapperUtil.asJson(applicationDto)))
//                .andExpect(MockMvcResultMatchers.status().is(expectedHttpStatus.value())
//                );
//
//    }
//
//    static Stream<Arguments> applicationPutRejectApplicationParameters() {
//        return Stream.of(
//                Arguments.of("test", "test", HttpStatus.CREATED),
//                Arguments.of("", "test", HttpStatus.BAD_REQUEST)
//        );
//    }
//
//    @ParameterizedTest
//    @MethodSource("applicationPutChangeStatusApplicationParameters")
//    void shouldValidateChangeApplicationStatusParameters(Status status, HttpStatus expectedHttpStatus) throws Exception {
//
//        //given
//        ApplicationDto applicationDto = new ApplicationDto();
//        applicationDto.setName("test");
//        applicationDto.setContent("test");
//        applicationDto.setStatus(status);
//        //when
//        Mockito.when(applicationServiceMock.changeStatus(Mockito.isA(Long.class))).thenReturn(applicationDto.getStatus());
////        Mockito.doNothing().when(applicationServiceMock).changeStatus(applicationDto);
//        //then
//        mockMvc.perform(MockMvcRequestBuilders.post(BASE_API_PATH + "/add")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(JsonObjectMapperUtil.asJson(applicationDto)))
//                .andExpect(MockMvcResultMatchers.status().is(expectedHttpStatus.value())
//                );
//
//    }
//
//    static Stream<Arguments> applicationPutChangeStatusApplicationParameters() {
//        return Stream.of(
//                Arguments.of(Status.CREATED, HttpStatus.OK),
//                Arguments.of(Status.PUBLISHED, HttpStatus.BAD_REQUEST)
//        );
//    }
}