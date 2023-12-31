INSERT INTO location (address, city, state, postal_code, country, latitude, longitude, status)
VALUES
    ('123 Main St', 'Lahore', 'Punjab', 54000, 'Pakistan', 31.5497, 74.3436, true),
    ('456 Business Rd', 'Karachi', 'Sindh', 75500, 'Pakistan', 24.8607, 67.0011, true),
    ('789 Green Ave', 'Islamabad', 'ICT', 44000, 'Pakistan', 33.6844, 73.0479, true),
    ('101 Commercial St', 'Faisalabad', 'Punjab', 38000, 'Pakistan', 31.4504, 73.1350, true),
    ('XYZ Shopping Mall', 'Rawalpindi', 'Punjab', 46000, 'Pakistan', 33.6844, 73.0479, true);

INSERT INTO roles (name) VALUES ('ROLE_ADMIN');

INSERT INTO users (created_at, name, password, email, phone, profile_picture_url, device_id, otp, otp_flag, status, location_id, verified)
VALUES
    ('2023-01-01 12:00:00', 'Muhammad Tabish', '$2a$10$VIfRcKEbcO6U4WT1oZf/l.xIlAV.Py5cQjOACRqaUNXaSpgtH1lQi', 'muhammadtabish05@gmail.com', '03353183328', 'https://buffer.com/cdn-cgi/image/w=1000,fit=contain,q=90,f=auto/library/content/images/size/w600/2023/10/free-images.jpg', 'device123', '$2a$10$fy5rnFrxUfZHbqSM3aKGYueg3b53W6SbnlcB6n3UKg6998fMxJkhi', true, true, 1, true),
    ('2023-02-02 14:30:00', 'Sham Ahmed', '$2a$10$VIfRcKEbcO6U4WT1oZf/l.xIlAV.Py5cQjOACRqaUNXaSpgtH1lQi', 'sham.ahmed@email.com', '03331234567', 'https://buffer.com/cdn-cgi/image/w=1000,fit=contain,q=90,f=auto/library/content/images/size/w600/2023/10/free-images.jpg', 'device456', '$2a$10$fy5rnFrxUfZHbqSM3aKGYueg3b53W6SbnlcB6n3UKg6998fMxJkhi', true, true, 2, true),
    ('2023-03-03 08:45:00', 'Ahmed Raza', '$2a$10$VIfRcKEbcO6U4WT1oZf/l.xIlAV.Py5cQjOACRqaUNXaSpgtH1lQi', 'ahmed.raza@email.com', '03041234567', 'https://buffer.com/cdn-cgi/image/w=1000,fit=contain,q=90,f=auto/library/content/images/size/w600/2023/10/free-images.jpg', 'device789', '$2a$10$fy5rnFrxUfZHbqSM3aKGYueg3b53W6SbnlcB6n3UKg6998fMxJkhi', true, true, 3, true),
    ('2023-04-04 09:30:00', 'Ayesha Malik', '$2a$10$VIfRcKEbcO6U4WT1oZf/l.xIlAV.Py5cQjOACRqaUNXaSpgtH1lQi', 'ayesha.malik@email.com', '03451234567', 'https://buffer.com/cdn-cgi/image/w=1000,fit=contain,q=90,f=auto/library/content/images/size/w600/2023/10/free-images.jpg', 'device101', '$2a$10$fy5rnFrxUfZHbqSM3aKGYueg3b53W6SbnlcB6n3UKg6998fMxJkhi', true, true, 4, true),
    ('2023-05-05 11:00:00', 'Kamran Khan', '$2a$10$VIfRcKEbcO6U4WT1oZf/l.xIlAV.Py5cQjOACRqaUNXaSpgtH1lQi', 'kamran.khan@email.com', '03121234567', 'https://buffer.com/cdn-cgi/image/w=1000,fit=contain,q=90,f=auto/library/content/images/size/w600/2023/10/free-images.jpg', 'device202', '$2a$10$fy5rnFrxUfZHbqSM3aKGYueg3b53W6SbnlcB6n3UKg6998fMxJkhi', true, true, 5, true),
    ('2023-06-06 15:30:00', 'Fatima Khan', '$2a$10$VIfRcKEbcO6U4WT1oZf/l.xIlAV.Py5cQjOACRqaUNXaSpgtH1lQi', 'fatima.khan@email.com', '03451234567', 'https://buffer.com/cdn-cgi/image/w=1000,fit=contain,q=90,f=auto/library/content/images/size/w600/2023/10/free-images.jpg', 'device303', '$2a$10$fy5rnFrxUfZHbqSM3aKGYueg3b53W6SbnlcB6n3UKg6998fMxJkhi', true, true, 1, true),
    ('2023-07-07 12:45:00', 'Zubair Ahmed', '$2a$10$VIfRcKEbcO6U4WT1oZf/l.xIlAV.Py5cQjOACRqaUNXaSpgtH1lQi', 'zubair.ahmed@email.com', '03221234567', 'https://buffer.com/cdn-cgi/image/w=1000,fit=contain,q=90,f=auto/library/content/images/size/w600/2023/10/free-images.jpg', 'device404', '$2a$10$fy5rnFrxUfZHbqSM3aKGYueg3b53W6SbnlcB6n3UKg6998fMxJkhi', true, true, 2, true),
    ('2023-08-08 11:15:00', 'Sara Malik', '$2a$10$VIfRcKEbcO6U4WT1oZf/l.xIlAV.Py5cQjOACRqaUNXaSpgtH1lQi', 'sara.malik@email.com', '03331234567', 'https://buffer.com/cdn-cgi/image/w=1000,fit=contain,q=90,f=auto/library/content/images/size/w600/2023/10/free-images.jpg', 'device505', '$2a$10$fy5rnFrxUfZHbqSM3aKGYueg3b53W6SbnlcB6n3UKg6998fMxJkhi', true, true, 3, false),
    ('2023-09-09 14:00:00', 'Usman Khan', '$2a$10$VIfRcKEbcO6U4WT1oZf/l.xIlAV.Py5cQjOACRqaUNXaSpgtH1lQi', 'usman.khan@email.com', '03041234567', 'https://buffer.com/cdn-cgi/image/w=1000,fit=contain,q=90,f=auto/library/content/images/size/w600/2023/10/free-images.jpg', 'device606', '$2a$10$fy5rnFrxUfZHbqSM3aKGYueg3b53W6SbnlcB6n3UKg6998fMxJkhi', true, true, 4, false),
    ('2023-10-10 10:30:00', 'Aisha Ahmed', '$2a$10$VIfRcKEbcO6U4WT1oZf/l.xIlAV.Py5cQjOACRqaUNXaSpgtH1lQi', 'aisha.ahmed@email.com', '03551234567', 'https://buffer.com/cdn-cgi/image/w=1000,fit=contain,q=90,f=auto/library/content/images/size/w600/2023/10/free-images.jpg', 'device707', '$2a$10$fy5rnFrxUfZHbqSM3aKGYueg3b53W6SbnlcB6n3UKg6998fMxJkhi', true, true, 5, false);

