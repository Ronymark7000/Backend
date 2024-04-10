package com.project.JewelHub.booklist;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BooklistRepo extends JpaRepository<Booklist, Integer> {
    @Query(value = "SELECT * FROM booklist where user_id = :userId", nativeQuery = true)
    List<Booklist> findAllItemsByUser(long userId);

    @Query(value = "SELECT * FROM booklist WHERE user_id = :userId AND item_code = :itemCode LIMIT 1", nativeQuery = true)
    Booklist findBooklistByUserIdAndItemCode(int userId, int itemCode);
}
