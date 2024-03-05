package nl.novi.techiteasy1121.repositories;

import nl.novi.techiteasy1121.models.CIModule;
import nl.novi.techiteasy1121.models.RemoteController;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RemoteControllerRepository extends JpaRepository<RemoteController, Long> {
    List<RemoteController> findAllRemoteControllerByBrandEqualsIgnoreCase(String brand);
}