INSERT INTO `user_roles`(user_id, role_id) VALUES (1,1),(2,1),(3,1),(4,1),(5,1),(6,1),(7,1),(8,1),(9,1),(10,1);

INSERT INTO service (name, description, status)
VALUES
    ('Plumber', 'Emergency plumbing repairs', true),
    ('Electrician', 'Electrical repairs and installations', true),
    ('Carpenter', 'Carpenter services', true);

INSERT INTO service_provider (created_at, description, hourly_price, total_experience, total_rating, at_work, have_shop, status, user_id, service_id, cnic_no, cnic_url)
VALUES
    ('2023-02-02 08:15:00', 'Professional plumber', 30.0, 8.0, 4.8, true, true, true, 2, 1,'094637281','ServiceProvider/ServiceProvider_28/Cnic_2023_12_31_15_02_03.jpg'),
    ('2023-03-03 09:30:00', 'Fine electrician', 20.0, 3.0, 4.2, true, false, true, 3, 2,'094637281','ServiceProvider/ServiceProvider_28/Cnic_2023_12_31_15_02_03.jpg'),
    ('2023-04-04 11:15:00', 'Skilled electrician', 35.0, 7.0, 4.7, true, true, true, 4, 2,'094637281','ServiceProvider/ServiceProvider_28/Cnic_2023_12_31_15_02_03.jpg'),
    ('2023-04-04 11:15:00', 'Skilled carpenter', 45.0, 6.0, 4.8, true, true, true, 5, 3,'094637281','ServiceProvider/ServiceProvider_28/Cnic_2023_12_31_15_02_03.jpg'),
    ('2023-04-04 11:15:00', 'Very Skilled electrician', 55.0, 8.8, 4.9, true, true, true, 7, 2,'094637281','ServiceProvider/ServiceProvider_28/Cnic_2023_12_31_15_02_03.jpg'),
    ('2023-04-04 11:15:00', 'Very Skilled carpenter', 60.0, 9.0, 4.6, true, true, true, 6, 3,'094637281','ServiceProvider/ServiceProvider_28/Cnic_2023_12_31_15_02_03.jpg'),
    ('2023-04-04 11:15:00', 'Good carpenter', 20.0, 2.0, 3.6, true, true, true, 10, 3,'094637281','ServiceProvider/ServiceProvider_28/Cnic_2023_12_31_15_02_03.jpg'),
    ('2023-04-04 11:15:00', 'Ok carpenter', 10.0, 1.0, 2.6, true, true, true, 9, 3,'094637281','ServiceProvider/ServiceProvider_28/Cnic_2023_12_31_15_02_03.jpg');


