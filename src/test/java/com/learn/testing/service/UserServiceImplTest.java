package com.learn.testing.service;

import com.learn.testing.model.User;
import com.learn.testing.repo.UserRepo;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("userServiceImpl Testing Methods")
@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    //Helps in instantiating actual impl class and auto-inject the mocks
    @InjectMocks
    UserServiceImpl userServiceImpl;

    @Mock
    UserRepo userDatabase;

    @Mock
    EmailVerificationServiceImpl emailService;

    // This state is sustained b/w test method executions. Since, only one TestClass instance is created as above.
    private String createdUserId;

    // This method is not static because we are using TestInstance.Lifecycle.PER_CLASS. Else, this would be static
    @BeforeAll
    void setup() {
        System.out.println("Executing @BeforeAll setup()");
    }

    @AfterAll
    void cleanup() {
        System.out.println("Executing @AfterAll cleanup()");
        createdUserId = null;
    }

    @Order(1)
    @DisplayName("CreateUser")
    @Test
    void testCreateUser_1UserCreated_ShouldBeSavedToDB() {
        System.out.println("Executing @Test CreateUser");

        // Arrange/Given
        User user1 = new User("Anirudh Patel", 35);
        int expectedUserCount = 1;
        when(userDatabase.saveUser(Mockito.anyString(), Mockito.any(User.class))).thenReturn(true);
        when(userDatabase.getAllUsersCount()).thenReturn(1);

        // Act/When
        createdUserId = userServiceImpl.createUser(user1);

        // Assert/Then
        int actualUsersCount = userServiceImpl.getAllUsersCount();
        System.out.println("ActualUsersCount : " + actualUsersCount);

        assertNotNull(createdUserId, "createdUserId should not be null");
        assertEquals(expectedUserCount, actualUsersCount, "Mismatch between expected and actual users count");

        // times(1) can be omitted since it is the default value in verification mode
        verify(userDatabase, times(1)).saveUser(Mockito.anyString(), Mockito.any(User.class));
    }

    @Order(2)
    @DisplayName("CreateUserFails")
    @Test
    void testCreateUser_NoUserCreatedRepoReturnsFalse_ShouldThrowException() {
        System.out.println("Executing @Test CreateUserFails");

        // Arrange/Given
        User user1 = new User("Anirudh Patel", 35);
        int expectedUserCount = 0;
        // If you mock thenTrow(RuntimeException), then add try/catch in createUser method
        when(userDatabase.saveUser(Mockito.anyString(), Mockito.any(User.class))).thenReturn(false);
        when(userDatabase.getAllUsersCount()).thenReturn(0);

        // Act/When
        assertThrows(UserServiceException.class, () -> {
            createdUserId = userServiceImpl.createUser(user1);
        }, "Should have thrown UserServiceException instead");

        // Assert/Then
        int actualUsersCount = userServiceImpl.getAllUsersCount();
        System.out.println("ActualUsersCount : " + actualUsersCount);

        assertEquals(expectedUserCount, actualUsersCount, "Mismatch between expected and actual users count");

        // times(1) can be omitted since it is the default value in verification mode
        verify(userDatabase, times(1)).saveUser(Mockito.anyString(), Mockito.any(User.class));
    }

    @Order(3)
    @DisplayName("CreateUserHandleEmailNotificationException")
    @Test
    void testCreateUser_EmailNotificationExceptionThrown_ShouldThrowException() {
        System.out.println("Executing @Test CreateUserHandleEmailNotificationException");

        // Arrange/Given
        User user1 = new User("Anirudh Patel", 35);
        int expectedUserCount = 0;
        // If you mock thenTrow(RuntimeException), then add try/catch in createUser method
        when(userDatabase.saveUser(Mockito.anyString(), Mockito.any(User.class))).thenReturn(true);

        // when does not work for void methods throwing exceptions
        // when(emailService.scheduleEmailConfirmation(any(User.class))).thenThrow();

        // this will mock exception throw in EmailNotificationService void method
        doThrow(EmailNotificationServiceException.class)
                .when(emailService).scheduleEmailConfirmation(any(User.class));
        // Another variation is doNothing when you want mock methods to do nothing
        // doNothing().when(emailService).scheduleEmailConfirmation(any(User.class));

        when(userDatabase.getAllUsersCount()).thenReturn(0);

        // Act/Assert/When
        assertThrows(UserServiceException.class, () -> {
            createdUserId = userServiceImpl.createUser(user1);
        }, "Should have thrown UserServiceException instead");

        // Assert/Then
        int actualUsersCount = userServiceImpl.getAllUsersCount();
        System.out.println("ActualUsersCount : " + actualUsersCount);

        assertEquals(expectedUserCount, actualUsersCount, "Mismatch between expected and actual users count");

        // times(1) can be omitted since it is the default value in verification mode
        verify(userDatabase, times(1)).saveUser(Mockito.anyString(), Mockito.any(User.class));
        verify(emailService, times(1)).scheduleEmailConfirmation(any(User.class));
    }

    @Order(4)
    @DisplayName("CreateUserWithEmailConfirmation")
    @Test
    void testCreateUser_WithEmailConfirmation_ShouldCallRealMethod() {
        // Arrange
        int expectedUserCount = 0;
        User user1 = new User("Anirudh Patel", 35);

        when(userDatabase.saveUser(anyString(), any(User.class))).thenReturn(true);
        doCallRealMethod()
                .when(emailService)
                .scheduleEmailConfirmation(any(User.class));

        // Act/When
        createdUserId = userServiceImpl.createUser(user1);

        // Assert
        int actualUsersCount = userServiceImpl.getAllUsersCount();
        System.out.println("ActualUsersCount : " + actualUsersCount);

        assertNotNull(createdUserId, "createdUserId should not be null");
        assertEquals(expectedUserCount, actualUsersCount, "Mismatch between expected and actual users count");

        verify(userDatabase, times(1)).saveUser(anyString(), any(User.class));
        verify(emailService, times(1)).scheduleEmailConfirmation(any(User.class));
    }


    @Order(5)
    @DisplayName("GetUserDetails")
    @Test
    @Disabled
    void testGetUser_SupplyUserIdState_ShouldReturnCorrectUser() {
        // Arrange
        System.out.println("Executing @Test GetUserDetails");

        // Act
        User returnedUserDetails = userServiceImpl.getUser(createdUserId);

        // Assert
        assertNotNull(returnedUserDetails, () -> "Returned UserDetails should not be null for createdUserId : " + createdUserId);
    }


    @Order(6)
    @DisplayName("UpdateUser")
    @Test
    @Disabled
    void testUpdateUser_ExistingUsersUpdated_ShouldBeUpdatedToDB() {
        // Arrange
        System.out.println("Executing @Test UpdateUser");
        User updateUserDetails = userServiceImpl.getUser(createdUserId);
        int expectedUserCount = 1, expectedUserAge = 40;

        // Act
        updateUserDetails.setAge(40);
        userServiceImpl.updateUser(createdUserId, updateUserDetails);

        // Assert
        int actualUsersCount = userServiceImpl.getAllUsersCount();
        int updatedUserAge = userServiceImpl.getUser(createdUserId).getAge();
        System.out.println("UpdatedUserAge : " + updatedUserAge);

        assertEquals(expectedUserCount, actualUsersCount, "Mismatch between expected and actual users count");
        assertEquals(expectedUserAge, updatedUserAge, "Mismatch between expected and updatedUserAge");
    }

    @Order(7)
    @DisplayName("UpdateUserWithEmptyUserId")
    @Test
    @Disabled
    void testUpdateUser_PassEmptyUserId_ShouldThrowException() {
        // Arrange
        System.out.println("Executing @Test UpdateUserWithEmptyUserId");
        User updateUserDetails = userServiceImpl.getUser(createdUserId);
        String expectedExceptionMessage = "userId cannot be null or empty!!!";
        updateUserDetails.setAge(40);

        // Assert
        IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class, () -> {
            userServiceImpl.updateUser("", updateUserDetails);
        });

        assertEquals(expectedExceptionMessage, illegalArgumentException.getMessage(), "Exception message should have matched");
    }

    @Order(8)
    @DisplayName("UpdateUserNotFound")
    @Test
    @Disabled
    void testUpdateUser_PassIncorrectUserId_ShouldThrowException() {
        // Arrange
        System.out.println("Executing @Test UpdateUserNotFound");
        User updateUserDetails = userServiceImpl.getUser(createdUserId);
        updateUserDetails.setAge(40);
        String incorrectUserId = "1234";
        String expectedExceptionMessage = "User does not exist for userId : " + incorrectUserId;

        // Assert
        IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class, () -> {
            userServiceImpl.updateUser(incorrectUserId, updateUserDetails);
        });

        assertEquals(expectedExceptionMessage, illegalArgumentException.getMessage(), "Exception message should have matched");
    }

    @Order(9)
    @DisplayName("RemoveUser")
    @Test
    @Disabled
    void testRemoveUser_UsersRemoved_ShouldBeDeletedFromDB() {
        System.out.println("Executing @Test RemoveUser");

        // Act
        userServiceImpl.removeUser(createdUserId);

        // Assert
        assertTrue(userServiceImpl.getAllUsersCount() == 0, "userServiceImpl should not have any users in database");
    }
}