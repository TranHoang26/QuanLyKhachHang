package org.example.demo.repository;

import org.example.demo.model.Customer;
import org.springframework.stereotype.Repository;
import javax.persistence.*;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;

@Transactional
@Repository
public class CustomerRepository implements ICustomerRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Customer> findAll() {
        TypedQuery<Customer> query = entityManager.createQuery("SELECT c FROM Customer c", Customer.class);
        return query.getResultList(); // thực thi truy vấn và trả về danh sách các kết quả
    }

    @Override
    public Customer findById(Long id) {
        TypedQuery<Customer> query = entityManager.createQuery("SELECT c FROM Customer c WHERE c.id = :id", Customer.class);
        query.setParameter("id", id);
        try {
            return query.getSingleResult();
        }catch(NoResultException e) {
            return null;
        }
    }

    @Override
    public void save(Customer customer) { //phương thức lưu 1 đối tượng Customer và CSDL
        if(!Objects.isNull(customer.getId())){ // kiểm tra ID
            entityManager.merge(customer); // merge giúp cập nhật đối tượng nếu ID đã tồn tại
        }else {
            entityManager.persist(customer); // persist giúp lưu 1 đối tượng mới vào CSDL nếu ID chưa tồn tại
        }
    }

    @Override
    public void remove(Customer customer) {
        Customer c = findById(customer.getId()); // tìm đối tượng cần xóa
        if(c != null){
            entityManager.remove(c); // remove xóa 1 đối tượng khỏi CSDL
        }
    }
}
