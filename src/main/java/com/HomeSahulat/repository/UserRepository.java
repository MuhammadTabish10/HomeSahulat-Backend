package com.HomeSahulat.repository;

import com.HomeSahulat.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByName(String username);
    User findByPhoneAndPassword(String phone, String password);

    @Modifying
    @Query("UPDATE User u SET u.status = false WHERE u.id = :id")
    void setStatusInactive(@Param("id") Long id);

    @Modifying
    @Query("UPDATE User u SET u.otpFlag = true WHERE u.id = :id")
    void setOtpFlagTrue(@Param("id") Long id);

    User findByPhone(String phone);
}
