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
@ComponentScan(basePackages = { "propensi.proyek.siRelawan" })
public class SiRelawanApplication {

	public static void main(String[] args) {
		SpringApplication.run(SiRelawanApplication.class, args);
	}
}
