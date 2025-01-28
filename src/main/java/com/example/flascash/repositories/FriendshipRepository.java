package com.example.flascash.repositories;

import com.example.flascash.entities.Friendship;
import com.example.flascash.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;

import java.util.List;
import java.util.Optional;

public interface FriendshipRepository extends JpaRepository<Friendship, Long> {
    Optional<Friendship> findByUserAndFriendUser(User user, User friendUser);

    @Procedure(name = "findFriendByUserID")
    List<User> findFriendsByUserId(Integer id);

//    @Query("SELECT u FROM User u " +
//            "WHERE u.id IN (SELECT f.friendUser.id FROM Friendship f WHERE f.user.id = :userId " +
//            "UNION " +
//            "SELECT f.user.id FROM Friendship f WHERE f.friendUser.id = :userId)")
    @Procedure(name = "findAllFriendsForUser")
    List<User> findAllFriendsForUser(Long userId);
}
