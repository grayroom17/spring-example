package com.example.spring.database.repository;

import com.example.spring.database.utils.ConnectionPool;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class UserService {
    //внедрение, когда бин указанного класса
    // существует только в одном экземпляре в контексте
    @Autowired
    UserRepository userRepository;

    //внедрение через @Qualifier
    @Autowired
    @Qualifier("pool1")
    ConnectionPool connectionPool;

    //внедрение через алиас/id бина
    @Autowired
    ConnectionPool pool1;
    @Autowired
    ConnectionPool pool2;

    @NonFinal
    ConnectionPool duplicatePool2;

    //внедрение всех бинов одного класса в список
    @Autowired
    List<ConnectionPool> pools;

    //    @Value("#{'${db.pool.size}'}")
    @Value("${db.pool.size}")
    @NonFinal
    Integer poolSize;

    public ConnectionPool getDuplicatePool2() {
        return duplicatePool2;
    }

    //при внедрении через сеттер имя параметра должно совпадать с аллиасом бина,
    //если таких бинов может быть несколько в контексте
    @Autowired
    public void setDuplicatePool2(ConnectionPool pool1) {
        this.duplicatePool2 = pool1;
    }
}
