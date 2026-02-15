package com.example.SanGeets.DAO;

import com.example.SanGeets.Model.PlayList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayListDAO extends JpaRepository<PlayList,Long> {

    PlayList findByTitle(String title);
}
