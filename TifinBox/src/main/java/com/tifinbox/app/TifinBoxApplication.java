package com.tifinbox.app;

import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;
import com.tifinbox.app.model.Location;
import com.tifinbox.app.model.Role;
import com.tifinbox.app.model.ServiceCategory;
import com.tifinbox.app.model.TiffinCategory;
import com.tifinbox.app.repo.LocationRepo;
import com.tifinbox.app.repo.RoleRepo;
import com.tifinbox.app.repo.ServiceCategoryRepo;
import com.tifinbox.app.repo.TiffinCategoryRepo;

@SpringBootApplication
public class TifinBoxApplication implements CommandLineRunner {

	@Autowired
	RoleRepo roleRepo;

	@Autowired
	ServiceCategoryRepo serviceCategoryRepo;

	@Autowired
	TiffinCategoryRepo tiffinCategoryRepo;

	@Autowired
	LocationRepo locationRepo;

	public static void main(String[] args) {
		SpringApplication.run(TifinBoxApplication.class, args);
	}

	/* (non-Javadoc)
	 * @see org.springframework.boot.CommandLineRunner#run(java.lang.String[])
	 */
	@Override
	public void run(String... args) throws Exception {

		System.out.println(" in run method..");

		// --------------

		if (roleRepo.findAll().stream().count() <= 0) {
			Role r1 = new Role();
			r1.setName("Customer");
			roleRepo.save(r1);

			Role r2 = new Role();
			r2.setName("Vendor");
			roleRepo.save(r2);
		}

		if (serviceCategoryRepo.findAll().stream().count() <= 0) {
			ServiceCategory sc1 = new ServiceCategory();
			sc1.setCategoryName("Break fast");
			serviceCategoryRepo.save(sc1);

			ServiceCategory sc2 = new ServiceCategory();
			sc2.setCategoryName("Lunch");
			serviceCategoryRepo.save(sc2);

			ServiceCategory sc3 = new ServiceCategory();
			sc3.setCategoryName("Dinner");
			serviceCategoryRepo.save(sc3);
		}

		if (tiffinCategoryRepo.findAll().stream().count() <= 0) {
			TiffinCategory tc1 = new TiffinCategory();
			tc1.setCategoryName("Mini Tiffin");
			tiffinCategoryRepo.save(tc1);

			TiffinCategory tc2 = new TiffinCategory();
			tc2.setCategoryName("Regular Tiffin");
			tiffinCategoryRepo.save(tc2);

			TiffinCategory tc3 = new TiffinCategory();
			tc3.setCategoryName("Jumbo Tiffin");
			tiffinCategoryRepo.save(tc3);
		}
		if (locationRepo.findAll().stream().count() <= 0) 
		{
			final String uri = "https://indian-cities-api-nocbegfhqg.now.sh/cities";
			RestTemplate restTemplate = new RestTemplate();
			ResponseEntity<List<Location>> rateResponse = restTemplate.exchange(uri, HttpMethod.GET, null,
					new ParameterizedTypeReference<List<Location>>() {
					});
			List<Location> locations = rateResponse.getBody();
			locationRepo.saveAll(locations);

		}

	}

}
