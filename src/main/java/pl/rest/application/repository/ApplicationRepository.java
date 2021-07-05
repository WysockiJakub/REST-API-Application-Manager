package pl.rest.application.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.rest.application.entity.Application;
import pl.rest.application.entity.Status;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Long> {

    Page<Application> findAllByNameAndStatus(String name, Status state, Pageable pageable);
    Page<Application> findAllByName(String name, Pageable pageable);
    Page<Application> findAllByStatus(Status state, Pageable pageable);
    Page<Application> findAllByAppNumberAndArchived(Long appNumber, boolean isArchived, Pageable pageable);
    Page<Application> findAllByAppNumber(Long appNumber, Pageable pageable);
}
