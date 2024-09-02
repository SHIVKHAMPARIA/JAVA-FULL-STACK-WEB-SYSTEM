package com.example.usermanagement.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.example.usermanagement.model.User;
import com.example.usermanagement.repository.UserRepo;

@Service
public class UserService {

    @Autowired
    private UserRepo userRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Transactional
    public void registerUser(User user) {
        userRepository.save(user);
        createUserTable(user.getId());
    }

    private void createUserTable(Long userId) {
        String tableName = "user_" + userId;
        String sql = "CREATE TABLE " + tableName + " (" +
                     "Dates DATE, " +
                     "Text TEXT, " +
                     "Contact_Portal TEXT, " +
                     "Contact_Person TEXT, " +
                     "Helping_material VARCHAR(1000), " +
                     "Checklist TEXT, " +
                     "Enabled TEXT)";
        jdbcTemplate.execute(sql);
    }

    public void saveDates(Long userId, String dates, String text) {
        String tableName = "user_" + userId;
        String sql = "INSERT INTO " + tableName + " (Dates, Text) VALUES (?, ?)";
        jdbcTemplate.update(sql, dates, text);
    }

    public void saveChecklist(Long userId, String checklist) {
        String tableName = "user_" + userId;
        String sql = "INSERT INTO " + tableName + " (Checklist) VALUES (?)";
        jdbcTemplate.update(sql, checklist);
    }

    public void saveContacts(Long userId, String contactPortal, String contactPerson) {
        String tableName = "user_" + userId;
        String sql = "INSERT INTO " + tableName + " (Contact_Portal, Contact_Person) VALUES (?, ?)";
        jdbcTemplate.update(sql, contactPortal, contactPerson);
    }

    public void saveResources(Long userId, MultipartFile resourceFile) {
        String tableName = "user_" + userId;
        String sql = "INSERT INTO " + tableName + " (Helping_material) VALUES (?)";
        try {
            jdbcTemplate.update(sql, resourceFile.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception as needed
        }
    }

    public List<Map<String, Object>> getAllRows(Long userId) {
        String tableName = "user_" + userId;
        String sql = "SELECT * FROM " + tableName;
        return jdbcTemplate.queryForList(sql);
    }
    
    public List<Map<String, Object>> getDates(Long userId) {
        String tableName = "user_" + userId;
        String sql = "SELECT Dates, Text FROM " + tableName + " WHERE Dates IS NOT NULL AND Text IS NOT NULL";
        return jdbcTemplate.queryForList(sql);
    }

    public List<Map<String, Object>> getChecklist(Long userId) {
        String tableName = "user_" + userId;
        String sql = "SELECT Checklist, Enabled FROM " + tableName + " WHERE Checklist IS NOT NULL";
        return jdbcTemplate.queryForList(sql);
    }

    public List<Map<String, Object>> getContacts(Long userId) {
        String tableName = "user_" + userId;
        String sql = "SELECT Contact_Portal, Contact_Person FROM " + tableName + " WHERE Contact_Portal IS NOT NULL AND Contact_Person IS NOT NULL";
        return jdbcTemplate.queryForList(sql);
    }
    public void saveChecklistItem(Long userId, String checklistText, String enabled) {
        String tableName = "user_" + userId;
        String sql = "UPDATE " + tableName + " SET Enabled = ? WHERE Checklist = ?";
        jdbcTemplate.update(sql, enabled, checklistText);
    }


}
