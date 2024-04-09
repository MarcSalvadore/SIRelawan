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
            Long nik = 1000000000000L + random.nextInt(1000000000);
            Long npwp = 1000000000000L + random.nextInt(1000000000);
            Long noRekening = 1000000000000L + random.nextInt(1000000000);

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
