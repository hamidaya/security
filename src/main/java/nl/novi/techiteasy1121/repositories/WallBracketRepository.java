package nl.novi.techiteasy1121.repositories;

import nl.novi.techiteasy1121.models.RemoteController;
import nl.novi.techiteasy1121.models.WallBracket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface WallBracketRepository extends JpaRepository<WallBracket, Long> {
    List<WallBracket> findAllRemoteControllerByNameEqualsIgnoreCase(String name);
}


