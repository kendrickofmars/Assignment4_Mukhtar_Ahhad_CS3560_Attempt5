import jakarta.persistence.*;
import entity.*;
import java.sql.Date;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args)
    {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        try{
            // Example Adding an Item/Book to Table, same process for documentary
            /*
            transaction.begin();
            Item item = new Item("2451LS", "Harry Potter", "Wizards", "Hogwarts", 12.50);
            Date date = Date.valueOf("2001-06-19");
            String authors = "J.K. Rowling";
            Book harry = new Book(authors, 13, "HP Publishing", date);
            harry.setItemByItemCode(item);
            entityManager.persist(harry);
            transaction.commit();
             */

            // Example Adding a Student to Table

            transaction.begin();
            Student student = new Student("022", "Jon Doe", "Comp Sci");
            entityManager.persist(student);
            transaction.commit();


            // Example Adding a Loan
            /*
            transaction.begin();
            Student student = entityManager.getReference(Student.class, "0221432");
            Item item = entityManager.getReference(Item.class, "2451LS");
            Loan loan = new Loan();
            loan.setDueDate(new Date(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(7)));
            loan.setStudentByStudentId(student);
            loan.setItemByItemCode(item);
            entityManager.persist(loan);
            transaction.commit();
             */
            /*
            Loan loan = entityManager.getReference(Loan.class,1);
            List result = loan.reciept();
            System.out.print(Arrays.toString(result.toArray()));
            /*
            Date date = Date.valueOf("2030-06-19");
            System.out.println(date.getTime());
            System.out.println("Your fine is: " + loan.computeFine(date));
             */

        }finally {
            if(transaction.isActive()){
                transaction.rollback();
            }
            entityManager.close();
            entityManagerFactory.close();
        }
    }
}
