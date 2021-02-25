package am.vardanmk.etllambda.respository;

import am.vardanmk.etllambda.domain.Notes;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DataRepo extends CrudRepository<Notes, Long> {
}
