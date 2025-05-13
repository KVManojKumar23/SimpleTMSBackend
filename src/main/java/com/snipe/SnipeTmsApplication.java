package com.snipe;

import com.snipe.entity.Organization;
import com.snipe.entity.Role;
import com.snipe.repository.OrganizationRepository;
import com.snipe.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDateTime;
import java.util.Arrays;

@SpringBootApplication
public class SnipeTmsApplication {

    private final RoleRepository roleRepository;
    private OrganizationRepository organizationRepository;

    @Autowired
    public SnipeTmsApplication(OrganizationRepository organizationRepository, RoleRepository roleRepository) {
        this.organizationRepository = organizationRepository;
        this.roleRepository = roleRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(SnipeTmsApplication.class, args);
    }

//    @Override
//    public void run(String... args) throws Exception {
//
//        Role adminRole = Role.builder()
//                .roleName(Role.RoleType.ADMIN)
//                .description("Department admin")
//                .createdAt(LocalDateTime.parse("2025-04-03T18:54:24.787123"))
//                .lastModifiedAt(LocalDateTime.parse("2025-04-03T18:54:24.787123"))
//                .build();
//
//        Role orgRole = Role.builder()
//                .roleName(Role.RoleType.ORGANIZATION)
//                .description("Organization administrator")
//                .createdAt(LocalDateTime.parse("2025-04-03T18:55:34.515106"))
//                .lastModifiedAt(LocalDateTime.parse("2025-04-03T18:55:34.515106"))
//                .build();
//
//        Role hrRole = Role.builder()
//                .roleName(Role.RoleType.HR)
//                .description("Human Resources")
//                .createdAt(LocalDateTime.parse("2025-04-03T18:56:06.309662"))
//                .lastModifiedAt(LocalDateTime.parse("2025-04-03T18:56:06.309662"))
//                .build();
//
//        Role managerRole = Role.builder()
//                .roleName(Role.RoleType.MANAGER)
//                .description("Team manager")
//                .createdAt(LocalDateTime.parse("2025-04-03T18:56:28.436005"))
//                .lastModifiedAt(LocalDateTime.parse("2025-04-03T18:56:28.436005"))
//                .build();
//
//        Role userRole = Role.builder()
//                .roleName(Role.RoleType.USER)
//                .description("Basic user access")
//                .createdAt(LocalDateTime.parse("2025-04-03T18:57:14.302569"))
//                .lastModifiedAt(LocalDateTime.parse("2025-04-03T18:57:14.302569"))
//                .build();
//
//
//        Organization org1 = Organization.builder()
//                .orgName("TechSpark Solutions")
//                .address("123 Innovation Road, Bangalore")
//                .email("contact@techspark.com")
//                .phoneNumber("9876543210")
//                .website("https://www.techspark.com")
//                .industryType("IT Services")
//                .status(true)
//                .createdBy("admin")
//                .updatedBy("admin")
//                .createdAt(LocalDateTime.now())
//                .updatedAt(LocalDateTime.now())
//                .build();
//
//        Organization org2 = Organization.builder()
//                .orgName("MediCore Labs")
//                .address("45 Health Street, Hyderabad")
//                .email("support@medicorelabs.com")
//                .phoneNumber("9123456789")
//                .website("https://www.medicorelabs.com")
//                .industryType("Healthcare")
//                .status(true)
//                .createdBy("admin")
//                .updatedBy("admin")
//                .createdAt(LocalDateTime.now())
//                .updatedAt(LocalDateTime.now())
//                .build();
//
//        Organization org3 = Organization.builder()
//                .orgName("GreenGrid Energy")
//                .address("789 Eco Park, Pune")
//                .email("info@greengrid.com")
//                .phoneNumber("9988776655")
//                .website("https://www.greengrid.com")
//                .industryType("Renewable Energy")
//                .status(true)
//                .createdBy("admin")
//                .updatedBy("admin")
//                .createdAt(LocalDateTime.now())
//                .updatedAt(LocalDateTime.now())
//                .build();
//
//        Organization org4 = Organization.builder()
//                .orgName("EduLeap Academy")
//                .address("88 Knowledge Ave, Delhi")
//                .email("hello@eduleap.com")
//                .phoneNumber("9786543210")
//                .website("https://www.eduleap.com")
//                .industryType("Education")
//                .status(true)
//                .createdBy("admin")
//                .updatedBy("admin")
//                .createdAt(LocalDateTime.now())
//                .updatedAt(LocalDateTime.now())
//                .build();
//
//        Organization org5 = Organization.builder()
//                .orgName("FinGenius Pvt Ltd")
//                .address("66 Finance Street, Mumbai")
//                .email("services@fingenius.com")
//                .phoneNumber("9345612789")
//                .website("https://www.fingenius.com")
//                .industryType("Finance")
//                .status(true)
//                .createdBy("admin")
//                .updatedBy("admin")
//                .createdAt(LocalDateTime.now())
//                .updatedAt(LocalDateTime.now())
//                .build();
//
//        organizationRepository.saveAll(Arrays.asList(org1,org2,org3,org4,org5));
//        roleRepository.saveAll(Arrays.asList(adminRole, orgRole, hrRole, managerRole, userRole));
//
//     }
}
