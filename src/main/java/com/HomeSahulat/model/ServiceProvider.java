    package com.HomeSahulat.model;

    import com.fasterxml.jackson.annotation.JsonIgnore;
    import lombok.AllArgsConstructor;
    import lombok.Builder;
    import lombok.Data;
    import lombok.NoArgsConstructor;
    import org.hibernate.annotations.CreationTimestamp;

    import javax.persistence.*;
    import java.time.LocalDateTime;
    import java.util.List;

    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    @Entity
    @Table(name = "service_provider")
    public class ServiceProvider {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @CreationTimestamp
        private LocalDateTime createdAt;

        private String name;
        private String description;
        private Double hourlyPrice;
        private Double totalExperience;
        private Double totalRating;
        private Boolean atWork;
        private Boolean haveShop;
        private Boolean status;

        @JsonIgnore
        @OneToOne
        @JoinColumn(name = "user_id")
        private User user;

        @ManyToOne
        @JoinColumn(name = "service_id")
        private Services services;

        @OneToMany(mappedBy = "serviceProvider", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
        private List<Attachment> attachment;
    }
