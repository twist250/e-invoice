package com.qtsoftwareltd.invoicing;

import com.qtsoftwareltd.invoicing.configs.ApplicationConfigs;
import com.qtsoftwareltd.invoicing.entities.User;
import com.qtsoftwareltd.invoicing.enums.EUserRole;
import com.qtsoftwareltd.invoicing.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BackdoorInit implements CommandLineRunner {
    private static final Logger logger = LogManager.getLogger(BackdoorInit.class);
    private final ApplicationConfigs applicationConfigs;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        if (applicationConfigs.getBackDoor() == null) {
            logger.info("Backdoor credentials not set");
            return;
        }

        userRepository.findByEmail(applicationConfigs.getBackDoor().getEmail()).ifPresentOrElse(
                user -> logger.info("Backdoor user already exists"),
                () -> {
                    logger.info("Creating backdoor user");
                    User backdoorAdmin = new User();
                    backdoorAdmin.setName("Backdoor Admin");
                    backdoorAdmin.setRole(EUserRole.ADMIN);
                    backdoorAdmin.setEmail(applicationConfigs.getBackDoor().getEmail());
                    backdoorAdmin.setPassword(passwordEncoder.encode(applicationConfigs.getBackDoor().getPassword()));
                    userRepository.save(backdoorAdmin);
                }
        );
    }
}
