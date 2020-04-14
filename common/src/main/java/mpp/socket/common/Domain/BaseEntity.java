package mpp.socket.common.Domain;

import org.springframework.stereotype.Component;

import java.io.Serializable;


public class BaseEntity<ID> implements Serializable {
    private ID id;


    public ID getId() {
        return id;
    }

    public void setId(ID id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "BaseEntity{" +
                "id=" + id +
                '}';
    }
}
