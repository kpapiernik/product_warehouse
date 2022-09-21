package com.krzysztofpapiernik.products.dto;

import com.krzysztofpapiernik.products.exception.ValidationException;
import com.krzysztofpapiernik.products.model.Customer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CreateCustomerDtoTest {

    @Test
    void itShouldCreateValidDto() {
        //Given
        String firstName = "John";
        String lastName = "Doe";
        String email = "test@example.com";
        String dateOfBirth = "12/02/1991";
        //When
        CreateCustomerDto result = new CreateCustomerDto(firstName, lastName, email, dateOfBirth);
        //Then
        assertThat(result.firstName()).isEqualTo(firstName);
        assertThat(result.lastName()).isEqualTo(lastName);
        assertThat(result.email()).isEqualTo(email);
        assertThat(result.dateOfBirth()).isEqualTo(dateOfBirth);
    }

    @ParameterizedTest
    @MethodSource("testValuesForNames")
    void itShouldNotCreateDtoWhenFirstNameDoesNotMeetRequirements(String value) {
        assertThatThrownBy(() -> new CreateCustomerDto(value, "test", "email@test.com", "12/03/1991"))
                .isInstanceOf(ValidationException.class);
    }

    @ParameterizedTest
    @MethodSource("testValuesForNames")
    void itShouldNotCreateDtoWhenLastNameDoesNotMeetRequirements(String value) {
        assertThatThrownBy(() -> new CreateCustomerDto("test", value, "email@test.com", "12/03/1991"))
                .isInstanceOf(ValidationException.class);
    }

    static Stream<String> testValuesForNames(){
        return Stream.of(null, "", "<script></script>", "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
    }

    @Test
    void itShouldNotCreateDtoWhenEmailIsNull() {
        //Given
        String email = null;
        //When
        //Then
        assertThatThrownBy(() -> new CreateCustomerDto("John", "Doe", email, "01/01/1990"))
                .isInstanceOf(ValidationException.class)
                .hasFieldOrPropertyWithValue("errors", Map.of("email", "is null"));
    }

    @Test
    void itShouldNotCreateDtoWhenEmailDoesNotMatchPattern() {
        //Given
        String email = "test@email";
        //When
        //Then
        assertThatThrownBy(() -> new CreateCustomerDto("John", "Doe", email, "01/01/1990"))
                .isInstanceOf(ValidationException.class)
                .hasFieldOrPropertyWithValue("errors", Map.of("email", "does not match required pattern"));
    }

    @Test
    void itShouldNotCreateDtoWhenEmailIsEmpty() {
        //Given
        String email = "";
        //When
        //Then
        assertThatThrownBy(() -> new CreateCustomerDto("John", "Doe", email, "01/01/1990"))
                .isInstanceOf(ValidationException.class)
                .hasFieldOrPropertyWithValue("errors", Map.of("email", "does not match required pattern"));
    }

    @Test
    void itShouldNotCreateDtoWhenDateOfBirthIsNull() {
        assertThatThrownBy(() -> new CreateCustomerDto("John", "Doe", "test@example.com", null))
                .isInstanceOf(ValidationException.class)
                .hasFieldOrPropertyWithValue("errors", Map.of("dateOfBirth", "is null"));
    }

    @Test
    void itShouldNotCreateDtoWhenDateOfBirthDoesNotMatchPattern() {
        assertThatThrownBy(() -> new CreateCustomerDto("John", "Doe", "test@example.com", "2000/11/30"))
                .isInstanceOf(ValidationException.class)
                .hasFieldOrPropertyWithValue("errors", Map.of("dateOfBirth", "does not match required pattern (d/MM/yyyy)"));
    }

    @Test
    void itShouldNotCreateDtoWhenDateOfBirthIsNotPastDate() {
        LocalDate dateFromFuture = LocalDate.now().plusYears(1);
        String dateOfBirth = dateFromFuture.format(DateTimeFormatter.ofPattern("d/MM/yyyy"));
        assertThatThrownBy(() -> new CreateCustomerDto("John", "Doe", "test@example.com", dateOfBirth))
                .isInstanceOf(ValidationException.class)
                .hasFieldOrPropertyWithValue("errors", Map.of("dateOfBirth", "must be date from the past"));
    }

    @Test
    void itShouldReturnCustomerFromDto() {
        //Given
        CreateCustomerDto dto = new CreateCustomerDto("John", "Doe", "test@example.com", "01/01/1980");
        Customer expected = Customer
                .builder()
                .firstName("John")
                .lastName("Doe")
                .email("test@example.com")
                .dateOfBirth(LocalDate.parse("01/01/1980", DateTimeFormatter.ofPattern("d/MM/yyyy")))
                .build();
        //When
        Customer result = dto.toCustomer();
        //Then
        assertThat(result)
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(expected);
    }
}