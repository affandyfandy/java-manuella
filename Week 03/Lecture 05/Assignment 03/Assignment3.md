## Assignment 03: Comparing Best Practice RestAPI

#### Best practice from the article:
1. Use Nouns in URLs
Design your endpoint paths using nouns that represent the resources.
2. Use Plural Nouns
Use plural nouns in your endpoint paths for consistency.
3. Use HTTP Methods Correctly
Map HTTP methods to CRUD operations:
GET for reading
POST for creating
PUT/PATCH for updating
DELETE for deleting
4. Version Your API
Include versioning in your API to manage changes and ensure backward compatibility.
5. Provide Useful Responses
Ensure responses are informative, providing status codes and meaningful messages.
6. Use Proper Status Codes
Return appropriate HTTP status codes to indicate the result of a request.
7. Document Your API
Maintain comprehensive documentation, possibly using tools like Swagger.
8. Handle Errors Gracefully
Implement structured error responses with clear messages and codes.
9. Validate Request Data
Validate incoming data to ensure it meets expected formats and constraints.
10. Use HATEOAS
Implement Hypermedia as the Engine of Application State to make APIs more self-descriptive.
11. Support Pagination and Filtering
Implement pagination and filtering for endpoints that return large datasets.
12. Use SSL/TLS
Secure your API using HTTPS to protect data in transit.
13. Implement Authentication and Authorization
Secure your API endpoints with appropriate authentication and authorization mechanisms.
14. Cache Responses
Use caching to improve performance and reduce server load.
15. Optimize for Performance
Optimize your API for performance by minimizing payload sizes and improving query efficiency.
16. Use JSON for Responses
Prefer JSON as the response format due to its readability and compatibility.
17. Log and Monitor
Implement logging and monitoring to track usage and diagnose issues.
18. Rate Limiting
Implement rate limiting to protect against abuse and ensure fair usage.
19. Test Thoroughly
Ensure comprehensive testing, including unit tests, integration tests, and end-to-end tests.
20. Design for Extensibility
Design your API to be flexible and extensible, anticipating future requirements and changes.

#### Improvement from previous task:
- Plural Collection Naming:
Articles: Data collections should use the plural form.
Code: The /employee endpoint can be changed to /employees for consistency.

- Caching to Improve Performance:
Article: Caching implementation to improve performance.
Code: No caching implementation. Could consider using Redis or an in-memory cache.