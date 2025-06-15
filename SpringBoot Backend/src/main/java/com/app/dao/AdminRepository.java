package com.app.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.dto.LoginDTO;  // Ensure this import is correct
import com.app.model.Admin;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Integer> {

    // Check if email exists (case-insensitive)
    @Query("SELECT CASE WHEN COUNT(a) > 0 THEN TRUE ELSE FALSE END FROM Admin a WHERE LOWER(a.email) = LOWER(:email)")
    Boolean emailExist(@Param("email") String email);

    // Return LoginDTO if admin with matching email and password exists
    @Query("SELECT new com.app.dto.LoginDTO(a.id, a.email, a.name) FROM Admin a WHERE LOWER(a.email) = LOWER(:email) AND a.password = :password")
    LoginDTO getAdminDetails(@Param("email") String email, @Param("password") String password);
}
