package com.devstaff.farmcollector;

import com.devstaff.farmcollector.dao.CropReport;
import com.devstaff.farmcollector.dao.FarmReport;
import com.devstaff.farmcollector.entities.Crop;
import com.devstaff.farmcollector.entities.Farm;
import com.devstaff.farmcollector.repositories.CropRepository;
import com.devstaff.farmcollector.usecases.PlantAndHarvestService;
import com.devstaff.farmcollector.usecases.ReportService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.MOCK, classes={ FarmcollectorApplication.class })
class FarmcollectorApplicationTests {

	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext webApplicationContext;

	@MockBean
	private CropRepository cropRepository;

	@MockBean
	private ReportService reportService;

	@BeforeEach
	public void setUp() {
		this.mockMvc = webAppContextSetup(webApplicationContext).build();
	}

	@Test
	 void should_addPlantedData_When_ValidRequest() throws Exception {
		String url = "/api/planted";

		this.mockMvc.perform(post(url)
				.contentType(MediaType.APPLICATION_JSON)
				.content("{ \"cropName\": \"maize\", \"plantingArea\": 5000.0 , \"expectedProduct\": 10000.0, \"farName\": \"piwuorie-farm\"}")
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status()
						.isOk());

	}

	@Test
	 void should_addHarvestedData_When_ValidRequest() throws Exception {
		String url = "/api/harvested";

		Crop crop = new Crop();
		crop.setCropName("maize");
		crop.setFarm(new Farm());

		when(cropRepository.findByCropNameIgnoreCaseAndFarm_FarmNameIgnoreCase(any(),any())).thenReturn(Optional.of(crop));

		this.mockMvc.perform(post(url)
						.contentType(MediaType.APPLICATION_JSON)
						.content("{ \"cropName\": \"maize\", \"actualProduct\": 5000.0, \"farName\": \"piwuorie-farm\"}")
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status()
						.isOk());

	}

	@Test
	 void should_return_farm_report() throws Exception{
		String url = "/api/report/farm/piwuorie-farm";

		FarmReport farmReport = new FarmReport("piwuorie-farm", List.of(new FarmReport.Crop("maize",500,700,300, LocalDate.now(),LocalDate.now())));
		when(reportService.getReportByFarm(any())).thenReturn(farmReport);

		mockMvc.perform(get(url)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.farmName").value("piwuorie-farm"))
				.andExpect(jsonPath("$.cropReport[0].cropName").value("maize"))
				.andExpect(jsonPath("$.cropReport[0].actualProduct").value(300));
	}

	@Test
	 void should_return_crop_report() throws Exception{
		String url = "/api/report/crop/maize";

		CropReport cropReport = new CropReport("maize", List.of(new CropReport.Farm(500,700,300,"piwuorie-farm", LocalDate.now(),LocalDate.now())));
		when(reportService.getReportByCrop(any())).thenReturn(cropReport);

		mockMvc.perform(get(url)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.cropName").value("maize"))
				.andExpect(jsonPath("$.farmReport[0].farmName").value("piwuorie-farm"))
				.andExpect(jsonPath("$.farmReport[0].actualProduct").value(300));
	}

}
