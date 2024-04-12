package propensi.proyek.siRelawan;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import propensi.proyek.siRelawan.model.EnumRole;
import propensi.proyek.siRelawan.model.UserModel;
import propensi.proyek.siRelawan.repository.UserDb;

@SpringBootApplication
@ComponentScan(basePackages = {"propensi.proyek.siRelawan"})
public class SiRelawanApplication implements CommandLineRunner{
	@Autowired
	private UserDb userDb;

	public static void main(String[] args) {
		SpringApplication.run(SiRelawanApplication.class, args);
	}

    @Override
    public void run(String... args) throws Exception {
        List<UserModel> dummyRelawan = generateRelawan(10);
        List<UserModel> dummyAdmin = generateAdmin(2);
        userDb.saveAll(dummyRelawan);
        userDb.saveAll(dummyAdmin);
    }

	public List<UserModel> generateRelawan(int count) {
        List<UserModel> users = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < count; i++) {
            String fullName = "Relawan Dummy " + i;
            String username = "Relawan" + i;
            String password = "relawan" + i;
            String email = "relawan" + i + "@dummy.com";
            Long nomorWA = 628000000000L + random.nextInt(1000000000);
            EnumRole role = EnumRole.RELAWAN;
            int poin = 20000 + random.nextInt(150001);

            // Calculate the base values to ensure the desired digit length
            long baseNik = 1000000000000000L; // Base for NIK (16 digits)
            long baseNpwp = 100000000000000L; // Base for NPWP (15 digits)
            long baseNoRekening = 100000000000L; // Base for NoRekening (12 digits)

            // Random ranges based on lengths
            long rangeNik = 1000000000000L; // Range for NIK addition
            long rangeNpwp = 100000000000L; // Range for NPWP addition
            long rangeNoRekening = 1000000000L; // Range for NoRekening addition

            // Ensure the range is positive and greater than zero before using it
            if (rangeNik <= 0 || rangeNpwp <= 0 || rangeNoRekening <= 0) {
                throw new IllegalArgumentException("Range for random number generation must be positive.");
            }

            // Generate random numbers with the correct length
            long nik = baseNik + (long) (random.nextDouble() * rangeNik);
            long npwp = baseNpwp + (long) (random.nextDouble() * rangeNpwp);
            long noRekening = baseNoRekening + (long) (random.nextDouble() * rangeNoRekening);

            UserModel user = new UserModel(UUID.randomUUID(), fullName, username, password, email,
                    nomorWA, role, false, poin, nik, npwp, noRekening);
            // UserModel user = new UserModel(UUID.randomUUID(), fullName, username, password, email,
            //         nomorWA, role, false, poin);
            users.add(user);
        }

        return users;
    }

    public List<UserModel> generateAdmin(int count) {
        List<UserModel> users = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < count; i++) {
            String fullName = "Admin Salam Setara " + i;
            String username = "Pak Admin" + i;
            String password = "admin" + i;
            String email = "admin" + i + "@sirelawan.com";
            Long nomorWA = 628000000000L + random.nextInt(1000000000);
            EnumRole role = EnumRole.SUPERADMIN;
            Long nik = 1000000000000L + random.nextInt(1000000000);
            Long npwp = 1000000000000L + random.nextInt(1000000000);
            Long noRekening = 1000000000000L + random.nextInt(1000000000);

            UserModel user = new UserModel(UUID.randomUUID(), fullName, username, password, email,
                    nomorWA, role, false, 0, nik, npwp, noRekening);
            // UserModel user = new UserModel(UUID.randomUUID(), fullName, username, password, email,
            //         nomorWA, role, false, 0);
            users.add(user);
        }

        return users;
    }
}
