package markigor.io.newscrawler.application.model.repository;

import markigor.io.newscrawler.application.model.entity.UserService;
import markigor.io.newscrawler.application.model.repository.querydsl.QUserServiceRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserServiceRepository extends JpaRepository<UserService, Long>, QUserServiceRepository {
}
