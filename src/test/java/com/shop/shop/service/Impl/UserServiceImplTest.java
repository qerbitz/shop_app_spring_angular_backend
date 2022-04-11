package com.shop.shop.service.Impl;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.shop.shop.entity.User;
import com.shop.shop.exception.EmailExistException;
import com.shop.shop.exception.EmailNotFoundException;
import com.shop.shop.exception.UserNotFoundException;
import com.shop.shop.exception.UsernameExistException;
import com.shop.shop.repositories.UserRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import javax.mail.MessagingException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {UserServiceImpl.class, BCryptPasswordEncoder.class})
@ExtendWith(SpringExtension.class)
class UserServiceImplTest {
    @MockBean
    private EmailService emailService;

    @MockBean
    private LoginAttemptService loginAttemptService;

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private UserServiceImpl userServiceImpl;

    @Test
    void testLoadUserByUsername() throws UsernameNotFoundException {
        User user = new User();
        user.setActive(true);
        user.setAuthorities(new String[]{"JaneDoe"});
        user.setEmail("jane.doe@example.org");
        //user.setId(123L);
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        user.setJoinDate(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user.setLastLoginDate(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        LocalDateTime atStartOfDayResult2 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user.setLastLoginDateDisplay(Date.from(atStartOfDayResult2.atZone(ZoneId.of("UTC")).toInstant()));
        user.setNotLocked(true);
        user.setPassword("iloveyou");
        user.setRole("Role");
        user.setUserId("42");
        user.setUsername("janedoe");

        User user1 = new User();
        user1.setActive(true);
        user1.setAuthorities(new String[]{"JaneDoe"});
        user1.setEmail("jane.doe@example.org");
       // user1.setId(123L);
        LocalDateTime atStartOfDayResult3 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user1.setJoinDate(Date.from(atStartOfDayResult3.atZone(ZoneId.of("UTC")).toInstant()));
        LocalDateTime atStartOfDayResult4 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user1.setLastLoginDate(Date.from(atStartOfDayResult4.atZone(ZoneId.of("UTC")).toInstant()));
        LocalDateTime atStartOfDayResult5 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user1.setLastLoginDateDisplay(Date.from(atStartOfDayResult5.atZone(ZoneId.of("UTC")).toInstant()));
        user1.setNotLocked(true);
        user1.setPassword("iloveyou");
        user1.setRole("Role");
        user1.setUserId("42");
        user1.setUsername("janedoe");
        when(this.userRepository.save((User) any())).thenReturn(user1);
        when(this.userRepository.findUserByUsername((String) any())).thenReturn(user);
        when(this.loginAttemptService.hasExceededMaxAttempts((String) any())).thenReturn(true);
        UserDetails actualLoadUserByUsernameResult = this.userServiceImpl.loadUserByUsername("janedoe");
        assertTrue(actualLoadUserByUsernameResult.isEnabled());
        assertFalse(actualLoadUserByUsernameResult.isAccountNonLocked());
        verify(this.userRepository).findUserByUsername((String) any());
        verify(this.userRepository).save((User) any());
        verify(this.loginAttemptService).hasExceededMaxAttempts((String) any());
    }

    @Test
    void testRegister() throws EmailExistException, UserNotFoundException, UsernameExistException {
        User user = new User();
        user.setActive(true);
        user.setAuthorities(new String[]{"JaneDoe"});
        user.setEmail("jane.doe@example.org");
        //user.setId(123L);
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        user.setJoinDate(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user.setLastLoginDate(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        LocalDateTime atStartOfDayResult2 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user.setLastLoginDateDisplay(Date.from(atStartOfDayResult2.atZone(ZoneId.of("UTC")).toInstant()));
        user.setNotLocked(true);
        user.setPassword("iloveyou");
        user.setRole("Role");
        user.setUserId("42");
        user.setUsername("janedoe");

        User user1 = new User();
        user1.setActive(true);
        user1.setAuthorities(new String[]{"JaneDoe"});
        user1.setEmail("jane.doe@example.org");
        //user1.setId(123L);
        LocalDateTime atStartOfDayResult3 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user1.setJoinDate(Date.from(atStartOfDayResult3.atZone(ZoneId.of("UTC")).toInstant()));
        LocalDateTime atStartOfDayResult4 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user1.setLastLoginDate(Date.from(atStartOfDayResult4.atZone(ZoneId.of("UTC")).toInstant()));
        LocalDateTime atStartOfDayResult5 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user1.setLastLoginDateDisplay(Date.from(atStartOfDayResult5.atZone(ZoneId.of("UTC")).toInstant()));
        user1.setNotLocked(true);
        user1.setPassword("iloveyou");
        user1.setRole("Role");
        user1.setUserId("42");
        user1.setUsername("janedoe");
        when(this.userRepository.findUserByEmail((String) any())).thenReturn(user1);
        when(this.userRepository.findUserByUsername((String) any())).thenReturn(user);
        assertThrows(UsernameExistException.class,
                () -> this.userServiceImpl.register("janedoe", "iloveyou", "jane.doe@example.org"));
        verify(this.userRepository).findUserByEmail((String) any());
        verify(this.userRepository).findUserByUsername((String) any());
    }

    @Test
    void testRegister2() throws EmailExistException, UserNotFoundException, UsernameExistException {
        User user = new User();
        user.setActive(true);
        user.setAuthorities(new String[]{"JaneDoe"});
        user.setEmail("jane.doe@example.org");
        //user.setId(123L);
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        user.setJoinDate(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user.setLastLoginDate(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        LocalDateTime atStartOfDayResult2 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user.setLastLoginDateDisplay(Date.from(atStartOfDayResult2.atZone(ZoneId.of("UTC")).toInstant()));
        user.setNotLocked(true);
        user.setPassword("iloveyou");
        user.setRole("Role");
        user.setUserId("42");
        user.setUsername("janedoe");
        when(this.userRepository.findUserByEmail((String) any())).thenThrow(new UsernameNotFoundException(""));
        when(this.userRepository.findUserByUsername((String) any())).thenReturn(user);
        assertThrows(UsernameNotFoundException.class,
                () -> this.userServiceImpl.register("janedoe", "iloveyou", "jane.doe@example.org"));
        verify(this.userRepository).findUserByEmail((String) any());
        verify(this.userRepository).findUserByUsername((String) any());
    }

    @Test
    void testFindUserByUsername() {
        User user = new User();
        user.setActive(true);
        user.setAuthorities(new String[]{"JaneDoe"});
        user.setEmail("jane.doe@example.org");
       // user.setId(123L);
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        user.setJoinDate(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user.setLastLoginDate(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        LocalDateTime atStartOfDayResult2 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user.setLastLoginDateDisplay(Date.from(atStartOfDayResult2.atZone(ZoneId.of("UTC")).toInstant()));
        user.setNotLocked(true);
        user.setPassword("iloveyou");
        user.setRole("Role");
        user.setUserId("42");
        user.setUsername("janedoe");
        when(this.userRepository.findUserByUsername((String) any())).thenReturn(user);
        assertSame(user, this.userServiceImpl.findUserByUsername("janedoe"));
        verify(this.userRepository).findUserByUsername((String) any());
    }

    @Test
    void testFindUserByUsername2() {
        when(this.userRepository.findUserByUsername((String) any())).thenThrow(new UsernameNotFoundException("Msg"));
        assertThrows(UsernameNotFoundException.class, () -> this.userServiceImpl.findUserByUsername("janedoe"));
        verify(this.userRepository).findUserByUsername((String) any());
    }

    @Test
    void testFindUserByEmail() {
        User user = new User();
        user.setActive(true);
        user.setAuthorities(new String[]{"JaneDoe"});
        user.setEmail("jane.doe@example.org");
       // user.setId(123L);
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        user.setJoinDate(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user.setLastLoginDate(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        LocalDateTime atStartOfDayResult2 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user.setLastLoginDateDisplay(Date.from(atStartOfDayResult2.atZone(ZoneId.of("UTC")).toInstant()));
        user.setNotLocked(true);
        user.setPassword("iloveyou");
        user.setRole("Role");
        user.setUserId("42");
        user.setUsername("janedoe");
        when(this.userRepository.findUserByEmail((String) any())).thenReturn(user);
        assertSame(user, this.userServiceImpl.findUserByEmail("jane.doe@example.org"));
        verify(this.userRepository).findUserByEmail((String) any());
    }

    @Test
    void testFindUserByEmail2() {
        when(this.userRepository.findUserByEmail((String) any())).thenThrow(new UsernameNotFoundException("Msg"));
        assertThrows(UsernameNotFoundException.class, () -> this.userServiceImpl.findUserByEmail("jane.doe@example.org"));
        verify(this.userRepository).findUserByEmail((String) any());
    }

    @Test
    void testResetPassword() throws EmailNotFoundException, MessagingException {
        User user = new User();
        user.setActive(true);
        user.setAuthorities(new String[]{"JaneDoe"});
        user.setEmail("jane.doe@example.org");
       // user.setId(123L);
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        user.setJoinDate(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user.setLastLoginDate(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        LocalDateTime atStartOfDayResult2 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user.setLastLoginDateDisplay(Date.from(atStartOfDayResult2.atZone(ZoneId.of("UTC")).toInstant()));
        user.setNotLocked(true);
        user.setPassword("iloveyou");
        user.setRole("Role");
        user.setUserId("42");
        user.setUsername("janedoe");

        User user1 = new User();
        user1.setActive(true);
        user1.setAuthorities(new String[]{"JaneDoe"});
        user1.setEmail("jane.doe@example.org");
        //user1.setId(123L);
        LocalDateTime atStartOfDayResult3 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user1.setJoinDate(Date.from(atStartOfDayResult3.atZone(ZoneId.of("UTC")).toInstant()));
        LocalDateTime atStartOfDayResult4 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user1.setLastLoginDate(Date.from(atStartOfDayResult4.atZone(ZoneId.of("UTC")).toInstant()));
        LocalDateTime atStartOfDayResult5 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user1.setLastLoginDateDisplay(Date.from(atStartOfDayResult5.atZone(ZoneId.of("UTC")).toInstant()));
        user1.setNotLocked(true);
        user1.setPassword("iloveyou");
        user1.setRole("Role");
        user1.setUserId("42");
        user1.setUsername("janedoe");
        when(this.userRepository.save((User) any())).thenReturn(user1);
        when(this.userRepository.findUserByEmail((String) any())).thenReturn(user);
        doNothing().when(this.emailService).sendNewPasswordEmail((String) any(), (String) any());
        this.userServiceImpl.resetPassword("jane.doe@example.org");
        verify(this.userRepository).findUserByEmail((String) any());
        verify(this.userRepository).save((User) any());
        verify(this.emailService).sendNewPasswordEmail((String) any(), (String) any());
    }
}

