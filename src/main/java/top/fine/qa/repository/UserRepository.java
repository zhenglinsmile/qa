package top.fine.qa.repository;

import org.springframework.data.repository.CrudRepository;
import top.fine.qa.model.User;

/**
 * @description: description
 * @author: zhengLin
 * @version: 1.0
 * @create: 2020-04-08 16:22
 **/
public interface UserRepository extends CrudRepository<User, Integer> {

    User findUserByToken(String token);

    User findUserByAccountId(Long accountId);

}
