```sql
CREATE TABLE employee (
    id SERIAL PRIMARY KEY,
    first_name VARCHAR(45),
    last_name VARCHAR(45),
    email VARCHAR(45)
);

INSERT INTO employee (id, first_name, last_name, email) VALUES
    (1, 'Leslie', 'Andrews', 'leslie@luv2code.com'),
    (2, 'Emma', 'Baumgarten', 'emma@luv2code.com'),
    (3, 'Avani', 'Gupta', 'avani@luv2code.com'),
    (4, 'Yuri', 'Petrov', 'yuri@luv2code.com'),
    (5, 'Juan', 'Vega', 'juan@luv2code.com');
```
![img.png](img/img.png)