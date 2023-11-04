INSERT INTO `location` VALUES (1,'Johar-block19','karachi','pakistan',24.860735,67.001137,'home',76483,'sindh',_binary ''),(2,'Malir-block 12','Karachi','Pakistan',21.73524,11.34562,'work',25473,'sindh',_binary ''),(3,'DHA','Karachi','Pakistan',25.34524,14.354562,'work 2',253473,'sindh',_binary ''),(4,'Clifton','Karachi','Pakistan',25.34524,14.354562,'work 3',253473,'sindh',_binary ''),(5,'Landhi','Karachi','Pakistan',25.34524,14.354562,'work 4',253473,'sindh',_binary '\0'),(6,'GulburgTown','Lahore','Pakistan',50.34524,24.222562,'Home 2',22273,'Punjab',_binary '');
INSERT INTO `roles` VALUES (1,'ROLE_ADMIN');
INSERT INTO `users` VALUES (1,'2023-10-22 00:00:00.000000','dev-01','muhammadtabish05@gmail.com','Tabish','Rashid','tabish','152637',true,'$2a$12$srwaRGHf6V.vPD6WfY0MleTZ3gfy8fMr04/YXIEs6NoYlJr3J/njS','03353183356','https://images.unsplash.com/photo-1529665253569-6d01c0eaf7b6?auto=format&fit=crop&q=80&w=1385&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',_binary '',1),(2,'2023-10-22 21:05:59.095000','dev-02','basim36@gmail.com','Basim','Ahmed','basim','145637',true,'$2a$10$sGff2r/IvZNZgvUlGQs4ge5BzFC97yJFPHDWZEQbb3Af1BS1d5UUa','03353214453','https://www.latlong.net/photos/tb-taylor-swift-the-eras-tour.jpg',_binary '',4);
INSERT INTO `user_roles` VALUES (1,1),(2,1);
INSERT INTO `service` VALUES (1,'works for water system','Plumber',_binary ''),(2,'works for wood system','Carpenter',_binary '');
INSERT INTO `service_provider` VALUES (1,_binary '','2023-10-22 21:43:12.424000','Hi I\'m a worker',_binary '',500,_binary '',2,4.5,2,1);
INSERT INTO `attachment` VALUES (1,'2023-10-22 21:43:12.439000','D/cnic.jpg','cnic-1',_binary '',1);
INSERT INTO `booking` VALUES (2,'2023-10-26','2023-10-22 00:00:00.000000',_binary '',1,2);
INSERT INTO `report_user` VALUES (1,'2023-10-22 21:51:10.805000','He is a fraud',_binary '',1,2),(2,'2023-10-22 21:52:54.217000','He is a fraud',_binary '',1,2);
INSERT INTO `review` VALUES (1,'2023-10-22 22:02:41.421000','He was excellent',4.9,_binary '',1,2);
INSERT INTO `payment` VALUES (1,10746,'2023-10-22 22:28:05.113000',_binary '',2);


