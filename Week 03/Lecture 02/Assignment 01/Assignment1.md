## Assignment 01
Normalization is a database design technique that organizes tables to reduce redundancy and improve data integrity. The goal is to divide larger tables into smaller, related tables without losing information. Normalization typically involves several normal forms, each with specific requirements. The most commonly used normal forms are the First Normal Form (1NF), Second Normal Form (2NF), and Third Normal Form (3NF).

#### 1NF
Characteristics:
- All column values are atomic (indivisible).
- Each column contains only one value per row.
- Each row is unique.

##### Example
| StudentID  | StudentName  | Courses  |
|----------|----------|----------|
| 1 | Alice | Math, Physics |
| 2 | Bob | Biology, Chemistry |
| 3 | Charlie | Math, Chemistry |

This table is not in 1NF because the Courses column contains multiple values. Convert to 1NF:
| StudentID | StudentName | Course   |
|-----------|-------------|----------|
| 1         | Alice       | Math     |
| 1         | Alice       | Physics  |
| 2         | Bob         | Biology  |
| 2         | Bob         | Chemistry|
| 3         | Charlie     | Math     |
| 3         | Charlie     | Chemistry|

#### 2NF
Characteristics:
- Already in 1NF.
- All non-key attributes are fully functional dependent on the primary key.

##### Example
| StudentID | StudentName | Course    | Instructor  |
|-----------|-------------|-----------|-------------|
| 1         | Alice       | Math      | Dr. Smith   |
| 1         | Alice       | Physics   | Dr. Clark   |
| 2         | Bob         | Biology   | Dr. Johnson |
| 2         | Bob         | Chemistry | Dr. Lee     |
| 3         | Charlie     | Math      | Dr. Smith   |
| 3         | Charlie     | Chemistry | Dr. Lee     |

Instructor depends on Course, not on StudentID. To achieve 2NF, we need to remove partial dependencies by creating a new table for courses and instructors.
Students table:
| StudentID | StudentName |
|-----------|-------------|
| 1         | Alice       |
| 2         | Bob         |
| 3         | Charlie     |

Courses table:
| CourseID | Course    | Instructor  |
|----------|-----------|-------------|
| 1        | Math      | Dr. Smith   |
| 2        | Physics   | Dr. Clark   |
| 3        | Biology   | Dr. Johnson |
| 4        | Chemistry | Dr. Lee     |

StudentCourses table:
| StudentID | CourseID |
|-----------|----------|
| 1         | 1        |
| 1         | 2        |
| 2         | 3        |
| 2         | 4        |
| 3         | 1        |
| 3         | 4        |

#### 3NF
Characteristics:
- Already in 2NF.
- All the attributes are functionally dependent only on the primary key.

##### Example
| CourseID | Course    | Instructor  | InstructorOffice |
|----------|-----------|-------------|------------------|
| 1        | Math      | Dr. Smith   | Room 101         |
| 2        | Physics   | Dr. Clark   | Room 102         |
| 3        | Biology   | Dr. Johnson | Room 103         |
| 4        | Chemistry | Dr. Lee     | Room 104         |

InstructorOffice depends on Instructor, not on CourseID. To achieve 3NF, we separate the instructors' information into another table.

Courses table:
| CourseID | Course    | Instructor  |
|----------|-----------|-------------|
| 1        | Math      | Dr. Smith   |
| 2        | Physics   | Dr. Clark   |
| 3        | Biology   | Dr. Johnson |
| 4        | Chemistry | Dr. Lee     |

Instructors table:
| Instructor  | InstructorOffice |
|-------------|------------------|
| Dr. Smith   | Room 101         |
| Dr. Clark   | Room 102         |
| Dr. Johnson | Room 103         |
| Dr. Lee     | Room 104         |
