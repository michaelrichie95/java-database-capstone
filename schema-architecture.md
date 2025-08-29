This Spring Boot application combines MVC and REST architectures. The Admin and Doctor dashboards are built with Thymeleaf templates, while the other modules are exposed through RESTful APIs. It integrates with two databases—MySQL for patient, doctor, appointment, and admin information, and MongoDB for prescription data. All incoming requests flow through a unified service layer, which then interacts with the appropriate repositories. MySQL operations use JPA entities, whereas MongoDB relies on document-based models.

1. User accesses AdminDashboard or Appointment pages.
2. The action is routed to the appropriate Thymeleaf or REST controller.
3. The controller calls the service layer.
4. The service layer uses either MySQL Repo or MongoDB Repo.
5. Either repository accesses its similarly named Database, respectively.
6. MySQL Database routes to MySQL Models and MongoDB Database routes to MongoDB Model
7. The appropriate JPA entity is accessed for MySQL models and the appropriate document for MongoDB Model.