INSERT INTO booking (created_at, appointment_date, appointment_time, status, user_id, service_provider_id, booking_status)
VALUES
    ('2023-01-11 14:15:00', '2023-01-20', '12:30:00', true, 1, 7, "Confirmed"),
    ('2023-02-12 09:45:00', '2023-02-05', '15:00:00', true, 2, 4, "Confirmed"),
    ('2023-03-13 15:30:00', '2023-02-10', '14:00:00', true, 3, 6, "Pending"),
    ('2023-04-14 18:00:00', '2023-03-15', '16:30:00', true, 4, 8, "Pending"),
    ('2023-05-15 12:00:00', '2023-03-20', '10:45:00', true, 5, 6, "Rejected");

INSERT INTO report_user (created_at, note, status, report_from_user_id, report_to_user_id)
VALUES
    ('2023-01-07 11:10:00', 'Inappropriate behavior', true, 1, 2),
    ('2023-02-08 13:30:00', 'Unreliable service', true, 2, 4),
    ('2023-03-09 09:45:00', 'Concerns about cleanliness', true, 3, 5),
    ('2023-04-10 12:30:00', 'Late electrical repairs', true, 4, 6),
    ('2023-05-11 14:15:00', 'AC service not satisfactory', true, 5, 9);

INSERT INTO review (created_at, note, rating, status, user_id, service_provider_id)
VALUES
    ('2023-06-10 09:00:00', 'Excellent plumbing work!', 4.9, true, 1, 1),
    ('2023-06-11 11:30:00', 'Quick response and good service', 4.7, true, 2, 1),
    ('2023-06-12 10:00:00', 'Fine electrical repairs', 4.5, true, 3, 2),
    ('2023-06-13 12:45:00', 'Prompt and professional', 4.8, true, 4, 2),
    ('2023-06-14 08:30:00', 'Impressive carpentry skills', 4.6, true, 5, 3),
    ('2023-06-15 14:15:00', 'Good communication and service', 4.4, true, 6, 3),
    ('2023-06-16 13:00:00', 'Average service', 3.8, true, 7, 3),
    ('2023-06-17 15:30:00', 'Not satisfied with the carpentry work', 2.5, true, 8, 3),
    ('2023-04-08 14:00:00', 'Efficient electrical repairs', 4.7, true, 1, 4),
    ('2023-05-09 10:45:00', 'Quick repair service', 4.5, true, 8, 5);

INSERT INTO payment (created_at, amount, status, booking_id)
VALUES
    ('2023-01-09 09:25:00', 50.0, true, 1),
    ('2023-02-10 18:00:00', 75.0, true, 2),
    ('2023-03-11 11:30:00', 30.0, true, 3),
    ('2023-04-12 14:45:00', 40.0, true, 4),
    ('2023-05-13 16:00:00', 55.0, true, 5);
